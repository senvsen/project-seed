import zhCN from 'ant-design-vue/lib/locale-provider/zh_CN'
import enUS from 'ant-design-vue/lib/locale-provider/en_US'

const en = {
  appName: 'Seed',
  errorPage: {
    btnContent: 'Back to home',
    tip: {
      403: "Sorry, you don't have access to this page",
      404: "Sorry, the page you visited does not exist",
      500: "Sorry, the server is reporting an error"
    }
  },
  loginPage: {
    title: 'Login',
    username: {
      placeholder: 'Please input username',
      emptyTip: 'Username cannot be empty!',
    },
    password: {
      placeholder: 'Please input password',
      emptyTip: 'Password cannot be empty!',
      lengthTip: 'Password length cannot be less than 6 !'
    },
    rememberMe: 'Remember me',
    forget: 'Forget password',
    login: 'Login',
    register: 'Register now！',
  },
  nav: {
    logo: 'https://bulma.io/images/bulma-logo.png',
    language: {
      label: 'Languages', icon: 'global',
      options: [
        {key: 'zh_CN', label: 'Chinese Simplified', locale: zhCN},
        {key: 'en', label: 'English', locale: enUS},
      ]
    },
    notify: {
      label: 'Messages',
      icon: 'message',
      tip: 'There is no message. | You have a new message. | You have {n} new messages.'
    },
    userMenu: [
      {label: 'Profile', icon: 'user', isDivided: false, link: '/profile'},
      {label: 'Modify Password', icon: 'key', isDivided: false, link: '/modify-password'},
      {label: 'Logout', icon: 'logout', isDivided: true, link: '/logout', isDirect: true},
      {label: 'Report Bug', icon: 'form', isDivided: true, link: '/bug-report'},
    ]
  },
  sidebar: [
    {label: 'Overview', icon: 'dashboard', link: '/dashboard', custom: 'dashboard'},
    {label: 'Security', icon: 'safety', key: 'auth', children: [
        {label: 'User', icon: 'user', link: '/user', pageType: 'manage', menuKey: 'auth'},
        {label: 'Department', icon: 'appstore-o', link: '/department', pageType: 'manage', menuKey: 'auth'},
        {label: 'Role', icon: 'idcard', link: '/role', pageType: 'manage', menuKey: 'auth'},
        {label: 'Privilege', icon: 'profile', link: '/privilege', pageType: 'manage', menuKey: 'auth'},
        {label: 'Authorization', icon: 'filter', link: '/filter-chain', pageType: 'manage', menuKey: 'auth'},
      ]
    },
    {label: 'Monitor', icon: 'line-chart', key: 'monitor', children: [
        {label: 'Druid Monitor', icon: 'database', link: '/druid/index.html', pageType: 'iframe', menuKey: 'monitor'}
      ]
    },
    {label: 'Notification', icon: 'message', key: 'msg'},
    {label: 'Developer Tool', icon: 'tool', key: 'dev-tool', children: [
        {label: 'API', icon: 'api', link: '/swagger-ui.html', pageType: 'iframe', menuKey: 'dev-tool'},
        {label: 'Code Generator', icon: 'code-o', link: '/code-gen', menuKey: 'dev-tool', custom: 'code-gen'}
      ]
    },
  ],
  breadcrumb: {
    label: 'Home',
    options: {
      '/user': ['Security', 'User'],
      '/department': ['Security', 'Department'],
      '/role': ['Security', 'Role'],
      '/privilege': ['Security', 'Privilege'],
      '/filter-chain': ['Security', 'Authorization'],
      '/code-gen': ['Developer Tool', 'Code Generator'],
    }
  },
  pager: {
    showQuickJumper: true,
    showSizeChanger: true,
    pageSize: 10,
    pageSizeOptions: ['10', '20', '30', '50']
  },
  toolbar: {
    createBtn: 'Create',
    batchDeleteBtn: 'Batch Delete',
    searchBtn: 'Search',
  },
  search: {
    title: 'Search',
    idLabel: 'ID: ',
    idPlaceholder: 'Please input id',
    keywordLabel: 'Keyword: ',
    keywordPlaceholder: 'Please input keyword',
    closeBtn: 'Cancel',
    confirmBtn: 'Go',
  },
  columns: {
    '/user': [
      {title: 'ID', width: '15%', dataIndex: 'id'},
      {title: 'Name', width: '15%', dataIndex: 'name'},
      {title: 'CreatedAt', width: '15%', scopedSlots: {customRender: 'createdAt'}},
      {title: 'UpdatedAt', width: '15%', scopedSlots: {customRender: 'updatedAt'}},
      {title: 'Operation', scopedSlots: {customRender: 'opt'}},
    ],
    '/department': [
      {title: 'ID', width: '15%', dataIndex: 'id'},
      {title: 'CreatedAt', width: '15%', scopedSlots: {customRender: 'createdAt'}},
      {title: 'UpdatedAt', width: '15%', scopedSlots: {customRender: 'updatedAt'}},
      {title: 'Operation', scopedSlots: {customRender: 'opt'}},
    ],
    '/role': [
      {title: 'ID', width: '15%', dataIndex: 'id'},
      {title: 'CreatedAt', width: '15%', scopedSlots: {customRender: 'createdAt'}},
      {title: 'UpdatedAt', width: '15%', scopedSlots: {customRender: 'updatedAt'}},
      {title: 'Operation', scopedSlots: {customRender: 'opt'}},
    ],
    '/privilege': [
      {title: 'ID', width: '15%', dataIndex: 'id'},
      {title: 'CreatedAt', width: '15%', scopedSlots: {customRender: 'createdAt'}},
      {title: 'UpdatedAt', width: '15%', scopedSlots: {customRender: 'updatedAt'}},
      {title: 'Operation', scopedSlots: {customRender: 'opt'}},
    ],
    '/filter-chain': [
      {title: 'ID', width: '15%', dataIndex: 'id'},
      {title: 'CreatedAt', width: '15%', scopedSlots: {customRender: 'createdAt'}},
      {title: 'UpdatedAt', width: '15%', scopedSlots: {customRender: 'updatedAt'}},
      {title: 'Operation', scopedSlots: {customRender: 'opt'}},
    ],
  },
  operation: {
    editBtn: 'Edit',
    deleteBtn: 'Delete',
    batchDeleteTip: 'Confirm to batch delete the selected {label} list？',
    deleteTip: 'Confirm to delete this {label}？',
    confirmText: 'OK',
    cancelText: 'Cancel'
  },
  pageLabel: {
    '/user': 'user',
    '/department': 'department',
    '/role': 'role',
    '/privilege': 'privileges',
    '/filter-chain': 'rule',
  },
  modal: {
    createTitle: 'Create {label}',
    editTitle: 'Edit {label}'
  }
};

export default en