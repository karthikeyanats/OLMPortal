$(document).ready(function(){
	var courseid=$('[name=courseid]').val();
	manageOrgCourse.loadPageInfo(courseid);
});

manageOrgCourse={
		loadPageInfo:function(courseid){
			var self=this;
			self.loadcontent(courseid);
			self.renderContentImages();		
		},
		renderContentImages:function(){
			$('.contenttypeclass').each(function(){
				var conttype=$(this).attr('contenttype');
				if(conttype=="video"){
					$(this).css('background-color','#1abc9c');
					$(this).html("<img src='../assets/app/images/contenttype/video.png'></img>");
				}
				else if(conttype=="pdf"){
					$(this).css('background-color','red');
					$(this).html("<img src='../assets/app/images/contenttype/pdf.png'></img>");
				}
				else if(conttype=="swf"){
					$(this).css('background-color','orange');
					$(this).html("<img src='../assets/app/images/contenttype/swf.png'></img>");
				}
				else{
					$(this).css('background-color','red').attr('disabled','disabled');
					$(this).html("<img src='../assets/app/images/contenttype/nocontent.png'></img>");
				}
			});
		},
		loadcontent:function(courseid){
			var self=this;
			var courseidobj={};
			courseidobj.courseid=courseid;
			var tobeformatedList=courseData.getOrgCourseContent(courseidobj);
			self.formatList(tobeformatedList);
			self.loadCourseLogoPart(courseid);
		},
		formatList:function(formatList){
			var self=this;
			self.loadbasicinfo(formater.formatCourseInfoContent(formatList));
			self.loadcourseheader(formater.formatCourseHeaderContent(formatList));
			self.loadinitialinfo(formater.formatbasicInfo(formatList));
			manageContentInfo.loadcontentinfo(formater.formatCourseMainContent(formatList));
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
				if(price==="Free"){
					$(this).text("Price : Free");
				}
				else{
					$(this).text("Price : $"+price);	
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
			renderTemplate("#courselogotmpl", courseLogoList, "#courselogotable");
			self.changecourselogo();
		},
		loadcourseheader:function(courseHeader){
			var self=this;
			renderTemplate("#courseheadertmpl",courseHeader,"#courseheadertable");
			$('.publishcoursebtn').click(function(e){
				var publishstatus=self.returnpublishstatus($(this).attr('sectioncount'),$(this).attr('lecturecount'),$(this).attr('contentcount'));
				if(publishstatus=="ok"){
					$('#messagemodal').modal('show');
					$('.description').text("Course Published");		
					var publishcourse={};
					publishcourse.courseid=$(this).attr('courseid');
					var publishedStatus=ajaxhelperwithdata("publishCourse", publishcourse);
					if(publishedStatus=="1"){
						$(this).text('published');	
						$(this).attr('disabled','disabled');
						e.preventDefault();
					}
				}
				else if(publishstatus=="sectionless"){
					$('#messagemodal').modal('show');
					$('.mesdesc').text("you need to create atleast three sections");
					setTimeout(function(){
						$('#messagemodal').modal('hide');										
					},1000);					
				}
				else if(publishstatus=="lectureless"){
					$('#messagemodal').modal('show');
					$('.mesdesc').text("you need to create atleast three lectures");
					setTimeout(function(){
						$('#messagemodal').modal('hide');										
					},1000);					
				}
				else if(publishstatus=="contentless"){
					$('#messagemodal').modal('show');
					$('.mesdesc').text("you need to add atleast three contents");
					setTimeout(function(){
						$('#messagemodal').modal('hide');										
					},1000);
				}
			});
		},
		loadinitialinfo:function(coursebasicinfo){
			var self=this;
			renderTemplate("#coursebasicinfotmpl",coursebasicinfo,"#coursebasicinfotable");
			selectvalue=$('[name=coursecategoryid]').val();
			var courseCategories=self.loadcourseCategories();
			renderTemplate("#categorytmpl", courseCategories, "#categorytable");
			self.comboSelected('#categorytable',selectvalue);
			$('.coursecategoryaddbtn').click(function(){
				self.newCategorymodal();
			});
			var priceval=$('[name=price]').val();
			if(priceval!='' && priceval!=="0" && priceval!=="Free"){
				$('.pricestatusbtn').removeClass('btn-blue');
				$('.priceactivebtn').addClass('btn-blue');
				$('.priceshowbtn').show();
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
				$('[name=price]').val($(this).attr('price'));
				$('.pricestatusbtn').removeClass('btn-blue');
				$(this).addClass('btn-blue');
				$('.priceshowbtn').show();
			});
			var levelval=$('[name=instructoinallevel]').val();
			var selector=$(".levelbtn[level~='" + levelval + "']");
			selector.addClass('btn-blue');
			$('.levelbtn').click(function(){
				$('.levelbtn').removeClass('btn-blue');
				$(this).addClass('btn-blue');
				$('[name=instructoinallevel]').val($(this).attr('level'));
			});
			$('.coursebasicinfosavebtn').click(function(){
				var basicinfo={};
				var courseid=$(this).attr('courseid');
				basicinfo.courseid=courseid;
				basicinfo.coursetitle=$('[name=coursetitle]').val();
				basicinfo.coursesubtitle=$('[name=coursesubtitle]').val();
				basicinfo.coursedescription=$('[name=coursedescription]').val();
				basicinfo.coursegoal=$('[name=coursegoal]').val();
				basicinfo.courselogo=$('[name=courselogo]').val();
				basicinfo.coursepromevideopath=$('[name=coursepromevideopath]').val();
				basicinfo.priceid=$('[name=price]').attr('priceid');
				basicinfo.price=$('[name=price]').val();
				basicinfo.coursecategoryid=$('#categorytable').val();
				basicinfo.coursecategoryname=$('[name=coursecategoryname]').val();
				basicinfo.instructoinallevel=$('[name=instructoinallevel]').val();
				basicinfo.intendedaudience=$('[name=intendedaudience]').val();
				basicinfo.coursecreateddate=$('[name=coursecreateddate]').val();
				basicinfo.coursestatus=$('[name=coursestatus]').val();
				basicinfosave=ajaxhelperwithdata("managecourseinfo", basicinfo);
				if(basicinfosave=="1")	{
					$('#messagemodal').modal('show');
					$('.mesdesc').text("Information Saved Successfully");
					setTimeout(function(){
						$('#messagemodal').modal('hide');										
					},1000);
					
					self.loadPageInfo(courseid);
				}
				else{
					$('#messagemodal').modal('show');
					$('.mesdesc').text("Error in Saving the Information");
					setTimeout(function(){
						$('#messagemodal').modal('hide');										
					},1000);					
				}
			});
		},
		returnpublishstatus:function(sections,lectures,contents){
			if(sections<3){
				return "sectionless";
			}
			else{
				if(lectures<3){return "lectureless";}
				else{
					if(contents<3){return "contentless";}
					else{return "ok";}
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
		newCategorymodal:function(){
			var self=this;
			$("#coursecategorymodal").modal("show");
			var courseCategories=self.loadcourseCategories();
			renderTemplate("#editCategorytmpl", courseCategories, "#existingcategoriestable");
			$('.newcategoryaddbtn').click(function(){
				self.checknullcategory($('.newcategorytitle').val(),$('.newcategoryaddbtn').text());
 			});
			
			$('.editcategorybtn').click(function(){
 				$('.newcategorytitle').val($(this).attr('coursecategoryname'));  
				$('.newcategoryaddbtn').text("Update"); 
				$('.newcategoryaddbtn').attr("coursecategoryid",$(this).attr('coursecategoryid')); 
 			});
			$('.deletecategorybtn').click(function(){
 				$('.newcategoryaddbtn').text("Delete"); 
				$('.newcategoryaddbtn').attr("coursecategoryid",$(this).attr('coursecategoryid')); 
				self.deleteCategory($(this).attr('coursecategoryid'));
 			});
		}, 
		loadcourseCategories:function(){
			var categorystatusobj={};
			categorystatusobj.coursecategorystatus="A";
			coursecategorylist=courseData.getGuestCategories(categorystatusobj);
			return coursecategorylist;
		},
		addNewCategoryData:function(coursecategoryname){
			var self=this;
			var coursecategoryobj={};
			coursecategoryobj.coursecategoryname=coursecategoryname;
			self.addNewCategory(coursecategoryobj);
		},
		addNewCategory:function(coursecategoryobj){
			var self=this;
			var categoryinserted=courseData.SaveNewCategory(coursecategoryobj);
			if(categoryinserted!=null){
				$('.newcategorytitle').val("");
				var selectvalue=$('[name=coursecategoryid]').val();
				self.loadCat();
				self.comboSelected('#categorytable',selectvalue);
			}
		}, 
 		editCategory:function(coursecategoryid,coursecategoryname){
			var self=this;
			var coursecategoryobj={};
			coursecategoryobj.coursecategoryname=coursecategoryname;
			coursecategoryobj.coursecategoryid=coursecategoryid;
			var categoryinserted=courseData.updateCategory(coursecategoryobj);
 			if(categoryinserted=="updated"){ 
				$('.newcategorytitle').val(""); 
 				var selectvalue=$('[name=coursecategoryid]').val();
 				self.loadCat();
 				self.comboSelected('#categorytable',selectvalue);
				$('.newcategoryaddbtn').text("Save");   
 			}
 		},
 		deleteCategory:function(coursecategoryid){
 			var categoruobj={};
 			categoruobj.coursecategoryid=coursecategoryid;
 			var categoryinserted=courseData.coursecategoryexists(categoruobj); 
			if(categoryinserted=="exists"){
				$('#messagemodal').modal('show');
				$('.mesdesc').text("This category cant be deleted since published courses will be affected");
				setTimeout(function(){
					$('#messagemodal').modal('hide');										
				},1000);				
			} 
			else  {
				if(confirm('Are you sure you want to delete this Category?')) { 
 					var self=this;
					var coursecategoryobj={};
		 			coursecategoryobj.coursecategoryid=coursecategoryid;
					var categoryinserted=courseData.trashCategory(coursecategoryobj);
		 			if(categoryinserted=="updated"){ 
						$('.newcategorytitle').val(""); 
		 				var selectvalue=$('[name=coursecategoryid]').val();
		 				self.loadCat();
						self.comboSelected('#categorytable',selectvalue);
						$('.newcategoryaddbtn').text("Save");   
		 			}					
				}
			} 			
 		},
 		loadCat:function(){
 			var self=this;
 			var courseCategories=self.loadcourseCategories();
			renderTemplate("#editCategorytmpl", courseCategories, "#existingcategoriestable");
			renderTemplate("#categorytmpl",courseCategories,"#categorytable");
				$('.editcategorybtn').click(function(){
	 				$('.newcategorytitle').val($(this).attr('coursecategoryname'));  
					$('.newcategoryaddbtn').text("Update"); 
					$('.newcategoryaddbtn').attr("coursecategoryid",$(this).attr('coursecategoryid')); 
	 			});
				$('.deletecategorybtn').click(function(){
	 				$('.newcategoryaddbtn').text("Delete"); 
					$('.newcategoryaddbtn').attr("coursecategoryid",$(this).attr('coursecategoryid')); 
					self.deleteCategory($(this).attr('coursecategoryid'));
	 			});
 		},
 		checknullcategory:function(coursecategoryname,submitType){
			var self=this;
			if(coursecategoryname.trim().length!==0){
				if(submitType=="Save") {
					self.addNewCategoryData(coursecategoryname);
				} else if(submitType=="Update"){ 
 					self.editCategory($('.newcategoryaddbtn').attr('coursecategoryid'),coursecategoryname);
				}
 			}
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
		}
};