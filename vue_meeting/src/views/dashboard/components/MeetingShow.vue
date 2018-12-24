<template>
  <div>
    <span class="label">显示方式</span>
    <el-radio-group v-model="radio" size="small" @change="handlechange">
      <el-radio-button label="日"></el-radio-button>
      <el-radio-button label="周"></el-radio-button>
      <el-radio-button label="月"></el-radio-button>
    </el-radio-group>
    <div>
      <el-input placeholder="请输入会议室名称" prefix-icon="el-icon-search" v-model="meetingInput" class="meeting-input">
      </el-input>
    </div>

  </div>
</template>

<script>
export default {
  data() {
    return {
      radio: '日',
      meetingInput: ''
    };
  },
  methods: {
    handlechange() {
      this.$store.commit('CHANGE_SHOWSTATE', this.radio)
    }
  }
}
</script>

<style>
.meeting-input {
  margin-top: 20px;
  width: 200px;
}
.label {
  font-size: 14px;
  color: #777777;
  margin-right: 10px;
}
</style>
