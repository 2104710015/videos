<template>
  <div class="body">
    <div class="table">
      <div class="filter font-bold">更新信息时间轴</div>
      <div class="margin-top-l margin-left-l flex flex-center-cz">
        <timeline :timeData="timeData"></timeline>
      </div>
    </div>
  </div>
</template>

<script>
/*
       名称：组件库（二） 时间轴
*/

import timeline from "../components/timeline";
export default {
  name: 'updateInfo',
  components: {
    timeline
  },
  data () {
    return {
      timeData:[],
    }
  },
  mounted: function () {
    let _this=this;
    _this.list();
  },
  methods: {
    list(page) {
      let _this = this;
      // Loading.show();
      _this.$ajax.post(process.env.VUE_APP_SERVER + "/customer/admin/updateInfo/list", {
        page: 1,
        size: 7
      }).then((res) => {
        console.log('获取更新的数据信息', res);
        if (res.data.boo) {//有结果返回了对应的数据信息，才进行显示并取消加载的图片
          _this.timeData = res.data.content.data;
          console.log('res.data.content.data.total', res.data.content.total);
        }
      })

    },
  }
}
</script>

<style scoped>
    .body {
      display: flex;
      justify-content: center;
      margin-top: 73px;
      width: 100%;
    }
    .table {
      background-color: #fff;
      width: 1080px;
      min-height: 800px;
      box-shadow: 0px 3px 6px rgba(0, 0, 0, .1);
      margin-bottom: 10px;
    }
    .filter {
      display: flex;
      height: 60px;
      align-items:center;
      background-color: #fff;
      font-size: 18px;
      justify-content: center;
      border-bottom: 1px solid rgba(0, 0, 0, .2);;
    }

</style>
