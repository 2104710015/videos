<template>
  <div class="flex flex-col">
    <div v-for="(item,index) in timeData" :key="index">
      <div class="flex flex-center-cz">
        <div class="time-dot"></div>
        <div class="margin-left-m ">{{item.date}}</div>
      </div>
      <div class="flex flex-col padding-m time-item" >
        <div v-for="detail in item.list">{{detail}}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'timeline',
  props: {
    timeData: {},
  },
  created() {
  },
  data() {
    return {
    }
  },
  methods: {

  }
}
</script>

<style>
    .time-dot {
      width: 7px;
      height: 7px;
      border-radius: 100%;
      background-color: rgba(0, 0, 0, .5);
    }
    .time-item {
      border-left: 1px solid rgba(0, 0, 0, .2);
      margin-left: 3px;
      line-height: 28px;
    }
</style>
