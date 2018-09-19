
$(document).ready(function(){
	addTextPatternValidations(["firstname"], 3, 45);
	//addTextPatternValidations(["pass"], 3, 45);
	addEmailValidations([ "usernameCheckId" ], 3, 45);
	addTextValidations(["pass"], 3, 45);
	var masterorgidobj=courseData.getMasterAdminId();
	var masterorgid=masterorgidobj[0].organizationid;
	if(masterorgid!=null){
		$('[name=organizationid]').val(masterorgid);	
	}			
	$('#singlebutton').click(function(){
		$('#alertBox').hide();  
		if($("#firstname").val()==''||  $("#usernameCheckId").val()=="" || $("#pass").val()==""){
			
		}else{
		   if($('#alertBox').hasClass('signupusers')){	
			  $('#alertBox').hide();  
			  $('#usernameCheckId').val('');
		  }else{
			  $('[name=organizationid]').val(masterorgid);
			  $('#alertBox').hide();  
			  $('[name=withoutCourseSignUpForm]').attr('action','withoutCourseSignUp');
			  $('[name=withoutCourseSignUpForm]').submit();
			  
		}
	  }
	}); 
	
	$("#usernameCheckId").blur(function(){
		 $('#alertBox').hide();  
		 var useravailability = userNameCheck();
		 if(useravailability=="Exits"){ 
			$('#alertBox').show();  
			$('#alertBox').removeClass("alert alert-success").addClass("alert alert-danger signupusers");
			$('#checkmsg').text("The Email has been already used.");					
		}else{
			$('#alertBox').removeClass("signupusers");
		}
	});
});
function userNameCheck()
{
	usernameobj={};
	usernameobj.organizationid=$('[name=organizationid]').val();
	usernameobj.username=$('#usernameCheckId').val();
	var useravailability=courseData.getUserNameAvailability(usernameobj);
	return useravailability;
}


