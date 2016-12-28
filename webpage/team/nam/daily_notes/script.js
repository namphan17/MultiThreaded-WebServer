$(document).ready(function() {
		console.log("html length=" + $("html").height());
		var height = Math.max($(".col-1").height(), $(".col-2").height());
		if ($(".col-2").height() < $("html").height()) {
	        var height = Math.max($(".col-1").height(), $(".col-2").height());
	    	height = $("html").height();
	    } else {
	    	height = $(".col-2").height() + 200;
	    }
	    	console.log(height);
	        $(".col-1").height(height);
	        $(".col-2").height(height);
	        $(".col-3").height(height);
    	
    });