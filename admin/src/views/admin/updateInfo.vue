<template>
  <div>
    <p>
      <button  @click="list(1)" class="btn btn-lg  btn-primary">
        <i class="ace-icon fa fa-refresh"></i>
      </button>
      &nbsp;
      <!-- Button trigger modal -->
      <button @click="add()" class="btn btn-lg btn-success"   >
        <i class="ace-icon fa glyphicon-plus"></i>
      </button>
    </p>
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>id</th>
        <th>更新时间</th>
        <th>更新内容</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(itme,index) in updateInfos">
        <td>{{itme.id}}</td>
        <td>{{itme.updatetime}}</td>
        <td>{{itme.updatecontent}}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button @click="update(itme)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>

            <button @click="del(itme.id)" class="btn btn-xs btn-danger">
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
                  <a href="#"  @click="update(updateInfo)" class="tooltip-success" data-rel="tooltip" title="Edit">
                    <span class="green">
                      <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                    </span>
                  </a>
                </li>

                <li>
                  <a href="#"  @click="del(updateInfo.id)" class="tooltip-error" data-rel="tooltip" title="Delete">
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

    <!-- Modal -->
    <div class="modal fade" id="form-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="myModalLabel">操作角色</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label for="inputEmail1" class="col-sm-2 control-label">更新时间</label>
                <div class="col-sm-10">
                  <input type="datetime-local" v-model="updateInfo.updatetime"  class="form-control" id="inputEmail1" placeholder="请选择时间">
                </div>
              </div>
              <div class="form-group">
                <label for="inputEmail1" class="col-sm-2 control-label">更新内容</label>
                <div class="col-sm-10">
                  <input type="text" v-model="updateInfo.updatecontent"  class="form-control" id="inputEmail1" placeholder="请输入更新内容">
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" @click="save()" class="btn btn-primary">确定</button>
          </div>
        </div>
      </div>
    </div>



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
    hasResource(id) {
      return Tool.hasResource(id);
    },
    list(page) {
      let _this = this;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/customer/admin/updateInfo/list", {
        page: page,
        size: _this.$refs.pagination.size
      }).then((res) => {
        console.log('获取权限的数据信息', res);
        if (res.data.boo) {//有结果返回了对应的数据信息，才进行显示并取消加载的图片
          _this.updateInfos = res.data.content.data;
          console.log('res.data.content.data.total', res.data.content.total);
          _this.$refs.pagination.render(page, res.data.content.total);
          Loading.hide();
        }
      })

    },
    del(id) {
      let _this = this;
      //进行一个弹窗提示需要进行一个删除
      let message="注意【删除当前 编号="+id+" 数据】后不可恢复!!!";
      Confrim.Show(message,function (){
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/customer/admin/updateInfo/del/"+id).then(
            (res)=>{
              console.log("删除成功",res)
              //无论是删除失败还是成功，都要重新进行查询一次数据
              _this.list(1);
              if (res.data.boo){
                prompt.success("编号"+id+"删除成功");
              }else {
                prompt.error("编号"+id+"删除失败");
              }
            }
        )
      })
    },
    add(){
      let _this = this;
      _this.updateInfo = {};//弹窗是一个空对象
      $("#form-modal").modal(true);
    },
    update(update) {
      console.log("当前操作对象：", update)
      let _this = this;
      _this.updateInfo = $.extend({}, update);
      console.log("转换之后的数据", _this.updateInfo);
      $("#form-modal").modal(true);
    },
    save(){
      let _this = this;
      console.log(_this.updateInfo);
      //进行验证数据信息
      if (1 != 1
          || !Validator.require(_this.updateInfo.updatetime, "更新时间")
          || !Validator.length(_this.updateInfo.updatetime, "更新时间", 1, 30)
          || !Validator.require(_this.updateInfo.updatecontent, "更新内容")
          || !Validator.length(_this.updateInfo.updatecontent, "更新内容", 1, 2000)
      ) {
        return;
      }
      // console.log(111111111111111)
      Loading.show();//有转圈效果
      //进行出发提交添加操作
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/customer/admin/updateInfo/saveAndUpdate", _this.updateInfo)
          .then((res) => {
            console.log("添加数据信息：", res);
            $("#form-modal").modal("hide");//隐藏弹窗
            if (res.data.boo) {
              //添加成功
              //进再次调用查询所有的数据信息
              _this.list(1);
              //进行一个提示
              prompt.success("更新添加成功");
            } else {
              prompt.error(res.data.message);
            }
          })
    },




  }
}
</script>

<style scoped>

</style>