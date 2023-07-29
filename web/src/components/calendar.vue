<template>
  <div class="calender">
    <div class="top">
      <div class="preDate">
        <button class="preYear" @click="getMyDate(year-parseInt(1,10),month)">&lt;&lt;</button>
        <button class="preMonth" @click="getMyDate(year,month-parseInt(1,10))">&lt;</button>
      </div>
      <div>{{year}}年{{month}}月</div>
      <div class="nextDate">
        <button class="nextMonth" @click="getMyDate(year,month+parseInt(1,10))">&gt;</button>
        <button class="nextYear" @click="getMyDate(year+parseInt(1,10),month)">&gt;&gt;</button>
      </div>
    </div>
    <div class="bottom">
      <table>
        <thead>
            <tr>
              <th v-for="item in weeks">
                {{item.name}}
              </th>
            </tr>
        </thead>
        <tbody>
            <tr>
              <th v-for="item in all[0]" :key="item" :class="{ active: item === day }">
                {{ item !== 0 ? item : '&nbsp;' }}
              </th>
            </tr>
            <tr v-for="(item, index) in all.slice(1)">
              <template v-for="item2 in item">
                <th v-if="item2 === day" class="active">{{ item2 }}</th>
                <th v-else-if="item2 > 0">{{ item2 }}</th>
                <th v-else></th>
              </template>
            </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  name: "calender",
  data(){
    return {
      year:2023,  // 设置当前年份
      month:7,    // 设置当前月份
      day:26,      // 设置当前号数
      all:[],  // 日历表格数组
      weeks:[
        {id:0,name:"日"},
        {id:1,name:"一"},
        {id:2,name:"二"},
        {id:3,name:"三"},
        {id:4,name:"四"},
        {id:5,name:"五"},
        {id:6,name:"六"},
      ]
    }
  },
  beforeMount() {
    this.getMyDate();
  },
  methods:{
    getMyDate(year=false,month=false){
      let obj;    // 初始化的时间对象
      let first=[]; // 第1排的数据
      let second=[];  // 第2-6排的数据
      let firstDay; // 判断当月第一天是周几，作为日期对象的开头
      if(year&&month){
        // 处理月份越界问题
        if(month<1){
          this.year-=1;
          this.month=12;
        }
        else if(month>12){
          this.year+=1;
          this.month=1;
        }
        else {
          this.month=month;
          this.year=year;
        }
        obj=new Date(this.year,month,this.day);
        this.all=[];  // 每次请求时把日期数组清空
      }
      else {
        obj=new Date();
        this.year=obj.getFullYear();  // 查询年份
        this.month=obj.getMonth()+1;  // 查询月份
        this.day=obj.getDate();       // 查询多少号
      }

      let s=this.year+'/'+this.month+'/'+1;
      firstDay=new Date(s).getDay();   // 查询当月的第一天是周几
      let days=new Date(this.year, this.month, 0).getDate(); // 判断当前月份有多少天

      // 生成日历表格
      // 生成第一排的数据
      let index;
      let data1=[7,1,2,3,4,5,6];
      data1.forEach((i,item)=>{
        if(item===firstDay){
          index=i;
        }
      });
      for(let i=0;i<7;i++){
        if(i<index){
          first.push(0)
        }
        else {
          first.push(i-index+1)
        }
      }

      // 生成第2-6排数据
      let index2=first[first.length-1];
      for(let i=0;i<5;i++){
        let d=[];
        for(let j=1;j<8;j++){
          if(i*7+index2+j>days){
            d.push(0)
          }
          else{
            d.push(i*7+index2+j)
          }
        }
        second.push(d)
      }
      // 将第1排和第2-6排数据合到一起
      this.all.push(first);
      second.forEach(x=>this.all.push(x));
    }
  }
}
</script>

<style scoped>
    .calender{
      width: 283px;
      height: auto;
      box-shadow: 0 0 1px #9E9C9C;
      display: flex;
      flex-flow: column;
      margin-left: 10px;
    }
    .top{
      flex: 1;
      border-bottom: solid 1px #E0DEDE;
      text-align: center;
      line-height: 40px;
      color: #696868;
      display: flex;
    }
    .bottom{
      flex: 7;
      text-align: center;
    }
    table{
      width: 100%;
      height: 100%;
    }
    th{
      color: #6B6A6A;
      font-weight: normal;
      transition: all 0.5s;
      border-radius: 50%;
      height: 36px;
    }
    tbody>tr>th:hover{
      background-color: #E5E6E6;
    }
    .active,.active:hover{
      background-color: #FB4023;
      color: white;
    }
    .top>div:nth-child(2){
      width: 100px;
      height: 100%;
      margin: 0 auto;
    }
    .preDate,.nextDate{
      flex: 1;
      display: flex;
    }
    .preYear,.nextYear{
      flex: 2;
    }
    .preMonth,.nextMonth{
      flex: 1;
    }
    .preDate>button,.nextDate>button{
      border: none;
      background-color: white;
      font-size: 17px;
      color: #999999;
      transition: color 0.8s;
      font-weight: 100;
      outline: none;
    }
    .preMonth{
      text-align: left;
    }
    .nextMonth{
      text-align: right;
    }
    .preMonth:hover,.nextMonth:hover,.preYear:hover,.nextYear:hover{
      color: red;
      cursor: pointer;
    }
</style>
