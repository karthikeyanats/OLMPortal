$(document)
.ready(
		function() {
			indexPage.loadPopularCourses();
			var masterorgidobj=courseData.getMasterAdminId();
			var masterorgid=masterorgidobj[0].organizationid;
			if(masterorgid!=null){
				$('[name=organizationid]').val(masterorgid);	
			}			
			$('#singlebutton').click(function(){
				$('#alertBox').hide();  
				if($("#firstname").val()==''||  $("#usernameCheckId").val()=="" || $("#password").val()==""){
					
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
					$('#checkmsg').text("The username has already been used.");					
				}else{
					$('#alertBox').removeClass("signupusers");
				}
			});
			$('.userlogin').click(function(){
				$('[name=userlogin]').attr('action','withoutCourseSignUp');
				$('[name=userlogin]').submit();
			});					
			$(".btnsign").click(function(){
				$("#firstname").val(""); 
				$("#usernameCheckId").val(""); 
				$("#password").val("");
			});
			$('.newpassword').click(function() {				
				$('.newpassword').hide();
				$('.loadingicon').css('display','block');
				var username = $('#usernameId').val();
				var passwordidobj = {};
				passwordidobj.username = username;
				passwordidobj.organizationid=masterorgid;
				var string = courseData.forgotpassword(passwordidobj);
				
				if (string !== "valid") {
					$('.alert').removeClass('alert-success');
					$('.alert').addClass('alert-danger');											
					$('#msg').html("The username is invalid.");
					$('.forgotalert').show();	
					$('.newpassword').show();
					$('.loadingicon').css('display','none');
				} else {
					$('.loadingicon').show();
					$('.alert').removeClass('alert-danger');
					$('.alert').addClass('alert-success');
					$('#msg').html("The password has been sent to your email successfully");
					$('#usernameId').val("");
					$('.forgotalert').show();
					$('.newpassword').show();
					$('.loadingicon').css('display','none');
				}
			});
			$('.forgotpasslink').click(function(){
				$('.forgotalert').hide();
				$('#usernameId').val("");
			});				
			//addTextPatternValidations(["password","firstname"], 3, 45);
			//addEmailValidations([ "usernameCheckId" ], 3, 45); 
	});

function userNameCheck()
{
	usernameobj={};
	usernameobj.organizationid=$('[name=organizationid]').val();
	usernameobj.username=$('#usernameCheckId').val();
	var useravailability=courseData.getUserNameAvailability(usernameobj);
	return useravailability;
}
indexPage={
		loadPopularCourses:function(){
			var self=this;
			var popularCourses=courseData.getPopularCourses();
			self.renderPopularCourses(popularCourses);
		},
		renderPopularCourses:function(popularCourses){
			var self=this;
			renderTemplate("#topcoursestmpl", popularCourses, "#topcoursestable");		
			$('.popcourselink').click(function(){
				var courseid=$(this).attr('courseid');
				var count=$(this).attr('count');
				self.previewCourseData(courseid,count);
			});
			$('.crsprice').each(function(){
				var price=$(this).attr('price');
				var priceicon = $(this).attr('priceicon');
				if(price=="Free"){
					$(this).html("Free");
				}
				else if(price==""){
					$(this).html("");
				}
				else{
						$(this).html("<i class='fa "+priceicon+"'></i>"+price);	
				}
			});
		},
		previewCourseData:function(courseid,count){
			$('[name=courseid]').val(courseid);
			$('[name=count]').val(count);
			$('[name=popcourseform]').attr('action','preview');
			$('[name=popcourseform]').submit();
		},	
        organizationname:function(){
	     var organizationid=$('[name=organizationid]').val();
	     var org={};
	     org.organizationid=organizationid;
		 var orglist = ajaxhelperwithdata("loadOrganization",org);
	     if(orglist.length>0){
		    $('#orgname').html(orglist[0].orgname);
	    }
	}
};