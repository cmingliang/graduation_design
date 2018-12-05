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
        label="管理员"
        prop="administrator"
        align="center"
        >
      </el-table-column>
      <el-table-column
        label="状态"
        prop="state"
        align="center"
        >
           
      </el-table-column>
      <el-table-column
        label="备注"
        prop="desc"
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
          style="background-color:#3ccc97;"
          @click="centerDialogVisible=true">查看</el-button>
        <el-button       
          type="primary"
          class="btn"
          style="background-color:#3cafcc;"
          @click="handleDelete(scope.$index, scope.row)">编辑</el-button>
        <el-button
          type="primary"
          disabled
          class="btn"
          style="background-color:#f5856d;"
          @click="handleDelete(scope.$index, scope.row)">取消</el-button>      
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
          <div v-else-if="tableData[scope.$index].state==='审核中'">
           <el-button
          size="mini"
          @click="centerDialogVisible=true">查看</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)">编辑</el-button>
           <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)">取消</el-button>      
          </div>
           <el-dialog
    title="会议信息"
    :visible.sync="centerDialogVisible"
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
          <p>状态：{{tableData[scope.$index].state}}</p>
        </div>
      </div>
      <div>确认参加<i class="el-icon-caret-bottom"></i></div>
      <div>请假<i class="el-icon-caret-bottom"></i></div>
      <div>未响应<i class="el-icon-caret-bottom"></i></div>
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
              administrator:'小乔',
              desc:'',
              state:'已同意'
            }],
          centerDialogVisible:false
          
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
  