package com.yupaits.web.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupaits.auth.entity.*;
import com.yupaits.auth.mapper.*;
import com.yupaits.commons.consts.SecurityConsts;
import lombok.Data;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yupaits
 * @date 2018/10/16
 */
@Component
public class UserRealm extends AuthorizingRealm {

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final RoleMapper roleMapper;
    private final RolePrivilegeMapper rolePrivilegeMapper;
    private final PrivilegeMapper privilegeMapper;

    @Autowired
    public UserRealm(UserMapper userMapper, UserRoleMapper userRoleMapper, RoleMapper roleMapper,
                     RolePrivilegeMapper rolePrivilegeMapper, PrivilegeMapper privilegeMapper) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.roleMapper = roleMapper;
        this.rolePrivilegeMapper = rolePrivilegeMapper;
        this.privilegeMapper = privilegeMapper;
        this.setCredentialsMatcher(credentialsMatcher());
    }

    private HashedCredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
        credentialsMatcher.setHashIterations(SecurityConsts.ITERATIONS);
        return credentialsMatcher;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", token.getUsername()));
        if (user == null) {
            throw new UnknownAccountException(String.format("用户%s不存在", token.getUsername()));
        }
        if (!user.getEnabled()) {
            throw new DisabledAccountException(String.format("%s已被禁用", token.getUsername()));
        }
        ByteSource salt = ByteSource.Util.bytes(user.getUsername() + SecurityConsts.CREDENTIALS_SALT);
        return new SimpleAuthenticationInfo(user, user.getPassword(), salt, getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();
        UserRolesAndPermissions userRolesAndPermissions = getFromUser(user);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(userRolesAndPermissions.getRoles());
        authorizationInfo.addStringPermissions(userRolesAndPermissions.getPermissions());
        return authorizationInfo;
    }

    @Data
    protected class UserRolesAndPermissions {
        private final Set<String> roles;
        private final Set<String> permissions;

        public UserRolesAndPermissions(Set<String> roles, Set<String> permissions) {
            this.roles = roles;
            this.permissions = permissions;
        }
    }

    /**
     * 根据用户信息获取用户拥有的角色和权限信息
     * @param user 用户信息
     * @return 用户拥有的角色和权限信息
     */
    public UserRolesAndPermissions getFromUser(User user) {
        List<Role> roleList = roleMapper.selectBatchIds(userRoleMapper.selectList(new QueryWrapper<UserRole>()
                .eq("user_id", user.getId()))
                .stream().map(UserRole::getRoleId).collect(Collectors.toList()));
        Set<String> roles = roleList.stream().map(Role::getRoleKey).collect(Collectors.toSet());
        Set<String> permissions = privilegeMapper.selectBatchIds(rolePrivilegeMapper.selectList(new QueryWrapper<RolePrivilege>()
                .in("role_id", roleList.stream().map(Role::getId).collect(Collectors.toList())))
                .stream().map(RolePrivilege::getPrivilegeId).collect(Collectors.toList()))
                .stream().map(Privilege::getPrivilegeKey).collect(Collectors.toSet());
        return new UserRolesAndPermissions(roles, permissions);
    }
}
