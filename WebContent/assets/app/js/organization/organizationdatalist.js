$(document).ready(function(){
	orgDataList.getFirstLoadItems();	
	orgDataList.orgProfile();
	orgDataList.orgupload();

	//orgDataList.userCoursesData();
//	orgDataList.orgprofilevalues();
});
$('.orgcourse').click(function(){
	var self=this;
	var usercourses=courseData.getOrgCourses();
	orgDataList.renderUserCourses(usercourses);
});
$('.paymentanalytics').click(function(){
	payments.analytics().init();	
});
$('.colorpatternlink').click(function(){
	$('.leftsideli').removeClass('active');
	$(this).addClass('active');
	orgcolorpattern().init();
});

orgDataList={		
		getFirstLoadItems:function(){
			var self=this;			
			self.userCourseItems();

		},				
		userCourseItems:function(){
			var self=this;
			$('.courseuserbtn').click(function(){
				self.userCoursesData();				
			});	
		},		

		userCoursesData:function(){
			var self=this;
			var usercourses=courseData.getOrgCourses();
			self.renderUserCourses(usercourses);
		},


		renderUserCourses:function(usercourses){
			var self=this;
			renderTemplate("#coursedetailstmpl",usercourses,"#userdetails");
			$('.coursetitle').click(function(){
				var courseid=$(this).attr('courseid');	
				$('[name=courseid]').val(courseid);
				$('[name=coursedetailsmanage]').attr('action','managecourse');
				$('[name=coursedetailsmanage]').submit();
			});			
		},
		orgProfile:function(){
			var self=this;
			$('.basicinfo').click(function(){
				var userlist =courseData.getParicularOrganizationdetails();
				self.renderBasicinfo(userlist);
			});
		},
		renderBasicinfo:function(userlist){

			var self=this;
			renderTemplate("#basicprofiletempl",userlist,"#userdetails");	
			orgDataList.validation();
			orgDataList.orgnameonly();
			orgDataList.domaincheck();
			$('.changelogobtn').click(function(){
				$('#changelogomodal').modal('show');
			});	

			$('.trimvalue').keydown(function (e) {
				if (e.shiftKey || e.ctrlKey || e.altKey) {
					e.preventDefault();
				} else {
					var key = e.keyCode;
					if (!((key == 8) || (key == 46) || (key >= 35 && key <= 40) || (key >= 48 && key <= 57) || (key >= 96 && key <= 105))) {
						e.preventDefault();
					}
				}
			});
			$('.orgsizename').keydown(function (e) {
				if (e.shiftKey || e.ctrlKey || e.altKey) {
					e.preventDefault();
				} else {
					var key = e.keyCode;
					if (!((key == 8) || (key == 46) || (key >= 35 && key <= 40) || (key >= 48 && key <= 57) || (key >= 96 && key <= 105))) {
						e.preventDefault();
					}
				}
			});	
			$('.saveprofilebtn').click(function() {	

				if($.trim($('.organizationname').val()=="") && $.trim($('.orgemailaddress').val()=="")&& $.trim($('.domainurl').val()=="") && $.trim($('.orgphonenumber').val()=="") && $.trim($('.orgsizename').val()=="") && $.trim($('.orgsizename').val()=="0") && $.trim($('.orgurl').val()=="")){
					if($.trim($('.organizationname').val())==""){
						$('.organizationnamealert').show();
						$('.organizationname').focus();
					}
					else if(!self.txtonly()){
						$('.organizationnamealert').show();
						$('.organizationname').focus();
					}			
					else if(!self.domaincheck()){
						$('.domainurlalert').show();
						$('.msginfo').text("Invalid URL address.");										 
					}
					else if($.trim($('.orgemailaddress').val())==""){
						$('.orgemailaddressalert').show();
						$('.orgemailaddress').focus();
					}
					else if(!self.ValidateEmail($('.orgemailaddress').val())){
						$('#messageinfomodal').modal('show');
//						$('.msginfo').text("Invalid email address.");
						$('.orgemailaddressalert').show();
					}

					else if($.trim($('.orgphonenumber').val())==""){
						$('.orgphonenumberalert').show();
						$('.orgphonenumber').focus();
					}
					else if(!self.contact()){
						$('.orgphonenumberalert').show();
						$('.orgphonenumber').focus();
					}
					else if(!self.hasMinLength()){
						$('.minerror').show();
						$('.orgphonenumber').focus();
					}
					else if($.trim($('.orgsizename').val())==""){
						$('.orgsizenamealert').show();
						$('.orgsizename').focus();
					}
					else if($.trim($('.orgsizename').val())== 0){
						$('.orgsizenamealert').show();
						$('.msginfo').text("Invalid size.");
					}
					else if(!self.durlcheck()){
						$('.orgurlalert').show();
						$('.msginfo').text("Invalid organization URL.");	
					}

					else{
						var org={};
						org.organizationname=$('.organizationname').val().trim();
						org.orglogo=$('.orglogo').val();
						org.organizationurl=$('.orgurl').val().trim();
						org.email=$('.orgemailaddress').val().trim();
						org.contactno=$('.orgphonenumber').val();
						org.sizeoforg=$('.orgsizename').val().trim();
						org.domainurl=$('.domainurl').val().trim();
						org.organizationid=$('.saveprofilebtn').attr('organizationid');
						org.phoneid=$('.saveprofilebtn').attr('phoneid');
						org.emailid=$('.saveprofilebtn').attr('emailid');
						org.contactinfoid=$('.saveprofilebtn').attr('contactinfoid');
						org.urlid=$('.saveprofilebtn').attr('urlid');					

						var orglist=courseData.getOrganizationdetails(org);	
						//hide the validation alert
						$('.organizationnamealert').hide();
						$('.domainurlalert').hide();
						$('.orgemailaddressalert').hide();
						$('.orgphonenumberalert').hide();
						$('.orgsizenamealert').hide();
						$('.orgurlalert').hide();

						$('#messageinfomodal').modal('show');
						$('.msginfo').text("Basic Information has been updated");
						/*//hide the validation alert
				         $('.organizationnamealert').hide();
				         $('.domainurlalert').hide();
				         $('.orgemailaddressalert').hide();
				         $('.orgphonenumberalert').hide();
				         $('.orgsizenamealert').hide();
				         $('.orgurlalert').hide();*/
						$('.basicinfo').trigger('click');
					}

				}else{	

				}

			});			
		},

		orgprofilevalues:function(){
			var org={};
			org.organizationname=$('.organizationname').val().trim();
			org.orglogo=$('.orglogo').val();
			org.organizationurl=$('.orgurl').val().trim();
			org.email=$('.orgemailaddress').val().trim;
			org.contactno=$('.orgphonenumber').val().trim();
			org.sizeoforg=$('.orgsizename').val().trim();
			org.domainurl=$('.domainurl').val().trim();
			org.organizationid=$('.saveprofilebtn').attr('organizationid');
			org.phoneid=$('.saveprofilebtn').attr('phoneid');
			org.emailid=$('.saveprofilebtn').attr('emailid');
			org.contactinfoid=$('.saveprofilebtn').attr('contactinfoid');
			org.urlid=$('.saveprofilebtn').attr('urlid');

			var orglist=courseData.getOrganizationdetails(org);
			$('#messageinfomodal').modal('show');
			$('.msginfo').text("Basic Information has been updated");
		},
		orgupload:function(){
			$('#fileUpload').change(function(){
				var ext = $('#fileUpload').val().split('.').pop().toLowerCase();
				if($.inArray(ext, ['png','jpg','jpeg']) == -1) {				     
					$('.imagealert').show();
					$('.changelogosavebtn').hide();	
					$('.imagesizealert').hide();
				}
				else{
					var fileBool = checkfilesize("#fileUpload", "lo");
					if(fileBool==="no"){
						$('.imagesizealert').show();						
						$('.changelogosavebtn').hide();
						$('.imagealert').hide();
					}
					else{
					$('.imagesizealert').hide();
					$('.imagealert').hide();
					$('.changelogosavebtn').show();
					}
					$('.changelogosavebtn').click(function(){
						var organizationid=$('.saveprofilebtn').attr('organizationid');
							if(organizationid !=0){
								$('.organid').val(parseInt(organizationid));
								$('[name=logoform]').attr('action','orgupload');	
								$('[name=logoform]').submit();
							} 
						else{
							
						}

					});
				}
			});

			var userlist =courseData.getParicularOrganizationdetails();
			orgDataList.renderBasicinfo(userlist);
		},
			ValidateEmail:function(email) {
			 var expr = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
			 return expr.test(email);
		 },
		 validation:function(){

			 $( ".organizationname" ).keypress(function( event ) {			
				 $('.organizationnamealert').hide();				
			 });			
			 $('.domainurl').keypress(function(){
				 $('.domainurlalert').hide();
			 });
			 $('.orgemailaddress').keypress(function(){
				 $('.orgemailaddressalert').hide();
			 });
			 $('.orgphonenumber').keypress(function(){
				 $('.orgphonenumberalert').hide();
			 });
			 $('.orgsizename').keypress(function(){
				 $('.orgsizenamealert').hide();
			 });
			 $('.orgurl').keypress(function(){
				 $('.orgurlalert').hide();
			 });

		 },
		 orgnameonly:function(){
			 $('.orgsizename').keyup(function () { 
				 this.value = this.value.replace(/[^a-zA-Z0-9]/g, '');
			 });

		 },		
		 contact:function(){
			 var input=$('.orgphonenumber').val().trim();
			 var txtcheck=/^\d+$/;
			 if(txtcheck.test(input)){
				 return true;
			 }
			 else{ return false;}

		 },
		 hasMinLength:function(){
			 var input=$('.orgphonenumber').val().trim();			
			 if(input.length>=10){
				 return true;
			 }
			 else{ return false;}
		 },

		 txtonly:function(){
			 var input=$('.organizationname').val().trim();
			 var txtcheck=/^[0-9a-zA-Z\- \/_?:.,&\s]+$/;
			 if(txtcheck.test(input)){
				 return true;

			 }
			 else{ return false;}

		 },

		 durlcheck:function(){
			 var durl=$('.orgurl').val().trim();
			 var re=/(http(s)?:\\)?([\w-]+\.)+[\w-]+[.com|.in|.org]+(\[\?%&=]*)?/;
			 if(re.test(durl))
			 {
				 return true;
			 }else{
				 return false;
			 }
		 },
		 domaincheck:function(){
			 var txt=$('.domainurl').val().trim();
//			 var re=/(http(s)?:\\)?([\w-]+\.)+[\w-]+[.com|.in|.org]+(\[\?%&=]*)?/;
			 var re=/^[A-Za-z\s]+$/;
			 if(re.test(txt)){
				 return true;
			 }

			 else{
				 return false;
			 }
		 }
};
