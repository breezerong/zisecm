<script>
import BaseGrid from './Layout.vue'
export default {
    extends:BaseGrid,
    methods:{
        setCondition(){
            console.log("NetSync setCondition")
            this.dataType="NetSync"
            this.viewDocField="fromId"
        }
    }
}
</script>