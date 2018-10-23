package com.yupaits.auth.vo;

import java.time.LocalDate;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yupaits.commons.core.serializer.LongSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户VO
 * </p>
 *
 * @author yupaits
 * @date 2018-10-23
 */
@Data
@ApiModel(value = "UserVO", description = "UserVO对象")
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "生日")
    private LocalDate birthday;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "创建人ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long createdBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "更新人ID")
    @JsonSerialize(using = LongSerializer.class)
    private Long updatedBy;

    @ApiModelProperty(value = "账户可用")
    private Boolean enabled;
}
