<template>
  <div>
      <ul>

        <li v-for="article in articles" v-bind:key="article.index" @click="getDescribe(article.id)">{{article.name}}</li>

      </ul>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
    data(){
        return{
            articles:[
                {
                    id:"1",name:"A"
                },
                {
                    id:"2",name:"B"
                }
            ]
        }
    },
    methods:{
        getDescribe(id){
            console.log(id);
           /*  this.$router.push({
                path: 
            }) */

            window.open(`#/viewdoc?paraName=${id}`,"_blank")
        }
    }
}
</script>

<style>

</style>