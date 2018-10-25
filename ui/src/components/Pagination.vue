<template>
  <nav class="pagination is-right is-small ml-1 mr-1">
    <a class="pagination-previous">上一页</a>
    <a class="pagination-next">下一页</a>
    <ul class="pagination-list">
      <li><a class="pagination-link" >1</a></li>
      <li><span class="pagination-ellipsis">&hellip;</span></li>
      <li><a class="pagination-link">45</a></li>
      <li><a class="pagination-link is-current">46</a></li>
      <li><a class="pagination-link">47</a></li>
      <li><span class="pagination-ellipsis">&hellip;</span></li>
      <li><a class="pagination-link">86</a></li>
    </ul>
  </nav>
</template>

<script>
  export default {
    name: "Pagination"
  }
</script>

<style scoped>

</style>