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
                }
            ]
        }
    },
    methods:{
        getDescribe(id){
            console.log(id);
            this.$router.push({
                path: `/dc/viewdoc/${id}`
            })
        }
    }
}
</script>

<style>

</style>