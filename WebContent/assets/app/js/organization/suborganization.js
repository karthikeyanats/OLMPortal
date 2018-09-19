
$(document).ready(function(){   
	subOrgList.validOrganization();	
	subOrgList.allOrganizationList();
});

subOrgList={
			
		validOrganization:function (){
			var self=this;
			$('.orgcreate').click(function(){
				var data = $('.organizationurl').val();
				var data1=".remedicloud.com";
				var domaindata=data+data1;
				$('[name=organizationurl]').val(domaindata);
				self.orgNameDuplicateCheck();			 
		  });
		},
		allOrganizationList:function(){
			var self=this;
			var allorganization=courseData.allOrganization();
			self.renderOrganization(allorganization);
		},
		renderOrganization:function(allorganization){
		  var self=this;
		  renderTemplate("#Orgcategorytmpl",allorganization,"#organcategory");
		  $('#organcategory').change(function(){
			 var orgid=$('option:selected').val();
			 if(orgid!=0){
			 courseData.setOrganization(orgid);
			 }
			 $('[name="organizationid"]').val(orgid);
		  });	
		  $('.orgfacebook').click(function(){
			  var orgname=parseInt($('option:selected').val());	
			  if(orgname==0){
				  var errobj={};
				  self.selectOrganization(errobj);
			  }
			  else{
				  $(this).attr('href',$('#context').text()+'/auth/facebook');
			  }
		  });
		  $('.orggoogle').click(function(){
			  var orgname=parseInt($('option:selected').val());	
			  var errobj={};
              if(orgname==0){
            	  self.selectOrganization(errobj);  
			  }
              else{
            	  $(this).attr('href',$('#context').text()+'/auth/googleplus?scope=openid email');
              }
		  });
		  $('.signin').click(function(){
			  var errobj={};
			  var email=$('#username').val().trim();
			  var username=self.validateEmail(email);
			  var password=$('#password').val();
			  var orgname=parseInt($('option:selected').val());			  
			  if(orgname!=0 && username==true && password.length!=0){
				  self.checkUsers(email,password,errobj);			   
			  }else if(orgname==0 && email.length!=0 && password.length!=0){
				 self.selectOrganization(errobj);
			  }else if(orgname!=0 && username==false && password.length!=0){
				 self.emailValid(errobj);
			  }else{
				  self.subOrganizationValid(errobj);
			  }
		  });
//enter button to submit the form.	  
		  $(window).keydown(function(e) {
			    if (e.keyCode == 13) {
			    	 var errobj={};
					  var email=$('#username').val().trim();
					  var username=self.validateEmail(email);
					  var password=$('#password').val();
					  var orgname=parseInt($('option:selected').val());			  
					  if(orgname!=0 && username==true && password.length!=0){
						  self.checkUsers(email,password,errobj);			   
					  }else if(orgname==0 && email.length!=0 && password.length!=0){
						 self.selectOrganization(errobj);
					  }else if(orgname!=0 && username==false && password.length!=0){
						 self.emailValid(errobj);
					  }else{
						  self.subOrganizationValid(errobj);
					  }
			    }
			});
		},
	    checkUsers:function(email,password,errobj){
	    	suborgobj={};
	    	suborgobj.organizationid=$('[name="organizationid"]').val();
	    	suborgobj.username=email;
	    	suborgobj.password=password;
			var checkorgusers= courseData.getSuborgcheckUsers(suborgobj);
			if(checkorgusers==="success"){
			  $('[name=userlogin]').submit();
			}
			else if(checkorgusers==="deactivated"){
				errobj.message="Your account has been deactivated, please contact the administrator";
				renderTemplate("#validationtmpl",errobj,"#validationmessage");
			}
			else{
				errobj.message="Please check the username and password";
				renderTemplate("#validationtmpl",errobj,"#validationmessage");
			}  	
	    },
	    selectOrganization:function(errobj){
	    	 errobj.message="Please select organization";
			 renderTemplate("#validationtmpl",errobj,"#validationmessage");
	    },
	    subOrganizationValid:function(errobj){
	    	errobj.message="Please fill the required fields";
			  renderTemplate("#validationtmpl",errobj,"#validationmessage"); 
	    },	
	    emailValid:function(errobj){
	    	errobj.message="Please check the username";
			  renderTemplate("#validationtmpl",errobj,"#validationmessage"); 
	    },	
	    validateEmail:function (email) { 
			var emaildata=email.trim();
		    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		    return re.test(emaildata);
		},
		orgNameDuplicateCheck:function(){	
			var self=this;
			orgnameobj={};
			orgnameobj.organizationname=$('[name=organizationname]').val();
			orgnameobj.email=$('[name=email]').val();
			var checkorgname= courseData.getOrgNameAvalibility(orgnameobj);
			if(checkorgname=="nameexists"){
				//self.orgNameValidation();
				alert('name exists');
			}else if(checkorgname=="emailexits"){
				//self.orgEmailValidation();
				alert('emailexits');
			}else if(checkorgname=="nameemailexists"){			
				//self.orgNameEmailValidation();	
				alert('nameemailexists');
			}else{
				self.orgCreate();
			}			
		},	
		/*orgNameValidation:function(){
			$('#alertBoxs').hide();  
			$('#alertOrgNameBoxs').show();   
			$('#alertOrgNameBoxs').removeClass("alert alert-success").addClass("alert alert-danger");
			$('#orgNamemsg').text("The organizationname has already been used.");
		},
		orgEmailValidation:function(){
			$('#alertOrgNameBoxs').hide();
			$('#alertBoxs').show();   
			$('#alertBoxs').removeClass("alert alert-success").addClass("alert alert-danger");
			$('#userNamemsg').text("The email has already been used.");		
		},
		orgNameEmailValidation:function(){
			$('#alertOrgNameBoxs').show();   
			$('#alertOrgNameBoxs').removeClass("alert alert-success").addClass("alert alert-danger");
			$('#orgNamemsg').text("The organizationname has already been used.");
			$('#alertBoxs').show();   
			$('#alertBoxs').removeClass("alert alert-success").addClass("alert alert-danger");
			$('#userNamemsg').text("The email has already been used.");		
		},	*/	
		orgCreate:function(){
			$('#alertOrgNameBoxs').hide();
			$('#alertBoxs').hide();  
			$('[name=organizationform]').attr('action','organizationinfo');				
			$('[name=organizationform]').submit();
		}
};




