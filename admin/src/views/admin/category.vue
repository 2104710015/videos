<template>
    <div>
        <div class="row">
            <div class="col-md-6">
                <p>
                    <button v-on:click="add1()" class="btn btn-white btn-default btn-round">
                        <i class="ace-icon fa fa-edit"></i>
                        新增一级
                    </button>
                    &nbsp;
                    <button v-on:click="all()" class="btn btn-white btn-default btn-round">
                        <i class="ace-icon fa fa-refresh"></i>
                        刷新
                    </button>
                </p>

                <table id="level1-table" class="table  table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>名称</th>
                        <th>顺序</th>
                        <th>操作</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr v-for="category in level1" v-on:click="onClickLevel1(category)" v-bind:class="{'active' : category.id === active.id}">
                        <td>{{category.id}}</td>
                        <td>{{category.name}}</td>
                        <td>{{category.sort}}</td>
                        <td>
                            <div class="hidden-sm hidden-xs btn-group">
                                <button v-on:click="edit(category)" class="btn btn-xs btn-info">
                                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                                </button>
                                <button v-on:click="del(category.id)" class="btn btn-xs btn-danger">
                                    <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="col-md-6">
                <p>
                    <button v-on:click="add2()" class="btn btn-white btn-default btn-round">
                        <i class="ace-icon fa fa-edit"></i>
                        新增二级
                    </button>
                </p>

                <table id="level2-table" class="table  table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>名称</th>
                        <th>顺序</th>
                        <th>操作</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr v-for="category in level2">
                        <td>{{category.id}}</td>
                        <td>{{category.name}}</td>
                        <td>{{category.sort}}</td>
                        <td>
                            <div class="hidden-sm hidden-xs btn-group">
                                <button v-on:click="edit(category)" class="btn btn-xs btn-info">
                                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                                </button>
                                <button v-on:click="del(category.id)" class="btn btn-xs btn-danger">
                                    <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>


        <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">表单</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">父分类</label>
                                <div class="col-sm-10" v-show="active.name!=null && active.name!=''">
                                    <p class="form-control-static">{{active.name || "无"}}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">名称</label>
                                <div class="col-sm-10">
                                    <input v-model="category.name" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">顺序</label>
                                <div class="col-sm-10">
                                    <input v-model="category.sort" class="form-control">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </div>
</template>

<script>
    export default {
        name: "business-category",
        data: function() {
            return {
                category: {},
                categorys: [],
                level1: [],
                level2: [],
                active: {},
            }
        },
        mounted: function() {
          let _this=this;
          this.all()
            // sidebar激活样式方法一
            // this.$parent.activeSidebar("business-category-sidebar");

        },
        methods: {
          //查询索引的分类的数据信息
          all(){
            let _this=this;
            Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/customer/admin/category/getAllList').then(
                (res)=>{
              console.log("获取的分类数据信息",res);
              let categorysList=res.data.content;
              //给创建的对象进行赋值
                  _this.categorys=categorysList;
                  //将对应的数据转换成树形结构
                  _this.level1=[];
                  Loading.hide();
                  //使用循环的方式进行遍历
                  for (let i = 0; i < _this.categorys.length; i++) {
                     let c=_this.categorys[i];
                     if (c.parent == "00000000"){
                       _this.level1.push(c);//存储父级菜单的
                       for (let j = 0; j < _this.categorys.length; j++) {
                         let chilred=_this.categorys[j];
                         if (chilred.parent==c.id){
                           if (Tool.isEmpty(c.children)){
                             c.children=[];
                           }
                           c.children.push(chilred);
                         }
                       }
                     }
                  }
                  _this.level2=[];
                  //设置一个时间，等待多少秒之后进行页面的渲染
                  setTimeout(()=>{
                      $("tr.active").trigger("click")
                  },100);
            })
          },
          onClickLevel1(obj) {
            //获取二级分类的数据
            let _this=this;
            _this.active=obj;
            _this.level2=obj.children;
          },
          add1(){//新增一级菜单
            let _this=this;
            _this.active=[];
            _this.level2=[];
            _this.category={
              parent:'00000000',

            };
            //进行调用弹窗
            $("#form-modal").modal("show");
          },
          add2(){//新增二级菜单
            let _this=this;
            if (Tool.isEmpty(_this.active)){
              prompt.error("请先选择一级分类");
              //如满足
              //那么下面的代码不执行
            }
            _this.category={
              parent:_this.active.id,//设置一级分类的id
            }
            //调用弹窗
            $(".modal").modal("show");
          },
          edit(data){//修改
            let _this=this;
            _this.category=$.extend({},data);
            $("#form-modal").modal("show");

          },
          save(){//添加对应分类数据信息
            let _this=this;
            //验证数据是否为空
            if (1!=1
            ||!Validator.require(_this.category.parent,"父级id")
                ||!Validator.require(_this.category.name,"父级名称")
                ||!Validator.length(_this.category.name,"父级名称",1,50)
            ){
              return;
            }
            //添加的时候进行转圈加载
            Loading.show();
            //后台接口
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/customer/admin/category/saveAndUpdate',
                _this.category
            ).then(
                (res)=>{
                  Loading.hide();
                  console.log(res);
                  if (res.data.boo){
                    $("#form-modal").modal("hide");
                    //再次进行查询所有的数据信息
                    _this.all();
                    //进行提示
                    prompt.success("数据操作成功");
                  }else {
                    prompt.error("网络异常，请稍后再试");
                  }
                })
          },
          del(id){//进行删除分类
            let _this=this;
            Confrim.Show("删除编号("+id+")数据不可恢复,请确认是否删除！",
            function (){
              Loading.show();
              //调用后台接口数据
              _this.$ajax.post(process.env.VUE_APP_SERVER + '/customer/admin/category/del/'+id)
                  .then((res)=>{
                    Loading.hide();
                    console.log("删除返回的数据",res);
                    if(res.data.boo){
                      _this.all();
                      //进行提示
                      prompt.success("数据操作成功");
                    }else {
                      prompt.error(res.data.message);
                    }
              })
            })
          }
        }
    }
</script>

<style scoped>
    .active td {
        background-color: #d6e9c6 !important;
    }
</style>