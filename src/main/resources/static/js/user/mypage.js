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
		url:"ddddd"
	},
	beforeCreate:function(){
		
		
		axios.get("/mypage/info",{
			headers:{'X-CSRF-TOKEN':metaToken}
		}).then(function(response){
			let userInfo = response.data.userInfo;
			let cartList = response.data.cartList;
			let productList = response.data.productList;
			let imageList = response.data.imageList;
			
			app.user = userInfo;
			app.carts = cartList;
			app.products = productList;
			app.images = imageList;
			app.createCartList();
			app.totalSizeSet(app.list);
			app.addCheckboxList(app.list);
			app.url = document.location.href.split('/')[4];
		});
		
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
		createCartList:function(){
			let carts = app.carts;
			let products = app.products;
			let images = app.images;
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
			console.log(app.list);
		
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
			console.log(orderForm);
			axios.post('/order/buy', orderForm, {
				headers:{'X-CSRF-TOKEN':metaToken}
			}).then(function(response){
				if(response.data.isSuccess == 'success'){
					location.href ='/order/credit';
				}
			})
		}
	}
	
	
})