<template>
  <div>
    <div class="announcement" v-for="(announcement, index) in announcements" :key="index">
      <li @click="showPopup(index)">{{ announcement.title }}</li>
    </div>

    <div class="popup-overlay" v-if="showPopupFlag">
      <div class="popup" >
        <div class="popup-content">
          <div class="popup-title">
            <h3>{{ selectedAnnouncement.title }}</h3>
          </div>
          <div class="popup-divider"></div>
          <div class="popup-description">
            <p>{{ selectedAnnouncement.content }}</p>
          </div>
          <div class="close-button-container">
            <button @click="closePopup" class="close-button">关闭</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name:'anno',
  data() {
    return {
      // announcements1: [
      //   { title: '公告1', content: '这是公告1的内容。' },
      //   { title: '公告2', content: '这是公告2的内容。' },
      //   { title: '公告3', content: '这是公告3的内容。' }
      // ],
      announcements: [], // 从数据库读取的公告列表
      showPopupFlag: false,
      selectedAnnouncement: {}
    };
  },
  mounted() {
    let _this = this;
    _this.loadAnnouncements();
  },
  methods: {
    loadAnnouncements(page) {
      let _this=this;
      _this.$ajax.post(process.env.VUE_APP_SERVER+'/system/admin/anno/list',{
        page:1,
        size:1000
      }) // 发送异步请求获取公告数据（根据实际情况调整API路径）
          .then(response => {
            console.log("这是公告信息: ",response);
            if (response.data.boo){
              _this.announcements=response.data.content.data;
            }else {
              prompt.error("访问失败");
            }
          })
    },
    showPopup(index) {
      this.selectedAnnouncement = this.announcements[index];
      this.showPopupFlag = true;
    },
    closePopup() {
      this.showPopupFlag = false;
    }
  }
};
</script>
<style>
.close-button {
  padding: 8px 16px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

.close-button:hover {
  background-color: #45a049;
}

.close-button:focus {
  outline: none;
}

.close-button-container {
  /*position: relative;*/
  position: absolute;
  bottom: 0;
  text-align: center;
  padding: 16px 0;
}

.announcement{
  text-align: left;
}

.popup-title {
  /* 标题样式 */
  text-align: center;
  margin-bottom: 16px;
}

.popup-divider{
  width: 100%;
  height: 1px;
  background-color: #ccc;
  margin: 16px 0;
}

.popup-content {
  text-align: justify;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.popup-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.popup {
  /*background-color: white;*/
  /*padding: 40px;*/
  background-color: #FFF;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  max-width: 400px;
  padding: 24px;
  position: relative;
  width: 500px;
  height: 300px;
  top: 50%;
  left: 50%;
  transform: translate(-80%, -170%);
}

.popup-content h3 {
  margin-top: 0;
}

.popup-content p {
  margin-bottom: 20px;
}
</style>



