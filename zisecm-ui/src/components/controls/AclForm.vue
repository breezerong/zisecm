<template>
  <div>
    <el-row>
      <el-col :span="6">
        <el-input
          v-model="inputKey"
          :placeholder="$t('application.placeholderSearch')"
          @change="search"
          prefix-icon="el-icon-search"
        ></el-input>
      </el-col>
      <el-col :span="1">
        <el-button type="primary" plain icon="el-icon-edit" @click="newItem()">{{$t('application.new')}}</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-table
      :data="permitList"
      border
      height="360"
      v-loading="loading"
      size="mini"
      style="width: 100%"
    >
      <el-table-column :label="$t('field.indexNumber')" type="index" width="60"></el-table-column>
       <el-table-column :label="$t('field.type')"  width="60">
          <template slot-scope="scope">
            <i v-if="scope.row.targetType==1" class="el-icon-user" :title="$t('application.user')"></i>
            <i v-if="scope.row.targetType==2" class="el-icon-ecm-friend" :title="$t('application.role')"></i>
          </template>
        </el-table-column>
      <el-table-column :label="$t('field.name')" prop="targetName" min-width="20%"></el-table-column>
      <el-table-column :label="$t('field.permission')" prop="permission" :formatter="permitFormatter" min-width="10%"></el-table-column>
      <el-table-column :label="$t('field.expireDate')" prop="expireDate" :formatter="dateFormatter" width="160"></el-table-column>
      <el-table-column :label="$t('application.operation')" width="160">
        <template slot-scope="scope">
          <el-button
            :plain="true"
            type="primary"
            size="small"
            icon="edit"
            @click="showItem(scope.row)"
          >{{$t('application.property')}}</el-button>
          <el-button
            :plain="true"
            type="danger"
            size="small"
            icon="delete"
            @click="delItem(scope.row)"
          >{{$t('application.delete')}}</el-button>
        </template>
      </el-table-column>
    </el-table>
      </el-col>
    </el-row>
  </div>
</template>

<script>
//动态表单
import UserSelectInput from '@/components/controls/UserSelectInput'
import RoleSelectInput from '@/components/controls/RoleSelectInput'

export default {
  data(){
    return {
      aclData:"",  //Acl信息
      aclId: "",
      inputKey:"",
      permitList:[],
      permitListFull:[],
      loading: false
    }
  },
  components: {
    UserSelectInput:UserSelectInput,
    RoleSelectInput:RoleSelectInput
  },
  props:{
    name:{//Acl 名称
      type:String
    },
    id:{//Acl Id
      type:String
    }
  },
  methods:{
    permitFormatter(row, column) {
      //console.log(column);
      let data = row[column.property];
      return this.$t('application.objectPermission'+data);
    },
    dateFormatter(row, column) {
      //console.log(column);
      let datetime = row[column.property];
      return this.datetimeFormat(datetime);
    },
    loadAcl(){
      let _self = this;
      let aclUrl = "/zisecm/acl/getAclById";
      let pdata = _self.id;
      if( !_self.id && _self.name){
        aclUrl = "/zisecm/acl/getAclByName";
        pdata = _self.name;
      }
      
      var m = new Map();
      m.set('itemInfo',name);//ID 或类型
      m.set('lang',_self.getLang());
      
      //console.log(name);
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: "post",
        data: pdata,
        url: aclUrl
      }).then(function(response){
        //console.log(response.data.data);
        _self.aclData = response.data.data;
        _self.refreshData();

      });
    },
    /*
    
    */
    refreshData(){
      let _self = this;
      //console.log(name);
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: "post",
        data: _self.aclData.id,
        url: "/zisecm/acl/getPermitsById"
      }).then(function(response){
        console.log(JSON.stringify(response.data.data));
        _self.permitList = response.data.data;
        _self.permitListFull = response.data.data;
      });
    },
    search(){
      let _self = this;
      _self.permitList = _self.permitListFull.filter(function(item){
        //console.log(item);
        return item.targetName.match(_self.inputKey);
      });
    }
     
  },
  mounted(){
    this.loadAcl();
  },
  created(){
   
  }
}
</script>

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
}
li {
  /* display: inline-block; */
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>