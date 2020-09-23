<template>
  <el-container>
    <el-header>
      <el-row>
          <el-form size="small" label-width="100" inline>
            <el-form-item label="名称">
              <el-select v-model="selectedName">
                <el-option v-for="item in customNames" :key="item.name" :label="item.name" :value="item.name" @click.native="onSelectChange(item)">{{item.name}}</el-option>
              </el-select>
            </el-form-item>
             <el-form-item>
                <el-button @click="createGridView">{{$t('application.new')}}</el-button>
                <el-button @click="deleteGridView">{{$t('application.delete')}}</el-button>
             </el-form-item>
          </el-form>
        </el-row>
    </el-header>
    <el-main>
      <el-row>
        <el-col :span="8">

          <el-table ref="sourceTable" :data="tables.source.data" v-bind="tables.source.attrs" @selection-change="onSelectTableDataSource">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column label="行号" type="index" width="60"></el-table-column>
            <el-table-column label="属性名" prop="label"></el-table-column>
            <el-table-column label="操作" width="60" fixed="right">
              <template slot-scope="scope">
                <el-button :plain="true" type="primary" size="small" icon="edit" @click="addItem(scope.row)">添加</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
        <el-col :span="2" class="center_buttons">
          <el-row>
            <el-col :span="24">
              <el-button @click="addItemToTarget">添加</el-button>
            </el-col>
            <el-col :span="24">
              <el-button @click="removeTagetRow">移除</el-button>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="14">
          <el-table ref="targetTable" :data="tables.target.data" v-bind="tables.target.attrs"  @selection-change="onSelectTableDataTarget">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column label="行号" type="index" width="60"></el-table-column>
            <el-table-column label="属性名" prop="label"></el-table-column>
            <el-table-column label="宽度" width="100">
              <template slot-scope="scope">
                <el-input v-model="scope.row.width"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="显示类型" width="120">
              <template slot-scope="scope">
                <el-select v-model="scope.row.visibleType">
                  <el-option label="显示" value="1"></el-option>
                  <el-option label="可选" value="2"></el-option>
                  <el-option label="隐藏" value="3"></el-option>
                </el-select>
              </template>
          </el-table-column>
          <el-table-column label="可排序" width="100">
            <template slot-scope="scope">
              <el-select v-model="scope.row.allowOrderby">
                <el-option label="是" :value="true"></el-option>
                <el-option label="否" :value="false"></el-option>
              </el-select>
            </template>
          </el-table-column>
            <el-table-column label="操作" width="150" fixed="right">
              <template slot-scope="scope">
                
                <el-button size="mini" @click="moveUp(scope.$index,scope.row)">上移</el-button>
                <el-button size="mini" @click="moveDown(scope.$index,scope.row)">下移</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </el-main>
    <el-footer style="text-align: right;">
      <el-button @click="saveCustomColumn">{{$t('application.save')}}</el-button>
      <el-button @click="cancelCustomColumn">{{$t('application.cancel')}}</el-button>
    </el-footer>
  </el-container>
</template>

<script>
export default {
  name:"EcmCustomColumns",
  props:{
    typeName:{
      type:String,default:""
    },
    gridViewName:{
      type:String,default:""
    },
    sysColumnInfo:{
      type:Array,
      default:function(){
        return []
      }
    }
  },
  data(){
    return{
      customNames:[],
      selectedName:"",
      currentLanguage:"zh-cn",
      tables:{
        source:{
          attrs:{
           
            border:true,
            height:300
          },
          data:[],
          selections:[]
        },
        target:{
          attrs:{
            
            border:true,
            height:300
          },
          data:[],
          selections:[]
        }
      }
    }
  },
  mounted(){
    this.loadCustomName()
    this.loadSysColumnInfo()
    this.tables.source.data = this.sysColumnInfo
  },
  watch: {
    '$store.state.app.language':function(nv,ov){
        this.currentLanguage=nv;        
      }
  },
  methods:{
    loadSysColumnInfo(){
      let _self = this
      let url = "/admin/getFormItems/"+this.gridViewName+"/"+this.currentLanguage
      axios.post(url).then(function(response){
        _self.tables.source.data = response.data.data
        console.log(response)
      }).catch(function(error){

      })
    },
    onSelectChange(item){
      let id = item.id
      let name = item.name
     this.loadCustomInfo(id,name)
    },
    loadCustomInfo(id,name){
      let _self=this;
      var m = new Map();
      m.set('gridId',id);
      m.set("lang", _self.currentLanguage);
      let url = "/dc/getOneEcmCustomGridViewInfo" 
      axios.post(url,JSON.stringify(m)).then(function(response) {
        if(response.data.code==1){
          _self.tables.target.data = response.data.data
          _self.selectedName = name
        }
      }).catch(function(error){
        console.log(error);
      })
    },
    saveCustomColumn(){
      let _self=this;
      if(_self.selectedName==''){
        _self.$message({ message: '名称为空！', type: 'warning' })
        return;
      }
      let datalist = this.tables.target.data
      let postArray = new Array()
      for (let index = 1; index <= datalist.length; index++) {
        let item = datalist[index-1]
        item.orderIndex=index
        let removeAttrs = ["enableChange","defaultValue","classification","maxCount","required",
        "searchable","isHide","controlType","valueList","isRepeat",
        "readOnly","parentId","minCount","widthType","queryName",
        "id","validatePolicy"]
        removeAttrs.forEach(function(attr){
          delete item[attr]
        })
        postArray.push(item)
      }
      let postData = new Map();
      postData.set("gridName",this.gridViewName)
      postData.set("NAME",this.selectedName)
      postData.set('items',postArray);
      let url = "/admin/createOrUpdateGridView"
      axios.post(url,JSON.stringify(postData)).then(function(response){
        if(response.data.code==1){
          _self.$message({showClose: true,message: "保存成功！", duration: 2000,type: "Success"});
          _self.clearData()
          _self.$emit("onClose")
        }
      }).catch(function(error){
        console.log(error)
      })
    },
    
    cancelCustomColumn(){
      this.clearData()
      this.$emit("onClose")
    },
    clearData(){
      this.$refs.targetTable.clearSelection()
      this.selectedName=""
      this.tables.target.data=[]
      this.tables.source.selections=[]
      this.tables.target.selections=[]  
    },
    loadCustomName(){
      let _self = this
      let url = "/admin/getAllGridViewsOfCurrentUser"
      let params = {
        "gridName":this.gridViewName
      }
      axios.post(url,JSON.stringify(params)).then(function(response){
        if(response.data.code==1){
          _self.customNames=response.data.data
        }
      }).catch(function(error){
        console.log(error)
      })
    },
    onSelectTableDataSource(val){
      this.tables.source.selections = val
    },
    onSelectTableDataTarget(val){
      this.tables.target.selections = val
    },
    addItem(row){
      this.tables.source.selections.push(row)
      this.addItemToTarget()
      this.tables.source.selections=[]
    },
    addItemToTarget(){
      let _self = this
      let selections = this.tables.source.selections
      let targetList = this.tables.target.data
      selections.forEach(function(item){
          let isExist = false
          targetList.forEach(function(sub){
            if(item.attrName == sub.attrName){
              isExist = true
            }
          })
          if(isExist==false){
            item.width="200"
            item.visibleType="1"
            item.allowOrderby=true
            _self.tables.target.data.push(item)
          }
      })
    },
    removeTagetRow(){
      let datalist = this.tables.target.data
      let selection = this.tables.target.selections
      let itemlist = new Array()
      selection.forEach(function(item){
        itemlist.push(item.attrName)
      })
      for (let index = datalist.length-1 ; index >=0 ; index--) {
        const atname = datalist[index].attrName
        if(itemlist.includes(atname)){
          datalist.splice(index,1)
        }
      }
      this.tables.target.data = datalist
    },
    moveUp(index, row){
      if (index == 0) {
        this.$message({
            message: '处于顶端，不能继续上移',
            type: 'warning'
          });
      }else{
        let upData = this.tables.target.data[index - 1]
        this.tables.target.data.splice(index - 1, 1)
        this.tables.target.data.splice(index, 0, upData)

      }
    },
    moveDown(index, row){
      if (index === (this.tables.target.data.length - 1)) {
        this.$message.error('不能继续向下移动')
      } else {
        const downData = this.tables.target.data[index + 1]
        this.tables.target.data.splice(index + 1, 1)
        this.tables.target.data.splice(index, 0, downData)
      }
    },
    createGridView(){
      let _self = this
      this.$prompt('请输入列名称', '新建列名', {
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }).then(({ value }) => {
          _self.selectedName = value
          let m = new Map();
          m.set('gridName',_self.gridViewName);
          m.set('NAME',_self.selectedName);
          let url = "/admin/createOrUpdateGridView"
          axios.post(url,JSON.stringify(m)).then(function(response){
            if(response.data.code==1) {
              _self.$message({showClose: true,message: "保存成功！",duration: 2000,type: "Success"});
              _self.loadCustomName();
            }
          })

          this.$message({
            type: 'success',
            message: '你的邮箱是: ' + value
          });
        }).catch();
    },
    deleteGridView(){
      let _self = this
      let m = new Map();
      m.set('gridName',this.gridViewName);
      m.set('NAME',this.selectedName);
      m.set("lang", this.currentLanguage);
      let url = "/admin/deleteCustomGridView"
      axios.post(url,JSON.stringify(m)).then(function(response){
        if(response.data.code==1){
          _self.$message({showClose: true,message: "删除成功！",duration: 2000,type: "Success"});
          _self.selectedName=""
          _self.loadCustomName()
        }
      }).catch(function(error){})
    }
  }
}
</script>

<style scoped>
.center_buttons .el-row .el-col{
  text-align: center;
  display: inline-block;
  vertical-align: middle;
}
</style>