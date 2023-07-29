import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from './views/index.vue'
import list from "./views/list";
import detail from "./views/detail";
import helpDocument from "./views/helpDocument";
import updateInfo from "./views/updateInfo";
import myStudy from "./views/myStudy";



Vue.use(VueRouter);

export default new VueRouter({
    mode:"history",
    base: process.env.BASE_URL,
    routes:[{
        path:"*",
        redirect:"/index",
    },{
        path:"/index",
        component: Index
    },{
        path: "/list",
        component: list
    },{
        path: "/detail",
        component: detail
    },{
        path: "/helpDocument",
        component: helpDocument
    },{
        path: "/updateInfo",
        component: updateInfo
    },{
        path: "/myStudy",
        component: myStudy
    }
    ]

})