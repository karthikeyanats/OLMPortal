$(document).ready(function(){
	$('.pricebtn').each(function(){
		var price=$("[name=planamount]").val();
		var priceicon = $(this).attr('priceicon');
		if(price=="Free"){
			$(this).text("Free");
		}
		else if(price==""){
			$(this).html("");
		}
		else{
				$(this).html("<i class='fa "+priceicon+"'></i> "+price);	
		}
	});
	getPlanidDetails.getPlanList();
	
});
getPlanidDetails={
		getPlanList:function(){
			var self=this; 
			var planidobj={};
			var planid = $("[name=planid]").val();
			planidobj.id = $("[name=planid]").val();
			var planlist=courseData.planiddetails(planidobj);
			self.renderTemplate(planlist,planid);
		},
		renderTemplate:function(planlist,planid){
			var self = this;
			renderTemplate("#subscriptitletempl",planlist,"#subscriptitle");
			$(".paymentab").click(function(){
				self.createplanpayment(planid);
			});
		},
		createplanpayment:function(planid){
			var context=$('#context').text();
				var orgsubcriptionid = $('[name=orgsubscriptionid]').val();
				var priceid=$("[name=planamount]").val();
				var planid=$("[name=planid]").val();
				var durationtype=$("[name=durationtype]").val();
				var duration=$("[name=duration]").val();
				var orgpersonid = $("[name=orgpersonid]").val();
				var time = new Date();
				var orderid = "SP_"+orgsubcriptionid+"_"+time.getTime()+"_"+orgpersonid;
				
				window.location.href=context+"/PaymentGateway/makeplanpayment.jsp?planpaymentid="
				+ orderid
				+ "&orgsubcriptionid="
				+ orgsubcriptionid
				+ "&price="
				+ priceid
				+ "&planid="
				+ planid
				+ "&durationtype="
				+ durationtype
				+ "&duration="
				+ duration;
			}
};