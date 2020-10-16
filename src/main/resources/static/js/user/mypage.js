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
		list:[]
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
			console.log(app.carts);
			console.log(app.products);
			console.log(imageList);
			app.createCartList();
			
		});
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
			console.log(app.list);
		
		}
	}
	
	
})