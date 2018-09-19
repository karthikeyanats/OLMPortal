$(document).ready(function(){
	$("#adminorganizationlist").addClass('hover').addClass('active');
	 $('.leftsideli').click(function (e) {
		    //save the latest tab; use cookies if you like 'em better:
		      sessionStorage.setItem('lastTab', $(e.target).attr('class'));
		  });
	
	orgList.loadOrganizations();	
	orgList.getFilter();
	
	if(sessionStorage.getItem('lastTab')){
		  var lastTab = sessionStorage.getItem('lastTab');
		  if(lastTab.search("vieworganization")>=0){
			  switchtab(1);
		  }
		  else if(lastTab.search("trashed")>=0){
			  switchtab(2);
		  }
	     else if(lastTab.search("subscriptionplandetail")>=0){
	    	 switchtab(3);
		  }
	  }
	function switchtab(e){
	 switch(e){
		  case 1:{
			  $('.vieworganization').trigger('click');
			  break;
		  }
		  case 2:{
			  $('.trashed').trigger('click');
			  $('.orgsearch').hide();
			  $('.deorgsearch').show();
			  break;
		  }
		  case 3:{
			  $('.subscriptionplandetail').trigger('click');
			  break;
		  }
		  }
	}
	
	
});

orgList={
		loadOrganizations:function (){
		   orgList.getAllorganization();
		   $('.organizationbtn').click(function(){
				$('#orgcreationmodal').modal('show');
			});	
		   $('.trashed').click(function(){
			   $('.vieworganization').removeClass('active');
			   $('.subscriptionplandetail').removeClass('active');
			   $(this).addClass('active');
			   orgList.getTrashedorganization();
		   });
		   $('.vieworganization').click(function(){
			   $('.trashed').removeClass('active');
			   $('.subscriptionplandetail').removeClass('active');
			   $(this).addClass('active');				   
			   orgList.getAllorganization();
		   });
		   $(".subscriptionplandetail").click(function(){
			   $('.trashed').removeClass('active');
			   $('.vieworganization').removeClass('active');
			   $(this).addClass('active');
			   orgList.loadSubscriptionList();
		   });
		   
		},
		getAllorganization:function(){
			$(".orgplan").css({"display":"none"});
			var self=this;
			var orglist=Ajax.get(courseData.getContextPath()+"/rest/organizationlist?status=A",undefined,"json","application/json");
			var orgmaxuserlist=Ajax.get(courseData.getContextPath()+"/rest/organizationlist/orgmaxuser/A",undefined,"json","application/json");
			if(orglist.length!=0 && orglist!="INTERNAL_SERVER_ERROR"){
				var organizationList=self.constructDate(orglist);
				var formatList = self.formatorganizationList(organizationList,orgmaxuserlist); 
				renderTemplate("#organizationstmpl", formatList, "#contentdisplay");
				self.pagination();
				if(organizationList!=undefined){
					var orgcount=organizationList.length;
					$('.orgcount').html(orgcount);
					//$("[name=orgcount]").val(orgcount);
				 }else{
					 $('.orgcount').html("0");
				}	
			}		
			else{
				 $('.orgcount').html("0");
				self.rendernodata("contentdisplay");
			}	
				$('.planname').click(function(){
				$('#plannamedetails').modal('show');
				$("#plannamedetailsmodal").text('Plan Details');
			    var obj={};	
			    obj.status="A";			    
				obj.organizationid=$(this).attr('organizationid');			
				var plandetails=courseData.getOrganizationplan(obj);
				renderTemplate("#planinfotmpl", plandetails, "#planinfotable");
				
			});
			$('.courselist').click(function(){
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
				$('.coursedetails').html($(this).attr('orgname'));
			});	
			$('.userlist').click(function(){				
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
				$('.userdetails').html($(this).attr('orgname'));
			});
			
			$('.deactorg').click(function(){
				var organizationid=parseInt($(this).attr('organizationid'));
				/*if(confirm("Are you sure want to deactivate?")){
					var organizationid=parseInt($(this).attr('organizationid'));					
					var org={};
					org.organizationstatus="T";
					orgList.getActionorganization(organizationid,org);
				//	orgList.getTrashedorganization();
				}*/
				swal({   
					title: "Are you sure want to deactivate?",  
					animation: "slide-from-top",
					showCancelButton: true,   
					confirmButtonColor: "#DD6B55",  
					confirmButtonText: "Yes, proceed it!",   

				}, 
				function(){   
									
					var org={};
					org.organizationstatus="T";
					orgList.getActionorganization(organizationid,org);
			
				});
			});	
			/*var organizationList=Ajax.get(courseData.getContextPath()+"/rest/organizationlist",undefined,"json","application/json");
			if(organizationList!="INTERNAL_SERVER_ERROR"){
				var orgcount=organizationList.length;
				$('.orgcount').html(orgcount);
				$("[name=orgcount]").val(orgcount);
			}else{
				 $('.orgcount').html("0");
			}*/
			var deorganizationList=Ajax.get(courseData.getContextPath()+"/rest/organizationlist?status=T",undefined,"json","application/json");
			if(deorganizationList!="INTERNAL_SERVER_ERROR"){
				var trashcount=deorganizationList.length;
				$('.trashcount').html(trashcount);
				$("[name=orgtrashcount]").val(trashcount);
			}else{
				 $('.trashcount').html("0");
			}	

			$('.orgname').click(function(){
				$('#plannamedetails').modal('show');
				$("#plannamedetailsmodal").text('Organization Details');
				var orgname=$(this).attr('orgname');
				var organizationstatus="A";
				
				var organizationid=$(this).attr('organizationid');
				var orgdetail=courseData.Organizationdetail(organizationid,organizationstatus);
				if(orgdetail!=''){
				renderTemplate("#orgdetail", orgdetail, "#planinfotable");
				}else{
					renderTemplate("#nodatatempplandetails", "", "#planinfotable");
				}
			
				
			});
			
		//  organization detail	
			$('.readmore').click(function(){
				$('#orgdetailslist').modal('show');
				var orgname=$(this).attr('orgname');
				
				var organizationid=$(this).attr('organizationid');
				var plans=courseData.getOrganizationplanlist(organizationid);
				Handlebars.registerHelper('dateParser', function(planstartdate) {
					return new Date(planstartdate).toDateString();
				});
				Handlebars.registerHelper('dateParser', function(planenddate) {
					return new Date(planenddate).toDateString();
				});
				if(plans!=''){
				renderTemplate("#orginfotmpl", plans, "#orginfotable");
				}else{
					renderTemplate("#nodatatempplandetails", "", "#orginfotable");
					
				}
				$('.orgdetails').text(orgname);
				/*var orgnames=_.uniq(_.pluck(plans,'orgname'));
		     	$('.orgdetails').html(orgnames);*/
				
			});
	
		},

			constructDate:function(orglist){
			var self=this;
			var organlistArray=[];
			_.each(orglist,function(orgdata){
				if(orgdata.planstartdate!=undefined){
					var castDate=orgdata.planstartdate;
					var planstartdate=self.formatDate(castDate);
					orgdata.planstartdate=planstartdate;
					organlistArray.push(orgdata);
				}else{
					organlistArray.push(orgdata);
				}
			});
			return organlistArray;
		},		
		formatDate:function(castDate){
     		var dateFormat1=castDate.split('-');
			var formatDate=dateFormat1[2]+"-"+dateFormat1[1]+"-"+dateFormat1[0];
			return formatDate;
		},
		getTrashedorganization:function(){
			$(".orgplan").css({"display":"none"});
			var self=this;
			var deorglist=Ajax.get(courseData.getContextPath()+"/rest/organizationlist?status=T",undefined,"json","application/json");
			var orgmaxuserlist=Ajax.get(courseData.getContextPath()+"/rest/organizationlist/orgmaxuser/T",undefined,"json","application/json");
			if(deorglist.length!=0 && deorglist!="INTERNAL_SERVER_ERROR"){
			var deorganizationList=self.constructDate(deorglist);
			var formatList = self.formatorganizationList(deorganizationList,orgmaxuserlist);
			renderTemplate("#deorganizationstmpl", formatList, "#contentdisplay");
			self.pagination();
			if(deorganizationList!=undefined){
				var trashcount=deorganizationList.length;
				$('.trashcount').html(trashcount);
				}			
			else{
			 $('.trashcount').html("0");
				self.rendernodata("contentdisplay");
			}	
			
			}
			else{
				 	$('.trashcount').html("0");
					self.rendernodata("contentdisplay");
				}	
			$('.planname').click(function(){
				$('#plannamedetails').modal('show');
			    var obj={};
				obj.organizationid=$(this).attr('organizationid');
				obj.status="T";
 				var plandetails=courseData.getOrganizationplan(obj);
				renderTemplate("#planinfotmpl", plandetails, "#planinfotable");
				
			});
			$('.courselist').click(function(){
				$('#plannamedetails').modal('show');
				var obj={};
				obj.orgpersonid=$(this).attr('personid');
				var plandetails=courseData.getOrganizationcourses($(this).attr('orgplansubscriptionid'));
				var orgnames=_.uniq(_.pluck(plandetails,'orgname'));
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
				$('.coursedetails').html(orgnames);
			});	
			$('.userlist').click(function(){
				$('#plannamedetails').modal('show');
				var obj={};
				obj.organizationid=$(this).attr('organizationid');
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
				var orgnames=_.uniq(_.pluck(users,'orgname'));
				renderTemplate("#userinfotmpl", users, "#planinfotable");
				$('.userdetails').html(orgnames);
			});
			
			
			$('.actorg').click(function(){
				var organizationid=parseInt($(this).attr('organizationid'));
				var org={};						
				org.organizationstatus="A";
				orgList.getActionorganization(organizationid,org);		
				orgList.getAllorganization();
				orgList.getTrashedorganization();
			});
	
		
		
			$('.delete').click(function(){
				if(confirm("Are you sure want to delete?")){
				var organizationid=parseInt($(this).attr('organizationid'));
				var org={};						
				org.organizationstatus="D";
				orgList.getDeleteorganization(organizationid,org);
				}
				
			});
			$('.orgname').click(function(){
				
				$('#plannamedetails').modal('show');
				var orgname=$(this).attr('orgname');
				var organizationstatus="T";
				
				var organizationid=$(this).attr('organizationid');
				var orgdetail=courseData.Organizationdetail(organizationid,organizationstatus);
				if(orgdetail!=''){
				renderTemplate("#orgdetail", orgdetail, "#planinfotable");
				}else{
					renderTemplate("#nodatatempplandetails", "", "#planinfotable");
					
				}
			});
		},
		formatorganizationList:function(orglist,orgmaxuserlist){
			var maxuser = [];
			if(orglist)
			var pluckorgsid = _.pluck(orglist,'organizationid');
			_.each(pluckorgsid,function(pluckorgsids){
				var userlist = {};
				var ids = _.findWhere(orglist,{organizationid:pluckorgsids});
				var pluckid = _.pluck(orgmaxuserlist,'organizationid');
				_.each(pluckid,function(pluckids){
					var idss = _.findWhere(orgmaxuserlist,{organizationid:pluckids});
					if(idss.organizationid==ids.organizationid){
						userlist.id=ids.id;
						userlist.planid=ids.planid;
						userlist.noofcourses=ids.noofcourses;
						userlist.organizationid=ids.organizationid;
						userlist.planname = ids.planname;
						userlist.orgname = ids.orgname;
						userlist.maxcourse= ids.maxcourse;
						userlist.maxusers = ids.maxusers;
						userlist.noofusers = idss.noofusers;
						maxuser.push(userlist);
					}
				});
			});
			return maxuser;
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
						userlist.noofcourses=ids.noofcourses;
						userlist.organizationid=ids.organizationid;
						userlist.planname = ids.planname;
						userlist.orgname = ids.orgname;
						userlist.maxcourse= ids.maxcourse;
						userlist.maxusers = ids.maxusers;
						userlist.noofusers = idss.noofusers;
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
		rendernodata:function(parentdiv){
			//var divheight=document.getElementById(parentdiv).style.getPropertyValue("min-height");
			//var middle=divheight.substring(0,divheight.length-2)/2-20;	
			renderTemplate("#nodatatemp", "","#"+parentdiv);
			//$('.nodata').attr("style","margin-top:"+middle+"px");	
			
		},	
		
		getActionorganization:function(organizationid,org){		
			var OrgStatus=Ajax.put(courseData.getContextPath()+"/rest/organizationlist?organizationid="+organizationid,org,"json","application/json");
			if(OrgStatus=="CREATED"){				
					if(org.organizationstatus=="A"){
						$('.vieworganization').removeClass('active');
						$('.trashed').addClass('active');
						orgList.getTrashedorganization();
						$('#orgmessage').modal('show');	
						$('.succesmsg').text("Organization is Activated Successfully");
						
					}else if(org.organizationstatus=="T"){
						$('.trashed').removeClass('active');
						$('.vieworganization').addClass('active');
						orgList.getAllorganization();
						$('#orgmessage').modal('show');	
						$('.succesmsg').text("Organization is DeActivated Successfully");
						
				}
	       }
		},
		
		/*patterns:function(){
			var list =  model.patterns();
			helper.render(templates.patterns(),list,"#patterns");
			$('.applypatternbtn').click(function(){
				var idval = $(this).attr('patid');
				swal({   
					title: "Are you sure to update the Pattern ?",  
					animation: "slide-from-top",
					showCancelButton: true,   
					confirmButtonColor: "#DD6B55",  
					confirmButtonText: "Yes, Update it!",   
					closeOnConfirm: false 
				}, 
				function(){   
					view.updatePattern(idval);
				});
			});
		},*/
		
		
		getDeleteorganization:function(organizationid,org){		
			var OrgStatus=Ajax.post(courseData.getContextPath()+"/rest/organizationlist/deleteorganization?organizationid="+organizationid,org,"json","application/json");
					
					if(org.organizationstatus=="D"){
						//$('.trashed').addClass('active');
						//$('.vieworganization').addClass('active');
						orgList.getTrashedorganization();
						$('#orgmessage').modal('show');	
						$('.succesmsg').text("Organization is Deleted Successfully");
						       }
		},
		
		getFilter:function(){
			var activeSystemClass = $('.list-group-item.active');
		    //something is entered in search form
		    $('#system-search').keyup( function() {
		       var that = this;
		        // affect all table rows on in systems table
		        var tableBody = $('.table-list-search tbody');
		        var tableRowsClass = $('.table-list-search tbody tr');
		        $('.search-sf').remove();
		        tableRowsClass.each( function(i, val) {		        
		            //Lower text for case insensitive
		            var rowText = $(val).text().toLowerCase();
		            var inputText = $(that).val().toLowerCase();
		            if(inputText != '')
		            {
		                $('.search-query-sf').remove();
		                tableBody.prepend('<tr class="search-query-sf"><td colspan="6"><strong>Searching for: "'
		                    + $(that).val()
		                    + '"</strong></td></tr>');
		            }
		            else
		            {
		                $('.search-query-sf').remove();
		            }

		            if( rowText.indexOf( inputText ) == -1 )
		            {
		                //hide rows
		                tableRowsClass.eq(i).hide();
		                
		            }
		            else
		            {
		                $('.search-sf').remove();
		                tableRowsClass.eq(i).show();
		            }
		        });
		        //all tr elements are hidden
		        if(tableRowsClass.children(':visible').length == 0)
		        {
		            tableBody.append('<tr class="search-sf"><td class="text-muted" colspan="6">No entries found.</td></tr>');
		        }
		    });		  
		},		
		loadSubscriptionList:function (){
			var self = this;
			$(".orgsearch").css({"display":"none"});
			$(".deorgsearch").css({"display":"none"});
			$(".orgplan").css({"display":"block"});
			var subscriptiondetails = courseData.SubscriptionPlanList();
			if(subscriptiondetails=="INTERNAL_SERVER_ERROR"){
				renderTemplate("#nodatatempsubsplandetails", undefined, "#contentdisplay");
			}
			else{
				
			var orgmaxuserlist=Ajax.get(courseData.getContextPath()+"/rest/organizationlist/orgmaxusers",undefined,"json","application/json");
			var formatList = self.formatplanList(subscriptiondetails,orgmaxuserlist);
			var subscrip={};
			subscrip=_.groupBy(formatList,'orgname');
			Handlebars.registerHelper('planstartdates', function(planstartdate) 
			 { 
				var d = new Date(parseInt(planstartdate));
		         var date2 =d.toLocaleDateString();
		         var date1 = date2.split("/");
		         var date = date1[1]+ "/" +date1[0]+ "/" + date1[2];
		         var time = d.toLocaleTimeString();
		         var plandatetime = date+" "+time;
	         return plandatetime;
			 });
			Handlebars.registerHelper('planenddates', function(planenddate) 
					 { 
				var d = new Date(parseInt(planenddate));
		         var date2 =d.toLocaleDateString();
		         var date1 = date2.split("/");
		         var date = date1[1]+ "/" +date1[0]+ "/" + date1[2];
		         var time = d.toLocaleTimeString();
		         var plandatetime = date+" "+time;
	         return plandatetime;
					 });
			renderTemplate("#subscriptionplantmplt", subscrip, "#contentdisplay");
			self.orgpagination();
			$('.courselist').click(function(){
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
				$('.coursedetails').html($(this).attr('orgname'));
			});	
			$('.userlist').click(function(){				
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
				$('.userdetails').html($(this).attr('orgname'));
			});
			}
		},
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
		},
		orgpagination:function(){
			/**
			 * Pagination starts here
			 *
			 * 
			 * 
			 */

			spanOffset=100;			
			var totalPages=$('.paginationtab tbody').length;
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
					$('.pagination').append('<input id="previous" rel="'+currentPage+'" class="page hover" type="button" value="<">');

					for(i;i < numPages;i++) {
						pageNum = i + 1;
						$('.pagination').append('&nbsp;<a href="javascript:void(0)" rel="'+i+'"class="page hover">'+pageNum+'</a> ');
					}
					$('.pagination').append('&nbsp;<input id="next" rel="'+currentPage+'" class="page hover" type="button" value=">">');
					$('.paginationtab').hide();
					$('.paginationtab').slice(0, rowsShown).show();
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

						$('.pagination a').removeClass('active');
						$(this).addClass('active');
						var currPage = $(this).attr('rel');
						var startItem = currPage * rowsShown;
						var endItem = startItem + rowsShown;
						$("#previous").attr('rel',currPage);
						$("#next").attr('rel',currPage);
						$('.paginationtab').css('opacity','0.0').hide().slice(startItem, endItem).
						css('display','table').animate({opacity:1}, 300);
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
							$('.paginationtab').css('opacity','0.0').hide().slice(startItem, endItem).
							css('display','table').animate({opacity:1}, 300);
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
							$('.paginationtab').css('opacity','0.0').hide().slice(startItem, endItem).
							css('display','table').animate({opacity:1}, 300);
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

