<template>
    <div>
           <div>
                <el-dialog :title="$t('application.property')" :visible.sync="propertyVisible" @close="propertyVisible = false" width="80%">
                  <ShowProperty ref="ShowProperty"  @onSaved="onSaved" width="100%" v-bind:itemId="selectedItemId"></ShowProperty>
                  <div slot="footer" class="dialog-footer">
                    <el-button @click="saveItem()">{{$t('application.save')}}</el-button> 
                    <el-button @click="propertyVisible = false">{{$t('application.cancel')}}</el-button>
                  </div>
                </el-dialog>
               <!-- 选字段对话框 -->
                <el-dialog title="选择需要展示的字段" :visible.sync="columnsInfo.dialogFormVisible" width="40%" center top="15vh">
                    <el-checkbox :indeterminate="columnsInfo.isIndeterminate" v-model="columnsInfo.checkAll" @change="handleCheckAllChange">全选</el-checkbox>
                    <div style="margin: 15px 0;"></div>
                    <el-checkbox-group v-model="showFields" @change="handleCheckedColsChange">
                        <el-checkbox v-for="item in columnList" :label="item.attrName" :key="item.attrName">{{item.label}}</el-checkbox>
                    </el-checkbox-group>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="columnsInfo.dialogFormVisible=false" size="medium">取 消</el-button>
                        <el-button type="primary" @click="confirmShow" size="medium">确定</el-button>
                    </div>
                </el-dialog>
                    <el-table
                        :height="tableHeight"
                        :data="itemDataList"
                        border
                        @selection-change="selectChange"
                        @sort-change="sortchange"
                        @row-click="rowClick"
                        v-loading="loading"
                        :style="{'width': tableWidth}">
                        <el-table-column type="selection" width="40">
                        </el-table-column>
                        <el-table-column :label="$t('field.indexNumber')"  key="#1" width="60">
                          <template slot-scope="scope">
                            <span>{{(currentPage-1) * pageSize + scope.$index+1}}</span>
                          </template>
                        </el-table-column>
                        <el-table-column width="40" v-if="isshowicon">
                          <template slot-scope="scope">
                            <img :src="'./static/img/format/f_'+scope.row.FORMAT_NAME+'_16.gif'" border="0">
                          </template>
                        </el-table-column>
                        <div v-for="(citem,idx) in columnList" :key="idx+'_C'">
                          <div v-if="citem.visibleType==1">
                            <div v-if="(citem.width+'').indexOf('%')>0">
                                <el-table-column :label="citem.label" :prop="citem.attrName" :min-width="citem.width" :sortable="citem.allowOrderby">
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
                              <el-table-column :label="citem.label" :width="citem.width" :prop="citem.attrName" :sortable="citem.allowOrderby">
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
                        <el-table-column v-if="isshowOption" :label="$t('application.operation')" width="140">
                          <template slot="header">
                            <el-button icon="el-icon-s-grid" @click="dialogFormShow"></el-button>
                          </template>
                          <template slot-scope="scope">
                            <!-- <el-button type="primary" plain size="small" :title="$t('application.viewProperty')" icon="el-icon-info" @click="showItemProperty(scope.row)"></el-button>
                            <el-button type="primary" plain size="small" :title="$t('application.viewContent')" icon="el-icon-picture-outline" @click="showItemContent(scope.row)"></el-button>
                            <el-button type="primary" plain size="small" :title="$t('application.view')" icon="el-icon-picture-outline" @click="showNewWindow(scope.row.ID)"></el-button> -->
                            
                              <el-button type="primary" plain size="small" :title="$t('application.property')" icon="el-icon-info" @click="showItemProperty(scope.row)"></el-button>
                              <el-button type="primary" plain size="small" :title="$t('application.viewContent')" icon="el-icon-picture-outline" @click="showItemContent(scope.row)"></el-button>
                          </template>
                        </el-table-column>
                      </el-table>
                      <el-pagination v-if="isshowPage"
                        background
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="currentPage"
                        :page-sizes="[10, 20, 50, 100, 200]"
                        :page-size="pageSize"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="itemCount">
                      </el-pagination>
                    </div> 
    </div>
</template>
<script type="text/javascript">
import ShowProperty from '@/components/ShowProperty'
export default {
    name:"dataGrid",
    data() {
        return {
            columnsInfo:{
                checkAll: true,
                checkedCities:[],
                temCol:[],
                dialogFormVisible:false,
                isIndeterminate:false
            },
            propertyVisible:false,
            currentPage:1,
            pageSize: 20,
            showFields:[],
            selectedItemId:""
        }
    },
    props:{
        itemDataList:{type:Array,default:false},
        columnList:{type:Array,default:false},
        isshowicon:{type:Boolean,default:true},
        isshowOption:{type:Boolean,default:false},
        tableHeight:{type:[String,Number],default:window.innerHeight - 408},
        tableWidth:{type:[String,Number],default:'100%'},
        itemCount:{type:[String,Number]},
        isshowPage:{type:Boolean,default:true},
        loading:{type:Boolean,default:false}
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
  components: {
    ShowProperty: ShowProperty
  },
   methods:{
     // 查看内容
    showItemContent(indata){
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
      // 保存文档
    saveItem(){
      this.$refs.ShowProperty.saveItem();
    },
     //查看属性
    showItemProperty(indata) {
      let _self = this;
      _self.selectedItemId = indata.ID;
      _self.propertyVisible = true;
      setTimeout(()=>{
        if(_self.$refs.ShowProperty){
        _self.$refs.ShowProperty.myItemId = indata.ID ;
        _self.$refs.ShowProperty.loadFormInfo();
      }
      },10);
      
    },
    onSaved(indata){
       if(indata=='update')
      {
        this.$message(this.$t("message.saveSuccess"));
        this.$emit('refreshdatagrid');
      }
      else
      {
        //this.$message("新建成功!");
      }
      this.propertyVisible = false;
    },
        showFieldOption(){
            let _self=this;
            _self.showFields = [];
          
          _self.columnList.forEach(element => {
            if(element.visibleType==1){
              _self.showFields.push(element.attrName);
            }
          });
        },
        getgriditem(attrName){
            let _self = this;
            let ret =null;
            _self.columnList.forEach(element => {
                if(element.attrName==attrName){
                ret = element;
                return;
                }
            });
            return ret;
        },
        //全选按钮
        handleCheckAllChange(val) {
            this.showFields=[]
            if(val){
                this.columnList.forEach(element => {
                this.showFields.push(element.attrName)
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
        dialogFormShow(){
            this.showFieldOption();
            this.columnsInfo.dialogFormVisible = true
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
            this.columnsInfo.dialogFormVisible = false
        },
        handleCurrentChange(val) 
        {
            
            this.currentPage = val;
            //console.log('handleCurrentChange', val);
            this.$emit("pagechange", this.currentPage);
        },
        // 分页 页数改变
        handleSizeChange(val) 
        {
            this.pageSize = val;
            localStorage.setItem("docPageSize",val);
            //console.log('handleSizeChange', val);
            this.$emit("pagesizechange", this.pageSize);
        },
        sortchange(column){
            console.log(JSON.stringify(column));
            console.log(column.column.property);
            console.log(column.column.order);//ascending, descending
            this.orderBy = column.column.property+ column.column.order=="ascending"?" ASC":" DESC";
        },
        rowClick(row){
            this.$emit("rowclick", row);
        },
        selectChange(val){
            this.$emit("selectchange", val);
        }
        
    }
}
</script>
<style scoped>
.el-table--striped .el-table__body tr.el-table__row--striped td{background:#FAFAFA}.el-table--striped .el-table__body tr.el-table__row--striped.current-row td,.el-table__body tr.current-row>td,.el-table__body tr.hover-row.current-row>td,.el-table__body tr.hover-row.el-table__row--striped.current-row>td,.el-table__body tr.hover-row.el-table__row--striped>td,.el-table__body tr.hover-row>td{background-color:rgb(140, 211, 252)}
</style>