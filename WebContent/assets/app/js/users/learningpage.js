var starttime;
var endtime;
var spendtime;

$(window).on('beforeunload', function() {
	var endtime=getLectureEndTime();
	$("#contentlecendtime").attr("endtime",endtime);
	calculateLectureTimeSpend();
	insertTimeSpend($('.current').attr('coursesectionid'),$('.current').attr('courselectureid'));	
});

$(document).ready(function(){	
	learnCourse.documentload();

	calculateLectureTimeSpend();
	SATEvaluation.Views.configModal();
	
	
	if ($("#rolename").val() == "user")
		$("[al='reference']").hide();
	

	$('.previewbtn').click(function()	{
		$('[name=courseid]').val($('.coursetitleclass').attr('courseid'));
		$('[name=previewform]').attr('action','previewcourse');
		$('[name=previewform]').submit();
	}); 

});
Handlebars.registerHelper('decode', function(notesdescription) {
	return decodeURIComponent(notesdescription.replace(/\+/g,' '));
});	

learnCourse={
		documentload:function(){
			var self=this;
			self.loadLectures(self.returncourseenrollmentidobj());
			var lectureid='';
			if($('[name=courselectureid]').val()!=''){
				lectureid=$('[name=courselectureid]').val();
			}
			else{
				lectureid=$('.lecturetitle:first').attr('courselectureid');
			}
			self.UnitHelper(lectureid);
			self.loadSubjectParts();
		},
		returncourseenrollmentidobj:function(){
			var courseenrollmentidobj={};
			courseenrollmentidobj.courseenrollmentid=$('[name=courseenrollmentid]').val();
			return courseenrollmentidobj;		
		},
		UnitHelper:function(lectureid){
			var self=this;
			self.loadCoursetitle();
			self.loadCourseProgress();
			self.lectureStuffs(lectureid);
		},
		loadLectures:function(courseidobj){
			var self=this;
			var lecturesList=courseData.getLearningDetails(courseidobj);
			renderTemplate("#lecturestmpl", lecturesList, "#alllectures");
			var clectureid='';
			if($('[name=courselectureid]').val()!=''){
				clectureid=$('[name=courselectureid]').val();
			}
			else{
				clectureid=$('.lecturetitle:first').attr('courselectureid');
			}
			$('#tabs div[courselectureid='+clectureid+']').attr('class', 'current');
			$('#tabs div').each(function(i){
				i = i + 1;
				$(this).attr('id', 'tab-' + i);
				if(i !== $('#tabs div').size()){
					$(this).append('<a class="boxshadow tabPagination nextbtn noborder btn lineheight30 btn-default lightgraybg pull-right" style="width:50px;" rel="tab-' + (i + 1) + '"><i class="fa fa-chevron-right"></i></a>');
				}
				if(i !== 1){
					$(this).append('<a class="boxshadow tabPagination previousbtn noborder lineheight30 btn btn-default lightgraybg pull-left" style="width:50px;" rel="tab-' + (i - 1) + '"><i class="fa fa-chevron-left"></i></a>');
				}                
			});
			var LearningStatusList=self.loadLearningStatus($('#tabs div[courselectureid='+clectureid+']').attr('courselectureid'));
			if(LearningStatusList!==undefined && LearningStatusList!==''){
				if(LearningStatusList[0].learningstatus==="Completed"){
					$('#tabs div[courselectureid='+clectureid+']').find('.lecturestats').removeClass("ncomplecturestats").addClass("complecturestats");	
				}
				else{
					$('#tabs div[courselectureid='+clectureid+']').find('.lecturestats').removeClass("complecturestats").addClass("ncomplecturestats");
				}				
			}
			else{
				$('#tabs div[courselectureid='+clectureid+']').find('.lecturestats').removeClass("complecturestats").addClass("ncomplecturestats");
			}
			$('.complecturestats').tooltip({
				placement: 'top',
				title:'Completed'
			});			
			$('.ncomplecturestats').tooltip({
				placement: 'top',
				title:'Mark As Completed'
			});
			$('.ncomplecturestats').click(function()	{
				var status=self.loadLearningStatus($(this).attr('courselectureid'));
				if(status===undefined){
					self.completedStatusdata($(this).attr('courselectureid'));
					$(this).removeClass("ncomplecturestats").addClass("complecturestats");
				}
				else{
					self.completedStatusdata($(this).attr('courselectureid'));
					$(this).removeClass("ncomplecturestats").addClass("complecturestats");			
				}
				self.loadCourseProgress();
			});
			$('#tabs div[class!="current"]').hide();
			$('.tabPagination').click(function(){
				var endtime=getLectureEndTime();
				$("#contentlecendtime").attr("endtime",endtime);
				calculateLectureTimeSpend();
				var courselectureid=$(this).parent('div').attr('courselectureid');
				var coursesectionid=$(this).parent('div').attr('coursesectionid');
				insertTimeSpend(coursesectionid,courselectureid);
				$('.current').removeClass('current');
				$('#tabs div[class!="current"]').hide();
				$('#' + $(this).attr('rel')).show();
				var courselectureid=$('#' + $(this).attr('rel')).find('.lecturetitle').attr('courselectureid');
				self.lectureStuffs(courselectureid);
				var LearningStatusList=self.loadLearningStatus(courselectureid);
				if(LearningStatusList!==undefined)	{
					if(LearningStatusList[0].learningstatus==="Completed"){
						$('#' + $(this).attr('rel')).find('.lecturestats').removeClass("ncomplecturestats").addClass("complecturestats");
					}
					else{
						$('#' + $(this).attr('rel')).find('.lecturestats').removeClass("complecturestats").addClass("ncomplecturestats");
					}					
				}
				else{
					$('#' + $(this).attr('rel')).find('.lecturestats').removeClass("complecturestats").addClass("ncomplecturestats");
				}
				$('.complecturestats').tooltip({
					placement: 'top',
					title:'Completed'
				});
				$('.ncomplecturestats').tooltip({
					placement: 'top',
					title:'Mark As Completed'
				});
				$('.ncomplecturestats').click(function()	{
					var status=self.loadLearningStatus($(this).attr('courselectureid'));
					if(status===undefined)	{
						self.completedStatusdata($(this).attr('courselectureid'));
						$(this).removeClass("ncomplecturestats").addClass("complecturestats");
					}
					else{
						self.completedStatusdata($(this).attr('courselectureid'));
						$(this).removeClass("ncomplecturestats").addClass("complecturestats");				
					}
					self.loadCourseProgress();
				});				
			});		
		},
		completedStatusdata:function(courselectureid){
			var self=this;
			var learnstatsobj={};
			learnstatsobj.courseenrollmentid=$('[name=courseenrollmentid]').val();
			var coursesectionid =$(".lecturetitle:visible").attr("coursesectionid");
			learnstatsobj.courselectureid=courselectureid;
			learnstatsobj.coursesectionid=coursesectionid;			
			learnstatsobj.learningstatus="Completed";
			var startime=$("#contentlecstarttime").attr("starttime");

			var enddtime=getLectureEndTime();
			learnstatsobj.starttime=startime;
			learnstatsobj.endtime=enddtime;
			var timespent=getTimeSpent(startime,enddtime);
			learnstatsobj.timespent=timespent;
			self.setCompletedStatus(learnstatsobj);
		},
		setCompletedStatus:function(learnstatsobj){
			var completedStatus=courseData.setCompletedUnit(learnstatsobj);
			if(completedStatus!=="false"){
				return completedStatus;
			}
		},
		loadLearningStatus:function(courselectureid,selector){
			var self=this;
			var lectureidobj={};
			lectureidobj.courselectureid=courselectureid;
			lectureidobj.courseenrollmentid=self.returncourseenrollmentidobj().courseenrollmentid;
			var LearningStatusList=courseData.getLearningstatus(lectureidobj);
			return LearningStatusList;
		},
		loadCourseProgress:function(){
			var self=this;
			var alecturesList=courseData.getAllLearningLecturesList(self.returncourseenrollmentidobj());
			var alllectures=_.uniq(_.compact(_.pluck(alecturesList,"courselectureid"))).length;
			var clecturesList=courseData.getCompletedLectureDetails(self.returncourseenrollmentidobj());
			var clectures=_.where(clecturesList,{learningstatus:"Completed"}).length;
			if(clectures>alllectures){				
				courseprogress=100;	
			}
			else{
				courseprogress=(clectures/alllectures*100).toFixed(2);	
			}
			/*$('.bar').css('width',courseprogress+"%");
			$('.progresspercent').text(courseprogress);*/
			self.setCourseProgress(courseprogress);
		},
		setCourseProgress:function(courseprogress){
			var self=this;
			var progressobj={};
			progressobj.courseenrollmentid=self.returncourseenrollmentidobj().courseenrollmentid;
			if(courseprogress==100){
				progressobj.courseenrollmentstatus="C";
			}
			else{
				progressobj.courseenrollmentstatus="A";
			}
			progressobj.courseprogress=courseprogress;
			courseData.setCourseProgressLength(progressobj);
		},
		loadCoursetitle:function(){
			var coursetitle=$('.coursetitleclass').attr('coursetitle');
			$('.learningcourse').text(coursetitle);
		},
		lectureStuffs:function(courselectureid){
			var self=this;
			$('.notestaxtarea').attr('courselectureid',courselectureid);
			//$('.questiontextarea').attr('courselectureid',courselectureid);

			var lectureidobj={};
			lectureidobj.courselectureid=courselectureid;

			learninglectureInitialLoadItems.home(courselectureid);

			self.loadContent(lectureidobj);

		},
		loadContent:function(lectureidobj){
			var self=this;
			var contentList=courseData.getLectureContent(lectureidobj);
			self.renderContentData(contentList);	
		},
		renderContentData:function(contentList){
			var self=this;
			if(contentList!=undefined){
				var file = contentList[0].contentpath;
				if(file!=undefined){
					var filetypesplit= file.split('.');
					var filetype= filetypesplit[filetypesplit.length-1];
					if(filetype==="pdf"){
						self.renderpdfcontent(contentList);
					}
					else if(filetype==="swf"){
						self.renderSwfContent(contentList);
					}
					else{
						self.renderVideoContent(contentList);
					}
					var starttime=getLectureStartTime();
					$("#contentlecstarttime").attr("starttime",starttime);
				}
				else{ 
					self.renderNoContent(contentList);
				}
			}else{ 
				self.renderNoContent(contentList);
			}
		},
		renderpdfcontent:function(contentList){
			renderTemplate('#pdfcontenttmpl',contentList,'#contenttable');
			$('.maximum').show();
			$('.minimum').hide();
			if($('.glyphicon glyphicon-resize-full').hasClass('maxi')==true){
				$('.navbar').css('position','static');				 
			}else{
				$('.navbar').css('position','fixed');		
			} 
			$('.maxi').click(function(){
				$('#contenttable').addClass('width100');
				$('.maximum').hide();
				$('.minimum').show();
				$('.lecturesupplementary').hide();
			});
			$('.mini').click(function(){
				$('#contenttable').removeClass('width100');
				$('.maximum').show();
				$('.minimum').hide();				
				$('.lecturesupplementary').show();
			});
		},
		renderSwfContent:function(contentList){
			renderTemplate('#swfcontentmpl',contentList,'#contenttable');
			$('.maximum').show();
			$('.minimum').hide();
			$('.maxi').click(function(){
				$('#contenttable').addClass('width100');
				$('.maximum').hide();
				$('.minimum').show();
				$('.lecturesupplementary').hide();
			});
			$('.mini').click(function(){
				$('#contenttable').removeClass('width100');
				$('.maximum').show();
				$('.minimum').hide();				
				$('.lecturesupplementary').show();
			});

		},
		renderVideoContent:function(contentList){
			renderTemplate('#videocontenttmpl',contentList,'#contenttable');
			$('.flowplayer').flowplayer();
			$('.maximum').show();
			$('.minimum').hide();
			$('.maxi').click(function(){
				$('#contenttable').addClass('width100');
				$('.maximum').hide();
				$('.minimum').show();
				$('.lecturesupplementary').hide();
			});
			$('.mini').click(function(){
				$('#contenttable').removeClass('width100');
				$('.maximum').show();
				$('.minimum').hide();				
				$('.lecturesupplementary').show();
			});
		},
		renderNoContent:function(contentList){
			renderTemplate('#nocontenttmpl',contentList,'#contenttable');			
		},
		loadSubjectParts:function(){
			var self=this;
			var lecturesList=courseData.getLearningDetails(self.returncourseenrollmentidobj());
			renderTemplate("#subjectpartstmpl", lecturesList, ".dropdownlecturestable");
			$('.slidetoggledrop').click(function(){				
				$('.dropdownlecturestable').slideToggle();
				$( '.tree .lecclass').children().hide();
				self.makeActiveLingLecture();
			});
			self.treecollapse();
			$('.grandchildtitles').click(function(){
				var endtime=getLectureEndTime();
				$("#contentlecendtime").attr("endtime",endtime);
				$('.grandchildtitles').removeClass('active');
				$(this).addClass('active');
				var coursesectionid = $(this).attr('coursesectionid');
				var courselectureid = $(this).attr('courselectureid');
				calculateLectureTimeSpend();
				insertTimeSpend(coursesectionid,courselectureid);
				var starttime=getLectureStartTime();
				$("#contentlecstarttime").attr("starttime",starttime);				
				var courselectureid=$(this).attr('courselectureid');
				$('.current').removeAttr('class');
				$('#tabs div[class!="current"]').hide();
				$('.lecturetitle[courselectureid="'+courselectureid+'"]').parent().show();

				var learningStatusList=self.loadLearningStatus(courselectureid);
				if(learningStatusList!==undefined)	{
					if(learningStatusList[0].learningstatus==="Completed"){
						$('.lecturestats[courselectureid="'+courselectureid+'"]').removeClass("ncomplecturestats").addClass("complecturestats");
					}
					else{
						$('.lecturestats[courselectureid="'+courselectureid+'"]').removeClass("complecturestats").addClass("ncomplecturestats");
					}					
				}
				else{
					$('.lecturestats[courselectureid="'+courselectureid+'"]').removeClass("complecturestats").addClass("ncomplecturestats");
				}
				$('.dropdownlecturestable').hide();
				$('.ncomplecturestats').click(function()	{
					var status=self.loadLearningStatus($(this).attr('courselectureid'));
					if(status===undefined){
						self.completedStatusdata($(this).attr('courselectureid'));
						$(this).removeClass("ncomplecturestats").addClass("complecturestats");
					}
					else{
						self.completedStatusdata($(this).attr('courselectureid'));
						$(this).removeClass("ncomplecturestats").addClass("complecturestats");			
					}
					self.loadCourseProgress();
				});
				self.lectureStuffs(courselectureid);
			});
		},
		makeActiveLingLecture:function(){
			var courselectureid=$( 'div[curr]:visible .lecturetitle').attr('courselectureid');
			$( ".grandchildtitles").removeClass('active');
			$( ".grandchildtitles[courselectureid|='"+courselectureid+"']").parent().parent().children().show();
			$( ".grandchildtitles[courselectureid|='"+courselectureid+"']").addClass('active');			
		},
		treecollapse:function(){
			$(function () {				
				$('.tree li:has(ul)').addClass('parent_li');

				$('.tree li.parent_li > span').on('click', function (e) {
					var children = $(this).parent('li.parent_li').find(' > ul > li');
					if (children.is(":visible")) {
						children.hide('fast');
						$(this).find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
					} else {
						children.show('fast');
						$(this).find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
					}
					e.stopPropagation();
				});
			});	 
		},
		starImageLoader : function(){ 
			var $starImgNodeElement = $('.avgrating,.newratediv,.ratediv,.ratedivlect').children(); 
			$starImgNodeElement.each(function(){
				var _srcAttr = $(this).attr('src');
				var _context = courseData.getContextPath(); 
				$(this).attr("src", _context+ "/" +_srcAttr ); 
			});

		}
};
Handlebars.registerHelper('correctanswer', function(value,faqanswerid,faqanswer) {	
	if( value=="C") { 
		return '<i class="fa fa-check" style="font-size: 25px;float: right;color:rgb(101, 194, 101);"></i>';
		/*return '<i class="fa fa-check"></i>&nbsp;&nbsp;'+faqanswer;*/
	} /*else {
        return "&nbsp;<font>"+faqanswer+"</font><br>";
    }*/
});

learninglectureInitialLoadItems = {
		home : function(id){
			learningLectureInformation.home(id);
			learningLectureDiscussionQuestion.home(id);
			$('.tlink').click(function(){
				tablinkChooser.home($(this).attr('al'),id);
			});
		}
};

tablinkChooser = {
		home : function(link,id){

			switch(link) {

			case "info":
				learningLectureInformation.home(id);
				break;

			case "notes":
				learningNotes.home(id);
				break;

			case "scratchpad":
				learningScratchPad.home(id);
				break;

			case "discussion":
				learningLectureDiscussionQuestion.home(id);
				break;

			case "review":
				learningLectureReview.home(id);
				break;

			case "activity":
				learningLectureActivity.home(id);
				break;

			case "box":
				learningLectureBoxItem.home(id);
				break;

			case "diagram":
				learningLectureDiagram.home(id);
				break;

			case "table":
				learningLectureTable.home(id);
				break;

			case "map":
				learningLectureMap.home(id);
				break;

			case "reference":
				learningLectureReference.home(id);
				break;

			case "weblink":
				learningLectureWeblinks.home(id);
				break;

			case "projectidea":
				learningLectureProjectIdeas.home(id);
				break;

			}
		}	
};

learningLectureInformation = {
		home : function(id){
			$('#learntab .borderbottoma').removeClass('active');
			$('.informationtablink').addClass('active');
			this.view(id);
		},

		view : function(id){
			var thislist = this.list(id);
			renderTemplate("#learningLectureInformationtmpl", thislist, '#learningsidetab');
		},

		list : function(id){
			var keywordsList = metainfoListByLecture(id,"Keyword");
			var summaryList = metainfoListByLecture(id,"Summary");

			var obj = {
					keywords : keywordsList,
					summary : summaryList
			};

			return obj;
		} 
};

learningNotes = {
		home:function(id){

			$('#learntab .borderbottoma').removeClass('active');
			$('.notestablink').addClass('active');

			$privatenotes = $('.privatenotes'),$publicnotes = $('.publicenotes');		

			this.privateNoteView(id);

			$privatenotes.click = function(){
				privateNoteView(id);		
			};

			$publicnotes.click = function(){
				publicNoteView(id);		
			};
		},

		privateNotesList:function(courselectureid){
			var obj = {
					courselectureid : courselectureid,
					notetype : "N",
					notestatus : "R"
			};
			var list = ajaxhelperwithdata("getNotesList", obj);
			return list = _.isObject(list) ? list : null;
		},

		privateNoteView:function(courselectureid){
			var list = this.privateNotesList(courselectureid);
			renderTemplate('#textnotestmpl',list,'#learningsidetab');

			$textnotestextarea = $('.textnotestextarea'),$savetextnotes = $('.savetextnotes'),
			$noteslink = $('.noteslink'),$emptynotealert = $('.emptynotealert'),
			$publicnotelink = $('.publicnotelink');

			$textnotestextarea.keyup ( function(){
				el = $(this);
				if(el.val().length >= 2001){
					el.val( el.val().substr(0, 2000) );
				} else {
					$('.textnotescharremaining').text(2000-el.val().length);
				}
			});

			$savetextnotes.click (function(){
				var obj = {
						notesdescription : encodeURIComponent($textnotestextarea.val()),
						courselectureid : courselectureid
				};
				if($textnotestextarea.val().trim().length!=0){
					$savetextnotes.hide();
					learningNotes.newNoteSave(obj);
				}else{
					$emptynotealert.show();
					setTimeout(function(){
						$emptynotealert.hide();
					},3000);
				}				
			});

			$publicnotelink.click(function(){
				learningNotes.publicNoteView(courselectureid);
			});

			$noteslink.click ( function(){
				var currentnoteid  = $(this).attr('notesid');
				var currentnoteList = _.where(list,{notesid:parseInt(currentnoteid)});
				learningNotes.privateNoteDescription(currentnoteList);
			});
		},
		newNoteSave:function(obj){
			var savedStatus =  ajaxhelperwithdata("saveNotes", obj),
			notesuccess = $notessuccess = $('.notessuccess'),
			notefailure = $invaliderror = $('.invaliderror');
			if(savedStatus=="ok"){
				notesuccess.show();
				
				setTimeout(function(){
					$('.savetextnotes').show();
					notesuccess.hide();
					learningNotes.privateNoteView(obj.courselectureid);
				},3000);
			}else{
				notefailure.show();
				setTimeout(function(){
					$('.savetextnotes').show();
					notefailure.hide();
				},3000);				
			}
		},

		privateNoteDescription : function(list){
			renderTemplate('#textnotedescriptiontmpl',list,'#learningsidetab');	
			var $backtonotes = $('.backtonotes'),$updatenotesdesc = $('.updatenotesdesc'),
			$sharenote = $('.sharenote'),$deletenote = $('.deletenote'),
			$notesdescvalue = $('.notesdescvalue'),$updateareanotesremaining = $('.updateareanotesremaining');

			$updateareanotesremaining.html(2000-($notesdescvalue.val().length));

			$notesdescvalue.keyup ( function(){
				el = $(this);
				if(el.val().length >= 2001){
					el.val( el.val().substr(0, 2000) );
				} else {
					$updateareanotesremaining.text(2000-el.val().length);
				}
			});

			$backtonotes.click(function(){
				var lectureid = $(this).attr('courselectureid');
				learningNotes.privateNoteView(lectureid);
			});

			$updatenotesdesc.click(function(){
				var notesid = $(this).attr('notesid');
				var lectureid = $(this).attr('courselectureid');
				var descriptionval = $notesdescvalue.val().trim();
				if(descriptionval.length!=0){
					learningNotes.updateNote(notesid,encodeURIComponent(descriptionval),lectureid);
				}
			});

			$sharenote.click(function(){
				var checkstr =  confirm('Are you sure you want to share this Note');
				if(checkstr === true)	{
					var notesid = $(this).attr('notesid');
					var lectureid = $(this).attr('courselectureid');
					var sharedStatus = learningNotes.updateNoteStatus(notesid,"U");

					if(sharedStatus=="updated"){
						$('.successmessgagediv').show();
						$('.successmessgage').text("Note is shared among all subscribers");
						setTimeout(function(){
							$('.successmessgagediv').hide();
							learningNotes.privateNoteView(lectureid);
						},3000);	
					}
				}				
			});

			$deletenote.click(function(){
				var checkstr =  confirm('Are you sure you want to delete this Note');
				if(checkstr === true)	{
					var notesid = $(this).attr('notesid');
					var lectureid = $(this).attr('courselectureid');
					var deleteStatus = learningNotes.updateNoteStatus(notesid,"T");
					if(deleteStatus=="updated"){
						$('.successmessgagediv').show();
						$('.successmessgage').text("Notes Deleted");
						setTimeout(function(){
							$('.successmessgagediv').hide();
							learningNotes.privateNoteView(lectureid);
						},3000);	
					}
				}
			});			
		},

		updateNote : function(id,val,lectureid){
			var obj = {
					notesid : id,
					notesdescription : val,
					courselectureid : lectureid
			};
			var updatedSuccess = ajaxhelperwithdata("updateNotesDescription", obj);
			if(updatedSuccess==="updated"){
				$('.successmessgagediv').show();
				$('.successmessgage').text("Notes Saved");
				setTimeout(function(){
					$('.successmessgagediv').hide();	
				},1000);	
			}
		},

		updateNoteStatus:function(noteid,status){
			var obj = {
					notesid:noteid,
					notestatus: status
			};
			return ajaxhelperwithdata("updateNoteStatus", obj);
		},

		publicNoteView:function(courselectureid){
			var list = this.publicNotesList(courselectureid);
			renderTemplate('#publictextnotestmpl',list,'#learningsidetab');

			var $privatenotelink = $('.privatenotelink'),$publicnoteslink = $('.publicnoteslink');

			$privatenotelink.click(function(){
				learningNotes.privateNoteView(courselectureid);
			});

			$publicnoteslink.click(function(){
				var currentnoteid  = $(this).attr('notesid');
				var currentnoteList = _.where(list,{notesid:parseInt(currentnoteid)});
				learningNotes.publicNoteDescription(currentnoteList);
			});
		},

		publicNoteDescription : function(list){
			renderTemplate('#publictextnotedescriptiontmpl',list,'#learningsidetab');	
			var $backtonotes = $('.backtonotes');

			$backtonotes.click(function(){
				var lectureid = $(this).attr('courselectureid');
				learningNotes.publicNoteView(lectureid);
			});
		},

		publicNotesList:function(courselectureid){
			var obj = {
					courselectureid : courselectureid,
					notetype : "N",
					notestatus : "U"
			};
			var list =  ajaxhelperwithdata("getNotesList", obj);
			return list = _.isObject(list) ? list : null;
		},
};

learningScratchPad = {
		home: function(obj){
			$('#learntab .borderbottoma').removeClass('active');
			$('.scratchtablink').addClass('active');
			this.scratchPadView(obj);
		},

		scratchPadView:function(courselectureid){
			var list = this.scratchPadList(courselectureid);
			renderTemplate("#scratchpadlisttmpl", list, "#learningsidetab");
			applypagination($('.scratchpadpagination'), 12) ;
			$newScratchPad = $('.newScratchPad'),$changecustompath = $('.changecustompath'),
			$scratchbotespath = $('.scratchbotespath');

			$changecustompath.each(function(){
				var actualFilePath = $(this).attr('notespath');
				var actualFileName = actualFilePath.split('\\').pop().split('/').pop();
				$(this).text(actualFileName);
			});

			$newScratchPad.click(function(){
				learningScratchPad.newScratchPad(courselectureid);
			});

			$scratchbotespath.click(function(){
				learningScratchPad.scratchPadDescription(courselectureid,$(this).attr('notespath'),$(this).attr('notesid'));
			});
		},

		scratchPadDescription:function(courselectureid,imageFile,notesid){
			var obj = {
					imageFile:imageFile,
					fileName : imageFile.split('\\').pop().split('/').pop(),
					notesid : notesid
			};

			renderTemplate("#scratchPadDescriptiontmpl", obj, "#learningsidetab");
			$backtoscratch = $('.backtoscratch');

			$backtoscratch.click(function(){
				learningScratchPad.home(courselectureid);
			});

			$('.trashscratchbtn').click(function(){
				var checkstr =  confirm('Are you sure you Delete this scratch ? ');
				if(checkstr === true)	{
					learningScratchPad.trshScratchPad(courselectureid,notesid);	
				}	
			});
		},
		trshScratchPad : function(lectureid,notesid){
			var deleteStatus = learningNotes.updateNoteStatus(notesid,"T");
			if(deleteStatus=="updated"){
				$('.trashscratchbtn').hide();
				$('.trashsuccessalert').show();
				setTimeout(function(){
					$('.trashsuccessalert').hide();
					$('.trashscratchbtn').show();
					learningScratchPad.home(lectureid);
				},2000);
			}

		},
		scratchPadList:function(courselectureid){
			var obj = {
					courselectureid : courselectureid,
					notetype : "S",
					notestatus : "R"
			};
			var list =  ajaxhelperwithdata("getNotesList", obj);
			return list = _.isObject(list) ? list : null;
		},

		newScratchPad:function(courselectureid){
			renderTemplate("#newscratchpadtmpl", null, "#learningsidetab");
			LC.setDefaultImageURLPrefix('../assets/whiteboard/img');

			var containerOne =$('.literally')[0];
			var lc = LC.init(containerOne,{
				tools: [LC.tools.Pencil, LC.tools.Eraser, LC.tools.Line,
				        LC.tools.Rectangle]
			});
			//$('.lc-drawing canvas').css('background','#E8ECEE');
			$savescratchpad = $(".savescratchpad"),$backtoscratchpadlist = $('.backtoscratchpadlist'),
			$newscratchalert = $('.newscratchalert'),$emptyalert = $('.newscratchalert.emptyalert');

			$savescratchpad.click(function(){
				if(lc.getImage()!=undefined){
					var Pic = lc.getImage().toDataURL("image/png");
					elemnt = Pic.replace('data:image/png;base64,', '');
					learningScratchPad.fileUploadAjaxAction(elemnt.toString(),"drawobject",courselectureid);	
				}else{
					$newscratchalert.hide();
					$emptyalert.show();
					setTimeout(function(){
						$newscratchalert.hide();
						$emptyalert.hide();
					},3000);
				}								
			});

			$backtoscratchpadlist.click(function(){
				learningScratchPad.home(courselectureid);
			});

		},

		fileUploadAjaxAction : function(element,drawobject,courselectureid){
			try { 
				var xhr = new XMLHttpRequest(), fd = new FormData();
				$newscratchalert = $('.newscratchalert'),$successalert = $('.newscratchalert.successalert'),
				$failurealert = $('.newscratchalert.failurealert'),
				xhr.onload = function(data){
					if(xhr.statusText==="OK"){
						$newscratchalert.hide();
						$successalert.show();
						setTimeout(function(){
							$newscratchalert.hide();
							$successalert.hide();
							learningScratchPad.home(courselectureid);
						},3000);

					}else{
						$newscratchalert.hide();
						$failurealert.show();
						setTimeout(function(){
							$newscratchalert.hide();
							$failurealert.hide();
						},3000);
					}
				};
				if(drawobject != null)
					fd.append("drawobject", element.toString());
				else{
					fd.append("drawobject", null);
					fd.append("file", element);
				}
				fd.append("courselectureid",courselectureid);
				xhr.open("POST",courseData.getContextPath()+"/rest/file/notesScratchPad",false);
				xhr.send(fd); 
			} catch (e) {
				console.error(e);
			}			
		}
};

learningLectureReview = {

		orgpersonid : $('.pagat').attr('orgpersonid'),

		home : function(lectureid){
			$('.informationview .borderbottoma').removeClass('active');
			$('.reviewstablink').addClass('active');
			this.lectureReviewView(lectureid);
		},

		lectureReviewView:function(lectureid){
			var list = this.lectureReviewList(lectureid);
			renderTemplate("#modifiedLectureReviewstmpl", list, "#myTabContent3");
			var starHalfImage = courseData.getContextPath()+'/assets/app/images/star/star-half.png';
			var starOffImage = courseData.getContextPath()+'/assets/app/images/star/star-off.png';
			var starOnImage = courseData.getContextPath()+'/assets/app/images/star/star-on.png';
			if(list!=null){
				if(list.myReviews!=null){
					if(list.myReviews.length == 0){
						$('.newratediv').raty({
							starHalf      : starHalfImage,
							starOff       : starOffImage, 
							starOn        : starOnImage, 
						});					
					}else{
						$('.reviewstar').raty({
							readOnly: true, 
							score: list.myReviews[0].averagerevieworiginal ,
							starHalf      : starHalfImage,
							starOff       : starOffImage, 
							starOn        : starOnImage, 
						});
					}	
				}else{
					$('.newratediv').raty({
						starHalf      : starHalfImage,
						starOff       : starOffImage, 
						starOn        : starOnImage, 
					});
				}
				if(list.otherReviews!=null){
					if(list.otherReviews.length!=0){
						$('.othersreviewstar').each(function(){
							var thisrating = $(this).attr('rating');
							$(this).raty({
								readOnly: true, 
								score: thisrating,
								starHalf      : starHalfImage,
								starOff       : starOffImage, 
								starOn        : starOnImage, 
							});
						});
					}
				}
			}

			$('#totalrating').click(function(){		
				learningLectureReview.newReview(lectureid);
			});

		},

		newReview : function(lectureid){
			var courseratingobj={};
			courseratingobj.lectureid=lectureid;
			var rate="";
			var review="";
			$('.newratediv>[name=score]').each(function() {
				if($(this).val()==''){
					$(this).val(0);									
				}
				rate+=$(this).val()+",";
			});
			$('.reviewid').each(function(){
				review+=$(this).val()+",";							
			});

			courseratingobj.rank=rate;
			courseratingobj.reviewtype=review;
			var lecres=courseData.savelectureReview(courseratingobj);
			if(lecres>0){
				learningLectureReview.home(lectureid);
			}
		},

		lectureReviewList:function(lectureid){
			var obj = {
					lectureid:lectureid
			};
			var list = ajaxhelperwithdata("lectureReviewList", obj);
			list = _.isObject(list) ? list : null;
			var formatedLectureReviewsList = this.myReviewsList(list);
			return formatedLectureReviewsList;
		},

		myReviewsList : function(list){
			if(list!=null){
				var myReviews =_.filter(list, function(num){ return num.userid == parseInt(learningLectureReview.orgpersonid);});
				var otherReviews = _.filter(list, function(num){ return num.userid != parseInt(learningLectureReview.orgpersonid);});
				var reviewValues = _.pluck(list,'averagerevieworiginal');
				var sum = _.reduce(reviewValues, function(memo, num){ return memo + num; }, 0);
				var average = Math.round(sum/list.length);
				var reviewTypes = "";
				if(myReviews.length==0){
					reviewTypes = courseData.loadReviewType();
				}else{
					reviewTypes = null;
				}
				return obj ={
						myReviews : myReviews,
						otherReviews : otherReviews,
						average : average,
						count : list.length,
						reviewtype : reviewTypes
				};	
			}else{
				var reviewTypes = courseData.loadReviewType();
				return obj ={
						myReviews : null,
						otherReviews : null,
						average : null,
						count : null,
						reviewtype : reviewTypes
				};	
			}

		}
};

learningLectureDiscussionQuestion = {

		home : function(lectureid){
			$('.informationview .borderbottoma').removeClass('active');
			$('.discussiontablink').addClass('active');
			this.questionsView(lectureid);
		},

		questionsView : function(lectureid){
			var list = this.questionsList(lectureid);
			Handlebars.registerHelper('decode', function(FAQquestion) {
				return decodeURIComponent(FAQquestion.replace(/\+/g,' '));
			});
			renderTemplate("#discussionquestionstmpl", list, "#myTabContent3");

			var $discussioninput = $('.discussioninput'), $discussionempty = $('.discussionempty'),
			$submitdiscussion = $('.submitdiscussion'),$discussionanswerlink = $('.discussionanswerlink');

			$discussioninput.keyup(function(){
				el = $(this);
				if(el.val().length >= 501){
					el.val( el.val().substr(0, 500) );
				} else {
					$('.discussionremaining').text(500-el.val().length);
				}
			});

			$submitdiscussion.off().click(function(){
				var discussionquestion = $discussioninput.val().trim();
				if(discussionquestion.length!=0){
					$discussionempty.hide();
					var checkstr =  confirm('Are you sure you submit this Question ? ');
					if(checkstr === true)	{
						$(this).hide();
						learningLectureDiscussionQuestion.newQuestion(lectureid,discussionquestion);	
					}					
				}else{
					$discussionempty.show();
				}
			});

			$discussionanswerlink.click(function(){
				var questionid = $(this).attr('questionid');
				var questionData = _.where(list,{FAQid:parseInt(questionid)});
				questionData[0].lectureid=lectureid;
				learningLectureDiscussionAnswer.home(questionData[0]);
			});
		},

		newQuestion : function(id,val){ 
			var obj = {
					FAQquestion : encodeURIComponent(val) ,
					courselectureid : id
			};
			var $successalert = $('.discussionsuccess'),$failurealert = $('.discussionfailure');
			var savedSuccess = ajaxhelperwithdata("saveQuestion", obj);
			if(savedSuccess != "error"){
				$('.emptynotealert').hide();
				$successalert.show();
				setTimeout(function(){
					$successalert.hide();
					learningLectureDiscussionQuestion.home(id);
				},2000);
			}else{
				$failurealert.show();
				setTimeout(function(){
					$failurealert.hide();
					learningLectureDiscussionQuestion.home(id);
				},2000);
			}
		},

		questionsList : function(lectureid){
			var obj = {
					courselectureid : lectureid
			};
			var list = ajaxhelperwithdata("loadLectureDiscussions",obj);
			return list = _.isObject(list) ? list : null;
		},

};

learningLectureDiscussionAnswer = {
		home : function(obj){
			this.answersView(obj);
		},

		answersView : function(obj){
			var list = this.answersList(obj.FAQid);

			var convertedObj = {
					questions : obj,
					answers : list
			};

			renderTemplate("#discussionanswerstmpl", convertedObj, "#myTabContent3");

			var $discussionanswerinput = $('.discussionanswerinput'), $submitdiscussionanswer = $('.submitdiscussionanswer'),
			$discussionswernempty = $('.discussionswernempty'), $discussionanswerremaining = $('.discussionanswerremaining'),
			$backtodiscussions = $('.backtodiscussions'); 

			$discussionanswerinput.keyup(function(){
				el = $(this);
				if(el.val().length >= 501){
					el.val( el.val().substr(0, 500) );
				} else {
					$discussionanswerremaining.text(500-el.val().length);
				}
			});

			$submitdiscussionanswer.off().click(function(){
				var discussionanswer = $discussionanswerinput.val().trim();
				if(discussionanswer.length!=0){
					$discussionswernempty.hide();
					var checkstr =  confirm('Are you sure you Save this answer ? ');
					if(checkstr === true)	{
						$(this).hide();
						learningLectureDiscussionAnswer.newAnswer(obj,obj.FAQid,discussionanswer);	
					}					
				}else{
					$discussionswernempty.show();
				}
			});

			$backtodiscussions.click(function(){
				learningLectureDiscussionQuestion.home(obj.lectureid);
			});
		},

		newAnswer : function(obj,id,val){
			var answerobj = {
					FAQid : id,
					faqanswer : val
			};

			var $successalert = $('.discussionanswersuccess'), $failurealert = $('.discussionanswerfailure');

			var answersaved = ajaxhelperwithdata("saveQAnswer", answerobj);

			if(parseInt(answersaved)>0){
				$successalert.show();
				setTimeout(function(){
					$successalert.hide();
					learningLectureDiscussionAnswer.home(obj);
				},2000);
			}else{
				$failurealert.show();
				setTimeout(function(){
					$failurealert.hide();
					learningLectureDiscussionAnswer.home(obj);
				},2000);
			}

		},

		answersList : function(id){
			var obj = {
					FAQid : id
			};
			var list = ajaxhelperwithdata("loadAnswers", obj);
			return list = _.isObject(list) ? list : null;
		}
};

learningLectureActivity = {

		home : function(id){
			$('.informationview .borderbottoma').removeClass('active');
			$('.activitytablink').addClass('active');
			this.view(id);
		},

		view : function(id){
			var thislist = this.list(id);
			renderTemplate("#learningLectureActivitytmpl", thislist, "#myTabContent3");
		},

		list : function(id){
			return list = metainfoListByLecture(id,"Activity");
		} 
};

learningLectureBoxItem = {

		home : function(id){
			$('.informationview .borderbottoma').removeClass('active');
			$('.boxitemtablink').addClass('active');
			this.view(id);
		},

		view : function(id){
			var thislist = this.list(id);
			renderTemplate("#learningLectureBoxItemtmpl", thislist, "#myTabContent3");
		},

		list : function(id){
			return list = metainfoListByLecture(id,"Box Item");
		} 
};

learningLectureDiagram = {

		home : function(id){
			$('.informationview .borderbottoma').removeClass('active');
			$('.diagramtablink').addClass('active');
			this.view(id);
		},

		view : function(id){
			var thislist = this.list(id);
			renderTemplate("#learningLectureDiagramtmpl", thislist, "#myTabContent3");
			$('#cars .item:first').addClass('active');
			$('#cars').carousel();
		},

		list : function(id){
			return list = metainfoListByLecture(id,"Diagram");
		} 
};

learningLectureTable = {

		home : function(id){
			$('.informationview .borderbottoma').removeClass('active');
			$('.tabletablink').addClass('active');
			this.view(id);
		},

		view : function(id){
			var thislist = this.list(id);
			renderTemplate("#learningLectureTabletmpl", thislist, "#myTabContent3");
			$('#cars .item:first').addClass('active');
			$('#cars').carousel();
		},

		list : function(id){
			return list = metainfoListByLecture(id,"Table");
		} 
};

learningLectureMap = {

		home : function(id){
			$('.informationview .borderbottoma').removeClass('active');
			$('.maptablink').addClass('active');
			this.view(id);
		},

		view : function(id){
			var thislist = this.list(id);
			renderTemplate("#learningLectureMaptmpl", thislist, "#myTabContent3");
			$('#cars .item:first').addClass('active');
			$('#cars').carousel();
		},

		list : function(id){
			return list = metainfoListByLecture(id,"Mind Map");
		} 
};

learningLectureReference = {

		home : function(id){
			$('.informationview .borderbottoma').removeClass('active');
			$('.referencetablink').addClass('active');
			this.view(id);
		},

		view : function(id){
			var thislist = this.list(id);
			renderTemplate("#learningLectureReferencetmpl", thislist, "#myTabContent3");
			$('#cars .item:first').addClass('active');
			$('#cars').carousel();
		},

		list : function(id){
			return list = metainfoListByLecture(id,"Reference");
		} 
};

learningLectureWeblinks = {

		home : function(id){
			$('.informationview .borderbottoma').removeClass('active');
			$('.weblinkstablink').addClass('active');
			this.view(id);
		},

		view : function(id){
			var thislist = this.list(id);
			renderTemplate("#learningLectureWeblinkstmpl", thislist, "#myTabContent3");
		},

		list : function(id){
			return list = metainfoListByLecture(id,"Weblink");
		} 
};

learningLectureProjectIdeas = {

		home : function(id){
			$('.informationview .borderbottoma').removeClass('active');
			$('.projectideastablink').addClass('active');
			this.view(id);
		},

		view : function(id){
			var thislist = this.list(id);
			renderTemplate("#learningLectureProjectIdeastmpl", thislist, "#myTabContent3");
		},

		list : function(id){			
			return list = metainfoListByLecture(id,"Project Idea");
		} 
};

function metainfoListByLecture(id,type){
	var obj = {
			courselectureid : id,
			descriptiontype : type
	};
	var list = ajaxhelperwithdata("loadmetainfoitems", obj);
	return list = _.isObject(list) ? list : null;
}