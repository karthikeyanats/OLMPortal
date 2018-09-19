<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Signed Up Login page </title>
<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/lib/js/jquery.js"></script>
<script type="text/javascript">
window.onload = function () {
	$('[name=signinform]').submit();	
};
</script>
</head>
<body>
	<form action="j_spring_security_check" method="POST" name="signinform">
		<input name="j_username" type="hidden" value="<%=request.getParameter("newjusername")%>"> 
		<input name="j_password" type="hidden" value="<%=request.getParameter("newjpassword")%>"> 
		<input type="hidden" name="targetUrl" value="<%=request.getParameter("newtargeturl")%>"> 
		<input type="hidden" name="organizationid" value="<%=request.getParameter("neworganid")%>">
		<input type="hidden" name="courseid" value="<%=request.getParameter("courseid")%>">
		<input type="hidden" name="personid" value="<%=request.getParameter("personid")%>">
		<input type="hidden" name="orgpersonid" value="<%=request.getParameter("orgpersonid")%>">
		<input type="hidden" name="coursetitle" value="<%=request.getParameter("coursetitle")%>">
		<input type="hidden" name="price" value="<%=request.getParameter("price")%>">
		<input type="hidden" name="priceid" value="<%=request.getParameter("priceid")%>">
		<input type="hidden" name="courseinvitationid" value="<%=request.getParameter("courseinvitationid")%>">
	</form>
</body>
</html>