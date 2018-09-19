$(document).ready(function(){
	$("#usermycourse").addClass('hover').addClass('active');
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

	$('.searchkwrdbtn').click(function(e){
		$('.dropbox').slideDown(400);
	});
	$('.closebtn').click(function(){
		$('.searchbox').val('');
		$('.dropbox').slideUp(400);
	});
	$('.searchbtn').click(function(e){
		var searchkeyword=$('.searchbox').val().trim();
		if(searchkeyword!=undefined && searchkeyword.length!=0){
			$('[name=keyword]').val(searchkeyword);
			$('[name=searchresultform]').submit();
		}
		else{
			e.preventDefault();
		}
	});
	/*$('.searchbox').keypress(function(event){
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == '13'){
			var searchkeyword=$('.searchbox').val().trim();
			if(searchkeyword!=undefined && searchkeyword.length!=0){
				$('[name=keyword]').val(searchkeyword);				
				$('[name=previewform]').attr('action','searchresult');
				$('[name=previewform]').submit();
			}
			else{
				event.preventDefault();
			}
		}
	});*/


	$('#coursedescid').on('change',function(){
		$('#coursedescid').val($('#coursedescid').val().trim());
	});
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
					var data=encodeURIComponent($.trim($("[name=coursedescription]").val()));
					$("[name=coursedescription]").val(data);
					if(confirm("Are you sure you want to create this course ?")){
						$('[name=coursecreationmodalform]').attr('method','post');
						$('[name=coursecreationmodalform]').attr('action','newCourseCreate');
						$('[name=coursecreationmodalform]').submit();
						$('[name=coursecreationmodalform]')[0].reset();
					}
				}

				$('#coursetitleerror').hide();
				$('#coursedecerror').hide();
			}
		}
		$('[name=coursetitle]').on("focusout keyup",function(){	
			$('#coursetitleerror').hide();				
		});
		$( '[name=coursedescription]').on("focusout keyup",function( event ) {			
			$('#coursedecerror').hide();				
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
	});

	var status =$('#statusID').attr('status');
	var enrstatus=$('#statusID').attr('enrollstatus');
	myCourses.enrolstatusCheck(status,enrstatus);
	myCourses.docReady();

	if(sessionStorage.getItem('lastTab')){
		var lastTab = sessionStorage.getItem('lastTab');
		if(lastTab.search("learningcourselink")>=0){
			switchtab(1);
		}
		else if(lastTab.search("teachingcourselink")>=0){
			switchtab(2);
		}
		else if(lastTab.search("completedcourselink")>=0){
			switchtab(3);
		}
		else if(lastTab.search("wishlistcourselink")>=0){
			switchtab(4);
		}
		else if(lastTab.search("myearnings")>=0){
			switchtab(5);
		}
		else if(lastTab.search("totalamount")>=0){
			switchtab(6);
		}
		else if(lastTab.search("paymentrequest")>=0){
			switchtab(7);
		}
	}
	function switchtab(e){
		switch(e){
		case 1:{
			$('.learningcourselink').trigger('click');
			break;
		}
		case 2:{
			$('.teachingcourselink').trigger('click');
			break;
		}
		case 3:{
			$('.completedcourselink').trigger('click');
			break;
		}
		case 4:{
			$('.wishlistcourselink').trigger('click');
			break;
		}
		case 5:{
			$('.myearnings').trigger('click');
			break;
		}	
		case 6:{
			$('.totalamount').trigger('click');
			break;
		}
		case 7:{
			$('.paymentrequest').trigger('click');
			break;
		}	
		}
	}

});


var myCourses={
		docReady:function(){
			var self=this;
			self.loadInitialCourses();
			self.initialClickEvents();
			coursesCount.home();
		},
		searchcheck:function(courseid){
			var self=this;
			var courseidobj={};
			if(courseid!=null){
				courseidobj.courseid=courseid;
				self.newCourseLearning(courseidobj,"previewform","previewuser");
			}
		},
		searchCourses:function(){
			var data=[];
			var index=[];
			var courses=ajaxhelper("searchCourses");
			for (var i = 0; i < courses.length;i++ ) {
				data[i]=courses[i].coursetitle;
				index[i]=courses[i].courseid;
			}
			return data;
		},
		loadInitialCourses:function(){
			var self=this;
			var link=$('.pagereferlinks').attr('link');
			if(link=="dc"){
				$('.leftsideli').removeClass('active');
				$('.teachingcourselink').addClass('active');
			}else if(link=="pc"){
				$('.leftsideli').removeClass('active');
				$('.teachingcourselink').addClass('active');
				$('.authcourselink[st=pub]').addClass('active');
				authoringCourses().init();
			}else{
				$('.leftsideli').removeClass('active');
				$('.learningcourselink').addClass('active');
				self.loadLearningCourses();
			}
		},
		enrolledValuesCheck:function(courseidobj){
			var enrollmentStatus=courseData.getEnrolledBoolean(courseidobj);
			if(enrollmentStatus===undefined){
				return "notenrolled";
			}
			else{
				var status=_.isObject(enrollmentStatus);
				if(status===true){
					if(enrollmentStatus[0].courseenrollmentstatus=="C"){
						return "completed";
					}
					else if(enrollmentStatus[0].courseenrollmentstatus=="A"){
						return "enrolled";
					}
					else if(enrollmentStatus[0].courseenrollmentstatus=="W"){
						return "wishlisted";
					}
					else if(enrollmentStatus[0].courseenrollmentstatus=="P"){
						return "subscribed";
					}
				}
			}
		},
		
		initialClickEvents:function(){
			var self=this;
			$('.learningcourselink').click(function(){
				$('.leftsideli').removeClass('active');
				$(this).addClass('active');				
				self.loadLearningCourses();
			});
			
			$('.authcourselink').click(function(){
				$('.leftsideli').removeClass('active');
				$(this).addClass('active');				
				authoringCourses().init();
			});
			$('.completedcourselink').click(function(){				
				self.loadCompletedCourses();
				$('.rightpanetitle').text("Completed Courses");
			});
			$('.myearnings').click(function(){				
				self.loadMyEarningCourses();
				$('.rightpanetitle').text("My Earnnigs");

			});
			$('.paymentrequest').click(function(){				
				self.loadPaymentRequest();
			});
			$(".totalamount").click(function(){
				self.loadTotalAmount();
			});
			$('.wishlistcourselink').click(function(){				
				self.loadWishlistCourses();
				$('.rightpanetitle').text("WishListed Courses");
			});
		},
		loadLearningCourses:function(){
			var enrolledCoursesList=courseData.getEnrollcourses();
			if(enrolledCoursesList!=undefined){
				var learningcourseslist=enrolledCoursesList.learningCourses;
				if(learningcourseslist.length!==0){
					renderTemplate("#learningcoursestmpl",learningcourseslist,"#coursedescriptiontable");
					$('.learningcoursestab').click(function(){
						$('.learningcoursestab').removeClass('active');
						$(this).addClass('active');
					});
					$('.crsprice').each(function(){
						var price=$(this).attr('price');
						var priceicon = $(this).attr('priceicon');
						if(price=="Free"){
							$(this).html("Free");
						}
						else if(price==""){
							$(this).html("");
						}
						else{
							$(this).html("<i class='fa "+priceicon+"'></i>"+price);	
						}
					});
					$('.startlearninggridbtn').each(function(){
						if($(this).attr('courseenrollmentstatus')!=="A"){
							$(this).text('Blocked');
							$(this).attr("disabled",'disabled');
						}
					});
					$('.startlearningbtn').each(function(){
						if($(this).attr('courseenrollmentstatus')!=="A"){
							$(this).text('Blocked');
							$(this).attr("disabled",'disabled');
						}
					});
					$('.startlearninggridbtn').click(function(e){
						if($(this).text()!=="Blocked"){
							$('[name=courseid]').val($(this).attr('courseid'));
							$('[name=courseenrollmentid]').val($(this).attr('courseenrollmentid'));
							$('[name=courseenrollmentstatus]').val($(this).attr('courseenrollmentstatus'));
							$('[name=startlearninggridform]').attr('action','previewcourse');
							$('[name=startlearninggridform]').submit();	
						}
						else{
							e.preventDefault();	
						}
					});

					$('.startlearningbtn').click(function(){
						if($(this).text()!=="Blocked"){
							$('[name=courseid]').val($(this).attr('courseid'));
							$('[name=courseenrollmentid]').val($(this).attr('courseenrollmentid'));
							$('[name=courseenrollmentstatus]').val($(this).attr('courseenrollmentstatus'));
							$('[name=startlearningform]').attr('action','previewcourse');
							$('[name=startlearningform]').submit();
						}
						else{
							e.preventDefault();	
						}						
					});					
				}else{
					renderTemplate("#nocoursestmpl",enrolledCoursesList,"#coursedescriptiontable");
					$('.rightpanetitle').html("Learning Courses");
				}				
			}
			else{	
				renderTemplate("#nocoursestmpl",enrolledCoursesList,"#coursedescriptiontable");
				$('.rightpanetitle').html("Learning Courses");
			}
		},
		loadCompletedCourses:function(){
			var self=this;
			$('.leftsideli').removeClass('active');
			$('.completedcourselink').addClass('active');			
			var enrolledCoursesList=courseData.getEnrolledcourses();
			if(enrolledCoursesList!=undefined){
				var completedCoursesList=enrolledCoursesList.completedCourses;
				self.renderCompletedCourses(completedCoursesList);			
			}
			else{
				renderTemplate("#nocoursestmpl",enrolledCoursesList,"#coursedescriptiontable");
				$('.rightpanetitle').html("Completed Courses");
			}
		},
		renderCompletedCourses:function(completedCoursesList){
			var self=this;
			renderTemplate("#completedcoursestmpl",completedCoursesList,"#coursedescriptiontable");
			$('.reportbtn').click(function(){
				$('[name=courseenrollmentid]').val($(this).attr('courseenrollmentid'));
				$('[name=courseenrollmentstatus]').val($(this).attr('courseenrollmentstatus'));
				$('[name=completedcourseform]').attr('action','report');
				$('[name=completedcourseform]').submit();
			});
			$('.certificaterequestbtn').click(function(){
				var enrollmentidobj={};
				enrollmentidobj.courseenrollmentid=$(this).attr('courseenrollmentid');
				self.certificateRequestData(enrollmentidobj);
			});
			$('.startlearnbtn').click(function(){
				$('[name=courseid]').val($(this).attr('courseid'));
				$('[name=courseenrollmentid]').val($(this).attr('courseenrollmentid'));
				$('[name=courseenrollmentstatus]').val($(this).attr('courseenrollmentstatus'));
				$('[name=completedcourseform]').attr('action','previewcourse');
				$('[name=completedcourseform]').submit();
			});

		},
		certificateRequestData:function(enrollmentidobj){
			var self=this;
			var certificateDetails=courseData.getCertificateDetails(enrollmentidobj);
			if(certificateDetails[0].coursecertificatestatus!=undefined){
				if(certificateDetails[0].coursecertificatestatus=="A"){
					self.renderCertificateDetails(certificateDetails);
				}
				else{
					$("#messagemodal").modal('show');
				}				
			}			
		},
		renderCertificateDetails:function(certificateDetails){
			var self=this;
			renderTemplate("#certificatetmpl", certificateDetails, "#coursedescriptiontable");
			$('.backcoursebtn').click(function(){
				self.loadCompletedCourses();
			});
			$('.printcertificatebtn').click(function(){
				self.printDiv();
			});
		},
		loadWishlistCourses:function(){
			$('.rightpanetitle').text("Wishlisted Courses");
			var self=this;
			$('.leftsideli').removeClass('active');
			$('.wishlistcourselink').addClass('active');
			var enrolledCoursesList=courseData.getEnrolledcourses();
			if(enrolledCoursesList!=undefined){
				var wishlistedCoursesList=enrolledCoursesList.wishlistCourses;
				self.renderWishlistCourses(wishlistedCoursesList);			
			}else{				
				renderTemplate("#nocoursestmpl",enrolledCoursesList,"#coursedescriptiontable");
				$('.rightpanetitle').html("Wishlisted Courses");
			}
		},
		renderWishlistCourses:function(wishlistCoursesList){
			renderTemplate("#wishlistcoursestmpl",wishlistCoursesList,"#coursedescriptiontable");
			pricesicon();
			$('.wishenrollBtn').click(function(){
				$('[name=courseenrollmentid]').val($(this).attr('courseenrollmentid')) ;
				$('[name=courseid]').val($(this).attr('courseid')) ;
				$('[name=wishlisted]').val($(this).attr('wishlisted'));
				$('[name=wishlistEnrollmentForm]').attr('action','previewuser');
				$('[name=wishlistEnrollmentForm]').submit();
			});
		},

		printDiv:function() {
			var pdf = new jsPDF('l','pt','a4');
			pdf.addHTML($('#certificatearea'),function() {
				/*pdf.output('dataurlnewwindow');*/ //unhide this if you want to open in a new window 
				pdf.output('save', 'certificate.pdf'); //to download the certificate
			});		
		},
		loadMyEarningCourses:function(){
			$('.rightpanetitle').text("My Earnings");
			var self=this;
			$('.leftsideli').removeClass('active');
			$('.myearnings').addClass('active');
			var earingCoursesList=null;
			earingCoursesList=courseData.getMyEarningCourses();
			if(earingCoursesList!=undefined){				
				self.renderMyEarningCourses(earingCoursesList);	
			}
			else{
				renderTemplate("#myearningscoursestmpl",earingCoursesList,"#coursedescriptiontable");
			}
		},
		loadPaymentRequest:function(){
			$('.rightpanetitle').text("Payment Requested List");
			$('.leftsideli').removeClass('active');
			$('.paymentrequest').addClass('active');
			var paymentIssueList = courseData.getPaymentIssueList();
			Handlebars.registerHelper('dateofrequest', function(dateofrequest) 
					{ 
				var d = new Date(dateofrequest);
				var date =d.toDateString();
				data=date.split(" ");
				var data1 = data[2]+"/"+data[1]+"/"+data[3];
				var time = d.toLocaleTimeString();
				var datetime = data1+" "+time;
				return datetime;
					});
			Handlebars.registerHelper('dateofissue', function(dateofissue) 
					{ 
				var d = new Date(dateofissue);
				var date =d.toDateString();
				data=date.split(" ");
				var data1 = data[2]+"/"+data[1]+"/"+data[3];
				var time = d.toLocaleTimeString();
				var datetime = data1+" "+time;
				return datetime;
					});
			renderTemplate("#paymentrequesttmpl",paymentIssueList,"#coursedescriptiontable");
		},
		loadTotalAmount:function(){
			$(".requestlimitmsg").empty();
			$(".requestlimitamount").empty();
			$(".issueamount").empty();
			$('.leftsideli').removeClass('active');
			$('.totalamount').addClass('active');
			var authortotalamount = courseData.getAuthorTotalList(); 
			var royaltytotalamount = courseData.getTotaltAmountList();
			var requestlimitlist = courseData.getRequestLimit();
			var requestList = courseData.getPaymentRequest();
			var paymentrequestauthorlist=courseData.getPaymentRequestAuthorList();
			if(paymentrequestauthorlist.length>0){
				royaltytotalamount[0].dateofrequest=requestList[0].dateofrequest;
				Handlebars.registerHelper('dateofrequest', function(dateofrequest) 
						{ 
					var d = new Date(dateofrequest);
					var date =d.toDateString();
					data=date.split(" ");
					var data1 = data[2]+"/"+data[1]+"/"+data[3];
					var time = d.toLocaleTimeString();
					var datetime = data1+" "+time;
					return datetime;  
						});
			}
			if(royaltytotalamount=="BAD_REQUEST"){
				royaltytotalamount=[];
				obj={};
				obj.pendingamount = "--";
				obj.totalamount = "--";		
				royaltytotalamount[0]=obj;
			}
			renderTemplate("#royaltytotalamounttmplt",royaltytotalamount,"#coursedescriptiontable");
			var amountdata =_.pluck(requestlimitlist,"minimumamount");
			var amount = amountdata.toString();
			console.log(requestlimitlist);
			if(requestlimitlist=="BAD_REQUEST"){
			}
			else{
				var priceicon = $(".requestlimitmsg").attr('priceiconid');
				if(requestlimitlist[0].minimumamount==0){					
					$(".requestlimitmsg").html("Minimum Dispatch Limit Per Request: <i class='fa "+priceicon+"'></i>"+amount);
					$('.requestlimitamount').text("");
					$('.requestlimitamount').append("--");
				}
				else{  
					$(".requestlimitmsg").html("Minimum Dispatch Limit Per Request: <i class='fa "+priceicon+"'></i>"+amount); 
					$(".requestlimitamount").append(amount);
				} 
			} 
			if(authortotalamount=="BAD_REQUEST"){
				$(".issueamount").append("--");
			}
			else{
				var authoramountdata =_.pluck(authortotalamount,"minimumamount");
				var authoramount = authoramountdata.toString();
				if(authoramount=="")
				{
					$(".issueamount").append("--");
				}
				else{
					$(".issueamount").append(authoramount);	
				}
			}
			$(".paymentrequestbtn").click(function(){
				$(".paymentrequstmsg").empty();
				var amountdata =_.pluck(requestlimitlist,"minimumamount");
				var amount = parseInt(amountdata);
				var pendingamount =_.pluck(royaltytotalamount,"pendingamount");
				var pendingamountdata = parseInt(pendingamount);
				var requestlimit = _.pluck(requestlimitlist,"id");
				var requestlimitid = parseInt(requestlimit);
				if(pendingamountdata>amount){
					if(paymentrequestauthorlist.length>0){
						/*						if($(".paymentissueval").text()=="Pending"){
							//$("#alertmsg").modal('show');
							}
							else{
								var paymentRequest={};
								paymentRequest.requestlimit_id=1;
								console.log("createPaymentRequest ====>>");
								var paymentRequestList=courseData.createPaymentRequest(paymentRequest);
								if(paymentRequestList.length>0){
									myCourses.loadTotalAmount();
								}
								}
						 */							}
					else{
						var paymentRequest={};
						paymentRequest.requestlimit_id=requestlimitid;
						var paymentRequestList=courseData.createPaymentRequest(paymentRequest);
						if(paymentRequestList.length>0){
							myCourses.loadTotalAmount();
							$("#paymentmessagemodal").modal('show');
						}
					}
				}
				else{
					$("#alertamountchkmsg").modal("show");
					$(".paymentrequstmsg").append('Payment Amount must be greater than '+ amount);

				}

			});
		},
		renderMyEarningCourses:function(myEarningsCoursesList){
			var self=this;
			renderTemplate("#myearningscoursestmpl",myEarningsCoursesList,"#coursedescriptiontable");			
			$('.authorcoursestudents').click(function(){	

				$("#authorcoursemodal").modal("show");
				$('.authorcoursestudents').parent().parent().removeClass('activecourse');
				$(this).parent().parent().addClass('activecourse');				
				self.loadauthorcoursestudents($(this).attr('courseid'),$(this).attr('coursetitle'),$(this).attr('price'));				


			});

		},
		getTotalEarnings:function(earningCoursesList){
			var sum=0;
			var courseprices=_.pluck(earningCoursesList,'totalprice');
			_.each(courseprices,function(price){
				prices=parseFloat(price);
				sum = sum+prices;
			});			
			$("#totalearnings").text(sum);
		},
		loadauthorcoursestudents:function(authorcourseid,coursetitle,price){
			var self=this;
			var stcourseidobj={};
			stcourseidobj.authorcourseid=authorcourseid;
			var authroCourseStudents=courseData.loadAuthorCourseStudents(stcourseidobj);
			self.renderAuthorCourseStudents(authroCourseStudents,coursetitle,price);
		},
		renderAuthorCourseStudents:function(authroCourseStudents,coursetitle,price){
			$("#coursetitlespan").text(coursetitle);
			if(authroCourseStudents==undefined){
				renderTemplate('#datanotfoundtmpl',authroCourseStudents,'#authorcoursestudentinfo');	
			}else{
				$('#warningtextdiv').hide();
				if(price=='Free'){
					renderTemplate('#authorfreecoursestudentsinfotmpl',authroCourseStudents,'#authorcoursestudentinfo');	
				}
				else{
					renderTemplate('#authorcoursestudentsinfotmpl',authroCourseStudents,'#authorcoursestudentinfo');
				}
			}
		},
		enrolstatusCheck:function(status,enrstatus){
			if(status=='alreadytaken') {
				switch (enrstatus) {
				case "B":
					errorenrollmsg="You are blocked From this course. Please Contact Administrator";				
					break;
				case "A":
					errorenrollmsg="You have enrolled in this course.";
					break;
				case "P":
					errorenrollmsg="You subscription for this course is waiting for your approval. Please wait till the administrator.";
					break;
				case "C":
					errorenrollmsg="You have already completed this course. You can find them in the completed courses Section.";
					break;
				case "W":
					errorenrollmsg="You have already wish-listed this course. You can find them in the wishlisted courses Section";
					break;
				}
				$('#statusID').show();
				$("#statusID").fadeIn('slow').delay(3000).fadeOut('slow');
				$('#statusID').attr('status','');
				$('#statusID').attr('enrollstatus','');
				$('.enrollmsg').text(errorenrollmsg);
			}
		},
		deletecourseData:function(trashcourseobj,method){
			var self=this;
			var deletedCourse=courseData.updateCourseStatus(trashcourseobj);
			if(deletedCourse==1){
				coursesCount.home();
				if(method=="delete"){
					$('#trashrestoreheader').html("Draft Course");
					$('#trashrestoremsg').html("The course has been successfully trashed");
					$('#trashrestoremodal').modal('show');
					$('.authcourselink').eq($('a[st=dra]').index()).trigger('click');
				}
				else if(method=="restore"){
					$('#trashrestoreheader').html("Trashed Course");
					$('#trashrestoremsg').html("The course has been successfully restored");
					$('#trashrestoremodal').modal('show');
					$('.authcourselink').eq($('a[st=trash]').index()).trigger('click');
				}
				else{
					$('#trashrestoreheader').html("Trashed Course");
					$('#trashrestoremsg').html("The course has been successfully removed from the trash list");
					$('#trashrestoremodal').modal('show');
					$('.authcourselink').eq($('a[st=trash]').index()).trigger('click');
				}				
			}
		}
};

function pricesicon(){

	$('.icon_price').each(function(){
		var price=$(this).attr('price');
		var priceicon = $(this).attr('priceicon');
		if(price=="Free"){
			$(this).html("Free");
		}
		else if(price==""){
			$(this).html("");
		}
		else{
			$(this).html("<i class='fa "+priceicon+"'></i>"+price);	

		}
	});
}
var data=[];
var index=[];
function searchCourses(keyword){
	data.length=0;
	index.length=0;
	var courses=ajaxhelper("searchCourses?keyword="+keyword);
	if(courses!="false"){
		for (var i = 0; i < courses.length;i++ ) {
			data[i]=courses[i].coursetitle+" - "+courses[i].author;
			index[i]=courses[i].courseid;
		}
		return data;
	}
	else{
		return "false";
	}
}
/*var timer = null;
$('.searchbox').typeahead({
	matcher: function () { return true; },
	source:function(query,process){
		if(timer){
			clearTimeout(timer);
		}
		timer=setTimeout(function(){
			if(query.trim()!="")
			{
				var result=searchCourses(query);
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
			return myCourses.searchcheck(index[data.indexOf(item)]);
		}
	} 
}); 	*/

var authoringCourses = (function () {
	var controller = {	
			allcourses:function(courseidobj){
				return ajaxhelperwithdata("loadOtherCourses",courseidobj);
			},		
	}; 
	var view = {
			init:function(){
				/*renderTemplate("#authoringcoursestmpl", null, "#coursedescriptiontable");
				$('.authcourselink').removeClass('active');
				$('.authcourselink[st=dra]').addClass('active');*/
				this.typecourses($('.authcourselink.active').attr('st'));
				/*$('.authoringcoursetitle').text("Draft Courses");
				$('.authcourselink').click(function(){
					$('.authcourselink').removeClass('active');
					$(this).addClass('active');
					var linktype=$(this).attr('st');
					view.typecourses(linktype);
				});*/
			},
			typecourses:function(linktype){
				var statusobj=objects.statusType("All");
				var coursesList=model.courses(statusobj);
				switch (linktype) {
				case "app":
					$('.authoringcoursetitle').text("Approved Courses");
					var list=formater.approved(coursesList);
					this.approvedCourses(list);
					break;
				case "pub":
					$('.authoringcoursetitle').text("Published Courses");
					var list=formater.published(coursesList);
					this.publishedCourses(list);
					break;
				case "dra":
					var list=formater.draft(coursesList);
					this.draftCourses(list);
					break;				
				case "trash":
					var list=formater.trash(coursesList);
					this.trashCourses(list);
					break;
				}
			},
			draftCourses:function(list){
				renderTemplate("#authordraftcoursestmpl", list, "#coursedescriptiontable");
				helper.priceiconfn();
				$('.managecoursebtn').click(function(){
					$('[name=courseid]').val($(this).attr('courseid'));
					$('[name=teachingcourseform]').submit();
				});
				$('.deletecourse').click(function(){
					var checkstr =  confirm('Are you sure you want to trash this Course');
					if(checkstr == true)	{
						var trashcourseobj={};
						trashcourseobj.courseid=$(this).attr('courseid');
						trashcourseobj.coursestatus="T";
						myCourses.deletecourseData(trashcourseobj,"delete");
					}	
				});
			},
			publishedCourses:function(list){
				renderTemplate("#authorpublishedcoursestmpl", list, "#coursedescriptiontable");
			},
			approvedCourses:function(list){
				renderTemplate("#authorapprovedcoursestmpl", list, "#coursedescriptiontable");
				helper.priceiconfn();
				$('.managecoursebtn').click(function(){
					$("[name=courseid]").val($(this).attr('courseid'));
					$('[name=teachingcourseform]').attr('action','managecourse');
					$('[name=teachingcourseform]').submit();
				});
				$('.manageusersbtn').click(function(){
					$("[name=courseid]").val($(this).attr('courseid'));
					$("[name=coursetitle]").val($(this).attr('coursetitle'));
					$("[name=price]").val($(this).attr('price'));
					$('[name=teachingcourseform]').attr('action','usermanage');
					$('[name=teachingcourseform]').submit();
				});
				$('.inviteuser').click(function(){
					$('.sendinvitation').attr('courseid',$(this).attr('courseid'));
				});

				sendingCourseInvitation.home();
				/*$('.sendinvitation').click(function(){
					var obj={};
					var emails=$('.emailuser').val();					
					var messagedescription=$('#messagedescription').val();
					var emailval = $('.emailval').attr('val');

					if(emailval==emails){
						$('.emailuser').val('');
						obj.errorvalidation="Can't invite to the same user";
						renderTemplate("#sameuser",obj,"#errormessage");
						$('#errormessage').fadeIn();
						setTimeout(function(){ $('#errormessage').fadeOut(); }, 3000);
					}
					else{
						if(emails.length!=0 && messagedescription!=0){
							if(helper.validateEmails(emails)){				    

								var noEmail=helper.checkemails(emails,$(this).attr('courseid'),$('.emailval').attr('val'));						
								if(noEmail.length=='0'){	
									var user = {};				
									user.email =emails.trim();	
									user.courseid=$(this).attr('courseid');
									user.message = encodeURIComponent(messagedescription);
									console.info("everything OK");
									helper.asyncajax("authorinvite",user);
								}else{			
									//obj.duplicatemessage=noEmail[0] +" already exists";
									obj.duplicatemessage="Already invited users will be automatically removed, Please wait..";
									renderTemplate("#duplicatemessagetmpl",obj,"#errormessage");
									$('#errormessage').fadeIn();
									setTimeout(function(){ $('#errormessage').fadeOut(); 
									$('.errormessage').fadeOut();		
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
									}, 3000);
								}																		
							}else{
								obj.errormessage="Please Check Your Email Id";
								renderTemplate("#errormesstmpl",obj,"#errormessage");
								$('#errormessage').fadeIn();
								setTimeout(function(){ $('#errormessage').fadeOut(); }, 3000);
							}
						}else{
							obj.errorvalidation="Please Fill the Required Fields";
							renderTemplate("#validationtmpl",obj,"#errormessage");
							$('#errormessage').fadeIn();
							setTimeout(function(){ $('#errormessage').fadeOut(); }, 3000);
						}
					}
				});	*/
			},
			trashCourses:function(list){
				renderTemplate("#authortrashedcoursestmpl", list, "#coursedescriptiontable");
				$('.restorecoursebtn').click(function(){
					if(confirm('Do you want to restore it back?')){
						var restorecourseobj={};
						restorecourseobj.courseid=$(this).attr('courseid');
						restorecourseobj.coursestatus="D";
						myCourses.deletecourseData(restorecourseobj,"restore");
					}
				});
				$('.deleteforever').click(function(){
					if(confirm('Do you want to wipe out the course?')){
						var restorecourseobj={};
						restorecourseobj.courseid=$(this).attr('courseid');
						restorecourseobj.coursestatus="X";
						myCourses.deletecourseData(restorecourseobj,"forever");
					}
				});
			}
	}; 
	var model = {
			courses:function(statusobj){
				return controller.allcourses(statusobj);
			},
	};
	var objects={
			statusType:function(coursestatus){
				var statusobj={};
				statusobj["coursestatus"]=coursestatus;
				return statusobj;
			}
	};
	var helper={
			checkemails:function(emails,courseid,userownemail){
				var staticEmailArray=[];
				var emaildata=emails.split(',');
				if(emaildata.length!=0){
					$.each(emaildata, function(index, value) {
						var response="";
						var obj={};
						obj.email=value;
						obj.courseid=courseid;

						if(response=="failure"){
							response=courseData.checkAuthourData(obj);
							staticEmailArray.push(obj);		
						}
					});
					return staticEmailArray;
				}
			},
			validateEmail:function (email) { 
				var emaildata=email.trim();
				var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
				return re.test(emaildata);
			},
			validateEmails:function(emails)	{
				var self=this;
				var i;
				var res = emails.split(",");
				for(i = 0; i < res.length; i++)
					if(!self.validateEmail(res[i])) return false;
				return true;
			},
			asyncajax:function(url,data){
				var self=this;
				output = "";
				if(_.isString(url))	{
					if(_.isObject(data) && !_.isArray(data)){
						$.ajax({
							type : 'GET',
							url : url,			
							data:data,	
							beforeSend:self.startProgress(),
							success : function(data, textStatus, jqXHR){								
								if(data=="success"){
									self.endProgress();
									var obj={};
									$('.emailuser').val("");	
									$('#messagedescription').val("");
									obj.successmessage="Invitation has been sent";
									renderTemplate("#successmessagetmpl",obj,"#errormessage");
									$('#errormessage').fadeIn();
									setTimeout(function(){ $('#errormessage').fadeOut(); }, 3000);
								}
								else{
									self.endProgress();
									var obj={};
									obj.mailservererror="Problem in sending the mail, Please try after sometime..";
									renderTemplate("#mailservererrortmpl",obj,"#errormessage");
									$('#errormessage').fadeIn();
									setTimeout(function(){ $('#errormessage').fadeOut(); }, 3000);	
								}
							},
							error : function(jqXHR, textStatus, errorThrown){
								self.endProgress();
								var obj={};
								obj.mailservererror="Problem in sending the mail, Please try after sometime..";
								renderTemplate("#mailservererrortmpl",obj,"#errormessage");
								$('#errormessage').fadeIn();
								setTimeout(function(){ $('#errormessage').fadeOut(); }, 3000);
								console.log("Couldn't connect to server " + textStatus);
							}
						});	
					}
					else{
						console.log("data is not an object "+data);	
					}
				}
				else{
					console.log("url is not a String "+url);	
				}
				return output;
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
			},
			priceiconfn:function(){
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
			}
	};
	var formater={
			draft:function(list){
				return _.where(list,{coursestatus:"D"});
			},
			published:function(list){
				return _.where(list,{coursestatus:"P"});
			},
			approved:function(list){
				return _.where(list,{coursestatus:"A"});
			},
			trash:function(list){
				return _.where(list,{coursestatus:"T"});
			}
	};

	return {
		init: function () {
			initialize();
		},
		count:function(){
			var statusobj=objects.statusType("All");
			var coursesList=model.courses(statusobj);
			if(coursesList!="false"){
				return coursesList.length;
			}else{
				return "0";	
			}			
		},
	}; 
	function initialize(){
		view.init();		
	};	


});

sendingCourseInvitation = {

		home : function(){
			var self = this;
			$('.emailuser').change(function(){
				self.validateInvitationEmails($(this).val().trim());
			});

			$('.sendinvitation').off().click(function(){
				var emptyCheckVal = self.emptyCheck();
				if(emptyCheckVal==true){
					var user = {};				
					user.email =$('.emailuser').val().trim();	
					user.courseid=$(this).attr('courseid');
					user.message = encodeURIComponent(messagedescription);
					self.view("authorinvite",user);
					emptyCheckVal ==false;
				}else{
					var emptyObj = {};
					emptyObj.errorvalidation="Please Fill the Required Fields.";
					renderTemplate("#validationtmpl",emptyObj,"#errormessage");
					$('#errormessage').fadeIn();
					setTimeout(function(){ $('#errormessage').fadeOut(); }, 3000);
				}
			});
		},

		view : function(url,data){
			var self=this;
			if(_.isString(url))	{
				if(_.isObject(data) && !_.isArray(data)){
					$.ajax({
						type : 'GET',
						url : url,			
						data:data,	
						beforeSend:this.startProgress(),
						success : function(data, textStatus, jqXHR){								
							if(data=="success"){
								var obj={};
								obj.successmessage="Invitation has been sent";
								renderTemplate("#successmessagetmpl",obj,"#errormessage");
								self.endProgress();
								$('#errormessage').fadeIn();
								setTimeout(function(){ $('#errormessage').fadeOut(); }, 3000);
							}
							else{
								self.endProgress();
								var obj={};
								obj.mailservererror="Problem in sending the mail, Please try after sometime..";
								renderTemplate("#mailservererrortmpl",obj,"#errormessage");
								$('#errormessage').fadeIn();
								setTimeout(function(){ $('#errormessage').fadeOut(); }, 3000);	
							}
						},
						error : function(jqXHR, textStatus, errorThrown){
							self.endProgress();
							var obj={};
							obj.mailservererror="Problem in sending the mail, Please try after sometime..";
							renderTemplate("#mailservererrortmpl",obj,"#errormessage");
							$('#errormessage').fadeIn();
							setTimeout(function(){ $('#errormessage').fadeOut(); }, 3000);
							console.log("Couldn't connect to server " + textStatus);
						}
					});	
				}
				else{
					console.log("data is not an object "+data);	
				}
			}
			else{
				console.log("url is not a String "+url);	
			}
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
		},


		emptyCheck  : function(){
			var emailValues = $('.emailuser').val().trim().length;
			var messageValue = $("#messagedescription").val().trim().length;
			if(messageValue==0 || emailValues == 0){
				return false;
			}else{
				return true;		
			}
		},

		validateInvitationEmails : function(emailVals){
			var emails=emailVals;
			var emailArray = [];
			var emaildata=emails.split(',');
			if(emaildata.length!=0){
				$.each(emaildata, function(index, value) {
					var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
					var emailValidation = re.test(value);
					if(emailValidation==true){
						var emailval = $('.emailval').attr('val');
						if(emailval==value){
							var invalidObj = {};
							invalidObj.errorvalidation="Your own email id has been removed from the list.";
							renderTemplate("#validationtmpl",invalidObj,"#errormessage");
							$('#errormessage').fadeIn();
							setTimeout(function(){ $('#errormessage').fadeOut(); }, 3000);
						}else{
							var obj={};
							obj.email=value;
							obj.courseid=$('.sendinvitation').attr('courseid');
							var response=courseData.checkAuthourData(obj);
							if(response=="success"){
								emailArray.push(value);	
							}else{
								var invalidObj = {};
								invalidObj.errorvalidation="Already invited email id will be automatically removed from the list.";
								renderTemplate("#validationtmpl",invalidObj,"#errormessage");
								$('#errormessage').fadeIn();
								setTimeout(function(){ $('#errormessage').fadeOut(); }, 3000);
							}
						}
					}		
				});
				$('.emailuser').val(emailArray);
				if(emailArray.length!=0){

				}else{
					var invalidObj = {};
					invalidObj.errorvalidation="Invalid and your own email id will be automatically removed from the list.";
					renderTemplate("#validationtmpl",invalidObj,"#errormessage");
					$('#errormessage').fadeIn();
					setTimeout(function(){ $('#errormessage').fadeOut(); }, 3000);
					return false;
				}
			}	
		}

};

var commonMyCoursesUrl = courseData.getContextPath()+"/rest/mycourses/";

var coursesCount = {
		home : function(){
			this.view();
		},

		view : function(){
			var thislist = this.list();
			
			$('.learningcoursescouunt').text(thislist[0].learning);
			$('.compcoursecount').text(thislist[0].completed);
			$('.wishlistcount').text(thislist[0].wishlisted);
			$('.authcourselink[st=dra] .count').text(thislist[0].draftcourses);
			$('.authcourselink[st=pub] .count').text(thislist[0].publishedcourses);
			$('.authcourselink[st=app] .count').text(thislist[0].approvedcourses);
			$('.authcourselink[st=trash] .count').text(thislist[0].trashedcourses);
		},

		list : function(){
			var list = Ajax.get(commonMyCoursesUrl+"count",undefined,"json","application/json");
			return list = _.isObject(list) ? list : null;
		} 
};