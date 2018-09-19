<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
#printarea {
	min-height: 700px;
}

.invoicepart {
	min-height: 300px;
}
</style>
<script id="printtmpl" type="text/x-handlebars-template">
{{#each .}}
<div class="col-md-12">
	<a class="pull-right btn btn-flat btn-blue printinvoicebtn">Save</a>
</div>
<div id="invoicearea" class="row">
	<div class="col-md-12">
		<div class="col-md-2">
			<img alt="" class="width70per height140"
				src="<%=request.getContextPath()%>/assets/app/images/invoicelogo.png">
		</div>
		<div class="col-md-10 martop20">
			<address class="invoiceheader">
				<h4 class="righttext martop20">Invoice Number : {{courseenrollmentid}}</h4>
				<h4 class="righttext martop20">Invoice Date: {{paymentdate}}</h4>
			</address>
		</div>
	</div>
	<h3 class="invoicereceipt centertext">Invoice Receipt</h3>
	<div class="invoicepart pad10">
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th>Course Title</th>
					<th>Course Author</th>
					<th>Price <span class="rubee" style='font-family;'>(&#8377;)</span></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>{{coursetitle}}</td>
					<td>{{firstname}}</td>
					<td>{{price}}</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="col-md-12">
		<address class="invoiceaddress">
			<h4>For Details Contact</h4>
			<h4><s:text name="label.productname"></s:text></h4>
			<h4>i-Grandee Software Technologies Private Limited</h4>
			<h4>Villapuram, Madurai.</h4>
			<h4>TamilNadu,India</h4>
		</address>
	</div>
</div>
{{/each}}
</script>
<script id="successTemplate" type="text/x-handlebars-template">
<form name="invoiceform" method="post" action="mycourses">
<div class="row">
<div class="col-md12 centertext" style="margin-top:150px;">
<h3 ><p class="centertext">You have been Successfully enrolled<br>You can start learning Once it is approved</p></h3>
<input type="submit" value="Go to My Courses" class="center btn btn-flat btn-blue mycoursesbtn">
</div>
</div>
</form>  
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="col-md-12 boxshadow" style="background-color: white; padding: 20px;"
			id="printarea">
		</div>
	</div>
	<s:hidden name="invoiceid" value="%{invoiceid}"></s:hidden>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/html2canvas.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/jspdf.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/jspdf/FileSaver.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/course/invoice.js"></script>
</body>
</html>