const app = angular.module("cart-app", []);

app.controller("cart-ctrl", function($scope, $http) {

	$scope.result = "";

	var results;

	$scope.cart = {
		products: [],
		isEmptyCart: true, // Thêm biến isEmptyCart

		add(productId) {
			var product = this.products.find(product => product.productId == productId);
			var quantity = parseInt(document.getElementById("quantityInput").value) || 1; // Mặc định số lượng là 1 nếu không có giá trị nhập vào
			if (product) {
				product.qty += quantity;
				this.saveToLocalStorage();
			} else {
				$http.get(`/api/products/${productId}`).then(resp => {
					resp.data.qty = quantity;
					resp.data.image = `/image/product/${resp.data.image}`;
					this.products.push(resp.data);
					this.saveToLocalStorage();
				});
			}
			this.isEmptyCart = false; // Giỏ hàng không còn trống
			// Hiển thị form "ADDED TO CART"
			var toast = document.getElementById("toast");
			toast.classList.add("show");
			setTimeout(function() {
				toast.classList.remove("show");
			}, 10000);
		},

		get count() {
			return this.products
				.map(product => product.qty)
				.reduce((total, qty) => total += qty, 0);
		},

		get amount() {
			return this.products
				.map(product => product.qty * product.price)
				.reduce((total, qty) => total += qty, 0);
		},
		get amountPrice() {
			return this.amount * 23000;
		},

		saveToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.products));
			localStorage.setItem("cart", json);
		},

		loadFromLocalStorage() {
			var json = localStorage.getItem("cart");
			this.products = json ? JSON.parse(json) : [];
			this.isEmptyCart = this.products.length === 0; // Cập nhật giá trị của isEmptyCart
		},

		remove(productId) {
			var index = this.products.findIndex(product => product.productId == productId);
			this.products.splice(index, 1);
			this.saveToLocalStorage();
			if (this.products.length === 0) {
				this.isEmptyCart = true; // Giỏ hàng trở thành rỗng
			}
		},

		clear() {
			this.products = []
			this.saveToLocalStorage();
		},
	}

	$scope.cart.loadFromLocalStorage();
	//	$scope.continueToPayment = function() {
	//		if (!$scope.order.address || !$scope.order.phone) {
	//			var errorMessage = document.getElementById("message");
	//			errorMessage.textContent = "Please provide complete order information!";
	//			return;
	//		}
	//		location.href = "/vnpay";
	//	};

	const usernameInput = angular.element(document.getElementById('username'));
	$scope.order = {
		email: usernameInput.val(),
		address: "",
		phone: "",

		purchase() {
			var order = angular.copy(this);
			order.statusOrderID = 1;
			if (!order.address || !order.phone) {
				var errorMessage = document.getElementById("message");
				errorMessage.textContent = "Please provide complete order information!";
				return;
			}

			$http.post("/rest/orders/create", order).then(resp => {
				// Lấy thông tin đơn hàng vừa tạo từ phản hồi
				var createdOrder = resp.data;

				// Lặp qua các sản phẩm trong giỏ hàng
				$scope.cart.products.forEach(product => {
					// Tạo một đối tượng orderDetail từ thông tin sản phẩm và đơn hàng
					var orderDetail = {
						orderId: createdOrder.orderId,
						productId: product.productId,
						quantity: product.qty,
						price: product.price
					};

					// Gửi yêu cầu POST để tạo orderDetail
					$http.post("/rest/orderdetails/create", orderDetail).then(response => {
						console.log("Order detail created:", response.data);
					}).catch(error => {
						console.log("Error creating order detail:", error);
					});
				});

				alert("Order Confirmed!");
				$scope.cart.clear();
				location.href = "/order/detail/" + resp.data.orderId;
			}).catch(error => {
				var errorMessage = document.getElementById("message");
				errorMessage.textContent = "Order processing error. Please retry later.";
				console.log(error)
			})
		}
	}
});