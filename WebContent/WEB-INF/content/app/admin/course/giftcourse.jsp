<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=request.getContextPath()%>/assets/plugins/datepicker/css/bootstrap-datepicker.min.css"	type="text/css" rel="stylesheet">
</head>
<body>
	<%@ page
		import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
	<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
	<%
		UserDetailsAdaptor userDetailsAdaptor = null;
		userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String email = userDetailsAdaptor.getUser().getProperty("email");
		String courseenrollmentid = request
				.getParameter("courseenrollmentid");
		String courseenrollmentstatus = request
				.getParameter("enrollStatus");
		String orgperson = userDetailsAdaptor.getUser().getProperty(
				"orgpersonid");
	%>
	<div id="alertholder"></div>
	<div class="container-fluid">
		<div id="giftcourseholder" contextpath=<%=request.getContextPath()%>></div>
	</div>
	<s:hidden name="courseid" value="%{courseid}"></s:hidden>
	<s:hidden name="coursetitle" value="%{coursetitle}"></s:hidden>
	<s:hidden name="price" value="%{price}"></s:hidden>
	<s:hidden name="priceid" value="%{priceid}"></s:hidden>
	<s:hidden name="courselogo" value="%{courselogo}"></s:hidden>
	<form name='giftform' method="post">
		<input type="hidden" name="giftcourseid" value=""> <input
			type="hidden" name="orgperson" value="<%=orgperson%>" />
	</form>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/users/giftcourse.js"></script>
		<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/plugins/datepicker/js/bootstrap-datepicker.min.js"></script>
</body>
</html>