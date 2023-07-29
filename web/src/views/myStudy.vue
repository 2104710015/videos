<template>
<!--  <h1>我的学习</h1>-->

  <div>
    <main role="main">
      <div class="carousel-all">
        <div class="row">
          <div class="carousel-left col-md-3 offset-md-1">
            <div class="carousel-myself col-md-4=12">
              个人信息
            </div>
            <div class="carousel-calendar col-md-4=12">
              <calendar class="calendar-card"></calendar>
            </div>
          </div>

          <div class="carousel-right col-md-7">
            <div class="carousel-course col-md-4=12">
              我的课程
              <myCourse></myCourse>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import myCourse from "../components/myCourse";
import calendar from "../components/calendar";
export default {
  name: "myStudy",
  components:{
    calendar,
    myCourse
  }
}
</script>

<style scoped>
.carousel-myself{
  border: 2px solid #834444;
  /*padding: 50px; !* 设置内边距为10像素 *!*/
  margin-top: 3%;
  /*padding-left: 100px; !* 设置左边距为100像素 *!*/
  /*padding-right: 20px; !* 设置右边距为20像素 *!*/
  height: 280px;

}
.carousel-calendar{
  border: 2px solid #834444;
  /*padding: 50px; !* 设置内边距为10像素 *!*/
  margin-top: 3%;
  /*padding-left: 100px; !* 设置左边距为100像素 *!*/
  /*padding-right: 20px; !* 设置右边距为20像素 *!*/
  height: 296px;
}
.calendar-card{
  margin: auto;
  width: 100%;
  /*height: 100%;*/
}
.carousel-course{
  border: 2px solid #834444;
  /*padding: 50px; !* 设置内边距为10像素 *!*/
  margin-top: 1.5%;
  /*padding-left: 100px; !* 设置左边距为100像素 *!*/
  /*padding-right: 20px; !* 设置右边距为20像素 *!*/
  height: 584px;
}
</style>
