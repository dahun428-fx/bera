/**
 * 
 */
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
var main = new Vue({
	el:"#main",
	data:{
		icecreams:[1,2,3,4,5,6],
		events:[1,2,3,4]
	}

})
var loginApp = new Vue({

	el:"#loginApp",
	data:{
		user:{}
	},
	methods:{
		login:function(){
			
		}
	}
})