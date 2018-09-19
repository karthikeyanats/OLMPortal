$(document).ready(function(){
	invoiceObj.loadInvoice();
	$('.printinvoicebtn').show();
	$('.printinvoicebtn').click(function(){
		$(this).hide();
		invoiceObj.printDiv();
	});
});

invoiceObj={
		loadInvoice:function(){
			if($('[name=courseenrollmentid]').val()!=""){
				var invoiceidobj={};
				invoiceidobj.invoiceid=$('[name=courseenrollmentid]').val();
				var invoiceDetailList=courseData.getInvoiceDetails(invoiceidobj);
				renderTemplate("#printtmpl", invoiceDetailList, "#printarea");
				$('.pricebtn').each(function()
						{
					var priceicon = $(this).attr('priceicon');
							$(this).html("("+"<i class='fa "+priceicon+"'></i>"+")");	
				});	
			}
			else if($('[name=livesessionenrollmentid]').val()!=""){
				var invoiceidobj={};
				invoiceidobj.invoiceid=$('[name=livesessionenrollmentid]').val();
				var invoiceDetailList=courseData.getLivesessionInvoiceDetails(invoiceidobj);
				renderTemplate("#livesessionprinttmpl", invoiceDetailList, "#printarea");
				$('.pricebtn').each(function()
						{
					var priceicon = $(this).attr('priceicon');
							$(this).html("("+"<i class='fa "+priceicon+"'></i>"+")");	
				});	
			}
		},
		printDiv:function(divName) {
			if($('[name=courseenrollmentid]').val()!=""){
				var pdf = new jsPDF('l','pt','a4');
				pdf.addHTML($('#invoicearea'),function() {
					var courseenrollmentid = $(".courseenrollmentid").text();
					pdf.output("save", "InvoiceReceipt"+courseenrollmentid+".pdf");
				});	
			}
			else if($('[name=livesessionenrollmentid]').val()!=""){
				var pdf = new jsPDF('l','pt','a4');
				pdf.addHTML($('#invoicearea'),function() {
					var livesessionenrollmentid = $(".livesessionenrollmentid").text();
					pdf.output("save", "InvoiceReceipt"+livesessionenrollmentid+".pdf");
				});	
			}

			
		}
};