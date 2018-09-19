$(document).ready(function(){
	livesession().init();
});
var livesession = (function(){
	var livesessionid = $('[name=livesessionid]').val();
	var controller={
			participants:function(){
				return Ajax.get(courseData.getContextPath()+"/rest/conduct/participants/"+livesessionid,undefined,"json","application/json");
			},
			desc:function(){
				return Ajax.get(courseData.getContextPath()+"/rest/conduct/desc/"+livesessionid,undefined,"json","application/json");
			},
			lectures:function(courseid){
				return Ajax.get(courseData.getContextPath()+"/rest/conduct/lectures/"+courseid,undefined,"json","application/json");				
			}
	};

	var model={
			participants:function(){
				return controller.participants();
			},
			desc:function(){
				return controller.desc();
			},
			lectures:function(courseid){
				return controller.lectures(courseid);
			}
	};

	var templates = {
			participants:function(){
				return "{{#each .}}<li class='particpantname onlineUsersUL' orgpersonid='{{orgpersonid}}'>" +
				"{{firstName}}</li>{{/each}}";		
			},
			desc:function(){
				return "{{#each .}}<a href='"+courseData.getContextPath()+"/app/mycourses'> My Courses </a> / " +
				"<a id='previewbtn'>{{coursetitle}}</a> / Live Session ({{starttime}} to {{endtime}}){{/each}}";
			},
			lectures:function(){
				return "{{#each .}}<div class='sectionbox'><h5 class='sectiontoggle'><i class='icon-plus-sign'></i>" +
				"{{coursesectiontitle}}</h5><ul class='lecturebox'>{{#each lectures}}<li class='contentlink hover' " +
				"section='{{coursesectiontitle}}' lecture='{{courselecturetitle}}' style='padding:10px; " +
				"border-bottom:1px solid rgb(236, 236, 236);' contentpath='{{contentpath}}'>" +
				"<i class='icon-chevron-right'></i> {{courselecturetitle}}</li>{{/each}}</ul></div>{{/each}}";
			},
			pdfcontent:function(){
				return "<object style='width: 100%;height: 500px;' " +
				"data='"+courseData.getContextPath()+"/OpenFile?r1=serverpath&r2={{contentpath}}'>" +
				"Unable to view the content </object>";
			},
			videocontent:function(){
				return "<div class='row-fluid'><div class='span12 flowplayer' " +
				"data-swf='"+courseData.getContextPath()+"/assets/flowplayer/igplayer.swf' " +
				"data-ratio='0.5' style='width: 100%; height: 500px;'><video data-setup='" +
				"{'customControlsOnMobile': true}'><source type='video/flv' " +
				"src='"+courseData.getContextPath()+"/PlayFile?r1=serverpath&r2={{contentpath}}'>" +
				"</source></video></div></div>";
			}
	};

	var view={
			participants:function(){
				var participantsList = model.participants();
				helper.render(templates.participants(),participantsList,"#slimstudents");
				$('#slimstudents').slimscroll({height: "210px"});
				$('#chatroomholder').slimscroll({height: "210px"});
				view.chat();
				view.session();
				view.desc();

				$('#previewbtn').click(function()	{				
					$('[name=previewform]').attr('action','previewcourse');
					$('[name=previewform]').submit();
				});  
			},

			chat:function(){
				CHATMESSAGE = "chatmessage";
				NEWUSER = "newuser";
				GROUPCHATMESSAGE="groupchatmessage";
				ERRORMESSAGE="error";
				LOGEDOUT="logeout";		
				$(".groupChatBox").keyup (function(event){																	
					if(event.keyCode == '13'){
						var participantsList = model.participants();
						var firstName=$('[name=firstname]').val();
//						$(".groupChatHistory").append("Me:"+$(this).val());						
						var $groupChatHistory = $(".groupChatHistory");
						var from =firstName;
						var message="message="+$(this).val();
						var chatMessage = GROUPCHATMESSAGE+"#"+from +"#"+message+ "<br>";								
						clientServerOperations.sendMessage(chatMessage);
						$(this).val('');												
					}

				});

				clientServerOperations = new ClientServerOperationsHandler("myChannel", onMessageReceive);
				clientServerOperations.createConnection();	
				var loginUserDetails = "Welcome, "+$('[name=firstname]').val();
				$(".loginUserDetails").html(loginUserDetails);
				view.populateFriendList();
			},
			renderChatDiv:function(userName){							 
				var userName= $('[name=firstname]').val();				
				if( $( "#"+userName).length ){
					if(! $( "#"+userName).is(":visible")  ){
						$( "#"+userName).show();
					}
					return;
				}

				var chatWindow = "<div id='"+userName+"' class='chatDiv popwindow col-md-12 boxshadow pad0'>"+
				"<div class='col-md-12 chatDivTitle'>"+userName+" <a href='#' style='float:right' class='chatClose'>close</a></div>"+
				"<div rows='12' id='chatHistory' class='chatHistory col-md-12 pad0' readonly='true' cols='30' ></div><br>"+
				"<textarea rows='3' id='chatBox' class='chatBox form-control' cols='30' ></textarea>"+
				"</div>";
				$(".allChatDivs").append(chatWindow);
				$("#"+userName).find(".chatClose").click(function(event){
					$(this).closest(".chatDiv").hide();
				});
				$("#"+userName).find("#chatBox").keyup(function(event){

					var keycode = (event.keyCode ? event.keyCode : event.which);
					if(keycode == '13'){
						var from =$('[name=firstname]').val();
						var to   = userName; 
						var message="message="+$(this).val();
						var chatMessage = CHATMESSAGE+"#"+from +"#"+to+"#"+message;
						clientServerOperations.sendMessage(chatMessage);

						var oldChatHistory = $("#"+userName).find("#chatHistory").val();
						$("#"+userName).find("#chatHistory").val(oldChatHistory +"me:"+$(this).val());
						$(this).val('');
						var $charHistory = $("#"+userName).find("#chatHistory");
						$charHistory .scrollTop($charHistory.prop("scrollHeight"));						
						clearInterval(interval);
					}
					if (event.keyCode == 27) {
						$(this).closest(".chatDiv").hide();
					}
				});
			},

			populateFriendList:function(){
				var users = $('[name=firstname]').val();				
				var requestParams = {
						channel:'myChannel'
				};
				requestParams = jQuery.param(requestParams);
				$.ajax({
					type : 'GET',//GET Or POST 
					url  : "ecloudbaseweb/getOnlineUsersList",
					cache: false, //get fresh copy of details.html instead of cahced one
					data :requestParams,

					success: function(response, textStatus, jqXHR){
						response = jqXHR.responseText;
						if(response ==""){

						}else{
							var users = response.split(",");

							for(var i=0;i<users.length;i++){
								if(users[i] != $('[name=firstname]').val()){
									$(".onlineUsersUL").append("<img class='onlineGreen' src=''><a style='float:right;background-color:green;' href='#'>"+users[i]+"</a>");
//									$(".onlineUsersUL").append("borderBottom");

								}
							}				  

							setSelectFriendEvent();
						}
					},

					error: function(jqXHR, textStatus, errorThrown){
						console.log(
								"The following error occured: "+
								textStatus, errorThrown
						);
					}				
				});
				function setSelectFriendEvent(){
					$(".onlineUsersUL").click(function (){
						var userName = $(this).text();							
						view.renderChatDiv(userName);				
					});
				}				
				$('#logout').click(function (){
					var userName=$('[name=firstname]').val();
					var param = "userName="+$('[name=firstname]').val()+'&channel=myChannel';
					jQuery.ajax({url:"/ecloudbaseweb/logout?"+param, async:false});

					if(confirm('Are you sure you want to logout chat session ?')){
//						$(".particpantname").addClass("onlineblue");
						var chatMessage ="Loutout#"+$('[name=firstname]').val() ;
						clientServerOperations.sendMessage(chatMessage);
						return;
					}

					return;
				});
				function logout(){						
					var param = "userName="+$('[name=firstname]').val()+'&channel=myChannel';
					jQuery.ajax({url:"/ecloudbaseweb/logout?"+param, async:false});
					//location.reload();

				}
			},

			session:function(){
				/*var http = require('http');

				var server = http.createServer(function(req, res) {
				  res.writeHead(200);
				  res.end('Hello Http');
				});
				server.listen(3020);
				var socket = io.connect('http://localhost:3020',{reconnection: false });

				// emit some data
	            socket.emit("user_online_data", {
	                mydata: {id: 1, name: "Tester", msg: "Hello!"}
	            });
	            // handle our events
	            socket.on('welcome', function (data) {
	                // process data from node script
	                $('#messages').append('<li>' + data.message + '</li>');
	                // emit some data to node script
	                socket.emit("sayhi", {
	                    data: 'Hi node!'
	                });
	            });
	            // another "event" we named "time"
	            socket.on('time', function (data) {
	                console.log(data);
	                $('#messages').append('<li>' + data.time + '</li>');
	            });
	            // if someting went wront and node script sent error data
	            socket.on('error', function () {
	                console.error(arguments);	                
	            });*/
			},
			desc:function(){
				var descList = model.desc();
				helper.render(templates.desc(),descList,"#desccontainer");
				view.lectures(descList[0].courseid);
			},
			lectures:function(courseid){
				var lecturesList = model.lectures(courseid);
				var modifiedList = helper.formatLectureList(lecturesList);
				helper.render(templates.lectures(),modifiedList,"#lectureslistholder");
				$( ".lecturedropdown" ).click(function() {
					$( "#lectureslistholder" ).toggle( "slow", function() {
					});
				});
				$('.lecturebox').hide();
				$('.lecturebox:first').show();
				$('.sectiontoggle').find('i').addClass('icon-plus-sign');
				$('.sectiontoggle').click(function(){
					$('.sectiontoggle').find('i').removeAttr('class');
					$('.sectiontoggle').find('i').addClass('icon-plus-sign');
					$(this).find('i').addClass('icon-plus-sign');
					$('.lecturebox').hide();
					$(this).parent().find('.lecturebox').show();
				});
				var obj1 = {
						contentpath :$('.contentlink:first').attr('contentpath'),
						section : $('.contentlink:first').attr('section'),
						lecture : $('.contentlink:first').attr('lecture')
				};
				$('.contentlink:first').addClass('lectureactive');
				helper.showContent(obj1);
				$('.contentlink').click(function(){
					var obj = {
							contentpath :$(this).attr('contentpath'),
							section : $(this).attr('section'),
							lecture : $(this).attr('lecture')
					};
					$('.contentlink').removeClass('lectureactive');
					$(this).addClass('lectureactive');
					helper.showContent(obj);

				});

			}
	};

	var helper = {
			render:function(tmpl,list,outputselector){
				var template = Handlebars.compile(tmpl);
				var templateHtml = template(list);
				$(outputselector).html(templateHtml);
			},
			formatLectureList:function(coursecontentlist){
				var sections = [];
				sectionids =_.uniq(_.pluck(coursecontentlist,'coursesectionid'));
				_.each(sectionids,function(coursesectionid){
					var sectionarray = _.where(coursecontentlist,{coursesectionid:coursesectionid});
					var section = _.pick(sectionarray[0],'coursesectionid','coursesectiontitle');
					var lectureids=_.uniq(_.pluck(sectionarray,'courselectureid'));
					var lecture = [];
					_.each(lectureids,function(courselectureid){
						var lectureArray = _.where(sectionarray,{courselectureid:courselectureid});
						var lectureOutput=_.pick(lectureArray[0],'coursesectiontitle','courselecturetitle','contentpath');
						lecture.push(lectureOutput);
					});
					section.lectures=lecture;
					sections.push(section);
				});
				return sections;
			},
			showContent:function(obj){
				$("#lectureslistholder" ).hide();
				$('.contenttitle').text(obj.section+" / "+obj.lecture);
				var fileextension=obj.contentpath.split('.').pop();
				switch(fileextension) {
				case "pdf":
					helper.render(templates.pdfcontent(),obj,"#contentdisplayholder");
					break;
				case "flv":
					helper.render(templates.videocontent(),obj,'#contentdisplayholder');
					$('.flowplayer').flowplayer();
					break;
				default:
					alert("should show default content");
				break;
				}
			}
	};

	return {
		init:function(){
			initialize();
		}
	};

	function initialize(){
		view.participants();
	}	
});