<template>
  <!-- <el-scrollbar wrap-class="scrollbar-wrapper"> -->
  <div>
    <hamburger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container" />
    <el-menu :show-timeout="200" :default-active="$route.path" :collapse="isCollapse" mode="vertical">
      <sidebar-item v-for="route in permission_routers" :key="route.name" :item="route" :base-path="route.path" />
    </el-menu>
  </div>
  <!-- </el-scrollbar> -->
</template>

<script>
import { mapGetters } from 'vuex'
import SidebarItem from './SidebarItem'
import Hamburger from '@/components/Hamburger'

export default {
  components: { SidebarItem, Hamburger },
  computed: {
    ...mapGetters([
      'permission_routers',
      'sidebar'
    ]),
    isCollapse() {
      return !this.sidebar.opened
    },
    // filterList: function () {
    //   var key = this.key;
    //   var list = this.permission_routers;
    //   return list.filter(function (item) {
    //     console.log(item);
    //     return item.meta.navShow ==null || item.meta.navShow != false;
    //   });
    // }
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('toggleSideBar')
    }
  },
  mounted() {
    //console.log(this.permission_routers);
  }
}
</script>

<style scoped>
.hamburger-container {
  margin-left: 17px;
  margin-top: 7px;
}
</style>


