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
        <div class="row">
        <div class="col-xs-6 col-sm-4 col-md-3" v-for="course in courses">
            <div class="thumbnail search-thumbnail">
                <img v-show="!course.image" class="media-object" src="/static/image/demo-course.jpg" />
                <img v-show="course.image" class="media-object" v-bind:src="course.image" />
                <div class="caption">
                    <div class="clearfix">
                        <span class="pull-right label label-primary info-label ">
                        {{COURSE_LEVEL | optionKV(course.level)}}
                      </span>&nbsp;
                                <span class="pull-right label label-primary info-label">
                        {{COURSE_CHARGE | optionKV(course.charge)}}
                      </span>&nbsp;
                                <span class="pull-right label label-primary info-label">
                        {{COURSE_STATUS | optionKV(course.status)}}
                      </span>&nbsp;
                    </div>
                    <h3 class="search-title">
                        <a href="#" class="blue">{{course.name}}</a>
                    </h3>
                      <!--显示课程的讲师  filter 标识过滤 -->
                    <div  class="profile-activity clearfix">
                        <div>
                            <img v-show="!teacher.image" class="pull-left" src="/ace/assets/images/avatars/avatar5.png">
                            <img v-show="teacher.image" class="pull-left" v-bind:src="teacher.image">
                            <a class="user" href="#"> {{teacher.name}} </a>
                            <br>
                            {{teacher.position}}
                        </div>
                    </div>


                    <p>
                        <span class="blue bolder bigger-150">{{course.price}}&nbsp;<i class="fa fa-rmb"></i></span>&nbsp;
                    </p>
                    <p>{{course.summary}}</p>
                    <p>
                        <span class="badge badge-info">{{course.id}}</span>&nbsp;
                        <span class="badge badge-info">排序：{{course.sort}}</span>&nbsp;
                        <span class="badge badge-info">时长：{{course.time  | formatSecond}}</span>&nbsp;
                    </p>
                    <p>
                        <button v-on:click="toChapter(course)" class="btn btn-white btn-xs btn-success btn-round">
                            大章
                        </button>&nbsp;&nbsp;&nbsp;
                        <button v-on:click="toCourseContent(course)" class="btn btn-white btn-xs btn-rotate btn-round">
                            内容
                        </button>&nbsp;&nbsp;
                        <button v-on:click="openSort(course)" class="btn btn-white btn-xs btn-warning  btn-round">
                            排序
                        </button>&nbsp;&nbsp;
                        <button v-on:click="update(course)" class="btn btn-white btn-xs btn-info btn-round">
                            编辑
                        </button>&nbsp;&nbsp;&nbsp;
                        <button v-on:click="del(course.id)" class="btn btn-white btn-xs btn-warning btn-round">
                            删除
                        </button>
                    </p>
                </div>
            </div>
        </div>
        </div>
    <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>

        <!-- Modal -->
        <div class="modal fade" id="form-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">操作课程</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">
                                        分类
                                    </label>
                                    <div class="col-sm-10">
                                        <ul id="tree" class="ztree"></ul>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label  class="col-sm-2 control-label">封面</label>
                                    <div class="col-sm-10">
                                    <!-- 文件上传 -->
                                        <div v-show="course.image" class="row">
                                            <div class="col-md-4">
                                                <img v-bind:src="course.image"  class="img-responsive">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="inputEmail1" class="col-sm-2 control-label">名称</label>
                                    <div class="col-sm-10">
                                        <input type="text" v-model="course.name"  class="form-control" id="inputEmail1" placeholder="请输入名称名称">
                                    </div>
                                </div>
                            <div class="form-group">
                                <label for="teachersId" class="col-sm-2 control-label">讲师</label>
                                <div class="col-sm-10">
                                  <input type="text" v-model="teacher.name" disabled  class="form-control" id="teachersId" placeholder="讲师名称">
                                </div>
                            </div>
                                <div class="form-group">
                                    <label for="inputEmail1" class="col-sm-2 control-label">概述</label>
                                    <div class="col-sm-10">
                                        <textarea type="text" v-model="course.summary"  class="form-control"  placeholder="请输入概述名称">
                                        </textarea>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label  class="col-sm-2 control-label">时长</label>
                                    <div class="col-sm-10">
                                        <input type="text" v-model="course.time"  class="form-control" placeholder="请输入时长名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label  class="col-sm-2 control-label">价格（元）</label>
                                    <div class="col-sm-10">
                                        <input type="text" v-model="course.price"  class="form-control"  placeholder="请输入价格（元）名称">
                                    </div>
                                </div>
                                   <div class="form-group">
                                    <label class="col-sm-2 control-label">级别</label>
                                    <div class="col-sm-10">
                                      <select v-model="course.level" class="form-control">
                                        <option v-for="o in COURSE_LEVEL" v-bind:value="o.key">{{o.value}}</option>
                                      </select>
                                    </div>
                                  </div>
                                   <div class="form-group">
                                    <label class="col-sm-2 control-label">收费</label>
                                    <div class="col-sm-10">
                                      <select v-model="course.charge" class="form-control">
                                        <option v-for="o in COURSE_CHARGE" v-bind:value="o.key">{{o.value}}</option>
                                      </select>
                                    </div>
                                  </div>
                                   <div class="form-group">
                                    <label class="col-sm-2 control-label">状态</label>
                                    <div class="col-sm-10">
                                      <select v-model="course.status" class="form-control">
                                        <option v-for="o in COURSE_STATUS" v-bind:value="o.key">{{o.value}}</option>
                                      </select>
                                    </div>
                                  </div>
                                <div class="form-group">
                                    <label  class="col-sm-2 control-label">报名数</label>
                                    <div class="col-sm-10">
                                        <input type="text" v-model="course.enroll"  class="form-control" placeholder="请输入报名数名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">顺序</label>
                                    <div class="col-sm-10">
                                        <input v-model="course.sort" class="form-control" disabled>
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
   
<!--课程排序弹窗-->
        <div id="course-sort-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">排序</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="control-label col-lg-3">
                                    当前排序
                                </label>
                                <div class="col-lg-9">
                                    <input class="form-control" v-model="sort.oldSort" name="oldSort" disabled>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-3">
                                    新排序
                                </label>
                                <div class="col-lg-9">
                                    <input class="form-control" v-model="sort.newSort" name="newSort">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
                            <i class="ace-icon fa fa-times"></i>
                            取消
                        </button>
                        <button type="button" class="btn btn-white btn-info btn-round" v-on:click="updateSort()">
                            <i class="ace-icon fa fa-plus blue"></i>
                            更新排序
                        </button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </div>
</template>
<script>
    import  Pagination from  "../../components/pagination";
    import category from "@/views/admin/category";
    export default {
        components: {Pagination,File},
        name: 'course',
        data:function() {
            return {
                loginUser:{},
                course:{},//课程
                courses: [],//课程对象
                COURSE_LEVEL: COURSE_LEVEL,//{ONE:{key:"1", value:"初级"},TWO:{key:"2", value:"中级"},THREE:{key:"3", value:"高级"}};
                COURSE_CHARGE: COURSE_CHARGE,//{CHARGE:{key:"C", value:"收费"},FREE:{key:"F", value:"免费"}};
                COURSE_STATUS: COURSE_STATUS,//{PUBLISH:{key:"P", value:"发布"},DRAFT:{key:"D", value:"草稿"}};
                categorys:[],//分类的数组
                tree: {},//树形菜单对象
                saveContentLabel: "",
                sort: {
                    id: "",
                    oldSort: 0,
                    newSort: 0
                },
               teacher:{},//老师的数组
               FILE_USE:FILE_USE,//{COURSE:{key:"C", value:"课程"},TEACHER:{key:"T", value:"讲师"}}
            }
        },
      mounted: function () {
        let _this = this;
        _this.$refs.pagination.size = 5
        _this.allCategory();
        //调用当前的老师

        _this.allTeacher();
        // _this.list(1)//查询所有的课程信息
        },
        methods:{
          list(page){//查询所有课程信息
            let _this = this;
            Loading.show();
            //查询
            //传递一个参数

            _this.$ajax.post(process.env.VUE_APP_SERVER + "/customer/admin/course/list", {
              page: page,
              size: _this.$refs.pagination.size,
              data:[_this.teacher.id]
            }).then((res) => {
              console.log("获取到的课程信息",res);
              //进行赋值
              if (res.data.boo){
                _this.courses=res.data.content.data;
                Loading.hide();
                _this.$refs.pagination.render(page, res.data.content.total);

              }
            })
          },
          add(){
            let _this = this;
            _this.course = {
              sort:_this.$refs.pagination.total+1
            };
            //每次加载树形分类的时候，需要清空之前的选择的分类数据
            _this.zTree.checkAllNodes(false);
            //加载对应的模态窗
            $("#form-modal").modal("show");
          },
          allCategory() {//查询所有分类
            let _this = this;
            //加载转圈按钮
            Loading.show();
            //查询所有分类
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/customer/admin/category/getAllList').then(
                (res)=>{
                  Loading.hide();
                  console.log("查询所有的分类",res);
                  //赋值
                  if (res.data.boo){
                    _this.categorys=res.data.content;
                    //调用对应的树形结构
                    _this.initTree();
                  }
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
                  rootPId: "00000000",//rootPId
                  enable: true
                }
              }
            }
            let zNodes=_this.categorys;
            _this.zTree= $.fn.zTree.init($("#tree"),setting,zNodes);
          },
          allTeacher(){//查询当前登录的账号对应老师的id
            //获取的的当前的账号的id编号
            let _this = this;
            _this.loginUser=Tool.getLoginUser();
            console.log("获取当前账号的id",_this.loginUser.id);
            //进行查询后台的数据信息
            _this.$ajax.get(process.env.VUE_APP_SERVER + "/customer/admin/teacher/getByUsersId/"+_this.loginUser.id)
                .then((res) => {
              console.log("获取当前账号对应的教师信息",res);
              if (res.data.boo){
                _this.teacher=res.data.content;
                _this.list(1)//查询所有的课程信息
              }else {
                //没有开通对应的权限的时候进行返回登录界面
                _this.$router.push("/login");
              }
            })

          },
          save(){//添加对应的课程信息
            let _this = this;
            //进行前端验证
            if (1!=1
            ||!Validator.require(_this.course.name,"课程名称")
            ||!Validator.length(_this.course.name,"课程名称",1,50)
                ||!Validator.require(_this.course.summary,"课程描述",1,3000)
            ){
              return;
            }
            //获取对应的课程选中的分类
            let categoryNodes = _this.zTree.getCheckedNodes();
            if (Tool.isEmpty(categoryNodes)){
              prompt.warning("请选择对应的分类信息");
              return;
            }
            //将获得到的分类的信息进行赋值
            _this.course.categorys=categoryNodes;
            //进行给添加的数据进行赋值老师的id
            _this.course.teacherId=_this.teacher.id;
            //调用后台的添加
            Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER + "/customer/admin/course/saveAndUpdate",_this.course)
                .then((res) => {
                  Loading.hide();
                  console.log("添加新课程的信息",res);
                  if (res.data.boo){
                    $("#form-modal").modal("hide");
                    prompt.success("数据操作成功");
                    //无论添加成功还失败都要进行查询一次
                    _this.list(1);
                  }else {
                    prompt.error(res.data.message);
                  }

                })
          },
          update(data){
            //调用对应的弹窗
            let _this = this;
            //获取到课程信息
            _this.course=$.extend({},data);
            //调用对应的方法
            //查询当前课程对应的分类信息
            _this.courseListCategory(_this.course.id);

            //触发对应的弹窗
            $("#form-modal").modal("show");

          },
          courseListCategory(courseId){
            if (1!=1
                ||!Validator.require(courseId,"课程编号")
            ){
              return;
            }
            let _this = this;
            _this.$ajax.get(process.env.VUE_APP_SERVER + "/customer/admin/course/list-category/"+courseId)
                .then((res) => {
                  console.log("获取到分类数据",res)
                  if (res.data.boo){
                    let courseCategory=res.data.content;
                    //勾选上查询到的分类信息
                    _this.zTree.checkAllNodes(false);//取消全部选中
                    for (let i = 0; i < courseCategory.length; i++) {
                      let node=_this.zTree.getNodeByParam("id",courseCategory[i].categoryId);
                      _this.zTree.checkNode(node,true);
                    }
                  }
                })
          },
          openSort(course){//排序
            let _this = this;
            _this.sort={
              id:course.id,
              oldSort: course.sort,
              newSort: course.sort
            };
            //触发弹窗
            $("#course-sort-modal").modal("show");
          },
          updateSort(){//触发修改的排序
            let _this = this;
            if (_this.sort.newSort===_this.sort.oldSort){
              prompt.warning("没有对排序进行变化");
             return;
            }
            //调用后台的接口
            _this.$ajax.post(process.env.VUE_APP_SERVER + "/customer/admin/course/sort",_this.sort)
                .then((res) => {
                  console.log("修改排序的结果",res);
                  _this.list(1);
                  $("#course-sort-modal").modal("hide");
                  if (res.data.boo){
                    prompt.success("数据操作成功");
                  }else {
                    prompt.warning("数据操作失败");
                  }
                })
          },
          toChapter(course){
            let _this = this;
            //进行存储当前的课程信息
            SessionStorage.set(SESSION_KEY_COURSE,course);
            _this.$router.push("/system/chapter");//进行跳转到大章节中

          }
        }
    }
</script>

<style scoped>
    .caption h3 {
        font-size: 20px;
    }
    @media (max-width: 1199px) {
        .caption h3 {
            font-size: 16px;
        }
    }
</style>