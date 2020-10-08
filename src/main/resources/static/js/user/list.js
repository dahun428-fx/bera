/**
 * 
 */
let metaToken = document.querySelector('meta[name="_csrf"]').content;

var app = new Vue({
	
	el:"#productApp",
	data:{
		products:[],
		productTags:[],
		bestProducts:[],
		backgroundColors:['#FFB6C1','#FAC87D','#FF7F50','#369F36','#FAC87D','#FF7F50'],
		activeColor:"#FFB6C1"
	},
	beforeCreate:function(){
		let category = document.getElementById('uriType').value;
		
		searchForm = new FormData();
		searchForm.append('pageNo',1);
		searchForm.append('category',category);
		searchForm.append('rowsPerPage', 5);
		searchForm.append('pagesPerBlock',5);
		searchForm.append('endIndex', 25);
		searchForm.append('formType', 'user');
		searchForm.append('listType', category);
		axios.post('/product/products', searchForm,{
			headers:{'X-CSRF-TOKEN':metaToken}
		})
		.then(function(response){
			app.products = response.data.list;
		})
		
		searchFormBest = new FormData();
		searchFormBest.append('pageNo',1);
		searchFormBest.append('category', category);
		searchFormBest.append('rowsPerPage', 5);
		searchFormBest.append('pagesPerBlock', 5);
		searchFormBest.append('endIndex', 6);
		searchFormBest.append('formType', 'user');
		searchFormBest.append('listType', category);
		axios.post('/product/products',searchFormBest,{
			headers:{'X-CSRF-TOKEN':metaToken}
		})
		.then(function(response){
			console.log(response.data);
			app.bestProducts = response.data.list;
			
		});
		
		
	},
	methods:{
		product:function(){
			
		},
		changeColor:function(index){
			app.activeColor = app.backgroundColors[index];
		}
	}
	
	
	
	
	
	
	
	
	
	
})