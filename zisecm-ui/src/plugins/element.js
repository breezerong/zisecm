import Vue from 'vue'
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import wlGantt from 'wl-gantt'
import 'wl-gantt/lib/wl-gantt.css'

Vue.use(Element)
Vue.use(wlGantt)
Vue.prototype.onHeaderDragend = function(newWidth,oldWidth,column,event){
	if(oldWidth<120&&newWidth<oldWidth){
	  column.width = oldWidth;
	  return true;
	}
	if(newWidth<120){
	  column.width = 120;
	  return false;
	}
	column.width = newWidth;
	return true;
  }