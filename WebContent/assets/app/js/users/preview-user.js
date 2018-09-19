$(document).ready(function(){
	var courseid=$('[name=courseid]').val();
	preview.loadReview($('[name=courseid]').val());
	var courseenrollmentid=$('[name=courseenrollmentid]').val();
	var wishlisted=$('[name=wishlisted]').val();
	var socialmsg=$('[name=socialmsg]').val();
	if(courseenrollmentid==""){
		    loadcoursecontent(courseid);
	}
	else if(wishlisted==""){
		loadcontent(courseid);
	}
	else{
		loadcoursecontent(courseid);
	}
		if(socialmsg=="synchronized"){
			$(".sharelink").trigger("click");
	}
		
		
		  $('.giftcourse').click(function(){
				$('#courseid').val();
				$("[name=coursetitle]").val($(this).attr('coursetitle'));
				$("[name=courselogo]").val($(this).attr('courselogo'));
				$('[name=giftcourseform]').attr('action','giftcourse');
				$('[name=giftcourseform]').submit();
				});
});
preview={
		loadReview:function(courseid){
			var self=this;
			var courseidobj={};
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
				renderTemplate('#noratingstmpl','','#allreviewtable');						
			}
		}, 
};
function loadcontent(courseid)
{
	var courseidobj={};
	courseidobj.courseid=courseid;
	var tobeformatedList=courseData.getCourseContent(courseidobj);
	formatList(tobeformatedList);
	loadprofessionalinfo(tobeformatedList[0].personid);
	var coursespenttime=courseData.getCourseSpentTime(courseidobj);	
	console.log(coursespenttime);
	if(coursespenttime==undefined)
		{
		$("#timespentspan").text("00 Mins");
		}
	else
		{
		
		console.log(coursespenttime[0].timespent);
		var timespent=	sumMultipleTimesSpent(coursespenttime);	
		if(timespent!=null)
		$("#timespentspan").text(timespent);
		else
			$("#timespentspan").text("00 Mins");	

		}
	
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

function loadcoursecontent(courseid){
	var courseidobj={};
	courseidobj.courseid=courseid;
	var tobeformatedList=courseData.getCourseContent(courseidobj);
	formatcourseList(tobeformatedList);
	var courselists=courseData.getCourseDetails(tobeformatedList[0].orgpersonid);
	loadprofessionalinfo(tobeformatedList[0].personid);
	loadCourseLists(courselists);
}
function formatList(formatList)
{
	loadInfoContent(formater.formatCourseInfoContent(formatList));
	loadMainContent(formater.formatCourseMainContent(formatList));
	loadHeaderContent(formater.formatCourseHeaderContent(formatList));	
}

function formatcourseList(formatList){
	loadMainContent(formater.formatCourseMainContent(formatList));
	loadInfoContent(formater.formatCourseInfoContent(formatList));
	loadCourseInfo(formater.formatCourseInfoContent(formatList));
	loadcourseHeaderContent(formater.formatCourseHeaderContent(formatList));	
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
		var user = $('#rolename').val();
		if(user=="user") { 
			$('[name=popcourseform]').attr('action','previewuser'); 
			$('[name=popcourseform]').submit();
		}else {
			$('[name=popcourseform]').attr('action','preview');
			$('[name=popcourseform]').submit();
		}
	}
}

function loadWorkExperience(workExperienceList){
	renderTemplate("#authorprofessproftmpl",workExperienceList,"#authorprofessproftable");
}
function loadCourseInfo(infoContent){
	renderTemplate("#courseinfotmpl",infoContent,"#courseinformtable");
}
function loadInfoContent(infoContent)
{
	 Handlebars.registerHelper('checkFree', function(price,coursetitle,courselogo) { 
		 if(price=="Free"){	return "";}
		 else if(price.trim()==""){return "";}
		 else{
			 return "<li class=\"leftsideli leftsideseperateli\"><a class=\"giftcourse\" style=\"cursor:pointer; color:rgb(51, 51, 51);\" coursetitle='"+coursetitle+"' courselogo='"+courselogo+"'><img src=\"../assets/app/images/gift.jpg\" style=\"height: 28px;width: 42px;\">Gift This Course</a></li>";
		 }
 });

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
			$(this).html(pricelable + "<font style='color:green;'>Free</font>");
		}
		else if(price==""){
			$(this).html("");
		}
		else{
			$(this).html(pricelable+"<i class='fa "+priceicon+"'></i>"+"<span style=color:#0E800E;>"+price+"</span>");	
		}
			});
}

function loadcourseHeaderContent(headerContent){
	renderTemplate("#courseheadertmpl",headerContent,"#courseheadertable");	
	$('.videocount').html($('.contenttypeclass[contenttype=video]').length);
	$('.pdfcount').html($('.contenttypeclass[contenttype=pdf]').length);
	$(".startlearninglinkbtn").addClass("startlearningbtncss");
	$('.startlearninglinkbtn').click(function()
	{
		subscribeCourse();
	});
}
function loadHeaderContent(headerContent)
{
	renderTemplate("#courseheadertmpl",headerContent,"#courseheadertable");
	$('.videocount').html($('.contenttypeclass[contenttype=video]').length);
	$('.pdfcount').html($('.contenttypeclass[contenttype=pdf]').length);
	$(".imgicon").addClass("imgiconcss");
	$(".sharelink").show();
	$(".startlearninglinkbtn").addClass("pull-right startlearninglinkbtncss");
	$(".sharelink").click(function(){
		var photodetails={};
		photodetails.link="http://"+$('#metaURI').html()+courseData.getContextPath()+"/preview?courseid="+$(this).attr('courseid');
		photodetails.name=$(this).attr('coursetitle');
		photodetails.caption="REAMEDI CLOUD";
		photodetails.description=$(this).attr('coursedescription');
		photodetails.message="REAMEDI CLOUD";
 		photodetails.picture="http://"+$('#metaURI').html()+courseData.getContextPath()+"/OpenFile?r1=serverpath&r2="+$(this).attr('courselogo');
		//photodetails.picture="https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcT_YqaFXOMk8zIWsG4GBfcttUSYijgdx6gID05T9PwdUqRT8nj6";		
 		var sharestatus=Ajax.ajaxHelper("POST",$('#context').html()+"/rest/share",photodetails,"json","application/json");
 		var obj={};
 		if(sharestatus=="CREATED"){
 			obj.message="The course has been successfully shared on facebook";
 			$(".contentbackgound").css('margin-top','36px');
 			renderTemplate("#facebookalerttmpl",obj,"#facebookalert");
 			alertCloseEvent();
 			 $(".alerter").fadeIn('slow').delay(3000).fadeOut('slow',function() {
 	 				$(".contentbackgound").css('margin-top','20px');}); 
 		}
 		else if(sharestatus=="NOT_ACCEPTABLE"){
 			var context=$('#context').html();
 			console.log(context);
 			console.log(context+'/rest/publicuser/facebook');
 			$('.sharelink').attr('href',context+'/rest/publicuser/facebook?redirecturi=/app/previewcourse');
 			
 		} 	
 		else{
		    obj.message="Problem in sharing the course on facebook";
		    renderTemplate("#facebookalerttmpl",obj,"#facebookalert");
		    alertCloseEvent();
		 	$(".alerter").removeClass("alert-success");	
		 	$(".alerter").addClass("alert-error");
		 	$(".contentbackgound").css('margin-top','36px');
		 	$(".alerter").fadeIn('slow').delay(3000).fadeOut('slow',function() {
	 		$(".contentbackgound").css('margin-top','20px');});
		}
	
	});	
	
	$('.startlearninglinkbtn').click(function()
	{
		subscribeCourseEnrollment();
	});  
}
function alertCloseEvent(){
	$('.alertclose').click(function(){
		$(".contentbackgound").css('margin-top','20px');		
	});
	}

function subscribeCourse()
{	
	$('[name=startlearningform]').attr('action','coursepayment');
	$('[name=startlearningform]').submit();
}
function subscribeCourseEnrollment()
{	
	$('[name=startlearningform]').attr('action','learnCourse');
	$('[name=startlearningform]').submit();
}
function loadMainContent(mainContentList)
{
	renderTemplate("#coursecontenttmpl",mainContentList,"#coursecontenttable");
	$('.contenttypeclass').each(function(){
		conttype=$(this).attr('contenttype');
		if(conttype=="video")
		{
			$(this).css('background-color','#1abc9c');
			$(this).html("<img src='"+courseData.getContextPath()+"/assets/app/images/contenttype/video.png'></img>");
		}
		else if(conttype=="pdf")
		{
		$(this).css('background-color','red');
			$(this).html("<img src='"+courseData.getContextPath()+"/assets/app/images/contenttype/pdf.png'></img>");
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
function sumMultipleTimesSpent(times)
{	
	console.log("currenthrs:::"+times);
	var existhrs="00:00:00";

	for(var i=0;i<times.length;i++)
		{
		console.log("before add  times[i].timespent :"+times[i].timespent);
			curtime=times[i].timespent.split(/\D/);
			exttime=existhrs.split(/\D/);
			
			var x1=parseInt(curtime[0])*60*60 + parseInt(curtime[1])*60 + parseInt(curtime[2]);
			var x2=parseInt(exttime[0])*60*60 + parseInt(exttime[1])*60 + parseInt(exttime[2]);
			

			var s=x1+x2;
			var m=Math.floor(s/60); s=s%60;
			var h=Math.floor(m/60); m=m%60;

			var result = [				  		  
				  		  h  , // HOURS		 
				  		  m, // MINUTES			  		   
				  		  s // SECONDS
				  		];
				result = result.map(function(v) {
				    return v < 10 ? '0' + v : v;
				}).join(':');
				existhrs=result;
			}
		var hrs=existhrs.split(":");	
	
	if(hrs[0]!="00")
	{
		console.log(hrs[0]+" Hrs");
		tothrs=hrs[0]+" Hrs ";
		}
	else{
		tothrs="";
		}
	if(hrs[1]!="00")
	{
		console.log(hrs[1]+" Mins");
		totmins=hrs[1]+" Mins ";
		}
	else{
		console.log("zero");
		totmins="0";
		}
	if(hrs[2]!="00")
	{
		console.log(hrs[2]+" Secs");
		totsecs=hrs[2]+" Secs ";
		}
	else{
		console.log("zero");
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
 }
		
}
/*giftcourse={
		giftCourseData:function(){
			$('[name=giftcourseform]').attr('action','giftcourse');
			$('[name=giftcourseform]').submit();
		}
		
};*/