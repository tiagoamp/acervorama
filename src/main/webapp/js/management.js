var formMediaSearch = $('#form-media-search'); 

$(document).ready(function () {
	
	$("div .ui-pnotify-container").hide();
	
	$("#btn-search").on('click', function (e) {
        e.preventDefault();
        
        var filepathParam = formMediaSearch.find('input[name="search-file-path"]').val();
        var filenameParam = formMediaSearch.find('input[name="search-file-name"]').val();
        var classificationParam = formMediaSearch.find('input[name="search-classification"]').val();
        var tagsParam = formMediaSearch.find('input[name="search-tags"]').val();
        var typeParam = $('input[name="mediatype"]:checked').val();
        
        searchByParams(filepathParam, filenameParam, classificationParam, tagsParam, typeParam);
        
	});
	
	
});

function searchByParams(filepathParam, filenameParam, classificationParam, tagsParam, typeParam) {
	
	console.log("Path = " + filepathParam);    
	console.log("Name = " + filenameParam);
    console.log("Classif = " + classificationParam);
    console.log("Tags = " + tagsParam);
    console.log("Type = " + typeParam);
    
    var input = { type: typeParam, filepath: filepathParam, filename: filenameParam, classification: classificationParam, tags: tagsParam };
	
	$.get("http://localhost:8080/acervorama/webapi/media", input, function( data ) {
		console.log("DAta = " + data);
		if (data.length == 0) { 
			showInfoMessage("Search returned no results.");
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
