var loginApp = new Vue({
	
	el:"#loginApp",
	data:{
		user:{id:"",password:""},
		hasUserId:false,
		hasUserPw:false,
		errorMsg:"",
		hasError : false
	},
	methods:{
		loginAction:function(user){
		
			this.hasError = false;
			if(user.id == ""){
				this.hasUserId = true;
				this.hasError = true;
				this.errorMsg = "아이디를 입력해주세요.";	
				return;
			}
			if(user.password == ""){
				this.hasUserPw = true;
				this.hasError = true;
				this.errorMsg = "비밀번호를 입력해주세요.";
				return;
			}
			document.getElementById('loginForm').submit();
		},
		clearFormId:function(){
			this.user.id = "";
			this.hasUserId = false;
		},
		clearFormPw:function(){
			this.user.password = "";
			this.hasUserPw = false;
		}
	
	
	}



})