import UserToolbar from './components/toolbar/UserToolbar'
import UserForm from './components/form/UserForm'
import UserOpt from './components/opt/UserOpt'
import UserSearch from './components/search/UserSearch'

export default {
  '/user': {
    toolbarComponent: UserToolbar,
    formComponent: UserForm,
    optComponent: UserOpt,
    searchComponent: UserSearch
  },
}