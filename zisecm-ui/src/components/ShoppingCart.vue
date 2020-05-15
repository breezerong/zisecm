     <template>
     <div>
         <el-row v-loading="loading">
            <el-col :span="10" class="topbar-input">
              
                    <el-input  v-model="inputkey" :placeholder="$t('message.pleaseInput')+$t('application.keyword')" @change="searchItem" prefix-icon="el-icon-search"></el-input>
                </el-col>
            
         </el-row>
        
            <DataGrid
              ref="shoppingcartDataGrid"
              key="shoppingcart"
              v-bind:isshowPage="false"
              v-bind:itemDataList="itemDataList"
              v-bind:columnList="gridList"
              @pagesizechange="handleSizeChange"
              @pagechange="handleCurrentChange"
              v-bind:itemCount="itemCount"
              v-bind:tableHeight="leftTableHeight"
              @selectchange="selectChange"
            ></DataGrid>
          <div slot="footer" class="dialog-footer" style="padding-top:10px">
             <router-link  ref="borrowRouteLink" to="/borroworder"></router-link>
           <!-- <el-button  v-if="formId!=''" @click="addToFormFromShopingCart()" style="float:left">添加到表单</el-button> -->
           <div v-if="formId==''">
            <el-button type="primary" @click="cancel(false)">取 消</el-button>
            <el-button @click="cleanShopingCart()">清空借阅单</el-button>
            <el-button @click="removeShopingCart()">移除所选</el-button>
             <el-button @click="showDrawingItem()">调晒</el-button>
           <el-button @click="borrowItem()">借阅</el-button>
           </div>
          </div>
             <router-view></router-view>
 </div>
</template>


<script type="text/javascript">
 import ShowBorrowForm from "@/components/form/Borrow";
 import DataGrid from'@/components/DataGrid'
export default {
  name: "ShowProperty",
   components: {
    ShowBorrowForm:ShowBorrowForm,
    DataGrid:DataGrid
  },
 data() {
    return {  
        gridviewName:'shopingCartGrid',
        gridList: [],
        inputkey:"",
        itemDataList:[],
        currentLanguage: "zh-cn",
        tabledata: [],
        loading: false,
        formLabelWidth: "100px",
        showFields:[],
        pageSize: 20,
        itemCount: 0,
        currentPage:1,
        selectedItems:[],
        leftTableHeight: window.innerHeight - 124,
    };
  },
  props: {
    formId: { type: String, default: "" },
    excludeRows: { type:Array, default:()=>[]},
  },
  watch:{
    
    showFields(val, oldVal){//普通的watch监听
      //console.log("a: "+val, oldVal);
      let _self = this;
      _self.gridList.forEach(element => {
        element.visibleType=2;
      });
      val.forEach(element => {
        let item = _self.getgriditem(element);
        if(item){
          //console.log(element);
          item.visibleType=1;
        } 
      }); 
    }
  },
  created() {
    let _self = this;
    _self.loadGridView();
    _self.loadGridData(null);
  },
  methods: {
      // 表格行选择
        selectChange(val) 
        {
        // console.log(JSON.stringify(val));
        this.selectedItems = val;
        },
      // 分页 页数改变
        handleSizeChange(val) 
        {
            this.pageSize = val;
            localStorage.setItem("docPageSize",val);
            this.loadGridData(this.currentFolder);
            //console.log('handleSizeChange', val);
        },
      // 分页 当前页改变
        handleCurrentChange(val) 
        {
            this.currentPage = val;
            this.loadGridData(this.currentFolder);
            //console.log('handleCurrentChange', val);
        },
      // 加载表格样式
      loadGridView(){
              
        let _self = this;
        _self.loading = true;
        var m = new Map();
        m.set('gridName',"shopingCartGrid");
        m.set('lang',_self.currentLanguage);
        _self.axios({
            headers: {
            "Content-Type": "application/json;charset=UTF-8"
            },
            method: 'post',
            data: JSON.stringify(m),
            url: "/dc/getGridViewInfo"
        })
            .then(function(response) {
            _self.showFields = [];
            _self.gridList = response.data.data;
            _self.gridList.forEach(element => {
                if(element.visibleType==1){
                _self.showFields.push(element.attrName);
                }
            });
            _self.loading = false;
            })
            .catch(function(error) {
            console.log(error);
            _self.loading = false;
            });
      },
      // 加载表格数据
    loadGridData(indata) 
    {
      let _self = this;
      
      var key =_self.inputkey;
      var m = new Map();
      if(key!=""){
        key = " and (a.coding like '%"+key+"%' or a.title like '%"+key+"%') "; 
        m.set('condition',key);
      }else{
        m.set('condition',"");
      }
      
      m.set('gridName',"shopingCartGrid");
      
      m.set('pageSize',_self.pageSize);
      m.set('pageIndex', (_self.currentPage-1)*_self.pageSize);
      m.set('orderBy','');
      // console.log('pagesize:', _self.pageSize);
      _self
      .axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: "post",
        data: JSON.stringify(m),
        url: "/dc/shopingCartDocList"
      })
      .then(function(response) {
        _self.itemDataList = response.data.data;
        _self.itemDataListFull = response.data.data;
        _self.itemCount = response.data.pager.total;
        //console.log(JSON.stringify(response.data.datalist));
        _self.loading = false;
      })
      .catch(function(error) {
        console.log(error);
        _self.loading = false;
      });
    },















     //获取待办任务列表，最多五条
    openShopingCart() {
      let _self = this;
      var m = new Map();
      _self.loadingTodoData = true
      m.set("condition", "");
      m.set("pageSize", 7);
      m.set("pageIndex", 0);
      m.set("userId", sessionStorage.getItem("access-userName"));
      //let i=0;
      axios
        .post("/dc/openShopingCart", JSON.stringify(m))
        .then(function(response) {
           _self.tabledata = response.data.data.filter(function(item){
              let goodData=true;
            for ( let i = 0; i < _self.excludeRows.length; i++) {
               if(item.ID==_self.excludeRows[i].ID){
                 goodData=false;
               }
              }
              return goodData;
             });
         _self.totalCount = response.data.totalCount;
          _self.loadingTodoData = false
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    dateFormatter(row, column) {
      let datetime = row[column.property];
      return this.datetimeFormat(datetime);
    },
    cancel(b){
      this.$emit('showOrHiden',b)
    },
      loadGridView(){
        let _self = this;
        var m = new Map();
        m.set("gridName", _self.gridviewName);
        m.set("lang", _self.currentLanguage);
       
        axios.post("/dc/getGridViewInfo",JSON.stringify(m)).then(function(response) {
          _self.gridList = response.data.data;
          _self.openShopingCart();
        }).catch(function(error) {
          console.log(error);
        });

     },
      
       showDrawingItem(){
       let _self = this;
       let m= new Map();
       let C_ARCHIVE_UNIT=sessionStorage.getItem("access-department");
                var addItemId = [];
        if (_self.selectedItems.length > 0) {
          for (var i = 0; i < _self.selectedItems.length; i++) {
                addItemId.push(_self.selectedItems[i].ID);
                if(typeof(_self.selectedItems[i].C_ARCHIVE_UNIT)=="undefined"
                ||C_ARCHIVE_UNIT!=_self.selectedItems[i].C_ARCHIVE_UNIT
                ||_self.selectedItems[i].C_SECURITY_LEVEL!='内部公开'){
                _self.$message({
                  showClose: true,
                  message: "只能晒本人所在部门且内部公开的图纸!",
                  duration: 5000,
                  type: "warning"
                });
                return;
                }
          }
          let showDrawingMap=new Map();
          showDrawingMap.set("C_DRAFTER",sessionStorage.getItem("access-userName"));
          showDrawingMap.set("C_DESC1",sessionStorage.getItem("access-department"));
          showDrawingMap.set("STATUS","待晒图");
          // showDrawingMap.set(C_DRAFTER,sessionStorage.getItem("access-userName"));
          m.set("formData",showDrawingMap);
          m.set("documentIds",addItemId);
          m.set("formId","");
          _self.loading = true;
            axios.post("/dc/SaveShowDrawing", JSON.stringify(m))
          .then(function(response) {
                _self.$message({
                showClose: true,
                message: "调晒成功",
                duration: 2000,
                type: "warning"
              });
          _self.loading = false;
          })
          .catch(function(error) {
              _self.$message({
                showClose: true,
                message: "晒图失败",
                duration: 2000,
                type: "warning"
              });

          _self.loading = false;
            console.log(error);
          });

        }else{
               _self.$message({
                showClose: true,
                message: "请选择需要调图的图纸",
                duration: 5000,
                type: "warning"
              });
              return;

      }
    },
    //借阅
    borrowItem() {
      let _self = this;
      var addItemId = [];
      var C_ARCHIVE_UNIT="";


 

      if (_self.selectedItems.length > 0) {
        for (var i = 0; i < _self.selectedItems.length; i++) {
          if(i==0){
            C_ARCHIVE_UNIT=_self.selectedItems[i].C_ARCHIVE_UNIT;
              if(typeof(_self.selectedItems[i].C_ARCHIVE_UNIT)=="undefined"){
              _self.$message({
                showClose: true,
                message: "所借阅档案，归档单位为空，不能外借!",
                duration: 5000,
                type: "warning"
              });
              return;
              }
          }else{
            if(C_ARCHIVE_UNIT!=_self.selectedItems[i].C_ARCHIVE_UNIT){
              _self.$message({
                showClose: true,
                message: "所借阅档案，归档单位只能是同一个!",
                duration: 5000,
                type: "warning"
              });
              return;
            }
          }

        }
      }else{
               _self.$message({
                showClose: true,
                message: "请选择需要借阅的档案",
                duration: 5000,
                type: "warning"
              });
              return;

      }
      setTimeout(()=>{
            _self.$router.replace({
            path:'/borrow',
            query: { 
              tabledata: _self.selectedItems,
              C_ARCHIVE_UNIT:C_ARCHIVE_UNIT
             }
          });
        },10);

    },
    
    cleanShopingCart(){
       let _self = this;
      var m = new Map();
     axios
        .post("/dc/cleanShopingCart", JSON.stringify(m))
        .then(function(response) {
          if( response.data.code==1){
            m = new Map();
              axios.post("/dc/openShopingCart", JSON.stringify(m))
              .then(function(response) {
                _self.tabledata= response.data.data;

                //_self.tabledata=excludeRows;
                _self.totalCount = response.data.totalCount;
                _self.loadingTodoData = false
              })
              .catch(function(error) {
                console.log(error);
              });

             _self.$message({
                showClose: true,
                message: "清空成功!",
                duration: 2000,
                type: "success"
              });

          }
          _self.totalCount = response.data.totalCount;
          _self.loadingTodoData = false
        })
        .catch(function(error) {
          console.log(error);
        });      
    },
        removeShopingCart(){
       let _self = this;
      var m = new Map();
      var addItemId = [];
      if (_self.selectedItems.length > 0) {
        for (var i = 0; i < _self.selectedItems.length; i++) {
          addItemId.push(_self.selectedItems[i].ID);
        }
      }
      if(addItemId.length==0){
             _self.$message({
                showClose: true,
                message: "请选择需要移除的档案",
                duration: 2000,
                type: "warning"
              });
              return;
      }
     axios
        .post("/dc/removeShopingCart", JSON.stringify(addItemId))
        .then(function(response) {
          if( response.data.code==1){
            m = new Map();
              axios.post("/dc/openShopingCart", JSON.stringify(m))
              .then(function(response) {
                _self.tabledata= response.data.data;
                _self.totalCount = response.data.totalCount;
                _self.loadingTodoData = false
              })
              .catch(function(error) {
                console.log(error);
              });

             _self.$message({
                showClose: true,
                message: "移除成功!",
                duration: 2000,
                type: "success"
              });

          }
          _self.totalCount = response.data.totalCount;
          _self.loadingTodoData = false
        })
        .catch(function(error) {
          console.log(error);
        });      
    },
    addToFormFromShopingCart(){
    let _self = this;
      var m = new Map();
      var addItemId = [];
      if (_self.selectedItems.length > 0) {
        for (var i = 0; i < _self.selectedItems.length; i++) {
          addItemId.push(_self.selectedItems[i].ID);
        }
      }
      m.set("documentIds",addItemId);
      m.set("formId",_self.formId);
       axios.post("/dc/addItemToForm",JSON.stringify(m)).then(function(response){
          _self.formId=response.data.data;
          _self.$message({
          showClose: true,
          message: "添加成功!",
          duration: 2000,
          type: "success"
          });

        })
        .catch(function(error) {
          console.log(error);
        }); 
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

  },
  
};

</script>