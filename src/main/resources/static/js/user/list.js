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
		pagination:{
			pageNo:"",
			totalPages:"",
			beginPage:"",
			endPage:"",
			beginIndex:"",
			endIndex:""
		}
	},
	beforeCreate:function(){
		let category = document.getElementById('uriType').value;
		
		searchForm = new FormData();
		searchForm.append('pageNo',1);
		searchForm.append('category',category);
		searchForm.append('rowsPerPage', 5);
		searchForm.append('pagesPerBlock',5);
		searchForm.append('endIndex', 16);
		searchForm.append('formType', 'user');
		searchForm.append('listType', category);
		axios.post('/product/products', searchForm,{
			headers:{'X-CSRF-TOKEN':metaToken}
		})
		.then(function(response){
			app.products = response.data.list;
			const pagination = response.data.pagination;
			app.pagination.pageNo = pagination.pageNo;
			app.pagination.totalPages = pagination.totalPages;
			app.pagination.beginPage = pagination.beginPage;
			app.pagination.endPage = pagination.endPage;
			app.pagination.beginIndex = pagination.beginIndex;
			app.pagination.endIndex = pagination.endIndex;
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
			app.bestProducts = response.data.list;
		});
		
		
	},
	methods:{
		movePage:function(pageNo){
			console.log(pageNo);
			let category = document.getElementById('uriType').value;
			searchForm = new FormData();
			searchForm.append('pageNo',pageNo);
			searchForm.append('category',category);
			searchForm.append('rowsPerPage', 5);
			searchForm.append('pagesPerBlock',5);
			searchForm.append('endIndex', 16);
			searchForm.append('formType', 'user');
			searchForm.append('listType', category);
			
			axios.post('/product/products', searchForm,{
				headers:{'X-CSRF-TOKEN':metaToken}
			})
			.then(function(response){
				console.log(response.data);
				const products = response.data.list;
				const pagination = response.data.pagination;
				app.products = products;
				app.pagination.pageNo = pagination.pageNo;
			})
		},
		buyAction:function(no){
			location.href = '/order/'+no;
		},
		detailAction:function(no){
			location.href ='/menu/'+no;
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
})