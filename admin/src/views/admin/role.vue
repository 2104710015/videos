<template>
    <div>
        <p>
            <button  @click="listRloe(1)" class="btn btn-lg  btn-primary">
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
                    <th>角色</th>
                    <th>描述</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="(itme,index) in roles">
                            <td>{{itme.id}}</td>
                            <td>{{itme.name}}</td>
                            <td>{{itme.desc}}</td>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button @click="editUser(itme)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-user bigger-120"></i>
                        </button>

                        <button @click="editResource(itme)" class="btn btn-xs btn-warning">
                            <i class="ace-icon fa fa-list bigger-120"></i>
                        </button>

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
                                    <a href="#"  @click="editUser(role)" class="tooltip-success" data-rel="tooltip" title="Edit">
                                                                    <span class="blue">
                                                                        <i class="ace-icon fa  fa-user  bigger-120"></i>
                                                                    </span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#"  @click="editResource(role)" class="tooltip-success" data-rel="tooltip" title="Edit">
                                                                    <span class="blue">
                                                                        <i class="ace-icon fa fa-leaf bigger-120"></i>
                                                                    </span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#"  @click="update(role)" class="tooltip-success" data-rel="tooltip" title="Edit">
                                                                    <span class="green">
                                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                                    </span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#"  @click="del(role.id)" class="tooltip-error" data-rel="tooltip" title="Delete">
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
        <pagination ref="pagination" v-bind:list="listRloe" v-bind:itemCount="8"></pagination>

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
                                    <label for="inputEmail1" class="col-sm-2 control-label">角色</label>
                                    <div class="col-sm-10">
                                        <input type="text" v-model="role.name"  class="form-control" id="inputEmail1" placeholder="请输入角色名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="inputEmail1" class="col-sm-2 control-label">描述</label>
                                    <div class="col-sm-10">
                                        <input type="text" v-model="role.desc"  class="form-control" id="inputEmail1" placeholder="请输入描述名称">
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
        <!-- /.modal -->

        <!-- 角色资源关联配置 -->
        <div id="resource-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">角色资源关联配置</h4>
                    </div>
                    <div class="modal-body">
                        <ul id="tree" class="ztree"></ul>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
                            <i class="ace-icon fa fa-times"></i>
                            关闭
                        </button>
                        <button type="button" class="btn btn-white btn-info btn-round" v-on:click="saveResource()">
                            <i class="ace-icon fa fa-plus blue"></i>
                            保存
                        </button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->

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
                                    <tr v-for="user in roleUsers">
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
      name: 'role',
      data: function () {
        return {
          role: {}, //角色对象
          roles: [],//获取角色的数据信息
          resources: [],//资源菜单
          zTree: {},//树型结构
          users: [],//用户
          roleUsers: [],//角色和用户
        }
      },
      mounted: function () {
        let _this = this;
        _this.$refs.pagination.size = 5
        _this.listRloe(1)
      },
      methods: {
        //查询所有的方法
        listRloe(page) {
          let _this = this;
          Loading.show();
          _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/role/list", {
            page: page,
            size: _this.$refs.pagination.size
          }).then((res) => {
            console.log('获取权限的数据信息', res);
            if (res.data.boo) {//有结果返回了对应的数据信息，才进行显示并取消加载的图片
              _this.roles = res.data.content.data;
              console.log('res.data.content.data.total', res.data.content.total);
              _this.$refs.pagination.render(page, res.data.content.total);
              Loading.hide();
            }
          })

        },
        add() {
          let _this = this;
          // _this.$refs.pagination.size = 5;
          _this.role = {};//弹窗是一个空对象
          $("#form-modal").modal(true);
        },
        save() {

          let _this = this;
          console.log(_this.role.id);
          //进行验证数据信息
          if (1 != 1
              || !Validator.require(_this.role.name, "角色名称")
              || !Validator.length(_this.role.name, "角色名称", 1, 15)
              || !Validator.require(_this.role.desc, "描述信息")
              || !Validator.length(_this.role.desc, "描述信息", 1, 2000)
          ) {
            return;
          }
          console.log(111111111111111)
          Loading.show();//有转圈效果
          //进行出发提交添加操作
          _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/role/saveAndUpdate", _this.role)
              .then((response) => {
                console.log("添加数据信息：", response);
                $("#form-modal").modal("hide");//隐藏弹窗
                if (response.data.boo) {
                  //添加成功
                  //进再次调用查询所有的数据信息
                  _this.listRloe(1);
                  //进行一个提示
                  prompt.success("角色添加成功");
                } else {
                  prompt.error(response.data.message);
                }
              })
        },
        update(itme) {
          console.log("当前操作对象：", itme)
          let _this = this;
          _this.role = $.extend({}, itme);
          console.log("转换之后的数据", _this.role);
          $("#form-modal").modal(true);
        },
        del(id) {
          let _this = this;
          //进行一个弹窗提示需要进行一个删除
          let message="注意【删除当前 编号="+id+" 数据】后不可恢复!!!";
          Confrim.Show(message,function (){
            _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/role/del/"+id).then(
                (res)=>{
                  console.log("删除成功",res)
                  //无论是删除失败还是成功，都要重新进行查询一次数据
                  _this.listRloe(1);
                  if (res.data.boo){
                    prompt.success("编号"+id+"删除成功");
                  }else {
                    prompt.error("编号"+id+"删除失败");
                  }
                }
            )
          })
        },
        editResource(itme){
          let _this = this;
          _this.role = $.extend({}, itme);
          _this.loadResource();//获取菜单的资源
          //出发弹窗
          $("#resource-modal").modal(true);
          //加载对应的菜单资源,使用一个函数进行加载
        },
        loadResource(){
          //加载对应的菜单资源
          let _this = this;
          _this.$ajax.get(process.env.VUE_APP_SERVER + "/system/admin/resource/resourceListTree").then(
              (res)=>{
            console.log("获取到的资源信息",res);
            _this.resources=res.data.content;
            //进行初始化tree树形结构
                console.log("获取所有的菜单资源",_this.resources)
            _this.initTree();
            //进行初始化资源,某个角色对应的菜单资源
            _this.listRloeResources();


          })
        },
        initTree(){//初始化树形结构
          let _this = this;
          let setting={
            check:{
                enable:true
            },
            data:{
              simpleData:{
                idKey:"id",//key
                pIdKey: "parent",
                rootPId: "",//rootPId
                enable: true
              }
            }
          }
          _this.zTree= $.fn.zTree.init($("#tree"),setting,_this.resources);
          _this.zTree.expandAll(true);
        },
        listRloeResources(){
          let _this = this;
          //插叙某个角色对应的权限的资源数据
          _this.$ajax.get(process.env.VUE_APP_SERVER + "/system/admin/role/listResourceByRoleId/"+_this.role.id).then(
              (res)=>{
                console.log("获取到某个角色的资源数据信息",res);
                let resource=res.data.content;
                //进行将对应的资源信息进行勾选
                _this.zTree.checkAllNodes(false);//将所有的已勾选菜单进行取消勾选
                for (let i=0;i<resource.length;i++){
                  console.log("resource[i]",resource[i])
                  let node=_this.zTree.getNodeByParam("id",resource[i]);//进行和已有的权限进行对比
                  console.log("node",node);
                  _this.zTree.checkNode(node,true);//将已有的权限进行勾选
                }
              })
        },
        //进行提交保存修改权限数据信息
        saveResource(){
          let _this=this;
          //获取对应的节点菜单
          let resource=_this.zTree.getCheckedNodes();
          console.log("获取已勾选的节点",resource);
          //进行验证
          if (1!=1
          ||!Validator.require(resource,"勾选对应的资源"))
          {
            return;
          }
          //进行组装对应的资源信息
          let resourcesIds=[];
          //进行遍历获取对应的节点id
          for (let i = 0; i < resource.length; i++) {
            resourcesIds.push(resource[i].id);

          }
          console.log("勾选的节点id",resourcesIds);
          //进行调用后台
          _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/role/saveResource",{
            id:_this.role.id,
            resourcesIds:resourcesIds
          }).then(
              (res)=>{
                console.log("修改对应的菜单的数据信息",res);
                $("#resource-modal").modal("hide");//隐藏弹窗
                if (res.data.boo){
                  prompt.success("数据操作成功");
                }else {
                    prompt.error(res.data.message);
                }
              })
        },
        //用户和角色的模态框
        editUser(role){
          let _this=this;
          _this.role=$.extend({},role);
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
                  _this.roleAndUserList();

                }else {
                  $("#user-modal").modal("hide");
                  prompt.error("网络异常,请稍后再试");
                }
              })
        },
        roleAndUserList(){
          let _this=this;
          //获取当前角色和用户关联的用户数据信息
          //进行清空之前的数据信息
          _this.roleUsers=[];
          _this.$ajax.get(process.env.VUE_APP_SERVER + "/system/admin/role/roleAndUserList/"+_this.role.id)
              .then((res)=>{
                console.log("查询了用户和角色关联的用户数据信息",res);
                if(res.data.boo){
                  let userIds=res.data.content;
                  //需要进行循环遍历
                  for (let i = 0; i < userIds.length; i++) {
                    for (let j = 0; j < _this.users.length; j++) {
                      if (userIds[i]===_this.users[j].id){
                        _this.roleUsers.push(_this.users[j]);
                      }
                    }
                  }
                }
              })
        },
        //给当前角色添加用户
        addUser(user){
          console.log("给某个角色添加用户",user);
          let _this=this;
          let users=_this.roleUsers;
          //调用后台的数据信息
          for (let i = 0; i < users.length; i++) {
            if (user===users[i]){
              return;
            }
          }
          _this.roleUsers.push(user);
        },
        /**
         * 将某个角色关联的用户进行删除
         * @param user
         */
        deleteUser(user){
          console.log("需要进行删除的用户",user);
          let _this=this;
          //进行编写一个删除的事件
          Tool.removeObj(_this.roleUsers,user);
        },
        saveUser(){
          let _this=this;
          let users=_this.roleUsers;

          //只是需要保存用户的编号
          //进行遍历获取用户的id编号
          let userIds=[];
          for (let i = 0; i < users.length; i++) {
            userIds.push(users[i].id);
          }
          console.log("需要进行重新分配的用户的编号",userIds);
          _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/role/saveUser",{
            roleId:_this.role.id,
            userIds:userIds
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
      }
    }
</script>
