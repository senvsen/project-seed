const config = {
  appName: '种子应用',
  nav: {
    logo: 'https://bulma.io/images/bulma-logo.png',
    avatar: 'https://avatars3.githubusercontent.com/u/12194490?s=460&v=4',
    menu: {
      system: {
        label: '系统管理',
        sidebar: [
          {
            label: '概览', options: [
              {label: '仪表盘', icon: 'fas fa-tachometer-alt', link: '/dashboard', menuKey: 'system', custom: 'dashboard'}
            ]
          },
          {
            label: '系统管理', options: [
              {label: '组织管理', icon: 'fas fa-building', children: [
                  {label: '用户管理', icon: 'fas fa-users', link: '/user', pageType: 'manage', menuKey: 'system', breadcrumb: ['系统管理', '组织管理', '用户管理']},
                  {label: '部门管理', icon: 'fas fa-building', link: '/department', pageType: 'manage', menuKey: 'system', breadcrumb: ['系统管理', '组织管理', '部门管理']},
                ]
              },
              {label: '权限管理', icon: 'fas fa-shield-alt', children: [
                  {label: '角色管理', icon: 'fas fa-user-tag', link: '/role', pageType: 'manage', menuKey: 'system', breadcrumb: ['系统管理', '组织管理', '角色管理']},
                  {label: '权限管理', icon: 'fas fa-shield-alt', link: '/privilege', pageType: 'manage', menuKey: 'system', breadcrumb: ['系统管理', '组织管理', '权限管理']},
                  {label: '鉴权规则', icon: 'fas fa-user-shield', link: '/filter-chain', pageType: 'manage', menuKey: 'system', breadcrumb: ['系统管理', '组织管理', '鉴权规则']},
                ]
              },
              {label: '系统监控', icon: 'fas fa-chart-line', children: [
                  {label: 'Druid监控', icon: 'fas fa-database', link: '/druid/index.html', pageType: 'iframe', menuKey: 'system'}
                ]
              },
              {label: '消息推送', icon: 'fas fa-comment-alt'},
              {label: '开发工具', icon: 'fas fa-toolbox', children: [
                  {label: '接口文档', icon: 'fas fa-code', link: '/swagger-ui.html', pageType: 'iframe', menuKey: 'system'},
                  {label: '代码生成器', icon: 'fas fa-laptop-code', link: '/code-gen', menuKey: 'system', custom: 'code-gen'}
                ]
              },
            ]
          },
        ]
      },
      biz: {
        label: '业务管理',
        sidebar: [

        ]
      }
    },
    theme: {
      label: '主题', icon: 'fas fa-palette',
      options: [
        {label: '主色', theme: 'is-primary'},
        {label: '亮色', theme: 'is-white'},
        {label: '暗色', theme: 'is-dark'},
        {label: '绿色', theme: 'is-success'},
        {label: '黄色', theme: 'is-warning'},
        {label: '红色', theme: 'is-danger'},
        {label: '蓝色', theme: 'is-info'},
      ]
    },
    language: {
      label: '语言', icon: 'fas fa-globe',
      options: [
        {label: 'English', locale: 'en'},
        {label: '简体中文', locale: 'zh_CN'},
      ]
    },
    notify: {
      label: '消息', icon: 'fas fa-envelope'
    },
    userMenu: [
      {label: '个人中心', icon: 'fas fa-user', isDivided: false, link: '/profile'},
      {label: '修改密码', icon: 'fas fa-lock', isDivided: false, link: '/modify-password'},
      {label: '退出登录', icon: 'fas fa-sign-out-alt', isDivided: true, link: '/logout', isDirect: true},
      {label: '报告错误', icon: 'fas fa-bug', isDivided: true, link: '/bug-report'},
    ]
  },
};

export default config