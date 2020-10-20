/**
 * 
 */
let metaToken = document.querySelector('meta[name="_csrf"]').content;


var app = new Vue({	
	el:"#app",
	data:{
		user:{},
		carts:[],
		products:[],
		images:[],
		list:[],
		totalSize:0,
		allCheckbox:true,
		checkboxList:[],
		url:"",
		cartListIsEmpty:false,
		pagination:{
			pageNo:"",
			totalPages:"",
			beginPage:"",
			endPage:"",
			beginIndex:"",
			endIndex:""
		},
		pointObj:{
			pointList:[],
			pointOrder:[],
			orderCount:0
		}
	},
	beforeCreate:function(){
		
		axios.get("/mypage/info", {
			headers:{'X-CSRF-TOKEN':metaToken}
		}).then(function(response){
			let userInfo = response.data.userInfo;
			let cartList = response.data.cartList;
			let productList = response.data.productList;
			let imageList = response.data.imageList;
			//let pagination = response.data.pagination;
			
			app.user = userInfo;
			app.carts = cartList;
			app.products = productList;
			app.images = imageList;
			app.createCartList(app.carts, app.products, app.images);
			app.totalSizeSet(app.list);
			app.addCheckboxList(app.list);
			app.url = document.location.href.split('/')[4];
			//app.createPagination(pagination);
			if(app.carts == ''){
				app.cartListIsEmpty = true;
			}
		});
		axios.get("/mypage/points",{
			headers:{'X-CSRF-TOKEN':metaToken}
		}).then(function(response){
			let pointList = response.data.pointList;
			let pointOrder = response.data.pointOrder;
			let orderCount = response.data.orderCount;
			app.pointObj.pointList = pointList;
			app.pointObj.pointOrder = pointOrder;
			app.pointObj.orderCount = orderCount;
			console.log(app.pointObj);
		})
		
	},
	filters:{
		currency:function(value){
			value = new Number(value);
			return value.toLocaleString();
		}
	},
	computed:{
		
	},
	methods:{
		createPagination:function(pagination){
			app.pagination.pageNo = pagination.pageNo;
			app.pagination.totalPages = pagination.totalPages;
			app.pagination.beginPage = pagination.beginPage;
			app.pagination.endPage = pagination.endPage;
			app.pagination.beginIndex = pagination.beginIndex;
			app.pagination.endIndex = pagination.endIndex;
		},
		createCartList:function(carts, products, images){
			const list = [];
			for(var i in carts){
				let obj = new Object();
				obj.productNo = products[i].no;
				obj.category = products[i].category;
				obj.name = products[i].name;
				obj.imagePath = images[i].imagePath;
				obj.orderProductAmount = carts[i].amount;
				obj.productPrice = products[i].discountPrice;
				obj.point = products[i].point
				list.push(obj);
			}
			app.list = list;
		},
		totalSizeSet:function(list){
			app.totalSize = list.length;
		},
		addCheckboxList:function(list){
			for(var i in list){
				obj = new Object();
				obj.order = list[i];
				obj.productNo = list[i].productNo;
				obj.isChecked = true;
				app.checkboxList.push(obj);				
			}
		},
		changeAllCheckToggle:function(){
			let list = app.checkboxList
			if(app.allCheckbox){
				for(var i in list){
					list[i].isChecked = true;
				}
			} else {
				for(var i in list){
					list[i].isChecked = false;
				}
			}
		},
		changeCheckbox:function(){
			let list = app.checkboxList
			for(var i in list){
				if(!list[i].isChecked){
					app.allCheckbox = false;
					return;
				} 
				if(list[i].isChecked){
					app.allCheckbox = true;
				}
				
			}
		},
		selectedDelect:function(){
			let list = app.checkboxList;
			let orders = new Array();
			for(var i in list){
				if(list[i].isChecked){
					orders.push(list[i]);
				}
			}
			console.log(orders);
		},
		selectedOrder:function(){
			let list = app.checkboxList;
			let orders = new Array();
			for(var i in list){
				if(list[i].isChecked){
					orders.push(list[i].order);
				}
			}
			var orderForm = new Object();
			orderForm.orders = orders;
			orderForm.orderType = "cart";
			console.log(orderForm);
			axios.post('/order/buy', orderForm, {
				headers:{'X-CSRF-TOKEN':metaToken}
			}).then(function(response){
				if(response.data.isSuccess == 'success'){
					location.href ='/order/credit';
				}
			})
		},
		selectedOneOrder:function(index){
			let list = app.checkboxList;
			let order = list[index].order;
			let orders = new Array();
	
			orders.push(order);
			var orderForm = new Object();
			orderForm.orders = orders;
			orderForm.orderType = "cart";
			
			axios.post('/order/buy', orderForm,{
				headers:{'X-CSRF-TOKEN':metaToken}
			}).then(function(response){
				if(response.data.isSuccess == 'success'){
					location.href = '/order/credit';
				}
			})
		}
	}
	
	
})