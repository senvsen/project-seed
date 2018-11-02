<template>
  <div>
    <div v-for="(department, index) in departments" :key="department.id">
      <div class="item-box mb-1"
           :class="{'is-active': selectedDepartment.id === department.id}"
           @click="toggleSelect(department, index)">
        <a-row>
          <a-col :span="10">
            <a-icon type="team" class="mr-1"/> {{department.departmentKey}}
          </a-col>
          <a-col :span="14">
            {{department.name}}
          </a-col>
        </a-row>
      </div>
      <div class="ml-3" v-if="department.subDepartments && department.subDepartments.length > 0">
        <department :departments="department.subDepartments"
                    :selected-department="selectedDepartment"
                    @toggleSelect="(subDepartment, subIndex) => toggleSelect(subDepartment, subIndex)"/>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Department",
    props: {
      departments: {
        type: Array,
        required: false,
        default() {
          return [];
        }
      },
      selectedDepartment: {
        type: Object,
        required: true,
      },
    },
    methods: {
      toggleSelect(department, index) {
        this.$emit('toggleSelect', department, index);
      }
    }
  }
</script>

<style scoped>
  .item-box {
    padding: 8px;
    border: 1px solid #e8e8e8;
    border-radius: 4px;
  }
  .item-box:hover {
    cursor: pointer;
    background: #f5f5f5;
  }
  .is-active {
    color: #f5f5f5;
    background: #40a9ff;
  }
  .is-active:hover {
    background: #1890ff;
  }
</style>