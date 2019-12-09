     <template>
     <div>
              <el-dialog
      :title="$t('application.borrow')"
      :visible.sync="borrowDialogVisible"
      @close="propertyVisible = false"
      width="95%"
      style="width:100%"
      custom-class="customWidthFull"
    >
      <ShowBorrowForm
        ref="ShowBorrowForm"
        width="100%"
        v-bind:borrowForm="borrowForm"
      ></ShowBorrowForm>
       </el-dialog>
   

          <el-form :model="shopingCartForm" style="width:100%">
            <el-row style="width:100%">
              <div v-if="1==1">


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
                          <template v-if="item.attrName=='C_DOC_DATE'">
                            {{dateFormat(scope.row.C_DOC_DATE)}}
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


             </div>

            </el-row>
          </el-form>

          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="cancel(false)">取 消</el-button>
            <router-link  ref="borrowRouteLink" to="/borroworder"></router-link>

            <el-button @click="borrowItem()">借 阅</el-button>
          </div>
             <router-view></router-view>
 </div>
</template>


<script type="text/javascript">
 import ShowBorrowForm from "@/components/form/Borrow";
export default {
  name: "ShowProperty",
   components: {
    ShowBorrowForm:ShowBorrowForm
  },
 data() {
    return {  
             gridviewName:'shopingCartGrid',
            gridList: [],
            currentLanguage: "zh-cn",
              tabledata: [],
       loading: false,
          formLabelWidth: "100px",
      shopingCartData: [],
      selectedItemList:[],
      dialogTitle:"借阅",
       shopingCartDialogVisible: false,
          shopingCartForm: {
            taskId: 0,
            result: "在线浏览",
            message: ""

        },
      borrowData: [],
       borrowDialogVisible: false,
      borrowForm: {
        taskId: 0,
        result: "在线浏览",
        message: ""
      },

    };
  },
  created() {
     this.loadGridView()
  },
  methods: {
       loadGridView(){
        let _self = this;
        var m = new Map();
        m.set("gridName", _self.gridviewName);
        m.set("lang", _self.currentLanguage);
        axios.post("/dc/getGridViewInfo",JSON.stringify(m)).then(function(response) {
         
          _self.gridList = response.data.data;
            _self.tabledata=_self.$route.query.tabledata;
  //_self.loadData();
        });
      },
      loadData(){
          let _self = this;
          axios.post("/dc/getRelations",this.docId).then(function(response) {
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
    //获取待办任务列表，最多五条
    openShopingCart() {
      alert("1");
      let _self = this;
      var m = new Map();
      _self.loadingTodoData = true
      m.set("condition", "");
      m.set("pageSize", 7);
      m.set("pageIndex", 0);
      m.set("userId", sessionStorage.getItem("access-userName"));
      axios
        .post("/dc/openShopingCart", JSON.stringify(m))
        .then(function(response) {
          _self.dataList.todoData = response.data.data;
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
    //借阅
    borrowItem() {
      let _self = this;
      setTimeout(()=>{
            _self.$router.push({
            path:'/borrow',
            query: { tabledata: _self.selectedItemList }
          });
        },10);

    }
  }
};

</script>