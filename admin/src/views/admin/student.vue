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
        <th>姓名</th>
        <th>昵称</th>
        <th>头像</th>
        <th>性别</th>
        <th>手机号</th>
        <th>座右铭</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="student in students">
        <td>{{student.id}}</td>
        <td>{{student.name}}</td>
        <td>{{student.nickname}}</td>
        <td>{{student.image}}</td>
        <td>{{student.sex}}</td>
        <td>{{student.phone}}</td>
        <td>{{student.motto}}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
<!--            <button @click="editUser(student)" class="btn btn-xs btn-info">-->
<!--              <i class="ace-icon fa fa-user bigger-120"></i>-->
<!--            </button>-->
            <button @click="update(student)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>
            &nbsp;
            <button @click="del(student.id)" class="btn btn-xs btn-danger">
              <i class="ace-icon fa fa-trash-o bigger-120"></i>
            </button>

          </div>

          <div class="hidden-md hidden-lg">
            <div class="inline pos-rel">
              <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
              </button>

              <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
<!--                <li>-->
<!--                  <a href="#"  @click="editUser(student)" class="tooltip-success" data-rel="tooltip" title="Edit">-->
<!--                    <span class="blue">-->
<!--                      <i class="ace-icon fa  fa-user  bigger-120"></i>-->
<!--                    </span>-->
<!--                  </a>-->
<!--                </li>-->
                <li>
                  <a href="#"  @click="update(student)" class="tooltip-success" data-rel="tooltip" title="Edit">
                     <span class="green">
                       <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                     </span>
                  </a>
                </li>
                <li>
                  <a href="#"  @click="del(student.id)" class="tooltip-error" data-rel="tooltip" title="Delete">
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

    <div class="modal fade" id="form-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="myModalLabel">操作讲师</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label  class="col-sm-2 control-label">头像</label>
                <div class="col-sm-10">
                  <!-- 文件上传 -->
                  <div v-show="student.image" class="row">
                    <div class="col-md-4">
                      <img v-bind:src="student.image"  class="img-responsive">
                    </div>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label for="inputEmail1" class="col-sm-2 control-label">学生名称</label>
                <div class="col-sm-10">
                  <input type="text" v-model="student.name"  class="form-control" id="inputEmail1" placeholder="请输入学生名称">
                </div>
              </div>
              <div class="form-group">
                <label for="teachersId" class="col-sm-2 control-label">昵称</label>
                <div class="col-sm-10">
                  <input type="text" v-model="student.nickname" class="form-control" id="teachersId" placeholder="请输入昵称">
                </div>
              </div>
              <div class="form-group">
                <label  class="col-sm-2 control-label">性别</label>
                <div class="col-sm-10">
<!--                  <input type="text" v-model="student.sex"  class="form-control"  placeholder="请输入">-->
                  <select v-model="student.sex" class="form-control">
                    <option v-for="o in SEX" v-bind:value="o.key">{{o.value}}</option>
                  </select>
                </div>

              </div>
              <div class="form-group">
                <label  class="col-sm-2 control-label">座右铭</label>
                <div class="col-sm-10">
                  <input type="text" v-model="student.motto"  class="form-control" placeholder="请输入座右铭">
                </div>
              </div>
              <div class="form-group">
                <label for="inputEmail1" class="col-sm-2 control-label">手机号</label>
                <div class="col-sm-10">
                  <input type="text" v-model="student.phone"  class="form-control"  placeholder="请输入手机号">
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
  name: 'student',
  data:function() {
    return {
      student:{},
      students: [],
      SEX:{ONE:{key:"男", value:"男"},TWO:{key:"女", value:"女"},}
    }
  },
  mounted: function () {
    let _this=this;
    _this.$refs.pagination.size=5;
    _this.list(1);
  },
  methods:{
    list(page) {
      let _this = this;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/customer/admin/student/list", {
        page: page,
        size: _this.$refs.pagination.size
      }).then((res) => {
        console.log('获取学生的数据信息', res);
        if (res.data.boo) {//有结果返回了对应的数据信息，才进行显示并取消加载的图片
          _this.students = res.data.content.data;
          console.log('res.data.content.data.total', res.data.content.total);
          _this.$refs.pagination.render(page, res.data.content.total);
          Loading.hide();
        }
      })

    },
    add() {
      let _this = this;
      // _this.$refs.pagination.size = 5;
      _this.student = {};//弹窗是一个空对象
      $("#form-modal").modal(true);
    },
    save() {

      let _this = this;
      console.log(_this.student.id);
      //进行验证数据信息
      if (1 != 1
          ||!Validator.require(_this.student.name, "姓名")
          ||!Validator.length(_this.student.name, "姓名", 1, 50)
          ||!Validator.length(_this.student.nickname, "昵称", 1, 50)
          ||!Validator.length(_this.student.image, "头像", 1, 100)
          ||!Validator.length(_this.student.sex,"性别", 1, 10)
          ||!Validator.require(_this.student.phone, "手机号", 1, 11)
          ||!Validator.length(_this.student.motto, "座右铭", 1, 50)
      ){
        return;
      }
      console.log(111111111111111)
      Loading.show();//有转圈效果
      //进行出发提交添加操作
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/customer/admin/student/saveAndUpdate", _this.student)
          .then((response) => {
            console.log("添加数据信息：", response);
            $("#form-modal").modal("hide");//隐藏弹窗
            if (response.data.boo) {
              //添加成功
              //进再次调用查询所有的数据信息
              _this.list(1);
              //进行一个提示
              prompt.success("角色添加/修改成功");
            } else {
              prompt.error(response.data.message);
            }
          })
    },
    update(itme) {
      console.log("当前操作对象：", itme)
      let _this = this;
      _this.student = $.extend({}, itme);
      console.log("转换之后的数据", _this.student);
      $("#form-modal").modal(true);
    },
    del(id) {
      let _this = this;
      //进行一个弹窗提示需要进行一个删除
      let message="注意【删除当前 编号="+id+" 数据】后不可恢复!!!";
      Confrim.Show(message,function (){
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/customer/admin/student/del/"+id).then(
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
  }
}
</script>

<style scoped>

</style>