const config = {
  appName: '种子应用',
  nav: {
    logo: 'https://bulma.io/images/bulma-logo.png',
    avatar: 'https://avatars3.githubusercontent.com/u/12194490?s=460&v=4',
    theme: {
      label: '主题', icon: 'fa fa-palette',
      options: {
        nav:[
          {label: '导航-主色', theme: 'is-primary'},
          {label: '导航-亮色', theme: 'is-light'},
          {label: '导航-暗色', theme: 'is-dark'},
          {label: '导航-绿色', theme: 'is-success'},
          {label: '导航-黄色', theme: 'is-warning'},
          {label: '导航-红色', theme: 'is-danger'},
          {label: '导航-蓝色', theme: 'is-info'},
        ],
        sidebar: [
          {label: '边栏-暗色', theme: 'is-dark'},
          {label: '边栏-亮色', theme: 'is-light'},
        ]
      }
    },
    language: {
      label: '语言', icon: 'fa fa-globe',
      options: [
        {label: 'English', locale: 'en'},
        {label: '简体中文', locale: 'zh_CN'},
      ]
    },
    notify: {
      label: '消息', icon: 'fa fa-envelope'
    },
    userMenu: [
      {label: '个人中心', icon: 'fa fa-user', isDivided: false, link: '/profile'},
      {label: '修改密码', icon: 'fa fa-lock', isDivided: false, link: '/modify-password'},
      {label: '退出登录', icon: 'fa fa-sign-out', isDivided: true, link: '/logout', isDirect: true},
      {label: '报告错误', icon: 'fa fa-bug', isDivided: true, link: '/bug-report'},
    ]
  },
  sidebar: [
    {label: '概览', options: [
        {label: '仪表盘', icon: 'dashboard', link: '/dashboard', custom: 'dashboard'}
      ]
    },
    {label: '系统管理', options: [
        {label: '组织管理', icon: 'building', children: [
            {label: '用户管理', icon: 'users', link: '/user', pageType: 'manage', menuKey: '组织管理', breadcrumb: ['系统管理', '组织管理', '用户管理']},
            {label: '部门管理', icon: 'building', link: '/department', pageType: 'manage', menuKey: '组织管理', breadcrumb: ['系统管理', '组织管理', '部门管理']},
          ]
        },
        {label: '权限管理', icon: 'shield', children: [
            {label: '角色管理', icon: 'user', link: '/role', pageType: 'manage', menuKey: '权限管理', breadcrumb: ['系统管理', '组织管理', '角色管理']},
            {label: '权限管理', icon: 'shield', link: '/privilege', pageType: 'manage', menuKey: '权限管理', breadcrumb: ['系统管理', '组织管理', '权限管理']},
            {label: '鉴权规则', icon: 'filter', link: '/filter-chain', pageType: 'manage', menuKey: '权限管理', breadcrumb: ['系统管理', '组织管理', '鉴权规则']},
          ]
        },
        {label: '系统监控', icon: 'tachometer', children: [
            {label: 'Druid监控', icon: 'database', link: '/druid/index.html', pageType: 'iframe', menuKey: '系统监控'}
          ]
        },
        {label: '消息推送', icon: 'comment-o'},
        {label: '开发工具', icon: 'wrench', children: [
            {label: '接口文档', icon: 'code', link: '/swagger-ui.html', pageType: 'iframe', menuKey: '开发工具'},
            {label: '代码生成器', icon: 'file-code-o', link: '/code-gen', menuKey: '开发工具', custom: 'code-gen'}
          ]
        },
      ]
    },
  ],
};

export default config