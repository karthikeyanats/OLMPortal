$(document).ready(function() {
	organization.organizationname();
	organization.getUserAvailablity();
});
organization={
		getUserAvailablity:function(){
			var self=this;
			self.getUserAvailablityData($('#emaidval').text());
		},
		getUserAvailablityData:function(emailaddress){
			var self=this;
			var emailobj={};
			emailobj.emailaddress=emailaddress;
			self.CheckUserAvailablity(emailobj);
		},
		CheckUserAvailablity:function(emailobj){
			var self=this;
			var availablitystatus=courseData.checkinvitees(emailobj);
			if(availablitystatus==="available"){
				renderTemplate("#existingusertmpl", null, "#logintable");	
				self.exUserSignin();
			}
			else if(availablitystatus==="not available"){
				renderTemplate("#newusertmpl", null, "#logintable");
				self.organizationsignup();
			}
		},
		organizationname:function(){
			var organizationid=$('[name=organizationid]').val();
			var org={};
			org.organizationid=organizationid;
			var orglist = ajaxhelperwithdata("loadOrganization",org);
			renderTemplate("#logintmpl", orglist, "#userlogin");	
		},
		exUserSignin:function(){
			$('.exuserlogin').click(function(){
				if($('.expass').val().trim().length!=0){
					var usercourseid=$('.usercourseid').val(); 
					if(usercourseid!='' && usercourseid!="null"){
						$('[name=targetUrl]').attr('value','/app/previewuser'); 
						$('[name=userlogin]').attr('action','invitesubscribe');
						$('[name=userlogin]').submit(); 
					}else{
						$('[name=userlogin]').attr('action','invitesubscribe');
						$('[name=userlogin]').submit();
					}	
				}else{
					alert("Please enter password");
				}
			});	    	
		},
		organizationsignup:function(){
			var self=this;
			$('.userlogin').click(function(){
				var obj={};
				obj.firstname=$('[name=firstname]').val();
				obj.username=$('[name=username]').val();
				obj.password=$('[name=password]').val();
				obj.confirmpassword=$('[name=confirmpassword]').val();	
				obj.newusercourseid=$('.newusercourseid').val();
				self.organizationvalid(obj);			
			});	
		},
		organizationvalid:function(obj){
			var self=this;
			if(obj.firstname.length!=0 && obj.username.length!=0 && obj.password.length!=0 && obj.confirmpassword.length!=0){
				self.validpassword(obj);				
			}else{
				obj.message="Please fill the required fields";
				renderTemplate("#validationtmpl", obj, "#errormessage");	
			}	
		},
		validpassword:function(obj){
			if(obj.password==obj.confirmpassword){
				if(obj.newusercourseid!='' && obj.newusercourseid!="null"){					
					$('[name=targetUrl]').attr('value','/app/previewuser'); 
					$('[name=userlogin]').attr('action','invitesubscribe');
					$('[name=userlogin]').submit();
				}else{
					$('[name=userlogin]').attr('action','invitesubscribe');
					$('[name=userlogin]').submit();	
				}							
			}else{						 
				obj.message="Password does not match";
				renderTemplate("#validationtmpl", obj, "#errormessage");
			}
		}		
};