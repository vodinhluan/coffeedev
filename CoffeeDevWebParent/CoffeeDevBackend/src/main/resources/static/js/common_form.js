$(document).ready(function() {
	$("#buttonCancel").on("click", function() {
		window.location = "[[@{/users}]]";
	});

	$("#fileImage").change(function() {
		fileSize = this.files[0].size;
		//alert("File Size:   " + fileSize);

		if (fileSize > MAX_FILE_SIZE) {
			this.setCustomValidity("You must choose an image less than " + MAX_FILE_SIZE + " bytes!");
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


