/**
 * 
 */
let metaToken = document.querySelector('meta[name="_csrf"]').content;

var app = new Vue({
	
	el:"#productApp",
	data:{
		products:[],
		search:{
		},
		productTags:[]
	},
	beforeCreate:function(){
		let category = document.getElementById('uriType').value;
		
		console.log(category);
		searchForm = new FormData();
		searchForm.append('pageNo',1);
		searchForm.append('category',category);
		searchForm.append('formType', 'user');
		searchForm.append('listType', category);
		axios.post('/product/products', searchForm,{
			headers:{'X-CSRF-TOKEN':metaToken}
		})
		.then(function(response){
			const list = response.data.list;
			app.products = list;
			console.log(app.products);
			for(var i in list){
				let tagArray = list[i].productTag;
				console.log(tagArray);
			}
			
		})
	},
	methods:{
		product:function(){
			
		}
	}
	
	
	
	
	
	
	
	
	
	
})