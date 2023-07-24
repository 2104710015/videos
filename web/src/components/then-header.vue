<template>
    <div>
        <header>
            <div class="collapse bg-dark" id="navbarHeader">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-8 col-md-7 py-4">
                            <h4 class="text-white"><i class="fas fa-photo-video"></i>&nbsp;猿视课</h4>
                            <p class="text-muted">
                                Bootstrap中文网致力于前端开发推广，坚持分享、开放的互联网精神，旨在为广大前端攻城师、设计师提供交流的平台，如果你和我们有相同的目标，我们乐意一起携手前行。
                                如果你也热爱前端开发、热爱 Bootstrap，有一颗创业的心，欢迎和我们联系，更欢迎你加入我们的团队！
                            </p>
                        </div>
                    </div>
                </div>
            </div>
            <div class=" navbar-dark bg-dark shadow-sm">
                <nav class="navbar navbar-expand-lg  container  navbar-dark bg-dark  justify-content-between    navbar-light bg-light">
                    <a class="navbar-brand" href="#">
                        <i class="fas fa-photo-video"></i>&nbsp;猿视课</a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>


                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav mr-auto">
                            <li class="nav-item active">
                                <router-link class="nav-link" to="/">主页 <span class="sr-only">(current)</span></router-link>
                            </li>
                            <li class="nav-item active">
                                <router-link class="nav-link" to="/list">全部课程</router-link>
                            </li>
                            <li class="nav-item active">
                                <a class="nav-link" href="#" data-toggle="collapse" data-target="#navbarHeader" aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation">公告</a>
                            </li>
                            <li class="nav-item dropdown active">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-expanded="false">
                                   更多
                                </a>
                                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <a class="dropdown-item" href="#">关于我们</a>
                                    <a class="dropdown-item" href="#">渠道合作</a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item" href="#">更多信息</a>
                                </div>
                            </li>
                        </ul>
                        <span v-show="loginMember.id" class="text-white pr-3">{{loginMember.name}}</span>
                        <button v-show="loginMember.id" v-on:click="logout()" class="btn btn-outline-light my-2 my-sm-0">退出登录</button>
                        <button v-show="!loginMember.id"  type="submit"  @click="openLoginModal()" class="btn btn-outline-success my-2 my-sm-0 " >
                            <i class="fas fa-sign-in-alt"></i>
                            登录/注册
                        </button>
                    </div>
                </nav>
            </div>

            <!--引入登陆和注册组件-->
            <the-login ref="loginComponent"></the-login>
        </header>
    </div>
</template>

<script>
    import TheLogin from "./then-login";
    export default {
        name: "then-header",
        components: {TheLogin},
        data: function () {
            return {
                loginMember: {},
            }
        },
        mounted() {
            let _this = this;
            _this.loginMember = Tool.getLoginMember();
        },
        methods: {
            /**
             * 打开登录注册窗口
             */
            openLoginModal() {
                let _this = this;
                _this.$refs.loginComponent.openLoginModal();
            },
            setLoginMember(loginMember) {
                let _this = this;
                _this.loginMember = loginMember;
                console.log("-------登录的用户 数据", _this.loginMember);
            },

            logout () {

                let _this = this;
                console.log("-------登录的用户 数据", _this.loginMember.toKen);
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/web/member/logout/' + _this.loginMember.toKen).then((response)=>{
                    let resp = response.data;
                    if (resp.boo) {
                        Tool.setLoginMember(null);
                        _this.loginMember = {};
                        prompt.success("退出登录成功");
                        _this.$router.push("/");
                    } else {
                        prompt.warning(resp.message);
                    }
                });
            },


        }
    }
</script>

<style scoped>

</style>