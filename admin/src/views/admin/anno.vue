<template>
  <div>
    <p>
      <button  @click="list(1)" class="btn btn-lg  btn-primary">
        <i class="ace-icon fa fa-refresh"></i>
      </button>
      &nbsp;
    </p>
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>id</th>
        <th>标题</th>
<!--        <th>发布教师</th>-->
<!--        <th>所属课程</th>-->
        <th>内容</th>
        <th>发布时间</th>
        <th>状态</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="anno in annos">
        <td>{{anno.id}}</td>
        <td>{{anno.title}}</td>
        <td>{{anno.content}}</td>
        <td>{{anno.releasetime}}</td>
<!--        <td>{{anno.teachername}}</td>-->
<!--        <td>{{anno.coursename}}</td>-->
        <td>{{anno.status}}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button @click="update(anno)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>
            &nbsp;
            <button @click="del(anno.id)" class="btn btn-xs btn-danger">
              <i class="ace-icon fa fa-trash-o bigger-120"></i>
            </button>

          </div>

          <div class="hidden-md hidden-lg">
            <div class="inline pos-rel">
              <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
              </button>

              <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                <li>
                  <a href="#"  @click="update(anno)" class="tooltip-success" data-rel="tooltip" title="Edit">
                     <span class="green">
                       <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                     </span>
                  </a>
                </li>
                <li>
                  <a href="#"  @click="del(anno.id)" class="tooltip-error" data-rel="tooltip" title="Delete">
                    <span class="red">
                      <i class="ace-icon fa fa-trash-o bigger-120"></i>
                    </span>
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </td>
      </tr>
      </tbody>
    </table>
    <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>
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
    hasResource(id) {
      return Tool.hasResource(id);
    },
    list(page){
      let _this=this;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + '/customer/admin/anno/list',{
        page:page,
        size:_this.$refs.pagination.size,
      }).then((response)=>{
        if (response.data.boo) {
          console.log("查询到的公告信息",response)
          _this.annos = response.data.content.data;
          _this.$refs.pagination.render(page, response.data.content.total);
          Loading.hide();
        }
      })
    },
    del(id){
      let _this=this;
      Confrim.Show("注意【用户删除】后不可恢复，请慎重!",function () {
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/customer/admin/anno/del/'+id)
            .then((response)=>{
          if (response.data.boo) {
            _this.list(1)
            prompt.success('用户删除成功!');
          }else {
            prompt.error(response.data.message);
          }
        })
      })
    },
  },
}
</script>

<style scoped>

</style>