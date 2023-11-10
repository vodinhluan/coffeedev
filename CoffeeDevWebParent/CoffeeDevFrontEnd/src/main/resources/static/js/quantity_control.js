$(document).ready(function() {
    const quantityInput = document.getElementById('quantityInput');
    const minusButton = document.getElementById('minusButton');
    const plusButton = document.getElementById('plusButton');

    minusButton.addEventListener('click', function () {
        if (parseInt(quantityInput.value) > 1) {
            quantityInput.value = parseInt(quantityInput.value) - 1;
        }
    });

    plusButton.addEventListener('click', function () {
        quantityInput.value = parseInt(quantityInput.value) + 1;
    });
});
