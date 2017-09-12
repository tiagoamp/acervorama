$(document).ready(function () {

	 // $("#div-scan-result").hide();
	
	$("#button-scan").on('click', function (e) {
        e.preventDefault();        
        $("#div-scan-result").toggle();
        var from = $('#form-scan-input').find('input[name="dirpath"]').val();
        var mediatype = $('input[name="mediatype"]:checked').val(); 
        scanFiles(from, mediatype);
	});
	
});

function scanFiles(from, mediatype) {
	var input = { type: mediatype, dirPath: from };
	
	$.get("http://localhost:8080/acervorama/webapi/scanner", input, function( data ) {
		showScanResultTable(data);
	});
		
}

function showScanResultTable(data) {
	
	console.log("Received " + data );
	
	$("#div-scan-result").toggle();
	
}
