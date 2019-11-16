<template>
  <div>
    <el-table
                :data="dataList"
                border
                height="400"
                v-loading="loading"
                style="width: 100%">
      <el-table-column
      type="selection"
      width="40">
      </el-table-column>
        <el-table-column label="" type="index" width="60">
      </el-table-column>
      <div v-for="(citem,idx) in gridList">
        <div v-if="citem.visibleType==1">
          <div v-if="(citem.width+'').indexOf('%')>0">
            <el-table-column :label="citem.label" :minwidth="citem.width">
              <template slot-scope="scope">
                <span>{{scope.row[idx].value}}</span>
              </template>
            </el-table-column>
          </div>
          <div v-else>
            <el-table-column :label="citem.label" :width="citem.width">
              <template slot-scope="scope">
                <span>{{scope.row[idx].value}}</span>
              </template>
            </el-table-column>
          </div>
        </div>
      </div>
    </el-table>
     <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[10, 20, 50, 100, 200]"
      :page-size="20"
      layout="total, sizes, prev, pager, next, jumper"
      :total="400">
    </el-pagination>
  </div>
</template>

<script type="text/javascript">

export default {
  name: "GridViewItemCheck",
  permit: 9,
  data() {
    return {
      dataList: [],
      gridList: [],
      loading: false,
      currentPage: 2,
      tableHeight: window.innerHeight - 98,
      form: {
        name: "",
        description: "",
        value: "",
        visibleType: "1",
        width: "120",
        orderIndex: 1
      },
    };
  },
  props: {
    parentgridid:""
  },
   created(){ 
     if(this.$route.query.parentid)
     {
       this.parentgridid = this.$route.query.parentid;
     }
     this.loaddata();
    },
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    loaddata()
    {
      let _self = this;
      _self.loading = true;
      
      //alert(_self.parentid);
      axios.post('/admin/getGridViewItem',_self.parentgridid)
        .then(function(response) {
          _self.gridList = response.data.data;
          _self.loading = false;
          _self.refreshData();
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    refreshData() {
      var i;
      let tab = this.gridList;
      this.dataList = [];
      for(i=0;i<10;i++)
      {
         var j;
         var rowdata=[];
         for (j in tab) {
           var obj = {
              name:"",
              value:""
           };
           obj.name = tab[j].attrName; // + " " + i + ":" + j;
           obj.value = tab[j].label; // + " " + i + ":" + j;

           rowdata.push(obj);
         }
         this.dataList.push(rowdata);
      }
     console.log(JSON.stringify(this.dataList));
    },
    search() {
      let _self = this;
      let tab = _self.dataListFull;
      _self.dataList = [];
      var i;
      if (_self.inputkey != "" || _self.parentid != "") {
        for (i in tab) {
          if (_self.inputkey != "") {
            if (
              tab[i].attrName.indexOf(_self.inputkey) >= 0 ||
              tab[i].label.indexOf(_self.inputkey) >= 0
            ) {
              if (_self.parentid != "") {
                if (tab[i].parentId == _self.parentid) {
                  _self.dataList.push(tab[i]);
                }
              } else {
                _self.dataList.push(tab[i]);
              }
            }
          } else if (_self.parentid != "") {
            //_self.$message(tab[i].parentId+","+_self.parentid);
            if (tab[i].parentId == _self.parentid) {
              _self.dataList.push(tab[i]);
            }
          }
        }
      } else {
        _self.dataList = _self.dataListFull;
      }
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
