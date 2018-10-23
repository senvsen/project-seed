const config = {
  nav: {
    logo: 'https://bulma.io/images/bulma-logo.png',
    avatar: 'https://avatars3.githubusercontent.com/u/12194490?s=460&v=4',
    menu: [
      {
        label: '语言', icon: 'fas fa-globe',
        options: [
          {label: 'English', link: '/index?locale=en'},
          {label: '简体中文', link: '/index?locale=zh_CN'},
        ]
      }
    ],
    notify: {
      label: '消息', icon: 'fas fa-envelope'
    },
    userMenu: [
      {label: '个人中心', icon: 'fas fa-user', isDivided: false, link: ''},
      {label: '修改密码', icon: 'fas fa-lock', isDivided: false, link: ''},
      {label: '退出登录', icon: 'fas fa-sign-out-alt', isDivided: true, link: '/logout'},
      {label: '报告错误', icon: 'fas fa-bug', isDivided: true, link: ''},
    ]
  },
  sidebar: [
    {
      label: '概览', options: [
        {label: '仪表盘', icon: 'fas fa-tachometer-alt', link: '/dashboard'}
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
        {label: '开发工具', icon: 'fas fa-toolbox', children: [
            {label: '代码生成器', icon: 'fas fa-laptop-code', link: '/code-gen'}
          ]
        },
      ]
    },
  ]
};

export default config