/**
 * 
 */
var app = new Vue({
	el:"#app",
	data:{
		showNavi:false,
		events:[1,2,3,4]
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