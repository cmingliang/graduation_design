 <template>
 <div class="table-wrapper">
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
        label="预约日期"
        prop="date"
        align="center"
        >
      </el-table-column>
       <el-table-column
        label="预约时间"
        prop="time"
        align="center"
        >
      </el-table-column>
      <el-table-column
        label="会议主题"
        prop="title"
        align="center"
        >
      </el-table-column>
      <el-table-column
        label="预约人"
        prop="booker"
        align="center"
        >
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        width="180"
        >
        <template slot-scope="scope">
          <div v-if="tableData[scope.$index].state==='已同意'" class="btn-wrapper">
            <el-button
          type="primary"
          class="btn"
          style="background-color:rgba(60, 175, 204, 0.5);"
          disabled
          @click="centerDialogVisible=true">已批准</el-button>
          </div>
          <div v-else-if="tableData[scope.$index].state==='已拒绝'">
             <el-button
          size="mini"
          @click="handleEdit(scope.$index, scope.row)">查看</el-button>
        <el-button
          size="mini"
          type="danger"
          disabled
          @click="handleDelete(scope.$index, scope.row)">编辑</el-button>
           <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)">取消</el-button>     
          </div>  
          <div v-else-if="tableData[scope.$index].state==='审核中'" style="display:flex;justify-content:flex-start;margin: 0 10px">
           <el-button
           type="primary"
           class="btn"
          style="background-color:#3cafcc;"
          @click="okDialogVisible=true">批准</el-button>
           <el-button
           type="primary"
            class="btn"
          style="background-color:#f5856d;"
          @click="refuseDialogVisible=true">拒绝</el-button>      
          </div>
           <el-dialog
    title="会议信息"
    :visible.sync="okDialogVisible"
    width="50%"
    center>
    <div>
      <div style="display:flex;justify-content:space-between">
        <div>
          <p>会议室：{{tableData[scope.$index].meetingRoom}}</p>
          <p>会议主题：{{tableData[scope.$index].title}}</p>
        </div>
        <div>
          <p>预约日期：{{tableData[scope.$index].date}}&nbsp;&nbsp;&nbsp;{{tableData[scope.$index].time}}</p>
          <p>预约人：{{tableData[scope.$index].booker}}</p>
        </div>
      </div>
      <span>批准此会议？</span>
       <el-button type="primary" @click="okDialogVisible=false">确定</el-button>
       <el-button type="primary" @click="okDialogVisible=false">取消</el-button>
    </div>
    </el-dialog> 
    <el-dialog
    title="会议信息"
    :visible.sync="refuseDialogVisible"
    width="50%"
    center>
    <div>
      <div style="display:flex;justify-content:space-between">
        <div>
          <p>会议室：{{tableData[scope.$index].meetingRoom}}</p>
          <p>会议主题：{{tableData[scope.$index].title}}</p>
        </div>
        <div>
          <p>预约日期：{{tableData[scope.$index].date}}&nbsp;&nbsp;&nbsp;{{tableData[scope.$index].time}}</p>
          <p>预约人：{{tableData[scope.$index].booker}}</p>
        </div>
      </div>
      <span>拒绝此会议？</span>
       <el-input 
       type="textarea"
       :rows="2"
       placeholder="请输入拒绝原因"
       v-model="tableData.desc">
      </el-input>
      <el-button type="primary" @click="refuseDialogVisible=false">确定</el-button>
       <el-button type="primary" @click="refuseDialogVisible=false">取消</el-button>
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
              date:'2018-12-5',
              time:'13:00-14:00',
              title:'市场部会议',
              booker:'小乔',
              desc:'',
              state:'已同意'
            },
            {
              meetingRoom:'第2会议室',
              date:'2018-12-5',
              time:'13:00-14:00',
              title:'市场部会议',
              booker:'大乔',
              desc:'',
              state:'审核中'
            }],
          okDialogVisible:false,
          refuseDialogVisible:false
          
        }
      },
      methods: {
      handleEdit(index, row) {
        console.log(index, row);
      },
      handleDelete(index, row) {
        console.log(index, row);
      },
      handletest(index) {
        console.log(index);
      },
      handleOK() {
        this.centerDialogVisible=false;
        this.tableData[scope.$index].state==='已同意'
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
  </style>
  