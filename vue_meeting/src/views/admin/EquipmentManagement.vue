 <template>
 <div class="table-wrapper">
   <el-button type="primary" style="margin-bottom:30px" @click="handleChange2">新增设备</el-button>
   <el-table
      :data="tableData"
      style="width: 100%"
      :span-method="arraySpanMethod"
      border
      header-row-class-name="header">
       <el-table-column
        label="会议室"
        prop="meetingRoom"
        align="center"
        >
      </el-table-column>
       <el-table-column
        label="设备"
        prop="equipment"
        align="center"
        >
       
      </el-table-column>
      <el-table-column
        label="数量"
        prop="number"
        align="center"
        >
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
          <el-form-item label="会议室" :label-width="formLabelWidth">
            <el-select v-model="form.meetingRoom" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="设备名称" :label-width="formLabelWidth">
            <el-input v-model="form.name" ></el-input>        
          </el-form-item>
          <el-form-item label="设备数量" :label-width="formLabelWidth">
            <el-input v-model="form.number" ></el-input>        
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
              totalNumber:2,
              equipment:'白板',
              number:4
            },
             {
              meetingRoom:'第一会议室',
              totalNumber:2,
              equipment:'投影仪',
              number:3
            },
            {
              meetingRoom:'第二会议室',
              totalNumber:3,
              equipment:'投影仪',
              number:2
            },
            {
              meetingRoom:'第二会议室',
              totalNumber:3,
              equipment:'投影仪',
              number:2
            },
            {
              meetingRoom:'第二会议室',
              totalNumber:3,
              equipment:'投影仪',
              number:2
            }    
           ],
          addDialogVisible:false,
          formLabelWidth: '100px', 
          form:{
            name:'',
            capacity:'',
            place:'',
            equipments:[],
            type:1
          } ,
          dialogTitle:'新增设备',
          options: [{
            value: '选项1',
            label: '第一会议室'
          }, {
            value: '选项2',
            label: '第二会议室'
          }, {
            value: '选项3',
            label: '第三会议室'
          }, {
            value: '选项4',
            label: '第四会议室'
          }, {
            value: '选项5',
            label: '第五会议室'
          }], 
          
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
        this.dialogTitle="编辑设备"
        this.addDialogVisible=true
      },
       handleChange2(){
        this.dialogTitle="新增设备"
        this.addDialogVisible=true
      },
      arraySpanMethod({ row, column, rowIndex, columnIndex }) {
        if(columnIndex===0){
          if(rowIndex===0) {
          this.prevMeetingRoom=row.meetingRoom;
            return {
              rowspan: row.totalNumber,  //要合并的行数
              colspan: 1
            };
          }else if(row.meetingRoom!==this.prevMeetingRoom){
          this.prevMeetingRoom=row.meetingRoom;
             return {
              rowspan: row.totalNumber,  //要合并的行数
              colspan: 1
            };
          }else if(row.meetingRoom===this.prevMeetingRoom){
            return {
              rowspan: 0,
              colspan: 0
            };
          }       
        }
           
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
      justify-content: center;
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
  