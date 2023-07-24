<template>
    <div class="main-container">
        <div class="main-content">
            <div class="row">
                <div class="col-sm-10 col-sm-offset-1">
                    <div class="login-container">
                        <div class="center">
                            <h1>
                                <i class="ace-icon fa fa-leaf green"></i>
                                <span class="red">后台</span>
                                <span class="blue" id="id-text2">管理页面</span>
                            </h1>
                            <h4 class="blue" id="id-company-text">&copy;请叫我隔壁【老陈】</h4>
                        </div>

                        <div class="space-6"></div>

                        <div class="position-relative">
                            <div id="login-box" class="login-box visible widget-box no-border">
                                <div class="widget-body">
                                    <div class="widget-main">
                                        <h4 class="header blue lighter bigger">
                                            <i class="ace-icon fa fa-coffee green"></i>
                                            欢迎你，登陆后台系统
                                        </h4>

                                        <div class="space-6"></div>

                                        <form>
                                            <fieldset>
                                                <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" v-model="user.loginName" class="form-control" placeholder="请输入账号....." />
															<i class="ace-icon fa fa-user"></i>
														</span>
                                                </label>

                                                <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" v-model="user.password"  class="form-control" placeholder="请输入密码....." />
															<i class="ace-icon fa fa-lock"></i>
														</span>
                                                </label>

                                                <label class="block clearfix">
                                                  <span class="block input-icon input-icon-right">
                                                    <div class="input-group">
                                                      <input type="text" v-model="user.imageCode" class="form-control" placeholder="验证码">
                                                      <span class="input-group-addon" id="basic-addon2">
                                                        <img v-on:click="loadImageCode()" id="image-code" alt="验证码"/>
                                                      </span>
                                                    </div>
                                                  </span>
                                                </label>

                                                <div class="space"></div>

                                                <div class="clearfix">
                                                    <label class="inline">
                                                        <input type="checkbox" v-model="remember" class="ace" />
                                                        <span class="lbl"> 记住我</span>
                                                    </label>

                                                    <button type="button"
                                                            @click="login()"
                                                            class="width-35 pull-right btn btn-sm btn-primary">
                                                        <i class="ace-icon fa fa-key"></i>
                                                        <span class="bigger-110">登陆</span>
                                                    </button>
                                                </div>

                                                <div class="space-4"></div>
                                            </fieldset>
                                        </form>

                                        <div class="space-6"></div>

                                    </div><!-- /.widget-main -->

                                    <div class="toolbar clearfix">
                                        <div>
                                        </div>
                                        <div>
                                        </div>
                                    </div>

                                </div><!-- /.widget-body -->
                            </div><!-- /.login-box -->



                        </div><!-- /.position-relative -->

                        <div class="navbar-fixed-top align-right">
                            <br />
                        </div>
                    </div>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container -->

</template>

<script>
    export default {
        name: 'App',
        data:function() {
            return {
                user:{},
                remember:true, //默认是勾选上的，记得我
                imageCodeToken: "",
            }
        },
        mounted: function () {
            let _this = this;
            $('body').removeClass('no-skin');
            $('body').attr('class','login-layout light-login');
            //console.log("login");
            // 从缓存中获取记住的用户名密码，如果获取不到，说明上一次没有勾选“记住我”
            let rememberUser = LocalStorage.get(LOCAL_KEY_REMEMBER_USER);
            if (rememberUser) {
                _this.user = rememberUser;
            }

            // 初始时加载一次验证码图片
            _this.loadImageCode();
        },
        methods:{
            login(){
                let _this=this;
                // 保存校验
                if (1 != 1
                    || !Validator.require(_this.user.loginName, "登陆名")
                    || !Validator.require(_this.user.password, "密码")
                    || !Validator.require(_this.user.imageCode, "验证码")
                ) {
                    return;
                }
                 //未优化之前的，将密码使用明文的方式进行保存
                //let passwordShow = _this.user.password;
                // 如果密码是从缓存带出来的，则不需要重新加密
                let md5 = hex_md5(_this.user.password);
                //防止获取的用户和密码方式null
                let rememberUser = LocalStorage.get(LOCAL_KEY_REMEMBER_USER) || {};
                if (md5 !== rememberUser.md5) {
                    //如:是重新输入的密码，这需要进行重新加密
                    //密码前台加密:key在加密追加字符之后，在进行加密
                    _this.user.password = hex_md5(_this.user.password + KEY);
                }
                //设置token,每次都是随机的
                _this.user.imageCodeToken = _this.imageCodeToken;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/login',_this.user)
                    .then((response)=>{
                        Loading.hide();
                        $("#form-modal").modal("hide");
                        if (response.data.boo) {
                           // prompt.success('用户登陆成功!');
                            console.log("登录成功：", response.data.content);
                            let loginUser = response.data.content;
                            Tool.setLoginUser(response.data.content);

                            // 判断“记住我”
                            if (_this.remember) {
                                // 如果勾选记住我，则将用户名密码保存到本地缓存，这里需要保存密码明文，否则登录时又会再加一层密
                                //优化后: 需要对密码进行加密，，并保存密文md5，用于检测密码是否被重新输入过
                                let md5 = hex_md5(_this.user.password);
                                LocalStorage.set(LOCAL_KEY_REMEMBER_USER, {
                                    loginName: loginUser.loginName,
                                    password: _this.user.password,
                                    md5:md5,
                                });
                            } else {
                                // 没有勾选“记住我”时，要把本地缓存清空，否则按照mounted的逻辑，下次打开时会自动显示用户名密码
                                LocalStorage.set(LOCAL_KEY_REMEMBER_USER, null);
                            }
                            _this.$router.push("/welcome");
                        }else {
                            prompt.error(response.data.message);
                            _this.user.password = "";
                            _this.user.imageCode = "";
                            _this.loadImageCode();
                        }
                    })

            },

            /**
             * 加载图形验证码
             */
            loadImageCode: function () {
                let _this = this;
                _this.imageCodeToken = Tool.uuid(8);
                $('#image-code').attr('src', process.env.VUE_APP_SERVER + '/system/admin/kaptcha/image-code/' + _this.imageCodeToken);
            },
        }
    }
</script>

<style scoped>
    .input-group-addon {
        padding: 0;
        cursor:pointer;
    }
</style>