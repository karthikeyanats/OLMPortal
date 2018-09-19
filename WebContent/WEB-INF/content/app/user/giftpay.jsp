<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body>
<%
String giftcourseid= request.getParameter("giftcourseid");
%>
<div class="paymentpage">
<div id="giftcourseholder" contextpath=<%=request.getContextPath()%>></div>
		<div id="giftcoursedetails"></div>
	</div>
<input type="hidden" value="<%=giftcourseid%>" name="giftcourseid">
<script type="text/javascript" src="<%=request.getContextPath()%>/assets/app/js/users/giftpay.js"></script>
</body>
</html>