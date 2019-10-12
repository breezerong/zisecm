<template>
  <el-container>
    <el-container>
      <el-aside width="180px">
        <div v-bind:style="{height: menuHeight +'px'}">
          <!--左侧导航-->
          <el-menu default-active="1" class="el-menu-vertical-ecm" :default-openeds="opens" style="background-color:#D3DCE6">
            <el-menu-item index="1">
              <i class="el-icon-document"></i>
              <span slot="title">
                <router-link to="/search/fulltextsearch">{{$t('menu.fullTextSearch')}}</router-link>
              </span>
            </el-menu-item>
            <el-submenu index="10">
              <template slot="title">
                <i class="el-icon-document-copy"></i>
                <span>{{$t('menu.cardSearch')}}</span>
              </template>
              <div v-for="(item,index) in cardList">
                <el-menu-item :index="(index+100)+''">
                  <i class="el-icon-tickets"></i>
                  <span slot="title">
                    <router-link :to="{path:'/search/cardSearch',query:{id:item.id}}">{{item.label}}</router-link>
                  </span>
                </el-menu-item>
              </div>
            </el-submenu>
            <el-menu-item index="3">
              <i class="el-icon-s-grid"></i>
              <span slot="title">
                <router-link to="/search/advsearch">{{$t('menu.advSearch')}}</router-link>
              </span>
            </el-menu-item>
            <el-menu-item index="4">
              <i class="el-icon-user"></i>
              <span slot="title">
                <router-link to="/search/mysearch">{{$t('menu.mySearch')}}</router-link>
              </span>
            </el-menu-item>
          </el-menu>
        </div>
      </el-aside>
      <el-main>
        <transition name="fade" mode="out-in">
          <router-view :key="$route.fullPath"></router-view>
        </transition>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
export default {
  name: "SearchCenter",
  data() {
    return {
      opens: ["10"],
      username: "",
      cardList: [],
      clientPermission: 0,
      menuHeight: window.innerHeight - 64
    };
  },
  created() {
    let _self = this;
    var user = sessionStorage.getItem("access-user");
    if (user) {
      _self.clientPermission = sessionStorage.getItem(
        "access-clientPermission"
      );
    }
    _self.$router.push({ path: "/search/fulltextsearch" });
    _self.loadCards();
  },
  methods: {
    loadCards()
    {
      let _self = this;
      _self.loading = true;
      
      //alert(_self.parentid);
      _self.axios({
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        method: 'post',
        data: _self.getLang(),
        url: '/zisecm/search/getCardSearchAll'
      })
        .then(function(response) {
          _self.cardList = response.data.data;
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

<style>
body > .el-container {
  padding: 0px;
  margin-top: 0px;
  margin-left: 0px;
  margin-right: 0px;
  margin-bottom: 0px;
}
.el-submenu .el-menu-item {
    height: 32px;
    line-height: 32px;
    padding: 0 36px;
    min-width: 200px;
}
</style>