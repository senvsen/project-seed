import zhCN from 'ant-design-vue/lib/locale-provider/zh_CN'
import enUS from 'ant-design-vue/lib/locale-provider/en_US'

const zh_CN = {
  appName: '种子应用',
  errorPage: {
    btnContent: '返回主页',
    tip: {
      403: "抱歉，你无权访问该页面",
      404: "抱歉，你访问的页面不存在",
      500: "抱歉，服务器出错了"
    }
  },
  loginPage: {
    title: '登录',
    username: {
      placeholder: '请填写用户名',
      emptyTip: '用户名不能为空！',
    },
    password: {
      placeholder: '请填写登录密码',
      emptyTip: '登录密码不能为空！',
      lengthTip: '登录密码长度不能小于6！'
    },
    rememberMe: '记住我',
    forget: '忘记密码',
    login: '登录',
    register: '现在注册！',
  },
  nav: {
    logo: 'https://bulma.io/images/bulma-logo.png',
    language: {
      label: '语言', icon: 'global',
      options: [
        {key: 'zh_CN', label: '简体中文', locale: zhCN},
        {key: 'en', label: '英语', locale: enUS},
      ]
    },
    notify: {
      label: '消息',
      icon: 'message',
      tip: '你有 {n} 条新消息'
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
        {label: '用户管理', icon: 'user', link: '/user', pageType: 'manage', menuKey: 'auth', breadcrumb: ['系统安全', '用户管理']},
        {label: '部门管理', icon: 'appstore-o', link: '/department', pageType: 'manage', menuKey: 'auth', breadcrumb: ['系统安全', '部门管理']},
        {label: '角色管理', icon: 'idcard', link: '/role', pageType: 'manage', menuKey: 'auth', breadcrumb: ['系统安全', '角色管理']},
        {label: '权限管理', icon: 'profile', link: '/privilege', pageType: 'manage', menuKey: 'auth', breadcrumb: ['系统安全', '权限管理']},
        {label: '鉴权规则', icon: 'filter', link: '/filter-chain', pageType: 'manage', menuKey: 'auth', breadcrumb: ['系统安全', '鉴权规则']},
      ]
    },
    {label: '系统监控', icon: 'line-chart', key: 'monitor', children: [
        {label: 'Druid监控', icon: 'database',link: '/druid/index.html', pageType: 'iframe', menuKey: 'monitor'}
      ]
    },
    {label: '消息推送', icon: 'message', key: 'msg'},
    {label: '开发工具', icon: 'tool', key: 'dev-tool', children: [
        {label: '接口文档', icon: 'api', link: '/swagger-ui.html', pageType: 'iframe', menuKey: 'dev-tool'},
        {label: '代码生成器', icon: 'code-o', link: '/code-gen', menuKey: 'dev-tool', custom: 'code-gen', breadcrumb: ['开发工具', '代码生成器']}
      ]
    },
  ],
  breadcrumb: {
    label: '主页',
    options: {
      '/user': ['系统安全', '用户管理'],
      '/department': ['系统安全', '部门管理'],
      '/role': ['系统安全', '角色管理'],
      '/privilege': ['系统安全', '权限管理'],
      '/filter-chain': ['系统安全', '鉴权规则'],
      '/code-gen': ['开发工具', '代码生成器'],
    }
  },
  pager: {
    showQuickJumper: true,
    showSizeChanger: true,
    pageSize: 10,
    pageSizeOptions: ['10', '20', '30', '50']
  },
  toolbar: {
    createBtn: '创建',
    batchDeleteBtn: '批量删除',
    searchBtn: '搜索',
  },
  search: {
    title: '搜索',
    idLabel: 'ID：',
    idPlaceholder: '请填写ID',
    keywordLabel: '关键字：',
    keywordPlaceholder: '请填写查找关键字',
    closeBtn: '取消',
    confirmBtn: '查找',
  },
  columns: {
    '/user': [
      {title: 'ID', width: '15%', dataIndex: 'id'},
      {title: '姓名', width: '15%', dataIndex: 'name'},
      {title: '创建时间', width: '15%', scopedSlots: {customRender: 'createdAt'}},
      {title: '更新时间', width: '15%', scopedSlots: {customRender: 'updatedAt'}},
      {title: '操作', scopedSlots: {customRender: 'opt'}},
    ],
    '/department': [
      {title: 'ID', width: '15%', dataIndex: 'id'},
      {title: '创建时间', width: '15%', scopedSlots: {customRender: 'createdAt'}},
      {title: '更新时间', width: '15%', scopedSlots: {customRender: 'updatedAt'}},
      {title: '操作', scopedSlots: {customRender: 'opt'}},
    ],
    '/role': [
      {title: 'ID', width: '15%', dataIndex: 'id'},
      {title: '创建时间', width: '15%', scopedSlots: {customRender: 'createdAt'}},
      {title: '更新时间', width: '15%', scopedSlots: {customRender: 'updatedAt'}},
      {title: '操作', scopedSlots: {customRender: 'opt'}},
    ],
    '/privilege': [
      {title: 'ID', width: '15%', dataIndex: 'id'},
      {title: '创建时间', width: '15%', scopedSlots: {customRender: 'createdAt'}},
      {title: '更新时间', width: '15%', scopedSlots: {customRender: 'updatedAt'}},
      {title: '操作', scopedSlots: {customRender: 'opt'}},
    ],
    '/filter-chain': [
      {title: 'ID', width: '15%', dataIndex: 'id'},
      {title: '创建时间', width: '15%', scopedSlots: {customRender: 'createdAt'}},
      {title: '更新时间', width: '15%', scopedSlots: {customRender: 'updatedAt'}},
      {title: '操作', scopedSlots: {customRender: 'opt'}},
    ],
  },
  operation: {
    editBtn: '编辑',
    deleteBtn: '删除',
    batchDeleteTip: '确认批量删除选中的{label}吗？',
    deleteTip: '确认删除该{label}吗？',
    confirmText: '确定',
    cancelText: '取消'
  },
  pageLabel: {
    '/user': '用户',
    '/department': '部门',
    '/role': '角色',
    '/privilege': '权限',
    '/filter-chain': '规则',
  },
  modal: {
    createTitle: '创建{label}',
    editTitle: '编辑{label}'
  }
};

export default zh_CN