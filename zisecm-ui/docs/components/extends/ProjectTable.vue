<script>
import BaseCurd from './BaseCrud'

export default {
    name:'ProjectTable',
    extends : BaseCurd,
    data(){
      return {
        table:{
          header:{
            url:"abc"
          }
        }
      }
    },
    mounted(){
      console.log("ProjectTable");
    },
    methods:{
     
    }
}
</script>

/* <style scoped>
.el-header{
    background-color: #ffffff;
    color: #333;
    text-align: left;
    padding: 0px;
    margin-top: 0px;
    margin-left: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
    height: auto;
  }
</style> */