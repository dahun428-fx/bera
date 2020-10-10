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
			totalPrice : 0
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
		}
	}
	
	
})