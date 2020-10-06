/**
 * 
 */
let metaToken = document.querySelector('meta[name="_csrf"]').content;

var productAdd = new Vue({
	el:"#productApp",
	data:{
		products:[],
		productImages:[],
		productImagesPath:[],
		product:{
			name:"",
			category:"",
			price:"",
			discountPrice:0,
			point:0,
			amount:"",
			explain:"",
			tagArray:[],
			imagePath:"",
			upload:"",
			isAvailalble:""
		},
		search:{
			pageNo:"1",
			searchType:"name",
			searchValue:""
		},
		pagination:{
			pageNo:"",
			totalPages:"",
			beginPage:"",
			endPage:"",
			beginIndex:"",
			endIndex:""
		},
		//discuntRates 에서 선택된 rate
		selectedDiscountRate:"",
		//option list discountRates
		discountRates:[0,5,10,15,20,25,30,35,40,45,50,55,60,65,70],
		//radio에서 선택된 value가 price or rate 인지 구별
		isDiscountPrice:true,
		//input tag를 사용할지 안할지에 대한 checkbox 여부
		isUseTag:true,
		//radio 초기값
		discountType:"price",
		//'상품등록'을 눌렀을때 card box 표시여부
		addFormShow:false,
		//input =tags 의 초기값, bind 되어있다.
		productTags:"",
		error:{
			errorMsg:"",
			errorType:""
		},
		btn:{
			type:"new"
		},
		img:{
			previewImgSrc : "/static/img/main/no_detail_img.jpg",
			previewImgName : "파일을 선택하세요",
			addImgRadioType : "new"
		}
	},
	beforeCreate:function(){
		searchForm = new FormData();
		searchForm.append('pageNo',1);
		searchForm.append('formType', 'admin');
	
		axios.post("/product/products",searchForm, {
			headers:{
				'X-CSRF-TOKEN':metaToken
			}
		})
		.then(function(response){
			productAdd.products = response.data.list;
			productAdd.pagination = response.data.pagination;
		});
		
		axios.get("/product/images",{
			headers:{
				'X-CSRF-TOKEN':metaToken
			}
		})
		.then(function(response){
			const imageList = response.data.imageList;
			productAdd.productImages = imageList;
			for(var i in imageList){
				productAdd.productImagesPath.push(imageList[i].imagePath.substring(13));
			}
		})
	},
	filters:{
		currency:function(value){
			const v = new Number(value);
			return v.toLocaleString('en');
		}
	},
	computed:{
		//discountPrice 계산 function
		computePrice:function(){
			const price = this.product.price;
			const rate = this.selectedDiscountRate / 100 ;
			const result = (rate == 0) ? price : price - ( price * rate );
			this.product.discountPrice = result;
			this.product.point = Math.floor(result * 0.05);
			return result;
		}
	},
	methods:{
		deleteProduct:function(no){
			axios.delete("/product/products/"+no,{
				headers:{
						'X-CSRF-TOKEN':metaToken
				}
			})
			.then(function(response){
				const isSuccess = response.data.isSuccess;
				const msg = response.data.msg;
				if(isSuccess == 'fail'){
					alert(msg);
				} else {
					alert(msg);
					productAdd.clearForm();
					history.go(0);
				}
			})
		},
		getProduct:function(no){
			productAdd.openForm();
			this.btn.type = 'modify';
			axios.get("/product/products/"+no,{
				headers:{
						'X-CSRF-TOKEN':metaToken
				}
			})
			.then(function(response){
				const data = response.data;
				productAdd.product.no = data.no;
				productAdd.product.name = data.name;
				productAdd.product.category = data.category;
				productAdd.product.discountPrice = data.discountPrice;
				productAdd.product.price = data.price;
				productAdd.product.point = data.point;
				productAdd.product.amount = data.amount;
				productAdd.product.explain = data.explain;
				productAdd.product.tagArray = data.productTag;
				productAdd.product.isAvailable = data.isAvailable;
				const tagArray = data.productTag;
				var tags = '';
				if(tagArray.length > 0){
					for(var i in tagArray){
						var comma = ', ';
						tags += tagArray[i].tag + ', ';
					}
				}
				tags = tags.substring(0, tags.length - 2);
				productAdd.productTags = tags;
				productAdd.product.imagePath = data.productImage.imagePath;
				productAdd.img.previewImgSrc = "/static/img/"+data.category+"/"+data.productImage.imagePath;
				productAdd.img.previewImgName = data.productImage.imagePath.substring(13);
			}); 	
		},
		//pagination 함수
		movePage:function(pageNo){
			
			searchForm = new FormData();
			searchForm.append('pageNo',pageNo);
			searchForm.append('searchType',this.search.searchType);
			searchForm.append('searchValue',this.search.searchValue);
			axios.post("/product/products",searchForm, {
			
					headers:{
						'X-CSRF-TOKEN':metaToken
					}
			})
			.then(function(response){
				productAdd.products = response.data.list;
				productAdd.pagination = response.data.pagination;
			});
			
		},
	
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
				alert('jpg, jpeg, png, gif, bmp 확장자 이미지 파일만 가능합니다.');
				document.getElementById(e.target.id).value = '';
				return;
			}
			this.product.imagePath = fileName;
			this.img.previewImgName = fileName;
			this.img.previewImgSrc = URL.createObjectURL(file);
		},
		//이미지 미리보기 눌렀을때 input file 클릭 연계 function
		fileOpen:function(){
			document.getElementById('product-image').click();
		},
		//addForm 표시 여부 function
		toggleForm:function(){
			this.btn.type = 'new';
			if(productAdd.addFormShow){
				this.closeForm();
			} else {
				this.openForm();
			}
		},
		openForm:function(){
			productAdd.clearForm();
			productAdd.addFormShow = true;
		},
		closeForm:function(){
			productAdd.addFormShow = false;
			productAdd.clearForm();
		},
		//product 객체 초기화
		clearForm:function(){
			productAdd.product = {	
				name:"",
				category:"",
				price:"",
				discountPrice:0,
				point:0,
				amount:"",
				explain:"",
				tagArray:[],
				imagePath:"",
				upload:"",
				isAvailalble:""
			};
			this.productTags = "";
			document.getElementById("product-image").value = '';
			this.img.previewImgName = "파일을 선택하세요";
			this.img.previewImgSrc = "/static/img/main/no_detail_img.jpg";
		},
		updateProduct:function(no){
			productAdd.checkFunction();
			
			this.product.tagArray = this.productTags.split(',');
			
			const formData = new FormData();
			formData.append('no', no);
			formData.append('name', this.product.name);
			formData.append('category', this.product.category);
			formData.append('price', this.product.price);
			formData.append('discountPrice', this.product.discountPrice);
			formData.append('point', this.product.point);
			formData.append('amount', this.product.amount);
			formData.append('explain', this.product.explain);
			formData.append('tagArray', this.product.tagArray);
			formData.append('imagePath', this.product.imagePath);
			axios.put('/product/update', formData,{
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
					history.go(0);
				}
			});
		},
		addProduct:function(){
			
			productAdd.checkFunction();
			
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
					history.go(0);
				}
			});
		},
		errorClear:function(){
			this.error.errorType='';
			this.error.errorMsg = '';
		},
		fileSelect:function(){
			this.product.upload = this.$refs.upload.files[0];
		},
		checkFunction:function(){
			
			this.error.errorType = '';
			
			if(productAdd.product.name == ""){
				this.error.errorType = 'name';
				this.error.errorMsg = '상품이름을 입력해주세요';
				return;
			}
			if(productAdd.product.category == ""){
				this.error.errorType = 'category';
				this.error.errorMsg = '카테고리를 입력해주세요';
				return;
			}
			if(productAdd.product.price == ""){
				this.error.errorType = 'price';
				this.error.errorMsg = '가격을 입력해주세요';
				return;
			}
			if(productAdd.product.point == ""){
				this.error.errorType = 'point';
				this.error.errorMsg = '포인트를 입력해주세요';
				return;
			}
			if(productAdd.product.amount == ""){
				this.error.errorType = 'amount';
				this.error.errorMsg = '수량을 입력해주세요';
				return;
			}
			if(productAdd.product.imagePath == ""){
				this.error.errorType = 'image';
				this.error.errorMsg = '이미지를 등록해주세요';
				return;
			}
		},
		isAvailable:function(no){
			const formData = new FormData();
			formData.append('no', no);
			formData.append('isAvailable', 'Y');
			axios.put('/product/update', formData, {
				headers:{
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
					history.go(0);
				}
			})
		},
		noAvailable:function(no){
			console.log(no);
			const formData = new FormData();
			formData.append('no', no);
			formData.append('isAvailable', 'N');
			axios.put('/product/update', formData, {
				headers:{
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
					history.go(0);
				}
			})
		}
	}
	
})
