$(document).ready(function(){
	
	$('.leftsideli').click(function (e) {
	    //save the latest tab; use cookies if you like 'em better:
	      sessionStorage.setItem('lastTab', $(e.target).attr('class'));
	  });
	
	download.initialView();	
	var socialmsg=$('[name=socialmsg]').val();
	if(socialmsg=="sharesync"){
		view.facebookshare();
	}

	if(sessionStorage.getItem('lastTab')){
		  var lastTab = sessionStorage.getItem('lastTab');
		  if(lastTab.search("downloadhistorylink")>=0){
			  switchtab(1);
		  }
		  else if(lastTab.search("downloadviewlink")>=0){
			  switchtab(2);
		  }
	     else if(lastTab.search("downloadlinks")>=0){
	    	 switchtab(3);
		  }
	  }
	function switchtab(e){
	 switch(e){
		  case 1:{
			  $('.downloadhistorylink').trigger('click');
			  break;
		  }
		  case 2:{
			  $('.downloadviewlink').trigger('click');
			  break;
		  }
		  case 3:{
			  $('.downloadlinks').trigger('click');
			  break;
		  }
		  }
	}
	
});
function alertCloseEvent(){
	$('.alertclose').click(function(){
		$(".contentbackgound").css('margin-top','20px');		
	});
}
var download={
		role:$('[name=roleval]').val(),
		orgstatus:$('[name=orgstatusval]').val(),
		initialView:function(){
			if(this.orgstatus=="M" && this.role=="admin"){
				$('.downloadhistorylink').trigger('click');
			}
			else if(this.orgstatus=="A" && this.role=="admin"){
				$('.downloadlinks').trigger('click');
			}
			else if(this.role=="user"){
				$('.downloadlinks').trigger('click');			
			}
			$('.downloadhistorylink').click(function(){
				$('.leftsideli').removeClass('active');
				$(this).addClass('active');
				view.downloadHistories();
				view.pagination();
			});
			$('.downloadviewlink').click(function(){
				$('.leftsideli').removeClass('active');
				$(this).addClass('active');
				view.presenterManage();		
			});
			$('.downloadlinks').click(function(){
				$('.leftsideli').removeClass('active');
				$(this).addClass('active');
				view.presenterLists();		
			});
		}
};

var view={
		downloadHistories:function(){
			renderTemplate("#presenterdownloaddetailstmpl", controller.downloadHistories(), "#downloaddetails");
		},		
		newPresenter:function(){
			var self=this;
			self.presentertypeData();
			dhelper.numvalidate();
			dhelper.numberplusandminus();
			$('.newcheckstatus').click(function(){
				var chstatus=$(this).attr('checkstatus');				
				if(chstatus=="0"){
					$(this).attr('checkstatus','1');
				}
				else{
					$(this).attr('checkstatus','0');
				}				
			});
			$('.newpresentersavebtn').click(function(e){
				$('#newhelpurl,#newcategoryerror,#newappname,#newpath').hide();	
				var v={};
				v["prescategory"]=$('.newcheckstatus:checked').length;
				v["applicationname"]=$('.newappname').val().trim().length;
				v["price"]=$('#price').val();
				v["path"]=$('.newpath').val().trim().length;
				var pathsplittedarray=($('.newpath').val()).split("/");
				v["pathvalidate"]=(_.last(pathsplittedarray)).indexOf(' ') >= 0;
				v["helpurl"]=$('.newhelpurl').val().trim().length;
				v["al"]=$('#newappname');
				v["pl"]=$('#newpath');
				v["hl"]=$('#newhelpurl');
				v["cl"]=$('#newcategoryerror');				
				v["type"]="new";				
				data.presenterValidate(v,e);				
			});
			$('#presentersave').click(function(){
				$('#alertBoxsuccess').hide();
				$('#alertBox').hide();
				var presentertype=$('.presenterdata').val().trim();
				if(presentertype.length!=0){
					self.duplicatePresentertype();
					self.presenterValidationsuccess("Successfuly Saved");
					setTimeout(function(){
						$('#alertBoxsuccess').hide();
							$('#alertBoxs').hide();	
						},1500);				}
				else{
					
					$('#alertBoxs').show();
					self.presenterValidation("can't be empty");
					setTimeout(function(){
						$('#alertBoxs').hide();					
					},1500);
				}											
			});		
			$('.presenterlist').click(function(e){
				 var status='A';
				 data.presenterapptypeView(status);	
				 $('#alertBoxs').attr('hidden',true);
				});
			$('.trashpresenterlist').click(function(e){
				 var status='T';
				 data.presenterapptypeView(status);	
				 self.savePresentertype();
				 $('#alertBoxs').attr('hidden',true);				 
			});
			$('.presenterdata').keypress(function(){
				$('#alertBoxs').attr('hidden',true);
						
			});						
		},
		presenterManage:function(){
			var self=this;
			renderTemplate("#presenterdetailstmpl",controller.presenterManage(),"#downloaddetails");
			$('.editbtn').each(function(){
				var stat=$(this).attr('presenterstatus');
				if(stat!=="A"){
					$(this).removeClass('editbtn');
					$(this).attr('disabled','disabled');
					self.savePresentertype();
				}
			});
			$('.editbtn').click(function(){
				self.presenterEdit($(this).attr('presenterid'));				
			});			
			$('.downloadlink').click(function(){
				self.newPresenter();
			});						
		},
		presenterValidation:function(message){
			var obj={};
			obj.message=message;
			$('#alertBoxs').show();
			renderTemplate("#errormessagetmpl",obj, "#userNamemsg");	
			$('#alertBoxs').attr('hidden',false);
			setTimeout(function(){
				$('#alertBoxs').hide();					
			},1500);
		},
		presenterValidationsuccess:function(message){
			var obj={};
			obj.message=message;
			renderTemplate("#successmessagetmpl",obj, "#userNamemsgsuccess");	
			$('#alertBoxsuccess').attr('hidden',false);
			setTimeout(function(){
				$('#alertBoxs').hide();					
			},1500);
		},
		presentertypeList:function(presentertype){	
			var self=this;
			if(presentertype=="NO_CONTENT"){
			   renderTemplate("#editPresentertmpl",undefined, "#existingtypetable");
			}else{
			   renderTemplate("#editPresentertmpl",presentertype, "#existingtypetable");
			   self.presentertypeData();
			}
			$('.editpresentertype').click(function(){
				var id=$(this).attr('id');				
				self.updatePresentertype(id);
				$('#alertBoxs').attr('hidden',true);
			
								
			});	
			$('.deletepresentertype').click(function(){
				$('#alertBoxs').attr('hidden',true);
				if(confirm("Do you want to Trash?")){
					var id=$(this).attr('id');
					var typename=$(this).attr('typename');
					var presenterstatus='T';
					data.presenterapptypeStatus(id,presenterstatus,typename);
					}
			});
		},
		updatePresentertype:function(id){
			$('#presentersave').removeClass("presentersave");
			$('#presentersave').addClass('presenterupdate');
			$('#presentersave').html("Update");			
			data.presenterapptypeEdit(id);
					},
		savePresentertype:function(){
			 $('#presentersave').removeClass("presenterupdate");
			 $('#presentersave').addClass('presentersave');
			 $('#presentersave').html("Save");
			 $('.presenterdata').val('');
		},
		presentertypeTrash:function(presentertype){			
			var self=this;
			if(presentertype=="NO_CONTENT"){
			   renderTemplate("#trashPresentertmpl",undefined, "#existingtypetable");
		     }else{
		       renderTemplate("#trashPresentertmpl",presentertype, "#existingtypetable");	
		       self.presentertypeData();
		    } 
			$('.restorepresentertype').click(function(){
			  $('#alertBoxs').attr('hidden',true);
			  if(confirm("Do you want to Restore?")){
				var id=$(this).attr('id');
				var presenterstatus='A';	
				var typename=$(this).attr('typename');
				data.presenterapptypeStatus(id,presenterstatus,typename);	
				self.presenterValidationsuccess("Successfuly restored");
				$('#alertBoxsuccess').show();
				setTimeout(function(){
					$('#alertBoxsuccess').hide();					
				},1500);
			  }
			});		
			$('.deletetrashtype').click(function(){
				var id=$(this).attr('id');
				if(confirm("Do you want to permanently delete "+$(this).attr('typename')+" from trash list?")){
				var status=controller.deletetrashtypelist(id);
				if(status=="ACCEPTED"){
					$('.trashpresenterlist').trigger('click');
					self.presenterValidationsuccess("Successfuly deleted permanently");
					$('#alertBoxsuccess').show();
					setTimeout(function(){
						$('#alertBoxsuccess').hide();					
					},1500);
				}
				}
			});
		},
		presenterEdit:function(presenterid){
			renderTemplate("#editpresenterapptmpl",controller.presenterEdit(presenterid), "#downloaddetails");
			dhelper.numvalidate();
			dhelper.numberplusandminus();
			$('.checkstatus').each(function(){
				if($(this).attr('checkststus')=="1"){
					$(this).attr('checked','checked');
				}
			});
			$('.checkstatus').click(function(){
				var chstatus=$(this).attr('checkststus');
				if(chstatus=="0"){
					$(this).attr('checkststus','1');
				}
				else{
					$(this).attr('checkststus','0');
				}				
			});
			$('.updatepresenterbtn').click(function(e){
				$('#edithelpurl,#editcategoryerror,#editappname,#editpath').hide();				
				var v={};
				v["prescategory"]=$('.checkstatus:checked').length;
				v["applicationname"]=$('.appname').val().trim().length;
				v["path"]=$('.path').val().trim().length;
				var pathsplittedarray=($('.path').val()).split("/");
				v["pathvalidate"]=(_.last(pathsplittedarray)).indexOf(' ') >= 0;
				v["helpurl"]=$('.helpurl').val().trim().length;
				v["al"]=$('#editappname');
				v["pl"]=$('#editpath');
				v["hl"]=$('#edithelpurl');
				v["cl"]=$('#editcategoryerror');
				v["type"]="edit";
				data.presenterValidate(v,e);
			});
		},
		presenterList:function(){
			
			var presenterlist = controller.presenterList();
			renderTemplate("#remedipresenterdownloadtmpl",controller.presenterList(), "#downloaddetails");
			var presenterlistdata =_.findWhere(presenterlist,{typename: "Free"});
			var presenteriddata	=null;
			if(presenterlistdata!=undefined){
				presenteriddata	=	presenterlistdata.presenterappdetailsid;
			}
			$("#status").click(function(){
				$(this).attr("class", "checked");
			});
			$(".presenterdownload").each(function(){
				if($(this).attr('presenterappdetailsid')==presenteriddata){
				$(this).show();
				$(".presenterdownloadfreelink").attr('presenterappdetailsid',$(this).attr('presenterappdetailsid'));
				}
				else{
					$(this).hide();
					$(this).addClass('licensepresenter');
				}
				});
			$(".presenterdownload").click(function(){
				if($("#status").is('.checked')){
					if($('.free').is(".active")){
					if(confirm("Do you want to download?")){
						var contextpath	=	$(".contextpath").val();
						var appath= $(".presenterapp").attr('apppath');
						$(".presenterapp").attr("href", ""+contextpath+"/OpenFile?r1=serverpath&r2="+appath+"&r3=download");
						var presenterappdetailsid=$(this).attr('presenterappdetailsid');
						var presenterappdetail={};
						presenterappdetail.presenterAppdetailsId=presenterappdetailsid;
						var presenterappdetails = courseData.getPresenterDownloadDetails(presenterappdetail);
					if(confirm("Would you like to share download information in facebook?")){
						view.facebookshare();
				     }
				   }
				  }
					else if($('.license').is(".active")){
						if(confirm("Do you want to download?")){
							$("#presenterappdetailsid").val(presenterlicensetypename);
							$("#presenterprice").val(presenterprice);
							$("#presenterappdownload").attr('action','licensepresenter');
							$("#presenterappdownload").submit();
						}
					}
					else{
						alert("Please select Free or License");
					}
				}
				else{
					alert("Please select the terms of services");
				}
			});
			$(".presenterdownloadfreelink").click(function(){
				$('.license').hide();
				$('.free').show();
				$('.free').addClass('active');
				$('.license').removeClass('active');
			});
			var presentertypename	=	$(".licensepresenter").attr('typename');
			var presenterlicensetypename	= null;	
			var presenterprice	=	null;	
			$(".licensepresenter").each(function(){
				if(presentertypename=="License"){
					presenterlicensetypename = $(this).attr('presenterappdetailsid');
					presenterprice	=	$(this).attr('price');
				}
			});
			$(".presenterdownloadlicenselink").click(function(){
				$('.free').hide();
				$('.license').show();
				$('.license').addClass('active');
				$('.free').removeClass('active');
			});
			$(".remedipresentermodal").click(function(){
				$("#downloadtermsModal").modal('show');
			});
		},
		facebookshare:function(){
			var fbshareinfo={};
			fbshareinfo.link="http://"+$('#metaURI').html()+courseData.getContextPath();
			fbshareinfo.name="REAMEDI PRESENTER";
			fbshareinfo.caption="REAMEDI CLOUD";
			fbshareinfo.description="Course content preparation tool";
			//fbshareinfo.message="Course content preparation tool";
			fbshareinfo.picture="http://"+$('#metaURI').html()+courseData.getContextPath()+"/assets/app/images/logo.png";
			//fbshareinfo.picture="https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcT_YqaFXOMk8zIWsG4GBfcttUSYijgdx6gID05T9PwdUqRT8nj6";		
	 		var sharestatus=Ajax.ajaxHelper("POST",$('#context').html()+"/rest/share",fbshareinfo,"json","application/json");
	 		var obj={};
	 		if(sharestatus=="CREATED"){
	 			obj.message="The presenter download information has been successfully shared in facebook";
	 			$(".contentbackgound").css('margin-top','36px');
	 			renderTemplate("#facebookalerttmpl",obj,"#facebookalert");
	 			alertCloseEvent();
	 			 $(".alerter").fadeIn('slow').delay(3000).fadeOut('slow',function() {
	 	 				$(".contentbackgound").css('margin-top','20px');}); 
	 			var contextpath	=	$(".contextpath").val();
				var appath= $(".presenterapp").attr('apppath');
				$(".presenterapp").attr("href", ""+contextpath+"/OpenFile?r1=serverpath&r2="+appath+"&r3=download");
				var presenterappdetailsid=$(this).attr('presenterappdetailsid');
				var presenterappdetail={};
				presenterappdetail.presenterAppdetailsId=presenterappdetailsid;
				var presenterappdetails = courseData.getPresenterDownloadDetails(presenterappdetail);
	 			
	 		}
	 		else if(sharestatus=="NOT_ACCEPTABLE"){
	 			var context=$('#context').html();
	 			$('.presenterapp').attr('href',context+'/rest/publicuser/facebook?redirecturi=/app/downloadpresenter');
	 			
	 		} 	
	 		else{
			    obj.message="Problem in sharing the course in facebook";
			    renderTemplate("#facebookalerttmpl",obj,"#facebookalert");
			    alertCloseEvent();
			 	$(".alerter").removeClass("alert-success");	
			 	$(".alerter").addClass("alert-error");
			 	$(".contentbackgound").css('margin-top','36px');
			 	$(".alerter").fadeIn('slow').delay(3000).fadeOut('slow',function() {
		 		$(".contentbackgound").css('margin-top','20px');});
			 	var contextpath	=	$(".contextpath").val();
				var appath= $(".presenterapp").attr('apppath');
				$(".presenterapp").attr("href", ""+contextpath+"/OpenFile?r1=serverpath&r2="+appath+"&r3=download&r4=ori");
				var presenterappdetailsid=$(this).attr('presenterappdetailsid');
				var presenterappdetail={};
				presenterappdetail.presenterAppdetailsId=presenterappdetailsid;
				var presenterappdetails = courseData.getPresenterDownloadDetails(presenterappdetail);
			}
		},
		presenterLists:function(){
			
			var presenterlist = controller.presenterList();
			renderTemplate("#downloadlisttmpl",presenterlist, "#downloaddetails");
			var presenterlistdata =_.findWhere(presenterlist,{typename: "Free"});
			var presenteriddata	=null;
			var presenterappdetailsid=null;
			if(presenterlistdata!=undefined){
				presenteriddata	=	presenterlistdata.presenterappdetailsid;
			}
			$(".presenterdownload").each(function(){
				if($(this).attr('presenterappdetailsid')==presenteriddata){
					presenterappdetailsid=$(this).attr('presenterappdetailsid');
					$(this).show();
				}
				else{
					$(this).addClass('licensepresenter');
					$(this).hide();
				}
				});
			this.presenterdownloadfree(presenterappdetailsid,presenteriddata);
		    presentertypename	=	$(".licensepresenter").attr('typename');
			presenterlicensetypename	= null;	
			presenterprice	=	null;
			this.presenterdownloadlicense();
			$(".remedipresentermodal").click(function(){
				$("#downloadtermsModal").modal('show');
			});
		},
		presenterdownloadfree:function(presenterappdetailsid,presenteriddata){
			var contextpath	=	$(".contextpath").val();
			var apppath= $(".presenterapp").attr('apppath');
			
			$("#status").click(function(){
				$(this).attr("class", "checked");
				});
			
			$(".presenterdownloadfreelink").each(function(){
				if(presenterappdetailsid==presenteriddata){
				$(this).show();
				}
				else{
					$(this).remove();
				}
				});
			$(".presenterdownloadfreelink").click(function(){
				$('.license').hide();
				$('.free').show();
				$('.free').addClass('active');
				$('.license').removeClass('active');
			});
			$(".presenterdownload").click(function(){
				if($("#status").is('.checked')){
					if($('.free').is(".active")){
						if(confirm("Do you want to download?")){
					$(".presenterapp").attr("href", ""+contextpath+"/OpenFile?r1=serverpath&r2="+apppath+"&r3=download&r4=ori");
					var presenterappdetailsid=$(this).attr('presenterappdetailsid');
					var presenterappdetail={};
					presenterappdetail.presenterAppdetailsId=presenterappdetailsid;
					var presenterappdetails = courseData.getPresenterDownloadDetails(presenterappdetail);
					if(confirm("Would you like to share download information in facebook?")){
						view.facebookshare();
				        }
				      }
					}
					else if($('.license').is(".active")){
						$("#presenterappdetailsid").val(presenterlicensetypename);
						$("#presenterprice").val(presenterprice);
						$("#presenterappdownload").attr('action','licensepresenter');
						$("#presenterappdownload").submit();
					}
					else {
						alert("Please select Free or License");
					}
				}
				else{
					alert("Please select the terms of services");
				}
			});
		},
		presenterdownloadlicense:function(){
				
			$(".licensepresenter").each(function(){
				if(presentertypename=="License"){
					presenterlicensetypename = $(this).attr('presenterappdetailsid');
					presenterprice	=	$(this).attr('price');
				}
			});
			$(".presenterdownloadlicenselink").click(function(){
				$('.free').hide();
				$('.license').show();
				$('.license').addClass('active');
				$('.free').removeClass('active');
			});
			
		},
		presentertypeview:function(status){
		
			if(status=="CREATED"){
			 	 data.presenterapptypeView('A');
			 	 $('.presenterdata').val('');
			 	$('#presentersave').removeClass("presenterupdate");
				$('#presentersave').addClass('presentersave');
			$('#presentersave').html('save');
			$('#alertBoxsuccess').hide();

			view.presenterValidationsuccess("Successfuly Updated");
			$('#alertBoxsuccess').show();
			setTimeout(function(){
					$('#alertBoxsuccess').hide();					
				},2000);
			}
		},
		presentertypeEdit:function(status){
			if(status!=undefined){
				$('.presenterdata').val(status[0].typename);
				$('#presentersave').attr('typeid',status[0].id);
				$('.presenterdata').focus();
			}
		},
		checkpresenterType:function(status,presentertype){
			var self=this;
			if(status!="CREATED"){
				if(status[0].typestatus=="T"){
				if(confirm("Do you want to restore it back from trash?")){
					var id=status[0].id;
					var presenterstatus='A';	
					var typename=status[0].typename;
					data.presenterapptypeStatus(id,presenterstatus,typename);	
					$('.presenterlist').trigger('click');
				}	
				}
				else{
					$('#alertBoxsuccess').hide();
				self.presenterValidation("type name already exists");
				}
			}else{
				data.presenterapptypeInsert(presentertype);	
			}
		},
		checkpresentertypeUpdate:function(status,presentertype){
			var self=this;
			if(status=="typeexists"){				
				self.presenterValidation("type name already allocated");
				
				}else if(status=="nameexists"){
					
				self.presenterValidation("type name already exists");
				
			}
				else{
			  if($('#alertBoxs').attr('hidden')=="hidden"){	
				var id=$('#presentersave').attr('typeid');
				data.presenterapptypeUpdate(id,presentertype);
				self.presentertypeActive();
				
				  }
				}
		},
		presentertypeActive:function(){
			$('.presenterlist').parent().addClass('active');
			$('.trashpresenterlist').parent().removeClass('active');
		},
		trashpresentertypeCheck:function(status,presenterstatus){
			var self=this;
			if(status=="CREATED"){
				self.viewpresentertypeList(presenterstatus);
			}else{
				self.trashPresenterMessage(status);
			}			
		},
		viewpresentertypeList:function(presenterstatus){
			if(presenterstatus=='A'){
				data.presenterapptypeView('T');
				$('#alertBox').show();
			}else{
				data.presenterapptypeView('A');
				view.presenterValidationsuccess("Successfuly deleted");
				$('#alertBoxsuccess').show();
				setTimeout(function(){
					$('#alertBoxsuccess').hide();	
					$('#alertBoxs').hide();
				},1500);
			}
		},
		trashPresenterMessage:function(status){
			var self=this;
			if(status=="CONFLICT"){
				self.presenterValidation("type name already allocated");
						}
		},
		presentertypeData:function(){
			var self=this;
			renderTemplate("#newUploadformtmpl",controller.newPresenter(), "#downloaddetails");
			$('.presentertype').click(function(){
				 $('#presentermodal').modal("show");	
				 var status='A';
				 data.presenterapptypeView(status);	
				 self.savePresentertype();
			});
		},
		duplicatePresentertype:function(){
			var presentertypename=$('.presenterdata').val().trim();
			if($('#presentersave').hasClass('presentersave')){
				data.checkpresenterType(presentertypename);	
				
			}else{
				var id=$('#presentersave').attr('typeid');
				data.checkpresentertypeUpdate(id,presentertypename);
				
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
		}
};
var data={
		updatePresenterData:function(){			
			var presenterDataObj=[];
			var presenterData={};
			presenterData["applicationname"]=$('.editpresenter .appname').val().trim();
			presenterData["presenterid"]=$('.editpresenter .appname').attr('presenterid');
			presenterData["path"]=$('.editpresenter .path').val().trim();
			presenterData["helpurl"]=$('.editpresenter .helpurl').val().trim();
			var existingappDetails=controller.presenterAppDetail(presenterData.presenterid);			
			var typeobj=[];
			$('.typenamesd').each(function(){
				var typediv=$(this);
				var typearray={};
				var typename=typediv.attr('typename');
				var exavail=_.findWhere(existingappDetails, {typename: typename});
				if(exavail!=undefined){
					typearray["checkstatus"]=typediv.find('.checkstatus').attr('checkststus');
					typearray["typeid"]=typediv.attr('id');
					typearray["presenterdetailid"]=exavail.presenterdetailid;
					if(typediv.find('.checkstatus').attr('checkststus')==="1"){
						typearray["workduration"]=typediv.find('.workduration').val().trim();
						typearray["logincount"]=typediv.find('.logincount').val().trim();
						typearray["price"]=typediv.find('.price').val().trim();
						typearray["detailstatus"]="A";						
						typearray["updatestatus"]="update";
					}
					else{
						typearray["workduration"],typearray["logincount"],typearray["price"]="0";
						typearray["detailstatus"]="T";						
						typearray["updatestatus"]="update";						
					}	
					typeobj.push(typearray);
				}
				else{
					if(typediv.find('.checkstatus').attr('checkststus')==="1"){
						typearray["checkstatus"]=typediv.find('.checkstatus').attr('checkststus');
						typearray["typeid"]=typediv.attr('id');
						typearray["workduration"]=typediv.find('.workduration').val().trim();
						typearray["logincount"]=typediv.find('.logincount').val().trim();
						typearray["price"]=typediv.find('.price').val().trim();
						typearray["detailstatus"]="A";
						typearray["updatestatus"]="save";
						typeobj.push(typearray);
					}
				}	
			});
			presenterData.typeobj=typeobj;
			presenterDataObj.push(presenterData);
			var pres=presenterDataObj[0];
			var successvalue=controller.presenterUpdate(pres);
			if(successvalue==="OK"){
				$('#usermessage').modal('show');	
				$('.succesmsg').text("Presenter Information Updated Successfully");
				view.presenterManage();
			}			
		},
		newPresenterData:function(){
			var presenterDataObj=[];
			var presenterData={};
			presenterData.applicationname=$('.newappname').val().trim();
			presenterData.path=$('.newpath').val().trim();
			presenterData.helpurl=$('.newhelpurl').val().trim();			
			var typeobj=[];
			$('.newcheckstatus[checkstatus=1]').each(function(){
				var typediv=$(this).parent().parent();
				var typearray={};
				typearray["typeid"]=$(this).attr('id');
				typearray["workduration"]=typediv.find('.workduration').val().trim();
				typearray["logincount"]=typediv.find('.logincount').val().trim();
				typearray["price"]=typediv.find('.price').val().trim();
				typeobj.push(typearray);					
			});
			presenterData.typeobj=typeobj;
			presenterDataObj.push(presenterData);
			var inserted=controller.presenterNew(presenterDataObj[0]);
			if(inserted==="OK"){
				$('#usermessage').modal('show');	
				$('.succesmsg').text("Presenter uploaded successfully");
				view.presenterManage();
			}
		},
		presenterValidate:function(v,e){//v->>>>>>validationobject  e->>>>>>>event
			(v["cl"],v["pl"],v["hl"],v["al"]).hide();
			var errorstatus=dhelper.error(v);	
			switch (errorstatus) {
			case "ok":
				if(v.type=="new"){
					this.newPresenterData();
				}
				else if (v.type=="edit"){
					this.updatePresenterData();	
				}				
				break;
			case "al":
				dhelper.showerror(e,v.al,"Please enter application name");
				break;
			case "pl":
				dhelper.showerror(e,v.pl,"Please enter application path");
				break;
			case "plv":
				dhelper.showerror(e,v.pl,"Your File Name in Application path should not contain spaces.");
				break;				
			case "hl":
				dhelper.showerror(e,v.hl,"Please enter application help url");
				break;
			case "cl":
				dhelper.showerror(e,v.cl,"Please choose a category for the presenter");
				break;
			case "price":
				dhelper.showerror(e,v.price,"Please choose a Price for the presenter");
				break;
			}
		},
		presenterapptypeView:function(presenterstatus){
			var presenterobj={};
			presenterobj.status=presenterstatus;
			var status=controller.presentertypeList(presenterobj);
			if(presenterstatus=="A"){
				view.presentertypeList(status);
			}else{
				view.presentertypeTrash(status);
			}			
		},
		presenterapptypeInsert:function(presentertype){
			var presenterobj={};
			presenterobj.applicationname=presentertype;
			var status=controller.presentertypeInsert(presenterobj);
			view.presentertypeview(status);
		},
		presenterapptypeEdit:function(id){
			var presenterobj={};
			presenterobj.typeid=id;
			var status=controller.presentertypeEdit(presenterobj);
			view.presentertypeEdit(status);
			
		},
		presenterapptypeUpdate:function(id,name){
			var presenterobj={};
			presenterobj.typeid=id;
			presenterobj.applicationname=name;
			var status=controller.presentertypeUpdate(presenterobj);
			view.presentertypeview(status);
//			alert(successupdated);
				},
		presenterapptypeStatus:function(id,presenterstatus,typename){
			var presenterobj={};
			presenterobj.typeid=id;
			presenterobj.applicationname=typename;
			presenterobj.status=presenterstatus;
			var status=controller.presentertypeStatus(presenterobj);
		    view.trashpresentertypeCheck(status,presenterstatus);
		},			
		checkpresenterType:function(presentertypename){
			var presenterobj={};
			presenterobj.applicationname=presentertypename;
			var status=controller.checkpresenterType(presenterobj);
			view.checkpresenterType(status,presentertypename);			
		},
		checkpresentertypeUpdate:function(id,name){
			var presenterobj={};
			presenterobj.typeid=id;
			presenterobj.applicationname=name;
			var status=controller.checkpresentertypeUpdate(presenterobj);
			view.checkpresentertypeUpdate(status,name);
			
		}
};

var controller={
		downloadHistories:function(){
			return(courseData.getDownloadHistory());
		},
		newPresenter:function(){
			return(courseData.getPresentertype());
		},
		presenterManage:function(){
			return(courseData.getPresenterDetails());
		},
		presenterList:function(){			
			return(_.uniq(courseData.getDownloadDetails(),'typename'));			
		},
		presenterNew:function(presenterobj){
			return(courseData.getNewpresenter(presenterobj));
		},
		presenterEdit:function(presenterid){
			return(courseData.getEditpresenter(presenterid));	
		},
		presenterUpdate:function(presenterDataObj){
			return(courseData.updatePresenterValues(presenterDataObj));			
		},
		presenterAppDetail:function(presenterid){
			return(courseData.getPresenterAppDetailsForId(presenterid));			
		},
		presentertypeList:function(presenterobj){
			return courseData.getPresentertypeList(presenterobj);
		},
		presentertypeInsert:function(presenterobj){
			return(courseData.getNewPresenterapp(presenterobj));
		},
		presentertypeEdit:function(presenterobj){
			return(courseData.getEditPresenterapp(presenterobj));
		},
		presentertypeUpdate:function(presenterobj){
			return(courseData.getUpdatePresenterapp(presenterobj));
		},
		presentertypeStatus:function(presenterobj){
			return(courseData.getStatusPresenterapp(presenterobj));
		},
		deletetrashtypelist:function(id){
			return(courseData.deleteTrashtypelist(id));
		},
		checkpresenterType:function(presenterobj){
			return(courseData.getCheckpresenterType(presenterobj));
		},
		checkpresentertypeUpdate:function(presenterobj){
			return(courseData.getCheckpresentertypeUpdate(presenterobj));
		}
};

var dhelper={
		numvalidate:function(){
			$('.numvalidate').keydown(function(event) {
				if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 || event.keyCode == 13 || 
						(event.keyCode == 65 && event.ctrlKey === true) || 
						(event.keyCode >= 35 && event.keyCode <= 39)) {
					return;
				}
				else {
					if (event.shiftKey || (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) {
						event.preventDefault(); 
					}   
				}
			});
		},
		numberplusandminus:function(){
			$('.number-spinner button').click(function(){
				var btn = $(this),
				oldValue = btn.closest('.number-spinner').find('input').val().trim(),
				newVal = 0;
				if (btn.attr('data-dir') == 'up') {
					newVal = parseInt(oldValue) + 1;
				} 
				else {
					if (oldValue > 0) {
						newVal = parseInt(oldValue) - 1;
					} 
					else {
						newVal = 0;
					}
				}
				btn.closest('.number-spinner').find('input').val(newVal);
			});
		},
		showerror:function(e,id,message){			
			e.preventDefault();
			id.show().html(message);
		},
		error:function(v){//v->>>>>>>>>>>validation data object
			if(v.applicationname===0){
				return "al";
			}
			else if(v.pathvalidate===true){
				return "plv";
			}
			else if(v.path===0){
				return "pl";
			}
			else if(v.helpurl===0){
				return "hl";				
			}
			else if(v.prescategory===0){
				return "cl";				
			}			
			else{
				return "ok";				
			}
		}
};