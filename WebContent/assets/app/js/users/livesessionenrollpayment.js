$(document).ready(function(){
	if($("[name=status]").val()=="sucess"){
		$(".successmsg").text("Payment Enrolled");
		$(".successmsgtxt").text("Successfully");
	}
	else if($("[name=status]").val()=="failed"){
		$(".successmsg").text("Payment Enrolled");
		$(".successmsgtxt").text("Failed");
	}
	$(".enrolllivechart").click(function(){
		var urls = $('[name=remediliveclassroomurl]').val();
		var url = urls+"?orgpersonid="+$('[name=orgpersonid]').val()+"&liveentrolmentid="+$('[name=livesessionenrollmentid]').val();
		$(this).attr({href:url,target:"_blank"});
		/*$('#livesession').attr('action','livesession');
		$('#livesession').submit();*/	
	});
});