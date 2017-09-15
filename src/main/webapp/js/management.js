var formMediaSearch = $('#form-media-search');
var divSearchResult = $("#div-search-result");
var tableResult = $("#table-search-result");

$(document).ready(function () {
	
	$("div .ui-pnotify-container").hide();
	divSearchResult.hide();
	
	$("#btn-search").on('click', function (e) {
        e.preventDefault();
        
        var filepathParam = formMediaSearch.find('input[name="search-file-path"]').val();
        var filenameParam = formMediaSearch.find('input[name="search-file-name"]').val();
        var classificationParam = formMediaSearch.find('input[name="search-classification"]').val();
        var tagsParam = formMediaSearch.find('input[name="search-tags"]').val();
        var typeParam = $('input[name="mediatype"]:checked').val();
        
        tableResult.find("tbody").find("tr").each(function() {
        	$(this).remove();
        });
        
        searchByParams(filepathParam, filenameParam, classificationParam, tagsParam, typeParam);        
	});	
					
});

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

function showSearchResultTable(data) {
	divSearchResult.show();	
	var tbodyResult = tableResult.find("tbody");		
	var itemCount = data.length;
	
	for (i=0; i < itemCount; i++) {
		var item = JSON.parse(data[i]);
		var newRow = createNewRow(i, item.hash, item.filename, item.filePath, item.type);
		tbodyResult.append(newRow);
	}
	
	tableResult.find(".view-link").on('click', function (e) {
        e.preventDefault();
        var itemhash = $(this).closest("tr").attr("scope");
        showItemModal(itemhash);        
	});
}

function createNewRow(rownum, hash, fileName, filePath, fileType) {
	var rowTr = $("<tr>").attr("scope",hash);
	
	var thNum = $("<th>").attr("scope","row").text(rownum+1);
	var filenameTd = $("<td>").text(fileName);
	var filepathTd = $("<td>").text(filePath);
	var filetypeTd = $("<td>").text(fileType);
	
	var aViewEl = $("<a>").attr("href","#").addClass("view-link").attr("data-toggle","modal").attr("data-target",".item-view-modal").text(" View ");
	
	var actionTd = $("<td>").addClass(" last");
	
	actionTd.append(aViewEl);
	rowTr.append(thNum);
	rowTr.append(filenameTd);
	rowTr.append(filepathTd);
	rowTr.append(filetypeTd);
	rowTr.append(actionTd);
	
	return rowTr;	
}

function showItemModal(itemhash) {
	var divModal = $("#item-modal-view");
	
	var input = { hash: itemhash };
	
	$.get("http://localhost:8080/acervorama/webapi/media", input, function( data ) {
		var item = JSON.parse(data);
		
		var dttm = item.registerDate;
		var dt = dttm.date;
		var tm = dttm.time;
		$("#view-reg-date").val(dt.day + "/" + dt.month + "/" + dt.year + " " + tm.hour + ":" + tm.minute);
		
		if (item.type == "IMAGE") {
			$("#view-title").hide();
			$("#view-author").hide();
		} else if (item.type == "AUDIO" || item.type == "TEXT") {
			$("#view-title").val(item.title);
			$("#view-author").val(item.author);
		} else if (item.type == "VIDEO") {
			$("#view-title").val(item.title);
			$("#view-author").hide();
		}
		
		$("#view-file-path").val(item.filePath);
		$("#view-file-name").val(item.filename);
		$("#view-media-type").val(item.type);
		$("#view-classification").val(item.classification);
		$("#view-media-description").val(item.description);
		$("#view-media-comments").val(item.additionalInformation);
		$("#tags_1").val(item.tags);
		
		
	})
	.fail( function() { showErrorMessage("Fail to connect to database...") } );
		
}


function showErrorMessage(msg) {
	return new PNotify({
        title: 'Error!',
        text: msg,
        type: 'error',
        styling: 'bootstrap3'
    });
};

function showSuccessMessage(msg) {
	return new PNotify({
        title: 'Success!',
        text: msg,
        type: 'success',
        styling: 'bootstrap3'
    });
};

function showInfoMessage(msg) {
	return new PNotify({
		title: 'Info',
        text: msg,
        type: 'info',
        styling: 'bootstrap3'
    });
};
