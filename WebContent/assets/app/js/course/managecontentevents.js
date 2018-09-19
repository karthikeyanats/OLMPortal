$(document).ready(function(){
	$('#scheduleli').click(function(){
		$('#faqtable').hide();
		$('.ansbackqnbtn').hide();
		$('#content').hide();
		$('#schedule').show();
		$('#calendar').fullCalendar('render');
		$('#calendar').show();
	});
});
manageContentInfo={
		loadcontentinfo:function(courseContents){
			var self=this;
			var courseid=$('.rightpanetitle').attr('courseid');
			Handlebars.registerHelper('decode', function(coursesectiontitle) {
				return decodeURIComponent(coursesectiontitle.replace(/\+/g,' '));
			});	
			Handlebars.registerHelper('decodes', function(courselecturetitle) {
				return decodeURIComponent(courselecturetitle.replace(/\+/g,' '));
			});	
			
			renderTemplate("#coursecontenttmpl",courseContents,"#coursecontenttable");
			if($('.publishcoursebtn').attr('coursestatus')==="A"){
				$('.newsectionrow').hide();
				$('.editsectionbtn').hide();
				$('.addNewLecturebtn').hide();
				$('.editlecturebtn').hide();
				$('.addContentbtn').hide();
				$('.deletecourse').hide();
				$('.deletelectcourse').hide();
			}			
			$('.quizbtn').click(function(){
				$('#examTab').children().remove();
				sat.initialize($(this).attr("courselectureid"));
				if($('.publishcoursebtn').attr('coursestatus')==="A"){
					$('.qst-edit-div').hide();
					$('.tgl').hide();
				}	
			});
			$('.newsectionbtn').click(function(e){
				var cateid=$('#categorytable').val();
				if(cateid!="0"){
					$('#newsectionmodal').modal('show');
					var sectiontitle = $(".sectionhiddenval").val();
					$(".sectiontitlename").html(sectiontitle);
					self.newSectionEvents(courseid);	
				}
				else{
					e.preventDefault();
					$('#messagemodal').modal('show');
					$('.mesdesc').text("Please choose a category for the course");
				}				
			});
			$('.editsectionbtn').click(function(){
				$('#editSectionModal').modal('show');
				var coursesectiontitle=decodeURIComponent($(this).attr('coursesectiontitle').replace(/\+/g,' '));
				$('.editsectionsavebtn').attr('coursesectionid',$(this).attr('coursesectionid'));
				self.editSectionEvents(courseid,coursesectiontitle);
			});
			$('.lecture').on("focusout keyup",function(){
				var len=$(this).attr('len');
				var errorid="#"+$(this).attr('errorid');
				/*if(($(this).val().trim() == "")){
					$(errorid).html("please enter "+$(this).attr('placeholder'));
					$(errorid).show();
					$(errorid).attr("style","margin-top:4px");
					newlecture();
				}
				else*/ 
				if($(this).attr("len")){
					if($(this).val().trim().length>len){
						$(errorid).html("The length must be within 500 characters");
						$(errorid).show();
						$(errorid).attr("style","margin-top:4px");
						newlecture();
					}

					else{
						$(errorid).attr("style","display: none;");
						$(errorid).text("");
						newlecture();
					}
				}
			});
			function newlecture(){
				var isError = "";
				$('.error-mask3').each(function(){
					if($(this).attr('style')!="display: none;"){
						isError="true";
					}
				});
				if(isError!="true"){
					$(".savenewlecturesubmit").show();
					$(".asavenewlecture").hide();
				}
				else{
					$(".savenewlecturesubmit").hide();
					$(".asavenewlecture").show();
				}
			}
			$('.addNewLecturebtn').click(function()	{
				var cateid=$('#categorytable').val();
				if(cateid!="0"){
					$('#newlecturemodal').modal('show');
					$('#errortitlemsg').hide();
					var titledata=decodeURIComponent($(this).attr("coursesectiontitle").replace(/\+/g,' '));
					$(".lecturetitlename").html(titledata);
					$(".lecturetitlename").attr('title',titledata);
					var coursesectionid=$(this).attr('coursesectionid'); 
					$('.hidcoursesectionid').val(coursesectionid);
					$('.hidcoursesectiontitle').val(titledata);
					$('.newlecturesavebtn').attr('coursesectionid',coursesectionid);
					/*self.newLectureEvents(courseid);
					self.newLectureSaveEvents;*/
				}
				else{
					e.preventDefault();
					$('#messagemodal').modal('show');
					$('.mesdesc').text("Please choose a category for the course");
				}

			});
			$('#savenewlecture').click(function(){
				if($('.newlecturetitle').val().trim()==''){
					$('.newlecturetitle').focus();
					$('#errormsgshow').show().fadeOut(4000);
					return false;
				}else{
					$('.lecture').trigger('focusout');
					var isError = "";
					$('.error-mask3').each(function(){
						if($(this).attr('style')!="display: none;"){
							isError="true";
						}
					});
					if(isError!="true"){
						$('[name=leccontentdetail]').ajaxForm({		
							success : function (response) {
								if(response="success"){					        	   
									manageCourse.loadPageInfo(courseid);
									$('#newlecturemodal').modal('hide');
									document.getElementById("addNewLecture").reset();
									$('#messagemodal').modal('show');
									$('.mesdesc').text("Lecture Saved successfully");
								}
							}

						});
					}
					else{
						return false;
					}

				}
			});
			$('.editlecturebtn').click(function(){	
				$('#editlecturemodal').modal('show');
				var courselectureid=$(this).attr('courselectureid');
				var courselecturetitle=decodeURIComponent($(this).attr('courselecturetitle').replace(/\+/g,' '));
				$('.editlecturesavebtn').attr('courselectureid',courselectureid).attr('coursesectionid',$(this).attr('coursesectionid'));
				self.editLectureEvents(courseid,courselectureid,courselecturetitle);
			});
			$('.metainfo').on("focusout keyup",function(){
				var len=$(this).attr('len');
				var errorid="#"+$(this).attr('errorid');
				/*if(($(this).val().trim() == "")){
					$(errorid).html("please enter "+$(this).attr('placeholder'));
					$(errorid).show();
					$(errorid).attr("style","margin-top:4px");
					newlecturesavesubmitbtn();
				}
				else*/ if($(this).attr("len")){
					if($(this).val().trim().length>len){
						$(errorid).html("The length must be within 500 characters");
						$(errorid).show();
						$(errorid).attr("style","margin-top:4px");
						newlecturesavesubmitbtn();
					}

					else{
						$(errorid).attr("style","display: none;");
						$(errorid).text("");
						newlecturesavesubmitbtn();
					}
				}
			});
			function newlecturesavesubmitbtn(){
				var isError = "";
				$('.error-mask2').each(function(){
					if($(this).attr('style')!="display: none;"){
						isError="true";
					}
				});
				if(isError!="true"){
					$(".newlecturesavesubmitbtn").show();
					$(".anewlecturesavebtn").hide();
				}
				else{
					$(".newlecturesavesubmitbtn").hide();
					$(".anewlecturesavebtn").show();
				}
			}

			$('.editmetainfobtn').click(function(){
				lecturemetainfo().init($(this).attr('metainfoid'));
			});
			$('.addContentbtn').click(function(){
				$('#addContentmodal').modal('show');
				$('#file_upload').attr('courselectureid',$(this).attr('courselectureid'));
				var courselectureid=$(this).attr('courselectureid');
				var courseid=$(this).attr('courseid');
				var courselecturetitle=/*$(this).attr('courselecturetitle');*/decodeURIComponent($(this).attr('courselecturetitle').replace(/\+/g,' '));
				$(".contenttitlename").html("Add New Content For "+courselecturetitle);
				self.viewexistingcontent(courselectureid,courseid);
			});
			$('.lecturequestbtn').click(function(){	
				var lectureidobj={};
				var courselectureid=$(this).attr('courselectureid');
				lectureidobj.courselectureid=courselectureid;
				self.loadFAQ(lectureidobj);
			});
			$('#correctansbtn').click(function(){	
				var faqanswerids="";
				if($('input#correctanswer:checked').length>0) {
					$("input:checked").each(function () {
						faqanswerids+= $(this).attr("faqanswerid")+",";
					});
					self.editCorrectAnswers(faqanswerids);
				}else {
					alert('Select at least one Answer');
				}
			});
			$('#deleteansbtn').click(function(){	
				var faqanswerids="";
				if($('input#correctanswer:checked').length>0) {
					var checkstr =  confirm('Are you sure you want to Remove this Answer');
					if(checkstr == true){
						$("input:checked").each(function () {
							faqanswerids+= $(this).attr("faqanswerid")+",";
						});
						self.deleteAnswers(faqanswerids);
					}
				}else{
					alert('Select at least one Answer to Trash');
				}
			});
			$('#trashansbtn').click(function(){	
				var faqanswerids="";
				if($('input#correctanswer:checked').length>0) {
					var checkstr =  confirm('Are you sure you want to Remove this Answer');
					if(checkstr == true){
						$("input:checked").each(function () {
							faqanswerids+= $(this).attr("faqanswerid")+",";
						});
						self.trashAnswers(faqanswerids);
					}
				}else{
					alert('Select at least one Answer to Trash');
				}
			});
			$('#trashanslistbtn').click(function(){	
				$('.trashanswerpart').show();
				$('.answerpart').hide();
				var questid=$('.questionheader').attr('faqid');
				var question=$('.questionheader').text();
				manageContentInfo.alltrashanswerdata(questid,question);
			});

			$('.deletecourse').click(function(){
				var checkstr =  confirm('Are you sure you want to delete this Course');
				if(checkstr == true){
					var trashcourseobj={};
					trashcourseobj.courseid=$(this).attr('courseid');
					trashcourseobj.coursesectionid=$(this).attr('coursesectionid');
					trashcourseobj.coursesectionstatus="T";
					self.deleteCourseData(trashcourseobj);
				}				
			});
			$('.deletelectcourse').click(function(){
				var checkstr =  confirm('Are you sure you want to delete this Course');
				if(checkstr == true){
					var trashlectureobj={};
					trashlectureobj.courseid=$(this).attr('courseid');
					trashlectureobj.courselectureid=$(this).attr('courselectureid');					
					trashlectureobj.courselecturestatus="T";
					self.deleteCourselectureData(trashlectureobj);
				}				
			});

			$('.preview-lecture-btn').click(function(){
				var self = this;

				if($(self).attr('contenttype')!=="nocontent"){
					var courselectureid=$(self).attr('courselectureid');  
					var courseTitle = $(self).attr('courselecturetitle');
					var pushHeader = {};
					pushHeader['courselecturetitle'] = courseTitle;
					$('#preview-content-modal').modal('show'); 
					renderTemplate('#preview-content-modal-title-template',pushHeader,"#preview-modal-header");

					var lectureidobj={};
					lectureidobj.courselectureid=courselectureid;


					var fileNamewithExtn =   $(self).attr('contentpath');  
					var obsoluteFileNameWithExtn = fileNamewithExtn.split('/').pop();
					var extn = obsoluteFileNameWithExtn.split('.').pop();
					var pushObject = {};pushObject['contentpath'] = fileNamewithExtn; 


					if(extn == 'mp4' | extn == 'webm' | extn == 'mov' | extn == 'flv'){
						renderTemplate('#previewContentTempl-video',pushObject,"#preview-content-space");
						$('.flowplayer.preview').flowplayer();
					}else if(extn == 'pdf'){
						renderTemplate('#previewContentTempl-pdf',pushObject,"#preview-content-space");
					}else if(extn ==  'swf'){
						renderTemplate('#previewContentTempl-swf',pushObject,"#preview-content-space");
					}else if(extn == 'igst' ){
						renderTemplate('#encpreviewContentTempl-video',pushObject,"#preview-content-space"); 
						$('.flowplayer.preview').flowplayer(); 
					}else{ 
						renderTemplate('#nocontenttmpl',undefined,'#preview-content-space');	
					}
				}else{
					$('#messagemodal').modal('show');
					$('.mesdesc').text("No Content added yet");
				}
			});
		},
		deleteCourseData:function(trashcourseobj){
			var courseid=trashcourseobj.courseid;
			if(courseid!=undefined){
				var trashlist=courseData.deleteCoursesection(trashcourseobj);
				if(trashlist=="success"){				 
					manageCourse.loadPageInfo(courseid);
				}else{
					alert("error in delete");
				}
			}
		},	
		deleteCourselectureData:function(trashlectureobj){
			var courseid=trashlectureobj.courseid;
			if(courseid!=undefined){
				var trashlist=courseData.deleteCourselecture(trashlectureobj);
				if(trashlist=="success"){				 
					manageCourse.loadPageInfo(courseid); 
				}
				else{
					alert("error in delete");
				}
			}
		},

		newQuizSaveData:function(questionobj){
			var s=courseData.saveNewQuiz(questionobj);
		},
		newSectionEvents:function(courseid){
			var self=this;
			$('.newsectionsavebtn').click(function(){
				if($('.newsectiontitle').val().trim()==''){
					$('#sectionerr').show();
				}
				else{
					$('#sectionerr').hide();
					var coursesectiontitle=$('.newsectiontitle').val();
					if(coursesectiontitle!=''){
						self.newSectionSaveEvent(courseid,coursesectiontitle);
					}
				}
			});
		},
		newSectionSaveEvent:function(courseid,coursesectiontitle){
			var result=manageCourseFunctions.addNewSection(courseid,coursesectiontitle);
			$('.newsectiontitle').val('');
			if(result!=="false"){
				$('#newsectionmodal').modal('hide');
				$('#messagemodal').modal('show');
				$('.mesdesc').text("Section added successfully");
				setTimeout(function(){
					$('#messagemodal').modal('hide');										
				},1000);
				manageCourse.loadPageInfo(courseid);
			}


		},
		editSectionEvents:function(courseid,coursesectiontitle)	{
			var self=this;
			$('.titleforedit').val(coursesectiontitle);
			$('.editsectionsavebtn').click(function(){
				var editedcoursesectiontitle=$('.titleforedit').val();
				self.editSectionSaveEvent(courseid,$(this).attr('coursesectionid'),editedcoursesectiontitle);
			});
		},
		editSectionSaveEvent:function(courseid,coursesectionid,coursesectiontitle){
			if(coursesectiontitle!='')	{
				var result=manageCourseFunctions.editSectionTitle(courseid,coursesectionid,coursesectiontitle);
				$('.titleforedit').val('');
				$("#editsectionmsgshow2").hide();
				var edittitle = $('.titleforedit').val('');
				if(result!="false")		{
					$('#editSectionModal').modal('hide');
					$('#messagemodal').modal('show');
					$('.mesdesc').text("Section edited successfully");
					setTimeout(function(){
						$('#messagemodal').modal('hide');										
					},1000);
					$("#editsectionmsgshow2").hide();
					manageCourse.loadPageInfo(courseid);
					
				}
				else if(edittitle ==''){							
					$('#editsectionmsgshow2').show().fadeOut(4000);					
				}

			}
			else{	
				$('#editsectionmsgshow2').show().fadeOut(4000);
				
			}
		},
		editLectureEvents:function(courseid,courselectureid,courselecturetitle){
			var self=this;
			$('.editlecturetitle').val(courselecturetitle);
			$('.editlecturesavebtn').click(function(){
				var editedLectureTitle=$('.editlecturetitle').val().trim();
				if(editedLectureTitle!=''){
					self.editLectureSaveEvent(courseid,$(this).attr('coursesectionid'),$(this).attr('courselectureid'),editedLectureTitle);
				}else{
					$('.editlecturetitle').focus();
					$('#alertempty').show().fadeOut(4000);

				}
			});
		},
		editLectureSaveEvent:function(courseid,coursesectionid,courselectureid,lecturetitleedited){
			if(lecturetitleedited!=''){
				var result=manageCourseFunctions.editLectureTitle(coursesectionid,courselectureid,lecturetitleedited);
				if(result!="false")	{
					$('#editlecturemodal').modal('hide');
					$('#messagemodal').modal('show');
					$('.mesdesc').text("Lecture Edited successfully");
					$('.editlecturetitle').val('');
					setTimeout(function(){
						$('#messagemodal').modal('hide');										
					},1000);										
					manageCourse.loadPageInfo(courseid);
				}	
			}
		},		
		viewexistingcontent:function(courselectureid,courseid){
			var self=this;
			var lectureidobj={};
			lectureidobj.courselectureid=courselectureid;
			var existingContent=courseData.getExistingContent(lectureidobj);
			if(existingContent!=undefined ){
				var fileContent=self.removefilepath(existingContent);
				renderTemplate('#fileuploadrendertemplate',fileContent,"#uploadtable");
				var contentstatus=$('.contentapprovestatus').attr('approvestatus');
				var approvedescription=$('.contentapprovestatus').attr('approvedescription');
				if(contentstatus=="A"){
					$('.contentapprovestatus').text("Approved");
				}
				else if(contentstatus=="R"){
					$('.contentapprovestatus').text("Rejected for Following Reason ' "+approvedescription+" '");
				}else{
					$('.contentapprovestatus').text("Uploaded");
				}
				$('.removecontentbtn').click(function()	{
					var contentid=$(this).attr('contentid');
					var contentpath=$(this).attr('contentpath');
					self.removeContentEvents(courselectureid,contentid,contentpath,courseid);
				});
			}
			else{
				self.renderNewContentForUp(existingContent,courselectureid,courseid);
			}			
		},
		renderNewContentForUp:function(existingContent,courselectureid,courseid){
			var self=this;
			renderTemplate('#uploadcontenttmpl',existingContent,"#uploadtable");
			$('.errblcok').hide();
			$('.uploadcnt').hide();
			$('.prggrop').hide();
			$('.input-file').change(function(){
				$('.errblcok').hide();				
				$('.prggrop').hide();
				if(this.files[0]!=undefined){
					var fileextension=this.files[0].name.split('.').pop();
					var fileSize=this.files[0].size/(1024*1024);				
					fileSize=fileSize.toFixed(2);
					$('[name=filesizeval]').val(fileSize);
					if(_.isString(fileextension)){					
						if(fileextension==="pdf" || fileextension==="PDF" || fileextension==="mp4" || fileextension==="MP4" ||  
								fileextension==="swf" || fileextension==="SWF" || fileextension==="flv" || fileextension==="FLV" ){
							$('#invalidalert').hide();
							if(fileextension==="flv" || fileextension==="FLV" || fileextension==="mp4" || fileextension==="MP4"){
								$('.encgroup').show();							
								$('[name=enstatus]').val($('.encryptbtn.btn-blue').attr('enst'));
								$('.encryptbtn').click(function(){
									$('.encryptbtn').removeClass('btn-blue');
									$(this).addClass('btn-blue');
									$('[name=enstatus]').val($(this).attr('enst'));
								});
							}
							else{
								$('.encgroup').hide();
								$('[name=enstatus]').val("N");
							}
							self.upLectureContent(courselectureid,courseid);
						}
						else{
							$('#invalidalert').show();
							$('.uploadcnt').hide();
							$('.input-file').val('');
							$('.close').click(function(){
								$('#invalidalert').hide();
							});
						}
					}	
				}

			});			
		},
		upLectureContent:function(courselectureid,courseid){
			var self= this;
			$('[name=courselectureidhidden]').val(courselectureid);
			$('.uploadcnt').show();
			$('.uploadcnt').click(function(){
				var contboolean = checkfilesize("#contfile", "co");
				if(contboolean==="ok"){
					$('.prggrop').show();
					self.upEvent(courseid,courselectureid);
				}else{
					$("#contfile").val("");
					$('.errblcok').show();
					$('.errblcok').removeClass('alert-success').addClass('alert-danger');
					$('.emsg').text("The file Size specified is 800 MB. You cannot upload files greater than 800MB");
					setTimeout(function(){
						$('.errblcok').hide();									
					},3000);
				}	

			});			
		},
		upEvent:function(courseid,courselectureid){
			$('.uploadcnt').hide();
			contUpload.uploadContentFiles(courseid,courselectureid);
		},
		showContent:function(courselectureid){
			var self=this;
			var lectureidobj={};
			lectureidobj.courselectureid=courselectureid;
			var existingContent=courseData.getExistingContent(lectureidobj);
			var fileNamewithExtn = existingContent[0].contentpath;  
			var obsoluteFileNameWithExtn = fileNamewithExtn.split('/').pop();
			var extn = obsoluteFileNameWithExtn.split('.').pop();
			var pushObject = {};pushObject['contentpath'] = fileNamewithExtn; 
			if(extn == 'mp4' | extn == 'webm' | extn == 'mov' | extn == 'flv'){
				renderTemplate('#autopreviewContentTempl-video',pushObject,"#showcontenttable");
				$('.flowplayer.autopreview').flowplayer()
				.bind("ready", function () {
					self.updateVideoTime(existingContent[0].contentid,$('.fp-duration').text());
				});
			}
			else if(extn == 'pdf'){
				renderTemplate('#previewContentTempl-pdf',pushObject,"#showcontenttable");
			}else if(extn ==  'swf'){
				renderTemplate('#previewContentTempl-swf',pushObject,"#showcontenttable");
			}else if(extn == 'igst' ){
				renderTemplate('#encpreviewContentTempl-video',pushObject,"#showcontenttable"); 
				$('.flowplayer').flowplayer().bind("ready", function () {
					self.updateVideoTime(existingContent[0].contentid,$('.fp-duration').text());					 
				}); 
			}else{ 
				renderTemplate('#nocontenttmpl',undefined,'#preview-content-space');	
			} 

		},
		updateVideoTime:function(contentid,duration){
			var timeobj={};
			timeobj.duration=duration;
			timeobj.contentid=contentid;
			var videoDuration=courseData.updateVideoDuration(timeobj);
		},
		removeContentEvents:function(courselectureid,contentid,contentpath,courseid){
			var self=this;
			var checkstr =  confirm('Are you sure you want to delete this Content?');
			if(checkstr == true)	{
				var contentRemoved=self.removecontent(courselectureid,contentid,contentpath);
				if(contentRemoved=="1"){
					self.viewexistingcontent(courselectureid,courseid);
					manageCourse.loadPageInfo(courseid);
				}
			}
			else{
				return false;
			}
		},
		removecontent:function(courselectureid,contentid,contentpath){
			$('.alert-block1').show();
			$('.ContentRemovemsg').text('Lecture Content has been Removed Successfully');
			var contentidobj={};
			contentidobj.courselectureid=courselectureid;
			contentidobj.contentid=contentid;
			contentidobj.contentpath=contentpath;
			contentRemoved=courseData.removeCourseContent(contentidobj);
			return contentRemoved;
		},
		removefilepath:function(existingContent){
			var existsCont=[];
			if(existingContent.length>0){
				_.each(existingContent,function(excontent){
					if(excontent!=undefined){
						excontent.oripath=excontent.contentpath;
						excontent.contentpath=_.last(excontent.contentpath.split('/'));
						existsCont.push(excontent);
					}										
				});
			}
			return existsCont;
		},
		loadFAQ:function(lectureidobj){
			var self=this;
			$("#content").hide();
			$("#coursequestions").addClass('active');
			$('.questionpart').show();
			$('.answerpart').hide();
			var questionsList=courseData.getLectureQuestions(lectureidobj);
			self.renderQuestions(questionsList);
		},
		renderQuestions:function(questionsList){
			var self=this;
			console.log('questionsListquestionsListquestionsListquestionsList::::::'+questionsList);
			if(questionsList!=undefined){
				renderTemplate('#faqtmpl',questionsList,'#faqtable');

				$('#faqtable').slimScroll({
					height: '430px'
				});
			}
			else{
				renderTemplate('#noquestionsstmpl',questionsList,'#faqtable');
				$('#faqtable').slimScroll({
					height: '430px'
				});
			}
			$('.newquestionbtn').click(function(){
				$('.newquestionrow').hide();
				$('.questioninput').show();
			});
			$('.answerlink').click(function(){
				$('.answerpart').show();
				$('.questionpart').hide();

				var FAQid=$(this).attr('FAQid');
				var FAQquestion=$(this).attr('FAQquestion');
				self.allanswerdata(FAQid,FAQquestion);
			});
			$('.questiontextarea').keypress(function(e){
				if (e.which == 13){
					$('.newquestionrow').show();
					$('.questioninput').hide();
					var questionval=$(this).val();
					var courselectureid=$(this).attr('courselectureid');
					self.questiondata(questionval,courselectureid);
				}
			});			
		},
		questiondata:function(questionval,courselectureid){
			var self=this;
			if(questionval!=''){
				var questionobj={};
				questionobj.FAQquestion=questionval;
				questionobj.courselectureid=courselectureid;
				self.saveQuestion(questionobj);
			}
		},
		saveQuestion:function(questionsobj){
			var self=this;
			savedResult=ajaxhelperwithdata("saveQuestion", questionsobj);
			if(savedResult!=''){
				lectureidobj={};
				lectureidobj.courselectureid=questionsobj.courselectureid;
				self.loadFAQ(lectureidobj);
				$('.questiontextarea').val('');
			}
		},
		allanswerdata:function(FAQid,FAQquestion){
			var self=this;
			var faqidobj={};
			console.log('Allanswer datea::::::::::faquid'+FAQid);
			faqidobj.FAQid=FAQid;
			faqidobj.FAQquestion=FAQquestion;
			self.getAnswersData(faqidobj);
		},
		getAnswersData:function(faqidobj){
			var self=this;
			var answersList=courseData.getAnswers(faqidobj);
			self.renderAnswers(answersList,faqidobj);
		},
		renderAnswers:function(answersList,faqidobj){ 


			var self=this;
			renderTemplate("#answerstmpl", answersList, "#faqanwsertable");
			$('#faqanwsertable').css('overflow','auto');
			$('#faqanwsertable').slimScroll({
				height: '430px'
			});
			var FAQid=faqidobj.FAQid;
			var FAQquestion=faqidobj.FAQquestion;

			$(".questionheader").text('');

			$(".questionheader").text(FAQquestion);
			$(".questionheader").attr("FAQid",FAQid);

			$('.newanswerbtn').click(function(){
				$('.newanswerrow').hide();
				$('.answerinput').show();
			});
			$('.answertextarea').keypress(function(e){
				if (e.which == 13){
					$('.newanswerrow').show();
					$('.answerinput').hide();
					self.saveAnswerData($(this).val(),$(".questionheader").attr("FAQid"));
				}
			});
			$('.backqnbtn').tooltip({
				placement: 'left',
				title:'back to Questions'
			});
			$('.backqnbtn').click(function(){
				$('.questionpart').show();
				$('.answerpart').hide();
				$('.trashanswerpart').hide();

			});
		},
		saveAnswerData:function(faqanswer,FAQid){
			if(faqanswer!=''){
				var self=this;
				var answerobj={};
				answerobj.FAQid=FAQid;
				answerobj.faqanswer=faqanswer;
				self.saveAnswerStatus(answerobj);	
			}				
		}
		,
		editCorrectAnswers:function(faqanswersid){
			var self=this;

			self.editCorrctAnswerEvent(faqanswersid);

		},		
		editCorrctAnswerEvent:function(faqanswersid){
			console.log('inside of edit correct answersSSsssss:1:'+faqanswersid);

			if(faqanswersid!=''){
				console.log('inside of edit correct answersSSsssss::2'+faqanswersid);

				var result=manageCourseFunctions.editCorrectAnswer(faqanswersid);
				if(result!="false")	{

					$('.mesdesc').text("Correct Answer Stored successfully");
					$('.editlecturetitle').val('');
					setTimeout(function(){
						$('#messagemodal').modal('hide');										
					},1000);										
					manageCourse.loadPageInfo(courseid);
				}	
			}
		}	
		,
		deleteAnswers:function(faqanswersid){
			var self=this;

			self.deleteAnswerEvent(faqanswersid);

		},		
		deleteAnswerEvent:function(faqanswersid){

			if(faqanswersid!=''){

				var result=manageCourseFunctions.deleteAnswer(faqanswersid);
				if(result!="false")	{

					$('.mesdesc').text("Correct Answer Stored successfully");
					$('.editlecturetitle').val('');
					setTimeout(function(){
						$('#messagemodal').modal('hide');										
					},1000);										
					manageCourse.loadPageInfo(courseid);
				}	
			}
		},
		trashAnswers:function(faqanswersid){
			var self=this;

			self.trashAnswerEvent(faqanswersid);

		},
		trashAnswerEvent:function(faqanswersid){

			if(faqanswersid!=''){

				var result=manageCourseFunctions.trashAnswer(faqanswersid);
				if(result!="false")	{

					$('.mesdesc').text("Answers Removed successfully");
					$('.editlecturetitle').val('');
					setTimeout(function(){
						$('#messagemodal').modal('hide');										
					},1000);										
					manageCourse.loadPageInfo(courseid);
				}	
			}
		},
		alltrashanswerdata:function(FAQid,FAQquestion){
			var self=this;
			var faqidobj={};
			console.log('alltrashanswerdata datea::::::::::faquid'+FAQid);
			faqidobj.FAQid=FAQid;
			faqidobj.FAQquestion=FAQquestion;
			self.getTrashAnswersData(faqidobj);
		},
		getTrashAnswersData:function(faqidobj){
			var self=this;
			var trashanswersList=courseData.getTrashAnswers(faqidobj);
			self.renderTrashAnswers(trashanswersList,faqidobj);
		},
		renderTrashAnswers:function(answersList,faqidobj){ 
			console.log(answersList);
			var self=this;
			renderTemplate("#trashanswerstmpl", answersList, "#faqtrashanwsertable");
			$('#faqanwsertable').css('overflow','auto');
			$('#faqanwsertable').slimScroll({
				height: '430px'
			});
			var FAQid=faqidobj.FAQid;
			var FAQquestion=faqidobj.FAQquestion;
			//$(".questionheader").text(FAQquestion);
			$(".questionheader").attr("FAQid",FAQid);

			$('.trashbackqnbtn').tooltip({
				placement: 'left',
				title:'back to Answers'
			});
			$('.trashbackqnbtn').click(function(){
				$('.answerpart').show();
				$('.questionpart').hide();
				$('.trashanswerpart').hide();
			});

		}

};
$('.ansbackqnbtn').tooltip({
	placement: 'left',
	title:'back to Lectures'
});
$('.ansbackqnbtn').click(function(){
	$('#content').show();	
	$('.answerpart').hide();
	$('.questionpart').hide();
	$('.trashanswerpart').hide();
});

$('#contentli').click(function(e){
//	e.stopPropagation();
	if($('.publishcoursebtn').attr('coursestatus')=="A"){
		$('#faqtable').show();
		$('.ansbackqnbtn').show();
		$('#myTab a[href="#content"]').tab('show');
		$('#content').show();	
		$('.answerpart').hide();
		$('.questionpart').hide();
		$('.trashanswerpart').hide();	
	}
	else{
		var binfvalidate=manageCourseFunctions.checkCourseLogo();
		if(binfvalidate==="OK"){
			var clogovalidate=manageCourseFunctions.checkCourseContent();
			if(clogovalidate=="logook"){
				$('#myTab a[href="#content"]').tab('show');
				$('#content').show();	
				$('.answerpart').hide();
				$('.questionpart').hide();
				$('.trashanswerpart').hide();	
			}
			else{
				$('#myTab a[href="#courselogo"]').tab('show');
				$('#messagemodal').modal('show');
				$('.mesdesc').text("Please upload the logo before you start uploading your content");	
			}		
		}
		else{
			$('#messagemodal').modal('show');
			$('.mesdesc').text("Please Finish basic Information before you start uploading your content");	
		}
	}		
});

$('#basicli').click(function(){
	$('#content').hide();
	$('.answerpart').hide();
	$('.questionpart').hide();
	$('.trashanswerpart').hide();
});
$('#courselogoli').click(function(e){
	e.preventDefault();
	e.preventDefault();
	if($('.publishcoursebtn').attr('coursestatus')=="A"){
		$('#myTab a[href="#courselogo"]').tab('show');
		$('#content').hide();	
		$('.answerpart').hide();
		$('.questionpart').hide();
		$('.trashanswerpart').hide();
	}
	else{
		var binfvalidate=manageCourseFunctions.checkCourseLogo();
		if(binfvalidate==="OK"){
			$('#myTab a[href="#courselogo"]').tab('show');
			$('#content').hide();	
			$('.answerpart').hide();
			$('.questionpart').hide();
			$('.trashanswerpart').hide();
		}
		else{
			$('#messagemodal').modal('show');
			$('.mesdesc').text("Please Finish basic Information In order to upload the logo");	
		}
	}

});
$('#roadmap').click(function(){
	$('#content').hide();	
	$('.answerpart').hide();
	$('.questionpart').hide();
	$('.trashanswerpart').hide();
});
$('#exam').click(function(){
	sat.initialize(null);
});


manageCourseFunctions={
		checkCourseLogo:function(){
			var ctitlelength=$('[name=coursetitle]').val().trim().length;
			var cdesclength=$('[name=coursedescription]').val().trim().length;
			var crsctgyid=$('#categorytable').val();
			var ckeywrdslength=$('[name=coursegoal]').val().trim().length;
			if(ctitlelength!=0){
				if(cdesclength!=0){
					if(crsctgyid!=0){
						if(ckeywrdslength!=0){
							return "OK";
						}
						else{
							return "keywrds";
						}
					}
					else{
						return "categry";						
					}
				}
				else{
					return "desc";					
				}
			}
			else{
				return "ctit";
			}
		},
		checkCourseContent:function(){
			var clogoavail=$('[name=courselogo]').val();
			if(clogoavail!=undefined){
				return "logook";
			}
			else{
				return "notok";
			}
		},
		addNewSection:function(courseid,coursesectiontitle){
			var data={};
			data.courseid=courseid;
			data.coursesectiontitle=encodeURIComponent(coursesectiontitle);
			var output=ajaxhelperwithdata("addNewSection", data);
			return output;
		},
		editSectionTitle:function(courseid,coursesectionid,coursesectiontitle){
			var data={};
			data.courseid=courseid;
			data.coursesectionid=coursesectionid;
			data.coursesectiontitle=encodeURIComponent(coursesectiontitle);
			var output=ajaxhelperwithdata("editSection", data);
			return output;
		},

		addNewLecture:function(coursesectionid,courselecturetitle,keywords,weblinks,summary){
			var data={};
			data.coursesectionid=coursesectionid;
			data.courselecturetitle=encodeURIComponent(courselecturetitle);
			data.keywords=keywords;
			data.weblinks=weblinks;
			data.summary=summary;
			var output=ajaxhelperwithdata("addNewLecture", data);
			return output;
		},
		editLectureTitle:function(coursesectionid,courselectureid,courselecturetitle){
			var data={};
			data.coursesectionid=coursesectionid;
			data.courselectureid=courselectureid;
			data.courselecturetitle=encodeURIComponent(courselecturetitle);
			var output=ajaxhelperwithdata("editLecture", data);
			return output;
		},
		editCorrectAnswer:function(faqanswersid){
			console.log('inside of edit correct answersSSsssss:final:'+faqanswersid);
			var data={};
			data.faqanswersid=faqanswersid;
			var output=ajaxhelperwithdata("editCorrectAnswer", data); 
			var questid=$('.questionheader').attr('faqid');
			var question=$('.questionheader').text();

			manageContentInfo.allanswerdata(questid,question);
			return output;
		},
		deleteAnswer:function(faqanswersid){
			console.log('inside of deleteAnswer correct answersSSsssss:final:'+faqanswersid);
			var data={};
			data.faqanswersid=faqanswersid;
			var output=ajaxhelperwithdata("deleteAnswer", data); 
			var questid=$('.questionheader').attr('faqid');
			var question=$('.questionheader').text();

			manageContentInfo.alltrashanswerdata(questid,question);
			return output;
		},

		trashAnswer:function(faqanswersid){
			console.log('inside of trashAnswer answersSSsssss:final:'+faqanswersid);
			var data={};
			data.faqanswersid=faqanswersid;
			var output=ajaxhelperwithdata("trashAnswer", data); 
			var questid=$('.questionheader').attr('faqid');
			var question=$('.questionheader').text();

			manageContentInfo.allanswerdata(questid,question);
			return output;
		}

};
Handlebars.registerHelper('correctanswer', function(value,faqanswerid,faqanswer) {


	if( value=="C") {
		return '<i class="fa fa-check"></i>&nbsp;&nbsp;'+faqanswer;
	} else {
		return "<input type='checkbox' id='correctanswer'  faqanswerid="+faqanswerid+" class='correctanswerchk'>&nbsp;<font>"+faqanswer+"</font>";
	}
});

var contUpload ={
		uploadContentFiles:function(courseid,courselectureid){
			var bar = $('.progress-bar.progress-bar-success');
			var percent = $('.progressper');
			var status = $('#status');		
			status.empty();
			var xhr=new XMLHttpRequest();
			xhr.upload.onprogress = function(evt){
				$('.uploadcnt').hide();
				if (evt.lengthComputable) {					
					var percentComplete =Math.floor((evt.loaded / evt.total)*100);
					bar.show();
					percent.html(percentComplete+"% ");
					bar.css("width",percentComplete+"% ");
				} 
			};
			xhr.onload=function(data){
				if(xhr.status == 200){
					if(xhr.responseText=="error"){		
						$('#newcon').show();
						$('.errblcok').show();
						$('.errblcok').removeClass('alert-success').addClass('alert-error');
						bar.width('0%');
						percent.html('0%');
						$('.emsg').text("Upload failed. Please contact Administrator");
						$('.input-file').val('');
						$('.uploadcnt').hide();
						$('.prggrop').hide();
					}
					else if(xhr.responseText=="exception"){		
						$('#newcon').show();
						$('.errblcok').show();
						$('.errblcok').removeClass('alert-success').addClass('alert-danger');
						bar.width('0%');
						percent.html('0%');
						$('.emsg').text("Upload failed. Please contact Administrator");
						$('.input-file').val('');
						$('.uploadcnt').hide();
						$('.prggrop').hide();
					}
					else if(xhr.responseText=="nospace"){
						$('#newcon').show();
						$('.errblcok').show();
						$('.errblcok').removeClass('alert-success').addClass('alert-danger');
						bar.width('0%');
						percent.html('0%');
						$('.emsg').text("Upload failed. Please contact Administrator");
						$('.input-file').val('');
						$('.uploadcnt').hide();
						$('.prggrop').hide();
					}
					else{
						$('.errblcok').show();
						$('.errblcok').removeClass('alert-danger').addClass('alert-success');
						$('.strbtn').text('Success !');
						$('.emsg').text("Upload Finished");
						$('.input-file').val('');
						$('.uploadcnt').hide();
						$('.prggrop').hide();
						$('#newcon').hide();
						manageContentInfo.showContent(courselectureid);
						manageCourse.loadPageInfo(courseid);
					}
				}else{
					$('#newcon').show();
					$('.errblcok').show();
					$('.errblcok').removeClass('alert-success').addClass('alert-error');
					bar.width('0%');
					percent.html('0%');
					$('.emsg').text("Upload failed. Please contact Administrator");
					$('#contfile').val('');
					$('.uploadcnt').hide();
					$('.prggrop').hide();
				}
			};
			xhr.onerror=function(){
				console.info("Error ! upload failed.Can not connect to server");
				$('#newcon').show();
				$('.errblcok').show();
				$('.errblcok').removeClass('alert-success').addClass('alert-error');
				bar.width('0%');
				percent.html('0%');
				$('.emsg').text("Upload failed. Please contact Administrator");
				$('#contfile').val('');
				$('.uploadcnt').hide();
				$('.prggrop').hide();
			};
			var fd=new FormData();
			fd.append("file",document.getElementById('contfile').files[0]);
			fd.append("courselectureidhidden",$('[name=courselectureidhidden]').val());
			fd.append("filesizeval",$('[name=filesizeval]').val());
			fd.append("enstatus",$('[name=enstatus]').val());
			xhr.open("POST",courseData.getContextPath()+"/rest/file/upload",true);
			xhr.send(fd);
		}
};