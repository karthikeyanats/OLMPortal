/**
 * Self Assessment Test Evaluation
 * Author 	: Selvarajan.j
 * Date 	: 19-Sep-2014	
 */
SATEvaluation={
		Controllers:{},
		Views:{},
		Business:{},
		Selectors:{},
};
/** 
 * Communicate to server function
 */
SATEvaluation.Business={
		// GET QUESTIONS @Param COURSELECTUREID
		getQuestionIDs:function(courselectureid){
			var data=Ajax.get(courseData.getContextPath()+"/rest/connector/questionID?lectureid="+courselectureid,null,"json","application/json");
			
			if(data != "NOT_FOUND"){
				SATEvaluation.Selectors.element.$totalQust.text("Total no.of Question(s) : "+data.length);
				SATEvaluation.Controllers.loadQuestions(data);
				$(".modal-footer").show();
				$(".result").show();
			}else{
				SATEvaluation.Selectors.element.$totalQust.text("Total no.of Question(s) : 0");
				SATEvaluation.Views.renderEmpty();
			}
		},
		getQuestions:function(questionid){
			var data=Ajax.get(courseData.getContextPath()+"/rest/connector/getSTDAnswer?questionid="+questionid,null,"json","application/json");
			if(data != "NOT_FOUND"){
				SATEvaluation.Controllers.global.count=0;
				var questions={};
				questions=_.groupBy(data,'questionid', function(model){return model;});
				questions=_.toArray(questions);
				questions[0][0].sno=((SATEvaluation.Controllers.global.pgindex)+1);
				//console.log("CHECK POINTS "+questions);
				SATEvaluation.Views.renderQuestion(questions);
				SATEvaluation.Controllers.global.qst=data;
				$("input:checked").parent().removeClass("deselected").addClass("selected");
				
			}else{
				SATEvaluation.Views.renderEmpty();
			}
			SATEvaluation.Views.imgHover();
		},
		// SAVE STUDENT ANSWER PROCESS HANDLER @Param OBJECT
		saveAnswer:function(obj){
			var url=courseData.getContextPath();
			if(obj.process == "insert")
				url+="/rest/connector/saveAnswer";
			else		
				url+="/rest/connector/updateAnswer";
				
			var data=Ajax.post(url,obj,"json","application/json");
				if(data != "NOT_FOUND"){
 					SATEvaluation.Controllers.moveToNext();
 				}else{
					SATEvaluation.Views.showAlertEvent("failed","Your answer submit process is failed");
				}
		},
		studentResult:function(courselectureid,time){
			var data=Ajax.get(courseData.getContextPath()+"/rest/connector/getSDTResult?lectureid="+courselectureid+"&time="+time,null,"json","application/json");
			if(data != "NOT_FOUND"){
				return data;
			}else{
				SATEvaluation.Views.renderEmpty();
			}
		},
		getCorrectAnswer:function(){
			var elemnt=$("#tabs").find("div:visible").find("font");
			var data=Ajax.get(courseData.getContextPath()+"/rest/connector/evaluation?lectureid="+elemnt.attr("courselectureid"),null,"json","application/json");
			
			if(data != "NOT_FOUND"){
				 pluck=_.pluck(data,'questionid');
				 union=_.union(pluck);
				
				questions=_.groupBy(data,'questionid', function(model){
					return model;
				});
				SATEvaluation.Views.renderCorrectAnswer(questions);
				var data=Ajax.get(courseData.getContextPath()+"/rest/connector/studentquizans?questionid="+union.toString(),null,"json","application/json");
				SATEvaluation.Controllers.updateStdAns(data,questions);
			}else{
				SATEvaluation.Views.renderEmpty();
			}
			SATEvaluation.Views.imgHover();
		}
		
};
//CONFIGURATION VIEW PARTS
SATEvaluation.Views={
		configModal:function(){
			$('.quiz').click(function(){
				//SATEvaluation.Controllers.setEmpty();
				//SATEvaluation.Controllers.global.crtAns=[];
				SATEvaluation.Selectors.element.$quizModal.modal('show');
				var elemnt=$("#tabs").find("div:visible").find("font");
				SATEvaluation.Selectors.element.$modalHeading.text(elemnt.text());
				SATEvaluation.Business.getQuestionIDs(elemnt.attr("courselectureid"));
				courselectureid=elemnt.attr("courselectureid");
				SATEvaluation.Selectors.element.$totalQust.show();
				SATEvaluation.Controllers.global.timerstatus=0;
				SATEvaluation.Controllers.global.pgindex=0;
				$('.clock').text("00:00");
				$('.close-btn').click(function(){
					clearInterval(SATEvaluation.Controllers.global.tmstart);
				});
				
			});
		},	
		renderQuestion:function(obj){
			renderTemplate("#questionstmpl",obj,"#questionsDiv");
			
			/*if(obj[0][0].answerstate != null)
				$("#description").show();*/
			// Binding click action of selected class and deselected class event
			SATEvaluation.Controllers.checkBokTogggleEvent();
			if(obj[0][0].answerstate !="C"){
				$("#sortable").shuffleChildren();
				SATEvaluation.Controllers.configDragEvent();
			}
			
			SATEvaluation.Controllers.global.matchingItem=[];
			if(obj[0][0].qtypeid == "4"){
				for(var k=0;k<obj[0].length;k++)
					SATEvaluation.Controllers.global.matchingItem.push(obj[0][k].answerid);
				
				SATEvaluation.Controllers.updateElementEvent();	
			}else if(obj[0][0].qtypeid == "3"){
				for(var k=0;k<obj[0].length;k++)
					SATEvaluation.Controllers.global.matchingItem.push(obj[0][k].answers);
				
				 //$('.tool-tip').tooltip();
				
				 if(obj[0][0].answerstate == "C"){
					 $('.custom-input').each(function (i){
						 $(this).val(obj[0][i].answers);
					 });
				 }else
					 $("#draggable").shuffleChildren();
				 
				 
				 $( "#draggable li" ).draggable({
					      helper: "clone"
					    });
					
					 $( ".custom-input" ).droppable({
					      drop: function( event, ui ) {
					    	$( this ).val( ui.draggable.text() );
					      }
				    });
			}
		},
		setEmpty:function(){
			$(".modal-footer").hide();
			//$("#status").text("");
			//$('.notification').hide();
		},
		renderEmpty:function(){
			var self=this;
			renderTemplate("#emptyQuestionstmpl",null,"#questionsDiv");
			self.setEmpty();
		},
		renderCorrectAnswer:function(data){
			renderTemplate("#questionsAnswertmpl",data,"#questionsDiv");
			SATEvaluation.Controllers.updateElementEvent();
		},
		showAlertEvent:function(alert_type,message){
			$('.alert').remove();
			if(alert_type == "success")
				$('#showmessage').html("<div class='alert alert-success alert-fixed-top' style=' width: 100%;padding-top: 10px;padding-bottom: 10px;' role='alert' ><button type='button' class='close' data-dismiss='alert'><div aria-hidden='true'>&times;</div></button><strong>Success !</strong> "+message+".</div>"); 
			else if(alert_type == "failed")
				$('#showmessage').html("<div class='alert alert-danger alert-fixed-top' style=' width: 100%;padding-top: 10px;padding-bottom: 10px;' role='alert' ><button type='button' class='close' data-dismiss='alert'><div aria-hidden='true'>&times;</div></button><strong>Message !</strong> "+message+".</div>");
			
			$(".alert").fadeIn('slow').delay(2000).fadeOut('slow');
		},
		imgHover:function(){
			$('.max-image').click(function(){
				window.open($(this).attr("src"), "",  "width=650, height=550");
			});
		},
		renderAnsStatus:function(data){
			renderTemplate("#stdAnstmpl",data,"#stdAnsUL");
		}
		
};
//CONFIGURATION FUNCTIONALITY FOR STUDENT ANSWER 
SATEvaluation.Controllers={
		global:{
			qst:'',
			answerid:"000",
			qstid:"000",
			count:0,
			pgindex:0,
			tmstart:0,
			totlQ:0,
			timerstatus:0,
			ii:0,
			ansstate:"W",
			questionId:[],
			matchingItem:[],
			points:"Points:0 out of 0"
				
		},
		checkBokTogggleEvent:function(){
			var self=this;
			$('.qst-chk').click(function(e){
				$(this).parent().removeClass("deselected").addClass("selected");
				//self.updateObjectValue($(this).is(":checked"),$(this).attr("id"));
			  	$('input').not(":checked").parent().removeClass("selected").addClass("deselected");
			});
		},
		loadQuestions:function(obj){
			var self=this;
			//self.global.qst=obj;
			self.global.totlQ=obj.length;
			self.global.ii=0;
			SATEvaluation.Business.getQuestions(obj[0].id);
			SATEvaluation.Views.renderAnsStatus(obj);
			self.navigatePage();
			self.global.questionId=obj;
			$("#stdAnsUL").children().first().find("small").removeClass("delite").addClass("heightlite");
			//stdAnstmpl
			/*$("#questionPg").pagination(obj.length, {
				callback:function(page_index){
					self.global.pgindex=page_index;
					SATEvaluation.Business.getQuestions(obj[page_index].questionid);
            	},
                items_per_page:1 // Show only one item per page
            });	*/
			if($("#stdAnsUL li").length != self.global.timerstatus)
				self.startTimer();
		},
		navigatePage:function(){
			var self=this;
			$('.std-ans-state').click(function(event){
				$('.std-ans-state').find("small").removeClass("heightlite").addClass("delite");
				event.preventDefault();
				var $this=$(this);
				$this.find("small").removeClass("delite").addClass("heightlite");
				self.global.pgindex=parseInt($this.attr("index"));
				SATEvaluation.Business.getQuestions($this.attr("id"));
				self.configDragEvent();
			});
		},
		configDragEvent:function(){
			var self=this;
			$( "#sortable" ).sortable({
				 appendTo: "body",
			});
		    $( "#sortable" ).disableSelection();
		    $( "#sortable" ).on( "sortupdate", function( event, ui ) {self.updateElementEvent();} );
		    
		    
			
		},
		updateElementEvent:function(){
			  $("li").removeAttr( 'style' );
			  $("li").css( 'display','inline' );
				$("#questions li").each(function(i){
				  var first=$(this).height();
				  var second=0;
				   $('#sortable li').each(function(k){
				      if(i == k){
				    	  second= $(this).height();
				          if(first>second){
				               $(this).height(first+10);
				          }      
				      }
				   });
				   if(first<second)
				     $(this).height(second);
				 });
		},
		saveStudentAns:function(){
			var self=this;
			
			var studentans=0;
			var i=0;
			var questionid="000";
			var answerid=0;
			
			var ansstatus='W';
			var crt=0;
			var status=false;
			var process="insert";
			var updateid=0;
			
			var attempt=0;
			
			var items= SATEvaluation.Controllers.global.matchingItem;

			// SUBMIT FILL IN THE BLANK DATA
			var crtans="";
			var $elm=$('.custom-input');
			if($elm.length >0){
				status=false;
				$elm.each(function(l){
					var fval=items[l];
					var lval=$(this).val();
					if (fval == $.trim(lval))
						studentans++;
					
					for(var k=0;k<items.length;k++){
						if(items[k] ==  lval){
							crtans+=(k+1);
							break;
						}
					}
				});
				questionid=$('.qstid-li').attr("qstid");
				if($elm.length == studentans )
					ansstatus="C";
				else
					studentans=0;
				
				
				studentans=crtans;
				
			}// SUBMIT MATCH THE FOLLOWING DATA
			else if(items.length>0){
				$('#sortable li').each(function(i){
					var lval= $(this).attr("id");
					if(items[i] == lval)
						studentans++;
					
					for(var k=0;k<items.length;k++){
						if(items[k] ==  lval){
							crtans+=(k+1);
							break;
						}
					}
				});
				
				
				if(items.length == studentans )
					ansstatus="C";
				
				questionid=$('.qst-chk').attr("qstid");
				
				studentans=crtans;
				
			}// SUBMIT TRUE OR FALSE AND OBJECTIVE DATA
			else{
				status=true;
			if($('.qst-chk:checked').length == 0){
				SATEvaluation.Views.showAlertEvent("failed","Please select your answer");
				return;
			}
			
			$('.qst-chk').each(function(){
				i++;
				if($(this).prop("checked")){
					questionid=$(this).attr("qstid");
					answerid=$(this).attr("answerid");
					studentans=i;
				}
			});
			}
			
			var select=self.global.qst;
    		for(var j=0;j<select.length;j++){
    			if(select[0].evaluationid != null){
    				process="update";
    				updateid=select[0].evaluationid;
    				attempt=select[0].attempt;
    			}
    			if(select[j].anscid != "0"){
    				crt=select[j].anscid;
    				status=false;
    				break;
    			}
    		}
    		
    		if(status)
    			crt=2;
    		
    		if(crt == studentans)
				ansstatus='C';
    			self.global.ansstate=ansstatus;
    		
			// MAKE AS STUDENT ANSWER OBJECT
    		attempt++;
			var studentAnsObj={
					answer:studentans,
					questionid:questionid,
					ansstatus:ansstatus,
					process:process,
					updateid:updateid,
					answerid:answerid,
					attempt:attempt
			};
			
			SATEvaluation.Business.saveAnswer(studentAnsObj);
		},
		nextQuestions:function(){
			var self=this;
			var element=$("#stdAnsUL").children();
			var subelement=element.find("a").get((self.global.pgindex));
			self.global.pgindex=parseInt(subelement.getAttribute("index"));
			SATEvaluation.Business.getQuestions(subelement.getAttribute("id"));
			var i=0;
			//element.find("hr").get((self.global.pgindex)+1).show();
			element.find("small").each(function(){
				  if(i == self.global.pgindex){
					  $(this).removeClass("delite").addClass("heightlite");
				  }else
					  $(this).removeClass("heightlite").addClass("delite");
				i++;
			});
		},
		similitude:function (a, b) {
		    for (var i = 0, len = Math.max(a.length, b.length); i < len; i++)
		        if (a.charAt(i) != b.charAt(i)) 
		            return Math.round(i / len * 100);
		        
		    return 100;
		},
		moveToNext:function(){
			var self=this;
			self.updateAnswerState();
			var element=$("#stdAnsUL").children();
			
			
			if((self.global.pgindex)+1 == self.global.totlQ){
				var subelement=element.find("a").get((self.global.pgindex));
				SATEvaluation.Business.getQuestions(subelement.getAttribute("id"));
				$("#submitbtn").text("Save");
				$("#submitbtn").removeClass("btn-success").addClass("btn-primary");
				$('#p-status').fadeTo('slow', 1);
				$('#p-status').fadeTo(5000, 0);
			}else{
				var subelement=element.find("a").get((self.global.pgindex)+1);
				//element.find("hr").get((self.global.pgindex)+1).show();
				self.global.pgindex=parseInt(subelement.getAttribute("index"));
				//SATEvaluation.Business.getQuestions(subelement.getAttribute("id"));
				//console.log("ddddddddddddddddddd ")
				//if(obj[0][0].answerstate != null)
					
					$("#submitbtn").remove();
					$("#nextbtn").show();
					
			}
			$("#description").show();
			/*var i=0;
			element.find("small").each(function(){
			  if(i == self.global.pgindex){
				  $(this).removeClass("delite").addClass("heightlite");
			  }else
				  $(this).removeClass("heightlite").addClass("delite");
			i++;
			});*/
			
			//$('.notification').show();
			/*if(self.global.tmstart == "0")
				self.global.tmstart=self.getTime();*/
			
		},
		startTimer:function(){
			var self=this;
			var sec = 0;
			self.global.tmstart = setInterval(function () {
					$('.clock').text(self.pad(parseInt(sec / 3600, 10))+":"+self.pad(parseInt((sec / 3600)* 60))+":"+self.pad(++sec % 60));
				}, 1000);
		},
		pad:function (val) {
		    return val > 9 ? val : "0" + val;
		},
		updateAnswerState:function(){
			var self=this;
			var element=$("#stdAnsUL").children().find( "a" ).eq(self.global.pgindex);
			if(self.global.ansstate == "C"){	
				element.removeClass("skips");
				element.removeClass("wrong");
				element.addClass("correct");
				element.children().first().removeClass(element.find("i").attr("class")).addClass("fa fa-check i-style");
			}else if(self.global.ansstate == "W"){
				element.removeClass("skips");
				element.addClass("wrong");
				element.children().first().removeClass(element.find("i").attr("class")).addClass("fa fa-times i-style");
			}
		},
		displayResult:function(){
			$(".result").hide();
			$(".modal-footer").hide();
			var self=this;
			var time=$('.clock').text();
			
			clearInterval(self.global.tmstart);
			
			/*if(self.global.tmstart != "0"){
				time=self.calculateTime(self.global.tmstart,self.getTime());
				self.global.lasttime=time;
				self.global.tmstart=0;
			}else{
				time=self.global.lasttime;
			}*/
			// CALCULATE SPENT TIME FOR THE EXAM
			
			var crtdate=self.getcurrentDate();
			// GET STUDENT ANSWER FOR THE RESULT
			var elemnt=$("#tabs").find("div:visible").find("font");

			var data =SATEvaluation.Business.studentResult(elemnt.attr("courselectureid"),crtdate+" "+time);
			renderTemplate("#stdResulttmpl",data,"#questionsDiv");
			
			var crt=0;
			var skip=0;
			var wrg=0;
			var firsttime=0;

			var olddate="- 00:00:00 0 0";
				_.each(data,function(val){
					
					if(val.takentime != null){
						olddate=val.takentime;
					}
					if(val.answerstate == "W"){
						wrg++;
					}
					else if(val.answerstate == "C"){
						crt++;
					}else{
						skip++;
					}
				});
			var skp=skip;
			var wng=wrg;
			
			var ttl=self.global.totlQ;
			var datetime=olddate.split(" ");
			
			var fcrt=parseInt(datetime[2]);
			var fwrng=parseInt(datetime[3]);
			
			if(ttl==crt)
				$('.quizreport').text("Excellent!");
			else if(crt>=(ttl/2)&&crt<ttl)
				$('.quizreport').text("Great!");
			else if(crt>=(ttl/3)&&crt<(ttl/2))
				$('.quizreport').text("Good!");
			else
				$('.quizreport').text("Poor!");
			
			
			self.global.points="Points : "+crt+" out of "+ttl;
			//if(time != "00:00:00"){
			$("#crtqstatus").html("<small style='color: darkgray;font-size: 10px;'>Questions: <strong class='strong'>  "+ttl+"  </strong> |  Answered: <strong class='strong'>" +
					"  "+(crt+wrg)+"  </strong> |  Skipped:<strong class='strong'>  "+skip+"  </strong> |  Correct:<strong class='strong'>  "+crt+"  </strong></small>");
			/*}else{
				$("#crtqstatus").html("<small style='color: darkgray;font-size: 10px;'>Questions: <strong class='strong'>  "+ttl+"  </strong> |  Answered: <strong class='strong'>" +
						"  0  </strong> |  Skipped:<strong class='strong'>  "+skip+"  </strong> |  Correct:<strong class='strong'>  0  </strong></small>");
			}*/
				
			
			$("#firstqstatus").html("<small style='color: darkgray;font-size: 10px;'>Questions:<strong class='strong'>  "+ttl+"  </strong> |  Answered:<strong class='strong'>" +
					"  "+(fcrt+fwrng)+"  </strong> |  Skipped:<strong class='strong'>  "+(ttl-(fcrt+fwrng))+"  </strong> |  Correct:<strong class='strong'>  "+fcrt+"  </strong>  |  1st time correct:<strong class='strong'>  "+fcrt+"  </strong></small>");
			
			 SATEvaluation.Selectors.element.$totalQust.hide();
			  var $default_black = "#666";
			  var $primary_color = "#428bca";
			  var $go_green = "#6ac280";//"#93caa3";
			  var $jet_blue = "#70aacf";
			  var $lemon_yellow = "#ffe38a";
			  var $nagpur_orange = "#fc965f";
			  var $ruby_red = "#e84f4c" ; //"#fa9c9b";
			  
				  var per=0;
				  per=(crt/ttl)*100;
			  		  crt=per.toFixed();
			  	  per=(skip/ttl)*100;
			  		  skip=per.toFixed();
			  	  per=(wrg/ttl)*100;
			  	  	 wrg=per.toFixed();
			  	  per=(fcrt/ttl)*100;
			  	  	firsttime=per.toFixed();
			  	  	
			  	  	
			  	  	
			  	  	
			Morris.Donut({
			    element: 'socialGraph_3',
			    data: [
			      {value:parseInt(crt), label: 'Correct'},
			      {value:parseInt(skip), label: 'Skipped'},
			      {value:parseInt(wrg), label: 'Wrong'},
			    ],
			    labelColor: '#666666',
			    colors: [$go_green, $primary_color, $ruby_red, $jet_blue, $lemon_yellow, $nagpur_orange, $default_black],
			    formatter: function (x) { return x + "%"}
			  });
			
			if(crt >= 80)
				$('.ncomplecturestats').trigger("click");
				//self.completedStatusdata();
			//if(time != "00:00:00")
				$("#crtAverage").text(parseInt(crt)+"%");
			
			$("#firstTime").text(parseInt(firsttime)+"%");
			
		if(wrg != "0" || skip != "0")
			$('.status-bar').html("<center><strong style='color: white;'>"+(wng+skp)+"</strong> <em style='color: white;'>questions remaining!</em> <strong style='color: white;'>Try them again now!</strong></center>");
		else{
			$('.status-bar').html("<center><strong style='color: white;'>Perfect!!!</strong></center>");
			$('#status-sms').text("Very Good!");
		}
		
		
		$(".modify-date").html("<small style='color: darkgray;font-size: 10px;'>Date taken : "+datetime[0]+"	|   Time taken : "+datetime[1]+" </small>");
		$(".modify-date-crnt").html("<small style='color: darkgray;font-size: 10px;'>Date taken : "+crtdate+"	|   Time taken : "+time+" </small>");
		
	},
	getcurrentDate:function(){
		var mnt=['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth(); //January is 0!
		var yyyy = today.getFullYear();
		return  mnt[mm]+'-'+dd+'-'+yyyy;
	},
		calculatSec:function(time_str){
			 // EXTRACT HOURS, MINUTES AND SECONDS
		    var parts = time_str.split(':');
		    // COMPUTE AND RETURN TOTAL SECONDS
		    return parts[0] * 3600 + // AN HOUR HAS 3600 SECONDS
		    parts[1] * 60 + // A MINUTE HAS 60 SECONDS
		    +
		    parts[2]; // SECONDS
		},
		/*getTime:function(){
			var d=new Date();
			var spl=d.toLocaleTimeString().split(" ");
			return spl[0];
		},*/
		calculateTime:function(a,b){
			var self=this;
			var difference = Math.abs(self.calculatSec(a) - self.calculatSec(b));
			// FORMAT TIME DIFFERNECE
			var result = [
			    Math.floor(difference / 3600), // AN HOUR HAS 3600 SECONDS
			    Math.floor((difference % 3600) / 60), // A MINUTE HAS 60 SECONDS
			    difference % 60
			];
			// 0 PADDING AND CONCATATION
			result = result.map(function(v) {
			    return v < 10 ? '0' + v : v;
			}).join(':');
			
			
			return result;
		},
		changePage:function(){
			var self=this;
			var element=$("#stdAnsUL").children().first();
				self.global.pgindex=parseInt(element.find("a").attr("index"));
				SATEvaluation.Business.getQuestions(element.find("a").attr("id"));
				$('.std-ans-state').find("small").removeClass("heightlite").addClass("delite");
				element.find("small").removeClass("delite").addClass("heightlite");
				$(".result").show();
				$(".modal-footer").show();
				SATEvaluation.Selectors.element.$totalQust.show();
				$('.clock').text("00:00");
				if($("#stdAnsUL li").length != self.global.timerstatus)
					self.startTimer();
		},
		updateStdAns:function(object,answer){
			var self=this;
			
			$("#points").text(self.global.points);
			obj=_.toArray(answer);
			$(".st-ans").each(function(k){
				var ansval="Skipped";
				var answerid="";
				var $thiselm=$(this);
				for(var l=0;l<object.length;l++){
					var qid=obj[k][0].questionid;
					if(object[l].questionid  == qid){
						var str=object[l].studentanswer;
						str=str.toString();
						var innerItem=obj[k];
						 sub=_.toArray(innerItem);
						 b=parseInt(str);
						 if(str.length == 1){
							 ansval=sub[(b-1)].answers;
							 
							 answerid=sub[(b-1)].answerid;
							 $thiselm.append("<li id='"+answerid+"'>"+ansval+"</li>");
							// $("#"+answerid).text(ansval);
							 self.addSign(qid,ansval);
							 
							var boo= self.addSign(qid,ansval);
							  if(boo)
								 $('.icon'+qid).html('<i class="fa fa-check" style="font-size: 16pt; color: rgb(116, 235, 116);"></i>');
							  else
								  $('.icon'+qid).html('<i class="fa fa-times" style="font-size: 16pt; color: rgb(255, 65, 65);"></i>');
							  
						 }else{
							 ansval="";
							  var len= str.length;
							  var boo=false;
							  var ln=0;
							  for(var q=0;q<len;q++){
								 var  sln=str.charAt(q);
								 	sln=parseInt(sln);
								  ansval= sub[(sln-1)].answers;
								  answerid=sub[(sln-1)].answerid;
								 $thiselm.append("<li id='"+answerid+"'>"+ansval+"</li>");
								 //$("#"+answerid).text(ansval);
								 if(q == (sln-1)){
								 	boo = true;
								 	ln++;
								 }
								 else	
									boo = false;
							  }
							  
							  if(boo && ln == len)
								 $('.icon'+qid).html('<i class="fa fa-check" style="font-size: 16pt; color: rgb(116, 235, 116);"></i>');
							  else
								  $('.icon'+qid).html('<i class="fa fa-times" style="font-size: 16pt; color: rgb(255, 65, 65);"></i>');
						 }
					 break;	
					}
				}
				
				if($thiselm.children().length == 0)
					$thiselm.append('<label style="color: crimson;font-size: 11pt;">Skipped</label>');
			});
		},
		addSign:function(id,val){
			var status=false;
			$(".sign"+id+" li").each(function(){
			   if($.trim($(this).text()) == val)
				   status= true ;//console.log("EQL");
			   else 
				   status= false;  //console.log("NOT-EQL");
			});
			
			return status;
		}
};

//CONFIGURATION LIMITED SELECTORS
SATEvaluation.Selectors={
	element:{
		$quizModal:$("#questionmodal"),
		$totalQust:$('.total-qst'),
		$modalHeading:$('.modal-title'),
		$oldTyep:$('.old'),
		rowColor:'color:green;',
	}
};

$.fn.shuffleChildren = function() {
    $.each(this.get(), function(index, el) {
        var $el = $(el);
        var $find = $el.children();

        $find.sort(function() {
            return 0.5 - Math.random();
        });

        $el.empty();
        $find.appendTo($el);
    });
};

//HANDLEBAR FUNCTION FOR DYNAMIC IMAGE RENDERER 
Handlebars.registerHelper('set-image', function(val){
	if(val !="-"){
		return "<span>" +
		"<img class='img-polaroid' src="+$('#context').text()+"/OpenFile?r1=serverpath&amp;r2="+val+""+ 
		" style='height: 120px;width: 130px;margin-left: 20px;'>" +
		"<a href='#' style='margin-left: -86px;font-size: 23pt;color: black;'>" +
		"<i class='icon-zoom-in max-image tool-tip' data-toggle='tooltip' src="+$('#context').text()+"/OpenFile?r1=serverpath&amp;r2="+val+" data-original-title='Open New Window'></i>" +
		"</a>" +
		"</span>";
		//return 	"<img class='img-polaroid' src=/ecloudbaseweb/OpenFile?r1=serverpath&amp;r2="+val+""+ 
			//	" style='height: 200px;width: 250px;margin-left: 20px;max-height: 400px;max-width: 500px;margin-bottom: 7px;'>" ;
	}
});
// HANDLEBAR FUNCTION FOR DYNAMIC BUTTON RENDERER
Handlebars.registerHelper('dynamic-btn', function(val,answerstate){ 
	if(answerstate != "C" ){
	if(val == null)
		return "<span class='btn btn-flat btn-success' id='submitbtn' onClick=SATEvaluation.Controllers.saveStudentAns() style='width:100px;margin-right: 5px;'>Submit</span><span class='btn btn-flat btn-danger' id='nextbtn' onClick=SATEvaluation.Controllers.nextQuestions() style='width:100px;display:none'>Next</span>";
	else
		return "<span class='btn btn-flat btn-primary' id='submitbtn' onClick=SATEvaluation.Controllers.saveStudentAns() style='width:100px;margin-right: 5px;'>Save</span><span class='btn btn-flat btn-danger' id='nextbtn' onClick=SATEvaluation.Controllers.nextQuestions() style='width:100px;display:none'>Next</span>";
	}
	return "";
});

//HANDLEBAR FUNCTION CHECKED FOR PREVIOUS ANSWER
Handlebars.registerHelper('select-me', function(val){ 
	SATEvaluation.Controllers.global.count++;
	if(val == SATEvaluation.Controllers.global.count)
		return 	"checked";
	
	return "";
});
//HANDLEBAR FUNCTION FOR DYNAMIC BUTTON RENDERER
Handlebars.registerHelper('answer-status', function(val){ 
	if(val == "W")
		return "<li style='display: inline;line-height: 20px;'><a href='#' class='stado wrong'><i class='fa fa-times' style='color: white;font-size: 16px;'></i></a></li>";
	else if(val == "C")
		return "<li style='display: inline;line-height: 20px;'><a href='#' class='stado correct'><i class='fa fa-check' style='color: white;font-size: 16px;'></i></a></li>";

	return "<li style='display: inline;line-height: 20px;'><a href='#' class='stado skips'><i class='icon-question-sign' style='color: white;font-size: 16px;'></i></a></li>";
});
//HANDLEBAR FUNCTION FOR DYNAMIC BUTTON RENDERER
Handlebars.registerHelper('std-status', function(val){
	
	if(val == "W"){
						return "<li style='display: inline;line-height: 20px;'><a href='#' index='"
								+ (SATEvaluation.Controllers.global.ii++)
								+ "' id='"
								+ this.id
								+ "' class='std-ans-state wrong'><i class='fa fa-times i-style' ></i><br><small  class='delite'>"+SATEvaluation.Controllers.global.ii+"</small></a></li>";
	}
	else if(val == "C"){
		SATEvaluation.Controllers.global.timerstatus++;
						return "<li style='display: inline;line-height: 20px;'><a href='#' index='"
								+ (SATEvaluation.Controllers.global.ii++)
								+ "' id='"
								+ this.id
								+ "' class='std-ans-state correct'><i class='fa fa-check i-style'></i><br><small  class='delite'>"+SATEvaluation.Controllers.global.ii+"</small></a></li>";
	}

					return "<li style='display: inline;line-height: 20px;'><a href='#' index='"
							+ (SATEvaluation.Controllers.global.ii++)
							+ "' id='"
							+ this.id
							+ "' class='std-ans-state skips'><i class='fa fa-question i-style' ></i><br><small  class='delite'>"+SATEvaluation.Controllers.global.ii+"</small></a></li>";
});
//HANDLEBAR FUNCTION FOR DYNAMIC BUTTON RENDERER
Handlebars.registerHelper('answer', function(items,options){ 
	  var object=[];
	  var li="";
	  for (var j=0;j<items.length;j++){
		  object.push({correctanswer:items[j].answers});
		  var val1=items[j].anscid;
		  var val2=items[j].answers;
		  var id=items[j].id;
		  
		  if(id == "3" || id == "4"){
				if(val1 != "111"){
					li+="<li>"+options.fn(items[j])+"</li>";
				}
			}else
			if(val2 == "False"){
				if(val1 == "0")
					li+="<li>"+options.fn(items[j])+"</li>";
			}else if(val2 == "True"){
				if(val1 == "1"){
					li+="<li>"+options.fn(items[j])+"</li>";
				}
			}else{
				if(val1 != "0")
					li+="<li>"+options.fn(items[j])+"</li>";
			}
	  }
	  
	  return li;
});

Handlebars.registerHelper('question-helper', function(val){ 
	return val.replace(new RegExp("courseData.getContextPath()", 'g'),courseData.getContextPath());
});

Handlebars.registerHelper('match-question', function(question, options) {
	var items=question.split("%J@A#C&K~");
	var slno=1;
	 var object=this;
	 var out='';
	 for (var i=0;i<object.length;i++){
		 var ans=object[i].answers;
		 var qst="";
		 ans=ans.replace(new RegExp("courseData.getContextPath()", 'g'),courseData.getContextPath());
		 if(items.length > i){
			 qst=items[i];
			 qst=qst.replace(new RegExp("courseData.getContextPath()", 'g'),courseData.getContextPath());
		 }
		 out+= '<li><div class="col-md-12 match-container" ><div class="col-md-6">'+slno+'. '+qst+'</div><div class="col-md-6">'+ans+'</div></div></li>';
		 slno++;
	 }
	 return out;
});
Handlebars.registerHelper('answer-description', function(obj){
	var desc="-";
			for(var m=0;m<obj.length;m++){
				if(obj[m].answerDescription != null){
					
					desc=obj[m].answerDescription;
					break;
				}
			}
	return desc.replace(new RegExp("courseData.getContextPath()", 'g'),courseData.getContextPath());
});
Handlebars.registerHelper('ifEquals', function(id,options) {
	if(id == '4')
	     return options.fn(this);
	  
	  return options.inverse(this);
	});
Handlebars.registerHelper('ifEqualSEC', function(id,options) {
	if(id == '3')
	     return options.fn(this);
	  
	  return options.inverse(this);
	});

Handlebars.registerHelper('match-question', function(question, options) {
	var items=question.split("%J@A#C&K~");
	  var out = "";
	  for (var j=0;j<items.length;j++){
		  var val=items[j];
		  out = out + "<li>"+ val.replace(new RegExp("courseData.getContextPath()", 'g'),courseData.getContextPath())+"</li>";
	  }
	  return out;
	});

Handlebars.registerHelper('student-ans', function() {
  return "A";	
});

