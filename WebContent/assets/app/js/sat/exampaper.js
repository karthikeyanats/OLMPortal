/**
 * EXAM PAPER GENERATOR
 * exampaper.js
 * @Copyright 2014-2015 iGrandee pvt.ltd.
 * @author selvarajan_j
 * @contact selvarajan_j@igrandee.com,jjjackpterson@gmail.com
 * 
 * Wed Apr 30 2015 12:01 
 */
 var deleteQuestions=[];
	var deleteHeader=[];
	var selectedElm=null;
	var newQuestion=[];
	var actionStatus="Save";
	var dysectionid=null;
	var titleid=null;
	var indexElm=0;
	var orstatus=false;
var expaper=function(){
	
	
	var savePaper=function(){
		var object = [];
		var $titleinput=$("#titleinput");
		if($titleinput.val().length == 0){
			alert("Question paper title can not be empty");
			return;
		}
		if($(".update").length == 0){
			alert("Questions cannot be with out section.Please create section ");
			return;
		}
		var updateHeaderid=[];	
		$(".thumbnail").each(function() {
			var self = $(this);
			var splitClass = self.attr("class").split(" ");
			item = {};
			if (splitClass[1] == undefined) {
				var inn = [];
				var $inner = self.children();
				
				$inner.children().each(function() {
					
					if($(this).attr("questionid") != undefined){
						inneritem = {};
						inneritem["id"] = $(this).attr("questionid");
						inn.push(inneritem);
					}else{
						
						var apndstr="";
						$(this).find(".ortype").children().each(function(){
							if($(this).attr("questionid")!=undefined)
								apndstr += $(this).attr("questionid")+"&"; //console.log("get ortypes "+$(this).attr("questionid"));
						});
						apndstr=apndstr.substring(0,apndstr.length-1);
						inneritem = {};
						inneritem["id"] = apndstr;
						inn.push(inneritem);
					}
					
				});
				//item["question"] = inn;
				object[object.length-1].questions=inn;
			} else {
				if($(this).attr("sectionid") != undefined){
					updateHeaderid.push($(this).attr("sectionid"));
				}else
					updateHeaderid.push("-");
				
				item["headers"] = self.html();
				object.push(item);
			}
			
		});
		var examobj=[];
		examobj={"exam":object};
		
		var substr=JSON.stringify(examobj);
		console.log("get delete action data values "+substr);
		console.log("get delete action data deleteQuestions "+JSON.stringify(deleteQuestions));
		console.log("get delete action data deleteHeader "+deleteHeader);
		console.log("get delete action data updateHeaderid "+updateHeaderid);
		
		var jk={};
		var finalNewQus=[];
		var dub="";
		_.each(newQuestion,function(item){
		   if(dub !=item.sectionid){
		     if(jk.questionid != undefined){
		    	 finalNewQus.push(jk);
		      	jk={};
		     }
		     dub=item.sectionid;
		    jk.sectionid=item.sectionid;
		    jk.questionid=item.questionid;
		   }else{
		    jk.subquestionid=item.questionid;
		   }
		})
		finalNewQus.push(jk);
		
		console.log("get delete action data newQuestion "+JSON.stringify(finalNewQus));
		/** UPDATE PROCESS */
		if(actionStatus == "update"){
			
		/*if(deleteQuestions.length == 0 && newQuestion.length == 0){
			alert("Does the question has no changes");
			return;
		}*/
			object={jsonData:"",courselectureid:"",titleid:titleid,title:$("#titleinput").val(),deleteQuestions:JSON.stringify(deleteQuestions),deleteHeader:JSON.stringify(deleteHeader),newQuestion:JSON.stringify(finalNewQus)};
			status=controllers.examDelegate("/updatepaper",object);
			if(status != "INTERNAL_SERVER_ERROR"){
				$("#preview").html('<div class="row-fluid"><center id="id-8642"><h4 style="color:gainsboro">No Preview Data</h4></center><ul id="preview" class="thumbnails"></ul></div>');
				$("#generatBtn").text("Generate Question Paper");
				clearAll();
			}else
				alert("Cannot update question paper. Please try again later ");
		}else{

		var substr=JSON.stringify(examobj);
			object={jsonData:substr,courselectureid:$(".noborder").attr("courseid"),titleid:titleid,title:$("#titleinput").val(),deleteQuestions:'',deleteHeader:'',newQuestion:''};
			status=controllers.examDelegate("/savepaper",object);
			if(status != "INTERNAL_SERVER_ERROR"){
				$("#preview").html('<div class="row-fluid"><center id="id-8642"><h4 style="color:gainsboro">No Preview Data</h4></center><ul id="preview" class="thumbnails"></ul></div>');
				$("#generatBtn").text("Generate Question Paper");
				clearAll();
			}else
				alert("Cannot generate question paper. Please try again later ");
		}	
	};
	
	/**
	 * LOAD QUESTION PAPERS
	 */
	loadQuestionPaper=function(){
		$questionPaperTable=$("#questionPaperTable");
		var data=controllers.getExamDelegate("/getExamPaperTitle?courseid="+$(".noborder").attr("courseid"),null);
		console.log("get data "+data);
		var option="";
		if(data != "NOT_FOUND"){
			
		for(var i=0;i<data.length;i++){
			option += '<tr><td>'
					+ data[i].papername
					+ '</td><td>'
					+ formatDate(data[i].createddate)
					+ '</td>'
					+ '<td><span class="btn btn-flat btn-primary paper-action" style="padding:2px 3px 1px 6px; margin-right: 5px;"   title="Edit This Question Paper" paperid="'+data[i].id+'"><i class="glyphicon glyphicon-edit"> </i></span>'
					+ '<span class="btn btn-flat btn-danger paper-action" style="padding:2px 5px 1px 5px;" title="Delete This Question Paper"   paperid="'+data[i].id+'"><i class="glyphicon glyphicon-trash" > </i></span>'
					+ '</td>'
					+ '</tr>';
			}
		 $questionPaperTable.html(option);
		}
		
			$(".paper-action").click(function(){
				$this=$(this);
				var paperid=$this.attr("paperid");
				if($this.attr("title") == "Edit This Question Paper"){
					if (confirm("Do you want 'edit' this question paper ?")) {
						var data=controllers.getExamDelegate("/getExamPaper?paperId="+paperid,null);
						rendererQuestionPaper(data);
				    } else {
				    	console.log("no edit");
				    }
				}
				else{	
					if (confirm("Are you sure do you want 'delete' this question paper ?")) {
						var data=controllers.getExamDelegate("/deleteExamPaper?paperId="+paperid,null);
						if(data != "ACCEPTED"){
							alert("Cannot delete this question paper. Please try again later");	
						}else{
							$(this).parent().parent().remove();	
						}
				    }  
				}
			});
		};
	
	rendererQuestionPaper=function(data){
		deleteHeader=[];
	 	selectedElm=null;
	 	newQuestion=[];
	 	dysectionid=null;
	 	titleid=null;
	 	actionStatus="update";
	 	
		 $preview=$("#preview");
		 $preview.html("");
		// console.log("get id "+data);
		 titleid=data[0].id;
		 $("#titleinput").val(data[0].papername);
	 	var temp='<i class="glyphicon glyphicon-remove-sign delPart" title="Delete This Part" style="width: 100%;cursor: pointer;"   onclick="deleteItem(this,0)">'
	 		+'</i>'
	 		+'<a class="thumbnail update" style="margin-top: -5px;color:#333333;cursor: pointer;" type="header" sectionid ="'+data[0].id+'" id="section-0">'+data[0].instruction+'</a>'
	 		+'</ul>';
	 	
	 	var _examquestion=[];
	 	var _sectionid=[];
	 	var root=1;
	 	var n=1;
	 	//console.log("get data values "+data);
		for(var i=0;i<data.length;i++){
			n++;
			temp+='<i class="glyphicon glyphicon-remove-sign delPart" title="Delete This Part" style="width: 100%;cursor: pointer;" onclick="deleteItem(this,'+n+')">'
					+'</i>'
					+'<a class="thumbnail update" style="margin-top: -5px;color:#333333;cursor: pointer;" type="header" sectionid ="'+data[i].sectionid+'" id="section-'+n+'">'+data[i].sectionname+'</a>'
					+'</ul>';
					
				
				var obj=controllers.getExamDelegate("/getpaperquestion?sectionid="+data[i].sectionid,null);
				//console.log("obj "+obj);
					if(obj != "NOT_FOUND"){
						n++;
						_examquestion.push(obj);
						_sectionid.push("root-"+root);
						temp+='<i class="glyphicon glyphicon-remove-sign delPart" title="Delete This Part" style="width: 100%;cursor: pointer;"   onclick="deleteItem(this,'+n+')"></i>'
							+'<a class="thumbnail" style="margin-top: -5px;color:#333333;cursor: pointer;" type="question" sectionid ="'+obj[0].sectionid+'" id="section-'+n+'"><ol id="root-'+root+'"></ol></a>';
						root++;
					}
			}
		 $preview.html(temp);
		
		 var collection=[];
	 	 var alpha=['A','B','C','D','E','F','G','H','I','J'];
	 	
		 for(var j=0;j<_examquestion.length;j++){
			 questions=_examquestion[j];
			 collection=new Array();
		 	var dubl="";
		 	var k=0;
		 	
			 _.each(questions,function(item){
				//console.log("get items "+item);	 
				 
				 if (item.ortype != undefined ){
					 orstatus=true;
					 $("#uporchk").prop( "checked",true );
					 //console.log("json sting "+item);
					 templates.updatePaper(item,1000,_sectionid[j]);//templates.genQstTemplate(collection);
				 }else{
					 if(dubl !=item.questionid ){
						 dubl =item.questionid; 
						 k=0;
					 }
						 
					if(item.id != "5")
						item.slno="( "+alpha[k]+" ). ";
					else{
						item.answers='';
					}
					collection.push(item);		
					k++;
				}
	 		});
			 	if(!orstatus)
			 		templates.groupJSON(collection,1000,_sectionid[j]);//templates.genQstTemplate(collection);
		 };
		 
		 $(".qdeleteAng").click(function(){
			 var self=$(this);
	 			if(self.attr("sectionid") != undefined)
	 				deleteQuestions.push({sectionid:self.attr("sectionid")});
	 		
	 			self.parent().remove();
	 	});
		 $("#id-8642").html("");
		 $(".delPart").css("display","none");
		/* $(".qdeleteAng").remove();
		 $(".delPart").remove();
		 $("#generatBtn").css("display","none");
		 $("#id-8642").html("<h2>Preview</h2><br>");*/
		 	//clearAll();
		 	
		 	$("#generatBtn").text("Update Questions");
		  $(".thumbnail").click(function(){
			  //console.log("call error "+$(this).children().attr("id"));
			  selectedElm=$(this).children().attr("id");
			  dysectionid=$(this).attr("sectionid");
			  console.log("get section id "+dysectionid);
		 });
		  
	};
	
	
	formatDate=function(val){
		 var d=new Date(parseInt(val)); 
		 return d.toDateString() +" "+d.toLocaleTimeString();
	};
	
	configExamEvent=function(){
		 //var delbtn='<i class=" icon-remove-sign pull-right" style="margin-top: -8px;margin-right: -5px;"></i>';
		 var $previewBtn=$("#previewBtn");
		 var $cancelBtn=$("#cancelBtn");
		 var $preview=$("#preview");
		 var $questBtn=$("#questBtn");
		 
		 var $summernote=$(".summernote");
		 $previewBtn.on('click', function(){
			 if(actionStatus == "update"){
				 alert("Cannot create new header section. Only applicable for question section");
				 return;
			 }
			 
			 if($(this).text() == "Add Preview"){
				 var $empty=$("#id-8642");
				 if($empty.length == 1)
					 $empty.remove();
				 $preview.append('<i class="glyphicon glyphicon-remove-sign con-remove-sign delPart" title="Delete This Part" style="width: 100%;cursor: pointer;"  onClick="deleteItem(this,'+$preview.children().length+')"></i><a class="thumbnail update" style="margin-top: -5px;color:#333333;cursor: pointer;" id="section-'+$preview.children().length+'">'+$summernote.code()+'</a>');
				  
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
		 			//console.log("ddddddd "+lectureid);
		 		});
		 	
		 	$("#loadQstBtn").click(function(){
		 		var arr=[];
		 		arr.push(parseInt(lectureid));
		 		
		 		/*var arr=[];
		 		$(".contenttypeclass").each(function(){
		 			arr.push(parseInt($(this).attr("courselectureid")));
		 		});*/
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
		 		 
		 		if(actionStatus != "update"){
		 			var dynamicDiv="root-"+indexElm;
		 			if($("#"+dynamicDiv).length == 0)
		 				$preview.append('<i class="glyphicon glyphicon-remove-sign delPart" title="Delete This Part" style="width: 100%;cursor: pointer;"  onClick="deleteItem(this,'+$preview.children().length+')"></i><a class="thumbnail" style="margin-top: -5px;color:#333333;cursor: pointer;" id="section-'+$preview.children().length+'">'
		 					+'<ol id="'+dynamicDiv+'"></ol></a>');
		 			}else{
		 				if(selectedElm != null)
		 					dynamicDiv=selectedElm;
		 				else{
		 					alert("Please select question part then append question");
		 					return;
		 				}
		 			}
		 		
		 		$qstDiv.each(function(j){
		 			var id=$(this).val();
		 			if(actionStatus == "update"){
		 				newQuestion.push({questionid:id,sectionid:dysectionid});
		 			}
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
		 		//console.log("get collectyiosn "+collection);
		 		templates.groupJSON(collection,1000,dynamicDiv);//templates.genQstTemplate(collection);
		 		
		 		$(".qdeleteAng").click(function(){
		 			var self=$(this);
		 			self.parent().remove();
		 			console.log("NEW ACTION "+self.parent().attr("sectionid"));
		 			$.each(newQuestion, function(i){
		 			    if(newQuestion[i].questionid=== self.parent().attr("questionid")) {
		 			    	newQuestion.splice(i,1);
		 			        return false;
		 			    }
		 			});
		 		});
		 	});
		 	
		 	//$preview
		 	$("#closeBtn").click(function(){
		 		$this.show();	
		 		$entry.children().remove();
		 	});
		 	$this.hide();
		 });
	 };
	 
	 var exportPDF=function(){
		 var doc = new jsPDF();
		 doc.fromHTML($('#preview').html(), 15, 15, {
		        'width': 170,
		    });
		    doc.save('sample-file.pdf'); 
	 };
	 var clearAll=function(){
		 indexElm=0;
		 deleteHeader=[];
		 selectedElm=null;
		 newQuestion=[];
		 actionStatus="Save";
		 dysectionid=null;
		 titleid=null;
		 orstatus=false;
		 $("#titleinput").val("");
		 $("#generatBtn").text("Generate Question Paper");
		 $("#preview").html('<div class="row-fluid"><center id="id-8642"><h4 style="color:gainsboro">No Preview Data</h4></center><ul id="preview" class="thumbnails"></ul></div>');
	 };
	 
	return {
		savePaperData:savePaper,
		exportPDF:exportPDF,
		configExamEvent:configExamEvent,
		loadQuestionPaper:loadQuestionPaper,
		clearAll:clearAll
	};
}();

 
function deleteItem(element,indx){
	  var self=$('#section-'+indx);
	  console.log("get delete headers "+self.attr("type") +" SECTION "+self.attr("sectionid"));
	  if(self.attr("type") == "header"){
		  if(self.attr("sectionid") != undefined)
			  deleteHeader.push(self.attr("sectionid"));
	  }else{
		  self.children().children().each(function(){
			  console.log("values "+$(this).attr("sectionid"));
			  if($(this).attr("sectionid") != undefined)
				  deleteQuestions.push({sectionid:$(this).attr("sectionid")});
		  });
		  self.children().children().find(".ortype").children().each(function(){
			  if($(this).attr("sectionid") != undefined)
				  deleteQuestions.push({sectionid:$(this).attr("sectionid")});
		  });
		  
	  }
	  console.log("call delete Items "+deleteHeader +" == "+deleteQuestions);
	  element.remove();
	  self.remove();
}
