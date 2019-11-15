<template>
  
  <div>
    <el-dialog :title="$t('application.property')" :visible.sync="propertyVisible" @close="propertyVisible = false">
      <ShowProperty ref="ShowProperty"  @onSaved="onSaved" width="560" v-bind:itemId="selectedItemId" v-bind:typeName="currentItem['TYPE_NAME']"></ShowProperty>
      <div slot="footer" class="dialog-footer">
        <el-button @click="saveItem">{{$t('application.save')}}</el-button> <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible="imageViewVisible" @close="imageViewVisible = false">
      <div v-if=" currentType!='' && imageFormat.indexOf(currentType)>-1">
         <viewer :images="imageArray" @inited="inited" class="viewer" ref="viewer" >
          <img v-for="src in imageArray" :src="src" :key="src" width="240" @click="onImageClick" style="cursor:hand">
        </viewer>
      </div>
      <div v-else>
        <a :href="imageArray[0]" target="_blank">{{$t('application.download')}}</a>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="imageViewVisible = false">{{$t('application.cancel')}}</el-button>
      </div>
   </el-dialog>
    <div class="navbar">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>{{$t('menu.searchCenter')}}</el-breadcrumb-item>
        <el-breadcrumb-item>{{$t('menu.fullTextSearch')}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="searchInput">
      <el-autocomplete
        class="inline-input"
        prefix-icon="el-icon-search"
        v-model="inputkey"
        :fetch-suggestions="querySearch"
        :placeholder="$t('application.placeholderSearch')"
        :trigger-on-focus="false"
        @select="handleSelect"
        @keyup.enter.native="enterDown"
        style="width:60%"
      ></el-autocomplete>
      <el-checkbox :label="$t('application.propertyOnly')" v-model="propertyOnly"></el-checkbox>
    </div>
    <div>
      <div class="docType">
        <span>{{$t('application.docTypeName')}}: </span>
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">{{$t('application.selectAll')}}</el-checkbox>
        <el-checkbox-group v-model="checkedCards">
          <el-checkbox v-for="card in cards" :label="card.label" :key="card.id" checked  @change="handleCheckedTypeChange">{{card.label}}</el-checkbox>
        </el-checkbox-group>
      </div>
    </div>
    <div v-if="searched">
    <div>
      <span>{{$t('application.searchResult')}} {{itemCount}}, &nbsp; {{$t('application.takeTime')}} {{searchTime}}{{$t('application.scend')}}</span>
    </div>
    <el-container>
      <el-aside width="200px">
        <el-row>
          <el-button size="small" type="primary" icon="el-icon-search" @click="handleScendSearch()" :disabled="searched==false">{{$t('application.scendSearch')}}</el-button>
        </el-row>
        <div v-for="term in terms">
          <div v-if="term.fieldName != 'by_object_type'">
            <el-row>
              <el-row class="termHead">
                <span>{{$t('application.'+term.fieldName)}}</span>
              </el-row>
              <el-row>
                <div>
                  <el-checkbox-group v-model="checkedTerms[term.index]">
                    <div v-for="gg in term.groups">
                      <el-checkbox :label="gg.name" :key="term.fieldName+''+gg.name">{{gg.name}}({{gg.value}})</el-checkbox>
                    </div>
                  </el-checkbox-group>
                </div>
              </el-row>
            </el-row>
          </div>
        </div>
      </el-aside>
      <el-main>
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
        <el-table
          :data="dataList"
          style="width: 100%;margin-bottom: 20px;"
          row-key="ID"
          border
          default-expand-all
        >
          <el-table-column type="expand">
            <template slot-scope="scope">
              <el-table :data="scope.row.childrens" border style="width: 100%" :show-header="false">
               
                <el-table-column>
                  <template slot-scope="scope">
                    <div v-html="scope.row.highlightText"></div>
                  </template>
                </el-table-column>
              </el-table>
            </template>
          </el-table-column>
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
                    <div v-if="citem.attrName.indexOf('_DATE')>0">
                      <span>{{dateFormat(scope.row[citem.attrName])}}</span>
                    </div>
                    <div v-else>
                      <span>{{scope.row[citem.attrName]}}</span>
                    </div>
                    <!--span>{{scope.row[citem.attrName]}}</span-->
                  </template>
                </el-table-column>
              </div>
            </div>
          </div>
          <!--
          <el-table-column
            :label="$t('application.content')"
            minWidth="100%"
            >
              <template slot-scope="scope">
                <span>{{scope.row['filecontent']}}</span>
              </template>
          </el-table-column>
          -->
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
      </el-main>
    </el-container>
    </div>
  </div>
</template>

<script type="text/javascript">
import ShowProperty from '@/components/ShowProperty'
export default {
  name: "FullTextSearch",
  components: {
    ShowProperty: ShowProperty
  },
  permit: 1,
  data() {
    return {
      imageFormat: 'jpg,jpeg,bmp,gif,png',
      currentData: [],
      dataList: [],
      dataListFull: [],
      gridList: [],
      terms: [],
      inputkey: "",
      checkedCards: [],
      cards: [],
      currentType: "",
      currentItem: "",
      cardsLabel: [],
      checkedTerms: [],
      checkAll: true,
      isScend: false,
      searched: false,
      isIndeterminate: false,
      imageViewVisible: false,
      imageViewer: Object,
      imageArray:[],
      propertyOnly: false,
      searchTime: "",
      pageSize: 20,
      itemCount: 0,
      selectedItemId: "",
      propertyVisible: false,
      selectedItems: [],
      currentPage: 1,
      loading: false,
      keywords: [],
      dialogVisible: false,
      tableHeight: window.innerHeight - 170,
      formLabelWidth: "120px"
    };
  },
  created() {
    let _self = this;
    var psize = localStorage.getItem("FTSearchPageSize");
    if (psize) {
      _self.pageSize = parseInt(psize);
    }
    _self.loadCards();
    _self.loadGridInfo();
  },
  methods: {
    enterDown() {
      this.currentPage = 1;
      this.search(false);
    },
    handleCheckAllChange(val){
      
      this.checkedCards = val ? this.cardsLabel : [];
      this.isIndeterminate = false;
    },
    handleCheckedTypeChange(value){
      //console.log(JSON.stringify(value));
      let checkedCount = this.checkedCards.length;
      this.checkAll = checkedCount === this.cardsLabel.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.cards.length;
    },
    // 查看属性
    showItemProperty(indata){
      let _self = this;
      _self.currentItem = indata;
      _self.selectedItemId = indata.ID ;
      _self.propertyVisible = true;
      if(_self.$refs.ShowProperty){
        _self.$refs.ShowProperty.myItemId = indata.ID ;
        _self.$refs.ShowProperty.loadFormInfo();
      }
    },
    // 查看内容
    showItemContent(indata){
      let _self = this;
      _self.imageArray = [];
      _self.currentType = indata.FORMAT_NAME;
      // 拦截器会自动替换成目标url
      _self.imageArray[0] =  "/dc/getContent?id="+indata.ID+"&token="+sessionStorage.getItem('access-token');
      if(_self.currentType == "pdf"){
         window.open("./static/pdfviewer/web/viewer.html?file="+encodeURIComponent(_self.imageArray[0])+"&.pdf");
      }else{
         _self.imageViewVisible =true;
      }
    },
    querySearch(queryString, cb) {
      let _self = this;
      _self
        axios.get("/search/getSuggestion",JSON.stringify(_self.inputkey))
        .then(function(response) {
          _self.keywords = response.data.data;
          if(_self.keywords){
             var list = [{}];
             var i;
             for(i=0;i<_self.keywords.length;i++){
               var item = new Object();
               item.value = _self.keywords[i];
               list[i] = item;
             }
             _self.keywords = list;
          }
          cb(_self.keywords);
          //console.log(JSON.stringify(_self.taskList));
          _self.dialogVisible = true;
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    handleSelect(item) {
      //console.log(item);
      this.currentPage = 1;
      this.inputkey = item.value;
      this.search(false);
    },
     handleScendSearch() {
      //console.log(item);
      this.currentPage = 1;
      this.search(true);
    },
    createFilter(queryString) {
      return keyword => {
        return (
          keyword.value.toLowerCase().indexOf(queryString.toLowerCase()) >= 0
        );
      };
    },
    // 分页 页数改变
    handleSizeChange(val) {
      this.pageSize = val;
      var maxPage = Math.floor(this.itemCount /val)+1;
      this.currentPage = this.currentPage>maxPage?maxPage:this.currentPage;
      localStorage.setItem("FTSearchPageSize", val);
      this.search(this.isScend);
      //console.log('handleSizeChange', val);
    },
    // 分页 当前页改变
    handleCurrentChange(val) {
      this.currentPage = val;
      this.search(this.isScend);
    },
    loadCards() {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      axios.get("/search/getCardSearchs",_self.getLang())
        .then(function(response) {
          _self.cards = response.data.data;
          var i=0;
          for(i=0;i<_self.cards.length;i++){
            _self.cardsLabel[i] = _self.cards[i].label;
          }
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    // 加载表格样式
    loadGridInfo() {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("gridName", "FullTextGrid");
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
    search(isScend) {
      let _self = this;
      _self.isScend = isScend;
      //console.log(JSON.stringify(_self.checkedCards));
      var m = new Map();
      m.set("keyword",  _self.inputkey);
      m.set("pageSize", _self.pageSize);
      m.set("pageIndex", _self.currentPage - 1);
      m.set("typeNames", _self.checkedCards);
      m.set("attrOnly", _self.propertyOnly);
      if(isScend){
        var i;
        var terms = [];
        var len = 0;
        for( i=0;i<_self.terms.length;i++){
            let term = _self.terms[i];
            let cterm = _self.checkedTerms[i];
            if(cterm.length>0){
              var obj = new Object();
              obj.fieldName = term.fieldName;
              obj.values = cterm;
              terms[len] = obj;
              len ++;
            }
        }
        if(len>0){
          m.set("terms", terms);
        }
      }
      axios.get("/search/searchByKeyword",JSON.stringify(m))
        .then(function(response) {
          _self.dataList = response.data.data.list;
          _self.searchTime = response.data.data.searchTime;
          _self.itemCount = response.data.total;
          let myterms = response.data.data.aggregations;

          //console.log(JSON.stringify(_self.dataList));
          _self.dataList.map(x=>{
              var child = new Object();
              child.highlightText = x.highlightText;
              child.parentId = x.ID;
              x.childrens = new Array(child);
          });
          var i;
          _self.checkedTerms = [];
          // for(term in _self.terms){
          for( i=0;i<myterms.length;i++){
            let term = myterms[i];
            term.index = i;
            _self.checkedTerms[i]=[];
            var gg = JSON.stringify(term.groups);
            //console.log(gg);
            gg = gg.replace("{","");
            gg = gg.replace("}","");
            //console.log(gg);
            var items = gg.split(",");
            var gitem;
            var newGroup = [];
            var j;
            for(j=0;j<items.length;j++){
              var val = items[j].split(":");
              gitem = new Object();
              gitem.name = val[0].replace("\"","").replace("\"","");
              gitem.value = val[1];
              newGroup[j] = gitem;
            }
            term.groups = newGroup;
          }
           _self.terms = myterms;
          _self.searched = true;
          console.log(JSON.stringify(_self.checkedTerms));
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
.searchInput {
  display: inline;
}
.docType {
  display: inline;
  font-size: 14px;
}
.termHead {
  background-color: rgb(196, 225, 255);
}
.el-aside {
    background-color: rgb(231, 236, 241);
}

</style>
