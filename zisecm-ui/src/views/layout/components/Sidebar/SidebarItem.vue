<template>
  <div class="menu-wrapper">
    <template v-for="item in dataList.menuItems">
      <template v-if="item.submenus && item.url==null">
        <el-submenu :index="item.id+''" :key="item.id">
          <template slot="title">{{item.label}}</template>
          <template v-for="sitem in item.submenus">
            <router-link :to="sitem.url">
              <el-menu-item :index="sitem.url" :key="sitem.id" :class="{'submenu-title-noDropdown':!isNest}">
                  <svg-icon v-if="sitem.icon!=null" :icon-class="sitem.icon"></svg-icon>
                  <!-- <i v-if="sitem.icon!=null" :class="sitem.icon  + ' menu-white'"></i> -->
                  {{sitem.label}}
              </el-menu-item>
            </router-link>
          </template>
        </el-submenu>
      </template>
      <template v-else>
        <router-link :to="item.url">
          <el-menu-item :index="item.url" :key="item.id+'_e'" :class="{'submenu-title-noDropdown':!isNest}">
            <!-- <i v-if="item.icon!=null" :class="item.icon + ' menu-white'"></i> -->
            <svg-icon v-if="item.icon!=null" :icon-class="item.icon"></svg-icon>
            {{item.label}}
          </el-menu-item>
        </router-link>
      </template>
    </template>
    <AdminMenu :isNest="isNest"></AdminMenu>
  </div>
</template>

<script>
import { generateTitle } from "@/utils/i18n";
import AdminMenu from "./AdminMenu";
export default {
  name: "SidebarItem",
  components: {
    AdminMenu: AdminMenu
  },
  data() {
    return {
      dataList: []
    };
  },
  props: {
    isNest: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    generateTitle,
    hasOneShowingChildren(children) {
      const showingChildren = children.filter(item => {
        return !item.hidden;
      });
      if (showingChildren.length === 1) {
        return true;
      }
      return false;
    },
    loadMenu() {
      let _self = this;
      var m = new Map();
      m.set("name", "TopMenu");
      m.set("lang", _self.getLang());
      axios
        .post("/memu/getMyMenu", JSON.stringify(m))
        .then(function(response) {
          //console.log(response);
          if (response.data.code == 1) {
            _self.dataList = response.data.data;
          }
          //console.log(_self.dataList);
          _self.loading = false;
        })
        .catch(function(error) {
          console.log(error);
          _self.loading = false;
        });
    }
  },
  created() {
    this.loadMenu();
  }
};
</script>
<style scoped>
</style>