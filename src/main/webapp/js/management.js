var formMediaSearch = $('#form-media-search');
var divSearchResult = $("#div-search-result");
var tableResult = $("#table-search-result");
var service = new ManagementService();

$(document).ready(function () {
	
	$("div .ui-pnotify-container").hide();
	divSearchResult.hide();
	
	$("#btn-search").on('click', function (e) {
        e.preventDefault();
        
        let filepathParam = formMediaSearch.find('input[name="search-file-path"]').val();
        let filenameParam = formMediaSearch.find('input[name="search-file-name"]').val();
        let classificationParam = formMediaSearch.find('input[name="search-classification"]').val();
        let tagsParam = formMediaSearch.find('input[name="search-tags"]').val();
        let typeParam = $('input[name="mediatype"]:checked').val();
        
        tableResult.find("tbody").find("tr").each(function() {
        	$(this).remove();
        });
        
        searchByParams(filepathParam, filenameParam, classificationParam, tagsParam, typeParam);        
	});
	
	
	$("#btn-save").on('click', function (e) {
        e.preventDefault();        
        saveChanges();
	});
	
	
	$("#btn-delete").on('click', function (e) {
        e.preventDefault();        
        deleteItem();
	});
					
});

function searchByParams(filepathParam, filenameParam, classificationParam, tagsParam, typeParam) {
	let promise = service.searchByParams(filepathParam, filenameParam, classificationParam, tagsParam, typeParam);
	promise
		.then( (data) => {
			if (data.length == 0) { 
					showInfoMessage("Search returned no results.");
				} else {
					showSearchResultTable(data);
				}			
			} 
		)
		.catch( (error) => { 			
			showErrorMessage("Fail to search files with given parameters.")
			}
		);
};

function showSearchResultTable(data) {
	divSearchResult.show();	
	let tbodyResult = tableResult.find("tbody");		
	let itemCount = data.length;
	
	for (i=0; i < itemCount; i++) {
		var item = JSON.parse(data[i]);
		var newRow = createNewRow(i, item.id, item.filename, item.filePath, item.type);
		tbodyResult.append(newRow);
	}
	
	tableResult.find(".view-link").on('click', function (e) {
        e.preventDefault();
        var itemId = $(this).closest("tr").attr("scope");
        showItemModal(itemId);        
	});
}

function createNewRow(rownum, id, fileName, filePath, fileType) {
	let rowTr = $("<tr>").attr("scope",id);
	
	let thNum = $("<th>").attr("scope","row").text(rownum+1);
	let filenameTd = $("<td>").text(fileName);
	let filepathTd = $("<td>").text(filePath);
	let filetypeTd = $("<td>").text(fileType);
	
	let aViewEl = $("<a>").attr("href","#").addClass("view-link").attr("data-toggle","modal").attr("data-target",".item-view-modal").text(" View ");
	
	let actionTd = $("<td>").addClass(" last");
	
	actionTd.append(aViewEl);
	rowTr.append(thNum);
	rowTr.append(filenameTd);
	rowTr.append(filepathTd);
	rowTr.append(filetypeTd);
	rowTr.append(actionTd);
	
	return rowTr;	
}

function showItemModal(itemId) {
	let divModal = $("#item-modal-view");

	let promise = service.getMediaItem(itemId);
	promise
		.then( (data) => {
			var item = data;   //var item = JSON.parse(data);
			
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
				
			$("#view-id").val(item.id);
			$("#view-hash").val(item.hash);
			$("#view-file-path").val(item.filePath);
			$("#view-file-name").val(item.filename);
			$("#view-media-type").val(item.type);
			$("#view-classification").val(item.classification);
			$("#view-media-description").val(item.description);
			$("#view-media-comments").val(item.additionalInformation);
			$("#tags_1").val(item.tags);			
		})
		.catch( (error) => { 			
			showErrorMessage("Fail to connect to database...");
			}
		);	
}


function saveChanges() {
	let editedItem = {
			id: $("#view-id").val(),
			hash: $("#view-hash").val(),
			filePath: $("#view-file-path").val(),
			filename: $("#view-file-name").val(),
			type: $("#view-media-type").val(),
			classification: $("#view-classification").val(),
			description: $("#view-media-description").val(),
			additionalInformation: $("#view-media-comments").val(),
			tags: $("#tags_1").val()
	};
	
	if (editedItem.type == "AUDIO" || editedItem.type == "TEXT" || editedItem.type == "VIDEO") {
		editedItem.title = $("#view-title").val();		
	}
	if (editedItem.type == "AUDIO" || editedItem.type == "TEXT") {
		editedItem.author = $("#view-author").val();
	}

	let promise = service.updateMediaItem(editedItem);
	promise
		.then( (data) => {
			showSuccessMessage("File updated: " + data.filePath);	    	  
			$("#item-modal-view").modal('toggle');			
			} 
		)
		.catch( (error) => { 			
			showErrorMessage("Fail to save this file: " + error);
			}
		);	
}

function deleteItem() {	
	let itemId = $("#view-id").val();
	let filename = $("#view-file-name").val();
	
	if ( confirm("Are you sure to delete item '" + filename + "' ? ")) {
		let promise = service.deleteMediaItem(itemId);
		promise
			.then( (data) => {
				showSuccessMessage("File deleted: " + filename);	    	  
				$("#item-modal-view").modal('toggle'); // close modal
				tableResult.find("tbody").find("tr").each(function() {
					if ( $(this).attr("scope") == itemId ) {
						$(this).remove(); // delete row from table
					}
				});			
			})
			.catch( (error) => { 			
				showErrorMessage("Fail to delete this file: " + filename);
				}
			);			
	}	
	
}
