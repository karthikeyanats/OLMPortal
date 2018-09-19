var courseusers = (function(){
	courseobj={
			courseid:$("#courseUserModel").attr('courseid')		
	};
	var templates={
			successMessage:function(){
				return "<div class='alert alert-success'>" +
				"<strong class='marright5'>Success!</strong>" +
				"<command class='courseusersuccessmsg'></command>" +
				"</div>";
			},
			failureMessage:function(){
				return "<div class='alert alert-danger'>" +
				"<strong class='marright5'>Failure!</strong>" +
				"<command class='courseuserfailuremsg'></command>" +
				"</div>";
			},
			learningCourseUsers:function(){
				return "" +
				"<table class='table table-bordered table-striped'>" +
				"<thead>" +
				"<tr>" +
				"<th>Sl. No</th>" +
				"<th>Name</th>" +
				"<th class='centertext'><font>Actions</font></th>" +
				"</tr>" +
				"</thead>" +
				"<tbody>{{#if .}}{{#each .}}" +
				"<tr>" +
				"<td>{{math @index '+'1}}</td>" +
				"<td><font class='overflowtext'>{{firstName}} {{lastName}}</font></td>" +
				"<td class='centertext'><a class='btn btn-primary blockUserbtn' " +
				"orgpersonid='{{orgpersonid}}' courseenrollmentid='{{courseenrollmentid}}'>Block User</a></td>" +
				"</tr>" +
				"{{/each}}{{else}}" +
				"<tr>" +
				"<td colspan='3' class='centertext'>No Users</td>" +
				"</tr>{{/if}}" +
				"</tbody>" +
				"</table>";
			},
			completedCourseUsers : function(){
				return "" +
				"<table class='table table-bordered table-striped'>" +
				"<thead>" +
				"<tr>" +
				"<th>Sl. No</th>" +
				"<th>Name</th>" +
				"</tr>" +
				"</thead>" +
				"<tbody>{{#if .}}{{#each .}}" +
				"<tr>" +
				"<td>{{math @index '+'1}}</td>" +
				"<td><font class='overflowtext'>{{firstName}} {{lastName}}</font></td>" +
				"</tr>" +
				"{{/each}}{{else}}" +
				"<tr>" +
				"<td colspan='2' class='centertext'>No Users</td>" +
				"</tr>{{/if}}" +
				"</tbody>" +
				"</table>";
			},
			blockedCourseUsers:function(){
				return "" +
				"<table class='table table-bordered table-striped'>" +
				"<thead>" +
				"<tr>" +
				"<th>Sl. No</th>" +
				"<th>Name</th>" +
				"<th class='centertext'><font>Actions</font></th>" +
				"</tr>" +
				"</thead>" +
				"<tbody>{{#if .}}{{#each .}}" +
				"<tr>" +
				"<td>{{math @index '+'1}}</td>" +
				"<td><font class='overflowtext'>{{firstName}} {{lastName}}</font></td>" +
				"<td class='centertext'><a class='btn btn-primary unblockUserbtn' " +
				"orgpersonid='{{orgpersonid}}' courseenrollmentid='{{courseenrollmentid}}'>Un-Block User</a></td>" +
				"</tr>" +
				"{{/each}}{{else}}" +
				"<tr>" +
				"<td colspan='3' class='centertext'>No Users</td>" +
				"</tr>{{/if}}" +
				"</tbody>" +
				"</table>";
			},
			availableCourseUsers:function(){
				return "<div id='errholder'></div><div id='availcoursesusertable'>" +
				"<table class='table table-bordered table-striped width100'>" +
				"<thead>" +
				"<tr>" +
				"<th>Sl. No</th>" +
				"<th>Name</th>" +
				"<th class='centertext'>" +
				"<font><a class='btn btn-primary selectallsuserbtn'>Select All</a></font>" +
				"</th>" +
				"</tr>" +
				"</thead>" +
				"<tbody>{{#if .}}{{#each .}}" +
				"<tr>" +
				"<td>{{math @index '+'1}}</td>" +
				"<td><font class='overflowtext'>{{firstName}} {{lastName}}</font></td>" +
				"<td class='centertext'><a class='btn btn-primary selectuserbtn' " +
				"personid='{{personid}}'>Select</a></td>" +
				"</tr>{{/each}}{{else}}" +
				"<tr>" +
				"<td colspan='3' class='centertext'>No Users</td>" +
				"</tr>{{/if}}" +
				"</tbody>" +
				"</table>" +
				"</div>";
			},
			invitedCourseUsers : function(){
				return "" +
				"<table class='table table-bordered table-striped'>" +
				"<thead>" +
				"<tr>" +
				"<th>Sl. No</th>" +
				"<th>Name</th>" +
				"</tr>" +
				"</thead>" +
				"<tbody>{{#if .}}{{#each .}}" +
				"<tr>" +
				"<td>{{math @index '+'1}}</td>" +
				"<td><font class='overflowtext'>{{emailid}}</font></td>" +
				"</tr>" +
				"{{/each}}{{else}}" +
				"<tr>" +
				"<td colspan='2' class='centertext'>No Users</td>" +
				"</tr>{{/if}}" +
				"</tbody>" +
				"</table>";
			},
	}; 

	var view = {
			users:function(){
				$('.cusertitle').text($("#courseUserModel").attr('coursetitle'));
				$('.courseusers').removeClass('active');
				$('.courseusers:first').addClass('active');
				view.learning();
				$('.courseusers').click(function(){
					$('.courseusers').removeClass('active');
					$(this).addClass('active');
					var attrval=$(this).attr('a');
					switch(attrval){
					case "learning": 
						view.learning();
						break;
					case "blocked": 
						view.blocked();
						break;
					case "available": 
						view.available();
						break;
					case "completed": 
						view.completed();
						break;
					case "invited": 
						view.invited();
						break;
					}
				});
			},	
			learning : function(){
				$('.assignSubmitbtn').hide();
				var list =model.learning();
				util.render(templates.learningCourseUsers(),list,"#usercourselist");
				$('#usercourselist').slimscroll({height: "400px"});
				$('.blockUserbtn').click(function(e){
					var checkstr =  confirm('Are you sure you want to block this user');
					if(checkstr == true){
						var conformation = view.updateEnrollment($(this).attr('courseenrollmentid'),"B");
						if(conformation=="OK"){
							util.render(templates.successMessage(),null,"#usercourselist");
							$('.courseusersuccessmsg').text("");
							$('.courseusersuccessmsg').text("User is blocked Successfully");
							setTimeout(function(){
								view.learning();
							},3000);

						}else{
							util.render(templates.failureMessage(),null,"#usercourselist");
							$('.courseuserfailuremsg').text("");
							$('.courseuserfailuremsg').text("Error in blocking the user. Please try again later.");
							setTimeout(function(){
								view.learning();
							},3000);
						}
					}
				});				
			},
			completed : function(){
				$('.assignSubmitbtn').hide();
				var list =model.completed();
				util.render(templates.completedCourseUsers(),list,"#usercourselist");
				$('#usercourselist').slimscroll({height: "400px"});
			},
			invited : function(){
				$('.assignSubmitbtn').hide();
				var list =model.invited();
				if(list=="false"){
					lists = null;
					util.render(templates.invitedCourseUsers(),lists,"#usercourselist");
				}
				else{
					util.render(templates.invitedCourseUsers(),list,"#usercourselist");
				}
				$('#usercourselist').slimscroll({height: "400px"});
			},
			blocked : function(){
				$('.assignSubmitbtn').hide();
				var list =model.blocked();
				util.render(templates.blockedCourseUsers(),list,"#usercourselist");
				$('#usercourselist').slimscroll({height: "400px"});
				$('.unblockUserbtn').click(function(e){
					var checkstr =  confirm('Are you sure you want to un-block this user');
					if(checkstr == true){
						var conformation = view.updateEnrollment($(this).attr('courseenrollmentid'),"A");
						if(conformation=="OK"){
							util.render(templates.successMessage(),null,"#usercourselist");
							$('.courseusersuccessmsg').text("");
							$('.courseusersuccessmsg').text("User is Un-blocked Successfully");
							setTimeout(function(){
								view.blocked();
							},3000);

						}else{
							util.render(templates.failureMessage(),null,"#usercourselist");
							$('.courseuserfailuremsg').text("");
							$('.courseuserfailuremsg').text("Error in blocking the user. Please try again later.");
							setTimeout(function(){
								view.blocked();
							},3000);
						}
					}
				});	
			},
			updateEnrollment:function(id,status){
				var okval = model.updateEnrollment(id,status);
				if(okval=="OK"){
					return "OK";	
				}else{
					return null;
				}
			},
			available : function(){ 
				var list =model.available();
				util.render(templates.availableCourseUsers(),list,"#usercourselist");
				if(list!=null){
					$('.assignSubmitbtn').show();
				}else{
					$('.assignSubmitbtn').hide();
					$('.selectallsuserbtn').hide();
				}
				
				$('#availcoursesusertable').slimscroll({height: "400px"});
				$('.selectuserbtn').click(function(){
					var text = $(this).text();
					text == "Select" ? util.selectSingle($(this)) : util.deselectSingle($(this));					
				});
				$('.selectallsuserbtn').click(function(){
					var text = $(this).text();
					text=="Select All" ?  util.selectAll():util.deselectAll();
				});
				$('.assignSubmitbtn').unbind().click(function(e){
					view.assignUsers();	
				});
			}, 
			assignUsers:function(){
				$("#errholder").hide();
				var selectedCount = $(".selectuserbtn:contains('Selected')").length;
				if(selectedCount>0){
					var checkstr =  confirm('Are you sure you want to assign the following user(s) to the course');
					if(checkstr == true){
						personidval=[];
						$(".selectuserbtn:contains('Selected')").each(function(){
							personidval.push($(this).attr('personid'));			
						});
						var obj = {};
						obj.courseid = $("#courseUserModel").attr('courseid');
						obj.personid = personidval.toString();
						var assignModel = model.assign(obj);
						if(assignModel=="OK"){
							util.render(templates.successMessage(),null,"#usercourselist");
							$('.courseusersuccessmsg').text("");
							$('.courseusersuccessmsg').text("User(s) has been enrolled");
							setTimeout(function(){
								view.available();
							},3000);

						}else{
							util.render(templates.failureMessage(),null,"#usercourselist");
							$('.courseuserfailuremsg').text("");
							$('.courseuserfailuremsg').text("Error in enrolling the user. Please try again later.");
							setTimeout(function(){
								view.available();
							},3000);
						}
					}							
				}else{
					$("#errholder").show();
					util.render(templates.failureMessage(),null,"#errholder");
					$('.courseuserfailuremsg').text("");
					$('.courseuserfailuremsg').text("Please choose users to assign.");
					setTimeout(function(){
						$("#errholder").hide();
					},3000);
				}												
			}
	};
	var model={
			learning : function(){
				var list=controller.learning(courseobj);
				return util.listHelper(list);				
			},
			blocked : function(obj){
				var list=controller.blocked(courseobj);
				return util.listHelper(list);
			},
			available : function(){
				var list=controller.available(courseobj);
				return util.listHelper(list);
			},
			completed : function(){
				var list=controller.completed(courseobj);
				return util.listHelper(list);
			},
			invited : function(){
				var list=controller.invited(courseobj);
				return util.listHelper(list);
			},
			updateEnrollment:function(status,id){
				var confimationString = controller.updateEnrollment(status,id);
				if(confimationString=="OK"){
					return "OK";
				}else{
					return "NO";
				}
			},
			assign:function(obj){
				var confimationString = controller.assign(obj);
				if(confimationString=="SUCCESS"){
					return "OK";
				}else{
					return "NO";
				}
			}
	};
	var controller={
			learning : function(obj){
				return Ajax.get(courseData.getContextPath()+"/rest/courseusers/A/"+obj.courseid,undefined,"json","application/json");				
			}, 
			blocked : function(obj){
				return Ajax.get(courseData.getContextPath()+"/rest/courseusers/B/"+obj.courseid,undefined,"json","application/json");				 
			},
			completed : function(obj){
				return Ajax.get(courseData.getContextPath()+"/rest/courseusers/C/"+obj.courseid,undefined,"json","application/json");				 
			},
			available : function(obj){
				return Ajax.get(courseData.getContextPath()+"/rest/courseusers/N/"+obj.courseid,undefined,"json","application/json");
			},
			invited : function(obj){
				return ajaxhelperwithdata("authorcourseusers",obj);
			},
			updateEnrollment:function(status,id){
				return Ajax.put(courseData.getContextPath()+"/rest/courseusers/update/"+id+"/"+status,undefined,"json","application/json");
			},
			assign:function(obj){
				return ajaxhelperwithdata("submitCoursesforUsers", obj);
			}
	};
	var util = {
			render:function(tmpl,list,outputselector){
				var template = Handlebars.compile(tmpl);
				var templateHtml = template(list);
				$(outputselector).html(templateHtml);				
			},
			listHelper:function(list){
				if(list=="NO_CONTENT"){
					return null;
				}else if(list=="INTERNAL_SERVER_ERROR"){
					return null;
				}else{
					return list;
				}
			},
			selectSingle:function(obj){
				obj.text("Selected").removeClass('btn-primary').removeClass('btn-danger');
				obj.addClass('btn-danger');
				util.checkAll();
			},			
			deselectSingle:function(obj){
				obj.text("Select").removeClass('btn-primary').removeClass('btn-danger');
				obj.addClass('btn-primary');
				util.checkAll();
			},
			selectAll:function(){
				$('.selectuserbtn').removeClass('btn-primary').removeClass('btn-danger');
				$('.selectuserbtn').addClass('btn-danger').text("Selected");
				$('.selectallsuserbtn').removeClass('btn-primary').removeClass('btn-danger');
				$('.selectallsuserbtn').addClass('btn-danger').text("De-Select All");
			},
			deselectAll:function(){
				$('.selectuserbtn').removeClass('btn-primary').removeClass('btn-danger');
				$('.selectuserbtn').addClass('btn-primary').text("Select");
				$('.selectallsuserbtn').removeClass('btn-primary').removeClass('btn-danger');
				$('.selectallsuserbtn').addClass('btn-primary').text("Select All");
			},
			checkAll:function(){
				var selectedUsersLength = $(".selectuserbtn:contains('Selected')").length;
				var allUsersLength = $(".selectuserbtn").length;
				if(selectedUsersLength==allUsersLength){
					$('.selectallsuserbtn').text("De-Select All");
					$('.selectallsuserbtn').removeClass('btn-primary').removeClass('btn-danger');
					$('.selectallsuserbtn').addClass('btn-danger');
				}else{
					$('.selectallsuserbtn').text("Select All");
					$('.selectallsuserbtn').removeClass('btn-primary').removeClass('btn-danger');
					$('.selectallsuserbtn').addClass('btn-primary');
				}
			}
	};
	return {
		init:function(){
			initialize();
		}
	};
	function initialize(){
		view.users();		 
	};
});	