$(document).ready(function () {
	
	$("#button-scan").on('click', function (e) {
        e.preventDefault();
        var from = $('#form-scan-input').find('input[name="fromPath"]').val(); 
        scanFiles(from);
	});
	
});

function scanFiles(path) {
	console.log("Scanning with Ajax... " + path );
}