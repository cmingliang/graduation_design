 <template>
 <div class="table-wrapper">
   <el-button type="primary" style="margin-bottom:30px" @click="handleChange2">新增会议室</el-button>
   <el-table
      :data="tableData"
      style="width: 100%"
      border
      header-row-class-name="header">
       <el-table-column
        label="会议室"
        prop="meetingRoom"
        align="center"
        >
      </el-table-column>
       <el-table-column
        label="客容量"
        prop="capacity"
        align="center"
        >
      </el-table-column>
       <el-table-column
        label="设备"
        prop="equipments"
        align="center"
        >
        <template slot-scope="scope">         
         <span>{{tableData[scope.$index].equipments.join()}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="类型"
        prop="type"
        align="center"
        >
      </el-table-column>
      <el-table-column
        label="会议室状态"
        prop="status"
        align="center"
        >
        <template slot-scope="scope">         
         <el-switch
          v-model="tableData[scope.$index].status"
          active-color="#3cafcc"
          inactive-color="#d0cfcf">
        </el-switch>
        </template>
      </el-table-column>
      <el-table-column
        label="自动审核"
        prop="auto"
        align="center"
        >
        <template slot-scope="scope">         
         <el-switch
          v-model="tableData[scope.$index].status"
          active-color="#3cafcc"
          inactive-color="#d0cfcf">
        </el-switch>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
      >
        <template slot-scope="scope">
          <div class="btn-wrapper">
            <el-button
          type="primary"
          class="btn"
          style="background-color:#3cafcc;"
          @click="handleChange1"
          >编辑</el-button>
          <el-button
          type="primary"
          class="btn"
          style="background-color:#f5856d;"
          >删除</el-button>
          </div>
          <el-dialog
          :modal=false
          :title="dialogTitle"
          :visible.sync="addDialogVisible"
          fullscreen
          class="addDialog">
          <el-form :model="form" label-position="left">
          <el-form-item label="会议室名称" :label-width="formLabelWidth">
            <el-input v-model="form.name" ></el-input>
          </el-form-item>
          <el-form-item label="客容量" :label-width="formLabelWidth">
            <el-input v-model="form.capacity" ></el-input>        
          </el-form-item>
          <el-form-item label="地点" :label-width="formLabelWidth">
            <el-input v-model="form.place" ></el-input>        
          </el-form-item>
          <el-form-item label="设备">
             <el-checkbox-group v-model="form.equipments">
            <el-checkbox label="白板"></el-checkbox>
            <el-checkbox label="远程视频"></el-checkbox>
            <el-checkbox label="投影仪"></el-checkbox>
            <el-checkbox label="麦克风"></el-checkbox>
          </el-checkbox-group>
          </el-form-item>
          <el-form-item label="类型">
             <el-radio-group v-model="form.type">
            <el-radio :label="1">小型</el-radio>
            <el-radio :label="2">中型</el-radio>
            <el-radio :label="3">大型</el-radio>
            </el-radio-group>
          </el-form-item>     
        </el-form>
        <div slot="footer" class="add-footer">
        <el-button type="primary" @click="addDialogVisible = false">确 定</el-button>
        <el-button @click="addDialogVisible = false">取 消</el-button>       
        </div>
          </el-dialog>
      </template>
      </el-table-column>
    </el-table>
   
 </div>
    
  </template>

  <script>
    export default {
      data() {
        return {
          tableData: [
            {
              meetingRoom:'第一会议室',
              capacity:100,
              equipments:['白板','投影仪'],
              type:'小型',
              status:true,
              auto:true             
            },
            {
              meetingRoom:'第二会议室',
              capacity:200,
              equipments:['白板','投影仪','远程视频'],
              type:'大型',
              status:false,
              auto:false             
            }],
          addDialogVisible:false,
          formLabelWidth: '100px', 
          form:{
            name:'',
            capacity:'',
            place:'',
            equipments:[],
            type:1
          } ,
          dialogTitle:'新增会议室'   
        }
      },
      // computed:{
      //   totalEquipments: function () {     
      //     return this.tableData.equipments.join('、')
      //   }
      // },
      methods: {
      handleEdit(index, row) {
        console.log(index, row);
      },
      handleDelete(index, row) {
        console.log(index, row);
      },
      handleChange1(){
        this.dialogTitle="编辑会议室"
        this.addDialogVisible=true
      },
       handleChange2(){
        this.dialogTitle="新增会议室"
        this.addDialogVisible=true
      }
     
      }
    }
  </script>

  <style>
    .header {
      background-color: #f2f2f2 !important;
      color:#666666;
    }
    .table-wrapper {
      margin: 60px 40px;
    }
    .btn{
      width: 45px;
      height: 20px;
      border:0;
      font-size: 12px;
      padding: 0 0;
    }
    .btn-wrapper {
      display: flex;
      justify-content: space-between;
      margin: 0 10px ;
    }
    .addDialog {
      height: 92%;
      width: 30%;
      position: fixed;
      right: 0;
      left:auto !important;
      top:80px;
      box-shadow: -5px 0px 5px 0px 
		rgba(16, 5, 10, 0.1);
    }
    .add-footer {
      display: flex;
      justify-content: space-around;
    }
  </style>
  