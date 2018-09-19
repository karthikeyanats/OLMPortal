$(function(){ 
	author.profile.init();
});

var author = author || {};

author.profile = (function(){
	var personid = $('[name=personid]').val();
	var orgpersonid = $('[name=orgpersonid]').val();
	var userstatus = $('.userconfirm').attr('at');
	var controller = {
			personalinfo:function(){
				return Ajax.get(courseData.getContextPath()+"/rest/tutors/individual/profile/"+personid,undefined,"json","application/json");
			},
			courses:function(){
				return Ajax.get(courseData.getContextPath()+"/rest/tutors/individual/courses/"+orgpersonid,undefined,"json","application/json");
			},
			reviews:function(){
				return Ajax.get(courseData.getContextPath()+"/rest/tutors/individual/reviews/"+orgpersonid,undefined,"json","application/json");
			},
			message:function(obj){
				return courseData.sendMessage(obj);
			}
	};

	var model = {
			personalinfo:function(){
				var list = util.personalinfo(controller.personalinfo()); 
				return list;
			},
			courses:function(){
				return controller.courses();
			},
			reviews:function(){
				return controller.reviews();
			},
			message:function(){
				var obj = objects.message();
				return controller.message(obj);				
			}
	};

	var objects = {
			message : function(){
				return obj = {
						toOrgpersonid : orgpersonid,
						messagedescription : $("#messagetosend").val().trim()
				};
			}
	};	
	
	var view = {
			personalinfo:function(){
				var list = model.personalinfo();
				renderTemplate("#personalinfotmpl", list, "#personalinfotable");
				$('.messageauthor').click(function(){
					if(userstatus=="guest"){
						$("#authorprofilemessagemodal").modal("show");
						$('.authorprofilemessage').text("You must be logged in to send message to author");
					}else{
						view.message();
					}
				});
				
				$('.authprofilelink:first').addClass('active');
				view.courses();				
				
				$('.authprofilelink a[at=courses]').click(function(){
					$('.authprofilelink').removeClass('active');
					$('.authprofilelink:first').addClass('active');
					view.courses();	
				});
				
				$('.authprofilelink a[at=review]').click(function(){
					$('.authprofilelink').removeClass('active');
					$('.authprofilelink:last').addClass('active');
					view.reviews();	
				});
				
				
			},
			message:function(){
				$("#sendmsgmodal").modal("show");
				$('#sendtouser').show();
				$('textarea').keyup(function(){
					el = $(this);
					if(el.val().length >= 201){
						el.val( el.val().substr(0, 200) );
					}
				});
				$('#sendtouser').click(function(){
					var textareaval = $("#messagetosend").val().trim();
					if(textareaval.length!=0){
						$('.descnullalert').hide();
						var successmsg = model.message();
						if(successmsg!="false"){
							$("#messagetosend").val("");
							$('#sendtouser').hide();
							$('.descnullalert').hide();
							$('.msgsentalert').show();
							$('.msgsentalert').text("Message Successfully Sent.");
							setTimeout(function(){ 
								$(".msgsentalert").hide();
								$("#sendmsgmodal").modal("hide");
							}, 3000);
						}else{
							$('#sendtouser').show();
							$('.msgsentalert').hide();
							$('.descnullalert').show();
							$('.descnullalert').text("Sending Failed. Try After Sometime");
						}
					}else{
						$('#sendtouser').show();
						$('.descnullalert').show();
						$('.msgsentalert').hide();
						$('.descnullalert').text("Please Enter a Message");
					}
				});
			},
			courses:function(){
				var list = model.courses();
				renderTemplate("#authorcoursestmpl", list, "#authorprofileholder");
				$('.ratediv').each(function(){
					var rating=$(this).attr('data-score');
					$(this).raty({ readOnly: true,score: rating, 
						starHalf      : courseData.getContextPath()+"/assets/app/images/star/star-half.png",
						starOff       : courseData.getContextPath()+"/assets/app/images/star/star-off.png", 
						starOn        : courseData.getContextPath()+"/assets/app/images/star/star-on.png", 
					});
				});
				$('.icon_price').each(function(){
					var price=$(this).attr('price');
					var priceicon = $(this).attr('priceicon');
					if(price=="Free"){
						$(this).html("Free");
					}else if(price==""){
						$(this).html("");
					}else{
						$(this).html("<i class='fa "+priceicon+"'></i>"+price);
					}
				});
				$('.shorten').each(function(){
					shortenCharcters(this,700);
				});
				applypagination($('.pagapplycourses'),5);
				$(".viewcoursedetailslink").click(function(){
					if(userstatus=="guest"){
						view.guestRedirect($(this).attr('courseid'));
					}else{
						view.userRedirect($(this).attr('courseid'));
					}
				});				
			},
			reviews:function(){
				var list = model.reviews();
				Handlebars.registerHelper('ratingdate', function(ratingdate) { 
					var d = new Date(parseInt(ratingdate));
					var date =d.toLocaleString();
					return date;
				});
				renderTemplate("#authorreviewstmpl", list, "#authorprofileholder");
				applypagination($('.pagapplyreviews'),10);
				$('.ratediv').each(function(){
					var rating=$(this).attr('data-score');
					$(this).raty({ readOnly: true,score: rating, 
						starHalf      : courseData.getContextPath()+"/assets/app/images/star/star-half.png",
						starOff       : courseData.getContextPath()+"/assets/app/images/star/star-off.png", 
						starOn        : courseData.getContextPath()+"/assets/app/images/star/star-on.png", 
					});
				});
			},
			guestRedirect:function(courseid){
				$('[name=courseid]').val(courseid);
				$('[name=authorprofileform]').attr('action','preview');
				$('[name=authorprofileform]').submit();	
			},
			userRedirect:function(courseid){
				var courseidobj={};
				courseidobj.courseid=courseid;
				var enrolledValue=courseData.getEnrolledBoolean(courseidobj);
				if(enrolledValue){
					var status=enrolledValue[0].courseenrollmentstatus;			
					switch(status){
					case "A":
						$('[name=courseenrollmentid]').val(enrolledValue[0].courseenrollmentid);
						$('[name=authorprofileform]').attr('action','previewcourse');
						$('[name=authorprofileform]').submit();	
						break;
					case "C":
						$('[name=courseenrollmentid]').val(enrolledValue[0].courseenrollmentid);
						$('[name=authorprofileform]').attr('action','previewcourse');
						$('[name=authorprofileform]').submit();	
						break;
					case "W":
						$('[name=courseid]').val(courseid);
						$('[name=wishlisted]').val("wishListed");
						$('[name=courseenrollmentid]').val(enrolledValue[0].courseenrollmentid);
						$('[name=authorprofileform]').attr('action','previewuser');
						$('[name=authorprofileform]').submit();	
						break;
					case "B":
						$("#authorprofilemessagemodal").modal("show");
						$('.authorprofilemessage').text("You have been blocked from the course. Please contact Administrator");
						break;
					case "P":
						$("#authorprofilemessagemodal").modal("show");
						$('.authorprofilemessage').text("Your subscription for the course is waiting for approval. Please wait");
						break;					
					}
				}else{
					$('[name=courseid]').val(courseid);
					$('[name=authorprofileform]').attr('action','previewuser');
					$('[name=authorprofileform]').submit();	
				}
			}
			
	};

	var util = {
			personalinfo:function(list){
				var persons = [];
				var personids =_.uniq(_.pluck(list,'personid'));
				_.each(personids,function(personid){
					var personidarray = _.where(list,{personid:personid});
					var person = _.pick(personidarray[0],'personphoto','aboutauthor',
							'number','userid','firstName','lastName','personid','designation');
					var urls = _.pluck(personidarray,'url');
					if(urls[0]!=null){
						_.each(urls,function(url){
							if(_.contains(url.split('.'), 'twitter')){
								person["twitterurl"] = url;
							}else if(_.contains(url.split('.'), 'facebook')){
								person["facebookurl"] = url;
							}else if(_.contains(url.split('.'), 'linkedin')){
								person["linkedinurl"] = url;
							}else{
								person["websiteurl"] = url;
							}
						});
					}
					persons.push(person);
				});				
				persons = _.sortBy(persons, function(obj){ return obj.ratings *-1;});
				return persons;
			},
	};

	return {
		init:function(){
			initialize();	
		}
	};	

	function initialize(){
		view.personalinfo();
	}

})();