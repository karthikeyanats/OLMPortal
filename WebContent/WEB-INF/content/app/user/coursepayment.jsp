<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0">
<script id="courseparticularstmpl" type="text/x-handlebars-template">
<h3 class="centertext martop30">You have subscribed to the following course</h3>
{{#each .}}
<h2 class="martop30 centertext cornflowerblue uppercase">{{coursetitle}}</h2>
<h3 class="centertext">Price : <font class="pricebtn" courseid="{{courseid}}" priceid="{{priceid}}" price="{{price}}" priceicon ="<s:text name="label.price"></s:text>"></font></h3>
{{/each}}
</script>
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
	<span id="context" style="display: none;" cid="<%=courseenrollmentid%>"
		cstatus="courseenrollmentstatus"><%=request.getContextPath()%></span>
	<div class="container-fluid min450 martop30 whitebg boxshadow">
		<div class="paymentpage centertext martop30">
			<div id="courseparticularstable" class="martop30"></div>
			<div class="freecourseenrolldiv">
				<form action="admission" name="freecourseform" method="post">
					<a class="btn white btn-blue freecoursebtn" courseid="" priceid=""
						paymenttype="Free" paymentstatus="A" courseenrollmentstatus="A">Enroll</a>
					<a class="btn btn-blue backbtnclass white">Back</a>
				</form>
			</div>
			<div class="offlinepayment martop30" style="display: none;">
				<h3 class="lightbrown">Select Payment Mode</h3>
				
					<a type="button" class="btn btn-blue btn-padded btn-big paymentab"
						href="#offlinepay" data-toggle="tab">Off-line</a> <a type="button"
						href="#onlinepay" data-toggle="tab"
						class="btn btn-flat btn-default btn-padded btn-big paymentab">On-line</a>
				
				<div class="row">
					<div class="tab-content martop20">
						<div class="tab-pane active" id="offlinepay">
							<form name="invoiceemailform" method="post">
								<a courseid="" priceid="" paymentstatus="P"
									courseenrollmentstatus="P" paymenttype="invoice-email"
									class="btn white btn-blue previewinvoicebtn" value="Show Modal">Preview
									Invoice</a> <a></a> <a class="btn white btn-blue backbtnclass">Back</a>
								<s:hidden name="invoiceid" value=""></s:hidden>
								<s:hidden name="courseid" value="%{courseid}"></s:hidden>
							</form>
						</div>
						<div class="tab-pane" id="onlinepay">
							<a email="<%=email%>" id="existingemail"
								class="btn white btn-blue onlinepaymentbtn">Pay Online</a> <a
								class="btn white btn-blue backbtnclass">Back</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<div id="Searching_Modal" class="modal fade" tabindex="-1"
		role="dialog" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="text-align: center">
					<h3>Generating Invoice</h3>
				</div>
				<div class="modal-body">
					<div style="height: 200px">
						<i class="fa fa-clock-o"
							style="font-size: 100px !important; padding-left: 38% !important;"></i>
						<h1 style="text-align: center !important;">Please wait...</h1>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="free-course-enroll-model" class="modal fade" tabindex="-1"
		role="dialog" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="text-align: center">
					<h3>Enrolling Course</h3>
				</div>
				<div class="modal-body">
					<div style="height: 200px">
						<i class="fa fa-clock-o"
							style="font-size: 100px !important; padding-left: 38% !important;"></i>
						<h1 style="text-align: center !important;">Please wait...</h1>
					</div>
				</div>
			</div>
		</div>
	</div>

	<s:hidden name="courseidhidden" value="%{courseid}"></s:hidden>
	<s:hidden name="courseenrollmentid" value="%{courseenrollmentid}"></s:hidden>
	<input type="hidden" name="orgperson" value="<%=orgperson%>" />
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/course/payment.js"></script>
</body>
</html>