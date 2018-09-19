/**
 * SELF ASSESSMENT TEST
 * sat.js
 * @Copyright 2014-2015 iGrandee pvt.ltd.
 * @author selvarajan_j
 * @contact selvarajan_j@igrandee.com,jjjackpterson@gmail.com
 * 
 * Wed Feb 18 2015 5:15 
 */
/**
 * SET BLOBAL VARIABLES
 */
/*var Gloable=function(){
	return {
		courselectureid:"-"
	};
};*/
/**
 * DELEGATES SETUPS  
 */
/*var Controllers=function(){
	var context=courseData.getContextPath();
 return {
	 	*//**
		 * CONNECT DELEGATES OBJECT FOR GET DATA
		 * @Param String 
		 * @returns JSONData
		 *//*
	 	connectDelegate:function(extension){
	 	  return Ajax.get(context+"/rest/connector"+extension,null,"json","application/json");
		},
		*//**
 		 * CONNECT DELEGATES OBJECT FOR SAVE QUESTION DATA
 		 * @param String
 		 * @param Object
 		 * @returns String
 		 *//*
 		saveDelegateData:function(url,object){
			return Ajax.post(context+url, object,"json","application/json");
 		},
		*//**
		 * CONNECT DELEGATES BOJECT FOR QUESTION DELETING
		 * @param String Id
		 * @param String Status
		 *//*
		deleteDelegateData:function(questionid){
			return Ajax.get(context+"/rest/sat/delete?questionid="+questionid,null,"json","application/json");
		},
		*//**
		 * CONNECT DELEGATES OBJECT FOR BULK QUESTION UPLOAD
		 * @parm Object
		 * @return String
		 *//*
		savebulkDelegate:function(object){
			return Ajax.post(context+"/rest/connector/saveBulkQst",object,"json","application/json");
		},
		*//**
		 * CONNECT DELEGATES OBJECT FOR EXAM
		 * @param String id
		 *//*
		examDelegate:function(extension,object){
			return Ajax.post(context+"/rest/exam"+extension,object,"json","application/json");
		}
	 };
};*/
/**
 * TO HANDLED TYPE OF EVENTS. 
 */

var EventHandler=function(){
	/**
	 * CREATE DELEGATE INSTANCE
	 */
		 //var controllers=new Controllers();
		 var typeId="1";
		 var courselectureid="000";
		 var questionid="000";
		 var deleteAnswer=[];
		 var answerid=[];
		 var questions=[];
		 var description="-";
		 var toggle=true;
		 var wboardOnce=true;
		 var mark="1";
		 var editExamDivid="";
		 
	 
		/**
		 * CONFIG QUESTION FUNCTION SELECTORS
		 * @returns selectors of element
		 */
		qstSelectors=function(){
			return{
					$select		:$('.breadcrumb select'),
					$toggleBtn	:$('.tgl'),
					$toggleElm	:$('.toggle-elm'),
					$header		:$('.questionHeader'),
					
					$Btn		:$("#loadqtBtn")
				};
		};
		globalSelectors=function(){
			return {
				$summernote		:$('.summernote'),
				$dessummernote	:$('.description .summernote')
			};
		};
		objSelectors=function(){
			return {
				$olisummer	:$(".ol-list .summernote"),
				$oksign		:$('.glyphicon-ok-sign'),
				$removesign	:$(".ol-list .glyphicon-remove-sign")
			};
		};
		 $(document).on('click', '.cancel-btn', function(e){
				e.preventDefault(); 
		 		var id=$(this).parent().find("input").attr("answerid");
				 answer=$(this).parent().find("input").val();
				 if(id != undefined)
				 deleteAnswer.push({id:id,answer:answer});
			 $(this).parent().remove();
		 });
		 htmlToPlaintext=function(text) {
			  return text ? String(text).replace(/<[^>]+>/gm, '') : '';
			};
		 	 
		
// START EXTERNAL METHODS ACCESS		
	 /**
	  * SAVE NEW QUESTIONS IN TO THE TABLE
	  */
	 	var validateData = function(){
	 		var answer_arr=[];
			var crtanswer_arr=[];
			var $this=globalSelectors();
				mark="1";
				if($this.$summernote.length>0 && typeId != "4" && typeId != "3" && typeId!='2-0'){
					if(htmlToPlaintext($this.$summernote.code()).replace(/&nbsp;/g, '').trim().length <= 0){
						templates.showAlert("failed","Question can not be empty");
						return;
					}
					if(htmlToPlaintext($this.$summernote.code()).replace(/&nbsp;/g, '').trim().length >= 2000){
						templates.showAlert("failed","Question should not exceed 2000 characters");
						return;
					}
			}
	 		data=null;
	 		 if(typeId == '1'){	// VALIDATE TRUE / FALSE DATA TYPE TEMPLATE
	 			 answerid=[$('a[data-toggle=tf]:first').attr("answerid"),$('a[data-toggle=tf]:last').attr("answerid")];
	 			 data=generateObject(['True','False'],[$("#tf").val(),$("#tf").val()]);
	 		 	}
	 			else if(typeId == '2'){	// VALIDATE OJECTIVE DATA TYPE TEMPLATE
	 				 answer_arr=[];
	 				var $thisselect=objSelectors();
	 					if($thisselect.$olisummer.length == 1){
	 						templates.showAlert("failed","Minimum two options are required");
	 						return;
	 					}
	 					answerid=[];
	 					$thisselect.$olisummer.each(function(){
	 						var answers=$(this).code();
	 							if(answers.length == 0){
	 								templates.showAlert("failed","Options can not be empty");
	 								return;
	 							}else{
	 									answer_arr.push(answers);
	 									var ids=$(this).parent().attr("class");
	 										ansid=$('.'+ids).find("a").attr("answerid");
	 									if(ansid != undefined)
	 										answerid.push(ansid);
	 									else
	 										answerid.push("-");
	 								}
	 					});
	 					if($thisselect.$oksign.length == 0){
	 						templates.showAlert("failed","Please selecte a correct answer");
	 						return;
	 					}
	 					
	 					var i=0;
	 					$thisselect.$removesign.each(function(){
	 						var ss=$(this).attr("class");
	 						var arr=ss.split(" ");
	 						if(arr[arr.length-1] == "glyphicon-ok-sign")
	 							crtanswer_arr.push((i)+1);
	 						else
	 						   crtanswer_arr.push(null);
	 						i++;
	 					});
	 					 data=generateObject(answer_arr,crtanswer_arr);
	 				}
		 			 else if(typeId == '3'){// VALIDATE FILL IN THE BLANK DATA TYPE TEMPLATE
		 				 
		 				answerid=[];
		 				var $dyelm=$('#dyncamicItem');
		 				if($dyelm.children().length == 0){
		 					templates.showAlert("failed","Question can not be empty");
		 					return;
		 				}
		 				var status=false;
		 				var $element=$('#dyncamicItem input');
		 				var val="";
		 					
		 				if($element.length == 0){
		 					templates.showAlert("failed","Add blank element");
		 					return;
		 				}
		 				
		 				var $questionele = $('.blank');
		 				if($questionele.text().length == 0){
		 					templates.showAlert("failed","Add Question");
		 					return;
		 				}
		 				
		 				var i=0;
		 				$element.each(function(){
		 						val=$(this).val();
		 					if(val.length != 0){
		 						answer_arr.push(val);
		 						crtanswer_arr.push(i++);
		 					}else
		 						status=true;
		 							
		 					if($(this).attr("answerid") != undefined)
		 						answerid.push($(this).attr("answerid"));
		 					else
		 						answerid.push("-");
		 					
		 				});
		 				if(status){
		 					templates.showAlert("failed","Answer can not be empty");
		 					return;	
		 				} /*else if(i<2){
		 					self.showAlertEvent("failed","Answer must be minimum two");
		 					return;
		 				}*/
		 				var $choiceelm=$('#choice input');
		 				if($element.length == 1){
		 					if($choiceelm.length == 0 ){
		 						templates.showAlert("failed","Your answer not enough for this question. Please add choice atleast one");
		 						return;
		 					}
		 				}
		 				var innerstatus=false;
		 				$choiceelm.each(function(i){
		 					val=trimText($(this).val());
		 					if(val.length != 0){
		 						answer_arr.push(val);
		 						crtanswer_arr.push(111);
		 						innerstatus=false;
		 					}else
		 						innerstatus=true;
		 					
		 					if($(this).attr("answerid") != undefined)
		 						answerid.push($(this).attr("answerid"));
		 					else
		 						answerid.push("-");
		 				});
		 				
		 				if(innerstatus){
		 					templates.showAlert("failed","Choice can not be empty");
		 					return;
		 				}
		 				if($dyelm.attr("questionid") != undefined)
		 					questionid=$dyelm.attr("questionid"); 
		 				$dyelm.find("a").remove();
		 				$( ".fill-margin" ).removeAttr('contentEditable');
		 					$element.prop('readonly', true);
		 					$element.css('background-color', 'white');
		 					$element.removeAttr("answerid");
		 					$dyelm.removeAttr("questionid");
		 					data=generateObjectOther($dyelm.html(),answer_arr,crtanswer_arr);
		 			 }	
		 			 else if(typeId == '4'){
		 				 if($(".match-container").length>=2){
		 					answerid=[];
			 				var qst="";
			 				var sortItem=[];
			 				/*if(htmlToPlaintext($this.$summernote.code()).replace(/&nbsp;/g, '').trim().length <= 0){
								templates.showAlert("failed","Question can not be empty");
								return;
							}*/
							/*if(htmlToPlaintext($this.$summernote.code()).replace(/&nbsp;/g, '').trim().length > 2000){
								templates.showAlert("failed","Question should not exceed 2000 characters");
								return;
							}	*/					
			 				$(".match-container").each(function(){
			 				     $this=$(this);
			 				     var qstVal=$this.find("div:eq(0)").html();
			 				     var ans=$this.find("div:eq(1)").html();
			 				     if(qstVal.length != 0 ){
			 				    	 answer_arr.push( ans );
			 				    	qst+=qstVal+"%J@A#C&K~";
			 				     }
			 				     else
			 				    	 sortItem.push(ans);
			 				    
			 				     
			 				     if(qstVal.length !=0 )
	 								crtanswer_arr.push(111);
			 				     else
	 								crtanswer_arr.push(0);
			 				     
			 				    if($(this).attr("answerid")!= undefined)
	 								answerid.push($(this).attr("answerid"));
	 							else
	 								answerid.push("-");
			 				});
			 				
			 				for(var k=0;k<sortItem.length;k++)
			 					answer_arr.push(sortItem[k]);
			 					
			 				qst=qst.substring(0,qst.length-9);
			 						data=generateObjectOther(qst,answer_arr,crtanswer_arr);
		 				 }
		 				 else if($("#saveBtn").text()=="Update"){
		 					answerid=[];
			 				var qst="";
			 				var sortItem=[];
			 				/*if(htmlToPlaintext($this.$summernote.code()).replace(/&nbsp;/g, '').trim().length <= 0){
								templates.showAlert("failed","Question can not be empty");
								return;
							}*/
							if(htmlToPlaintext($this.$summernote.code()).replace(/&nbsp;/g, '').trim().length >= 2000){
								templates.showAlert("failed","Question should not exceed 2000 characters");
								return;
							}						
			 				$(".match-container").each(function(){
			 				     $this=$(this);
			 				     var qstVal=$this.find("div:eq(0)").html();
			 				     var ans=$this.find("div:eq(1)").html();
			 				     if(qstVal.length != 0 ){
			 				    	 answer_arr.push( ans );
			 				    	qst+=qstVal+"%J@A#C&K~";
			 				     }
			 				     else
			 				    	 sortItem.push(ans);
			 				    
			 				     
			 				     if(qstVal.length !=0 )
	 								crtanswer_arr.push(111);
			 				     else
	 								crtanswer_arr.push(0);
			 				     
			 				    if($(this).attr("answerid")!= undefined)
	 								answerid.push($(this).attr("answerid"));
	 							else
	 								answerid.push("-");
			 				});
			 				
			 				for(var k=0;k<sortItem.length;k++)
			 					answer_arr.push(sortItem[k]);
			 					
			 				qst=qst.substring(0,qst.length-9);
			 						data=generateObjectOther(qst,answer_arr,crtanswer_arr);
		 				 }
		 				 else{
		 					templates.showAlert("failed","Question should be greater than 1");
							return;
		 				 }
		 				
		 			 }	// VALIDATE MATCH THE FOLLOWING DATA TYPE TEMPLATE
		 			 else if(typeId == '5'){	// VALIDATE DESCRIPTIVE DATA TYPE TEMPLATE
		 				var mrk=$.trim($("#mark").val());
		 				if(mrk.length == 0 ){
		 					templates.showAlert("failed","Mark field can not be empty");
		 					return;
		 				}
		 				mark=mrk;
		 				var answers=$(".answerdiv .summernote").code();
		 				 if(answers.length == 0){
		 					templates.showAlert("failed","Answer can not be empty");
		 					return ;
		 				 }else
		 					answer_arr.push(answers);
		 				 
		 				crtanswer_arr=[1];
		 				data=generateObject(answer_arr,crtanswer_arr);
		 			 } else if (typeId == '2-0'){
		 				var arry=["A","B","C","D","E","F","G","H","I"];
		 				if(!$(".chk-self").is(":checked")){
		 					templates.showAlert("failed","Nothing has been selected");
		 					return;
		 				}
		 				
		 				var qstobject=[];
		 				var ansobject=[];
		 				var optionObject=[];
		 				var crtans="";
		 				$('#qstSheetTabl tr').each(function(num){
		 					if($(this).eq(0).find('input:checked').attr("status") == "true" && !$(this).eq(0).find('input:checkbox').is(":disabled")){  
		 						var i=0;
		 						crtans="";
		 							for(i;i<arry.length;i++){
		 							  if(arry[i]== $(this).find("td").eq(2).text()){
		 								  crtans+=(i+1)+",";
		 							  }else
		 								  crtans+="0,";
		 							}
		 						var str="";
		 						$(this).find("li").each(function(){
		 							str+=$(this).text()+",";
		 						});
		 						ansobject.push(crtans);
		 						optionObject.push(str);
		 						qstobject.push($(this).find("td").eq(1).text());
		 						
		 					 }
		 				});
		 				
		 				if(qstobject.length == 0){
		 					templates.showAlert("failed","Nothing has been selected");
		 					return;
		 				}

		 				var object={qstobject:qstobject,ansobject:ansobject,optionObject:optionObject,courselectureid:courselectureid,entryType:"ecloud"};
		 				var result=controllers.savebulkDelegate(object);
		 				if(result!="INTERNAL_SERVER_ERROR"){
		 		 			templates.showAlert("success","Question has successfully saved");
		 		 			setTimeout(function(){ 
		 		 				$('.back-btn').trigger('click');
		 		 			}, 2000);		 		 			
		 		 			$('#qstSheetTabl tr').each(function(num){
		 						$(this).eq(0).find('input:checked').prop("disabled",true);
		 					});
		 		 			//resetFields();
		 		 		}
		 		 		else
		 		 			templates.showAlert("failed","Question can not save");
		 				
		 				typeId="2";
		 				return;
		 			 }
		 						 
	 		var url="/rest/connector";
	 		if($("#saveBtn").text() != "Save")
	 			url="/rest/sat/update";
	 		
	 		var result=controllers.saveDelegateData(url,data);
	 		if(result!="INTERNAL_SERVER_ERROR"){
	 			templates.showAlert("success","Question has successfully saved");
	 			resetFields();
	 			$("#saveBtn").text('Save');
	 		}
	 		else
	 			templates.showAlert("failed","Question can not save");
	 	 };
	 	 /**
		  * DELETE SELECTED QUESTIONS
		  * @param String question Id
		  */
		var confirmDelete=function(id){
			var data=controllers.deleteDelegateData(id);
			if(data == "ACCEPTED")
				loadQuestion();
			else
				templates.showAlert("failed","Question can not save");
		 };	 
		 /**
		  * RESET ALL ENTRY FIELDS 
		  */
		 var resetFields=function(){
			 var $this=globalSelectors();
				 $this.$summernote.code('');
				 $this.$dessummernote.code('');
			 description="";
			 answerid=[];
			 deleteAnswer=[];
			 questions=[];
			 
			 if(typeId == "1"){ // ONLY TRUE / FALSE TYPE FIELD ACCESS
				$('#tf').prop('value', "1");
			 	$('a[data-toggle=tf]').not('[data-title=1]').removeClass('btn-blue');
			 	$('a[data-toggle=tf][data-title=1]').addClass('btn-blue');
			 }else if(typeId == "2"){
				 $('.glyphicon-remove-sign').removeClass('glyphicon-ok-sign');
			 } // ONLY OBJECTIVE TYPE FIELD ACCESS
			 	else if(typeId == "3"){
			 		$('#choice').children().remove();
					$('#dyncamicItem').children().remove();
			 	} // ONLY FILL IN THE BLANK TYPE FIELD ACCESS
			 		else if(typeId == "4"){
			 			$(".match-container").remove();
			 			$(".questionentry").hide();
			 			$(".cancelbtn").show();
			 		} // ONLY MATCH THE FOLLOWING TYPE FIELD ACCESS
			 			else if(typeId == "5"){} // ONLY DISCRIPTIVE TYPE FIELD ACCESS
		 };	 
// END		 
// START INTERNAL METHODS ASSCESS 		 
		
		 /**
		 * LOAD TEMPLATE TYPES BASED ON QUESTION TYPES
		 */
		 loadEntryForm=function(formId){
			 if(formId == "1"){
				 templates.tftEntryForm(); // LOAD TRUE / FALSE TYPE TEMPLATE
				 configSummernote("summernote-container"); // LOAD SUMMERNOTE FOR QUESTION ENTRY
				 configtftEvent();
			 	}else if(formId == "2"){
			 		templates.objtEntryForm(); // LOAD OBJECTIVE TYPE TEMPLATE
			 		configSummernote("summernote-container"); // LOAD SUMMERNOTE FOR QUESTION ENTRY
			 		configobjEvent(); 
			 		}else if(formId == "3"){
			 			templates.fitbtEntryForm(); // LOAD FILL IN THE BLANK TYPE TEMPLATE
			 			configFillBlank();
			 			}else if(formId == "4"){
			 					templates.mtftEntryForm(); // LOAD MATCH THE FOLLOWING TYPE TEMPLATE
			 					configSummernote("summernote-container"); // LOAD SUMMERNOTE FOR QUESTION ENTRY
			 					configDragEvent();
			 					$(".questionentry").hide();
			 				}else if(formId == "5"){
			 					templates.destEntryForm(); // LOAD DESCRIPTIVE TYPE TEMPLATE
			 					configSummernote("summernote-container"); // LOAD SUMMERNOTE FOR QUESTION ENTRY
			 					configDescription();
			 					}else if(formId == "exam"){
			 						templates.examForm();
			 						configSummernote("summernote-container"); // LOAD SUMMERNOTE FOR QUESTION ENTRY
			 						expaper.configExamEvent();
			 						}
			 /**
			  * CONFIG ANSWERE DESCRIPTION TEMPLATE
			  */
			 $("#openDescr").one("click",function(){
				 configSummernote("description");
				 var $this=globalSelectors();
				 $this.$dessummernote.code(stringReplace(description));
			 });
		 };
		 /**
		  * EDIT QUESTION 
		  * @param object 
		  * @param int type id
		  */
		   editQuestions=function(object){

			   loadEntryForm(typeId);
			   deleteAnswer=[];
			   $("#saveBtn").text("Update");
			   var $this=globalSelectors();
			   $this.$summernote.code(stringReplace(object[0].question));
			   questionid=object[0].questionid;
			   /**
			    * GET ANSWER DESCRIPTION FOR CORRECT ANSWER
			    */
			   for(var i=0;i<object.length;i++){
				     if(object[i].answerDescription != null){
						description=object[i].answerDescription;
						break;
				     }
			   }
			   if(typeId == "1"){ // ONLY TRUE / FALSE TYPE FIELD ACCESS
				   	$('#tf').prop('value', object[0].correctans);
					$('a[data-toggle=tf]').not("[data-title="+object[0].correctans+"]").removeClass('btn-blue');
					$("a[data-toggle=tf][data-title="+object[0].correctans+"]").addClass('btn-blue');
					$('a[data-toggle=tf]:first').attr("answerid",object[0].answerid);
					$('a[data-toggle=tf]:last').attr("answerid",object[1].answerid);
				 }else if(typeId == "2"){// ONLY OBJECTIVE TYPE FIELD ACCESS
					 for(var i=1;i<object.length;i++)
							$( "#moreoption" ).trigger( "click" );
					 var j=0;
						$(".remove-qst").each(function(){
							$(this).attr("answerid",object[j].answerid);
							var clsid=$(this).attr("classid");
							var $elm =$("."+clsid);
							$elm.find("div").code(stringReplace(object[j].answers));
							j++;
						});
						j=0;
						$(".glyphicon-remove-sign").each(function(){
							j++;   
							if(j == parseInt(object[j-1].correctans)){
								$(this).addClass("glyphicon-ok-sign");
							}
						});
				 } 
				 else if(typeId == "3"){ // ONLY FILL IN THE BLANK TYPE FIELD ACCESS
				 		$('#dyncamicItem').html(object[0].question);
				 		var fill='<a href="#" style="margin-left:1px" data-toggle="tooltip" data-original-title="Delete" class="cancel-btn "><i class="glyphicon glyphicon-remove-sign "></i></a>';
						$(".blank").append(fill);
						$(".fill").append(fill);
						
						$( "#dyncamicItem .fill-margin" ).prop('contentEditable',true);
						var $custominput=$('.custom-input');
						$custominput.prop('readonly', false);
						var i=0;
						$('.custom-input').each(function(){
							$(this).attr("answerid",object[i].answerid);
							$(this).val(object[i].answers);
							i++;
						}); 
						var m=$custominput.length;
						if(m == 1 || m < object.length){
							var j=0;
							for(var i=m;i<object.length;i++){
								$("#choice").append('<span class="fill"><input type="text" class="custom-input fill-margin" maxlength="500" placeholder="@ Fill your answer" answerid="'+object[i].answerid+'" ><a href="#" data-toggle="tooltip" data-original-title="Delete" class="cancel-btn "><i class="glyphicon glyphicon-remove-sign"></i></a></span>');
								$("#choice input")[j].value=object[i].answers;
								j++;
							}
						}
						$('#dyncamicItem').attr("questionid",object[0].questionid);
				 	} 
				 	else if(typeId == "4"){ // ONLY MATCH THE FOLLOWING TYPE FIELD ACCESS
						templates.edtMfTemplate(object);
						 $this.$summernote.code('');
				 }else if(typeId == "5"){
					 $("#mark").val(object[0].mark);
					 $(".answerdiv .summernote").code(object[0].answers);
					 answerid.push(object[0].answerid);
				 } // ONLY DISCRIPTIVE TYPE FIELD ACCESS
		   };
		/**
		 * GENERATE QUESTION DATA AS OBJECT 
		 * @param array[]
		 * @param array[]
		 */
		generateObject=function(answer,correctAnswer){
			var $this=globalSelectors();
			return object={
					question:stringReplace($this.$summernote.code()),
					answer:answer,
					correctAnswer:correctAnswer,
					difficultyfactor:2,
					questiontypeid:typeId,
					noofoccurance:0,
					lifetime:null,
					answerDescription:stringReplace($this.$dessummernote.code()),
					courselectureid:courselectureid,
					questionid:questionid,
					deleteAnswer:deleteAnswer,
					answerid:answerid,
					mark:mark,
					entryType:"ecloud"
			};
		};
		/**
		 * GENERATE QUESTION OBJECT
		 * @param array[]
		 * @param array[]
		 */
		generateObjectOther=function(questions,answer,correctAnswer){
			var $this=globalSelectors();
			return object={
					question:stringReplace(questions),
					answer:answer,
					correctAnswer:correctAnswer,
					difficultyfactor:2,
					questiontypeid:typeId,
					noofoccurance:0,
					lifetime:null,
					answerDescription:stringReplace($this.$dessummernote.code()),
					courselectureid:courselectureid,
					questionid:questionid,
					deleteAnswer:deleteAnswer,
					answerid:answerid,
					mark:mark,
					entryType:"ecloud"
			};
		};
		 /**
		  * REPLACE STRING FOR IMAGE UPLOAD
		  */
		 stringReplace=function(val){
				if(val!= undefined)
					return val.replace(/courseData.getContextPath/g, courseData.getContextPath());
				return "-";
		};
		/**
		  * CONFIG TRUE / FALSE TYPE TEMPLATE EVENTS
		  */
		 configtftEvent=function(){
			 $('.radioBtn a').on('click', function(){
					var sel = $(this).data('title');
					var tog = $(this).data('toggle');
					$('#'+tog).prop('value', sel);
					$('a[data-toggle="'+tog+'"]').not('[data-title="'+sel+'"]').removeClass('btn-blue');
					$('a[data-toggle="'+tog+'"][data-title="'+sel+'"]').addClass('btn-blue');
			 });
		 };
		 /**
		  * CONFIG FILL IN THE BLANK SELECTORS
		  */
		 configFillBlank=function(){
			 deleteAnswer=[];
			 
			 var $addItem=$('.add-item');
			 var $dynamicItem=$("#dyncamicItem");
			 var $area=$('#area');
			 var $input=$('.add-input');
			 var $choice= $('.choice-btn');
			 $area.keyup(function(){
					el = $(this);
					if(el.val().length >= 2001){
						el.val( el.val().substr(0, 2000) );
					} 
				});
			 	$addItem.click(function(){
			    	var l=$.trim($area.val()).length;
			    	if(l != 0){
			    		if(l<=2000){
			    		$dynamicItem.append('<span class="blank"><span class="fill-margin" contenteditable><b>'+trimText($('#area').val())+'</b></span><a href="#" style="margin-left:2px" data-toggle="tooltip" data-original-title="Delete" class="cancel-btn"><i class="glyphicon glyphicon-remove-sign "></i></a></span>');			    		
			    		$area.val('');
			    		}
			    		else{
			    			templates.showAlert("failed","Question should not exceed 2000 characters");
			    		}
				  }else
			    		templates.showAlert("failed","Question can not be empty");
			    });
			    var elem='<span class="fill"><input type="text" class="custom-input fill-margin" maxlength="500" placeholder="@ Fill your answer"><a href="#" data-toggle="tooltip" data-original-title="Delete" class="cancel-btn "><i class="glyphicon glyphicon-remove-sign"></i></a></span>';
			 	$input.click(function(){
			 		$dynamicItem.append(elem);
			 		
			    });
			 	$choice.click(function(){
			    	$("#choice").append(elem);
			    });
			 	
		 };
		 /**
		  * CONFIG MATCH THE FOLLOWING SELECTORS
		  */
		 configDragEvent=function(){
			 var $qstsummer=$("#qstsummer .summernote");
			 var $anssummer=$("#anssummer .summernote");
			 var $questiondiv=$("#questiondiv");
			 var $edtElm=null;
			 var $addpreview=$("#addpreview");
			 $addpreview.click(function(){
				 if(htmlToPlaintext($anssummer.code()).replace(/&nbsp;/g, '').trim().length == 0){
					 if(htmlToPlaintext($qstsummer.code()).replace(/&nbsp;/g, '').trim().length == 0){
						 templates.showAlert("failed","Question can not be empty");
						 return;
					 }else{
						 templates.showAlert("failed","Answer can not be empty");
						 return;
					 }
				 }
				 if(htmlToPlaintext($qstsummer.code()).replace(/&nbsp;/g, '').trim().length >= 2000){
						templates.showAlert("failed","Question should not exceed 2000 characters");
						return;
					}
				 if(htmlToPlaintext($anssummer.code()).replace(/&nbsp;/g, '').trim().length >= 2000){
					 templates.showAlert("failed","Answer should not exceed 2000 characters");
						return;
				 }
				 if($(this).text() == "Update Preview"){
					$edtElm.find("div:eq(0)").html($qstsummer.code());
					$edtElm.find("div:eq(1)").html($anssummer.code());
					$(".questionentry").show();
					 $(".cancelbtn").hide();
				 }else{
				 var btn='<div class="col-md-12 borderbottom1"><a class="btn btn-danger pull-right matchbtn" style="padding-top: 1px;padding-bottom: 3px;padding-right: 6px;padding-left: 6px;" title="Delete Question">'+
							'<i class="fa fa-trash"></i>'+
						'</a>'+
						'<a class="btn btn-primary pull-right matchbtn" style="padding-top: 2px;padding-bottom: 2px;padding-right: 3px;margin-right: 3px;"  title="Edit Question" >'+
							'<i class="fa fa-pencil"></i>'+
						'</a></div><hr>';
				 
				 console.log("$qstsummer.code() "+$qstsummer.code());
				var elms='<div class="col-md-12 match-container"><div class="col-md-6">'+$qstsummer.code()+'</div><div class="col-md-6">'+$anssummer.code()+'</div>'+btn+'<br></div>';
				 //if($qstsummer.code().length != 0)
					 $questiondiv.append(elms);
				 /*else{
					 $('#questiondiv div:last').before( elms );
				 }*/
					 $(".questionentry").show();
					 $(".cancelbtn").hide();
				 }
				 $addpreview.text("Add Preview");
				 $qstsummer.code('');
				 $anssummer.code('');
				
			 });
			 $questiondiv.on("click",".matchbtn",function(){
				 if($(this).children().attr("class") == "fa fa-pencil"){
					 $edtElm=$(this).parent().parent();
					 $qstsummer.code($edtElm.find("div:eq(0)").html());
					 $anssummer.code($edtElm.find("div:eq(1)").html());
					 $addpreview.text("Update Preview");
				 }else{
					 var $parent=$(this).parent().parent();
					 if($parent.attr("answerid")!= undefined)
						 deleteAnswer.push({id:$parent.attr("answerid"),answer:$parent.find("div:eq(1)").html()});
						
					 $parent.remove();
				 }
			 });
		 };
		 /**
		  * CONFIG EXAM EVENT MANAGEMENT
		  */
		/* configExamEvent=function(){
			 //var delbtn='<i class=" glyphicon glyphicon-remove-sign pull-right" style="margin-top: -8px;margin-right: -5px;"></i>';
			 var $previewBtn=$("#previewBtn");
			 var $cancelBtn=$("#cancelBtn");
			 var $preview=$("#preview");
			 var $questBtn=$("#questBtn");
			 var indexElm=0;
			 var $summernote=$(".summernote");
			 $previewBtn.on('click', function(){
				 if($(this).text() == "Add Preview"){
					 var $empty=$("#id-8642");
					 if($empty.length == 1)
						 $empty.remove();
					 $preview.append('<i class=" glyphicon glyphicon-remove-sign delPart" title="Delete This Part" style="width: 100%;cursor: pointer;" onClick="deleteItem(this,'+$preview.children().length+')"></i><a class="thumbnail update" style="margin-top: -5px;color:#333333;cursor: pointer;" id="section-'+$preview.children().length+'">'+$summernote.code()+'</a>');
					  
				 }else{
					 $("#"+editExamDivid).html($summernote.code());
					 $previewBtn.text("Add Preview");
				 }
				 
				 $summernote.code('');
				 indexElm =($preview.find("a").length-1);
			 });
			 $preview.on('click',".update",function(){
				 var self=$(this);
				 $previewBtn.text("Update Preview");
				 $summernote.code(self.html());
				 editExamDivid=self.attr("id");
			 });
			 $cancelBtn.on('click',function(){
				 $previewBtn.text("Add Preview");
				 $summernote.code('');
			 });
			 $questBtn.on('click',function(){
				var $entry=$("#entry"); 
				var $this=$(this);
			 	var types=controllers.connectDelegate("/getQuestionType");
			 	templates.qstEntryForm(types);
			 	var $lbl=$("#lbl");
			 	var $markdiv=$("#marksDiv");
			 	var qstTypeId="1";
			 	var txt="";
			 	$(".select-item").change(function(){
			 		var self=$(this);
			 		if(self.val() != "0"){
			 			qstTypeId=self.val();
			 			if(self.attr("name") == "questiontype"){
			 				txt=self.find("option:selected").text();
			 				if(txt == "Descriptive")
			 					$markdiv.show();
			 				else
			 					$markdiv.hide();
			 			}else{
			 			}
			 		}else{}
			 	});
			 	var opt="";
			 	var lectureid="";
			 	$(".contenttypeclass").each(function(i){
			 		if(i == 0)
			 			lectureid=$(this).attr("courselectureid");
			 		
			 		opt += '<option value='+$(this).attr("courselectureid")+'>'+$(this).attr("courselecturetitle")+'</option>'; 
		 		});
			 	var $selectchap=$("#select-chap");
			 		$selectchap.html(opt);
			 		
			 		$selectchap.change(function(){
			 			lectureid=$(this).val();
			 			console.log("ddddddd "+lectureid);
			 		});
			 	
			 	$("#loadQstBtn").click(function(){
			 		var arr=[];
			 		arr.push(parseInt(lectureid));
			 		
			 		var arr=[];
			 		$(".contenttypeclass").each(function(){
			 			arr.push(parseInt($(this).attr("courselectureid")));
			 		});
			 		var mark="1";
			 		$lbl.text(txt+" Type Question(s)");
			 		if(qstTypeId == "5"){
			 			mark=$("#qtype").val();
			 		}
			 			
			 		var object={lecture:arr,typeId:qstTypeId,mark:mark};
			 		console.log("object "+object);
			 			questions=controllers.examDelegate("/getQuestions",object);
			 			templates.groupJSON(questions,000,"");
			 	});
			 	
			 	
			 	$("#addQustionsBtn").click(function(){
			 		var $qstDiv=$("#qstDiv").find(':checkbox:checked');
			 		
			 		if($qstDiv.length == 0){
			 			alert("Select a question");
			 			return;
			 		}
			 		if($("#orchk").prop( "checked" )){
			 			if($qstDiv.length == 1){
			 				alert("Please select another one question");
			 				return;
			 			}else if($qstDiv.length > 2){
			 				alert(" ' or ' type question does not support more then 2 questions");
			 				return;
			 			}
			 		}
			 			
			 		var collection=[];
			 		var alpha=['A','B','C','D','E','F','G','H','I','J'];
			 		 
			 		var dynamicDiv="root-"+indexElm;
			 		
			 		if($("#"+dynamicDiv).length == 0)
			 			$preview.append('<i class=" glyphicon glyphicon-remove-sign delPart" title="Delete This Part" style="width: 100%;cursor: pointer;" onClick="deleteItem(this,'+$preview.children().length+')"></i><a class="thumbnail" style="margin-top: -5px;color:#333333;cursor: pointer;" id="section-'+$preview.children().length+'">'
			 					+'<ol id="'+dynamicDiv+'"></ol></a>');
			 		
			 		$qstDiv.each(function(j){
			 			var id=$(this).val();
			 			var obj=_.where(questions, {questionid:parseInt(id) });
			 				$.each(obj,function(i){
			 					if(this.id != "5")
			 					 this.slno="( "+alpha[i]+" ). ";
			 					else{
			 						this.answers='';
			 					}
			 					collection.push(this);		
			 				});
			 		});
			 		
			 		templates.groupJSON(collection,1000,dynamicDiv);//templates.genQstTemplate(collection);
			 		
			 		$(".qdeleteAng").click(function(){
			 			$(this).parent().remove();
			 		});
			 	});
			 	
			 	//$preview
			 	$("#closeBtn").click(function(){
			 		$this.show();	
			 		$entry.children().remove();
			 	});
			 	$this.hide();
			 });
		 };*/
		 
		 /**
		  * Bind delete parts action
		  */
		  /*deletePart=function(){
			  $(".delPart").click(function(){
		 			$(this)..remove();
		 	});
		  };*/
		 /**
		  * CONFIG DESCRIPTION SELECTORS
		  */
		 configDescription=function(){
			 configSummernote("answerdiv");
		 };
		 trimText=function(textval){
				return $.trim(textval);
			};
		 /**
		  * CONFIG OBJECTIVE TYPE TEMPLATE EVENTS
		  */
		 	configobjEvent=function(){
				deleteAnswer=[];
		 		var incress=0;
				var $optionsummer=$('.ol-list');
				$("#moreoption").click(function(){
					appendElement($optionsummer,incress++);
				});
				appendElement($optionsummer,incress++);
				$optionsummer.on("click",'a',function(e){
					var len=$(".remove-qst").length;
					if(len > 1){
						var clsid=$(this).attr("classid");
						var $elm =$("."+clsid);
						$('.'+clsid).remove();
						var ansid=$(this).attr("answerid");
						var answer=$elm.find("div").code();
						if(ansid != undefined)
							deleteAnswer.push({id:ansid,answer:answer});
					}
					e.preventDefault();
				});
				$optionsummer.on("click",'.glyphicon-remove-sign',function(){
				    $(".glyphicon-ok-sign").not(this).removeClass('glyphicon-ok-sign');
				    $(this).toggleClass("glyphicon-ok-sign");
				});
				
				$(".upload-qst").click(function(){
					templates.uploadForm();
					$("#savequestion").hide();
					$(".questionentry").hide();
					typeId="2-0";
					$("#dwnld").attr("href",courseData.getContextPath()+"/assets/Objective-Type-Question-Format.xls");
					$('.back-btn').click(function(){
						 typeId="2";
						 loadEntryForm("2");
						 $(".questionentry").show();
					});
					
					$('.uploadxls').click(function(){
						var progressBar=document.getElementById("progressBar");
						progressBar.value=0;
						var fileType = $("#qst-attach-id").val().split('/').pop().split('\\').pop();
						fileType=fileType.substring(fileType.lastIndexOf("."),fileType.length).toLowerCase();
						if($("#qst-attach-id").val().length == 0){
							templates.showAlert("failed","Select a file ( .xls,.xlsx )");
							return;
						}
						if(fileType == ".xls" || fileType == ".xlsx"){
							$(".upload-status").show();
							var xhr=new XMLHttpRequest();
							xhr.onload=function(data){
								if(xhr.status == 200){ 
									obj=xhr.responseText; 									
									if(obj.length!=2){
										templates.uptblTemplate(JSON.parse(obj));
										$("#savequestion").show();
									}else{
										templates.showAlert("failed","Error ! Content not found in uploaded document");
									}
									progressBar.value=0;
									$(".upload-status").hide();
									$(".chk-all").click(function(){
										$(".chk-self").prop("checked", $(this).is(':checked'));	
										$(".chk-self").attr("status",$(this).is(':checked'));
									});
									var $elemnt=$(".chk-self");
									$elemnt.click(function (){
										$(".chk-all").prop("checked", $elemnt.is(':checked'));	
										$(this).attr("status",$elemnt.is(':checked'));
									});
									
								}else{
									$('#saveAction' ).prop( "disabled", false );
									templates.showAlert("failed","Error ! Upload failed.Can not connect to server");
								}
							};
							xhr.upload.onprogress=function(e){
								var percentComplete=(e.loaded/e.total)*100;
								progressBar.value=percentComplete;
							};
							xhr.onerror=function(){
								//console.log("Error ! upload failed.Can not connect to server");
								templates.showAlert("failed","Error ! Upload failed.Can not connect to server");
								//alert("Upload failed.Can not connect to server");
							};
							var fd=new FormData();
							fd.append("file",document.getElementById('qst-attach-id').files[0]);
							fd.append("filetype",fileType);
							xhr.open("POST",courseData.getContextPath()+"/rest/sheet/excelparser",true);
							xhr.send(fd);
						}else{
							templates.showAlert("failed","Only '.xls and .xlsx' file can upload");
						}
					});
				});
		 	};
		 /**
		  * LOAD QUESTION DATA BASE ON QUESTION TYPE ID
		  * @param string 
		  */
		 loadQuestion=function(){
			 description="";
			 questions=controllers.connectDelegate("/getQuestions?lectureid="+courselectureid+"&typeId="+typeId);
			 templates.groupJSON(questions,typeId,""); 
			 editQuestion();
		 };
		 /**
		  * CONFIG EDIT ACTION 
		  */
		 editQuestion=function(){
			 $('.edit-Item').click(function(){
				 var question=_.where(questions,{questionid:parseInt($(this).attr("questionid"))});
			 	editQuestions(question);
			 	$('.toggle-elm').toggle();toggle=true;
			 	$(".questionentry").show();
			 });
			 
		 };
		 
		 /**
		  * CONFIG SUMMERNOTE.JS 
		  * @param Class name 
		  */
		 configSummernote=function(classType){
			 var $serverImageURL="";
			 $('<div class="summernote"></div>').appendTo('.'+classType).summernote({
				 height: 220,
				 /*oninit: function() {
					 var $note=$('.note-editor');
					 var $full=$('[data-event="fullscreen"]');
					 if(! ($full.hasClass('active'))){
				 		$note.css('overflow-y' , 'auto');
					 }
					 $full.click(function(){ 
						 if(! ($full.hasClass('active'))){
							 $note.removeAttr('style'); 
						 }else if($full.hasClass('active')){
							 $note.css('overflow-y' , 'auto');
							 $note.css('height' , '250px');
						 };
					 });
	        	 },*/
				 onDrawingWindow:function(editor,$editable) {
		             	var $modal=$("#whiteboarModal");
		             		$modal.modal("show");
		             		$edit=$editable;
		             	if(wboardOnce){
		            		LC.setDefaultImageURLPrefix('../assets/whiteboard/img');
		            		var containerOne =$('.literally')[0];
		              	  	var lc = LC.init(containerOne);
		             		wboardOnce=false;
		             		$("#uploadDrawobject").click(function(){
		                 		var Pic = lc.getImage().toDataURL("image/png");
		    				    elemnt = Pic.replace('data:image/png;base64,', '');
		    				    $serverImageURL=fileUploadAjaxAction(elemnt.toString(),"drawobject");
		                 		var url=courseData.getContextPath()+'/OpenFile?r1=serverpath&r2='+  '/sat'+ $serverImageURL.split('"')[1].split('/sat')[1];
		                 		var texts =$edit.html();
		                 		$edit.html(texts+'<img  src='+url+' >');
		                 		$modal.modal("hide");
		                 	});
		             	}
		        	 },
		        	 onImageUpload: function(files, editor, $editable) { 
		  			  	$('.note-image-input').each(function(){
		  			  		if($(this).val().length > 0)
		  			  			$clientImageURL =$(this).val().trim();
		  			  	});
		  				 if($clientImageURL.length > 0 ){
		  					 $serverImageURL =  fileUploadAjaxAction(files[0],null);
		  			     };
		  			    var url=courseData.getContextPath()+'/OpenFile?r1=serverpath&r2='+  '/sat'+ $serverImageURL.split('"')[1].split('/sat')[1];
		  			     editor.insertImage($editable, url);
		  			  }
			 });
		 };
		 /**
		  * UPLOAD FILE
		  */
		 fileUploadAjaxAction = function(element,drawobject){
				var servletImageURL = null;
				try { 
					var xhr = new XMLHttpRequest(), fd = new FormData();
					xhr.onload = function(data){
						if(xhr.status == 202){
							console.info('upload success!');
							servletImageURL = xhr.responseText;
							return servletImageURL;
						}else{
							return 'ERROR';
						}
					};
					if(drawobject != null)
						fd.append("drawobject", element.toString());
					else{
						fd.append("drawobject", null);
						fd.append("file", element);
					}
					xhr.open("POST",courseData.getContextPath()+"/rest/connector/fileUri",false);
					xhr.send(fd); 
				} catch (e) {
					 console.error(e);
				}
				return servletImageURL;
			};
			/**
			 * APPEND REMOVE BUTTON AND LABELS
			 */
			appendElement=function($optionsummer,incress){
				$optionsummer.append('<li class="smnote-'+incress+'" style="padding-top: 11px;">'+
						'<button class="btn btn-default glyphicon glyphicon-remove-sign" type="button" data-toggle="tooltip" title="Select Correct Answer"></button>'+
						'<b><u>Enter Option <a href="#" style="margin-right: 16px;" class="pull-right remove-qst" data-toggle="tooltip" title="Delete Option" classid="smnote-'+incress+'">'+
						'[<i class="glyphicon glyphicon-remove" ></i>]</a>'+
						'</u><span style="color: red;"> *</span>'+
						'</b></li>');
				configSummernote("smnote-"+incress);
			};
			
		 
// THE END
		 
	return{
		/**
		 * NAVIGATE QUIZ TAB EVENT TRIGGER
		 */
		 pageEvent:function(){
			 $("#quizAtag").trigger("click");
			 return controllers.connectDelegate("/getQuestionType");
		 },
		 /**
		  * SELECT DATA CHANGE EVENT TRIGGER
		  * @param courselectureid
		  */
		 selectEvent:function (courselectid){
			 courselectureid=courselectid;
			 var Selectors=qstSelectors();
			 typeId="1";
			 var text="True/False";
			 Selectors.$select.change(function (){
				 typeId=Selectors.$select.val();
				 text=Selectors.$select.find("option:selected").text();
				 if(Selectors.$toggleElm.css('display') == "none"){
					 loadEntryForm (typeId); 
					 Selectors.$header.text(text);
				 }
					 
			 });
			 Selectors.$Btn.on("click",function(){
				 typeId=Selectors.$select.val();
				  loadQuestion (); // LOAD QUESTIONS
				  Selectors.$header.text(text);
			  });
			 Selectors.$toggleBtn.on("click",function(){
				 Selectors.$toggleElm.toggle();
				 Selectors.$header.text(text);
				 
				 if(typeId == "2-0"){ typeId="2";}
				 
				 	if(Selectors.$toggleElm.css('display') == "none"){
				 		loadEntryForm(typeId);
				 		toggle=true;
				 		$("#saveBtn").text("Save");
				 	}else{
				 		loadQuestion (); // LOAD QUESTIONS
				 	}
			 });
			 loadQuestion (); // LOAD QUESTIONS
		 },
		 validateData:validateData,
		 confirmDelete:confirmDelete,
		 resetFields:resetFields,
		 backToQuestion:function(){if(toggle){$('.toggle-elm').toggle();toggle=false;}  loadQuestion();},
		 examForm: function (){loadEntryForm("exam");}
	};
};

var sat=function(){
	/**
	 * CREATE INSTANCE OF GLOBAL VARIABLES
	 */
	// var gloable =new Gloable();
	 var eventHandler=new EventHandler();
	
	/**
	 * @param courselectureid
	 * GET QUESTION TYPES
	 */
	var initialize=function(courselectureid){
		if(courselectureid !=null){
			templates.selectTemplate(eventHandler.pageEvent());
			eventHandler.selectEvent(courselectureid);
			
		}else{
			eventHandler.examForm();
		}
	};
	
	/**
	 * SAVE QUESTION DATA 
	 */
	var saveQuestions=function(){
		eventHandler.validateData();
	};
	
	/**
	 * CANCEL OR RESET FIELD  
	 */
	var cancelQuestion=function(){
		eventHandler.resetFields();
	};
	/**
	 * BACK TO QUESTIONS
	 */
	var backToQuestion=function(){
		eventHandler.backToQuestion();
	};
	
	/**
	 * APPEND DELETE CONFIRM ALERT ELEMENT
	 */
	 var deleteQuestion=function(id){
		 $("#"+id).children().remove();
		 var element='<span class="alert alert-info offset3" role="alert" style="margin-bottom :0px">' +
						'<strong>Message! </strong>' +
						'Do you want delete this question ?' +
						'<a type="button" class="btn btn-flat btn-success del-btn" questionid="'+id+'" style="margin-left:10px;margin-top: -3px; font-size: smaller; height: 18px; padding: 2px 5px 1px 6px;">Yes</a>' +
						'<a type="button" class="btn btn-flat btn-danger" data-dismiss="alert" style="margin-left:4px;margin-top: -3px; font-size: smaller; height: 18px; padding: 2px 5px 1px 6px;">No</a>' +
					'</span>';
		 $("#"+id).prepend(element);
		 $(".del-btn").click(function(){
			 eventHandler.confirmDelete($(this).attr("questionid"));
		 });
	 };
	
	
	/**
	 * BINDING METHOD FOR PUBLIC ACCESS 
	 */
	return{
		initialize:initialize,
		saveQuestions:saveQuestions,
		cancelQuestion:cancelQuestion,
		deleteQuestion:deleteQuestion,
		backToQuestion:backToQuestion
	};
}();