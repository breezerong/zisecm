     <template>
     <div>
          <el-form :model="borrowForm" style="width:100%">
            <el-row style="width:100%">
              <div  v-if="(istask==1 && formEditPermision==1)||istask==0">
              <el-col >
                <el-form-item label="姓名" :label-width="formLabelWidth"  style="float:left">
                  <el-input   v-model="borrowForm.C_DRAFTER" auto-complete="off"></el-input>
                </el-form-item>
                 <el-form-item label="电话" :label-width="formLabelWidth" style="float:left">
                  <el-input   v-model="borrowForm.TITLE" auto-complete="off"></el-input>
                </el-form-item>
                 <el-form-item label="用户部门" :label-width="formLabelWidth" style="float:left">
                  <el-input   v-model="borrowForm.C_DESC1" auto-complete="off"></el-input>
                </el-form-item>
                 <el-form-item label="编制部门" :label-width="formLabelWidth" style="float:left">
                  <el-input   v-model="borrowForm.C_CREATION_UNIT" auto-complete="off"></el-input>
                </el-form-item>
                <!-- <el-form-item label="日期" :label-width="formLabelWidth" style="float:left">
                  <el-input   v-model="borrowForm." :formatter="dateFormatter" auto-complete="off"></el-input> 
                </el-form-item>
                  <el-form-item label="文件归档部门" :label-width="formLabelWidth" style="float:left">
                  <el-input   v-model="borrowForm.cCreationUnit" auto-complete="off"></el-input>
                </el-form-item>-->
               </el-col>
             <el-col>
                <el-form-item label="借阅类型" :label-width="formLabelWidth" style="float:left">
                  <el-radio-group  v-model="borrowForm.SUB_TYPE"   >
                      <el-radio-button label="在线浏览">在线浏览</el-radio-button>
                      <el-radio-button label="纸质借阅">纸质借阅</el-radio-button>
                      <el-radio-button label="下载">下载</el-radio-button>
                   </el-radio-group>
                </el-form-item>
              </el-col>
             <el-col>
            <el-form-item label="借阅开始时间" :label-width="formLabelWidth" style="float:left">
                  <el-date-picker   v-model="borrowForm.C_START_DATE" auto-complete="off"></el-date-picker >
                </el-form-item>
                 <el-form-item label="借阅结束时间" :label-width="formLabelWidth" style="float:left">
                  <el-date-picker    v-model="borrowForm.C_END_DATE" auto-complete="off"></el-date-picker >
                </el-form-item>
               </el-col>
             <el-col>
                <el-table :data="tabledata" 
                border
              v-loading="loading"
              @selection-change="selectChange">
                 <el-table-column type="selection" width="40">
                </el-table-column> 
               <el-table-column type="index" label="#" width="50">
                </el-table-column>
                <el-table-column prop="id" label="id"  v-if="1==2" min-width="15%" sortable>
                </el-table-column>
                  <template  v-for="item in gridList">
                    <el-table-column :key="item.id" :label="item.label" :prop="item.attrName">
                      <template slot-scope="scope">
                          <template v-if="item.attrName=='C_ARCHIVE_DATE'">
                            {{dateFormat(scope.row.C_ARCHIVE_DATE)}}
                          </template>
                          <template v-else>
                            {{scope.row[item.attrName]}}  
                          </template>            
                      </template>        
                    </el-table-column>
                  </template>
                  <el-table-column align="right">
                    <template slot-scope="scope">
                      <el-button size="mini" @click="viewdoc(scope.row)">查看</el-button>
                      <el-button size="mini"  @click="removeItemFromForm(scope.row)">移除</el-button>
                     </template>
                  </el-table-column>
                </el-table>
            </el-col>
           <el-col v-if="istask==1 && formEditPermision==1" slot="footer"  style="float:left;padding-top:10px;padding-bottom:10px;width:100%;height:100%">
              <el-button @click="showOrCloseShopingCart()" style="" >{{showOrCloseShopingCartLabel}}</el-button>
              <div v-if="vshowShopingCart==true">
              <ShowShopingCart
                ref="ShowShopingCart"
                width="100%"  v-bind:formId="formId" v-bind:excludeRows="tabledata"></ShowShopingCart>           
                <el-button ref="add" style="float:left"  @click="addToFormFromShopingCart()">添加到表单</el-button>
              </div>
            </el-col>
          
         <el-col style="padding-top:3px;">
                <el-form-item label="申请人领导" :label-width="formLabelWidth" style="float:left">
                 <UserSelectInput  v-model="borrowForm.C_REVIEWER1" v-bind:inputValue="borrowForm.C_REVIEWER1" roleName="审批领导"></UserSelectInput>
               </el-form-item>
                 <el-form-item label="形成部门领导" :label-width="formLabelWidth" style="float:left">
                 <UserSelectInput  v-model="borrowForm.C_REVIEWER2" v-bind:inputValue="borrowForm.C_REVIEWER2" roleName="审批领导"></UserSelectInput>
                </el-form-item>
                 <el-form-item label="分管领导" :label-width="formLabelWidth" style="float:left">
                  <UserSelectInput  v-model="borrowForm.C_REVIEWER3" v-bind:inputValue="borrowForm.C_REVIEWER3" roleName="审批领导"></UserSelectInput>
                </el-form-item>

              </el-col>
               <el-col>
                <el-form-item label="借阅目的" :label-width="formLabelWidth"  style="text-align:left">
                  <el-input type="textarea" :autosize="{minRows:3}" v-model="borrowForm.C_COMMENT" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
             </div>
             <div v-if="istask==1 && formEditPermision==0">
              <el-col>
                <el-form-item label="姓名" :label-width="formLabelWidth"  style="float:left">
                  {{borrowForm.C_DRAFTER}}
                </el-form-item>
                 <el-form-item label="电话" :label-width="formLabelWidth" style="float:left">
                  {{borrowForm.TITLE}}
                </el-form-item>
                 <el-form-item label="用户部门" :label-width="formLabelWidth" style="float:left">
                  {{borrowForm.C_DESC1}}
                </el-form-item>
                 <el-form-item label="编制部门" :label-width="formLabelWidth" style="float:left">
                  {{borrowForm.C_CREATION_UNIT}}
                </el-form-item>
                <!-- <el-form-item label="日期" :label-width="formLabelWidth" style="float:left">
                  {{borrowForm." :formatter="dateFormatter" auto-complete="off"></el-input> 
                </el-form-item>
                  <el-form-item label="文件归档部门" :label-width="formLabelWidth" style="float:left">
                  {{borrowForm.cCreationUnit" auto-complete="off"></el-input>
                </el-form-item>-->
               </el-col>
             <el-col>
                <el-form-item label="借阅类型" :label-width="formLabelWidth" style="float:left">
                   {{borrowForm.SUB_TYPE}}
                </el-form-item>
              </el-col>
             <el-col>
            <el-form-item label="借阅开始时间" :label-width="formLabelWidth" style="float:left">
                  {{dateFormat(borrowForm.C_START_DATE)}}
                </el-form-item>
                 <el-form-item label="借阅结束时间" :label-width="formLabelWidth" style="float:left">
                   {{dateFormat(borrowForm.C_START_DATE)}}
                </el-form-item>
               </el-col>
             <el-col>
                <el-table :data="tabledata">
               <el-table-column type="index" label="#" width="50">
                </el-table-column>
                <el-table-column prop="id" label="id"  v-if="1==2" min-width="15%" sortable>
                </el-table-column>
                  <template  v-for="item in gridList">
                    <el-table-column :key="item.id" :label="item.label" :prop="item.attrName">
                      <template slot-scope="scope">
                          <template v-if="item.attrName=='C_ARCHIVE_DATE'">
                            {{dateFormat(scope.row.C_ARCHIVE_DATE)}}
                          </template>
                          <template v-else>
                            {{scope.row[item.attrName]}}  
                          </template>            
                      </template>        
                    </el-table-column>
                  </template>
                  <el-table-column align="right">
                    <template slot-scope="scope">
                      <el-button size="mini" @click="viewdoc(scope.row)">查看</el-button>
                    </template>
                  </el-table-column>
                </el-table>
            </el-col>
            <el-col>
                <el-form-item label="申请人领导" :label-width="formLabelWidth" style="float:left">
                  {{borrowForm.C_REVIEWER1}}
                </el-form-item>
                 <el-form-item label="形成部门领导" :label-width="formLabelWidth" style="float:left">
                  {{borrowForm.C_REVIEWER2}}
                </el-form-item>
                 <el-form-item label="分管领导" :label-width="formLabelWidth" style="float:left">
                  {{borrowForm.C_REVIEWER3}}
                </el-form-item>

              </el-col>
               <el-col>
                <el-form-item label="借阅目的" :label-width="formLabelWidth" style="text-align:left">
                  {{borrowForm.C_COMMENT}}
                </el-form-item>
              </el-col>
             </div>
            </el-row>
          </el-form>


          <div slot="footer" class="dialog-footer" v-if="istask==false">
            <el-button ref="borrowCancel" type="primary" @click="cancel()">取 消</el-button>
            <el-button ref="borrowStartwf" @click="startWorkflow(borrowForm)">启动流程</el-button>
          </div>
        </div>
</template>


<script type="text/javascript">
import ShowShopingCart from "@/components/form/ShopingCart";
import UserSelectInput from '@/components/controls/UserSelectInput';
export default {
   components: {
     ShowShopingCart:ShowShopingCart,
     UserSelectInput:UserSelectInput
    },
    name: "BorrwoForm",
  data() {
    return {  
              gridviewName:'borrowGrid',
            gridList: [],
            currentLanguage: "zh-cn",
              tabledata: [],
     dataList: [],
      loading: false,
      formLabelWidth: "120px",
      borrowData: [],
      dialogTitle:"借阅",
      borrowDialogVisible: false,
      componentName:"borrow",
      borrowForm: {
            C_DRAFTER:"Admin",
            C_DESC1:"用户部门",
            TITLE:"13983",
            SUB_TYPE:"在线浏览",
            C_START_DATE:"2019-12-05",
            C_END_DATE:"2019-12-05",
            C_REVIEWER1:"Admin",
            C_REVIEWER2:"Admin",
            C_REVIEWER3:"Admin",
            C_COMMENT:"",
            C_CREATION_UNIT:"编制部门"

      },
      formId:"",
      istask:0,
      formEditPermision:0,
      vshowShopingCart:false,
      showOrCloseShopingCartLabel:"从购物车添加",

    };
  },
   created() {
    let _self = this;
    _self.formId=_self.$route.query.borrowFormId;
    if(typeof(_self.$route.query.istask)!="undefined"){
      _self.formEditPermision=_self.$route.query.formEditPermision;
      _self.istask=_self.$route.query.istask;
    }   
    _self.loadGridView()
  }, 
 methods: {
       loadGridView(){
        let _self = this;
        var m = new Map();
        m.set("gridName", _self.gridviewName);
        m.set("lang", _self.currentLanguage);
       axios.post("/dc/getGridViewInfo",JSON.stringify(m)).then(function(response) {
         
          _self.gridList = response.data.data;
          if(typeof(_self.formId)=="undefined"){
            _self.tabledata=_self.$route.query.tabledata;
          }else{
            _self.loadData();
          }
        });
      },
      loadData(){
          let _self = this;
          axios.post("/dc/getDocumentById",_self.formId).then(function(response) {
            let result = response.data;
            if(result.code==1){
              _self.borrowForm = result.data;
            }
          });
          axios.post("/dc/getFormRelateDocument",_self.formId).then(function(response) {
            let result = response.data;
            if(result.code==1){
              _self.tabledata = result.data;
            }
          });
      },
       selectChange(selection) {
          this.selectedItemList = [];
          if (selection.length > 0) {
            for (var i = 0; i < selection.length; i++) {
              this.selectedItemList.push(selection[i]);
            }
          }
       },
           dateFormatter(row, column) {
      let datetime = row[column.property];
      return this.datetimeFormat(datetime);
    },
     cancel(){
      this.$emit('showOrHiden',false)
    },
    startWorkflow() {
      let _self = this;
      let m = new  Map();
      m.set("formData",_self.borrowForm);

      let documentIds="";
      let fileTopestSecurityLevel="内部公开";
      let drawingNumber=_self.tabledata.length;
      for (let index = 0; index < _self.tabledata.length; index++) {
        let element = _self.tabledata[index];
        fileTopestSecurityLevel=fileTopestSecurityLevel+element.C_SECURITY_LEVEL;
        if(index==0){
         documentIds=documentIds+element.ID;
        }else{
         documentIds=documentIds+","+element.ID;     
         }
      }
      if(fileTopestSecurityLevel.indexOf("核心商密")>0){
        fileTopestSecurityLevel="核心商密";
      }else if(fileTopestSecurityLevel.indexOf("普通商密")>0){
        fileTopestSecurityLevel="普通商密";
      }else if(fileTopestSecurityLevel.indexOf("受限")>0){
        fileTopestSecurityLevel="受限";
      }else{
        fileTopestSecurityLevel="内部公开";
      }
      m.set("documentIds",documentIds);
     _self.loading = true;
     axios.post("/dc/saveBorrowForm",JSON.stringify(m)).then(function(response){
        _self.formId=response.data.data;
        console.log(response);  
        m= new Map();
        m.set("formId",_self.formId);
        m.set("fileTopestSecurityLevel",fileTopestSecurityLevel);
        m.set("drawingNumber",drawingNumber);
        
        axios.post("/workflow/startWorkflow",JSON.stringify(m)).then(function(response){
          console.log(response);  
          _self.loading = false;
          _self.cancel();
        }).catch(function(error){
          console.log(error);
          _self.loading = false;
        });
      }).catch(function(error){
        console.log(error);
        _self.loading = false;
      });
    },
    addFromShopingCart(){
       let _self = this;
      var arg = [];
        axios
          .post("/dc/openShopingCart", JSON.stringify(arg))
          .then(function(response) {
            if (response.data.code) {
              // _self.shopingCartDialogVisible = true;
              // setTimeout(()=>{
                // _self.$refs.ShopingCart.dataList = response.data.data;
                // _self.$refs.ShopingCart.dataList = response.data.data;
                // _self.$router.push({
                //   path:'/ShopingCart',
                //    query: { tabledata: response.data.data }
                // });
                if(_self.$refs.ShowShopingCart && _self.$refs.ShowShopingCart.componentName=="shopingCart"){
                   _self.$refs.ShowShopingCart.openShopingCart();
                }
              // },10);
              
              
            } else {
              _self.$message({
                showClose: true,
                message: "打开失败!",
                duration: 2000,
                type: "warning"
              });
            }
          });
    },
    showOrCloseShopingCart(){
      let _self=this;
      if(_self.vshowShopingCart==true){
        _self.vshowShopingCart=false;
        _self.showOrCloseShopingCartLabel="从购物车添加";
        }else{
          _self.vshowShopingCart=true;
          _self.showOrCloseShopingCartLabel="关闭购物车"
          if(_self.$refs.ShowShopingCart && _self.$refs.ShowShopingCart.componentName=="shopingCart"){
             
            _self.$refs.ShowShopingCart.openShopingCart();
          }      
        
        }
    },
    addToFormFromShopingCart(){
      let _self=this;
      _self.$refs.ShowShopingCart.addToFormFromShopingCart();
      setTimeout(()=>{
        axios.post("/dc/getFormRelateDocument",_self.formId).then(function(response) {
            let result = response.data;
            if(result.code==1){
              _self.tabledata = result.data;
            }
      });
      },2500);
      },
      
      removeItemFromForm(varArg){
          let _self=this;
        if(typeof(_self.formId)=="undefined"   ||_self.formId==""){
          let a=[];
           a = _self.tabledata.filter(function(item){
              return item.ID!=varArg.ID;
             });
           _self.tabledata=a;

        }else{
            let _self=this;
            let m = new Map();
            m.set("relateId",varArg.RELATE_ID);
            axios.post("/dc/removeItemFromForm",m).then(function(response) {
              let result = response.data;
              if(result.code==1){
                _self.$message({
                  showClose: true,
                  message: "操作成功!",
                  duration: 2000,
                  type: "success"
                });
              }
        });
            setTimeout(()=>{
          axios.post("/dc/getFormRelateDocument",_self.formId).then(function(response) {
              let result = response.data;
              if(result.code==1){
                _self.tabledata = result.data;
              }
        });
        },1000);

      }

  },
      viewdoc(indata){
        let condition = indata.ID;
     let href = this.$router.resolve({
        path: '/viewdoc',
        query: {
          id: condition
          //token: sessionStorage.getItem('access-token')
        }
      });
      //console.log(href);
      window.open(href.href, '_blank');
    },




  }
};

</script>