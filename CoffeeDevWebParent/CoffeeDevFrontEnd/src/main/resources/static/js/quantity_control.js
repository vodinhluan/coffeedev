//$(document).ready(function() {
//    const minusButton = document.getElementById('minusButton');
//    const plusButton = document.getElementById('plusButton');

//    minusButton.addEventListener('click', function () {
//        if (parseInt(quantityInput.value) > 1) {
//            quantityInput.value = parseInt(quantityInput.value) - 1;
//        }
//    });

//    plusButton.addEventListener('click', function () {
//        quantityInput.value = parseInt(quantityInput.value) + 1;
//    });
//});

$(document).ready(function() {
	$(".linkMinus").on("click", function(evt) {
		evt.preventDefault();
		productId = $(this).attr("pid");
		quantityInput = $("#quantity" + productId);
		newQuantity = parseInt(quantityInput.val()) - 1;

		if (newQuantity > 0) {
			quantityInput.val(newQuantity);
		} else {
			showWarningModal('Minimum quantity is 1');
		}
	});

	$(".linkPlus").on("click", function(evt) {
		evt.preventDefault();
		productId = $(this).attr("pid");
		quantityInput = $("#quantity" + productId);
		newQuantity = parseInt(quantityInput.val()) + 1;

		quantityInput.val(newQuantity);
		
	});	
});

function updateQuantityDisplay(productId, quantity) {
    // Find the quantity input and update its value
    $("#quantity" + productId).val(quantity);
}