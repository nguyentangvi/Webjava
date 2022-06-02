app.controller("product-ctrl",function($scope,$http){
	$scope.items=[];
	$scope.cates=[];
	$scope.form={};
	
	$scope.initialize = function(){
		//load products tu csdl
		$http.get("/rest/products").then(resp => { // dung get de lay
			$scope.items= resp.data; // bo vao itesm de hien table
			$scope.items.forEach(item => {
			item.createDate= new Date(item.createDate)		// chuyen doi ngay thang trong jvcrip
				
			})
			});
	
		//load categories
		$http.get("/rest/categories").then(resp => {
			$scope.cates= resp.data;
			
			});
	
	}
	//khoi dau
	$scope.initialize();
	//xoa form
	$scope.reset=function(){ //phuong thuc reset
		$scope.form={
			createDate: new Date(),
			image: 'cloud-upload.jpg',
			available : true,
			discount:0,
			price:0
		}
	}
	//hien thi len form
	$scope.edit = function(item){
		$scope.form= angular.copy(item);
		
	}
	//them moi san pham 
	$scope.create = function(){
		var item = angular.copy($scope.form); // lay thong tin tren form ra item
		$http.post('/rest/products',item).then(resp => { // sau do post 
				resp.data.createDate = new Date(resp.data.createDate) 
		$scope.items.push(resp.data); // bo vao cai list vi cai list chua co
		$scope.reset();// rest form
		alert("Thêm mới sản phẩm thành công !");
}).catch(error => {
	alert("Lỗi thêm mới sản phẩm !");
	console.log("Error",error) 
	
});
		
		
	}
	//cap nhat san pham
	$scope.update= function(){
			var item = angular.copy($scope.form); // lay thong tin tren form ra item
		$http.put(`/rest/products/${item.id}`,item).then(resp => { // sau do put
			var index = $scope.items.findIndex(p => p.id == item.id);  // sau khi du lieu phan hoi thay đổi sản phẩm bên trong list ở phía client
			$scope.items[index]=item;	
		
		alert("Cập nhật thành công !");
}).catch(error => {
	alert("Lỗi cập nhật sản phẩm !");
	console.log("Error",error) 
	
});
	}
	//xoa san pham 
	$scope.delete = function(item){
		$http.delete(`/rest/products/${item.id}`).then( resp => {
			var index = $scope.items.findIndex(p => p.id == item.id); //thong tin tra id ve tu server
			$scope.items.splice(index,1);  //sản phẩm thay đổi list cũng thay đổi theo
			$scope.reset();
			alert("Xoá thành công !");
}).catch(error => {
	alert("Lỗi xóa sản phẩm !");
	console.log("Error",error) 
	
});
	}
	//upload hinh 
	$scope.imageChanged=function(files){
		var data = new FormData();
		
		data.append('file',files[0]);
		$http.post('/rest/upload/images',data,{
			transformRequest: angular.identity,
			headers: {'Content-Type' : undefined}
		}).then(resp => {
			$scope.form.image = resp.data.name;
			
		}).catch(error => {
			alert("Lỗi Upload hình ảnh");
			console.log("Error", error);
			
		})
			
		
	}
	$scope.pager={
		page:0,
		size:5,
		get items(){
			var start= this.page * this.size;
		 return	$scope.items.slice(start,start + this.size);
			
		},
		get count(){
			return Math.ceil(1.0 * $scope.items.length/this.size);
		},
		first(){
			this.page = 0;
		},
		prev(){
			this.page--;
			if(this.page < 0){
				this.last();
				
			}
		},
		next(){
			this.page++;
			if(this.page >= this.count){
				this.first();
		}},
		last(){
			this.page = this.count -1 ;
		}
		
		
		
	}
	
	
});