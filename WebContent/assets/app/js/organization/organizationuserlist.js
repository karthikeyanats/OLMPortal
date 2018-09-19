$(document).ready(function(){
	
	$('.leftsideli').click(function (e) {
	    //save the latest tab; use cookies if you like 'em better:
	      sessionStorage.setItem('lastTab', $(e.target).attr('class'));
	  });
	
	$('.organizationusers').click(function(){
		organizationWiseUserList.getUserList();
	});
	$('.subscrips').click(function(){
		organizationSubscripsList.getSubscripList();
	});
	$(".subscriptionplandetail").click(function(){
		organizationSubscripdetailList.getorganizationSubscripdetailList();
	});
	$('.courseanalytics').click(function(){
		organizationCourseAnalyticsList.getCourseAnalyticsList();
	});
	$(".useractivity").click(function(){
		organizationUserActivityList.getUserActivityList();
	});
	$(".leftsideli").click(function(){
		$(".leftsideli").removeClass('active');
		$(this).addClass('active');
		});
	orgDataList.getFirstLoadItems();
	
	if(sessionStorage.getItem('lastTab')){
		  var lastTab = sessionStorage.getItem('lastTab');
		  if(lastTab.search("basicinfo")>=0){
			  switchtab(1);
		  }
		  else if(lastTab.search("orgcourse")>=0){
			  switchtab(2);
		  }
	     else if(lastTab.search("subscrips")>=0){
	    	 switchtab(3);
		  }
	     else if(lastTab.search("subscriptionplandetail")>=0){
	    	 switchtab(4);
		  }
	     else if(lastTab.search("courseanalytics")>=0){
	    	 switchtab(5);
		  }
	  }
	function switchtab(e){
	 switch(e){
		  case 1:{
			  $('.basicinfo').trigger('click');
			  break;
		  }
		  case 2:{
			  $('.orgcourse').trigger('click');
			  break;
		  }
		  case 3:{
			  $('.subscrips').trigger('click');
			  break;
		  }
		  case 4:{
			  $('.subscriptionplandetail').trigger('click');
			  break;
		  }
		  case 5:{
			  $('.courseanalytics').trigger('click');
			  break;
		  }	
		  }
	}
	
});
organizationWiseUserList={
		getUserList:function(){
			var self=this;
			var userlist=courseData.getOrgUsers();
			self.renderUsers(userlist);
		},
		renderUsers:function(userlist){
			renderTemplate("#organizationwiseusertmplt",userlist,"#userdetails");			
		}
};

organizationSubscripsList={
		getSubscripList:function(){
			 
			
			var subscriptiondetails=courseData.loadSubscriptionData();
			
			var subscrip={};
			subscrip=_.groupBy(subscriptiondetails,'durationtype');
			var orgplanobj={};
			orgplanobj.orgplanstatus="A";
			var orgplanupadtelist=courseData.orgplanlist(orgplanobj);
			var orgsubscriptioniddatas = _.pluck(orgplanupadtelist,"planid");
			var orgsubscriptioniddata = _.pluck(orgplanupadtelist,"id");
			var id =_.pluck(subscriptiondetails,"id");
			var basicplanid =_.pluck(subscriptiondetails,"id");
			
			if(orgplanupadtelist !="INTERNAL_SERVER_ERROR"){
				var planstartdateformat =_.pluck(orgplanupadtelist,"planstartdate");
				var d = new Date(parseInt(planstartdateformat));
		         var date2 =d.toDateString();
		         var date1 = date2.split(" ");
		         var date = date1[2]+ "/" +date1[1]+ "/" + date1[3];
		         var time = d.toLocaleString();
		         var datetime=time.split(" ");
		         var datetimes="";
		         if(datetime[4]==undefined){
		        	 datetimes=datetime[1]+" "+datetime[2];
		         }
		         else if(datetime[4]!=undefined){
		        	 datetimes=datetime[4]+" "+datetime[5];
		         }
		         var plandatetime = date+" "+datetimes;
		         var planenddateformat =_.pluck(orgplanupadtelist,"planenddate");
					var d1 = new Date(parseInt(planenddateformat));
			         var enddate1 =d1.toDateString();
			         var enddate2 = enddate1.split(" ");
			         var enddate = enddate2[2]+ "/" +enddate2[1]+ "/" + enddate2[3];
			         var time = d1.toLocaleString();
			         var datetime=time.split(" ");
			         var datetimes="";
			         if(datetime[4]==undefined){
			        	 datetimes=datetime[1]+" "+datetime[2];
			         }
			         else if(datetime[4]!=undefined){
			        	 datetimes=datetime[4]+" "+datetime[5];
			         }
			         var planenddatetime = enddate+" "+datetimes;
			}
			Handlebars.registerHelper('sid', function(id) 
					 { 
					 if(id==orgsubscriptioniddatas){
						 return "<p class='daeformat' style='color:#B94A48;margin-left:143px;'><b>Valid From</b> </p><p class='daeformat' style='color:#B94A48'><b> "+plandatetime+" to "+planenddatetime+"</b><span class='badge badge-important subscriptionpayment pull-right' planid="+orgsubscriptioniddatas+">Subscribed</span></p>";
					 }
					 else{
						 return "<a class='btn btn-success subscriptioncoursepayment pull-right' href='#' planid="+id+" style='margin-top:25px;'> Subscribe </a>";;	 
					 }
					 });
			 
			renderTemplate("#subscriptionplantmplt", subscrip, "#userdetails");
			
			var basicplan={};
			basicplan=courseData.loadBasicplanData();
			Handlebars.registerHelper('basicplanid', function(basicplanid) 
					 { 
					 if(basicplanid==orgsubscriptioniddatas){
						 return "<p class='daeformat' style='color:#B94A48;margin-left:120px;'><b>Valid From</b> </p><p class='daeformat' style='color:#B94A48'><b> "+plandatetime+" to "+planenddatetime+"</b><span class='badge badge-important subscriptionpayment pull-right' planid="+orgsubscriptioniddatas+">Subscribed</span></p>";
					 }
					 });
			renderTemplate("#basicplantmplt",basicplan,"#basicplan");
			
			
			
			$(".subscriptioncoursepayment").click(function(){
				//$(".planname").val(orgsubscriptioniddatas);
				//var planid = $(".planname").val();
				$(".planid").val($(this).attr("planid"));
				var planid = $(".planid").val();
				var d =_.findWhere(subscriptiondetails,{id:parseInt(planid)});
				var planamount = d.planamount;
				var durationtype =d.durationtype;
				var duration = d.duration;
				$(".orgsubscriptionidval").val(orgsubscriptioniddata);
				var orgsubscriptionidval = $(".orgsubscriptionidval").val();
				$(".orgsubscriptionid").val(orgsubscriptionidval);
				$(".planname").val(planamount);
				$(".durationtype").val(durationtype);
				$(".duration").val(duration);
				$('[name=coursepayment]').attr("action","subscriptioncoursepayment");
				$('[name=coursepayment]').submit();
				/*if(orgplanupadtelist.length>0){
					var orgsubscriptioniddata = _.pluck(orgplanupadtelist,"id");
					var planidval = _.pluck(orgplanupadtelist,"planid");
					$(".orgsubscriptionidval").val(orgsubscriptioniddata);
					var orgsubscriptionidval = $(".orgsubscriptionidval").val();
					if(planidval==$(this).attr("planid")){
						$(".orgsubscriptionid").val(orgsubscriptionidval);
						var orgsubscriptionid=$(".orgsubscriptionid").val();
						$('[name=coursepayment]').attr("action","subscriptioncoursepayment");
						$('[name=coursepayment]').submit();
					}
					else{
						orgplanidobj={};
						orgplanidobj.orgsubscriptionid=orgsubscriptionidval;
						orgplanidobj.orgplanstatus="T";
						var updateplanlist = courseData.updateorgplan(orgplanidobj);
						
						if(updateplanlist!=null){
							var planids = $(this).attr("planid");
							var planidobj={};
							planidobj.planid=$(this).attr("planid");
							planidobj.durationtype=durationtype;
						     planidobj.duration=duration;
							var orgplanlistval=courseData.createorgplan(planidobj);	
							if(orgplanlistval!=null){
								$(".orgsubscriptionid").val(orgplanlistval);
								var orgsubscriptionid=$(".orgsubscriptionid").val();
								$('[name=coursepayment]').attr("action","subscriptioncoursepayment");
								$('[name=coursepayment]').submit();
						}
					}
				}
				}*/
				/*else{
					var planidobj={};
					planidobj.planid=$(this).attr("planid");
					planidobj.durationtype=durationtype;
				    planidobj.duration=duration;
					var orgplanlist=courseData.createorgplan(planidobj);
					if(orgplanlist!=null){
						console.log("create orgplan");
						$(".orgsubscriptionid").val(orgplanlist);
						var orgsubscriptionid=$(".orgsubscriptionid").val();
						$('[name=coursepayment]').attr("action","subscriptioncoursepayment");
						$('[name=coursepayment]').submit();
					}
				}*/
				
			});
		}
		
};
organizationSubscripdetailList ={
		getorganizationSubscripdetailList:function(){
			var subscriptiondetails=courseData.getOrganizationSubsplanlist();
			
			//var personid=
			Handlebars.registerHelper('planstartdate', function(planstartdate) 
					 { 
						var d = new Date(parseInt(planstartdate));
				         var date2 =d.toLocaleDateString();
				         var date1 = date2.split("/");
				         var date = date1[1]+ "/" +date1[0]+ "/" + date1[2];
				         var time = d.toLocaleTimeString();
				         var plandatetime = date+" "+time;
			         return plandatetime;
					 });
					Handlebars.registerHelper('planenddate', function(planenddate) 
							 { 
						var d = new Date(parseInt(planenddate));
				         var date2 =d.toLocaleDateString();
				         var date1 = date2.split("/");
				         var date = date1[1]+ "/" +date1[0]+ "/" + date1[2];
				         var time = d.toLocaleTimeString();
				         var plandatetime = date+" "+time;
			         return plandatetime;
							 });
					Handlebars.registerHelper('noofcourses', function(noofcourses) 
							 { 
							 if(noofcourses>0){
								 return noofcourses;
							 }
							 else{
								 return noofcourses;
							 }
							 });
					Handlebars.registerHelper('noofusers', function(noofusers) 
							 { 
							 if(noofusers>0){
								 return noofusers;
							 }
							 else{
								 return noofusers;
							 }
							 });
					var orgmaxuserlist=Ajax.get(courseData.getContextPath()+"/rest/organizationlist/orgmaxusers",undefined,"json","application/json");
					var formatList = this.formatplanList(subscriptiondetails,orgmaxuserlist);
					renderTemplate("#orgsubscriptionplantmpl", formatList, "#userdetails");
						$(".maxcourse").each(function(){
							if($(this).text()==0){
								$(this).addClass("defaultcursor");
								$(this).removeClass("maxcourse");								
							}else{
								//$(this).addClass('underline');
							}
						});
						$(".maxuser").each(function(){
							if($(this).text()==0){
								$(this).addClass("defaultcursor");
								$(this).removeClass("maxuser");								
							}else{
								//$(this).addClass('underline');
							}
						});
						$(".maxcourse").click(function(){
							$('#plannamedetails').modal('show');							
							var plandetails=courseData.getOrganizationcourses($(this).attr('orgplansubscriptionid'));
							Handlebars.registerHelper('status', function(coursestatus) 
									 { 
									 if(coursestatus=="A"){
										 return "<font class='label label-info pad612'>Approved Course</font>";
									 }
									 else if(coursestatus=="D"){
										 return "<font class='label label-primary pad612'>Draft Course</font>";	 
									 }
									 else if(coursestatus=="T"){
										 return "<font class='label label-warning pad612'>Trashed Course</font>";	 
									 }
									 else if(coursestatus=="P"){
										 return "<font class='label label-success pad612'>Published Course</font>";	 
									 }
									 });
							renderTemplate("#courseinfotmpl", plandetails, "#planinfotable");
							$('.coursedetails').html('Course Details');
						});
						$('.maxuser').click(function(){
							$('#plannamedetails').modal('show');							
							var users=courseData.getOrganizationUsers($(this).attr('orgplansubscriptionid'));
							Handlebars.registerHelper('userstatus', function(courseinvitationstatus) 
									 { 
									 if(courseinvitationstatus=="A"){
										 return "<font class='label label-success pad612'>Enrolled User</font>";
									 }
									 else if(courseinvitationstatus=="I"){
										 return "<font class='label label-primary pad612'>Invited User</font>"; 
									 }
									 });
							renderTemplate("#userinfotmpl", users, "#planinfotable");
							$('.coursedetails').html('User Details');
						});
		},
		formatplanList:function(orglist,orgmaxuserlist){
			var maxuser = [];
			
			var pluckorgsid = _.pluck(orglist,'id');
			_.each(pluckorgsid,function(pluckorgsids){
				var userlist = {};
				var ids = _.findWhere(orglist,{id:pluckorgsids});
				var pluckid = _.pluck(orgmaxuserlist,'id');
				_.each(pluckid,function(pluckids){
					var idss = _.findWhere(orgmaxuserlist,{id:pluckids});
					if(idss.id==ids.id){
						userlist.id=ids.id;
						userlist.planid=ids.planid;
						userlist.publishedcoursecount=ids.publishedcoursecount;
						userlist.organizationid=ids.organizationid;
						userlist.planname = ids.planname;
						userlist.maxcourse= ids.maxcourse;
						userlist.maxusers = ids.maxusers;
						userlist.inviteduserscount = idss.noofusers;
						userlist.dateofsubscription = ids.dateofsubscription;
						userlist.duration=ids.duration;
						userlist.durationtype=ids.durationtype;
						userlist.planamount=ids.planamount;
						userlist.planenddate=ids.planenddate;
						userlist.planid = ids.planid;
						userlist.planname = ids.planname;
						userlist.planstartdate= ids.planstartdate;
						maxuser.push(userlist);
					}
				});
			});
			return maxuser;
		},
};
organizationUserActivityList={
		getUserActivityList:function(){
			//renderTemplate("#courseAnalyticstmplt","","#userdetails");
		}
};
organizationCourseAnalyticsList={		
		getCourseAnalyticsList:function(){
			var self = this;
		    var usercourses=courseData.getOrgCourses();
			self.renderUserCourses(usercourses);
		},
		renderUserCourses:function(usercourses){
			renderTemplate("#courseAnalyticstmplt",usercourses,"#userdetails"); 
			helper.pagination();
			
			$('.enrollstatus').each(function(){
				$(this).css('text-decoration','underline !important');
				var thistext=$(this).text();
				if(thistext==0){
					$(this).removeClass('enrollstatus');
				}
			});
			$('.enrollstatus').click(function(){
				if($(this).text()!=0){
				var courseid=$(this).attr('courseid');
				var estatus=$(this).attr('status');
				var title=$(this).attr('title');
				var Enrollresult='';
				var enrollobj={};
				enrollobj.status=estatus;
				enrollobj.courseid=courseid;				
					Enrollresult=courseData.getEnrolledDiffList(enrollobj); 
				
				$('#enrolledusersmodal').modal('show');
				$('.resulttitle').text(title);
				if(Enrollresult!='false'){
					renderTemplate("#enrolllisttmpl", Enrollresult, "#enrolllistdiv");		
					$('.modal-body').slimscroll({height: "475px"});
				}
				}
			});
			
		}
};
helper={
		pagination:function(){
			/**
			 * Pagination starts here
			 *
			 * 
			 * 
			 */

			spanOffset=100;			
			var totalPages=$('.paginationtab tbody tr').length;
			spanCount=0;
			if(totalPages>spanOffset){
				spanCount=totalPages/spanOffset;
				span=1;
				currentSpan=spanCount-span;
				$('.pagination').text("");
				$('.pagination').append('<input id="previouspages" rel="'+span+'" class="page hover" type="button" value="<<">');
				$("#previouspages").addClass('active');
				$("#previouspages").prop("disabled",true);
			}
			else{
				span=1;
				spanOffset=totalPages;
			}
			renderPageNavigation(span,spanOffset);			       


			function renderPageNavigation(span,spanOffset){

				//$('#alluserstable').after('<div class="navbar" id="nav"></div>');
				var currentPage=0;
				var rowsShown = 10;
				var rowsTotal=0;
				if(span>spanCount){
					rowsTotal=totalPages;  
				}
				else{
					rowsTotal = span*spanOffset;
				}
				var pageNum=0;
				var i=(span-1)*spanOffset/10;
				if(rowsTotal>rowsShown)
				{
					var numPages = Math.ceil(rowsTotal/rowsShown);
					//$('.pagination').text("");
					$('.pagination').append('<input id="previous" rel="'+currentPage+'" class="page hover" type="button" value="<">');

					for(i;i < numPages;i++) {
						pageNum = i + 1;
						$('.pagination').append('&nbsp;<a href="javascript:void(0)" rel="'+i+'"class="page hover">'+pageNum+'</a> ');
					}
					$('.pagination').append('&nbsp;<input id="next" rel="'+currentPage+'" class="page hover" type="button" value=">">');
					$('.paginationtab tbody tr').hide();
					$('.paginationtab tbody tr').slice(0, rowsShown).show();
					$('.pagination a:first').addClass('active');
					$('.pagination a').bind('click', function(){
						if($(this).text()==pageNum&&$('#nextpages').is(':disabled')){
							$('#next').addClass('active');
							$("#next").prop("disabled","disabled");						    		  
						}
						else{
							$("#next").removeClass('active');
							$("#next").removeAttr("disabled");
						}  
						if($(this).text()==1){
							$("#previous").addClass('active');
							$("#previous").prop("disabled","disabled");
						}
						else{
							$("#previous").removeClass('active');
							$("#previous").removeAttr("disabled");
						}  
						//$("#previous").removeAttr("disabled");

						$('.pagination a').removeClass('active');
						$(this).addClass('active');
						var currPage = $(this).attr('rel');
						var startItem = currPage * rowsShown;
						var endItem = startItem + rowsShown;
						$("#previous").attr('rel',currPage);
						$("#next").attr('rel',currPage);
						$('.paginationtab tbody tr').css('opacity','0.0').hide().slice(startItem, endItem).
						css('display','table-row').animate({opacity:1}, 300);
						if($('#nextpages').length==0&& $('.pagination a:last').hasClass('active')){
							$('#next').addClass('active');
							$('#next').prop('disabled',true);
						}

					});


					if($("#previous").attr('rel')==0){
						$("#previous").addClass('active');
						$("#previous").prop("disabled",true);
					}				      
					if(totalPages>spanOffset){
						$('.pagination').append('&nbsp;<input id="nextpages" rel="'+span+'" class="page hover" type="button" value=">>">');
					}
					if(span>=spanCount){   
						$("#nextpages").addClass('active');
						$("#nextpages").prop("disabled",true);
					}  
					if(span<=1){
						$("#previouspages").addClass('active');
						$("#previouspages").prop("disabled",true);
					}						   						      
					/**
					 * 
					 * Next span button function 
					 */


					$('#nextpages').click(function(){

						++span;
						$('.pagination').text("");
						$('.pagination').append('<input id="previouspages" rel="'+span+'" class="page hover" type="button" value="<<">');
						renderPageNavigation(span,spanOffset);
						$('.pagination a:first').trigger('click');	                        	                             
					});


					/**
					 * 
					 * previous span button function 
					 */


					$('#previouspages').click(function(){

						--span;			            	                        
						$('.pagination').text("");
						$('.pagination').append('<input id="previouspages" rel="'+span+'" class="page hover" type="button" value="<<">');
						renderPageNavigation(span,spanOffset);
						$('.pagination a:last').trigger('click');                	   	

					});

					/**
					 * 
					 * Next button function 
					 */

					$('#next').click(function () {					    	  

						if ((currPage * numPages) <= rowsTotal)
							currPage++;
						{   
							var currPage = $(this).attr('rel');
							$("a[rel='" + currPage + "']").removeClass('active');
							currPage=parseInt(currPage)+1;
							$("a[rel='" + currPage + "']").addClass('active');
							$("#previous").attr('rel',currPage);
							$("#next").attr('rel',currPage);
							console.log(currPage);
							var startItem = currPage * rowsShown;
							var endItem = startItem + rowsShown;
							$('.paginationtab tbody tr').css('opacity','0.0').hide().slice(startItem, endItem).
							css('display','table-row').animate({opacity:1}, 300);
						}

						if($("#previous").attr('rel')==1){
							$("#previous").removeClass("active");
							$("#previous").removeAttr("disabled");
						}
						if($('#nextpages').is(':enabled')){
							if(currPage==(parseInt(numPages))){
								$('#nextpages').trigger('click');
							}
						}
						else if(!$('#nextpages').is(':enabled')){
							if(currPage==(parseInt(numPages)-1)){
								$(this).addClass('active');
								$(this).prop("disabled",true);
							}
						}
						else{
							$(this).removeClass('active');
							$(this).removeAttr("disabled");
						}
						if(!$('.pagination a:first').hasClass('active')){
							$("#previous").prop("disabled",false);
						}
					});

					/**
					 * 
					 * Previous button function 
					 */
					$('#previous').click(function () {

						if($(this).attr('rel')==1){		
							$(this).addClass('active');
							$(this).prop("disabled",true);					                		  
						}
						else if($('#previouspages').is(':enabled')&&$('.pagination a:first').hasClass('active')){
							$("#previouspages").trigger('click');
						}
						else{
							$(this).removeClass('active');
							$(this).removeAttr("disabled");
						}
						if($("#next").attr('rel')==(parseInt(numPages)-1)){
							$("#next").removeClass("active");
							$("#next").removeAttr("disabled");
						}
						else{
							$("#next").removeClass("active");
							$("#next").prop("disabled",false);
						}

						if (currPage > 1) currPage--;
						{
							var currPage = $(this).attr('rel');
							$("a[rel='" + currPage + "']").removeClass('active');
							currPage=parseInt(currPage)-1;
							$("a[rel='" + currPage + "']").addClass('active');
							$("#previous").attr('rel',currPage);
							$("#next").attr('rel',currPage);
							var startItem = currPage * rowsShown;
							var endItem = startItem + rowsShown;
							$('.paginationtab tbody tr').css('opacity','0.0').hide().slice(startItem, endItem).
							css('display','table-row').animate({opacity:1}, 300);
						}
						if($("#next").attr('rel')==(parseInt(numPages)-1)){
							$("#next").removeAttr("disabled");
						}					                	
					});
				}
				return "";
			}

			/**
			 * 
			 * Pagination function ends here
			 */
			$('.zoomin').slimScroll({
				height:"40px"
				});
		}	
};