<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Education-Cloud : Index Page</title>
</head>
<body>
<%@ page
		import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
	<%
		UserDetailsAdaptor userDetailsAdaptor = null;
		userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String organizationid = userDetailsAdaptor.getUser().getProperty(
				"organizationid");
		String orglogo = userDetailsAdaptor.getUser().getProperty(
				"orglogo");
		String orgname = userDetailsAdaptor.getUser().getProperty(
				"orgname");
		String username = userDetailsAdaptor.getUser().getProperty(
				"username");
		String password = userDetailsAdaptor.getUser().getProperty(
				"password");
		String targetUrl = userDetailsAdaptor.getUser().getProperty(
				"targetUrl");
	%>
	<div style="margin-left:20px;margin-right: 20px;text-align: center;min-height: 490px;background-color:white;">
		<form action="dashboard" name="organizationview" method="post">
			<div class="organizationview">
				<h3><img class="orglogopath" style="height:165px;width: 165px;margin-top: 50px;border-radius:5px;"></h3>
				<h3 style="font-size: 23px; color: rgb(74, 112, 194);">Congratulations! You have been created the </h3>
				<h3 style="font-size: 23px; color: rgb(74, 112, 194);">organization successfully</h3>
			<div>
				<input type="submit"  style="height: auto;padding:10px;" class="btn btn-flat btn-blue btn-padded btn-big plansubscriptionemail" value="Start Organization">
			</div>
			</div>
				<input type="hidden" name="organizationid" value="<%=organizationid%>"/>
				<input type="hidden" name="organizationname" value="<%=orgname%>"/>
				<input type="hidden" name="orglog" value="<%=orglogo%>">
				<input type="hidden" name="username" value="<%=username%>">
				<input type="hidden" name="password" value="<%=password%>">
				<input type="hidden" name="targetUrl" value="<%=targetUrl%>">
				
				<input type="hidden" name="contextpath" class="contextpath" value="<%=request.getContextPath()%>">
		</form>
	</div>
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/app/js/organization/organizationview.js"></script>
</body>
</html>