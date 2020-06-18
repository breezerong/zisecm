<template>
  <div>
    <div>
      <el-dialog
        title="编辑列"
        :visible.sync="editColumn"
        @close="onCloseCustom"
        width="60%"
      >
      <el-dialog
        title="新建名称"
        :visible.sync="inputColumn"
        @close="inputColumn = false;"
        width="60%"
        :append-to-body='true'
      >
      <el-input  placeholder="请输入自定义显示名称" v-model="selectedName"></el-input>
      <div slot="footer" class="dialog-footer">
          <el-button @click="createCustomGrid()">{{$t('application.save')}}</el-button>
          <el-button @click="inputColumn = false">{{$t('application.cancel')}}</el-button>
        </div>
      </el-dialog>
      <el-row>
        <el-col :span="2">名称</el-col>
        <el-col :span='8'>
          <el-select v-model="selectedName" placeholder="请选择">
            <el-option
              v-for="item in customNames"
              :key="item.id"
              :label="item.description"
              :value="item.description">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="8">
          <el-button @click="showCreateName">新建</el-button>
          <el-button @click="deleteGridView">删除</el-button>
        </el-col>
      </el-row>
        <el-transfer
          filterable
          :titles="['Source', 'Target']"
          v-model="selectedColumns"
          :render-content="renderContent"
          @right-check-change="rightCheckChange"
          @change="rightChange"
          :data="leftData"
          target-order="push">
          <el-button class="transfer-footer" slot="right-footer" size="small" @click="moveUp">上移</el-button>
          <el-button class="transfer-footer" slot="right-footer" size="small" @click="moveDown">下移</el-button>
        </el-transfer>
        <div slot="footer" class="dialog-footer">
          <el-button @click="saveCustomColumn()">{{$t('application.save')}}</el-button>
          <el-button @click="editColumn = false">{{$t('application.cancel')}}</el-button>
        </div>
      </el-dialog>
      
      <el-dialog
        :append-to-body="true"
        :title="typeName+$t('application.property')"
        :visible.sync="propertyVisible"
        @close="propertyVisible = false"
        width="90%"
        style="text-align:center"
      >
        <ShowProperty
          ref="ShowProperty"
          @onSaved="onSaved"
          width="100%"
          :typeName="typeName"
          v-bind:itemId="selectedItemId"
        ></ShowProperty>
        <div slot="footer" class="dialog-footer">
          <el-button @click="saveItem()">{{$t('application.save')}}</el-button>
          <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
        </div>
      </el-dialog>
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
            v-for="item in columnList"
            :label="item.attrName"
            :key="item.attrName"
          >{{item.label}}</el-checkbox>
        </el-checkbox-group>
        <div slot="footer" class="dialog-footer">
          <el-button @click="columnsInfo.dialogFormVisible=false" size="medium">取 消</el-button>
          <el-button type="primary" @click="confirmShow" size="medium">确定</el-button>
        </div>
      </el-dialog>
      
        <el-table
          :key="rkey"
          id="datatable"
          :height="tableHeight"
          :data="itemDataList"
          border
          @selection-change="selectChange"
          @sort-change="sortchange"
          @row-click="rowClick"
          @row-dblclick="dbclick"
          @header-dragend="onHeaderDragend"
          v-loading="loading"
          :style="{'width': tableWidth}"
          highlight-current-row
        >
          <el-table-column v-if="isshowSelection" type="selection" width="40"></el-table-column>
          <el-table-column :label="$t('field.indexNumber')" key="#1" width="70">
            <template slot-scope="scope">
              <span>{{(currentPage-1) * pageSize + scope.$index+1}}</span>
            </template>
          </el-table-column>
          <el-table-column width="40" v-if="isshowicon">
            <template slot-scope="scope">
               <img v-if="scope.row.TYPE_NAME=='图册'"
                    :src="'./static/img/drawing.gif'"
                    :title="scope.row.TYPE_NAME"
                    border="0"
                  />
                  <img v-else-if="scope.row.TYPE_NAME=='卷盒'"
                    :src="'./static/img/box.gif'"
                    :title="scope.row.TYPE_NAME"
                    border="0"
                  />
                  <img
                  v-else-if="scope.row.FORMAT_NAME==null || scope.row.FORMAT_NAME==''"
                  :src="'./static/img/format/f_undefined_16.gif'"
                  title="无电子文件"
                  border="0"
                />
                <img
                  v-else
                  :src="'./static/img/format/f_'+scope.row.FORMAT_NAME+'_16.gif'"
                  :title="scope.row.FORMAT_NAME"
                  border="0"
                />
            </template>
          </el-table-column>
          <div v-for="(citem,idx) in columnList" :key="idx+'_C'">
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
                      <span :class="scope.row['LIFECYCLE_DIR']==0? 'reject':'success'">{{scope.row[citem.attrName]}}</span>
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
                      <span :class="scope.row['LIFECYCLE_DIR']==0? 'reject':'success'">{{scope.row[citem.attrName]}}</span>
                    </div>
                  </template>
                </el-table-column>
              </div>
            </div>
          </div>
          <el-table-column v-if="isshowOption" :label="$t('application.operation')" width="190">
            
            <template slot="header">
              <el-button icon="el-icon-s-grid" size="small" @click="dialogFormShow" title="选择展示字段"></el-button>
              <el-dropdown trigger="click" style="overflow:visible">
                <el-button
                type="primary"
                plain
                size="small"
                title="列表选择"
                icon="el-icon-more"
              ></el-button>
                <el-dropdown-menu slot="dropdown">
                  <!-- <el-button v-for="(item,idx) in customNames" :key="idx+'cc'">{{item.description}}</el-button> -->
                  <el-dropdown-item v-for="(item,idx) in customList"
                  :key="idx+'_Cz'"
                   @click.native="showCustomInfo(item.id)">{{item.description}}</el-dropdown-item>
                  
                </el-dropdown-menu>
              </el-dropdown>
              <el-button v-if="isshowCustom" icon="el-icon-setting" size="small" @click="showEditColumn" title="自定义列表"></el-button>
              
            </template>
            <template slot-scope="scope">
              <!-- <el-button type="primary" plain size="small" :title="$t('application.viewProperty')" icon="el-icon-info" @click="showItemProperty(scope.row)"></el-button>
                            <el-button type="primary" plain size="small" :title="$t('application.viewContent')" icon="el-icon-picture-outline" @click="showItemContent(scope.row)"></el-button>
              <el-button type="primary" plain size="small" :title="$t('application.view')" icon="el-icon-picture-outline" @click="showNewWindow(scope.row.ID)"></el-button>-->
              <!-- <el-button
                type="primary"
                plain
                size="small"
                :title="$t('application.viewContent')"
                icon="el-icon-picture-outline"
                @click="showMenu($event)"
              ></el-button> -->
              <el-dropdown trigger="click">
                <el-button
                type="primary"
                plain
                size="small"
                :title="$t('application.more')"
                icon="el-icon-more"
              ></el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item icon="el-icon-reading" @click.native="showItemContent(selectedRow)">查看内容</el-dropdown-item>
                  <el-dropdown-item icon="el-icon-info" @click.native="showItemProperty(selectedRow)">查看属性</el-dropdown-item>
                  <el-dropdown-item icon="el-icon-circle-plus-outline" @click.native="addToShoppingCar([selectedRow])">加入购物车</el-dropdown-item>
                  <el-dropdown-item icon="el-icon-check" @click.native="upgrade(selectedRow)">升版</el-dropdown-item>
                  
                </el-dropdown-menu>
              </el-dropdown>
              <!-- showItemContent(scope.row) -->
              <el-button
                type="primary"
                plain
                size="small"
                :title="$t('application.property')"
                icon="el-icon-info"
                @click="showItemProperty(scope.row)"
              ></el-button>
            </template>
          </el-table-column>
        </el-table>

        <div id="menu" @mouseleave="leave">
            <div class="menu" @click="showItemContent(selectedRow)">查看内容</div>
            <div class="menu" @click="showItemProperty(selectedRow)">查看属性</div>
            <div class="menu" @click="addToShoppingCar([selectedRow])">加入购物车</div>
            <div class="menu" @click="upgrade(selectedRow)">升版</div>
        </div>
       
      <el-pagination
        v-if="isshowPage"
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
  name: "dataGrid",
  data() {
    
    return {
      rkey:0,
      refreshCustomView:true,
      inputColumn:false,
      customNames:[],
      customList:[],
      selectedName:"",
      leftData:[],
      selectedColumns:[],
      editColumn:false,
      columnsInfo: {
        checkAll: true,
        checkedCities: [],
        temCol: [],
        dialogFormVisible: false,
        isIndeterminate: false
      },
      propertyVisible: false,
      currentPage: 1,
      pageSize: 20,
      showFields: [],
      selectedItemId: "",
      selectedRow:"",
      typeName:"",
      selectedKey:[],
      selectedIndex:""
    };
  },
  props: {
    itemDataList: { type: Array, default: null },
    sysColumnInfo:{type: Array, default: null},
    columnList: { type: Array, default: null },
    isshowicon: { type: Boolean, default: true },
    isshowOption: { type: Boolean, default: false },
    isshowSelection: { type: Boolean, default: true },
    tableHeight: { type: [String, Number], default: window.innerHeight - 408 },
    tableWidth: { type: [String, Number], default: "100%" },
    itemCount: { type: [String, Number] },
    isshowPage: { type: Boolean, default: true },
    loading: { type: Boolean, default: false },
    gridViewName:{type:String,default:''},
    isshowCustom:{type:Boolean,default:false}
  },
  watch: {
    showFields(val, oldVal) {
      //普通的watch监听
      //console.log("a: "+val, oldVal);
      let _self = this;
      _self.columnList.forEach(element => {
        element.visibleType = 2;
      });
      val.forEach(element => {
        let item = _self.getgriditem(element);
        if (item) {
          //console.log(element);
          item.visibleType = 1;
        }
      });
    },
    // customNames(){
    //   let _self=this;
    //   _self.refreshCustomView=false;
    //   _self.$nextTick(()=>{
    //                 _self.refreshCustomView=true;
    //             });
    // },
    value(val) {
				this.selectedColumns = val;
			},
			selectedColumns(val){
				this.$emit('input', val);
			}
  },
  components: {
    ShowProperty: ShowProperty
  },
  mounted(){
    // this.ready();
    this.loadCustomName();
  },
  methods: {
    // ready(){
      
    //   document.addEventListener('click',(e)=>{
    //       // let sp3 =document.getElementById("locationName")
    //       let menu= document.querySelector("#menu");
    //       if(!menu.contains(e.target)){
    //         menu.style.display = 'none';
    //       }
    //       // if(menu.contains(e.target)&&menu.style.display=='none'){
    //       //   menu.style.display = 'block';
    //       // }else{
    //       //   menu.style.display = 'none';
    //       // }
    //     })
    // },
    onCloseCustom(){
      let _self=this;
      _self.editColumn = false;
      _self.$nextTick(()=>{
        _self.loadCustomName();
        _self.$forceUpdate();
        _self.rkey++;
        // location.reload();
      })
      
    },
    deleteGridView(){
      let _self=this;
      var m = new Map();
      m.set('gridName',_self.gridViewName);
      m.set('DESCRIPTION',_self.selectedName);
      m.set("lang", "zh-cn");
      _self.axios({
            headers: {
            "Content-Type": "application/json;charset=UTF-8"
            },
            method: 'post',
            data: JSON.stringify(m),
            url: "/admin/deleteCustomGridView"
        })
            .then(function(response) {
              if(response.data.code==1){
                _self.$message({
                      showClose: true,
                      message: "删除成功！",
                      duration: 2000,
                      type: "Success"
                    });
                    _self.selectedName='';
                    _self.loadCustomName();
                
              }
              
            })
            .catch(function(error) {
            console.log(error);
            });
    },
    showCustomInfo(id){
      let _self=this;
      var m = new Map();
      m.set('gridId',id);
      m.set("lang", "zh-cn");
      _self.axios({
            headers: {
            "Content-Type": "application/json;charset=UTF-8"
            },
            method: 'post',
            data: JSON.stringify(m),
            url: "/dc/getOneEcmCustomGridViewInfo"
        })
            .then(function(response) {
              if(response.data.code==1){
                _self.columnList=response.data.data;

              }
              
            })
            .catch(function(error) {
            console.log(error);
            });
    },
  createCustomGrid(){
    let _self=this;
    let mp=new Array();
    if(_self.selectedName==''){
      _self.$message({
                      showClose: true,
                      message: "名称为空！",
                      duration: 2000,
                      type: "Error"
                    });
      retrun;
    }
        var m = new Map();
        m.set('gridName',_self.gridViewName);
        m.set('DESCRIPTION',_self.selectedName);
        
        _self.axios({
            headers: {
            "Content-Type": "application/json;charset=UTF-8"
            },
            method: 'post',
            data: JSON.stringify(m),
            url: "/admin/createOrUpdateGridView"
        })
            .then(function(response) {
              if(response.data.code==1){
                _self.$message({
                      showClose: true,
                      message: "保存成功！",
                      duration: 2000,
                      type: "Success"
                    });

              }
              _self.inputColumn=false;
              _self.loadCustomName();
              
            })
            .catch(function(error) {
            console.log(error);
            });
  },
  loadCustomName(){
    
    let _self=this;
    _self.axios({
            headers: {
            "Content-Type": "application/json;charset=UTF-8"
            },
            method: 'post',
            url: "/admin/getAllGridViewsOfCurrentUser"
        })
            .then(function(response) {
              if(response.data.code==1){
                // _self.$set(_self.customNames,response.data.data);
                _self.customNames=response.data.data;
                _self.customList=response.data.data;
              }
              
            })
            .catch(function(error) {
            console.log(error);
            });
  },
  saveCustomColumn(){
    let _self=this;
    if(_self.selectedName==''){
      _self.$message({
                      showClose: true,
                      message: "名称为空！",
                      duration: 2000,
                      type: "Error"
                    });
      retrun;
    }
    let mp=new Array();
    for(let i=0;i<_self.selectedColumns.length;i++){
      for(let j=0;j<_self.sysColumnInfo.length;j++){
        let obj=_self.sysColumnInfo[j];
        if(obj.attrName==_self.selectedColumns[i]){
          mp.push(obj);
        }
      }
    }
        var m = new Map();
        m.set('gridName',_self.gridViewName);
        m.set('DESCRIPTION',_self.selectedName);
        m.set('items',mp);
        _self.axios({
            headers: {
            "Content-Type": "application/json;charset=UTF-8"
            },
            method: 'post',
            data: JSON.stringify(m),
            url: "/admin/createOrUpdateGridView"
        })
            .then(function(response) {
              if(response.data.code==1){
                _self.$message({
                      showClose: true,
                      message: "保存成功！",
                      duration: 2000,
                      type: "Success"
                    });

              }
              // _self.loadGridInfo();
              // _self.loadCustomName();
              _self.editColumn=false;
            })
            .catch(function(error) {
            console.log(error);
            });
  },

  loadGridInfo() {
      let _self = this;
      var m = new Map();
      m.set("gridName", _self.gridViewName);
      m.set("lang", "zh-cn");
      _self
        .axios({
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          method: "post",
          data: JSON.stringify(m),
          url: "/dc/getEcmCustomGridViewInfo"
        })
        .then(function(response) {
          _self.columnList = response.data.customGridInfo;
          _self.sysColumnInfo=response.data.sysGridInfo;
          
        })
        .catch(function(error) {
          console.log(error);
         
        });
  },
   rightChange(value, direction, movedKeys){
     if(direction=='left'){
       this.selectedKey=[];
     }
   },
    rightCheckChange(val){

      this.selectedKey=val;
    },
    renderContent (h, option) {
        return h('span', {domProps: {title: option.label}}, option.label);
    },
    moveUp(){
      let _self=this;
      //选中值的下标
      if(_self.selectedKey.length==1){
          _self.selectedColumns.find((val, indexs, arr) => {

          if (val ==_self.selectedKey[0]) {

            _self.selectedIndex = indexs;

          }

        });
        if (_self.selectedIndex == 0) { //当选择的项的下标为0，即第一个，则提醒没有上移的空间，选择其他项进行上移
            _self.$message({
                      showClose: true,
                      message: "没有上移的空间了",
                      duration: 2000,
                      type: "warning"
                    });
           
            return;

          }
        // 上移-改变的数组（项和下标同时改变）

        let changeItem = JSON.parse(JSON.stringify(_self.selectedColumns[_self.selectedIndex- 1]));
        _self.selectedColumns.splice(_self.selectedIndex- 1, 1);
        _self.selectedColumns.splice(_self.selectedIndex, 0, changeItem);

      }else{
        _self.$message({
                      showClose: true,
                      message: "只允许选择一条数据进行排序",
                      duration: 2000,
                      type: "error"
                    });
      }
        
      
    },
    moveDown(){
      let _self=this;
      //选中值的下标
      if(_self.selectedKey.length==1){
          _self.selectedColumns.find((val, indexs, arr) => {

          if (val ==_self.selectedKey[0]) {

            _self.selectedIndex = indexs;

          }

        });
        if (_self.selectedIndex == _self.selectedColumns.length-1) { //当选择的项的下标为0，即第一个，则提醒没有上移的空间，选择其他项进行上移
            _self.$message({
                      showClose: true,
                      message: "没有下移的空间了",
                      duration: 2000,
                      type: "warning"
                    });
           
            return;

          }
        // 上移-改变的数组（项和下标同时改变）

        let changeItem = JSON.parse(JSON.stringify(_self.selectedColumns[_self.selectedIndex]));
        _self.selectedColumns.splice(_self.selectedIndex, 1);
        _self.selectedColumns.splice(_self.selectedIndex+1, 0, changeItem);

      }else{
        _self.$message({
                      showClose: true,
                      message: "只允许选择一条数据进行排序",
                      duration: 2000,
                      type: "error"
                    });
      }
    },
    showCreateName(){
        let _self=this;
        _self.selectedName='';
        _self.inputColumn=true;
    },
    showEditColumn(){
      let _self=this;
      _self.editColumn=true;
      _self.$nextTick(() => {
        _self.leftData=_self.generateData();
      });
    },
     generateData() {
       let _self =this;
        const data = [];
        for (let i = 0; i < _self.sysColumnInfo.length; i++) {
          data.push({
            key: _self.sysColumnInfo[i].attrName,
            label: _self.sysColumnInfo[i].label
          });
        }
        return data;
      },
    leave(){
       var menu = document.querySelector("#menu");
        menu.style.display = 'none';
    },
    //显示菜单
    showMenu(event) { // 鼠标右击触发事件
        var menu = document.querySelector("#menu");
        menu.style.display = 'block';
        menu.style.left = event.clientX - 250 + 'px'
        menu.style.top = event.clientY - 120 + 'px'
        
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
    upgrade(item){
            let _self = this;

            // console.log('pagesize:', _self.pageSize);
            axios
                .post("/dc/upgradeDocument", item.ID)
                .then(function(response) {
                  if(response.data.code=='1'){
                    _self.$emit("upgradeFun", response.data.id);
                  }else{
                    _self.$message({
                      showClose: true,
                      message: response.data.message,
                      duration: 2000,
                      type: "warning"
                    });
                  }
                  
                     
                })
                .catch(function(error) {
                console.log(error);
                _self.loading = false;
                });

           
        },
    // 保存文档
    saveItem() {
      this.$refs.ShowProperty.saveItem();
    },
    //查看属性
    showItemProperty(indata) {
      let _self = this;
      _self.selectedItemId = indata.ID;
      _self.propertyVisible = true;
      setTimeout(() => {
        if (_self.$refs.ShowProperty) {
          _self.$refs.ShowProperty.myItemId = indata.ID;
          _self.typeName=indata.TYPE_NAME;
          _self.$refs.ShowProperty.loadFormInfo();
        }
      }, 10);
    },
    onSaved(indata) {
      if (indata == "update") {
        this.$message(this.$t("message.saveSuccess"));
        this.$emit("refreshdatagrid");
      } else {
        //this.$message("新建成功!");
      }
      this.propertyVisible = false;
    },
    showFieldOption() {
      let _self = this;
      _self.showFields = [];

      _self.columnList.forEach(element => {
        if (element.visibleType == 1) {
          _self.showFields.push(element.attrName);
        }
      });
    },
    getgriditem(attrName) {
      let _self = this;
      let ret = null;
      _self.columnList.forEach(element => {
        if (element.attrName == attrName) {
          ret = element;
          return;
        }
      });
      return ret;
    },
    //全选按钮
    handleCheckAllChange(val) {
      this.showFields = [];
      if (val) {
        this.columnList.forEach(element => {
          this.showFields.push(element.attrName);
        });
      }
      this.columnsInfo.isIndeterminate = false;
    },
    //单个选中
    handleCheckedColsChange(value) {
      let checkedCount = value.length;
      this.columnsInfo.checkAll = checkedCount === this.columnList.length;
      this.columnsInfo.isIndeterminate =
        checkedCount > 0 && checkedCount < this.columnList.length;
    },
    //展示勾选弹框
    dialogFormShow() {
      this.showFieldOption();
      this.columnsInfo.dialogFormVisible = true;
    },
    confirmShow() {
      let _self = this;
      _self.columnList.forEach(element => {
        element.visibleType = 2;
      });
      _self.showFields.forEach(element => {
        let item = _self.getgriditem(element);
        if (item) {
          item.visibleType = 1;
        }
      });
      this.columnsInfo.dialogFormVisible = false;
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      //console.log('handleCurrentChange', val);
      this.$emit("pagechange", this.currentPage);
    },
    // 分页 页数改变
    handleSizeChange(val) {
      this.pageSize = val;
      localStorage.setItem("docPageSize", val);
      //console.log('handleSizeChange', val);
      this.$emit("pagesizechange", this.pageSize);
    },
    sortchange(column) {
      console.log(JSON.stringify(column));
      console.log(column.column.property);
      console.log(column.column.order); //ascending, descending
      this.orderBy =
        column.column.property + column.column.order == "ascending"
          ? " ASC"
          : " DESC";
    },
    rowClick(row) {
      
      this.selectedRow = row;
      this.$emit("rowclick", row);
    },
    dbclick(row){
      this.$emit("dbclick", row);
    },
    selectChange(val) {
      this.$emit("selectchange", val);
    }
  }
};
</script>
<style>
.el-transfer-panel__item.el-checkbox{
  margin-left: 0px;
}
</style>
<style scoped>

.success {
  color:'';
}
.reject{
  color:red;
}
    #menu {
        width: 120px; 
        height: 100px;
        overflow: hidden; /*隐藏溢出的元素*/
        box-shadow: 0 1px 1px #888, 1px 0 1px #ccc;
        position: absolute; 
        display: none;
        background: #ffffff;
        z-index: 10;
    }
 
    .menu {
        width: 125px;
        height: 25px;
        line-height: 25px;
        text-indent: 10px;
        cursor: pointer;
    }
 
    .menu:hover {
        color: deeppink;
        text-decoration: underline;
    }
    .el-dropdown-link {
      cursor: pointer;
      color: #409EFF;
    }
    .hey-btn { display: inline-block; 
    background-color: #87CEEB; 
    color: white; 
    text-decoration: none; 
    font-family: 'Microsoft YaHei', sans-serif; 
    text-align: center; 
    border: 1px; 
    width: 2.5rem;
    height: 1.8rem;
    cursor: pointer; }
</style>