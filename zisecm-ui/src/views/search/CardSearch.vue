<template>
  <div>
    <!-- 选字段对话框 -->
    <el-dialog
      title="选择需要展示的字段"
      :visible.sync="columnsInfo.dialogFormVisible"
      width="40%"
      center
      top="15vh"
    >
      <el-checkbox
        :indeterminate="columnsInfo.isIndeterminate"
        v-model="columnsInfo.checkAll"
        @change="handleCheckAllChange"
      >全选</el-checkbox>
      <div style="margin: 15px 0;"></div>
      <el-checkbox-group v-model="showFields" @change="handleCheckedColsChange">
        <el-checkbox
          v-for="item in gridList"
          :label="item.attrName"
          :key="item.attrName"
        >{{item.label}}</el-checkbox>
      </el-checkbox-group>
      <div slot="footer" class="dialog-footer">
        <el-button @click="columnsInfo.dialogFormVisible=false" size="medium">取 消</el-button>
        <el-button type="primary" @click="confirmShow" size="medium">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :title="$t('application.property')"
      :visible.sync="propertyVisible"
      @close="propertyVisible = false"
      width="90%"
    >
      <ShowProperty ref="ShowProperty" width="100%" v-bind:itemId="selectedItemId"></ShowProperty>
      <div slot="footer" class="dialog-footer">
        <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>
    <div class="navbar">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>{{$t('menu.searchCenter')}}</el-breadcrumb-item>
        <el-breadcrumb-item>{{$t('menu.cardSearch')}}</el-breadcrumb-item>
        <el-breadcrumb-item>{{currentCard.label}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div>
      <el-form label-position="right" label-width="120px">
        <div v-for="(subitem,sidx) in formItemList" :key="sidx+'_S'">
          <el-col :span="8" style="align:left;">
            <el-form-item :label="subitem.label">
              <div v-if="subitem.controlType === 'TextBox'">
                <el-input style="width:18em" v-model="subitem.defaultValue"></el-input>
              </div>
              <div v-if="subitem.controlType=='Integer'">
                <el-input style="width:18em" type="number" v-model.number="subitem.defaultValue"></el-input>
              </div>
              <div v-if="subitem.controlType=='Boolean'">
                <el-checkbox style="width:18em" v-model="subitem.defaultValue"></el-checkbox>
              </div>
              <div v-else-if="subitem.controlType === 'TextArea'">
                <el-input style="width:18em" type="textarea" v-model="subitem.defaultValue"></el-input>
              </div>
              <div v-else-if="subitem.controlType ==='Date'">
                <el-date-picker
                  style="width:18em"
                  v-model="subitem.defaultValue"
                  type="date"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  :placeholder="$t('application.selectDate')"
                ></el-date-picker>
              </div>
              <div
                v-else-if="subitem.controlType === 'Select'|| subitem.controlType=='Department'
                      || subitem.controlType=='SQLSelect'"
              >
                <el-select style="width:18em" v-model="subitem.defaultValue">
                  <el-option label value style="width:230px"></el-option>
                  <div v-for="(name,vidx) in subitem.validValues" :key="vidx+'_V'">
                    <el-option :label="name" :value="name" style="width:230px"></el-option>
                  </div>
                </el-select>
              </div>
            </el-form-item>
          </el-col>
        </div>
        <el-col :span="8" style="align:left;padding-top: 5px;">
          <el-button
            type="primary"
            size="small"
            icon="el-icon-search"
            @click="startSearch()"
          >{{$t('application.search')}}</el-button>
          <span>&nbsp; &nbsp; &nbsp; &nbsp;</span>
        </el-col>
      </el-form>
    </div>
    <div>
      <el-table
        :height="tableHeight"
        :data="itemDataList"
        border
        v-loading="loading"
        style="width: 100%"
        @header-dragend="onHeaderDragend"
      >
        <el-table-column :label="$t('field.indexNumber')" width="70">
          <template slot-scope="scope">
            <span>{{(currentPage-1) * pageSize + scope.$index+1}}</span>
          </template>
        </el-table-column>
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
              :title="scope.row.FORMAT_NAME"
              :src="'./static/img/format/f_'+scope.row.FORMAT_NAME+'_16.gif'"
              border="0"
            />
          </template>
        </el-table-column>
        <div v-for="(citem,idx) in gridList" :key="idx+'_C'">
          <div v-if="citem.visibleType==1">
            <div v-if="(citem.width+'').indexOf('%')>0">
              <el-table-column
                :label="citem.label"
                :prop="citem.attrName"
                :min-width="citem.width"
                :sortable="citem.allowOrderby"
              >
                <template slot-scope="scope">
                  <div v-if="citem.attrName.indexOf('DATE')>0">
                    <span>{{dateFormat(scope.row[citem.attrName])}}</span>
                  </div>
                  <div v-else>
                    <span>{{scope.row[citem.attrName]}}</span>
                  </div>
                </template>
              </el-table-column>
            </div>
            <div v-else>
              <el-table-column
                :label="citem.label"
                :width="citem.width"
                :prop="citem.attrName"
                :sortable="citem.allowOrderby"
              >
                <template slot-scope="scope">
                  <div v-if="citem.attrName.indexOf('DATE')>0">
                    <span>{{dateFormat(scope.row[citem.attrName])}}</span>
                  </div>
                  <div v-else>
                    <span>{{scope.row[citem.attrName]}}</span>
                  </div>
                </template>
              </el-table-column>
            </div>
          </div>
        </div>
        <el-table-column :label="$t('application.operation')" width="160">
          <template slot-scope="scope">
            <el-button
              type="primary"
              plain
              size="small"
              :title="$t('application.viewContent')"
              icon="el-icon-picture-outline"
              @click="showItemContent(scope.row)"
            ></el-button>
            <el-button
              type="primary"
              plain
              size="small"
              :title="$t('application.viewProperty')"
              icon="el-icon-info"
              @click="showItemProperty(scope.row)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 50, 100, 200]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="itemCount"
      ></el-pagination>
    </div>
  </div>
</template>

<script type="text/javascript">
import ShowProperty from "@/components/ShowProperty";

export default {
  name: "CardSearch",
  components: {
    ShowProperty: ShowProperty
  },
  permit: 1,
  data() {
    return {
      columnsInfo: {
        checkAll: true,
        checkedCities: [],
        temCol: [],
        dialogFormVisible: false,
        isIndeterminate: false
      },
      showFields: [],
      itemDataList: [],
      gridList: [],
      inputkey: "",
      pageSize: 20,
      itemCount: 0,
      cardId: "",
      formItemList: [],
      currentCard: "",
      currentPage: 1,
      loading: false,
      dialogVisible: false,
      selectedItemId: "",
      propertyVisible: false,
      tableHeight: window.innerHeight - 216,
      formLabelWidth: "120px"
    };
  },
  created() {
    let _self = this;
    var psize = localStorage.getItem("cardSearchPageSize");
    if (psize) {
      _self.pageSize = parseInt(psize);
    }
    _self.cardId = _self.$route.query.id;
    _self.loadCard();
  },
  methods: {
    // 查看属性
    showItemProperty(indata) {
      let _self = this;
      _self.selectedItemId = indata.ID;
      _self.propertyVisible = true;
      if (_self.$refs.ShowProperty) {
        _self.$refs.ShowProperty.myItemId = indata.ID;
        _self.$refs.ShowProperty.loadFormInfo();
      }
    },
    // 查看内容
    showItemContent(indata) {
      let condition = indata.ID;
      let href = this.$router.resolve({
        path: "/viewdoc",
        query: {
          id: condition
          //token: sessionStorage.getItem('access-token')
        }
      });
      //console.log(href);
      window.open(href.href, "_blank");
    },
    // 分页 页数改变
    handleSizeChange(val) {
      this.pageSize = val;
      var maxPage = Math.floor(this.itemCount / val) + 1;
      this.currentPage =
        this.currentPage > maxPage ? maxPage : this.currentPage;
      localStorage.setItem("cardSearchPageSize", val);
      this.search();
      //console.log('handleSizeChange', val);
    },
    // 分页 当前页改变
    handleCurrentChange(val) {
      this.currentPage = val;
      this.search();
    },

    loadCard() {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("lang", _self.getLang());
      m.set("id", _self.cardId);
      //alert(_self.parentid);
      axios
        .post("/search/getCardSearch", JSON.stringify(m))
        .then(function(response) {
          _self.currentCard = response.data.data;
          //console.log(JSON.stringify(_self.currentCard));
          _self.loadFormItem();
          _self.loadGridInfo(_self.currentCard.gridView);
          _self.tableHeight = "98%";
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    loadFormItem() {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("lang", _self.getLang());
      m.set("id", _self.cardId);
      //alert(_self.parentid);
      axios
        .post("/search/getCardSearchItem", JSON.stringify(m))
        .then(function(response) {
          let items = response.data.data;
          var i;
          var j = 0;
          var newItems = [];
          for (i = 0; i < items.length; i++) {
            let itm = items[i];
            if (itm.controlType == "Date") {
              var startLabel =
                itm.label + "(" + _self.$t("application.start") + ")";
              var startAttrName = "dtstart_" + itm.attrName;
              var endLabel =
                itm.label + "(" + _self.$t("application.end") + ")";
              var endAttrName = "dtend_" + itm.attrName;
              itm.label = startLabel;
              itm.attrName = startAttrName;
              newItems[j] = itm;
              j++;
              var endDt = new Object();
              endDt.controlType = "Date";
              endDt.attrName = endAttrName;
              endDt.label = endLabel;
              endDt.defaultValue = "";
              newItems[j] = endDt;
              j++;
            } else {
              newItems[j] = itm;
              j++;
            }
          }
          _self.formItemList = newItems;
          console.log(JSON.stringify(_self.formItemList));
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    loadGridInfo(val) {
      let _self = this;
      //console.log(val);
      _self.loading = true;
      var m = new Map();
      m.set("gridName", val);
      m.set("lang", _self.getLang());
      axios
        .post("/dc/getGridViewInfo", JSON.stringify(m))
        .then(function(response) {
          _self.gridList = response.data.data;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    getInput() {
      let _self = this;
      var i;
      var j = 0;
      var conds = [];
      for (i = 0; i < _self.formItemList.length; i++) {
        //console.log("defaultValue:"+_self.formItemList[i].defaultValue);
        if (
          _self.formItemList[i].defaultValue != null &&
          _self.formItemList[i].defaultValue != ""
        ) {
          conds[j] = _self.formItemList[i];
          j++;
        }
      }
      return conds;
    },
    startSearch() {
      let _self = this;
      _self.currentPage = 1;
      _self.itemCount = 0;
      _self.search();
    },
    search() {
      let _self = this;
      let conds = _self.getInput();
      if (conds == 0) {
        _self.$message({
          message: _self.$t("application.conditionCannotEmpty"),
          type: "warning"
        });
        return;
      }
      _self.loading = true;
      var m = new Map();
      m.set("conditions", conds);
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", _self.currentPage - 1);
      m.set("gridName", _self.currentCard.gridView);
      m.set("typeName", _self.currentCard.name);
      axios
        .post("/search/searchByCard", JSON.stringify(m))
        .then(function(response) {
          _self.itemDataList = response.data.data;
          _self.itemCount = response.data.total;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    //展示勾选弹框
    dialogFormShow() {
      this.columnsInfo.dialogFormVisible = true;
    },
    //全选按钮
    handleCheckAllChange(val) {
      this.showFields = [];
      if (val) {
        this.gridList.forEach(element => {
          this.showFields.push(element.attrName);
        });
      }
      this.columnsInfo.isIndeterminate = false;
    },
    //单个选中
    handleCheckedColsChange(value) {
      let checkedCount = value.length;
      this.columnsInfo.checkAll = checkedCount === this.gridList.length;
      this.columnsInfo.isIndeterminate =
        checkedCount > 0 && checkedCount < this.gridList.length;
    },
    confirmShow() {
      let _self = this;
      _self.gridList.forEach(element => {
        element.visibleType = 2;
      });
      _self.showFields.forEach(element => {
        let item = _self.getgriditem(element);
        if (item) {
          item.visibleType = 1;
        }
      });
      this.columnsInfo.dialogFormVisible = false;
    }
  }
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
.el-form-item {
  margin-top: 2px;
  margin-bottom: 2px;
}
</style>
