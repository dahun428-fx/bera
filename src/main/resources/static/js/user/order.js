/**
 * 
 */
 let metaToken = document.querySelector('meta[name="_csrf"]').content;
 
var app = new Vue({
	el:"#app",
	data:{
		userInfo:[],
		orders:[],
		totalPrice:0,
		totalDiscountPrice:0,
		usePoint:0,
		savePoint:0,
		totalPay:0,
		allCheckbox:true,
		checkboxList:[],
	},
	beforeCreate:function(){
		axios.get('/order/products',{
			headers:{'X-CSRF-TOKEN':metaToken}
		}).then(function(response){
			const userInfo = response.data.userInfo;
			const orderList = response.data.orderList;
			if(orderList == null) {
				alert('유효하지 않은 접근입니다. 다시 시도해주세요');
				location.href = '/';
				return;
			}

			app.userInfo = userInfo;
			app.orders = orderList;
			app.addCheckboxList(app.orders);
			console.log(app.orders);
		})
	},
	filters:{
		currency:function(value){
			return value.toLocaleString();
		}
	},
	computed:{
		totalPayCompute:function(){
			let totalPay = app.totalPrice - app.totalDiscountPrice - app.usePoint;
			app.totalPay = totalPay;
			return totalPay;
		},
		savePointCompute:function(){
		
		}
	},
	methods:{
		isAvailablePoint:function(){
			let userPoint = app.userInfo.point;
			let usingPoint = app.usePoint;
			if(userPoint < usingPoint){
				alert('보유 포인트를 확인하세요');
				app.usePoint = 0;
			}
		},
		getTotalPrice:function(){
			let list = app.checkboxList;
			let orders = app.orders;
			let totalPrice = 0;
			let totalDiscountPrice = 0;
			for(var i = 0; i < list.length; i++){
				if(list[i].isChecked && orders[i].productNo == list[i].order.productNo){
					totalPrice += orders[i].productPrice * orders[i].orderAmount;
					totalDiscountPrice =+ (orders[i].productPrice - orders[i].productDiscountPrice) * orders[i].orderAmount;
				}
			}
			app.totalDiscountPrice = totalDiscountPrice;
			app.totalPrice = totalPrice;
		},
		addCheckboxList:function(list){
			for(var i in list){
				obj = new Object();
				obj.order = list[i];
				obj.productNo = list[i].productNo;
				obj.isChecked = true;
				app.checkboxList.push(obj);				
			}
			app.getTotalPrice();
		},
		changeCheckbox:function(){
			let list = app.checkboxList
			for(var i in list){
				if(!list[i].isChecked){
					app.allCheckbox = false;
				} else {
					app.allCheckbox = true;
				}
			}
			app.getTotalPrice();
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
			app.getTotalPrice();
		},
		submitOrder:function(){
			var list = app.checkboxList;
			var orderList = app.orders;
			var orders = new Array();
			console.log(list);
			for(var i = 0; i < list.length; i++){
				if(list[i].isChecked){
				
					let productNo = orderList[i].productNo
					let productPrice = orderList[i].productPrice;
					let orderProductAmount = orderList[i].orderAmount;
					console.log(productNo, productPrice, orderProductAmount);
					var order =
						{
							"productNo":productNo,
							"productPrice":productPrice,
							"orderProductAmount":orderProductAmount
						}
					orders.push(order);	
				}
			}
			var orderForm = new Object();
			orderForm.orders = orders;
			orderForm.userId = app.userInfo.id;
			orderForm.orderUsingPoint = app.usePoint;
			orderForm.orderPayment = app.totalPay;
			axios.post("/order/addOrder",orderForm,{
				headers:{'X-CSRF-TOKEN':metaToken}
			})
			.then(function(response){
				console.log(response.data)
				if(response.data.isSuccess == "success"){
					location.href = '/order/complete';
				} else {
					alert(response.data.msg);
				}
			})
		}
		
	}
	
	
	
	
})