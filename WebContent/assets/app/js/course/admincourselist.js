$(document).ready(function(){
	$("#admincourselist").addClass('hover').addClass('active');
	var expiryDate=courseData.getExpiryDate();
	if(courseData.ensureRequestLimit()=="OK"&&expiryDate!="INTERNAL_SERVER_ERROR"?(!Array.isArray(expiryDate)||$('#origin').val()=="tour"):false){

		$('.uploadextfile').click(function(){
			$('#emaildiv').hide();
			email.uploadxlEmail();
		});
		$("#messagedescription").keyup(function(){
			el = $(this);
			if(el.val().length >= 501){
				el.val( el.val().substr(0, 500) );
			} else {
				$('.mesdesccharremaining').text(500-el.val().length);
			}
		});
		$('.leftsideli').click(function (e) {
			//save the latest tab; use cookies if you like 'em better:
			sessionStorage.setItem('lastTab', $(e.target).attr('class'));
		});

		$('input.cleanup').blur(function() {
			var value = $.trim( $(this).val() );
			$(this).val( value );
		});
		$('textarea.cleanup').blur(function() {
			var value = $.trim( $(this).val() );
			$(this).val( value );
		}); 

		adminCourses.getFirstLoadItems();
		$('.coursepartsbtn').click(function(event){
			if($(this).attr('b')==="tour"){
				event.preventDefault();	
			}else{
				$('.leftsideli').removeClass('active');
				$(this).addClass('active');
				courseParts().init();	
			}		
		});
		/*$.validator.addMethod("isDuplicate", function(value, element) {
			var result=courseData.checkDuplicateCourse(value);
			if(result=="false"){
				return true;
			}
			return false;
		},"Duplicate course");*/

		/* Create Course */	
		$('#createcoursesubmit').click(function(){
			var txt = ($('[name=coursetitle]').val());
			if($.trim($("[name=coursetitle]").val()=="" && $("[name=coursedescription]").val()=="")){
				if($.trim($("[name=coursetitle]").val())==""){
					$('#coursetitleerror').show();
					$('#coursetitleerror').text('Enter course name');
					$('[name=coursetitle]').focus();
				}
				else if(txt.charAt(0)==' '){
					$('#coursetitleerror').show();
					$('#coursetitleerror').text("You can't leave this empty.");			     		     
				}		 
				else if($.trim($('#coursedescid').val())==""){
					$('#coursedecerror').show();
					$('#coursedecerror').text('Enter course description');
					$('[name=coursedescription]').focus();
				}
				else
				{			
					var txt = ($('[name=coursetitle]').val());
					inputsHaveDuplicateValues(txt);
					if($.trim($("[name=coursetitle]").val())!="" && ($.trim($("[name=coursedescription]").val())!="")){	
						if(confirm("Are you sure you want to create this course ?")){
							$('[name=coursecreationmodalform]').attr('method','post');
							$('[name=coursecreationmodalform]').attr('action','newCourseCreate');
							$('[name=coursecreationmodalform]').submit();
							$('[name=coursecreationmodalform]')[0].reset();
						}
					}

					$('#coursetitleerror').hide();
					$('#coursedecerror').hide();
//					$('[name=coursecreationmodalform]')[0].reset();
				}
			}
			$('[name=coursetitle]').on("focusout keyup",function(){	
				$('#coursetitleerror').hide();				
			});
			$( '[name=coursedescription]').on("focusout keyup",function( event ) {			
				$('#coursedecerror').hide();				
			});
		});		
		$('.managecoursebtn').click(function(){
			self.manageCourse($(this).attr('courseid'));
		});

		function inputsHaveDuplicateValues(value) {
			var result=courseData.checkDuplicateCourse(value);
			if(result=="true"){	
				$('#courseduplicateerror').show();
				$('#courseduplicateerror').text('Course Title already exist');				
				$('[name=coursetitle]').focus();
				$('[name=coursetitle]').val("");
			}
			else{
				$('#courseduplicateerror').hide();
			}

		}

		if($('.linkattr').attr('link').trim()=="null"){		

			if(sessionStorage.getItem('lastTab')){
				var lastTab = sessionStorage.getItem('lastTab');
				if(lastTab.search("publishedcoursesbtn")>=0){
					switchtab(1);
				}
				else if(lastTab.search("draftcoursebtn")>=0){
					switchtab(2);
				}
				else if(lastTab.search("trashedcoursebtn")>=0){
					switchtab(3);
				}
				else if(lastTab.search("publiedcoursebtn")>=0){
					switchtab(4);
				}
				else if(lastTab.search("coursepartsbtn")>=0){
					switchtab(5);
				}
				else if(lastTab.search("myearnings")>=0){
					switchtab(6);
				}
			}
			if(sessionStorage.getItem('coursecategoryid')&&sessionStorage.getItem('lastTab').search("courselinktodescbtn")>=0){
				var coursecategoryid = sessionStorage.getItem('coursecategoryid');
				$('.courselinktodescbtn').eq($('li[coursecategoryid='+coursecategoryid+']').index()-1).trigger('click');
			}
		}
	}	
	else{
		var live=false;
		var course=false;
		for(var i=0;i<expiryDate.length;i++){
			if(expiryDate[i].royaltytype=="live"){
				live=true;
			}
			if(expiryDate[i].royaltytype=="course"){
				course=true;
			}
		}
		if(live&&!course){
			alert("Please configure Request Limit and Live Session Royalty for the next day");
		}
		else if(!live&&course){
			alert("Please configure Request Limit and Course Royalty for the next day");
		}
		else{
			alert("Please configure Request Limit, Course, Live Session Royalty for the next day");
		}
		window.location.href = $('#context').text()+"/app/royalty";
	}

});


adminCourses={
		getFirstLoadItems:function(){
			var self=this;
			self.createCourse();
			self.searchEvents();
			self.loadCounts();
			self.draftCourseItems();
			self.trashCourseItems();
			/*self.allCourseItems();*/
			self.publishCourseItems();
			self.publishedCourseItems();
			self.myearningsList();
			authorCourses().init();
			var selecteditem=$('.linkattr').attr('link');
			if(selecteditem=="ac"){
				$('.leftsideli').removeClass('active');
				$('.publiedcoursebtn').addClass('active');
				$('.publiedcoursebtn').trigger('click');
			}
			else if(selecteditem=="dc"){
				$('.leftsideli').removeClass('active');
				$('.draftcoursebtn').addClass('active');
				$('.draftcoursebtn').trigger('click');	
			}
			else if(selecteditem=="pc"){
				$('.leftsideli').removeClass('active');
				$('.publishedcoursesbtn').addClass('active');
				$('.publishedcoursesbtn').trigger('click');
			}
			else if(selecteditem=="tc"){
				$('.leftsideli').removeClass('active');
				$('.trashedcoursebtn').addClass('active');
				$('.trashedcoursebtn').trigger('click');
			}
		},
		searchEvents:function(){
			$('.searchbtn').click(function(e)  {
				$('.dropbox').slideDown(400);
				$('.searchtextbox').val('');
			});
			$('.closebtn').click(function(){
				$('.dropbox').slideUp(400);
			});
			$('.searchkwrdbtn').click(function(e){
				var searchkeyword=$('.searchtextbox').val().trim();
				if(searchkeyword!=undefined && searchkeyword.length!=0){
					adminCourses.searchcourse(searchkeyword);		
				}else{
					e.preventDefault();
				}
			});
		},
		searchcourse:function(keyword){
			$('[name=keyword]').val(keyword);				
			$('[name=browseuserform]').attr('action','searchresult');
			$('[name=browseuserform]').submit();
		},
		createCourse:function(){
			$(".createcoursebtn").click(function(){
				if($(this).attr('b')==="tour"){
					event.preventDefault();	
				}else{
					if($('.orgstatus').val()=="A"){
						var getmaxcourseuserlist =courseData.getmaxcourseuser();
						if(getmaxcourseuserlist!="INTERNAL_SERVER_ERROR"){
							var maxcourse = getmaxcourseuserlist[0].maxcourse;
							var createcourse = getmaxcourseuserlist.length;
							if(maxcourse>createcourse){
								$('#coursecreationmodal').modal('show');
							}
							else{
								$('.maxcourseuser').show();
								$('.maxcoursecount').text(maxcourse);
								setTimeout(function(){
									$('.maxcourseuser').hide();										
								},4000);
							}
						}
						else{
							$('#coursecreationmodal').modal('show');
						}
					}
					else{
						$('#coursecreationmodal').modal('show');
					}	
				}	

			});
			$('.orgplanlink').click(function(){
				var context = $('#context').text();
				$(this).attr("href",context+"/app/myorganization");
				sessionStorage.setItem('lastTab', 'leftsideli border whitebg pad10 hover paiduserbtn subscrips active');
			});
		},
		loadCounts:function(){
			var courseidobj={};
			courseidobj.coursestatus="D";
			var draftcourseslist=courseData.getOtherCourses(courseidobj);
			if(draftcourseslist!=undefined){
				var draftcoursescount=draftcourseslist.length;
				$('.draftcoursecount').text(draftcoursescount);
			}
			else{
				$('.draftcoursecount').text("0");
			}
			var courseidobj={};
			courseidobj.coursestatus="T";
			var trashcourseslist=courseData.getOtherCourses(courseidobj);
			if(trashcourseslist!=undefined){
				var trashcoursescount=trashcourseslist.length;
				$('.trasedcoursecount').text(trashcoursescount);
			}
			else{
				$('.trasedcoursecount').text("0");
			}

			var courseobj={};
			courseobj.coursestatus="A";
			var approvedcourselist=courseData.getPublishedCourses(courseobj);
			if(approvedcourselist!=undefined){
				var approvedcoursescount=approvedcourselist.length;
				$('.approvedcoursecount').text(approvedcoursescount);
			}
			else{
				$('.approvedcoursecount').text("0");
			}
			var courseobj={};
			courseobj.coursestatus="P";
			var publishcourselist=ajaxhelperwithdata("loadAllPublishedCourses",courseobj);
			if(publishcourselist!=undefined && publishcourselist!="false"){
				var publishcoursecount=publishcourselist.length;
				$('.publishedcoursecount').text(publishcoursecount);
			} 
			else{
				$('.publishedcoursecount').text("0");
			}
		},
		publishCourseItems:function(){
			var self=this;
			$('.publiedcoursebtn').click(function(){
				if($(this).attr('b')==="tour"){
					event.preventDefault();	
				}else{
					$('.leftsideli').removeClass('active');
					$(this).addClass('active');
					var courseobj={};
					courseobj.coursestatus="A";
					self.publishCourseData(courseobj);	
					adminCourses.pagination();
				}				
			});	
		},
		myearningsList:function(){
			$('.myearnings').click(function(){				
				loadMyEarningCourses();
				adminCourses.pagination();
				$('.rightpanetitle').text("My Earnnigs");
			});
			function loadMyEarningCourses(){
				$('.rightpanetitle').text("My Earnings");
				$('.leftsideli').removeClass('active');
				$('.myearnings').addClass('active');
				var earingCoursesList=null;
				earingCoursesList=courseData.getMyEarningCourses();

				if(earingCoursesList!=undefined){				
					var list3 = _.map(earingCoursesList, function(num, key){ if(num.price!="Free" ){
						return num;
					}});
					var list = _.compact(list3);
					renderMyEarningCourses(list);	
				}
				else{ 
					var list3 = _.map(earingCoursesList, function(num, key){ if(num.price!="Free" ){
						return num;
					}});
					var list = _.compact(list3);
					renderTemplate("#myearningscoursestmpl",list,"#coursedescriptiontable");
					$('.modal-body').slimscroll({height: "475px"});   
				}
			};
			function renderMyEarningCourses(myEarningsCoursesList){
				renderTemplate("#myearningscoursestmpl",myEarningsCoursesList,"#coursedescriptiontable");
				$('.modal-body').slimscroll({height: "475px"});   
				$('.authorcoursestudents').click(function(){
					$("#authorcoursemodal").modal("show");
					$('.authorcoursestudents').parent().parent().removeClass('activecourse');
					$(this).parent().parent().addClass('activecourse');				
					loadauthorcoursestudents($(this).attr('courseid'),$(this).attr('coursetitle'),$(this).attr('price'));	
				});

			};
			function loadauthorcoursestudents(authorcourseid,coursetitle,price){
				var stcourseidobj={};
				stcourseidobj.authorcourseid=authorcourseid;
				var authroCourseStudents=courseData.loadAuthorCourseStudents(stcourseidobj);
				console.log(coursetitle);
				renderAuthorCourseStudents(authroCourseStudents,coursetitle,price);
			};
			function renderAuthorCourseStudents(authroCourseStudents,coursetitle,price){
				$("#coursetitlespan").text(coursetitle);
				if(authroCourseStudents==undefined){
					renderTemplate('#datanotfoundtmpl',authroCourseStudents,'#authorcoursestudentinfo');	
				}else{
					$('#warningtextdiv').hide();
					if(price=="Free"){
						renderTemplate('#authorfreecoursestudentsinfotmpl',authroCourseStudents,'#authorcoursestudentinfo');
					}
					else{
						renderTemplate('#authorcoursestudentsinfotmpl',authroCourseStudents,'#authorcoursestudentinfo');	
					}	
				}
			}
		},
		publishCourseData:function(courseobj){

			var self=this;
			var publishcourses=courseData.getPublishedCourses(courseobj);
			self.renderPublishCourses(publishcourses);
		},
		renderPublishCourses:function(publishcourses){
			var self=this;
			renderTemplate("#approvedcoursestmpl",publishcourses,"#coursedescriptiontable");
			$('.authorinfobtn').click(function(){
				$('#authorInfoModal').modal('show');
				$('.autname').text($(this).attr('auname'));
				$('.autemail').text($(this).attr('emailid'));				
			});
			$('.managecourse').click(function()	{
				var courseid=$(this).attr('courseid');	
				$('[name=courseid]').val(courseid);
				$('[name=coursemanage]').attr('method','post');
				$('[name=coursemanage]').attr('action','managecourse');
				$('[name=coursemanage]').submit();
			});

			$('.inviteuser').click(function (){
				if($("#orgstatus").val()=="A"){
					var userlist = courseData.getmaxuser();
					var maxuser = userlist[0].maxusers;
					//var planid = userlist[0].planid;
					//	var userscountlist=courseData.getOrganizationUsers(planid);
					if(userlist!="INTERNAL_SERVER_ERROR"){
						//if(userscountlist!="INTERNAL_SERVER_ERROR"){
						var allusercount=userlist.length;
						if(maxuser>allusercount){
							$("[name=courseid]").val($(this).attr('courseid'));
							$("#inviteusersmodal").modal('show');
							$('#errormessage').hide();
							$('.sendingmess').hide();
						}
						else{
							$('.maxuser').show();
							$('.maxcoursecount').text(maxuser);
							setTimeout(function(){
								$('.maxuser').hide();										
							},4000);
						}
						//}
						/*else{
							$("[name=courseid]").val($(this).attr('courseid'));
							$("#inviteusersmodal").modal('show');
							$('#errormessage').hide();
							$('.sendingmess').hide();
						}*/
					}
					else{
						$("[name=courseid]").val($(this).attr('courseid'));
						$("#inviteusersmodal").modal('show');
						$('#errormessage').hide();
						$('.sendingmess').hide();
					}
				}
				else{
					$("[name=courseid]").val($(this).attr('courseid'));
					$("#inviteusersmodal").modal('show');
					$('#errormessage').hide();
					$('.sendingmess').hide();
				}
				//self.manageInvite($(this).attr('courseid'));
			});
			self.manageInvite();
		},
		draftCourseItems:function(){
			$('.draftcoursebtn').addClass('active');
			renderDraftCourses();
			$('.draftcoursebtn').click(function(event){
				if($(this).attr('b')==="tour"){
					event.preventDefault();	
				}else{
					renderDraftCourses();	
					adminCourses.pagination();
				}	

			});	
			function renderDraftCourses(){
				$('.leftsideli').removeClass('active');
				$('.draftcoursebtn').addClass('active');
				var courseidobj={};
				courseidobj.coursestatus="D";
				adminCourses.draftCoursesData(courseidobj);	
			}
		},
		publishedCourseItems:function(){
			var self=this;
			$('.publishedcoursesbtn').click(function(){
				if($(this).attr('b')==="tour"){
					event.preventDefault();	
				}else{
					$('.leftsideli').removeClass('active');
					$(this).addClass('active');
					var courseidobj={};
					courseidobj.coursestatus="P";
					self.publishedCoursesData(courseidobj);	
					adminCourses.pagination();
				}			
			});	
		},
		manageInvite:function(){
			var self=this;
			$('.sendinvitation').click(function(){
				var courseid = $("[name=courseid]").val();
				$('#errormessage').show();
				var obj={};
				var emails=$('.emailuser').val();
				var messagedescription=$('#messagedescription').val();
				var orgemail = self.checkuseremails($('.emailuser').val());
				if(orgemail=="true"){
					obj.duplicatemessage=" Invalid and your own email id will be automatically removed from the list.";
					renderTemplate("#duplicatemessagetmpl",obj,"#errormessage");
					setTimeout(function(){
						var emailuser = $("#emailuser").val();
						var useremail = $('.orgemail').val();
						var emailusersplit = emailuser.split(',');
						_.each(emailuser,function(email){
							var dat = _.contains(emailusersplit,useremail);
							if(dat==true){
								var removemail = emailusersplit.indexOf(email);
								emailusersplit.splice(removemail,1);
								$("#emailuser").val(emailusersplit);
								$("#errormessage").hide();
							}
						});
					}, 2000);
				}
				else{
					if(emails.length!=0 && messagedescription!=0){
						if(self.validateEmails(emails)){				    
							var user = {};				
							user.email =emails.trim();	
							user.courseid=courseid;
							user.message = encodeURIComponent(messagedescription);
							var noEmail=self.checkemails(emails,courseid);
							if(noEmail.length=='0'){	
								$('#errormessage').hide();
								if($("#orgstatus").val()=="A"){
									orgplanobj = {};
									orgplanobj.orgplanstatus='A';
									var orgplanmaxuser = courseData.getmaxuser();
									var orgplanmaxusers = courseData.orgplanlist(orgplanobj);
									if(orgplanmaxuser=="INTERNAL_SERVER_ERROR"){
										var maxusers=orgplanmaxusers[0].maxusers;
										var emaluser = $("#emailuser").val();
										var emailsplit =emaluser.split(",");
										var emailsplitlength = emailsplit.length;
										if(maxusers>=emailsplitlength){
											if(confirm('Are you sure you want to invite this user')){
												self.asyncajax("authorinvite",user);
											}
										}
										else{
											$('.inviteuseralert').show();
											$('.inviteuseralert').text('Your current plan does not allow creation of users more than '+maxusers+'.Already invited users is 0');
											setTimeout(function(){
												$('.inviteuseralert').hide();
											}, 2000);
										}
									}
									else{
										var maxusers=orgplanmaxuser[0].maxusers;
										var alluserdetails = orgplanmaxuser.length;
										var userdetail =alluserdetails;
										if(maxusers>userdetail){
											var emaluser = $("#emailuser").val();
											var emailsplit =emaluser.split(",");
											var emailsplitlength = emailsplit.length+userdetail;
											if(maxusers>=emailsplitlength){
												if(confirm("Are you sure you want to invite this course ?")){
													self.asyncajax("authorinvite",user);
												}
											}
											else{
												$('.inviteuseralert').show();
												$('.inviteuseralert').text('Your current plan does not allow creation of users more than '+maxusers+'.Already invited users is '+userdetail);
												setTimeout(function(){
													$('.inviteuseralert').hide();
												}, 3000);
											}
										}
										else{
											$('.inviteuseralert').show();
											$('.inviteuseralert').text('Your current plan does not allow creation of users more than '+maxusers+'.Already invited users is '+userdetail);
											setTimeout(function(){
												$('.inviteuseralert').hide();
											}, 3000);
										}
									}
								}
								else{
									if(confirm('Are you sure you want to invite this user')){
										self.asyncajax("authorinvite",user);
									}
								}
								/*if(confirm("Are you sure you want to invite this course ?")){
							$('#errormessage').hide();
							self.asyncajax("authorinvite",user);
							}*/
							}else{
								obj.duplicatemessage="Already invited users will be automatically removed, Please wait...";
								renderTemplate("#duplicatemessagetmpl",obj,"#errormessage");
								setTimeout(function(){
									$('.duplicate').fadeOut();		
									var emaills=emails.split(',');
									var emaillistYetToSend=_.difference(emaills,_.pluck(noEmail, 'email'));
									if(emaillistYetToSend.length>0){
										$('.emailuser').val(emaillistYetToSend.join());
									}
									else if(emaillistYetToSend.length>0){
										$('.emailuser').val(emaillistYetToSend[0]);
									}
									else{
										$('.emailuser').val("");
									}
								},3000);							
							}																		
						}else{
							obj.errormessage="Please Check Your Email Id";
							renderTemplate("#errormesstmpl",obj,"#errormessage");
						}
					}else{
						obj.errorvalidation="Please Fill the Required Fields";
						renderTemplate("#validationtmpl",obj,"#errormessage");
					}
				}
			});	
		},	
		startProgress:function(){
			$('.sendinvitation').hide();
			$('.sendingmess').show();
		},
		endProgress:function(){
			$('.sendingmess').hide();		
			$('.sendinvitation').show();
			$('.emailuser').val('');
			$('#messagedescription').val('');
			$('#inviteusersmodal').modal('hide');
			$("#assignUserMsgModal").modal('show');
		},
		validateEmails:function(emails)
		{
			var self=this;
			var i;
			var res = emails.split(",");
			for(i = 0; i < res.length; i++)
				if(!self.validateEmail(res[i])) return false;
			return true;
		},
		validateEmail:function (email) { 
			var emaildata=email.trim();
			var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			return re.test(emaildata);
		},
		checkemails:function(emails,courseid){
			var staticEmailArray=[];
			var emaildata=emails.split(',');
			if(emaildata.length!=0){
				$.each(emaildata, function(index, value) {
					var response="";
					var obj={};
					obj.email=value;
					obj.courseid=courseid;
					response=courseData.checkAuthourData(obj);
					if(response=="failure"){
						obj.status="failure";
						staticEmailArray.push(obj);	
					}					 			 
				});
				return staticEmailArray;
			}
		},
		checkuseremails:function(emails){
			var response="";
			var emaildata=emails.split(',');
			var data = $('.orgemail').val();
			if(emaildata.length!=0){
				$.each(emaildata, function(index, value) {
					if(value==data){
						response="true";
					}
				});
				return response;
			}
		},
		asyncajax:function(url,data){
			var self=this;
			if(_.isString(url))	{
				if(_.isObject(data) && !_.isArray(data)){
					$.ajax({
						type : 'GET',
						url : url,			
						data:data,
						beforeSend:self.startProgress(),							
						success : function(data, textStatus, jqXHR){
							if(data=="success"){
								var obj={};
								$('.emailuser').val("");	
								$('#messagedescription').val("");
								obj.successmessage="Invitation has been sent";							
								self.endProgress();
								$('#assignUserMsgModal #msg').text("User is invited successfully");
							}
							else{
								self.endProgress();
								$('#assignUserMsgModal #msg').text("Problem in sending email, Please try after sometime..");
							}
						},
						error : function(jqXHR, textStatus, errorThrown){
							self.endProgress();
							$('#assignUserMsgModal #msg').text("Problem in sending email, Please try after sometime..");
							console.log("Couldn't connect to server " + textStatus);
						}
					});	
				}
				else{
					console.log("data is not an object"+data);	
				}
			}
			else{
				console.log("url is not a String"+url);	
			}
			return status;
		},
		draftCoursesData:function(courseidobj){
			var self=this;
			var draftcourses=courseData.getOtherCourses(courseidobj);
			self.renderDraftCourses(draftcourses);
		},
		renderDraftCourses:function(draftcourses){
			var self=this;
			renderTemplate("#draftcoursesdesctmpl",draftcourses,"#coursedescriptiontable");
			$('.mcoursebtn').click(function(){
				if(confirm("Are you sure you want to manage this course ?")){
					var courseid=$(this).attr('courseid');	
					$('[name=courseid]').val(courseid);
					$('[name=coursemanage]').attr('method','post');
					$('[name=coursemanage]').attr('action','managecourse');
					$('[name=coursemanage]').submit();
				}
			});
			$('.deletecourse').click(function(){
				var checkstr =  confirm('Are you sure you want to trash this Course');
				if(checkstr == true)	{
					var trashcourseobj={};
					trashcourseobj.courseid=$(this).attr('courseid');
					trashcourseobj.coursestatus="T";
					self.deletecourseData(trashcourseobj);
				}				
			});
		},
		publishedCoursesData:function(courseidobj){
			var self=this;
			var publishedcourses=ajaxhelperwithdata("loadAllPublishedCourses",courseidobj);
			self.renderPublishedCourses(publishedcourses);
		},
		renderPublishedCourses:function(publishedcourses){
			var list; 
			publishedcourses =="false"?list=null : list=publishedcourses;
			renderTemplate("#publishedcoursestmpl",list,"#coursedescriptiontable");
			$('.authorinfobtn').click(function(){
				$('#authorInfoModal').modal('show');
				$('.autname').text($(this).attr('auname'));
				$('.autemail').text($(this).attr('emailid'));				
			});
			$('.contentapprovebtn').click(function(){
				$('[name=courseid]').val($(this).attr('courseid'));
				$('[name=contentapproveform]').attr('action','contentPublish');
				$('[name=contentapproveform]').submit();
			});
		},
		deletecourseData:function(trashcourseobj){
			var self=this;
			var deletedCourse=courseData.updateCourseStatus(trashcourseobj);
			if(deletedCourse==1){	
				renderTemplate('#alerttrashtmpl', "", '#alertdiv');
				$('#alertdiv').show();
				setTimeout(function(){
					$('#alertdiv').hide();					
				},1500);
				var courseidobj={};
				courseidobj.coursestatus="D";
				self.draftCoursesData(courseidobj);
				self.loadCounts();
			}
		},
		trashCourseItems:function(){
			var self=this;
			$('.trashedcoursebtn').click(function()	{
				if($(this).attr('b')==="tour"){
					event.preventDefault();	
				}else{
					$('.leftsideli').removeClass('active');
					$(this).addClass('active');
					var courseidobj={};
					courseidobj.coursestatus="T";
					self.trashCoursesData(courseidobj);
					adminCourses.pagination();
				}	
			});	
		},
		trashCoursesData:function(courseidobj){
			var self=this;
			var trashedCourses=courseData.getOtherCourses(courseidobj);
			self.renderTrashedCourses(trashedCourses);
		},
		renderTrashedCourses:function(trashedCourses){
			var self=this;
			renderTemplate("#trashedcoursesdesctmpl",trashedCourses,"#coursedescriptiontable");
			$('.undodeletecourse').click(function(){
				var checkstr =  confirm('Are you sure you want to move this Course to Draft');
				if(checkstr == true)	{
					var trashcourseobj={};
					trashcourseobj.courseid=$(this).attr('courseid');
					trashcourseobj.coursestatus="D";
					self.backtoDraftData(trashcourseobj);
				}	
			});
		},
		backtoDraftData:function(trashcourseobj){
			var self=this;
			var deletedCourse=courseData.updateCourseStatus(trashcourseobj);
			if(deletedCourse==1){		
				renderTemplate('#alertrestoretmpl', "", '#alertdiv');
				$('#alertdiv').show();
				setTimeout(function(){
					$('#alertdiv').hide();					
				},1500);
				var courseidobj={};
				courseidobj.coursestatus="T";
				self.trashCoursesData(courseidobj);
				self.loadCounts();
			}
		},
		allCourseItems:function(){
			var self=this;
			self.loadCategories();
			var categoryidobj={};
			$('.courselinktodescbtn:first').addClass('active');
			categoryidobj.coursecategoryid=$('.courselinktodescbtn:first').attr('coursecategoryid');
			categoryidobj.coursecategorystatus="A";
			self.categoryCoursesData(categoryidobj);
		},
		categoryCoursesData:function(categoryidobj){
			var self=this;
			self.loadCategorywiseCourses(categoryidobj);
		},
		loadCategories:function(){
			var self=this;
			var categorystatusobj={};
			categorystatusobj.coursecategorystatus="A";
			self.categoriesData(categorystatusobj);
		},
		categoriesData:function(categorystatusobj){
			var self=this;
			var coursecategories=courseData.getCourseCategories(categorystatusobj);
			self.renderCategories(coursecategories);
		},
		renderCategories:function(coursecategories){
			var self=this;
			renderTemplate("#coursestmpl",coursecategories,"#coursestable");
			$('.courselinktodescbtn').click(function(){
				sessionStorage.setItem('lastTab', 'courselinktodescbtn');
				sessionStorage.setItem('coursecategoryid', $(this).attr('coursecategoryid'));
				$('.leftsideli').removeClass('active');
				$(this).addClass('active');
				var categoryidobj={};	
				categoryidobj.coursecategoryid=$(this).attr('coursecategoryid');
				categoryidobj.coursecategorystatus="A";
				self.categorywiseCoursesData(categoryidobj);
				window.scrollTo(0,0);
			});
		},
		categorywiseCoursesData:function(categoryidobj){
			var self=this;
			self.loadCategorywiseCourses(categoryidobj);
		},
		loadCategorywiseCourses:function(categoryidobj){
			var self=this;
			var categoryCourses=courseData.getCaterorywiseCourses(categoryidobj);
			self.renderCategorywiseCourses(categoryCourses);	
		},

		renderCategorywiseCourses:function(categoryCourses){
			renderTemplate("#coursesdesctmpl",categoryCourses,"#coursedescriptiontable");
			$('.catname').text($('.courselinktodescbtn.active').attr('coursecategoryname'));
			$('.managecourse').click(function()	{
				var courseid=$(this).attr('courseid');	
				$('[name=courseid]').val(courseid);
				$('[name=coursemanage]').attr('method','post');
				$('[name=coursemanage]').attr('action','managecourse');
				$('[name=coursemanage]').submit();
			});
			$('.assign').click(function(){
				var courseid=$(this).attr('courseid');
				var coursetitle=$(this).attr('coursetitle');
				data={courseid:courseid};
				assignUserslist(data,coursetitle,courseid);		
			}); 	
			$('.usercount').click(function(){
				var courseid=$(this).attr('courseid');
				var coursetitle=$(this).attr('coursetitle'); 
				data={courseid:courseid};
				registeredUserslist(data,coursetitle,courseid);			
			});		
		},
		pagination:function(){
			/**
			 * Pagination starts here
			 *
			 * 
			 * 
			 */

			spanOffset=100;			
			var totalPages=$('.paginationtab tbody tr').length;
			spanCount=0;
			if(totalPages>spanOffset){
				spanCount=totalPages/spanOffset;
				span=1;
				currentSpan=spanCount-span;
				$('.pagination').text("");
				$('.pagination').append('<input id="previouspages" rel="'+span+'" class="page hover" type="button" value="<<">');
				$("#previouspages").addClass('active');
				$("#previouspages").prop("disabled",true);
			}
			else{
				span=1;
				spanOffset=totalPages;
			}
			renderPageNavigation(span,spanOffset);			       


			function renderPageNavigation(span,spanOffset){

				//$('#alluserstable').after('<div class="navbar" id="nav"></div>');
				var currentPage=0;
				var rowsShown = 10;
				var rowsTotal=0;
				if(span>spanCount){
					rowsTotal=totalPages;  
				}
				else{
					rowsTotal = span*spanOffset;
				}
				var pageNum=0;
				var i=(span-1)*spanOffset/10;
				if(rowsTotal>rowsShown)
				{
					var numPages = Math.ceil(rowsTotal/rowsShown);
					//$('.pagination').text("");
					$('.pagination').append('<input id="previous" rel="'+currentPage+'" class="page hover" type="button" value="<">');

					for(i;i < numPages;i++) {
						pageNum = i + 1;
						$('.pagination').append('&nbsp;<a href="javascript:void(0)" rel="'+i+'"class="page hover">'+pageNum+'</a> ');
					}
					$('.pagination').append('&nbsp;<input id="next" rel="'+currentPage+'" class="page hover" type="button" value=">">');
					$('.paginationtab tbody tr').hide();
					$('.paginationtab tbody tr').slice(0, rowsShown).show();
					$('.pagination a:first').addClass('active');
					$('.pagination a').bind('click', function(){
						if($(this).text()==pageNum&&$('#nextpages').is(':disabled')){
							$('#next').addClass('active');
							$("#next").prop("disabled","disabled");						    		  
						}
						else{
							$("#next").removeClass('active');
							$("#next").removeAttr("disabled");
						}  
						if($(this).text()==1){
							$("#previous").addClass('active');
							$("#previous").prop("disabled","disabled");
						}
						else{
							$("#previous").removeClass('active');
							$("#previous").removeAttr("disabled");
						}  
						//$("#previous").removeAttr("disabled");

						$('.pagination a').removeClass('active');
						$(this).addClass('active');
						var currPage = $(this).attr('rel');
						var startItem = currPage * rowsShown;
						var endItem = startItem + rowsShown;
						$("#previous").attr('rel',currPage);
						$("#next").attr('rel',currPage);
						$('.paginationtab tbody tr').css('opacity','0.0').hide().slice(startItem, endItem).
						css('display','table-row').animate({opacity:1}, 300);
						if($('#nextpages').length==0&& $('.pagination a:last').hasClass('active')){
							$('#next').addClass('active');
							$('#next').prop('disabled',true);
						}

					});


					if($("#previous").attr('rel')==0){
						$("#previous").addClass('active');
						$("#previous").prop("disabled",true);
					}				      
					if(totalPages>spanOffset){
						$('.pagination').append('&nbsp;<input id="nextpages" rel="'+span+'" class="page hover" type="button" value=">>">');
					}
					if(span>=spanCount){   
						$("#nextpages").addClass('active');
						$("#nextpages").prop("disabled",true);
					}  
					if(span<=1){
						$("#previouspages").addClass('active');
						$("#previouspages").prop("disabled",true);
					}						   						      
					/**
					 * 
					 * Next span button function 
					 */


					$('#nextpages').click(function(){

						++span;
						$('.pagination').text("");
						$('.pagination').append('<input id="previouspages" rel="'+span+'" class="page hover" type="button" value="<<">');
						renderPageNavigation(span,spanOffset);
						$('.pagination a:first').trigger('click');	                        	                             
					});


					/**
					 * 
					 * previous span button function 
					 */


					$('#previouspages').click(function(){

						--span;			            	                        
						$('.pagination').text("");
						$('.pagination').append('<input id="previouspages" rel="'+span+'" class="page hover" type="button" value="<<">');
						renderPageNavigation(span,spanOffset);
						$('.pagination a:last').trigger('click');                	   	

					});

					/**
					 * 
					 * Next button function 
					 */

					$('#next').click(function () {					    	  

						if ((currPage * numPages) <= rowsTotal)
							currPage++;
						{   
							var currPage = $(this).attr('rel');
							$("a[rel='" + currPage + "']").removeClass('active');
							currPage=parseInt(currPage)+1;
							$("a[rel='" + currPage + "']").addClass('active');
							$("#previous").attr('rel',currPage);
							$("#next").attr('rel',currPage);
							console.log(currPage);
							var startItem = currPage * rowsShown;
							var endItem = startItem + rowsShown;
							$('.paginationtab tbody tr').css('opacity','0.0').hide().slice(startItem, endItem).
							css('display','table-row').animate({opacity:1}, 300);
						}

						if($("#previous").attr('rel')==1){
							$("#previous").removeClass("active");
							$("#previous").removeAttr("disabled");
						}
						if($('#nextpages').is(':enabled')){
							if(currPage==(parseInt(numPages))){
								$('#nextpages').trigger('click');
							}
						}
						else if(!$('#nextpages').is(':enabled')){
							if(currPage==(parseInt(numPages)-1)){
								$(this).addClass('active');
								$(this).prop("disabled",true);
							}
						}
						else{
							$(this).removeClass('active');
							$(this).removeAttr("disabled");
						}
						if(!$('.pagination a:first').hasClass('active')){
							$("#previous").prop("disabled",false);
						}
					});

					/**
					 * 
					 * Previous button function 
					 */
					$('#previous').click(function () {

						if($(this).attr('rel')==1){		
							$(this).addClass('active');
							$(this).prop("disabled",true);					                		  
						}
						else if($('#previouspages').is(':enabled')&&$('.pagination a:first').hasClass('active')){
							$("#previouspages").trigger('click');
						}
						else{
							$(this).removeClass('active');
							$(this).removeAttr("disabled");
						}
						if($("#next").attr('rel')==(parseInt(numPages)-1)){
							$("#next").removeClass("active");
							$("#next").removeAttr("disabled");
						}
						else{
							$("#next").removeClass("active");
							$("#next").prop("disabled",false);
						}

						if (currPage > 1) currPage--;
						{
							var currPage = $(this).attr('rel');
							$("a[rel='" + currPage + "']").removeClass('active');
							currPage=parseInt(currPage)-1;
							$("a[rel='" + currPage + "']").addClass('active');
							$("#previous").attr('rel',currPage);
							$("#next").attr('rel',currPage);
							var startItem = currPage * rowsShown;
							var endItem = startItem + rowsShown;
							$('.paginationtab tbody tr').css('opacity','0.0').hide().slice(startItem, endItem).
							css('display','table-row').animate({opacity:1}, 300);
						}
						if($("#next").attr('rel')==(parseInt(numPages)-1)){
							$("#next").removeAttr("disabled");
						}					                	
					});
				}
				return "";
			}

			/**
			 * 
			 * Pagination function ends here
			 */
			$('.zoomin').slimScroll({
				height:"40px"
			});
		}
};
function assignusers(){
	$('.coursesubmit').click(function(){
		var personidval=[];
		var courseid=$('.coursehiddenid').text();
		$('.Selected').each(function(){
			personidval.push($(this).attr('personid'));			
		});
		var data={};
		data.personid=""+personidval;
		data.courseid=courseid;
		var getCourseUser=ajaxhelperwithdata("submitCoursesforUsers", data);
		if(getCourseUser=="SUCCESS") {
			$('#alertBox').show(); 
			$('#alertBox').removeClass("alert alert-success").addClass("alert alert-success"); 
			$('#msg').text("SUCCESS");
			setTimeout(function(){
				$('#alertBox').hide();										
			},1000);
			var coursetitle=$('.coursetitleuser').text();			
			data1={courseid:courseid,};
			assignUserslist(data1,coursetitle,courseid);	
		} else { 
			$('#alertBox').show(); 
			$('#alertBox').removeClass("alert alert-success").addClass("alert alert-danger"); 
			$('#msg').text("FAILURE");
			setTimeout(function(){
				$('#alertBox').hide();										
			},1000);
		} 		
	});	
}

function assignUserslist(data,coursetitle,courseid){
	var assignusers=ajaxhelperwithdata("loadCourseDetailsforUsers", data);
	var list={};
	list.assignusers=assignusers;
	if(list.assignusers==="false"){
		list.assignusers=false;
	}
	renderTemplate("#userlisttempl",list,"#userlist");	
	$('.coursesubmit').click(function(){
		var personidval=[];
		var getCourseUser={};
		if($('.Selected').length!=0){
			$('.Selected').each(function(){
				personidval.push($(this).attr('personid'));			
			});
			var data={};
			data.personid=""+personidval;
			data.courseid=courseid;
			getCourseUser=ajaxhelperwithdata("submitCoursesforUsers", data);
		}else{
			getCourseUser="NOTSELECTED";
		}
		if(getCourseUser=="SUCCESS") {
			$('#alertBox').show(); 
			$('#alertBox').removeClass("alert alert-danger").addClass("alert alert-success"); 
			$('#msg').text("Successfully Assigned");
			setTimeout(function(){
				$('#alertBox').hide();										
			},3000);
			data1={courseid:courseid,};
			assignUserslist(data1,coursetitle,courseid);	
		}else if(getCourseUser=="NOTSELECTED"){
			$('.assignbtn').stop().addClass('btn-danger').delay(500).queue(function(d) {
				$('.assignbtn').removeClass('btn-danger');
			});
			$('#alertBox').show(); 
			$('#alertBox').removeClass("alert alert-success").addClass("alert alert-danger"); 
			$('#msg').text("Please select the person from the list");
			setTimeout(function(){
				$('#alertBox').hide();			
			},3000);
		}
		else { 
			$('#alertBox').show(); 
			$('#alertBox').removeClass("alert alert-success").addClass("alert alert-danger"); 
			$('#msg').text("Sorry this person is not assigned");
			setTimeout(function(){
				$('#alertBox').hide();										
			},3000);
		} 		
	});	
	$('.coursetitleuser').html(coursetitle);
	selectDeselectFunction();
}

function registeredUserslist(data,coursetitle,courseid){
	var getCourseUser=ajaxhelperwithdata("getCourseUser", data);
	if(getCourseUser.length!==0){
		if(getCourseUser==="false"){
			getCourseUser=false;
		}
		renderTemplate("#usercourselisttempl",getCourseUser,"#usercourselist");	
		$('.coursetitleuser').html(coursetitle);
		$('.coursehiddenid').html(courseid);		
		$('.blockUserbtn').click(function(){
			var checkstr =  confirm('Are you sure you want to block this student');
			if(checkstr == true)	{
				var data={};
				data.courseenrollmentid=$(this).attr('courseenrollmentid');			
				var getCourseUser=ajaxhelperwithdata("removeCourseForUser", data);			
				if(getCourseUser=="SUCCESS") {
					$('#alertBlockBox').show(); 
					$('#alertBlockBox').removeClass("alert alert-success").addClass("alert alert-success"); 
					$('#blockmsg').text("This Person Removed from this course");	
					setTimeout(function(){
						$('#alertBlockBox').hide();										
					},1000);
					var blockdet={};
					blockdet.personid=$(this).attr('personid');
					blockdet.message=$(this).attr('message');
					var notifimsg=ajaxhelperwithdata("notifythisperson", blockdet); 

					var coursetitle=$('.coursetitleuser').text();			 
					var data1={};
					data1.courseid=courseid;				
					registeredUserslist(data1,coursetitle,courseid);				  
				} 
				else { 
					$('#alertBlockBox').show(); 
					$('#alertBlockBox').removeClass("alert alert-success").addClass("alert alert-danger"); 
					$('#blockmsg').text("Sorry this person can't be removed");
					setTimeout(function(){
						$('#alertBlockBox').hide();										
					},1000);
				} 			
			}
		});	 
	}
}

function selectDeselectFunction(){
	$('.selectbtn').click(function(){
		if($(this).text()=='Deselect All'){
			$('.assignbtn').text('Select');
			$('.assignbtn').removeClass('Selected');
			$('.assignbtn').removeClass('btn-danger');
			$(this).text('Select All');
			$(this).removeClass('btn-danger');
		}else{
			$(this).text('Deselect All');
			$('.assignbtn').text('Selected');
			$('.assignbtn').addClass('Selected');
			$('.assignbtn').addClass('btn-danger');
			$(this).addClass('btn-danger');
		}
	});
	$('.assignbtn').click(function(){
		if($(this).text()=='Selected'){
			$(this).text('Select');
			$(this).removeClass('Selected');
			$(this).removeClass('btn-danger');
		}else{
			$(this).text('Selected');
			$(this).addClass('Selected');
			$(this).addClass('btn-danger');
		}
	});
}

function switchtab(e){
	switch(e){
	case 1:{
		$('.publishedcoursesbtn').trigger('click');
		break;
	}
	case 2:{
		$('.draftcoursebtn').trigger('click');
		break;
	}
	case 3:{
		$('.trashedcoursebtn').trigger('click');
		break;
	}
	case 4:{
		$('.publiedcoursebtn').trigger('click');
		break;
	}
	case 5:{
		$('.coursepartsbtn').trigger('click');
		break;
	}	
	case 6:{
		$('.myearnings').trigger('click');
		break;
	}
	}
}

var authorCourses = (function(){
	var commonRestUrlForAuthor = courseData.getContextPath()+"/rest/admincourses/";
	var controller = {
			courses : function(status){
				return Ajax.get(commonRestUrlForAuthor+"courses/"+status,null,"json","application/json");
			},
			counts : function(){
				return Ajax.get(commonRestUrlForAuthor+"counts",null,"json","application/json");
			},
	};

	var model = {
			counts : function(){
				return controller.counts();
			},
			courses : function(){
				var thislist =  controller.courses($('.auc.active').attr('cstatus'));
				return thislist = _.isObject(thislist) ? thislist : null;				
			},
	};

	var view = {
			counts : function(){
				var list = model.counts();
				$('.count[at=Draft]').text(list[0].draft);
				$('.count[at=Trashed]').text(list[0].trashed);
				$('.count[at=Published]').text(list[0].published);
				$('.count[at=Approved]').text(list[0].approved);

			},
			courses : function(){
				var list = model.courses();
				if($('.auc.active').attr('at')=="Published"){
					renderTemplate("#authorCoursesForAdminPublishedTmpl", list, "#coursedescriptiontable");
					applypagination($('.rr'), 10);
					$('.contentapprovebtn').click(function(){
						$('[name=contentapproveformauthor] [name=courseid]').val($(this).attr('courseid'));
						$('[name=contentapproveformauthor]').attr('action','contentPublish');
						$('[name=contentapproveformauthor]').submit();
					});
				}else{
					renderTemplate("#authorCoursesForAdminTmpl", list, "#coursedescriptiontable");
					applypagination($('.rr'), 10);
				}
				$('.authorCoursesForAdminTmplTitle').text($('.auc.active').attr('at')+" Courses");
			},
			events : function(){
				$('.auc').click(function(e){
					var tourattr = $(this).attr('b');
					if(tourattr=="tour"){
						e.preventDefault();
					}else{
						$('.leftsideli').removeClass('active');
						$(this).addClass('active');
						view.courses();	
					}
				});
			},
	};

	return {
		init : function(){
			initializr();
			courseEvents();
		}
	};

	function initializr (){
		view.counts();
	}

	function courseEvents(){
		view.events();
	}
});