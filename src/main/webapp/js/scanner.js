var divScanResult = $("#div-scan-result");
var tableResult = $("#table-scan-result")

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
	var input = { type: mediatype, dirPath: from };
	
	$.get("http://localhost:8080/acervorama/webapi/scanner", input, function( data ) {
		showScanResultTable(data);
	})
	.fail( function() { showErrorMessage("Fail to scan files with given parameters.") } )
	.success( function() { 
		showSuccessMessage("Scan performed.");
		$("#button-scan").attr("disabled",true);
		$("#dirpath").attr("disabled",true);
	} );		
}

function showScanResultTable(data) {
	divScanResult.show();	
	var tbodyResult = tableResult.find("tbody");		
	var itemCount = data.length;
	
	for (i=0; i < itemCount; i++) {
		var filename = data[i].split("/").pop();
		var newRow = createNewRow(i ,filename, data[i]);
		tbodyResult.append(newRow);
	}
}

function createNewRow(i, fileName, filePath) {
	var rowTr = $("<tr>");
	
	if (i % 2 == 0 ) {
		rowTr.addClass("even pointer");
	} else {
		rowTr.addClass("odd pointer");
	}
	
	var columnCheckboxTd = $("<td>").addClass("a-center");
	var checkboxInput = $("<input>").attr("type","checkbox").attr("name","table_records").attr("value",filePath).addClass("flat");
	columnCheckboxTd.append(checkboxInput);
	var filenameTd = $("<td>").text(fileName);
	var filepathTd = $("<td>").text(filePath);
	
	rowTr.append(columnCheckboxTd);
	rowTr.append(filenameTd);
	rowTr.append(filepathTd);
	
	return rowTr;
}

function saveSelectedFiles() {
	var mediatype = $('input[name="mediatype"]:checked').val();
	var checkeds = $('input[name="table_records"]:checked');
    
    if (checkeds.length == 0) {
    	return showErrorMessage("No items selected.");
    }
    
    for (var i=0; i<checkeds.length; i++){
    	var fpath = checkeds[i].value;
    	
    	mediaitem = {
   			 "filePath":fpath,
   			 "type":mediatype
   			};
    	
    	$.ajax({
    	      url:"http://localhost:8080/acervorama/webapi/media",
    	      type:"POST",
    	      headers: { 
    	        "Content-Type":"application/json"
    	      },
    	      data: JSON.stringify(mediaitem),
    	      dataType:"json",
    	      success: function (data){
    	    	  showSuccessMessage("File saved: " + data.filePath);
    	      },
    	      error: function (data){
    	    	  showErrorMessage("Fail to save this file: " + data.filePath) ;        
    	      }
    	    });
    }        
}

function saveAllFiles() {
	var mediatype = $('input[name="mediatype"]:checked').val();
	var inputs = $('input[name="table_records"]');
    
    if (inputs.length == 0) {
    	return showErrorMessage("No items displayed.");
    }
    
    for (var i=0; i<inputs.length; i++){
    	var fpath = inputs[i].value;
    	
    	mediaitem = {
   			 "filePath":fpath,
   			 "type":mediatype
   			};
    	
    	$.ajax({
    	      url:"http://localhost:8080/acervorama/webapi/media",
    	      type:"POST",
    	      headers: { 
    	        "Content-Type":"application/json"
    	      },
    	      data: JSON.stringify(mediaitem),
    	      dataType:"json",
    	      success: function (data){
    	    	  showSuccessMessage("File saved: " + data.filePath);
    	      },
    	      error: function (data){
    	    	  showErrorMessage("Fail to save this file: " + data.filePath) ;        
    	      }
    	    });
    }
        
}

function excludeExistingInCollection() {
	var filePaths = $('input[name="table_records"]');
	
	$.get("http://localhost:8080/acervorama/webapi/media", function( data ) {
		if (data.length > 0) {
			items = data;
	
			for (var i=0; i<filePaths.length; i++){ 
				var fpath = filePaths[i].value;
				for (var j=0; j<data.length; j++){   
					var item = JSON.parse(data[j]);					
					if (fpath == item.filePath) {
						filePaths[i].closest("tr").remove();						
    				}					
				}				
			}			
		}
	})
	.fail( function() { showErrorMessage("Fail acessing database...") } );		   	   	
}

function searchByParams(filepathParam, filenameParam, classificationParam, tagsParam, typeParam) {
	var input = { type: typeParam, filepath: filepathParam, filename: filenameParam, classification: classificationParam, tags: tagsParam };
	
	$.get("http://localhost:8080/acervorama/webapi/media", input, function( data ) {
		if (data.length == 0) { 
			showInfoMessage("Search returned no results.");
		} else {
			showSearchResultTable(data);
		}
	})
	.fail( function() { showErrorMessage("Fail to search files with given parameters.") } )
	.success( function() { showSuccessMessage("Search performed.") } );	
};


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
