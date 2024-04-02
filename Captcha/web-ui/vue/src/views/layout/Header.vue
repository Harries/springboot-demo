<template>
  <div class="nav-menu">
    <el-menu :default-active="onRoutes" :default-openeds="[$route.path]" class="el-menu-demo" mode="horizontal" background-color="#203160" text-color="rgba(255,255,255,0.4)" active-text-color="#7AB1F9" router @select="handleSelect">
      <el-row type="flex" justify="center">
        <el-col :xs="22" :sm="22" :md="22" :lg="22" :xl="23">
          <div class="grid-content pd-main">
            <div class="userBox fr" @click="dropOut">
              <!-- {{getAccessUser.userName}} -->
              <el-button class="goOut"><i class="icon iconfont icon-zhuxiao" />退出</el-button>
            </div>
            <a @click="goHome"><img class="logo" src="./../../assets/image/logo2.png" alt=""></a>
            <div class="nav-md fr">
              <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name" class="fr">{{ item.navItem }}</el-menu-item>
            </div>
          </div>
        </el-col>
      </el-row>
    </el-menu>
  </div>
</template>

<!--
-->
<script>
import { setItem, getItem } from '@/utils/storage'
export default {
  data() {
    return {
      activeIndex: '1',
      userManageCode: [],
      navList: [
        { name: '/apply', navItem: '谁在使用', manage: 'applyManage' },
        { name: '/doc', navItem: '在线文档', manage: 'docManage' },
        { name: '/useOnline', navItem: '在线体验', manage: 'chartManage' },
      ]
    }
  },
  computed: {
    getAccessUser() {
      return getItem('accessUser')
    },
    onRoutes() {
      if (this.$route.path.includes('/helpCenter')) {
        return '/helpCenter'
      } else if (this.$route.path.includes('/useOnline')) {
        return '/useOnline'
      } else {
        return this.$route.path
      }
    },

  },
  mounted() {
  },
  methods: {
    dropOut() {
      var self = this
      this.$confirm('您确定要退出吗?', '退出平台', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(() => {
        this.$router.push('/login')
        // const info = {
        //   userName: this.getAccessUser.userName
        // }
        // LogOut(info).then(res => {
        //   if (res.repCode == "0000") {
        //     sessionStorage.clear()
        //     localStorage.clear();
        //     this.$router.push("/login");
        //   }
        // }).catch(error => {
        // })
      }).catch(() => {

      })
    },
    handleSelect(key, keyPath) {
    },
    // go home
    goHome() {
      this.$router.push('/index')
    }
  }
}
</script>

<style lang="less" scoped>
  .el-dropdown-link{
    color:rgba(255, 255, 255, 0.4);
    font-size: 12px;
  }
.nav-menu {
  width: 100%;
  background: #203160;
  position: fixed;
  z-index: 10000;
  .logo {
    margin-top: 14px;
    width: 85px;
  }
  .nav-md {
    display: block;
    margin-right: 80px;
  }
  .nav-mini {
    display: none;
  }
}
.el-menu-item {
  padding: 0;
  margin: 0 15px;
  font-size: 15px;
  line-height: 70px;
}

.el-menu-item.is-active {
  animation: 2s infinite;
  border-top: 6px solid #7ab1f9;
  font-size: 15px;
  color: #7ab1f9;
  font-weight: bold;
  line-height: 59px;
}

.el-menu--horizontal .el-menu-item:not(.is-disabled):focus,
.el-menu--horizontal .el-menu-item:not(.is-disabled):hover {
  border-top: 6px solid #7ab1f9;
  color: #7ab1f9;
  background: none !important;
  line-height: 59px;
}
.el-menu-item:last-child {
  margin-right: 15px !important;
}
.el-menu-item:first-child {
  margin-left: 15px !important;
}
.userBox {
  line-height: 64px;
  color: rgba(255, 255, 255, 0.4);
  font-size: 12px;
  cursor: pointer;
  .headerP {
    width: 20px;
    height: 20px;
    display: block;
    border-radius: 100%;
    border: 1px solid #03afff;
    margin-top: 20px;
    margin-right: 10px;
    float: left;
  }
  .goOut {
    padding: 0;
    background: none;
    border: 0;
    color: rgba(255, 255, 255, 1);
    margin-left: 5px;
    .iconfont {
      font-size: 14px;
    }
    &:hover {
      color: #03afff;
    }
  }
}

@media screen and(max-width: 900px) {
  .nav-menu {
    .nav-md {
      display: none;
    }
    .nav-mini {
      display: block;
      span {
        margin-right: -30px;
        color: #fff;
        line-height: 62px;
        float: right;
        position: relative;
        font-size: 36px;
        width: 100px;
        text-align: center;
      }
      .menu {
        display: none;
        width: 100px;
        position: absolute;
        z-index: 1000;
        background: #203160;
        padding: 10px;
        right: 190px;
        top: 50px;
      }
    }
    .nav-mini:hover {
      .menu {
        display: block;
      }
    }
  }
}
</style>
