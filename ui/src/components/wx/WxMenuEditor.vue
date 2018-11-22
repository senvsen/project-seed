<template>
  <div>
    <a-button type="primary" icon="sync" :disabled="editing" class="mb-1" @click="handleMenuSave">同步到公众号</a-button>
    <a-row :gutter="16">
      <a-col :span="8" v-for="(button, idx) in wxMenu.button" :key="button.name">
        <a-row type="flex" align="bottom" class="sub-menu-container">
          <a-col :span="24">
            <div v-for="(subButton, index) in button.sub_button" :key="subButton.name" class="sub-menu-item" :class="{'active': isEditing('submenu', subButton.name)}">
              {{subButton.name}}
              <a-button-group size="small" class="is-pulled-right" v-if="!editing">
                <a-button icon="arrow-up" title="向上移动" v-if="index > 0" @click="moveButton('up', button.sub_button, index)"></a-button>
                <a-button icon="arrow-down" title="向下移动" v-if="index < button.sub_button.length -1" @click="moveButton('down', button.sub_button, index)"></a-button>
                <a-button icon="edit" title="修改菜单" @click="editMenuButton(button.sub_button, 'submenu', subButton, index)"></a-button>
                <a-popconfirm title="确定要移除该菜单项吗？" trigger="click" placement="topRight" @confirm="removeMenuButton(button.sub_button, index)"
                              :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText">
                  <a-button icon="delete" title="移除菜单"></a-button>
                </a-popconfirm>
              </a-button-group>
            </div>
          </a-col>
        </a-row>

        <div class="menu-item" :class="{'active': isEditing('menu', button.name)}">
          {{button.name}}
          <a-button-group size="small" class="is-pulled-right" v-if="!editing">
            <a-button icon="arrow-left" title="向左移动" v-if="idx > 0" @click="moveButton('left', wxMenu.button, idx)"></a-button>
            <a-button icon="arrow-right" title="向右移动" v-if="idx < wxMenu.button.length - 1" @click="moveButton('right', wxMenu.button, idx)"></a-button>
            <a-button icon="plus" title="添加二级菜单" v-if="!button.type && (!button.sub_button || button.sub_button.length < 5)" @click="addMenuButton(button.sub_button, 'submenu')"></a-button>
            <a-button icon="edit" title="修改菜单" @click="editMenuButton(wxMenu.button, 'menu', button, idx)"></a-button>
            <a-popconfirm title="确定要移除该菜单项吗？" trigger="click" placement="topRight" @confirm="removeMenuButton(wxMenu.button, idx)"
                          :okText="$messages.operation.confirmText" :cancelText="$messages.operation.cancelText">
              <a-button icon="delete" title="移除菜单"></a-button>
            </a-popconfirm>
          </a-button-group>
        </div>
      </a-col>

      <a-col :span="8" v-if="wxMenu.button.length < 3">
        <div class="sub-menu-container"></div>
        <a-button icon="plus" :disabled="editing" @click="addMenuButton(wxMenu.button, 'menu')">添加一级菜单</a-button>
      </a-col>
    </a-row>

    <div v-if="editing">
      <a-divider>编辑菜单</a-divider>
      <a-form>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="菜单名称" required>
              <a-input v-model="button.name" placeholder="请填写菜单名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="响应动作" v-if="menuType === 'menu' && (button.sub_button === undefined || button.sub_button.length === 0)">
              <a-switch v-model="isAction">
                <a-icon type="check" slot="checkedChildren"/>
                <a-icon type="cross" slot="unCheckedChildren"/>
              </a-switch>
            </a-form-item>
          </a-col>
        </a-row>

        <div v-if="menuType === 'submenu' || (menuType === 'menu' && isAction)">
          <a-row :gutter="16">
            <a-col :span="12">
              <a-form-item label="响应类型" required>
                <a-select v-model="button.type" placeholder="请选择响应动作类型">
                  <a-select-option v-for="(label, type) in $messages.enums.wxMenuTypes" :key="type" :value="type">{{label}}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="网页链接" required v-if="['view', 'miniprogram'].indexOf(button.type) !== -1">
                <a-input v-model="button.url" placeholder="请填写网页链接"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="素材ID" required v-if="['media_id', 'view_limited'].indexOf(button.type) !== -1">
                <a-input v-model="button.media_id" placeholder="请填写素材ID"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="Key值" required v-if="['click', 'location_select'].indexOf(button.type) !== -1 || button.type.indexOf('scancode') !== -1 || button.type.indexOf('pic') !== -1">
                <a-input v-model="button.key" placeholder="请填写Key值"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="小程序AppId" required v-if="button.type === 'miniprogram'">
                <a-input v-model="button.appid" placeholder="请填写小程序AppId"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="小程序页面路径" required v-if="button.type === 'miniprogram'">
                <a-input v-model="button.pagepath" placeholder="请填写小程序页面路径"></a-input>
              </a-form-item>
            </a-col>
          </a-row>
        </div>
        <a-form-item>
          <a-button type="primary" class="mr-1" @click="confirmButton">提交</a-button>
          <a-button @click="editing = false">取消</a-button>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>

<script>
  const defaultButton = {name: '', type: 'click', key: '', url: '', media_id: '', appid: '', pagepath: '', sub_button: []};
  export default {
    name: "WxMenuEditor",
    props: {
      accountId: {
        type: String,
        required: true

      }
    },
    computed: {
      wxMenu() {
        return this.$store.getters.wxMenu;
      }
    },
    watch: {
      wxMenu(val) {
        this.$store.dispatch('setWxMenu', val);
      }
    },
    data() {
      return {
        button: {},
        menuType: '',
        editing: false,
        isAction: false,
        optType: '',
        editingButton: '',
        selectedButtonList: [],
        selectedButtonIndex: -1
      }
    },
    methods: {
      moveButton(moveType, list, index) {
        if (['up', 'left'].indexOf(moveType) !== -1) {
          list.splice(index - 1, 0, list.splice(index, 1)[0]);
        } else if (['down', 'right'].indexOf(moveType) !== -1) {
          list.splice(index + 1, 0, list.splice(index, 1)[0]);
        } else {
          console.error('不支持的移动方式');
        }
      },
      addMenuButton(list, buttonType) {
        this.menuType = buttonType;
        this.selectedButtonList = list;
        this.button = JSON.parse(JSON.stringify(defaultButton));
        this.isAction = false;
        this.optType = 'add';
        this.editing = true;
      },
      editMenuButton(list, menuType, button, index) {
        if (button.type === undefined) {
          button.type = '';
        }
        this.menuType = menuType;
        this.selectedButtonList = list;
        this.selectedButtonIndex = index;
        this.button = JSON.parse(JSON.stringify(button));
        this.isAction = menuType === 'menu' && button.type !== '';
        this.optType = 'edit';
        this.editingButton = menuType + button.name;
        this.editing = true;
      },
      confirmButton() {
        if (this.optType === 'add') {
          if (this.menuType === 'menu' && !this.isAction) {
            this.button = {type: '', name: this.button.name, sub_button: []};
          }
          this.selectedButtonList.push(this.button);
          this.editing = false;
        } else if (this.optType === 'edit') {
          if (this.menuType === 'menu' && !this.isAction) {
            this.button.type = '';
            if (this.button.sub_button === undefined) {
              this.$set(this.button, 'sub_button', []);
            }
          }
          this.selectedButtonList.splice(this.selectedButtonList, 1, this.button);
          this.editing = false;
        } else {
          console.error('不支持的操作类型');
        }
      },
      removeMenuButton(list, index) {
        list.splice(index, 1);
      },
      handleMenuSave() {
        this.$api.wx.saveMpMenu(this.accountId, this.$store.getters.wxMenu).then(() => {
          this.$message.success(this.$messages.successResult.save);
          this.$emit('synced');
        });
      },
      isEditing(menuType, buttonName) {
        return this.editingButton === menuType + buttonName;
      }
    }
  }
</script>

<style scoped>
  .sub-menu-container {
    height: 200px;
  }
  .menu-item {
    background: aliceblue;
    padding: 0 10px;
    line-height: 40px;
    border: 1px solid #e8e8e8;
    border-radius: 4px;
  }
  .menu-item.active {
    border: 1px solid #1890ff;
  }
  .sub-menu-item {
    margin-left: 16px;
    background: #eee;
    padding: 0 10px;
    margin-bottom: 3px;
    height: 38px;
    line-height: 40px;
    border: 1px solid #e8e8e8;
    border-radius: 4px;
  }
  .sub-menu-item.active {
    border: 1px solid #1890ff;
  }
</style>