$(document).ready(function(){
	giftpay().init();
});

var giftpay=(function(){
	var contextpath=$('#giftcourseholder').attr('contextpath');
	var objects={
			/*giftcourse:function(){
						giftcourseid:$('[name=giftcourseid]').val();
			},*/
	};
	var controller={
					giftcourselist:function(obj){
						return Ajax.ajaxHelper("GET", contextpath+ "/rest/gift/"+obj+"",undefined, "json", "application/json");
					},
	};
	var model={
			giftcourse:function(){
				return controller.giftcourselist($('[name=giftcourseid]').val());
			},
	};
	
	var templates={
			giftcoursetemplate:function(){
				return	"{{#each .}}<h2>{{coursetitle}}</h2>"+
						"<div><img src='"+contextpath+"/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo' style='height:150px;'></div><div style='margin-top: 15px;'>"+
						"<font style='font-size: 18px;'>Price :</font>"+
						"<font style='font-size: 18px;margin-left:50px;'>{{price}}</font></div>" +
						"<div style='margin-top:15px;'><form name='freecourseform'><a class='btn btn-flat btn-blue onlinepaymentbtn' style='margin-right:25px;'>Proceed Pay</a></form></div>{{/each}}";
			} ,
	};
	var helper={
			render:function(tmpl,list,outputselector){
				var template = Handlebars.compile(tmpl);
				var templateHtml = template(list);
				$(outputselector).html(templateHtml);
			},
	};
	var view={
			giftcourseview:function(){
				var giftcourseList = model.giftcourse();
				var giftcourseids	=	$('[name=giftcourseid]').val();
				var giftcourseidslist	=	_.findWhere(giftcourseList,{giftcourseid:parseInt(giftcourseids)});
				var priceid	=giftcourseidslist.price;
				var priceids	=giftcourseidslist.priceid;
				
				helper.render(templates.giftcoursetemplate(),giftcourseList,"#giftcoursedetails");
				$('.paymentab').click(function(){
					$('.paymentab').removeClass('btn-blue').addClass('btn-gray');
					$(this).addClass('btn-blue').removeClass('btn-gray');
				});
				$(".onlinepaymentbtn").click(function(){
					window.location.href=contextpath+"/PaymentGateway/makegiftcoursepayment.jsp?giftcourseid="
					+ giftcourseids
					+ "&price="
					+ priceid
					+ "&priceid="
					+ priceids;
				});
			},
	};
	
	return{
		init:function(){
			initialize();
		}
	};
	function initialize(){
		view.giftcourseview();
	}
});
