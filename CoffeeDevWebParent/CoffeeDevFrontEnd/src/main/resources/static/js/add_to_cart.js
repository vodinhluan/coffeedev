$(document).ready(function() {
	$("#buttonAdd2Cart").on("click", function(evt) {
		addToCart();
	});
});

function addToCart() {
	quantity = $("#quantity" + productId).val();
	url = contextPath + "cart/add/" + productId + "/" + quantity;

	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(response) {
		console.log("thanh cong", response);
		showModalDialog("Shopping Cart", response);
	}).fail(function() {
		console.log("that bai", response);
		showErrorModal("Error while adding product to shopping cart.");
	});
}