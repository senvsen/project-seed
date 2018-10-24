<template>
  <nav class="pagination is-small ml-1 mr-1" role="navigation" aria-label="pagination">
    <a class="pagination-previous">上一页</a>
    <a class="pagination-next">下一页</a>
    <ul class="pagination-list">
      <li><a class="pagination-link" aria-label="Goto page 1">1</a></li>
      <li><span class="pagination-ellipsis">&hellip;</span></li>
      <li><a class="pagination-link" aria-label="Goto page 45">45</a></li>
      <li><a class="pagination-link is-current" aria-label="Page 46" aria-current="page">46</a></li>
      <li><a class="pagination-link" aria-label="Goto page 47">47</a></li>
      <li><span class="pagination-ellipsis">&hellip;</span></li>
      <li><a class="pagination-link" aria-label="Goto page 86">86</a></li>
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