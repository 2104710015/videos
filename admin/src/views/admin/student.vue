<template>
  <div>
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>id</th>
        <th>登陆名</th>
        <th>昵称</th>
        <th>密码</th>
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
  name: 'student',
  data:function() {
    return {
      student:{},
      students: [],
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