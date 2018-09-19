$(document).ready(function(){
	$('.pricebtn').each(function(){
		var price=$("[name=price]").val();
		var priceicon = $(this).attr('priceicon');
		if(price=="Free"){
			$(this).text("Free");
		}
		else if(price==""){
			$(this).html("");
		}
		else{
				$(this).html("<i class='fa "+priceicon+"'></i>"+price);	
		}
	});
	$(".paymentab").click(function(){
		getPaymentDetails.paymentDetails();
	});
});

getPaymentDetails={
		paymentDetails:function(){
			var context=$('#context').text();
				var presenterappdetailsid =$("[name=presenterappdetailsid]").val();
				var priceid=$("[name=price]").val();
				
				window.location.href=context+"/PaymentGateway/makelicensepayment.jsp?price="
				+ priceid
				+ "&presenterappdetailsid="
				+ presenterappdetailsid;
		}
};
