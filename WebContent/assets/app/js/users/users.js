$(document).ready(function(){
	
	 $('.leftsideli').click(function (e) {
		    //save the latest tab; use cookies if you like 'em better:
		      sessionStorage.setItem('lastTab', $(e.target).attr('class'));
		  });
	 
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
	$('.sendingmess').hide();	
	domreadyobj.domreadyfn();
	userinfoobj.sendMessage();
	$('.inviteuserlist').click(function(){
		if($(this).attr('b')==="tour"){
			event.preventDefault();	
		}else{
			$('.leftsideli').removeClass('active');
			$(this).addClass('active');
			userinfoobj.inviteUsers();	
		}
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
	
	if(sessionStorage.getItem('lastTab')){
		  var lastTab = sessionStorage.getItem('lastTab');
		  if(lastTab.search("allusersbtn")>=0){
			  switchtab(1);
		  }
		  else if(lastTab.search("inviteuserlist")>=0){
			  switchtab(2);
		  }
	     else if(lastTab.search("enrollusersbtn")>=0){
	    	 switchtab(3);
		  }
	     else if(lastTab.search("removesersbtn")>=0){
	    	 switchtab(4);
		  }
	     else if(lastTab.search("approvallistlinkbtn")>=0){
	    	 switchtab(5);
		  }
	     else if(lastTab.search("pendinglistlinkbtn")>=0){
	    	 switchtab(6);
		  }
	     else if(lastTab.search("approvedlistlinkbtn")>=0){
	    	 switchtab(7);
		  }
	     else if(lastTab.search("allcertificate")>=0){
	    	 switchtab(8);
		  }
	     else if(lastTab.search("pendingcertificate")>=0){
	    	 switchtab(9);
		  }
	     else if(lastTab.search("issuedcertificate")>=0){
	    	 switchtab(10);
		  }
	     else if(lastTab.search("paidusersbtn")>=0){
	    	 switchtab(11);
		  }
		  
	  }
	function switchtab(e){
	 switch(e){
		  case 1:{
			  $('.allusersbtn').trigger('click');
			  break;
		  }
		  case 2:{
			  $('.inviteuserlist').trigger('click');
			  break;
		  }
		  case 3:{
			  $('.enrollusersbtn').trigger('click');
			  break;
		  }
		  case 4:{
			  $('.removesersbtn').trigger('click');
			  break;
		  }
		  case 5:{
			  $('.approvallistlinkbtn').trigger('click');
			  break;
		  }	
		  case 6:{
			  $('.pendinglistlinkbtn').trigger('click');
			  break;
		  }	
		  case 7:{
			  $('.approvedlistlinkbtn').trigger('click');
			  break;
		  }	
		  case 8:{
			  $('.allcertificate').trigger('click');
			  break;
		  }	
		  case 9:{
			  $('.pendingcertificate').trigger('click');
			  break;
		  }	
		  case 10:{
			  $('.issuedcertificate').trigger('click');
			  break;
		  }	
		  case 11:{
			  $('.paidusersbtn').trigger('click');
			  break;
		  }	
		  
		  }
	}
	
});

domreadyobj={
		domreadyfn:function(){
			var selecteditem=$('.linkattr').attr('link');
			if(selecteditem=="eu"){
				$('.enrollusersbtn').trigger('click');
			}
			else if(selecteditem=="cr"){
				$('.leftsideli').removeClass('active');
				$('.allpedlist').addClass('active');
				userPaymentobj.loadPendinglist("Pending","P");
			}
			else{
				userinfoobj.loadalluserinfo();
			}
			countobj.loadCountsData();
			$('.allusersbtn').click(function(event)	{
				if($(this).attr('b')==="tour"){
					event.preventDefault();	
				}else{
					$('.leftsideli').removeClass('active');
					$(this).addClass('active');
					userinfoobj.loadalluserinfo();	
				}
			});
			$('.removesersbtn').click(function(){
				if($(this).attr('b')==="tour"){
					event.preventDefault();	
				}else{
					$('.leftsideli').removeClass('active');
					$(this).addClass('active');				
					countobj.alldetails();	
				}
			});
			$('.enrollusersbtn').click(function(event){
				if($(this).attr('b')==="tour"){
					event.preventDefault();	
				}else{
					$('.leftsideli').removeClass('active');
					$(this).addClass('active');
					//	var ans=$('#organizationid').val();
					var obj={};
					obj.organizationid=$('#organizationid').val();
					var users=courseData.getEnrolledUsers(obj);
					renderTemplate("#enrolleduserinfotmpl", users, "#contentdisplay");
					$('.courseinfobody').slimscroll({height: "475px"});   
					$('.enrolcoursebtn').tooltip();
					userinfoobj.pagination();
					searchvalues();
					$('.enrolcoursebtn').click(function(){
						$('#enrollcourseinfomodal').modal('show');
						var obj={};
						obj.organizationid=$('#organizationid').val(); 
						obj.orgpersonid=$(this).attr('orgpersonid');
						var users=courseData.getEnrolledCourses(obj);
						renderTemplate("#courseinfotmpl", users, "#enrollcourseinfotable");
						var firstname=_.uniq(_.pluck(users,'firstname'));
						$('.enrolluser').html(firstname);
					});	
				}	
				
			});
			$('.allauthorsbtn').click(function()	{
				$('.leftsideli').removeClass('active');
				$(this).addClass('active');
				authorsinfoobj.loadallauthorsinfo();
			});
			
			
			$('.paidusersbtn').click(function(){
				if($(this).attr('b')==="tour"){
					event.preventDefault();	
				}else{
					$('.leftsideli').removeClass('active');
					$(this).addClass('active');
					paidUser.home();
				}				
			});
			$('.certificatebtn').click(function(){
				if($(this).attr('b')==="tour"){
					event.preventDefault();	
				}else{
					$('.leftsideli').removeClass('active');
					$(this).addClass('active');
					userCertificatesobj.loadCertificates($(this).attr('certificatestatus'));	
				}				
			});
			$('.pendingcertificatebtn').click(function(){
				if($(this).attr('b')==="tour"){
					event.preventDefault();	
				}else{
					$('.leftsideli').removeClass('active');
					$(this).addClass('active');
					userCertificatesobj.pendingCertificates($(this).attr('certificatestatus'));	
				}
			});
			$('.issuecertificatebtn').click(function(){
				if($(this).attr('b')==="tour"){
					event.preventDefault();	
				}else{
					$('.leftsideli').removeClass('active');
					$(this).addClass('active');
					userCertificatesobj.issueCertificates($(this).attr('certificatestatus'));	
				}
			});
			$('.approvallistlinkbtn').click(function(){
				if($(this).attr('b')==="tour"){
					event.preventDefault();	
				}else{
					$('.leftsideli').removeClass('active');
					$(this).addClass('active');
					userPaymentobj.loadInvoices($(this).attr('paymenttype'),$(this).attr('status'));	
				}
			});
			$('.pendinglistlinkbtn').click(function(){
				if($(this).attr('b')==="tour"){
					event.preventDefault();	
				}else{
					$('.leftsideli').removeClass('active');
					$(this).addClass('active');
					userPaymentobj.loadPendinglist($(this).attr('paymenttype'),$(this).attr('status'));	
				}				
			});

			$('.approvedlistlinkbtn').click(function(){
				if($(this).attr('b')==="tour"){
					event.preventDefault();	
				}else{
					$('.leftsideli').removeClass('active');
					$(this).addClass('active');
					userPaymentobj.loadApprovedlist($(this).attr('paymenttype'),$(this).attr('status'));	
				}
			});
		}
};

retiveuserobj={
		retrivefn:function(){
			countobj.loadCountsData();
			$('.restoreuserbtn').click(function(){
				if(confirm("Are you sure want to restore?")){						
					var orgpersonid=$(this).attr('orgpersonid');
					var loginobj={};	
					loginobj.orgpersonid=orgpersonid;
					loginuser=courseData.getRetriveUserDetails(loginobj);
					$('#usermessage').modal('show');	
					$('.succesmsg').text("User is Restore Successfully");
					countobj.alldetails();
				}
			});

		}

},

countobj={
		loadCountsData:function(){
			/*var self=this;			
			self.usersCount();
			self.authorsCount();
			self.paymentCount();
			self.certicateCount();
			self.removeuserCount();
			self.inviteuserCount();*/
			var list = Ajax.get(courseData.getContextPath()+"/rest/adminusers/count/",undefined,"json","application/json");
			
			$('.allusercount').text(list[0].registedusers);
			$('.allauthorcount').text(list[0].authors);
			$('.inviteusercount').text(list[0].invitedusers);
			$('.alluserscount').text(list[0].enrolleduser);
			$('.removeusercount').text(list[0].deactivatedusers);
			$('.paidusercount').text(list[0].paidusers);
			
			$('.onlinepaiduserscount').text(list[0].onlinepaidusers);
			$('.freepaiduserscount').text(list[0].freepaidusers);
			$('.allapprovalcount').text(list[0].allpayment);				
			$('.pendingapprovalcount').text(list[0].pendingpayment);				
			$('.approvedsuccesscount').text(list[0].paidpayment);
			
			$('.allcertificatecount').text(list[0].allcertificaterequests);
			$('.approvedcertificatecount').text(list[0].pendingcertificates);
			$('.issuedcertificatecount').text(list[0].approvedcertificates);
			
		},
		alldetails:function(){
			var removeuserdetails=courseData.getRemoveUserDetails();
			renderTemplate("#removeduserinfotmpl", removeuserdetails, "#contentdisplay");
			$('.restoreuserbtn').tooltip();
			userinfoobj.pagination();
			searchvalues();
			retiveuserobj.retrivefn();
			$('.moreinfouserbtn').click(function(){
				$('#moreinfomodal').modal('show');
				userinfoobj.loadprofiledetail($(this).attr('orgpersonid'));
				userinfoobj.loadlearningcourses($(this).attr('orgpersonid'));
			});	
			
		},	
		removeuserCount:function(){
			var courseobj={};
			var removeusercountlist=courseData.getRemoveUserDetails(courseobj);
			if(removeusercountlist!=undefined){
				var removeusercount=_.uniq(_.pluck(removeusercountlist,'personid')).length;
				$('.removeusercount').text(removeusercount);
			}else{
				$('.removeusercount').text("0");
			}			
		},
		usersCount:function(){
			var courseidobj={};
			//	courseidobj.courseid="null";
			var userscountlist=courseData.getAllUsersDetails(courseidobj);
			if(userscountlist!=undefined){
				var allusercount=_.pluck(userscountlist,'personid').length;
				$('.allusercount').text(allusercount);
			}else{
				$('.allusercount').text("0");
			}
			var usersCount=courseData.getUsersCount();
			if(usersCount!=undefined){
				if(usersCount.enrolledUsers!=undefined){
					$('.alluserscount').text(usersCount.enrolledUsers);	
				}
				else{
					$('.alluserscount').text("0");
				}				
			}	
			else{
				$('.alluserscount').text("0");
			}
		},
		authorsCount:function(){
			var authoridobj={};
			authoridobj.courseid="null";
			var athorscountlist=courseData.getAllAuthorsDetails(authoridobj);
			if(athorscountlist!=undefined){
				var allauthorscount=_.uniq(_.pluck(athorscountlist,'personid')).length;
				$('.allauthorcount').text(allauthorscount);
			}else{
				$('.allauthorcount').text("0");
			}
		
		},
		inviteuserCount:function(){
			/*var personobj={};
			personobj.personid="null";
			var inviteusercount=_.uniq(_.pluck(courseData.getWaitingUsers(personobj),'personid')).length;
			$('.inviteusercount').text(inviteusercount);*/
			var inviteusercount=courseData.getWaitingUsers();
			if(inviteusercount!=undefined){
				$('.inviteusercount').text(inviteusercount.length);
			}else{
				$('.inviteusercount').text("0");
			}
		},
		paymentCount:function(){
			var allapprovalList=userPaymentobj.getPaymentList("All","All");
			if(allapprovalList!=undefined){
				$('.allapprovalcount').text(allapprovalList.length);				
			}
			else{
				$('.allapprovalcount').text("0");

			}
			var paymentpendingList=userPaymentobj.getPaymentList("Pending","P");
			if(paymentpendingList!=undefined){
				$('.pendingapprovalcount').text(paymentpendingList.length);				
			}
			else{
				$('.pendingapprovalcount').text("0");
			}
			var approvedPaymentList=userPaymentobj.getPaymentList("Approved","A");
			if(approvedPaymentList!=undefined){
				$('.approvedsuccesscount').text(approvedPaymentList.length);				
			}
			else{
				$('.approvedsuccesscount').text("0");
			}
		},
		certicateCount:function(){
			var allCertificateList=userCertificatesobj.getCertificateList("All");
			if(allCertificateList!=undefined){
				$('.allcertificatecount').text(allCertificateList.length);				
			}
			else{
				$('.allcertificatecount').text("0");

			}
			var approvedCertificateList=userCertificatesobj.getCertificateList("P");
			if(approvedCertificateList!=undefined){
				$('.approvedcertificatecount').text(approvedCertificateList.length);				
			}
			else{
				$('.approvedcertificatecount').text("0");
			}
			var issuedCertificateList=userCertificatesobj.getCertificateList("A");
			if(issuedCertificateList!=undefined){
				$('.issuedcertificatecount').text(issuedCertificateList.length);				
			}
			else{
				$('.issuedcertificatecount').text("0");
			}
		}
};
userinfoobj={
		loadalluserinfo:function(){
			var self=this;
			var alluserdetails=courseData.getAllUsersDetails();
			self.renderAllUserDetails(alluserdetails);
			countobj.loadCountsData();
		},
		renderAllUserDetails:function(alluserdetails){
			var self=this;
			var currentPage=0;
			renderTemplate("#allusertmpl", alluserdetails, "#contentdisplay");
			$('.removebtn').tooltip();
			$('.messageuserbtn').tooltip();
			self.pagination();
		
			$('.inviteusermodal').click(function(){
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
			
			searchvalues();
			$('.allusersbtn').addClass('active');
			$('.messageuserbtn').click(function(){
				$('.chatername').text($(this).attr('personname'));
				$('.popwindow').show();
				self.loadparticularusersmessage($(this).attr('orgpersonid'));
				$('.sendmessagebtn,.chatmessage').attr('chatterpersonid',$(this).attr('orgpersonid'));
			});
			$('.sendmessagebtn').click(function(){
				var messagedescription=$('.chatmessage').val();
				if(messagedescription!=''){
					self.sendMessageData($(this).attr('chatterpersonid'),messagedescription);
				}
			});
			$('#appendedInputButton').keyup(function(e){
			    if(e.keyCode == 13)
			    {
			    	var messagedescription=$('.chatmessage').val();
					if(messagedescription!=''){
						self.sendMessageData($(this).attr('chatterpersonid'),messagedescription);
					}
			    }
			});
			$('.moreinfouserbtn').click(function(){
				$('#moreinfomodal').modal('show');
				self.loadprofiledetail($(this).attr('orgpersonid'));
				self.loadlearningcourses($(this).attr('orgpersonid'));
			});	

			$('.removebtn').click(function(){

				/*$('#detailmodal').modal('show');
				self.loadlearncourses($(this).attr('orgpersonid'));*/
				//self.loadlearncourses($(this).attr('orgpersonid'));	
				if(confirm("Are you sure want to remove?")){
					var orgpersonid=$(this).attr('orgpersonid');
					var loginidobj={};		     	
					loginidobj.orgpersonid=orgpersonid;
					loginusers=courseData.getDeletelogin(loginidobj);
					$('#usermessage').modal('show');	
					$('.succesmsg').text("User is Removed Successfully");
					userinfoobj.loadalluserinfo();
				}
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
		},
		startProgress:function(){
			$('.sendinvitation').hide();
			$('.sendingmess').show();
			//countobj.loadCountsData();
			},
		endProgress:function(message){
			$('.sendingmess').hide();
			$('.sendinvitation').show();
			$('.emailuser').val('');
			$('.description').html(message);
			$('#messagedescription').val('');
			$('#inviteusersmodal').modal('hide');
			$('#inviteusermessage').modal('show');
			countobj.loadCountsData();
		},
		errormsg:function(){
			$('.sendinvitation').hide();
			$('.sendingmess').hide();
			//countobj.loadCountsData();
			$('#inviteusersmodal').modal('hide');
			$('.sendinvitation').show();
			$('#inviteusererrormsg').modal('show');
			$('.emailuser').val('');
//			$('.description').html(message);
			$('#messagedescription').val('');
		},
		
		inviteUsers:function(){
			var alluserdetails=courseData.getWaitingUsers();
			renderTemplate("#waitingusertmpl", alluserdetails, "#contentdisplay");
			userinfoobj.pagination();
			searchvalues();
		},
		loadprofiledetail:function(personid){
			var self=this;
			var personidobj={};
			personidobj.personid=personid;
			var userprofileValues=courseData.loadProfileData(personidobj);
			self.renderProfileValue(userprofileValues);
		},
		renderProfileValue:function(userprofileValues){
			if(userprofileValues==''|| userprofileValues.length<0){
				renderTemplate('#datanotfoundtmpl',userprofileValues,'#profileinfotable');	
			}
			else{
				$('#warningtextdiv').hide();
				renderTemplate('#profileinfotmpl',userprofileValues,'#profileinfotable');	
			}
		},
		loadlearningcourses:function(personid){
			var self=this;
			var personidobj={};
			personidobj.personid=personid;
			var coursesList=courseData.getIndividualCourseswithoutTime(personidobj);
			self.renderLearningCourses(coursesList);
		},
		renderLearningCourses:function(coursesList){
			var courseobjlist={};
			courseobjlist.courseliststatusA = _.where(coursesList,{courseenrollmentstatus:"A"});
			courseobjlist.courseliststatusC = _.where(coursesList,{courseenrollmentstatus:"C"});
			courseobjlist.courseliststatusW = _.where(coursesList,{courseenrollmentstatus:"W"});
			courseobjlist.courseliststatusB = _.where(coursesList,{courseenrollmentstatus:"B"});
			renderTemplate('#coursesinfotmpl',courseobjlist,'#coursesinfotable');
		},
		loadparticularusersmessage:function(orgpersonid){
			var self=this;
			var particularusersmessagelist=self.getMessages(orgpersonid);
			if(particularusersmessagelist == '' || particularusersmessagelist=="false"){
				renderTemplate("#nomessagestmpl",particularusersmessagelist,"#messagetable");
				self.chatclose();
			}
			else{
				renderTemplate("#messagestmpl",particularusersmessagelist,"#messagetable");
				self.chatclose();
			}
		},
		getMessages:function(orgpersonid){
			var personidobj={};
			personidobj.toOrgpersonid=orgpersonid;
			var particularusersmessagelist=courseData.getMessagesForuser(personidobj);
			return particularusersmessagelist;	
		},
		sendMessageData:function(personid,messagedescription){
			var self=this;
			var personidobj={};
			personidobj.toOrgpersonid=personid;
			personidobj.messagedescription=messagedescription;
			self.writeMessage(personidobj);	
		},
		writeMessage:function(personidobj){
			var self=this;
			var successvalue=courseData.sendMessage(personidobj);
			if(successvalue!="false"){
				$('.chatmessage').val('');
				self.loadparticularusersmessage(personidobj.toOrgpersonid); 
			}
		}, 
		chatclose:function(){
			$('.closechatwindowbtn').click(function(){
				$('.popwindow').hide();
			});	
		},
		sendMessage:function(){
			var self=this;
			$('.sendinvitation').click(function(){
				$('#errormessage').show();
				var obj={};
				var emails=$('.emailuser').val();
				var messagedescription=$('#messagedescription').val();
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
										countobj.loadCountsData();
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
			});
			
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
		}
};

userCertificatesobj={
		loadCertificates:function(certificatestatus){
			var self=this;
			var certificateList=self.getCertificateList(certificatestatus);
			self.renderCertificates(certificateList);
			self.setTitleforRightPane(certificatestatus);
		},
		pendingCertificates:function(certificatestatus){
			var self=this;
			var certificateList=self.getCertificateList(certificatestatus);
			self.renderPendingcertificates(certificateList);
			self.setTitleforRightPane(certificatestatus);
		},
		issueCertificates:function(certificatestatus){
			var self=this;
			var certificateList=self.getCertificateList(certificatestatus);
			self.renderIssuecertificates(certificateList);
			self.setTitleforRightPane(certificatestatus);
		},
		renderCertificates:function(certificateList){
			var self=this;
			renderTemplate("#allcertificatedetailstmpl",certificateList,"#contentdisplay");
			searchvalues();
			userinfoobj.pagination();				
			self.setCertificateStatus($('.certificatestatusbtn'));
			$('.certificateApprovebtn').click(function(){
				var checkstr =  confirm('Are you sure you want to issue this certificate');
				if(checkstr == true)	{
				self.approveCertificateData($(this).attr('coursecertificateid'));
				}
			});
		},
		renderPendingcertificates:function(certificateList){
			var self=this;
			renderTemplate("#pendingcertificatedetailstmpl",certificateList,"#contentdisplay");
			searchvalues();
			userinfoobj.pagination();
			self.setCertificateStatus($('.certificatestatusbtn'));
			$('.certificateApprovebtn').click(function(){
				var checkstr =  confirm('Are you sure you want to issue this certificate');
				if(checkstr == true)	{
				self.pendingCertificateData($(this).attr('coursecertificateid'));
				}
			});
		},
		renderIssuecertificates:function(certificateList){
			var self=this;
			renderTemplate("#issuedcertificatedetailstmpl",certificateList,"#contentdisplay");
			searchvalues();
			userinfoobj.pagination();
			self.setCertificateStatus($('.certificatestatusbtn'));

		},
		approveCertificateData:function(coursecertificateid,coursecertificatestatus){
			var self=this;
			var certificateidobj={};
			certificateidobj.coursecertificateid=coursecertificateid;
			self.approveCertificate(certificateidobj);	
		},
		approveCertificate:function(certificateidobj){
			var self=this;
			var approvedStatus=courseData.approveCertificateRequest(certificateidobj);
			if(approvedStatus=="1"){
				$('#msginfomodal').modal('show');	
				$('.succesmsg').text("Certificate issued");
				setTimeout(function(){
					$('#msginfomodal').modal('hide');										
				},1000);
				self.loadCertificates($('.rightpanetitle').attr('coursecertificatestatus'));
				countobj.loadCountsData();
			}
		},
		pendingCertificateData:function(coursecertificateid,coursecertificatestatus){
			var self=this;
			var certificateidobj={};
			certificateidobj.coursecertificateid=coursecertificateid;
			self.pendingCertificate(certificateidobj);	
		},
		pendingCertificate:function(certificateidobj){
			var self=this;
			var approvedStatus=courseData.approveCertificateRequest(certificateidobj);
			if(approvedStatus=="1"){
				$('#msginfomodal').modal('show');	
				$('.succesmsg').text("Certificate issued");
				setTimeout(function(){
					$('#msginfomodal').modal('hide');										
				},1000);
				self.pendingCertificates($('.rightpanetitle').attr('coursecertificatestatus'));
				countobj.loadCountsData();
			}
		},
		setCertificateStatus:function(selector){
			selector.each(function(){
				var coursecertificatestatus=$(this).attr('coursecertificatestatus');
				if(coursecertificatestatus=="A"){
					$(this).html("<i class='icon-ok-sign approvedicon'></i>Issued");
					$(this).attr('disabled','disabled');
					$(this).removeClass('certificateApprovebtn');
				}
				else if(coursecertificatestatus=="P"){
					$(this).text('issue').removeClass('btn-gray').addClass('btn-blue');
					$(this).addClass('certificateApprovebtn');
				}
			});	
		},
		setTitleforRightPane:function(certificatestatus){	
			$('.rightpanetitle').attr('coursecertificatestatus',certificatestatus);
			if(certificatestatus=="All"){
				$('.rightpanetitle').text('All Certificate Requests');
				$('.rightpanetitle').attr('coursecertificatestatus','All');
			}
			else if(certificatestatus=="A"){
				$('.rightpanetitle').text('Approved Certificate Requests');
				$('.rightpanetitle').attr('coursecertificatestatus','A');
			}
			else{
				$('.rightpanetitle').text('Pending Certificate Requests');
				$('.rightpanetitle').attr('coursecertificatestatus','P');
			}
		},
		getCertificateList:function(certificatestatus)	{
			var courseidobj={};
			courseidobj.courseid="null";
			var CertificatesList=courseData.getCertificates(courseidobj);
			if(CertificatesList!==undefined){
				if(certificatestatus=="All"){
					var Certificates=CertificatesList.allCerticates;
					return Certificates;
				}
				else if(certificatestatus=="A"){
					var Certificates=CertificatesList.issuedCertificates;
					return Certificates;
				}
				else{
					var Certificates=CertificatesList.pendingCertificates;
					return Certificates;
				}	
			}
			else{
				return undefined;
			}			
		}
};

userPaymentobj={	
		loadInvoices:function(paymenttype,status){
			var self=this;
			var paymentApprovalList=self.getPaymentList(paymenttype,status);
			self.renderPaymentDetails(paymentApprovalList,paymenttype,status);	
		},
		loadPendinglist:function(paymenttype,status){
			var self=this;
			var paymentApprovalList=self.getPaymentList(paymenttype,status);
			self.renderPendingpaymentDetails(paymentApprovalList,paymenttype,status);	
		},
		loadApprovedlist:function(paymenttype,status){
			var self=this;
			var paymentApprovalList=self.getPaymentList(paymenttype,status);
			self.renderApprovedpaymentDetails(paymentApprovalList,paymenttype,status);	
		},
		renderPaymentDetails:function(paymentApprovalList,paymenttype,status){  
			var self=this;
			renderTemplate("#allpaymentdetailstmpl",paymentApprovalList,"#contentdisplay");
			$(".ordernoval").each(function(){
				if($(this).text()=="null"){
					$(this).text('-');
				}
			});
			userinfoobj.pagination();
			searchvalues(); 
			self.rightpaneTitle(status,paymenttype);
			self.setPaymentStatus($('.paymentstatusbtn'));		
			$('.paymentapprovebtn').click(function(){
				var checkstr =  confirm('Are you sure you want to approve this payment?');
				if(checkstr == true)	{
				var enrollmentidobj={};
				enrollmentidobj.courseenrollmentid=$(this).attr('courseenrollmentid');
				enrollmentidobj.courseenrollmentstatus="A";					
				var paymentidobj={};
				paymentidobj.paymentstatus="A";
				paymentidobj.paymentid=$(this).attr('paymentid');
				paymentidobj.orderno=$(this).attr('orderno');
				var enrollobj={};
				enrollobj.courseenrollmentid=$(this).attr('courseenrollmentid');
				var royaltyPayment = courseData.createRoyaltyPayment(enrollobj,1);
				self.approvepaymentprocess(enrollmentidobj,paymentidobj,status,paymenttype);
				$('#msginfomodal').modal('show');	
				$('.succesmsg').text("Approved Successfully");
				setTimeout(function(){
					$('#msginfomodal').modal('hide');										
				},3000);
				countobj.loadCountsData();
				}
			});		
		},
		renderPendingpaymentDetails:function(paymentApprovalList,paymenttype,status){  
			var self=this;
			renderTemplate("#pendingpaymentdetailstmpl",paymentApprovalList,"#contentdisplay");
			$(".ordernoval").each(function(){
				if($(this).text()=="null"){
					$(this).text('-');
				}
			});
			userinfoobj.pagination();
			searchvalues();
			self.rightpaneTitle(status,paymenttype);
			self.setPaymentStatus($('.paymentstatusbtn'));		
			$('.paymentapprovebtn').click(function(){
				var checkstr =  confirm('Are you sure you want to approve this payment?');
				if(checkstr == true)	{
				var enrollmentidobj={};
				enrollmentidobj.courseenrollmentid=$(this).attr('courseenrollmentid');
				enrollmentidobj.courseenrollmentstatus="A";					
				var paymentidobj={};
				paymentidobj.paymentstatus="A";
				paymentidobj.paymentid=$(this).attr('paymentid');
				paymentidobj.orderno=$(this).attr('orderno');
				var enrollobj={};
				enrollobj.courseenrollmentid=$(this).attr('courseenrollmentid');
				var royaltyPayment = courseData.createRoyaltyPayment(enrollobj,1);
				self.pendingpaymentprocess(enrollmentidobj,paymentidobj,status,paymenttype);
				$('#msginfomodal').modal('show');	
				$('.succesmsg').text("Approved Successfully");
				setTimeout(function(){
					$('#msginfomodal').modal('hide');										
				},1000);
				countobj.loadCountsData();
				}
			});	

		},

		renderApprovedpaymentDetails:function(paymentApprovalList,paymenttype,status){  
			var self=this;
			renderTemplate("#approvedpaymentdetailstmpl",paymentApprovalList,"#contentdisplay");
			$(".ordernoval").each(function(){
				if($(this).text()=="null"){
					$(this).text('-');
				}
			});
			userinfoobj.pagination();
			searchvalues();
			self.rightpaneTitle(status,paymenttype);
			self.setPaymentStatus($('.paymentstatusbtn'));		
			$('.paymentapprovebtn').click(function(){
				var checkstr =  confirm('Are you sure you want to approve this payment?');
				if(checkstr == true)	{
				var enrollmentidobj={};
				enrollmentidobj.courseenrollmentid=$(this).attr('courseenrollmentid');
				enrollmentidobj.courseenrollmentstatus="A";					
				var paymentidobj={};
				paymentidobj.paymentstatus="A";
				paymentidobj.paymentid=$(this).attr('paymentid');
				paymentidobj.orderno=$(this).attr('orderno');
				var enrollobj={};
				enrollobj.courseenrollmentid=$(this).attr('courseenrollmentid');
				var royaltyPayment = courseData.createRoyaltyPayment(enrollobj,1);
				self.pendingpaymentprocess(enrollmentidobj,paymentidobj,status,paymenttype);
				$('#msginfomodal').modal('show');	
				$('.succesmsg').text("Approved Successfully");
				setTimeout(function(){
					$('#msginfomodal').modal('hide');										
				},1000);
				countobj.loadCountsData();
				}
			});		
		},



		getPaymentList:function(paymenttype,approvedstatus){			
			var paymentstatusobj={};
			paymentstatusobj.paymenttype=paymenttype;
			paymentstatusobj.approvedstatus=approvedstatus;
			paymentstatusobj.courseid="null";
			var allPaymentList=courseData.getInvoices(paymentstatusobj);
			return allPaymentList;
		},
		rightpaneTitle:function(status,paymenttype){
			$('.rightpanetitle').attr('paymenttype',paymenttype);
			$('.rightpanetitle').attr('status',status);
			if(status=="P")	{
				$('.rightpanetitle').text('Pending Course Requests');
			}
			else if(status=="A"){
				$('.rightpanetitle').text('Approved Payment Details');
			}
			else{
				$('.rightpanetitle').text('All Payment Details');
			}
		},
		setPaymentStatus:function(selector){
			selector.each(function(){
				var paymentStatus=$(this).attr('courseenrollmentstatus');
				if(paymentStatus=="P"){
					$(this).text('Approve').removeClass('btn-gray').addClass('btn-blue');
					$(this).addClass('paymentapprovebtn');					
				}
				else if(paymentStatus=="C"){					
					$(this).html("<i class='icon-ok-sign approvedicon'></i>Completed");
					$(this).attr('disabled','disabled');
					$(this).removeClass('paymentapprovebtn');					
				}
				else{
					$(this).html("<i class='icon-ok-sign approvedicon'></i>Approved");
					$(this).attr('disabled','disabled');
					$(this).removeClass('paymentapprovebtn');
				}
			});	
		},		


		approvalpaymentprocedd:function(enrollmentidobj,paymentidobj){
			var self=this;
			courseData.updateCourseEnrollmentStatus(enrollmentidobj);
			courseData.updateCoursePaymentStatus(paymentidobj);
			self.loadApprovedlist($('.rightpanetitle').attr('paymenttype'),$('.approvedlistlinkbtn.active').attr('status'));
			//self.loadInvoices($('.rightpanetitle').attr('paymenttype'),$('.pendinglistlinkbtn.active').attr('status'));
		},

		pendingpaymentprocess:function(enrollmentidobj,paymentidobj){
			var self=this;
			courseData.updateCourseEnrollmentStatus(enrollmentidobj);
			courseData.updateCoursePaymentStatus(paymentidobj);
			self.loadPendinglist($('.rightpanetitle').attr('paymenttype'),$('.pendinglistlinkbtn.active').attr('status'));
			//self.loadInvoices($('.rightpanetitle').attr('paymenttype'),$('.pendinglistlinkbtn.active').attr('status'));
		},
		approvepaymentprocess:function(enrollmentidobj,paymentidobj){
			var self=this;
			courseData.updateCourseEnrollmentStatus(enrollmentidobj);
			courseData.updateCoursePaymentStatus(paymentidobj);
			self.loadInvoices($('.rightpanetitle').attr('paymenttype'),$('.approvallistlinkbtn.active').attr('status'));

		},
};
authorsinfoobj={
		loadallauthorsinfo:function(){
			var self=this;
			/*var courseidobj={};
			courseidobj.courseid="null";*/
			var allauthorsdetails=courseData.getAllAuthorsDetails();
			self.renderAllAuthorsDetails(allauthorsdetails);
		},
		renderAllAuthorsDetails:function(allauthorsdetails){
			var self=this;
			renderTemplate("#allauthorstmpl", allauthorsdetails, "#contentdisplay");
			$('.authorcourses').tooltip();
			userinfoobj.pagination();
			searchvalues();
			$('.authorcourses').click(function(){
				$('#authorcoursemodal').modal('show');
				self.loadauthorcourses($(this).attr('orgpersonid'));
			});
		},
		loadauthorcourses:function(authorid){
			var self=this;
			var authoridobj={};
			authoridobj.authorid=authorid;
			var authroCourseValues=courseData.loadAuthorCourses(authoridobj);
			self.renderAuthroCourses(authroCourseValues);
		},
		renderAuthroCourses:function(authroCourseValues){
			var self=this;
			if(authroCourseValues==''|| authroCourseValues.length<0){
				renderTemplate('#datanotfoundtmpl',authroCourseValues,'#authorcourseinfotable');				
			}
			else{
				$('#warningtextdiv').hide();
				renderTemplate('#authorcourseinfotmpl',authroCourseValues,'#authorcourseinfotable');
				$('.aurthorcrstable').slimscroll({height: "450px"});
				self.loadauthorcoursestudents($('.authorcoursestudents:first').attr('courseid'),$('.authorcoursestudents:first').attr('coursetitle'));
			}
			$('.authorcoursestudents').click(function(){				
				$('.authorcoursestudents').parent().parent().removeClass('activecourse');
				$(this).parent().parent().addClass('activecourse');				
				self.loadauthorcoursestudents($(this).attr('courseid'),$(this).attr('coursetitle'));
			});
		},
		loadauthorcoursestudents:function(authorcourseid,coursetitle){

			var self=this;
			var stcourseidobj={};
			stcourseidobj.authorcourseid=authorcourseid;

			var authroCourseStudents=courseData.loadAuthorCourseStudents(stcourseidobj);
			self.renderAuthorCourseStudents(authroCourseStudents,coursetitle);
		},
		renderAuthorCourseStudents:function(authroCourseStudents,coursetitle){
			if(authroCourseStudents==undefined){
				renderTemplate('#authorcoursestudentsinfotmpl',null,'#authorcoursestudentinfo');
				$("#coursetitlespan").text(coursetitle);
			}
			else{
				$('#warningtextdiv').hide();
				renderTemplate('#authorcoursestudentsinfotmpl',authroCourseStudents,'#authorcoursestudentinfo');
				$('.coursestudentstab').slimscroll({height: "450px"});
				$("#coursetitlespan").text(coursetitle);
			}
		}
};

function searchvalues(){
	var tableRowsClass = $('.list-search tbody tr');
	$('.system-search').keyup( function() {
		if($('.system-search').val()!=""){
			$('.paginationtab tbody tr').removeAttr("style");
			var that = this;
			var tableBody = $('.list-search tbody');
			$('.search-sf').remove();
			tableRowsClass.each( function(i, val) {
				var rowText = $(val).text().toLowerCase();
				var inputText = $(that).val().toLowerCase();
				if(inputText != '')	{
					$('.search-query-sf').remove();
					tableBody.prepend('<tr class="search-query-sf"><td colspan="7"><strong>Searching for: "'
							+ $(that).val()
							+ '"</strong></td></tr>');
				}else{
					$('.search-query-sf').remove();
					$('.nav').show();
				}
				if( rowText.indexOf( inputText ) == -1 ){
					tableRowsClass.eq(i).hide();
				}else{
					$('.search-sf').remove();
					tableRowsClass.eq(i).show();				
				}
			});
			if(tableRowsClass.children(':visible').length == 0){
				tableBody.append('<tr class="search-sf"><td class="text-muted" colspan="7">No entries found.</td></tr>');
				$('.pagination').text("");
			}
			$('.pagination').text("");
		}else{
			$(".search-sf").remove();
			$(".search-query-sf").remove();
			tableRowsClass.show();
			userinfoobj.pagination();
		}
	});
}

paidUser = {
		
		home : function(){
			this.view();
		},
		
		view : function(){
			var thislist = this.list();
			renderTemplate("#paidusertmpl", thislist, "#contentdisplay");
			applypagination($('.paidUserRow'), 15); 
			
			$('.moreinfouserbtn').click(function(){
				$('#moreinfomodal').modal('show');
				userinfoobj.loadprofiledetail($(this).attr('orgpersonid'));
				userinfoobj.loadlearningcourses($(this).attr('orgpersonid'));
			});	
		},
		
		list : function(){
			var list = Ajax.get(courseData.getContextPath()+"/rest/adminusers/paidUsers/",undefined,"json","application/json");
			return list = _.isObject(list) ? list : null;
		}
};