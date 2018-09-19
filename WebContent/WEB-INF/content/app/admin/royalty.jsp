<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page
	import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="<%=request.getContextPath()%>/assets/app/css/normalize.min.css"	type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/assets/app/css/bootstrap-slider.css"	type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/assets/plugins/datepicker/css/bootstrap-datepicker.min.css"	type="text/css" rel="stylesheet">

<script id="royaltytmpl" type="text/x-handlebars-template">
<table class="table table-striped whitebg list-search manageprofilecont paginationtab">
<thead>
<tr>
<th>Author Royalty(%)</th>
<th>Org Royalty(%)</th>
<th>From Date</th>
<th>To Date</th>
<th class="action">Action</th>
</tr>
</thead>
<tbody>
{{#each .}}
<tr>
<td>{{authorroyalty}}</td>
<td>{{orgroyalty}}</td>
<td>{{startdateformat effectfrom}}</td>
<td>{{enddateformat effectto}}</td>
<td class="actiondetail"><a class="btn btn-default btn-userinfo royaltyeditbtn" id={{id}} effectfrom="{{effectfrom}}" orgroyalty="{{orgroyalty}}"
	authorroyalty="{{authorroyalty}}" effectto="{{effectto}}">
<i class="fa fa-pencil"></i></a> 
	<a class="btn btn-default btn-userinfo royaltytrashbtn" id={{id}}>
<i class="fa fa-trash icon-large"></i></a></td>
</tr>
{{else}}
<tr> 
<td colspan='5'>Data Not Found</td> 
</tr>
{{/each}}
</tbody>
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>
<script id="requestlimittmpl" type="text/x-handlebars-template">
<h3 class="rightpanetitle">Request Limit
<a href="#" data-toggle="modal" class="btn btn-success  marbottom10 pull-right limitmodal">New Request Limit</a></h3>
<table class="table table-striped whitebg list-search manageprofilecont"> 
<thead>
<tr>
<th>Request Limit(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
<th>Date of Creation</th>
<th class="actiondata">Action</th>
</tr>
</thead>
<tbody>
{{#each .}}
<tr>
<td>{{minimumamount}}</td>
<td>{{creationdate dateofcreation}}</td>
<td class="actionbtn"><a class="btn btn-default btn-userinfo requesteditbtn" minimumamount={{minimumamount}} id={{id}}>
<i class="fa fa-pencil"></i></a> 
	</td>
</tr>
{{else}}
<tr>
 <td colspan='5'>Data Not Found</td> 
</tr>
{{/each}}
</tbody>
</table>

</script>
<script id="paymenttmpl" type="text/x-handlebars-template">
<h3 class="rightpanetitle">Payment Requested Details</h3>
<table class="table table-striped whitebg list-search manageprofilecont">
<thead>
<tr>
<th>Requested by</th>
<th>Requested date</th>
<th>Requested amount(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
<th>Bank Account Detail</th>
<th>Action</th>
</tr>
</thead>
<tbody>
{{#each .}}
<tr>
<td>{{requestedby}} {{lastName}}</td>
<td>{{dateofcreate dateofrequest}}</td>
<td>{{minimumamount}}</td>
<td><a class="bankdetail" bankdetailid={{bankdetailid}}>{{accountnumber}}</a></td>
<td><a class="btn btn-blue requestissuebtn" id={{id}} minimumamount="{{minimumamount}}" requestedby="{{requestedby}}">Issue</a></td>
</tr>
{{else}}
<tr>
 <td colspan='5' >Data Not Found</td>
 </tr>
{{/each}}
</tbody>
</table>	
</script>
<script id="paymentdatecleartmpl" type="text/x-handlebars-template">
<h3 class="rightpanetitle">Payment Issued Details</h3>
<table class="table table-striped whitebg list-search manageprofilecont paginationtab">
<thead>
<tr>
<th>Requested by</th>
<th>Issued by</th>
<th>Request amount(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
<th>Issued date</th>
</tr>
</thead>
<tbody>
{{#each .}}
<tr>
<td>{{Requestedby}} {{RequestedbyLastName}}</td>
<td>{{Issuedby}} {{IssuedbyLastName}}</td>
<td>{{amount}}</td>
<td>{{dateofcreate dateofissue}}</td>
</tr>
{{else}}
<tr>
<td colspan='5' >Data Not Found</td>
</tr>
{{/each}}
</tbody>
</table>	
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>
<script id="paymentdateissuedtmpl" type="text/x-handlebars-template">
						<div class="col-md-12 row">
							<ul id="myTab" class="nav nav-tabs pad0 mar0 marbottom10">
							<li class="borderbottoma presentation active"><a
								class="learningtablinks royaltypaymenttype" href="#tab1" data-toggle="tab" paymenttype="1">
									Course </a></li>
							<li class="borderbottoma presentation"><a
								class="learningtablinks royaltypaymenttype" href="#tab2" data-toggle="tab" paymenttype="2">
									Live </a></li>
						</ul>
						</div>
<div class="rightpanetitle paymentheader centertext martop10 col-md-12" style="color:#B15315;font-size:15px">Royalty Payment Details</div>
<div class="martop10" >Organization Royalty Amount(<i class="fa <s:text name='label.price'></s:text>"></i>) : <span class="orgtotalamount" ></span> 
<span class="pull-right">Upto : <span class="currentdate"></span></span></div>
<table class="table table-striped whitebg list-search manageprofilecont martop10 paginationtab">
<thead>
<tr>
<th>Sl.No</th>
<th>Course Name</th>
<th>Organization Royalty(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
<th>Author Royalty(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
<th>Users Enrolled</th>
</tr>
</thead>
<tbody>
{{#each .}}
<tr>
<td>{{math @index "+" 1}}</td>
<td>{{coursetitle}}</td>
<td>{{roundtwo orgroyaltyamount}}</td>
<td>{{roundtwo authorroyaltyamount}}</td>
<td><a href="#" class="userdetail badge pad612" courseid={{courseid}}>{{courseenrollmentcount}}</a></td>
</tr>
{{else}}
<tr>
<td colspan='5'>Data Not Found</td> 
</tr>
{{/each}}
</tbody>
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>
<script id="livesessionpaymentdateissuedtmpl" type="text/x-handlebars-template">
<div class="col-md-12 row">
							<ul id="myTab" class="nav nav-tabs pad0 mar0 marbottom10">
							<li class="borderbottoma presentation"><a
								class="learningtablinks royaltypaymenttype" href="#tab1" data-toggle="tab" paymenttype="1">
									Course </a></li>
							<li class="borderbottoma presentation active"><a
								class="learningtablinks royaltypaymenttype" href="#tab2" data-toggle="tab" paymenttype="2">
									Live </a></li>
						</ul>
						</div>
<div class="rightpanetitle paymentheader martop10  centertext col-md-12" style="color:#B15315;font-size:15px">Live Session Royalty Payment Details</div>
<div class="martop10">Organization Royalty Amount(<i class="fa <s:text name='label.price'></s:text>"></i>) : <span class="orgtotalamount"></span> 
<span class="pull-right">Upto : <span class="currentdates"></span></span></div>
<table class="table table-striped whitebg list-search manageprofilecont martop10">
<thead>
<tr>
<th>Sl.No</th>
<th>Course Name</th>
<th>Start Time</th>
<th>End Time</th>
<th>Organization Royalty(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
<th>Author Royalty(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
<th>Users Enrolled</th>
</tr>
</thead>
<tbody>
{{#each .}}
<tr>
<td>{{math @index "+" 1}}</td>
<td>{{coursetitle}}</td>
<td>{{starttime}}</td>
<td>{{endtime}}</td>
<td>{{roundtwo orgroyaltyamount}}</td>
<td>{{roundtwo authorroyaltyamount}}</td>
<td><a href="#" class="livesessionuserdetail badge pad612" livesessionid={{livesessionid}}>{{livesessionenrollmentid}}</a></td>
</tr>
{{else}}
<tr>
<td colspan='5' >Data Not Found</td> 
</tr>
{{/each}}
</tbody>
</table>
</script>
<script id="userdetails" type="text/x-handlebars-template">
<table class="table table-striped table-bordered whitebg list-search manageprofilecont">
<thead>
<tr>
<th>Sl.No</th>
<th>Name</th>
<th>Email Id</th>
<th>Amount Paid(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
<th>Payment Type</th>
<th>Applied Royalty</th>
<th>Royalty</th>
<th style="padding: 1px;!important">Royalty Amount(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
<th>Date of Paid</th>
</tr>
</thead>
<tbody>
{{#each .}}
<tr>
<td>{{math @index "+" 1}}</td>
<td>{{name}} {{lastName}}</td>
<td>{{emailid}}</td>
{{#if paymentamount}}
<td>{{paymentamount paymentamount}}</td>
{{else}}
<td>-</td>
{{/if}}
<td>{{paymenttype}}</td>
<td>{{millitodate effectfrom}} - {{millitodate effectto}}</td>
<td>{{orgroyalty}}%</td>
<td>{{orgroyaltyamount}}</td>
<td>{{paymentdate paymentdate}}</td>
</tr>
{{else}}
<tr>
<td colspan='8'>Data Not Found</td>
</tr>
{{/each}}
</tbody>
</table>
</script>
<script id="livesessionuserdetails" type="text/x-handlebars-template">

<table class="table table-striped table-bordered whitebg list-search manageprofilecont">
<thead>
<tr>
<th>Sl.No</th>
<th>Name</th>
<th>Email Id</th>
<th>Amount Paid(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
<th>Payment Type</th>
<th>Applied Royalty</th>
<th>Royalty</th>
<th>Royalty Amount(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
<th>Date of Paid</th>
</tr>
</thead>
<tbody>
{{#each .}}
<tr>
<td>{{math @index "+" 1}}</td>
<td>{{name}} {{lastName}}</td>
<td>{{emailid}}</td>
{{#if paymentamount}}
<td>{{paymentamount paymentamount}}</td>
{{else}}
<td>-</td>
{{/if}}
<td>{{paymenttype}}</td>
<td>{{millitodate effectfrom}} - {{millitodate effectto}}</td>
<td>{{orgroyalty}}%</td>
<td>{{orgroyaltyamount}}</td>
<td>{{#if paymentdate}}{{paymentdate paymentdate}}{{else}} -- {{/if}}</td>
</tr>
{{else}}
<tr>
<td colspan='8'>Data Not Found</td>
</tr>
{{/each}}
</tbody>
</table>
</script>
<script id="currentroyaltytmplt" type="text/x-handlebars-template">
<div class="row boxshadow whitebg marleft0 marbottom10">
{{#each .}}
<form class="form-horizontal whitebg " style="padding:20px 0;">
<div class="row">
<div class="col-md-12">
<div class="col-md-6">
<div class="form-group">
    <label class="col-sm-6 control-label">Author Royalty : </label>
    <div class="col-sm-5">
      <label class="lefttext col-sm-12 control-label">{{authorroyalty}}%</label>
    </div>
  </div>
</div>
<div class="col-md-6">
<div class="form-group">
    <label class="col-sm-6 control-label">Org Royalty :</label>
    <div class="col-sm-6">
      <label class="col-sm-12 lefttext control-label"><font class="issueamount">{{orgroyalty}}%</font></label>
    </div>
  </div>
</div>
</div>
</div>
<div class="row">
<div class="col-md-12">
<div class="col-md-6">
<div class="form-group">
    <label class="col-sm-6 control-label">From Date : </label>
    <div class="col-sm-6">
      <label class="lefttext col-sm-12 control-label">{{startdateformat effectfrom}}</label>
    </div>
  </div>
</div>
<div class="col-md-6">
<div class="form-group">
    <label class="col-sm-6 control-label">To Date :</label>
    <div class="col-sm-6">
      <label class="col-sm-12 lefttext control-label"><font class="issueamount">{{enddateformat effectto}}</font></label>
    </div>
  </div>
</div>
</div>
</div>
<div class="row">
<div class="col-md-12">
<div class="col-md-5">
</div>
<div class="col-md-4">
			<a class="btn btn-default editcurrentroyalty" id={{id}} effectfrom={{effectfrom}} orgroyalty={{orgroyalty}} authorroyalty={{authorroyalty}} effectto={{effectto}} data-toggle="modal" href="#assignUserModel">Edit</a>
        	<a class="btn btn-default delcurrentroyalty" data-toggle="modal" id={{id}} href="#assignUserModel">Delete</a>
			</div>
			<div class="col-md-3"></div>
			</div>
</div>
</div>
</form>
{{else}}
<div class="">
	<div class="col-md-12 padtop200 whitebg min450">
		<h4 class="centertext">Data not found</h4>
		<div style="text-align:center;color:red;" id="defaultroyal"></div>
	</div>
</div>
{{/each}}
</div>
</script>
<script id="defaultroyaltytmplt" type="text/x-handlebars-template">
<div>
{{#if .}}
Note: Current Royalty is not set, so royalty will be calculated as
Default Author Royalty 		= {{authorroyalty}}% ,
Default Organization Royalty = {{orgroyalty}}%
{{else}}
{{/if}}
</div>
</script>
<script id="bankdetailtmplt" type="text/x-handlebars-template">
{{#each .}}
						<div class="form-group">
							<label class="control-label col-md-5">Account Number : </label>
							<div class="controls-label col-md-4">
								{{accountnumber}}
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-5">Bank Name : </label>
							<div class="controls-label col-md-4">
								{{bankname}}
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-5">IFSC Code : </label>
							<div class="controls-label col-md-4">
								{{ifscCode}}
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-5">Branch Name : </label>
							<div class="controls-label col-md-4">
								{{branchname}}
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-5">Branch Location : </label>
							<div class="controls-label col-md-4">
								{{location}}
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-5">Country : </label>
							<div class="controls-label col-md-4">
								{{country}}
							</div>
						</div>
{{/each}}
					</div>

</script>
</head>
<body>

	<%
		UserDetailsAdaptor userDetailsAdaptor = null;
			userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			String roleName = userDetailsAdaptor.getUser().getProperty(
					"rolename");		
			String orgstatus= userDetailsAdaptor.getUser().getProperty(
					"orgstatus");			
	%>
<input type="hidden" ss="<%=request.getParameter("a")%>" id="hidroyaltyPreviousListsrole" value="<%=roleName %>">	
<input type="hidden" id="orgstatus" value="<%=userDetailsAdaptor.getUser().getProperty("orgstatus")%>">
<input type="hidden" class="royaltypageid" value=1>
<input type="hidden" class="royaltypreviouspageid" value=-1>
<input type="hidden" class="paymentrequestid" value="">
<input type="hidden" class="royaltyeffcttodate" value="">
<input type="hidden" class="royaltyeffctfromdate" value="">
<input type="hidden" class="royaltyeffctfromtodate" value="">
<input type="hidden" class="royaltytype" value="">
<div class="container-fluid titlecontainer">
		<div class="row">
			<div class="col-md-12 lefttext">
				<a href="<%=request.getContextPath() %>/app/dashboard">Dashboard
				</a> / Royalty
			</div>
		</div>
	</div>
	<!-- <form action="" name="royaltyform" method="post"> -->
	<div class="alert alert-danger alert-dismissible col-md-12 martopbottom10 pad10 addauthorroyaltyset" style="display: none;"><b><span>No Royalty is set after <span class="alertmsg"></span>, Please create a new Royalty.</span></b></div>
	<div class="alert alert-danger alert-dismissible col-md-12 martopbottom10 pad10 deleteroyalty" style="display: none;"></div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12 pad0">
				<div class="col-md-3 pad0">
					<div class="leftsideul martop10">
						<li class="leftsideliheader"><h3>Royalty Settings</h3></li>
						<li class="leftsideli hover royaltysetting basicinfo">Royalty</li>
						<li class="leftsideli hover requestlimit basicinfo">Request Limit</li>
					</div>
					<div class="leftsideul martop10">
						<li class="leftsideliheader"><h3>Payment Settings</h3></li>
						<li class="leftsideli hover paymentrequest basicinfo">Payment Requested Details</li>
						<li class="leftsideli hover cleardate basicinfo">Payment Issued Details</li>
						<li class="leftsideli hover issuedhistory basicinfo">Royalty Payment</li>
					</div>
				</div>
				<div class="col-md-9 martop10 padright0">
				<div class="royaltysettingmsg">
						<div class="col-md-12 row">
							<ul id="myTab" class="nav nav-tabs pad0 mar0 marbottom10">
							<li class="borderbottoma presentation active"><a
								class="learningtablinks royaltytypeactive" href="#tab1" data-toggle="tab" royaltytypeid="1">
									Course </a></li>
							<li class="borderbottoma presentation"><a
								class="learningtablinks royaltytypeactive" href="#tab2" data-toggle="tab" royaltytypeid="2">
									Live </a></li>
						</ul>
						</div>
							<div class="row">
								<div class="col-md-6 royaltyheader martop10">Course Royalty Settings</div>
								<div class="col-md-6 martop10">
									<a class="btn btn-default btn-blue active current">Current</a> <a class="btn btn-default forthcoming">Forth Coming</a> <a class="btn btn-default completed">Completed</a> <a href="#" data-toggle="modal" class="btn btn-success btn-default addroyalty pull-right">New Royalty</a>
									
								</div>
							</div>
						</div>
						<div id="royaltytable" class="martop10"></div>
					</div>
			</div>
		</div>
	</div>
	<!-- </form> -->
<div id="addroyaltymodal" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title size20">Add New Royalty</h4>
      </div>
      <div class="modal-body">
			<input type="hidden" class="royaltyconfigid">
			<form class="form-horizontal" action="" method="post" name="">
						<div class="form-group">
							<label class="control-label col-md-5">Author Royalty(%) <span style="color: red;">*</span> :</label>
							<div class="col-md-6">
								<input id="authorroyalty" data-slider-id='authorSlider' type="text" data-slider-min="0" data-slider-max="100" data-slider-step="1" data-slider-value="0"/>
						     </div>
						     <div class="alert alert-danger alert-dismissible col-md-6 martopbottom10 pad10 addauthorroyalty alert-dismissible">Please Select Author Royalty</div>
						</div>
						<div class="form-group"> 
							<label class="control-label col-md-5">Org Royalty(%) <span style="color: red;">*</span> : </label>
							<div class="col-md-6">
								<input id="orgroyalty" data-slider-id='orgSlider' type="text" data-slider-min="0" data-slider-max="100" data-slider-step="1" data-slider-value="100"/>
							</div>
						</div>
						<div class="form-group">
								 <label class="control-label col-md-5">From Date <span style="color: red;">*</span> : </label>
							<div class="col-md-6">
								<div class="input-group input-append  date fromdateval1"
									id="datePicker">
									<input type="text" class="form-control fromdateval" name="date"
										disabled> <span class="input-group-addon add-on"><%-- <span
										class="glyphicon glyphicon-calendar"></span> --%><i class="fa fa-calendar"></i></span>
								</div>
							</div>
							<div class="col-md-5"></div>
							<div class="alert alert-danger alert-dismissible col-md-6 martopbottom10 pad10 addfromdate">Please select the date</div>
						</div>
					<div class="form-group">
							<label class="control-label col-md-5">To Date <span style="color: red;">*</span> : </label>
							<div class="col-md-6">
								<div class="input-group input-append  date todateval1"
									id="datePicker">
									<input type="text" class="form-control todateval" name="date" disabled>
									<span class="input-group-addon add-on"><%-- <span
										class="glyphicon glyphicon-calendar"></span> --%><i class="fa fa-calendar"></i></span>
								</div>
							</div>
							<div class="col-md-5"></div>
							<div class="alert alert-danger alert-dismissible col-md-6 martopbottom10 pad10 addtodate">Please select the date</div>
                		<div class="alert alert-danger alert-dismissible col-md-12 martopbottom10 pad10 passdatemsg marleft10"></div>
					</div>
					</form>
				</div>
      		<div class="modal-footer messagemodal">
				<button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Close</button>
				<a class="btn btn-blue submitroyalty">Save</a>
				<a class="btn btn-blue updateroyalty updatecurrentroyalty">Update</a>
			</div>
    </div>
  </div>
</div>
	<div id="addrequestmodal" class="modal fade in" tabindex="-1" role="dialog" aria-labelledby="requestModal" aria-hidden="false">
		<form class="form-horizontal" action="" method="post" name="">
			<div class="modal-dialog">
    		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h4 id="requestModal" class="modal-title modal-title size20">New Request Limit</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<input type="hidden" class="idrequest">
					<div class="col-md-12" >
						<div class="form-group">
							<label class="control-label col-md-5">Request Limit <span style="color:red;">*</span> : </label>
							<div class="col-md-6">
								<input type="text" class="form-control requestlimitamount" required="required">
							</div>
							</div>
							<div class="col-md-5"></div>
							<div class="alert alert-danger alert-dismissible col-md-6 martopbottom10 pad10 addrequestlimit">Please Enter Request Limit</div>
							<div class="alert alert-success alert-dismissible col-md-12 martopbottom10 pad10 alertmessage marleft10" style="display: none;"></div>
						</div>
					</div>
					<div class="col-md-12" >
						<div id="errormessage"></div>
					</div>
				</div>
			<div class="modal-footer messagemodal">
				<button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Close</button>
				<a class="btn btn-blue submitrequest submitrequestbtn">Save</a>
				<a class="btn btn-blue updaterequest updaterequestbtn">Update</a>
			</div>
			</div>
			</div>
		</form>
	</div>
	<div id="addpaymentmodal" class="modal fade in" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false">
		<form class="form-horizontal" action="" method="post" name="">
			<div class="modal-dialog">
    		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h4 id="myModalLabel" class="modal-title modal-title size20">New Payment Request</h4>
			</div>
			<div class="modal-body">

						<div class="form-group">
							<label class="control-label col-md-5">Requested by : </label>
							<div class="controls-label col-md-4 requestdetail">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-5">Requested amount : </label>
							<div class="controls-label col-md-4 requestlimitdetail">
							</div>
						</div>
					<div class="span12" >
						<div id="errormessage"></div>
					</div>
			</div>
			<div class="modal-footer messagemodal">
				<button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Close</button>
				<a class="btn btn-blue submitpayment">Issue</a>
			</div>
			</div>
			</div>
		</form>
		</div>
	<div id="userdetailmodal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  		<div class="modal-dialog modal-lg">
    	<div class="modal-content" style="width:950px;">
  			<div class="modal-header">
    			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    			<h4 id="myModalLabel" class="modal-title modal-title size20">User List</h4>
  			</div>
  		<div class="modal-body">
    		<div id="userdetaillist">
  		</div>
		</div>
		</div>
		</div> 
</div>
<div id="bankdetailmodal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
    <div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h4 id="myModalLabel" class="modal-title modal-title size20">Bank Account Details</h4>
			</div>
			<div class="modal-body">
			<form class="form-horizontal" action="" method="post" name="">
			<div id="bankaccountdetails"></div>
			</form>
			</div>
			<div class="modal-footer messagemodal">
				<button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Close</button>
			</div>
		</div>
		</div>
</div>
	<script src="<%=request.getContextPath()%>/assets/app/js/bootstrap-slider.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/app/js/admin/requestlimit.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/app/js/admin/paymentrequest.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/app/js/admin/paymentissue.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/app/js/admin/royalty.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/plugins/datepicker/js/bootstrap-datepicker.min.js"></script>
	
</body>
</html>