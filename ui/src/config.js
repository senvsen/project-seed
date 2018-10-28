const config = {
  appName: '种子应用',
  nav: {
    logo: 'https://bulma.io/images/bulma-logo.png',
    avatar: 'https://avatars3.githubusercontent.com/u/12194490?s=460&v=4',
    theme: {
      label: '主题', icon: 'skin',
      options: {
        nav:[
          {label: '导航-亮色', theme: '#5e5e5e'},
          {label: '导航-暗色', theme: '#001529'},
        ],
        sidebar: [
          {label: '边栏-暗色', theme: 'dark'},
          {label: '边栏-亮色', theme: 'light'},
        ]
      }
    },
    language: {
      label: '语言', icon: 'global',
      options: [
        {label: 'English', locale: 'en'},
        {label: '简体中文', locale: 'zh_CN'},
      ]
    },
    notify: {
      label: '消息', icon: 'fa fa-envelope'
    },
    userMenu: [
      {label: '个人中心', icon: 'user', isDivided: false, link: '/profile'},
      {label: '修改密码', icon: 'key', isDivided: false, link: '/modify-password'},
      {label: '退出登录', icon: 'logout', isDivided: true, link: '/logout', isDirect: true},
      {label: '报告错误', icon: 'form', isDivided: true, link: '/bug-report'},
    ]
  },
  sidebar: [
    {label: '概览', icon: 'dashboard', link: '/dashboard', custom: 'dashboard'},
    {label: '系统安全', icon: 'safety', key: 'auth', children: [
        {label: '用户管理', link: '/user', pageType: 'manage', menuKey: 'auth', breadcrumb: ['系统安全', '用户管理']},
        {label: '部门管理', link: '/department', pageType: 'manage', menuKey: 'auth', breadcrumb: ['系统安全', '部门管理']},
        {label: '角色管理', link: '/role', pageType: 'manage', menuKey: 'auth', breadcrumb: ['系统安全', '角色管理']},
        {label: '权限管理', link: '/privilege', pageType: 'manage', menuKey: 'auth', breadcrumb: ['系统安全', '权限管理']},
        {label: '鉴权规则', link: '/filter-chain', pageType: 'manage', menuKey: 'auth', breadcrumb: ['系统安全', '鉴权规则']},
      ]
    },
    {label: '系统监控', icon: 'line-chart', key: 'monitor', children: [
        {label: 'Druid监控', link: '/druid/index.html', pageType: 'iframe', menuKey: 'monitor'}
      ]
    },
    {label: '消息推送', icon: 'message', key: 'msg'},
    {label: '开发工具', icon: 'tool', key: 'dev-tool', children: [
        {label: '接口文档', link: '/swagger-ui.html', pageType: 'iframe', menuKey: 'dev-tool'},
        {label: '代码生成器', link: '/code-gen', menuKey: 'dev-tool', custom: 'code-gen', breadcrumb: ['开发工具', '代码生成器']}
      ]
    },
  ]
};

export default config