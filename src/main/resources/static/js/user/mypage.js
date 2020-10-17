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
		checkboxList:[]
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
				obj.no = products[i].no;
				obj.category = products[i].category;
				obj.name = products[i].name;
				obj.imagePath = images[i].imagePath;
				obj.amount = carts[i].amount;
				obj.price = products[i].price;
				obj.discountPrice = products[i].discountPrice;
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
			console.log(app.checkboxList);
		}
	}
	
	
})