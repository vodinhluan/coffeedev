$(document).ready(function() {
	$(".linkMinus").on("click", function(evt) {
		evt.preventDefault();
		decreaseQuantity($(this));

	});

	$(".linkPlus").on("click", function(evt) {
		evt.preventDefault();
		increaseQuantity($(this));

	});
});

function updateQuantityDisplay(productId, quantity) {
	$("#quantity" + productId).val(quantity);
}

function decreaseQuantity(link) {
	productId = link.attr("pid");
	quantityInput = $("#quantity" + productId);
	newQuantity = parseInt(quantityInput.val()) - 1;
	console.log(newQuantity);


	if (newQuantity > 0) {
		quantityInput.val(newQuantity);
		updateQuantity(productId, newQuantity);
	} else {
		alert('xóa sản phẩm nhé');
		deleteProduct(productId);

	}
}

function increaseQuantity(link) {
	productId = link.attr("pid");
	quantityInput = $("#quantity" + productId);
	newQuantity = parseInt(quantityInput.val()) + 1;
	console.log(newQuantity);

	quantityInput.val(newQuantity);
	updateQuantity(productId, newQuantity);

}



function updateQuantity(productId, quantity) {
	url = contextPath + "cart/update/" + productId + "/" + quantity;

	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(updatedSubtotal) {
		updateSubtotal(updatedSubtotal, productId);
		updateTotal();
	}).fail(function() {
		alert("Error while updating product quantity.");
	});
}

function deleteProduct(productId) {
	url = contextPath + "cart/remove/" + productId;

	$.ajax({
		type: "DELETE",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(response) {
		alert(response);

		updateTotal();
		updateCountNumbers();

		window.location.href = contextPath + "cart";
	}).fail(function() {
		alert("Error while removing product.");
	});
}

function updateSubtotal(updatedSubtotal, productId) {
	formattedSubtotal = updatedSubtotal.toLocaleString('en-US', { minimumFractionDigits: 1 });
	$("#subtotal" + productId).text(formattedSubtotal);
}

function updateTotal() {
	total = 0.0;
	productCount = 0;

	$(".subtotal").each(function(index, element) {
		productCount++;
		total += parseFloat(element.innerHTML.replaceAll(",", ""));
	});

	if (productCount < 1) {
		showEmptyShoppingCart();
	} else {
		formattedTotal = $.number(total, 2);
		$("#total").text(formattedTotal);
	}

}

function showEmptyShoppingCart() {
	$("#sectionTotal").hide();
	$("#sectionEmptyCartMessage").removeClass("d-none");
}

function removeProduct(link) {
	url = link.attr("href");

	$.ajax({
		type: "DELETE",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(response) {
		alert(response);

		updateTotal();
		updateCountNumbers();

		// Handle the redirect manually
		window.location.href = "/coffeedev/cart";

	}).fail(function() {
		showErrorModal("Error while removing product.");
	});
}



function removeProductHTML(rowNumber) {
	$("#row" + rowNumber).remove();
	$("#blankLine" + rowNumber).remove();
}

function updateCountNumbers() {
	$(".divCount").each(function(index, element) {
		element.innerHTML = "" + (index + 1);
	});
}