<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@page language="java" import="java.util.ResourceBundle"%>
<title></title> 
<script type="text/javascript">
function completePayment(){
	
 	document.paymentform.submit();
}
</script>
</head>  
<%@include file="./payment/libFunctions.jsp"%>     	
<body onload="completePayment()"> 
<form method="post" action="https://www.ccavenue.com/shopzone/cc_details.jsp" name="paymentform">      
	<%
		String presenterappdetailsid	=	request.getParameter("presenterappdetailsid");
 		String Merchant_Id 				= "M_mut22273_22273"; //This id(also User_Id)  available at "Generate Working Key" of "Settings & Options"
 		String Order_Id 				= null;  //your script should substitute the order description here in the quotes provided here
		String Redirect_Url 			= "";
 		String Amount="1";/* (String)request.getParameter("price"); */
  		//String courseid = request.getParameter("courseid");
   		//String registerid = "raja";    
		String ddnumber=null;   
		
 		/* out.println("<script>opener.parent.window.close();</script>"); */
		
		if(request.getProtocol().indexOf("HTTPS") != -1)
			Redirect_Url				= "https://";  
		else
			Redirect_Url				= "http://";
		
		if(request.getServerPort() == 80) 
			Redirect_Url				+= request.getServerName()+""+request.getContextPath()+"/PaymentGateway/licensecheckout.jsp?presenterappdetailsid="+presenterappdetailsid+"&price="+Amount;

		else
			Redirect_Url				+= request.getServerName()+":"+request.getServerPort()+""+request.getContextPath()+"/PaymentGateway/licensecheckout.jsp?presenterappdetailsid="+presenterappdetailsid+"&price="+Amount;
		
 		//your redirect URL where your customer will be redirected after authorisation from CCAvenue 
		//String Redirect_Url = "http://"+bundle1.getString("label.paymentgateway.ipaddress")+":"+bundle1.getString("label.paymentgateway.port")+request.getContextPath()+"/payment/onlinefeescollectionsubmit.jsp?totalamount="+Amount+"&expertid="+Order_Id;
		String WorkingKey 				= "hhlgn2t4infk24njfy";   //put in the 32 bit alphanumeric key in the quotes provided here.Please note that get this key login to your CCAvenue merchant account and visit the "Generate Working Key" section at the "Settings & Options" page. 
 		
		String billing_cust_name		= "";
		String billing_cust_address		= "";
		String billing_cust_country		= "";
		String billing_cust_tel			= "";
		String billing_cust_email		= "";
		String billing_cust_state		= "";
		String delivery_cust_name		= "";
		String delivery_cust_address	= "";
		String delivery_cust_country	= "";
	    String delivery_cust_state		= "";
		String delivery_cust_tel		= "";
		String delivery_cust_notes		= "";
		String Merchant_Param			= "";       
		String billing_city 			= "";
		String billing_zip 				= "";
		String delivery_city 			= "";
		String delivery_zip 			= "";  
		String instituteid				= "";
 		String ipaddress				= request.getRemoteHost();  
		Order_Id 						= request.getParameter("presenterappdetailsid");
		String Checksum 				= getChecksum(Merchant_Id, Order_Id, Amount, Redirect_Url, WorkingKey); 
  
		%>    
    		
		<input type="hidden" name="Merchant_Id" 			value="<%=Merchant_Id%>"> 
		<input type="hidden" name="Amount" 					value="<%=Amount%>">
 		<input type="hidden" name="Order_Id"  id="orderID"  value="<%=Order_Id%>">
 		<input type="hidden" name="Redirect_Url" 			value="<%=Redirect_Url%>">
		<input type="hidden" name="Checksum" 				value="<%=Checksum%>">
		<input type="hidden" name="billing_cust_name" 		value="<%=billing_cust_name%>"> 
		<input type="hidden" name="billing_cust_address" 	value="<%=billing_cust_address%>"> 
		<input type="hidden" name="billing_cust_country" 	value="<%=billing_cust_country%>"> 
		<input type="hidden" name="billing_cust_state" 		value="<%=billing_cust_state%>">
		<input type="hidden" name="billing_cust_tel" 		value="<%=billing_cust_tel%>"> 
		<input type="hidden" name="billing_cust_email" 		value="<%=billing_cust_email%>"> 
		<input type="hidden" name="delivery_cust_name" 		value="<%=delivery_cust_name%>"> 
		<input type="hidden" name="delivery_cust_address" 	value="<%=delivery_cust_address%>"> 
		<input type="hidden" name="delivery_cust_country" 	value="<%=delivery_cust_country%>">
		<input type="hidden" name="delivery_cust_state" 	value="<%=delivery_cust_state%>">
		<input type="hidden" name="delivery_cust_tel" 		value="<%=delivery_cust_tel%>"> 
		<input type="hidden" name="delivery_cust_notes" 	value="<%=delivery_cust_notes%>"> 
		<input type="hidden" name="Merchant_Param" 			value="<%=Merchant_Param%>"> 
		<input type="hidden" name="billing_cust_city" 		value="<%=billing_city%>"> 
		<input type="hidden" name="billing_zip_code" 		value="<%=billing_zip%>"> 
		<input type="hidden" name="delivery_cust_city" 		value="<%=delivery_city%>"> 
		<input type="hidden" name="delivery_zip_code" 		value="<%=delivery_zip%>">
</form>
</body> 
</html>