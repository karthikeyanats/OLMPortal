$(document).ready(function(){
	
	previewcourse().init();
	$('.clicktoreview').click(function(){
		$('#ratingmodal').modal('show');
	});
	$('.newratediv').raty({
		starHalf      : courseData.getContextPath()+"/"+'assets/app/images/star/star-half.png',
		starOff       : courseData.getContextPath()+"/"+'assets/app/images/star/star-off.png', 
		starOn        : courseData.getContextPath()+"/"+'assets/app/images/star/star-on.png', 
	});

	var today=new Date();
	 var date = today.toDateString();
	$('#todaydate').attr("value",date);
	
	Rating.ratingdata();
});
Handlebars.registerHelper('freeChecker', function(price) 
		 { 
		 if(price=="Free"){			 
			 return new Handlebars.SafeString("Free");
		 }
		 else{
			 
			 return new Handlebars.SafeString("<i class='fa fa-inr'>"+price+"</i>");
			 
		 }
});
manageschedule={
		loadSchedule:function(){
			var courseid=$('[name=courseids]').val();
			
			var schedulelist = courseData.getSchedulecourseDetails(courseid); 
			
			Handlebars.registerHelper('checkdate',function(scheduledate,livesessionid,starttime,endtime,price,courseid){
								
				var today = new Date();
			    var dd = today.getDate();
			    var mm = today.getMonth()+1;
                var yyyy = today.getFullYear();
			    if(dd<10){
			        dd='0'+dd;
			    } 
			    if(mm<10){
			        mm='0'+mm;
			    } 
			    var today = yyyy+'-'+mm+'-'+dd;
			    
				var dates = new Date(scheduledate);
				var dd = dates.getDate();
				var mm = dates.getMonth()+1;
                var yyyy = dates.getFullYear();
				    if(dd<10){
				        dd='0'+dd;
				    } 
				    if(mm<10){
				        mm='0'+mm;
				    } 
				    var dates = yyyy+'-'+mm+'-'+dd;
				    
				    var currentdate = new Date(); 
				    var cuntdate =currentdate.toLocaleTimeString();
				    var cntdate =cuntdate.split(' ');
				    var cunttime =cntdate[0].split(':'); 
				    var hour=currentdate.getHours();
				    var mint=currentdate.getMinutes();
				    if(mint<10){
				    	mint='0'+mint;
				    } 
				    var datetimeval = /*hour + ":" + mint;*/cunttime[0] + ":" +cunttime[1] +" "+ cntdate[1];
				    /*var start=$.trim(starttime);
				    var end=$.trim(endtime);*/
				    
				    var datetime ="";
					var i=datetimeval.split(' ');
					var j=i[0].split(':');
					if(i[1]=="PM"){
						if(j[0]=="12"){
							datetime=j[0]+":"+j[1]+" "+i[1];
						}
						else{
							datetime=parseInt(j[0])+12+":"+j[1]+" "+i[1];
						}
						
					}
					else if(i[1]=="AM"){
						if(j[0]=="12"){
							datetime="00:"+j[1]+" "+j[1];	
						}
						else if(parseInt(j[0])<10){
							datetime="0"+j[0]+":"+j[1]+" "+j[1];
						}
						else{
							datetime=j[0]+":"+j[1]+" "+i[1];
						}
					}
					else{
						datetime=j[0]+":"+j[1]+" "+i[1];
					}
				    var startstimeval=$.trim(starttime);
				    var endstimeval=$.trim(endtime);
				    
				    var start ="";
					var a=startstimeval.split(' ');
					var b=a[0].split(':');
					if(a[1]=="PM"){
						if(b[0]=="12"){
							start=b[0]+":"+b[1]+" "+a[1];
						}
						else{
							start=parseInt(b[0])+12+":"+b[1]+" "+a[1];
						}
						
					}
					else if(a[1]=="AM"){
						if(b[0]=="12"){
							start="00:"+b[1]+" "+a[1];	
						}
						else if(parseInt(b[0])<10){
							start="0"+b[0]+":"+b[1]+" "+a[1];	
						}
						else{
							start=b[0]+":"+b[1]+" "+a[1];
						}
					}
					else{
						start=b[0]+":"+b[1]+" "+a[1];
					}
					
					var end="";
					var c=endstimeval.split(' ');
					var d=c[0].split(':');
					if(c[1]=="PM"){
						if(d[0]=="12"){
							end=d[0]+":"+d[1]+" "+c[1];
						}
						else{
							end=parseInt(d[0])+12+":"+d[1]+" "+c[1];
						}
					}
					else if(c[1]=="AM"){ 
						if(d[0]=="12"){
							end="00:"+d[1]+" "+c[1];
						}
						else if(parseInt(d[0])<10){
							end="0"+d[0]+":"+d[1]+" "+c[1];
						}
						else{
							end=d[0]+":"+d[1]+" "+c[1];
						}
					}
					else{
						end=d[0]+":"+d[1]+" "+c[1];
					}
				 if((today == dates)){
					 
					    if ( (datetime >= start && datetime<=end))
					    {
					     return  "<a class='label marleft15' id='livechat' style='background-color:#449d44;' courseid="+courseid+" scheduledate="+scheduledate+" price="+price+" livesessionid="+livesessionid+" data-toggle='tooltip' title='Live'><i class='fa fa-play-circle'></i></a>";"<span class='btn-circle live ' id='livechat' style='background-color:#0A8F0A;' courseid="+courseid+" scheduledate="+scheduledate+" price="+price+" livesessionid="+livesessionid+" starttime="+starttime+" endtime="+endtime+"  data-toggle='tooltip' title='Live'><b><i class='fa fa-play-circle' style='font-size:21px;margin-left:6px;color: white !important;'></i>&nbsp;&nbsp;</b> </span>";
					    }
					    else if(datetime<start && datetime< end) {
					    	return "<a class='label future marleft15' id='livechat' style='background-color:rgb(218, 154, 37);' data-toggle='tooltip' title='Future'><i class='fa fa-play-circle'></i></a>";/*"<span class='btn-circle c1 future' courseid="+courseid+" scheduledate="+scheduledate+" price="+price+" livesessionid="+livesessionid+" starttime="+starttime+" endtime="+endtime+" style='background-color:rgb(218, 154, 37);'data-toggle='tooltip' title='Future' ><b><i class='icon-signin'  style='font-size:22px;margin-left:6px;'></i>&nbsp;&nbsp;</b> </span>";*/
					    }
					    else if(datetime>start && datetime>end){
					    	return "<a class='label complete marleft15' id='livechat' style='background-color:rgb(73, 179, 199);' data-toggle='tooltip' title='Completed'><i class='fa fa-play-circle'></i></a>";/*"<span class='btn-circle c1 complete' courseid="+courseid+" scheduledate="+scheduledate+" price="+price+" livesessionid="+livesessionid+" starttime="+starttime+" endtime="+endtime+" style='background-color:rgb(73, 179, 199);'data-toggle='tooltip' title='Complete' ><b><i class='icon-time' style='font-size:22px;margin-left:6px;'></i>&nbsp;&nbsp;</b> </span>";*/    	
					    }
				 } 
				    
			    
			    else if(today<dates/*) &&(datetime<start && datetime< end)*/){
			    	return "<a class='label future marleft15' id='livechat' style='background-color:rgb(218, 154, 37);' data-toggle='tooltip' title='Future'><i class='fa fa-play-circle'></i></a>";/*"<span class='btn-circle c1 future' courseid="+courseid+" scheduledate="+scheduledate+" price="+price+" livesessionid="+livesessionid+" starttime="+starttime+" endtime="+endtime+" style='background-color:rgb(218, 154, 37);'data-toggle='tooltip' title='Future' ><b><i class='icon-signin'  style='font-size:22px;margin-left:6px;'></i>&nbsp;&nbsp;</b> </span>";*//*return "<span class='btn-circle c1 future' courseid="+courseid+" scheduledate="+scheduledate+" price="+price+" livesessionid="+livesessionid+" starttime="+starttime+" endtime="+endtime+" style='background-color:rgb(218, 154, 37);'data-toggle='tooltip' title='Future' ><b><i class='icon-signin'  style='font-size:22px;margin-left:6px;'></i>&nbsp;&nbsp;</b> </span>";*/
			    } 
			    else
			    {
			    	return "<a class='label complete marleft15' id='livechat' style='background-color:rgb(73, 179, 199);' data-toggle='tooltip' title='Completed'><i class='fa fa-play-circle'></i></a>";/*"<span class='btn-circle c1 complete' courseid="+courseid+" scheduledate="+scheduledate+" price="+price+" livesessionid="+livesessionid+" starttime="+starttime+" endtime="+endtime+" style='background-color:rgb(73, 179, 199);'data-toggle='tooltip' title='Complete' ><b><i class='icon-time' style='font-size:22px;margin-left:6px;'></i>&nbsp;&nbsp;</b> </span>";*//*return "<span class='btn-circle c1 complete' courseid="+courseid+" scheduledate="+scheduledate+" price="+price+" livesessionid="+livesessionid+" starttime="+starttime+" endtime="+endtime+" style='background-color:rgb(73, 179, 199);'data-toggle='tooltip' title='Complete' ><b><i class='icon-time' style='font-size:22px;margin-left:6px;'></i>&nbsp;&nbsp;</b> </span>";*/
			    }
			});
			
			/* ***************************************  schedule date ************************************* */
			Handlebars.registerHelper('schedulesate',function(scheduledate){
				var date =new Date(scheduledate);
				return date.toDateString();
			});
		/* ********************** Enroll process ********************************************** */
			
			Handlebars.registerHelper('checkdates',function(scheduledate,livesessionid,starttime,endtime,price,courseid){
				
				var today = new Date();
			    var dd = today.getDate();
			    var mm = today.getMonth()+1;
                var yyyy = today.getFullYear();
			    if(dd<10){
			        dd='0'+dd;
			    } 
			    if(mm<10){
			        mm='0'+mm;
			    } 
			    var today = yyyy+'-'+mm+'-'+dd;
			    
				var dates = new Date(scheduledate);
				var dd = dates.getDate();
				var mm = dates.getMonth()+1;
                var yyyy = dates.getFullYear();
				    if(dd<10){
				        dd='0'+dd;
				    } 
				    if(mm<10){
				        mm='0'+mm;
				    } 
				    var dates = yyyy+'-'+mm+'-'+dd;
				    
				    var currentdate = new Date(); 
				    var cuntdate =currentdate.toLocaleTimeString();
				    var cntdate =cuntdate.split(' ');
				    var cunttime =cntdate[0].split(':'); 
				    var hour=currentdate.getHours();
				    var mint=currentdate.getMinutes();
				    if(mint<10){
				    	mint='0'+mint;
				    } 
				    var datetimeval = /*hour + ":" + mint;*/cunttime[0] + ":" +cunttime[1] +" "+ cntdate[1];
				    /*var start=$.trim(starttime);
				    var end=$.trim(endtime);*/
					
					var datetime ="";
					var i=datetimeval.split(' ');
					var j=i[0].split(':');
					if(i[1]=="PM"){
						if(j[0]=="12"){
							datetime=j[0]+":"+j[1]+" "+i[1];
						}
						else{
							datetime=parseInt(j[0])+12+":"+j[1]+" "+i[1];
						}
						
					}
					else if(i[1]=="AM"){
						if(j[0]=="12"){
							datetime="00:"+j[1]+" "+j[1];	
						}
						else if(parseInt(j[0])<10){
							datetime="0"+j[0]+":"+j[1]+" "+j[1];
						}
						else{
							datetime=j[0]+":"+j[1]+" "+i[1];
						}
					}
					else{
						datetime=j[0]+":"+j[1]+" "+i[1];
					}
					
				    var startstimeval=$.trim(starttime);
				    var endstimeval=$.trim(endtime);
				    
				    var start ="";
					var a=startstimeval.split(' ');
					var b=a[0].split(':');
					if(a[1]=="PM"){
						if(b[0]=="12"){
							start=b[0]+":"+b[1]+" "+a[1];
						}
						else{
							start=parseInt(b[0])+12+":"+b[1]+" "+a[1];
						}
						
					}
					else if(a[1]=="AM"){
						if(b[0]=="12"){
							start="00:"+b[1]+" "+a[1];	
						}
						else if(parseInt(b[0])<10){
							start="0"+b[0]+":"+b[1]+" "+a[1];	
						}
						else{
							start=b[0]+":"+b[1]+" "+a[1];
						}
					}
					else{
						start=b[0]+":"+b[1]+" "+a[1];
					}
					
					var end="";
					var c=endstimeval.split(' ');
					var d=c[0].split(':');
					if(c[1]=="PM"){
						if(d[0]=="12"){
							end=d[0]+":"+d[1]+" "+c[1];
						}
						else{
							end=parseInt(d[0])+12+":"+d[1]+" "+c[1];
						}
					}
					else if(c[1]=="AM"){ 
						if(d[0]=="12"){
							end="00:"+d[1]+" "+c[1];
						}
						else if(parseInt(d[0])<10){
							end="0"+d[0]+":"+d[1]+" "+c[1];
						}
						else{
							end=d[0]+":"+d[1]+" "+c[1];
						}
					}
					else{
						end=d[0]+":"+d[1]+" "+c[1];
					}
				    
				    /*var livesessionidobj={};
					livesessionidobj.livesessionid=livesessionid;
					var livesessionschedulelist = courseData.getlivesessionschedulelist(livesessionidobj);
					var livesession = _.pluck(livesessionschedulelist,"livesessionid");*/
				 
					if((today == dates)){
					 
					    if ( (datetime >= start && datetime<=end))
					    {
							return "<a class='btn btn-flat btn-success live' courseid="+courseid+" scheduledate="+scheduledate+" price="+price+" livesessionid="+livesessionid+" data-toggle='tooltip' title='Live'><span class='enrolvalidate' livesessionid="+livesessionid+">Enroll</span></a>";
					    }
					    else if(datetime<start && datetime< end) {
							return "<span class='label label-warning' data-toggle='tooltip' title='Future'><span class='enrolvalidate' livesessionid="+livesessionid+">Enroll</span></span>";
					    }
					    else if(datetime>start && datetime>end){
							return "<span class='label label-info' data-toggle='tooltip' title='Completed'><span class='enrolvalidate' livesessionid="+livesessionid+">Enroll</span></a>";
					    }
				 } 
				    
			    else if(today<dates){
						return "<span class='label label-warning' data-toggle='tooltip' title='Future'><span class='enrolvalidate' livesessionid="+livesessionid+">Enroll</span></span>";
			    } 
			    else
			    {
					return "<span class='label label-info' data-toggle='tooltip' title='Completed'><span class='enrolvalidate' livesessionid="+livesessionid+">Enroll</span></span>";
			    }
			});
			$('.live').tooltip();
			$('.future').tooltip();
			$('.complete').tooltip();
			renderTemplate("#scheduletemplate", schedulelist, "#scheduleholder");
			
			/* *************** Enroll and Enrolled validate *********************** ****/
			var livesessionids = _.pluck(schedulelist,'livesessionid');
			$(".livesessionidss").val(livesessionids);
			
			var array = [];
			$(".enrolvalidate").each(function(){
				array.push($(this).attr('livesessionid'));
			});
			
			livesessionarray = {};
			livesessionarray.livesessionarrays=$(".livesessionidss").val();
			if(livesessionarray!=undefined && livesessionarray!=""){
				var livesessionidarray = ajaxhelperwithdata("livesessionlist", livesessionarray);
				var livesessionidsarr =_.pluck(livesessionidarray,'livesessionid');
				if(livesessionidsarr.length>0){
					_.each(livesessionidsarr,function(e){
						if(_.contains(array,String(e))==true){
							$(".enrolvalidate").each(function(){
								if(e==$(this).attr('livesessionid')){
								$(this).text("Enrolled");
							}
							});
						}
					});
				}
			}
			 
			/*$('.schedule').each(function(){
				var scdate=$(this).attr('schdate');
				dates = new Date(parseInt(scdate));
	             date = dates.toDateString();
	             $('.scheduledate').text(date);
			});*/
			$('.live').click(function(){	
				var courseenrollmentid=$('#courseenrollmentid').val();
				var livesessionid=$(this).attr('livesessionid');
				var courseid=$(this).attr('courseid');
				var priceval=$(this).attr('price');
				
				var livesessionidobj={};
				livesessionidobj.livesessionid=livesessionid;
				livesessionidobj.courseenrollmentid=courseenrollmentid;
				livesessionidobj.courseid=courseid;
				
				var livesessionschedulelist = courseData.getlivesessionschedulelist(livesessionidobj);
				if(livesessionschedulelist!="false" ){					
					/*$('[name="courseid"]').val(courseid);
					$('[name="courseenrollmentid"]').val(courseenrollmentid);										
					$('[name="livesessionid"]').val(livesessionid);	
					$('[name="courseenrollmentstatus"]').val(status);	
					$('#livesession').attr('action','livesession');
					$('#livesession').submit();*/
					var urls = $('[name=remediliveclassroomurl]').val();
					var url = urls+"?orgpersonid="+$('[name=orgpersonid]').val()+"&liveentrolmentid="+livesessionschedulelist[0].livesessionenrollmentid;
					$(this).attr({href:url,target:"_blank"});
				}
				else{
					$('[name=coursetitle]').val($(".crstitle").text());
					$('[name="livesessionid"]').val(livesessionid);
					$('[name="price"]').val(priceval);
					$('[name="courseid"]').val(courseid);
					$('[name="courseenrollmentid"]').val(courseenrollmentid);
						$('#livesession').attr('method','post');
						$('#livesession').attr('action','schedulepayment');
						$('#livesession').submit();							
				}
			});
			
			$('.duration').each(function(){
				var first=$(this).attr('start');
				var second=$(this).attr('end');
				/*var startstime=first.split(' ');
				var endsstime=second.split(' ');*/

				var a="";
				var b="";
				/*if(startstime[1]=="PM"){
					var starttime = startstime[0].split(':');
					a=parseInt(starttime[0])+12+":"+starttime[1]+":00";
				}
				else{
					a=startstime[0]+":00";
				}

				if(endsstime[1]=="PM"){
					var endtime = endsstime[0].split(':');
					b=parseInt(endtime[0])+12+":"+endtime[1]+":00";
				}
				else{
					b=endsstime[0]+":00";
				}*/
				
				var a1=first.split(' ');
				var b1=a1[0].split(':');
				if(a1[1]=="PM"){
					if(b1[0]=="12"){
						a=b1[0]+":"+b1[1]+" "+":00";
					}
					else{
						a=parseInt(b1[0])+12+":"+b1[1]+" "+":00";
					}
					
				}
				else if(a1[1]=="AM"){
					if(b1[0]=="12"){
						a="00:"+b1[1]+" "+":00";	
					}
					else if(parseInt(b1[0])<10){
						a="0"+b1[0]+":"+b1[1]+" "+":00";	
					}
					else{
						a=b1[0]+":"+b1[1]+" "+":00";
					}
				}
				else{
					a=b1[0]+":"+b1[1]+" "+":00";
				}
				
				var c=second.split(' ');
				var d=c[0].split(':');
				if(c[1]=="PM"){
					if(d[0]=="12"){
						b=d[0]+":"+d[1]+" "+":00";
					}
					else{
						b=parseInt(d[0])+12+":"+d[1]+" "+":00";
					}
				}
				else if(c[1]=="AM"){ 
					if(d[0]=="12"){
					b="00:"+d[1]+" "+":00";
					}
					else if(parseInt(d[0])<10){
						b="0"+d[0]+":"+d[1]+" "+":00";
					}
					else{
						b=d[0]+":"+d[1]+" "+":00";
					}
				}
				else{
					b=d[0]+":"+d[1]+" "+":00";
				}

				function toSeconds(time_str) {
					// Extract hours, minutes and seconds
					var parts = time_str.split(':');
					// compute  and return total seconds
					return parts[0] * 3600 + // an hour has 3600 seconds
					parts[1] * 60 + // a minute has 60 seconds
					+
					parts[2]; // seconds
				}

				var difference = Math.abs(toSeconds(a) - toSeconds(b));

				// format time differnece
				var result = [
				              Math.floor(difference / 3600), // an hour has 3600 seconds
				              Math.floor((difference % 3600) / 60), // a minute has 60 seconds
				              difference % 60
				              ];
				// 0 padding and concatation
				result = result.map(function(v) {
					return v < 10 ? '0' + v : v;
				}).join(':');
				var range = result.split(':');
				$(this).text(range[0]+" hour"+" : "+range[1]+" min");
			});
		}
};

Rating={
		ratingdata:function(){
			var ratei=this;
			$('.ratestextarea').keyup(function(){
				el = $(this);
				if(el.val().length >= 501){
					el.val( el.val().substr(0, 500) );
				} else {
					$('.ratedestext').text(500-el.val().length);
				}
			});
			$('.rateitbtn').click(function(){
				courseratingdescription=$('.ratestextarea').val();
				if(courseratingdescription!=""){
					var courseratingobj={};
					courseratingobj.courserating=$('.newratediv>[name=score]').val();
					courseratingobj.courseid=$('.clid').val();
					courseratingobj.courseratingdescription=encodeURIComponent($('.ratestextarea').val());
					console.info("courseratingdescription.length() "+courseratingobj.courseratingdescription.length);
					ratei.rateCourseData(courseratingobj);
				}else{
					$('.reviewstatus').html("Your Review is Required");
					$('#mandatoryp').show().fadeOut(2000); 
				}
			});	
		},
		rateCourseData:function(courseratingobj){			
			if($('.ratestextarea').val()!=''){
				var savedRating=courseData.saveRating(courseratingobj);
				$('[name=score]').val('');
				$('.ratestextarea').val('');
				if(savedRating!=undefined){
					$('#ratesuccess').show().fadeOut(2000); 
					setTimeout(
					function() {$('#ratingmodal').modal('hide');},2000);
					previewcourse().review();
				}
				else{
					$('.reviewstatus').html("Problem in saving Your Review");
				}
			}			
		}
};
var previewcourse=(function(){
	var courseenrollmentid=$('[name=learnform] > [name=courseenrollmentid]').val();
	var cpath=courseData.getContextPath();
	var controller={
			info:function(){
				return Ajax.get(cpath+"/rest/lpreview/info/"+courseenrollmentid+"",undefined,"json","application/json");	
			},
			curriculum:function(){
				return Ajax.get(cpath+"/rest/lpreview/curriculum/"+courseenrollmentid+"",undefined,"json","application/json");
			},
			review:function(){
				return Ajax.get(cpath+"/rest/lpreview/reviews/"+courseenrollmentid+"",undefined,"json","application/json");
			},
			question:function(){
				return Ajax.get(cpath+"/rest/lpreview/questions/"+courseenrollmentid+"",undefined,"json","application/json");
			},
			answer:function(questionid){
				return Ajax.get(cpath+"/rest/lpreview/answers/"+questionid+"",undefined,"json","application/json");
			},
			newanswer:function(obj){
				return Ajax.post(cpath+"/rest/lpreview/newanswer/",obj,"json","application/json");
			},
			aboutauthor:function(personid){
				return Ajax.get(cpath+"/rest/professionalinfo/course/"+personid+"",undefined,"json","application/json");
			},
			learningstatus:function(){
				return Ajax.get(cpath+"/rest/lpreview/statuses/"+courseenrollmentid+"",undefined,"json","application/json");
			},
			quizDetails:function(obj){
				return Ajax.get(cpath+"/rest/lpreview/quizStatus?lectureids="+obj+"",undefined,"json","application/json");
			},
			share:function(obj){
				return Ajax.post(cpath+"/rest/share",obj,"json","application/json");
			},
			like:function(obj){
				return Ajax.post(cpath+"/rest/share/like",obj,"json","application/json");
			},
			ratingcourse:function(){
				return Ajax.get(cpath+"/rest/tutors/topthreeratingcourse",undefined,"json","application/json");
			},
			enrollstatus:function(courseid1){
				return Ajax.get(courseData.getContextPath()+"/rest/gpreview/enrollstatus/"+courseid1+"",undefined,"json","application/json");
			},

	};
	var model={
			info:function(){
				var list=controller.info();
				if(list=="NO_CONTENT"){
					return null;
				} else if(list=="INTERNAL_SERVER_ERROR"){
					return null;
				}else{
					return list;	 
				}	
			},
			curriculum:function(){
				var list= controller.curriculum();
				if(list=="NO_CONTENT"){
					return null;
				} else if(list=="INTERNAL_SERVER_ERROR"){
					return null;
				}else{
					return list;	 
				}	
			},
			review:function(){
				var list=controller.review();
				if(list=="NO_CONTENT"){
					return null;
				}else if(list=="INTERNAL_SERVER_ERROR"){
					return null;
				}else{
					return list;	 
				}
			},
			question:function(){
				var list=controller.question();
				if(list=="NO_CONTENT"){
					return null;
				} else if(list=="INTERNAL_SERVER_ERROR"){
					return null;
				}else{
					return list;	 
				}
			},
			answer:function(questionid){
				var list=controller.answer(questionid);
				if(list=="NO_CONTENT"){
					return null;
				} else if(list=="INTERNAL_SERVER_ERROR"){
					return null;
				}else{
					return list;	 
				}
			},
			newanswer:function(obj){
				var list=controller.newanswer(obj);
				if(list=="NO_CONTENT"){
					return null;
				}else{
					return list;	 
				}
			},
			aboutauthor:function(){
				return controller.aboutauthor($('.coursettl').attr('personid'));
			},
			learningstatus:function(){
				return controller.learningstatus();
			},
			quizDetails:function(obj){
				return controller.quizDetails(obj);
			},
			share:function(obj){
				return controller.share(obj);
			},
			like:function(obj){
				return controller.like(obj);
			},
			ratingcourse:function(){
				return controller.ratingcourse();
			},
			enrollstatus:function(courseid1){
				return controller.enrollstatus(courseid1);				
			},
	};

	var templates={
			info:function(){
				return "{{#each .}}<div class='row'><div class='col-md-12 whitebg pad10 boxshadow col-xs-12'>" +
				"<div class='col-md-8 col-xs-8'><div class='row'><div class='col-md-3 pad0 col-xs-4'><img class='height200 width100'" +
				"src='"+courseData.getContextPath()+"/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo'></div>" +
				"<div class='col-md-9 col-xs-8'><h5 >{{categoryname}}/{{subcategoryname}}</h5><h4 class='coursettl' " +
				"personid='{{personid}}'>{{coursetitle}}</h4><h6 class='lineheight20 justifytext'>{{coursedescription}}</h6>" +
				"</div></div></div><div class='col-md-4 col-xs-4'><div class='row martop20'><span class='pull-left'>" +
				"<b class='cl'>0</b> out of <b class='tl'>0</b> lectures Completed</span> " +
				"<span class='marright7 pull-right prg'>0%</span></b><br /><div class='mar0 progress progress-striped active marright7'" +
				"style='border-radius: 10px;'><div class='progress-bar progress-bar-striped bar prgbar'></div></div></div><div class='row martop20'>" +
				"<div class='btn-toolbar pull-right marright7'><div class='btn-group'><a class='btn btn-info likelink border1cfblue'" +
				"style='background: #3b5998;'> <i class='fa fa-facebook' style='margin-right: 7px;'></i> <i " +
				"class='fa fa-thumbs-up'></i></a><a class='btn btn-info sharelink border1cfblue' style='background: #3b5998; '><i " +
				"class='fa fa-facebook' style='margin-right: 7px;'></i> <i class='fa fa-share-alt'></i></a>" +
				"</div></div></div><div class='row martop20 righttext'><a class='btn btn-primary startcoursebtn marright7'><i" +
				"class='fa fa-play'></i> Start Course</a></div></div></div></div></div>{{/each}}";		
			}, 
			curriculum:function(){  
				return "<div class='boxshadow' id='lessonplan'> <div class='greybgwhiteborder section' data-toggle='collapse' data-target='#accorId' style='font-size:14px'><i class='fa fa-plus-square-o'> </i> <font size=3px>Lesson Plan</font> </div><div class='whitebg pad10 collapse' id='accorId' >" + 
 						"<table class='paddin' cellpadding=10 cellspacing=10 border=1 style='background-color:#D8EDFF'> <tr><td style='width: 30%;'>Lesson Title</td>" +
						" <td>Anatomy and Physiology of the Eye</td></tr><tr>" +
						"<td>Lesson Description</td>" + 
						"<td>The anatomy and physiology of the human eye is an important part of many courses." +
						" However for an ophthalmic assistant this is the main subject that should be taught in detail because, they are responsible for conducting vision tests, maintaining equipment’s, profile or obtain patient history, complete the particular tests, requested by the doctor, work with the patient as needed and provide assistance during eye surgeries." +
						"</td></tr>	<tr><td>Lesson Objectives</td>" +
						"<td>To enhance the ophthalmic assistants in the basic anatomy and physiology of the eye.</td>" +
						"</tr></table><br><table class='paddin' cellpadding=5 border=1 style='background-color:#E8FFF4 ' > <tr><td colspan=2 style='" +
						"font-size: 24px'>Lesson Plan</td></tr><tr> <td style='width: 30%;'>Preparation</td> " +
						"<td>What should be taken to the classroom session What should be deliver in the classroom session What " +
						"teaching materials could be aided to make the trainees understand the concept .(20 Minutes)</td> </tr> <tr> " +
						"<td>Classroom Session 1</td> <td>Anatomy of the Eye - Various parts of the eye, its anatomical structure" +
						" (40 Minutes)</td> </tr> <tr> <td>Classroom Session 2</td> <td>Physiology of the Eye – The functions of each " +
						"part of the eye, the diseases that affect each part-in brief (40 Minutes)</td> </tr> <tr> <td>Final Evaluation</td>" +
						" <td>PEvaluation using Objective Questions Labeling the parts Oral test – Formative evaluation Written test –" +
						" Summative evaluation</td> </tr> <tr> <td>Assignments</td> <td>Submit project on one of the topics: Label " +
						"the parts of the eye Two page assignments from books (from Library ) on Anatomy and Physiology of the eye </td> " +
						"</tr> <tr> <td>Additional reference</td> <td>Books , Website references , Videos</td> </tr> </table> " +
						" <br><table border=1 class='paddin' style='background-color:rgba(251, 229, 245, 0.54);' > " +
								"<tr><td colspan=2 style='font-size: 24px'>Tutor Preparation</td></tr><tr> " +
								"<td style='width: 30%;'>Preparation for Classroom Session</td> <td><ol><li> Keeping the teacher’s guide and go through " +
								"the lesson video and prepare the activities and other group discussions</li><li> Print the following " +
								"documents – make copies for each student Handout(We recommend that the tutor should print " +
								"this material and distribute to the trainees) Labeling image </li></ol></td> </tr> <tr> " +
								"<td>Materials to Print & Make Copies</td> <td><ol><li> Take Printout of the teacher’s guide</li> " +
								"<li>Take printout of handouts and make enough copies of the trainees</li></ol></td> </tr> </table><br>" +
								"  <table border=1 class='paddin' style='background-color: rgb(220, 246, 255);' > " +
										"<tr><td colspan=2 style='font-size: 24px'>Classroom Session 1</td></tr><tr> " +
										"<td style='width:30%'>Objective</td> <td>At the end of this session the trainee will be able to, " +
										"<ol> <li> Explain the formation of the eye (Embryology)</li> <li>List the major parts of the eye </li>" +
										"<li> Label external & internal parts of the eye</li><ol> </td> </tr> <tr> <td>Teaching Resources</td>" +
										" <td><ol><li> Download and read the Teacher’s Guide </li><li> Download the Powerpoint</li>" +
										" <li> Print the Handout – make copies for each student </li>" + 
										"<li> Print the Activity Printout – make copies for each student group Refer" +
										" the Assignment Section for additional activities for the students</li><ol> </td> </tr> " +
										"</table>   <table class='paddin' style='width:100%;background-color: rgb(220, 246, 255);' border=1> " +
										"<tr><td colspan=2 style='font-size: 24px'>Classroom Session 2</td></tr><tr >" +
										" <td style='width:30%' >Objective</td> <td>At the end of this session the trainee will be able to, " +
										"<ol><li>Describe the function of each major structures of the eye</li><li> Identify the parts of the eye </li> " +
										"<li> Explain how visual information travels to the brain</li></ol> </td> </tr> <tr> " +
										"<td>Teaching Resources</td> <td> <ol><li> Download and read the Teacher’s Guide</li> " +
										"<li>Download the Powerpoint</li><li> Print the Handout – make copies for each student</li> " +
										"<li>Print the Activity Printout – make copies for each student group</li> " +
										"<li>Refer the Assignment Section for additional activities for the students</li></ol> </td> </tr> </table> " +
										"<br><table class='paddin' border=1 style='background-color: rgb(216, 243, 212);' ><tr><td colspan=2 style='font-size: 24px'>Task</td></tr> <tr> " +
										"<td style='width:30%'>Instructions</td> <td>Download the Assignment Instruct the student to submit the assignment" +
										" within 2 Days </td> </tr> <tr> <td>Tasks</td> <td>Label the parts of the eye " +
										"Two page assignments from books (from Library ) on Anatomy and Physiology of the eye </td> " +
										"</tr> </table>" +
 						"</div>" +
						"</div>{{#each .}}<div class='boxshadow'> " + 
				"<div class='greybgwhiteborder section'>Section : {{sectiontitle}}</div> " +
				"<div class='whitebg pad10'><ul class='timeline'> " +
				"{{#each lectures}}<li class='lecture'><div class='hidden-xs timeline-badge contenticon' learnstatus='{{learnstatus}}' " +
				"contenttype='{{contentytpe}}'><i class='fa fa-play'></i></div> " +
				"<div class='timeline-panel'><div class='timeline-heading'><h5 class='timeline-title'> " +
				"<span class='badge marright7'>{{duration}}</span>" +
				"{{lecturetitle}}<span class='lecturelearnlink pull-right' lectureid='{{lectureid}}'>" +
				"<span class='label label-primary marright7 pointer'>{{#if timespent}}Time Spent : {{timespent}}{{else}}Not Yet Started{{/if}}</span>" +
				"<i class='fa fa-chevron-right pointer'></i></span></h5><small style'color: rgb(3, 157, 27);" +
				"font-size: 12px;display: inline;' class='satStatus' ><b style='color:cornflowerblue'>Exercise : </b>" +
				"Total no.of questions:&nbsp;&nbsp<strong class='strong set-total' " +
				"style='color: forestgreen;font-size: 9pt;'>{{total}}</strong>&nbsp;&nbsp|&nbsp;&nbspAnswered:&nbsp;&nbsp" +
				"<strong class='strong set-ans' style='color: forestgreen;font-size: 9pt;'>  {{answered}}  </strong>&nbsp;&nbsp| " +
				"&nbsp;&nbspCorrect:&nbsp;&nbsp<strong class='strong set-crt' style='color: forestgreen;font-size: 9pt;'>" +
				"  {{correct}}  </strong></small></div></div></li>{{/each}}</ul></div></div>{{/each}}";
			},
			review:function(){
				return 	"<div id='review' class='active'><ul class='comments'>{{#if .}}{{#each.}}<li class='clearfix'>" +
				"<img src='"+courseData.getContextPath()+"/OpenFile?r1=serverpath&r2={{raterphoto}}&filetype=photo' class='avatar' alt=''>"+
				"<div class='post-comments'><p class='meta'>On {{{ratingdate ratingdate}}} <a >{{raterfname}}</a>" +
				" rated :<span class='ratediv' style='margin-left:10px;' data-score='{{rating}}'></span></p>"+
				"<p style='text-overflow: ellipsis;overflow: hidden;'>{{ratingdescription}}</p></div></li>{{/each}}{{else}}" +
				"<h5 style='text-align:center;'>No Reviews Given</h5>{{/if}}</ul></div>";
			},
			question:function(){
				return 	"<div id='discussion' class=''><ul class='comments'>{{#if .}}{{#each .}}<li class='clearfix'>" +
				"<img src='"+courseData.getContextPath()+"/OpenFile?r1=serverpath&r2={{raterphoto}}&filetype=photo' class='avatar' alt=''><div class='post-comments'>" +
				"<p class='meta'>On {{askeddate askeddate}}<a> {{raterfname}} </a> asked : " +
				"<a><i class='pull-right'><small class='anslink ans{{questionid}}' questionid='{{questionid}}'>Answers</small></i></a></p>" +
				"<p style='text-overflow: ellipsis;overflow: hidden;'>{{question}}" +
				"<a><i class='pull-right'><small class='replylink' question='{{question}}' questionid='{{questionid}}'>Reply</small></i></a></p>" +
				"</div><div id='answers{{questionid}}'></div>" +
				"</li>{{/each}}{{else}}<h5 style='text-align:center;'>No Discussions</h5>{{/if}}</ul></div></div>";
			},
			answer:function(){
				return 	"<div id='discussion' class=''><ul class='comments'>{{#if .}}{{#each .}}<li class='clearfix'>" +
				"<img src='"+courseData.getContextPath()+"/OpenFile?r1=serverpath&r2={{answeredphoto}}&filetype=photo' " +
				"class='avatar' alt=''><div class='post-comments'>" +
				"<p class='meta'>On {{askeddate answereddate}}<a> {{answeredfname}} </a> answered : </p><p>{{answer}}" +
				"<i class='pull-right'></i></p>" +
				"</div></li>{{/each}}{{else}}<li><h5 style='text-align:center;'>No Answers</h5></li>{{/if}}</ul></div></div>";
			},
			aboutauthor:function(){
				return "<div class='ligtgreenbg pad1010'>" +
				"<h3 class='martopbottom10 centertext white'>About Author</h3>" +
				"<img src='"+courseData.getContextPath()+"/OpenFile?r1=serverpath&r2={{photourl}}&filetype=photo' " +
				"class='height140 border50per marcenterimage' alt='avatar'>" +
				"</div></div><div class='span12'><div class='details padtop50 centertext'><h4>{{authorname}} " +
				"<i class='fa fa-sheild'></i></h4><h5 class='lineheight20'>{{aboutauthor}}</h5>" +
				"{{#if aboutauthor}}<div><span>{{designation}} @ </span><span class='breakword'>{{organization}}</span></div><div>{{award}}</div>{{/if}}" +
				"<div class='martop10 marbottom10'><div class='social'>{{#if facebook}}<a href='http://{{facebook}}'" +
				" target='new' class='[ social-icon facebook ] roundicon fbbg white animate'><i class='fa fa-facebook'>" +
				"</i></a>{{/if}}{{#if twitter}}<a href='http://{{twitter}}' target='new'" +
				" class='[ social-icon twitter ] roundicon twittbg animate white'><i class='fa fa-twitter'></i>" +
				"</a>{{/if}}{{#if linkedin}}<a href='http://{{linkedin}}' target='new' " +
				"class='[ social-icon linkedin ] roundicon linkinbg animate white'><i class='fa fa-linkedin'>" +
				"</i></a>{{/if}}{{#if orgurl}}<a href='http://{{orgurl}}' target='new'" +
				" class='[ social-icon github ] roundicon ligtgreenbg white animate'><i class='fa'>Org</i></a>" +
				"{{/if}}{{else}}</div></div>'";
			},
			share:function(msg){
				if(msg=="CREATED"){
					return "<div class='alert alert-success' style=''>" +
					"<a class='close' data-dismiss='alert'>&times;</a><strong id='sharestatus' " +
					"style='text-align:center;'>The course has been successfully shared in facebook</strong></div>";
				}else{
					return "<div class='alert alert-danger' style=''>" +
					"<a class='close' data-dismiss='alert'>&times;</a><strong id='sharestatus' " +
					"style='text-align:center;'>Problem in sharing the course in facebook</strong></div>";
				}
			},
			like:function(msg){
				if(msg=="CREATED"){
					return "<div class='alert alert-success' style=''>" +
					"<a class='close' data-dismiss='alert'>&times;</a><strong id='sharestatus' " +
					"style='text-align:center;'>You liked the course in facebook</strong></div>";
				}else{
					return "<div class='alert alert-error' style=''>" +
					"<a class='close' data-dismiss='alert'>&times;</a><strong id='sharestatus' " +
					"style='text-align:center;'>Problem in liking the course in facebook</strong></div>";
				}
			},
			facebookcount:function(){
				return "<div class='facecount centertext martop7'>" +
				"<span class='white btn btn-default fbbg fbcount marright7' rel='tooltip' data-placement='top' " +
				"data-original-title='{{likecountexp}} likes'>{{likecount}}&nbsp;&nbsp;&nbsp;" +
				"<i class='fa fa-thumbs-o-up'></i> </span><span class='marright7 white btn btn-default fbbg fbcount'" +
				"rel='tooltip' data-placement='top' data-original-title='{{commentcountexp}} comments'>" +
				"{{commentcount}}&nbsp;&nbsp;&nbsp;<i class='fa fa-comment-o'></i> </span>" +
				"<span class='marright7 white btn btn-default fbbg fbcount' rel='tooltip' data-placement='top' " +
				"data-original-title='{{sharecountexp}} shares'>{{sharecount}}&nbsp;&nbsp;&nbsp;" +
				"<i class='fa fa-share-alt'></i></span><br></div>";
			},
			ratingcourse:function(){
				return "<form method='post' name='previewuserform'>" +
						"<input type='hidden' name='courseid' value=''>"+
						"<input type='hidden' name='courseenrollmentid' value=''>"+
				"<h5 class='greybgwhiteborder pad10 mar0'>Suggested Courses</h5>" +
				"{{#each .}}"+
							"<div class='col-md-12 pad5 whitebg boxshadow popcourselink' courseid='{{courseid}}'>"+
									"<div class='col-md-4 pad0'>"+
										"<a><img src='"+courseData.getContextPath()+"/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo'"+
											"style='height: 87px;'height='120' border='0' class='width100 boxshadow'></a></div>"+
									"<div class='col-md-8 padright0'>"+
										"<a class='categorydescription pad0'> {{coursetitle}}</a>"+
										"<div class='icon_price' price='{{price}}' priceicon='fa-inr' style='color:#0E800E;'>{{price}}</div>"+
										"<span class='ratediv' data-score='{{averagerating}}'></span></div></div></div>"+
								"{{/each}}</form>";
			}
	};
	var objects={
			newanswer:function(questionid,answerdesc){
				return obj={
						questionid:questionid,
						answerdesc:answerdesc
				};
			}
	};
	var view={
			coursepreview:function(){
				this.curriculum();
				this.info();	
				this.review();
				this.aboutauthor();
				this.ratingcourse();
			},
			discussionevent:function(){
				$('.discussionholder').click(function(){
					var qlist= model.question();
					Handlebars.registerHelper('askeddate', function(askeddate) { 
						var d = new Date(parseInt(askeddate));
						var date =d.toLocaleString();
						return date;
					});
					view.question(qlist);
				});
			},
			scheduleevent:function(){
				$('.scheduleholder').click(function(){
					manageschedule.loadSchedule();
				});
			},
			info:function(){
				var list= model.info();
				helper.render(templates.info(),list,"#infoholder");
				var price=$(this).attr('price');
				console.info(list[0].price);
				if(list[0].price=="Free"){
					$('.giftcls').hide();
				}else{
					$('.giftcls').show();
				}
				$('[name=courseid]').val(list[0].courseid);
				$('[name=coursetitle]').val(list[0].coursetitle);
				$('[name=courselogo]').val(list[0].courselogo);				
				$('.crstitle').text(list[0].coursetitle);
				$('[name=price]').val(list[0].price);
				$('[name=priceid]').val(list[0].priceid);
				var clectures=$('.contenticon[learnstatus=Completed]').length;
				var tlectures=$('.contenticon').length;
				$('.cl').text(clectures);
				$('.tl').text(tlectures);
				var percent=(clectures/tlectures)*100;			
				$('.prg').html(percent.toFixed(2)+"%");
				$('.prgbar').css('width',percent.toFixed(2)+"%");
				$('.prgbar').text(percent.toFixed(2)+"%");
				var obj={
						link:"http://"+$('#metaURI').html()+cpath+"/preview?courseid="+list[0].courseid,
						name:list[0].coursetitle,
						caption:"REAMEDI CLOUD",
						description:list[0].coursedescription,
						message:"REAMEDI CLOUD",
						picture:"http://"+$('#metaURI').html()+cpath+"/OpenFile?r1=serverpath&r2="+list[0].courselogo
				};
				$(".sharelink").click(function(){
					var sharestatus=model.share(obj);
					if(sharestatus!="NOT_ACCEPTABLE"){
						helper.render(templates.share(sharestatus),null,"#facebookalert");
						setTimeout(function(){
							$('#facebookalert').hide();
						},2000);
					}else{
						$('.sharelink').attr('href',cpath+'/rest/publicuser/facebook?redirecturi=/app/previewcourse?courseenrollmentid='+$('[name=courseenrollmentid]').val());
					}
				});
				$(".likelink").click(function(){
					var liketatus=model.like(obj);
					if(liketatus!="NOT_ACCEPTABLE"){
						helper.render(templates.like(liketatus),null,"#facebookalert");
						setTimeout(function(){
							$('#facebookalert').hide();
						},2000);
					}else{
						$('.likelink').attr('href',cpath+'/rest/publicuser/facebook?redirecturi=/app/previewcourse?courseenrollmentid='+$('[name=courseenrollmentid]').val());
					}
				});	
				$('.startcoursebtn').click(function(){
					$('[name=learnform]').attr('action','learnCourse');
					$('[name=learnform]').submit();	
				});
			},
			curriculum:function(){
				var list= model.curriculum();
				var modifiedList=helper.curriculum(list);
				helper.render(templates.curriculum(),modifiedList,"#curriculumholder");
				if($('#rolename').val()=='tutor') {
					$('#lessonplan').show();
				} else { 
					$('#lessonplan').hide();
				}
				$('.contenticon').each(function(){
					var ctype=$(this).attr('contenttype');
					switch(ctype){
					case "pdf":
						$(this).html("<i class='fa fa-file-pdf-o'></i>");
						break;
					case "video":
						$(this).html("<i class='fa fa-video-camera'></i>");
						break;
					case "swf":
						$(this).html("<img style='height:20px !important;' src='"+courseData.getContextPath()+"/assets/app/images/contenttype/flashicon.png'/>");
						break;
					case "NO_CONTENT":
						console.info("this is NO_CONTENT ");
						break;
					}
					var lstat=$(this).attr('learnstatus');
					switch(lstat){
					case "Completed":
						$(this).css("background","rgb(107, 177, 107)");
						break;
					case "learning":
						$(this).css("background","rgb(255, 198, 94)");
						break;
					case "notyet":
						$(this).css("background","rgb(218, 218, 218)");
						break;
					}
				});
				$('.lecturelearnlink').click(function(){
					$('[name=courselectureid]').val($(this).attr('lectureid'));
					$('[name=learnform]').attr('action','learnCourseP');
					$('[name=learnform]').submit();	
				});
				this.summary();
			},
			summary:function(){
				$('.seccount').text($('.section').length);
				$('.leccount').text($('.lecture').length);
				$('.pdfcount').text($('.contenticon[contenttype=pdf]').length);
				$('.vidcount').text($('.contenticon[contenttype=video]').length);
			},
			review:function(){
				var fivestar,fourstar,threestar,twostar,onestar,zerostar=0;
				var list= model.review();
				var clist=_.pluck(list,'rating');
				var orgprsonid=$('.hiddenclss').attr('orgpersonid');
				if(clist.length!=0){
					var b = (clist.toString()).split(',').map(Number);
					var obj = { };
					for (var i = 0, j = b.length; i < j; i++) {
						obj[b[i]] = (obj[b[i]] || 0) + 1;
					}
					fivestar=Math.round((obj[5]/clist.length)*100);
					if(isNaN(fivestar)){
						$('.fivestar').text(0);
						$('.fiveper').attr('style','width:0%');
					}else{
						$('.fivestar').text(obj[5]);
						$('.fiveper').attr('style','width:'+fivestar+'%');
					}
					fourstar=Math.round((obj[4]/clist.length)*100);
					if(isNaN(fourstar)){
						$('.fourstar').text(0);
						$('.fourper').attr('style','width:0%');
					}else{
						$('.fourstar').text(obj[4]);
						$('.fourper').attr('style','width:'+fourstar+'%');
					}
					threestar=Math.round((obj[3]/clist.length)*100);
					if(isNaN(threestar)){
						$('.threestar').text(0);
						$('.threeper').attr('style','width:0%');
					}else{
						$('.threestar').text(obj[3]);
						$('.threeper').attr('style','width:'+threestar+'%');
					}
					twostar=Math.round((obj[2]/clist.length)*100);
					if(isNaN(twostar)){
						$('.twostar').text(0);
						$('.twoper').attr('style','width:0%');
					}else{
						$('.twostar').text(obj[2]);
						$('.twoper').attr('style','width:'+twostar+'%');
					}
					onestar=Math.round((obj[1]/clist.length)*100);
					if(isNaN(onestar)){
						$('.onestar').text(0);
						$('.oneper').attr('style','width:0%');
					}else{
						$('.onestar').text(obj[1]);	
						$('.oneper').attr('style','width:'+onestar+'%');
					}
					zerostar=Math.round((obj[0]/clist.length)*100);
					if(isNaN(zerostar)){
						$('.zerostar').text(0);	
						$('.zeroper').attr('style','width:0%');
					}else{
						$('.zerostar').text(obj[0]);
						$('.zeroper').attr('style','width:'+zerostar+'%');
					}
					sum = _.reduce(b, function(memo, num){ return memo + num; }, 0);
					var avg=sum/(clist.length);
					$('.totreviews').text(clist.length);
					$('.rating-num').text(avg.toFixed(1));
					$('.avgrate').attr('data-score',avg);
					var myreviewboolean=_.where(list,{orgpersonid:parseInt(orgprsonid)});
					if(myreviewboolean.length==0){
						$('.ownreview').show();
						$('.ownrating').hide();
					}else{
						$('.ownreview').hide();
						$('.ownrating').show();
						$('.myrate').attr('data-score',myreviewboolean[0].rating);
					}
				}
				else{
					$('.rating-num').text(0.0);
					$('.avgrate').attr('data-score',0);
					$('.ownreview').attr('style','display:');
				}
				Handlebars.registerHelper('ratingdate', function(ratingdate) { 
					var d = new Date(parseInt(ratingdate));
					var date =d.toLocaleString();
					return date;
				});
				helper.render(templates.review(),list,"#reviewholder");
				$('.ratediv').each(function(){
					var rating=$(this).attr('data-score');
					$(this).raty({ readOnly: true,score: rating, 
						starHalf      : courseData.getContextPath()+"/"+'assets/app/images/star/star-half.png',
						starOff       : courseData.getContextPath()+"/"+'assets/app/images/star/star-off.png', 
						starOn        : courseData.getContextPath()+"/"+'assets/app/images/star/star-on.png', 
					});
				});
			},
			question:function(qlist){
				helper.render(templates.question(),qlist,"#discussionholder");
				$('.anslink').click(function(){
					view.answer($(this).attr('questionid'));
				});
				$('.replylink').click(function(){
					$('#answerModal').modal('show');
					$('.answererror').hide();
					$('#answerit').attr('questionid',$(this).attr('questionid'));
					$('.qntxt').text($(this).attr('question'));
					$('.charremaining').text("500");
					$('.qnans').keyup(function(){
						el = $(this);
						if(el.val().length >= 501){
							el.val( el.val().substr(0, 500) );
						} else {
							$('.charremaining').text(500-el.val().length);
						}
					});
				});
				$('#answerit').click(function(){
					$('.succansalert').hide();
					$('.failcansalert').hide();
					var questionid=$(this).attr('questionid');
					var answer=$('.qnans').val().trim();
					var answelength=$('.qnans').val().trim().length;
					if(answelength!=0){
						$('.answererror').hide();
						var obj=objects.newanswer(questionid,answer);
						var updateanswer=model.newanswer(obj);
						if(updateanswer=="OK"){
							$('.qnans').val('');
							$('.succansalert').show();
							$('.failcansalert').hide();
							setTimeout(function(){
								$('.succansalert').hide();
								$('.ans'+questionid).trigger('click');
								$('#answerModal').modal('hide');
							},2000);
						}else{
							$('.succansalert').hide();
							$('.failcansalert').show();
						}

					}else{
						$('.answererror').show();
					}
				});
			},
			answer:function(questionid){
				var list=model.answer(questionid);
				helper.render(templates.answer(),list,"#answers"+questionid);
			},
			aboutauthor:function(){
				var list= model.aboutauthor();
				var modifiedauthorlist=helper.aboutauthor(list);
				helper.render(templates.aboutauthor(),modifiedauthorlist,"#authorholder");
			},
			ratingcourse:function(){
				var list= model.ratingcourse();
				if(list.length>0){
					helper.render(templates.ratingcourse(),list,"#ratingcourses");	
				$('.icon_price').each(function(){
					var price=$(this).attr('price');
					var priceic= $('#priceicon').text();
					var priceicon = /*$(this).attr('priceicon');*/priceic;
					if(price=="Free"){
						$(this).html("Free");
					}else if(price==""){
						$(this).html("");
					}else{
						$(this).html("<i class='fa "+priceicon+"'></i>"+price);
					}
				});
				$('.ratediv').each(function(){
					var rating=$(this).attr('data-score');
					$(this).raty({ readOnly: true,score: rating, 
						starHalf      : courseData.getContextPath()+"/"+'assets/app/images/star/star-half.png',
						starOff       : courseData.getContextPath()+"/"+'assets/app/images/star/star-off.png', 
						starOn        : courseData.getContextPath()+"/"+'assets/app/images/star/star-on.png', 
					});
				});
				$('.popcourselink').click(function(){
					var list = model.enrollstatus($(this).attr('courseid'));
					if(list==="NO_CONTENT"){
						$('[name=courseid]').val($(this).attr('courseid'));
						$('[name=previewuserform]').attr('action','previewuser');
						$('[name=previewuserform]').submit();
					}else{
						switch(list[0].courseenrollmentstatus){
						case "A":
							$('[name=courseenrollmentid]').val(list[0].courseenrollmentid);
							$('[name=previewuserform]').attr('action','previewcourse');
							$('[name=previewuserform]').submit();	
							break;
						case "C":
							$('[name=courseenrollmentid]').val(list[0].courseenrollmentid);
							$('[name=previewuserform]').attr('action','previewcourse');
							$('[name=previewuserform]').submit();	
							break;
						case "W":
							$('[name=courseid]').val(courseid);
							$('[name=wishlisted]').val("wishListed");
							$('[name=courseenrollmentid]').val(list[0].courseenrollmentid);
							$('[name=previewuserform]').attr('action','previewuser');
							$('[name=previewuserform]').submit();	
							break;
						case "P":
							$("#msgmodal").modal("show");
							break;
						}							
					}
				});
				}
			},
			loadSatStatus:function(list){
				helper.loadSatStatus(list);
			},

	};
	var helper={
			render:function(tmpl,list,outputselector){
				var template = Handlebars.compile(tmpl);
				var templateHtml = template(list);
				$(outputselector).html(templateHtml);
			},
			curriculum:function(list){
				var learningstatuslist=model.learningstatus();
				var lectureids=_.pluck(list, 'lectureid');
				var statusList=Ajax.get(courseData.getContextPath()+"/rest/lpreview/quizStatus?lectureids="+lectureids.toString(),null,"json","application/json");

				sections = [];
				sectionids =_.uniq(_.pluck(list,'sectionid'));
				if(sectionids!='' && sectionids!=undefined){
					_.each(sectionids,function(sectionid){
						var sectionarray = _.where(list,{sectionid:sectionid});
						var lectureOutputArray = [];
						var section = {};
						section.sectionid = sectionid;
						section.sectiontitle = sectionarray[0].sectiontitle;
						lectureOutputArray=[];
						var lectureids=_.uniq(_.pluck(sectionarray,'lectureid'));
						_.each(lectureids,function(lectureid){
							lectureArray = _.where(sectionarray,{lectureid:lectureid});
							if(lectureArray[0]){
								var courselectureid=lectureArray[0].lectureid;
								if(courselectureid!=undefined){
									var lecture=_.pick(lectureArray[0],'lectureid','lecturetitle','contentpath','duration');
									var lecturestatusarray = _.where(learningstatuslist,{courselectureid:lectureid});
									var quizstatusarray = _.where(statusList,{courselectureid:lectureid});
									if(lecturestatusarray[0]){
										lecture.learnstatus=lecturestatusarray[0].learningstatus;
										lecture.timespent=lecturestatusarray[0].timespent;

									}else{
										lecture.learnstatus="notyet";
										lecture.timespent=null;
									}
									if(quizstatusarray[0]){
										lecture.correct=quizstatusarray[0].correct;
										lecture.skip=quizstatusarray[0].skip;
										lecture.wrong=quizstatusarray[0].wrong;
										lecture.total=parseInt(lecture.correct)+parseInt(lecture.skip)+parseInt(lecture.wrong);
										lecture.answered=parseInt(lecture.correct)+parseInt(lecture.wrong);
									}else{
										lecture.correct="0";
										lecture.skip="0";
										lecture.wrong="0";
										lecture.total="0";
										lecture.answered="0";
									}
									var contentype=lectureArray[0].contentpath;
									if(contentype==undefined){
										lecture.contentytpe="nocontent";
										lecture.filename="No Content";
									}
									else{
										var filetypesplit= contentype.split('.');
										var filetype= filetypesplit[filetypesplit.length-1];
										var filenamesplit= contentype.split('/');
										var filename= filenamesplit[filenamesplit.length-1];
										lecture.filename=filename;
										if(filetype=="pdf"){
											lecture.contentytpe="pdf";
										}
										else if(filetype=="swf"){
											lecture.contentytpe="swf";
										}
										else{
											lecture.contentytpe="video";
										}
									}
									lectureOutputArray.push(lecture);
								}
							}
						});
						section.lectures=lectureOutputArray;
						sections.push(section);
					});	
				}			
				return sections;
			},

			aboutauthor:function(workexperienceList){
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
				return professionaldetails;
			},
	};
	return {
		init:function(){
			initialize();
			view.discussionevent();
			view.scheduleevent();
		},
		review:function(){
			view.review();
		},
		
	};	
	function initialize(){
		view.coursepreview();
		$('.giftcoursebtn').click(function(){
			$('[name=learnform]').attr('action','giftcourse');
			$('[name=learnform]').submit();
		});
		$('.fbcaret').click(function(){
			$("#frep").toggle("slow");
			var facebookurl={};
			facebookurl.link="http://"+$('#metaURI').html()+courseData.getContextPath()+"/preview?courseid="+$('.clid').val();
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
				var result=largeBitConversion(facebookcountlist[0][properties[i]]);
				facebookcountlist[0][properties[i]]=result;
			};
			if(facebookcounttmp.length>0){
				facebookcountlist[0].likecountexp=facebookcounttmp[0].likecount;
				facebookcountlist[0].sharecountexp=facebookcounttmp[0].sharecount;
				facebookcountlist[0].commentcountexp=facebookcounttmp[0].commentcount;
				facebookcountlist[0].totalcountexp=facebookcounttmp[0].totalcount;
			}
			helper.render(templates.facebookcount(),facebookcountlist[0],"#frep");
			$('.carets').removeClass('fa-caret-square-o-down');
			$('.carets').addClass('fa-caret-square-o-up');
			
			
		});
	}
	function largeBitConversion(value){
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
});