<template>
  <div class="left-manu">
    <el-row class="tac">
      <el-col :span="24">
        <el-menu
          :default-active="activeNav"
          class="el-menu-vertical-demo"
          router
          @select="handleSelect2"
        >
          <!-- <el-menu-item index="/helpCenter/desc">
            <span slot="title">功能概述</span>
          </el-menu-item> -->
          <el-submenu index="/helpCenter/question">
            <template slot="title">
              <span>开发文档</span>
            </template>
            <el-menu-item-group>
              <el-menu-item v-for="item in sliderArr" :key="item.id" :index="`/helpCenter/admin/${item.id}`">{{ item.title }}</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-menu-item index="/helpCenter/question">
            <span slot="title">常见问题</span>
          </el-menu-item>
        </el-menu>
      </el-col>
    </el-row>
  </div>
</template>
<script>
export default {
  name: 'MenuItem',
  data() {
    return {
      sliderArr: [
        { id: 1, title: '接入流程' },
        { id: 2, title: '后端接入' },
        { id: 3, title: 'html接入' },
        { id: 4, title: 'vue接入' },
        { id: 5, title: 'flutter接入' },
        { id: 6, title: 'uni-app接入' },
        { id: 7, title: 'weex接入' },
        { id: 8, title: 'ReactNative接入' },
        { id: 9, title: 'android接入' },
        { id: 10, title: 'ios接入' },
      ]
    }
  },
  computed: {
    activeNav() {
      return this.$route.path
    }
  },
  mounted() {
    // this.activeNav=window.location.href.lastIndexOf("\\");
    // console.log(this.activeNav)
  },
  methods: {
    handleSelect2(key, keyPath) {
      // this.activeNav=key
    },
  }
}
</script>

<style scoped lang="less">
  .sidebar-container {
    transition: width 0.28s;
  .horizontal-collapse-transition {
    transition: 0s width ease-in-out, 0s padding-left ease-in-out, 0s padding-right ease-in-out;
  }
  .el-scrollbar {
    height: 100%;
  }
  .scrollbar-wrapper {
    overflow-x: hidden!important;
  .el-scrollbar__view {
    height: 100%;
  }
  }
  .el-scrollbar__bar.is-vertical{
    right: 0px;
  }
  .is-horizontal {
    display: none;
  }
  a {
    display: inline-block;
    width: 100%;
    overflow: hidden;
  }
  .el-menu {
    border: none;
    height: 100%;
    width: 100% !important;
  }
  .is-active > .el-submenu__title{
    color: #f4f4f5!important;
  }
  }

  .el-menu {
    border-right: none;
    a {
      text-decoration: none;
    }
  }
  // div:hover,ul:hover,li:hover,span:hover,ul li:hover,ul li ul:hover{
  //   background: none!important;
  //   color: #333!important;
  // }

</style>
