$(function(){ 
	$("#adminuserlist").addClass('hover').addClass('active');
	adminUsers().init();
	adminUsers().pageevents();
});

var adminUsers = adminUsers || {};

adminUsers = (function(){

	var $leftsidelink = $('.leftsidelink'),
	$registeredUsersLink = $('.leftsidelink[al=registered]'),
	$enrolledUsersLink = $('.leftsidelink[al=enrolled]'),
	$paidUsersLink = $('.leftsidelink[al=paid]'),
	$deactivatedUsersLink = $('.leftsidelink[al=deactivated]'),	
	$authorsLink = $('.leftsidelink[al=author]'),
	$invitedUsersLink = $('.leftsidelink[al=invited]'),

	$onlinePaymentLink = $('.leftsidelink[al=onlinepayment]'),
	$freePaymentLink = $('.leftsidelink[al=freepayment]'),
	$pendingPaymentLink = $('.leftsidelink[al=pendingpayment]'),
	$approvedPaymentLink = $('.leftsidelink[al=approvedpayment]'),

	$allCertificateLink = $('.leftsidelink[al=allcertificate]'),
	$pendingCertificateLink = $('.leftsidelink[al=pendingcertificate]'),
	$issuedCertificateLink = $('.leftsidelink[al=issuedcertificate]'),

	commonUserRestURLPath = courseData.getContextPath()+"/rest/adminusers";

	var model = {

			count : function(){
				var thislist = Ajax.get(commonUserRestURLPath+"/count",undefined,"json","application/json");
				return thislist = _.isObject(thislist) ? thislist : null;
			},

			registeredUsers : function(){
				var thislist = Ajax.get(commonUserRestURLPath+"/registered",undefined,"json","application/json");
				return thislist = _.isObject(thislist) ? thislist : null;
			},

			enrolledUsers : function(){
				var thislist = Ajax.get(commonUserRestURLPath+"/enrolled",undefined,"json","application/json");
				return thislist = _.isObject(thislist) ? thislist : null;
			},

			paidUsers : function(){
				var thislist = Ajax.get(commonUserRestURLPath+"/paidUsers",undefined,"json","application/json");
				return thislist = _.isObject(thislist) ? thislist : null;
			},

			deactivatedUsers : function(){
				var thislist = Ajax.get(commonUserRestURLPath+"/deactivated",undefined,"json","application/json");
				return thislist = _.isObject(thislist) ? thislist : null;
			},

			invitedUsers : function(){
				var thislist = Ajax.get(commonUserRestURLPath+"/invited",undefined,"json","application/json");
				return thislist = _.isObject(thislist) ? thislist : null;
			},

			authors : function(){
				var thislist = Ajax.get(commonUserRestURLPath+"/authors",undefined,"json","application/json");
				return thislist = _.isObject(thislist) ? thislist : null;
			},

			authorCourses : function(id){
				var thislist = Ajax.get(commonUserRestURLPath+"/authorCourses/"+id,undefined,"json","application/json");
				return thislist = _.isObject(thislist) ? thislist : null;
			},

			authorCourseUsers : function(id){
				var thislist = Ajax.get(commonUserRestURLPath+"/authorCourseUsers/"+id,undefined,"json","application/json");
				return thislist = _.isObject(thislist) ? thislist : null;
			},

			onlinePayments : function(){
				var obj = {
						paymenttype: "Online",
						approvedstatus : "A"
				};
				return ajaxhelperwithdata("getUsersInvoices",obj); 

			},
			updateemail : function(invitationid,email){
				var obj = {
						email: email,
						invitationid : invitationid,
				};
				return ajaxhelperwithdata("updateemail",obj); 

			},
			freePayments : function(){
				var obj = {
						paymenttype: "Free",
						approvedstatus : "all"
				};
				return ajaxhelperwithdata("getUsersInvoices",obj);
			},

			pendingPayments : function(){
				var obj = {
						paymenttype: "invoice-email",
						approvedstatus : "P"
				};
				return ajaxhelperwithdata("getUsersInvoices",obj);
			},

			paidPayments : function(){
				var obj = {
						paymenttype: "invoice-email",
						approvedstatus : "A"
				};
				return ajaxhelperwithdata("getUsersInvoices",obj);
			},

			allCertificates : function(){
				var thislist = Ajax.get(commonUserRestURLPath+"/certificateRequests/all",undefined,"json","application/json");
				return thislist = _.isObject(thislist) ? thislist : null;
			},

			pendingCertificates : function(){
				var thislist = Ajax.get(commonUserRestURLPath+"/certificateRequests/P",undefined,"json","application/json");
				return thislist = _.isObject(thislist) ? thislist : null;
			},

			approvedCertificates : function(){
				var thislist = Ajax.get(commonUserRestURLPath+"/certificateRequests/A",undefined,"json","application/json");
				return thislist = _.isObject(thislist) ? thislist : null;
			},

			issueCertificate : function(id){
				var obj = {
						coursecertificateid  : id
				};
				return ajaxhelperwithdata("approveCertificate", obj);
			},

			sendNotification : function(){
				var obj = objects.sendobj();
				return Ajax.post(commonUserRestURLPath+"/notify?type="+obj.type+"&description="+obj.description,undefined,"json","application/json");				
			},

			sendMessage : function(){
				var obj = objects.sendobj();
				return Ajax.post(commonUserRestURLPath+"/message?type="+obj.type+"&description="+obj.description,undefined,"json","application/json");				
			},
			sendSingleMessage : function(id,val){
				return Ajax.post(commonUserRestURLPath+"/singlemessage?orgpersonid="+id+"&description="+val,undefined,"json","application/json");
			},

			updatePersonStatus : function(id,status){
				return Ajax.put(commonUserRestURLPath+"/"+id+"/"+status,undefined,"json","application/json");
			},

			enrolledCourses : function(id){
				return Ajax.get(commonUserRestURLPath+"/enrolledCourses?orgpersonid="+id,undefined,"json","application/json");
			},

			assignRoyalty : function(courseenrollmentid){
				var obj = {
						courseenrollmentid : courseenrollmentid
				};				
				var royaltyPayment =Ajax.ajaxHelper("POST", courseData.getContextPath()+"/rest/royaltypayment?type=1",obj,"json","application/json"); 
				return royaltyPayment;
			},

			approveCoursePayment : function(paymentid,enrollmentid){
				return Ajax.post(commonUserRestURLPath+"/paymentapprove/"+paymentid+"/"+enrollmentid,undefined,"json","application/json");
			},

			userMoreInfoProfile : function(id){

				var obj = {
						personid : id
				};
				var list = ajaxhelperwithdata("loadUserProfile",obj);
				return list;
			},

			userMoreInfoCourses : function(id){

				var obj = {
						personid : id
				};
				var list = ajaxhelperwithdata("loadIndividualCoursewithoutTimespend",obj);

				var finallist={};
				finallist.courseliststatusA = _.where(list,{courseenrollmentstatus:"A"});
				finallist.courseliststatusC = _.where(list,{courseenrollmentstatus:"C"});
				finallist.courseliststatusW = _.where(list,{courseenrollmentstatus:"W"});
				finallist.courseliststatusB = _.where(list,{courseenrollmentstatus:"B"});

				return finallist;
			},
			updateRole : function(personallocationid,roleid){
				return Ajax.post(commonUserRestURLPath+"/updaterole?personallocationid="+personallocationid+"&roleid="+roleid,undefined,"json","application/json");				
			},
	};

	var objects = {

			sendobj : function(){
				return obj = {
						type : $('.leftsidelink.active').attr('al'),
						description : $('.sendcommomtextarea').val().trim()		
				};
			}
	};


	var view = {

			home : function(){
				view.count();
				var linkvalue = util.initialLink();
				util.navigation(linkvalue);
				$('.emailupdate').click(function(){
					var status = model.updateemail($('.emailIdval').val(),$('.emailusers').val().trim());
					if(status>"0"){
						$('.emailusertxt').text(status);
						$('.emailusertxt').show();
						$('.emailedit').show();
						$('.emailusers').hide();
						$('.emailupdate').hide();
						$('.sendinvite').show();
					}
				});
				$('.sendinvite').click(function(){
						var obj = {
								email: $('.emailusertxt').text(),
								invitationid : $('.emailIdval').val(),
								message	: encodeURIComponent($('.msgdescription').val()),
								attachmentpath: ""
						};
						inviteUsers.asyncajaxs("recentemil",obj);
				});
			},

			count : function(){
				var list = model.count();
				$('.leftsidelink[al=registered] .count').text(list[0].registedusers);
				$('.leftsidelink[al=invited] .count').text(list[0].invitedusers);
				$('.leftsidelink[al=enrolled] .count').text(list[0].enrolleduser);
				$('.leftsidelink[al=deactivated] .count').text(list[0].deactivatedusers);
				$('.leftsidelink[al=paid] .count').text(list[0].paidusers);
				$('.leftsidelink[al=author] .count').text(list[0].authors);

				$('.leftsidelink[al=onlinepayment] .count').text(list[0].onlinepaidusers);
				$('.leftsidelink[al=freepayment] .count').text(list[0].freepaidusers);
				$('.leftsidelink[al=pendingpayment] .count').text(list[0].pendingpayment);
				$('.leftsidelink[al=approvedpayment] .count').text(list[0].paidpayment);

				$('.leftsidelink[al=allcertificate] .count').text(list[0].allcertificaterequests);
				$('.leftsidelink[al=pendingcertificate] .count').text(list[0].pendingcertificates);
				$('.leftsidelink[al=issuedcertificate] .count').text(list[0].approvedcertificates);
			},

			inviteNewUsers : function(){
				$("#inviteUserModal").modal("show");
				$("#inviteUserModal .modal-body").text("show");
			},

			commonUserHeader : function(){
				renderTemplate("#commonuserheadertmpl", null, "#commonuserheaderholder");
				$('.notifyuser').click(function(){
					$('.commouseralert').hide();
					$('.dropbox').slideDown(400);
					$('.sendcommomtextarea').attr('placeholder','Enter Your Notification here..');
					$('.sendcommomtextarea').val("");
					$('.sendcommonbtn').text("Send Notification");
				});

				$('.messageuser').click(function(){
					$('.commouseralert').hide();
					$('.dropbox').slideDown(400);
					$('.sendcommomtextarea').attr('placeholder','Enter Your Message here..');
					$('.sendcommomtextarea').val("");
					$('.sendcommonbtn').text("Send Message");
				});

				$('.closebtn').click(function(){
					$('.dropbox').slideUp(400);
					$('.sendcommomtextarea').val("");
				});

				$('.sendcommonbtn').click(function(){
					var thislength = $('.sendcommomtextarea').val().trim().length;
					if(thislength!=0){
						$('.commouseralert').hide();
						view.commonsend();
					}else{
						$('.commouseralert.alert-danger').show();
						$('.commonusermsg').text("Please don't leave it blank");
					}
				});

				$('.sendcommomtextarea').keyup(function(){
					el = $(this);
					if(el.val().length >= 501){
						el.val( el.val().substr(0, 500) );
					}
				});
			},

			registeredUsers : function(){
				var list = model.registeredUsers();
				Handlebars.registerHelper('role',function(roleid,personallocationid,firstname,lastname){
					if(roleid==3){
						return '<font class="label label-success pad612">Tutor</font>';
					}else{
						return '<a class="btn btn-info pad612 tutorprocess" personallocationid="'+personallocationid+'"'+ 
								'username="'+firstname+'">Assign Tutor</a>';
					}
				});
				view.commonUserRenderer("#registeredUserstmpl", list);
				view.commonSearch("#registeredUserstmpl",list);

			},

			enrolledUsers : function(){
				var list = model.enrolledUsers();
				var modifiedList = util.modifyEnrolledUsers(list);
				view.commonUserRenderer("#enrolledUserstmpl", modifiedList);		
				view.commonSearch("#enrolledUserstmpl",modifiedList);
			},

			enrolledCourses : function(id){
				var list = model.enrolledCourses(id);
				Handlebars.registerHelper('enrollstatus',function(statusval){
					if(statusval=="A"){
						var result = '<font class="label label-info pad612">Learning</font>';
						return new Handlebars.SafeString(result);
					}else if(statusval=="C"){
						var result = '<font class="label label-success pad612">Completed</font>';
						return new Handlebars.SafeString(result);
					}else if(statusval=="B"){
						var result = '<font class="label label-danger pad612">Blocked</font>';
						return new Handlebars.SafeString(result);
					}
				});
				$("#usercommonmodal").modal("show");
				$('#userModalLabel').text("Enrolled Courses");
				renderTemplate("#enrolledCoursestmpl", list, ".usercommonmodalbody");
				$('.usercommonmodalbody').slimscroll({height: "500px"});
			},
			paidUsers : function(){
				var list = model.paidUsers();
				view.commonUserRenderer("#paidUserstmpl", list);
				view.commonSearch("#paidUserstmpl",list);
			},

			deactivatedUsers : function(){
				var list = model.deactivatedUsers();
				view.commonUserRenderer("#deactivatedUserstmpl", list);
				view.commonSearch("#deactivatedUserstmpl",list);
			},

			invitedUsers : function(){

				var list = model.invitedUsers();
				Handlebars.registerHelper('df', function(dateofissue) { 
					var d = new Date(dateofissue);
					var date =d.toDateString();
					var time = d.toLocaleTimeString();
					data=date.split(" ");
					return data[2]+"-"+data[1]+"-"+data[3]+" "+time;
				});
				Handlebars.registerHelper('email', function(courseinvitationstatus) 
						 { 
						 if(courseinvitationstatus==="I"){
							 return "<span class='label label-success'>Send</span>";
						 }
						 else if(courseinvitationstatus==="F"){
							 return "<a class='btn btn-success recentemail'> Resend</a>";;	 
						 }
						 });
				//var list = model.invitedUsers();
				view.commonUserRenderer("#invitedUserstmpl", list);
				view.commonSearch("#invitedUserstmpl",list);
			},

			authors : function(){
				var list = model.authors();
				view.commonUserRenderer("#authorstmpl", list);
				view.commonSearch("#authorstmpl",list);				
			},

			authorCourses : function(id){
				var list = model.authorCourses(id);
				view.renderAuthorCourses(list);
			},

			renderAuthorCourses : function(list){
				renderTemplate("#authorcoursestmpl", list, ".usercommonmodalbody");
				$('.usercommonmodalbody').slimscroll({height: "500px"});
				$('.usercountlink').click(function(){
					if($(this).text()!=="0"){
						var coursetitle = $(this).attr('coursetitle');
						view.authorCourseUsers($(this).attr('courseid'),list,coursetitle);	
					}
				});				
			},
			authorCourseUsers : function(id,courseslist,coursetitle){
				var list = model.authorCourseUsers(id);
				renderTemplate("#authorcourseuserstmpl", list, ".usercommonmodalbody");
				$('.authorcourseusertitle').text(coursetitle);
				$('.backtoauthorcourses').click(function(){
					view.renderAuthorCourses(courseslist);
				});
			},

			onlinePayments : function(){
				var list = model.onlinePayments();
				view.commonUserRenderer("#onlinePaidUserstmpl", list);
				view.commonSearch("#onlinePaidUserstmpl",list);
			},

			freePayments : function(){
				var list = model.freePayments();
				view.commonUserRenderer("#freePaidUserstmpl", list);
				view.commonSearch("#freePaidUserstmpl",list);
			},

			pendingPayments : function(){
				var list = model.pendingPayments();
				view.commonUserRenderer("#offlinependingtmpl", list);
				view.commonSearch("#offlinependingtmpl",list);				
			},

			approvePayment : function(){
				$('.paymentapprovebtn').off().click(function(){
					var checkstr =  confirm('Are you sure you want to approve this payment');
					if(checkstr == true)	{
						var paymentid = $(this).attr('paymentid');
						var enrollid = $(this).attr('courseenrollmentid');
						var assignRoyalty = model.assignRoyalty(enrollid);
						if(assignRoyalty=="CREATED"){
							var paymentapprovestatus = model.approveCoursePayment(paymentid, enrollid);
							if(paymentapprovestatus=="OK"){
								msg = "OK";							
							}else{
								msg = "NO";
							}	
						}else{
							msg = "NO";
						}
						view.commonTopAlert(msg, "Payment Successfully Approved", 
						"Error occured during approval. Please try again later");
						view.count();
						$('.leftsidelink.active').trigger('click');
					}

				});
			},

			userInfo : function(){
				$('.userinfo').click(function(){
					view.loadUserInfo($(this).attr('orgpersonid'));
				});
			},

			loadUserInfo : function(id){
				$('#moreinfomodal').modal('show');
				renderTemplate("#profileinfotmpl", model.userMoreInfoProfile(id), "#profileinfotable");
				renderTemplate("#coursesinfotmpl", model.userMoreInfoCourses(id), "#coursesinfotable");
				$('#coursesinfotable').slimscroll({height: "420px"}); 
			},

			paidPayments : function(){
				var list = model.paidPayments();
				view.commonUserRenderer("#offlinePaidpaymentstmpl", list);
				view.commonSearch("#offlinePaidpaymentstmpl",list);		
			},

			allCertificates : function(){
				Handlebars.registerHelper('certificatestatus',function(id,status){
					if(status=="P"){
						var result = '<a class="btn btn-info pad612 approvecertificatelink" id='+id+'>Approve</a>';
						return new Handlebars.SafeString(result);
					}else{
						var result = '<font class="label label-success pad612">Approved</font>';
						return new Handlebars.SafeString(result);
					}
				});
				var list = model.allCertificates();
				view.commonUserRenderer("#allCertificateRequesttmpl", list);
				view.commonSearch("#allCertificateRequesttmpl",list);				
			},
			issueCertificate : function(){
				$('.approvecertificatelink').click(function(){
					var checkstr =  confirm('Are you sure you want to issue this certificate');
					if(checkstr == true)	{
						var successtatus = model.issueCertificate($(this).attr('id'));
						if(successtatus=="1"){
							statusmsg = "OK";
						}else{
							statusmsg = "NO";
						}
						view.commonTopAlert(statusmsg, "Certificate Successfully Approved", "Error in Approving the certificate. Try again later.");
						view.count();
						$('.leftsidelink.active').trigger('click');
					}
				});

			},
			pendingCertificates : function(){
				Handlebars.registerHelper('certificatestatus',function(id,status){
					if(status=="P"){
						var result = '<a class="btn btn-info pad612 approvecertificatelink" id='+id+'>Approve</a>';
						return new Handlebars.SafeString(result);
					}else{
						var result = '<font class="label label-success pad612">Approved</font>';
						return new Handlebars.SafeString(result);
					}
				});
				var list = model.pendingCertificates();
				view.commonUserRenderer("#pendingCertificateRequesttmpl", list);
				view.commonSearch("#pendingCertificateRequesttmpl",list);
			},

			approvedCertificates : function(){
				var list = model.approvedCertificates();
				view.commonUserRenderer("#approvedCertificateRequesttmpl", list);
				view.commonSearch("#approvedCertificateRequesttmpl",list);
			},

			commonUserRenderer:function(tmpl,list){
				if(list=="false"){
					lists=null;
					renderTemplate(tmpl, lists, "#userholder");
				}
				else{
					renderTemplate(tmpl, list, "#userholder");
				}
				/*renderTemplate(tmpl, list, "#userholder");*/
				applypagination($('#userholder .table tbody tr'),10);

				view.commonSingleMessage();

				$('.blockuser').click(function(){
					util.blockPerson($(this).attr('orgpersonid'),$(this).attr('personname'));
				});
				
				$('.recentemail').click(function(){
					$("#inviteusermodals").modal('show');
					$('.sendingmess').hide();
					$('.sendinvite').show();
					$('.emailIdval').val($(this).parent().children('span:nth(0)').text());
					$('.emailusertxt').text($(this).parent().children('span:nth(1)').text());
					$('.msgdescription').val($(this).parent().children('span:nth(2)').text());
				});
				$('.emailedit').click(function(){
					$('.emailusertxt').hide();
					$(this).hide();
					$('.sendinvite').hide();
					$('.emailusers').show().val($('.emailusertxt').text());
					$('.emailupdate').show();
				});
				$('.enrolledcourses').click(function(){
					view.enrolledCourses($(this).attr('orgpersonid'));
				});

				$('.activateuser').click(function(){
					util.activateUser($(this).attr('orgpersonid'),$(this).attr('personname'));
				});

				view.issueCertificate();

				view.approvePayment();

				$('.authorcourseslink').click(function(){
					if($(this).text()!=="0"){
						$('#userModalLabel').text($(this).attr('personname')+"'s Courses");
						$("#usercommonmodal").modal("show");
						view.authorCourses($(this).attr('orgpersonid'));	
					}
				});
				$('.tutorprocess').click(function(){
					var result = model.updateRole($(this).attr('personallocationid'),3);
					if(result=="OK"){
						$('.tutoralerts').show();
						$('.username').text($(this).attr('username'));
						view.registeredUsers();
						setTimeout(function(){ 
							$('.tutoralerts').hide();
						}, 2000);
					}
				});
				view.userInfo();
			},

			commonsend : function(){
				var type = $('.sendcommonbtn').text();
				if(type=="Send Notification"){
					var sentSuccess = model.sendNotification();
					view.commonUserAlert(sentSuccess, "Notification Successfully Sent", "Error in sending the Notification. Try again later.");
				}
				else if(type=="Send Message"){
					var sentSuccess = model.sendMessage();
					view.commonUserAlert(sentSuccess, "Message Successfully Sent", "Error in sending the message. Try again later.");	
				}
				else {
					var messgeSent = model.sendSingleMessage($('.sendcommomtextarea').attr('orgpersonid'), $('.sendcommomtextarea').val());
					view.commonUserAlert(messgeSent, "Message Successfully Sent", "Error in sending the message. Try again later.");				
				}	
			},

			commonSearch : function(tmpl,list){

				/*$('.usersearchbtn').click(function(event){
					var searchkeyword=$('.usersearchinput').val().trim();
					if(searchkeyword!=undefined && searchkeyword.length!=0){
						var newlist = util.nameFilters(searchkeyword,list);
						view.commonUserRenderer(tmpl, newlist);
					}else{
						event.preventDefault();
					}
				});	
				$('.usersearchinput').keypress(function(event){
					var keycode = (event.keyCode ? event.keyCode : event.which);
					if(keycode == '13'){
						var searchkeyword=$('.usersearchinput').val().trim();
						if(searchkeyword!=undefined && searchkeyword.length!=0){
							var newlist = util.nameFilters(searchkeyword,list);
							view.commonUserRenderer(tmpl, newlist);
						}else{
							event.preventDefault();
						}
					}
				});*/
				$('.usersearchinput').keyup(function(){
					var searchkeyword=$('.usersearchinput').val().trim();
					if(searchkeyword!=undefined && searchkeyword.length!=0){
						var newlist = util.nameFilters(searchkeyword,list);
						view.commonUserRenderer(tmpl, newlist);
					}else{
						view.commonUserRenderer(tmpl, list);
					}

				});
				$('.alluser').click(function(){
					$('.usersearchinput').val("");
					view.commonUserRenderer(tmpl, list);
				});
			},
			commonUserAlert : function(result,successmsg,failuremsg){
				$('.commouseralert').hide();
				if(result=="OK"){
					$('.commouseralert.alert-success').show();
					$('.sendcommomtextarea').val("");
					$('.commonusermsg').text(successmsg);
					setTimeout(function(){ 
						$('.commouseralert').hide();
						$('.dropbox').slideUp(400);
					}, 3000);
				}else{
					$('.commouseralert.alert-danger').show();
					$('.sendcommomtextarea').val("");
					$('.commonusermsg').text(failuremsg);
					setTimeout(function(){ 
						$('.commouseralert').hide();								
					}, 3000);					
				}
			},

			commonTopAlert : function(status,success,failure){
				window.scrollTo(0,0);
				if(status == "OK"){
					renderTemplate("#commonusersuccesstmpl", null, ".useralertholder");
					$(".commonuseralertmsg").text(success);
					setTimeout(function(){ 
						$('.useralertholder').hide();
					}, 4000);

				}else{
					renderTemplate("#commonuserfailuretmpl", null, ".useralertholder");
					$(".commonuseralertmsg").text(failure);
					setTimeout(function(){ 
						$('.useralertholder').hide();
					}, 4000);
				}
			},
			commonSingleMessage : function(){

				$('.sendsinglemessage').click(function(){
					$('.commouseralert').hide();
					$('.dropbox').slideDown(400);
					$('.sendcommomtextarea').attr('placeholder','Enter Your Message here..');
					$('.sendcommomtextarea').val("");
					$('.sendcommonbtn').text("Send Message to "+$(this).attr('personname'));
					$('.sendcommomtextarea').attr('orgpersonid',$(this).attr('orgpersonid'));
					$('.sendcommomtextarea').attr('type','individual');
				});
			}
	};

	var util = {
			initialLink:function(){
				var actualRedirect , 
				requestValue = $('.linkvalredirect').attr('aa'),
				lastLinkValue = sessionStorage.getItem("lastLink"), 
				defaultValue = "registered";
				if(requestValue!="null"){
					actualRedirect = util.requestValueEquivalent(requestValue);
				}else{
					if(lastLinkValue!=null){
						actualRedirect = lastLinkValue;
					}else{
						actualRedirect = defaultValue;
					}	
				}
				return actualRedirect;
			},

			requestValueEquivalent : function(link){
				if(link=="eu"){
					return "enrolled";
				}else if(link=="ru"){
					return "registered";
				}else if(link=="authors"){
					return "author";
				}else if(link=="du"){
					return "deactivated";
				}else if(link=="cr"){
					return "pendingpayment";
				}else if(link=="cer"){
					return "pendingcertificate";
				}
			},
			nameFilters:function(name,searchList){

				var searchtype = $('.leftsidelink.active').attr('searchval');

				if(searchtype=="name"){					
					return util.nameSearch(name, searchList);					
				} else if(searchtype=="email"){
					return util.emailSearch(name, searchList);					
				} else if(searchtype=="payment"){
					return util.paymentSearch(name, searchList);
				} else if(searchtype=="certificate"){
					return util.certificateSearch(name, searchList);
				}

			},

			nameSearch : function(name,searchList){
				var persons=[];
				var personids =_.uniq(_.pluck(searchList,'orgpersonid'));
				_.each(personids,function(orgpersonid){
					var person={};
					var personarray = _.where(searchList,{orgpersonid:orgpersonid});
					var firstnamestr =personarray[0].firstname.toLowerCase();
					if(util.contains(firstnamestr, name.toLowerCase())){
						person = personarray[0];
						persons.push(person);
					}
					if(personarray[0].lastname!=null){
						var lastnamestr =personarray[0].lastname.toLowerCase();
						if(util.contains(lastnamestr, name.toLowerCase())){
							person = personarray[0];
							persons.push(person);
						}
					}
				});
				return persons;
			},

			emailSearch : function(name,searchList){
				var persons=[];
				var personids =_.uniq(_.pluck(searchList,'emailid'));
				_.each(personids,function(emailid){
					var person={};
					var personarray = _.where(searchList,{emailid:emailid});
					var firstnamestr =personarray[0].emailid.toLowerCase();
					if(util.contains(firstnamestr, name.toLowerCase())){
						person = personarray[0];
						persons.push(person);
					}						
				});
				return persons;
			},

			paymentSearch : function(name,searchList){
				var persons=[];
				var personids =_.uniq(_.pluck(searchList,'paymentid'));
				_.each(personids,function(paymentid){
					var person={};
					var personarray = _.where(searchList,{paymentid:paymentid});
					if(personarray[0]){
						var firstnamestr =personarray[0].firstname.toLowerCase();
						if(util.contains(firstnamestr, name.toLowerCase())){
							person = personarray[0];
							persons.push(person);
						}	
					}
					if(personarray[0].lastname!=null){
						var lastnamestr =personarray[0].lastname.toLowerCase();
						if(util.contains(lastnamestr, name.toLowerCase())){
							person = personarray[0];
							persons.push(person);
						}
					}
				});
				return persons;
			},

			certificateSearch : function(name,searchList){
				var persons=[];
				var personids =_.uniq(_.pluck(searchList,'coursecertificateid'));
				_.each(personids,function(coursecertificateid){
					var person={};
					var personarray = _.where(searchList,{coursecertificateid:coursecertificateid});
					if(personarray[0]){
						var firstnamestr =personarray[0].firstname.toLowerCase();
						if(util.contains(firstnamestr, name.toLowerCase())){
							person = personarray[0];
							persons.push(person);
						}	
					}
					if(personarray[0].lastname!=null){
						var lastnamestr =personarray[0].lastname.toLowerCase();
						if(util.contains(lastnamestr, name.toLowerCase())){
							person = personarray[0];
							persons.push(person);
						}
					}
				});
				return persons;
			},

			contains:function(r, s) {
				return r.indexOf(s) > -1;
			},
			navigation : function(al){

				$leftsidelink.removeClass('active');
				view.commonUserHeader();
				switch(al){

				case "registered" :
					$registeredUsersLink.addClass('active');
					$('.rightpaneusertitle').text("Registered Users");
					view.registeredUsers();
					var thiscount = $registeredUsersLink.find('.count').text();
					if(thiscount=="0"){
						$('.commonasuseractions').hide();
					}else{
						$('.commonasuseractions').show();
					}
					break;

				case "enrolled" :
					$enrolledUsersLink.addClass('active');
					$('.rightpaneusertitle').text("Enrolled Users");
					view.enrolledUsers();
					var thiscount = $enrolledUsersLink.find('.count').text();
					if(thiscount=="0"){
						$('.commonasuseractions').hide();
					}else{
						$('.commonasuseractions').show();
					}
					break;

				case "paid" :
					$paidUsersLink.addClass('active');
					$('.rightpaneusertitle').text("Paid Users");
					view.paidUsers();
					var thiscount = $paidUsersLink.find('.count').text();
					if(thiscount=="0"){
						$('.commonasuseractions').hide();
					}else{
						$('.commonasuseractions').show();
					}
					break;

				case "deactivated" :
					$deactivatedUsersLink.addClass('active');
					$('.rightpaneusertitle').text("Deactivated Users");
					$('.commonasuseractions').hide();
					view.deactivatedUsers();
					break;

				case "invited" :
					$invitedUsersLink.addClass('active');
					$('.rightpaneusertitle').text("Invited Users");
					$('.commonasuseractions').hide();
					view.invitedUsers();			
					break;

				case "author" :
					$authorsLink.addClass('active');
					$('.rightpaneusertitle').text("Authors");
					view.authors();
					var thiscount = $authorsLink.find('.count').text();
					if(thiscount=="0"){
						$('.commonasuseractions').hide();
					}else{
						$('.commonasuseractions').show();
					}
					break;

				case "onlinepayment" :
					$onlinePaymentLink.addClass('active');
					$('.rightpaneusertitle').text("Online Payments");
					$('.commonasuseractions').hide();
					view.onlinePayments();
					break;

				case "freepayment" :
					$freePaymentLink.addClass('active');
					$('.rightpaneusertitle').text("Free Course Payments");
					$('.commonasuseractions').hide();
					view.freePayments();
					break;

				case "pendingpayment" :
					$pendingPaymentLink.addClass('active');
					$('.rightpaneusertitle').text("Offline Course Payment Requests");
					view.pendingPayments();
					var thiscount = $pendingPaymentLink.find('.count').text();
					if(thiscount=="0"){
						$('.commonasuseractions').hide();
					}else{
						$('.commonasuseractions').show();
					}
					break;

				case "approvedpayment" :
					$approvedPaymentLink.addClass('active');
					$('.rightpaneusertitle').text("Offline Approved Payments");
					$('.commonasuseractions').hide();
					view.paidPayments();					
					break;

				case "allcertificate" :
					$allCertificateLink.addClass('active');
					$('.rightpaneusertitle').text("All Certificate Requests");
					$('.commonasuseractions').hide();
					view.allCertificates();
					break;

				case "pendingcertificate" :
					$pendingCertificateLink.addClass('active');
					$('.rightpaneusertitle').text("Pending Certificate Requests");
					$('.commonasuseractions').hide();
					view.pendingCertificates();
					break;

				case "issuedcertificate" :
					$issuedCertificateLink.addClass('active');
					$('.rightpaneusertitle').text("Approved Certificate Requests");
					$('.commonasuseractions').hide();
					view.approvedCertificates();
					break;			
				}
			},

			modifyEnrolledUsers : function(list){
				var searchList = _.where(list,{seperator:'persons'});
				var coursecountsList = _.where(list,{seperator:'countval'});
				var persons=[];
				var personids =_.uniq(_.pluck(searchList,'orgpersonid'));
				_.each(personids,function(orgpersonid){
					var person={};
					var personarray = _.where(searchList,{orgpersonid:orgpersonid});
					var coursecountarray = _.where(coursecountsList,{orgpersonid:orgpersonid});
					person = personarray[0];
					person["coursecount"] = coursecountarray[0].enrolledcoursescount;
					persons.push(person);
				});
				return persons;
			},

			blockPerson : function(id,name){
				var msg = "Are you sure want to Deactivate "+name+"?";
				swal({   
					title: msg,  
					animation: "slide-from-top",
					showCancelButton: true,   
					confirmButtonColor: "#DD6B55",  
					confirmButtonText: "Yes, Deactivate "+name+"!",   
					closeOnConfirm: false 
				},
				function(){   
					var str = model.updatePersonStatus(id,"D");
					if(str=="OK"){
						swal({   
							title: "Success!",
							text: name+" Successfully Deactivated !",
							type:"success",
							animation: "slide-from-top",
							timer: 4000,
							showConfirmButton: false
						});							
					}else{
						swal({   
							title: "Failure !",
							text: "Error in blocking "+name+". Please Try Again later",
							type:"error",
							animation: "slide-from-top",
							timer: 4000,
							showConfirmButton: false
						});
					}
					view.count();
					view.registeredUsers();
				});				
			},

			activateUser : function(id,name){
				var msg = "Are you sure want to Activate "+name+"?";
				swal({   
					title: msg,  
					animation: "slide-from-top",
					showCancelButton: true,   
					confirmButtonColor: "#DD6B55",  
					confirmButtonText: "Yes, Activate "+name+"!",   
					closeOnConfirm: false 
				},
				function(){   
					var str = model.updatePersonStatus(id,"A");
					if(str=="OK"){
						swal({   
							title: "Success!",
							text: name+" Successfully Activated !",
							type:"success",
							animation: "slide-from-top",
							timer: 4000,
							showConfirmButton: false
						});							
					}else{
						swal({   
							title: "Failure !",
							text: "Error in activating "+name+". Please Try Again later",
							type:"error",
							animation: "slide-from-top",
							timer: 4000,
							showConfirmButton: false
						});
					}
					view.count();
					view.deactivatedUsers();
				});

			}
	};

	return {
		init:function(){
			initialize();
		},
		pageevents:function(){
			eventsfn();
		},
		count : function(){
			view.count();
		}
	};	

	function initialize(){
		view.home();		
	}

	function eventsfn(){  
		inviteUserFileUpload();
		inviteUsers.sendMessage(); 
		$('.leftsidelink').click(function(event){
			$('.emptycommon').hide();
			$('.dropbox').slideUp(400);
			$('.sendcommomtextarea').val("");
			$('.usersearchinput').val("");

			if($(this).attr('b')==="tour"){
				event.preventDefault();	
			}else{
				sessionStorage.setItem("lastLink",$(this).attr('al'));
				util.navigation($(this).attr('al'));				
			}
		});

	}	

	function inviteUserFileUpload(){
		$("#messagedescription").keyup(function(){
			el = $(this);
			if(el.val().length >= 501){
				el.val( el.val().substr(0, 500) );
			} else {
				$('.mesdesccharremaining').text(500-el.val().length);
			}
		});
		$('.inviteusermodal').click(function(){
			$('.sendingmess').hide();
			$('#errormessage').hide();
			if($("#orgstatus").val()=="A"){
				var userlist = courseData.getmaxuser();
				var maxuser = userlist[0].maxusers;
				if(userlist!="INTERNAL_SERVER_ERROR"){
					var allusercount=userlist.length;
					if(maxuser>allusercount){
						$('#inviteusersmodal').modal('show');
						$('[name=upattachemntform]').show();
						$('.attchmentdiv').hide();
						$('.errblcok').hide();
					}
					else{
						$('.maxcourseuser').show();
						$('.maxusercount').text(maxuser);
						setTimeout(function(){
							$('.maxcourseuser').hide();										
						},4000);
					}
				}
				else{
					$('#inviteusersmodal').modal('show');
					$('[name=upattachemntform]').show();
					$('.attchmentdiv').hide();
					$('.errblcok').hide();
				}
			}
			else{
				$('#inviteusersmodal').modal('show');
				$('[name=upattachemntform]').show();
				$('.attchmentdiv').hide();
				$('.errblcok').hide();
			}

		});

		$('.orgplanlink').click(function(){
			var context = $('#context').text();
			$(this).attr("href",context+"/app/myorganization");
			sessionStorage.setItem('lastTab', 'leftsideli border whitebg pad10 hover paiduserbtn subscrips active');
		});

		$('.uploadcnt').hide();
		$('.input-file').change(function(e){
			$('.errblcok').hide();				
			$('.prggrop').hide();
			var fileextension=this.files[0].name.split('.').pop();
			var fileSize=this.files[0].size/(1024*1024);				
			fileSize=fileSize.toFixed(2);
			$('[name=attachmentsizeval]').val(fileSize);
			if(_.isString(fileextension)){					
				if(fileextension==="pdf" || fileextension==="PDF"){
					$('.uploadcnt').show();
					$('.prggrop').show();
					var bar = $('.bar');
					var percent = $('.progressper');
					var status = $('#status');
					$('[name=upattachemntform]').ajaxForm({
						beforeSend: function() {
							$('.sendinvitation').hide();
							status.empty();
							var percentVal = '0%';
							bar.width(percentVal);
							percent.html(percentVal);
						},
						uploadProgress: function(event, position, total, percentComplete) {
							var percentVal = percentComplete + '%';
							bar.width(percentVal);
							percent.html(percentVal);
						},
						success: function() {
							$('.errblcok').show();
							$('.errblcok').removeClass('alert-error').addClass('alert-success');
							$('.strbtn').text('Success !');
							$('.emsg').text("Upload Finished");
							$('.input-file').val('');
							$('.uploadcnt').hide();
							$('.prggrop').hide();
						},
						complete: function(xhr) {						
							if(xhr.responseText=="error"){									
								$('.errblcok').show();
								$('.errblcok').removeClass('alert-success').addClass('alert-error');
								bar.width('0%');
								percent.html('0%');									
								$('.input-file').val('');
								$('.uploadcnt').hide();
								$('.prggrop').hide();
							}
							else if(xhr.responseText=="nospace"){
								$('.errblcok').show();
								$('.errblcok').removeClass('alert-success').addClass('alert-error');
								bar.width('0%');
								percent.html('0%');
								$('.strbtn').text('Failure !');
								$('.emsg').text("Upload failed. Please contact Administrator");
								$('.input-file').val('');
								$('.uploadcnt').hide();
								$('.prggrop').hide();
							}
							else{
								$('.sendinvitation').show();
								$('[name=upattachemntform]').hide();
								$('.attchmentdiv').show();							
								$('.attchmentfilename').attr('atfilename',xhr.responseText);
								$('.attchmentfilename').text($('.attchmentfilename').attr('atfilename').split('/').pop());
								$('.errblcok').show();
								$('.errblcok').removeClass('alert-error').addClass('alert-success');
								$('.strbtn').text('Success !');
								$('.emsg').text("Upload Finished");
								$('.input-file').val('');
								$('.uploadlogocnt').hide();
								$('.prggrop').hide();
								$('.attclose').click(function(){
									$('[name=upattachemntform]').show();
									$('.attchmentdiv').hide();	
									$('.errblcok').hide();
								});
							}
						}
					}); 
				}
				else{
					$('.errblcok').show();
					$('.errblcok').removeClass('alert-success').addClass('alert-error');
					$('.emsg').text("File format not supported");
					$('.input-file').val('');
				}
			}	
			else{

			}
		});
	}

});


var inviteUsers  = {
		sendMessage:function(){
			var self=this;
			$('.sendinvitation').click(function(){
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

							var messageval=$('[name=messagedescription]').val();
							var user = {};				
							user.email =emails.trim();
							user.attachmentpath=$('.attchmentfilename').attr('atfilename');
							user.message=encodeURIComponent(messageval);
							var noEmail=self.checkemails(emails);
							if(noEmail.length=='0'){
								$('#errormessage').hide();
								if($("#orgstatus").val()=="A"){
									var orgplanmaxuser = courseData.getmaxuser();
									orgplanobj = {};
									orgplanobj.orgplanstatus='A';
									var orgplanmaxusers = courseData.orgplanlist(orgplanobj);
									if(orgplanmaxuser=="INTERNAL_SERVER_ERROR"){
										var maxusers=orgplanmaxusers[0].maxusers;
										var emaluser = $("#emailuser").val();
										var emailsplit =emaluser.split(",");
										var emailsplitlength = emailsplit.length;
										if(maxusers>=emailsplitlength){
											if(confirm('Are you sure you want to invite this user')){
												self.asyncajax("userlogin",user);
											}
										}
										else{
											$('.inviteuseralert').show();
											$('.inviteuseralert').text('Your current plan does not allow creation of users more than '+maxusers+'.Already invited users is 0');
											setTimeout(function(){
												$('.inviteuseralert').hide();
											}, 3000);
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
												if(confirm('Are you sure you want to invite this user')){
													self.asyncajax("userlogin",user);
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
											//countobj.loadCountsData();
											adminUsers().count();
											setTimeout(function(){
												$('.inviteuseralert').hide();
											}, 3000);
										}
									}
								}
								else{
									if(confirm('Are you sure you want to invite this user')){
										self.asyncajax("userlogin",user);
									}
								}
							}
							else{
								obj.duplicatemessage="Already Exists Email Id is Automatically Removed";
								renderTemplate("#duplicatemessagetmpl",obj,"#errormessage");
								setTimeout(function(){
									var emailuser = $("#emailuser").val();
									var emailusersplit = emailuser.split(',');
									_.each(noEmail,function(email){
										var dat = _.contains(emailusersplit,email);
										if(dat==true){
											var removemail = emailusersplit.indexOf(email);
											emailusersplit.splice(removemail,1);
											$("#emailuser").val(emailusersplit);
											$("#errormessage").hide();
										}
									});
								}, 1500);

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
			//
		},
		endProgress:function(message){
			$('.sendingmess').hide();
			$('.sendinvitation').show();
			$('.emailuser').val('');
			$('.description').html(message);
			$('#messagedescription').val('');
			$('#inviteusersmodal').modal('hide');
			$('#inviteusermessage').modal('show');
			//countobj.loadCountsData();
			adminUsers().count();
			$('.leftsidelink.active').trigger('click');
		},
		startprogres:function(){
			$('.sendinvite').hide();
			$('.sendingmess').show();
			$('.emailedit').hide();
		},
		endprogres:function(message){
			$('.sendingmess').hide();
			$('.sendinvite').show();
			$('#inviteusermodals').modal('hide');
			adminUsers().count();
			$('.emailedit').show();
			$('.leftsidelink.active').trigger('click');
		},
		errormsg:function(){
			$('.sendinvitation').hide();
			$('.sendingmess').hide();
			$('#inviteusersmodal').modal('hide');
			$('.sendinvitation').show();
			$('#inviteusererrormsg').modal('show');
			$('.emailuser').val('');
			$('#messagedescription').val('');
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
		checkemails:function(emails){
			var staticEmailArray=[];
			var emaildata=emails.split(',');
			if(emaildata.length!=0){
				$.each(emaildata, function(index, value) {
					var response="";
					var obj={};
					obj.organizationid=$('#organizationid').val();
					obj.email=value;
					response=courseData.checkuserData(obj);
					if(response=="failure"){
						staticEmailArray.push(value);	
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
					var output = "";
					$.ajax({
						type : 'GET',
						url : url,			
						data:data,
						beforeSend:self.startProgress(),							
						success : function(data, textStatus, jqXHR){
							output = data;
							if(output=="SUCCESS"){
								self.endProgress(output);
							}
							else{							
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
		
		asyncajaxs:function(url,data){
			var self=this;
			if(_.isString(url))	{
				if(_.isObject(data) && !_.isArray(data)){
					var output = "";
					$.ajax({
						type : 'GET',
						url : url,			
						data:data,
						beforeSend:self.startprogres(),							
						success : function(data, textStatus, jqXHR){
							output = data;
							if(output=="SUCCESS"){
								self.endprogres(output);
							}
							else{							
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
};