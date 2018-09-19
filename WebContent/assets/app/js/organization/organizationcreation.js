$(document).ready(function(){
	
	
	$('#tabid2').click(function () {
		console.log('Hello');
	});
	
	$.ajax({
		type:"GET",
		url:"organizationList",
		async:false,
		success: function(data, textStatus, jqXHR){  
			
			console.log("Success Organizatoin");
			//renderTemplate("#category-list", data, "#categorytemplateid");
		},
		error: function(jqXHR, textStatus, errorThrown)
		{
			console.log("Couldn't connect to server " + textStatus);
		}
	});
	
	
	/*$('#courseCategory').click(function(){
		console.log("its called");
		$.ajax({
			type:"GET",
			url:"coursecategorylist",
			async:false,
			success: function(data, textStatus, jqXHR){  
				renderTemplate("#category-list", data, "#categorytemplateid");
			},
			error: function(jqXHR, textStatus, errorThrown)
			{
				console.log("Couldn't connect to server " + textStatus);
			}
		});
	});*/
	
	
});
