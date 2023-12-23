app.controller("product-ctrl", function($scope, $http) {
	$scope.items = [];
	$scope.cates = [];
	$scope.form = {};
	$scope.field = [];
	$scope.status = [];
	$scope.error = ['err'];


	$scope.initialize = function() {
		let productCount = 0;
		let productPrice = 0;

		$http.get("/api/products").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.createDate = new Date(item.createDate)
				productCount++;
				productPrice = item.price.toLocaleString();
			})
			$scope.productCount = productCount;
			$scope.productPrice = productPrice;
		});
		$http.get("/api/categories").then(resp => {
			$scope.cates = resp.data;
		});
		$http.get("/rest/parent").then(resp => {
			$scope.field = resp.data;
		});
		$http.get("/rest/statusproducts").then(resp => {
			$scope.status = resp.data;
		});
	}

	$scope.pager = {
		page: 0,
		size: 10,
		get items() {
			const start = this.page * this.size;
			return $scope.items.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil($scope.items.length / this.size);
		},
		first() {
			this.page = 0;
		},
		prev() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) { // Thay đổi '>' thành '>=' để tránh vượt quá phạm vi
				this.last();
			}
		},
		last() {
			this.page = this.count - 1;
		}
	};




	$scope.initialize();

	$scope.toggleAll = function() {
		$scope.items.forEach(function(item) {
			item.checked = !item.checked;
		});
	};

	// Thêm sản phẩm mới
	$scope.create = function() {
		var item = angular.copy($scope.form);

		// Kiểm tra xem sản phẩm đã tồn tại chưa
		var existingProduct = $scope.items.find(i => i.productName === item.productName);
		if (existingProduct) {
			alert("Tên sản phẩm đã tồn tại. Vui lòng chọn tên khác.");
			return;
		}

		$http.post(`/api/products`, item).then(resp => {
			$scope.items.push(resp.data);
			/*			$scope.reset();*/
			alert("Thêm sản phẩm thành công!");
			console.log($scope.form)
		}).catch(error => {
			alert("Lỗi thêm mới sản phẩm");
			console.log("Error", error);
		});
	}

	$scope.edit = function(item) {
		$scope.form = angular.copy(item)
	};

	// Upload hình
	$scope.imageChanged = function(files, index) {
		// Kiểm tra xem có hình ảnh hiện có hay không
		let images = $scope.form.image ? $scope.form.image.split(',') : [];

		// Tải lên hình ảnh đã chọn
		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/rest/upload/product', data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			// Nếu không có hình ảnh hiện có, hãy thêm hình ảnh mới vào mảng trống
			if (!images.length) {
				images.push(resp.data.name);
			} else {
				// Thêm hình ảnh mới vào mảng hình ảnh hiện có
				images[index] = resp.data.name;
			}

			// Cập nhật thuộc tính image của biểu mẫu với mảng được cập nhật
			$scope.form.image = images.join(',');

		}).catch(error => {
			alert("Lỗi upload hình ảnh");
			console.log("Error", error);
		})
	}

	$scope.update = function(product) {
		// Tạo một bản sao của sản phẩm cần cập nhật
		var updatedProduct = angular.copy(product);

		// Gọi API để cập nhật sản phẩm
		$http.put(`/api/products/${product.productId}`, updatedProduct).then(resp => {

			// Cập nhật lại sản phẩm trong danh sách sản phẩm
			var index = $scope.items.findIndex(p => p.productId === product.productId);
			if (index !== -1) {
				$scope.items[index] = updatedProduct;
			}

			// Hiển thị thông báo cập nhật thành công
			alert("Cập nhật sản phẩm thành công!");

		}).catch(error => {
			// Hiển thị thông báo lỗi cập nhật
			alert("Lỗi cập nhật sản phẩm");
			console.log('Error:', error);
		});
	};

	$scope.reset = function() {
		$scope.form = {}
	}

	$scope.$watch('searchText', function(term) {
		$scope.filtered = filterFilter($scope.items, term);
		$scope.size = $scope.filtered.length;
		$scope.noOfPages = Math.ceil($scope.filtered.length / $scope.entryLimit);
	}, true);


	$scope.delete = function(productId) {

		var id = productId;
		var AproductId = $scope.items.filter(item => item.checked).map(item => item.productId);

		if (AproductId.length == 0) {
			// Lắng nghe sự kiện click của nút "Xóa" trong modal
			$('#deleteproduct').find('.delete-button').on('click', function() {
				// Thực hiện thao tác xóa
				$http.delete(`/api/products/${id}`).then(resp => {

					// Xóa sản phẩm khỏi danh sách
					var index = $scope.items.findIndex(p => p.productId === id);
					if (index !== -1) {
						$scope.items.splice(index, 1);
					}

				}).catch(error => {
					// Hiển thị thông báo lỗ	i
					alert('Lỗi Xóa sản phẩm');
					console.log('Error:', error);
				});
			});

		} else {
			//			 Lắng nghe sự kiện click của nút "Xóa" trong modal
			$('#deleteproduct').find('.delete-button').on('click', function() {
				// Thực hiện thao tác xóa
				AproductId.forEach(AproductId => {
					$http.delete(`/api/products/${AproductId}`).then(resp => {

						// Xóa sản phẩm khỏi danh sách
						var index = $scope.items.findIndex(p => p.productId === AproductId);
						if (index !== -1) {
							$scope.items.splice(index, 1);
						}

					}).catch(error => {
						// Hiển thị thông báo lỗi
						alert('Lỗi Xóa sản phẩm');
						console.log('Error:', error);
					});
				});
			});
		}
	};
});
app.controller("category-ctrl", function($scope, $http) {
	$scope.items = [];
	$scope.cates = [];
	$scope.form = {};
	$scope.field = [];
	$scope.error = ['err'];
	$scope.tempImageName = [];

	$scope.initialize = function() {

		$http.get("/api/categories").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach((item) => {
				$http.get("/api/products").then(resp => {
					$scope.field = resp.data;
					// Duyệt qua danh sách sản phẩm và cộng tổng số lượng sản phẩm có categoryID trùng nhau
					$scope.field.forEach((item1) => {
						let totalQuantity = 0;
						$scope.field.forEach((product) => {
							if ((item.categoryID === product.category.categoryID)) {
								totalQuantity++;
							}
						});

						item.quantity = totalQuantity;
					});
				});
			})
		});
		/*		$http.get("/rest/categories").then(resp => {
					$scope.items = resp.data;
		
					// Duyệt qua danh sách danh mục và cộng tổng số lượng danh mục có sản phẩm
					$scope.items.map((item) => {
						item.quantity = $scope.field.filter((product) => product.categoryID === item.categoryID).length;
					});
				});*/

		// Load categories
		$http.get("/rest/parent").then(resp => {
			$scope.cates = resp.data;

		});
	}




	$scope.pager = {
		page: 0,
		size: 10,
		get items() {
			const start = this.page * this.size;
			return $scope.items.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil($scope.items.length / this.size);
		},
		first() {
			this.page = 0;
		},
		prev() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) { // Thay đổi '>' thành '>=' để tránh vượt quá phạm vi
				this.last();
			}
		},
		last() {
			this.page = this.count - 1;
		}
	};


	$scope.$watch('searchText', function(term) {
		$scope.filtered = filterFilter($scope.items, term);
		$scope.size = $scope.filtered.length; // Cập nhật size của pager
		$scope.noOfPages = Math.ceil($scope.filtered.length / $scope.entryLimit);
	}, true);






	// Khởi đầu
	$scope.initialize();
	// Thêm sản phẩm mới
	$scope.create = function() {
		var item = angular.copy($scope.form);
		// Kiểm tra xem sản phẩm đã tồn tại chưa
		var existingProduct = $scope.items.find(i => i.categoryName === item.categoryName);
		if (existingProduct) {
			alert("Tên sản phẩm đã tồn tại. Vui lòng chọn tên khác.");
			return;
		}

		$http.post(`/api/categories`, item).then(resp => {
			$scope.items.push(resp.data);
			/*			$scope.reset();*/

			alert("Thêm loại sản phẩm thành công!");
		}).catch(error => {
			alert("Lỗi thêm mới loại sản phẩm");
			console.log("Error", error);
		});
	}

	// Upload hình
	$scope.imageChanged = function(files) {
		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/rest/upload/category', data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.form.image = resp.data.name;
		}).catch(error => {
			alert("Lỗi upload hình ảnh");
			console.log("Error", error);
		})

	}



	// Xóa sản phẩm 
	$scope.delete = function(item) {
		$('#deletecategory').find('.delete-button').on('click', function() {
			$http.delete(`/api/categories/${item.categoryID}`).then(resp => {
				var index = $scope.items.findIndex(p => p.categoryID == item.categoryID);
				$scope.items.splice(index, 1);
				/*			$scope.reset();*/
			})
				.catch(error => {
					console.log("Error", error);
				});
		});
	}


	// Hiện thị lên form
	//	$scope.edit = function(item) {
	//		$scope.form = angular.copy(item);
	//	}




	// Hiện thị lên form
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
	}

	// cặp nhật sản phẩm
	$scope.update = function() {
		var item = angular.copy($scope.form);
		$http.put(`/api/categories/${item.categoryID}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.categoryID == item.categoryID);
			$scope.items[index] = item;
			alert("Cập nhật sản phẩm thành công!");
		})
			.catch(error => {
				alert("Lỗi cập nhật sản phẩm");
				console.log("Error", error);
			});
	}
});

app.controller("dashboard-ctrl", function($scope, $http) {
	$scope.items = [];
	$scope.cates = [];
	$scope.form = {};
	$scope.field = [];
	$scope.status = [];
	$scope.error = ['err'];


	$scope.initialize = function() {

		$http.get("/rest/orders/admin").then(resp => {
			$scope.items = resp.data;
		});
	}




	$scope.initialize();

	$scope.update = function(item) {
		$http.put(`/rest/orders/${item.orderId}`, item)
			.then(resp => {
				var index = $scope.items.findIndex(p => p.orderId == item.orderId);
				$scope.items[index] = item;
			})
			.catch(error => {
				alert("Lỗi cập nhật trạng thái");
				console.log("Error", error);
			});
	};
});

