<template>
  <el-container>
    <el-container>
      <el-aside :width="asideWidth">
        <div style="text-align:left;cursor:pointer;" @click="handleClose">
          <i v-if="isCollapse==false" class="el-icon-s-fold"></i>
          <i v-else class="el-icon-s-unfold"></i>
        </div>
        <div v-bind:style="{height: menuHeight +'px'}">
          <!--左侧导航-->
          <el-menu
            default-active="1"
            class="el-menu-vertical-ecm"
            :collapse="isCollapse"
            :default-openeds="opens"
            style="background-color:#D3DCE6"
            ref = "searchMenu"
          >
            <router-link to="/search/fulltextsearch">
              <el-menu-item index="1">
                <i class="el-icon-document"></i>
                <span slot="title"  style="font-size:16px">{{$t('route.fullTextSearch')}}</span>
              </el-menu-item>
            </router-link>
            <el-submenu index="10">
              <template slot="title">
                <i class="el-icon-document-copy"></i>
                <span  style="font-size:16px">{{$t('route.cardSearch')}}</span>
              </template>
              <div v-for="(item,index) in cardList" :key="'C_'+index">
                <router-link :to="{path:'/search/cardSearch',query:{id:item.id}}">
                  <el-menu-item :index="(index+100)+''" @click="clickRouter(item.id)">
                    <i class="el-icon-tickets"></i>
                    <span slot="title">{{item.label}}</span>
                  </el-menu-item>
                </router-link>
              </div>
            </el-submenu>
            <el-menu-item index="3">
              <i class="el-icon-s-grid"></i>
              <span slot="title"  style="font-size:16px">
                <router-link to="/search/advsearch">{{$t('route.advSearch')}}</router-link>
              </span>
            </el-menu-item>
            <!--
            <el-menu-item index="4">
              <i class="el-icon-user"></i>
              <span slot="title">
                <router-link to="/search/mysearch">{{$t('menu.mySearch')}}</router-link>
              </span>
            </el-menu-item>
            -->
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
      menuHeight: window.innerHeight - 85,
      isCollapse: false,
      asideWidth: "160px"
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
    handleClose() {
      this.isCollapse = !this.isCollapse;
      this.asideWidth = this.isCollapse ? "50px" : "160px";
      if(!this.isCollapse){
        this.$refs.searchMenu.open("10");
      }
    },
    clickRouter(val) {
      let _self = this;
      _self.$router.push({
        path: "/search/cardSearch",
        query: { id: val }
      });
    },
    loadCards() {
      let _self = this;
      _self.loading = true;

      //alert(_self.parentid);
      axios
        .post("/search/getCardSearchAll", _self.getLang())
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