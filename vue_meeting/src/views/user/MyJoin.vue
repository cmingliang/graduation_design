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
        label="开会日期"
        prop="date"
        align="center"
        >
      </el-table-column>
       <el-table-column
        label="开会时间"
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
        label="主持人"
        prop="host"
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
        label="会议说明"
        prop="desc"
        align="center"
        >
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
              host:'小乔',
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
  