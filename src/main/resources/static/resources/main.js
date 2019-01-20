$(document).ready(function() {
    $(".result").hide();
	$("button").click(function() {
		$.ajax({
			type : 'POST',
			url : 'http://localhost:8080/api/shorturls',
			data : JSON.stringify({
				"fullUrl" : $("#urlinput").val()
			}),
			contentType : "application/json; charset=utf-8",
			success : function(data) {
			    $(".result").show();
                $("#shorturl").text(data.shortUrl);
                $("#shorturl").attr("href", data.shortUrl);
			}, error : function(data) {
				$(".result").hide();
				alert("Error: " +JSON.parse(data.responseText).message);
            }
		});
	});
});