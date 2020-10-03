/**
 * 	private int no;
	private String name;
	private int amount;
	private int price;
	private double point;
	private double discountPrice;
	private String category;
	private Date regDate;
	private int reviews;
	private String explain;
	
 */
let metaToken = document.querySelector('meta[name="_csrf"]').content;

var productAdd = new Vue({
	el:"#productApp",
	data:{
		products:[],
		product:{
			name:"",
			category:"",
			price:"",
			discountPrice:"",
			point:"",
			amount:"",
			explain:"",
			tagArray:[],
			imagePath:"",
			upload:""
		},
		//discuntRates 에서 선택된 rate
		selectedDiscountRate:"",
		//option list discountRates
		discountRates:[5,10,15,20,25,30,35,40,45,50,55,60,65,70],
		//radio에서 선택된 value가 price or rate 인지 구별
		isDiscountPrice:true,
		//input tag를 사용할지 안할지에 대한 checkbox 여부
		isUseTag:true,
		//radio 초기값
		discountType:"price",
		//이미지 미리보기 초기값
		previewImgSrc : "/static/img/main/no_detail_img.jpg",
		//input = file image 이미지 이름 미리보기 초기값
		previewImgName : "파일을 선택하세요",
		//'상품등록'을 눌렀을때 card box 표시여부
		addFormShow:false,
		//input =tags 의 초기값, bind 되어있다.
		productTags:"",
		error:{
			errorMsg:"",
			errorType:""
		}
	},
	beforeCreate:function(){
	
		axios.get("/product/products")
		.then(function(response){
			productAdd.products = response.data;
		});
	
	},
	filters:{
		currency:function(value){
			const v = new Number(value);
			return v.toLocaleString('en');
		}
	},
	computed:{
		//discountPrice 계산 function
		computeDiscount:function(){
			const price = this.product.price;
			const rate = this.selectedDiscountRate / 100 ;
			const result = (rate == "") ? price : price - ( price * rate );
			this.product.discountPrice = result;
			return result;
		},
		computePoint:function(){
			const discountPrice = new Number(this.product.discountPrice);
			
			const point = (this.product.discountPrice == "") ? 0 : (Math.floor(discountPrice * 0.05));
			this.product.point = point;
			return point;
		}
	},
	methods:{
		//tags 배열처리 function
		splitTags:function(){
			this.product.tagArray = this.productTags.split(',');
		},
		point:function(value){
			this.product.point = value * 0.1;
		},
		//tag input box disabled 여부 조작 function
		toggleUseTag:function(){
			if(this.isUseTag){
				this.isUseTag = false
			} else {
				this.isUseTag = true
			}
		},
		//image file 미리보기 function
		changeImage(e){
			var reg = /(.*?)\.(jpg|jpeg|png|gif|bmp)$/;
			
			const file = e.target.files[0];
			const fileName = e.target.files[0].name;
			
			if(!fileName.match(reg)){
				console.log('no image File');
				alert('jpg, jpeg, png, gif, bmp 확장자 이미지 파일만 가능합니다.');
				document.getElementById(e.target.id).value = '';
				return;
			}
			this.product.imagePath = fileName;
			this.previewImgName = fileName;
			this.previewImgSrc = URL.createObjectURL(file);
		},
		//이미지 미리보기 눌렀을때 input file 클릭 연계 function
		fileOpen:function(){
			document.getElementById('product-image').click();
		},
		//addForm 표시 여부 function
		toggleForm:function(){
			if(productAdd.addFormShow){
				this.closeForm();
			} else {
				this.openForm();
			}
		},
		openForm:function(){
			productAdd.addFormShow = true;
		},
		closeForm:function(){
			productAdd.addFormShow = false;
			productAdd.clearForm();
		},
		//product 객체 초기화
		clearForm:function(){
			productAdd.product = {};
			this.productTags = "";
			document.getElementById("product-image").value = '';
			this.previewImgName = "파일을 선택하세요";
			this.previewImgSrc = "/static/img/main/no_detail_img.jpg";
		},
		addProduct:function(){
			this.error.errorType = '';
			if(productAdd.product.name == ""){
				console.log("상품 이름을 입력해주세요");
				this.error.errorType = 'name';
				this.error.errorMsg = '상품이름을 입력해주세요';
				return;
			}
			if(productAdd.product.category == ""){
				this.error.errorType = 'category';
				this.error.errorMsg = '카테고리를 입력해주세요';
				console.log("카테고리를 입력해주세요");
				return;
			}
			if(productAdd.product.price == ""){
				this.error.errorType = 'price';
				this.error.errorMsg = '가격을 입력해주세요';
				console.log("가격을 입력해주세요");
				return;
			}
			if(productAdd.product.point == ""){
				this.error.errorType = 'point';
				this.error.errorMsg = '포인트를 입력해주세요';
				console.log("포인트를 입력해주세요");
				return;
			}
			if(productAdd.product.amount == ""){
				this.error.errorType = 'amount';
				this.error.errorMsg = '수량을 입력해주세요';
				console.log("입고 수량을 입력해주세요");
				return;
			}
			if(productAdd.product.imagePath == ""){
				this.error.errorType = 'image';
				this.error.errorMsg = '이미지를 등록해주세요';
				console.log("이미지를 등록해주세요");
				return;
			}
			
			this.product.tagArray = this.productTags.split(',');
			
			const formData = new FormData();
			formData.append('name', this.product.name);
			formData.append('category', this.product.category);
			formData.append('price', this.product.price);
			formData.append('discountPrice', this.product.discountPrice);
			formData.append('point', this.product.point);
			formData.append('amount', this.product.amount);
			formData.append('explain', this.product.explain);
			formData.append('tagArray', this.product.tagArray);
			formData.append('imagePath', this.product.imagePath);
			formData.append('upload', this.product.upload);
			axios.post('/product/add', formData,{
				headers:{
					'Content-Type':'multipart/form-data',
					'X-CSRF-TOKEN':metaToken
				}
			}).then(function(response){
				const isSuccess = response.data.isSuccess;
				const msg = response.data.msg;
				if(isSuccess == 'fail'){
					alert(msg);
				} else {
					alert(msg);
					productAdd.clearForm();
				}
			});
		},
		errorClear:function(){
			this.error.errorType='';
			this.error.errorMsg = '';
		},
		fileSelect:function(){
			this.product.upload = this.$refs.upload.files[0];
		}
	}
	
})
