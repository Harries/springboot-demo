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
          <el-col
            :xs="24"
            :sm="24"
            :md="24"
            :lg="24"
            :xl="24"
          >
            <div class="grid-content">
              <el-row
                type="flex"
                justify="center"
              >
                <el-col
                  :xs="24"
                  :sm="24"
                  :md="24"
                  :lg="24"
                  :xl="24"
                >
                  <div class="grid-content pd-main pt20 pb20">
                    <el-row class="row-bg">
                      <el-col
                        :xs="24"
                        :sm="24"
                        :md="24"
                        :lg="24"
                        :xl="24"
                      >
                        <div class="grid-content">
                          <div class="right-box">
                            <div class="log-list">
                              <div class="title">
                                <h2>WHO IS<span> USING </span> US</h2>
                                <h4>谁在使用</h4>
                              </div>
                              <el-button
                                type="primary"
                                round
                                class="push-logo"
                                @click="addLogo"
                              >我要提交</el-button>
                              <div class="apply-logo">
                                <el-row>
                                  <el-col
                                    :span="22"
                                    :offset="1"
                                  >
                                    <li><img
                                      src="../../../static/9.png"
                                      width="100%"
                                    ></li>
                                    <li><img
                                      src="../../../static/7.png"
                                      width="100%"
                                    ></li>
                                    <li><img
                                      src="../../../static/3.png"
                                      width="100%"
                                    ></li>
                                    <li><img
                                      src="../../../static/11.png"
                                      width="100%"
                                    ></li>
                                    <li><img
                                      src="../../../static/12.png"
                                      width="100%"
                                    ></li>

                                    <li><img
                                      src="../../../static/5.png"
                                      width="100%"
                                    ></li>
                                    <li><img
                                      src="../../../static/1.png"
                                      width="100%"
                                    ></li>
                                    <li><img
                                      src="../../../static/2.png"
                                      width="100%"
                                    ></li>
                                    <li><img
                                      src="../../../static/4.png"
                                      width="100%"
                                    ></li>
                                    <li>安吉智行TMS</li>

                                    <li><img
                                      src="../../../static/10.png"
                                      width="100%"
                                    ></li>
                                    <li>上汽红岩VLS</li>
                                    <li><img
                                      src="../../../static/8.png"
                                      width="100%"
                                    ></li>
                                    <li><img
                                      src="../../../static/6.png"
                                      width="100%"
                                    ></li>
                                    <li style="background:rgb(121 143 222)">智行库位专家</li>

                                    <li><img
                                      src="../../../static/13.png"
                                      width="100%"
                                    ></li>
                                    <li><img
                                      src="../../../static/15.png"
                                      width="100%"
                                    ></li>
                                    <li><img
                                      src="../../../static/14.png"
                                      width="100%"
                                    ></li>
                                    <li><img
                                      src="../../../static/16.png"
                                      width="100%"
                                    ></li>
                                  </el-col>
                                </el-row>
                              </div>
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
  },
  methods: {
    addLogo() {
      const html = '<div>我们希望您能提供的信息</div>' +
        '<div>您所在公司、学校或组织和首页，公司logo</div>' +
        '<div>您的联系方式: 微博、邮箱、微信 (至少一个)</div>' +
        '<div>您将AJ-Captcha用于哪些业务场景</div>' +
        '<div>有何改进优化建议</div>' +
        '<div>将以上信息发送到：MS@anji-plus.com</div>' +
        '<a href="https://gitee.com/anji-plus/captcha/issues/I2ACRL" style="color:#03afff" target="_blank">了解更多</a>'
      this.$alert(html, '我要提交', {
        dangerouslyUseHTMLString: true,
        confirmButtonText: '确定',
        callback: action => {
          window.open('mailto:MS@anji-plus.com&Subject=我要提交宣传栏&Body=' + html)
        }
      })
    }
  },
}
</script>

<style scoped lang="less">
@import "./../../assets/style/theme";
.nav-menu {
  width: 100%;
  background: #203160;
  position: fixed;
  z-index: 10000;
  top: 0;
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
.el-button {
  font-size: 12px;
}
.right-box {
  padding: 20px;
  overflow-x: hidden;
}
.right-box {
  background: #ffffff;
  border: 1px solid rgba(151, 151, 151, 0.07);
  box-shadow: 0 0 10px 0 rgba(122, 177, 249, 0.4);
  border-radius: 6px;
}
.log-list {
  min-height: calc(100vh - 225px);
  min-height: -moz-calc(100vh - 225px);
  min-height: -webkit-calc(100vh - 225px);
  min-height: calc(100vh - 225px);
  max-height: calc(100vh - 225px);
  max-height: -moz-calc(100vh - 225px);
  max-height: -webkit-calc(100vh - 225px);
  max-height: calc(100vh - 225px);
  overflow-y: auto;
  overflow-x: hidden;
}
.title {
  text-align: center;
  h2 {
    font-size: 32px;
    line-height: 50px;
    font-weight: bold;
    span {
      color: #03afff;
      font-weight: bold;
    }
  }
  h4 {
    font-size: 24px;
    line-height: 52px;
  }
  border-bottom: 60px solid #edf4f7;
}
.apply-logo {
  width: 100%;
  display: block;
  margin-top: 20px;
  li {
    width: 15.4vw;
    height: 100px;
    float: left;
    display: block;
    border: 1px solid #efefef;
    margin-bottom: 10px;
    overflow: hidden;
    font-size: 28px;
    text-align: center;
    line-height: 100px;
    margin-right: 10px;
    img {
      height: 100%;
    }
  }
}
.push-logo {
  float: right;
  margin-top: -1px;
  display: block;
  margin-top: -45px;
  margin-right: 10px;
}
</style>

