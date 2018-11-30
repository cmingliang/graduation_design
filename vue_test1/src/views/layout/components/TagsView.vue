<template>
  <div class="tags-view-container"  >
    <hamburger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container"/>
    <!-- 面包屑导航区域-->
    <div class="breadcrumb_container">

      <el-breadcrumb class="app-breadcrumb" separator="/">

        <el-breadcrumb-item v-for="(item)  in levelList" :key="item.path" v-if="item.meta.name">
          <span v-if="item.meta.clickAble != null && item.meta.clickAble==false">{{item.meta.name  }}</span>
          <router-link v-else  :to="item.redirect||item.path">{{item.meta.name  }}</router-link>

        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import ScrollPane from '@/components/ScrollPane'
  import Hamburger from '@/components/Hamburger'

export default {
  components: {   Hamburger,ScrollPane },
  data() {
    return {
      visible: false,
      top: 0,
      left: 0,
      selectedTag: {},
      levelList: []
    }
  },
  computed: {
    visitedViews() {
      return this.$store.state.tagsView.visitedViews
    },
    ...mapGetters([
      'sidebar',
      'name',
      'avatar',
      'device'
    ])
  },
  watch: {
    $route(to, from) {
      this.getBreadcrumb(to, from);
    },
    visible(value) {
      if (value) {
        document.body.addEventListener('click', this.closeMenu)
      } else {
        document.body.removeEventListener('click', this.closeMenu)
      }
    }
  },
  // mounted() {
  //   this.addViewTags()
  // },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('toggleSideBar')
    },
    //生成面包屑的方法
    getBreadcrumb(to, from) {

      let matched = this.$route.matched.filter(item => {
        return  item.name
      });

      let tempRouter = [];
      for(let index = 0;index< matched.length;index++){//为了解决循环调用问题,没有找到原因,自己封装一个简单的对象
        tempRouter.push({ path: matched[index].path, meta: matched[index].meta});
      }
      matched = tempRouter;
      if(to != null && to.meta.subPage){//不在菜单中的子页面
        let mydata = JSON.parse(localStorage.breadcrumb);
        let flag = false;
        if(from.meta.subPage){//从子页面返回的,或者点击面包屑导航过来的
          for(let i = 0;i<mydata.length;i++){
            if(mydata[i].path == to.path){
              mydata.splice(i+1,mydata.length-i-1);
              matched = mydata;
              flag = true;
              break;
            }
          }
        }
        if(!flag){
          matched = mydata.concat(matched);
        }
      }
      let json = JSON.stringify(matched);
      localStorage.setItem('breadcrumb',json);
      this.levelList = matched;
      console.log(this.levelList);
    },

  },
  mounted() {
    this.getBreadcrumb();
  },
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.tags-view-container {
  position: fixed;
  top: 60px;
  z-index: 2;
  transition: margin-left .28s;
  height: 34px;
  width: 100%;
  margin-left:180px;
  background: #fff;
  border-bottom: 1px solid #d8dce5;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .12), 0 0 3px 0 rgba(0, 0, 0, .04);
}

.hamburger-container{
  display: inline-block;
  float:left;
  line-height: 34px;
  margin-left: 5px;
  margin-top: 5px;
}

  .breadcrumb_container{
    float:left;
    margin-left: 10px;
    .el-breadcrumb{
      line-height: 34px;
    }
  }
</style>


