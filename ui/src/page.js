import ScheduleToolbar from './components/toolbar/ScheduleToolbar'
import ScheduleForm from './components/form/ScheduleForm'
import ScheduleOpt from './components/opt/ScheduleOpt'
import ScheduleSearch from './components/search/ScheduleSearch'
import UserToolbar from './components/toolbar/UserToolbar'
import UserForm from './components/form/UserForm'
import UserOpt from './components/opt/UserOpt'
import UserSearch from './components/search/UserSearch'
import MpAccountToolbar from './components/toolbar/MpAccountToolbar'
import MpAccountForm from './components/form/MpAccountForm'
import MpAccountOpt from './components/opt/MpAccountOpt'
import MpAccountSearch from './components/search/MpAccountSearch'

export default {
  '/user': {
    toolbarComponent: UserToolbar,
    formComponent: UserForm,
    optComponent: UserOpt,
    searchComponent: UserSearch
  },
  '/schedule': {
    toolbarComponent: ScheduleToolbar,
    formComponent: ScheduleForm,
    optComponent: ScheduleOpt,
    searchComponent: ScheduleSearch
  },
  '/mp-account': {
    toolbarComponent: MpAccountToolbar,
    formComponent: MpAccountForm,
    optComponent: MpAccountOpt,
    searchComponent: MpAccountSearch
  }
}