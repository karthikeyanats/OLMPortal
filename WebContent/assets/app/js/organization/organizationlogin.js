$(document).ready(function(){   
	$('[name=organizationform]').submit(function(e){		
		$('input.organizationurl').blur(function() {
	         var value = $.trim( $(this).val() );
	         $(this).val( value );
	        });		
		var dataurl = $('.organizationurl').val().trim();
		var data=$.trim(dataurl);
		var data1=".remedicloud.com";
		var domaindata=data+data1;
		var organizationname = $('[name=organizationname]').val().trim();
		var domainurl = $('[name=domainurl]').val().trim();
		var contactno = $('[name=contactno]').val().trim();
		var sizeoforg = $('[name=sizeoforg]').val().trim();
		var orgfirstname = $('[name=orgfirstname]').val().trim();
		var orgpassword = $('[name=orgpassword]').val().trim();
	 	organLogin.orgNameDuplicateCheck(e);	 	
		
		$('[name=organizationname]').val(organizationname);
		$('[name=domainurl]').val(domainurl);
		$('[name=contactno]').val(contactno);
		$('[name=sizeoforg]').val(sizeoforg);
		$('[name=orgfirstname]').val(orgfirstname);		
		$('[name=organizationurl]').val(domaindata);
	
	$('[name=organizationname]').keyup(function(e){
		var txt = ($('[name=organizationname]').val());
		   if(txt.charAt(0)!=' '){
			   $('.orgname.error').text("");
			      return true;	   
		   }
		   else{
			   $('.orgname.error').text("You can't leave this empty.");
			   return false;
		       }	
		
		$('input.trimvalue').blur(function() {
	         var value = $.trim( $(this).val() );
	         $(this).val( value );
	        });
		
		
		$('.orgname.error').css('display','none');	
		var obj={};
		orgnameobj={};
		orgnameobj.organizationname=$('[name=organizationname]').val().trim();
		var checkorgname= courseData.getOrgNameAvalibility(orgnameobj);
		if(checkorgname=="nameexists"){
			organLogin.orgNameValidation(e,obj);				
		}		 
		});	
	$('[name=url]').keyup(function(e){
		var txt = ($('[name=url]').val());
		   if(txt.charAt(0)!=' '){
			   $('.orgurl.error').text(""); 
			      return true;			     
		   }
		   else{
			 $('.orgurl.error').text("You can't leave this empty.");
			   return false;
			   $('.orgurl.error').css('display','none'); 
		       }
	 	});
	$('[name=domainurl]').keyup(function(e){
		var txt = ($('[name=domainurl]').val());
		   if(txt.charAt(0)!=' '){
			   $('#alertdomain.error').text(""); 
			      return true;			     
		   }
		   else{
			 $('#alertdomain.error').text("You can't leave this empty.");
			   return false;
		       }
	 	});
	$('[name=contactno]').keyup(function(e){
		var txt = ($('[name=contactno]').val());
		   if(txt.charAt(0)!=' '){
			   $('.orgcontact.error').text("");   
			      return true;			     
		   }
		   else{
			   
			   $('.orgcontact.error').text("You can't leave this empty.");
			   return false;
		       }
		   
	 	});
	$('[name=sizeoforg]').keyup(function(e){
		var txt = ($('[name=sizeoforg]').val());
		   if(txt.charAt(0)!=' '){
			   $('.organizationsize.error').text("");  
			      return true;			     
		   }
		   else{
			    $('.organizationsize.error').text("You can't leave this empty.");
			   return false;
		       }
	 	});
	$('[name=orgfirstname]').keyup(function(e){
		var txt = ($('[name=orgfirstname]').val());
		   if(txt.charAt(0)!=' '){
			   $('#orgfirstname.error').text("");
			      return true;			     
		   }
		   else{
			    $('#orgfirstname.error').text("You can't leave this empty.");
			   return false;
		       }
	 	});
	 $('[name=contactno]').keydown(function (e) {
			if (e.shiftKey || e.ctrlKey || e.altKey) {
			e.preventDefault();
			} else {
			var key = e.keyCode;
			if (!((key == 8) || (key == 46) || (key >= 35 && key <= 40) || (key >= 48 && key <= 57) || (key >= 96 && key <= 105))) {
			e.preventDefault();
			}
			}
			});
	    
	    $('[name=sizeoforg]').keydown(function (e) {
			if (e.shiftKey || e.ctrlKey || e.altKey) {
			e.preventDefault();
			} else {
			var key = e.keyCode;
			if (!((key == 8) || (key == 46) || (key >= 35 && key <= 40) || (key >= 48 && key <= 57) || (key >= 96 && key <= 105))) {
			e.preventDefault();
			}
			}
			});	
	$('[name=email]').change(function(e){			
	var obj={};
	orgnameobj={};
	orgnameobj.email=$('[name=email]').val().trim();
	var checkemail=courseData.getEmailAvalibility(orgnameobj);
	if(checkemail=="emailexists"){
		organLogin.orgEmailValidation(e,obj);
	}
	    
	});
	});	
    var status=["one","two","three","four","five"];
	var index=0;
	var caps=false;
	var small=false;
	var number=false;
	var spl=false;
	var leng=false;
	$('.newpwdval').on("keyup focusout",function(){
		if($(this).val()!=""){
		$('.progcntrl').show();
		}
		else{
		$('.progcntrl').hide();	
		}
		var Caps=/[A-Z]+/; 
		var NonCaps= /[a-z]+/;
		var numbers=/[0-9]+/;
		var specialcharacters=/[#?!@$%^&*-._()+=]+/;
		var maximumlength=8;
		var password=$('.newpwdval').val();
		if(Caps.test(password)&&!caps){
			strengthPass(status[index]);
			index++;
			caps=true;
		}
		if(NonCaps.test(password)&&!small){
			strengthPass(status[index]);
			index++;	
			small=true;
		}
		if(numbers.test(password)&&!number){
			strengthPass(status[index]);
			index++;	
			number=true;
		}
		if(specialcharacters.test(password)&&!spl){
			strengthPass(status[index]);
			index++;
			spl=true;
		}
		if(password.length>=maximumlength&&!leng){
			strengthPass(status[index]);
			index++;
			leng=true;
		}

		if(!Caps.test(password)&&caps){
			index--;
			deductstrength(status[index]);
			caps=false;
		}
		if(!NonCaps.test(password)&&small){
			index--;
			deductstrength(status[index]);	
			small=false;
		}
		if(!numbers.test(password)&&number){
			index--;
			deductstrength(status[index]);	
			number=false;
		}
		if(!specialcharacters.test(password)&&spl){
			index--;
			deductstrength(status[index]);
			spl=false;
		}
		if(password.length<maximumlength&&leng){
			index--;
			deductstrength(status[index]);
			leng=false;	
		}
	});	
	$('.showpass').mousedown(function(){
		$('.newpwdval').attr('type','text');
	});
	$('.showpass').on('mouseup mousemove',function(){
		$('.newpwdval').attr('type','password');
	});
	 	
	function strengthPass(status){
		if($('.one').attr('style').indexOf('width')<0&&status=="one"){
			$('#strengthmsg').html('weak!');
			$('.one').attr("style","width:20%;background-color: #dd514c!important;");
		}
		else if($('.two').attr('style').indexOf('width')<0&&status=="two"){
			$('#strengthmsg').html('weak!');
			$('.two').attr("style","width:20%;background-color: #dd514c!important;");
		}
		else if($('.three').attr('style').indexOf('width')<0&&status=="three"){
			$('#strengthmsg').html('moderate!');
			$('.one').attr("style","width:20%;background-color: #faa732 !important;");
			$('.two').attr("style","width:20%;background-color: #faa732!important;");
			$('.three').attr("style","width:20%;background-color: #faa732 !important;");
		}
		else if($('.four').attr('style').indexOf('width')<0&&status=="four"){
			$('#strengthmsg').html('strong!');
			$('.one').attr("style","width:20%;background-color:#5eb95e!important;");
			$('.two').attr("style","width:20%;background-color: #5eb95e!important;");
			$('.three').attr("style","width:20%;background-color: #5eb95e!important;");
			$('.four').attr("style","width:20%;background-color: #5eb95e!important;");
		}
		else if($('.five').attr('style').indexOf('width')<0&&status=="five"){
			$('#strengthmsg').html('excellent!');
			$('.five').attr("style","width:20%;background-color: #5eb95e!important;");
		}		
	}
	function deductstrength(status){
		if($('.one').attr('style').indexOf('width')>=0&&status=="one"){
			$('#strengthmsg').html('');
			$('.one').attr("style","background-color: #dd514c!important;");
		}
		else if($('.two').attr('style').indexOf('width')>=0&&status=="two"){
			$('#strengthmsg').html('weak!');
			$('.one').attr("style","width:20%;background-color: #dd514c!important;");
			$('.two').attr("style","background-color: #dd514c!important;");
		}
		else if($('.three').attr('style').indexOf('width')>=0&&status=="three"){
			$('#strengthmsg').html('weak!');
			$('.one').attr("style","width:20%;background-color: #dd514c!important;");
			$('.two').attr("style","width:20%;background-color: #dd514c!important;");
			$('.three').attr("style","background-color: #faa732 !important;");
		}
		else if($('.four').attr('style').indexOf('width')>=0&&status=="four"){
			$('#strengthmsg').html('moderate!');
			$('.one').attr("style","width:20%;background-color:#faa732!important;");
			$('.two').attr("style","width:20%;background-color: #faa732!important;");
			$('.three').attr("style","width:20%;background-color: #faa732!important;");
			$('.four').attr("style","background-color: #5eb95e!important;");
		}
		else if($('.five').attr('style').indexOf('width')>=0&&status=="five"){
			$('#strengthmsg').html('strong!');
			$('.five').attr("style","background-color: #5eb95e!important;");
		}		
	}
});

organLogin={			
		orgNameDuplicateCheck:function(e){	
			var self=this;	
			var obj={};
			orgnameobj={};
			orgnameobj.organizationname=$('[name=organizationname]').val().trim();
			orgnameobj.email=$('[name=email]').val().trim();
			var checkorgname= courseData.getOrgNameAvalibility(orgnameobj);
			var checkemail=courseData.getEmailAvalibility(orgnameobj);
			if(checkorgname=="nameexists"){
				self.orgNameValidation(e,obj);				
			}else if(checkemail=="emailexists"){
				self.orgEmailValidation(e,obj);				
			}
			else{
				self.orgCreate(e,obj);			
			}			
		},			
				
		orgNameValidation:function(e,obj){			
			obj.message="organizationname has already been used.";
			renderTemplate("#errortmpl", obj, "#alertorgname");
			$('.orgname.error').css('display','block');
			e.preventDefault(e);			
		},
		orgEmailValidation:function(e,obj){
			$('.email.error').css('display','none');
			var email=$('[name=email]').val();
			if(email==""){
				$('.email.error').css('display','block');
				}
			else{				
			obj.message="email has already been used.";
			renderTemplate("#errortmpl", obj, "#alertemail");
			$('.email.error').css('display','block');
			e.preventDefault(e); 	

		}
		},
		orgCreate:function(e,obj){	
			var self=this;
			var d1=$('[name=fileUpload]').val();
			if(d1.length!=0){
				self.orgProcess(d1,e,obj);
			}else{
				self.orgfileselect(e,obj);
		    }		
		},		
		orgProcess:function(d1,e,obj){
			var self=this;
			var d2=d1.split('.');
			var d3=d2[1].toLowerCase();
			if(d3.length==3|| d3.length==4){
				self.orgInsert(d3,e,obj);
			}else{
				self.orgFilevalid(e,obj);
			}
		},		
		orgInsert:function(d3,e,obj){	
			$('.file.error').hide(); 
			$('.imagesizealert').hide();
			 if(d3=="jpg"||d3=="png"||d3=="jpeg"){
				 var fileBool = checkfilesize("#fileUpload", "lo");
					if(fileBool==="ok"){						
 				 if($('#organizationinfo').valid() == true){
						$('.imagesizealert').hide();
					 if(confirm("Are you sure you want to create this organization")){						
						 $('[name=organizationform]').attr('action','organizationinfo');
					   }
					}
					else{
						
					}
				} else {
					$('.imagesizealert').show();
					$('[name=fileUpload]').val("");	
 				}
			 }else{
				obj.message="Invalid Image";
				$('[name=fileUpload]').val("");
				renderTemplate("#errortmpl", obj, "#alertLogo");
				$('.file.error').css('display','block');
				e.preventDefault(e);					
			}
		},
		orgFilevalid:function(e,obj){
			obj.message="data format is wrong";
			renderTemplate("#errortmpl", obj, "#alertLogo");	
			$('.file.error').css('display','block');
			e.preventDefault(e);
		},
		orgfileselect:function(e,obj){
			obj.message="please select the file";
			renderTemplate("#errortmpl", obj, "#alertLogo");
			$('.file.error').css('display','block');
			e.preventDefault(e);
		}
		
		
};




