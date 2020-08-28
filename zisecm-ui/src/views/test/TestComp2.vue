<template>
  <div class="components-container">
    <el-dialog :title="$t('application.borrow')" width="95%" :visible.sync="borrowDialogVisible" :close-on-click-modal="false">
      <ShowShopingCart
        ref="ShowShopingCart"
        width="100%"
        v-bind:formId="formId"
        v-bind:excludeRows="tabledata"
        @startBorrow ="showBorrow"
        @showOrHiden ="closeShopcar"
      ></ShowShopingCart>
    </el-dialog>
    <el-dialog :title="$t('application.borrow')" width="95%" :visible.sync="borrowVisible" :close-on-click-modal="false">
      <borrow1
        ref="borrowItem"
        width="100%"
        :formEditPermision=1
        :borrowItemList="borrowItemList"
        @showOrHiden ="closeBorrow"
      ></borrow1>
    </el-dialog>
    <el-row>
      <el-button type="primary" plain icon="save" @click="updateDept()">更新部门</el-button>
    </el-row>
    <el-row>
      <el-input type="textarea" :rows="6" v-model="optionMessage"></el-input>
    </el-row>
    <el-row>
      <el-form label-width="120px" @submit.native.prevent>
      <el-row>
         <el-col :span="8">
           起流程
         </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="表单">
            <el-input type="text"  v-model="wfData.formId" ></el-input>
          </el-form-item>
        </el-col>
         <el-col :span="8">
           <el-button type="primary" plain icon="save" @click="deployProcess()">发布流程</el-button> 
           <el-button type="primary" plain icon="save" @click="startWorkflow('process_borrow')">启动借阅流程</el-button> 
           <el-button type="primary" plain icon="save" @click="startWorkflow('BianJiaoShenPi')">启动编校审批流程</el-button> 
           <el-button type="primary" plain icon="save" @click="startWorkflow('process_borrow')">启动借阅流程-弹出表单</el-button>
           <el-button type="primary" plain icon="save" @click="showShopCar()">借阅单</el-button> 
           <el-button type="primary" plain icon="save" @click="testWorkflow()">完成任务</el-button> 
         </el-col>
      </el-row>
    </el-form>
    </el-row>
    <split-pane split="vertical" @resize="resize">
      <template slot="paneL">
        <div class="left-container" />
      </template>
      <template slot="paneR">
        <split-pane split="horizontal">
          <template slot="paneL">
            <div class="top-container" />
          </template>
          <template slot="paneR">
            <div class="bottom-container" />
          </template>
        </split-pane>
      </template>
    </split-pane>
  </div>
</template>

<script type="text/javascript">
import ShowShopingCart from "@/components/form/ShopingCart";
import borrow1 from "@/components/form/Borrow1.vue";
export default {
  components: {
    ShowShopingCart: ShowShopingCart,
    borrow1: borrow1
  },
  name: "TestComp2",
  permit: 1,
  data() {
    return {
      loading: false,
      //以下五个参数都是借阅单用到的
      borrowDialogVisible: false,
      borrowVisible:false,
      formId:'',
      tabledata:[],
      borrowItemList:[],
      optionMessage: "",
      wfData: {
        formId: "",
      },
      formLabelWidth: "120px",
    };
  },
  methods: {
    resize() {
      console.log("resize");
    },
    showShopCar() {
      let _self = this;
      _self.borrowDialogVisible = true;
    },
    showBorrow(val){
      let _self = this
      let biObj = this.$refs.borrowItem
      if(biObj != undefined){
        _self.borrowVisible = true
        _self.borrowDialogVisible = false;
        this.$refs.borrowItem.loadData(val);
      }else{
         _self.borrowItemList = val
        _self.borrowDialogVisible = false;
        _self.borrowVisible = true
      }
      // let biObj = this.$refs.borrowItem
      // if(_self.biObj == undefined){
        
      // }
     
    },
    closeShopcar(){
      let _self = this
      _self.borrowDialogVisible = false
    },
    closeBorrow(){
      let _self = this
      _self.borrowVisible = false
    },
    updateDept() {
      let _self = this;
      _self.loading = true;
      axios
        .post("/tools/updateDepartment")
        .then(function (response) {
          _self.optionMessage = response.data.data;
          _self.loading = false;
        })
        .catch(function (error) {
          console.log(error);
          _self.loading = false;
        });
    },
    startWorkflow(processInstanceKey) {
      let _self = this;
      _self.loading = true;
      let m = new Map();
      m.set("formId", _self.wfData.formId);
      switch (processInstanceKey) {
        case "BianJiaoShenPi":
          m.set("processName", "编校审批");
          break;

        case "process_borrow":
          m.set("processName", "借阅流程");
          break;

        default:
          break;
      }
      m.set("processInstanceKey", processInstanceKey);
      axios
        .post("/workflow/startWorkflow", JSON.stringify(m))
        .then(function (response) {
          console.log(response);
          _self.loading = false;
        })
        .catch(function (error) {
          console.log(error);
          _self.loading = false;
        });
    },
    testWorkflow() {
      let _self = this;
      _self.loading = true;
      let m = new Map();
      m.set("formId", _self.wfData.formId);
      axios
        .post("/workflow/testWorkflow", JSON.stringify(m))
        .then(function (response) {
          console.log(response);
          _self.loading = false;
        })
        .catch(function (error) {
          console.log(error);
          _self.loading = false;
        });
    },
    deployProcess() {
      let _self = this;
      _self.loading = true;
      let m = new Map();
      m.set("formId", _self.wfData.formId);
      axios
        .post("/workflow/deploymentProcess", JSON.stringify(m))
        .then(function (response) {
          console.log(response);
          _self.loading = false;
        })
        .catch(function (error) {
          console.log(error);
          _self.loading = false;
        });
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1,
h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}

.components-container {
  position: relative;
  height: 100vh;
}

.left-container {
  background-color: #f38181;
  height: 100%;
}

.right-container {
  background-color: #fce38a;
  height: 200px;
}

.top-container {
  background-color: #fce38a;
  width: 100%;
  height: 100%;
}

.bottom-container {
  width: 100%;
  background-color: #95e1d3;
  height: 100%;
}
</style>
