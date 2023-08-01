import Vue from 'vue'
import Login from './views/login.vue'
import VueRouter from 'vue-router'
import Admin from './views/admin'
import User from './views/admin/user'
import Welcome from './views/admin/welcome'
import Student from './views/admin/student'
import Category from './views/admin/category'
import Role from './views/admin/role'
import Teacher from "./views/admin/teacher"
import Course from "@/views/admin/course";
import Anno from "@/views/admin/anno";
import UpdateInfo from "@/views/admin/updateInfo";

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
            },
            {
            path:"system/user",
            name:"system/user",
            component: User,
            },
            {
                path:"system/student",
                name:"system/student",
                component: Student,
            },
            {
                path:"system/category",
                name:"system/category",
                component: Category,
            },
            {
                path:"system/role",
                name:"system/role",
                component: Role,
            },
            {
                path:"system/teacher",
                name:"system/teacher",
                component: Teacher,
            },
            {
                path:"system/anno",
                name:"system/anno",
                component: Anno,
            },
            {
                path:"system/updateInfo",
                name:"system/updateInfo",
                component: UpdateInfo,
            },
            {
                path:"system/course",
                name:"system/course",
                component: Course,
            },
           ]
    }
    ]



})