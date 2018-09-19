$(document).ready(function(){	
	$('.leftsideli').click(function (e) {
		//save the latest tab; use cookies if you like 'em better:
		sessionStorage.setItem('lastTab', $(e.target).attr('class'));
	});

	var rolename=$('.rolefind').attr('rolename');
	var message=$("#msghidden").text().trim();
	var notifications=$("#nothidden").text().trim();
	$('.inboxmessageslink').click(function(){
		if($(this).hasClass('active')){
			$('.leftsideli').removeClass('active');
			$(this).addClass('active');
		}
		else{
			$('.leftsideli').removeClass('active');
			$(this).addClass('active');
			loadReceivedImages();
			$('.rightpanetitle').text('Inbox');
		}
	});
	$('.othernotlink').click(function(){
		userinfoobj.otherNotificationsData("registered");
		//$('.notifyselectbtn:first').addClass('btn-blue');
	});
	$('.sentmessageslink').click(function(){
		$('.leftsideli').removeClass('active');
		$(this).addClass('active');
		loadSentMessages();
		$('.rightpanetitle').text('Sent');
		$('.messageuserbtn').hide();
	});
	$('.notification').click(function(){
		$('.leftsideli').removeClass('active');
		$(this).addClass('active');
		userinfoobj.notificationentry();
	});	
	$('.mynotificationslink').click(function(){
		$('.leftsideli').removeClass('active');
		$(this).addClass('active');
		userinfoobj.myNotifications();
	});
	$('#messagetosend').keyup(function(){
		el = $(this);
		if(el.val().length >= 501){
			el.val( el.val().substr(0, 500) );
		} else {
		}
	});
	$('#sendtouser').click(function(){
		var userids=$("#tagidss input").val();
		var messagedescription=$('#messagetosend').val().trim();
		if(messagedescription!='' && userids.length>0){
			var dataobj={};
			dataobj.toOrgpersonid=userids;
			dataobj.messagedescription=messagedescription;
			var dataresult=courseData.sendtoMultiuser(dataobj);
			if(dataresult==0){
				$("#tagidss input").val('destroy');
				$("#tagidss input").val('');
				$('#messagetosend').val('');
				loadReceivedImages();

				$('#sendmsgmodal').modal('hide');
				$('#successmodal').modal('show');
			}
		}else{
			renderTemplate("#messageemptytmpl", '', "#messageempty");
		}

	});
	//go to the latest tab, if it exists:
	if(sessionStorage.getItem('lastTab')){
		var lastTab = sessionStorage.getItem('lastTab');
		if(lastTab.search("personalinfolink")>=0){
			switchtab(1);
		}
		else if(lastTab.search("profinfolink")>=0){
			switchtab(2);
		}
		else if(lastTab.search("bank-info-link")>=0){
			switchtab(3);
		}
		else if(lastTab.search("changepwdlink")>=0){
			switchtab(4);
		}
		else if(lastTab.search("myloginslink")>=0){
			switchtab(5);
		}
		else if(lastTab.search("inboxmessageslink")>=0){
			switchtab(6);
		}
		else if(lastTab.search("sentmessageslink")>=0){
			switchtab(7);
		}
		else if(lastTab.search("mynotificationslink")>=0){
			switchtab(8);
		}
		else if(lastTab.search("notification")>=0){
			switchtab(9);
		}
		else if(lastTab.search("othernotlink")>=0){
			switchtab(10);
		}
	}
	function switchtab(e){
		switch(e){
		case 1:{
			$('.personalinfolink').trigger('click');
			break;
		}
		case 2:{
			$('.profinfolink').trigger('click');
			break;
		}
		case 3:{
			$('.bank-info-link').trigger('click');
			break;
		}
		case 4:{
			$('.changepwdlink').trigger('click');
			break;
		}
		case 5:{
			$('.myloginslink').trigger('click');
			break;
		}
		case 6:{
			$('.inboxmessageslink').trigger('click');
			break;
		}
		case 7:{
			$('.sentmessageslink').trigger('click');
			break;
		}
		case 8:{
			$('.mynotificationslink').trigger('click');
			break;
		}
		case 9:{
			$('.notification').trigger('click');
			break;
		}
		case 10:{
			$('.othernotlink').trigger('click');
			break;
		}

		}
	}
	if(message=="msg"){
		$('.active').removeClass('active');	
		$('.inboxmessageslink').addClass('active');
		loadReceivedImages();
		removeUrlParams();
	}
	if(notifications=="not"){
		$('.active').removeClass('active');	
		$('.mynotificationslink').addClass('active');
		userinfoobj.myNotifications();
		removeUrlParams();
	}
	if(rolename!="admin"){
		$('.notification').hide();
		$('.othernotlink').hide();
	}
	else{
		$('.notification').show();
		$('.othernotlink').show();
	}
	taginput();
});
function removeUrlParams(){
    var url=window.location.href;
    window.history.pushState(undefined, "Title", url.split('?')[0]);
};
function loadMessages(){
	loadReceivedImages();
	loadSentMessages();
}
function taginput(){
	
	var organizationid=$('#organizationid').val();
	var obj={};
	obj.organizationid=organizationid;
	var users=courseData.getOrgzUsers(obj);
	var userinfo = new Bloodhound({
		datumTokenizer: Bloodhound.tokenizers.obj.whitespace('username'),
		queryTokenizer: Bloodhound.tokenizers.whitespace,
		local:users
	});
	userinfo.initialize();

	var elt = $('#tagidss input');
	elt.tagsinput({
		freeInput: false,
		itemValue: 'orgpersonid',
		itemText: 'username',
		allowDuplicates: false,
		typeaheadjs: {
			name: 'cities',
			displayKey: 'username',
			source: userinfo.ttAdapter()
		}
	});
}
function loadReceivedImages(){
	var receivedMessages=courseData.getReceivedMessages();
	renderTemplate("#messageinfotmpl", receivedMessages, "#personalinfotable");

	
	
	$('#sendmsgto').click(function(){
		
		$('#sendmsgmodal').modal('show'); 
		$('#messageempty').empty();
		$("#messagetosend").val('');
		
		if($(".tag").text()!=""){
			$("#tagidss input").tagsinput("destroy");
			taginput();
		}
		/*------------------for single autocomplete this code --------------------*/
		/* $('#search').typeahead({
				    source: function(query, process) {
				        objects = [];
				        map = {};
				        var data = users;
				        $.each(data, function(i, object) {
				        	console.log(object.username);
				            map[object.username] = object;
				            objects.push(object.username);
				        });
				        process(objects);
				    },
				    updater: function(item) {
				        $('#hiddenpersonid').val(map[item].orgpersonid);
				        return item;
				    }
				});

			$('#sendtouser').click(function(){
			var messagedescription=$('#messagetosend').val();
			var chatid=  $('#hiddenpersonid').val();
			if(messagedescription!=''){
				userinfoobj.sendMessageData(chatid,messagedescription);
			}
		});*/



	});
	/*---------------Tag Input data goes here------------------------------------*/



	/*$('#sendtouser').click(function(){
		console.log("send msg to ========>>>");
		var userids=$("#tagidss input").val();
		var messagedescription=$('#messagetosend').val();
		if(messagedescription!=''){
		var dataobj={};
		dataobj.toOrgpersonid=userids;
		dataobj.messagedescription=messagedescription;
		var dataresult=courseData.sendtoMultiuser(dataobj);
		if(dataresult==0){
			loadReceivedImages();

		$('#sendmsgmodal').modal('hide');
		}
		}else{
			renderTemplate("#messageemptytmpl", receivedMessages, "#messageempty");
		}

	});*/



	$('.messageuserbtn').click(function(){
		$('.chatername').text($(this).attr('personname'));  
		$('.popwindow').show();  
		userinfoobj.loadparticularusersmessage($(this).attr('orgpersonid')); 
		$('.sendmessagebtn,.chatmessage').attr('chatterpersonid',$(this).attr('orgpersonid')); 
	}); 
	$('.chatmessage').keyup(function(){
		el = $(this);
		if(el.val().length >= 501){
			el.val( el.val().substr(0, 500) );
		} else {

		}
	});
	$('.sendmessagebtn').click(function(){
		var messagedescription=$('.chatmessage').val();
		if(messagedescription!=''){
			userinfoobj.sendMessageData($(this).attr('chatterpersonid'),messagedescription);
		}
	});
	$('#appendedInputButton').keyup(function(e){
		if(e.keyCode == 13)
		{
			var messagedescription=$('.chatmessage').val();
			if(messagedescription!=''){
				userinfoobj.sendMessageData($(this).attr('chatterpersonid'),messagedescription);
			}
		}
	});

	$('.deleteuserbtn').click(function(){
		var messageid=$(this).attr('messageid'); 
		var messageidobj={}; 
		if (confirm("Are you sure want to delete?"))
		{ 
			messageidobj.messageid=messageid;		
			var deleteMessage=courseData.deleteMessage(messageidobj);
			if(deleteMessage=="success"){
				$('#messageinfomodal').modal('show');
				$('.msginfo').text("Message successfully deleted.");
				loadReceivedImages();
				//var receivedMessages=courseData.getReceivedMessages();
				//renderTemplate("#messageinfotmpl", receivedMessages, "#personalinfotable");   
			}
		}
	}); 
	userinfoobj.pagination();
}

function loadSentMessages(){
	var sentMessages=courseData.getSentMessages(); 
	renderTemplate("#sentMsgTmpl", sentMessages, "#personalinfotable");
	$('.deleteuserbtn').click(function(){
		var messageid=$(this).attr('messageid'); 
		var messageidobj={}; 
		if (confirm("Are you sure want to delete?")) 
		{ 
			messageidobj.messageid=messageid;		
			var deleteMessage=courseData.deleteMessage(messageidobj);
			if(deleteMessage=="success"){
				$('#messageinfomodal').modal('show');
				$('.msginfo').text("Message successfully deleted.");
				loadSentMessages(); 
			}
		}
	}); 
	userinfoobj.pagination();

}

userinfoobj={		
		loadprofiledetail:function(personid){
			var self=this;
			var personidobj={};
			personidobj.personid=personid;
			var userprofileValues=courseData.loadProfileData(personidobj);
			self.renderProfileValue(userprofileValues);
		},
		renderProfileValue:function(userprofileValues){
			if(userprofileValues==''|| userprofileValues.length<0){
				renderTemplate('#datanotfoundtmpl',userprofileValues,'#profileinfotable');	
			}
			else{
				$('#warningtextdiv').hide();
				renderTemplate('#profileinfotmpl',userprofileValues,'#profileinfotable');	
			}
		},
		loadlearningcourses:function(personid){
			var self=this;
			var personidobj={};
			personidobj.personid=personid;
			var coursesList=courseData.getIndividualCourses(personidobj);
			self.renderLearningCourses(coursesList);
		},
		renderLearningCourses:function(coursesList){
			if(coursesList == '' || coursesList=="false"){
				renderTemplate('#coursenotregsiteredtmpl',coursesList,'#coursesinfotable');	
			}
			else{
				$('#warningtextcoursediv').hide();
				renderTemplate('#coursesinfotmpl',coursesList,'#coursesinfotable');	
			}
		},
		loadparticularusersmessage:function(orgpersonid){
			var self=this;
			var particularusersmessagelist=self.getMessages(orgpersonid);
			if(particularusersmessagelist == '' || particularusersmessagelist=="false"){
				renderTemplate("#nomessagestmpl",particularusersmessagelist,"#messagetable");
				self.chatclose();
			}
			else{
				renderTemplate("#messagestmpl",particularusersmessagelist,"#messagetable");
				self.chatclose();
			}
		},
		getMessages:function(orgpersonid){
			var personidobj={};
			personidobj.toOrgpersonid=orgpersonid;
			var particularusersmessagelist=courseData.getMessagesForuser(personidobj);
			return particularusersmessagelist;	
		},
		sendMessageData:function(personid,messagedescription){
			var self=this;
			var personidobj={};
			personidobj.toOrgpersonid=personid;
			personidobj.messagedescription=messagedescription;
			self.sendMessage(personidobj);	
		},
		sendMessage:function(personidobj){
			var self=this;
			var successvalue=courseData.sendMessage(personidobj);
			if(successvalue!="false"){
				$('.chatmessage').val('');
				self.loadparticularusersmessage(personidobj.toOrgpersonid);
			}
		},
		chatclose:function(){
			$('.closechatwindowbtn').click(function(){
				$('.popwindow').hide();
			});	
		},
		myNotifications:function(){
			var self=this;
			var makeasReadnotify=courseData.makeAllReadnotify();
			if(makeasReadnotify=="success"){
				$('.noticircle').attr("style","display:none");
				var individualNotifyData=courseData.getMyNotifications();
				renderTemplate('#myNotificationTmpl', individualNotifyData, "#personalinfotable");
				self.pagination();
			}

		},
		otherNotificationsData:function(categorystatus){
			var self=this;
			var categoryobj={};
			categoryobj.categorystatus=categorystatus;
			var otherNotificationList=courseData.getOtherNotifications(categoryobj);
			self.renderOtherNotifications(otherNotificationList,categorystatus);			
		},
		renderOtherNotifications:function(otherNotificationList,categorystatus){
			$('.leftsideli').removeClass('active');
			$('.othernotlink').addClass('active');
			var self=this;
			var notifications={};
			notifications.othernotifications=otherNotificationList;
			renderTemplate("#otherNotificationTmpl", notifications, "#personalinfotable");
			self.pagination();
			//$('.notifyselectbtn').removeClass('btn-blue');
			$('.notifyselectbtn[categorystatus="'+categorystatus+'"]').addClass('btn-blue');
			
			$(".notificationtypeselect option[value='"+categorystatus+"']").attr('selected','selected');
			$('.nottitle').text($(".notificationtypeselect option[value='"+categorystatus+"']").text());
			
			$(".notificationtypeselect").change(function(){
				self.otherNotificationsData($(this).val());
				$('.nottitle').text($(".notificationtypeselect option:selected").text());
				
			});
			/*$('.notifyselectbtn').click(function(){
				self.otherNotificationsData($(this).attr('categorystatus'));
				$('.nottitle').text($(this).attr('tit'));
			});*/
		},
		notificationentry:function(){
			var self=this;
			var data='';
			renderTemplate("#notificationtmpl", data, "#personalinfotable");
			$('.notificationbtn').click(function(){
				$('#notifyvalidation').html("");
				var notificationmessage=$('#notificationdescmessage').val().trim();
				if((notificationmessage.length!=0)&&(notificationmessage!=undefined)){
					var notifstatus=$(this).attr('id');
					var data={};
					data.notificationdesc=notificationmessage;
					data.notificationStatus=notifstatus;
					var response=ajaxhelperwithdata("notificationSave",data);	
					if(response=="nodata"){
						renderTemplate("#nodatampl", undefined, "#notifyvalidation");				
					}else{
						self.otherNotificationsData(notifstatus);
					}	
				}else{
					renderTemplate("#validationtmpl", undefined, "#notifyvalidation");
				}				  
			});
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
		
};