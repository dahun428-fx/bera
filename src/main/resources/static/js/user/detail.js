/**
 * 
 */
let metaToken = document.querySelector('meta[name="_csrf"]').content;

var app = new Vue({
	
	el:"#app",
	data:{
		product:{
			no:"",
			name:"",
			category:"",
			price:"",
			discountPrice:0,
			point:0,
			amount:"",
			explain:"",
			tagArray:[],
			imagePath:""
		},
		icecreamImage:"",
		order:{
			count:1,
			totalPrice : 0,
			type:"noBasket"
		}
	},
	beforeCreate:function(){
		let productNo = document.getElementById('productNo').value;
		axios.get('/product/products/'+productNo,{
			headers:{'X-CSRF-TOKEN':metaToken}
		})
		.then(function(response){
			const product = response.data;
			app.product.no = product.no;
			app.product.name = product.name;
			app.product.price = product.price;
			app.product.category = product.category;
			app.product.discountPrice = product.discountPrice;
			app.product.point = product.point;
			app.product.amount = product.amount;
			app.product.explain = product.explain;
			app.product.imagePath = product.productImage.imagePath;
			console.log(app.product.name);
			app.icecreamImage = '/static/img/'+app.product.category+'/'+app.product.imagePath;
		})
	},
	filters:{
		currency:function(price){
			return price.toLocaleString();
		}
	},
	computed:{
		totalPrice:function(){
			let total = this.product.price * this.order.count;
			this.order.totalPrice = total;
			return total;
		}
	},
	methods:{
		countPlus:function(){
			let count = this.order.count;
			if(count < 100) {
				count++;
			} else {
				count = 100;
			}
			this.order.count = count;
		},
		countMinus:function(){
			let count = this.order.count;
			if(count  > 1) count--;

			this.order.count = count;			
		},
		orderAction:function(){
		
			var order = {
				"productNo":app.product.no,
				"productPrice":app.product.price,
				"orderProductAmount":app.order.count
			}
//			var product = {
//				"no":app.product.no, 
//				"amount":app.product.amount,
//				"name":app.product.name,
//				"price":app.product.price,
//				"point":app.product.point,
//				"discountPrice":app.product.discountPrice,
//				"category":app.product.category,
//				"regDate":"",
//				"reviews":0,
//				"explain":"",
//				"isAvailable":""};
//			var products = new Array();
//			products.push(product);

			var orders = new Array();
			orders.push(order);
			
			var orderForm = new Object();
			orderForm.orders = orders;
			orderForm.orderType = app.order.type;
			
			axios.post('/order/buy', orderForm, {
				headers:{'X-CSRF-TOKEN':metaToken}
			})
			.then(function(response){
				const isSuccess = response.data.isSuccess
				if(isSuccess == 'success'){
					location.href = '/order/credit';
				}
			})
		},
		cartAction:function(){
			var order = {
				"productNo":app.product.no,
				"productPrice":app.product.price,
				"orderProductAmount":app.order.count
			}
			axios.post("/order/cart", order, {
				headers:{'X-CSRF-TOKEN':metaToken}
			}).then(function(response){
				console.log(response.data);
			})
			
		}
	}
	
	
})