     <template>
     <div>
              <el-dialog
      :title="$t('application.borrow')"
      :visible.sync="borrowDialogVisible"
      @close="propertyVisible = false"
      width="80%"
      style="width:100%"
      custom-class="customWidthFull"
    >
      <ShowBorrowForm
        ref="ShowBorrowForm"
        @onSaved="onSaved"
        width="100%"
        v-bind:borrowForm="borrowForm"
      ></ShowBorrowForm>
       </el-dialog>
   

          <el-form :model="shopingCartForm" style="width:100%">
            <el-row style="width:100%">
              <div v-if="1==1">


             <el-col>
            <el-table
              :data="dataList"
              border
              v-loading="loading"
              @selection-change="selectChange"  >
                <el-table-column type="selection" width="40">
                </el-table-column> 
                <el-table-column type="index" label="#" width="50">
                </el-table-column>
                <el-table-column prop="name" label="提名" min-width="15%" sortable>
                </el-table-column>
                <el-table-column prop="coding" label="文号"   min-width="10%" sortable>
                </el-table-column>
                <el-table-column prop="securityLevel" label="密级" min-width="10%" sortable>
                </el-table-column>
                <el-table-column prop="creationDate" label="编制日期" :formatter="dateFormatter" min-width="10%" sortable>
                </el-table-column>
                <el-table-column label="操作"  width="80">
                  <template slot-scope="scope">
                    <el-button :plain="true" type="success" size="small" icon="save" @click="showitem(scope.row)">查看</el-button>
                  </template>
                </el-table-column>
        </el-table>
            </el-col>


             </div>

            </el-row>
          </el-form>


          <div slot="footer" class="dialog-footer">
            <el-button @click="shopingCartDialogVisible = false">取 消</el-button>
            <router-link to="/borroworder">借阅</router-link>
            <el-button @click="borrowItem()">借 阅</el-button>
          </div>
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
       dataList: [],
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
    let _self = this;
  },
  methods: {
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
    //借阅
    borrowItem() {
      let _self = this;
      var obtainItemId = [];
      if (this.selectedItemList.length > 0) {
        for (var i = 0; i < this.selectedItemList.length; i++) {
          obtainItemId.push(this.selectedItemList[i].ID);
        }
      }
       _self.borrowDialogVisible = true;
    }

  }
};

</script>