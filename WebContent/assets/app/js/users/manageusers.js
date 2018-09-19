$(document).ready(function(){
	domreadyobj.domreadyfn();
	userinfoobj.loadalluserinfo();
});

domreadyobj={
		returncourseid:function(){
			var courseidbj={};
			courseidbj.courseid=$('[name=courseid]').val();
			return courseidbj;
		},
		domreadyfn:function(){
			countobj.loadCountsData();
			$('.allusersbtn').click(function()	{
				$('.leftsideli').removeClass('active');
				$(this).addClass('active');
				userinfoobj.loadalluserinfo();
			});
			$('.certificatebtn').click(function(){
				$('.leftsideli').removeClass('active');
				$(this).addClass('active');
				userCertificatesobj.loadCertificates($(this).attr('certificatestatus'));
			});
			$('.approvallistlinkbtn').click(function(){
				$('.leftsideli').removeClass('active');
				$(this).addClass('active');
				userPaymentobj.loadInvoices($(this).attr('paymenttype'),$(this).attr('status'));
			});
			$('.inviteduserbtn').click(function(){
				$('.leftsideli').removeClass('active');
				$(this).addClass('active');
				userinfoobj.inviteuserTemplate();
			});
		}
};

countobj={
		loadCountsData:function(){
			var self=this;			
			self.usersCount();
			self.paymentCount();
			self.certicateCount();
			self.inviteuserCount();
		},
		usersCount:function(){
			var allusercount=_.uniq(_.pluck(courseData.getAllUsersDetailss(domreadyobj.returncourseid()),'personid')).length;
			$('.allusercount').text(allusercount);
		},
		inviteuserCount:function(){
			var invitedUsers=courseData.getInvitingUsers(domreadyobj.returncourseid());
			if(invitedUsers!=undefined){
				$('.invitedusercount').text(invitedUsers.length);
			}else{
				$('.invitedusercount').text("0");
			}
		},
		paymentCount:function(){
			var allapprovalList=userPaymentobj.getPaymentList("Online","All");
			if(allapprovalList!=undefined){
				$('.allapprovalcount').text(allapprovalList.length);				
			}
			else{
				$('.allapprovalcount').text("0");

			}
			var paymentpendingList=userPaymentobj.getPaymentList("invoice-email","P");
			if(paymentpendingList!=undefined){
				$('.pendingapprovalcount').text(paymentpendingList.length);				
			}
			else{
				$('.pendingapprovalcount').text("0");
			}
			var approvedPaymentList=userPaymentobj.getPaymentList("invoice-email","A");
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
			var alluserdetails=courseData.getAllUsersDetailss(domreadyobj.returncourseid());
			self.renderAllUserDetails(alluserdetails);
		},
		renderAllUserDetails:function(alluserdetails){
			var self=this;
			renderTemplate("#allusertmpl", alluserdetails, "#contentdisplay");
			var coursename=_.uniq(_.pluck(alluserdetails,'coursetitle'));
			$('.userdetails').html(coursename);
			pagination();

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
				//self.loadlearningcourses($(this).attr('orgpersonid'));
			});
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
			var coursesList=courseData.getIndividualCourses(personidobj);			
			var coursearrayList=self.getcourseswithtimespent(coursesList);
			self.renderLearningCourses(coursearrayList);
		},
		renderLearningCourses:function(coursesList){
			console.log(coursesList);
			renderTemplate('#coursesinfotmpl',coursesList,'#coursesinfotable');	
			pagination();
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
			self.sendMessage(personidobj);	
		},
		sendMessage:function(personidobj){
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
		}
		,
		getcourseswithtimespent:function(coursesList){
			var coursearrayList=[];
			var coursesids=_.unique(_.pluck(coursesList,'courseid'));
			_.each(coursesids,function(courseid){
				var timearray=[];	

				var courseobj={};
				coursearray=_.where(coursesList,{courseid:courseid});	

				_.each(coursearray,function(courseary){
					courseprogress=[];
					if(courseary.timespent!=undefined && courseary.timespent.length!=0){
						timearray.push(courseary.timespent);
					}
					if(courseary.courseprogress!=undefined && courseary.courseprogress.length!=0){
						courseprogress.push(courseary.courseprogress);
					}					    				 
				});		

				var finaltimespent=sumMultipleTimesSpent(timearray);			
				courseobj.coursedescription=coursearray[0].coursedescription;
				courseobj.coursetitle=coursearray[0].coursetitle;
				courseobj.courseprogress=courseprogress[0];
				courseobj.timespent=finaltimespent;
				coursearrayList.push(courseobj);


			});
			return coursearrayList;
		},
		inviteuserTemplate:function(){	
			var courseid=$('[name="courseid"]').val();
			var obj={};
			obj.courseid=courseid;
			var inviteduserList=courseData.getAuthorcourseusers(obj);
			renderTemplate("#invitingusertmpl",inviteduserList,"#contentdisplay");
			pagination();
		}

};

userCertificatesobj={
		loadCertificates:function(certificatestatus){
			var self=this;
			var certificateList=self.getCertificateList(certificatestatus);
			self.renderCertificates(certificateList);
			self.setTitleforRightPane(certificatestatus);
		},
		renderCertificates:function(certificateList){
			var self=this;
			renderTemplate("#certificatedetailstmpl",certificateList,"#contentdisplay");
			pagination();
			self.setCertificateStatus($('.certificatestatusbtn'));
			$('.certificateApprovebtn').click(function(){
				self.approveCertificateData($(this).attr('coursecertificateid'));
			});
		},
		approveCertificateData:function(coursecertificateid,coursecertificatestatus){
			var self=this;
			var certificateidobj={};
			certificateidobj.coursecertificateid=coursecertificateid;
			self.approveCertificate(certificateidobj);	
		},
		approveCertificate:function(certificateidobj){
			var self=this;
			if(confirm("Do you want issue?")){
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
			var CertificateList=courseData.getCertificates(domreadyobj.returncourseid());
			if(CertificateList!==undefined){
				if(certificatestatus=="All"){
					var Certificates=CertificateList.allCerticates;
					return Certificates;
				}
				else if(certificatestatus=="A"){
					var Certificates=CertificateList.issuedCertificates;
					return Certificates;
				}
				else{
					var Certificates=CertificateList.pendingCertificates;
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
		renderPaymentDetails:function(paymentApprovalList,paymenttype,status){
			console.info("paymenttype is "+paymenttype);
			var self=this;
			if(status=="P"){
				self.tobeApproved(paymentApprovalList, paymenttype);	
			}else{
				if(paymenttype=="Online"){
					renderTemplate("#onlinepaymentdetailstmpl", paymentApprovalList, "#contentdisplay");	
				}else if(paymenttype=="invoice-email"){
					renderTemplate("#offlineapproveddetailstmpl", paymentApprovalList, "#contentdisplay");
				}
			}
		},
		tobeApproved : function(paymentApprovalList,paymenttype){
			var self=this;
			renderTemplate("#paymentdetailstmpl",paymentApprovalList,"#contentdisplay");
			pagination();
			self.rightpaneTitle($('.approvallistlinkbtn.active').attr('status'),paymenttype);
			self.setPaymentStatus($('.paymentstatusbtn'));		
			$('.paymentapprovebtn').click(function(){
				var checkstr =  confirm('Are you sure you want to approve this payment?');
				if(checkstr == true)	{
				var enrollmentidobj={};
				enrollmentidobj.courseenrollmentid=$(this).attr('courseenrollmentid');
				enrollmentidobj.courseenrollmentstatus="A";	
				var orgpersonid = $("[name=orgpersonid]").val();
				var courseid = $(this).attr('courseid');
				var time = new Date();
				var orderid = "CE_"+courseid+"_"+time.getTime()+"_"+orgpersonid;
				var paymentidobj={};
				paymentidobj.paymentstatus="A";
				paymentidobj.paymentid=$(this).attr('paymentid');
				paymentidobj.orderno=orderid;
				self.approvepaymentprocess(enrollmentidobj,paymentidobj,status,paymenttype);
				var enrollidobj={};
				enrollidobj.courseenrollmentid=$(this).attr('courseenrollmentid');
				var type="1";
				var royaltypayment = courseData.createRoyaltyPayment(enrollidobj,type);
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
			paymentstatusobj.courseid=$('[name=courseid]').val();
			var allPaymentList=courseData.getInvoices(paymentstatusobj);
			return allPaymentList;
		},
		rightpaneTitle:function(status,paymenttype){
			$('.rightpanetitle').attr('paymenttype',paymenttype);
			$('.rightpanetitle').attr('status',status);
			if(status=="P")	{
				$('.rightpanetitle').text('Pending Payment Details');
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
					$(this).text('Approve').removeClass('btn-success').addClass('btn-info');
					$(this).addClass('paymentapprovebtn');					
				}
				else if(paymentStatus=="C"){					
					$(this).html("<i class='icon-ok-sign approvedicon'></i>Completed");
					$(this).attr('disabled','disabled');
					$(this).removeClass('paymentapprovebtn');					
				}
				else{
					$(this).html("<i class='icon-ok-sign approvedicon'></i>Approved");
					$(this).addClass('defaultcursor');
					$(this).attr('disabled','disabled');
					$(this).removeClass('paymentapprovebtn');
				}
			});	
		},		
		approvepaymentprocess:function(enrollmentidobj,paymentidobj){
			var self=this;
			courseData.updateCourseEnrollmentStatus(enrollmentidobj);
			courseData.updateCoursePaymentStatus(paymentidobj);
			self.loadInvoices($('.rightpanetitle').attr('paymenttype'),$('.approvallistlinkbtn.active').attr('status'));
		},
};
function sumMultipleTimesSpent(times){
	var existhrs="00:00:00";
	for(var i=0;i<times.length;i++)	{
		curtime=times[i].split(/\D/);
		exttime=existhrs.split(/\D/);
		var x1=parseInt(curtime[0])*60*60 + parseInt(curtime[1])*60 + parseInt(curtime[2]);
		var x2=parseInt(exttime[0])*60*60 + parseInt(exttime[1])*60 + parseInt(exttime[2]);
		var s=x1+x2;
		var m=Math.floor(s/60); s=s%60;
		var h=Math.floor(m/60); m=m%60;
		var result = [h,m,s];
		result = result.map(function(v) {
			return v < 10 ? '0' + v : v;
		}).join(':');
		existhrs=result;
	}
	var hrs=existhrs.split(":");	
	if(hrs[0]!="00"){
		console.log(hrs[0]+" Hrs");
		tothrs=hrs[0]+" Hrs ";
	}else{
		tothrs="";
	}
	if(hrs[1]!="00"){
		totmins=hrs[1]+" Mins ";
	}else{
		console.log("zero");
		totmins="0";
	}
	if(hrs[2]!="00"){
		totsecs=hrs[2]+" Secs ";
	}else{
		totsecs="0";
	}
	if(tothrs!="0")
		tothrs;
	if(totmins!="0")
		tothrs=tothrs+totmins;
	if(totsecs!="0")
		tothrs=tothrs+totsecs;
	return tothrs;		
}


function pagination(){
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

}