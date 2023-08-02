<template>
    <div>
        <div class="main-content">
            <div class="main-content-inner">
                <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                    <ul class="breadcrumb">
                        <li>
                            <i class="ace-icon fa fa-home home-icon"></i>
                            <router-link to="/welcome">首页</router-link>
                        </li>
                        <li>
                            <router-link  to="/system/course">{{course.name}}</router-link>
                        </li>
                    </ul><!-- /.breadcrumb -->
                </div>
            </div>
        </div><!-- /.main-content -->
        <p>
            <router-link to="/system/course" class="btn btn-lg  btn-info">
                <i class="ace-icon fa fa-arrow-left"></i>
            </router-link>
                &nbsp;
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
                <th class="">
                    <label class="">
                        <input type="checkbox" class="ace" />
                        <span class="lbl"></span>
                    </label>
                </th>
                <th>id编号</th>
                <th class="hidden-480">大章编号</th>
                <th class="hidden-480">大章名称</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="chapter in chapters">
                <td class="">
                    <label class="pos-rel">
                        <input type="checkbox" class="ace" />
                        <span class="lbl"></span>
                    </label>
                </td>
                <td class="">
                    {{chapter.id}}
                </td>
                <td>
                    {{chapter.courseId}}
                </td>
                <td> {{chapter.name}}</td>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">

                        <button @click="update(chapter)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>

                        <button @click="del(chapter.id)" class="btn btn-xs btn-danger">
                            <i class="ace-icon fa fa-trash-o bigger-120"></i>
                        </button>

                        <button @click="toSection(chapter)" class="btn btn-xs btn-warning">
                            <i class="ace-icon fa  fa-search bigger-120"></i>
                        </button>
                    </div>

                    <div class="hidden-md hidden-lg">
                        <div class="inline pos-rel">
                            <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                            </button>

                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">

                                <li>
                                    <a href="#"  @click="update(chapter)" class="tooltip-success" data-rel="tooltip" title="Edit">
                                                                    <span class="green">
                                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                                    </span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#"  @click="toSection(chapter)"    class="tooltip-error" data-rel="tooltip" title="Delete">
                                                                    <span class="red">
                                                                          <i class="ace-icon fa  fa-search bigger-120"></i>
                                                                    </span>
                                    </a>
                                </li>
                                <li>
                                    <a href="#"  @click="del(chapter.id)" class="tooltip-error" data-rel="tooltip" title="Delete">
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
                        <h4 class="modal-title" id="myModalLabel">操作章节</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label for="inputEmail1" class="col-sm-2 control-label">名称</label>
                                <div class="col-sm-10">
                                    <input type="text" v-model="chapter.name"  class="form-control" id="inputEmail1" placeholder="请输入章节名称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">课程名称</label>
                                <div class="col-sm-10">
                                    <p class="form-control-static">{{chapter.name}}</p>
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
        name: 'chapter',
        data:function() {
            return {
                chapter:{},
                chapters: [],
                course: {},
            }
        },
        mounted: function () {
            let _this=this;
             _this.$refs.pagination.size=5;
             let course = SessionStorage.get(SESSION_KEY_COURSE) || {};//// 课程管理页面点击章管理时，保存课程信息
             console.log(course)
            // if (Tool.isEmpty(course)) {
            //     _this.$router.push("/welcome");
            // }
            _this.course = course;
            // _this.list(1);
            // //激活样式，跳转方法一
            // this.$parent.activeSidebar('business-chapter-sidebar');
        },
        methods:{
           

        }
    }
</script>
