<template>
  <dev>
     
  </dev>
</template>

<script type="text/javascript">
import ImportDocument from '@/components/controls/ImportDocument'

export default {
  name: "BatchImport",
  components: {
    ImportDocument:ImportDocument
  },
  data() {
    return {
      tableHeight: window.innerHeight - 98
    };
  },
  mounted() {
    
  },
  props: {
    deliveryId: {type:[String],required:true}
  },
  methods: {
    
    
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1,
h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
