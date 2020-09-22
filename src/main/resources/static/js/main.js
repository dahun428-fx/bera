/**
 * 
 */
var app = new Vue({
	el:"#app",
	data:{
		showNavi:false
	},
	methods:{
		show:function(){
			this.showNavi = true;
			
		},
		fade:function(){
			this.showNavi = false;
		}
	},
	
})