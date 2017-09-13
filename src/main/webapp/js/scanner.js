var divScanResult = $("#div-scan-result");
var tableResult = $("#table-scan-result")

$(document).ready(function () {

	$("div .ui-pnotify-container").hide();
	divScanResult.hide();
	
	$("#button-scan").on('click', function (e) {
        e.preventDefault();        
        var from = $('#form-scan-input').find('input[name="dirpath"]').val();
        var mediatype = $('input[name="mediatype"]:checked').val();
        scanFiles(from, mediatype);
	});
	
});

function scanFiles(from, mediatype) {
	var input = { type: mediatype, dirPath: from };
	
	$.get("http://localhost:8080/acervorama/webapi/scanner", input, function( data ) {
		showScanResultTable(data);
	})
	.fail(function() {
		new PNotify({
            title: 'Error!',
            text: 'Fail to scan files with given parameters.',
            type: 'error',
            styling: 'bootstrap3'
        });
	})
	.success(function() {
		new PNotify({
            title: 'Success',
            text: 'Scan performed.',
            type: 'success',
            styling: 'bootstrap3'
        });
	});
	
	// TODO: implementar fail !!!
		
}

function showScanResultTable(data) {
	divScanResult.show();
	
	var tbodyResult = tableResult.find("tbody");
		
	var itemCount = data.length;
	
	for (i=0; i < itemCount; i++) {
		var newRow = createNewRow("File Name", "File Path");
		
		console.log(newRow);
		
		tbodyResult.append(newRow);
	}
		
	tableResult.DataTable();		
}

function createNewRow(fileName, filePath) {
	var rowTr = $("<tr>");
	if (i % 2 == 0 ) {
		rowTr.addClass("even pointer");
	} else {
		rowTr.addClass("odd pointer");
	}
	var columnCheckboxTd = $("<td>").addClass("a-center");
	var checkboxInput = $("<input>").attr("type","checkbox").attr("name","table_records").addClass("flat");
	columnCheckboxTd.append(checkboxInput);
	var filenameTd = $("<td>").text(fileName);
	var filepathTd = $("<td>").text(filePath);
	
	rowTr.append(columnCheckboxTd);
	rowTr.append(filenameTd);
	rowTr.append(filepathTd);
	
	return rowTr;
}
