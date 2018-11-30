<template>
  <table class="mailTable" :style="styleObject" v-if="s_showByRow">
    <tr v-for="index in rowCount">
      <td class="column">{{tableData[index*3-3].key}}</td>
      <td class="value">{{tableData[index*3-3].value}}</td>
      <td class="column">{{tableData[index*3-2] !== undefined ? tableData[index*3-2].key : ''}}</td>
      <td class="value">{{tableData[index*3-2] !== undefined ? tableData[index*3-2].value : ''}}</td>
      <td class="column">{{tableData[index*3-1] !== undefined ? tableData[index*3-1].key : ''}}</td>
      <td class="value">{{tableData[index*3-1] !== undefined ? tableData[index*3-1].value : ''}}</td>
    </tr>
    <tr v-if="tableLangData != null">
      <td class="column">{{tableLangData.key}}</td>
      <td colspan="5" class="value">{{tableLangData.value}}</td>
    </tr>
  </table>

</template>

<script>
  export default {
    data() {
      return {
        styleObject: {},
        s_showByRow: true,
      };
    },
    props: ['tableData','tableLangData', 'tableStyle', 'showByRow'],
    computed: {
      rowCount: function() {
        return Math.ceil(this.tableData.length/3);
      }
    },
    created() {
      this.styleObject = this.tableStyle;
      if(this.showByRow !== undefined){
        this.s_showByRow = this.showByRow;
      }
    },
  }
</script>

<style scoped>
  table
  {
    border-collapse:collapse;
    color: #606266;
    font-size: 16px;
  }
  table, th, td
  {
    border: 1px solid #dcdcdc;
  }

  td{
    padding: 8px ;
    width: 23%;
  }

  table .column{
    background: #eee;
  }
  table .value{
    font-size: 14px;
  }
  .column{
    width: 10%;
  }
</style >
