<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page
		import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>online payement</title> 
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/lib/js/jquery.js"></script>
 
<style type="text/css">

.tablelight{font-family:"Arial",Times,arial;font-size:10pt;font-style:normal;}
.tablebold{font-family:"Arial",Arial,arial;font-size:10pt;font-weight:bold;}
.tableheading{font-family:"Arial",Arial,arial;font-size:10pt;font-weight:bold;background-color: #60c8f2;} 
.table.mystyle
{
	border-width: 0 0 1px 1px;
	border-spacing: 0;
	border-collapse: collapse;
	border-style: solid;
}

.mystyle td,.mystyle th
{
	margin: 0;
	padding: 4px;
	border-width: 0 0 0 0 ;
	border-style: solid;	
}
</style>

<script type="text/javascript">
 
</script>
</head>
<body  topmargin=0 bottommargin=0 rightmargin=0 leftmargin=0>
<div style="height: 150px;"></div>
<%@ page
		import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
	<%
		UserDetailsAdaptor userDetailsAdaptor = null;
			userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			String firstname = userDetailsAdaptor.getUser().getProperty(
					"firstname");
			String email = userDetailsAdaptor.getUser().getProperty(
					"email");
	%>

<%@include file="./payment/libFunctions.jsp"%>
 
<form action="" name="completedform" method="post">
  	
	<div class="container" style="margin-top:15px;">
		<h4 class="color-green pull-left">
			Welcome,<%=firstname%></h4> 
 	</div>			
			
<% 
String OrderId					= "";
String presenterappdetailsid					= "";
try
{	
	  
 	String WorkingKey 				= "hhlgn2t4infk24njfy" ; //put in the 32 bit working key in the quotes provided here 
	
	OrderId					= request.getParameter("Order_Id").trim();
	presenterappdetailsid					= request.getParameter("presenterappdetailsid").trim();
	String Amount					= request.getParameter("Amount").trim();
	String MerchantId				= request.getParameter("Merchant_Id").trim();
	String AuthDesc					= request.getParameter("AuthDesc").trim();////
	String Checksum					= request.getParameter("Checksum").trim();////
	
	Checksum 						= verifyChecksum(MerchantId, OrderId , Amount,AuthDesc,WorkingKey,Checksum);////
	
	String billing_cust_name		= request.getParameter("billing_cust_name");
	String billing_cust_address		= request.getParameter("billing_cust_address");
	String billing_cust_country		= request.getParameter("billing_cust_country");
	String billing_cust_tel			= request.getParameter("billing_cust_tel");
	String billing_cust_email		= request.getParameter("billing_cust_email");
	String delivery_cust_name		= request.getParameter("delivery_cust_name");
	String delivery_cust_address	= request.getParameter("delivery_cust_address");
	String delivery_cust_tel		= request.getParameter("delivery_cust_tel");
	String delivery_cust_notes		= request.getParameter("delivery_cust_notes"); 
	String Merchant_Param			= request.getParameter("Merchant_Param");
	String ipaddress  				= request.getRemoteHost();
	String personid 				= userDetailsAdaptor.getUser().getProperty("orgpersonid");
	 
   	if(Checksum.equals("true") && AuthDesc.equals("Y"))
	{
		//out.println("<br>Thank you for Registering with us. Your credit card has been charged and your transaction is successful. We will be able to write you exam soon.");
		//out.println("<br><a href='"+request.getContextPath()+"/jsp/BrowsingCenter/loadOnlineExamList.jsp'>Click here to go Home Page</a>");		
		//Here you need to put in the routines for a successful 
		//transaction such as sending an email to customer,
		//setting database status, informing logistics etc etc
   		presenterappdetailsid = request.getParameter("presenterappdetailsid").toString(); 
  	 %>
	 
		<input type="hidden" name="presenterappdetailsid" class="presenterappdetailsid" value="<%=presenterappdetailsid%>"/>
		<input type="hidden" name="emailaddress" class="emailaddress" value="<%=email%>"/>
  	<table class="CSSTableGenerator container" border="1" cellpadding="6" cellspacing="0" width="70%" height="100%" style="margin-top:15px;">
		<tr class="tableheading" height="25">
			<td colspan="2" align="left">Payment&nbsp;Details</td>
		</tr>
     		<tr>
          		<td class="tablebold" colspan="2"  align="left"><font color="green">Payment Successful!</font></td>
        	</tr>
     		<tr>
          		<td class="tablelight" colspan="2" align="left">Your transaction has been successfully processed, wait for 5 seconds.
           		<br> 
				<%      
				response.setHeader("Refresh", "5; URL=../app/downloadpresenter");         
          		%>            		
          		</br>   
          		</td>
        	</tr> 
     </table>
     <script type="text/javascript">
     $(document).ready(function(){
     	var presenterappdetail={};
		presenterappdetail.presenterAppdetailsId=$(".presenterappdetailsid").val();
		var presenterappdetailslist=Ajax.ajaxHelper("POST", "<%=request.getContextPath()%>/rest/downloadhistory",presenterappdetail,"json","application/json");
		
		var license = {};
		license.email= $(".emailaddress").val(); 
		var emaillist = ajaxhelperwithdata("licensepresenteremail",license);
     });
 	 </script>
 	 <%	
	}
	else if(Checksum.equals("true") && AuthDesc.equals("B"))
	{
		
 	 %>
 	 <input type="hidden" name="presenterappdetailsid" class="presenterappdetailsid" value="<%=presenterappdetailsid%>"/>
 	 
		<table border="1" cellpadding="4" cellspacing="0" width="95%">
			<tr class="tableheading" height="25">
				<td colspan="2"  align="left">Payment&nbsp;Details</td>
			</tr>
      		<tr>
           		<td class="tablebold" colspan="2"  align="left">Payment Pending!</td>
         	</tr>
      		<tr>
           		<td class="tablelight" colspan="2"  align="left">We will keep you posted regarding the status of your status through e-mail.</td>
         	</tr>
	     </table>		 
	   <script type="text/javascript">
 	 
		var presenterappdetail={};
		presenterappdetail.presenterAppdetailsId=$(".presenterappdetailsid").val();
		var presenterappdetailslist=Ajax.ajaxHelper("POST", "<%=request.getContextPath()%>/rest/downloadhistory",presenterappdetail,"json","application/json");

			
 	 </script>
	 <%	
	}
	else if(Checksum.equals("true") && AuthDesc.equals("N"))
	{
		//out.println("<br>Thank you for Registering with us.However,the transaction has been declined.");
		//out.println("<br><a href='"+request.getContextPath()+"/jsp/BrowsingCenter/loadOnlineExamList.jsp'>Click here to go Home Page</a>");
		//Here you need to put in the routines for a failed
		//transaction such as sending an email to customer
		//setting database status etc etc 
		
	 %> 
	 <input type="hidden" name="presenterappdetailsid" class="presenterappdetailsid" value="<%=presenterappdetailsid%>"/>
		<table border="1" cellpadding="4" cellspacing="0" width="95%">
			<tr class="tableheading" height="25">
				<td colspan="2" align="left">Payment&nbsp;Details</td>
			</tr>
      		<tr>
           		<td class="tablebold" colspan="2" align="left">Payment Failed!</td>
         	</tr>
      		<tr>
           		<td class="tablelight" colspan="2" align="left">The transaction has been declined. Please try after some time.</td>
         	</tr>
	     </table>		 
	  <script type="text/javascript">
   	var presenterappdetail={};
	presenterappdetail.presenterAppdetailsId=$(".presenterappdetailsid").val();
	var presenterappdetailslist=Ajax.ajaxHelper("POST", "<%=request.getContextPath()%>/rest/downloadhistory",presenterappdetail,"json","application/json");		

 	 </script>

	 <%		
	}
	else
	{
	 
	%>
	
	<input type="hidden" name="presenterappdetailsid" class="presenterappdetailsid" value="<%=presenterappdetailsid%>"/>
	<table border="1" cellpadding="4" cellspacing="0" width="95%">
		<tr class="tableheading" height="25">
			<td colspan="2" align="left">Payment&nbsp;Details</td>
		</tr>
     		<tr>
          		<td class="tablebold" colspan="2" align="left">Security Error. Illegal access detected!</td>
        	</tr>
     </table>
     <script type="text/javascript">
  	var presenterappdetail={};
	presenterappdetail.presenterAppdetailsId=$(".presenterappdetailsid").val();
	var presenterappdetailslist=Ajax.ajaxHelper("POST", "<%=request.getContextPath()%>/rest/downloadhistory",presenterappdetail,"json","application/json");
 	 </script>		
   <%
 	}
	}catch(Exception e){e.printStackTrace();}
%>
 
 				<input type="hidden" name="receiptid" value="<%=OrderId%>"/>				
			</div>

			
			</center>			
			
		</td>
	</tr>
</table>

<table>
	<tr height="120">
		<td>
			&nbsp;
		</td> 
	</tr>
</table> 
 
</form>					
</body>
</html>	
