<template>
  <div class="datefilter">
    <span class="label">客容量</span>
    <el-select v-model="capacity" placeholder="客容量" class="capacity" @change="handleCapacity" clearable>
      <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
      </el-option>
    </el-select>
    <span class="label">日期</span>
    <el-date-picker type="date" placeholder="选择日期" v-model="dateValue" class="date" @change="handleChange">
    </el-date-picker>
    <div class='equipment-container'>
      <span class="label">设备：</span>
      <el-checkbox-group v-model="checkedEquipment" class="equipment" @change="handleEquipments">
        <el-checkbox v-for="equipment in equipments" :label="equipment" :key="equipment">{{equipment}}</el-checkbox>
      </el-checkbox-group>
    </div>
  </div>

</template>
<script>
import moment from 'moment'

export default {
  name: 'MeetingRoomFilter',
  data() {
    return {
      options: [{
        value: 10,
        label: '10'
      }, {
        value: 20,
        label: '20'
      }, {
        value: 50,
        label: '50'
      }, {
        value: 100,
        label: '100'
      }],
      capacity: '',
      dateValue: this.$store.state.dashboard.date,
      checkedEquipment: [],
      equipments: ['白板', '远程视频', '投影', 'LED', '等离子']
    }
  },
  methods: {
    handleChange() {
      this.$store.commit("CHANGE_DATE", moment(this.dateValue))
    },
    handleCapacity() {
      this.$store.commit("CHANGE_ROOMFILTER", { capacity: this.capacity })
    },
    handleEquipments() {
      this.$store.commit("CHANGE_ROOMFILTER", { equipments: this.checkedEquipment })
    }
  }
}

</script>

<style scoped>
.label {
  font-size: 14px;
  color: #777777;
  margin-left: 10px;
}
.datefilter {
  margin-left: 0px;
}
.equipment-container {
  display: flex;
  margin-top: 25px;
}
</style>

