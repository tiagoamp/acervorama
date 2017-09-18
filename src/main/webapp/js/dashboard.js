
var audioArr = [];
var videoArr = [];
var imageArr = [];
var textArr = [];

$(document).ready(function () {
	
	 loadCharts();
					
});


function loadCharts() {	
	$.get("http://localhost:8080/acervorama/webapi/media", function( data ) {
		if (data.length > 0) {
			setMediaArrayValues(data);
			setMediaTypesTotal(data);
			setBarChartValues(data.length);
		} 
	})
	.fail( function() { alert("Fail to access files.") } );	
};

function setMediaArrayValues(data) {
	for (i=0; i < data.length; i++) {
		var item = JSON.parse(data[i]);
		if (item.type == "AUDIO") {
			audioArr.push(item);
		} else if (item.type == "VIDEO") {
			videoArr.push(item);			
		} else if (item.type == "IMAGE") {
			imageArr.push(item);
		} else if (item.type == "TEXT") {
			textArr.push(item);
		}		
	}
}

function setMediaTypesTotal( data ) {
	$("#pn-total-media").find(".count").text(data.length);
	$("#pn-total-audio").find(".count").text(audioArr.length);
	$("#pn-total-video").find(".count").text(videoArr.length);
	$("#pn-total-image").find(".count").text(imageArr.length);
	$("#pn-total-text").find(".count").text(textArr.length);	
};

function setBarChartValues( total ) {	
	var audioPerc = Math.round( (audioArr.length * 100) / total ) ;
	$("#progress-audio").find(".progress-bar").css("width", audioPerc + "%" );
	$("#progress-audio").find(".w_20").find("span").text( audioArr.length + " (" + audioPerc + "%)" );
	var videoPerc = Math.round( (videoArr.length * 100) / total ) ;
	$("#progress-video").find(".progress-bar").css("width", videoPerc + "%" );
	$("#progress-video").find(".w_20").find("span").text( videoArr.length + " (" + videoPerc + "%)" );
	var imagePerc = Math.round( (imageArr.length * 100) / total ) ;
	$("#progress-image").find(".progress-bar").css("width", imagePerc + "%" );
	$("#progress-image").find(".w_20").find("span").text( imageArr.length + " (" + imagePerc + "%)" );
	var textPerc = Math.round( (textArr.length * 100) / total ) ;
	$("#progress-text").find(".progress-bar").css("width", textPerc + "%" );
	$("#progress-text").find(".w_20").find("span").text( textArr.length + " (" + textPerc + "%)" );
};
