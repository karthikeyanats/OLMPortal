$(document).ready(function(){
	guestPreview().pageevents();
	guestPreview().init();	
});

var guestPreview=(function(){
	var objects={
			category:function(boardid){
				return categoryobj={
						boardid:boardid,
						coursecategorystatus:"A"		
				};
			},
			course:function(categoryid){
				return courseobj={
						coursecategoryid:categoryid,						
				};
			}
	};
	var controller={
			board:function(status){
				return Ajax.get(courseData.getContextPath()+"/rest/browse/",undefined,"json","application/json");				
			},
			category:function(boardid){
				return Ajax.get(courseData.getContextPath()+"/rest/browse/category?boardid="+boardid+"",undefined,"json","application/json");
			},
			course:function(categoryobj){
				return Ajax.post(courseData.getContextPath()+"/rest/boardcategory/courses/",categoryobj,"json","application/json");
			},
	};
	var model={
			board:function(){
				return controller.board("A");				
			},
			category:function(boardcategoryobj){
				return controller.category(boardcategoryobj.boardid);
			},
			course:function(categoryobj){
				return controller.course(categoryobj);
			},
			searchcourse:function(keyword){
				$('[name=keyword]').val(keyword);				
				$('[name=browseguestform]').attr('action','ssearchresult');
				$('[name=browseguestform]').submit();
			}
	};
	var view={
			board:function(){
				var boardslist=model.board();
				renderTemplate("#topcategoriestmpl",boardslist , "#topcategoriestable");
				var boardval=$('[name=boardid]').val();
				if(boardval.length!=0 && boardval!="null"){
					$('#topcategoriestable .leftsidelink[boardid="'+boardval+'"]').addClass('active');
					view.category(boardval);
				}else{
					$('#topcategoriestable .leftsidelink:first').addClass('active');
					view.category($('#topcategoriestable .leftsidelink').attr('boardid'));
				}
				$('#topcategoriestable .leftsidelink').click(function(){
					$('#topcategoriestable .leftsidelink').removeClass('active');
					$(this).addClass('active');
					$('[name=coursecategoryid]').val('');
					view.category($(this).attr('boardid'));
				});
			},
			category:function(boardid){
				var obj=objects.category(boardid);
				var list=model.category(obj);
				renderTemplate("#subcategoriestmpl",list , "#rightcategorytable");
				var catval=$('[name=coursecategoryid]').val();
				if(catval.length!=0 && catval!="null"){
					$('.subcategorylink[coursecategoryid="'+catval+'"]').addClass('active');
					view.courseheader();
				}else{
					$('.subcategorylink:first').addClass('active');
					view.courseheader();	
				}
				$('.subcategorylink').click(function(){
					$('.subcategorylink').removeClass('active');
					$(this).addClass('active');
					view.courseheader();
				});
			},
			courseheader:function(){
				renderTemplate("#coursesheadertmpl",null , "#rightheadertable");
				var obj=objects.course($('.subcategorylink.active').attr('coursecategoryid'));
				var list=model.course(obj);
				var formatedList=helper.courseformater(list);
				view.course(formatedList,"all");
				$('.filterlinks').click(function(){
					var ftype=$(this).attr('a');
					$('.filtertitle').html(($(this).text()));
					view.course(formatedList,ftype);
				});
				$('.learningcoursestab').click(function(){
					$('.learningcoursestab').removeClass('active');
					$(this).addClass('active');
					$('.leftsidelink').removeAttr('activetab');
					$('.leftsidelink.active').attr('activetab',$('.learningcoursestab.active').attr('href'));
				});				
			},
			course:function(formatedList,type){
				var activetab = $('.leftsidelink[activetab]').attr('activetab');
				var list=helper.filterCourses(formatedList,type);
				renderTemplate("#coursestmpl",list , "#righttable");
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
				$('.startlearninggridbtn').click(function(){
					$('[name=courseid]').val($(this).attr('courseid'));
					$("[name=coursesubscribers]").val($(this).attr('coursesubscribers'));
					$('[name=browseguestform]').attr('action','preview');
					$('[name=browseguestform]').submit();
				});
			},

	};
	var helper={
			courseformater:function(list){
				var courses=[];
				var coursevalues=_.uniq(_.pluck(list,'courseid'));
				_.each(coursevalues,function(courseid){
					var course={};
					var courseArray =_.where(list,{courseid:courseid});
					course=_.pick(courseArray[0],'courseid','coursetitle','price','firstName','lastName','courselogo','courserating');
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
			comboSelected:function(selector,selectvalue){
				$(selector+" option").each(function(){
					if($(this).val() == selectvalue){
						$(this).attr("selected","true");
						return false;
					}
				});
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