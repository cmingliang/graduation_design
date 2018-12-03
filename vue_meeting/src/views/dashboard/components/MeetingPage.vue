<template>
    <div class="meeting-page">
        <div class="show-state" >
            <div class="little-square square-grey"></div>
            <span class='label'>进行中</span>
            <div class="little-square square-red"></div>
            <span class='label'>审核中</span>
            <div class="little-square square-blue"></div>
            <span class='label'>已预约</span>
        </div>
        <el-pagination
        background
        layout="prev, pager, next"
        :total="totalpage"
        style="padding:0;margin-right:-5px;">
        </el-pagination>
    </div> 
</template>

<script>
  export default {
    data () {
      return {
        totalpage:100
      };
    }
  }
</script>

<style>
    .meeting-page {
        display: flex;
        justify-content: space-between;
        margin-left: 20px;
        margin-top: 30px;
    }
    .show-state {
        border:solid 1px #e6e6e6;
        border-radius:2px;
        line-height: 32px;
        vertical-align: middle;
        text-align: center;
    }
    .little-square {
        width: 14px;
        height: 14px;
        display: inline-block;
    }
    .square-grey {
        background-color:#c6c6c6;
        margin-left: 10px;
    }
    .square-red {
        background-color:#f5856d;
    }
    .square-blue {
        background-color:#3cafcc;
    }
</style>

