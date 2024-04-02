<template>
  <div class="markdown">
    <mavon-editor v-model="value" />
  </div>
</template>

<script>

import { readFile } from './../utils/readFile'

export default {
  props: {
    filePath: {
      type: String,
      require: true,
      default: ' '
    }
  },
  data() {
    return {
      value: '',
      newFilePath: '',
    }
  },
  watch: {
    'filePath': {
      handler() {
        this.newFilePath = 'https://captcha.anji-plus.com/' + this.filePath
        this.loadFile()
      },
      immediate: true
    }
  },
  methods: {
    loadFile() {
      readFile(this.newFilePath).then(res => {
        this.value = res.data ? res.data : res
      })
    }
  },
}
</script>

<style lang="less" scoped>
/deep/.v-note-wrapper {
    height:calc(100vh - 186px)!important;
    .v-note-op{
        display: none!important;
    }
    .v-note-edit{
        display: none!important;
    }
    .v-note-show{
        width: 100%!important;
        flex: 0 0 100%!important;
    }
}
</style>
