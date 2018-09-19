<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Subscription Plan</title>
<script id="allsubscriptiondetailtmpl" type="text/x-handlebars-template"> 
<span class="btn white btn-blue newsubscription pull-right marbottom10" >Add New Subscription</span>

<h3 class="marbottom15 size20">Annual Subscription Plans</h3>
<table class="table whitebg table-bordered table-striped manageprofilecont" >
<thead>
<tr>
<th>Plan Name</th>
<th>Duration(in year(s))</th> 
<th>Amount(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
<th>Maximum User</th>
<th>Maximum Course</th>
<th>Action</th>
</tr>
</thead> 
<tbody>
{{#if subdetailsyear}}
{{#each  subdetailsyear}}
<tr >
<td>{{name}}</td>
<td>{{duration}}</td>
<td>{{planamount}}</td>
<td>{{maxusers}}</td>
<td>{{maxcourse}}</td>
<td><a class="btn btn-flat btn-gray btn-userinfo planeditbtn" planid="{{id}}" name="{{name}}" duration="{{duration}}"
		durationtype="{{durationtype}}"	planamount="{{planamount}}" maxusers="{{maxusers}}" maxcourse="{{maxcourse}}">
<i class="fa fa-pencil"></i></a> 
	<a class="btn btn-flat btn-gray btn-userinfo plantrashbtn" planid="{{id}}" name="{{name}}" duration="{{duration}}"
		durationtype="{{durationtype}}"	planamount="{{planamount}}" maxusers="{{maxusers}}" maxcourse="{{maxcourse}}">
<i class="fa fa-trash icon-large"></i></a></td>
</tr>
{{/each}}
{{else}}
 <tr><td colspan="7" class="categorydescription">Data not found</td></tr>
  </tbody>
</table><br><br>

{{/if}}



<div class="main">

<table class="table  whitebg table-bordered table-striped manageprofilecont" >
<h3 class="marbottom15 size20">Monthly Subscription Plans</h3>
<thead>	
<tr>
<th>Plan Name</th>
<th>Duration(in month(s))</th>
<th>Amount(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
<th>Maximum User</th>
<th>Maximum Course</th>
<th>Action</th>
</tr>
</thead>
<tbody>
{{#if subdetails}}
{{#each subdetails}}
<tr>
<td>{{name}}</td>
<td>{{duration}}</td>
<td>{{planamount}}</td>
<td>{{maxusers}}</td>
<td>{{maxcourse}}</td>
<td><a class="btn btn-flat btn-gray btn-userinfo planeditbtn" planid="{{id}}" name="{{name}}" duration="{{duration}}"
		durationtype="{{durationtype}}"	planamount="{{planamount}}" maxusers="{{maxusers}}" maxcourse="{{maxcourse}}">
<i class="fa fa-pencil"></i></a> 
	<a class="btn btn-flat btn-gray btn-userinfo plantrashbtn" planid="{{id}}" name="{{name}}" duration="{{duration}}"
		durationtype="{{durationtype}}"	planamount="{{planamount}}" maxusers="{{maxusers}}" maxcourse="{{maxcourse}}">
<i class="fa fa-trash icon-large"></i></a></td>
</tr>
{{/each}}
{{else}}
<tr><td colspan="7" class="categorydescription">Data not found</td></tr>

{{/if}}
</tbody>
</table>
</div>
</script>


<script id="newsubscriptiontmpl" type="text/x-handlebars-template">

<h3 class="marbottom15 size20 lineheight40">New Subscription Plan
<a class="btn white btn-blue subscriptionlist pull-right"  >Subscription plans</a>
</h3>
<div id="planvalidation"></div>
<div class="form-horizontal whitebg pad10 manageprofilecont">
<input type="hidden" id="hiddenplanid" >
			<div class="form-group">
				<label class="col-sm-3 control-label">Plan Name<font class='red'>*</font> </label> 
				<div class="col-sm-4">
					<input type="text" required="required" contenteditable="true"
						class="form-control plannameval alertplan" maxlength="45" errorid="planerror" placeholder="Plan Name">
				<div class="alert alert-danger martop10 pad10 alert-error plannamevalmsg" id="planerror" style="display: none;">Please enter Plan Name</div>
				</div>
			</div>

	<div class="form-group">
		<label class="col-sm-3 control-label">Plan Type <font class='red'>*</font></label>
		<div class="col-sm-4"> 
			<div class="row">
<div class="col-sm-12"> 
<div class="col-sm-5 pad0 btn-group lefttext">
        			 <button class="btn white planbtn monthlyplan plantypebtn">
							Monthly
						</button>
						<button class="btn white planbtn yearlyplan plantypebtn">
							Annual
						</button>					
      			</div>
				<span class="col-sm-3"><input type="hidden" placeholder="" errorid="planmonthlyyearerror" id="planduration" maxlength="10" required="required" class="form-control offset1 span4 alertplan planduration" value=""></span>
			
</div>

</div>
			     <div class="alert alert-error alert-danger martop10 pad10 plantypevalmsg" id="planmonthlyyearerror" style="display: none;">Please enter Plan Type</div>
		</div>
	</div> 
			 
			<div class="form-group">
				<label class="col-sm-3  control-label">Amount<font class='red' >*</font></label>
				<div class="col-sm-4">
					<input type="text" required="required" contenteditable="true"
						class="form-control planamount alertplan" maxlength="10" errorid="planamounterror" placeholder="Plan Amount">
					<div class="alert alert-danger martop10 pad10 alert-error planamountvalmsg" id="planamounterror" style="display: none;">Please enter Plan Amount</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">Maximum Course<font class='red'>*</font> </label>
				<div class="col-sm-4">
					<input type="text" required="required" contenteditable="true"
						class="form-control planmaxcourse alertplan" maxlength="10" errorid="planmaxcourseerror" placeholder="Plan Maximum Course">
					<div class="alert alert-danger martop10 pad10 alert-error planmaxcoursevalmsg" id="planmaxcourseerror" style="display: none;">Please enter Plan Maximum Course</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">Maximum User<font class='red'>*</font></label>
				<div class="col-sm-4">
					<input type="text" contenteditable="true"
						class="form-control  planmaxuser alertplan" maxlength="10" errorid="planmaxusererror" placeholder="Plan Maximum User">
				<div class="alert alert-danger martop10 pad10 alert-error planmaxuservalmsg" id="planmaxusererror" style="display: none;">Please enter Plan Maximum User</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 righttext control-label">
				</label>
				<div class="col-sm-4">
					<span class="btn white btn-blue savesubscribtn">Save</span>
 				</div>
			</div>
		</div>		
</script>

<script id="nodatatmpl" type="text/x-handlebars-template">
<span class="btn btn-flat btn-blue newsubscription"  style="width: 200px; margin-top: 3px;">Add New Subscription</span>
<table class="table manageprofilecont">

<thead>
<tr >
<th>Plan Name</th>
<th>Duration</th>
<th>Duration Type</th>
<th>Amount(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
<th>Maximum User</th>
<th>Maximum Course</th>
<th>Action</th>
</tr>
</thead>
<tbody>
<tr >
<td><h3 >No Data Found</h3></td>
</tr></tbody></table>

</script>
</head>
<body>

	<div class="container-fluid ">
		<div class="row titlecontainer">
			<div class="col-md-12 lefttext">
				<a>Subscription Plan</a>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12 pad0">
				<div id="personalinfotable1"></div>
				<div id="personalinfotableyear"></div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="alertmessage" style="display: none;"
		aria-hidden="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title">Subscription Plan</h4>
				</div>
				<div class="modal-body">
					<p style="color: #F00" class="alertmessageval">The plan is in
						use. So edit and trash are not allowed</p>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="successalertmessage">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title">
						Subscription Plan
						</h3>
				</div>
				<div class="modal-body">
					<p class="successalertmsg"></p>
				</div>
			</div>
		</div>
	</div>
	<script
		src="<%=request.getContextPath()%>/assets/app/js/users/subscriptionplan.js"
		type="text/javascript"></script>

</body>
</html>