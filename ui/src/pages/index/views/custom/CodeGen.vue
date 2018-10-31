<template>
  <div class="page">
    <breadcrumb/>
    <div class="page-content">
      <a-form class="mt-2">
        <a-row :gutter="16">
          <a-col :span="9">
            <a-form-item label="MySQL地址" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
              <a-input v-model="config.dbHost" placeholder="请填写MySQL数据库所在的IP或者域名"></a-input>
            </a-form-item>
            <a-form-item label="MySQL用户" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
              <a-input v-model="config.dbUsername" placeholder="请填写MySQL用户"></a-input>
            </a-form-item>
            <a-form-item label="MySQL密码" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
              <a-input type="password" v-model="config.dbPassword" placeholder="请填写MySQL密码"></a-input>
            </a-form-item>
            <a-form-item label="数据库名称" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
              <a-input v-model="config.database" placeholder="请填写数据库名称"></a-input>
            </a-form-item>
            <a-form-item label="中间关联表" :labelCol="$style.form.label">
              <a-switch v-model="config.relatedTable"></a-switch>
            </a-form-item>
            <a-form-item label="RESTful风格" :labelCol="$style.form.label">
              <a-switch v-model="config.restful"></a-switch>
            </a-form-item>
            <a-form-item :wrapperCol="$style.form.contentWrapper">
              <a-button type="primary" icon="rocket" @click="generateCode">开始生成代码！</a-button>
            </a-form-item>
          </a-col>
          <a-col :span="9">
            <a-form-item label="项目路径" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
              <a-input v-model="config.projectPath" placeholder="请填写项目路径（如：D:\projects\seed）"></a-input>
            </a-form-item>
            <a-form-item label="开发人员" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper">
              <a-input v-model="config.developer" placeholder="请填写开发人员"></a-input>
            </a-form-item>
            <a-form-item label="模块名称" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
              <a-input v-model="config.moduleName" placeholder="请填写模块名称"></a-input>
            </a-form-item>
            <a-form-item label="数据表名" :labelCol="$style.form.label" :wrapperCol="$style.form.wrapper" required>
              <a-input v-model="config.tableName" placeholder="请填写数据表名"></a-input>
            </a-form-item>
            <a-form-item label="逻辑删除字段" :labelCol="$style.form.label">
              <a-switch v-model="config.hasLogicDeleteField"></a-switch>
            </a-form-item>
            <a-form-item label="乐观锁字段" :labelCol="$style.form.label">
              <a-switch v-model="config.hasVersionField"></a-switch>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-alert type="info" showIcon>
              <template slot="message">
                <div>通过代码生成器可以根据已经存在的数据表结构一键生成如下内容：</div>
                <div>1. 后台相应entity、dto、vo、mapper、service、serviceImpl、controller类的完整代码；</div>
                <div>2. Mybatis的mapper.xml文件；</div>
                <div>3. 完整的Swagger注解，通过swagger-ui.html即可查看详细的接口文档。</div>
                <div><a-icon type="warning"/> 注意事项：</div>
                <div>1. 数据表的主键字段名必须为"id"，如果存在逻辑删除字段则字段名必须是"deleted"，如果存在乐观锁字段则字段名必须是"version"；</div>
                <div>2. 关联数据表必须包含主键字段且字段名为"id"，关键表至少有两个"外键"字段（不要在表结构中定义成外键，逻辑外键），用于绑定关联关系。</div>
              </template>
            </a-alert>
          </a-col>
        </a-row>
      </a-form>
    </div>
  </div>
</template>

<script>
  import Breadcrumb from "../../../../components/Breadcrumb";
  import Tags from "../../../../components/Tags";

  export default {
    name: "CodeGen",
    components: {Tags, Breadcrumb},
    data() {
      return {
        config: {
          dbHost: 'localhost',
          dbUsername: 'root',
          dbPassword: '',
          database: 'test',
          projectPath: '',
          developer: 'yupaits',
          moduleName: '',
          tableName: '',
          relatedTable: false,
          restful: true,
          hasLogicDeleteField: true,
          hasVersionField: false
        },
      }
    },
    methods: {
      generateCode() {
        this.$api.sys.generateCode(this.config).then(() => {
          this.$message.success('代码生成成功');
        });
      }
    }
  }
</script>

<style scoped>

</style>