<template>
	<div>
		<template v-if="this.hasData" >
			<div style="text-align: right;">
				<el-button v-if="replay" @click="reAwnClick"  type="primary">回复</el-button>
			</div>
			<el-row class="prob-awns"
			:key="item.id"
			 v-for="item in cre">
				<el-col :span="row.left">
					<div>创建人：{{item.CREATOR}}</div>
                    <div>时间:{{item.CREATION_DATE}}</div>
				</el-col>
				<el-col :span="row.center">
					<div style="border-bottom-style:solid;border-width: 1px;">标题：{{item.TITLE}}</div>
					<div>内容：{{item.C_CONTENT}}</div>
				</el-col>
			</el-row>
			<el-row class="prob-awns"
			:key="item.id"
			 v-for="item in awns.slice((currentPage-1)*10,currentPage*10)">
				<el-col :span="row.left">
					<div>
					<div>回复人:{{item.CREATOR}}</div>
                    <div>时间:{{item.CREATION_DATE}}</div>
					</div>
				</el-col>
				<el-col :span="row.center">
					<div>{{item.C_CONTENT}}</div>
				</el-col>
			</el-row>
			<el-pagination
				@current-change="handleCurrentChange"
				:current-page="currentPage"
				hide-on-single-page
				layout="total,prev, pager, next"
				:total="awns.length">
			</el-pagination>
				<div style="text-align: right;">
					<el-button v-if="replay" @click="reAwnClick" type="primary" >回复</el-button>
				</div>
			<el-row v-if="reable" style="padding:20px;">
					<div>问题回复：</div>
					<el-form>
						<el-row>
							<el-col :span="24">
								<el-input
								type="textarea"
								placeholder="问题描述不能少于5个字符"
								v-model="form.content"
								></el-input>
							</el-col>
						</el-row>
					</el-form>
					<el-button @click="saveFormData">确定</el-button>
					<el-button @click="cancel">取消</el-button>
			</el-row>
		</template>
		<template>
			<div>{{this.message}}</div>
		</template>
	</div>
</template>

<script>
export default{
	data(){
		return{
			row:{
				left:4,
				center:20
			},
			form:{
				content:""
			},
			awns:[],
			cre:[],
			reable:false,//回复窗
			docId:"",
			docObj:[],
			message:"",
			hasData:false,//id
			replay:true,//回复按钮
			currentPage: 1,
		}
	},
	created(){
		var _self = this;
		_self.getUserIP((ip) => {
		_self.ip = ip;
		});
	},
	mounted(){
		this.docId = ""
		// this.$route.query.id;
		this.init()
	},
	methods:{
		getUserIP(onNewIP) {
			let MyPeerConnection = window.RTCPeerConnection || window.mozRTCPeerConnection || window.webkitRTCPeerConnection;
			let pc = new MyPeerConnection({
				iceServers: []
			});
			let noop = () => {
			};
			let localIPs = {};
			let ipRegex = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/g;
			let iterateIP = (ip) => {
			if (!localIPs[ip]) onNewIP(ip);
			localIPs[ip] = true;
			};
			pc.createDataChannel('');
			pc.createOffer().then((sdp) => {
			sdp.sdp.split('\n').forEach(function (line) {
				if (line.indexOf('candidate') < 0) return;
				line.match(ipRegex).forEach(iterateIP);
			});
			pc.setLocalDescription(sdp, noop, noop);
			}).catch((reason) => {
			});
			pc.onicecandidate = (ice) => {
			if (!ice || !ice.candidate || !ice.candidate.candidate || !ice.candidate.candidate.match(ipRegex)) return;
			ice.candidate.candidate.match(ipRegex).forEach(iterateIP);
			};
		},
		init(){
			this.search()
		},
		//回复查看
		search(){
			if(this.docId != undefined && this.docId!=null && this.docId.length>0){
				this.refresh()
			}else{
				this.message="docId有误"
			}
		},
		//加载数据
		refresh(){
			let _self=this
			this.hasData=true
			var m = new Map();
			m.set('C_FROM_CODING',this.docId);
			//问题详情获取
			axios.post("/exchange/feedback/searchCreator",JSON.stringify(m))
			.then(function(response) {
				_self.cre = response.data.data;
			})
			.catch(function(error) {
				console.log(error);
			});
			//回复获取
			axios.post("/exchange/feedback/searchReplay",JSON.stringify(m))
			.then(function(response) {
				_self.awns = response.data.data;
			})
			.catch(function(error) {
				console.log(error);
			});
			
		},
		//回复保存
		saveFormData(){
			let _self = this
			if(this.form.content.length>=5){
				// 保存form
				var m = new Map();
				m.set('TYPE_NAME','问题沟通');
				m.set('C_CONTENT',this.form.content);
				m.set('C_FROM_CODING',this.docId);
				let formdata = new FormData();
				formdata.append("metaData",JSON.stringify(m));
				axios.post("/exchange/feedback/newraplay",formdata,{
				'Content-Type': 'multipart/form-data'
				})
				.then(function(response){
					let code = response.data.code;
					if (code == 1) {
						_self.$message({
							message: "回复成功",
							duration: 2000,
							type: "success"
						});
					} 
					else{
						_self.$message({
							message: "回复失败1",
							duration: 2000,
							type: "warning"
						});
					}
				})
				.catch(function(error) {
					_self.$message("回复失败2");
					console.log(error);
				});
				this.form.content=""
				this.search()
				this.reable=false
				this.replay=true
			}else{
				this.$message("回复内容不少于5个字符");
			}
		},
		//回复按钮
		reAwnClick(){
			this.reable=true
			this.replay=false
		},
		//回复取消
		cancel(){
			this.form.content=""
			this.reable=false
			this.replay=true
		},
		handleCurrentChange(val){
			this.currentPage=val
		}
	}
}
	
</script>
<style scoped>
.el-row{
	border:solid;
	border-width: 1px;
	padding: 0px;
}
.el-col{
	border-left-style:solid;
	border-width: 1px;
	text-align: center;
}
</style>
