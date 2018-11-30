<template>
  <el-select v-model="svalue" placeholder="请选择">
    <el-option
      v-for="item in options"
      :key="item.key"
      :label="item.value"
      :value="item.key">
    </el-option>
  </el-select>
</template>

<script>
  import store from '@/store'
  import {loadSelectOption} from '@/api/selectOption'
    export default {
      name: "DynamicSelect",
      model: {    // 使用model， 这儿2个属性，prop属性说，我要将msg作为该组件被使用时（此处为aa组件被父组件调用）v-model能取到的值，event说，我emit ‘cc’ 的时候，参数的值就是父组件v-model收到的值。
        prop: 'svalue',
        event: 'cc'
      },
      data(){
        return {
          options:[],
          svalue:''
        }
      },
      watch:{
        //判断下拉框的值是否有改变
        svalue(val, oldVal) {
          if(val!=oldVal){
            this.$emit('change', this.svalue);
          }
          this.$emit('cc', val)
        },
        defaultValue(val, oldVal){
          this.svalue= val;
        }
      },
      props: {
        optionName:{
          type:String
        },
        addAllOption:{//是否添加'全部'选项
          type:String
        },
        defaultValue:''
      },
      mounted(){
        this.loadOptionData();
      },
      methods:{
        loadOptionData(){
            loadSelectOption(this.optionName).then((response)=>{
              this.options = response.data.data;
              if(this.addAllOption == "true"){
                this.options = [{key:"",value:"全部"}].concat(this.options);
              }
            });
        }

      }


    }
</script>

<style scoped>

</style>
