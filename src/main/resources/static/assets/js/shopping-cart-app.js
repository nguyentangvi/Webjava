const app = angular.module("shopping-cart-app",[]);
app.controller("shopping-cart-ctrl",function($scope,$http){
	// Quan Li Gio Hang
	$scope.cart={
		items: [] ,
		// them san pham vao gio hang
		add(id){
			var item = this.items.find(item => item.id == id); // them vao 
			if(item){ 
				item.qty++; // neu item co roi thi tang len 
 				this.saveToLocalStorage(); // và lưu vào local
				alert("Đã thêm vào giỏ hàng"); 
			} else{
				$http.get(`/rest/products/${id}`).then(resp => { // tải sản phẩm về
					resp.data.qty = 1; // số lượng =1
					this.items.push(resp.data); // bỏ vào ds các mặt hàng
					this.saveToLocalStorage(); // lưu vào local
					
					
				})
				
			}
			
		},
		// xoa san pham khoi gio hang 
		remove(id){
			var index = this.items.findIndex(item => item.id ==id); //tim vi tri id cua gio hang thong qua id 
			this.items.splice(index,1); // phuong thuc splid de xoa
			this.saveToLocalStorage(); // luu lai
		},
		//xoa sach cac mach hang trong gio
		clear(){
			this.items= []
			this.saveToLocalStorage();
		},
			// tinh tong so luong cac mat hang trong gio
		get count(){
			return this.items
			.map(item => item.qty) // dau tien duyet qua mat hang lay ra quantity
			.reduce((total,qty) => total += qty,0); // dung phuong thuc reduce tinh tong
			
		},
		//tong thanh tien cac mat hang tron gio
		get amount(){
			return this.items
			.map(item => item.qty * item.price) // tong so luong * don gia ra thanh tien
			.reduce((total,qty) => total += qty,0);
		},
		//luu gio hang vao local storage
		saveToLocalStorage(){
			var json = JSON.stringify(angular.copy(this.items)); // dung angular de coppy roi chuyen qua json
			localStorage.setItem("cart",json);
			 
		},
		//doc gio hang tu local storage
		loadFromLocalStoge(){
			var json = localStorage.getItem("cart");// doc cart trong local storage va gan vao items
			this.items=json ? JSON.parse(json) : []; //luu khi reset khong bi mat 
		}
		
		}
		$scope.cart.loadFromLocalStoge();
		
		$scope.order={
			createDate: new Date(),
			address: "",
			account:{username: $("#username").text()},
			get orderDetails(){
				 	return $scope.cart.items.map(item => {
						return {
							product : {id : item.id},
							price : item.price*(1-item.discount*0.01),
							quantity : item.qty
							
							
						}
					
				});
				 
			},
			purchase(){
				var order = angular.copy(this);
				// thuc hien dat hang
				$http.post("/rest/orders" , order).then(resp => {
					alert("Đặt Hàng Thành Công !");
					$scope.cart.clear();
					location.href = "/order/detail/" + resp.data.id;
					
				}).catch(error => {
					alert("đặt hàng lỗi !")
					console.log(error)
				})
			}
		}
	
	
})