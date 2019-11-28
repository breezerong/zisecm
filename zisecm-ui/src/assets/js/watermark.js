(function(root,factory){if(typeof define==='function'&&define.amd){define([],factory())}else if(typeof module==='object'&&module.exports){module.exports=factory()}else{root['watermark']=factory()}}(this,function(){var watermark={};var loadMark=function(settings){var defaultSettings={watermark_id:'wm_div_id',watermark_prefix:'mask_div_id',watermark_txt:"档案管理系统",watermark_x:20,watermark_y:20,watermark_rows:0,watermark_cols:0,watermark_x_space:100,watermark_y_space:50,watermark_font:'微软雅黑',watermark_color:'black',watermark_fontsize:'18px',watermark_alpha:0.15,watermark_width:100,watermark_height:100,watermark_angle:15,watermark_parent_width:0,watermark_parent_height:0,watermark_parent_node:null};if(arguments.length===1&&typeof arguments[0]==="object"){var src=arguments[0]||{};for(key in src){if(src[key]&&defaultSettings[key]&&src[key]===defaultSettings[key])continue;else if(src[key]||src[key]===0)defaultSettings[key]=src[key]}}var watermark_element=document.getElementById(defaultSettings.watermark_id);if(watermark_element){var _parentElement=watermark_element.parentNode;if(_parentElement){_parentElement.removeChild(watermark_element)}}var page_width=Math.max(document.body.scrollWidth,document.body.clientWidth)-defaultSettings.watermark_width/2;var page_height=Math.max(document.body.scrollHeight,document.body.clientHeight,document.documentElement.clientHeight)-defaultSettings.watermark_height/2;var setting=arguments[0]||{};var parentEle=defaultSettings.watermark_parent_node;var page_offsetTop=0;var page_offsetLeft=0;if(setting.watermark_parent_width||setting.watermark_parent_height){setting.watermark_parent_width?(page_width=setting.watermark_parent_width-defaultSettings.watermark_width/2):(defaultSettings.watermark_parent_node)?(page_width=parentEle.offsetWidth-defaultSettings.watermark_width/2):void 0;setting.watermark_parent_height?(page_height=setting.watermark_parent_height-defaultSettings.watermark_height/2):(defaultSettings.watermark_parent_node)?(page_height=Math.max(parentEle.offsetHeight,parentEle.scrollHeight)-defaultSettings.watermark_height/2):void 0;if(parentEle){page_offsetTop=parentEle.offsetTop||0;page_offsetLeft=parentEle.offsetLeft||0;defaultSettings.watermark_x=defaultSettings.watermark_x+page_offsetLeft;defaultSettings.watermark_y=defaultSettings.watermark_y+page_offsetTop}}else{if(parentEle){page_offsetTop=parentEle.offsetTop||0;page_offsetLeft=parentEle.offsetLeft||0;page_width=parentEle.offsetWidth-defaultSettings.watermark_width/2||0;page_height=(Math.max(parentEle.offsetHeight,parentEle.scrollHeight)-defaultSettings.watermark_height/2)||0;defaultSettings.watermark_x=defaultSettings.watermark_x+page_offsetLeft;defaultSettings.watermark_y=defaultSettings.watermark_y+page_offsetTop}}var otdiv=document.getElementById(defaultSettings.watermark_id);var shadowRoot=null;if(!otdiv){otdiv=document.createElement('div');otdiv.id=defaultSettings.watermark_id;otdiv.style.pointerEvents="none";if(typeof otdiv.createShadowRoot==='function'){shadowRoot=otdiv.createShadowRoot()}else{shadowRoot=otdiv}var nodeList=document.body.children;var index=Math.floor(Math.random()*(nodeList.length-1));if(nodeList[index]){document.body.insertBefore(otdiv,nodeList[index])}else{document.body.appendChild(otdiv)}}else if(otdiv.shadowRoot){shadowRoot=otdiv.shadowRoot}if(defaultSettings.watermark_cols==0||(parseInt(defaultSettings.watermark_x+defaultSettings.watermark_width*defaultSettings.watermark_cols+defaultSettings.watermark_x_space*(defaultSettings.watermark_cols-1))>page_width)){defaultSettings.watermark_cols=parseInt((page_width-defaultSettings.watermark_x+page_offsetLeft)/(defaultSettings.watermark_width+defaultSettings.watermark_x_space));defaultSettings.watermark_x_space=parseInt((page_width-defaultSettings.watermark_x+page_offsetLeft-defaultSettings.watermark_width*defaultSettings.watermark_cols)/(defaultSettings.watermark_cols-1))}if(defaultSettings.watermark_rows==0||(parseInt(defaultSettings.watermark_y+defaultSettings.watermark_height*defaultSettings.watermark_rows+defaultSettings.watermark_y_space*(defaultSettings.watermark_rows-1))>page_height)){defaultSettings.watermark_rows=parseInt((page_height-defaultSettings.watermark_y+page_offsetTop)/(defaultSettings.watermark_height+defaultSettings.watermark_y_space));defaultSettings.watermark_y_space=parseInt(((page_height-defaultSettings.watermark_y+page_offsetTop)-defaultSettings.watermark_height*defaultSettings.watermark_rows)/(defaultSettings.watermark_rows-1))}var x;var y;for(var i=0;i<defaultSettings.watermark_rows;i++){y=defaultSettings.watermark_y+(defaultSettings.watermark_y_space+defaultSettings.watermark_height)*i;for(var j=0;j<defaultSettings.watermark_cols;j++){x=defaultSettings.watermark_x+(defaultSettings.watermark_width+defaultSettings.watermark_x_space)*j;var mask_div=document.createElement('div');var oText=document.createTextNode(defaultSettings.watermark_txt);mask_div.appendChild(oText);mask_div.id=defaultSettings.watermark_prefix+i+j;mask_div.style.webkitTransform="rotate(-"+defaultSettings.watermark_angle+"deg)";mask_div.style.MozTransform="rotate(-"+defaultSettings.watermark_angle+"deg)";mask_div.style.msTransform="rotate(-"+defaultSettings.watermark_angle+"deg)";mask_div.style.OTransform="rotate(-"+defaultSettings.watermark_angle+"deg)";mask_div.style.transform="rotate(-"+defaultSettings.watermark_angle+"deg)";mask_div.style.visibility="";mask_div.style.position="absolute";mask_div.style.left=x+'px';mask_div.style.top=y+'px';mask_div.style.overflow="hidden";mask_div.style.zIndex="9999999";mask_div.style.opacity=defaultSettings.watermark_alpha;mask_div.style.fontSize=defaultSettings.watermark_fontsize;mask_div.style.fontFamily=defaultSettings.watermark_font;mask_div.style.color=defaultSettings.watermark_color;mask_div.style.textAlign="center";mask_div.style.width=defaultSettings.watermark_width+'px';mask_div.style.height=defaultSettings.watermark_height+'px';mask_div.style.display="block";mask_div.style['-ms-user-select']="none";shadowRoot.appendChild(mask_div)}}};watermark.init=function(settings){window.addEventListener('load',function(){loadMark(settings)});window.addEventListener('resize',function(){loadMark(settings)});window.addEventListener('DOMContentLoaded',function(){loadMark(settings)})};watermark.load=function(settings){loadMark(settings)};return watermark}));