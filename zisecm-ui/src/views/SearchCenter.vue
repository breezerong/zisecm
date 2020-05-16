<template>
  <el-container>
    <el-container>
      <el-aside :width="asideWidth" class="">
        <div style="text-align:left;cursor:pointer;" @click="handleClose">
          <i v-if="isCollapse==false" class="el-icon-s-fold"></i>
          <i v-else class="el-icon-s-unfold"></i>
        </div>
        <div v-bind:style="{height: menuHeight +'px'}">
          <!--左侧导航-->
          <el-menu
            default-active="$route.path"
            :collapse="isCollapse"
            :default-openeds="opens"
            style="background-color:#6959CD"
            text-color="white"
      		  active-text-color="#42b983"
            ref = "searchMenu"
          >

            <router-link to="/search/fulltextsearch">
              <el-menu-item index="/search/fulltextsearch">
                <i class="el-icon-document  menu-white"></i>
                <span slot="title"  style="font-size:16px">{{$t('route.fullTextSearch')}}</span>
              </el-menu-item>
            </router-link>
            <el-submenu index="1000">
              <template slot="title">
                <i class="el-icon-document-copy  menu-white"></i>
                <span  style="font-size:16px">{{$t('route.cardSearch')}}</span>
              </template>
              <template v-for="(item,index) in cardList">
                <router-link :to="{path:'/search/cardSearch',query:{id:item.id}}" :key="item.id">
                  <el-menu-item :index="'/search/cardSearch?id='+item.id">
                    <i class="el-icon-tickets  menu-white"></i>
                    <span slot="title">{{item.label}}</span>
                  </el-menu-item>
                </router-link>
              </template>
            </el-submenu>
            <router-link to="/search/advsearch">
            <el-menu-item index="/search/advsearch">
              <i class="el-icon-s-grid  menu-white"></i>
              <span slot="title"  style="font-size:16px">
                {{$t('route.advSearch')}}
              </span>
            </el-menu-item>
            </router-link>
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
      opens: ["1000"],
      username: "",
      cardList: [],
      clientPermission: 0,
      menuHeight: window.innerHeight - 120,
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
    // _self.$router.push({ path: "/search/fulltextsearch" });
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

<style scoped>
body > .el-container {
  padding: 0px;
  margin-top: 0px;
  margin-left: 0px;
  margin-right: 0px;
  margin-bottom: 0px;
}

#app .sidebar-container .nest-menu .el-submenu>.el-submenu__title, #app .sidebar-container .el-submenu .el-menu-item {
   
    background-color: #7171C6 !important;
}

.el-menu, .el-menu-item{
  background-color: #7171C6 !important;
}
.el-submenu {
    list-style: none;
    margin: 0;
    padding-left: 0;
    background-color: #7171C6 !important;
}
.el-menu-item:focus, .el-menu-item:hover {
  background-color: #5a5a9c !important;
}
.el-main {
    display: block;
    -webkit-box-flex: 1;
    -ms-flex: 1;
    flex: 1;
    -ms-flex-preferred-size: auto;
    flex-basis: auto;
    overflow: auto;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    padding: 2px;
}
</style>