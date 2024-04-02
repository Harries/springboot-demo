<template>
  <div>
    <el-container style="width: 100%;height: 100%;">
      <el-header style="height: auto; padding: 0;">
        <myHeader />
      </el-header>
    </el-container>
    <el-main>
      <div class="search-pop clearfix">
        <el-row>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <div class="grid-content">
              <el-row type="flex" justify="center">
                <el-col :xs="22" :sm="22" :md="22" :lg="22" :xl="24">
                  <div class="grid-content pd-main pt20 pb20">
                    <el-row class="row-bg">
                      <el-col type="flex" :xs="24" :sm="24" :md="6" :lg="4" :xl="4">
                        <div class="grid-content">
                          <div class="left-box">
                            <div class="project-list">
                              <MenuItem />
                            </div>
                          </div>
                        </div>
                      </el-col>
                      <el-col type="flex" :xs="24" :sm="24" :md="18" :lg="20" :xl="20">
                        <div class="grid-content">
                          <div class="right-box">
                            <div class="log-list">
                              <router-view />
                            </div>
                          </div>
                        </div>
                      </el-col>
                    </el-row>
                  </div>
                </el-col>
              </el-row>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-main>
  </div>
</template>

<script>
import myHeader from './../layout/Header'
import MenuItem from './../layout/UseMenuItem'

export default {
  name: 'UserOnline',
  components: {
    myHeader,
    MenuItem
  },
  data() {
    return {

    }
  }
}
</script>

<style scoped lang="less">
  @import "./../../assets/style/theme";
  .nav-menu {
    width: 100%;
    background: #203160;
    position: fixed;
    z-index: 10000;
    top:0;
    .logo {
      margin-top: 14px;
      width: 85px;
    }
  }
  .userBox {
    line-height: 64px;
    color: rgba(255, 255, 255, 0.4);
    font-size: 12px;
    cursor: pointer;
    .goOut {
      padding: 10px 18px;

      &:hover {
        color: #03afff;
      }
    }
  }
  .el-collapse{
    position: relative;
  }
  .el-button {
    font-size: 12px;
  }
  .left-box {
    margin-right: 15px;
    overflow: hidden;
  }
  .right-box {
    padding: 20px;
    overflow-x: hidden;
  }
  .left-box, .right-box {
    background: #ffffff;
    border: 1px solid rgba(151, 151, 151, 0.07);
    box-shadow: 0 0 10px 0 rgba(122, 177, 249, 0.4);
    border-radius: 6px;
  }
  .project-lid {
    background: #9199b1;
    height: 60px;
    padding: 0 20px;
    color: @white;
    line-height: 60px;
    font-size: @f16;
    overflow: hidden;
    label {
      color: @white;
    }
  }
  .project-list {
    line-height: 42px;
    font-size: 14px;
    color: #333;
    width: 100%;
    height: calc(100vh - 110px);
    height: -moz-calc(100vh - 110px);
    height: -webkit-calc(100vh - 110px);
    height: calc(100vh - 110px);
    overflow-y: auto;
    overflow-x: hidden;
  }
  .log-list {
    min-height: calc(100vh - 150px);
    min-height: -moz-calc(100vh - 150px);
    min-height: -webkit-calc(100vh - 150px);
    min-height: calc(100vh - 150px);
    max-height: calc(100vh - 150px);
    max-height: -moz-calc(100vh - 150px);
    max-height: -webkit-calc(100vh - 150px);
    max-height: calc(100vh - 150px);
    overflow-y: auto;
    overflow-x: hidden;
  }
  .log-repeat {
    .log-info {
      div {
        line-height: 20px;
        font-size: 13px;
        word-wrap:break-word;
        white-space:pre-wrap;
      }
    }
  }
  .el-collapse {
    border-width: 0;
  }
  .advanced-search {
    background: @white;
    border-radius: 100px;
    font-size: 13px;
    color: @ft-btn-color;
    height: 30px;
    line-height: 30px;
    padding: 0 20px;
  }
  .icon-zuidahua {
    color: @white;
    font-size: @f24;
    &:hover {
      font-size: 29px;
      color: @white;
    }
  }
  .zhiding {
    margin-top: 14px;
    font-size: @f14;
    color: #999;
    position: absolute;
    z-index: 1;
    right: 37px;
    .icon {
      color: @ft-btn-color;
    }
    .del-log{
      position: absolute;
      display: block;
      top: 0;
      right: -25px;
      color: #d8d8d8;
    }
    .del-log:hover {
      color: @pink;
    }
  }
  .amount{
    padding: 40px;
    line-height: 40px;
    strong{
      font-size: 36px;
      line-height: 40px;
      i{
        font-size: 32px;
      }
      &.yj{
        color: #DD4A68;

      }
      &.dx{
        color: #ea9015;

      }
      &.jg{
        color: #4bc70b;

      }
      &.ym{
        color: #3b89dd;

      }
    }
  }

  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }
</style>

