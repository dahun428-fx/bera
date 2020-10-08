/**
 * 
 */
let metaToken = document.querySelector('meta[name="_csrf"]').content;

var app = new Vue({
	
	el:"#app",
	data:{
		product:{
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
		icecreamImage:""
	},
	beforeCreate:function(){
		let productNo = document.getElementById('productNo').value;
		axios.get('/product/products/'+productNo,{
			headers:{'X-CSRF-TOKEN':metaToken}
		})
		.then(function(response){
			const product = response.data;
			app.product.name = product.name;
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
	methods:{
		
	}
	
	
})