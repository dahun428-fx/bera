var navi = new Vue({
	el:"#navi",
	data:{
		showNavi:false,
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