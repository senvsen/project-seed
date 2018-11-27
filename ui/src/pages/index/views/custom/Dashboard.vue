<template>
  <div>
    <a-row :gutter="16">
      <a-col :span="6">
        <div class="data-card">
          <h3 class="has-text-mute">总销售额
            <a-tooltip placement="top">
              <template slot="title">
                <span>指标说明</span>
              </template>
              <a-icon type="info-circle-o" class="is-pulled-right"/>
            </a-tooltip>
          </h3>
          <div class="data-total has-text-important">￥ 126,560</div>
          <div class="data-content">
            <div class="has-text-mute">
              <span class="mr-2">周同比 12% <a-icon type="caret-up" class="has-text-danger"/></span>
              <span>日同比 11% <a-icon type="caret-down" class="has-text-success"/></span>
            </div>
          </div>
          <p class="data-footer">
            <span class="mr-2">日销售额</span> <span class="has-text-important">￥ 12,423</span> 
          </p>
        </div>
      </a-col>
      <a-col :span="6">
        <div class="data-card">
          <h3 class="has-text-mute">总访问量
            <a-tooltip placement="top">
              <template slot="title">
                <span>指标说明</span>
              </template>
              <a-icon type="info-circle-o" class="is-pulled-right"/>
            </a-tooltip>
          </h3>
          <div class="data-total has-text-important">8,846</div>
          <div class="data-content">
          </div>
          <p class="data-footer">
            <span class="mr-2">日访问量</span> <span class="has-text-important">1,234</span>
          </p>
        </div>
      </a-col>
      <a-col :span="6">
        <div class="data-card">
          <h3 class="has-text-mute">支付笔数
            <a-tooltip placement="top">
              <template slot="title">
                <span>指标说明</span>
              </template>
              <a-icon type="info-circle-o" class="is-pulled-right"/>
            </a-tooltip>
          </h3>
          <div class="data-total has-text-important">6,560</div>
          <div class="data-content">
          </div>
          <p class="data-footer">
            <span class="mr-2">转化率</span> <span class="has-text-important">60%</span>
          </p>
        </div>
      </a-col>
      <a-col :span="6">
        <div class="data-card">
          <h3 class="has-text-mute">运营活动效果
            <a-tooltip placement="top">
              <template slot="title">
                <span>指标说明</span>
              </template>
              <a-icon type="info-circle-o" class="is-pulled-right"/>
            </a-tooltip>
          </h3>
          <div class="data-total has-text-important">78%</div>
          <div class="data-content-container">
            <div class="data-content">
            </div>
          </div>
          <p class="data-footer">
            <span class="mr-2">周同比 <span class="has-text-important">12%</span> <a-icon type="caret-up" class="has-text-danger"/></span>
            <span>日同比 <span class="has-text-important">11%</span> <a-icon type="caret-down" class="has-text-success"/></span>
          </p>
        </div>
      </a-col>
    </a-row>
  </div>
</template>

<script>
  import Breadcrumb from "../../../../components/Breadcrumb";
  export default {
    name: "Dashboard",
    components: {Breadcrumb}
  }      
</script>

<style scoped>
  .data-card {
    padding: 16px;
    background: #fff;
    height: 180px;
  }
  .data-total {
    font-size: 30px;
    height: 38px;
    line-height: 38px;
  }
  .data-content {
    height: 46px;
    line-height: 46px;
  }
  .data-footer {
    border-top: 1px solid #e8e8e8;
    padding-top: 9px;
    margin-top: 8px;
  }
</style>