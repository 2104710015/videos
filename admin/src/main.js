import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import filter from './filter/filter'



Vue.config.productionTip = false;
Vue.prototype.$ajax=axios;
// 解决每次ajax请求，对应的sessionId不一致的问题
axios.defaults.withCredentials = true;


/**
 * axios拦截器
 *  统一管理日志的输出
 */
axios.interceptors.request.use(function (config) {
  console.log("请求：", config);

  let toKen = Tool.getLoginUser().toKen;
  if (Tool.isNotEmpty(toKen)) {
    //这里的toKen必须与后台请求的参数key一致，便于获取
    //将对应的toKen设置到请求头上
    config.headers.toKen = toKen;
    console.log("请求headers增加token:", toKen);
  }
  return config;
}, error => {});
axios.interceptors.response.use(function (response) {
  console.log("返回结果：", response);
  return response;
}, error => {
  Loading.hide();
  console.log("返回拦截：", error.response);
  let response = error.response;
  const status = response.status;
  if (status === 401) {
    // 判断状态码是401 跳转到登录
    console.log("未登录，跳到登录页面");
    Tool.setLoginUser(null);
    router.push('/login');
  }
  return {
    data: {
      success: false,
      message: "请重新登录"
    }
  };

});

// 全局过滤器
Object.keys(filter).forEach(key => {
  Vue.filter(key, filter[key])
});
// 路由登录拦截
//to 标识之前   from 标识之后，  next 标识继续执行
router.beforeEach((to, from, next) => {
  // 要不要对meta.loginRequire属性做监控拦截
  if (to.matched.some(function (item) {
    return item.meta.loginRequire
  })) {
    let loginUser = Tool.getLoginUser();
    if (Tool.isEmpty(loginUser)) {
      next('/login');
    } else {
      next();
    }
  } else {
    next();
  }
});

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
