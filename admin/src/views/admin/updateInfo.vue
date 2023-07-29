<template>
  <div>
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>id</th>
        <th>更新时间</th>
        <th>更新内容</th>
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
  name: 'updateInfo',
  data:function() {
    return {
      updateInfo:{},
      updateInfos: [],
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