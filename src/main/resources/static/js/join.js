
let metaHeader = document.querySelector('meta[name="_csrf_header"]').content;
let metaToken = document.querySelector('meta[name="_csrf"]').content;

const config = {
	
	headers:{
		'X-CSRF-TOKEN':metaToken		
	}

};

var joinApp = new Vue({

	el:"#joinApp",
	data:{
		user:{
			id:"",
			name:"",
			password:"",
			passwordCheck:"",
			phone:"",
			email:"",
			agreeCheckBox:false
			},
		//input box의 값이 비어있는지 확인여부, 서버에 데이터를 보내지 않기 위해 정의한다.
		isEmpty:false,
		//error 가 있는지 여부, 서버에서 받아온 에러를 함께 처리한다. 
		hasError:false,
		errorMsg :"",
		errorList:{
			nameError:false,
			idError:false,
			pwError:false,
			pwCheckError:false,
			phoneError:false,
			emailError:false
		}
	
	},
	methods:{
			
		joinAction:function(user){
			this.clearError();
			this.userCheckFunction(user);
			if(this.isEmpty == false){return;}
		
			axios.post("/user/join", user, config)
			.then(function(response){
				const type = response.data.type;
				const msg = response.data.msg;
				
				if(type == 'error'){
					joinApp.errorList.idError = true;
					joinApp.hasError = true;
					joinApp.errorMsg = msg;
				} else if (type == 'success'){
					
					alert(joinApp.user.name + '님 회원가입을 축하합니다.');
				  	location.href = msg;
				}
				
			})
			
			
		},
		//유효성검사
		userCheckFunction:function(user){
			
			if(user.name == ""){
				this.hasError = true;
				this.errorMsg = "이름을 입력해주세요";
				this.errorList.nameError = true;
				return;
			}
			if(user.id == ""){
				this.hasError = true;
				this.errorMsg = "아이디를 입력해주세요";
				this.errorList.idError = true;
				return;
			}
			if(user.password == ""){
				this.hasError = true;
				this.errorMsg = "비밀번호를 입력해주세요";
				this.errorList.pwError = true;
				return;
			}
			if(user.passwordCheck == ""){
				this.hasError = true;
				this.errorMsg = "비밀번호 재확인란을 입력해주세요";
				this.errorList.pwCheckError = true;
				return;
			}
			if(user.password != user.passwordCheck){
				this.hasError = true;
				this.errorMsg = "비밀번호가 다릅니다.";
				this.errorList.pwError = true;
				this.errorList.pwCheckError = true;
				return;
			}
			if(user.phone == ""){
				this.hasError = true;
				this.errorMsg = "전화번호를 입력해주세요";
				this.errorList.phoneError = true;
				return;
			}
			if(user.email == ""){
				this.hasError = true;
				this.errorMsg = "이메일을 입력해주세요";
				this.errorList.emailError = true;
				return;
			}
			if(user.agreeCheckBox == false){
				this.hasError = true;
				this.errorMsg = "개인정보 동의란을 확인해주세요";
				return;
			}
			if(!this.validId(user.id)){
				this.hasError = true;
				this.errorMsg = "유효하지 않은 아이디입니다."
				this.errorList.idError = true;
				return;
			}
			if(!this.validName(user.name)){
				this.hasError = true;
				this.errorMsg = "유효하지 않은 이름입니다.";
				this.errorList.nameError = true;
				return;
			}
			if(this.validPhone(user.phone)){
				this.hasError = true;
				this.errorMsg = "유효하지 않은 전화번호입니다.";
				this.errorList.phoneError = true;
				return;
			}
			this.isEmpty = true;
		},
		validId:function(id){
			const check = /^[a-zA-Z0-9]{4,12}$/;
			
			return check.test(id);
		},
		validName:function(name){
			const check = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
			return check.test(name);
		},
		validPhone:function(phoneNumber){
			const check = /[^0-9]/g;
			return check.test(phoneNumber);
		},
		clearError:function(){
			this.hasError = false;
			this.isEmpty = false;
			this.errorMsg = "";
			this.errorList.nameError = false;
			this.errorList.idError = false;
			this.errorList.pwError = false,
			this.errorList.pwCheckError = false,
			this.errorList.phoneError = false,
			this.errorList.emailError = false
		},
		keyupFunction:function(number){
			number = number.replace(/[^0-9]/g, '');
			this.user.phone = number;
			
		}
	
	}

})