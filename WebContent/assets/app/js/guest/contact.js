$(document).ready(function(){	
	$('.sendingmess').hide();
	$("#btncontactus").click(function(){
		contact.helper.signupform();
	});		
	
});
var contact={		
		
};
contact.helper={				
		signupform:function(){	
			self.validation();			
			 $('#mailsuccess').hide();
			 $('#mailfailure').hide();
			 if($.trim($('[name=name]').val()=="") && $.trim($('[name=email]').val()=="")&& $.trim($('[name=organizationname]').val()=="")&& $.trim($('[name=address]').val()=="")&& $.trim($('[name=message]').val()==""))
			 {			 
				if($.trim($('[name=name]').val())==""){				
				$('#nameerror').css('display','block').text("Please enter the Firstname.");	
			}
			else if($.trim($('[name=email]').val())==""){
				$('#nameerror').hide();
				$('#emailerror').css('display','block').text("Please enter the Email Address");
			}
			else if(!self.IsEmail($('#email').val())){
				$('#emailerror').show();
				$('#emailerror').text('Please Enter valid email address');
			}
			else if($.trim($('[name=organizationname]').val())==""){
				$('#emailerror').hide();
				$('#organizationerror').css('display','block').text("Please enter the Organization Name.");
			}			
			else if($.trim($('[name=subject]').val())==""){
				$('#organizationerror').hide();
				$('#subjecterror').css('display','block').text("Please enter the Subject");			
			}
			else if($.trim($('[name=address]').val())==""){
				$('#subjecterror').hide();
				$('#addresserror').css('display','block').text("Please enter the Address");
			}
			else if($.trim($('[name=message]').val())==""){
				$('#addresserror').hide();
				$('#messageerror').css('display','block').text("Please enter the Message");				
			}		
			else{
				$('#nameerror').hide();
				$('#emailerror').hide();
				$('#organizationerror').hide();
				$('#subjecterror').hide();
				$('#messageerror').hide();
				$('#addresserror').hide();					
				contact.helper.mailsending();	
				}
			 }
		},
 mailsending:function(){	
	
		obj={												
			name				:$("#name").val(),
		    organizationname	:$("#organizationname").val(),
			email				:$("#email").val(),
			subject				:$("#subject").val(),
		    address				:$("#address").val(),
		    message				:$("#message").val(),	
		  };
	contact.helper.asyncajax(courseData.getContextPath()+"/rest/contactmail",obj);
 },   
 asyncajax: function(url,data){
		var self=this;
		if(_.isString(url))	{
			if(_.isObject(data) && !_.isArray(data)){
				var output = "";
				$.ajax({
					type : 'POST',
					url : url,
					dataType: 'json',
					data:JSON.stringify(data),					
					contentType:'application/json',
					beforeSend:self.startProgress(),							
					success : function(data, textStatus, jqXHR){
						output = data;
						if(output=="OK"){
							self.endProgress(output);
						}
						else {						
							self.errormsg(output);			
						}
						},
					error : function(jqXHR, textStatus, errorThrown){
						self.errormsg();
						console.log("Couldn't connect to server ");
					},
				});	
			}
			else{
				console.log("data is not an object"+data);	
			}
		}
		else{
			self.endProgress("Server not connected.Please contact adminstrator");
			console.log("url is not a String"+url);	
		}	
	},
 
startProgress:function(){
	$('.sendingmess').show();	
	$('#btncontactus').hide();
},
endProgress:function(message){
		$('.sendingmess').hide();
		$('.mailsuccess').show();
		$('.mailsuccess').text("Successfuly Mail Sent");
		$('.sendingmess').hide();
		 setTimeout(function(){
			  $(".mailsuccess").fadeOut("slow", function () {
			  $(".mailsuccess").remove();
			      });
			}, 2500);

		 $(".mailsuccess").delay(1000).fadeIn;
		$('.contact-form')[0].reset();	
		$('#btncontactus').show();
	},
		
errormsg:function(){
	$('.sendingmess').hide();	
	$('.mailsuccess').hide();
	$('.mailfailure').show();
	$('.mailfailure').text("Failed to sending Mail");
	setTimeout(function(){
		  $(".mailsuccess").fadeOut("slow", function () {
		  $(".mailsuccess").remove();
		      });
		}, 2500);

	 $(".mailfailure").delay(1000).fadeIn;
	},
};

function IsEmail(email) {
	var regex = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,4}$/;
	return regex.test(email);
 };
 
 function validation(){	
		$('#name').on("focusout keyup",function(){	
			$('#nameerror').hide();				
		});
		$( "#email").on("focusout keyup",function( event ) {			
			$('#emailerror').hide();				
		});
		$( "#organization").on("focusout keyup",function( event ) {			
			$('#organizationerror').hide();				
		});
		$( "#subject").on("focusout keyup",function( event ) {			
			$('#subjecterror').hide();				
		});
		$( "#address").on("focusout keyup",function( event ) {			
			$('#addresserror').hide();				
		});
		$( "#message").on("focusout keyup",function( event ) {			
			$('#messageerror').hide();				
		});	
		$('.trimvalue').blur(function() {	
		     var value = $.trim( $(this).val() );
		     $(this).val( value );
		   });		 
		};


  
		
