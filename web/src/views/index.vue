<template>
    <div>
        <main role="main">
            <section class="jumbotron text-center">
                <div class="row">
                    <div class="carousel-all col-md-7 offset-md-1">
                        <div id="carouselExampleCaptions" class="carousel slide  "  data-ride="carousel">
                    <ol class="carousel-indicators">
                        <li data-target="#carouselExampleCaptions" data-slide-to="0" class="active"></li>
                        <li data-target="#carouselExampleCaptions" data-slide-to="1"></li>
                        <li data-target="#carouselExampleCaptions" data-slide-to="2"></li>
                    </ol>
                    <div class="carousel-inner col-md-12 ">
                        <div class="carousel-item active">
                            <img src="../../public/static/img/1.png"   class="d-block w-100" alt="图片1">
                            <div class="carousel-caption d-none d-md-block">
                                <!-- <h5>网络文化课程</h5>
                                <p>免费预约试听课，分析学生学情，量身定制专属课程</p> -->
                                <p>
                                    <router-link to="/list" class="btn btn-primary my-2 p-3 font-weight-bold">点击进入所有课程</router-link>
                                </p>
                            </div>
                        </div>
                        <div class="carousel-item col-md-12">
                            <img src="../../public/static/img/2.png"   class="d-block w-100" alt="图片2">
                            <div class="carousel-caption d-none d-md-block">
                                
                                <p>
                                    <router-link to="/list" class="btn btn-primary my-2 p-3 font-weight-bold">点击进入所有课程</router-link>
                                </p>
                            </div>
                        </div>
                        <div class="carousel-item col-md-12">
                            <img src="../../public/static/img/3.png"   class="d-block w-100" alt="图片3">
                            <div class="carousel-caption d-none d-md-block">
                             
                                <p>
                                    <router-link to="/list" class="btn btn-primary my-2 p-3 font-weight-bold">点击进入所有课程</router-link>
                                </p>
                            </div>

                        </div>
                    </div>
                    <button class="carousel-control-prev" type="button" data-target="#carouselExampleCaptions"  data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-target="#carouselExampleCaptions"  data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </button>
                </div>
                    </div>
                    <div class="carousel-right col-md-3">
                        <div class="notice-content">
                          <h2>最新公告</h2>
                          <anno></anno>
                        </div>
                    </div>
                </div>
            
            </section>

            <div class="course-new col-md-12" style="margin-top: 60px">
                <div class="container">
                    <div class="title1">最新上线</div>
                    <div class="row">
                        <!--最新的3门课程-->
                        <div v-for="o in news" class="col-md-4"  v-bind:key="o" >
                            <then-course v-bind:course="o"></then-course>
                        </div>
                    </div>

                    <!-- <div class="title1">优质课程</div>
                    <div class="row">
                        <div v-for="o in news" class="col-md-4"  v-bind:key="o" >
                            <then-course v-bind:course="o"></then-course>
                        </div>
                    </div> -->
                    <!-- <div class="title1">学员推挤</div>
                    <div class="row">

                        <div v-for="o in news" class="col-md-4"  v-bind:key="o" >
                            <then-course v-bind:course="o"></then-course>
                        </div>
                    </div> -->


                </div>
            </div>

        </main>

    </div>
</template>

<script>
    import anno from "../components/anno";
    import ThenCourse from "../components/then-course";
    export default {
        name: "index",
        components: {
          ThenCourse,
          anno
        },
        data: function () {
            return {
                news: [],
            }
        },
        mounted() {
            let _this = this;
            _this.listNew();
        },
        methods: {
            /**
             * 查询新的上好课
             */
            listNew() {
                let _this = this;
                // 新上的好课不经常变，又经常被访问，适合用缓存
                // 判断是否有缓存
                let news = SessionStorage.get("news");
                if (!Tool.isEmpty(news)) {
                    _this.news = news;
                    return;
                }

                _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/web/course/listNew').then((response)=>{
                    console.log("查询新上好课结果：", response);
                    let resp = response.data;
                    if (resp.boo) {
                        _this.news = resp.content;
                        // 保存到缓存
                        SessionStorage.set("news", _this.news);
                    }
                }).catch((response)=>{
                    console.log("error：", response);
                })
            },
        }
    }
</script>

<style scoped>
    .carousel-control-next, .carousel-control-prev {
        position: absolute;
        top: 0;
        bottom: 0;
        z-index: 1;
        display: -ms-flexbox;
        display: flex;
        -ms-flex-align: center;
        align-items: center;
        -ms-flex-pack: center;
        justify-content: center;
        width: 15%;
        padding: 0;
        color: #ffffff;
        text-align: center;
        background: 0 0;
        border: 0;
        opacity: .5;
        transition: opacity .15s ease;}
    .jumbotron {
        padding-top: 0.4rem;
        padding-bottom: 0.1rem;
        margin-bottom: 0;
    }
    .title1{
        margin-bottom: 2rem;
        color: #fafafa;
        letter-spacing: 0;
        text-shadow: 0px 1px 0px #999, 0px 2px 0px #888, 0px 3px 0px #777, 0px 4px 0px #666, 0px 5px 0px #555, 0px 6px 0px #444, 0px 7px 0px #333, 0px 8px 7px #001135;
        font-size: 2rem;
    }
    .title2{
        margin-bottom: 2rem;
        color: transparent;
        -webkit-text-stroke: 1px black;
        letter-spacing: 0.04em;
        font-size: 2rem;
    }
    .notice-content {
        border: 2px solid #834444; /* 设置边框为2像素宽度的白色实线 */
        padding: 10px; /* 设置内边距为10像素 */
        padding-left: 20px; /* 设置左边距为20像素 */
        padding-right: 20px; /* 设置右边距为20像素 */
        height: 295px;
    }


</style>