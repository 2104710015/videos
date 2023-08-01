<template>
    <div>
        <p>
            <button  @click="list(1)" class="btn btn-lg  btn-primary">
                <i class="ace-icon fa fa-refresh"></i>
            </button>
             &nbsp;
            <!-- Button trigger modal -->
            <button @click="add()" v-show="hasResource('010101')"  class="btn btn-lg btn-success"   >
                <i class="ace-icon fa glyphicon-plus"></i>
             </button>
        </p>
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

            <tbody>
            <tr v-for="user in users">
                            <td>{{user.id}}</td>
                            <td>{{user.loginName}}</td>
                            <td>{{user.name}}</td>
                            <td>{{user.password}}</td>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">

                        <button @click="updatePassWord(user)" v-show="hasResource('010103')" class="btn btn-xs btn-yellow ">
                            <i class="ace-icon fa fa-key bigger-120"></i>
                        </button>
                        &nbsp;
                        <button @click="update(user)"   v-show="hasResource('010101')" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>
                        &nbsp;
                        <button @click="del(user.id)" v-show="hasResource('010102')" class="btn btn-xs btn-danger">
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
                                    <a href="#"  @click="updatePassWord(user)" class="tooltip-success" data-rel="tooltip" title="Edit">
                                                                    <span class="red">
                                                                        <i class="ace-icon fa fa-key bigger-120"></i>
                                                                    </span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#"  @click="update(user)" class="tooltip-success" data-rel="tooltip" title="Edit">
                                                                    <span class="green">
                                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                                    </span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#"  @click="del(user.id)" class="tooltip-error" data-rel="tooltip" title="Delete">
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
                        <h4 class="modal-title" id="myModalLabel">操作用户</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">登陆名</label>
                                <div class="col-sm-10">
                                    <input type="text" v-model="user.loginName" v-bind:disabled="user.id"   class="form-control" placeholder="请输入登陆名名称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">昵称</label>
                                <div class="col-sm-10">
                                    <input type="text" v-model="user.name"  class="form-control"  placeholder="请输入昵称名称">
                                </div>
                            </div>
                            <div class="form-group"   v-show="!user.id">
                                <label class="col-sm-2 control-label">密码</label>
                                <div class="col-sm-10">
                                    <input type="text" v-model="user.password" class="form-control"  placeholder="请输入密码名称">
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
        <!-- Modal修改密码 -->
        <div class="modal fade" id="form-modal-password" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel-password">修改密码</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="form-group" >
                                <label class="col-sm-2 control-label">密码</label>
                                <div class="col-sm-10">
                                    <input type="password"    v-model="user.password" class="form-control"  placeholder="请输入密码名称">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" @click="savePassword()" class="btn btn-primary">确定</button>
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
        name: 'user',
        data:function() {
            return {
                user:{},
                users: [],
            }
        },
        mounted: function () {
            let _this=this;
             _this.$refs.pagination.size=5;
            _this.list(1);
        },
        methods:{
            /**
             * 查找是否有权限
             * @param id
             */
            hasResource(id) {
                return Tool.hasResource(id);
            },
            add(){
                let _this=this;
                _this.user={};
                $("#form-modal").modal("show");
            },
            update(user){
                let _this=this;
                _this.user=$.extend({},user);
                $("#form-modal").modal("show");
            },
            updatePassWord(user){
                let _this=this;
                _this.user=$.extend({},user);
                _this.user.password=null;
                $("#form-modal-password").modal("show");
            },
            del(id){
                  let _this=this;
                Confrim.Show("注意【用户删除】后不可恢复，请慎重!",function () {
                        _this.$ajax.delete(process.env.VUE_APP_SERVER + '/system/admin/user/del/'+id).then((response)=>{
                            if (response.data.boo) {
                                _this.list(1)
                                prompt.success('用户删除成功!');
                            }else {
                                prompt.error(response.data.message);
                            }
                        })
                })
            },
            list(page){
                let _this=this;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/list',{
                    page:page,
                    size: _this.$refs.pagination.size,
                }).then((response)=>{
                    if (response.data.boo) {
                        _this.users = response.data.content.data;
                        _this.$refs.pagination.render(page, response.data.content.total);
                        Loading.hide();
                    }
                })
            },
            save(){
                let _this=this;
                /*      if(!Validator.require(_this.chapter.name,"名称")
                          ||!Validator.require(_this.chapter.courseId,"课程ID")
                          ||!Validator.length(_this.chapter.courseId,"课程ID",1,8 )){
                          return;
                      }*/

                // 保存校验
                if (1 != 1
                    || !Validator.require(_this.user.loginName, "登陆名")
                    || !Validator.length(_this.user.loginName, "登陆名", 1, 50)
                    || !Validator.length(_this.user.name, "昵称", 1, 50)
                    || !Validator.require(_this.user.password, "密码")
                ) {
                    return;
                }
                //密码前台加密:key在加密追加字符之后，在进行加密
                _this.user.password = hex_md5(_this.user.password + KEY);
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/saveAndUpdate',_this.user)
                    .then((response)=>{
                        Loading.hide();
                        $("#form-modal").modal("hide");
                        if (response.data.boo) {
                            _this.list(1);
                            prompt.success('用户操作成功!');
                        }else {
                            prompt.error(response.data.message);
                        }
                    })
            },
            savePassword(){
            let _this=this;
                // 保存校验
                if (1 != 1
                            || !Validator.require(_this.user.password, "密码")
                ) {
                    return;
                }
                //密码前台加密:key在加密追加字符之后，在进行加密
                _this.user.password = hex_md5(_this.user.password + KEY);
                Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/updatePassword',_this.user)
                .then((response)=>{
                    Loading.hide();
                    $("#form-modal-password").modal("hide");
                    if (response.data.boo) {
                      _this.list(1);
                        prompt.success('密码操作成功!');
                    }else {
                        prompt.error(response.data.message);
                    }
                })
            },

        }
    }
</script>
