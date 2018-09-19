$(document).ready(function(){
	Handlebars.registerHelper('roundtwo', function(num) { 
		return num.toFixed(2); 
	});
	/* ************************************* Range Slider **************************************** */
	$("#authorroyalty").slider({
		tooltip: 'always',
	});
	$("#authorroyalty").slider({
		formatter: function(value) {
			var slider_track =100-value;
			$("#orgSlider").children('div').children('.slider-selection').css("width",""+slider_track+"%");
			$("#orgSlider").children('div').children('.min-slider-handle').css("left",""+slider_track+"%");
			$("#orgSlider").children('div').children('.slider-track-high').css("width",""+slider_track+"%");
			$("#orgSlider").children('div:nth-child(2)').css("left",""+slider_track+"%");
			$("#orgSlider").children('div:nth-child(2)').children('div:nth-child(2)').text(""+slider_track);
			return value;
		}
	});
	$("#orgroyalty").slider({
		tooltip: 'always',
	});
	$("#orgroyalty").slider({
		formatter: function(value) {
			var slider_track =100-value;
			$("#authorSlider").children('div').children('.slider-selection').css("width",""+slider_track+"%");
			$("#authorSlider").children('div').children('.min-slider-handle').css("left",""+slider_track+"%");
			$("#authorSlider").children('div').children('.slider-track-high').css("width",""+slider_track+"%");
			$("#authorSlider").children('div:nth-child(2)').css("left",""+slider_track+"%");
			$("#authorSlider").children('div:nth-child(2)').children('div:nth-child(2)').text(""+slider_track);
			return value;
		}
	});
	/* *************************************End Range Slider **************************************** */

	$('.leftsideli').click(function (e) {
		//save the latest tab; use cookies if you like 'em better:
		sessionStorage.setItem('lastTab', $(e.target).attr('class'));
	});
	var type = $(".active").children("a").attr("royaltytypeid");
	getRoyaltyList.royaltyList(type);
	getMenuList.menuList();

	/* ************************************* datepicker **************************************** */
	
	$('.fromdateval1').on('changeDate',function(){
		$('.todateval').datepicker('setStartDate', new Date($(this).val()));
		//$(".todateval").prop('disabled',false);			
	});		 
	$('.fromdateval1').datepicker({
		format: 'dd-M-yyyy',
		autoclose:true,
		todayHighlight: true
	});
	$('.todateval1').datepicker({
		format: 'dd-M-yyyy',
		autoclose:true,
		todayHighlight: true
	});
	/* *************************************end datepicker **************************************** */

	/* ************************************* current, forth completed process **************************************** */
	$(".completed").click(function(){
		$('.current').removeClass('active');
		$('.current').removeClass('btn-blue');
		$('.forthcoming').removeClass('active');
		$('.forthcoming').removeClass('btn-blue');
		$(this).addClass('active');
		$(this).addClass('btn-blue');
		$(".previousroyalty").css('display','none');
		$(".nextroyalty").css('display','none');
		getRoyaltyList.completedList();
		helper.pagination();
	});
	$(".forthcoming").click(function(){
		$('.current').removeClass('active');
		$('.current').removeClass('btn-blue');
		$('.completed').removeClass('btn-blue');
		$('.completed').removeClass('active');
		$(this).addClass('active');
		$(this).addClass('btn-blue');
		$(".previousroyalty").css('display','none');
		$(".nextroyalty").css('display','none');
		getRoyaltyList.forthComingList();
		helper.pagination();
	});
	$(".current").click(function(){
		$('.forthcoming').removeClass('active');
		$('.forthcoming').removeClass('btn-blue');
		$('.completed').removeClass('btn-blue');
		$('.completed').removeClass('active');
		$(this).addClass('active');
		$(this).addClass('btn-blue');
		$(".previousroyalty").css('display','none');
		$(".nextroyalty").css('display','none');
		var type = $(".active").children("a").attr("royaltytypeid");
		getRoyaltyList.royaltyList(type);
	});
	/* *************************************end current, forth completed process **************************************** */

	/* ************************************* pagination **************************************** */
	$(".previousroyalty").click(function(){
		var royaltypageid=$(".royaltypreviouspageid").val();
		var type = $(".active").children("a").attr("royaltytypeid");
		var royaltyPreviousList = courseData.finishedRoyalty(royaltypageid,type);
		if(royaltyPreviousList.length>0){
			$(".royaltypreviouspageid").val(parseInt($(".royaltypreviouspageid").val())+1);
			if($(".royaltypreviouspageid").val()=="0"){
				$(".royaltypreviouspageid").val(parseInt($(".royaltypreviouspageid").val())-1);
				$(".royaltypageid").val(parseInt($(".royaltypageid").val())+1);
				if($(".royaltypreviouspageid").val()=="-1"){
					$(".royaltypageid").val(parseInt($(".royaltypageid").val())-1);
				}
				$(".nextroyalty").css('display','block');
				$(".nextroyalty").removeAttr( 'style');
				$(".previousroyalty").css('display','none');
			}
			else{
				$(".nextroyalty").css('display','block');
				$(".previousroyalty").css('margin-right','59px');
				$(".nextroyalty").css('margin-top','-34px');
				$(".nextroyalty").css('margin-left','71px');
			}
		}
		getRoyaltyList.royaltyRenderProcess(royaltyPreviousList);
	});
	$(".nextroyalty").click(function(){
		var royaltypageid=$(".royaltypageid").val();
		var type = $(".active").children("a").attr("royaltytypeid");
		var royaltyNextList = courseData.finishedRoyalty(royaltypageid,type);
		if(royaltyNextList.length>0){
			$(".royaltypageid").val(parseInt($(".royaltypageid").val())-1);
			var royaltypageids= $(".royaltypageid").val();
			var royaltyNextLists = courseData.finishedRoyalty(royaltypageids,type);
			if(royaltyNextLists.length>0){
				$(".previousroyalty").css('display','block');
				$(".previousroyalty").css('margin-right','59px');
				$(".nextroyalty").css('margin-left','76px');
				$(".nextroyalty").css('margin-top','-34px');
			}
			else{
				$(".previousroyalty").css('display','block');
				$(".nextroyalty").css('display','none');
				$(".royaltypageid").val(parseInt($(".royaltypageid").val())+1);
				$(".royaltypreviouspageid").val(parseInt($(".royaltypageid").val())+1);
			}
		}
		getRoyaltyList.royaltyRenderProcess(royaltyNextList);
	});
	/* *************************************end pagination **************************************** */

	/* ************************************* add royalty modal **************************************** */
	$(".addroyalty").click(function(){
		$(".alertmessage").empty();
		$('.fromdateval').val(" ");
		$(".todateval").val(" ");
		$("#addroyaltymodal").modal('show');
		$(".addauthorroyalty").css('display','none');
		$(".passdatemsg").css('display','none');
		$(".todateval").prop('disabled',true);
		$(".addfromdate").css('display','none');
		$(".addtodate").css('display','none');
		$('#myModalLabel').text('Add New Royalty');
		$('.submitroyalty').show();
		$('.updateroyalty').hide();

		var authorroyaltys =0;
		var slider_tracks =100-authorroyaltys;
		$("#authorSlider").children('div').children('.slider-selection').css("width",""+authorroyaltys+"%");
		$("#authorSlider").children('div').children('.min-slider-handle').css("left",""+authorroyaltys+"%");
		$("#authorSlider").children('div').children('.slider-track-high').css("width",""+slider_tracks+"%");
		$("#authorSlider").children('div:nth-child(2)').css("left",""+authorroyaltys+"%");
		$("#authorSlider").children('div:nth-child(2)').children('div:nth-child(2)').text(""+authorroyaltys);

		$("#orgSlider").children('div').children('.slider-selection').css("width","100%");
		$("#orgSlider").children('div').children('.min-slider-handle').css("left","100%");
		$("#orgSlider").children('div').children('.slider-track-high').css("width","0%");
		$("#orgSlider").children('div:nth-child(2)').css("left","100%");
		$("#orgSlider").children('div:nth-child(2)').children('div:nth-child(2)').text("100");

	});
	$(".submitroyalty").click(function(){
		getRoyaltyList.createroyaltyprocess();
	});
	/* *************************************end add royalty modal **************************************** */
		if(sessionStorage.getItem('lastTab')){
			  var lastTab = sessionStorage.getItem('lastTab');
			  if(lastTab.search("royaltysetting")>=0){
				  switchtab(1);
			  }
			  else if(lastTab.search("requestlimit")>=0){
				  switchtab(2);
			  }
		     else if(lastTab.search("paymentrequest")>=0){
		    	 switchtab(3);
			  }
		     else if(lastTab.search("cleardate")>=0){
		    	 switchtab(4);
			  }
		     else if(lastTab.search("issuedhistory")>=0){
		    	 switchtab(5);
			  }
		  }
		function switchtab(e){
		 switch(e){
			  case 1:{
				  $('.royaltysetting').trigger('click');
				  break;
			  }
			  case 2:{
				  $('.requestlimit').trigger('click');
				  break;
			  }
			  case 3:{
				  $('.paymentrequest').trigger('click');
				  break;
			  }
			  case 4:{
				  $('.cleardate').trigger('click');
				  break;
			  }
			  case 5:{
				  $('.issuedhistory').trigger('click');
				  break;
			  }	
			  }
		}
		var tobeRedirected = $("#hidroyaltyPreviousListsrole").attr('ss');
		if(tobeRedirected=="roy"){
			$('.paymentrequest').trigger('click');
		}
		$(".royaltytypeactive").click(function(){
			var type = $(this).attr('royaltytypeid');
			if(type=="1"){
				$('.royaltyheader').html("Course Royalty Settings");
			}
			else{
				$('.royaltyheader').html("Live Session Royalty Settings");
			}
			$('.forthcoming').removeClass('active');
			$('.forthcoming').removeClass('btn-blue');
			$('.completed').removeClass('btn-blue');
			$('.completed').removeClass('active');
			$(".current").addClass('active');
			$(".current").addClass('btn-blue');
			$(".previousroyalty").css('display','none');
			$(".nextroyalty").css('display','none');
			getRoyaltyList.royaltyList(type);
		});
});

Utility={
	
		convertToUTC530:function(date){
			var timezoneOffset=new Date(date).getTimezoneOffset();
			if(timezoneOffset<0){
				date=date+Math.abs(timezoneOffset)*60*1000-19800000;			    
			}
			else if(timezoneOffset>0){
				date=date-timezoneOffset*60*1000-19800000;	
			}
			return date;
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
getRoyaltyList = {
		royaltyList:function(type){
			var self = this;
					var royaltyList = courseData.getRoyaltyList(type);
					self.royaltyCurrentRenderProcess(royaltyList);
					var currentRoyalty=courseData.getDefaultRoyalty(type);	
					renderTemplate("#defaultroyaltytmplt", currentRoyalty[0],"#defaultroyal");
					$('.editcurrentroyalty').click(function(){
						$(".alertmessage").empty();
						$("#addroyaltymodal").modal('show');
						$(".addauthorroyalty").css('display','none');
						$(".addfromdate").css('display','none');
						$(".addtodate").css('display','none');
						$(".passdatemsg").css('display','none');
						$('.fromdateval').val("");
						$(".todateval").val("");
						$('#myModalLabel').text('Update Royalty');
						$('.updateroyalty').show();
						$('.submitroyalty').hide();
						var id = $(this).attr('id');
						var effectfrom= $(this).attr('effectfrom');
						var orgroyalty = $(this).attr('orgroyalty');
						var authorroyalty = $(this).attr('authorroyalty');
						var effectto = $(this).attr('effectto');
						self.updateprocess(id,effectfrom,orgroyalty,authorroyalty,effectto);
						$(".todateval").prop('disabled',true);
						$('.todateval').datepicker('setStartDate', new Date($('.fromdateval').val()));
						$('.updateroyalty').off('click');
						$('.updatecurrentroyalty').on('click');
						$('.updatecurrentroyalty').click(function(){
							getRoyaltyList.updateclickprocess("current");
						});
					});
					$('.delcurrentroyalty').click(function(){
						if (confirm("Are you sure want to Trash?"))
						{
						var id = $(this).attr('id');
						var royaltyresult = courseData.deleteCurrentRoyalty(id);
						if(royaltyresult=="ACCEPTED"){
							self.royaltyList($('.royaltytypeactive').attr('royaltytypeid'));
							$(".deleteroyalty").show();
							$(".deleteroyalty").removeClass('alert-danger').addClass('alert-success').text("This Royalty is Deleted Sucessfully");
							setTimeout(function(){ 
								$('.learningtablinks').trigger('click');
								$(".deleteroyalty").hide();
							}, 3000);
						}
						else if(royaltyresult=="FINISHED OR RUNNING CURRENTLY"){
							$(".deleteroyalty").show();
							$(".deleteroyalty").removeClass('alert-success').addClass('alert-danger').text("The Royalty has been used for course subscription, so can\'t be trashed");
							setTimeout(function(){ 
								$(".deleteroyalty").hide();
							}, 3000);
						}
						}						
					});
		},
		forthComingList:function(){
			var type = $(".active").children("a").attr("royaltytypeid");
			var royaltyNextList = courseData.finishedRoyalty(1,type);
			getRoyaltyList.royaltyRenderProcess(royaltyNextList);
			if(royaltyNextList.length>0){
				var royaltyNextLists = courseData.finishedRoyalty(2,type);
				if(royaltyNextLists.length>0){
					$(".previousroyalty").css('display','none');
					$(".nextroyalty").css('display','block');
				}
			}
		},
		completedList:function(){
			var type = $(".active").children("a").attr("royaltytypeid");
			var royaltyPreviousList = courseData.finishedRoyalty(-1,type);
			getRoyaltyList.royaltyRenderProcess(royaltyPreviousList);
			if(royaltyPreviousList.length>0){
				$(".royaltypageid").val(-2);
				var royaltyPageId = $(".royaltypageid").val();
				var royaltyPreviousLists = courseData.finishedRoyalty(royaltyPageId,type);
				if(royaltyPreviousLists.length>0){
					$(".previousroyalty").css('display','none');
					$(".nextroyalty").css('display','block');
				}
			}
		},
		royaltyRenderProcess:function(royaltyList){
			 Handlebars.registerHelper('startdateformat', function(effectfrom) 
					 { 
					 var d = new Date(effectfrom);
				         var date =d.toDateString();
				         var time =d.toTimeString().split(" ");
				         data=date.split(" ");
				         var data1 = data[2]+"/"+data[1]+"/"+data[3]+" "+time[0];
					 return data1; 
					 });
			 Handlebars.registerHelper('enddateformat', function(effectto) 
					 { 
					 var d = new Date(effectto);
					 var date =d.toDateString();
					 var time =d.toTimeString().split(" ");
			         data=date.split(" ");
			         var data1 = data[2]+"/"+data[1]+"/"+data[3]+" "+time[0];
					 return data1; 
					 });
			renderTemplate("#royaltytmpl", royaltyList,"#royaltytable");
			var self = this;
			$("#paymentdatetable").css('display','none');
			$('[name=rangeInput]').val(0);
			
				if($('.completed').is('.active')){
					$(".action").css('display','none');
					$(".actiondetail").css('display','none');
					}
				if($('.forthcoming').is('.active')){
					$(".action").css('display','block');
					$(".actiondetail").css('display','block');
					}
				
			$(".royaltyeditbtn").click(function(){
				$(".alertmessage").empty();
				$("#addroyaltymodal").modal('show');
				$(".addauthorroyalty").css('display','none');
				$(".passdatemsg").css('display','none');
				$(".addfromdate").css('display','none');
				$(".addtodate").css('display','none');
				$('.fromdateval').val("");
				$(".todateval").val("");
				$('#myModalLabel').text('Update Royalty');
				$('.updateroyalty').show();
				
				
				$('.submitroyalty').hide();
				var id = $(this).attr('id');
				var effectfrom= $(this).attr('effectfrom');
				var orgroyalty = $(this).attr('orgroyalty');
				var authorroyalty = $(this).attr('authorroyalty');
				var effectto = $(this).attr('effectto');
				self.updateprocess(id,effectfrom,orgroyalty,authorroyalty,effectto);
				//$(".todateval").prop('disabled',false);
				$('.todateval').datepicker('setStartDate', new Date($('.fromdateval').val()));
				$('.updatecurrentroyalty').off('click');
				$('.updateroyalty').on('click');
				$(".updateroyalty").click(function(){
				    getRoyaltyList.updateclickprocess("forthcoming");
				});				
			});
			$(".royaltytrashbtn").click(function(){
				if (confirm("Are you sure want to Trash?"))
				{
				var id = $(this).attr('id');
				var royaltyresult = courseData.deleteRoyalty(id);
				if(royaltyresult=="ACCEPTED"){
					$('.forthcoming').trigger('click');
					$(".deleteroyalty").show();
					$(".deleteroyalty").removeClass('alert-danger').addClass('alert-success').text("This Royalty is Deleted Sucessfully");
					setTimeout(function(){ 
						$(".deleteroyalty").hide();
					}, 3000);
				}
				else if(royaltyresult=="FINISHED OR RUNNING CURRENTLY"){
					$(".deleteroyalty").show();
					$(".deleteroyalty").removeClass('alert-success').addClass('alert-danger').text("The Royalty is currently running or finished.");
					setTimeout(function(){ 
						$(".deleteroyalty").hide();
					}, 3000);
				}
				}
			});
		},
		royaltyCurrentRenderProcess:function(royaltyList){
			 Handlebars.registerHelper('startdateformat', function(effectfrom) 
					 { 
				 if(effectfrom==null){
					 return "--";
				 }
				 else{
					 var d = new Date(effectfrom);
				         var date =d.toDateString();
				         var time =d.toTimeString().split(" ");
				         data=date.split(" ");
				         var data1 = data[2]+"/"+data[1]+"/"+data[3]+" "+time[0];
					 return data1; 
				 }
					 });
			 Handlebars.registerHelper('enddateformat', function(effectto) 
					 { 
				 if(effectto==null){
						return "--"; 
					 }
				 else{
					 var d = new Date(effectto);
					 var date =d.toDateString();
					 var time =d.toTimeString().split(" ");
			         data=date.split(" ");
			         var data1 = data[2]+"/"+data[1]+"/"+data[3]+" "+time[0];
					 return data1;
				 }
					 });
			renderTemplate("#currentroyaltytmplt", royaltyList,"#royaltytable");
			if($(".effectstartdatecontent").text()=="--"){
				 if($(".effecttodatecontent").text()=="--"){
					 $(".effecttodatelable").css('margin-left','216px');
				 }
			 }
			$(".previousroyalty").css('display','none');
			$(".nextroyalty").css('display','none');
			$("#paymentdatetable").css('display','none');
			
		},
			createroyaltyprocess:function(){
				var self=this;
				var authorroyalty = $("#authorSlider").text();
				var orgroyalty = $("#orgSlider").text();
				var startdate = $('.fromdateval').val();
				var enddate = $('.todateval').val();
				
				var royaltyconfigtype = $(".active").children("a").attr("royaltytypeid");
				
				$(".addfromdate").css('display','none');
				$(".addtodate").css('display','none');
				$(".addauthorroyalty").css('display','none');
				$(".addfromdate").css('display','none');
				$('.royaltytodate').css('margin-top','0px');
				
				if(authorroyalty=="0"){
					$(".addauthorroyalty").css('display','block');
					$('.orgroyaltystyle').css('margin-top','-29px');
				}
				else if(startdate==" "){
					$(".addfromdate").css('display','block');
					$('.royaltytodate').css('margin-top','-20px');
					
				}
				else if(enddate==" "){
					$(".addtodate").css('display','block');
				}
				else {
					fromdates={};
					todate={} ;
					if(startdate.indexOf('-')>0&&enddate.indexOf('-')){
						var datearray=startdate.split('-');
						var datearray1=enddate.split('-');
						fromdates=new Date(datearray[1]+" "+datearray[0]+" "+datearray[2]).getTime();
					    todate=new Date(datearray1[1]+" "+datearray1[0]+" "+datearray1[2]).getTime();
					    fromdates=Utility.convertToUTC530(fromdates);
						todate=Utility.convertToUTC530(todate);
						}
						else{
							fromdates=new Date(startdate).getTime();
							todate=new Date(enddate).getTime();
						    fromdates=Utility.convertToUTC530(fromdates);
							todate=Utility.convertToUTC530(todate);
						}					
					royaltyconfig={};
					royaltyconfig.authorroyalty=authorroyalty;
					royaltyconfig.orgroyalty=orgroyalty;
					royaltyconfig.effectfrom=fromdates;
					royaltyconfig.effectto=todate;
					royaltyconfig.royaltyconfigtype=royaltyconfigtype;
					var createRoyalty = courseData.createRoyalty(royaltyconfig);
					if(createRoyalty=="CREATED"){
						$('.forthcoming').trigger('click');
						$(".passdatemsg").show();
						$(".passdatemsg").removeClass('alert-danger').addClass('alert-success').text("Royalty has been created successfully");
						setTimeout(function(){ 
							$("#addroyaltymodal").modal('hide');
						}, 3000);
					}
					else if(createRoyalty=="OK"){
						$('.current').trigger('click');
						$(".passdatemsg").show();
						$(".passdatemsg").removeClass('alert-danger').addClass('alert-success').text("Royalty has been created successfully");
						setTimeout(function(){ 
							$("#addroyaltymodal").modal('hide');
						}, 3000);
					}
					else if(createRoyalty =="FINISHED OR RUNNING CURRENTLY"){
						$(".passdatemsg").show();
						$(".passdatemsg").removeClass('alert-success').addClass('alert-danger').text("The Royalty is currently running or finished.");
						setTimeout(function(){ 
							$(".passdatemsg").hide();
							$('.fromdateval').val(" ");
							$(".todateval").val(" ");
						}, 3000);
					}
					else if(createRoyalty=="PASTDATE"){
						$(".passdatemsg").show();
						$(".passdatemsg").removeClass('alert-success').addClass('alert-danger').text("Please pick future date");
						setTimeout(function(){ 
							$(".passdatemsg").hide();
							$('.fromdateval').val(" ");
							$(".todateval").val(" ");
						}, 3000);
						
					}
					else if(createRoyalty=="CONFLICT ON SCHEDULE"){
						$(".passdatemsg").show();
						$(".passdatemsg").removeClass('alert-success').addClass('alert-danger').text("The selected duration has been already scheduled, please pick some other dates.");
						setTimeout(function(){ 
							$(".passdatemsg").hide();
							$('.fromdateval').val(" ");
							$(".todateval").val(" ");
						}, 3000);
					}
					else if(createRoyalty=="DEFAULTINPOWER"){
						$(".passdatemsg").show();
						$(".passdatemsg").removeClass('alert-success').addClass('alert-danger').text("Default Royalty has been taken for course enrollment, New Royalty can't be created for today's date");
						setTimeout(function(){ 
							$(".passdatemsg").hide();
							$('.fromdateval').val(" ");
							$(".todateval").val(" ");
						}, 3000);
					}
				}
			},
		updateprocess:function(id,effectfrom,orgroyalty,authorroyalty,effectto){
			
			var slider_tracks =100-authorroyalty;
			$("#authorSlider").children('div').children('.slider-selection').css("width",""+authorroyalty+"%");
			$("#authorSlider").children('div').children('.min-slider-handle').css("left",""+authorroyalty+"%");
			$("#authorSlider").children('div').children('.slider-track-high').css("width",""+slider_tracks+"%");
			$("#authorSlider").children('div:nth-child(2)').css("left",""+authorroyalty+"%");
			$("#authorSlider").children('div:nth-child(2)').children('div:nth-child(2)').text(""+authorroyalty);
			
			var slider_track =100-orgroyalty;
			$("#orgSlider").children('div').children('.slider-selection').css("width",""+orgroyalty+"%");
			$("#orgSlider").children('div').children('.min-slider-handle').css("left",""+orgroyalty+"%");
			$("#orgSlider").children('div').children('.slider-track-high').css("width",""+slider_track+"%");
			$("#orgSlider").children('div:nth-child(2)').css("left",""+orgroyalty+"%");
			$("#orgSlider").children('div:nth-child(2)').children('div:nth-child(2)').text(""+orgroyalty);
			
			var startdate = parseInt(effectfrom);
			var d = new Date(startdate);
			var effectfromdate =d.toDateString();
			var data = effectfromdate.split(" ");
			var date1=data[2]+ "-" + data[1] + "-" +data[3]; 
			$(".fromdateval").val(date1);
			
			var enddate = parseInt(effectto);
			var d = new Date(enddate);
			var effecttodate =d.toDateString();
			var todata = effecttodate.split(" ");
			var date4=todata[2]+ "-" + todata[1] + "-" +todata[3]; 
			$(".todateval").val(date4);
			$('.royaltyconfigid').val(id);
		},
			updateclickprocess:function(method){
					var authorroyalty = $("#authorSlider").text();
					var orgroyalty = $("#orgSlider").text();
					var startdate = $(".fromdateval").val();
					var enddate = $(".todateval").val();
					var type = $(".active").children("a").attr("royaltytypeid");
					
					$(".addfromdate").css('display','none');
					$(".addtodate").css('display','none');
					if(authorroyalty=="0"){
						$(".addauthorroyalty").css('display','block');
					}
					else if(startdate==""){
						$(".addfromdate").css('display','block');
					}
					else if(enddate==""){
						$(".addtodate").css('display','block');
					}
					else{
						fromdates={};
						todate={} ;
						if(startdate.indexOf('-')>0&&enddate.indexOf('-')){
							var datearray=startdate.split('-');
							var datearray1=enddate.split('-');
							fromdates=new Date(datearray[1]+" "+datearray[0]+" "+datearray[2]).getTime();
						    todate=new Date(datearray1[1]+" "+datearray1[0]+" "+datearray1[2]).getTime();
						    fromdates=Utility.convertToUTC530(fromdates);
							todate=Utility.convertToUTC530(todate);
							}
							else{
								fromdates=new Date(startdate).getTime();
								todate=new Date(enddate).getTime();
								fromdates=Utility.convertToUTC530(fromdates);
								todate=Utility.convertToUTC530(todate);
							}
						var id =$(".royaltyconfigid").val();
						
						royaltyconfig={};
						royaltyconfig.authorroyalty=authorroyalty;
						royaltyconfig.orgroyalty=orgroyalty;
						royaltyconfig.effectfrom=fromdates;
						royaltyconfig.effectto=todate;
						royaltyconfig.royaltyconfigtype=type;
						var updateRoyalty="";
						if(method=="forthcoming"){
						updateRoyalty = courseData.updateRoyalty(id,royaltyconfig);
						if(updateRoyalty=="OK"){
							  $(".current").trigger("click");
							  $(".passdatemsg").show();
							  $(".passdatemsg").removeClass('alert-danger').addClass('alert-success').text("Royalty has been updated successfully");
							  setTimeout(function(){ 
								  $("#addroyaltymodal").modal('hide');
								}, 3000);
							  
						}
						else if(updateRoyalty=="CREATED"){
							$(".forthcoming").trigger("click");
							$(".passdatemsg").show();
							$(".passdatemsg").removeClass('alert-danger').addClass('alert-success').text("Royalty has been updated successfully");
							setTimeout(function(){ 
								$("#addroyaltymodal").modal('hide');
							}, 3000);
						}
						else if(updateRoyalty=="FINISHED OR RUNNING CURRENTLY"){
							$('.passdatemsg').show();
							$('.passdatemsg').removeClass('alert-success').addClass('alert-danger').text('The Royalty is currently running or finished.');
							setTimeout(function(){ 
								$(".passdatemsg").hide();
							}, 3000);
						}
						else if(updateRoyalty=="PASTDATE"){
							$(".passdatemsg").show();
							$(".passdatemsg").removeClass('alert-success').addClass('alert-danger').text("Please pick future date");
							setTimeout(function(){ 
								$(".passdatemsg").hide();
								$('.fromdateval').val(" ");
								$(".todateval").val(" ");
							}, 3000);
						}
						else if(updateRoyalty=="CONFLICT ON SCHEDULE"){
							$(".passdatemsg").show();
							$(".passdatemsg").removeClass('alert-success').addClass('alert-danger').text("The selected duration has been already scheduled, please pick some other dates.");
							setTimeout(function(){ 
								$(".passdatemsg").hide();
							}, 3000);
					}
						}
						else if(method=="current"){
						updateRoyalty = courseData.updateCurrentRoyalty(id,royaltyconfig);
						if(updateRoyalty=="OK"){
							  $(".current").trigger("click");
							  $(".passdatemsg").show();
							  $(".passdatemsg").removeClass('alert-danger').addClass('alert-success').text("Royalty has been updated successfully");
								setTimeout(function(){ 
									$("#addroyaltymodal").modal('hide');
								}, 3000);
						}
						else if(updateRoyalty=="CREATED"){
							$(".forthcoming").trigger("click");
							$(".passdatemsg").show();
							$(".passdatemsg").removeClass('alert-danger').addClass('alert-success').text("Royalty has been updated successfully");
							setTimeout(function(){ 
								$("#addroyaltymodal").modal('hide');
							}, 3000);
						}
						else if(updateRoyalty=="FINISHED OR RUNNING CURRENTLY"){
							$('.passdatemsg').show();
							$('.passdatemsg').removeClass('alert-success').addClass('alert-danger').text('The Royalty is currently running or finished.');
							setTimeout(function(){ 
								$(".passdatemsg").hide();
							}, 3000);
						}
						else if(updateRoyalty=="PASTDATE"){
							$(".passdatemsg").show();
							$(".passdatemsg").removeClass('alert-success').addClass('alert-danger').text("Please pick future date");
							setTimeout(function(){ 
								$(".passdatemsg").hide();
								$('.fromdateval').val(" ");
								$(".todateval").val(" ");
							}, 3000);
						}
						else if(updateRoyalty=="CONFLICT ON SCHEDULE"){
							$(".passdatemsg").show();
							$(".passdatemsg").removeClass('alert-success').addClass('alert-danger').text("The selected duration has been already scheduled, please pick some other dates.");
							setTimeout(function(){ 
								$(".passdatemsg").hide();
							}, 3000);
							
					}
						}
					}
			},
			royaltypayment:function(){
				$(".royaltypaymenttype").click(function(){
					var type = $(this).attr('paymenttype');
					if(type=="1"){
						$(".issuedhistory").trigger("click");
					}
					else if(type="2"){
						getRoyaltyList.royaltypaymentprocess();
					}
				});
				$(".livesessionuserdetail").click(function(){
					$("#userdetailmodal").modal("show");
					var livesessionuserlist = courseData.getlivesessionuserlist($(this).attr('livesessionid'));
					Handlebars.registerHelper('paymentdate', function(paymentdate) 
							 { 
							 var d = new Date(paymentdate);
						         var date =d.toDateString();
						         data=date.split(" ");
						         var data1 = data[2]+"/"+data[1]+"/"+data[3];
							 return data1; 
							 });
					Handlebars.registerHelper('paymentamount', function(paymentamount) 
							 { 
						if(paymentamount=="null"){
							return "--";
						}
						return paymentamount;
							 }); 
					Handlebars.registerHelper('millitodate', function(date) 
							 { 
							 var d = new Date(date);
						         var date =d.toDateString();
						         data=date.split(" ");
						         var data1 = data[2]+"/"+data[1]+"/"+data[3];							       
							 return data1; 
							 });
					renderTemplate("#livesessionuserdetails",livesessionuserlist,"#userdetaillist");
				});
			},
			royaltypaymentprocess:function(){
				Handlebars.registerHelper('roundtwo', function(num) { 
					return num.toFixed(2); 
				});
				var royaltypaymentlist = courseData.getlivesessiontotalpayment();
				renderTemplate("#livesessionpaymentdateissuedtmpl", royaltypaymentlist,"#royaltytable");
				var type ="2";
				var royaltyOrganizationTotalList=courseData.getRoyaltyOrganizationTotalPayment(type);
				var orgTotalAmount=_.pluck(royaltyOrganizationTotalList,"orgroyaltyamount");
				var uptodate=_.pluck(royaltyOrganizationTotalList,"upto");
				var datas = new Date(uptodate[0]);
				var d = new Date(datas);
		        var date =d.toDateString();
		        data=date.split(" ");
		        var data1 = data[2]+"/"+data[1]+"/"+data[3];
		        var time = d.toLocaleTimeString();
		        var datetime=data1+" "+time;
		        if(orgTotalAmount==null){
		        	$(".orgtotalamount").text("-");
		        }
		        else{
		        	$(".orgtotalamount").text(parseFloat(orgTotalAmount.toString()).toFixed(2));
		        }
				$(".currentdates").text(datetime);
				getRoyaltyList.royaltypayment();
			},
			removebodyattr:function(){
				$('body').removeAttr('style');
			}
};
	getMenuList={
		menuList:function(){
			$(".royaltysetting").addClass("active");
			$(".royaltysetting").click(function(){
				$(".previousroyalty").css('display','block');
				$(".nextroyalty").css('display','block');
				$(".royaltysettingmsg").css('display','block');
				$(".previousroyalty").css('margin-right','59px');
				$(".nextroyalty").css('margin-left','76px');
				$(".nextroyalty").css('margin-top','-34px');
				
				$(".royaltysetting").addClass("active");
				$(".requestlimit").removeClass("active");
				$(".paymentrequest").removeClass("active");
				$(".cleardate").removeClass("active");
				$(".issuedhistory").removeClass("active");
				
				$('.forthcoming').removeClass('active');
				$('.forthcoming').removeClass('btn-blue');
				$('.completed').removeClass('btn-blue');
				$('.completed').removeClass('active');
				$(".current").addClass('active');
				$(".current").addClass('btn-blue');
				var type = $(".active").children("a").attr("royaltytypeid");
				getRoyaltyList.royaltyList(type);
			});
			
			$(".issuedhistory").click(function(){
				$(".royaltysetting").removeClass("active");
				$(".requestlimit").removeClass("active");
				$(".paymentrequest").removeClass("active");
				$(".cleardate").removeClass("active");
				$(".issuedhistory").addClass("active");
					$(".currentdate").empty();
					var type = "1";
					var royaltyPaymentList = courseData.getRoyaltyPayment();
					var royaltyOrganizationTotalList=courseData.getRoyaltyOrganizationTotalPayment(type);
					var orgTotalAmount=_.pluck(royaltyOrganizationTotalList,"orgroyaltyamount");
					var uptodate=_.pluck(royaltyOrganizationTotalList,"upto");
					var datas = new Date(uptodate[0]);
					var d = new Date(datas);
			        var date =d.toDateString();
			        data=date.split(" ");
			        var data1 = data[2]+"/"+data[1]+"/"+data[3];
			        var time = d.toLocaleTimeString();
			        var datetime=data1+" "+time;

			        renderTemplate("#paymentdateissuedtmpl", royaltyPaymentList,"#royaltytable");
			        helper.pagination();
			        getRoyaltyList.royaltypayment();
			        if(orgTotalAmount==null){
			        	$(".orgtotalamount").text("-");
			        }
			        else{
			        	$(".orgtotalamount").text(parseFloat(orgTotalAmount.toString()).toFixed(2));
			        }
					$(".currentdate").text(datetime);
					$(".previousroyalty").css('display','none');
					$(".nextroyalty").css('display','none');
					$(".royaltysettingmsg").css('display','none');
					$(".userdetail").click(function(){
						$("#userdetailmodal").modal("show");
						var courseid = $(this).attr('courseid');
						var userlist = courseData.getNoUserDetails(courseid);
						Handlebars.registerHelper('paymentdate', function(paymentdate) 
								 { 
								 var d = new Date(paymentdate);
							         var date =d.toDateString();
							         data=date.split(" ");
							         var data1 = data[2]+"/"+data[1]+"/"+data[3];
								 return data1; 
								 });
						Handlebars.registerHelper('paymentamount', function(paymentamount) 
								 { 
							if(paymentamount=="null"){
								return "--";
							}
							return paymentamount;
								 }); 
						Handlebars.registerHelper('millitodate', function(date) 
								 { 
								 var d = new Date(date);
							         var date =d.toDateString();
							         data=date.split(" ");
							         var data1 = data[2]+"/"+data[1]+"/"+data[3];							       
								 return data1; 
								 });
						renderTemplate("#userdetails",userlist,"#userdetaillist");
						$('#userdetaillist').slimscroll({height: "475px"});  
					});
			});
		}	
	};
helper=		{pagination:function(){
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