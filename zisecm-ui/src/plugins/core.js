import Vue from 'vue'

const Modules = [
	require.context("@/custom/components",true,/\.vue$/)
]

Modules.forEach(element => {
	element.keys().forEach( fileName =>{
		const componentConfig = element(fileName);
		const componentName = fileName.replace(/^\.\//,'').replace(/\.vue$/,'');		
		const component = Vue.component(componentName,componentConfig.default || componentConfig)
	})
})
