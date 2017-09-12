$(document).ready(function () {
	
	$("#button-scan").on('click', function (e) {
        e.preventDefault();
        var from = $('#form-scan-input').find('input[name="dirpath"]').val();
        var mediatype = $('input[name="mediatype"]:checked').val(); 
        scanFiles(from, mediatype);
	});
	
});

function scanFiles(path, mediatype) {
	
	console.log("Scanning with Ajax... " + path + " | " + mediatype);
	
}