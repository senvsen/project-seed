# ui

使用vue-cli构建页面。

## 目录结构

|目录/文件名称|类型|说明|
|---|---|---|
|api|目录|封装异步数据请求，多模块|
|assets|目录|图片等静态资源文件|
|components|目录|自定义vue组件|
|pages|目录|使用@vue/cli定义的多页面|
|styles|目录|全局样式|
|utils|目录|公共函数|
|handler.js|文件|ManagePage.vue页面需要的api请求handler映射|
|messages.js|文件|项目常量、参数封装，可与vue-i18n进行整合|
|page.js|文件|ManagePage.vue页面拓展插槽自定义组件映射|

## 使用说明

1. 当新增一个业务领域管理页面（`pageType` 为 `manage`）时，需要在以下位置编写相应的配置信息。

    - api中新增接口封装
    - components中新增 **form表单组件**、**opt表格拓展操作项组件**、**search自定义搜索组件**、**toolbar表格工具栏拓展操作组件**
    - handler.js中新增api请求handler映射关系
    - messages.js的**sidebar**常量中新增侧边栏菜单项，**breadcrumb**常量中新增面包屑导航信息，**columns**常量中新增表格列定义和行展开属性定义，**pageLabel**常量中新增弹出Modal标题信息
    - page.js中新增ManagePage.vue页面拓展插槽自定义组件（即上述的form、opt、search、toolbar组件）映射关系

1. 当新增一个自定义页面时（`custom`），需要在以下位置编写相应配置信息。

    - api中新增接口封装
    - `pages -> index -> views -> custom` 中新增自定义页面组件
    - `pages -> index -> router.js` 的**customPage**常量中新增自定义页面组件与custom参数的映射关系
    - messages.js的**breadcrumb**常量中新增面包屑导航信息

1. 当新增一个内嵌iframe页面时（`pageType` 为 `iframe`），需要在messages.js的**sidebar**常量中新增侧边栏菜单项，**breadcrumb**常量中新增面包屑导航信息。