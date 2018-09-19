$(document).ready(function(){

	$('#roadmap').click(function(){
		$('#calendar').hide();
	});
	$('#basicli').click(function(){
		$('#calendar').hide();
	});
	$('#contentli').click(function(){
		$('#calendar').hide();
	});
	$('#courselogoli').click(function(){
		$('#calendar').hide();
	});
	$('#examTabs').click(function(){
		$('#calendar').hide();
	});

	$('#datetimepicker1').datetimepicker({
		format: 'LT'
	});
	$('#datetimepicker2').datetimepicker({
		format: 'LT'
	});

	var datetime = courseData.getcurrentdatetime();
	$('#calendar').fullCalendar({
		header: {
			left: 'prev,next today',
			center: 'title',
			right: ''
		},

		selectable: true,

		defaultView: 'month',
		
		dayClick: function(date, allDay, jsEvent, view) {

			var dates = datetime[0].split('-');
			var datime = dates[2]+"-"+dates[1]+"-"+dates[0];
			var now = new Date(datime);
			if (date.setHours(0,0,0,0) < now.setHours(0,0,0,0)){
				alert("Schedule should be allocated in current and future date only");
				$('#scheduleModal').hide();
			}
			else{
				$('#scheduleModal').modal({
					backdrop: true,
					keyboard: true,
				});
				manageCourse.sessionDate(date);
				$("#cancel").trigger('click');

			}

		},


		viewDisplay:function(view) {
			var date = datetime[0].split('-');
			var datime = date[2]+"-"+date[1]+"-"+date[0];
			$('.fc-day')
			.each(function(){
				$(this).removeClass('fc-today fc-state-highlight');	
				if($(this).attr('data-date')==datime){
					$(this).addClass('fc-today fc-state-highlight');
				}
			});
			manageCourse.sessionInfo();    
		}

	});
	/*$('#datetimepicker1').datetimepicker({ pickDate: false});
$('#datetimepicker2').datetimepicker({ pickDate: false});*/

	//$('.tabtable').show();
	var courseid=$('[name=courseid]').val();
	//var splitdate=$('[name=splitdate]').val();
	manageCourse.loadPageInfo(courseid);
	//SelfAssesment.Views.addMore();
	$('#scheduleli').click(function(){
		$('#schedule').show();
		$('#calendar').fullCalendar('render');
		$('#calendar').show();
	});

	$('.learningtablinks').click(function(){
		if($(this).attr("href")=="#examInfo"){
			$('#content').hide();
		}
	});
	$('.quizbtn').click(function(){
		$('#content').hide();
	});
	$('.closebtn').click(function(){
		$('#alertmessage').hide();
	});
	$('.scheduleupdate').hide();
});

manageCourse={
		loadPageInfo:function(courseid){
			var self=this;
			self.loadcontent(courseid);
			self.renderContentImages();		
			self.roadmapTemplate();
			self.createupdateprocess();
		},

		sessionInfo:function(){
			var self=this;
			self.schedulesession();

		},
		/* *******************************  Livesession schedule process ************************************** */
		sessionDate:function(date){
			var self=this;
			self.pickdate(date);
		},


		pickdate:function(date){

			var self = this;
			selectdate = date;
			$('#selected').attr("value", selectdate);
			var dateTimeSplit = $('#selected').val().split(' ');
			var dateSplit = dateTimeSplit[1].split('-')+ "-"+dateTimeSplit[2].split('-')+"-"+dateTimeSplit[3].split('-');
			$('#splitdate').attr("value", dateSplit);

			var courseid=$('[name=courseid]').val();
			var scheduledate=$('[name=splitdate]').val();
			var schedulelist = courseData.getTimecourseDetails(courseid,scheduledate);

			$('.enrolldate').text(scheduledate);
			self.renderliveschedule(schedulelist);

			/* validate for schedule price ***************** */
			$('.price').click(function(){
				$(this).addClass('active');
				$('#schedulepriceid').val('');
				$('#schedulepriceid').show();
				$('.free').removeClass('active');
				$('#schedulepriceid').addClass('liveerror');
			});

			$('.free').click(function(){
				$(this).addClass('active');
				$("#schedulepriceid").val("Free");	
				$("#schedulepriceid").hide();
				$('.price').removeClass('active');
				$('#schedulepriceid').removeClass('liveerror');	
				$("#schedulepriceiderror").hide();
			});
		},

		renderliveschedule:function(schedulelist){
			self = this;
			renderTemplate("#timetemplate", schedulelist, "#timetable");

			$('.editlive').click(function(){
				$('.alert-dismissible').hide();

				var livesessionid=$(this).attr('livesessionid');
				/*var livesessionidobj={};
				livesessionidobj.livesessionid=livesessionid;*/
				var livesessionschedulelist = courseData.checkeditdeletelivesession(livesessionid);

				if(livesessionschedulelist.length>0){
					$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('The Livesession is in enrolled. So cant edit').show();
					$("#cancel").trigger('click');
					setTimeout(function(){
						$('#alertsuccess').hide();										
					},2000);
				}
				else{
					var scheduleobj={};
					scheduleobj=livesessionid;
					var details=courseData.getEditSchedule(scheduleobj);

					if(details!=undefined){

						description=_.pluck(details,'description');
						starttime=_.pluck(details,'starttime');
						endtime=_.pluck(details,'endtime');
						price=_.pluck(details,'price');
						livesessionid=_.pluck(details,'livesessionid');

						$('.sessiondescription').val(description);
						$('.starttime').val(starttime);
						$('.endtime').val(endtime);
						$('#schedulepriceid').show();
						$('#schedulepriceid').val(price);


						if(price=="Free")
						{
							$('.free').addClass('active');	
							$("#schedulepriceid").val("Free");	
							$("#schedulepriceid").hide();
							$('.price').removeClass('active');
							$('#schedulepriceid').removeClass('liveerror');	
							$("#schedulepriceiderror").hide();
						}
						else{
							$('.price').addClass('active');	
							$('.free').removeClass('active');
							$('#schedulepriceid').addClass('liveerror');	
							$("#schedulepriceiderror").hide();
						}
						$('.schedulesave').hide();
						$('.scheduleupdate').attr('livesessionid',livesessionid).show();						
					}
				}
			});
			$('.deletelive').click(function(){
				var livesessionid=$(this).attr('livesessionid');
				/*var livesessionidobj={};
				livesessionidobj.livesessionid=livesessionid;*/
				var livesessionschedulelist = courseData.checkeditdeletelivesession(livesessionid);

				if(livesessionschedulelist.length>0){
					$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('The Livesession is in enrolled. So cant trash').show();
					setTimeout(function(){
						$('#alertsuccess').hide();										
					},2000);
				}
				else{
					if(confirm("Are you sure want to delete?")){
						var livevalue = courseData.getLivestatusDetails(livesessionid);

						if(livevalue=="ACCEPTED"){
							$('#alertsuccess').removeClass('alert-danger').addClass('alert-success').text('Livesession has been deleted sucessfully').show();
							setTimeout(function(){
								$('#alertsuccess').hide();										
							},2000);
							manageCourse.sessionDate();
							manageCourse.sessionInfo();
						}
						else{
							$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('Livesession has been deleted failed').show();
							setTimeout(function(){
								$('#alertsuccess').hide();										
							},2000);
						}
					}
				}
			});
		},
		createupdateprocess:function(){

			$(".schedulesave").click(function(){
				$('.liveerror').trigger('focusout'); 

				var isError = "";
				$('.alert-dismissible').each(function(){
					if($(this).attr('style')!="display: none;"){
						isError="true";
					}
				});

				if(isError!="true"){
					var scheduledate = $('[name=splitdate]').val();
					var schedulelist = courseData.getTimeDetails(scheduledate);
					self.checkliveschedule(schedulelist);
				}
			});
			$(".scheduleupdate").click(function(){
				$('.liveerror').trigger('focusout'); 

				var isError = "";
				$('.alert-dismissible').each(function(){
					if($(this).attr('style')!="display: none;"){
						isError="true";
					}
				});

				if(isError!="true"){
					var scheduledate = $('[name=splitdate]').val();
					var schedulelist = courseData.getTimeDetails(scheduledate);
					var scheduleobj={};
					scheduleobj=$(this).attr('livesessionid');
					var details=courseData.getEditSchedule(scheduleobj);
					self.checkupdateliveschedule(details,schedulelist,$(this).attr('livesessionid'));
				}
			});
			$("#cancel").click(function(){
				$('.sessiondescription').val('');
				$('.starttime').val('');
				$('.endtime').val('');
				$('#schedulepriceid').val('');
				$('.price').trigger('click');
				$('.alert-dismissible').hide();
				$('.schedulesave').show();
				$('.scheduleupdate').hide();
			});
			/* validation for live session schedule process ***************** */
			$('.liveerror').on("focusout keyup",function(){
				var errorid="#"+$(this).attr('id')+"error";
				if(($(this).val().trim() == "")){
					$(errorid).show();
					$("#numbererror").hide();
				}
				else{
					$(errorid).hide();
				}
			});
			$("#schedulepriceid").keypress(function (e) {
				if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
				/*	$("#numbererror").show();
					$("#schedulepriceiderror").hide();*/
					return false;
				}
			});
		},
		checkliveschedule:function(schedulelist){
			self=this;
			
			var description = $('.sessiondescription').val().trim();
			var startstimeval = $('.starttime').val().trim();
			var endstimeval = $('.endtime').val().trim();
			var price=$('#schedulepriceid').val().trim();
			
			var livedata = {};
			livedata.price=price;
			livedata.description = description;
			livedata.starttime = startstimeval;
			livedata.endtime = endstimeval;

			var startstime ="";
			var a=startstimeval.split(' ');
			var b=a[0].split(':');
			if(a[1]=="PM"){
				if(b[0]=="12"){
					startstime=b[0]+":"+b[1]+" "+a[1];
				}
				else{
					startstime=parseInt(b[0])+12+":"+b[1]+" "+a[1];
				}
				
			}
			else if(a[1]=="AM"){
				if(b[0]=="12"){
					startstime="00:"+b[1]+" "+a[1];	
				}
				else if(parseInt(b[0])<10){
					startstime="0"+b[0]+":"+b[1]+" "+a[1];
				}
				else{
					startstime=b[0]+":"+b[1]+" "+a[1];
				}
			}
			else{
				startstime=b[0]+":"+b[1]+" "+a[1];
			}
			
			var endstime="";
			var c=endstimeval.split(' ');
			var d=c[0].split(':');
			if(c[1]=="PM"){
				if(c[0]=="12"){
					endstime=c[0]+":"+d[1]+" "+c[1];
				}
				else{
					endstime=parseInt(c[0])+12+":"+d[1]+" "+c[1];
				}
			}
			else if(c[1]=="AM"){
				if(d[0]=="12"){
					endstime="00:"+d[1]+" "+c[1];	
				}
				else if(parseInt(d[0])<10){
					endstime="0"+d[0]+":"+d[1]+" "+c[1];
				}
				else{
					endstime=d[0]+":"+d[1]+" "+c[1];
				}
			}
			else{
				endstime=d[0]+":"+d[1]+" "+c[1];
			}
			var dat = $('[name=splitdate]').val();
			var dat1=new Date(dat);
			var dat2 = dat1.toLocaleDateString();
			var dat3 = dat2.split("/");
			var dat4="";
			var d3 = "";
			if(10>parseInt(dat3[0])){
				if(10>parseInt(dat3[1])){
					dat4="0"+dat3[0];
					d3="0"+dat3[1];
				}
				else{
					d3=dat3[1];
				}
				
			}
			else{
				if(10>parseInt(dat3[1])){
					dat4=dat3[0];
					d3="0"+dat3[1];
				}
				else{
					d3=dat3[1];
				}
			}
			var dat5=d3+"-"+dat4+"-"+dat3[2];
			livedata.scheduledates = dat5;
			
			livedata.courseid = $('[name=courseid]').val();
			livedata.scheduledate = /*$('[name=splitdate]').val()*/dat;
			
			var time ="";
			var datetime = courseData.getcurrentdatetime();
			var timesp =datetime[1].split(":");
			if("12"<=timesp[0]){
				time = timesp[0]+":"+timesp[1]+" "+"PM";
			}
			else{
				time = timesp[0]+":"+timesp[1]+" "+"AM";
			}

			endtimeda=_.uniq(_.pluck(schedulelist,'endtime'));
			starttimeda=_.uniq(_.pluck(schedulelist,'starttime'));
			
				var isTrue = "";
				if(endtimeda.toString()!=""){
					_.each(endtimeda,function(endtimedataval){
						var endtimedata="";
						var a=endtimedataval.split(' ');
						var b=a[0].split(':');
						if(a[1]=="PM"){
							if(b[0]=="12"){
								endtimedata=b[0]+":"+b[1]+" "+a[1];
								isTrueorFalse(endtimedata);
							}
							else{
								endtimedata=parseInt(b[0])+12+":"+b[1]+" "+a[1];
								isTrueorFalse(endtimedata);
							}
							
						}
						else if(a[1]=="AM"){
							if(b[0]=="12"){
								endtimedata="00:"+b[1]+" "+a[1];
								isTrueorFalse(endtimedata);
							}
							else if(parseInt(b[0])<10){
								endtimedata="0"+b[0]+":"+b[1]+" "+a[1];
								isTrueorFalse(endtimedata);
							}
							else{
								endtimedata=b[0]+":"+b[1]+" "+a[1];
								isTrueorFalse(endtimedata);
							}
							
						}
					});
				}
				else{
					isTrue = "true";
				}
				isTrues= "";
				if(starttimeda.toString()!=""){
					_.each(starttimeda,function(starttimedataval){
						var starttimedata="";
						var a=starttimedataval.split(' ');
						var b=a[0].split(':');
						if(a[1]=="PM"){
							if(b[0]=="12"){
								starttimedata=b[0]+":"+b[1]+" "+a[1];
								isTruesorFalses(starttimedata);
							}
							else{
								starttimedata=parseInt(b[0])+12+":"+b[1]+" "+a[1];
								isTruesorFalses(starttimedata);
							}
						}
						else if(a[1]=="AM"){
							if( b[0]=="12"){
								starttimedata="00:"+b[1]+" "+a[1];
								isTruesorFalses(starttimedata);
							}
							else if(parseInt(b[0])<10){
								starttimedata="0"+b[0]+":"+b[1]+" "+a[1];
								isTruesorFalses(starttimedata);
							}
							else{
								starttimedata=b[0]+":"+b[1]+" "+a[1];
								isTruesorFalses(starttimedata);
							}
						}
					});
				}
				else{
					isTrues = "true";
				}

			function isTruesorFalses(starttimedata){
				if(startstime<=starttimedata && endstime<=starttimedata){
					isTrues = "true";	
				}
				else if(starttimedata<=startstime){
					isTrues = "true";
				}
				else{
					isTrues = "false";
				}
			}
			
			function isTrueorFalse(endtimedata){
				if(endtimedata<=startstime){
					isTrue = "true";
				}
				else{
					isTrue = "false";
				}
			}

			var splitdate="";
			var dates = datetime[0].split('-');
			splitdate = dates[1]+"-"+dates[0]+"-"+dates[2];
			 
			var dateval = $('[name=splitdate]').val();
			var date="";
			var daates = new Date(dateval);
			var splitda=daates.toLocaleDateString();
			var splitdaa=splitda.split('/');
			if(parseInt(splitdaa[0])<10){
				date = "0"+splitdaa[0]+"-"+splitdaa[1]+"-"+splitdaa[2];
			}
			else{
				date = splitdaa[0]+"-"+splitdaa[1]+"-"+splitdaa[2];
			}

			if(isTrue=="true" && isTrues=="true"){
				if(splitdate<date){
					if(startstime<endstime){
						$('#alertsuccess').hide();
						self.createliveschedule(livedata);
					}
					else{
						$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('To Time is must be greater than to start time').show();
						setTimeout(function(){
							$('#alertsuccess').hide();
							$('.endtime').val('');
						},2000);
					}
				}
				else{
					if(time<=startstime){
						if(startstime<endstime){
							$('#alertsuccess').hide();
							self.createliveschedule(livedata);
						}
						else{
							$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('To Time is must be greater than to start time').show();
							setTimeout(function(){
								$('#alertsuccess').hide();
								$('.endtime').val('');
							},2000);
						}
					}
					else{
						$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('From Time is must be greater than to current time').show();
						setTimeout(function(){
							$('#alertsuccess').hide();
							$('.starttime').val('');
						},2000);
					}
				}
			}
			else{
				$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('This schedule is already allocated in author please choose different time').show();
				setTimeout(function(){
					$('#alertsuccess').hide();
					$('.starttime').val('');
					$('.endtime').val('');
				},2000);
			}
		},
		checkupdateliveschedule:function(details,schedulelist,livesessionid){
			self=this;
			var descriptions = $('.sessiondescription').val().trim();
			var startstimeval = $('.starttime').val().trim();
			var endstimeval = $('.endtime').val().trim();
			var price=$('#schedulepriceid').val().trim();

			var liveedit = {};
			liveedit.description = descriptions;
			liveedit.starttime = startstimeval;
			liveedit.endtime = endstimeval;
			liveedit.price=price;
			liveedit.livesessionid = livesessionid;
			liveedit.scheduledate = $('[name=splitdate]').val();
			
			var startstime ="";
			var a=startstimeval.split(' ');
			var b=a[0].split(':');
			if(a[1]=="PM"){
				if(b[0]=="12"){
					startstime=b[0]+":"+b[1]+" "+a[1];
				}
				else{
					startstime=parseInt(b[0])+12+":"+b[1]+" "+a[1];
				}
				
			}
			else if(a[1]=="AM"){
				if(b[0]=="12"){
					startstime="00:"+b[1]+" "+a[1];	
				}
				else if(parseInt(b[0])<10){
					startstime="0"+b[0]+":"+b[1]+" "+a[1];	
				}
				else{
					startstime=b[0]+":"+b[1]+" "+a[1];
				}
			}
			else{
				startstime=b[0]+":"+b[1]+" "+a[1];
			}
			
			var endstime="";
			var c=endstimeval.split(' ');
			var d=c[0].split(':');
			if(c[1]=="PM"){
				if(d[0]=="12"){
					endstime=d[0]+":"+d[1]+" "+c[1];
				}
				else{
					endstime=parseInt(d[0])+12+":"+d[1]+" "+c[1];
				}
			}
			else if(c[1]=="AM"){ 
				if(d[0]=="12"){
				endstime="00:"+d[1]+" "+c[1];
				}
				else if(parseInt(d[0])<10){
					endstime="0"+d[0]+":"+d[1]+" "+c[1];
				}
				else{
					endstime=d[0]+":"+d[1]+" "+c[1];
				}
			}
			else{
				endstime=d[0]+":"+d[1]+" "+c[1];
			}
			
			var dateval = $('[name=splitdate]').val();
			var dat="";
			var daates = new Date(dateval);
			var splitda=daates.toLocaleDateString();
			var splitdaa=splitda.split('/');
			if(parseInt(splitdaa[0])<10){
				dat = "0"+splitdaa[0]+"-"+splitdaa[1]+"-"+splitdaa[2];
			}
			else{
				dat = splitdaa[0]+"-"+splitdaa[1]+"-"+splitdaa[2];
			}
			var dat1=new Date(dat);
			var dat2 = dat1.toLocaleDateString();
			var dat3 = dat2.split("/");
			var dat4="";
			var d3 = "";
			if(10>parseInt(dat3[0])){
				if(10>parseInt(dat3[1])){
					dat4="0"+dat3[0];
					d3="0"+dat3[1];
				}
				else{
					d3=dat3[1];
				}
				
			}
			else{
				if(10>parseInt(dat3[1])){
					dat4=dat3[0];
					d3="0"+dat3[1];
				}
				else{
					d3=dat3[1];
				}
			}
			var dat5=d3+"-"+dat4+"-"+dat3[2];
			liveedit.scheduledates = dat5;

			startdataval=_.pluck(details,'starttime');
			enddataval=_.pluck(details,'endtime');
			endtimeda=_.uniq(_.pluck(schedulelist,'endtime'));
			starttimeda=_.uniq(_.pluck(schedulelist,'starttime'));
			
			var startdata ="";
			var e=startdataval.toString().split(' ');
			var f=e[0].split(':');
			if(e[1]=="PM"){
				if(f[0]=="12"){
					startdata=f[0]+":"+f[1]+" "+e[1];
				}
				else{
					startdata=parseInt(f[0])+12+":"+f[1]+" "+e[1];
				}
				
			}
			else if(e[1]=="AM"){
				if(f[0]=="12"){
					startdata="00:"+f[1]+" "+e[1];
				}
				else if(parseInt(f[0])<10){
					startdata="0"+f[0]+":"+f[1]+" "+e[1];
				}
			}
			else{
				startdata=f[0]+":"+f[1]+" "+e[1];
			}
			
			var enddata="";
			var g=enddataval.toString().split(' ');
			var h=g[0].split(':');
			if(g[1]=="PM"){
				if(h[0]=="12"){
					enddata=h[0]+":"+h[1]+" "+g[1];
				}
				else{
					enddata=parseInt(h[0])+12+":"+h[1]+" "+g[1];
				}
			}
			else if(g[1]=="AM"){
				if(h[0]=="12"){
					enddata="00:"+h[1]+" "+g[1];	
				}
				else if(parseInt(h[0])<10){
					enddata="0"+h[0]+":"+h[1]+" "+g[1];
				}
				else{
					enddata=h[0]+":"+h[1]+" "+g[1];
				}
				
			}
			else{
				enddata=h[0]+":"+h[1]+" "+g[1];
			}
			
			var datetime = courseData.getcurrentdatetime();
			var splitdate="";
			var dates = datetime[0].split('-');
			splitdate = dates[1]+"-"+dates[0]+"-"+dates[2];
			
			
			var time ="";
			var timesp =datetime[1].split(":");
			if("12"<=timesp[0]){
				time = timesp[0]+":"+timesp[1]+" "+"PM";
			}
			else{
				time = timesp[0]+":"+timesp[1]+" "+"AM";
			}
			
			var isTrue = "";
			if(endtimeda.length>1){
				if(endtimeda!=""){
					_.each(endtimeda,function(endtimedataval){
						var endtimedata="";
						var a=endtimedataval.split(' ');
						var b=a[0].split(':');
						if(a[1]=="PM"){
							if(b[0]=="12"){
								endtimedata=b[0]+":"+b[1]+" "+a[1];
								isTrueorFalse(endtimedata);
							}
							else{
								endtimedata=parseInt(b[0])+12+":"+b[1]+" "+a[1];
								isTrueorFalse(endtimedata);
							}
							
						}
						else if(a[1]=="AM"){
							if(b[0]=="12"){
								endtimedata="00:"+b[1]+" "+a[1];
								isTrueorFalse(endtimedata);
							}
							else if(parseInt(b[0])<10){
								endtimedata="0"+b[0]+":"+b[1]+" "+a[1];
								isTrueorFalse(endtimedata);
							}
							else{
								endtimedata=b[0]+":"+b[1]+" "+a[1];
								isTrueorFalse(endtimedata);
							}
							
						}
					});
				}
				else{
					isTrue="true";
				}
			}
			else{
				isTrue = "true";
			}

			var isTrues="";
			if(starttimeda.length>1){
				if(starttimeda.toString()!=""){
					_.each(starttimeda,function(starttimedataval){
						var starttimedata="";
						var a=starttimedataval.split(' ');
						var b=a[0].split(':');
						if(a[1]=="PM"){
							if(b[0]=="12"){
								starttimedata=b[0]+":"+b[1]+" "+a[1];
								isTruesorFalses(starttimedata);
							}
							else{
								starttimedata=parseInt(b[0])+12+":"+b[1]+" "+a[1];
								isTruesorFalses(starttimedata);
							}
						}
						else if(a[1]=="AM"){
							if( b[0]=="12"){
								starttimedata="00:"+b[1]+" "+a[1];
								isTruesorFalses(starttimedata);
							}
							else if(parseInt(b[0])<10){
								starttimedata="0"+b[0]+":"+b[1]+" "+a[1];
								isTruesorFalses(starttimedata);
							}
							else{
								starttimedata=b[0]+":"+b[1]+" "+a[1];
								isTruesorFalses(starttimedata);
							}
						}
					});
				}
				else{
					isTrues = "true";
				}
			}
			else{
				isTrues="true";
			}
			
			function isTrueorFalse(endtimedata){
				if(endtimedata<=startstime && endtimedata<=endstime){
					isTrue = "true";
				}
				else if(endstime<=enddata){
						//isTrue = "true";	
				}
				else if(endtimedata<=endstime){
						isTrue = "true";	
				}
				else{
					isTrue = "false";	
				}
			}
			function isTruesorFalses(starttimedata){
				if(startstime<=starttimedata){
					if(endstime<=starttimedata){
						isTrues = "true";
						isTrue = "true";
					}
					else if(time<=startstime){
						isTrues = "true";
					}
					else if(splitdate<dat){
						isTrues = "true";
					}
					else{
						isTrues = "true";
					}
				}
				else if(starttimedata<=startstime){
					isTrues = "true";
				}
				else{
					isTrues = "false";
				}
			}
			if(startdata.toString()==startstime){
				if(enddata.toString()==endstime){
					self.updateliveschedule(liveedit);
				}
				else if(startstime<endstime){
					if(splitdate<dat){
						if(isTrue=="true" && isTrues=="true"){
						$('#alertsuccess').hide();
						self.updateliveschedule(liveedit);
						}
						else{
							$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('This schedule is already allocated in author please choose different time').show();
							setTimeout(function(){
								$('#alertsuccess').hide();
								//$('.endtime').val('');
							},2000);
						}
					}
					else{
						if(isTrue=="true" && isTrues=="true"){
							if(time<=startstime){
								$('#alertsuccess').hide();
								self.updateliveschedule(liveedit);	
							}
							else{
								$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('From Time is must be greater than to current time').show();
								setTimeout(function(){
									$('#alertsuccess').hide();
									//$('.starttime').val('');
								},2000);
							}
						}
						else{
							$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('This schedule is already allocated in author please choose different time').show();
							setTimeout(function(){
								$('#alertsuccess').hide();
								//$('.endtime').val('');
							},2000);
						}
					}
				}
				else{
					if(isTrue=="true" && isTrues=="true"){
						if(splitdate<dat){
							if(startstime<endstime){
								$('#alertsuccess').hide();
								self.updateliveschedule(liveedit);
							}
							else{
								$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('To Time is must be greater than to start time').show();
								setTimeout(function(){
									$('#alertsuccess').hide();
									//$('.endtime').val('');
								},2000);
							}
						}
						else{
							if(time<=startstime){
								if(startstime<endstime){
									$('#alertsuccess').hide();
									self.updateliveschedule(liveedit);
								}
								else{
									$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('To Time is must be greater than to start time').show();
									setTimeout(function(){
										$('#alertsuccess').hide();
										//$('.endtime').val('');
									},2000);
								}
							}
							else{
								$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('From Time is must be greater than to current time').show();
								setTimeout(function(){
									$('#alertsuccess').hide();
									//$('.starttime').val('');
								},2000);
							}
						}
					}
					else{
						$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('This schedule is already allocated in author please choose different time').show();
						setTimeout(function(){
							$('#alertsuccess').hide();
							//$('.endtime').val('');
						},2000);
					}
				}
			}
			else{
				if(isTrue=="true" && isTrues=="true"){
					if(splitdate<dat){
						$('#alertsuccess').hide();
						self.updateliveschedule(liveedit);
					}
					else{
						if(time<=startstime){
							if(startstime<endstime){
								$('#alertsuccess').hide();
								self.updateliveschedule(liveedit);
							}
							else{
								$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('To Time is must be greater than to start time').show();
								setTimeout(function(){
									$('#alertsuccess').hide();
									$('.endtime').val('');
								},2000);
							}	
						}
						else{
							$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('From Time is must be greater than to current time').show();
							setTimeout(function(){
								$('#alertsuccess').hide();
								//$('.starttime').val('');
							},2000);
						}
					}
				}
				else{
					$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('This schedule is already allocated in author please choose different time').show();
					setTimeout(function(){
						$('#alertsuccess').hide();
						//$('.starttime').val('');
					},2000);
				}
			}
		},
		createliveschedule:function(livedata){
			var createLivesession = courseData.createLivesession(livedata);
			if(createLivesession=="CREATED"){
				$('#alertsuccess').removeClass('alert-danger').addClass('alert-success').text('Livesession has been created sucessfully').show();
				manageCourse.sessionDate();
				manageCourse.sessionInfo();
				$('#cancel').trigger('click');
				setTimeout(function(){
					$('#alertsuccess').hide();
				},2000);
			}
			else if(createLivesession=="PRECONDITION_FAILED"){
				$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('From Time is must be greater than to current time').show();
				setTimeout(function(){
					$('#alertsuccess').hide();										
				},2000);
			}
			else if(createLivesession=="REQUEST_TIMEOUT"){
				$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('To Time is must be greater than to current time').show();
				setTimeout(function(){
					$('#alertsuccess').hide();										
				},2000);
			}
			else if(createLivesession=="NO_CONTENT"){
				$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('To Time is must be greater than to start time').show();
				setTimeout(function(){
					$('#alertsuccess').hide();										
				},2000);
			}
			else{
				$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('To Time is must be greater than to current time').show();
				setTimeout(function(){
					$('#alertsuccess').hide();										
				},2000);
			}
		},
		updateliveschedule:function(liveedit){
			var editdetails = courseData.getUpdateschedule(liveedit);
			if(editdetails=="CREATED"){
				$('#alertsuccess').removeClass('alert-danger').addClass('alert-success').text('Livesession has been updated sucessfully').show();
				manageCourse.sessionDate();
				$('#cancel').trigger('click');
				setTimeout(function(){
					$('#alertsuccess').hide();
				},2000);
			}
			else if(editdetails=="PRECONDITION_FAILED"){
				$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('From Time is must be greater than to current time').show();
				setTimeout(function(){
					$('#alertsuccess').hide();										
				},2000);
			}
			else if(editdetails=="REQUEST_TIMEOUT"){
				$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('To Time is must be greater than to current time').show();
				setTimeout(function(){
					$('#alertsuccess').hide();										
				},2000);
			}
			else if(editdetails=="NO_CONTENT"){
				$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('To Time is must be greater than to start time').show();
				setTimeout(function(){
					$('#alertsuccess').hide();										
				},2000);
			}
			else{
				$('#alertsuccess').removeClass('alert-success').addClass('alert-danger').text('Livesession has been updated failed').show();
				setTimeout(function(){
					$('#alertsuccess').hide();										
				},2000);
			}
		},
		schedulesession:function (date){

			$('.fc-day').removeClass('cellBg');
			var courseid=$('[name=courseid]').val();
			var schedulelist = courseData.Schedulelist(courseid); 

			dat=_.uniq(_.pluck(schedulelist,'scheduledate')); 

			_.each(dat,function(scheduledate){		
				dates = new Date(scheduledate);
				date = $.fullCalendar.formatDate(dates, 'yyyy-MM-dd');
				$('.fc-day[data-date="'+ date +'"]').addClass('cellBg');
			});

		},
		/* ***************************  End Livesession schedule process **************************************** */

		renderContentImages:function(){
			$('.contenttypeclass').each(function(){
				var conttype=$(this).attr('contenttype');
				var contentstatus=$(this).attr('contentstatus');
				if(contentstatus=="W"){
					$(this).attr('disabled','disabled');
					$(this).html("<img src='../assets/app/images/contenttype/nocontent.png'></img>");
				}
				else{
					if(conttype=="video"){
						$(this).html("<i class='fa fa-video-camera'></i>");						
					}
					else if(conttype=="pdf"){
						$(this).html("<i class='fa fa-file-pdf-o'></i>");
					}
					else if(conttype=="swf"){
						$(this).html("<img style='height:20px !important;' src='"+courseData.getContextPath()+"/assets/app/images/contenttype/flashicon.png'/>");
					}
					else{
						$(this).removeClass("preview-lecture-btn");
						$(this).attr('disabled','disabled');
						$(this).html("<i class='fa fa-ban'></i>");
					}
				}

			});
		},

		roadmapTemplate:function(){
			renderTemplate("#roadmaptmpl1", undefined, "#courseroadmaptable");	
			$('.answerpart').hide();
			$('.questionpart').hide();
			$('.trashanswerpart').hide();			
		},
		loadcontent:function(courseid){
			var self=this;
			var courseidobj={};
			courseidobj.courseid=courseid;
			var tobeformatedList=courseData.getCourseContent(courseidobj);
			self.formatList(tobeformatedList);
			self.loadCourseLogoPart(courseid);
		},
		formatList:function(formatList){
			var self=this;			
			var basicInfoContent=formater.formatCourseInfoContent(formatList);
			var courseHeaderContent=formater.formatCourseHeaderContent(formatList);
			var courseMainContent=formater.formatCourseMainContent(formatList);
			var basicInfo=formater.formatbasicInfo(formatList);
			self.loadbasicinfo(basicInfoContent);
			self.loadcourseheader(courseHeaderContent);
			manageContentInfo.loadcontentinfo(courseMainContent);
			if(formatList[0].coursestatus!=="A"){
				self.loadinitialinfo(basicInfo);					
			}
			else{
				renderTemplate("#coursebasicinfotmpl1", basicInfo, "#coursebasicinfotable");	
				$('.previewpromo1').click(function(){
					var obj={};
					obj.coursepromevideopath=$('.previewpromo1').attr('promofilename');
					$('#promo-preview-content-modal1').modal('show'); 
					renderTemplate("#promoContentTempl-video", obj, "#promo-content-space1");	
					$('.flowplayer.promo').flowplayer();
				});
			}
		},
		loadbasicinfo:function(basiccourseinfo){
			renderTemplate("#basicinfotmpl",basiccourseinfo,"#basicinfotable");

			$('.questioncollapse').click(function(){
				$('.questionspart').toggle();
			});
			$('.notescollapse').click(function(){
				$('.notespart').toggle();
			});
			$('.pricebtn').each(function(){
				var price=$(this).attr('price');
				var priceicon = $(this).attr('priceicon');
				var pricelable = "Price :";
				if(price=="0"){
					$(this).html(pricelable+"Free");
				}
				else if(price==""){
					$(this).html("");
				}
				else if(price=="Free"){
					$(this).html("Price:"+price);	
				}

				else{
					$(this).html(pricelable+"<i class='fa "+priceicon+"'></i>"+price);	
				}
			});
		},
		loadCourseLogoPart:function(courseid){
			var self=this;
			var courseidobj={};
			courseidobj.courseid=courseid;
			var courseLogoList=courseData.getCourseLogo(courseidobj);
			self.renderCourseLogoPart(courseLogoList);
		},
		renderCourseLogoPart:function(courseLogoList){
			var self=this;
			if(courseLogoList[0].coursestatus==="A"){
				self.renderExistingCourseLogo(courseLogoList);
				$('.changelogobtn').hide();
			}
			else{
				if(courseLogoList[0].courselogo){
					self.renderExistingCourseLogo(courseLogoList);
				}
				else{
					self.renderNewCourseLogo(courseLogoList);
				}	
			}			
		},
		renderNewCourseLogo:function(courseLogoList){
			var self=this;
			renderTemplate("#newcourselogotmpl", courseLogoList, "#courselogotable");
			$('.cancelfileupload').click(function(){
				self.loadCourseLogoPart(courseLogoList[0].courseid);
			});
			$('.errblcok').hide();				
			$('.prggrop').hide();
			$('.uploadlogocnt').hide();
			var crscategoryval=$('#categorytable').val();
			$('.input-logo-file').click(function(e){
				if(crscategoryval!=="0"){
					$('.input-logo-file').change(function(){
						$('.errblcok').hide();				
						$('.prggrop').hide();
						if(this.files[0]){
							var fileextension=this.files[0].name.split('.').pop();
							var fileSize=this.files[0].size/(1024*1024);				
							fileSize=fileSize.toFixed(2);
							$('[name=logosizeval]').val(fileSize);
							if(_.isString(fileextension)){					
								if(fileextension==="jpg" || fileextension==="JPG" || 
										fileextension==="jpeg" || fileextension==="JPEG" || 
										fileextension==="png" || fileextension==="PNG" ){
									$('#alert-3').hide();
									$(".uploadlogocnt").show();
									self.updateClogo(courseLogoList[0].courseid,fileSize);
								}
								else{
									$(".uploadlogocnt").hide();
									$('.input-logo-file').val('');
									$('#alert-3').show();
									$('.close').click(function(){
										$('#alert-3').hide();
									});
								}
							}
						}							
					});			
				}
				else{
					e.preventDefault();
					self.modaltext("Please choose a category for the course");
				}
			});
		},
		updateClogo:function(courseid,fileSize){
			var self = this;
			$('.uploadlogocnt').show();
			$('.prggrop').show();
			$(".uploadlogocnt").click(function(){
				var fileBool = checkfilesize("#input-logo-file", "lo");
				if(fileBool==="no"){
					self.modaltext("The file Size specified is 1 MB. You cannot upload files greater than 1MB");
					$("#input-logo-file").val("");
					$('.errblcok').hide();				
					$('.prggrop').hide();
					$('.uploadlogocnt').hide();
				}else{
					logoUpload.uploadLogoFiles(courseid,fileSize);
				}
			});			
		},
		renderExistingCourseLogo:function(courseLogoList){
			var self=this;
			renderTemplate("#courselogotmpl", courseLogoList, "#courselogotable");
			$('.changelogobtn').click(function(){
				self.renderNewCourseLogo(courseLogoList);
			});
		},
		loadcourseheader:function(courseHeader){
			var self=this;
			renderTemplate("#courseheadertmpl",courseHeader,"#courseheadertable");
			$('.rightpanetitle').attr('coursetitle',courseHeader[0].coursetitle).text(courseHeader[0].coursetitle);
			var data =courseHeader[0].coursetitle;
			$(".sectionhiddenval").val(data);
			$('.publishcoursebtn').click(function(e){
				var contcount=$('.contenttypeclass[contenttype=nocontent]').length;
				/*var courseidobj={};
				courseidobj.coursestatus="A";
				var draftcourseslist=courseData.getOtherCourses(courseidobj);
				orgplanobj = {};
				orgplanobj.orgplanstatus='A';
				var orgplanmaxcourse = courseData.orgplanlist(orgplanobj);
				if(draftcourseslist!=undefined){
					var draftcoursescount=draftcourseslist.length;
					if(orgplanmaxcourse=="INTERNAL_SERVER_ERROR"){
						var accountInfo ={}; 
						accountInfo =  Ajax.ajaxHelper("GET",courseData.getContextPath()+"/rest/binfo",undefined,"json","application/json");
						var accountInfoId = 0; 
						if(accountInfo != undefined){
							accountInfoId = accountInfo[0].id;						} 
						var publishstatus=self.returnpublishstatus($(this).attr('sectioncount'),
								$(this).attr('lecturecount'),$(this).attr('contentcount'),contcount,accountInfoId,$('#categorytable').val(),
								$('[name=coursetitle]').val().trim(),
								$('[name=coursedescription]').val().trim(),$('[name=coursegoal]').val().trim(),$('.nolecturediv').length);
						if(publishstatus=="ok"){							
							var checkstr =  confirm('Are you sure to Publish this Course? (Note:Published course cant be edited)');
							if(checkstr == true){		
								var publishcourse={};
								publishcourse.courseid=$(this).attr('courseid');
								var publishedStatus=ajaxhelperwithdata("publishCourse", publishcourse);
								if(publishedStatus=="1"){
									$('.publishedscss').css('display','block');
									$(this).text('published');	
									$(this).attr('disabled','disabled');
									var courseid=$(this).attr('courseid');
									setTimeout(function(){			
										$('[name=coursemanageform]').attr('method','post');
										$('[name=coursemanageform]').attr('action','published');
										$('[name=coursemanageform]').submit();										
									},2000);									
									e.preventDefault();
								}
							}
						}
					}
					else{
						var maxcourse = _.pluck(orgplanmaxcourse,'maxcourse');
						if(maxcourse>draftcoursescount){
							var accountInfo ={}; 
							accountInfo =  Ajax.ajaxHelper("GET",courseData.getContextPath()+"/rest/binfo",undefined,"json","application/json");
							var accountInfoId = 0; 
							if(accountInfo != undefined){
								accountInfoId = accountInfo[0].id;
							} 
							var publishstatus=self.returnpublishstatus($(this).attr('sectioncount'),
									$(this).attr('lecturecount'),$(this).attr('contentcount'),contcount,accountInfoId,$('#categorytable').val(),
									$('[name=coursetitle]').val().trim(),
									$('[name=coursedescription]').val().trim(),$('[name=coursegoal]').val().trim(),$('.nolecturediv').length);
							if(publishstatus=="ok"){							
								var checkstr =  confirm('Are you sure to Publish this Course? (Note:Published course cant be edited)');
								if(checkstr == true){	
									var publishcourse={};
									publishcourse.courseid=$(this).attr('courseid');
									var publishedStatus=ajaxhelperwithdata("publishCourse", publishcourse);
									if(publishedStatus=="1"){
										self.modaltext("Course Published");
										$(this).text('published');	
										$(this).attr('disabled','disabled');
										var courseid=$(this).attr('courseid');	
										$('[name=courseid]').val(courseid);
										$('[name=coursemanageform]').attr('method','post');
										$('[name=coursemanageform]').attr('action','published');
										$('[name=coursemanageform]').submit();
										e.preventDefault();
									}
								}
							}
						} else{

							$('#messageinfomodal').modal('show');
							$('.maxcourse').text(maxcourse); 
						} 
					}
				}*/
				/*else{*/
					var accountInfo ={}; 
					accountInfo =  Ajax.ajaxHelper("GET",courseData.getContextPath()+"/rest/binfo",undefined,"json","application/json");
					var accountInfoId = 0; 
					if(accountInfo != undefined){
						accountInfoId = accountInfo[0].id;
					} 
					var publishstatus=self.returnpublishstatus($(this).attr('sectioncount'),
							$(this).attr('lecturecount'),$(this).attr('contentcount'),contcount,accountInfoId,$('#categorytable').val(),
							$('[name=coursetitle]').val().trim(),
							$('[name=coursedescription]').val().trim(),$('[name=coursegoal]').val().trim(),$('.nolecturediv').length);
					if(publishstatus=="ok"){							
						var checkstr =  confirm('Are you sure to Publish this Course? (Note:Published course cant be edited)');
						if(checkstr == true){
							var publishcourse={};
							publishcourse.courseid=$(this).attr('courseid');
							var publishedStatus=ajaxhelperwithdata("publishCourse", publishcourse);
							if(publishedStatus=="1"){
								self.modaltext("Course Published");
								$(this).text('published');	
								$(this).attr('disabled','disabled');
								var courseid=$(this).attr('courseid');	
								$('[name=courseid]').val(courseid);
								$('[name=coursemanageform]').attr('method','post');
								$('[name=coursemanageform]').attr('action','published');
								$('[name=coursemanageform]').submit();
								e.preventDefault();
							}
						}
					}
				//}
			});
		},
		modaltext:function(msg){
			$('#messagemodal').modal('show');
			$('.mesdesc').text(msg);
		},
		loadinitialinfo:function(coursebasicinfo){
			var self=this;
			renderTemplate("#coursebasicinfotmpl",coursebasicinfo,"#coursebasicinfotable");
			selectvalue=$('[name=coursecategoryid]').val();
			var boardList=courseData.loadboardList();
			var mediumList=courseData.loadmediumList();
			var standardList=courseData.loadstandardList();
			var board = $("#boardlistinfo");
			var medium=$('#mediumlistinfo');
			var standard=$('#standardlistinfo');
			$.each(boardList, function() {
				board.append($("<option />").val(this.boardid).text(this.boardname));
			});

			$.each(mediumList, function() {
				medium.append($("<option />").val(this.mediumid).text(this.mediumname));
			});			

			$.each(standardList, function() {
				standard.append($("<option />").val(this.standardlevelid).text(this.standardlevelname));
			});			

			if($('#boardsid').val()!=""){
				$("#boardlistinfo option[value=" + $('#boardsid').val() +"]").attr('selected','selected') ;
			}			
			if($('#mediumids').val()!=""){
				$("#mediumlistinfo option[value=" + $('#mediumids').val() +"]").attr('selected','selected') ;	
			}
			if($('#standardids').val()!=""){
				$("#standardlistinfo option[value=" + $('#standardids').val() +"]").attr('selected','selected') ;
			}
			var courseCategories=self.loadcourseCategories("A",$('#boardlistinfo').val());
			renderTemplate("#categorytmpl", courseCategories, "#categorytable");
			$("#boardlistinfo").change(function(){
				var courseCategories=self.loadcourseCategories("A",$('#boardlistinfo').val());
				renderTemplate("#categorytmpl", courseCategories, "#categorytable");
			});
			$(".priceshowbtn").keypress(function (e) {
				if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
					return false;
				}
			});			
			self.comboSelected('#categorytable',selectvalue);

			var priceval=$('[name=price]').val();
			var privacyval=$('[name=privacyname]').val();
			if(privacyval=="Public"){
				$('.publicbtn').addClass('btn-blue');
			}
			else if(privacyval=="Private"){
				$('.privatebtnbtn').addClass('btn-blue');				
			}
			$('.publicbtn').click(function(){
				$('.privacybtn').removeClass('btn-blue');
				$(this).addClass('btn-blue');
				$('[name=privacyname]').val('Public');				
			});
			$('.privatebtnbtn').click(function(){
				$('.privacybtn').removeClass('btn-blue');
				$(this).addClass('btn-blue');
				$('[name=privacyname]').val('Private');				
			});
			if(priceval!='' && priceval!=="0" && priceval!=="Free"){
				$('.pricestatusbtn').removeClass('btn-blue');
				$('.priceactivebtn').addClass('btn-blue');
				$('.priceshowbtn').show();
				$('.priceshowbtn').focus();
			}
			else{
				$('[name=price]').val('0');
				$('.pricestatusbtn').removeClass('btn-blue');
				$('.pricefreebtn').addClass('btn-blue');
				$('.priceshowbtn').hide();
			}
			$('.pricefreebtn').click(function(){
				$('[name=price]').val('0');
				$('.pricestatusbtn').removeClass('btn-blue');
				$(this).addClass('btn-blue');
				$('.priceshowbtn').hide();
			});
			$('.priceactivebtn').click(function(){
				if(priceval!='' && priceval!=="0" && priceval!=="Free"){
					$('[name=price]').val($(this).attr('price'));
				}
				else{
					$('[name=price]').val('');  
				}
				$('.pricestatusbtn').removeClass('btn-blue');
				$(this).addClass('btn-blue');
				$('.priceshowbtn').show();
				$('.priceshowbtn').focus();
			});
			var levelval=$('[name=instructoinallevel]').val();
			var selector=$(".levelbtn[level~='" + levelval + "']");
			selector.addClass('btn-blue');
			$('.levelbtn').click(function(){
				$('.levelbtn').removeClass('btn-blue');
				$(this).addClass('btn-blue');
				$('[name=instructoinallevel]').val($(this).attr('level'));
			});
			$('.previewpromo').click(function(){
				var obj={};
				obj.coursepromevideopath=$('#promovideo').attr('uploadedpath');
				$('#promo-preview-content-modal').modal('show'); 
				renderTemplate("#promoContentTempl-video", obj, "#promo-content-space");	
				$('.flowplayer').flowplayer();
			});

			$('.coursebasicinfosavebtn').click(function(){				
				var basicinfo={};				
				var courseid=$(this).attr('courseid');
				basicinfo.courseid=courseid;
				basicinfo.coursetitle=encodeURIComponent($('[name=coursetitle]').val().trim());
				basicinfo.coursesubtitle=encodeURIComponent($('[name=coursesubtitle]').val());
				basicinfo.coursedescription=encodeURIComponent($.trim($("[name=coursedescription]").val()));
				basicinfo.coursegoal=$('[name=coursegoal]').val().trim();
				basicinfo.courselogo=$('[name=courselogo]').val();
				basicinfo.coursepromevideopath=$('[name=coursepromevideopath]').val();
				basicinfo.priceid=$('[name=price]').attr('priceid');
				basicinfo.price=$('[name=price]').val();
				basicinfo.coursecategoryid=$('#categorytable').val();
				basicinfo.coursecategoryname=$('#categorytable').children(':selected').text();
				basicinfo.boardid =$('#boardlistinfo').val();
				basicinfo.mediumid=$('#mediumlistinfo').val();
				basicinfo.standardid=$('#standardlistinfo').val();
				basicinfo.instructoinallevel=$('[name=instructoinallevel]').val();
				basicinfo.intendedaudience=encodeURIComponent($('[name=intendedaudience]').val());
				basicinfo.coursecreateddate=$('[name=coursecreateddate]').val();
				basicinfo.privacyid=$('[name=privacyname]').attr('privacyid');
				basicinfo.privacyname=$('[name=privacyname]').val();
				basicinfo.coursestatus=$('[name=coursestatus]').val();
				if(basicinfo.boardid!=0){
					if(basicinfo.mediumid!=0){
						if(basicinfo.standardid!=0){
							if(basicinfo.coursecategoryid!=0){
								if(basicinfo.coursetitle.length!=0){
									if(basicinfo.coursedescription.length!=0){							
										if(basicinfo.price!=''){							
											if(basicinfo.coursegoal.length!=0){
												if(basicinfo.coursepromevideopath!=""&&$('#promovideo')[0].files.length==0){													
													var txt = ($('[name=coursetitle]').val());
													var savedvalue=$('[name=coursetitle]').attr('savedvalue');
													var result=undefined;
													if(txt!=savedvalue){
													result=courseData.checkDuplicateCourse(txt);
													}
													else{
													result=false;	
													}													
													if(result=="true"){	
													self.modaltext("Course title already exist,try again");
													$('[name=coursetitle]').focus();
													basicinfo.coursetitle="";
													$('[name=coursetitle]').focus();
													}												
													else{
														basicinfosave=ajaxhelperwithdata("managecourseinfo", basicinfo);													
														if(basicinfosave=="1")	{																															
															self.modaltext("Basic Info. is inserted successfully");
															self.loadPageInfo(courseid);
														}
														
														else{
															self.modaltext("Error in Saving the Information");																						
														}															
														
													}									
												}
												else{
													if($('#promovideo')[0].files.length>0){
														var filearray=$('#promovideo')[0].files[0].name.split('/');
														var file=filearray[filearray.length-1];
														var filetype=file.split('.');
														var videouploadstatus=null;
														if(filetype[filetype.length-1]=="flv"||filetype[filetype.length-1]=="mp4"){
															var chckBool = checkfilesize('#promovideo', "pr");
															if(chckBool==="ok"){
																videouploadstatus=manageCourse.sendfile($('#promovideo')[0].files[0],$('.coursebasicinfosavebtn').attr('courseid'));
																if(videouploadstatus==200){
																	basicinfo.coursepromevideopath=$('#promovideo').attr('uploadedpath');
																	basicinfosave=ajaxhelperwithdata("managecourseinfo", basicinfo);									
																	if(basicinfosave=="1")	{								
																		self.modaltext("Basic Info. is inserted successfully");
																		self.loadPageInfo(courseid);
																	}
																	else{
																		self.modaltext("Error in Saving the Information");																						
																	}
																}
																else{
																	self.modaltext("Problem in saving promo video");
																}
															}else{
																$('#promovideo').val("");
																self.modaltext("The file Size specified is 30 MB. You cannot upload files greater than 30 MB");
															}
															
														}
														else{
															self.modaltext("Please select video file");
														}
													}
													else{
														self.modaltext("Please upload promo video");
													}
												}
											}		
											else{
												self.modaltext("Please enter the keywords");	
											}							
										}
										else {
											self.modaltext("Please enter the price");
										}	
									}
									else{
										self.modaltext("Please enter the course description");
									}						
								}
								else{
									self.modaltext("Please enter the course title");
								}	
							}
							else{
								self.modaltext("Please choose a sub-category for the course");					 
							}

						}else{
							self.modaltext("Please choose a standard for the course");
						}
					}else{
						self.modaltext("Please choose a medium for the course");
					}
				}else{
					self.modaltext("Please choose a category for the course");
				}
				$('.basicli').trigger('click');
			});
			/*$('#promovideo').on('change',function(){
				var filearray=$(this)[0].files[0].name.split('/');
				var file=filearray[filearray.length-1];
				var filetype=file.split('.');				
				if(filetype[filetype.length-1]=="flv"||filetype[filetype.length-1]=="mp4"){
					manageCourse.sendfile($(this)[0].files[0],$('.coursebasicinfosavebtn').attr('courseid'));
				}
				else{
					alert("Please select video file");
				}
			});*/
			
		},
		returnpublishstatus:function(sections,lectures,contents,contcount,accountInfoId,categorychosen,coursetitlechosen,coursedesc,keywrds,nolecturediv){
			var self=this;
			var logocount=$('.courselogo').length;
			if(logocount==undefined){
				logocount=parseInt("0");
			}
			if(accountInfoId == 0 | typeof accountInfoId == "undefined" | accountInfoId == ""){
				self.modaltext("Please add Bank Account Information in user preferences to publish course");
				setTimeout(function(){
					$('#messagemodal').modal('hide');
					$('[name=bankinfo]').val("fill");
					$('[name=coursemanageform]').attr('action','manageprofile');
					$('[name=coursemanageform]').submit();
				},2000);				
			}
			else{
				if(coursetitlechosen.lenth!=0){
					if(categorychosen==0){
						self.modaltext("Please choose a category for the course");
					}else{
						if(coursedesc.length!=0){
							if(keywrds.length!=0){
								if(logocount<1){
									self.modaltext("Please upload a logo for the course");																					
								}
								else{
									if(sections<1){
										self.modaltext("you need to create atleast one section");
									}else{
										if(lectures<1){
											self.modaltext("you need to create atleast one lecture");
										}else{
											if(contents<1){
												self.modaltext("you need to add atleast one content");
											}
											else{
												if(contcount>0){
													self.modaltext("Please Upload Content for all lectures");				
												}
												else{
													if(nolecturediv>0){												
														self.modaltext("you need to create atleast one lecture for all sections");												
													}
													else{													
														return "ok";	
													}	
												}														
											}
										}
									}
								}
							}
							else{
								self.modaltext("Please enter keywords");
							}							
						}
						else{
							self.modaltext("Please enter course description");
						}
					}
				}
				else{
					self.modaltext("Please enter course title");
				}
			}	
		},
		comboSelected:function(selector,selectvalue){
			$(selector+" option").each(function(){
				if($(this).val() == selectvalue){
					$(this).attr("selected","true");
					return false;
				}
			});
		},
		loadcourseCategories:function(status,boardid){
			var categorystatusobj={};
			categorystatusobj.coursecategorystatus=status;
			categorystatusobj.boardid=boardid;
			return Ajax.post(courseData.getContextPath()+"/rest/boardcategory/",categorystatusobj,"json","application/json");
		},
		changecourselogo:function(){
			var self=this;
			$('.changelogobtn').click(function(){
				$('#changelogomodal').modal('show');
				self.uploadCourseLogo();
			});
		},		 
		uploadCourseLogo:function(){
			var self=this;
			$('.changelogosavebtn').click(function(){
				$('.loadinglogoholder').show();
				$('[name=coursetitle]').val($('[name=coursetitle]').val());
				$('[name=coursecategoryname]').val($('#categorytable option:selected').text()); 
				$('#form1').submit();				  
				setTimeout(function(){
					$('.loadinglogoholder').hide(); 
					self.loadCourseLogoPart($('.rightpanetitle').attr('courseid')); 
					$('#changelogomodal').modal('hide'); 
				},4000);	 
			});			
		},
		sendfile:function (file,courseid) {

			var xhr=new XMLHttpRequest();
			xhr.upload.onprogress = function(evt){
				if (evt.lengthComputable) 
				{
					var percentComplete =Math.floor((evt.loaded / evt.total)*100);
					$('.promostatusbar').show();
					$('.promomsg').html(percentComplete+"% "+'Uploading....');
					$('.promomsg').show();
					$('.promostatus').width(percentComplete*4);
				} 
			};
			xhr.onload=function(data){
				if(xhr.status == 200){
					console.log("complete "+xhr.status+" "+xhr.response);
					var videoname=xhr.response;
					$('#promovideo').attr('uploadedpath',videoname);
					$('.promomsg').css("color", "green");
					$('.promomsg').html('Completed!');
				}else{
					console.log("failed");
					$('#promovideo').attr('uploadedpath','');
					$('.promomsg').css("color", "red");
					$('.promomsg').html('Failed!');					
				}
			};
			xhr.onerror=function(){
				console.log("Error ! upload failed.Can not connect to server");
			};			    
			xhr.open("POST",courseData.getContextPath()+'/rest/video/'+courseid,false);
			console.log("open");
			var formdata = new FormData();
			formdata.append('file',file);
			xhr.send(formdata);
			return xhr.status;				
		}
};

var logoUpload ={
		uploadLogoFiles:function(courseid,logosizeval){
			var bar = $('.logoupprogress');
			var percent = $('.progressper');
			var status = $('#status');	
			status.empty();
			var xhr=new XMLHttpRequest();
			xhr.upload.onprogress = function(evt){
				if (evt.lengthComputable) {
					var percentComplete =Math.floor((evt.loaded / evt.total)*100);
					bar.show();
					status.html(percentComplete+"% "+'Uploading....');
					bar.css("width",percentComplete+"% ");
				} 
			};
			xhr.onload=function(data){
				if(xhr.status == 200){
					if(xhr.responseText=="error"){
						$('.errblcok').show();
						$('.errblcok').removeClass('alert-success').addClass('alert-danger');
						bar.width('0%');
						percent.html('0%');									
						$('.input-logo-file').val('');
						$('.uploadlogocnt').hide();
						$('.prggrop').hide();
					}
					else if(xhr.responseText=="nospace"){
						$('.errblcok').show();
						$('.errblcok').removeClass('alert-success').addClass('alert-danger');
						bar.width('0%');
						percent.html('0%');
						$('.emsg').text("Upload failed. Please contact Administrator");
						$('.input-logo-file').val('');
						$('.uploadlogocnt').hide();
						$('.prggrop').hide();
					}
					else{
						$('.errblcok').show();
						$('.errblcok').removeClass('alert-danger').addClass('alert-success');
						$('.strbtn').text('Success !');
						$('.emsg').text("Upload Finished");
						$('.input-logo-file').val('');
						$('.uploadlogocnt').hide();
						$('.prggrop').hide();
						manageCourse.loadCourseLogoPart(courseid);
					}
				}else{
					$('.errblcok').show();
					$('.errblcok').removeClass('alert-success').addClass('alert-danger');
					bar.width('0%');
					percent.html('0%');
					$('.emsg').text("Upload failed. Please contact Administrator");
					$('.input-logo-file').val('');
					$('.uploadlogocnt').hide();
					$('.prggrop').hide();
				}
			};
			xhr.onerror=function(){
				console.info("Error ! upload failed.Can not connect to server");
				$('.errblcok').show();
				$('.errblcok').removeClass('alert-success').addClass('alert-danger');
				bar.width('0%');
				percent.html('0%');
				$('.emsg').text("Upload failed. Please contact Administrator");
				$('.input-logo-file').val('');
				$('.uploadlogocnt').hide();
				$('.prggrop').hide();
			};
			var fd=new FormData();
			fd.append("cologo",document.getElementById('input-logo-file').files[0]);
			fd.append("logosizeval",logosizeval);
			fd.append("courseidval",courseid);
			xhr.open("POST",courseData.getContextPath()+"/rest/course/logoUpdate",true);
			xhr.send(fd);
		}
};