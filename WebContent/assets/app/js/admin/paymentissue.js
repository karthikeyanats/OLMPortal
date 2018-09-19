$(document).ready(function(){
	$(".cleardate").click(function(){
		getPaymentIssueList.paymentIssue();
	});
});
getPaymentIssueList = {
		paymentIssue:function(){
			$(".royaltysetting").removeClass("active");
			$(".requestlimit").removeClass("active");
			$(".paymentrequest").removeClass("active");
			$(".cleardate").addClass("active");
			$(".issuedhistory").removeClass("active");
			$(".royaltysettingmsg").css('display','none');
			var issueList = courseData.getPaymentIssue();
			 Handlebars.registerHelper('dateofcreate', function(dateofissue) 
					 { 
					 var d = new Date(dateofissue);
				         var date =d.toDateString();
				         data=date.split(" ");
				         var data1 = data[2]+"/"+data[1]+"/"+data[3];
				         /*var time = d.toLocaleTimeString();
				         var datetime = data1+" "+time;*/
					 return data1; 
					 });
			 renderTemplate("#paymentdatecleartmpl", issueList,"#royaltytable");
			 helper.pagination();
			 $(".previousroyalty").css('display','none');
				$(".nextroyalty").css('display','none');
		},
};