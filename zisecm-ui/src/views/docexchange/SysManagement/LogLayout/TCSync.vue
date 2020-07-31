<script>
import BaseGrid from './Layout.vue'
export default {
    extends:BaseGrid,
    methods:{
        setCondition(){
            console.log("TCSync setCondition")
            this.dataType="TCSync"
            this.viewDocField="toId"
        }
    }
}
</script>