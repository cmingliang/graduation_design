<template>
  <div class="form-wrapper">
    <el-form ref="form" :model="form" label-width="100px">
      <el-form-item label="会议室">
        <el-select v-model="form.roomName" placeholder="请选择会议室">
          <el-option label="第一会议室" value="one"></el-option>
          <el-option label="第二会议室" value="two"></el-option>
          <el-option label="第三会议室" value="three"></el-option>
          <el-option label="第四会议室" value="four"></el-option>
          <el-option label="第五会议室" value="five"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="会议时间">
        <el-time-picker is-range v-model="form.timeRange" range-separator="-" start-placeholder="开始时间" end-placeholder="结束时间">
        </el-time-picker>
      </el-form-item>
      <el-form-item label="会议主题">
        <el-input v-model="form.title"></el-input>
      </el-form-item>
      <el-form-item label="主持人">
        <el-button type="primary" @click="personDialogVisible = true">选择</el-button>
        <span v-show="hostVisible" v-for="item in this.form.hosts" :key="item.id">{{item.label}}&nbsp;&nbsp;</span>
      </el-form-item>
      <el-form-item label="参与人">
        <el-button type="primary" @click="participantDialogVisible = true">选择</el-button>
        <span v-show="participantVisible" v-for="item in this.form.participants" :key="item.id">{{item.label}}&nbsp;&nbsp;</span>
      </el-form-item>
      <el-form-item label="会议说明">
        <el-input type="textarea" v-model="form.description"></el-input>
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
        <el-button type="primary" @click="handleService">选择</el-button>
        <span v-show="totalPrice!==0">总计：{{totalPrice}}元</span>
        <el-dialog title="会议服务" :visible.sync="dialogVisible" width="50%" center>
          <div style="display:flex">
            <div style="flex-grow:3">
              <el-tabs v-model="activeName" stretch type="card">
                <el-tab-pane label="饮品类" name="first">
                  <el-table :data="drinkData">
                    <el-table-column prop="name" label="名称">
                    </el-table-column>
                    <el-table-column prop="price" label="价格">
                    </el-table-column>
                    <el-table-column label="数量">
                      <template slot-scope="scope">
                        <el-input v-model="scope.row.number" />
                      </template>
                    </el-table-column>
                  </el-table>
                </el-tab-pane>
                <el-tab-pane label="茶歇类" name="second">
                  <el-table :data="teaData">
                    <el-table-column prop="name" label="名称">
                    </el-table-column>
                    <el-table-column prop="price" label="价格">
                    </el-table-column>
                    <el-table-column label="数量">
                      <template slot-scope="scope">
                        <el-input v-model="scope.row.number" />
                      </template>
                    </el-table-column>
                  </el-table>
                </el-tab-pane>
                <el-tab-pane label="果盘类" name="third">
                  <el-table :data="fruitData">
                    <el-table-column prop="name" label="名称">
                    </el-table-column>
                    <el-table-column prop="price" label="价格">
                    </el-table-column>
                    <el-table-column label="数量">
                      <template slot-scope="scope">
                        <el-input v-model="scope.row.number" />
                      </template>
                    </el-table-column>
                  </el-table>
                </el-tab-pane>
              </el-tabs>
            </div>
            <div style="flex-grow:2;width:200px;border:1px solid #e4e7ed">
              清单
              <ul style="padding:0;">
                <li v-for="item in tableData" :key="item.id" v-show="item.number!==undefined" style="list-style-type:none;">{{item.name}}✖️{{item.number}}&nbsp;&nbsp;&nbsp;&nbsp;¥{{item.price*item.number}}</li>
              </ul>
              <span>总计：{{totalPrice}}</span>
            </div>
          </div>
          <div slot="footer" class="add-footer">
            <el-button type="primary" @click="handlePay">确 定</el-button>
            <el-button @click="dialogVisible = false">取 消</el-button>
          </div>
        </el-dialog>
      </el-form-item>
      <el-form-item label="会议室设备">
        <span>{{form.equipments}}</span>
      </el-form-item>
      <el-form-item label="客容量">
        <span>{{form.capacity}}</span>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">预约会议</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
    <el-dialog title="参会人员" :visible.sync="personDialogVisible" width="50%" center>
      <div style="display:flex">
        <div style="flex-grow:1;border:0px solid black">
          <div>
            <el-input placeholder="输入关键字进行搜索" v-model="filterText" prefix-icon="el-icon-search" style="width:240px">
            </el-input>
            <el-tree :data="data2" show-checkbox node-key="id" ref="tree2" :filter-node-method="filterNode" @check-change="getCheckedNodes">
            </el-tree>
          </div>
        </div>
        <div style="flex-grow:1;border:0px solid black">
          <p>已选择：{{this.form.hosts.length}}人</p>
          <p v-for="item in this.form.hosts" :key="item.id">{{item.label}}</p>
        </div>
      </div>
      <div slot="footer" class="add-footer">
        <el-button type="primary" @click="handleOK">确 定</el-button>
        <el-button @click="personDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="参会人员" :visible.sync="participantDialogVisible" width="50%" center>
      <div style="display:flex">
        <div style="flex-grow:1;border:0px solid black">
          <div>
            <el-input placeholder="输入关键字进行搜索" v-model="participantText" prefix-icon="el-icon-search" style="width:240px">
            </el-input>
            <el-tree :data="data3" show-checkbox node-key="id" ref="tree3" :filter-node-method="filterNode" @check-change="getParticipantNodes">
            </el-tree>
          </div>
        </div>
        <div style="flex-grow:1;border:0px solid black">
          <p>已选择：{{this.form.participants.length}}人</p>
          <p v-for="item in this.form.participants" :key="item.id">{{item.label}}</p>
        </div>
      </div>
      <div slot="footer" class="add-footer">
        <el-button type="primary" @click="handleParOK">确 定</el-button>
        <el-button @click="participantDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getMeetingService } from '@/api/meetingService'
import { addMeeting } from '@/api/meeting'
import moment from 'moment'

export default {
  data() {
    return {
      activeName: 'first',
      form: {
        roomId: this.$store.state.dashboard.roomId,
        roomName: this.$store.state.dashboard.roomName,
        timeRange: [this.$store.state.dashboard.date.hour(this.$store.state.dashboard.time[0]).toDate(),
        this.$store.state.dashboard.date.hour(this.$store.state.dashboard.time[1]).toDate()],
        title: '',
        hosts: '1',
        participants: [1, 2],
        description: '',
        type: '',
        loop: false,
        service: '',
        equipments: this.$store.state.dashboard.equipments,
        capacity: this.$store.state.dashboard.capacity,
        createTime: new Date(),
        createBy: 'Tom'
      },
      dialogVisible: false,
      personDialogVisible: false,
      hostVisible: false,
      participantDialogVisible: false,
      participantVisible: false,
      checkedServices: [],
      tableData: [],
      filterText: '',
      participantText: '',
      data2: [{
        id: 1,
        label: '市场部',
        children: [{
          id: 4,
          label: '张三',
        }]
      }, {
        id: 2,
        label: '运营部',
        children: [{
          id: 5,
          label: '李四'
        }]
      }, {
        id: 3,
        label: '销售部',
        children: [{
          id: 6,
          label: '小李'
        },
        {
          id: 7,
          label: '王五'
        }]
      }],
      data3: [{
        id: 1,
        label: '市场部',
        children: [{
          id: 4,
          label: '张三',
        }]
      }, {
        id: 2,
        label: '运营部',
        children: [{
          id: 5,
          label: '李四'
        }]
      }, {
        id: 3,
        label: '销售部',
        children: [{
          id: 6,
          label: '小李'
        },
        {
          id: 7,
          label: '王五'
        }]
      }],
      defaultProps: {
        children: 'children',
        label: 'label'
      },

    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val);
    },
    participantText(val) {
      this.$refs.tree3.filter(val);
    }
  },
  computed: {
    drinkData: function () {
      return this.tableData.filter(item => (item.type === "饮品类"))
    },
    teaData: function () {
      return this.tableData.filter(item => (item.type === "茶歇类"))
    },
    fruitData: function () {
      return this.tableData.filter(item => (item.type === "果盘类"))
    },
    totalPrice: function () {
      return this.tableData.reduce((total, item) => { return total + (item.number !== undefined ? item.number * item.price : 0) }, 0)
    }
  },
  methods: {
    multiply(a, b) {
      return a * b
    },
    handlePay() {
      this.dialogVisible = false
      console.log(this.tableData);
    },
    handleService() {
      getMeetingService().then(
        response => {
          this.tableData = response.data
        }
      ).catch(
        error => { console.log(error); }

      )
      this.dialogVisible = true;
    },
    handleOK() {
      this.personDialogVisible = false
      this.hostVisible = true
    },
    handleParOK() {
      this.participantDialogVisible = false
      this.participantVisible = true
    },
    getCheckedNodes() {
      let total = this.$refs.tree2.getCheckedNodes();
      this.form.host = total.filter(item => (item.children === undefined))
    },
    getParticipantNodes() {
      let total = this.$refs.tree3.getCheckedNodes();
      this.form.participant = total.filter(item => (item.children === undefined))
    },
    onSubmit() {
      addMeeting({        ...this.form,
        startTime: this.form.timeRange[0],
        endTime: this.form.timeRange[1],
        service: JSON.stringify(this.tableData)      }).then(
        response => {
          this.$message({
            message: '预约成功',
            type: 'success'
          })
          this.$router.push({ path: '/' })
        }
      ).catch(
        error => { console.log(error); }
      )
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    }
  },
  mounted: function () {

  }
}
</script>

<style>
.service-btn {
  width: 60px;
  height: 20px;
  border: 0;
  font-size: 12px;
  padding: 0 0;
}
.form-wrapper {
  margin-left: 40px;
  margin-top: 60px;
  width: 900px;
}
</style>
