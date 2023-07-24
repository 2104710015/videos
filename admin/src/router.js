import Vue from 'vue'
import Login from './views/login.vue'
import VueRouter from 'vue-router'
import Admin from './views/admin'
import User from './views/admin/user'
import Welcome from './views/admin/welcome'
import studetn from './views/admin/studetn'

Vue.use(VueRouter);

export default new VueRouter({
    mode:"history",
    base: process.env.BASE_URL,
    routes:[{
        path:"*",
        redirect:"/login",
    },{
        path:"",
        redirect:"/login",
    },{
        path:"/login",
        component: Login,
    },{
        path:"/",
        name:"admin",
        component: Admin,
        meta: {
            loginRequire: true
        },
        children:[{
            path:"welcome",
            name:"welcome",
            component: Welcome,
        },{
            path:"system/user",
            name:"system/user",
            component: User,
            }, {
                path:"system/studetn",
                name:"system/studetn",
                component: studetn,
            }
           ]
    }
    ]



})