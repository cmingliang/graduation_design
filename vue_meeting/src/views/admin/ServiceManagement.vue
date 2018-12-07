 <template>
 <div class="table-wrapper">
   <el-button type="primary" style="margin-bottom:30px" @click="handleAddService">新增服务</el-button>
   <el-table
      :data="tableData"
      style="width: 100%"
      :span-method="arraySpanMethod"
      border
      header-row-class-name="header">
       <el-table-column
        label="类别"
        prop="type"
        align="center"
        >
      </el-table-column>
       <el-table-column
        label="服务内容"
        prop="name"
        align="center"
        >
       
      </el-table-column>
      <el-table-column
        label="价格"
        prop="price"
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
          
      </template>
      </el-table-column>
    </el-table>
    <el-dialog
          :modal=false
          :title="dialogTitle"
          :visible.sync="addDialogVisible"
          fullscreen
          class="addDialog">
          <el-form :model="form" label-position="left">
          <el-form-item label="服务类型" :label-width="formLabelWidth">
            <el-select v-model="form.type" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="服务内容" :label-width="formLabelWidth">
            <el-input v-model="form.name" ></el-input>        
          </el-form-item>
          <el-form-item label="服务价格" :label-width="formLabelWidth">
            <el-input v-model="form.price" ></el-input>        
          </el-form-item>
          </el-form>
          <div slot="footer" class="add-footer">
          <el-button type="primary" @click="addDialogVisible = false">确 定</el-button>
          <el-button @click="addDialogVisible = false">取 消</el-button>       
          </div>
          </el-dialog>
 </div>
    
  </template>

  <script>
    export default {
      data() {
        return {
          tableData: [
            {
              type:'饮品类',
              totalNumber:2,
              name:'康师傅矿泉水',
              price:3
            },
            {
              type:'饮品类',
              totalNumber:2,
              name:'农夫山泉矿泉水',
              price:4
            },
            {
              type:'茶歇类',
              totalNumber:4,
              name:'红茶',
              price:5
            },
            {
              type:'茶歇类',
              totalNumber:4,
              name:'绿茶',
              price:5
            },
            {
              type:'茶歇类',
              totalNumber:4,
              name:'乌龙茶',
              price:3
            },
            {
              type:'茶歇类',
              totalNumber:4,
              name:'奶茶',
              price:3
            }        
           ],
          addDialogVisible:false,
          formLabelWidth: '100px', 
          form:{
            name:'',
            price:'',
            type:''
          } ,
          dialogTitle:'新增服务',
          options: [{
            value: '选项1',
            label: '果盘类'
          }, {
            value: '选项2',
            label: '茶歇类'
          }, {
            value: '选项3',
            label: '饮品类'
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
        this.dialogTitle="编辑服务"
        this.addDialogVisible=true
      },
      handleAddService(){
        this.dialogTitle="新增服务"
        this.addDialogVisible=true
      },
      arraySpanMethod({ row, column, rowIndex, columnIndex }) {
        if(columnIndex===0){
          if(rowIndex===0) {
          this.prevService=row.type;
            return {
              rowspan: row.totalNumber,  //要合并的行数
              colspan: 1
            };
          }else if(row.type!==this.prevService){
          this.prevService=row.type;
             return {
              rowspan: row.totalNumber,  //要合并的行数
              colspan: 1
            };
          }else {
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
  