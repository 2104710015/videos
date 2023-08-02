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
        <th>职位</th>
        <th>座右铭</th>
        <th>简介</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="teacher in teachers">
        <td>{{teacher.id}}</td>
        <td>{{teacher.name}}</td>
        <td>{{teacher.nickname}}</td>
        <td><img v-show="teacher.image" v-bind:src="teacher.image"/>{{teacher.image}}</td>
        <td>{{teacher.position}}</td>
        <td>{{teacher.motto}}</td>
        <td>{{teacher.intro}}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button @click="editUser(teacher)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-user bigger-120"></i>
            </button>
            <button @click="update(teacher)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>
            &nbsp;
            <button @click="del(teacher.id)" class="btn btn-xs btn-danger">
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
                  <a href="#"  @click="editUser(teacher)" class="tooltip-success" data-rel="tooltip" title="Edit">
                    <span class="blue">
                      <i class="ace-icon fa  fa-user  bigger-120"></i>
                    </span>
                  </a>
                </li>
                <li>
                  <a href="#"  @click="update(teacher)" class="tooltip-success" data-rel="tooltip" title="Edit">
                     <span class="green">
                       <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                     </span>
                  </a>
                </li>
                <li>
                  <a href="#"  @click="del(teacher.id)" class="tooltip-error" data-rel="tooltip" title="Delete">
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
                  <div v-show="teacher.image" class="row">
                    <div class="col-md-4">
                      <img v-bind:src="teacher.image"  class="img-responsive">
                    </div>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label for="inputEmail1" class="col-sm-2 control-label">讲师名称</label>
                <div class="col-sm-10">
                  <input type="text" v-model="teacher.name"  class="form-control" id="inputEmail1" placeholder="请输入讲师名称">
                </div>
              </div>
              <div class="form-group">
                <label for="teachersId" class="col-sm-2 control-label">昵称</label>
                <div class="col-sm-10">
                  <input type="text" v-model="teacher.nickname" class="form-control" id="teachersId" placeholder="请输入昵称">
                </div>
              </div>
              <div class="form-group">
                <label  class="col-sm-2 control-label">职位</label>
                <div class="col-sm-10">
                  <input type="text" v-model="teacher.position"  class="form-control"  placeholder="请输入职位">
                </div>
              </div>
              <div class="form-group">
                <label  class="col-sm-2 control-label">座右铭</label>
                <div class="col-sm-10">
                  <input type="text" v-model="teacher.motto"  class="form-control" placeholder="请输入座右铭">
                </div>
              </div>
              <div class="form-group">
                <label for="inputEmail1" class="col-sm-2 control-label">简介</label>
                <div class="col-sm-10">
                  <textarea type="text" v-model="teacher.intro"  class="form-control"  placeholder="请输入简介">
                  </textarea>
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


    <!-- 角色用户关联配置 -->
    <div id="user-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">角色用户关联配置</h4>
          </div>
          <div class="modal-body">
            <div class="row">
              <div class="col-md-6">
                <table id="user-table" class="table table-hover">
                  <thead>
                  <tr>
                    <th colspan="2">所有的用户信息</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr v-for="user in users">
                    <td>{{user.loginName}}</td>
                    <td class="text-right">
                      <a @click="addUser(user)" href="javascript:;" class="">
                        <i class="ace-icon fa fa-arrow-circle-right blue"></i>
                      </a>
                    </td>
                  </tr>
                  </tbody>
                </table>
              </div>
              <div class="col-md-6">
                <table id="role-user-table" class="table table-hover">
                  <thead>
                  <tr>
                    <th colspan="2">当前角色已有的用户信息</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr v-for="user in teacherUsers">
                    <td>{{user.loginName}}</td>
                    <td class="text-right">
                      <a @click="deleteUser(user)" href="javascript:;" class="">
                        <i class="ace-icon fa fa-trash blue"></i>
                      </a>
                    </td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
              <i class="ace-icon fa fa-times"></i>
              关闭
            </button>
            <button type="button" class="btn btn-white btn-info btn-round" v-on:click="saveUser()">
              <i class="ace-icon fa fa-plus blue"></i>
              保存
            </button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
  </div>
</template>

<script>
import  Pagination from  "../../components/pagination";
export default {
  components: {Pagination},
  name: 'teacher',
  data:function() {
    return {
      teacher:{},
      teachers: [],
      users: [],//用户
      teacherUsers: [],//教师和用户
    }
  },
  mounted: function () {
    let _this=this;
    _this.$refs.pagination.size=5;
    // _this.allUser();
    _this.list(1);
  },
  methods:{
    list(page) {
      let _this = this;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/customer/admin/teacher/list", {
        page: page,
        size: _this.$refs.pagination.size
      }).then((res) => {
        console.log('获取教师的数据信息', res);
        if (res.data.boo) {//有结果返回了对应的数据信息，才进行显示并取消加载的图片
          _this.teachers = res.data.content.data;
          let date=res.data.content.data;
          // for (let i = 0; i < _this.teachers.length; i++) {
          //   $("#img").attr("src",date[i].image);
          // }
          console.log('res.data.content.data.total', res.data.content.total);
          _this.$refs.pagination.render(page, res.data.content.total);
          Loading.hide();
        }
      })

    },
    add() {
      let _this = this;
      // _this.$refs.pagination.size = 5;
      _this.teacher = {};//弹窗是一个空对象
      $("#form-modal").modal(true);
    },
    save() {

      let _this = this;
      console.log(_this.role.id);
      //进行验证数据信息
      if (1 != 1
          ||!Validator.require(_this.teacher.name, "姓名")
          ||!Validator.length(_this.teacher.name, "姓名", 1, 50)
          ||!Validator.length(_this.teacher.nickname, "昵称", 1, 50)
          ||!Validator.length(_this.teacher.image, "头像", 1, 100)
          ||!Validator.length(_this.teacher.position, "职位", 1, 50)
          ||!Validator.length(_this.teacher.motto, "座右铭", 1, 50)
          ||!Validator.length(_this.teacher.intro, "简介", 1, 500)
      ){
        return;
      }
      console.log(111111111111111)
      Loading.show();//有转圈效果
      //进行出发提交添加操作
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/customer/admin/teacher/saveAndUpdate", _this.teacher)
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
      _this.teacher = $.extend({}, itme);
      console.log("转换之后的数据", _this.teacher);
      $("#form-modal").modal(true);
    },
    del(id) {
      let _this = this;
      //进行一个弹窗提示需要进行一个删除
      let message="注意【删除当前 编号="+id+" 数据】后不可恢复!!!";
      Confrim.Show(message,function (){
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/customer/admin/teacher/del/"+id).then(
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
    editUser(teacher){
      let _this=this;
      _this.teacher=$.extend({},teacher);
      //加载所有的用户的数据信息
      _this.usersList();
      //出发弹窗
      $("#user-modal").modal(true);

    },
    usersList(){
      let _this=this;
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/user/list",{
        page:1,
        size:100
      }).then(
          (res)=>{
            console.log("获取所有的用户的信息",res);
            if(res.data.boo){
              _this.users=res.data.content.data;
              //再次查询用户和角色已经关联的数据信息
              _this.teacherAndUserList();

            }else {
              $("#user-modal").modal("hide");
              prompt.error("网络异常,请稍后再试");
            }
          })
    },
    teacherAndUserList(){
      let _this=this;
      //获取当前角色和用户关联的用户数据信息
      //进行清空之前的数据信息
      _this.teacherUsers=[];
      _this.$ajax.get(process.env.VUE_APP_SERVER + "/customer/admin/teacher/selectById/"+_this.teacher.id)
          .then((res)=>{
            console.log("查询了用户和角色关联的用户数据信息",res);
            if(res.data.boo){
              let userId=res.data.content;
              // _this.teacherUsers.push(userId);
              //需要进行循环遍历
              for (let i = 0; i < _this.users.length; i++) {
                if (userId===_this.users[i].id){
                  _this.teacherUsers.push(_this.users[i]);
                }
              }
            }
          })
    },
    //给当前角色添加用户
    addUser(user){
      console.log("给某个教师添加账号",user);
      let _this=this;
      let users=_this.teacherUsers;
      //调用后台的数据信息
      for (let i = 0; i < users.length; i++) {
        if (user===users[i]){
          return;
        }
      }
      if (_this.teacherUsers.length==1){
        prompt.warning("一个讲师只能有一个账号")
        return;
      }
      _this.teacherUsers.push(user);
    },
    /**
     * 将某个角色关联的用户进行删除
     * @param user
     */
    deleteUser(user){
      console.log("需要进行删除的用户",user);
      let _this=this;
      //进行编写一个删除的事件
      Tool.removeObj(_this.teacherUsers,user);
    },
    saveUser(){
      let _this=this;
      let users=_this.teacherUsers;
      console.log("_this.teacherUsers",_this.teacherUsers)
      //只是需要保存用户的编号
      //进行获取用户的id编号
      let userIds={};
      for (let i = 0; i < users.length; i++) {
        userIds=users[i].id;
      }
      console.log("需要进行重新分配的用户的编号",userIds);
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/customer/admin/teacher/saveUser",{
        id:_this.teacher.id,
        name: _this.teacher.name,
        nickname: _this.teacher.nickname,
        intro:_this.teacher.intro,
        motto:_this.teacher.motto,
        image:_this.teacher.image,
        position:_this.teacher.position,
        userid:userIds
      }).then((res)=>{
        console.log("修改某个角色的用户权限",res);
        $("#user-modal").modal("hide");
        if (res.data.boo){
          prompt.success("数据操作成功")
        }else {
          prompt.error("网络异常,请稍后再试");
        }
      })
    }
  },
}
</script>

<style scoped>

</style>