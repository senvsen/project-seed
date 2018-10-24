<template>
  <div class="card">
    <table class="table is-striped is-hoverable is-fullwidth">
      <thead>
      <tr class="is-size-7">
        <th class="is-narrow">
          <input type="checkbox">
        </th>
        <th>ID</th>
        <th>头像</th>
        <th>用户名</th>
        <th>姓名</th>
        <th>手机号</th>
        <th>Email</th>
        <th>性别</th>
        <th>生日</th>
        <th>创建时间</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr class="is-size-7">
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td class="is-size-7">
          <button class="button is-primary is-small">
            <i class="fas fa-edit mr-1"></i>编辑
          </button>
        </td>
      </tr>
      </tbody>
    </table>
    <pagination/>
  </div>
</template>

<script>
  import Pagination from "./Pagination";
  export default {
    name: "DataTable",
    components: {Pagination},
    props: {
      columns: {

      },
      data: {

      },
    }
  }
</script>

<style scoped>

</style>