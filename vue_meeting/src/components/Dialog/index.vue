<template>
  <div class="dialog" transition="fade">
    <div class="mask"></div>
    <div style="width: 25%;min-width:300px;height:100%;position: fixed;background: #fff;top:0;right: 0;z-index: 7;box-shadow: 1px 2px 10px 0 rgba(23, 21, 21,0.5);" >
      <div style="height: 60px;line-height: 60px;margin-left: 20px;font-size: 18px;font-family: 微软雅黑">
        {{ modal.title }}
      </div>
      <hr>
      <slot name="name1"></slot>
      <div style="position: absolute;width:100%;height:60px;background:#eee;bottom: 0;text-align: center;padding-top: 10px">
        <el-button type="primary"  @click="submit">{{modal.confirmButtonText  }}</el-button>
        <el-button  @click="cancel">{{ modal.cancelButtonText }}</el-button>
      </div>

    </div>
  </div>
</template>

<script>
  export default {
    name: 'dialog',
    props: {
      dialogOption: Object
    },
    data() {
      return {
        resolve: '',
        reject: '',
        promise: '', // 保存promise对象

      }
    },
    computed: {
      modal: function() {
        let options = this.dialogOption;
        return {
          title: options.title || '提示',
          cancelButtonText: options.cancelButtonText ? options.cancelButtonText : '取消',
          confirmButtonText: options.confirmButtonText ? options.confirmButtonText : '确定',
        }
      }
    },
    methods: {
      //确定,将promise断定为完成态
      submit() {
        this.$emit('submit'); // 将当前对象 evt 传递到父组件
      },
      // 取消,将promise断定为reject状态
      cancel() {
        this.$emit('cancel'); // 将当前对象 evt 传递到父组件
      },
      //显示confirm弹出,并创建promise对象，给父组件调用
      confirm() {
        this.promise = new Promise((resolve, reject) => {
          this.resolve = resolve;
          this.reject = reject;
        });
        return this.promise; //返回promise对象,给父级组件调用
      }
    }

  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  div.fade-transition{opacity:1;}       //不透明

  div.fade-enter,.fade-leave{opacity:0}   //透明
  .dialog {
    position: relative;
    .dialog-content {
    }
    .mask {
      position: fixed;
      top: 0;
      left: 0;
      bottom: 0;
      right: 0;
      z-index: 6;
      background: rgba(0, 0, 0, .5);
    }

  }
</style>
