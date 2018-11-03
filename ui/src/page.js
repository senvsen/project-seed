import ScheduleToolbar from './components/toolbar/ScheduleToolbar'
import ScheduleForm from './components/form/ScheduleForm'
import ScheduleOpt from './components/opt/ScheduleOpt'
import ScheduleSearch from './components/search/ScheduleSearch'
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
  '/schedule': {
    toolbarComponent: ScheduleToolbar,
    formComponent: ScheduleForm,
    optComponent: ScheduleOpt,
    searchComponent: ScheduleSearch
  }
}