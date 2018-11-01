export default {
  appName: '种子应用',
  errorPage: {
    btnContent: '返回主页',
    tip: {
      error403: "抱歉，你无权访问该页面",
      error404: "抱歉，你访问的页面不存在",
      error500: "抱歉，服务器出错了"
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
    notify: {
      label: '消息',
      icon: 'message',
      tip: {
        prefix: '你有',
        suffix: '条新消息'
      }
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
        {label: '用户管理', icon: 'user', link: '/user', pageType: 'manage', menuKey: 'auth'},
        {label: 'RBAC管理', icon: 'appstore-o', link: '/rbac', menuKey: 'auth', custom: 'rbac'},
        {label: '鉴权规则', icon: 'filter', link: '/filter-chain', menuKey: 'auth', custom: 'filter-chain'},
      ]
    },
    {label: '系统监控', icon: 'line-chart', key: 'monitor', children: [
        {label: 'Session管理', icon: 'link',link: '/session', menuKey: 'monitor', custom: 'session'},
        {label: 'Druid监控', icon: 'database',link: '/druid/index.html', pageType: 'iframe', menuKey: 'monitor'}
      ]
    },
    {label: '消息推送', icon: 'message', key: 'msg', link: '/message'},
    {label: '开发工具', icon: 'tool', key: 'dev-tool', children: [
        {label: '接口文档', icon: 'api', link: '/swagger-ui.html', pageType: 'iframe', menuKey: 'dev-tool'},
        {label: '代码生成器', icon: 'code-o', link: '/code-gen', menuKey: 'dev-tool', custom: 'code-gen'}
      ]
    },
  ],
  breadcrumb: {
    label: '主页',
    options: {
      '/dashboard': ['概览'],
      '/user': ['系统安全', '用户管理'],
      '/rbac': ['系统安全', 'RBAC管理'],
      '/filter-chain': ['系统安全', '鉴权规则'],
      '/session': ['系统监控', 'Session管理'],
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
    refreshBtn: '刷新',
    searchBtn: '搜索',
  },
  search: {
    title: '搜索',
    idLabel: 'ID：',
    idPlaceholder: '请填写ID',
    keywordLabel: '关键字：',
    keywordPlaceholder: '请填写查找关键字',
    resetBtn: '重置',
    confirmBtn: '查找',
  },
  columns: {
    '/user': {
      tableColumns: [
        {title: 'ID', width: '15%', dataIndex: 'id'},
        {title: '用户名', width: '15%', dataIndex: 'username'},
        {title: '姓名', width: '15%', dataIndex: 'name'},
        {title: '手机号码', width: '10%', dataIndex: 'phone'},
        {title: 'Email', width: '15%', dataIndex: 'email'},
        {title: '性别', width: '10%', dataIndex: 'gender'},
        {title: '操作', width: '20%', scopedSlots: {customRender: 'opt'}},
      ],
      expandedColumns: [
        {title: '生日', dataIndex: 'birthday'},
        {title: '创建时间', dataIndex: 'createdAt', isDate: true},
        {title: '创建人', dataIndex: 'createdBy'},
        {title: '更新时间', dataIndex: 'updatedAt', isDate: true},
        {title: '更新人', dataIndex: 'updatedBy'},
      ]
    },
    '/session': {
      tableColumns: [
        {title: 'SessionID', width: '20%', dataIndex: 'id'},
        {title: '开始时间', width: '15%', scopedSlots: {customRender: 'startTimestamp'}},
        {title: '停止时间', width: '15%', scopedSlots: {customRender: 'stopTimestamp'}},
        {title: '最后活动时间', width: '15%', scopedSlots: {customRender: 'lastAccessTime'}},
        {title: '状态', width: '10%', scopedSlots: {customRender: 'expired'}},
        {title: 'Host', width: '15%', dataIndex: 'host'},
        {title: '操作', width: '10%', scopedSlots: {customRender: 'opt'}},
      ]
    }
  },
  operation: {
    editBtn: '编辑',
    deleteBtn: '删除',
    batchDeleteTip: '确认批量删除选中的记录吗？',
    deleteTip: '确认删除该记录吗？',
    confirmText: '确定',
    cancelText: '取消'
  },
  pageLabel: {
    '/user': '用户',
  },
  modal: {
    createTitle: '创建',
    editTitle: '编辑'
  },
  successResult: {
    create: '创建成功',
    edit: '编辑成功',
    delete: '删除成功',
    batchDelete: '批量删除成功',
    save: '保存成功',
  },
}