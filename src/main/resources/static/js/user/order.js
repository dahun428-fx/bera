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
		totalPay:0,
		allCheckbox:true,
		checkboxList:[],
		orderType:""
	},
	beforeCreate:function(){
		axios.get('/order/products',{
			headers:{'X-CSRF-TOKEN':metaToken}
		}).then(function(response){
			const userInfo = response.data.userInfo;
			const orderList = response.data.orderList;
			const orderType = response.data.orderType;
			
			if(orderList == null) {
				alert('유효하지 않은 접근입니다. 다시 시도해주세요');
				location.href = '/';
				return;
			}

			app.userInfo = userInfo;
			app.orders = orderList;
			app.orderType = orderType;
			app.addCheckboxList(app.orders);
			console.log(app.orderType);
		})
	},
	filters:{
		currency:function(value){
			value = new Number(value);
			return value.toLocaleString();
		}
	},
	computed:{
		totalPayCompute:function(){
			let totalPay = this.totalPrice - this.totalDiscountPrice - this.usePoint;
			this.totalPay = totalPay;
			return totalPay;
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
			let list = app.checkboxList;
			for(var i in list){
				if(!list[i].isChecked){
					app.allCheckbox = false;
					return;
				} 
				if(list[i].isChecked){
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
			console.log('orders 1 : ',orders);
			
			for(var i = 0; i < list.length; i++){
				console.log(list[i].isChecked)
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
			console.log('orders 2: ',orders);
			if(orders == ''){
				alert('상품을 1개 이상 선택해주세요');
				return;
			}
			
			var orderForm = new Object();
			orderForm.orders = orders;
			orderForm.userId = app.userInfo.id;
			orderForm.orderUsingPoint = app.usePoint;
			orderForm.orderPayment = app.totalPay;
			orderForm.orderType = app.orderType;
			
			axios.post("/order/addOrder",orderForm,{
				headers:{'X-CSRF-TOKEN':metaToken}
			})
			.then(function(response){
				if(response.data.isSuccess == "success"){
					location.href = '/order/complete';
				} else {
					alert(response.data.msg);
				}
			})
		}
		
	}
	
	
	
	
})