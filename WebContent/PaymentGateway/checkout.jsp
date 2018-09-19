<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@page language="java" import="java.util.ResourceBundle"%>
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
			String email=userDetailsAdaptor.getUser().getProperty("email");
	%>
<%@include file="./payment/libFunctions.jsp"%>
 <form id="livesession" method="post" action="">
<input type="hidden" name="status">
<input type="hidden" name="livesessionenrollmentid" value="">
</form>
<form action="" name="completedform" method="post">
  	
	<div class="container" style="margin-top:15px;">
		<h4 class="color-green pull-left">
			Welcome,<%=firstname%></h4> 
 	</div>			
			
<% 
String OrderId					= "";
try
{
 	String WorkingKey 				= "hhlgn2t4infk24njfy" ; //put in the 32 bit working key in the quotes provided here 
	
	OrderId					= request.getParameter("Order_Id").trim();
	String Amount					= request.getParameter("Amount").trim();
	String courseids = request.getParameter("courseid").toString(); 
	String priceid = request.getParameter("priceid").toString();
	String livesessionid =  request.getParameter("livesessionid").toString();
	String type = request.getParameter("type").toString();
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
   	if(Checksum.equals("true") && AuthDesc.equals("Y"))
	{
		//out.println("<br>Thank you for Registering with us. Your credit card has been charged and your transaction is successful. We will be able to write you exam soon.");
		//out.println("<br><a href='"+request.getContextPath()+"/jsp/BrowsingCenter/loadOnlineExamList.jsp'>Click here to go Home Page</a>");		
		//Here you need to put in the routines for a successful 
		//transaction such as sending an email to customer,
		//setting database status, informing logistics etc etc
   		 
  	 %>
	
 <div>
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
				if(type.equals("1")){
      			  response.setHeader("Refresh", "5; URL=../app/mycourses");         
          		%>            		
          		<%
				}
          		%>
          		<%  
				if(type.equals("2")){
      			  //response.setHeader("Refresh", "5; URL=../app/livesession?livesessionid="+livesessionid);         
          		%>            		
          		<%
				}
          		%>
          		</br>   
          		</td> 
        	</tr> 
     </table>
 	 </div> 
	  <script>
	  if(<%=type%>=="1"){
		
		 var courseidobj={};
		 courseidobj.courseid=<%=courseids%>;
		 var result=courseData.getCourseenrollchecked(courseidobj);
		 if(result.length>0){
			 
		 }
		 else{
			 var onlinepaymentobj={};
				onlinepaymentobj.courseid=<%=courseids%>;
				onlinepaymentobj.priceid=<%=priceid%>
				onlinepaymentobj.priceamount=<%=Amount%>; 
				onlinepaymentobj.courseenrollmentstatus="A";
				onlinepaymentobj.orderno="<%=OrderId%>";
				onlinepaymentobj.paymenttype="Online";
				onlinepaymentobj.paymentstatus="A";
				enrolledvalue=courseData.getCourseEnrolledValue(onlinepaymentobj);
				
				var enrollidobj={};
				enrollidobj.courseenrollmentid=enrolledvalue;
				courseData.createRoyaltyPayment(enrollidobj,<%=type%>);
		 }
	  }
	  else if(<%=type%>=="2"){
 		 
		 	obj={};
			obj.livesessionid=<%=livesessionid%>;
			var enrolldate = courseData.getlivesessionschedulelist(obj);
			
			if(enrolldate!=undefined && enrolldate!="false"){
			}
			else{
				var livesessionidobj ={}; 
				 livesessionidobj.livesessionid=<%=livesessionid%>;
				 livesessionidobj.orderno="<%=OrderId%>";
				 livesessionidobj.paymenttype="Online";
				 livesessionenrollmentid = courseData.enrolllivesession(livesessionidobj);
				  
				 livesessionenrollidobj={};
				 livesessionenrollidobj.livesessionenrollmentid=livesessionenrollmentid; 
				 var paymentlivesession = courseData.paymentlivesession(livesessionenrollidobj);
				 
				 id={};
				 id.livesessionenrollid=<%=livesessionid%>;
				 id.email="<%=email%>";
				 var data=courseData.enrolllivesessionemail(id);
				 $('[name=livesessionenrollmentid]').val(livesessionenrollmentid);
				 $("[name=status]").val("sucess");
		    	 $("#livesession").attr('action',courseData.getContextPath()+'/app/enrollpayment');
				 $("#livesession").submit();
			}
	  }
 	 </script>
	 <%	
	}
	else if(Checksum.equals("true") && AuthDesc.equals("B"))
	{
		
		 
	 %>
	  
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
	     <script>
	  var onlinepaymentobj={};
		onlinepaymentobj.courseid=<%=courseids%>;
		onlinepaymentobj.priceid=<%=priceid%>
		onlinepaymentobj.priceamount=<%=Amount%>; 
		onlinepaymentobj.courseenrollmentstatus="B";
		onlinepaymentobj.Orderid=<%=OrderId%>;
		enrolledvalue=courseData.getCourseEnrolledValue(onlinepaymentobj);
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
	 <script>
	  var onlinepaymentobj={};
		onlinepaymentobj.courseid=<%=courseids%>;
		onlinepaymentobj.priceid=<%=priceid%>
		onlinepaymentobj.priceamount=<%=Amount%>; 
		onlinepaymentobj.courseenrollmentstatus="F";
		enrolledvalue=courseData.getCourseEnrolledValue(onlinepaymentobj);
 	 </script>

	 <%		
	}
	else
	{
	 
	%>
	<table border="1" cellpadding="4" cellspacing="0" width="95%">
		<tr class="tableheading" height="25">
			<td colspan="2" align="left">Payment&nbsp;Details</td>
		</tr>
     		<tr>
          		<td class="tablebold" colspan="2" align="left">Security Error. Illegal access detected!</td>
        	</tr>
     </table>
     <script>
	var onlinepaymentobj={};
	onlinepaymentobj.courseid=<%=courseids%>;
	onlinepaymentobj.priceid=<%=priceid%>
	onlinepaymentobj.priceamount=<%=Amount%>; 
	onlinepaymentobj.courseenrollmentstatus="E";
	enrolledvalue=courseData.getCourseEnrolledValue(onlinepaymentobj);
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
