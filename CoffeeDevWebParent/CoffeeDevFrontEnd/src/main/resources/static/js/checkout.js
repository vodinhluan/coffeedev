function handleDistrictChange() {
	var districtSelect = document.getElementById('quan');
	var selectedDistrict = districtSelect.value;

	// Kiểm tra xem option "Chọn quận" đã được chọn chưa
	var isSelectDistrictOption = selectedDistrict === '';

	if (isSelectDistrictOption) {
		resetShippingPriceUI();
	} else {
		updateShippingPrice();
	}
}

function handlePaymentMethodChange() {
	var paymentMethodSelect = document.getElementById('paymentMethod');
	var bankTransferInfo = document.getElementById('bankTransferInfo');

	// Hiển thị hoặc ẩn thông tin thanh toán bằng chuyển khoản tùy thuộc vào lựa chọn
	if (paymentMethodSelect.value === 'bankTransfer') {
		bankTransferInfo.style.display = 'block';
	} else {
		bankTransferInfo.style.display = 'none';
	}
}
function validateForm() {
	// Lấy các trường input và select
	// ...

	// Kiểm tra xem tất cả các trường đã được nhập đủ thông tin hay không
	if (nameField.checkValidity() && phoneNumberField.checkValidity() && addressField.checkValidity() &&
		districtField.checkValidity() && paymentMethodField.checkValidity()) {

		if (paymentMethodField.value === 'bankTransfer') {
			// Kiểm tra và xử lý thông tin thanh toán bằng chuyển khoản nếu được chọn
			var accountNameField = document.getElementById('accountName');
			var accountNumberField = document.getElementById('accountNumber');

			if (accountNameField.checkValidity() && accountNumberField.checkValidity()) {
				// Nếu thông tin hợp lệ, cho phép submit
				return true;
			} else {
				// Nếu thông tin không hợp lệ, hiển thị cảnh báo và ngăn chặn submit
				alert('Vui lòng nhập đủ thông tin thanh toán bằng chuyển khoản.');
				return false;
			}
		}

		return true; // Cho phép submit nếu tất cả đều hợp lệ
	} else {
		alert('Vui lòng nhập đủ thông tin.');
		return false; // Ngăn chặn submit nếu có trường không hợp lệ
	}
}

function updateShippingPrice() {
	var districtSelect = document.getElementById('quan');
	var selectedDistrict = districtSelect.value;

	fetch('/coffeedev/api/districts/' + encodeURIComponent(selectedDistrict))
		.then(response => response.json())
		.then(data => {
			// Kiểm tra xem data có phải là số không
			if (typeof data === 'number') {
				updateShippingPriceUI(data);
			} else {
				console.error('Invalid data format received from the server.');
			}
		})
		.catch(error => console.error(error));
}

function updateShippingPriceUI(price) {
	var shippingPriceElement = document.getElementById('shipping-price');
	// Cập nhật nội dung của phần tử với giá giao hàng
	shippingPriceElement.textContent = price + '.000';

	// Cập nhật tổng tiền
	calculateTotalPrice();
}

function resetShippingPriceUI() {
	var shippingPriceElement = document.getElementById('shipping-price');
	// Reset nội dung của phần tử về giá trị rỗng
	shippingPriceElement.textContent = '';

	// Đặt tổng tiền về thành tiền
	calculateTotalPrice();
}

function calculateTotalPrice() {
	var totalPrice = parseFloat(document.getElementById('total').textContent);
	var shippingPrice = parseFloat(document.getElementById('shipping-price').textContent);

	var superTotalPriceElement = document.getElementById('super-total-price');

	if (isNaN(shippingPrice)) {
		// Nếu shippingPrice là NaN, tức là không chọn quận, đặt tổng tiền về thành tiền
		superTotalPriceElement.textContent = totalPrice.toFixed(3);
	} else {
		// Ngược lại, cộng thêm giá trị shippingPrice vào tổng tiền
		superTotalPriceElement.textContent = (totalPrice + shippingPrice).toFixed(3);
	}
}

function thanhToan() {
	alert("Thanh Toán Thành Công!");
	window.location.href = 'http://localhost/coffeedev/'; 

}