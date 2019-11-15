<template>
  <div>
    <div class="navbar">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>{{$t('menu.searchCenter')}}</el-breadcrumb-item>
        <el-breadcrumb-item>{{$t('menu.cardSearch')}}</el-breadcrumb-item>
        <el-breadcrumb-item>{{currentCard.label}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div>
      <el-form label-position="right" label-width="120px">
        <div v-for="subitem in formItemList">
          <el-col :span="8" style="align:left;">
            <el-form-item :label="subitem.label">
              <div v-if="subitem.controlType === 'TextBox'">
                <el-input
                  style="width:20em"
                  v-model="subitem.defaultValue"
                ></el-input>
              </div>
              <div v-if="subitem.controlType=='Integer'">
                <el-input
                  style="width:20em"
                  type="number"
                  v-model.number="subitem.defaultValue"
                ></el-input>
              </div>
              <div v-if="subitem.controlType=='Boolean'">
                <el-checkbox
                  style="width:20em"
                  v-model="subitem.defaultValue"
                ></el-checkbox>
              </div>
              <div v-else-if="subitem.controlType === 'TextArea'">
                <el-input
                  style="width:20em"
                  type="textarea"
                  v-model="subitem.defaultValue"
                ></el-input>
              </div>
              <div v-else-if="subitem.controlType ==='Date'">
                <el-date-picker
                  style="width:20em"
                  v-model="subitem.defaultValue"
                  type="date"
                  :placeholder="$t('application.selectDate')"
                ></el-date-picker>
              </div>
              <div
                v-else-if="subitem.controlType === 'Select'|| subitem.controlType=='Department'
                      || subitem.controlType=='SQLSelect'"
              >
                <el-select
                  style="width:20em"
                  v-model="subitem.defaultValue"
                >
                  <el-option label value></el-option>
                  <div v-for="name in subitem.validValues">
                    <el-option :label="name" :value="name"></el-option>
                  </div>
                </el-select>
              </div>
            </el-form-item>
          </el-col>
        </div>
        <el-col :span="8" style="align:left;">
          <el-button
            type="primary"
            icon="el-icon-search"
            @click="startSearch()"
          >{{$t('application.search')}}</el-button>
          <span>&nbsp; &nbsp; &nbsp;</span>
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
      >
        <el-table-column :label="$t('field.indexNumber')" width="60">
          <template slot-scope="scope">
            <span>{{(currentPage-1) * pageSize + scope.$index+1}}</span>
          </template>
        </el-table-column>
        <el-table-column width="40">
          <template slot-scope="scope">
            <img :src="'./static/img/format/f_'+scope.row.FORMAT_NAME+'_16.gif'" border="0" />
          </template>
        </el-table-column>
        <div v-for="(citem,idx) in gridList">
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
        <el-table-column :label="$t('application.operation')" width="157">
          <template slot-scope="scope">
            <el-button
              type="primary"
              plain
              size="small"
              :title="$t('application.viewProperty')"
              icon="el-icon-info"
              @click="showItemProperty(scope.row)"
            ></el-button>
            <el-button
              type="primary"
              plain
              size="small"
              :title="$t('application.viewContent')"
              icon="el-icon-picture-outline"
              @click="showItemContent(scope.row)"
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
export default {
  name: "CardSearch",
  permit: 1,
  data() {
    return {
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
      tableHeight: window.innerHeight - 225,
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
     axios.get("/search/getCardSearch",JSON.stringify(m))
        .then(function(response) {
          _self.currentCard = response.data.data;
          //console.log(JSON.stringify(_self.currentCard));
          _self.loadFormItem();
          _self.loadGridInfo(_self.currentCard.gridView);
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
      axios.get("/search/getCardSearchItem",JSON.stringify(m))
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
      axios.get("/dc/getGridViewInfo",JSON.stringify(m))
        .then(function(response) {
          _self.gridList = response.data.data;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    getInput(){
      let _self = this;
      var i;
      var j=0;
      var conds=[];
      for(i=0;i<_self.formItemList.length;i++){
        console.log("defaultValue:"+_self.formItemList[i].defaultValue);
        if(_self.formItemList[i].defaultValue != null && _self.formItemList[i].defaultValue !=""){
          conds[j] = _self.formItemList[i];
          j++;
        }
      }
      return conds;
    },
    startSearch(){
      let _self = this;
      _self.currentPage = 1;
      _self.itemCount = 0;
      _self.search();
    },
    search() {
      let _self = this;
      let conds = _self.getInput();
      if(conds==0){
        _self.$message({message:_self.$t("application.conditionCannotEmpty"),type: 'warning'});
        return;
      }
      _self.loading = true;
     var m = new Map();
      m.set("conditions", conds);
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", _self.currentPage - 1);
      m.set("gridName", _self.currentCard.gridView);
      m.set("typeName", _self.currentCard.name);
      axios.get("/search/searchByCard",JSON.stringify(m))
        .then(function(response) {
          _self.itemDataList = response.data.data;
          _self.itemCount = response.data.total;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
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
a {
  color: #42b983;
}
</style>
