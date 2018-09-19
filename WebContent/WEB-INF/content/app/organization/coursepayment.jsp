<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script id="subscriptitletempl" type="text/x-handlebars-template">
{{#each .}}
<font>Subscription Plan - {{name}} / {{durationtype}}</font>
{{/each}}
</script>
</head>
<body>
	<%@ page
		import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
	<%
		UserDetailsAdaptor userDetailsAdaptor = null;
		userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String orgpersonid = userDetailsAdaptor.getUser().getProperty(
				"orgpersonid");
	%>
	<div class="paymentpage container-fluid whitebg centertext boxshadow padtop50 min450">
		<form action="" name="coursepayment">
			<span id="context" style="display: none;"><%=request.getContextPath()%></span>
			<s:hidden name="planid" value="%{planid}"></s:hidden>
			<s:hidden name="planamount" value="%{planamount}"></s:hidden>
			<s:hidden name="orgsubscriptionid" value="%{orgsubscriptionid}"></s:hidden>
			<s:hidden name="durationtype" value="%{durationtype}"></s:hidden>
			<s:hidden name="duration" value="%{duration}"></s:hidden>
			<input name="orgpersonid" value="<%=orgpersonid%>" type="hidden">
			<div class="offlinepayment">
				<h2 class="cornflowerblue col-md-12 padtop20" id="subscriptitle"></h2>
				<h3 priceicon="<s:text name="label.price"></s:text>"
					class="pricebtn cornflowerblue col-md-12"></h3>
				<h3 class="size20 padtop20 col-md-12">Payment Mode</h3>
				<div class="padtop20 padbottom20 col-md-12">
					<a class="btn btn-blue paymentab">On-line</a> <a
						href="<%=request.getContextPath()%>/app/myorganization"
						class="btn btn-primary paymentback">Back</a>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/organization/coursepayment.js"></script>
</body>
</html>