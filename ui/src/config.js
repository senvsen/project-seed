const config = {
  nav: {
    logo: 'https://bulma.io/images/bulma-logo.png',
    avatar: 'https://avatars3.githubusercontent.com/u/12194490?s=460&v=4'
    userMenu: {

    }
  },
  sidebar: [
    {
      label: '概览', options: [
        {label: '仪表盘', icon: 'fas fa-tachometer-alt'}
      ]
    },
    {
      label: '系统管理', options: [
        {label: '组织管理', icon: 'fas fa-building', children: [
            {label: '用户管理', icon: 'fas fa-users'},
            {label: '部门管理', icon: 'fas fa-building'},
          ]
        },
        {label: '权限管理', icon: 'fas fa-shield-alt', children: [
            {label: '角色管理', icon: 'fas fa-user-tag'},
            {label: '权限管理', icon: 'fas fa-shield-alt'},
            {label: '系统管理员', icon: 'fas fa-user-shield'},
          ]
        },
        {label: '系统监控', icon: 'fas fa-chart-line'},
        {label: '消息推送', icon: 'fas fa-comment-alt'},
        {label: '开发工具', icon: 'fas fa-toolbox'},
      ]
    },
  ]
};

export default config