var payments = payments || {};

payments.analytics = (function(){
	var priceiconval = $('.priceval').attr('art');
	var services = {
			todayPayments:function(type){
				return Ajax.get(courseData.getContextPath()+"/rest/paymentanalytics/current/"+type,undefined,"json","application/json");	
			},
			rangepayments : function(from, to){
				return Ajax.get(courseData.getContextPath()+"/rest/paymentanalytics/range/"+from+"/"+to,undefined,"json","application/json");
			}
	};
	
	var controller = {
			todayPayments:function(){
				var type = $('.paymentheader').val();
				var thislist = services.todayPayments(type); 
				return thislist = _.isObject(thislist) ? thislist : null;	
			},
			rangepayments : function(){
				var s3 = $('[name=datefilter]').val();
				var s4= s3.split("-");
				var fromdate = s4[2].trim()+"-"+s4[1]+"-"+s4[0].trim();
				var todate = s4[5]+"-"+s4[4].trim()+"-"+s4[3].trim();
				var thislist = services.rangepayments(fromdate, todate);
				return thislist = _.isObject(thislist) ? thislist : null;
			}
	};
	
	var templates = {
			paymentHeader : function(){
				return "<div class='col-md-12 pad0 padright0'>" +
							"<div class='col-md-4 pad0'>" +
							"<h3 class='padleft0 marleft0 martop10 " +
							"marbottom15 size20'>Payments</h3>" +
							"</div>" +
							"<div class='col-md-4 pad0'>" +
							"<input type='text' class='form-control' name='datefilter' value='' placeholder='Select Date Range' />" +
							"</div>" +
							"<div class='col-md-2 pad0'>" +
							"</div>" +
							"<div class='col-md-2 pad0'>" +
							"<select class='paymentheader form-control pull-right'>" +
							"<option value='day'>Today</option>" +
							"<option value='week'>Current Week</option>" +
							"<option value='month'>Current Month</option>" +
							"<option value='year'>Current Year</option>" +
							"</select>" +
							"</div>" +
						"</div>" +
						"<div class='col-md-12 pad0 padright0' id='paymentHolder'></div>";
			},
			paymentList : function(){
				return "" +
				"<table class='table table-bordered table-striped whitebg'>" +
				"<thead>" +
				"<tr>" +
				"<th>Sl. No</th>" +
				"<th>Course Title</th>" +
				"<th>Price(<span class='pricespan'></span>)</th>" +
				"<th>Paid On</th>" +
				"<th>Author Name</th>" +
				"<th>Subscriber Name</th>" +
				"</tr>" +
				"</thead>" +
				"<tbody>{{#if .}}{{#each .}}" +
				"<tr class='rowcal'>" +
				"<td>{{math @index '+'1}}</td>" +
				"<td>{{coursetitle}}</td>" +
				"<td>{{price}}</td>" +
				"<td>{{df paymentdate}}</td>" +
				"<td>{{authorfirstname}} {{authorlastname}}</td>" +
				"<td>{{userfirstname}} {{userlastname}}</td>" +
				"</tr>" +
				"{{/each}}{{else}}" +
				"<tr>" +
				"<td colspan='6' class='centertext'>No Data Found</td>" +
				"</tr>{{/if}}" +
				"</tbody>" +
				"</table>" +
				"<div class='row'>" +
				"<div class='col-md-12'>" +
				"<div class='col-md-9 col-md-push-3 centertext pagination'></div>" +
				"</div></div>";
			},
	};
	
	var view = {
			paymentsHeader:function(){
				helper.render(templates.paymentHeader(),null,"#userdetails");
				view.currentPayments();
				$('input[name="datefilter"]').daterangepicker({
					locale: {
					      format: 'DD-MM-YYYY'
					    },
					    autoUpdateInput: false,
				});
				
				$('input[name="datefilter"]').on('apply.daterangepicker', function(ev, picker) {
				      $(this).val(picker.startDate.format('DD-MM-YYYY') + ' - ' + picker.endDate.format('DD-MM-YYYY'));
				      view.rangePayments();	
				  });

				  $('input[name="datefilter"]').on('cancel.daterangepicker', function(ev, picker) {
				      $(this).val('');
				  });
				
				
				$('.paymentheader').change(function(){
					$('input[name="datefilter"]').val('');
					view.currentPayments();
				});
			},
			currentPayments : function(){
				var thislist = controller.todayPayments();
				helper.render(templates.paymentList(),thislist,"#paymentHolder");
				$('.pricespan').html("<i class='fa "+priceiconval+"'></i>");
				applypagination($('.rowcal'),10);
			},
			rangePayments : function(){
				if($('input[name="datefilter"]').val().trim().length!=0){
					var thislist = controller.rangepayments();
					helper.render(templates.paymentList(),thislist,"#paymentHolder");
					$('.pricespan').html("<i class='fa "+priceiconval+"'></i>");
					applypagination($('.rowcal'),10);	
				}				
			}
	};
	
	var helper = {
			render : function(tmpl,list,outputselector){
				var template = Handlebars.compile(tmpl);
				var templateHtml = template(list);
				$(outputselector).html(templateHtml);
			}
	};

	return {
		init:function(){
			Handlebars.registerHelper('df', function(dateofissue) { 
				var d = new Date(dateofissue);
				var date =d.toDateString();
				var time = d.toLocaleTimeString();
				data=date.split(" ");
				return data[2]+"-"+data[1]+"-"+data[3]+" "+time;
			});
			initialize();	
		}
	};	

	function initialize(){
		view.paymentsHeader();
	}

});