<template>
      <el-container>
        <el-aside width="200px">
          <div v-bind:style="{height: menuHeight +'px'}">
          <!--左侧导航-->
          <el-menu default-active="201" class="el-menu-vertical-ecm"  :open="200">
                <div>
                  <el-submenu index="200">
                    <template slot="title">
                      <i class="el-icon-menu"></i>
                      <span>借阅单状态</span>
                    </template>
                    <el-menu-item index="201">
                      <i class="el-icon-caret-right"></i>
                      <span slot="title"><router-link to="/record/archiveoutgoing/archivepending">待入库</router-link></span>
                    </el-menu-item>
                    <el-menu-item index="202">
                      <i class="el-icon-caret-right"></i>
                      <span slot="title"><router-link to="/managercenter/actionmanager">待出库</router-link></span>
                    </el-menu-item>
                    <el-menu-item index="203">
                      <i class="el-icon-caret-right"></i>
                      <span slot="title"><router-link to="/managercenter/menumanager">已完成</router-link></span>
                    </el-menu-item>
                    
                  </el-submenu>
                </div>
          </el-menu>
          </div>
        </el-aside>
        <el-main>
          <transition name="fade" mode="out-in">
            <router-view></router-view>
          </transition>
        </el-main>
      </el-container>
</template>
<script type="text/javascript">
export default {
  name: "archiveoutgoing",
  permit: 1,
  data() {
        return {
            menuHeight: window.innerHeight -64
        }
        
    },
    methods: {

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