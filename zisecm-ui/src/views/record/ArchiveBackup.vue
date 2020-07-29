<template>
      <el-container>
        <el-aside width="200px">
          <div v-bind:style="{height: menuHeight +'px'}">
          <!--左侧导航-->
          <el-menu default-active="201" class="el-menu-vertical-ecm"  :open="201">
                <div>
                  <el-submenu index="200">
                    <template slot="title">
                      <i class="el-icon-menu"></i>
                      <span>处理状态</span>
                    </template>
                    <el-menu-item index="201" @click="changeTab('新建')">
                      <i class="el-icon-folder-add"></i>
                      <span slot="title">{{$t('application.new')}}</span>
                    </el-menu-item>
                    <el-menu-item index="202" @click="changeTab('制作中')">
                      <i class="el-icon-refresh-left"></i>
                      <span slot="title">制作中</span>
                    </el-menu-item>
                    <el-menu-item index="203" @click="changeTab('已完成')">
                      <i class="el-icon-folder-checked"></i>
                      <span slot="title">已完成</span>
                    </el-menu-item>
                    
                  </el-submenu>
                </div>
          </el-menu>
          </div>
        </el-aside>
        <el-main>
          <transition name="fade" mode="out-in">
            <ArchiveBackupNew v-if="backupStatus=='新建'" ref="_new"></ArchiveBackupNew>
            <ArchiveBackupPending v-if="backupStatus=='制作中'" ref="_pending"></ArchiveBackupPending>
            <ArchiveBackupCompleted v-if="backupStatus=='已完成'" ref="_completed"></ArchiveBackupCompleted>
          </transition>
        </el-main>
      </el-container>
</template>
<script type="text/javascript">
import ArchiveBackupNew from '@/views/record/ArchiveBackupNew'
import ArchiveBackupPending from '@/views/record/ArchiveBackupPending'
import ArchiveBackupCompleted from '@/views/record/ArchiveBackupCompleted'
export default {
  name: "archiveBackup",
  permit: 1,
  data() {
        return {
          backupStatus:"新建",
          menuHeight: window.innerHeight - 70
        }
        
    },
    components: {
        ArchiveBackupNew:ArchiveBackupNew,
        ArchiveBackupPending:ArchiveBackupPending,
        ArchiveBackupCompleted:ArchiveBackupCompleted
    },
    mounted(){
      
    },
    methods: {
      changeTab(val){
        let _self=this;
        _self.backupStatus=val;
        setTimeout(()=>{
        
        if(val=='新建'){
          
          _self.$refs._new.loadGridInfo();
          _self.$refs._new.loadGridData();
        }else if(val=='制作中'){
          _self.$refs._pending.loadGridInfo();
          _self.$refs._pending.loadGridData();
        }else if(val=='已完成'){
          _self.$refs._completed.loadGridInfo();
          _self.$refs._completed.loadGridData();
        }

      },10);
        
        
      }
    }
};
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
  margin: 0 10px;
}
a {
  color: #42b983;
}
body,html{
			height:100%;
			margin:0px;
			padding:0px;
			overflow:hidden;
		}
    .left,.right{
			width:100%;
      }
      .middle{
        width:5%;
      }
		.left,.middle,.right{
			/* width:200px; */
			/* height:100px; */
			/* background-color:rgb(34,124,134); */
			float:left;
			height:100%;
		}
</style>