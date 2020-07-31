<script>
import BaseGrid from './Layout.vue'
export default {
    extends:BaseGrid,
    methods:{
        setCondition(){
            console.log("TCPush setCondition")
            this.dataType="TCPush"
            this.viewDocField="fromId"
        }
    }

}
</script>