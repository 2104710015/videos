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
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>id</th>
        <th>姓名</th>
        <th>昵称</th>
        <th>头像</th>
        <th>职位</th>
        <th>座右铭</th>
        <th>简介</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="teacher in teachers">
        <td>{{teacher.id}}</td>
        <td>{{teacher.name}}</td>
        <td>{{teacher.nickname}}</td>
        <td><img v-show="teacher.image" v-bind:src="teacher.image"/>{{teacher.image}}</td>
        <td>{{teacher.position}}</td>
        <td>{{teacher.motto}}</td>
        <td>{{teacher.intro}}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button @click="update(teacher)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>
            &nbsp;
            <button @click="del(teacher.id)" class="btn btn-xs btn-danger">
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
                  <a href="#"  @click="update(teacher)" class="tooltip-success" data-rel="tooltip" title="Edit">
                     <span class="green">
                       <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                     </span>
                  </a>
                </li>
                <li>
                  <a href="#"  @click="del(teacher.id)" class="tooltip-error" data-rel="tooltip" title="Delete">
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
  </div>
</template>

<script>
import  Pagination from  "../../components/pagination";
export default {
  components: {Pagination},
  name: 'teacher',
  data:function() {
    return {
      teacher:{},
      teachers: [],
    }
  },
  mounted: function () {
    let _this=this;
    _this.$refs.pagination.size=5;
    _this.list(1);
  },
  methods:{
    list(page) {
      let _this = this;
      Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/customer/admin/teacher/list", {
        page: page,
        size: _this.$refs.pagination.size
      }).then((res) => {
        console.log('获取教师的数据信息', res);
        if (res.data.boo) {//有结果返回了对应的数据信息，才进行显示并取消加载的图片
          _this.teachers = res.data.content.data;
          let date=res.data.content.data;
          // for (let i = 0; i < _this.teachers.length; i++) {
          //   $("#img").attr("src",date[i].image);
          // }
          console.log('res.data.content.data.total', res.data.content.total);
          _this.$refs.pagination.render(page, res.data.content.total);
          Loading.hide();
        }
      })

    },
  },
}
</script>

<style scoped>

</style>