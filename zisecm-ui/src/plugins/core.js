import Vue from 'vue'

/**
* 定义组件获取范围
* context(<组件获取位置>,<是否递归读取>,<组件获取后缀>)
*/
const Modules = [
	require.context("@/custom/components",true,/\.vue$/)
]


/**
* 组件全局自动注册
*/
Modules.forEach(element => {
	element.keys().forEach( fileName =>{
		const componentConfig = element(fileName);
		const componentName = fileName.replace(/^\.\//,'').replace(/\.vue$/,'');		
		const component = Vue.component(componentName,componentConfig.default || componentConfig)
	})
})


