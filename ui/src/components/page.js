import UserForm from './form/UserForm'
import UserOpt from './opt/UserOpt'
import UserSearch from './search/UserSearch'

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
  '/filter-chain': {
    formComponent: UserForm,
    optComponent: UserOpt,
    searchComponent: UserSearch
  }
}