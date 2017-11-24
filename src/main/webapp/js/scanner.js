var divScanResult = $("#div-scan-result");
var tableResult = $("#table-scan-result")
var service = new ScannerService();

$(document).ready(function () {

	$("div .ui-pnotify-container").hide();
	divScanResult.hide();
	
	$("#button-scan").on('click', function (e) {
        e.preventDefault();
        var from = $('#form-scan-input').find('input[name="dirpath"]').val();
        var mediatype = $('input[name="mediatype"]:checked').val();
        if (from == "" || mediatype == null) {
        	showErrorMessage("Parameters not setted.")
        	return;
        }
        scanFiles(from, mediatype);
	});
	
	$("#btn-save-all").on('click', function (e) {
        e.preventDefault();
        saveAllFiles();
	});
	
	$("#btn-save-selected").on('click', function (e) {
        e.preventDefault();
        saveSelectedFiles();
	});
	
	$("#btn-turn-datatable").on('click', function (e) {
        e.preventDefault();
        tableResult.DataTable();
	});
	
	$("#btn-exclude-collection").on('click', function (e) {
        e.preventDefault();
        excludeExistingInCollection();
	});
	
});

function scanFiles(from, mediatype) {
	let promise = service.scanForMedia(from, mediatype);
	promise
		.then( (data) => {
			showSuccessMessage("Scan performed.");
			$("#button-scan").attr("disabled",true);
			$("#dirpath").attr("disabled",true);
			showScanResultTable(data);
			} 
		)
		.catch( (error) => { 			
			showErrorMessage("Fail to scan files with given parameters.")
			}
		);
}

function showScanResultTable(data) {
	divScanResult.show();	
	let tbodyResult = tableResult.find("tbody");		
	let itemCount = data.length;
	
	for (i=0; i < itemCount; i++) {
		var filename = data[i].split("/").pop();
		var newRow = createNewRow(i ,filename, data[i]);
		tbodyResult.append(newRow);
	}
}

function createNewRow(i, fileName, filePath) {
	let rowTr = $("<tr>");
	
	if (i % 2 == 0 ) {
		rowTr.addClass("even pointer");
	} else {
		rowTr.addClass("odd pointer");
	}
	
	let columnCheckboxTd = $("<td>").addClass("a-center");
	let checkboxInput = $("<input>").attr("type","checkbox").attr("name","table_records").attr("value",filePath).addClass("flat");
	columnCheckboxTd.append(checkboxInput);
	let filenameTd = $("<td>").text(fileName);
	let filepathTd = $("<td>").text(filePath);
	
	rowTr.append(columnCheckboxTd);
	rowTr.append(filenameTd);
	rowTr.append(filepathTd);
	
	return rowTr;
}

function saveSelectedFiles() {
	let mediatype = $('input[name="mediatype"]:checked').val();
	let checkeds = $('input[name="table_records"]:checked');
    
    if (checkeds.length == 0) {
    	return showErrorMessage("No items selected.");
    }
	
    for (var i=0; i<checkeds.length; i++){
		var fpath = checkeds[i].value;
		
		let promise = service.saveMediaFile(fpath, mediatype);
		promise
			.then( (data) => {
				showSuccessMessage("File saved: " + data.filePath);
				} 
			)
			.catch( (error) => { 			
				showErrorMessage("Fail to save this file: " + fpath) ;
				}
			);		
    }        
}

function saveAllFiles() {
	let mediatype = $('input[name="mediatype"]:checked').val();
	let inputs = $('input[name="table_records"]');
    
    if (inputs.length == 0) {
    	return showErrorMessage("No items displayed.");
    }
    
    for (var i=0; i<inputs.length; i++){
		let fpath = inputs[i].value;
		
		let promise = service.saveMediaFile(fpath, mediatype);
		promise
			.then( (data) => {
				showSuccessMessage("File saved: " + data.filePath);
				} 
			)
			.catch( (error) => { 			
				showErrorMessage("Fail to save this file: " + fpath) ;
				}
			);	
    }
        
}

function excludeExistingInCollection() {
	let filePaths = $('input[name="table_records"]');

	let promise = service.findAll();
	promise
		.then( (data) => {
			if (data.length > 0) {
				items = data;				
				for (var i=0; i<filePaths.length; i++){ 
					let fpath = filePaths[i].value;
					for (var j=0; j<data.length; j++){   
						let item = JSON.parse(data[j]);					
						if (fpath == item.filePath) {
							filePaths[i].closest("tr").remove();						
						}					
					}				
				}			
			}
		} 
		)
		.catch( (error) => { 			
			showErrorMessage("Fail acessing database...") ;
			}
		);			   	   	
}

// function searchByParams(filepathParam, filenameParam, classificationParam, tagsParam, typeParam) {
// 	let input = { type: typeParam, filepath: filepathParam, filename: filenameParam, classification: classificationParam, tags: tagsParam };
	
// 	$.get("http://localhost:8080/acervorama/webapi/media", input, function( data ) {
// 		if (data.length == 0) { 
// 			showInfoMessage("Search returned no results.");
// 		} else {
// 			showSearchResultTable(data);
// 		}
// 	})
// 	.fail( function() { showErrorMessage("Fail to search files with given parameters.") } )
// 	.success( function() { showSuccessMessage("Search performed.") } );	
// };


function showErrorMessage(msg) {
	return new PNotify({
        title: 'Error!',
        text: msg,
        type: 'error',
        styling: 'bootstrap3'
    });
}

function showSuccessMessage(msg) {
	return new PNotify({
        title: 'Success!',
        text: msg,
        type: 'success',
        styling: 'bootstrap3'
    });
}
