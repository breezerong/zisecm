import Vue from 'vue'
Vue.prototype.dateFormat = function(val){
    let datetime = val;
    // console.log(val);
    if (datetime){
      if(typeof datetime === 'string'){
        datetime = new Date(datetime.replace(/-/g,'/').replace(/T|Z/g,' ').replace('.000+0000','').trim());
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
    // console.log(val);
    if (datetime){
      if(typeof datetime === 'string'){
        datetime = new Date(datetime.replace(/-/g,'/').replace(/T|Z/g,' ').replace('.000+0000','').trim());
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
