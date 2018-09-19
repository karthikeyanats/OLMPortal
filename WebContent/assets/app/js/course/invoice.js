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
			var invoiceidobj={};
			invoiceidobj.invoiceid=$('[name=invoiceid]').val();
			var invoiceDetailList=courseData.getInvoiceDetails(invoiceidobj);
			renderTemplate("#printtmpl", invoiceDetailList, "#printarea");
			$('.pricebtn').each(function()
					{
				var priceicon = $(this).attr('priceicon');
						$(this).html("("+"<i class='fa "+priceicon+"'></i>"+")");	
			});
		},
		printDiv:function(divName) {
			var self=this;
			var pdf = new jsPDF('l','pt','a4');
			
			pdf.addHTML($('#printarea'),function() {
				//pdf.output('dataurlnewwindow');
				pdf.output('save', 'invoice.pdf');
			});
			setTimeout(function(){
				self.renderSuccessTemplte();										
			},1000);
			
		},
		renderSuccessTemplte:function(){
			var data='';
			renderTemplate("#successTemplate", data, "#printarea");
		}
};