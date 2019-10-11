<template>
	<div>
		<el-input type="text"
		placeholder="请输入内容"
		readonly="readonly"
		style="width: 100px;"
		v-model="showInput"></el-input>
		<input value="value1" type="hidden" />
		<el-popover ref="popover" placement="right" width="400" trigger="click" v-model="visible">
			<div>
				<span>
					<el-input style="width: 150px;" v-model="findValue"></el-input>
					<el-button type="primary" style="height: 40px;">搜索</el-button>
				</span>
				<el-table :data="userList" border height="320px" stripe style="width: 100%">
					<el-table-column prop="name" label="登录名" width="120"></el-table-column>
					<el-table-column prop="email" label="邮箱" width="200"></el-table-column>
					<el-table-column label="操作" width="80">
						<template slot-scope="scope">
							<el-button :plain="true" type="primary" size="small" icon="edit" @click="handleInput(scope.row)">添加</el-button>
						</template>
					</el-table-column>
				</el-table>
			</div>
			<el-button icon="el-icon-user-solid" circle slot="reference">选择</el-button>
		</el-popover>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				findValue: '',
				showInput:'',
				visible: false,
				userList: [{
					id:123,
					name: 'liuce',
					email: 'xiuxiu@163.com'
				}, {
					id:456,
					name: 'chenshuo',
					email: 'chenshuo@163.com'
				}]
			}
		},
		model:{
			prop:'value1',
			event:'change'
		},
		props: {
			//是否隐藏
			isHiden: {
				type: Boolean,
				default: false
			},
			//输入框默认显示值
			inputValue: {
				type: [String, Number],
				default: ''
			},
			//获取数据的url地址
			url:{
				type:String,
				default:''
			},
			//获取数据的请求方式，默认为post
			getmethods:{
				type:String,
				default:'post'
			},
			//属性名
			atrName:{
				type:String,
				default:''
			},
			dataList:{
				type:Array
			}
		},
		methods: {
			//获取选人框体数据
			refreshData(){
			      let _self = this;
			      _self.axios({
			         headers: {
			            "Content-Type": "application/json;charset=UTF-8"
			          },
			          method: this.getmethods,
			          data: '',
			          url: this.url
			      }).then(function(response){
			        console.log(response)
			      }).catch(function(error){
			        console.log(error)
			      })
			    },
			//改变value1的值
			handleInput: function(indata) {
				this.showInput = indata.name
				this.$emit("change", indata.id);
				this.visible=false
			},
			//筛选查询方法
			on_filter: function(val) {
				this.refreshData()
			}
		}
		
	}
</script>

<style>
</style>
