$(document).ready(function() {
	$("#buttonCancel").on("click", function() {
		window.location = "[[@{/users}]]";
	});

	$("#fileImage").change(function() {
		fileSize = this.files[0].size;
		//alert("File Size:   " + fileSize);

		if (fileSize > (1024000)) {
			this.setCustomValidity("You must choose an image less than 1024000  bytes!");
			this.reportValidity();

		} else {
			this.setCustomValidity("");
			showImageThumbnail(this);
		}
	});
});

function showImageThumbnail(fileInput) {
	var file = fileInput.files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		$("#thumbnail").attr("src", e.target.result);
	};

	reader.readAsDataURL(file);
}


