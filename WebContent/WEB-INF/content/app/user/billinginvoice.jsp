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
<div class="row-fluid">
	<div class="navbutton pull-right">


<a href="<%=request.getContextPath()%>/app/purchaseHistory" class=" btn btn-flat btn-blue">Back</a>
	<a class=" btn btn-flat btn-blue printinvoicebtn">Save</a>
</div>
</div>
<div id="invoicearea" style="background-color: white; padding: 20px;">
	<div class="row-fluid">
		<div class="span6">
			<img style="width:20%;" alt=""
				src="<%=request.getContextPath()%>/assets/app/images/invoicelogo.png">
		</div>
		<div class="span6">
			<address class="invoiceheader">
				<h4 class="righttext martop20">Invoice Number : {{#if orderno}}<span class="courseenrollmentid">{{orderno}}</span>
				    {{else}}<span class="courseenrollmentid">-</span>{{/if}}</h4>
				<h4 class="righttext martop20">Invoice Date: {{paymentdate}}</h4>
			</address>
		</div>
	</div>
	<h3 class="invoicereceipt">Invoice Receipt</h3>
	<div class="invoicepart">
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>Course Title</th>
					<th>Course Author</th>
					<th>Price<span class="rubee" style='font-family;'>(&#8377;)</span></th>
					
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
	<div class="row-fluid">
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
<script id="livesessionprinttmpl" type="text/x-handlebars-template">
{{#each .}}
	<div class="row-fluid">

	<div class="navbutton pull-right">


<a href="<%=request.getContextPath()%>/app/purchaseHistory" class=" btn btn-flat btn-blue">Back</a>
	<a class=" btn btn-flat btn-blue printinvoicebtn">Save</a>
</div>
</div>
<div id="invoicearea" style="background-color: white; padding: 20px;">
	<div class="row-fluid">
		<div class="span6">
			<img style="width: 150px; height: 100px;" alt=""
				src="<%=request.getContextPath()%>/assets/app/images/Learning.png">
		</div>
		<div class="span6">
			<address class="invoiceheader">
				<h4>Invoice Number : {{#if orderno}}<span class="livesessionenrollmentid">{{orderno}}</span>
				    {{else}}<span class="livesessionenrollmentid">-</span>{{/if}}</h4>
			</address>
		</div>
	</div>
	<h3 class="invoicereceipt">Invoice Receipt</h3>
	<div class="invoicepart">
		<table class="table table-bordered">
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
	<div class="row-fluid">
		<address class="invoiceaddress">
			<h4>For Details Contact</h4>
			<h4><s:text name="label.productname"></s:text></h4>
			<h4>M/S iGrandee Software Technologies Private Limited</h4>
			<h4>Villapuram, Madurai.</h4>
			<h4>TamilNadu,India</h4>
		</address>
	</div>
</div>
{{/each}}
</script>
<script id="successTemplate" type="text/x-handlebars-template">
<form name="invoiceform" method="post" action="mycourses">
<div class="row-fluid">
<div class="span12 center" style="margin-top:150px;">
<h3 ><p class="center">You have been Successfully enrolled<br>You can start learning Once it is approved</p></h3>
<input type="submit" value="Go to My Courses" class="center btn btn-flat btn-blue mycoursesbtn">
</div>
</div>
</form>  
</script>
</head>
<body>
	<div class="row-fluid">
		<div class="span12" style="background-color: white; padding: 20px;"
			id="printarea">
		</div>
	</div>
	<s:hidden name="invoiceid" value="%{invoiceid}"></s:hidden>
	<s:hidden name="courseenrollmentid" value="%{courseenrollmentid}"></s:hidden>
	<s:hidden name="livesessionenrollmentid" value="%{livesessionenrollmentid}"></s:hidden>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/html2canvas.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/jspdf.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/jspdf/FileSaver.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/users/billinginvoice.js"></script>
</body>
</html>