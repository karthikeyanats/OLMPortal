$(document).ready(function(){
	contentApprove().init();
});	

var contentApprove = (function () {
	var controller = {	
			contentApprovals:function(){
				var courseid=$courseid.val();
				return Ajax.get(courseData.getContextPath()+"/rest/approve/"+courseid+"",null,"json","application/json");
			},
			contentView:function(courselectureid){
				return Ajax.get(courseData.getContextPath()+"/rest/approve/content/"+courselectureid+"",null,"json","application/json");
			},
			contentApprove:function(approveobj){
				return Ajax.post(courseData.getContextPath()+"/rest/approve/create",approveobj,"json","application/json"); 
			},
			courseApprove:function(approveobj){
				return Ajax.post(courseData.getContextPath()+"/rest/approve/course",approveobj,"json","application/json"); 
			}	
	}; 
	var view = {	
			contentApprovals:function(){
				var contentApproveList= model.contentApprovals();
				renderTemplate("#subjectpartstmpl", contentApproveList, "#subjectparts");
				$authororgpersonid.val(contentApproveList[0].authororgpersonid);
				var coursetitle=contentApproveList[0].coursetitle;
				$('.crstitle').text(coursetitle);
				var $contentlink=$('.contentlink'),$courselink=$('.coursebtn'), $contentlinkfirst=$('.contentlink:first');
				$('.accordion-body:first').addClass('in').css('height','auto');
				$contentlinkfirst.addClass('active');
				this.contentStatusView();
				$contentlink.click(function(){
					$contentlink.removeClass('active');
					$(this).addClass('active');
					view.contentStatusView();
				});
				view.courselinkListener($courselink,coursetitle);				
			},
			courselinkListener:function($courselink,coursetitle){
				$courselink.unbind().click(function(e){
					var operation=$(this).attr('op');
					var updatestatus=$(this).attr('status');
					var obj=model.courseApproveModel(updatestatus,$courseid.val());
					obj.authororgpersonid=$authororgpersonid.val();
					obj.coursetitle=coursetitle;
					var checkstr =  confirm('Are you sure to  '+operation+' this Course');
					if(checkstr == true)	{
						var updatedCourseStatus=model.approveCourse(obj);
						if(updatedCourseStatus==="OK"){
							$('.updatemessge').show();
							if(updatestatus==="A"){
								$('.updatemsgdesc').text('The Course Has been Successfully approved');
							}
							else if(updatestatus==="D"){
								$('.updatemsgdesc').text('The Course has been rejected and instructions has been sent to the user');
							}
							setTimeout(function(){
								$('.updatemessge').hide();
								if(updatestatus==="A"){
									$('[name=contentapproveform]').attr('action','courses?a=pc');
								}
								else if(updatestatus==="D"){
									$('[name=contentapproveform]').attr('action','courses?a=dc');								
								}
								$('[name=contentapproveform]').submit();
							},5000);
						}
					}else{
						e.preventDefault();
					}
				});
			},
			contentStatusView:function(){
				var contentViewList=model.contentView($('.contentlink.active').attr('courselectureid'));
				this.renderStatusView(contentViewList);
				this.renderContent(contentViewList);
			},
			renderStatusView:function(contentViewList){
				renderTemplate("#contentstatustmpl", contentViewList, "#contentstatustable");
				var activecontentlink=$('.contentlink.active');
				var lecture=activecontentlink.attr('courselecturetitle');
				var section=activecontentlink.attr('coursesectiontitle');
				$('.cntstatusfor').text(section+" / "+lecture);
				if(contentViewList[0].contentapprovalid!=null){
					if(contentViewList[0].approvestatus=="R"){						
						$('.rejectbtn').text("Rejected");
						$('.rejectbtn').removeClass("rejectbtn");
						$('.approvebtn').text("Approve");
					}
					else if(contentViewList[0].approvestatus=="A"){						
						$('.approvebtn').text("Approved");
						$('.approvebtn').removeClass('approvebtn');
					}
					$('.approvebtn').attr('contentapprovalid',contentViewList[0].contentapprovalid);
					$('.rejectbtn').attr('contentapprovalid',contentViewList[0].contentapprovalid);
					$('.rejectbtn').attr('oper','update').attr('contentid',contentViewList[0].contentid);

				}else{
					$('.rejectbtn').attr('oper','create').attr('contentid',contentViewList[0].contentid);					
				}
				$('.approvebtn').click(function(e){
					var checkstr =  confirm('Are you sure you Approve this content');
					var contentapprovalid=$(this).attr('contentapprovalid');
					if(checkstr == true)	{
						var obj = model.contentApproveModel($(this).attr('contentid'),"A","OK",contentapprovalid);
						obj.authororgpersonid=$authororgpersonid.val();;
						var status=model.approve(obj);
						if(status=="OK"){
							$('.updatemessge').show();
							$('.updatemsgdesc').text('The Content Has been Successfully approved');
							setTimeout(function(){
								$('.updatemessge').hide();
								view.decisionfunction();			
							},1000);
							view.decisionfunction();
						}
					}else{
						e.preventDefault();
					}
				});
				$('.rejectbtn').click(function(e){
					$('#approveModal').modal('show');
					$('#singlebutton').attr('contentid',$(this).attr('contentid'));
					$('#singlebutton').attr('contentapprovalid',$(this).attr('contentapprovalid'));
					view.rejectionModal();
				});
			},
			rejectionModal:function(){
				$('.reasonwarning').hide();
				$('.reasonupdate').hide();
				$('#approvedescription').keyup(function(){
				    el = $(this);
				    if(el.val().length >= 501){
				        el.val( el.val().substr(0, 500) );
				    } else {
				    	$('.rejectreasoncount').text(500-el.val().length);
				    }
				});
				$('#singlebutton').click(function(){
					$('.reasonwarning').hide();
					$('.reasonupdate').hide();	
					var obj1={};
					obj1.contentapprovalid=$(this).attr('contentapprovalid');
					obj1.contentid=$(this).attr('contentid');
					obj1.approvestatus="R";
					obj1.authororgpersonid=$authororgpersonid.val();;
					var description=$('#approvedescription').val().trim();
					if(description.length!=0){
						obj1.description=encodeURIComponent(description);
						var status=model.approve(obj1);
						if(status=="OK"){
							$('.reasonupdate').show();
							$('#approvedescription').val('');
							setTimeout(function(){
								$('.reasonupdate').hide();
								$('#approveModal').modal('hide');
								view.decisionfunction();			
							},1000);				
						}
					}else{
						$('.reasonwarning').show();
						setTimeout(function(){
							$('.reasonwarning').hide();
						},1000);
					}					
				});
			},
			renderContent:function(contentViewList){
				var self=this;
				var s3=_.last(contentViewList[0].contentpath.split('/'));
				var s4=s3.split('.');
				var extn=_.last(s4);
				if(extn==="pdf"){
					renderTemplate("#pdfcontenttmpl", contentViewList, "#contentviewtable");	
				}
				else if(extn==="swf"){
					self.renderSwfContent(contentViewList);
				}
				else{
					self.renderVideoContent(contentViewList);
				}
			},
			renderSwfContent:function(contentList){
				renderTemplate('#swfcontentmpl',contentList,'#contentviewtable');
				$('.maximum').show();
				$('.minimum').hide();
				$('.maxi').click(function(){
					  $('.contentview').css('width','100%');
					  $('.maximum').hide();
					  $('.minimum').show();
					  $('.lecturesupplementary').hide();
					});
				 $('.mini').click(function(){
					  $('.contentview').css('width','70%');
					  $('.maximum').show();
					  $('.minimum').hide();				
					  $('.lecturesupplementary').show();
					});
				
			},
			renderVideoContent:function(contentList){
				renderTemplate('#videocontenttmpl',contentList,'#contentviewtable');
				$('.flowplayer').flowplayer();
				
				$('.maximum').show();
				$('.minimum').hide();
				$('.maxi').click(function(){
					  $('.contentview').css('width','100%');
					  $('.maximum').hide();
					  $('.minimum').show();
					  $('.lecturesupplementary').hide();
					});
				 $('.mini').click(function(){
					  $('.contentview').css('width','70%');
					  $('.maximum').show();
					  $('.minimum').hide();				
					  $('.lecturesupplementary').show();
					});
			},
			decisionfunction:function(){
				var contentset=controller.contentApprovals();
				var contentlength=contentset.length;
				var i=0;  
				var checklength=_.countBy(_.pluck(contentset, 'approvestatus'), function(num) {
				if(num =='A'){
				i=i+1;
				}
				return i;
				});
				var finalcount=i;
				if(contentlength==finalcount){
					$('.decisiondiv').attr('style','display:');
					$('.coursebtn').each(function(){
						if($(this).attr('op')=="Reject"){
						$(this).hide();
						}
						else{
							$(this).show();
						}
					});
					view.contentStatusView();
				}else{
					$('.decisiondiv').attr('style','display');
					$('.coursebtn').each(function(){
						if($(this).attr('op')=="Approve"){
						$(this).hide();
						}
						else{
							$(this).show();
						}
					});
				var contentApproveList= model.contentApprovals();
				}
				view.contentStatusView();
			},
	}; 
	var model = {
			contentApprovals:function(){
				return formater.contentApprovals(controller.contentApprovals());
			},
			contentView:function(courselectureid){
				return controller.contentView(courselectureid);
			},
			approve:function(obj){				
				return controller.contentApprove(obj);				
			},
			approveCourse:function(obj){				
				return controller.courseApprove(obj);				
			},
			contentApproveModel:function(contentid,approvestatus,description,contentapprovalid) {
				var obj={};
				obj.approvestatus = approvestatus;
				obj.description = description;
				obj.contentid = contentid;
				obj.contentapprovalid=contentapprovalid;		
				return obj;
			},
			courseApproveModel:function(coursestatus,courseid) {
				var obj={};
				obj.coursestatus = coursestatus;
				obj.courseid = courseid;
				return obj;
			}
	};

	var formater={
			contentApprovals:function(coursecontentlist){
				sections = [];
				var courseid=coursecontentlist[0].courseid;
				var courseArray =_.where(coursecontentlist,{courseid:courseid});
				sectionids =_.uniq(_.pluck(courseArray,'coursesectionid'));
				_.each(sectionids,function(coursesectionid){
					var sectionarray = _.where(courseArray,{coursesectionid:coursesectionid});
					var section = _.pick(sectionarray[0],'coursesectionid','coursesectiontitle','coursetitle','courseid','authororgpersonid');
					var lectureids=_.uniq(_.pluck(sectionarray,'courselectureid'));
					var lecture = [];
					_.each(lectureids,function(courselectureid){
						var lectureArray = _.where(sectionarray,{courselectureid:courselectureid});
						var lectureOutput=_.pick(lectureArray[0],'coursesectionid','coursesectiontitle','courseenrollmentid','courselectureid','courselecturetitle','compstatus');
						lecture.push(lectureOutput);
					});
					section.lectures=lecture;
					sections.push(section);
				});
				return sections;
			}
	};

	return {
		init: function () {
			initialize();
		}
	}; 
	function initialize(){
		$courseid=$('[name=courseid]');
		$authororgpersonid=$('[name=authororgpersonid]');
		view.contentApprovals();
		view.decisionfunction();
	};	
});