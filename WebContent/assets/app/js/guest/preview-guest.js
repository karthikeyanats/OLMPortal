/*$(document).ready(function(){
	$('.review').click(function(){
		$('.review').removeClass("reviewactive");
		$(this).addClass("reviewactive");
	});
	$('.reviewdata').click(function(){
		$('#review').addClass('active');
		$('#review').removeClass('clshide');
		$('#discussion').removeClass('active');
		$('#discussion').addClass('clshide');
	});
	$('.discussiondata').click(function(){
		$('#discussion').addClass('active');
		$('#discussion').removeClass('clshide');
		$('#review').removeClass('active');
		$('#review').addClass('clshide');
	});
});*/


$(document).ready(function()
		{
	preview.loadReview($('[name=courseid]').val());
	
	var masterorgidobj=courseData.getMasterAdminId();
	var masterorgid=masterorgidobj[0].organizationid;
	$('[name=organizationid]').val(masterorgid);
	
	/*$.validator.addMethod("regname", function(value, element, regexpr) {          
		return regexpr.test(value);
	}, "Invalid Full Name.");

	$.validator.addMethod("isDuplicate", function(value, element) {
		  return userNameDuplicateCheck();
		},"Already Exists, please login to continue");
	
	$("#courseinfo").validate({	
		rules: {

			email: {
				required: true,
				email: true,				
			},
			emailid:{
				isDuplicate:true
			}
		},
		messages: {
			email: "Please enter a valid email address"	
		}

	});*/

	loadcontent($('[name=courseid]').val());
	$('#userpassword').change(function() 
			{
		validatePassword();
			});

	$('.signinbtn').click(function(){
		$('#signUpModal').hide();
		$('#signinModal').show();
		$('.modal-backdrop.in').hide();
	});
	$('.signupmodalbtn').click(function(){
		$('#signinModal').hide();
		$('#signUpModal').show();
	});
	$(".signUpModalClose").click(function(){
		$('.modal-backdrop.in').hide();
	});
		});
preview={
		loadReview:function(courseid){
			var self=this;
			courseidobj={};
			courseidobj.courseid=courseid;
			self.allcoursereviews(courseidobj);
			
		},
		
		allcoursereviews:function(courseidobj){
			var self=this;
			var courserating=courseData.loadCourseReviews(courseidobj);
			self.renderCourseRating(courserating);	
		},
	
		
		renderCourseRating:function(courserating){ 
			if(courserating!=undefined){
				renderTemplate('#ratingstmpl',courserating,'#allreviewtable');
				$('#allreviewtable').slimScroll({
			       // height: '430px'
			    });
				$('.ratediv').each(function(){
					var rating=$(this).attr('data-score');
					$(this).raty({ readOnly: true,score: rating,
						starHalf      : courseData.getContextPath()+"/"+'assets/app/images/star/star-half.png',
					    starOff       : courseData.getContextPath()+"/"+'assets/app/images/star/star-off.png', 
					    starOn        : courseData.getContextPath()+"/"+'assets/app/images/star/star-on.png', 
					    });   
				});
			}
			else{
				renderTemplate('#noratingstmpl',courserating,'#allreviewtable');						
			}
		},
		rateCourseData:function(courseratingobj){
			var self=this;
			if($('.ratestextarea').val()!=''){
				var savedRating=courseData.saveRating(courseratingobj);
				$('[name=score]').val('');
				$('.ratestextarea').val('');
				if(savedRating!=undefined){
					self.loadReview($('[name=courseid]').val());
				}
			}			
		}
};

function loadcontent(courseid)
{
	var courseidobj={};
	courseidobj.courseid=courseid;


	var tobeformatedList=courseData.getCourseContent(courseidobj);
	formatList(tobeformatedList);
	var courselists=courseData.getCourseDetails(tobeformatedList[0].orgpersonid);
	var workexperienceList=courseData.getWorkExperienceByPersonId(tobeformatedList[0].personid);
	//formatLists(courselists);
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
	loadCourseLists(courselists);
}
function formatList(formatList)
{
	loadMainContent(formater.formatCourseMainContent(formatList));
	loadCourseInfo(formater.formatCourseInfoContent(formatList));
	loadInfoContent(formater.formatCourseInfoContent(formatList));
	loadHeaderContent(formater.formatCourseHeaderContent(formatList));
}


function loadCourselistInfo(infoContent){
	

	
}

function loadInfoContent(infoContent)
{

	renderTemplate("#basicinfotmpl",infoContent,"#basicinfotable");

	var facebookurl={};
	facebookurl.link="http://"+$('#metaURI').html()+courseData.getContextPath()+"/preview?courseid="+$('[name=courseid]').val();
	facebookstatistics.loadFacebookCount(facebookurl);
	$('.pricebtn').each(function()
			{

		var price=$(this).attr('price');
		var priceicon = $(this).attr('priceicon');
		var pricelable = "Price :&nbsp;&nbsp;";
		if(price=="Free"){
			$(this).html(pricelable + "Free");
		}
		else if(price==""){
			$(this).html("");
		}
		else{
			$(this).html(pricelable+"<i class='fa "+priceicon+"'></i>"+"<span style=color:#0E800E;>"+price+"</span>");	
		}
			});
}

function loadHeaderContent(headerContent)
{
	renderTemplate("#courseheadertmpl",headerContent,"#courseheadertable");
	renderTemplate("#courssummery",headerContent,"#coursesummery");
	$('.videocount').html($('.contenttypeclass[contenttype=video]').length);
	$('.pdfcount').html($('.contenttypeclass[contenttype=pdf]').length);
	$('.startlearninglinkbtn').click(function(){
		var status=$("#userstatus").val();
		var modalel=$('#signUpModal');
		if(status=="yes"){
			modalel.show();
			modalel.modal('show');
			$('.modal-backdrop.in').show();
			$('#termsandconditions').click(function(){
				$('#termsModal').modal('show');	
			});
		}else if(status=="no"){
			$('.modal-backdrop.in').hide();
			modalel.hide();
			$('#signinModal').modal('show');
		}else{
			modalel.show();
			modalel.modal('show');
			$('.modal-backdrop.in').show();
			$('#termsandconditions').click(function(){
				$('#termsModal').modal('show');	
			});
		}	
	});

	
	//$('#countid').text($('[name=count]').val());
	if($('[name=count]').val()!==""){
		$('#countid').text($('[name=count]').val()); 
	}
	else{
		$('#countid').text("0"); 
	}
}
function loadCourseInfo(infoContent){
	renderTemplate("#courseinfotmpl",infoContent,"#courseinformtable");
}


function loadMainContent(mainContentList)
{
	
	//loadcourseInfo();
	renderTemplate("#coursecontenttmpl",mainContentList,"#coursecontenttable");
	$('.contenttypeclass').each(function()
			{
		conttype=$(this).attr('contenttype');
		if(conttype=="video")
		{
			//$(this).css('background-color','#1abc9c');
			$(this).html("<i class='fa fa-video-camera'></i>");
		}
		else if(conttype=="pdf")
		{
			//$(this).css('background-color','red');
			$(this).html("<i class='fa fa-file-pdf-o'></i>");
		}
		else if(conttype=="swf")
		{
			$(this).css('background-color','orange');
			$(this).html("<img src='"+courseData.getContextPath()+"/assets/app/images/contenttype/swf.png'></img>");
		}
		else
		{
			$(this).css('background-color','red').attr('disabled','disabled');
			$(this).html("<img src='"+courseData.getContextPath()+"/assets/app/images/contenttype/nocontent.png'></img>");
		}
			});
	$('.durationcls').each(function(){
		var ctype=$(this).attr('contenttype');
		var duration=$(this).attr('duration');
		if(ctype==="pdf"){
			var findme = "pages";
			if ( duration.indexOf(findme) > -1 ) {
				$(this).html("<i class='fa fa-file-text-o' style='margin-right:5px;'></i>"+duration);
			} 
			else {
				$(this).html("<i class='fa fa-file-text-o' style='margin-right:5px;'></i> 0 pages");
			}
		}
		else{
			var findme1 = ":";
			if (duration.indexOf(findme1) > -1 ) {
				$(this).html("<i class='fa fa-clock-o' style='margin-right:5px;'></i>"+duration);
			} 
			else {
			}
		}
	});
} 




function loadCourseLists(courselists){
	if(courselists.length!=0){
		renderTemplate("#coursenametmpl",courselists,"#courselissttable");
	}
	else{
			$('#courselissttable').hide();
	}
	
	$('.popcourselink').click(function(){
		var courseid=$(this).attr('courseid');	
		$('[name=courseid]').val(courseid);
		previewCourseData(courseid);
	
		});

	$('.pricebtn').each(function()
			{

		var price=$(this).attr('price');
		var priceicon = $(this).attr('priceicon');	
		var pricelable = "Price :&nbsp;&nbsp;";
		if(price=="Free"){
			$(this).html(pricelable + "Free");
		}
		else if(price==""){
			$(this).html("");
		}
		else{
			$(this).html(pricelable+"<i class='fa "+priceicon+"'></i>"+price+"</span>");	
		}
			});
	
	
	function previewCourseData(courseid){
		$('[name=courseid]').val(courseid);
		
		$('[name=popcourseform]').attr('action','preview');
		$('[name=popcourseform]').submit();
	}
}



function loadWorkExperience(workExperienceList){
	renderTemplate("#authorprofessproftmpl",workExperienceList,"#authorprofessproftable");
}

function validatePassword() {
	if ($('#userpassword').val().length < '8') 
	{
		$('#alertBoxPassword').show();
		$('#alertBoxPassword').removeClass('alert-success');
		$('#alertBoxPassword').addClass('alert-danger');
		$('#alertBoxPasswordmsg').text('Password Value Must not be less than 8 Charactors !	');
	} 
	else 
	{
		if($('#userpassword').val().length == '8'||$('#userpassword').val().length >= '8')
		{
			$('#alertBoxPassword').hide();		
		}
	}
}

function userNameDuplicateCheck()
{
	usernameobj={};
	usernameobj.organizationid=$('[name=organizationid]').val();
	usernameobj.username=$('#emailid').val();
	useravailability=courseData.getUserNameAvailability(usernameobj);
	if(useravailability=="Exits")
	{
		return false;
	}
	else
	{
		return true;
	}
}
facebookstatistics={
		loadFacebookCount:function(facebookurl){
			var self=this;
			$('.fbreputation').click(function(){
			if(!$('.facecount').is(':visible')){
				$('.fbcaret').removeClass('fa-caret-square-o-down');
				$('.fbcaret').addClass('fa-caret-square-o-up');
			var facebookcountlist=courseData.getFacebookCount(facebookurl);
			var facebookcounttmp=[];
			    facebookcounttmp[0]={};
			if(facebookcountlist.length>0){
			    facebookcounttmp[0].likecount=facebookcountlist[0].likecount;
			    facebookcounttmp[0].sharecount=facebookcountlist[0].sharecount;
			    facebookcounttmp[0].commentcount=facebookcountlist[0].commentcount;
			    facebookcounttmp[0].totalcount=facebookcountlist[0].totalcount;
			}
			if(facebookcountlist.length==0 || facebookcountlist=="INTERNAL_SERVER_ERROR"){
				facebookcountlist=[];
				facebookcountlist[0]={};
				facebookcountlist[0].likecount=0;
				facebookcountlist[0].sharecount=0;
				facebookcountlist[0].commentcount=0;
				facebookcountlist[0].totalcount=0;				
			};
			var properties=[];
			properties=Object.keys(facebookcountlist[0]);
			for(var i=0;i<properties.length;i++){
				var result=self.largeBitConversion(facebookcountlist[0][properties[i]]);
				facebookcountlist[0][properties[i]]=result;
			};
			if(facebookcounttmp.length>0){
			facebookcountlist[0].likecountexp=facebookcounttmp[0].likecount;
			facebookcountlist[0].sharecountexp=facebookcounttmp[0].sharecount;
			facebookcountlist[0].commentcountexp=facebookcounttmp[0].commentcount;
			facebookcountlist[0].totalcountexp=facebookcounttmp[0].totalcount;
			}
			renderTemplate("#facebookcounttmpl",facebookcountlist[0],"#facebookcount");
			$('.facecount').slideToggle();
			$('.fbcount').tooltip();
			}
			else{
				$('.facecount').slideToggle();
				$('.fbcaret').removeClass('fa-caret-square-o-up');
				$('.fbcaret').addClass('fa-caret-square-o-down');
			}
			});
		},
		//Thousand,Lakh,Million,Billion Conversion
		largeBitConversion:function(value){
			var choice=0;
			var enumValue = {
					"BILLION" : "B",
					"MILLION" : "M",
					"LAKH" : "L",
					"THOUSAND" : "K",
			};
			if(value>=1000 & value<100000){
				choice= 1;
			}else if (value>=100000 & value<1000000){
				choice= 2;
			}else if (value>=1000000 & value<10000000){
				choice= 3;
			}else if(value>=100000000) {
				choice= 4;
			};
			 
			switch (choice){ 			
				case 1:		
					return  Math.floor(value/1000)+enumValue.THOUSAND;						
		            break;	             
				case 2:					
					return  Math.floor(value/10000)+enumValue.LAKH;						
		            break;	                
				case 3:	
					return  Math.floor(value/1000000)+enumValue.MILLION;						
		            break;	                
				case 4:	
					return  Math.floor(value/100000000)+enumValue.BILLION;						
		            break; 			            
			     default:
			            return value; 
			}
 },
		
		
	
}