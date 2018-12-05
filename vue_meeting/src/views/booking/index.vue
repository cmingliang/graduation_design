<template>
    <div class="form-wrapper">
        <el-form ref="form" :model="form" label-width="100px">
            <el-form-item label="会议室">
                <el-select v-model="form.meetingRoom" placeholder="请选择会议室">
                <el-option label="第一会议室" value="one"></el-option>
                <el-option label="第二会议室" value="two"></el-option>
                <el-option label="第三会议室" value="three"></el-option>
                <el-option label="第四会议室" value="four"></el-option>
                <el-option label="第五会议室" value="five"></el-option>                
                </el-select>
            </el-form-item >
            <el-form-item label="会议时间">
                <el-time-picker
                    is-range
                    v-model="form.timeRange"
                    range-separator="-"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                >
                </el-time-picker>
            </el-form-item>
            <el-form-item label="会议主题">
                <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="主持人">
                <el-input
                placeholder="请选择主持人"
                suffix-icon="el-icon-circle-plus-outline"
                v-model="form.host">
                </el-input>
            </el-form-item>
            <el-form-item label="参与人">
                <el-input
                placeholder="请选择参与人"
                suffix-icon="el-icon-circle-plus-outline"
                v-model="form.participant">
                </el-input>
            </el-form-item>
            <el-form-item label="会议说明">
                <el-input type="textarea" v-model="form.desc"></el-input>
            </el-form-item>
             <el-form-item label="会议类型">
                <el-radio-group v-model="form.type">
                <el-radio label="内部使用"></el-radio>
                <el-radio label="外部使用"></el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="循环预定">
               <el-switch v-model="form.loop"></el-switch>
            </el-form-item>
            <el-form-item label="会议服务">
               <el-button type="primary" class='service-btn' style="background-color:#f59986" @click="dialogVisible = true">茶歇类</el-button>
               <el-button type="primary" class='service-btn' style="background-color:#8ca9f4" @click="dialogVisible = true">饮料类</el-button>
               <el-button type="primary" class='service-btn' style="background-color:#f58caf" @click="dialogVisible = true">果盘类</el-button>
               <el-dialog
                title="会议服务"
                :visible.sync="dialogVisible"
                width="50%"
                :before-close="handleClose">
                <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" >
                    <el-menu-item index="1">茶歇类</el-menu-item>
                    <el-menu-item index="2">饮品类</el-menu-item>
                    <el-menu-item index="3">果盘类</el-menu-item>
                </el-menu>
                <div style="display:flex">
                    <div style="flex-grow:3;border:0px solid black">
                        <div> 
                        <el-table
                        :data="tableData"
                        >
                        <el-table-column
                            prop="name"
                            label="名称"
                            >
                        </el-table-column>
                        <el-table-column
                            prop="price"
                            label="价格"
                            >
                        </el-table-column>
                        <el-table-column
                            prop="number"
                            label="数量">
                        </el-table-column>
                        </el-table>
                        </div>
                       
                    </div>
                    <div style="flex-grow:2;border:0px solid black">清单
                        <p v-for="checkedService in checkedServices" :key="checkedService">
                            {{checkedService.name}}×{{checkedService.number}}</p></div>
                </div>
                </el-dialog>
            </el-form-item>
            <el-form-item label="会议室设备">
                <el-checkbox-group v-model="form.equipments">
                <el-checkbox v-for="equipment in form.equipments" :label="equipment" :key="equipment" disabled></el-checkbox>
                </el-checkbox-group>
            </el-form-item>
            <el-form-item label="客容量">
                <span>2000</span>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">预约会议</el-button>
                <el-button>取消</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
  export default {
    data() {
      return {
        form: {
          meetingRoom: '',
          timeRange: [new Date(2016, 9, 10, 8, 40), new Date(2016, 9, 10, 9, 40)],
          name: '',
          host: '',
          participant:'',
          desc: '',
          type:'',
          loop:false,
          equipments:["白板","投影仪"]
        },
        dialogVisible:false,
        checkedServices:[],
      }
    },
    methods: {
      onSubmit() {
        console.log('submit!');
      }
    }
  }
</script>

<style>
    .service-btn {
        width: 60px;
        height: 20px;
        border:0;
        font-size: 12px;
        padding: 0 0;
    }
    .form-wrapper {
        margin-left: 40px;
        margin-top: 60px;
        width: 900px;
    }
</style>
