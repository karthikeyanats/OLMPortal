$(document).ready(function(){
	purchaseHistory().init();
});

var purchaseHistory=(function(){
	var priceval= $('[name=priceval]').val();
	var model= {
			historyData:function() {
				var list=controller.historyData();
				if(list!="false"){
					return list;
				}
				else{
					return null;
				}
				
			},
			livehistoryData:function() {
				var list=controller.livehistoryData();
				if(list!="false"){
					return list;
				}
				else{
					return null;
				}
				
			},
			gifthistoryData:function() {
				var list=controller.gifthistoryData();
				if(list!="PRECONDITION_FAILED"){
					return list;
				}
				else{
					return null;
				}				
			}			
	};
	
	var controller={
			
			historyData:function() {
				return Ajax.get(courseData.getContextPath()+"/app/getPurchaseHistory",undefined,"json","application/json");
			},
			gifthistoryData:function() {
				return Ajax.ajaxHelper("GET", courseData.getContextPath()+ "/rest/gift/giftcoursehistory", undefined, "json", "application/json");
			},
		    livehistoryData:function(){
		    	return Ajax.ajaxHelper("GET", courseData.getContextPath()+ "/app/getPurchaseHistory/livepurchasehistory", undefined, "json", "application/json");
		    }
	};
	var templates={
			historyTable:function(){ 
				return "<h3 class='marbottom15 size20' >"+
						"<font class='nottitle' ></font>Purchase History</h3>"+
						"<div >"+
						"<table class='table table-striped table-borderd whitebg manageprofilecont'><thead>" +
						"<tr><th>Course</th><th>Amount(<i class='fa "+priceval+"'></i>)</th><th>Payment Date</th><th class='centertext'>" +
						"Order No</th><th>Actions</th></tr></thead><tbody>{{#if .}} {{#each .}}<tr class='paginationtr'>" +
						"<td >{{coursetitle}}</td><td>{{price}}</td><td>{{paymentdate}}</td>" +
						"{{#if orderno}}<td >{{orderno}}</td>{{else}}<td class='centertext'>-</td>{{/if}}<td><a class='btn btn-success printbtn' invoiceid='{{orderno}}' courseenrollmentid='{{courseenrollmentid}}'>Print</a></td></tr>" + 
						"{{/each}}{{else}}<tr><td colspan='5' class='categorydescription'>" +
						"Data Not Found</td></tr>{{/if}}</tbody></table></div><div class='row'>"+
						"<div class='col-md-12'>" +
						"<div class='col-md-9 col-md-push-3 centertext pagination'></div>" +
						"</div></div>";
			},
			livehistoryTable:function(){ 
				return "<h3 class='marbottom15 size20' >"+
						"<font class='nottitle' ></font>LiveSession Purchase History</h3>"+
						"<div >"+
						"<table class='table table-striped table-borderd whitebg  manageprofilecont'><thead>" +
						"<tr><th>Course</th><th>Start Time</th><th>End Time</th><th>Amount(<i class='fa "+priceval+"'></i>)</th><th class='centertext'>" +
						"Order No</th><th>Actions</th></tr></thead><tbody>{{#if .}} {{#each .}}<tr class='paginationtr'>" +
						"<td >{{coursetitle}}</td><td>{{starttime}}</td><td>{{endtime}}</td><td>{{price}}</td>" +
						"{{#if orderno}}<td class='centertext'>{{orderno}}</td>{{else}}<td class='centertext'>-</td>{{/if}}<td><a class='btn btn-success printbtn' invoiceid='{{orderno}}' livesessionenrollmentid='{{livesessionenrollmentid}}'>Print</a></td></tr>" + 
						"{{/each}}{{else}}<tr><td colspan='6' class='categorydescription'>" +
						"Data Not Found</td></tr>{{/if}}</tbody></table></div><div class='row'>"+
						"<div class='col-md-12'>" +
						"<div class='col-md-9 col-md-push-3 centertext pagination'></div>" +
						"</div></div>";
			},
			gifthistoryTable:function(){ 
				return "<h3 class='marbottom15 size20' >"+
						"<font class='nottitle' ></font>Gift Course History</h3>"+
						"<div>"+
						"<table class='table table-striped table-borderd whitebg manageprofilecont'><thead>" +
						"<tr><th>Course</th><th>Recipient Name</th><th>Recipientmail</th><th >" +
						"Message</th><th class='centertext'>Order No</th><th>Send Date</th></tr></thead><tbody>{{#if .}} {{#each .}}<tr class='paginationtr'>" +
						"<td >{{coursetitle}}</td>{{#if recipientname}}<td>{{recipientname}}</td>{{else}}<td>-</td>{{/if}}" +
						"{{#if recipientmail}}<td>{{recipientmail}}</td>{{else}}<td>-</td>{{/if}}" +
						"{{#if message}}<td>{{message}}</td>{{else}}<td>-</td>{{/if}}{{#if orderno}}<td class='centertext'>{{orderno}}</td>{{else}}<td class='centertext'>-</td>{{/if}}<td>{{{senddate senddate}}}</td></tr>" + 
						"{{/each}}{{else}}<tr><td colspan='6'  class='categorydescription'>" +
						"Data Not Found</td></tr>{{/if}}</tbody></table></div><div class='row'>"+
						"<div class='col-md-12'>" +
						"<div class='col-md-9 col-md-push-3 centertext pagination'></div>" +
						"</div></div>";
				},			
 	};	
	var view={
			historyTable:function(){				
				var list=model.historyData();
				helper.render(templates.historyTable(),list,"#purchaseHistoryDiv");
				applypagination($('.paginationtr'),10);
				$('.printbtn').click(function(){
					$('[name=invoiceid]').val($(this).attr('invoiceid'));
					$('[name=courseenrollmentid]').val($(this).attr('courseenrollmentid'));
					$('[name=purchaseHistoryForm]').attr('action','billinginvoice');
					$('[name=purchaseHistoryForm]').submit();
				});
 				
			},
			gifthistoryTable:function(){				
				var list=model.gifthistoryData();
				Handlebars.registerHelper('senddate', function(senddate) 
						 { 
					var d = new Date(parseInt(senddate));
			         var date =d.toLocaleDateString();
		         return date;
						 });
				helper.render(templates.gifthistoryTable(),list,"#purchaseHistoryDiv");
				applypagination($('.paginationtr'),10);
				$(".giftcourse").click(function(){
					view.gifthistoryTable();
				});
				$(".purchasehistory").click(function(){
					view.historyTable();
				});
			},
			livehistoryTable:function(){
				var list=model.livehistoryData();
				helper.render(templates.livehistoryTable(),list,"#purchaseHistoryDiv");
				applypagination($('.paginationtr'),10);
				$('.printbtn').click(function(){
					$('[name=invoiceid]').val($(this).attr('invoiceid'));
					$('[name=livesessionenrollmentid]').val($(this).attr('livesessionenrollmentid'));
					$('[name=purchaseHistoryForm]').attr('action','billinginvoice');
					$('[name=purchaseHistoryForm]').submit();
				});
			}
	};

	var helper={
			render:function(tmpl,list,outputselector){
 				var template = Handlebars.compile(tmpl);
				var templateHtml = template(list);
				$(outputselector).html(templateHtml);
			},
	};

	return{init:function(){
		intialize();
	}};
	function intialize(){
		view.historyTable();
		$(".giftcourselink").click(function(){
			$('.leftsideli').removeClass('active');
			$(this).addClass('active');
			view.gifthistoryTable();
		});
		$(".purchasecourselink").click(function(){
			$('.leftsideli').removeClass('active');
			$(this).addClass('active');
			view.historyTable();
		});
		$('.purchaselivelink').click(function(){
			$('.leftsideli').removeClass('active');
			$(this).addClass('active');
			view.livehistoryTable();
		});
	}	
});		
