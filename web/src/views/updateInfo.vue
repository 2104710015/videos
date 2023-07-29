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
      timeData:[
        {"date":"2023-1-1", "list":["新增PDF转WORD功能",'修复上传文件太大导致面面卡死的bug','修复了ppt部分页面不显示的问题','修改了其他已知问题']},
        {"date":"2023-12-25", "list":["新增线上富文编辑功能",'app端修复了文件上传未限制选择文件类型的bug']},
        {"date":"2023-12-22", "list":["修改了部分已知bug"]}
      ]
    }
  },
  methods: {
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
