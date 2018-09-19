<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page
		import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
	<%
		UserDetailsAdaptor userDetailsAdaptor = null;
		userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String orgstatus = userDetailsAdaptor.getUser().getProperty(
				"orgstatus");
		String rolename = userDetailsAdaptor.getUser().getProperty(
				"rolename");
		String email = userDetailsAdaptor.getUser().getProperty("email");
	%>

	<%
		String au = request.getParameter("a");
	%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Reamedi Cloud || My Courses</title>
<meta name="viewport"
	content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0">
<style type="text/css">
#certificatearea {
	background-image:
		url('<%=request.getContextPath()%>/assets/app/images/certificate/Reamedi Certificate.jpg')
		!important;
	background-repeat: no-repeat;
	background-size: 100% 690px;
}
</style>
<script id="learningcoursestmpl" type="text/x-handlebars-template">
<%if(rolename.equals("tutor")) {%>
<h4 class="marbottom15 size20">Teaching Courses</h4>
<% } else {%>
<h4 class="marbottom15 size20">Learning Courses</h4>
<% }%>
<div id="learningcoursegridview" class="hidden-xs hidden-sm hidden-md">
		{{#if .}} {{#each .}}
		<div class="col-md-3 pad5 marbottom10">
			<div class="col-md-12 whitebg boxshadow">
				<div class="row">
					<img class="width100 height140"
						src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo">			
				</div>
				<div class="row pad5">
					<h5 class="overflowtext coursetitle">{{coursetitle}}</h5>
					<p class="overflowtext lightbrown">By {{firstName}} {{lastName}}</p>			
				</div>
				<div class="row">
					<p class="pad5 centertext marbottom0">{{#if courseprogress}}{{courseprogress}}{{else}}0{{/if}}%
						Completed</p>
					<div class="progress marbottom0 height10">
						<div class="progress-bar progress-bar-info" style="width: {{courseprogress}}%;"></div>
					</div>
				</div>
				<div class="row">
					<form name="startlearninggridform" method="post">
						<a class="pad10 btn btn-default lightgraybg noborder centertext col-md-12 startlearninggridbtn"
							courseid={{courseid}} courseenrollmentstatus="{{courseenrollmentstatus}}"
							courseenrollmentid="{{courseenrollmentid}}"> <i
							class="fa fa-play-circle"></i>&nbsp;Preview Course 
						</a>
						<s:hidden name="courseid" value=""></s:hidden>
						<s:hidden name="courseenrollmentid" value=""></s:hidden>
					</form>
				</div>		
			</div>
		</div>
		{{/each}} {{else}}
		<div class="row">
			<div class="col-md-12 padtop200 whitebg min450">
				<h2 class="centertext">No courses</h2>
			</div>
		</div>
		{{/if}}
	</div>
	<div id="learningcourslistview" class="hidden-lg">
		{{#if .}} {{#each .}}
		<div class="row whitebg mar0 marbottom10 boxshadow">
			<div class="col-md-12 pad0">
				<div class="col-md-2 col-sm-10 pad0 col-xs-3">
					<img class="width100 height140"
						src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo">
				</div>
				<div class="col-md-10 pad0 col-xs-9">
					<div class="row">
						<div class="col-md-12">
							<div class="col-md-9">
								<h6 class="marbottom10">{{boardname}} /
									{{coursecategoryname}}</h6>
								<h5 class="mar0 marbottom10">{{coursetitle}}</h5>
								<p class="lightbrown">By {{firstName}} {{lastName}}</p>
							</div>
							<div class="col-md-3 centertext martop10 pad0">
								<form name="startlearningform" method="post">
									<a class="btn btn-primary btn-gray startlearningbtn"
										courseid={{courseid}}
										courseenrollmentstatus="{{courseenrollmentstatus}}"
										courseenrollmentid="{{courseenrollmentid}}"><i
										class="fa fa-play-circle"></i>&nbsp;Start Course</a>
									<s:hidden name="courseid" value=""></s:hidden>
									<s:hidden name="courseenrollmentid" value=""></s:hidden>
									<input type="hidden" name="courseenrollmentstatus">
								</form>
							</div>
						</div>
						<div class="col-md-12 martop10">
							<p class="righttext mar0">
							{{#if courseprogress}}{{courseprogress}}{{else}}0{{/if}}% completed</p>
							<div class="progress mar0 height10">
								<div class="progress-bar progress-bar-info" style="width: {{courseprogress}}%;"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		{{/each}} {{else}}
		<div class="row">
			<div class="col-md-12 padtop200 whitebg min450">
				<h2 class="centertext">No courses</h2>
			</div>
		</div>
		{{/if}}
	</div>
</div>
</script>
<script id="completedcoursestmpl" type="text/x-handlebars-template">
<h3 class="size20 martop10">Completed Courses</h3>
{{#if .}}
{{#each .}}
<div class="row whitebg mar0 marbottom10 boxshadow">
	<div class="col-md-2 pad0">
		<img class="height140 width100 hidden-xs"
			src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo">
	</div>
	<div class="col-md-10">
		<h3 class="size17">{{coursetitle}}</h3>
		<h6 class="lightbrown marbottom20">By {{firstName}} {{lastName}}</h6>
		<form name="completedcourseform" method="post">
	  		<a class="btn btn-primary certificaterequestbtn pull-right marright7 martopbottom10"
				courseenrollmentid="{{courseenrollmentid}}"><i class="fa fa-university"></i>&nbsp;Certificate</a>
			<a class="btn btn-primary pull-right startlearnbtn marright7 martopbottom10"
				courseid="{{courseid}}" courseenrollmentid="{{courseenrollmentid}}" courseenrollmentstatus ="{{courseenrollmentstatus}}">
				<i class="fa fa-play-circle"></i>&nbsp;Start Learning</a> 
			<a class="btn btn-primary btn-black pull-right reportbtn marright7 martopbottom10"
				courseenrollmentid="{{courseenrollmentid}}" style="margin-right: 7px;"><i class="fa fa-certificate"></i>&nbsp;Report</a>
				<s:hidden name="courseenrollmentid" value=""></s:hidden>
				<input type="hidden" name="courseid" value="{{courseid}}">
				<s:hidden name="courseenrollmentstatus" value="{{courseenrollmentstatus}}"></s:hidden>
		</form>
	</div>	
</div>
{{/each}}
{{else}}
<div class="row-fluid">
	<div class="col-md-12 padtop200 whitebg min450">
		<h4 class="centertext">No courses</h4>
	</div>
</div>
{{/if}}

</script>
<script id="authoringcoursestmpl" type="text/x-handlebars-template">
<h3 class="martop10 size20">
	<div class='row'>
		<div class='col-md-5 authoringcoursetitle'>Published Courses</div>
		<div class='col-md-7'>
			<a class='btn btn-flat btn-success pull-right newcoursebtn marleftt7'
				data-toggle='modal' href='#coursecreationmodal'>
				<i class='fa fa-plus' style='margin-right: 5px;'></i>New Course
			</a> <span class="btn-group pull-right">
                <a class=" btn btn-default authcourselink" st="trash">Trashed</a>
  				<a class=" btn btn-default authcourselink" st="app">Approved</a><a
				class=" btn btn-default authcourselink" st="pub">Published </a> <a
				class="btn btn-default authcourselink" st="dra">Draft</a>
			</span>
		</div>
</h3>
<div id="authoringtypecourses"></div>
</script>
<script id="authordraftcoursestmpl" type="text/x-handlebars-template">
<h3 class="martop10 size20">Draft Courses</h3>
{{#if .}}
{{#each .}}
<div class="row whitebg mar0 marbottom10 boxshadow">
	<div class="col-md-2 pad0">
		<img class="height140 width100 hidden-xs"
			src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo">
	</div>
	<div class="col-md-10">
		<form name="coursemanage" method="post">
			<h3 class="size17" courseid="{{courseid}}">{{coursetitle}}
				<span class="pull-right size17" style="color : #000 !important;">Price : <span class="icon_price" priceicon ="<s:text name="label.price"></s:text>" price={{price}} style="color:#0E800E"></span></span>
			</h3>
			<input type="hidden" name="courseid" value="">
		</form>
		<p class="lightbrown overflowtext marbottom0">{{coursedescription}}</p> 
	</div>
	<form name="teachingcourseform" method="post" action="managecourse">		
		<input type="hidden" name="courseid" value="{{courseid}}">
        <a class="btn btn-primary btn-trash deletecourse martop10 marright7 pull-right" courseid={{courseid}}>
			<i class="fa fa-trash-o" ></i>&nbsp;</span>Trash</a></span>
		<a class="btn btn-primary pull-right managecoursebtn martop10 marright7" courseid="{{courseid}}">
			<i class="fa fa-crosshairs"></i>&nbsp;Manage</a>
	</form>		
</div>
{{/each}}
{{else}}
<div class="row-fluid">
	<div class="col-md-12 padtop200 whitebg min450">
		<h4 class="centertext">No courses</h4>
	</div>
</div>
{{/if}}
</script>
<script id="authortrashedcoursestmpl" type="text/x-handlebars-template">
<h3 class="martop10 size20">Trashed Courses</h3>
<table class="table whitebg table-bordered table-striped">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Course</th>
			<th>Action</th>			
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
            <td>{{math @index "+" 1}}</td>
			<td>{{coursetitle}}</td>
			<td><form name="teachingcourseform" method="post" action="managecourse">		
		<input type="hidden" name="courseid" value="{{courseid}}">
        <a class="btn btn-primary deleteforever" courseid="{{courseid}}">
			<i class="icon-trash" style="margin-right:3px;"></i>&nbsp;Delete Forever</a>
		<a class="btn btn-primary restorecoursebtn" courseid="{{courseid}}" style="margin-right:10px;">
			<i class="fa fa-repeat" style="margin-right:3px;"></i>&nbsp;Restore</a>
	</form></td>
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="3"><p class="centertext">No Courses</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</script>
<script id="authorpublishedcoursestmpl"
	type="text/x-handlebars-template">
<h3 class="martop10 size20">Published Courses</h3>
<table class="table whitebg table-bordered table-striped">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Course</th>
			<th>Status</th>			
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
            <td>{{math @index "+" 1}}</td>
			<td>{{coursetitle}}</td>
			<td>Waiting For Approval</td>
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="3"><p class="centertext">No Courses</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</script>
<script id="authorapprovedcoursestmpl" type="text/x-handlebars-template">
<h3 class="martop10 size20">Approved Courses</h3>
{{#if .}}
{{#each .}}
<div class="row whitebg mar0 marbottom10 boxshadow">
	<div class="col-md-12 pad0">
		<div class="col-md-2 pad0">
			<img class="height140 width100 hidden-xs"
				src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo">
		</div>
		<div class="col-md-10">
			<form name="coursemanage" method="post">
				<h3 class="size17" courseid="{{courseid}}">
					{{coursetitle}}
				<span class="pull-right size17" style="color : #000 !important;">Price : <span class="icon_price" priceicon ="<s:text name="label.price"></s:text>" price={{price}} style="color:#0E800E"></span></span>
				</h3>
				<input type="hidden" name="courseid" value="{{courseid}}">
				
			</form>
			<p class="overflowtext lightbrown">{{coursedescription}}</p> 
		</div>
		<form name="teachingcourseform" method="post">		
			<a class="btn btn-primary pull-right manageusersbtn" coursetitle="{{coursetitle}}" 
				price="{{price}}" courseid="{{courseid}}" style="margin-right:10px;">
				<i class="fa fa-users"></i>&nbsp;Users</a>
				<input type="hidden" name="courseid" value="{{courseid}}">
				<input type="hidden" name="price" value="{{price}}">
				<input type="hidden" name="coursetitle" value="{{coursetitle}}">
   			<a class="btn btn-primary btn-gray btn-flat pull-right managecoursebtn" courseid="{{courseid}}" style="margin-right:10px;">
				<i class="fa fa-crosshairs"></i>&nbsp;Manage</a>		
  				<a href="#inviteusersmodal" data-toggle="modal" class="btn-primary btn btn-flat pull-right inviteuser" courseid="{{courseid}}" style="margin-right:10px;">
			<i class="fa fa-user"></i>&nbsp;Invite</a>	
		</form>
	</div>	
</div>
{{/each}}
{{else}}
<div class="row-fluid">
	<div class="col-md-12 padtop200 whitebg min450">
		<h4 class="centertext">No courses</h4>
	</div>
</div>
{{/if}}
</script>

<script id="nocoursestmpl" type="text/x-handlebars-template">
<h4 class="marbottom15 size20">Learning Courses</h4>
<div class="col-md-12 padtop200 whitebg min450 boxshadow">
	<p class="centertext size20">Please click the browse button to explore available courses</p>
	<p class="centertext"><a href="<%=request.getContextPath()%>/app/browseu" 
		class="btn btn-primary btn-large" id="browse-course">  Browse </a>
  	</p>
</div>
</script>

<script id="certificatetmpl" type="text/x-handlebars-template">
{{#each .}}
<div class="row">
	<div class="col-md-12 padbottom10">
		<a class="btn pull-right btn-blue printcertificatebtn white">Save</a>
		<a class="btn btn-blue backcoursebtn white">Back to Courses</a>
	</div>
</div>
<div id="certificatearea" class="height750">
	<div class="row printarea" id="printarea">
		<h3 class="centertext uppercase" style="margin-top: 340px;">{{userfirstname}} {{userlastname}}</h3>
		<h4 class="centertext" style="margin-top: 90px;">{{coursetitle}}</h4>
		<div class="row">
			<div class="col-md-12">
				<div class="col-md-4 col-md-offset-6">
					<h4 class="centertext" style="margin-top: 40px;">{{authorfirstname}} {{authorlastname}}</h4>
				</div>
			</div>
		</div>		
		<div class="row">
			<div class="col-md-12">
				<div class="col-md-3 col-md-offset-7">
					<h5 class="marleftt7 martop30">{{dateofissue}}</h5>
				</div>
			</div>
		</div>	
	</div>	
</div>
{{/each}}
</script>

<script id="wishlistcoursestmpl" type="text/x-handlebars-template">
<h3 class="martop10 size20">Pending Courses
</h3>
{{#if .}}
{{#each .}}
<div class="row whitebg mar0 marbottom10 boxshadow">
	<div class="col-md-2 pad0">
		<img class="height140 width100 hidden-xs"
			src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo">
	</div>
	<div class="col-md-10 ">
		<h3 class="size17">{{coursetitle}}
			<span class="pull-right" style="color : #000 !important;">Price : <span class="icon_price" priceicon ="<s:text name="label.price"></s:text>" price={{price}} style="color:#0E800E"></span></span>
		</h3>
		<p class="overflowtext">{{coursedescription}}</p>
	</div>
	<form name="wishlistEnrollmentForm" method="post">
       <span class="contentclass" style="color: rgb(148, 147, 147);padding-left:14px;"{{courseenrollmentid}}>By {{firstName}} {{lastName}}</span>
		<a class="btn btn-primary btn-gray pull-right wishenrollBtn" wishListed="wishListed"
			courseid="{{courseid}}" courseenrollmentid="{{courseenrollmentid}}" style="margin-right : 2%;" ><i class="fa fa-unlock-alt"></i>&nbsp;Enroll
			Now</a> <input type='hidden' name="courseenrollmentid" value="">
		<input type='hidden' name="courseid" value="">
		<input type='hidden' name="wishlisted" value="">
	</form>
</div>
{{/each}}
{{else}}
<div class="row-fluid">
	<div class="col-md-12 padtop200 whitebg min450">
		<h4 class="centertext">No courses</h4>
	</div>
</div>
{{/if}}
</script>
<script id="myearningscoursestmpl" type="text/x-handlebars-template">
<h3 class="martop10 size20">My Earnings
</h3>
<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Course Name</th>
			<th>Royalty Amount(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
            <th>Price(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
			<th>No of Users</th>			
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
			<td>{{coursetitle}}</td>
            {{#if authorroyaltyamount}}
				<td>{{authorroyaltyamount}}</td>
             {{else}}
                  <td>--</td>
             {{/if}}
            <td>{{price}}</td>
<td>
				<a href="#" class="authorcoursestudents" price="{{price}}"
				 courseid="{{courseid}}" coursetitle="{{coursetitle}}">{{usercount}}</a>
			</td>
		</tr>
		{{/each}}
{{else}}
		<tr>
			<td colspan="4">Data Not Found</td>
		</tr>
		{{/if}}
	</tbody>
</table>
</script>
<script id="coursestudentsinfotmpl" type="text/x-handlebars-template">
<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Users Name</th>
			<th>Email</th>
				{{firstname}}
		</tr>
	</thead>
	<tbody>

		{{#if .}} {{#each .}}
		<tr>
			<td>{{firstname}}&nbsp{{middlename}}&nbsp;{{lastname}}</td>
			<td>{{#if emailaddress}} <font class=""><i class="icon-envelope"
				style="padding-right: 5px;"></i>{{emailaddress}}</font> {{else}} <font
				class=""><i class="icon-envelope" style="padding-right: 5px;"></i>No
				Email address</font> {{/if}}</td>

		
						
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="3"><p class="center categorydescription">No Users</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</script>
<script id="authorcoursestudentsinfotmpl"
	type="text/x-handlebars-template">

<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Name</th>
			<th>Email</th>
            <th>Author Royalty Amount(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
            <th>Organization Royalty Amount(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
			
				{{firstname}}
		</tr>
	</thead>
	<tbody>

		{{#if .}} {{#each .}}
		<tr>
			<td>{{firstname}}&nbsp{{middlename}}&nbsp;{{lastname}}</td>
			<td>{{#if emailaddress}} <font class=""><i class="icon-envelope"
				style="padding-right: 5px;"></i>{{emailaddress}}</font> {{else}} <font
				class=""><i class="icon-envelope" style="padding-right: 5px;"></i>No
				Email address</font> {{/if}}</td>
         {{#if authorroyaltyamount}}
             <td>{{authorroyaltyamount}}</td>
         {{else}}
             <td>--</td>
         {{/if}}
         {{#if orgroyaltyamount}}
             <td>{{orgroyaltyamount}}</td>
		 {{else}}		
             <td>--</td>
         {{/if}}
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="3"><p class="center categorydescription">No Users</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</script>
<script id="authorfreecoursestudentsinfotmpl"
	type="text/x-handlebars-template">

<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Name</th>
			<th>Email</th>
				{{firstname}}
		</tr>
	</thead>
	<tbody>

		{{#if .}} {{#each .}}
		<tr>
			<td>{{firstname}}&nbsp{{middlename}}&nbsp;{{lastname}}</td>
			<td>{{#if emailaddress}} <font class=""><i class="icon-envelope"
				style="padding-right: 5px;"></i>{{emailaddress}}</font> {{else}} <font
				class=""><i class="icon-envelope" style="padding-right: 5px;"></i>No
				Email address</font> {{/if}}</td>
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="3"><p class="center categorydescription">No Users</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</script>
<script id="paymentrequesttmpl" type="text/x-handlebars-template">
<h3 class="martop10 size20">Approved Payment List</h3>
<table class="table table-bordered table-striped whitebg">
<thead>
<tr>
<th>Sl. No</th>
<th>Date of request</th>
<th>Requested amount(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
<th>Issued Date</th>
</tr>
</thead>
<tbody>
{{#each .}}
<tr>
<td>{{math @index "+" 1}}</td>
<td>{{dateofrequest dateofrequest}}</td>
<td>{{minimumamount}}</td>
<td>{{dateofissue dateofissue}}</td>
</tr>
{{else}}
<tr>
<td colspan="4">Data Not Found</td>
</tr>
{{/each}}
</tbody>
</table>	
</script>
<script id="royaltytotalamounttmplt" type="text/x-handlebars-template">
<h3 class="martop10 size20">Royalty Amount Request <span class="pull-right requestlimitmsg" 
			style="color:#B15315;font-size:15px" priceiconid="<s:text name='label.price'></s:text>"></span></h3>
{{#if .}}
{{#each .}}
<form class="form-horizontal whitebg " style="padding:20px 0;">
<div class='row'>
<div class='col-md-12'>
<div class='col-md-6'>
<div class="form-group">
    <label for="inputEmail3" class="col-sm-6 control-label">Outstanding Amount 
 (<i class="fa <s:text name='label.price'></s:text>"></i>): </label>
    <div class="col-sm-5">
      <label for="inputEmail3" class="lefttext col-sm-12 control-label">{{totalamount}}</label>
    </div>
  </div>
</div>
<div class='col-md-6'>
<div class="form-group">
    <label for="inputPassword3" class="col-sm-6 control-label">Issued Amount (<i class="fa <s:text name='label.price'></s:text>"></i>):</label>
    <div class="col-sm-6">
      <label for="inputPassword3" class="col-sm-12 lefttext control-label"><font class="issueamount"></font></label>
    </div>
  </div>
</div>
</div>
</div>
<div class='row'>
<div class='col-md-12'>
<div class='col-md-6'>
	<div class="form-group">
    	<label for="inputPassword3" class="col-sm-6 control-label">Payment Request (<i class="fa <s:text name='label.price'></s:text>"></i>):</label>
    	<div class="col-sm-6">
      		<label for="inputPassword3" class="col-sm-12 lefttext control-label">
				<span class="paymentrequesticon" ></span><span class="requestlimitamount" style="margin-left:3px;"></span></label>
    	</div>
  	</div>
</div>
<div class='col-md-6'>
	<div class="form-group">
    	<label for="inputPassword3" class="col-sm-6 control-label">Balance (<i class="fa <s:text name='label.price'></s:text>"></i>):</label>
    	<div class="col-sm-6">
      		<label for="inputPassword3" class="lefttext col-sm-12 control-label">
				{{pendingamount}}</label>
    	</div>
  	</div>
</div>
</div>
</div>
<div class='row'>
{{#if dateofrequest}}
<div class='col-md-12'>			
<div class='col-md-12'>
				<div class="form-group">
    				<label for="inputPassword3" class="col-sm-3 control-label">Payment Request On :</label>
    				<div class="col-sm-7">
      					<label for="inputPassword3" class="lefttext col-sm-12 control-label">
						{{dateofrequest dateofrequest}}</label>
    				</div>
  				</div>
			</div>
</div>
	{{else}}
	<div class='col-md-12 centertext'>
      <a class="btn btn-flat btn-success paymentrequestbtn paymentrequestspanleft" style="width: 280px">Payment Request</a>
</div>
	{{/if}}
</div>
</form>
{{/each}}
<div class="col-md-12 padtop10 cornflowerblue">Note: No more request will be sent until the approval of previous one.</div>
{{else}}
<div class='row'>
<div class='col-md-12 centertext'>No Data Found</div>
</div>
{{/if}}
</script>
<script id="errormesstmpl" type="text/x-handlebars-template">
<div class="alert alert-danger alert-dismissible" role="alert">
<strong>Warning!</strong> {{errormessage}}</div>
</script>

<script id="validationtmpl" type="text/x-handlebars-template">
<div class="alert alert-danger alert-dismissible" role="alert">
<strong>Warning!</strong> {{errorvalidation}}</div>
</script>

<script id="duplicatemessagetmpl" type="text/x-handlebars-template">
<div class="alert alert-danger alert-dismissible" role="alert">
<strong>Warning!</strong> {{duplicatemessage}}</div>
</script>
<script id="successmessagetmpl" type="text/x-handlebars-template">
<div class="alert alert-success alert-dismissible" role="alert">
<strong>Success!</strong> {{successmessage}}</div>
</script>
<script id="mailservererrortmpl" type="text/x-handlebars-template">
<div class="alert alert-danger alert-dismissible" role="alert">
{{mailservererror}}
</div>
</script>
<script id="sameuser" type="text/x-handlebars-template">
<div class="alert alert-danger alert-dismissible" role="alert">
<strong>Warning!</strong> {{errorvalidation}}</div>
</script>
</head>
<body class="body">
	
	<span class="pagereferlinks" link="<%=au%>" style="display: none;"></span>
	<div class="alert alert-danger"
		status='<s:property value="%{status}" />'
		enrollstatus='<s:property value="%{EnrollStatus}" />'
		style="display: none;" id='statusID'>
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<strong class="enrollmsg">You have already taken this course</strong>
	</div>

	<div class="container-fluid ">
		<div class="row titlecontainer">
			<div class="col-md-12 lefttext">
				<a>My Courses </a><span class="pull-right searchkwrdbtn"><i
					class="fa fa-search"></i></span>
					<%
						if (orgstatus.equals("M")) {
					%><a class='martop3 btn btn-success pull-right newcoursebtn marright7'
				data-toggle='modal' href='#coursecreationmodal'>				
				<i class='fa fa-plus' style='margin-right: 5px;'></i>New Course
			</a><%
						}
					%>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row dropbox titlecontainer form-horizontal pad5"
			style="display: none;">
			<div class="col-md-12">
				<form name="previewform" method="post">
					<input type="hidden" name="keyword" value=""> <input
						type="hidden" name="courseid" value="">
					<div class="col-sm-1 righttext martop3">Search :</div>
					<div class="col-sm-8 martop7">
						<input class="searchbox form-control" type="text"
							placeholder="Enter Keywords to search">
					</div>
					<div class="col-sm-3 lefttext">
						<a class="btn btn-primary searchbtn marright7"> <i
							class="icon-search marright7"></i>Search
						</a> <a class="btn btn-danger closebtn"> <i
							class="icon-remove-sign marright7"></i>Cancel
						</a>
					</div>
				</form>
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12 pad0">
				<div class="col-md-3 pad0 martop10">
					<ul class="pad0">
						<li class="leftsideliheader">
							<h3>
								My Courses
								<button type="button" class="navbar-toggle mar0 pad0"
									data-toggle="collapse" data-target="#mycoursesdiv"
									aria-expanded="false">
									<span class="sr-only">Toggle navigation</span> <i
										class="fa fa-bars"></i>
								</button>
							</h3>
						</li>
						<div id="mycoursesdiv" class="collapse navbar-collapse pad0">
						  <% if(rolename.equals("tutor")) {%>
						  <li class="leftsideli hover learningcourselink">Teaching<span
								class="count learningcoursescouunt"></span></li>						  						  
						  <% } else { %>
						  <li class="leftsideli hover learningcourselink">Learning<span
								class="count learningcoursescouunt"></span></li>
						  <% } %>
							
							<%-- <%
								if (orgstatus.equals("M")) {
							%>
							<li class="leftsideli hover teachingcourselink">Authoring<span
								class="count teachingcount">0</span></li>
							<%
								} else {
								}
							%> --%>
							<li class="leftsideli hover completedcourselink ">Completed<span
								class="count compcoursecount"></span></li>
							<li class="leftsideli hover wishlistcourselink">Pending<span
								class="count wishlistcount"></span></li>
						</div>

					</ul>
					<%
						if (orgstatus.equals("M")) {
					%>
					<ul class="pad0">
						<li class="leftsideliheader">
							<h3 class="">
								Authoring
								<button type="button" class="navbar-toggle mar0 pad0"
									data-toggle="collapse" data-target="#authoringdiv"
									aria-expanded="false">
									<span class="sr-only">Toggle navigation</span> <i
										class="fa fa-bars"></i>
								</button>
							</h3>
						</li>
						<div id="authoringdiv" class="collapse navbar-collapse pad0">
							<li class="leftsideli hover authcourselink" st="dra">Draft<span
								class="count"></span></li>
							<li class="leftsideli hover authcourselink" st="pub">Published<span
								class="count"></span></li>
								<li class="leftsideli hover authcourselink" st="app">Approved<span
								class="count"></span></li>
								<li class="leftsideli hover authcourselink" st="trash">Trashed<span
								class="count"></span></li>
						</div>
					</ul>
					<ul class="pad0">
						<li class="leftsideliheader">
							<h3 class="">
								My Earnings
								<button type="button" class="navbar-toggle mar0 pad0"
									data-toggle="collapse" data-target="#myearningsdiv"
									aria-expanded="false">
									<span class="sr-only">Toggle navigation</span> <i
										class="fa fa-bars"></i>
								</button>
							</h3>
						</li>
						<div id="myearningsdiv" class="collapse navbar-collapse pad0">
							<li class="leftsideli hover myearnings">Earnings<span
								class="count myearnings"></span></li>
							<li class="leftsideli hover totalamount">Payment Request</li>
							<li class="leftsideli hover paymentrequest">Approved Payment
								List</li>
						</div>
						<%
							}
						%>

					</ul>
				</div>

				<div class="col-md-9 martop10 padright0" id="coursedescriptiontable"></div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="subscribedmodal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">message</h4>
				</div>
				<div class="modal-body">
					<p class="categorydescription ">
						You have already <font class="enrolstatus"></font> this course
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="coursecertificatemodal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Course Certificate</h4>
				</div>
				<div class="modal-body" id="certificatetable"
					style="padding: 0px; margin: 10px; min-height: 430px;"></div>
				<div class="modal-footer">
					<a class="btn btn-flat btn-blue printcertificatebtn">Save</a>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="messagemodal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Course Certificate</h4>
				</div>
				<div class="modal-body">
					<p class="description" style="padding: 10px;">We are processing
						your certificate request. Please wait for a while.</p>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="paymentmessagemodal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Payment Request</h4>
				</div>
				<div class="modal-body">
					<p class="description" style="padding: 10px;">Payment Request
						has been successfully submitted.</p>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="trashrestoremodal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">
						<b id="trashrestoreheader"></b>
					</h4>
				</div>
				<div class="modal-body">
					<p style="padding: 10px;" id="trashrestoremsg"></p>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="coursecreationmodal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Create New Course</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" action="newCourseCreate"
						method="post" name="coursecreationmodalform" id="newCourse">
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-4 control-label">Course
								Title <font style="color: red; margin-left: 2px;">*</font>
							</label>
							<div class="col-sm-8">
								<input type="text" name="coursetitle" id="coursenameid"
									maxlength="50"
									class="form-control coursetitleduplicate cleanup"
									pattern="[a-zA-Z0-9.\s]+" placeholder="Enter Course title">
								<div class="alert alert-error coursetitleduplicatebar"
									hidden="true">
									<strong>Message!</strong>&nbsp;&nbsp;&nbsp; <b><command
											id="coursetitleduplicatemsg"></command></b>
								</div>
								<div class='alert alert-danger' style='display: none;'
									id='coursetitleerror'></div>
								<div class='alert alert-danger' style='display: none;'
									id='courseduplicateerror'></div>

								<div class='alert alert-danger ' style='display: none;'
									id='emptyerror'></div>
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-4 control-label">Course
								Description <font style="color: red; margin-left: 2px;">*</font>
							</label>
							<div class="col-sm-8">
								<textarea rows="4" cols="43" class="form-control cleanup"
									maxlength="1000" name="coursedescription" id="coursedescid"
									placeholder="Enter Course Description here..."></textarea>
								<div class='alert alert-danger' style='display: none;'
									id='coursedecerror'></div>
							</div>
						</div>
						<div class="form-group centertext martop10">
							<div class="col-sm-10">
								<a class="btn btn-primary" id="createcoursesubmit">Submit</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="authorcoursemodal">
		<div class="modal-dialog width60per" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title size20">
						Users List - <span id="coursetitlespan"></span>
					</h4>
				</div>
				<div class="modal-body">
					<div class="row profileinfobody">
						<div class="col-md-12">
							<div class="col-md-11" id="authorcoursestudentinfo"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="alertamountchkmsg">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title size20">Payment Request</h4>
				</div>
				<div class="modal-body">
					<p font-size:15px;" class="paymentrequstmsg"></p>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="alertmsg">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title size20">Payment Request</h4>
				</div>
				<div class="modal-body">
					<pfont-size:15px;">We are processing your previous request, please wait...</p>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="inviteusersmodal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title size20">Invite New Users</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" action="" method="post"
						name="invitenewusersform">
						<div id="uploadextfilediv"></div>
						<div class="form-group" id="emaildiv">
							<label for="inputEmail3" class="col-sm-4 control-label">Email
								address <span style="color: red; margin-right: 10px;">*</span>
							</label>
							<div class="col-sm-7">
								<textarea rows="4" cols="45"
									class="col-md-8 form-control emailuser" data-role="tagsinput"
									required="required" name="emailuser" id="emailuser"
									style="resize: none;" placeholder="Enter Email address here..."></textarea>
								<a class='btn btn-primary uploadextfile martop10'><i
									class="fa fa-upload marright7"></i>Upload From File</a>
							</div>
						</div>

						<div class="form-group">
							<label for="inputEmail3" class="col-sm-4 control-label">Message
								<span style="color: red; margin-right: 10px;">*</span>
							</label>
							<div class="col-sm-7">
								<textarea rows="4" cols="45" class="col-md-8 form-control"
									required="required" name="messagedescription"
									id="messagedescription" style="resize: none;"
									placeholder="Enter Message here..."></textarea>
								<p class="help-block">
									<font class="mesdesccharremaining">500</font> Characters
									Remaining
								</p>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12" style="margin-left: 0px;">
								<div id="errormessage" style="display: none;"></div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 centertext">
								<input type="button" class="btn btn-success sendinvitation"
									value="Send Invitations"> <a class="btn sendingmess"
									style="display: none;"> Message is Sending .... <img alt=""
									src="<%=request.getContextPath()%>/assets/app/images/ajax-loader6.gif">
								</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<span class="emailval" val="<%=email%>"></span>
	<form action="searchresult" method="post" name="searchresultform">
		<input type="hidden" name="keyword" value="">
	</form>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/livevalidation/js/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/html2canvas.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/jspdf.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/jspdf/FileSaver.js"></script>
	<script
		src="<%=request.getContextPath()%>/assets/app/js/users/mycourses.js"
		type="text/javascript"></script>
	<script
		src="<%=request.getContextPath()%>/assets/app/js/course/emailxl.js"
		type="text/javascript"></script>
</body>
</html>