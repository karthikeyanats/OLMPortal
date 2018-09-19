<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>   
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

<form name="purchaseHistoryForm" method="post">
<input type="hidden" name="invoiceid">
<input type="hidden" name="courseenrollmentid">
<input type="hidden" name="livesessionenrollmentid">

<div class="container-fluid ">
	<div class="row titlecontainer">
		<div class="col-md-12 lefttext">
			<a>Purchase History</a>
		</div>
	</div>
</div>
<div class="container-fluid">
		<div class="row">
			<div class="col-md-12 pad0">
				<div class="col-md-3 pad0 martop10">
					<ul class="pad0">
						<li class="leftsideliheader">
							<h3 >Reports</h3>
						</li>
						<li class="leftsideli hover purchasecourselink active">Course Purchase History<span
							class="count learningcoursescouunt"></span></li>
						<li class="leftsideli hover purchaselivelink">LiveSession Purchase History<span
							class="count learningcoursescouunt"></span></li>
						<li class="leftsideli hover giftcourselink">Gift Course History<span
							class="count learningcoursescouunt"></span></li>
					</ul>
				</div>
				<div class="col-md-9" id="purchaseHistoryDiv">
				
				</div>
			</div>
		</div>
</div>	
<input type="hidden" name="priceval" value='<s:text name="label.price" ></s:text>'>
<%-- <font class="pricebtn" courseid="{{courseid}}" priceid="{{priceid}}" price="{{price}}" priceicon ="<s:text name="label.price"></s:text>"></font></h3>	 	 --%>
 
</form>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/users/purchasehistory.js"></script>
</body>
</html>


