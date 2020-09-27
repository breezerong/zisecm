<template>
	<div>
		<template v-if="this.hasData" >
			<el-row class="prob-cre"
			:key="item.id"
			 v-for="item in cre"
			 style="margin-top:100px; border-radius: 10px;">
			 	<el-col >
					<div style="text-align: right;">
						<el-button v-if="replay" @click="reAwnClick"  type="primary" size="small">{{$t('application.Reply')}}</el-button>
					</div>
				</el-col>
				<el-col>
					<el-row>
						<div class="item_title">{{$t('application.Title')}}：{{item.TITLE}}</div>
					</el-row>
					<el-row>
						<div class="item_content" style="background: #f7f7fd;">{{$t('application.type')}}：{{item.SUB_TYPE}} &nbsp;  &nbsp; {{$t('application.StakeHolder')}}：{{item.C_TO}} &nbsp;  &nbsp; {{$t('application.RepNum')}}：{{awns.length}}</div>
					</el-row>
					<el-row style="background: #f7f7fd;">
						<el-col :span="row.left">
							<div class="item_content">{{$t('application.by_creator')}}：{{item.CREATOR}} &nbsp; <i class="el-icon-info"></i></div>
							<div class="item_content">{{$t('application.by_creator')}}:{{item.CREATION_DATE}}</div>
						</el-col>
						<el-col :span="row.center">
							<div class="div-left item_content">{{item.C_CONTENT}}</div>
						</el-col>
					</el-row>
				</el-col>
				<el-col>
					<el-row
					:key="item.id"
					v-for="item in awns.slice((currentPage-1)*10,currentPage*10)">
						<el-col :span="row.left" class="topic_l">
							<div>
							<div class="item_content">{{$t('application.RepPerson')}}:{{item.CREATOR}} &nbsp; <i class="el-icon-info"></i></div>
							<div class="item_content">{{$t('application.Times')}}:{{item.CREATION_DATE}}</div>
							</div>
						</el-col>
						<el-col :span="row.center">
							<div class="div-left item_content" style="padding:10px;">{{item.C_CONTENT}}</div>
						</el-col>
					</el-row>
					<el-pagination
						@current-change="handleCurrentChange"
						:current-page="currentPage"
						hide-on-single-page
						layout="total,prev, pager, next"
						:total="awns.length">
					</el-pagination>
				</el-col>
				<el-col v-if="reable" style="padding:20px;">
					<el-row>
						<el-col :span="row.left" style="text-align:left;padding:20px;1px;1px;1px;">{{$t('application.ReQuestion')}}：</el-col>
						<el-col :span="24" style="text-align: left;padding:5px;">
							<el-input
							type="textarea" rows="10"
							:placeholder="$t('message.queDismorefive')"
							v-model="form.content"
							style="padding:20px;"
							></el-input>
						</el-col>
						<el-col>
							<el-button @click="saveFormData" size="medium">{{$t('application.ok')}}</el-button>
							<el-button @click="cancel" size="medium">{{$t('application.cancel')}}</el-button>
						</el-col>
					</el-row>
				</el-col>
				<el-col>
					<div style="text-align: right;">
						<el-button v-if="replay" @click="reAwnClick"  type="primary" size="small">{{$t('application.Reply')}}</el-button>
					</div>
				</el-col>
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
				left:6,
				center:18
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
			this.docId = this.$route.query.id;
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
						_self.refresh()
					} 
					else{
						_self.$message({
							message: "回复失败",
							duration: 2000,
							type: "warning"
						});
					}
				})
				.catch(function(error) {
					_self.$message("回复失败");
					console.log(error);
				});
				_self.form.content=""
				_self.reable=false
				_self.replay=true
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
	border-style:solid; 
	border-width: 1px;
	padding: 0px;
	border-color: rgb(160, 160, 160);
}
.prob-cre{
	width: 70%;
	margin: 0 auto;
	margin-top: 10px !important;
	border-color: rgb(120, 120, 120);
}
.prob-awns{
	margin: 0 auto;
	border-color: rgb(160, 160, 160);
}
.item_title {
    width: 100%;
    font-size: 24px;
    color: #222226;
    font-weight: 700;
    line-height: 36px;
    margin-top: 12px;
    margin-bottom: 8px;
    word-break: break-all;
}
.item_content {
    width: 100%;
    color: #222226;
    line-height: 36px;
    margin-top: 2px;
    margin-bottom: 2px;
    word-break: break-all;
}
.topic_l {
    background: linear-gradient(180deg,#f9fbfc 0,#fff 100%);
    box-shadow: inset -1px 0 0 #f2f3f4;
    display: table-cell;
    position: relative;
    border-collapse: collapse;
}
.div-left{
	border-left-style:solid;
	border-width: 1px;
	text-align: left;
	white-space: pre-line;
}
.el-col{
	/* border-left-style:solid; */
	text-align: center;
}


.textarea {
  white-space: pre-line;  
}


</style>
