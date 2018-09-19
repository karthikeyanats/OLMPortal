$(document).ready(function(){
	report.reportview();
});

var report={
		reportview:function(){
			var self=this;
			var reports=self.reportData();
			renderTemplate("#reporttmpl",reports, "#timeline");
			$('.ratingclass').each(function(){
				var rating=$(this).attr('rank');
				$(this).raty({readOnly: true, score: rating,
					starHalf      : courseData.getContextPath()+"/"+'assets/app/images/star/star-half.png',
					starOff       : courseData.getContextPath()+"/"+'assets/app/images/star/star-off.png', 
					starOn        : courseData.getContextPath()+"/"+'assets/app/images/star/star-on.png', });
			});	
			$('.notescount').text($('.eachString:first').attr('notes'));
			$('.askedqnscount').text($('.eachString:first').attr('questionsasked'));
			$('.answeredqnscount').text($('.eachString:first').attr('questionsanswered'));
			$('.enrolledDate').text($('.eachString:first').attr('enrolleddate'));
			$('.completdDate').text($('.eachString').attr('completeddate'));
			$('.crstitle').text($('.eachString:first').attr('coursetitle'));
			$('.cbp_tmtime').each(function(){
				$(this).html("<span class='timespancls'>"+$(this).attr('attime')+"</span>");
			});
			var timespent=[];
			$('.timespentclass').each(function(){
				timespent.push($(this).attr('timespent'));
			});
			var totaltimespent=self.sumMultipleTimesSpent(timespent);
			$('.timespentholder').text(totaltimespent);
		},
		reportData:function(){
			var self=this;
			var courseenrollobj={};
			courseenrollobj.courseenrollmentid=$('[name=courseenrollmentid]').val();
			return self.reportController(courseenrollobj);
		},
		reportController:function(courseenrollobj){
			var reportList=courseData.getCompletedCourseReport(courseenrollobj);
			var noofnotes=_.where(reportList, {differentiator: "note"}).length;
			var questionsanswered=_.where(reportList, {differentiator: "questionans"}).length;
			var questionsasked=_.where(reportList, {differentiator: "question"}).length;
			var formatedList=_.sortBy(reportList , function(o) { 
				var dt = new Date(o.takenat); 
				return dt; 
			});
			_.each(formatedList,function(o){
				var splittedDateArray=o.takenat.split("-");
				var splittedDate=splittedDateArray[0]+"-"+splittedDateArray[1]+"-"+splittedDateArray[2];
				var monthNames = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun",
				                   "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ];
				var month=monthNames[splittedDateArray[0]-1];
				var formattedDate=splittedDateArray[1]+"-"+month+"-"+splittedDateArray[2];
				o.notes=noofnotes;
				o.questionsasked=questionsasked;
				o.questionsanswered=questionsanswered;
				o.formattedDate=formattedDate;
				o.splittedTime=splittedDateArray[3];
				o.splittedDate=splittedDate;
				if(o.differentiator=="scratch"){
					var actualFilePath = o.notespath;
					o.actualFileName = actualFilePath.split('\\').pop().split('/').pop();
				}
			});
			var formatedGroupList=_.groupBy(formatedList, 'splittedDate');
			var formatedTimeList=_.sortBy(formatedGroupList, function(o) { 
				var dt = new Date(o); 
				return dt; 
			});

			return formatedTimeList;
		},
		sumMultipleTimesSpent:function(times){	
			var existhrs="00:00:00";
			for(var i=0;i<times.length;i++){
				curtime=times[i].split(/\D/);
				exttime=existhrs.split(/\D/);
				var x1=parseInt(curtime[0])*60*60 + parseInt(curtime[1])*60 + parseInt(curtime[2]);
				var x2=parseInt(exttime[0])*60*60 + parseInt(exttime[1])*60 + parseInt(exttime[2]);
				var s=x1+x2;
				var m=Math.floor(s/60); s=s%60;
				var h=Math.floor(m/60); m=m%60;
				var result = [h  , // HOURS		 
				              m, // MINUTES			  		   
				              s // SECONDS
				              ];
				result = result.map(function(v) {
					return v < 10 ? '0' + v : v;
				}).join(':');
				existhrs=result;
			}
			var hrs=existhrs.split(":");	
			if(hrs[0]!="00"){
				tothrs=hrs[0]+" Hrs ";
			}
			else{
				tothrs="";
			}
			if(hrs[1]!="00"){
				totmins=hrs[1]+" Mins ";
			}
			else{
				totmins="0";
			}
			if(hrs[2]!="00"){
				totsecs=hrs[2]+" Secs ";
			}
			else{
				totsecs="0";
			}

			if(tothrs!="0"){
				tothrs;
			}				
			if(totmins!="0"){
				tothrs=tothrs+totmins;
			}				
			if(totsecs!="0"){
				tothrs=tothrs+totsecs;
			}
			return tothrs;
		}
};