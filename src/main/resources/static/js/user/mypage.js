/**
 * 
 */
let metaToken = document.querySelector('meta[name="_csrf"]').content;

var app = new Vue({
	
	el:"#app",
	data:{
		user:{}
	},
	beforeCreate:function(){
		axios.get("/mypage/info",{
			headers:{'X-CSRF-TOKEN':metaToken}
		}).then(function(response){
			let userInfo = response.data.userInfo;
			console.log(userInfo);
			app.user = userInfo;
		});
	},
	computed:{
		
	},
	methods:{
		
	}
	
	
})