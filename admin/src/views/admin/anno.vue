<template>
  <div>
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>id</th>
        <th>标题</th>
        <th>发布教师</th>
        <th>所属课程</th>
        <th>发布时间</th>
        <th>状态</th>
        <th>操作</th>
      </tr>
      </thead>
    </table>
  </div>
</template>

<script>
import  Pagination from  "../../components/pagination";
export default {
  components: {Pagination},
  name: 'anno',
  data:function() {
    return {
      anno:{},
      annos: [],
    }
  },
  mounted: function () {
    let _this=this;
    _this.$refs.pagination.size=5;
    _this.list(1);
  },
  methods:{

  },
}
</script>

<style scoped>

</style>