$(document).ready(function(){
	$("[name=masteradminid]").val(courseData.getMasterAdminId()[0].organizationid);
	$('.searchbtn').click(function(e){
		var searchkeyword=$('.searchtxt').val().trim();
		if(searchkeyword!=undefined && searchkeyword.length!=0){
			$('[name=keyword]').val(searchkeyword);
			$('[name=previewguestform]').attr('action','ssearchresult');
			$('[name=previewguestform]').submit();			
		}
		else{
			e.preventDefault();
		}
	});
	$('.searchtxt').keypress(function(event){
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == '13'){
			var searchkeyword=$('.searchtxt').val().trim();
			if(searchkeyword!=undefined && searchkeyword.length!=0){
				$('[name=keyword]').val(searchkeyword);				
				$('[name=previewguestform]').attr('action','ssearchresult');
				$('[name=previewguestform]').submit();
			}
			else{
				event.preventDefault();
			}
		}
	});
	if($('.invaliderror').attr('error')=="invalid"){
		$('.invalidsuercls').css('display','block');
		$('#signupmodal').modal('show');
	}
	else if($('.invaliderror').attr('error')=="invalidsocialuser"){
		$('.errormsg').hide();
		$('.socialerrormsg').show();
		$('.invalidsuercls').css('display','block');
		$('#signupmodal').modal('show');
	}
	else if($('.invaliderror').attr('error')=="deactivated"){
		$('.errormsg').html('<strong>Warning!</strong> Your account has been deactivated, Please contact the administrator');
		$('.invalidsuercls').css('display','block');
		$('#signupmodal').modal('show');
	}
	else{
		$('.invalidsuercls').hide();
	}
	
	signupform=$('.signupform'),signinform=$('.signinform'),
	signupspan=$('.signupspan'),loginspan=$('.loginspan'),
	downloadbtn=$('.downloadbtn'),signupmodal=$('#signupmodal'),
	forgotpassword=$('#forgotpassword'),footerlinks=$('.footerlinks'),footermodal=$('#footermodal'),
	footermodaltitle=$('#footermodaltitle'),footermodalbody=$('#footermodalbody'),
	privacy=$('#privacytmpl'),terms=$('#termstmpl'),refund=$('#refundtmpl');
	$('.signupmodal').click(function(){
		$('.invalidsuercls').css('display','none');
		portal.helper.hidediv([loginspan,signupform]);
		portal.helper.showdiv([signupspan,signinform]);
		$('.alreadyuser').hide().text("");
	});
	
	portal.getDomReady();	
	
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
	$('.showpassword').mousedown(function(){
		$('.newpwdval').get(0).type = 'text';
	});
	$('.showpassword').on('mouseup mousemove',function(){
		$('.newpwdval').get(0).type = 'password';
	});
});

$(".invalidsuercls").fadeIn('slow').delay(3000).fadeOut('slow',function() {
	$(".contentbackgound").css('margin-top','20px');
	}); 

var portal={
		getDomReady:function(){
			$('a.page-scroll').bind('click', function(event) {
				var $anchor = $(this);
				$('html, body').stop().animate({
					scrollTop: $($anchor.attr('href')).offset().top
				}, 1500, 'easeInOutExpo');
				event.preventDefault();
				
			});
			$( window ).scroll(function() {
			$('body').scrollspy({
				target: '.navbar-fixed-top'
			});
		
			});
			$('.navbar-collapse ul li a').click(function() {	
				$('body').scrollspy();
				target: '.navbar';
				$('[data-spy="scroll"]').each(function () {
					  $(this).scrollspy('refresh');			
			});
			});
			downloadbtn.click(function(){
				portal.data.presenterDownload();
			});
			$("[name=firstname]").keypress(function(){
				$(".alertfirstname").hide();
			});
			$("[name=password]").keypress(function(){
				$(".alertpassword").hide();
			});
			
			boards().init();
			
			var linkval = $('.invaliderror').attr('l');
			if(linkval=="c"){
				$('.page-scroll[href=#courses]').trigger('click');				
			}else if(linkval=="p"){
				$('.page-scroll[href=#services]').trigger('click');
			}else if(linkval=="o"){
				$('.page-scroll[href=#organization]').trigger('click');
			}else{
				$("html,body").animate({ scrollTop: 0 }, "slow");
			}
			var timer = null;
			$('.searchtxt').typeahead({
				matcher: function () { return true; },
				source:function(query,process){
					if(timer){
						clearTimeout(timer);
					}
					timer=setTimeout(function(){
						if(query.trim()!=""){
							var result=portal.data.searchCourses(query);
							if(result!="false"){
								process(result);
							}
							else{
								process(["no match found"]);
							}
						}
					},1500);
				},
				items:20,
				updater:function (item) {
					if(item!="no match found"){
						return portal.data.previewReDirector(index[data.indexOf(item)]);
					}
				} 
			});
			$('.signupa').click(function(){
				$("[name=firstname]").show();
				$("[name=password]").show();
				$(".sinupformsubmit").text('SIGN UP');
				$('.alreadyuser').hide().text("");
				$('.invalidsuercls').css('display','none');
				portal.helper.hidediv([signupspan,signinform]);
				portal.helper.showdiv([loginspan,signupform]);
			});
			$('.signina').click(function(){
				$('.invalidsuercls').css('display','none');
				portal.helper.hidediv([loginspan,signupform]);
				portal.helper.showdiv([signupspan,signinform]);
			});

			if(portal.controller.masterorgid()!=null){
				$('[name=organizationid]').val(portal.controller.masterorgid);	
			}
			$('.userch').blur(function(){
					portal.helper.userNameCheck(portal.data.userNameCheck());
			});
			footerlinks.click(function(){
				portal.helper.showFooterModal($(this).attr('a'));
			});
			if($('.invaliderror').attr('a')=='s'){
				signupmodal.modal('show');
				$('.invalidsuercls').hide();
			}
			else{
				
			}
			$('.newpassword').click(function() {		
				$('.newpassword').hide();
				$('.loadingicon').css('display','block');
				var username = $('#usernameId').val();
				var passwordidobj = {};
				passwordidobj.username = username;
				passwordidobj.organizationid=$('[name=organizationid]').val();
				if(username.length!=0){
					var string = courseData.forgotpassword(passwordidobj);					
					if (string !== "valid") {
						$('.alert').removeClass('alert-success');
						$('.alert').addClass('alert-danger');											
						$('#msg').html("The username is invalid.");
						$('.forgotalert').css('display','block');	
						$('.newpassword').css('display','block');
						$('.loadingicon').css('display','none');
					} else {
						$('.loadingicon').css('display','block');
						$('.alert').removeClass('alert-danger');
						$('.alert').addClass('alert-success');
						$('#msg').html("The password has been sent to your email successfully");
						$('#usernameId').val("");
						$('.forgotalert').css('display','block');
						$('.newpassword').css('display','block');
						$('.loadingicon').css('display','none');							
					}
				}
				else{
					$('#usernameId').focus();
					$('.alert').removeClass('alert-success');
					$('.alert').addClass('alert-danger');											
					$('#msg').html("Please enter a user name.");
					$('.forgotalert').css('display','block');	
					$('.newpassword').css('display','block');
					$('.loadingicon').css('display','none');
				}
				
			});
			$('.forgot').click(function(){
				signupmodal.modal('hide');
				forgotpassword.modal('show');				
			});
			$(".sinupformsubmit").click(function(){
				$('.userch').trigger('blur');
				var textValue = $(this).text();
				if(textValue=="Enroll in Organization"){
					var pwdval = $('.newpwdval').val().trim();
					if(pwdval.length!=0){
						newSignUpForExistingUser.home($('.userch').val(),$('.newpwdval').val());	
					}else{
						$('.alertpassword').css('display','block').text("Please enter the password.");
					}					
				}else{
					portal.helper.sinupform();
				}
			});
		}
};

portal.view={
		popularcourses:function(){
			renderTemplate("#topcoursestmpl",portal.controller.popularcourses(), "#topcoursestable");
			$('.crsp').each(function(){
				var price=$(this).attr('price');
				var priceicon = $(this).attr('priceicon');
				if(price=="Free"){
					$(this).html("Price : Free");
				}
				else if(price==""){
					$(this).html("");
				}
				else{
					$(this).html("Price : <i class='fa "+priceicon+"'></i>"+price);	
				}
			});
			$('.ratingcourse').each(function(){
				var rating=$(this).attr('rating');
				$(this).raty({readOnly: true, score: rating });
			});	
			$('.popcourselink').click(function(){
				var courseid=$(this).attr('courseid');
				var count=$(this).attr('count');
				portal.data.previewCourseData(courseid,count);
			});
		}
};

portal.data={
		previewCourseData:function(courseid,count){
			$('[name=courseid]').val(courseid);
			$('[name=count]').val(count);
			$('[name=popcourseform]').attr('action','preview');
			$('[name=popcourseform]').submit();
		},	
		searchCourses:function(keyword){
			data=[];
			index=[];
			var courses=ajaxhelper("searchOrgcourses?keyword="+keyword+"&masteradminid="+$("[name=masteradminid]").val());
			if(courses!="false"){
				for (var i = 0; i < courses.length;i++ ) {
					data[i]=courses[i].coursetitle+" - "+courses[i].author;
					index[i]=courses[i].courseid;
				}
				return data;
			}
			return "false";
		},
		previewReDirector:function(courseid){	
			$('[name=courseid]').val(courseid);
			$('[name=popcourseform]').attr('action','preview');
			$('[name=popcourseform]').submit();
		},
		userNameCheck:function(){
			usernameobj={};
			usernameobj.organizationid=$('[name=organizationid]').val();
			usernameobj.username=$('.userch').val();
			return portal.controller.userNameCheck(usernameobj);
		},
		presenterDownload:function(){
			signupmodal.modal('show');
			$('[name=targetUrl]').val('/app/downloadpresenter');	
		}
};

portal.controller={
		popularcourses:function(){
			return courseData.getPopularCourses();
		},
		masterorgid:function(){
			var masterorgidobj=$("[name=masteradminid]").val();
			return masterorgidobj;
		},
		userNameCheck:function(usernameobj){
			return useravailability=courseData.getUserNameAvailability(usernameobj);
		}
};

portal.helper={
		hidediv:function(arr,a){
			var i=0;
			for (;arr[i];) {
				arr[i].hide();
				i++;
			}
		},
		showdiv:function(arr){
			var i=0;
			for (;arr[i];) {
				arr[i].css('display','block');
				i++;
			}
		},
		userNameCheck:function(useravailability){
			if(useravailability=="Exits"){ 
				$('.alreadyuser').removeClass('alert-success');
				$('.alreadyuser').addClass('alert-danger');
				$("[name=firstname]").show();
				signupform.attr('action','');
				$("[name=password]").show();
				$(".sinupformsubmit").text('SIGN UP');
				$('.alreadyuser').css('display','block').text("The username has already been used.");	
				//$("[name=username]").focus();
			}
			else if(useravailability=="Addorganization"){
				//$("[name=password]").hide();
				$("[name=firstname]").hide();
				//$(".sinupformsubmit").attr('value','Enroll in Organization');
				$(".sinupformsubmit").text('Enroll in Organization');
				$('.alreadyuser').removeClass('alert-danger');
				$('.alreadyuser').addClass('alert-success');
				$('.alreadyuser').css('display','block').text("Already Exists, Do you want to enroll in this organization?");
			}
			else {
				$("[name=firstname]").show();
				$("[name=password]").show();
				$(".sinupformsubmit").text('SIGN UP');
				var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
				if (!filter.test($("[name=username]").val())) {
					$('.alreadyuser').removeClass('alert-success');
					$('.alreadyuser').addClass('alert-danger');
					$('.alreadyuser').css('display','block').text('Please provide a valid email address');
     				//$("[name=username]").focus();
				    return false;
				 }
				else{
					$('.alreadyuser').hide().text("");
				}
				//signupform.attr('action','withoutCourseSignUp');
			}
		},
		sinupform:function(){
			if($.trim($("[name=firstname]").val())==""){
				$('.alreadyuser').hide();
				$('.alertpassword').hide();
				$('.alertfirstname').css('display','block').text("Please enter the firstname.");
				//$("[name=firstname]").focus();
			}
			else if($.trim($("[name=username]").val())=="") {
				$('.alertfirstname').hide();
				$('.alertpassword').hide();
				$('.alreadyuser').css('display','block').text("Please enter the username.");
				//$("[name=username]").focus();
			}
			else if($.trim($("[name=password]").val())==""&&$(".sinupformsubmit").attr('value')=="SIGN UP"){				
					$('.alreadyuser').hide();
					$('.alertfirstname').hide();
					$('.alertpassword').css('display','block').text("Please enter the password.");
				//	$("[name=password]").focus();				
			}
			else if($.trim($("[name=password]").val()).length<6&&$(".sinupformsubmit").attr('value')=="SIGN UP"){
				$('.alreadyuser').hide();
				$('.alertfirstname').hide();
				$('.alertpassword').css('display','block').text("The password must have six characters");
			}
			else if($('.alreadyuser').is(':visible')&&$('.alreadyuser').hasClass('alert-danger')){
				$("[name=username]").focus();
			}
			else{
				signupform.attr('action','withoutCourseSignUp');
				signupform.submit();
			}
		},
		showFooterModal:function(type){
			footermodal.modal('show');
			var tmpl="",modaltxt="";
			switch(type) {
			case "p":
				tmpl=privacy;
				modaltxt="Privay Policy";
				break;
			case "t":
				tmpl=terms;
				modaltxt="Terms Of Use";
				break;
			case "r":
				tmpl=refund;
				modaltxt="Refund Policy";
				break;
			}
			renderTemplate(tmpl,null,footermodalbody);
			footermodaltitle.text(modaltxt);
		}
};

var newSignUpForExistingUser = {
		home : function(username,password){
			var obj = {
					username : username,
					password : password,
					organizationid : $('[name=organizationid]').val()
			};
			
			var signUpStaus = ajaxhelperwithdata("registerNewUserSignUp", obj);
			
			if(signUpStaus=="success"){
				$("[name=newjusername]").val(username);
				$("[name=newjpassword]").val(password);
				$("[name=newtargeturl]").val("/app/mycourses");
				$("[name=neworganid]").val($('[name=organizationid]').val());
				$("[name=newuserSignupform]").submit();
			}else if(signUpStaus=="passwordfailure"){
				$('.alertwrongpassword').html('<strong>Error!</strong> Wrong Password. Please type the correct password');
				$('.alertwrongpassword').css('display','block');
				setTimeout(function(){ 
					$('.alertwrongpassword').hide();					
				}, 3000);
				
			}else if(signUpStaus=="error"){
				$('.alertwrongpassword').html('<strong>Warning!</strong> Error, Please contact the administrator');
				$('.alertwrongpassword').css('display','block');
				setTimeout(function(){ 
					$('.alertwrongpassword').hide();					
				}, 3000);
			}
		},
};