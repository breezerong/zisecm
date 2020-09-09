import Vue from 'vue'
Vue.prototype.dateFormat = function(val){
    var datetime = val;
    //console.log(val);
    if (datetime){
      if(typeof datetime === 'string'){
        //console.log(datetime);
        datetime = datetime.replace('+0000','').trim();
        datetime = datetime.replace('.0000000','').trim();
        //console.log(datetime);
        var regexp=new RegExp(/(\.\d{3})/g);
        datetime = datetime.replace(regexp,"");
        //console.log(datetime);
        datetime = datetime.replace(/-/g,'/').replace(/T|Z/g,' ');
        
        datetime = new Date(datetime);
        // console.log(datetime);
      
      }else if(typeof datetime === 'object'){
        datetime = new Date(datetime);
      }
      let y = datetime.getFullYear() + "-"
      let mon = datetime.getMonth() + 1
      mon = mon<10 ? '0' + mon : mon
      mon = mon + "-"
      let d = datetime.getDate()
      d = d<10 ? '0' + d : d
      return y + mon + d 
    }
    return ""
  }
  Vue.prototype.datetimeFormat = function(val){
    let datetime = val;
    
    if (datetime){
      if(typeof datetime === 'string'){
        //console.log(datetime);
        datetime = datetime.replace('+0000','').trim();
        datetime = datetime.replace('.0000000','').trim();
        //console.log(datetime);
        var regexp=new RegExp(/(\.\d{3})/g);
        datetime = datetime.replace(regexp,"");
        //console.log(datetime);
        datetime = datetime.replace(/-/g,'/').replace(/T|Z/g,' ');
        //console.log(datetime);
        datetime = new Date(datetime);
        
      }else if(typeof datetime === 'object'){
        datetime = new Date(datetime);
      }
      let y = datetime.getFullYear() + "-"
      let mon = datetime.getMonth() + 1
      mon = mon<10 ? '0' + mon : mon
      mon = mon + "-"
      let d = datetime.getDate()
      d = d<10 ? '0' + d : d
      let hour = datetime.getHours() 
      hour = hour<10 ? '0' + hour : hour
      hour = hour + ':'
      let min = datetime.getMinutes()
      min = min<10 ? '0' + min : min
      min = min + ":"
      let sec = datetime.getSeconds()
      sec = sec<10 ? '0' + sec : sec
      return y + mon + d + ' ' + hour + min + sec
    }
    return ""
  }

  Vue.prototype.formatSize = function(data){
    if(data){
      if(data<1024){
        return data +" B";
      }
      if(data>1024 && data<1024*1204){
        return (data/1024).toFixed(2) + " KB";
      }
      return (data/1024/1024).toFixed(2) +" MB";
    }
    return "0 B";
  }
