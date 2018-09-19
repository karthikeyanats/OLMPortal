$(function(){ 
	contentApprove().init();
});

var contentApprove = contentApprove || {};

contentApprove = (function(){

	var courseid = $("[name=courseid]").val();

	var model = {

			info:function(){
				var thislist = Ajax.get(courseData.getContextPath()+"/rest/gpreview/info/"+courseid,undefined,"json","application/json");
				return thislist = _.isObject(thislist) ? thislist : null;
			},

			curriculum:function(){
				var thislist = Ajax.get(courseData.getContextPath()+"/rest/gpreview/curriculum/"+courseid,undefined,"json","application/json");
				return thislist = _.isObject(thislist) ? thislist : null;
			},
	};

	var view = {

			home : function(){

				var list = model.info();
				renderTemplate("#courseinfotmpl", list, ".leftpane");
				shortenCharcters(".shorttext",150);

				var price=$('.pricebtn').attr('price');
				var priceicon = $('.pricebtn').attr('priceicon');
				if(price=="Free"){
					$('.pricebtn').html("Free");
				}else if(price==""){
					$('.pricebtn').html("");
				}else{
					$('.pricebtn').html("<i class='fa "+priceicon+"'></i>"+price);
				}

				$('.promovideolink').click(function(){
					$('.dropdownlecturestable').hide();
					view.promoVideo($(this).attr('videolink'));
				});

				var authorpersonid = list[0].orgpersonid;
				$("[name=authorpersonid]").val(authorpersonid);
				$("[name=coursetitle]").val(list[0].coursetitle);

				var lecturesList = util.lectureList(model.curriculum());
				renderTemplate("#subjectpartstmpl", lecturesList, ".dropdownlecturestable");

				$('[name=currentlectureid]').val($('.leclink:first').attr('lectureid'));
				$('[name=currentlecture]').val($('.leclink:first').attr('lecturetitle'));
				$('[name=currentsection]').val($('.leclink:first').attr('sectiontitle'));
				$('[name=currentcontentpath]').val($('.leclink:first').attr('contentpath'));
				$('[name=currentcontentid]').val($('.leclink:first').attr('contentid'));

				view.lectureStuffs();

				$('.slidetoggledrop').click(function(){				
					$('.dropdownlecturestable').slideToggle();
				});

				$('#tree li').on('click', function (e) {
					var children = $(this).find('> ul > li');
					if (children.is(":visible")) children.hide('fast');
					else children.show('slow');
					e.stopPropagation();
				});

				$('.leclink').click(function(){
					$('.dropdownlecturestable').slideToggle();
					$('[name=currentlecture]').val($(this).attr('lecturetitle'));
					$('[name=currentsection]').val($(this).attr('sectiontitle'));
					$('[name=currentcontentpath]').val($(this).attr('contentpath'));
					$('[name=currentcontentid]').val($(this).attr('contentid'));
					$('[name=currentlectureid]').val($(this).attr('lectureid'));
					view.lectureStuffs();					
				});
			},

			promoVideo : function(contentpath){

				var obj = {
						contentpath : contentpath
				}; 

				$("#usercommonmodal").modal("show");
				$("#userModalLabel").text("Promo Video");
				renderTemplate('#promovideocontenttmpl',obj,'#usercommonmodalbody');
				$('.promoflowplayer').flowplayer();
			},

			lectureStuffs : function(){
				$(".contenttitle").text($('[name=currentsection]').val()+" / "+$('[name=currentlecture]').val());
				var id = $('[name=currentlectureid]').val();
				learninglectureInitialLoadItems.home(id);
				checkCourseApproveData.home();
				checkLectureApprove.home(id);
				view.lectureContent();
			},

			lectureContent : function(){

				var contentList = {
						contentpath : $('[name=currentcontentpath]').val()
				};

				var currentcontentpath = $('[name=currentcontentpath]').val();
				var filetypesplit= currentcontentpath.split('.');
				var filetype= filetypesplit[filetypesplit.length-1];
				if(filetype==="pdf"){
					view.renderpdfcontent(contentList);
				}
				else if(filetype==="swf"){
					view.renderSwfContent(contentList);
				}
				else{
					view.renderVideoContent(contentList);
				}
			},
			renderpdfcontent:function(contentList){
				renderTemplate('#pdfcontenttmpl',contentList,'#contenttable');
			},
			renderSwfContent:function(contentList){
				renderTemplate('#swfcontentmpl',contentList,'#contenttable');
			},
			renderVideoContent:function(contentList){
				renderTemplate('#videocontenttmpl',contentList,'#contenttable');
				$('.flowplayer').flowplayer();
			},
			renderNoContent:function(contentList){
				renderTemplate('#nocontenttmpl',contentList,'#contenttable');			
			},

	};	

	var util = {
			lectureList:function(list){
				sections = [];
				sectionids =_.uniq(_.pluck(list,'sectionid'));
				_.each(sectionids,function(sectionid){
					var sectionarray = _.where(list,{sectionid:sectionid});
					var section = _.pick(sectionarray[0],'sectionid','sectiontitle');
					var lectureids=_.uniq(_.pluck(sectionarray,'lectureid'));
					var lecture = [];
					_.each(lectureids,function(lectureid){
						var lectureArray = _.where(sectionarray,{lectureid:lectureid});
						var lectureOutput=_.pick(lectureArray[0],'sectiontitle','lectureid','lecturetitle','contentpath','contentid');
						lecture.push(lectureOutput);
					});
					section.lectures=lecture;
					sections.push(section);
				});
				return sections;
			}

	};

	return {
		init:function(){
			initialize();	
		}
	};	

	function initialize(){
		view.home();		
	}	

});

learninglectureInitialLoadItems = {
		home : function(id){
			learningLectureInformation.home(id);
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

			case "quiz":
				learningLectureQuiz.home(id);
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

checkCourseApproveData = {
		home : function(){
			this.view();
		},

		model : function(obj){
			return Ajax.post(courseData.getContextPath()+"/rest/approve/course",obj,"json","application/json");			
		},

		view : function(){
			var thislist = this.list();
			var totalListLength = thislist.length;
			var approvedListLength = _.where(thislist,{approvestatus:'A'}).length;
			var rejectedListLength = _.where(thislist,{approvestatus:'R'}).length;
			if(totalListLength == approvedListLength){
				$(".courseappholder").html("<a class='btn btn-success courseapprovebtn'>Approve Course</a>");
			}else{
				if(rejectedListLength!=0){
					$(".courseappholder").html("<a class='btn btn-danger courserejectbtn'>Reject Course</a>");
				}else{
					$(".courseappholder").html("");
				}
			}
			checkCourseApproveData.courseApproveEvents();
		},

		courseApproveEvents : function(){
			$('.courseapprovebtn').click(function(){
				var checkstr =  confirm('Are you sure you Approve this course');
				if(checkstr == true) {
					$(".courseappholder").html("");
					checkCourseApproveData.courseApprove();	
				}
			});

			$('.courserejectbtn').click(function(){
				var checkstr =  confirm('Are you sure you Reject this course');
				if(checkstr == true) {
					$(".courseappholder").html("");
					checkCourseApproveData.courseReject();
				}
			});
		},

		courseApprove : function(){

			var obj = {
					coursetitle : $("[name=coursetitle]").val(),
					courseid : $("[name=courseid]").val(),
					coursestatus : "A",
					authororgpersonid : $("[name=authorpersonid]").val()
			};
			var approveStatus = checkCourseApproveData.model(obj);

			if(approveStatus == "OK"){
				
				renderTemplate("#commonapprovesuccesstmpl", null, "#approvealertholder");
				$(".commonapprovealertmsg").text("Course Successfully Approved");
				setTimeout(function(){ 
					$('[name=contentapproveform]').attr('action','courses?a=pc');
					$('[name=contentapproveform]').submit();
				}, 4000);
				
			}else{
				renderTemplate("#commonuserfailuretmpl", null, "#approvealertholder");
				$(".commonapprovealertmsg").text("Error in Approving the Course. Please try again Later.");
				setTimeout(function(){ 
					$('#approvealertholder').hide();
				}, 4000);
			}
		},

		courseReject : function(){
			var obj = {
					coursetitle : $("[name=coursetitle]").val(),
					courseid : $("[name=courseid]").val(),
					coursestatus : "D",
					authororgpersonid : $("[name=authorpersonid]").val()
			};
			var approveStatus = checkCourseApproveData.model(obj);

			if(approveStatus == "OK"){
				$('#approvealertholder').show();
				renderTemplate("#commonapprovesuccesstmpl", null, "#approvealertholder");
				$(".commonapprovealertmsg").text("Course Successfully Rejected");
				setTimeout(function(){ 
					$('#approvealertholder').hide();
					$('[name=contentapproveform]').attr('action','courses?a=dc');
					$('[name=contentapproveform]').submit();
				}, 4000);

			}else{
				$('#approvealertholder').show();
				renderTemplate("#commonuserfailuretmpl", null, "#approvealertholder");
				$(".commonapprovealertmsg").text("Error in Rejecting the Course. Please try again Later.");
				setTimeout(function(){ 
					$('#approvealertholder').hide();
				}, 4000);
			}
		},

		list : function(){

			var obj = {
					courseid : $("[name=courseid]").val()
			};
			return ajaxhelperwithdata("loadCourseApproveStatus", obj);			

		} 		
};

checkLectureApprove = {
		home : function(id){
			this.view(id);
		},

		model : function(obj){
			return Ajax.post(courseData.getContextPath()+"/rest/approve/create",obj,"json","application/json"); 
		},

		view : function(id){
			var thislist = this.list(id);
			if(thislist=="false"){
				$(".appholder").html("<a class='btn btn-success marright7 approvebtn'>Approve</>" +
				"<a class='btn btn-danger marright7 rejectbtn'>Reject</>");
				this.viewEventsAsNew();
			}else {
				var approveStatus = thislist[0].approvestatus; 
				if(approveStatus == "A"){
					$(".appholder").html("<a class='btn btn-success marright7'>Approved</>" +
					"<a class='btn btn-danger marright7 rejectbtn'>Reject</>");
				}else{
					$(".appholder").html("<a class='btn btn-success marright7 approvebtn'>Approve</>" +
					"<a class='btn btn-danger marright7'>Rejected</>");
				}
				this.viewEventsAsUpdate(thislist[0].contentapprovalid);
			}

		},

		viewEventsAsNew : function(){
			$('.approvebtn').click(function(){
				var checkstr =  confirm('Are you sure you Approve this content');
				if(checkstr == true) {
					$(".appholder").html("");
					$(".courseappholder").hide();
					var obj = {
							contentid : $('[name=currentcontentid]').val(),
							description : "OK",
							approvestatus : "A",
							authororgpersonid : $("[name=authorpersonid]").val()
					};
					var approvedStatus = checkLectureApprove.model(obj);
					if(approvedStatus == "OK"){
						$('#approvealertholder').show();
						renderTemplate("#commonapprovesuccesstmpl", null, "#approvealertholder");
						$(".commonapprovealertmsg").text("Content Successfully Approved");
						setTimeout(function(){ 
							$(".courseappholder").show();
							$('#approvealertholder').hide();
							checkLectureApprove.home($('[name=currentlectureid]').val());
							checkCourseApproveData.home();
						}, 4000);

					}else{
						$('#approvealertholder').show();
						renderTemplate("#commonuserfailuretmpl", null, "#approvealertholder");
						$(".commonapprovealertmsg").text("Error in Approving the Content. Please try again Later.");
						setTimeout(function(){ 
							$(".courseappholder").show();
							$('#approvealertholder').hide();
							checkLectureApprove.home($('[name=currentlectureid]').val());
							checkCourseApproveData.home();
						}, 4000);
					}
				}
			});

			$('.rejectbtn').click(function(){
				$('.appholder').hide();
				$('.commouseralert').hide();
				$('.dropbox').slideDown(400);
				$('.sendcommomtextarea').attr('placeholder','Enter Your Reject Reason here..');
				$('.sendcommomtextarea').val("");
				$('.sendcommonbtn').text("Submit");
				checkLectureApprove.newRejectEvents();
			});
		},

		newRejectEvents : function(){
			$('.sendcommomtextarea').keyup(function(){
				el = $(this);
				if(el.val().length >= 501){
					el.val( el.val().substr(0, 500) );
				}
			});
			$('.sendcommonbtn').off().click(function(){
				var thislength = $('.sendcommomtextarea').val().trim().length;
				if(thislength!=0){
					$('.commouseralert').hide();
					checkLectureApprove.newRejectWithReason($('.sendcommomtextarea').val().trim());
				}else{
					$('.commouseralert.alert-danger').show();
					$('.commonusermsg').text("Please Dont leave it blank");
				}
			});
			$('.closebtn').click(function(){
				$(".courseappholder").show();
				$('.appholder').show();
				$('.dropbox').slideUp(400);
				$('.sendcommomtextarea').val("");
			});
		},

		newRejectWithReason : function(description){
			var checkstr =  confirm('Are you sure you Reject this content');
			if(checkstr == true) {
				$('.appholder').show();
				$(".appholder").html("");
				$(".courseappholder").hide();
				var obj = {
						contentid : $('[name=currentcontentid]').val(),
						description : encodeURIComponent(description),
						approvestatus : "R",
						authororgpersonid : $("[name=authorpersonid]").val()
				};
				var approvedStatus = checkLectureApprove.model(obj);

				if(approvedStatus == "OK"){
					$("#approvealertholder").show();
					renderTemplate("#commonapprovesuccesstmpl", null, "#approvealertholder");
					$(".commonapprovealertmsg").text("Content Successfully Rejected");
					setTimeout(function(){ 
						$(".courseappholder").show();
						$('#approvealertholder').hide();
						checkLectureApprove.home($('[name=currentlectureid]').val());
						checkCourseApproveData.home();
						$('.sendcommomtextarea').val("");
						$('.commouseralert').hide();
						$('.dropbox').slideUp(400);
					}, 4000);

				}else{
					$("#approvealertholder").show();
					renderTemplate("#commonuserfailuretmpl", null, "#approvealertholder");
					$(".commonapprovealertmsg").text("Error in Rejecting the Content. Please try again Later.");
					setTimeout(function(){ 
						$(".courseappholder").show();
						$('#approvealertholder').hide();
						checkLectureApprove.home($('[name=currentlectureid]').val());
						checkCourseApproveData.home();
						$('.commouseralert').hide();
						$('.dropbox').slideUp(400);
					}, 4000);
				}
			}		
		},

		viewEventsAsUpdate : function(id){
			$('.approvebtn').click(function(){
				var checkstr =  confirm('Are you sure you Approve this content');
				if(checkstr == true) {
					$(".appholder").html("");
					$(".courseappholder").hide();
					var obj = {
							contentid : $('[name=currentcontentid]').val(),
							description : "OK",
							approvestatus : "A",
							authororgpersonid : $("[name=authorpersonid]").val(),
							contentapprovalid : id
					};
					var approvedStatus = checkLectureApprove.model(obj);

					if(approvedStatus == "OK"){
						$("#approvealertholder").show();
						renderTemplate("#commonapprovesuccesstmpl", null, "#approvealertholder");
						$(".commonapprovealertmsg").text("Content Successfully Approved");
						setTimeout(function(){ 
							$(".courseappholder").show();
							$('#approvealertholder').hide();
							checkLectureApprove.home($('[name=currentlectureid]').val());
							checkCourseApproveData.home();
						}, 4000);

					}else{
						$("#approvealertholder").show();
						renderTemplate("#commonuserfailuretmpl", null, "#approvealertholder");
						$(".commonapprovealertmsg").text("Error in Approving the Content. Please try again Later.");
						setTimeout(function(){ 
							$(".courseappholder").show();
							$('#approvealertholder').hide();
							checkLectureApprove.home($('[name=currentlectureid]').val());
							checkCourseApproveData.home();
						}, 4000);
					}
				}
			});

			$('.rejectbtn').click(function(){
				$('.appholder').hide();
				$('.commouseralert').hide();
				$('.dropbox').slideDown(400);
				$('.sendcommomtextarea').attr('placeholder','Enter Your Reject Reason here..');
				$('.sendcommomtextarea').val("");
				$('.sendcommonbtn').text("Submit");
				checkLectureApprove.rejectEvents(id);
			});
		},

		rejectEvents : function(id){
			$(".courseappholder").hide();
			$('.sendcommomtextarea').keyup(function(){
				el = $(this);
				if(el.val().length >= 501){
					el.val( el.val().substr(0, 500) );
				}
			});
			$('.sendcommonbtn').off().click(function(){
				var thislength = $('.sendcommomtextarea').val().trim().length;
				if(thislength!=0){
					$('.commouseralert').hide();
					checkLectureApprove.rejectWithReason(id,$('.sendcommomtextarea').val().trim());
				}else{
					$('.commouseralert.alert-danger').show();
					$('.commonusermsg').text("Please Dont leave it blank");
				}
			});

			$('.closebtn').click(function(){
				$(".courseappholder").show();
				$('.appholder').show();
				$('.dropbox').slideUp(400);
				$('.sendcommomtextarea').val("");
			});
		},

		rejectWithReason : function(id,description){
			var checkstr =  confirm('Are you sure you Reject this content');
			if(checkstr == true) {
				$('.appholder').show();
				$(".appholder").html("");
				$(".courseappholder").hide();
				var obj = {
						contentid : $('[name=currentcontentid]').val(),
						description : encodeURIComponent(description),
						approvestatus : "R",
						authororgpersonid : $("[name=authorpersonid]").val(),
						contentapprovalid : id
				};
				var approvedStatus = checkLectureApprove.model(obj);
				if(approvedStatus == "OK"){
					$("#approvealertholder").show();
					renderTemplate("#commonapprovesuccesstmpl", null, "#approvealertholder");
					$(".commonapprovealertmsg").text("Content Successfully Rejected");
					setTimeout(function(){ 
						$(".courseappholder").show();
						$('#approvealertholder').hide();
						checkLectureApprove.home($('[name=currentlectureid]').val());
						checkCourseApproveData.home();
						$('.sendcommomtextarea').val("");
						$('.commouseralert').hide();
						$('.dropbox').slideUp(400);
					}, 4000);

				}else{
					$("#approvealertholder").show();
					renderTemplate("#commonuserfailuretmpl", null, "#approvealertholder");
					$(".commonapprovealertmsg").text("Error in Rejecting the Content. Please try again Later.");
					$('.sendcommomtextarea').val("");
					setTimeout(function(){ 
						$(".courseappholder").show();
						$('#approvealertholder').hide();
						$('.dropbox').slideUp(400);
						checkLectureApprove.home($('[name=currentlectureid]').val());
						checkCourseApproveData.home();
					}, 4000);
				}
			}
		},

		list : function(id){
			var obj = {
					courselectureid : id	
			};
			return ajaxhelperwithdata("loadLectureApproveStatus", obj);
		} 		
};

learningLectureInformation = {
		home : function(id){
			$('.informationview .borderbottoma').removeClass('active');
			$('.informationtablink').addClass('active');
			this.view(id);
		},

		view : function(id){
			var thislist = this.list(id);
			renderTemplate("#learningLectureInformationtmpl", thislist, '#approvebottomtab');
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

learningLectureQuiz = {
		home : function(id){
			$('.informationview .borderbottoma').removeClass('active');
			$('.quiztablink').addClass('active');
			this.view(id);
		},		
		view : function(id){
			Handlebars.registerHelper('question-helper', function(val){
				return val.replace(new RegExp("courseData.getContextPath()", 'g'),courseData.getContextPath());
			});
			Handlebars.registerHelper('answer', function() {
				var qtypeid = this.id;
				var correctans = this.correctans;
				var answers = this.answers;
				if (qtypeid == "3" || qtypeid == "4") {
					if (correctans != 111)
						return "color: forestgreen;";
					else
						return "";
				}
				if (qtypeid != "1") {
					if (correctans != "0")
						return "color: forestgreen;";
					else
						return "";
				} else {
					if (answers == "True" && correctans == "1")
						return "color: forestgreen;";
					else if (answers == "False" && correctans == "0")
						return "color: forestgreen;";
					else
						return "";
				}
			});
			Handlebars.registerHelper('match-question', function(question, answerList,options) {
				var items=question.split("%J@A#C&K~");
				var slno=1;
				var object=answerList;
				var out='';
				for (var i=0;i<object.length;i++){
					var ans=object[i].answers;
					var qst="";
					ans=ans.replace(new RegExp("courseData.getContextPath()", 'g'),courseData.getContextPath());
					if(items.length > i){
						qst=items[i];
						qst=qst.replace(new RegExp("courseData.getContextPath()", 'g'),courseData.getContextPath());
					}
					out+= '<div class="col-md-12 match-container" >'+
					'<div class="col-md-6">'+slno+'. '+qst+'</div>'+
					'<div class="col-md-6">'+slno+'. '+ans+'</div></div>';
					slno++;
				}
				return out;
			});
			var thislist = this.list(id);
			renderTemplate("#learningLectureQuiztmpl", thislist, '#approvebottomtab');
		},

		list : function(id){
			var obj = {
					courselectureid : id
			};
			var data = ajaxhelperwithdata("loadlecturequiz", obj);
			var thislist = this.quizFormater(data); 

			var questions = _.groupBy(thislist, 'id', function(model) {
				return model;
			});
			var trufalse = _.pick(questions,"1");
			var truFalseList = _.toArray(trufalse);

			var objective = _.pick(questions,["2"]);
			var objectiveList = _.toArray(objective);

			var fillintheBlank = _.pick(questions,["3"]);
			var fillintheBlankList = _.toArray(fillintheBlank);

			var matchValues = _.pick(questions,"4");
			var matchValuesList = _.toArray(matchValues);

			var finalList = {
					trurFalseList : truFalseList[0],
					objList : objectiveList[0],
					matchValuesList:matchValuesList[0],
					fillintheBlankList : fillintheBlankList[0]
			}; 

			return finalList;
		},

		quizFormater : function(thislist){

			var questions = [];
			questionids =_.uniq(_.pluck(thislist,'questionid'));
			_.each(questionids,function(questionid){
				var questionarray = _.where(thislist,{questionid:questionid});
				var question = _.pick(questionarray[0],'questionid','question','id');
				var answerids=_.uniq(_.pluck(questionarray,'answerid'));
				var answer = [];
				_.each(answerids,function(answerid){
					var answerArray = _.where(questionarray,{answerid:answerid});
					var answerOutput=answerArray[0];
					//var answerOutput=_.pick(answerArray[0],'answerid','answers');
					answer.push(answerOutput);
				});
				question.answers=answer;
				questions.push(question);
			});
			return questions;
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
			renderTemplate("#learningLectureActivitytmpl", thislist, "#approvebottomtab");
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
			renderTemplate("#learningLectureBoxItemtmpl", thislist, "#approvebottomtab");
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
			renderTemplate("#learningLectureDiagramtmpl", thislist, "#approvebottomtab");
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
			renderTemplate("#learningLectureTabletmpl", thislist, "#approvebottomtab");
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
			renderTemplate("#learningLectureMaptmpl", thislist, "#approvebottomtab");
			$('#cars .item:first').addClass('active');
			$('#cars').carousel();
		},

		list : function(id){
			return list = metainfoListByLecture(id,"Map");
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
			renderTemplate("#learningLectureReferencetmpl", thislist, "#approvebottomtab");
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
			renderTemplate("#learningLectureWeblinkstmpl", thislist, "#approvebottomtab");
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
			renderTemplate("#learningLectureProjectIdeastmpl", thislist, "#approvebottomtab");
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