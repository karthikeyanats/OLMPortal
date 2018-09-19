$(document).ready(function(){
	$(".paymentrequest").click(function(){
		getPaymentRequestList.paymentRequest();
	});
	$('.submitpayment').click(function(){
		getPaymentRequestList.createPaymentIssue();
	});
});
getPaymentRequestList = {
		paymentRequest:function(){
			$(".royaltysetting").removeClass("active");
			$(".requestlimit").removeClass("active");
			$(".paymentrequest").addClass("active");
			$(".cleardate").removeClass("active");
			$(".issuedhistory").removeClass("active");
			$(".royaltysettingmsg").css('display','none');
			var requestList = courseData.getPaymentRequest();
			 Handlebars.registerHelper('dateofcreate', function(dateofrequest) 
					 { 
					 var d = new Date(dateofrequest);
				         var date =d.toDateString();
				         data=date.split(" ");
				         var data1 = data[2]+"/"+data[1]+"/"+data[3];
				         /*var time = d.toLocaleTimeString();
				         var datetime = data1+" "+time;*/
					 return data1; 
					 });
			renderTemplate("#paymenttmpl", requestList,"#royaltytable");
			$(".previousroyalty").css('display','none');
			$(".nextroyalty").css('display','none');
			$('.requestissuebtn').click(function(){
				$("#addpaymentmodal").modal("show");
				$(".alertmessage").empty();
				$('.requestdetail').empty();
				$('.requestlimitdetail').empty();
				$('.requestlimitdetail').empty();
				var id = $(this).attr('id');
				$(".paymentrequestid").val(id);
				var amount = $(this).attr('minimumamount');
				var requestedby = $(this).attr('requestedby');
				$('.requestdetail').append(requestedby);
				$('.requestlimitdetail').append(amount);
			});
			$(".bankdetail").click(function(){
				var bankdetailidsList={};
				var bankdetailidList=[];
				var bankdetailids = $(this).attr('bankdetailid');
				var bankdetailList = _.findWhere(requestList,{bankdetailid:parseInt(bankdetailids)}); 
				var bankname	=	bankdetailList.bankname;
				var ifscCode	=	bankdetailList.ifscCode;
				var accountnumber	=	bankdetailList.accountnumber;
				var branchname	=	bankdetailList.branchname;
				var location	=	bankdetailList.location;
				var country	=	bankdetailList.country;
				bankdetailidList.bankdetailid=bankdetailList.bankdetailid;
				bankdetailidList.bankname=bankname;
				bankdetailidList.ifscCode=ifscCode;
				bankdetailidList.accountnumber=accountnumber;
				bankdetailidList.branchname=branchname;
				bankdetailidList.location=location;
				bankdetailidList.country=country;
				bankdetailidsList.bankdetailidList=bankdetailidList;
				$("#bankdetailmodal").modal('show');
				renderTemplate("#bankdetailtmplt", bankdetailidsList,"#bankaccountdetails");
			});
		},
		createPaymentIssue:function(){
			var id = $(".paymentrequestid").val();
			var paymentissue = {};
			paymentissue.paymentrequest=id;
			var paymentissueList = courseData.createPaymentIssue(paymentissue);
			if(paymentissueList=="CREATED"){
				courseData.deletePaymentRequest(id);
				$('#addpaymentmodal').modal('hide');
				getPaymentRequestList.paymentRequest();
				$("#alertupdatemessage").modal('show');
				$(".alertmessage").append("Payment Requested is Issued ");
			}
		}
};