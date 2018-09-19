$(document).ready(function(){
	
	var bankinfo=$('.rolefind').attr('bankinfo');
	if(bankinfo=="fill"){
			$('.leftsideli').removeClass('active');
			$('.bank-info-link').addClass('active');
			loadAccountInfotemplate();
			$('.account-info-hidden-number').focus();
	}
	else{
		loadpersonalinfo();	
	}
	$('.dobval').datepicker({
		dateFormat: 'dd/mm/yyyy', 
		endDate:'-1d'
	});
	$('.subscriptedplans').click(function(){
		$('.leftsideli').removeClass('active');
		$(this).addClass('active');
		subscriptionobj.loadsubscriptions();
	});
	$('.personalinfolink').click(function(){
		$('.leftsideli').removeClass('active');
		$(this).addClass('active');
		loadpersonalinfo();
	});
	$('.changepwdlink').click(function(){
		$('.leftsideli').removeClass('active');
		$(this).addClass('active');
		loadpasswordtmpl();
	});
	$('.bank-info-link').click(function(){
		$('.leftsideli').removeClass('active');
		$(this).addClass('active');
		loadAccountInfotemplate();
		$('.account-info-hidden-number').focus();
	});
	
	$('.profinfolink').click(function(){
		$('.leftsideli').removeClass('active');
		$(this).addClass('active');
		loadProfinfo();
	});	
	$('.myloginslink').click(function(){
		$('.leftsideli').removeClass('active');
		$(this).addClass('active');
		myloginaccounts();
	});
	
	});

function myloginaccounts(){
	var mylogindetails=courseData.getMyLoginDetails();
	for(var i=0;i<mylogindetails.length;i++){
		if(mylogindetails[i].providerId=="facebook"){
			mylogindetails[i].icon="fa fa-facebook-square";
			mylogindetails[i].color="#3b5998";
		}
		else if(mylogindetails[i].providerId=="googleplus"){
			mylogindetails[i].icon="fa fa-google-plus-square";
			mylogindetails[i].color="#dd4b39";
		}
	}
	renderTemplate("#myloginsinfotmpl", mylogindetails, "#personalinfotable");
	$('.loginremove').click(function(){
		var providerid=$(this).attr("providerid");
		if(confirm("Are you sure? Once removed you can't login with "+providerid + " again" )){
			 courseData.deleteMyLoginDetails(providerid);
		};
		myloginaccounts();
	});
}
function loadProfinfo(){
	var professionalinfo=courseData.loadIndividualProfessionalPro();
	var professionaldetails={};
	for(var i=0;i<professionalinfo.length;i++){
		professionaldetails.organization=professionalinfo[i].organization;
		professionaldetails.designation=professionalinfo[i].designation;
		professionaldetails.award=professionalinfo[i].award;
		professionaldetails.workexperienceid=professionalinfo[i].workexperienceid;
		professionaldetails.aboutauthor=professionalinfo[i].aboutauthor;
		if(professionalinfo[i].url!=null){
		if(professionalinfo[i].url.search("facebook")==4){
			professionaldetails.facebook=professionalinfo[i].url;
		}
		else if(professionalinfo[i].url.search("twitter")==4){
			professionaldetails.twitter=professionalinfo[i].url;
		}
		else if(professionalinfo[i].url.search("linkedin")==4){
			professionaldetails.linkedin=professionalinfo[i].url;
		}
		else{
			professionaldetails.orgurl=professionalinfo[i].url;	
		}
		}
	}
	renderTemplate("#professionalinfotmpl", professionaldetails, "#personalinfotable"); 
	$('.professinfoerror').on("focusout keyup",function(){
		var specialcharacters=/[#?!@$%^&*_()+=]+/;
		var errorid="#"+$(this).attr('errorid');
		var len=$(this).attr('len');
		if(($(this).val().trim() == "") && $(this).attr('mandatory')=="true"){
			$(errorid).html("please enter "+$(this).attr('placeholder'));
			$(errorid).show(); 			
		}
		else if($(this).attr("len")){
			if($(this).val().length>len){
				$(errorid).html("The length must be within "+len+" characters");  
                $(errorid).show();
			}
			else if($(this).is(".url")&&$(this).val()!=""){
            	if(errorid=="#fburlerr" && $(this).val().search("facebook.com")!=4){
                  $(errorid).html("Facebook link must start with www.facebook.com");  
                  $(errorid).show();
			     }
                else if(errorid=="#twurlerr" && $(this).val().search("twitter.com")!=4){
                $(errorid).html("Twitter link must start with www.twitter.com");   
                $(errorid).show();
    		    }
                else if(errorid=="#lnurlerr" && $(this).val().search("linkedin.com")!=4){
                $(errorid).html("Linkedin link must start with www.linkedin.com");
                $(errorid).show();
    		    } 
                else if(errorid!="#orgurlerr"&&$(this).val().search("/")==-1){
                	$(errorid).html("Please type your userid after '/' front slash ");
                    $(errorid).show();
                }
                else if(errorid!="#orgurlerr"&&$(this).val().split("/")[1].trim()==""){
                	$(errorid).html("Please type your userid after '/' front slash ");
                    $(errorid).show();
                }            	
                else if(errorid=="#orgurlerr" && $(this).val().search("www")!=0){
                    $(errorid).html("Please enter valid url");
                    $(errorid).show();
        		    }
                else{
                	$(errorid).hide();
                }
            }
			else if($(this).attr("spchar")){
				if($(this).attr("spchar")=="notallowed"&&specialcharacters.test($(this).val())){
					 $(errorid).html("Special characters are not allowed");
	                 $(errorid).show();
				}
				else{
					$(errorid).hide();
				}
			}
			else{
				$(errorid).hide();
			}
		}
		
        else{
            	$(errorid).hide();
            }
	});
	$('.saveprofo').click(function(){
		var data = professionalPushObject();
			//var obj={};
		$('.professinfoerror').trigger('focusout');
		var isError = "";
		$('.error-maskpro').each(function(){
			if($(this).attr('style')!="display: none;"){
				isError="true";
				
			}
		});		
		if(isError!="true"){
			 var addedData = Ajax.ajaxHelper("POST",courseData.getContextPath()+"/rest/professionalinfo",data,"json","application/json");
			
		}		
	  //  var addedData = Ajax.ajaxHelper("POST",courseData.getContextPath()+"/rest/professionalinfo",data,"json","application/json");
	   var obj={};
		if(addedData=="CREATED"){
			obj.message="The Professional information has been successfully saved";
			$(".contentbackgound").css('margin-top','0px');
			renderTemplate("#professprofilealerttmpl",obj,"#professprofilealert");
			alertCloseEvent();
			 $(".alerter").fadeIn('slow').delay(3000).fadeOut('slow'); 
			 previewinfo();
		}
	   else{
		  obj.message="Please correct the errors displayed in the Alert Box";
		 	$(".contentbackgound").css('margin-top','0px');
			renderTemplate("#professprofilealerttmpl",obj,"#professprofilealert");
			$(".alerter").removeClass("alert-success");	
		 	$(".alerter").addClass("alert-danger");
			alertCloseEvent();
			 $(".alerter").fadeIn('slow').delay(3000).fadeOut('slow'); 
	   		}		
	});
	function previewinfo(){
		loadprofessionalinfo($('#personid').val());
		$('#professinfoform').hide();
		$('#authorprofessproftable').show();
	}
	function loadprofessionalinfo(personid){
		var workexperienceList=courseData.getWorkExperienceByPersonId(personid);
		var professionaldetails={};
		for(i=0;i<workexperienceList.length;i++){
			professionaldetails.organization=workexperienceList[i].organization;
			professionaldetails.designation=workexperienceList[i].designation;
			professionaldetails.award=workexperienceList[i].award;
			professionaldetails.workexperienceid=workexperienceList[i].workexperienceid;
			professionaldetails.aboutauthor=workexperienceList[i].aboutauthor;
			professionaldetails.photourl=workexperienceList[i].personphoto;
			professionaldetails.authorname=workexperienceList[i].authorname;
			if(workexperienceList[i].url!=null){
			if(workexperienceList[i].url.search("facebook")==4){
				professionaldetails.facebook=workexperienceList[i].url;
			}
			else if(workexperienceList[i].url.search("twitter")==4){
				professionaldetails.twitter=workexperienceList[i].url;
			}
			else if(workexperienceList[i].url.search("linkedin")==4){
				professionaldetails.linkedin=workexperienceList[i].url;
			}
			else{
				professionaldetails.orgurl=workexperienceList[i].url;	
			}
			}
		}
		loadWorkExperience(professionaldetails);
	}
function loadWorkExperience(workExperienceList){
		renderTemplate("#authorprofessproftmpl",workExperienceList,"#authorprofessproftable");
}
}

function alertCloseEvent(){
	$('.alertclose').click(function(){
		$(".contentbackgound").css('margin-top','20px');		
	});
	}

function loadpersonalinfo(){
	var personalinfo=courseData.loadIndividualProfileData();
	renderTemplate("#personalinfotmpl", personalinfo, "#personalinfotable");
	var vbn=$('.dobval').val();
	var converteddob = new Date(vbn);
	if( converteddob != 'Invalid Date'){
		$(".dobval").datepicker({dateFormat: 'dd/mm/yyyy', endDate:'+0d'})
		.datepicker('setDate', converteddob);
	}
	/*$('.dobval').datepicker({
		dateFormat: 'dd/mm/yyyy', 
		endDate:'-1d'
	});*/
	
	$('.dobval').datepicker({
		format: 'dd-M-yyyy',
		endDate:'-1d',
		autoclose:true,
		todayHighlight: true		
	});
		
	var gendervalue=$('.genderval').attr('sex');
	$('.genderbtn').removeClass('btn-blue');
	if(gendervalue=="0"){
		$('.malebtn').addClass('btn-blue');
	}
	else{
		$('.femalebtn').addClass('btn-blue');
	}
	$('.malebtn').click(function(){
		$('.genderbtn').removeClass('btn-blue');
		$(this).addClass('btn-blue');
		$('.genderval').attr('sex','0');
	});
	$('.femalebtn').click(function()
			{
		$('.genderbtn').removeClass('btn-blue');
		$(this).addClass('btn-blue');
		$('.genderval').attr('sex','1');
			});
	$('.saveprofilebtn').click(
					function(e) {
						$('.alertfirstname').hide();
						$('.alertlastname').hide();
						$('.alerteduqalification').hide();
						var specialcharacters=/[#?!@$%^&*-._()+=]+/;
						var specialcharactersdot = /[^0-9a-zA-Z.]/g;
						var numericcharacters=/[0-9]/;
//						var eduqualification=/[A-Za-z];
						if ($.trim($('.dobval').val()) != '' && $.trim($('.firstNameval').val()) != ''  
							&& $.trim($('.eduqualificationval').val())!=''	&& $.trim($('.emailval').val()) != ''&& $.trim($('.contactnumberval').val()) !='' && $.trim($('.lastNameval').val() !='')) {
							
							if(specialcharacters.test($('.firstNameval').val())){
								$('.alertfirstname').html('Special characters are not allowed');
								$('.alertfirstname').show();
								$('.firstNameval').focus();
								focusedprofile();
							}
							
							else if(numericcharacters.test($('.firstNameval').val())){
								$('.alertfirstname').html('Numbers are not allowed');
								$('.alertfirstname').show();
								$('.firstNameval').focus();
								focusedprofile();
							}
							
							else if(specialcharacters.test($('.lastNameval').val())){
								$('.alertlastname').html('Special characters are not allowed');
								$('.alertlastname').show();
								$('.lastNameval').focus();
								focusedprofile();
							}
							
							else if(numericcharacters.test($('.lastNameval').val())){
								$('.alertlastname').html('Numbers are not allowed');
								$('.alertlastname').show();
								$('.lastNameval').focus();
								focusedprofile();
							}
							
							else if (specialcharactersdot.test($('.eduqualificationval').val().trim())) {
//								$('.eduqualificationval').numeric({allow:"."});
								/*$('.eduqualification').test($('.eduqualificationval').val())*/
								$('.alerteduqalification').html('Special characters are not allowed');
								$('.alerteduqalification').show();
								$('.eduqualificationval').focus();
								focusedprofile();
							} 
							
							else if(numericcharacters.test($('.eduqualificationval').val())){
								$('.alerteduqalification').html('Numbers are not allowed');
								$('.alerteduqalification').show();
								$('.eduqualificationval').focus();
								focusedprofile();
							}

							else if (!ValidateEmail($(".emailval").val().trim())) {
								$('#messageinfomodal').modal('show');
								$('.msginfo').text("Invalid email address.");
							}
							else if ($.trim($('.contactnumberval').val()).length<10){
								$('.contactnumberval').focus();
								$('.alertcontact').html("Should be greater than 10 digits");
								$('.alertcontact').show();
								focusedprofile();
							}
							else if ($.trim($('.contactnumberval').val())[0]==0&&$.trim($('.contactnumberval').val())[1]==0&&$.trim($('.contactnumberval').val())[2]==0){
								$('.contactnumberval').focus();
								$('.alertcontact').html("Invalid Phone Number");
								$('.alertcontact').show();
								focusedprofile();
							}
							else {
								// SAVE OR UPDATE PROFILE PICTURE
								if ($('#person-photo-url').attr('value').trim().length == 0	& $('#person-photo-url').attr('value').trim() == "") {									
									// new insert use new file ==>
									uploadEvent(this);

									profilevalues(this,$('#person-photo-url').attr('value').trim());

								} else if ($('#person-photo-url').attr('value').trim().length > 0 & $('#person-photo-url').attr('value').trim().length != "") {
									// existing==>
									if ($('#profile-media').val().trim() != "") {

										// new file added so update event to
										// update existing use newly added
										uploadEvent(this);
										// profilevalues(this,$('#person-photo-url').attr('value').trim());

									} else {
										// no new file added use existing ==>
										profilevalues(this, $('#person-photo-url').attr('value').trim());
									}
								}
							}

						} else if ($.trim($('.firstNameval').val()) == '') {
							$('.alertfirstname').show();
							$('.firstNameval').focus();
							focusedprofile();
						}else if ($.trim($('.eduqualificationval').val())=='') {
							$('.alerteduqalification').show();
							$('.eduqualificationval').focus();
							focusedprofile();
						} 
						else if ($.trim($('.dobval').val()) == '') {
							$('.dobval').focus();
							$('.alertdob').show();
							focusedprofile();
						}
						else if ($.trim($('.emailval').val()) == '') {
							$('.emailval').focus();
							$('.alertemail').show();
							focusedprofile();
						} 
						else if ($.trim($('.contactnumberval').val())==''){
							$('.contactnumberval').focus();
							$('.alertcontact').html("Please Enter the Contact Number");
							$('.alertcontact').show();
							focusedprofile();
						}

						else {
							$('#messageinfomodal').modal('show');
							$('.msginfo').text("Please Fill mandatory Fields");
							/*
							 * setTimeout(function(){
							 * $('#messageinfomodal').modal('hide'); },1000);
							 */
							e.preventDefault();
						}

					});
}
function profilevalues(selector,filename){
	personidobj={};
	personidobj.firstName=$('.firstNameval').val().trim();
	personidobj.lastName=$('.lastNameval').val().trim();
	personidobj.eduQualification=$('.eduqualificationval').val().trim();
	if($('.dobval').val().indexOf('-')>0){
	var datearray=$('.dobval').val().split('-');
	personidobj.dob=new Date(datearray[1]+" "+datearray[0]+" "+datearray[2]).getTime();
	}
	else{
		personidobj.dob=new Date($('.dobval').val()).getTime();
	}
	personidobj.sex=$('.genderval').attr('sex').trim();
	personidobj.email=$('.emailval').val().trim();
	personidobj.contactnumber=$('.contactnumberval').val().trim();
	personidobj.contactinfoid=$(selector).attr('contactinfoid');
	personidobj.phoneid=$(selector).attr('phoneid');
	personidobj.emailid=$(selector).attr('emailid');
	personidobj.personphotourl=filename;
	updateprofile(personidobj);
}
function updateprofile(personidobj){
	var updateSuccessValue=courseData.updateProfileData(personidobj);
	if(updateSuccessValue=="Success"){
		$('#messageinfomodal').modal('show');
		$('.msginfo').text("Information Saved");
		/*setTimeout(function(){
			$('#messageinfomodal').modal('hide');										
		},5000);*/
		
		loadpersonalinfo();
	}
}
function loadpasswordtmpl(){
	renderTemplate("#passwordinfotmpl", null, "#personalinfotable");
	var status=["one","two","three","four","five"];
	var index=0;
	var caps=false;
	var small=false;
	var number=false;
	var spl=false;
	var leng=false;
	$('.newpwdval').on("keyup focusout",function(){
		$('#strengthmsg').show();
		$('.progress').show();
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
		if(password.length<6){
			$('#password1err').show();
		}
		else{
			$('#password1err').hide();
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
	$('.confirmpwdval').on("keyup focusout",function(){
		var password=$('.newpwdval').val();
		var passwordretype=$('.confirmpwdval').val();
		if(password.indexOf(passwordretype)!=0){
			$('#password2err').html("The password doesn't match");
			$('#password2err').show();
		}
		else{
			$('#password2err').hide();
		}
	});
	$('.confirmpwdval').focusout(function(){
		var password=$('.newpwdval').val();
		var passwordretype=$('.confirmpwdval').val();
		if(passwordretype.trim()==""){
			$('#password2err').html("Please fill the password");
			$('#password2err').show();
		}
		else if(password.length!=passwordretype.length){
			var miss=password.length-passwordretype.length;
			if(password.length<passwordretype.length){
				$('#password2err').html("The Password doesn't match");
				$('#password2err').show();
			}
			else{			
			$('#password2err').html("Password doesn't match, Last "+miss+" letter missing");
			$('#password2err').show();
			}
		}
		else{
			$('#password2err').hide();
		}
	});
	$('.passwordresetbtn').click(function(e){
		var oldpassword=$('.oldpwdval').val();
		var newpassword=$('.newpwdval').val();
		var confirmpassword=$('.confirmpwdval').val();
	
		
		if(newpassword!=''){
		
			if(oldpassword!=newpassword){
			$('.newpwdval').trigger('focusout');
			$('.confirmpwdval').trigger('focusout');
			if($('#password1err').is(':hidden')&&$('#password2err').is(':hidden')&&newpassword==confirmpassword){
				resetPassword(oldpassword,newpassword);
				$('#strengthmsg').hide();
				$('.progress').hide();
				$('.oldpwdval').val("");
				$('.newpwdval').val("");
				$('.confirmpwdval').val("");
			}
		}
			else{
				$('#messageinfomodal').modal('show');
				$('.msginfo').text("Password do not same as old password");
				$('.oldpwdval').val("");
				$('.newpwdval').val("");
				$('.confirmpwdval').val("");
				$('#strengthmsg').hide();
				$('.progress').hide();
			}
		}
		else{
			$('#messageinfomodal').modal('show');
			$('.msginfo').text("Please fill the password field");
			
			/*setTimeout(function(){
				$('#messageinfomodal').modal('hide');										
			},3000);*/	
					if($('.oldpwdval').val()!== ""){
						
						if($('.newpwdval').val()!== ""){
							
							if($('.confirmpwdval').val()!== ""){
								
							}
							else{
								$('.confirmpwdval').focus();
							}
						}else{
							$('.newpwdval').focus();
						}
					}
					else{
						$('.oldpwdval').focus();
					}
			
			e.preventDefault();
		}
	});
}
function resetPassword(oldpassword,newpassword){
	var passwordobj={};
	passwordobj.oldpassword=oldpassword;
	passwordobj.newpassword=newpassword;
	var successvalue=courseData.resetPassword(passwordobj);
	if(successvalue=="nomatch"){		
		$('#messageinfomodal').modal('show');
		$('.msginfo').text("Please type the correct old password");
		/*setTimeout(function(){
			$('#messageinfomodal').modal('hide');										
		},1000);*/
	}
	else{
		$('#messageinfomodal').modal('show');
		$('.msginfo').text("password successfully updated");
		$('.oldpwdval').val("");
		$('.newpwdval').val("");
		$('.confirmpwdval').val("");
		$(".passwordresetbtn").attr('disabled','disabled');
		$(".resetupdatebtn").show();										
		
		/*setTimeout(function(){
			$('#messageinfomodal').modal('hide');										
		},1000);*/

	}
}
function focusedprofile(){
	$('.firstNameval').keypress(function(){
	$('.alertfirstname').hide();
	});
	$('.emailval').keypress(function(){
	$('.alertemail').hide();	
	});
	$('.eduqualificationval').keypress(function(){
	$('.alerteduqalification').hide();
	});
	$('.dobval').focus(function(){
		$('.alertdob').hide();
	});
	$('.contactnumberval').keypress(function(){
		$('.alertcontact').hide();
	});
}
function numberonly(){
	$('.contactnumberval').keyup(function () { 
	    this.value = this.value.replace(/[^0-9\.]/g,''); 
	});
	$('#account-info-account-number').keyup(function () { 
	    this.value = this.value.replace(/[^0-9\.]/g,''); 
	});
	$('#account-info-hidden-number').keyup(function () { 
	    this.value = this.value.replace(/[^0-9\.]/g,''); 
	});
}

//alphanumeric only allowed this validation 
function alphanumeric(alfanum,alfid){
	
	var rr =alfanum; 
	var letterNumber = /^[0-9a-zA-Z' ']+$/; 
	  if(rr.match(letterNumber)){    
		  $('#'+alfid).hide();
		  return true;
	    }
	  else{
		  $('#'+alfid).show();
		  return false;
	  }	
	}
/*Email*/ 
function ValidateEmail(email) {
    var expr = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    return expr.test(email);
};
/*Email*/
/*upload Process */
function uploadEvent(selector){
	if($('#profile-media').val()!=""){
		var fileInput=document.getElementById("profile-media");
	var str=fileInput.files[0].type;
	var img=str.split("/");
	if(img[0] != "image"){
		alert("Invalid file format. Only can uplod the following file types JPG,TIF,PNG,GIF");
		return;
	}
	var xhr=new XMLHttpRequest();
	xhr.onload=function(data){
		if(xhr.status == 200){
			console.log("complete "+xhr.status);
			$('#person-photo-url').attr('value',xhr.responseText);
			$('#uploadInput').val( xhr.responseText);
			$('#uploadInput').attr('readonly', true);
			profilevalues(selector,xhr.responseText);
		}else{
			console.log("failed");
			$('#saveAction' ).prop( "disabled", false );
		}
	};
	xhr.onerror=function(){
		console.log("Error ! upload failed.Can not connect to server");
	};
	
	var fd=new FormData();
	fd.append("file",document.getElementById('profile-media').files[0]);
	xhr.open("POST",courseData.getContextPath()+"/rest/userprofile/singlefile",true);
	console.log("open");
	xhr.send(fd);
	}
	else{
		profilevalues(selector,"-");
	}
};
// BANK INFO 
function loadAccountInfotemplate(){ 
	 var  bankDetails  = Ajax.ajaxHelper("GET",courseData.getContextPath()+"/rest/binfo",undefined,"json","application/json");
	 var _data = ""; 
	 var flag = true;
	 if(bankDetails != undefined ){
		 _data = bankDetails[0];
 renderTemplate("#account-info-template", _data, "#personalinfotable");
		 renderTemplate("#country-template", countryjson, "#account-info-country");
		 renderTemplate("#country-template", countryjson, "#account-info-country");
		 hideErrorClasses();
		 var selectvalue=_data.country;
		 combodata.comboSelected('#account-info-country',selectvalue);	 }else{
		 _data = {
				     accountNumber: "",
					 bankName: "",
					 bankdetailsstatus: "",
					 branchName: "",
					 dateofcreation: "",
					 id: "",
					 ifscCode: "",
					 location: "",
					 country:""
		 }; 
		 flag = false;
		 renderTemplate("#account-info-template", _data, "#personalinfotable");
		 renderTemplate("#country-template", countryjson, "#account-info-country");
		 hideErrorClasses();
	 }
	 
	$('.save-bank-account-information').on('click',function(){
		hideErrorClasses();
		if(validateAccountInfoForm(constructValidatElement('manageprofilecont'),flag)){ 
			var data = bankInfoPushObject();
			if(alphanumeric(alfanum,alfid)){
			if(assertAccountNumber()){
				if($('#account-info-account-id').val().trim() == ""){
					 var addedData = Ajax.ajaxHelper("POST",courseData.getContextPath()+"/rest/binfo",data,"json","application/json");
					 var newId = addedData.id;
					 $('#account-info-account-id').val(newId);
				     $('#messageinfomodal').modal('show');
			         if($('.rolefind').attr('courseid')=="null"){
			        	 $('.msginfo').text("Bank Account Information has been added to your profile.");
			        	 $('.bank-info-link').trigger('click');
			         }
			         else if($('.rolefind').attr('courseid')!=undefined){
			        	 $('.msginfo').text("Bank Account Information has been added to your profile.");
			        	 setTimeout(function(){
				        	 $('[name=courseid]').val($('.rolefind').attr('courseid'));
				        	 $('[name=banktocourseform]').attr('action','managecourse');
				        	 $('[name=banktocourseform]').submit();			
							},2000);	        	 
			         }	
				}else{
				     Ajax.ajaxHelper("PUT",courseData.getContextPath()+"/rest/binfo",data,"json","application/json");
				     $('#messageinfomodal').modal('show');
			         $('.msginfo').text("Bank Account Information has been updated");
			         $('#account-info-account-number-error-same').hide();
			         $('.bank-info-link').trigger('click');
				} 
			}else{
			    	$('#account-info-account-number-error-same').show();
				
			}
			}/*else{
				
			}*/
			
		}
		else{
			
		}
	}); 
}
function hideErrorClasses(){
		

	$('#account-info-hidden-number').on('keypress keyup keydown click focusout ',this,function(){
		console.log($(this).val().trim());
		if(! ($(this).val().trim() == "")){
			$('#account-info-hidden-number-error').hide(); 
			alfanum=$(this).val();
			alfid=$('#account-info-hidden-number-error-special').attr('id');
			alphanumeric(alfanum,alfid);
		} 
	});
		$('#account-info-account-number').on('keypress keyup keydown click focusout ',this,function(){
			console.log($(this).val().trim());
			
			if(! ($(this).val().trim() == "")){
				$('#account-info-account-number-error-same').hide(); 
				alfanum=$(this).val();
				alfid=$('#account-info-account-number-error-special').attr('id');
				alphanumeric(alfanum,alfid);
			} 
			    
		});
		
		$('#account-info-bank-name').on('keypress keyup keydown focusout',this,function(){
			console.log($(this).val().trim());
			if(! ($(this).val().trim() == "")){
				$('#account-info-bank-name-error').hide();
				alfanum=$(this).val();
				alfid=$('#account-info-bank-name-special').attr('id');
				alphanumeric(alfanum,alfid);
				
			} 
		});
		
		$('#account-info-branch-location').on('keypress keyup keydown focusout',this,function(){
			console.log($(this).val().trim());
			if(! ($(this).val().trim() == "")){
				$('#account-info-branch-location-error').hide();
				alfanum=$(this).val();
				alfid=$('#account-info-branch-location-special').attr('id');
				alphanumeric(alfanum,alfid);
			} 
		});
		
		$('#account-info-branch-name').on('keypress keyup keydown click focusout',this,function(){
			console.log($(this).val().trim());
			if(! ($(this).val().trim() == "")){
				$('#account-info-branch-name-error').hide();
				alfanum=$(this).val();
				alfid=$('#account-info-branch-name-special').attr('id');
				alphanumeric(alfanum,alfid);
			} 
		});
		
		$('#account-info-ifsc-code').on('keypress keyup keydown',this,function(){
			console.log($(this).val().trim());
			if(! ($(this).val().trim() == "")){ 
				$('#account-info-ifsc-code-error').hide();
				alfanum=$(this).val();
				alfid=$('#account-info-account-ifsc').attr('id');
				alphanumeric(alfanum,alfid);
			}
		});
		$('#account-info-country').on('click',this,function(){
			console.log($(this).val().trim());
			if(! ($(this).val().trim() == "")){ 
				$('#account-info-country-error').hide();
			}
		});
}
function constructValidatElement(formWrapperDiv){ 
	var validateELement = {};
	var $elements = [];
	$elements = $('.'+formWrapperDiv).find("input,select"); 
	$elements.each(function(){
		 scopeELement = $(this).attr('id');
		validateELement[scopeELement] = scopeELement +'-error';
	});  
	return validateELement;
}
function validateAccountInfoForm(validateElement,flag){  
	var elementIds =  [];
	elementIds = Object.keys(validateElement);
	for(var i=0; i<elementIds.length;i++){ 
		$this_elementId = elementIds[i];
		$this_errorId = validateElement[elementIds[i]]; 
		if(flag){
			if(! ( ($('#'+$this_elementId)).val().trim() == "") ){
				$('#'+$this_errorId).hide(); 
			}else{
				$('#'+$this_elementId ).focus();
				$('#'+$this_errorId).show(); 
				return false;
			}
		}else{ 
			if(! (  $this_elementId  == "account-info-account-id") ){
				if( ! ($('#'+$this_elementId).val().trim() == "") ){
					$('#'+$this_errorId).hide(); 
				}else{
					$('#'+$this_elementId ).focus();
					$('#'+$this_errorId).show(); 
					return false;
				}
			}  
		} 
	} 
	return true;
}
function bankInfoPushObject(){ 
	var bankDetails ={};
	bankDetails = {
				 "bankInfoId" :  $('#account-info-account-id').val().trim() , 
				 "bankInfoName" : $('#account-info-bank-name').val().trim(), 
				 "bankInfoBranchName" : $('#account-info-branch-name').val().trim() , 
				 "bankInfoAccountNumber" : $('#account-info-account-number').val().trim(),
				 "bankInfoIFSCCode" : $('#account-info-ifsc-code').val().trim(),
				 "bankInfoBranchLocation" : $('#account-info-branch-location').val().trim(),
				 "country": $('#account-info-country').val().trim()
		}; 
	return bankDetails;
};


function professionalPushObject(){ 
	var profDetails ={};
	profDetails = {
				 "organizationname" :  $('[name=organizationname]').val().trim() ,
				 "designation" :  $('[name=designation]').val().trim() , 
				 "aboutauthor" :  $('[name=aboutauthor]').val().trim() ,
				 "awards" :  $('[name=awards]').val().trim() , 
				 "websiteurl" :  $('[name=websiteurl]').val().trim() , 
				 "facebookurl" :  $('[name=facebookurl]').val().trim(),
				 "twitterurl" :  $('[name=twiiterurl]').val().trim(),
				 "linkedinurl" :  $('[name=linkedinurl]').val().trim()
		}; 
	return profDetails;

};

//-----------------------------------------

/*function presenterPushObject(){ 
	var presenterDetails ={};
	presenterDetails = {
				 "organizationname" :  $('[name=organizationname]').val().trim() ,
				 "designation" :  $('[name=designation]').val().trim() , 
				 "aboutauthor" :  $('[name=aboutauthor]').val().trim() ,
				 "awards" :  $('[name=awards]').val().trim() , 
				 "websiteurl" :  $('[name=websiteurl]').val().trim() , 
				 "facebookurl" :  $('[name=facebookurl]').val().trim(),
				 "twitterurl" :  $('[name=twiiterurl]').val().trim(),
				 "linkedinurl" :  $('[name=linkedinurl]').val().trim()
				 
				 
				 
				 
				 presentertypeid:array,
					applicationname:$('.appname').val(),
					path:$('.path').val(),
					helpurl:$('.helpurl').val(),
					workingduration:work,
					logincount:count
				 
				 
				 
				 
				 
				 
				 
		}; 
	return presenterDetails;

};*/



//-----------------------------------------





function presenterappdetailsPushObject(){
	var appDetails={};
	appDetails = {
				"appname":$('[name=appname]').val().trim(),
				"path":$('[name=path]').val().trim(),
				"helpurl":$('[name=helpurl]').val().trim(),
				"type":$('[name=type]').val().trim(),
				"workingduration":$('[name=workingduration]').val().trim(),
				"logincount":$('[name=logincount]').val().trim(),
	};
	return appDetails;
	
};


//-----------------------------------------
function assertAccountNumber(){
	
	var hiddenvalue= $('#account-info-hidden-number').val();
	var accountvalue= $('#account-info-account-number').val();
	console.log("hiddenvalue"+hiddenvalue);
	console.log("accountvalue"+accountvalue);
	if(hiddenvalue == accountvalue){
		$('.account-info-account-number error-masker').hide();
		return true;
	}
	else{
		$('.account-info-account-number error-masker').show(); 
		return false;
	}
	
}


combodata = {
		comboSelected:function(selector,selectvalue){
			$(selector+" option").each(function(){
				if($(this).val() == selectvalue){
					$(this).attr("selected","true");
					return false;
				}
			});
		}
};