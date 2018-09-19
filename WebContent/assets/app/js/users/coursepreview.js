$(document).ready(function(){
	preview.loadPreviewList($('[name=courseenrollmentid]').val());
	preview.loadCourseProgressbar();
	preview.loadReview($('[name=courseid]').val());
	var socialmsg=$('[name=socialmsg]').val();
	if(socialmsg=="sharesync"){
		 $('.sharelink').trigger("click");
	}
	else if(socialmsg=="likesync"){
		$('.likelink').trigger("click");
	}
	var reviewBox = $('#post-review-box');
	  var newReview = $('#new-review');
	  var openReviewBtn = $('#open-review-box');
	  var closeReviewBtn = $('#close-review-box');

	  openReviewBtn.click(function(e)
	  {
	    reviewBox.slideDown(400, function()
	      {
	    	newReview.val('');
	    	$('.newratediv').raty({
	    		starHalf      : courseData.getContextPath()+"/"+'assets/app/images/star/star-half.png',
			    starOff       : courseData.getContextPath()+"/"+'assets/app/images/star/star-off.png', 
			    starOn        : courseData.getContextPath()+"/"+'assets/app/images/star/star-on.png',
	    	});
	    	$('#new-review').trigger('autosize.resize');
	        newReview.focus();
	      });
	    openReviewBtn.fadeOut(100);
	    closeReviewBtn.show();
	  });

	  closeReviewBtn.click(function(e)
	  {
	    e.preventDefault();
	    reviewBox.slideUp(300, function()
	      {
	        newReview.focus();
	        openReviewBtn.fadeIn(200);
	      });
	    closeReviewBtn.hide();
	    
	  });
	  $('.giftcourse').click(function(){
			$('#courseid').val();
			$("[name=coursetitle]").val($(this).attr('coursetitle'));
			$("[name=courselogo]").val($(this).attr('courselogo'));
			$('[name=giftcourseform]').attr('action','giftcourse');
			$('[name=giftcourseform]').submit();
			});
});
var preview={
		loadReview:function(courseid){
			var self=this;
			var courseidobj={};
			courseidobj.courseid=courseid;
			//self.myCourseReviews(courseidobj);
			self.allcoursereviews(courseidobj);
			//self.renderAverageRating(courseidobj);
		},
		myCourseReviews:function(courseidobj){
			var self=this;
			var myReviews=courseData.loadMyCourseReviews(courseidobj);
			if(myReviews!=undefined){	
				$('#splratingdiv').show();
				renderTemplate('#alreadyratedtmpl',myReviews,'#myreviewtable');
				}
				else{					
					renderTemplate('#newratingstmpl',myReviews,'#myreviewtable'); 
					$('.newratediv').raty({
							    starHalf      : courseData.getContextPath()+"/"+'assets/app/images/star/star-half.png',
							    starOff       : courseData.getContextPath()+"/"+'assets/app/images/star/star-off.png', 
							    starOn        : courseData.getContextPath()+"/"+'assets/app/images/star/star-on.png', 
					});  
					$('.rateitbtn').click(function(){
						courseratingdescription=$('.ratestextarea').val();
						if(courseratingdescription!=""){
						var courseratingobj={};
						courseratingobj.courserating=$('.newratediv>[name=score]').val();
						courseratingobj.courseid=$('[name=courseid]').val();
						courseratingobj.courseratingdescription=$('.ratestextarea').val();
						self.rateCourseData(courseratingobj);
						}else{
							renderTemplate('#mandatorytmpl',"",'#mandatory'); 
						}
					});  
				}			
		},
		/*renderAverageRating:function(courseidobj){
			var courserating=courseData.loadCourseReviews(courseidobj);		
			var courseratingids=(_.compact(_.pluck(courserating,'courserating')));
			var courseratingsum = _.reduce(courseratingids, function(memo, num){return memo + parseInt(num);}, 0);
			var ratingaverage=courseratingsum/(courseratingids.length);
			if(_.isNaN(ratingaverage)){		
				$('.avgrating').raty({readOnly: true, score: 0,
					starHalf      : courseData.getContextPath()+"/"+'assets/app/images/star/star-half.png',
				    starOff       : courseData.getContextPath()+"/"+'assets/app/images/star/star-off.png', 
				    starOn        : courseData.getContextPath()+"/"+'assets/app/images/star/star-on.png', 
				    }); 
			}
			else{
				$('.avgrating').raty({readOnly: true, score: ratingaverage,
					starHalf      : courseData.getContextPath()+"/"+'assets/app/images/star/star-half.png',
				    starOff       : courseData.getContextPath()+"/"+'assets/app/images/star/star-off.png', 
				    starOn        : courseData.getContextPath()+"/"+'assets/app/images/star/star-on.png', 	
				}); 
			}
		},*/
		allcoursereviews:function(courseidobj){
			var self=this;
			var courserating=courseData.loadCourseReviews(courseidobj);
			if(courserating!=undefined){
				var sid=$('#studentid').val();
				if(_.contains(_.pluck(courserating, 'orgpersonid'),parseInt(sid))==true){
				$('#splratingdiv').show();
				renderTemplate('#alreadyratedtmpl',courserating,'#myreviewtable');
				}
				else{					
					renderTemplate('#newratingstmpl',courserating,'#myreviewtable'); 
					$('.newratediv').raty({
							    starHalf      : courseData.getContextPath()+"/"+'assets/app/images/star/star-half.png',
							    starOff       : courseData.getContextPath()+"/"+'assets/app/images/star/star-off.png', 
							    starOn        : courseData.getContextPath()+"/"+'assets/app/images/star/star-on.png', 
					});  
					$('.rateitbtn').click(function(){
						courseratingdescription=$('.ratestextarea').val();
						if(courseratingdescription!=""){
						var courseratingobj={};
						courseratingobj.courserating=$('.newratediv>[name=score]').val();
						courseratingobj.courseid=$('[name=courseid]').val();
						courseratingobj.courseratingdescription=$('.ratestextarea').val();
						self.rateCourseData(courseratingobj);
						}else{
							renderTemplate('#mandatorytmpl',"",'#mandatory'); 
						}
					});  
				}
			}else{					
				renderTemplate('#newratingstmpl',courserating,'#myreviewtable'); 
				$('.newratediv').raty({
						    starHalf      : courseData.getContextPath()+"/"+'assets/app/images/star/star-half.png',
						    starOff       : courseData.getContextPath()+"/"+'assets/app/images/star/star-off.png', 
						    starOn        : courseData.getContextPath()+"/"+'assets/app/images/star/star-on.png', 
				});  
				$('.rateitbtn').click(function(){
					courseratingdescription=$('.ratestextarea').val();
					if(courseratingdescription!=""){
					var courseratingobj={};
					courseratingobj.courserating=$('.newratediv>[name=score]').val();
					courseratingobj.courseid=$('[name=courseid]').val();
					courseratingobj.courseratingdescription=$('.ratestextarea').val();
					self.rateCourseData(courseratingobj);
					}else{
						renderTemplate('#mandatorytmpl',"",'#mandatory'); 
					}
				});  
			}
			self.renderCourseRating(courserating);	
			self.rendercoursetotalrating(courserating);
			self.renderindividualrating(courserating);
		},
		renderindividualrating:function(courserating){
			if(courserating!=undefined){
				ranked=[];
				_.each(courserating,function(courserating){
				ranked.push(courserating.courserating);
				});
				
				counts = {};
				var i, value;
				finalreview=[];
				for (i = 0; i < ranked.length; i++) {
				    value = ranked[i];
				    if (typeof counts[value] === "undefined") {
				        counts[value] = 1;
				    } else {
				        counts[value]++;
				    }

				}
				var result = Object.keys(counts);
				var one=_.contains(ranked, "1");
				var two=_.contains(ranked, "2");
				var three=_.contains(ranked, "3");
				var four=_.contains(ranked, "4");
				var five=_.contains(ranked, "5");				
				
				if(one==false){
				result.push("1");
				}
				if(two==false){
					result.push("2");
					}
				if(three==false){
					result.push("3");
					}
				if(four==false){
					result.push("4");
					}
				if(five==false){
					result.push("5");
					}
				function sortmyway(data_A, data_B)
				{
				    return (data_A - data_B);
				}
				result.sort(sortmyway);
				for(var i=0;i<result.length;i++){
				var obj={};
				obj.rankname=result[i];
				obj.rankcount=counts[result[i]];
				finalreview.push(obj);
				}				
				renderTemplate('#specialratingtmpl',finalreview,'#specialrating');
			}else{				
				$('#splratingdiv').hide();
			}
			
		},
		rendercoursetotalrating:function(courserating){
			if(courserating!=undefined){
				var users=courserating.length;
				rank=0;
				_.each(courserating,function(courserating){
				if(courserating.courserating==''){
				courserating.courserating=0;
				}
				rank+=parseInt(courserating.courserating);
				});
				result=parseFloat(rank/courserating.length);
				courserating=parseFloat(Math.round(result * 100) / 100).toFixed(2);
				
				var loadrating=[];
				var jsondata={};
				jsondata.courserating=courserating;
				jsondata.users=users;
				loadrating.push(jsondata);
				
				renderTemplate('#overallcourseratingtmpl',loadrating,'#overallcourserating');
				$('.ratediv').each(function(){
					var rating=$(this).attr('data-score');
					$(this).raty({ readOnly: true,score: rating,
						starHalf      : courseData.getContextPath()+"/"+'assets/app/images/star/star-half.png',
					    starOff       : courseData.getContextPath()+"/"+'assets/app/images/star/star-off.png', 
					    starOn        : courseData.getContextPath()+"/"+'assets/app/images/star/star-on.png', 
					    });   
				});
			}else{
				
			}
		},
		renderCourseRating:function(courserating){ 
			if(courserating!=undefined){
				renderTemplate('#ratingstmpl',courserating,'#allreviewtable');
				/*$('#allreviewtable').slimScroll({
			        height: auto
			    });*/
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
		},
		loadPreviewList:function(courseenrollmentid){
			var self=this;
			var enrollobj={};
			enrollobj.courseenrollmentid=courseenrollmentid;
			self.getpreviewList(enrollobj);
		},
		getpreviewList:function(enrollobj){
			var self=this;
			var previewList=courseData.getCoursePreviewValues(enrollobj);
			self.formatList(previewList);
			self.loadSatStatus(previewList);
		},
		formatList:function(formatList){
			var self=this;
			var completedLecturesList=self.loadCompletedLecturesList($('[name=courseenrollmentid]').val());
			self.loadInfoContent(formater.formatCourseInfoContent(formatList));			
			self.loadMainContent(formater.formatCourseMainContent(formatList),completedLecturesList);
			self.loadHeaderContent(formater.formatCourseHeaderContent(formatList));
			self.loadprofessionalinfo(formatList[0].personid);		
			var facebookurl={};
			facebookurl.link="http://"+$('#metaURI').html()+courseData.getContextPath()+"/preview?courseid="+$('.sharelink').attr('courseid');
			self.loadFacebookCount(facebookurl);
			$('.lecturelearningbtn').attr('courseenrollmentid',$('[name=courseenrollmentid]').val());
		},
		loadCompletedLecturesList:function(courseenrollmentid){
			var enrollobj={};
			enrollobj.courseenrollmentid=courseenrollmentid;
			var statuses=courseData.getLearningStatuses(enrollobj);
			return statuses;
		},
		loadInfoContent:function(infoContent)
		{   
			var priceid = _.pluck(infoContent,'price');
			var coursetitle = _.pluck(infoContent,'coursetitle');
			var courselogo = _.pluck(infoContent,'courselogo');
			
			Handlebars.registerHelper('priceid', function(priceid){ 
				if(priceid != "Free"){
						return "<li class='leftsideli leftsideseperateli'><a class='giftcourse' style='cursor:pointer; color:rgb(51, 51, 51);' coursetitle='"+coursetitle+"' courselogo='"+courselogo+"'><img src='../assets/app/images/gift.jpg' style='height: 28px;width: 42px;'>Gift This Course</a></li>";
				}
			});
			renderTemplate("#basicinfotmpl",infoContent,"#basicinfotable");
		},
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
		loadHeaderContent:function(headerContent){
			var self=this;
			renderTemplate("#courseheadertmpl",headerContent,"#courseheadertable");
			var videos=$('.contenttypeclass[contenttype="video"]').length;
			var pdfs=$('.contenttypeclass[contenttype="pdf"]').length;
			$('.videoscount').html(videos);
			$('.pdfcount').html(pdfs);
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
					obj.message="The course has been successfully shared in facebook";
					$(".contentbackgound").css('margin-top','36px');
					renderTemplate("#facebookalerttmpl",obj,"#facebookalert");
					alertCloseEvent();
					$(".alerter").fadeIn('slow').delay(3000).fadeOut('slow',function() {
						$(".contentbackgound").css('margin-top','20px');}); 
				}
				else if(sharestatus=="NOT_ACCEPTABLE"){
					var context=$('#context').html();
					$('.sharelink').attr('href',context+'/rest/publicuser/facebook?redirecturi=/app/previewcourse');

				} 	
				else{
					obj.message="Problem in sharing the course in facebook";
					renderTemplate("#facebookalerttmpl",obj,"#facebookalert");
					alertCloseEvent();
					$(".alerter").removeClass("alert-success");	
					$(".alerter").addClass("alert-error");
					$(".contentbackgound").css('margin-top','36px');
					$(".alerter").fadeIn('slow').delay(3000).fadeOut('slow',function() {
						$(".contentbackgound").css('margin-top','20px');});
				}
			});	
			$(".likelink").click(function(){
				var photodetails={};
				photodetails.link="http://"+$('#metaURI').html()+courseData.getContextPath()+"/preview?courseid="+$(this).attr('courseid');
				photodetails.name=$(this).attr('coursetitle');
				photodetails.caption="REAMEDI CLOUD";
				photodetails.description=$(this).attr('coursedescription');
				photodetails.message="REAMEDI CLOUD";
				photodetails.picture="http://"+$('#metaURI').html()+courseData.getContextPath()+"/OpenFile?r1=serverpath&r2="+$(this).attr('courselogo');
				//photodetails.picture="https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcT_YqaFXOMk8zIWsG4GBfcttUSYijgdx6gID05T9PwdUqRT8nj6";		
				var sharestatus=Ajax.ajaxHelper("POST",$('#context').html()+"/rest/share/like",photodetails,"json","application/json");
				var obj={};
				if(sharestatus=="CREATED"){
					obj.message="You liked the course in facebook";
					$(".contentbackgound").css('margin-top','36px');
					renderTemplate("#facebookalerttmpl",obj,"#facebookalert");
					alertCloseEvent();
					$(".alerter").fadeIn('slow').delay(3000).fadeOut('slow',function() {
						$(".contentbackgound").css('margin-top','20px');}); 
				}
				else if(sharestatus=="NOT_ACCEPTABLE"){
					var context=$('#context').html();
					$('.likelink').attr('href',context+'/rest/publicuser/facebook?redirecturi=/app/previewcourse');

				} 	
				else{
					obj.message="Problem in liking the course in facebook";
					renderTemplate("#facebookalerttmpl",obj,"#facebookalert");
					alertCloseEvent();
					$(".alerter").removeClass("alert-success");	
					$(".alerter").addClass("alert-error");
					$(".contentbackgound").css('margin-top','36px');
					$(".alerter").fadeIn('slow').delay(3000).fadeOut('slow',function() {
						$(".contentbackgound").css('margin-top','20px');});
				}

			});	
			function alertCloseEvent(){
				$('.alertclose').click(function(){
					$(".contentbackgound").css('margin-top','20px');		
				});
			}
			$('.startlearninglinkbtn').click(function(){
				$('[name=startlearningform]').attr('action','learnCourse');
				$('[name=startlearningform]').submit();
			}); 
			var courseidobj={};
			courseidobj.courseid=$('[name=courseid]').val();
			var coursespenttime=courseData.getCourseSpentTime(courseidobj);	
			if(coursespenttime==undefined)
			{
				$("#timespentspan").text("00 Mins");
			}
			else
			{
				var timespent=	self.sumMultipleTimesSpent(coursespenttime);	
				if(timespent!=null)
					$("#timespentspan").text(timespent);
				else
					$("#timespentspan").text("00 Mins");	

			}
		},
		sumMultipleTimesSpent:function (times)
		{	
			var existhrs="00:00:00";

			for(var i=0;i<times.length;i++)
			{
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

				tothrs=hrs[0]+" Hrs ";
			}
			else{
				tothrs="";
			}
			if(hrs[1]!="00")
			{

				totmins=hrs[1]+" Mins ";
			}
			else{

				totmins="0";
			}
			if(hrs[2]!="00")
			{

				totsecs=hrs[2]+" Secs ";
			}
			else{

				totsecs="0";
			}

			if(tothrs!="0")
				tothrs;
			if(totmins!="0")
				tothrs=tothrs+totmins;
			if(totsecs!="0")
				tothrs=tothrs+totsecs;

			return tothrs;
		},
		loadMainContent:function(mainContentList,completedLecturesList)
		{
			renderTemplate("#coursecontenttmpl",mainContentList,"#coursecontenttable");
			$('.contenttypeclass').each(function(){
				conttype=$(this).attr('contenttype');
				$(this).html($(this).attr('contenttype'));				
			});
			$('.durationcls').each(function(){
				$(this).html($(this).attr('duration'));
			});
			$('.learningstatusic').each(function(){
				var lectureid=$(this).attr('courselectureid');
				var statusarray=_.where(completedLecturesList,{courselectureid:parseInt(lectureid)});
				var statuscode={};
				if(statusarray.length>1)
				{
					var statusarray1=_.where(statusarray,{learningstatus:"Completed"});
					if(statusarray1!=undefined){
						statuscode=statusarray1;						
					}
					else{
						statuscode=_.last(statusarray);
					}					
				}
				else{
					statuscode=statusarray;
				}				
				var status='';
				if(statuscode[0]!=undefined){
					status=statuscode[0].learningstatus;
				}
				else{
					status='not started';
				}
				$(this).attr('learningstatus',status);
				var cstat=$(this).attr('learningstatus');				
				if(cstat=="Completed"){
					$(this).attr('title','Completed');
					$(this).html("<i class='icon-circle' style='line-height: 25px;background: none !important;color: green !important;font-size: 25px;'></i>");
				}
				else if(cstat=="learning"){
					$(this).attr('title','Learning');
					$(this).html("<i class='icon-circle' style='line-height: 25px;background: none !important;color: orange !important;font-size: 25px;'></i>");
				}
				else if(cstat=="not started"){
					$(this).attr('title','Not Started');
					$(this).html("<i class='icon-circle' style='line-height: 25px;background: none !important;color: rgb(230, 230, 230) !important;font-size: 25px;'></i>");
				}
				else {
					$(this).attr('title','Not Started');
					$(this).html("<i class='icon-circle' style='line-height: 25px;background: none !important;color:rgb(230, 230, 230) !important;font-size: 25px;'></i>");
				}
			});
			$('.durationspent').each(function(){
				var lectureid=$(this).attr('courselectureid');
				var statusarray=_.where(completedLecturesList,{courselectureid:parseInt(lectureid)});
				var statuscode={};
				if(statusarray.length>1)
				{
					var statusarray1=_.where(statusarray,{learningstatus:"Completed"});
					if(statusarray1!=undefined){
						statuscode=statusarray1;						
					}
					else{
						statuscode=_.last(statusarray);
					}					
				}
				else{
					statuscode=statusarray;
				}				
				var spentime='';
				if(statuscode[0]!=undefined){
					spentime=statuscode[0].timespent;
				}
				else{
					spentime='0:00';
				}
				$(this).attr('timespent',spentime);
				var spentime=$(this).attr('timespent');
				$(this).html("time spent : "+spentime);

			});
			$('.lecturelearningbtn').click(function(){
				$('[name=courslectureidhidden]').val($(this).attr('courselectureid'));
				$('[name=courseenrollmentidhidden]').val($(this).attr('courseenrollmentid'));
				$('[name=coursecontentform]').attr('action','learnCourseP');
				$('[name=coursecontentform]').submit();
			}); 
		},
		loadprofessionalinfo:function(personid){
			var self=this;
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
				professionaldetails.number=workexperienceList[i].number;
				professionaldetails.emailid=workexperienceList[i].emailid;
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
			self.loadWorkExperience(professionaldetails);
		},
		loadWorkExperience:function(workExperienceList){
			renderTemplate("#authorprofessproftmpl",workExperienceList,"#authorprofessproftable");
		},
		loadSatStatus:function(lectureid){
			var lectureids=_.pluck(lectureid, 'courselectureid');
			var data=Ajax.get(courseData.getContextPath()+"/rest/connector/getSATSatus?lectureid="+lectureids.toString(),null,"json","application/json");
			var i=0;
			
			if (data != "NOT_FOUND"){
			$('.satStatus').each(function(){
				 element=$(this).children();
				 
				 if(data[i].correct == null && data[i].skip == null && data[i].wrong == null  ){
					 	element.eq(0).text("  0  ");//total 
						element.eq(1).text("  0  "); // ans
						//element.eq(2).text("  0  "); // skip
						element.eq(3).text("  0  ");
				 }else{
					var crt=parseInt(data[i].correct);
					var wrng=parseInt(data[i].wrong);
					var skip=parseInt(data[i].skip);
					element.eq(0).text(crt+wrng+skip);//total 
					element.eq(1).text(crt+wrng); // ans
					//element.eq(2).text(skip); // skip
					element.eq(2).text(crt);
				}
				i++;
			});
			}
		},
		loadCourseProgressbar:function(){
			$('#courseprogresstable').show();
			$('.complecture').text($('.learningstatusic[learningstatus=Completed]').length);
			$('.totlecture').text($('.learningstatusic').length);
			var progresspercent=($('.learningstatusic[learningstatus=Completed]').length)/($('.learningstatusic').length)*100;			
			$('.prg').html(progresspercent.toFixed(2)+"%");
			$('.prgbar').css('width',progresspercent.toFixed(2)+"%");
		}
};