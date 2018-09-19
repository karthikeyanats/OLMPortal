$(document).ready(function(){
	$("#adminbrowsecourse").addClass('hover').addClass('active');
	browse().init();
	browse().pageevents();
});	

var browse = (function () {
	var objects={
			category:function(categoryid){
				return categoryobj={
						coursecategoryid:categoryid
				};
			},
			startlearning:function(ceid,cestatus,cid){
				return learnobj={
						courseenrollmentid:ceid,
						courseenrollmentstatus:cestatus,
						courseid:cid
				};
			}
	};
	var controller = {	
			board:function(){
				return Ajax.get(courseData.getContextPath()+"/rest/browseA/",undefined,"json","application/json");				
			},
			category:function(boardid){
				return Ajax.get(courseData.getContextPath()+"/rest/browseA/category?boardid="+boardid+"",undefined,"json","application/json");
			},
			course:function(categoryobj){
				return Ajax.post(courseData.getContextPath()+"/rest/browseA/courses/",categoryobj,"json","application/json");
			}
	}; 
	var model = {
			board:function(){
				return controller.board();				
			},
			category:function(boardid){
				return controller.category(boardid);
			},
			course:function(categoryid){
				return controller.course(categoryid);
			},
			searchcourse:function(keyword){
				$('[name=keyword]').val(keyword);				
				$('[name=browseuserform]').attr('action','searchresult');
				$('[name=browseuserform]').submit();
			}
	};
	var view = {	
			board:function(){
				var list=model.board();
				if(list!="NO_CONTENT"){
					renderTemplate("#topcategoriestmpl",list , "#topcategoriestable");
					$('#topcategoriestable .leftsidelink:first').addClass('active');
					view.category();
					$('#topcategoriestable .leftsidelink').click(function(){
						$('#topcategoriestable .leftsidelink').removeClass('active');
						$(this).addClass('active');
						view.category();
					});	
				}else{
					renderTemplate("#nocoursestmpl",null,"#topcontainer");
					$('.searchbtn').hide();
				}
			},
			category:function(){
				var boardid=$('#topcategoriestable .leftsidelink.active').attr('boardid');
				var list=model.category(boardid);
				renderTemplate("#subcategoriestmpl",list , "#rightcategorytable");
				$("#rightcategorytable .subcategorylink:first").addClass('active');
				view.courseHeader();
				$("#rightcategorytable .subcategorylink").click(function(){
					$("#rightcategorytable .subcategorylink").removeClass('active');
					$(this).addClass('active');
					view.courseHeader();
				});
			},
			courseHeader:function(){
				renderTemplate("#coursesheadertmpl",null , "#rightheadertable");
				var categoryid=$('.subcategorylink.active').attr('coursecategoryid');
				var formatedList=formater.course(model.course(objects.category(categoryid)));
				var list=formater.filterCourses(formatedList,"all");
				view.course(list);				
				$('.filterlinks').click(function(){
					$('.filtertitle').text($(this).text());
					var list=formater.filterCourses(formatedList,$(this).attr('a'));
					view.course(list);
				});
				$('.learningcoursestab').click(function(){
					$('.learningcoursestab').removeClass('active');
					$(this).addClass('active');
					$('.leftsidelink').removeAttr('activetab');
					$('.leftsidelink.active').attr('activetab',$('.learningcoursestab.active').attr('href'));
				});
			},
			course:function(list){	
				var activetab = $('.leftsidelink[activetab]').attr('activetab');
				renderTemplate("#coursestmpl",list , "#righttable");
				if(activetab!=undefined){
					$('.learningcoursestab').removeClass('active');
					$('a[href="'+activetab+'"]').addClass('active');
					$('a[href="'+activetab+'"]').tab('show');
				}			
				else{
					$('.learningcoursestab').removeClass('active');
					$('.learningcoursestab:first').addClass('active');
					$('.learningcoursestab:first').tab('show');				
				}
				var courseid = _.pluck(list,'courseid');
				_.each(courseid,function(courseids){
					var date = new Date();
					var dat = date.toDateString();
					var datesplit = dat.split(" ");
					var datsplit = datesplit[1]+"-"+datesplit[2]+"-"+datesplit[3];
					var courseidlist =courseData.getTimecourseDetails(courseids,datsplit);
					if(courseidlist.length>0){
						var time =_.pluck(courseidlist,'starttime');
						var endtime =_.pluck(courseidlist,'endtime');
						var isTrue="";
						var isTrues="";

						var dates = new Date();
						var datetime = dates.toLocaleTimeString();
						var times =datetime.split(" ");
						var timesp =times[0].split(":");
						var timesplit = timesp[0]+":"+timesp[1];
						var timessval = timesplit+" "+times[1];

						var timess ="";
						var a=timessval.split(' ');
						var b=a[0].split(':');
						if(a[1]=="PM"){
							if(b[0]=="12"){
								timess=b[0]+":"+b[1]+" "+a[1];
							}
							else{
								timess=parseInt(b[0])+12+":"+b[1]+" "+a[1];
							}
						}
						else if(a[1]=="AM"){
							if(b[0]=="12"){
								timess="00:"+b[1]+" "+a[1];	
							}
							else if(parseInt(b[0])<10){
								timess="0"+b[0]+":"+b[1]+" "+a[1];
							}
							else{
								timess=b[0]+":"+b[1]+" "+a[1];
							}
						}
						else{
							timess=b[0]+":"+b[1]+" "+a[1];
						}

						_.each(time,function(timec){
							var starttimedata="";
							var a=timec.split(' ');
							var b=a[0].split(':');
							if(a[1]=="PM"){
								if(b[0]=="12"){
									starttimedata=b[0]+":"+b[1]+" "+a[1];
								}
								else{
									starttimedata=parseInt(b[0])+12+":"+b[1]+" "+a[1];
								}
							}
							else if(a[1]=="AM"){
								if( b[0]=="12"){
									starttimedata="00:"+b[1]+" "+a[1];
								}
								else if(parseInt(b[0])<10){
									starttimedata="0"+b[0]+":"+b[1]+" "+a[1];
								}
								else{
									starttimedata=b[0]+":"+b[1]+" "+a[1];
								}
							}
							if(timess>=starttimedata){
								isTrue="true";
							}
						});

						_.each(endtime,function(endtimec){
							var endtimedata="";
							var a=endtimec.split(' ');
							var b=a[0].split(':');
							if(a[1]=="PM"){
								if(b[0]=="12"){
									endtimedata=b[0]+":"+b[1]+" "+a[1];
								}
								else{
									endtimedata=parseInt(b[0])+12+":"+b[1]+" "+a[1];
								}

							}
							else if(a[1]=="AM"){
								if(b[0]=="12"){
									endtimedata="00:"+b[1]+" "+a[1];
								}
								else if(parseInt(b[0])<10){
									endtimedata="0"+b[0]+":"+b[1]+" "+a[1];
								}
								else{
									endtimedata=b[0]+":"+b[1]+" "+a[1];
								}

							}
							if(endtimedata>=timess){
								isTrues="true";
							}
							else{
								isTrues="false";
							}
						});
						if(isTrue=="true" && isTrues=="true"){
							livebtn(courseids);
						}
					}
				});

				function livebtn(courseid){
					$('.livebtn').each(function(){
						if($(this).attr('courseid')==courseid){
							$(this).show();
							//$(this).text('LiveSession');
						}
					});
				}
				$('.icon_price').each(function(){
					var price=$(this).attr('price');
					var priceicon = $(this).attr('priceicon');
					if(price=="Free"){
						$(this).html("Free");
					}else if(price==""){
						$(this).html("");
					}else{
						$(this).html("<i class='fa "+priceicon+"'></i>"+price);
					}
				});
				$('.ratingcourse').each(function(){
					var rating=$(this).attr('rating');
					$(this).raty({ readOnly: true,score: rating, 
						starHalf      : courseData.getContextPath()+"/"+'assets/app/images/star/star-half.png',
						starOff       : courseData.getContextPath()+"/"+'assets/app/images/star/star-off.png', 
						starOn        : courseData.getContextPath()+"/"+'assets/app/images/star/star-on.png', 
					});
				});
				$('.startlearninggridbtn').each(function(){
					var status=$(this).attr('courseenrollmentstatus');
					switch(status){
					case "W":
						$(this).html("<i class='fa fa-heart'></i>&nbsp;Wishlisted");
						break;
					case "A":
						$(this).html("<i class='fa fa-play-circle'></i>&nbsp;Learning");
						break;
					case "C":
						$(this).html("<i class='fa fa-play-circle'></i>&nbsp;Completed");
						break;
					case "B":
						$(this).html("<i class='fa fa-exclamation-triangle'></i>&nbsp;Blocked");
						break;
					case "P":
						$(this).html("<i class='fa fa-clock-o'></i></i>&nbsp;Waiting for approval");
						break;
					case "N":
						$(this).html("<i class='fa fa-play-circle'></i>&nbsp;Start Course");
						break;
					}
				});
				$('.usercount').click(function(){
					$("#courseUserModel").attr('courseid',$(this).attr('courseid'));
					$('.cusertitle').text($(this).attr('coursetitle'));
					courseusers().init();
				});
				$('.managecourse').click(function()	{
					var courseid=$(this).attr('courseid');	
					$('[name=courseid]').val(courseid);
					$('[name=coursemanage]').attr('method','post');
					$('[name=coursemanage]').attr('action','managecourse');
					$('[name=coursemanage]').submit();
				});
				$('.startlearninggridbtn').click(function(){
					var obj=objects.startlearning($(this).attr('courseenrollmentid'),$(this).attr('courseenrollmentstatus'), $(this).attr('courseid'));
					helper.startlearning(obj);
				});
			}
	}; 

	var formater={
			course:function(list,userlist){
				var clist=formater.courseformater(list);
				_.each(clist,function(obj){
					var enrollstatusobj=_.where(userlist,{courseid:obj.courseid});
					if(enrollstatusobj.length!=0){
						obj.courseenrollmentid=enrollstatusobj[0].courseenrollmentid;
						obj.courseenrollmentstatus=enrollstatusobj[0].courseenrollmentstatus;
					}else{
						obj.courseenrollmentstatus="N";
					}
				});
				return clist;
			},
			courseformater:function(list){
				var courses=[];
				var coursevalues=_.uniq(_.pluck(list,'courseid'));
				_.each(coursevalues,function(courseid){
					var course={};
					var courseArray =_.where(list,{courseid:courseid});
					course=_.pick(courseArray[0],'courseid','coursetitle','price','firstName','lastName','coursedescription','courselogo','courserating',
							'mediumid','mediumname','standardlevelid','standardlevelname');
					course.coursesubscribers=_.compact(_.uniq(_.pluck(courseArray,'courseenrollmentid'))).length;
					var courseratingids=(_.compact(_.pluck(courseArray,'courserating')));
					var courseratingsum = _.reduce(courseratingids, function(memo, num){return memo + parseInt(num);}, 0);
					var ratingaverage=courseratingsum/(courseratingids.length);
					if(_.isNaN(ratingaverage)){
						course.rating=0;
					}
					else{
						course.rating=ratingaverage;	
					}
					courses.push(course);
				});
				courses = _.sortBy(courses, function(obj){ return obj.coursetitle;});
				return courses;
			},
			filterCourses:function(categoryCourses,ftype){
				var courses=[];
				items_to_add = [];
				_.each(categoryCourses, function(item){
					if(item.price != 'Free'){
						items_to_add.push(item);
					}
				});
				switch(ftype){
				case "p":
					courses=_.sortBy(categoryCourses, function(obj){ return obj.rating *-1;});
					break;
				case "h":
					a=_.sortBy(items_to_add, function(obj){ return parseInt(obj.price )* -1;});
					b=_.where(categoryCourses,{price:"Free"});				
					courses=_.union(a,b);
					break;
				case "l":
					a=_(items_to_add).sortBy(function(obj){return parseInt(obj.price);}); 
					b=_.where(categoryCourses,{price:"Free"});
					courses=_.union(b,a);
					break;
				case "e":
					courses=_.sortBy(categoryCourses, function(obj){ return obj.coursesubscribers *-1;});
					break;
				case "all":
					courses=categoryCourses;
					break;
				}			
				return courses;
			}
	};
	var helper={
			startlearning:function(courseidobj){
				switch(courseidobj.courseenrollmentstatus){
				case "W":
					$('[name=courseid]').val(courseidobj.courseid);
					$('[name=courseenrollmentid]').val(courseidobj.courseenrollmentid);
					$('[name=wishlisted]').val("wishListed");
					$('[name=browseuserform]').attr('action','previewuser');
					$('[name=browseuserform]').submit();
					break;
				case "A":
					$('[name=courseid]').val(courseidobj.courseid);
					$('[name=courseenrollmentid]').val(courseidobj.courseenrollmentid);
					$('[name=browseuserform]').attr('action','previewcourse');
					$('[name=browseuserform]').submit();
					break;
				case "C":
					$('[name=courseid]').val(courseidobj.courseid);
					$('[name=courseenrollmentid]').val(courseidobj.courseenrollmentid);
					$('[name=browseuserform]').attr('action','previewcourse');
					$('[name=browseuserform]').submit();
					break;
				case "B":
					$('#subscribedmodal').modal('show');
					$('.enrolstatus').text("You have been blocked for this course. Please contact administrator");
					break;
				case "P":
					$('#subscribedmodal').modal('show');
					$('.enrolstatus').text("Your subsription for the course is wating for approval from the author. Please wait for a while.");
					break;
				case "N":
					$('[name=courseid]').val(courseidobj.courseid);
					$("[name='browseuserform']").attr('action','previewuser');
					$("[name='browseuserform']").submit();
					break;
				}
			},
	};
	return {
		init: function () {
			initialize();
		},
		pageevents:function(){
			eventsfn();
		}
	}; 
	function initialize(){
		view.board();
	};	
	function eventsfn(){
		$('.searchbtn').click(function(e)  {
			$('.dropbox').slideDown(400);
			$('.searchtextbox').val('');
		});
		$('.closebtn').click(function(){
			$('.dropbox').slideUp(400);
		});
		$('.searchkwrdbtn').click(function(e){
			var searchkeyword=$('.searchtextbox').val().trim();
			if(searchkeyword!=undefined && searchkeyword.length!=0){
				model.searchcourse(searchkeyword);		
			}else{
				e.preventDefault();
			}
		});
		$('.searchtextbox').keypress(function(event){
			var keycode = (event.keyCode ? event.keyCode : event.which);
			if(keycode == '13'){
				var searchkeyword=$('.searchtextbox').val().trim();
				if(searchkeyword!=undefined && searchkeyword.length!=0){
					model.searchcourse(searchkeyword);
				}else{
					event.preventDefault();
				}
			}
		});
	}
});
  