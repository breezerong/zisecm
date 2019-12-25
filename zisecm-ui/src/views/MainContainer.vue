<template>
  <el-container>
    <el-aside width="200px">
      <div v-bind:style="{height: menuHeight +'px'}">
        <!--左侧导航-->
        <el-menu default-active="1" class="menu-wrapper" :open="1" >
          <template v-for="item in dataList.menuItems">
            <template v-if="item.submenus && item.url==null">
              <el-submenu :index="item.orderIndex+''" :key="item.orderIndex+''">
                <template slot="title">
                  <i v-if="item.icon!=null" :class="item.icon"></i>
                  {{item.label}}
                </template>
                <template v-for="sitem in item.submenus">
                  <el-menu-item
                    :index="sitem.orderIndex+''"
                    :key="sitem.orderIndex+''"
                    @click="clickRouter(sitem.url)"
                  >
                    <i v-if="sitem.icon!=null" :class="sitem.icon"></i>
                    {{sitem.label}}
                  </el-menu-item>
                </template>
              </el-submenu>
            </template>
            <template v-else>
              <el-menu-item :index="item.orderIndex+''" :key="item.orderIndex+''" @click="clickRouter(item.url)">
                <i v-if="item.icon!=null" :class="item.icon"></i>
                <template slot="title">{{item.label}}</template>
              </el-menu-item>
            </template>
          </template>
        </el-menu>
      </div>
    </el-aside>
    <el-main>
      <transition name="fade" mode="out-in">
        <router-view :key="$route.fullPath"></router-view>
      </transition>
    </el-main>
  </el-container>
</template>
<script>
export default {
  name: "MainContainer",
  data() {
    return {
      loading: false,
      menuName: "",
      dataList: [],
      menuHeight: window.innerHeight - 45
    };
  },
  mounted() {
    this.menuName = this.$route.query.name;
    this.loadMenu();
  },
  methods: {
    loadMenu() {
      let _self = this;
      _self.loading = true;
      var m = new Map();
      m.set("name", _self.menuName);
      m.set("lang", _self.getLang());
      axios
        .post("/memu/getMyMenu", JSON.stringify(m))
        .then(function(response) {
          //console.log(response);
          if (response.data.code == 1) {
            _self.dataList = response.data.data;
          }
          console.log(JSON.stringify(_self.dataList));
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    },
    clickRouter(pathVal){
      let _self = this;
      _self.$router.push({ 
        path: pathVal,
      });
    }
  }
};
</script>

<style scoped>
body > .el-container {
  padding: 0px;
  margin-top: 0px;
  margin-left: 0px;
  margin-right: 0px;
  margin-bottom: 0px;
}
element.style {
  padding-left: 20px;
  color: rgb(64, 158, 255);
  background-color: rgb(48, 65, 86);
}

li{
  
  width:100%;
  text-align: left;
}
i{
  font-size : 20px !important;
}
</style>
