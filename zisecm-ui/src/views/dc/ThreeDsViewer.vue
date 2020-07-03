<template>
  <div id="model">
		<iframe key="#1" :src="url" frameborder="0" width="100%" :height="itemHeight" ></iframe>
	</div>
</template>

<script type="text/javascript">
import * as THREE from 'three'
import OrbitControls from 'three-orbitcontrols'
import {OBJLoader} from 'three-obj-mtl-loader'
export default {
  name: "ThreeDsViewer",
  data() {
    return {
		browerName:'',
		dwgname:"",
		itemHeight: window.innerHeight - 50,
		container:[],
		url:"http://139.9.130.57:9032/test/test3D.html",
		camera:[], 
		scene:[], 
		renderer:[],

		mouseX: 0, 
		mouseY: 0,
		windowHalfX:window.innerWidth / 2,
		windowHalfY:window.innerHeight / 2,
		object:[],
		filePath:''
	
    };
  },
  computed: {
        NanJingBankUrl() {
            return "showToolBar=0&runmode=0&ze=1&OnLoadedEvent=OnFlashLoaded"
          + "&fileId=1001&fileName=test&cacheSize=1024&ocfSizeLimit=100&extData=null"
          + "&languagePage=/static/flash/cn.xml&lan=cn";
        }
    },
  props: {
    id:{type:String},
    format:{type:String}
  },
  created() {
    let _self=this;
    this.$nextTick(() => {
      _self.init();
		  _self.animate();
    })
    
  },
  methods: {
    init() {
				var Models = document.getElementById('model');

				this.camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 1, 3000 );
				this.camera.position.x = 0;
				this.camera.position.z = 2800;
        this.camera.position.y = 300;

				// scene

				this.scene = new THREE.Scene();

				let ambientLight = new THREE.AmbientLight( 0xcccccc, 0.3 );
				this.scene.add( ambientLight );

				let pointLight = new THREE.PointLight( 0xffffff, 0.2 );
				this.camera.add( pointLight );
				this.scene.add( this.camera );
        
        this.renderer = new THREE.WebGLRenderer();
				this.renderer.setPixelRatio( window.devicePixelRatio );
				this.renderer.setSize( window.innerWidth, window.innerHeight );

				let controls = new OrbitControls(this.camera,this.renderer.domElement);
				controls.autoRotate = true;// 设置平面自动旋转

				// manager
        let _self=this;
				function loadModel() {

					_self.object.traverse( function ( child ) {

					} );

					_self.object.position.y = -1050;
					_self.scene.add( _self.object );

				}

				let manager = new THREE.LoadingManager( loadModel );

				manager.onProgress = function ( item, loaded, total ) {

					console.log( item, loaded, total );

				};

				// model
				function onProgress( xhr ) {
					if ( xhr.lengthComputable ) {
						let percentComplete = xhr.loaded / xhr.total * 100;
						console.log( 'model ' + Math.round( percentComplete, 2 ) + '% downloaded' );

					}

				}

				function onError( xhr ) {}

				let loader = new OBJLoader( manager );

//				loader.load( 'models/obj/male02/male02.obj', function ( obj ) {
				

				// var m = new Map();
				
				// m.set("id", _self.id);
				// m.set("format", _self.format);
				
				// // console.log('pagesize:', _self.pageSize);
				// axios
				// 	.post("/dc/getFilePath", JSON.stringify(m))
				// 	.then(function(response) {
				// 		if(response.data.code==1){
				// 			_self.filePath = response.data.data;

				// 			loader.load( _self.filePath, function ( obj ) {
				// 				_self.object = obj;
							
				// 			}, onProgress, onError );
				// 		}else{
				// 			_self.$message({
				// 				showClose: true,
				// 				message: "文件路径获取失败！"+response.data.message,
				// 				duration: 5000,
				// 				type: 'error'
				// 			});
				// 		}
						
				// 	})
				// 	.catch(function(error) {
				// 		console.log(error);
				// 	});

				// loader.load( "http://localhost:8080/zisecm/dc/getContent/?id="+_self.id
				// +"&format="+_self.format
				// +"&token="+sessionStorage.getItem('access-token'), function ( obj ) {
				// 				_self.object = obj;
							
				// 			}, onProgress, onError );

				loader.load( "/static/e.jt.obj", function ( obj ) {
								_self.object = obj;
							
							}, onProgress, onError );
				Models.appendChild( _self.renderer.domElement );

				document.addEventListener( 'mousemove', _self.onDocumentMouseMove, false );

				window.addEventListener( 'resize', _self.onWindowResize, false );

      },
      onWindowResize() {

				this.windowHalfX = window.innerWidth / 2;
				this.windowHalfY = window.innerHeight / 2;

				this.camera.aspect = window.innerWidth / window.innerHeight;
				this.camera.updateProjectionMatrix();

				this.renderer.setSize( window.innerWidth, window.innerHeight );

			},

			onDocumentMouseMove( event ) {

				this.mouseX = ( event.clientX - this.windowHalfX ) / 2;
				this.mouseY = ( event.clientY - this.windowHalfY ) / 2;

			},


			animate() {
				requestAnimationFrame( this.animate );
				this.render();

			},

			render() {

//				this.camera.position.x += ( mouseX - this.camera.position.x ) * .05;
//				this.camera.position.y += ( - mouseY - this.camera.position.y ) * .05;
				this.camera.position.x +=.05;
				this.camera.position.y += .05;

				this.camera.lookAt( this.scene.position );

				this.renderer.render( this.scene, this.camera );

			}
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
