$(document).ready(function () {
	
	$("#button-scan").on('click', function (e) {
        e.preventDefault();
        var from = $('#form-scan-input').find('input[name="dirpath"]').val();
        var mediatype = $('input[name="mediatype"]:checked').val(); 
        scanFiles(from, mediatype);
	});
	
});

function scanFiles(from, mediatype) {
	var input = { type: mediatype, path: from };
	
	console.log(input);
	
	$.get("http://localhost:8080/acervorama/webapi/scanner", { type: mediatype, dirPath: from }, function( data ) {
		console.log("Data = " + data );
	});
	
	console.log("Após get ...");
	
}

function pegaRetorno(dados) {
	console.log(dados);
}