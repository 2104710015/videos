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
                        <router-link  to="/business/course">{{course.name}}</router-link>
                        </li>
                        <li>
                            <router-link  to="/business/chapter">{{chapter.name}}</router-link>
                        </li>
                    </ul><!-- /.breadcrumb -->
                </div>
            </div>
        </div><!-- /.main-content -->
        <p>
            <router-link to="/business/chapter" class="btn btn-lg  btn-info">
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
                    <th>id</th>
                    <th>标题</th>
                    <th>VOD</th>
                    <th>时长</th>
                    <th>收费</th>
                    <th>顺序</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="section in sections">
                    <td>{{section.id}}</td>
                    <td>{{section.title}}</td>
                    <td>{{section.vod}}</td>
                    <td>{{section.time | formatSecond}}</td>
                    <td>{{SECTION_CHARGE | optionKV(section.charge)}}</td>
                    <td>{{section.sort}}</td>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button v-on:click="play(section)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-video-camera bigger-120"></i>
                        </button>

                        <button @click="update(section)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>

                        <button @click="del(section.id)" class="btn btn-xs btn-danger">
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
                                    <a href="#"  @click="play(section)" class="tooltip-success" data-rel="tooltip" title="播放">
                                                                    <span class="green">
                                                                            <i class="ace-icon fa fa-video-camera bigger-120"></i>
                                                                    </span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#"  @click="update(section)" class="tooltip-success" data-rel="tooltip" title="Edit">
                                                                    <span class="green">
                                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                                    </span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#"  @click="del(section.id)" class="tooltip-error" data-rel="tooltip" title="Delete">
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
                        <h4 class="modal-title" id="myModalLabel">操作小节</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                                <div class="form-group">
                                    <label for="inputEmail1" class="col-sm-2 control-label">标题</label>
                                    <div class="col-sm-10">
                                        <input type="text" v-model="section.title"  class="form-control" id="inputEmail1" placeholder="请输入章节名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="inputEmail1" class="col-sm-2 control-label">课程</label>
                                    <div class="col-sm-10">
                                        <p class="form-control-static">{{course.name}}</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="inputEmail1" class="col-sm-2 control-label">大章</label>
                                    <div class="col-sm-10">
                                        <p class="form-control-static">{{chapter.name}}</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="inputEmail1" class="col-sm-2 control-label">视频</label>
                                    <div class="col-sm-10">
                                        <vod v-bind:file-upload-id="'video-upload'"
                                              v-bind:suffixs="['mp4']"
                                              v-bind:use="FILE_USE.COURSE.key"
                                              v-bind:after-upload="afterUpload"></vod>
                                        <div v-show="section.video" class="row">
                                            <div class="col-md-9">
                                                <!--阿里的视频播放器-->
                                                <oos-player ref="player" v-bind:player-id="'form-player-div'"></oos-player>
                                                <!--H5视频播放器-->
                                               <!-- <video v-bind:src="section.video" id="video" controls="controls"></video>-->
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label  class="col-sm-2 control-label">时长</label>
                                    <div class="col-sm-10">
                                        <input type="text"   v-model="section.time"  class="form-control"  placeholder="请输入章节名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">视频</label>
                                    <div class="col-sm-10">
                                        <input v-model="section.video" class="form-control" disabled>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">VOD</label>
                                    <div class="col-sm-10">
                                        <input v-model="section.vod" class="form-control" disabled>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="inputEmail1" class="col-sm-2 control-label">收费</label>
                                    <div class="col-sm-10">
                                        <select v-model="section.charge" class="form-control">
                                            <option v-for="o in SECTION_CHARGE" v-bind:value="o.key">{{o.value}}</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="inputEmail1" class="col-sm-2 control-label">顺序</label>
                                    <div class="col-sm-10">
                                        <input type="text" v-model="section.sort"  class="form-control" id="inputEmail1" placeholder="请输入章节名称">
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


        <modal-player ref="modalPlayer" ></modal-player>
    </div>
</template>
<script>
    import  Pagination from  "../../components/pagination";//分页组件
    import  SlicingFile from  "../../components/slicing-file";//文件上传
    import  OosPlayer from "../../components/oos-player";//视频播放
    import  Vod from  "../../components/vod";//文件上传封装组件
    import ModalPlayer from "../../components/modal-player";//视频播放封装组件
    export default {
        components: {Pagination,SlicingFile,Vod,OosPlayer,ModalPlayer},
        name: 'section',
        data:function() {
            return {
                section:{},
                chapter:{},
                course:{},
                sections: [],
                SECTION_CHARGE: SECTION_CHARGE,//{CHARGE:{key:"C", value:"收费"},FREE:{key:"F", value:"免费"}};
                FILE_USE:FILE_USE,//{COURSE:{key:"C", value:"课程"},TEACHER:{key:"T", value:"讲师"}};
            }
        },
        mounted: function () {
            let _this=this;
             _this.$refs.pagination.size=5;
            let course = SessionStorage.get(SESSION_KEY_COURSE) || {};//// 课程管理页面点击章管理时，保存课程信息
            let chapter = SessionStorage.get(SESSION_KEY_CHAPTER) || {};// 章管理页面点击节管理时，保存章信息
            if (Tool.isEmpty(course) || Tool.isEmpty(chapter)) {
                _this.$router.push("/welcome");
            }
            _this.course = course;
            _this.chapter = chapter;
            _this.list(1);
            // sidebar激活样式方法一
            this.$parent.activeSidebar("business-course-sidebar");
        },
    methods: {


        
          
        }
    }
</script>

<style scoped>
    video {
        width: 100%;
        height: auto;
        margin-top: 10px;
    }
</style>