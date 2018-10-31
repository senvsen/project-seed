import UserForm from './components/form/UserForm'
import UserOpt from './components/opt/UserOpt'
import UserSearch from './components/search/UserSearch'

export default {
  '/user': {
    formComponent: UserForm,
    optComponent: UserOpt,
    searchComponent: UserSearch
  },
  '/role': {
    formComponent: UserForm,
    optComponent: UserOpt,
    searchComponent: UserSearch
  },
  '/privilege': {
    formComponent: UserForm,
    optComponent: UserOpt,
    searchComponent: UserSearch
  },
  '/department': {
    formComponent: UserForm,
    optComponent: UserOpt,
    searchComponent: UserSearch
  },
}