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
      <ShowBorrowForm ref="ShowBorrowForm" width="100%" v-bind:borrowForm="borrowForm"></ShowBorrowForm>
    </el-dialog>

    <el-form :model="shopingCartForm" style="width:100%">
      <el-row style="width:100%">
        <div v-if="1==1">
          <el-col>
            <el-table :data="tabledata" border v-loading="loading" @selection-change="selectChange">
              <el-table-column type="selection" width="40"></el-table-column>
              <el-table-column type="index" label="#" width="50"></el-table-column>
              <el-table-column prop="id" label="id" v-if="1==2" min-width="15%" sortable></el-table-column>
              <el-table-column width="40">
                <template slot-scope="scope">
                  <img
                    v-if="scope.row.TYPE_NAME=='图册'"
                    :src="'./static/img/drawing.gif'"
                    :title="scope.row.TYPE_NAME"
                    border="0"
                  />
                  <img
                    v-else-if="scope.row.TYPE_NAME=='卷盒'"
                    :src="'./static/img/box.gif'"
                    :title="scope.row.TYPE_NAME"
                    border="0"
                  />
                  <img
                    v-else
                    :src="'./static/img/format/f_'+scope.row.FORMAT_NAME+'_16.gif'"
                    :title="scope.row.FORMAT_NAME"
                    border="0"
                  />
                </template>
              </el-table-column>>
              <template v-for="item in gridList">
                <el-table-column :key="item.id" :label="item.label" :prop="item.attrName" sortable>
                  <template slot-scope="scope">
                    <template v-if="item.attrName=='ADD_DATE'">{{dateFormat(scope.row.ADD_DATE)}}</template>
                    <template v-else>{{scope.row[item.attrName]}}</template>
                  </template>
                </el-table-column>
              </template>
              <el-table-column align="right" width="80">
                <template slot-scope="scope">
                  <el-button size="mini" @click="viewdoc(scope.row)">查看</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </div>
      </el-row>
    </el-form>

    <div slot="footer" class="dialog-footer" style="padding-top:10px">
      <router-link ref="borrowRouteLink" to="/borroworder"></router-link>
      <!-- <el-button  v-if="formId!=''" @click="addToFormFromShopingCart()" style="float:left">添加到表单</el-button> -->
      <!-- <div v-if="formId==''"> -->
        <div v-if="showFooter == true">
        <el-button type="primary" @click="cancel(false)">{{$t('application.cancel')}}</el-button>
        <el-button @click="cleanShopingCart()">清空借阅单</el-button>
        <el-button @click="removeShopingCart()">移除所选</el-button>
        <!-- <el-button @click="showDrawingItem()">调晒</el-button> -->
        <el-button @click="borrowItem()">借阅</el-button>
      </div>
    </div>
    <router-view></router-view>
  </div>
</template>


<script type="text/javascript">
import ShowBorrowForm from "@/components/form/Borrow";
export default {
  name: "ShowProperty",
  components: {
    ShowBorrowForm: ShowBorrowForm,
  },
  data() {
    return {
      gridviewName: "shopingCartGrid",
      gridList: [],
      currentLanguage: "zh-cn",
      tabledata: [],
      loading: false,
      formLabelWidth: "100px",
      shopingCartData: [],
      selectedItemList: [],
      dialogTitle: "借阅",
      shopingCartDialogVisible: false,
      shopingCartForm: {
        taskId: 0,
        result: "在线浏览",
        message: "",
      },
      borrowData: [],
      borrowDialogVisible: false,
      componentName: "shopingCart",
      borrowForm: {
        taskId: 0,
        result: "在线浏览",
        message: "",
      },
    };
  },
  props: {
    formId: { type: String, default: "" },
    excludeRows: { type: Array, default: () => [] },
    showFooter: { type: Boolean, default: true }
  },
  created() {
    let _self = this;
    _self.loadGridView();
  },
  methods: {
    //获取待办任务列表，最多五条
    openShopingCart() {
      let _self = this;
      var m = new Map();
      _self.loadingTodoData = true;
      m.set("condition", "");
      m.set("pageSize", 7);
      m.set("pageIndex", 0);
      m.set("userId", sessionStorage.getItem("access-userName"));
      //let i=0;
      axios
        .post("/dc/openShopingCart", JSON.stringify(m))
        .then(function (response) {
          _self.tabledata = response.data.data.filter(function (item) {
            let goodData = true;
            for (let i = 0; i < _self.excludeRows.length; i++) {
              if (item.ID == _self.excludeRows[i].ID) {
                goodData = false;
              }
            }
            return goodData;
          });
          _self.totalCount = response.data.totalCount;
          _self.loadingTodoData = false;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    dateFormatter(row, column) {
      let datetime = row[column.property];
      return this.datetimeFormat(datetime);
    },
    cancel(b) {
      this.$emit("showOrHiden", b);
    },
    loadGridView() {
      let _self = this;
      var m = new Map();
      m.set("gridName", _self.gridviewName);
      m.set("lang", _self.currentLanguage);
      axios
        .post("/dc/getGridViewInfo", JSON.stringify(m))
        .then(function (response) {
          _self.gridList = response.data.data;
          _self.openShopingCart();
        })
        .catch(function (error) {
          console.log(error);
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
    showDrawingItem() {
      let _self = this;
      let m = new Map();
      let C_ARCHIVE_UNIT = sessionStorage.getItem("access-department");
      var addItemId = [];
      if (_self.selectedItemList.length > 0) {
        for (var i = 0; i < _self.selectedItemList.length; i++) {
          addItemId.push(_self.selectedItemList[i].ID);
          if (
            typeof _self.selectedItemList[i].C_ARCHIVE_UNIT == "undefined" ||
            C_ARCHIVE_UNIT != _self.selectedItemList[i].C_ARCHIVE_UNIT ||
            _self.selectedItemList[i].C_SECURITY_LEVEL != "内部公开"
          ) {
            _self.$message({
              showClose: true,
              message: "只能晒本人所在部门且内部公开的图纸!",
              duration: 5000,
              type: "warning",
            });
            return;
          }
        }
        let showDrawingMap = new Map();
        showDrawingMap.set(
          "C_DRAFTER",
          sessionStorage.getItem("access-userName")
        );
        showDrawingMap.set(
          "C_DESC1",
          sessionStorage.getItem("access-department")
        );
        showDrawingMap.set("STATUS", "待晒图");
        // showDrawingMap.set(C_DRAFTER,sessionStorage.getItem("access-userName"));
        m.set("formData", showDrawingMap);
        m.set("documentIds", addItemId);
        m.set("formId", "");
        _self.loading = true;
        axios
          .post("/dc/SaveShowDrawing", JSON.stringify(m))
          .then(function (response) {
            _self.$message({
              showClose: true,
              message: self.$t('message.ShowDrawSuccess'),
              duration: 2000,
              type: "warning",
            });
            _self.loading = false;
          })
          .catch(function (error) {
            _self.$message({
              showClose: true,
              message: _self.$t('message.ShowDrawFailed'),
              duration: 2000,
              type: "warning",
            });

            _self.loading = false;
            console.log(error);
          });
      } else {
        _self.$message({
          showClose: true,
          message: 
_self.$t('message.PleaseSelectOneDraw'),
          duration: 5000,
          type: "warning",
        });
        return;
      }
    },
    //借阅
    borrowItem() {
      let _self = this;
      var addItemId = [];
      // var C_ARCHIVE_UNIT="";
      if (_self.selectedItemList.length > 0) {
        // for (var i = 0; i < _self.selectedItemList.length; i++) {
        //   if(i==0){
        //     C_ARCHIVE_UNIT=_self.selectedItemList[i].C_ARCHIVE_UNIT;
        //       if(typeof(_self.selectedItemList[i].C_ARCHIVE_UNIT)=="undefined"){
        //       _self.$message({
        //         showClose: true,
        //         message: _self.$t('message.EmptyPlaceOnFile'),
        //         duration: 5000,
        //         type: "warning"
        //       });
        //       return;
        //       }
        //   }else{
        //     if(C_ARCHIVE_UNIT!=_self.selectedItemList[i].C_ARCHIVE_UNIT){
        //       _self.$message({
        //         showClose: true,
        //         message: _self.$t('message.SameFiledUnit'),
        //         duration: 5000,
        //         type: "warning"
        //       });
        //       return;
        //     }
        //   }

        // }
        this.$emit("startBorrow", _self.selectedItemList);
      } else {
        _self.$message({
          showClose: true,
          message:_self.$t('message.PleaseSelectOneBorrow'),
          duration: 5000,
          type: "warning",
        });
        return;
      }
      // setTimeout(()=>{
      //       _self.$router.replace({
      //       path:'/borrow',
      //       query: {
      //         tabledata: _self.selectedItemList,
      //         // C_ARCHIVE_UNIT:C_ARCHIVE_UNIT
      //        }
      //     });
      //   },10);
    },

    cleanShopingCart() {
      let _self = this;
      var m = new Map();
      axios
        .post("/dc/cleanShopingCart", JSON.stringify(m))
        .then(function (response) {
          if (response.data.code == 1) {
            m = new Map();
            axios
              .post("/dc/openShopingCart", JSON.stringify(m))
              .then(function (response) {
                _self.tabledata = response.data.data;

                //_self.tabledata=excludeRows;
                _self.totalCount = response.data.totalCount;
                _self.loadingTodoData = false;
              })
              .catch(function (error) {
                console.log(error);
              });

            _self.$message({
              showClose: true,
              message:_self.$t('message.Clear'),
              duration: 2000,
              type: "success",
            });
          }
          _self.totalCount = response.data.totalCount;
          _self.loadingTodoData = false;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    removeShopingCart() {
      let _self = this;
      var m = new Map();
      var addItemId = [];
      if (_self.selectedItemList.length > 0) {
        for (var i = 0; i < _self.selectedItemList.length; i++) {
          addItemId.push(_self.selectedItemList[i].ID);
        }
      }
      if (addItemId.length == 0) {
        _self.$message({
          showClose: true,
          message:_self.$t('message.PleaseSelectOneFileRemoved'),
          duration: 2000,
          type: "warning",
        });
        return;
      }
      axios
        .post("/dc/removeShopingCart", JSON.stringify(addItemId))
        .then(function (response) {
          if (response.data.code == 1) {
            m = new Map();
            axios
              .post("/dc/openShopingCart", JSON.stringify(m))
              .then(function (response) {
                _self.tabledata = response.data.data;
                _self.totalCount = response.data.totalCount;
                _self.loadingTodoData = false;
              })
              .catch(function (error) {
                console.log(error);
              });

            _self.$message({
              showClose: true,
              message:_self.$t('message.RemoveSuccess'),
              duration: 2000,
              type: "success",
            });
          }
          _self.totalCount = response.data.totalCount;
          _self.loadingTodoData = false;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    addToFormFromShopingCart() {
      let _self = this;
      var m = new Map();
      var addItemId = [];
      if (_self.selectedItemList.length > 0) {
        for (var i = 0; i < _self.selectedItemList.length; i++) {
          addItemId.push(_self.selectedItemList[i].ID);
        }
      } else {
        _self.$message({
          showClose: true,
          message: "请勾选添加的文件!",
          duration: 2000,
          type: "success",
        });
        return;
      }
      m.set("documentIds", addItemId);
      m.set("formId", _self.formId);
      axios
        .post("/dc/addItemToForm", JSON.stringify(m))
        .then(function (response) {
          _self.formId = response.data.data;
          _self.$message({
            showClose: true,
            message:_self.$t('message.AddSuccess'),
            duration: 2000,
            type: "success",
          });
          _self.loadGridView();
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    viewdoc(indata) {
      let condition = indata.ID;
      let href = this.$router.resolve({
        path: "/viewdoc",
        query: {
          id: condition,
          //token: sessionStorage.getItem('access-token')
        },
      });
      //console.log(href);
      window.open(href.href, "_blank");
    },
  },
};
</script>