<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page
	import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin User Management</title>
<style type="text/css">
.mintablebody {
	height: auto;
	min-height: 600px;
}
.btn{
  border-radius: 0px;
}
.form-control{
 border-radius: 0px;
}
.badge{
  background-color: #14bdee;
}
</style>
<script id="registeredUserstmpl" type="text/x-handlebars-template">
<table class="table table-striped table-bordered whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Name</th>
			<th>Email Address</th>
			<th>Action</th>
			<th>Assign</th>
		</tr>
	</thead>
	{{#if .}} 
	<tbody class="mintablebody">
		{{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td><a class="userinfo" orgpersonid="{{orgpersonid}}">{{firstname}} {{lastname}}</a></td>
			<td>{{#if emailid}}{{emailid}}{{else}} -- {{/if}}</td>
			<td>
				<a class="btn btn-primary sendsinglemessage" personname ="{{firstname}} {{lastname}}" orgpersonid="{{orgpersonid}}" title="Click Here to Send Message to {{firstname}} {{lastname}}"><i class="fa fa-envelope"></i></a>
				<a class="btn btn-danger blockuser" orgpersonid="{{orgpersonid}}" personname ="{{firstname}} {{lastname}}" title="Click Here to Deactivate {{firstname}} {{lastname}}"><i class="fa fa-remove"></i></a>
			</td>
			<td>
				{{{role roleid personallocationid firstname lastname}}}
			</td>			
		</tr>
		{{/each}}
	</tbody> 
	{{else}}
	<tbody>		
		<tr class="centertext">
			<td colspan="4">No Users</td>
		</tr>
	</tbody>
	{{/if}}	
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="enrolledUserstmpl" type="text/x-handlebars-template">
<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Name</th>
			<th>Email Address</th>
			<th class="centertext">Courses Count</th>
		</tr>
	</thead>
	{{#if .}} 
	<tbody class="mintablebody">
	{{#each .}}	
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td><a class="userinfo" orgpersonid="{{orgpersonid}}">{{firstname}} {{lastname}}</a></td>
			<td>{{#if emailid}}{{emailid}}{{else}} -- {{/if}}</td>
			<td class="centertext"><a class="badge pad612 enrolledcourses" orgpersonid="{{orgpersonid}}">{{coursecount}}</a></td>
		</tr>
	{{/each}}	
	</tbody>
	 {{else}}
	<tbody>
		<tr class="centertext">
			<td colspan="4">No Users</td>
		</tr>
	</tbody>
	{{/if}}	
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="enrolledCoursestmpl" type="text/x-handlebars-template">
<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Course</th>
			<th>Status</th>			
		</tr>
	</thead>
	{{#if .}} 
	<tbody class="mintablebody">
	{{#each .}}	
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{coursetitle}}</td>
			<td>{{enrollstatus courseenrollmentstatus}}</td>			
		</tr>
	{{/each}}	
	</tbody>
	 {{else}}
	<tbody>
		<tr class="centertext">
			<td colspan="3">No Courses</td>
		</tr>
	</tbody>
	{{/if}}	
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="paidUserstmpl" type="text/x-handlebars-template">
<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Name</th>
			<th>Email Address</th>
			<th>Action</th>
		</tr>
	</thead>
	{{#if .}} 
	<tbody class="mintablebody">
		{{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td><a class="userinfo" orgpersonid="{{orgpersonid}}">{{firstname}} {{lastname}}</a></td>
			<td>{{#if emailid}}{{emailid}}{{else}} -- {{/if}}</td>
			<td><a class="btn btn-primary sendsinglemessage" personname ="{{firstname}} {{lastname}}" orgpersonid="{{orgpersonid}}" 
				title="Click Here to Send Message to {{firstname}} {{lastname}}"><i class="fa fa-envelope"></i></a></td>
		</tr>
		{{/each}} 
	</tbody>
	{{else}}
	<tbody>
		<tr class="centertext">
			<td colspan="4">No Users</td>
		</tr>
	</tbody>
	{{/if}}	
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="deactivatedUserstmpl" type="text/x-handlebars-template">
<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Name</th>
			<th>Email Address</th>
			<th>Action</th>
		</tr>
	</thead>
	{{#if .}} 
		<tbody class="mintablebody">
		{{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td><a class="userinfo" orgpersonid="{{orgpersonid}}">{{firstname}} {{lastname}}</a></td>
			<td>{{#if emailid}}{{emailid}}{{else}} -- {{/if}}</td>
			<td><a class="btn btn-default activateuser" orgpersonid="{{orgpersonid}}" 
				personname ="{{firstname}} {{lastname}}" 
				title="Click Here to Activate {{firstname}} {{lastname}}">Activate</a></td>
				
		</tr>
	{{/each}} 
	</tbody>
	{{else}}
	<tbody>		
		<tr class="centertext">
			<td colspan="4">No Users</td>
		</tr>
	</tbody>
	{{/if}}	
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="authorstmpl" type="text/x-handlebars-template">
<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Name</th>
			<th>Email Address</th>
			<th class="centertext">Courses Count</th>
		</tr>
	</thead>
	
		{{#if .}} 
	<tbody class="mintablebody">
	{{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{firstname}} {{lastname}}</td>
			<td>{{#if emailid}}{{emailid}}{{else}} -- {{/if}}</td>
			<td class="centertext"><a class="badge pad612 authorcourseslink" 
				personname ="{{firstname}} {{lastname}}" orgpersonid="{{orgpersonid}}">{{courses}}</a></td>
		</tr>
		{{/each}}</tbody> {{else}}
<tbody>
		<tr class="centertext">
			<td colspan="4">No Users</td>
		</tr></tbody>
		{{/if}}
	
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="authorcoursestmpl" type="text/x-handlebars-template">
<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Course</th>
			<th class="centertext">Users Count</th>
		</tr>
	</thead>	
		{{#if .}} 
	<tbody class="mintablebody">
	{{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{coursetitle}}</td>
			<td class="centertext"><a class="badge pad612 usercountlink" coursetitle="{{coursetitle}}" 
				courseid="{{courseid}}">{{studentcount}}</a></td>
		</tr>
		{{/each}}</tbody> {{else}}
		<tbody>
		<tr class="centertext">
			<td colspan="3">No Courses</td>
		</tr></tbody>
		{{/if}}
	
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="authorcourseuserstmpl" type="text/x-handlebars-template">
<h5 class="size20">Enrolled Users for <font class="authorcourseusertitle breakword"></font>
	<a class="btn btn-success pull-right backtoauthorcourses marbottom10"> << Courses</a></h5>
<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Name</th>
			<th>Email Address</th>			
		</tr>
	</thead>
	
		{{#if .}} 
	<tbody class="mintablebody">
	{{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{firstname}} {{lastname}}</td>
			<td class="breakword">{{#if emailid}}{{emailid}}{{else}} -- {{/if}}</td>
			
		</tr>
		{{/each}}</tbody> {{else}}
<tbody>
		<tr class="centertext">
			<td colspan="3">No Users</td>
		</tr></tbody>
		{{/if}}
	
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>
<script id="invitedUserstmpl" type="text/x-handlebars-template">
<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Email Address</th>
			<th>Date Of Invitation</th>
			<th>Status</th>
		</tr>
	</thead>
	<tbody>
	{{#if .}} 
	<tbody class="mintablebody">	
	{{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{#if emailid}}{{emailid}}{{else}} -- {{/if}}</td>
			<td>{{df dateofinvitation}}</td>
			<td class="courseinvitationval">{{{email courseinvitationstatus}}}<span class="courseinvitationidval" style="display:none;">{{courseinvitationid}}</span>
			<span class="emailval" style="display:none;">{{emailid}}</span><span class="messageval" style="display:none;">{{message}}</span></td>
		</tr>
	{{/each}}		
	</tbody>
	{{else}}
	<tbody>		
		<tr class="centertext">
			<td colspan="3">No Users</td>
		</tr>
	</tbody>
	{{/if}}
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>
<script id="onlinePaidUserstmpl" type="text/x-handlebars-template">
<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Name</th>
			<th>Course</th>
			<th>Order No</th>
			<th>Paid On</th>
		</tr>
	</thead>
	<tbody>
	{{#if .}} 
	<tbody class="mintablebody">	
	{{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{firstname}} {{lastName}}</td>
			<td>{{coursetitle}}</td>
			<td>{{orderno}}</td>
			<td>{{paymentdate}}</td>
		</tr>
	{{/each}}		
	</tbody>
	{{else}}
	<tbody>		
		<tr class="centertext">
			<td colspan="5">No Requests</td>
		</tr>
	</tbody>
	{{/if}}
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>
<script id="freePaidUserstmpl" type="text/x-handlebars-template">
<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Name</th>
			<th>Course</th>
			<th>Order No</th>
			<th>Paid On</th>
		</tr>
	</thead>
	<tbody>
	{{#if .}} 
	<tbody class="mintablebody">	
	{{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{firstname}} {{lastName}}</td>
			<td>{{coursetitle}}</td>
			<td>{{orderno}}</td>
			<td>{{paymentdate}}</td>
		</tr>
	{{/each}}		
	</tbody>
	{{else}}
	<tbody>		
		<tr class="centertext">
			<td colspan="5">No Requests</td>
		</tr>
	</tbody>
	{{/if}}
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>
<script id="offlinependingtmpl" type="text/x-handlebars-template">
<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Name</th>
			<th>Course</th>
			<th>Order No</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
	{{#if .}} 
	<tbody class="mintablebody">	
	{{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{firstname}} {{lastName}}</td>
			<td>{{coursetitle}}</td>
			<td>{{orderno}}</td>
			<td>
				<a class="btn btn-success paymentapprovebtn" courseenrollmentid="{{courseenrollmentid}}" 
					paymentid="{{paymentid}}">Approve</a>
			</td>
		</tr>
	{{/each}}		
	</tbody>
	{{else}}
	<tbody>		
		<tr class="centertext">
			<td colspan="5">No Requests</td>
		</tr>
	</tbody>
	{{/if}}
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>
<script id="offlinePaidpaymentstmpl" type="text/x-handlebars-template">
<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Name</th>
			<th>Course</th>
			<th>Order No</th>
			<th>Paid On</th>
		</tr>
	</thead>
	<tbody>
	{{#if .}} 
	<tbody class="mintablebody">	
	{{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{firstname}} {{lastName}}</td>
			<td>{{coursetitle}}</td>
			<td>{{orderno}}</td>
			<td>{{paymentdate}}</td>
		</tr>
	{{/each}}		
	</tbody>
	{{else}}
	<tbody>		
		<tr class="centertext">
			<td colspan="5">No Requests</td>
		</tr>
	</tbody>
	{{/if}}
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="allCertificateRequesttmpl" type="text/x-handlebars-template">
<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Name</th>
			<th>Course</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
	{{#if .}} 
	<tbody class="mintablebody">	
	{{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{firstname}} {{lastname}}</td>
			<td>{{coursetitle}}</td>
			<td>{{certificatestatus coursecertificateid coursecertificatestatus}}</td>
		</tr>
	{{/each}}		
	</tbody>
	{{else}}
	<tbody>		
		<tr class="centertext">
			<td colspan="4">No Requests</td>
		</tr>
	</tbody>
	{{/if}}
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="pendingCertificateRequesttmpl"
	type="text/x-handlebars-template">
<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Name</th>
			<th>Course</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
	{{#if .}} 
	<tbody class="mintablebody">	
	{{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{firstname}} {{lastname}}</td>
			<td>{{coursetitle}}</td>
			<td>{{certificatestatus coursecertificateid coursecertificatestatus}}</td>
		</tr>
	{{/each}}		
	</tbody>
	{{else}}
	<tbody>		
		<tr class="centertext">
			<td colspan="4">No Requests</td>
		</tr>
	</tbody>
	{{/if}}
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="approvedCertificateRequesttmpl"
	type="text/x-handlebars-template">
<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Name</th>
			<th>Course</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
	{{#if .}} 
	<tbody class="mintablebody">	
	{{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{firstname}} {{lastname}}</td>
			<td>{{coursetitle}}</td>
			<td><font class="label label-success pad612">Approved</font></td>
		</tr>
	{{/each}}		
	</tbody>
	{{else}}
	<tbody>		
		<tr class="centertext">
			<td colspan="4">No Requests</td>
		</tr>
	</tbody>
	{{/if}}
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>
<script id="commonusersuccesstmpl" type="text/x-handlebars-template">
<div class="alert alert-success" role="alert">
      <strong>Success!</strong> <font class="commonuseralertmsg"></font></a>.
    </div>
</script>

<script id="commonuserfailuretmpl" type="text/x-handlebars-template">
<div class="alert alert-danger" role="alert">
      <strong>Failure!</strong> <font class="commonuseralertmsg"></font></a>.
    </div>
</script>

<script id="commonuserheadertmpl" type="text/x-handlebars-template">
<div class="row">
	<div class="col-md-12">
		<div class="col-md-4 pad0">
			<h5 class="size20 rightpaneusertitle">Users</h5>
		</div>
		<div class="col-md-8 pad0">
			<div class="col-md-10">
				<div class="col-md-12 pad0">
					<div class="col-md-8 pad0">
						<input type="text" class="form-control usersearchinput col-md-12"
							placeholder="Search User">
					</div>
					<div class="col-md-4"><a class="btn btn-success pull-left alluser">Reset Search</a>
					</div>
				</div>		
			</div>
			<div class="col-md-2 padright0 pull-right ">
				<a class="btn btn-success pull-right commonasuseractions messageuser"><i
					class="fa fa-envelope"></i></a> <a
					class="btn btn-success pull-right marright7 commonasuseractions notifyuser"><i
					class="fa fa-bell "></i></a> 
			</div>
		</div>
	</div>
</div>
<div class="container-fluid dropbox" style="display: none;">
	<div class="row dropbox titlecontainer form-horizontal pad5">
		<div class="col-md-12">
			<div class="col-sm-8 martop7">
				<textarea class="form-control sendcommomtextarea" type="text"
					placeholder="Enter Notification"></textarea>
				<div
					class="alert alert-success commouseralert sent lineheight20 mar0"
					style="display: none">
					<font class="commonusermsg">Please don't leave it blank.</font>
				</div>
				<div
					class="alert alert-danger commouseralert errorinsend lineheight20 mar0"
					style="display: none">
					<font class="commonusermsg">Please don't leave it blank.</font>
				</div>
			</div>
			<div class="col-sm-4 lefttext martop10">

				<a class="btn btn-primary sendcommonbtn marright7"> Send
					Notification </a> <a class="btn btn-danger closebtn"> Cancel </a>
			</div>
		</div>
	</div>
</div>
</script>

<script id="validationtmpl" type="text/x-handlebars-template">
<div class="alert alert-danger alert-dismissible" role="alert">
<strong>Warning!</strong> {{errorvalidation}}</div>
</script>

<script id="duplicatemessagetmpl" type="text/x-handlebars-template">
<div class="alert alert-danger alert-dismissible" role="alert">
<strong>Warning!</strong>{{duplicatemessage}}</div>
</script>

<script id="errormesstmpl" type="text/x-handlebars-template">
<div class="alert alert-danger alert-dismissible" role="alert">
<strong>Warning!</strong> {{errormessage}}</div>
</script>

<script id="profileinfotmpl" type="text/x-handlebars-template">
{{#each .}}
<form class="form-horizontal">
	<div class="form-group">
		<label class="control-label col-md-5 righttext">Name : </label>
		<div class="controls-label col-md-7">{{firstName}} {{lastName}}
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-5 righttext">Date of birth : </label>
		<div class="controls-label col-md-7">{{#if dob}} {{dob}}
			{{else}} -- {{/if}}</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-5 righttext">Contact Number : </label>
		<div class="controls-label col-md-7">{{#if number}} {{number}}
			{{else}} -- {{/if}}</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-5 righttext">Qualification : </label>
		<div class="controls-label col-md-7">{{#if eduqualification}}
			{{eduqualification}} {{else}} -- {{/if}}</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-5 righttext">Email : </label>
		<div class="controls-label col-md-7">{{#if userid}} {{userid}}
			{{else}} -- {{/if}}</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-5 righttext">Photo : </label>
		<div class="controls-label col-md-4">
			<img
				src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{personphotourl}}&filetype=photo"
				class="courselogo thumbnailimage width100 height140"></img> <input
				type="hidden" name="courselogo" value="{{personphotourl}}">
		</div>
	</div>
</form>
{{/each}}
</script>

<script id="coursesinfotmpl" type="text/x-handlebars-template">
{{#if .}}

<div class="tabbable"> <!-- Only required for left/right tabs -->
  <ul class="nav nav-tabs pad0 mar0 marbottom10">
    <li class="borderbottoma active"><a href="#tab1" data-toggle="tab" class="studingcourselist" >Learning</a></li>
    <li class="borderbottoma"><a href="#tab2" data-toggle="tab" class="compleatedcourselist">Completed</a></li>
	<li class="borderbottoma"><a href="#tab3" data-toggle="tab" class="wishcourselist">Wish-Listed</a></li>
	<li class="borderbottoma"><a href="#tab4" data-toggle="tab" class="blockedcourselist">Blocked</a></li>  
</ul>
  <div class="tab-content">
    <div class="tab-pane active" id="tab1">
{{#if courseliststatusA}}
      <table class="table table-bordered table-striped whitebg list-search">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Course Name</th>
			<th>Progress Details in %</th>
					
		</tr>
	</thead>
	<tbody>

		{{#each courseliststatusA}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{coursetitle}} </td>
			{{#if courseprogress}}
			<td>{{courseprogress}}</td>
			{{else}}
			<td>--</td>
			{{/if}}
		</tr>
{{/each}}
{{else}}
<td class="centertext">No Learning Courses</td>
{{/if}}
</tbody>
</table>
    </div>
    <div class="tab-pane" id="tab2">
	{{#if courseliststatusC}}     
 	<table class="table table-bordered table-striped whitebg list-search">
		<thead>
		<tr>
			<th>Sl.No</th>
			<th>Course Name</th>			
		</tr>
	</thead>
	<tbody>
	
		{{#each courseliststatusC}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{coursetitle}} </td>
			
		</tr>
		{{/each}}

	</tbody>
	</table>
	{{else}}
	<td>No Completed Courses</td>
	{{/if}}
    </div>
	<div class="tab-pane" id="tab3">
{{#if courseliststatusW}} 
     <table class="table table-bordered table-striped whitebg list-search">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Course Name</th>			
		</tr>
	</thead>
	<tbody>
		{{#each courseliststatusW}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{coursetitle}} </td>
		</tr>
{{/each}}
</tbody>
</table>
{{else}}
	<td>No Wishlist Courses</td>
	{{/if}}
    </div>

<div class="tab-pane" id="tab4">
{{#if courseliststatusB}} 
     <table class="table table-bordered table-striped whitebg list-search">
	<thead>
		<tr>		
			<th>Sl.No</th>
			<th>Course Name</th>
			
		</tr>
	</thead>
	<tbody>
		{{#each courseliststatusB}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{coursetitle}} </td>
			
		</tr>
{{/each}}
</tbody>
</table>
{{else}}
	<td>No Blocked Courses</td>
	{{/if}}
    </div>
  </div>
</div>
 
{{else}}
<h5>No Subscribed Courses</h5>
{{/if}}
</script>

</head>
<body>
<%
		UserDetailsAdaptor userDetailsAdaptor = null;
		userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String orgstatus = userDetailsAdaptor.getUser().getProperty(
				"orgstatus");
		String email = userDetailsAdaptor.getUser().getProperty(
				"email");
	%>
	<div id="" class="useralertholder"></div>
	<div class="alert alert-danger maxcourseuser" style="display: none;">
		Your current plan does not allow creation of users more than <font class="maxusercount">2</font>. Consider upgrading your Plan <a class="orgplanlink">Click hear for plan</a>
	</div>
	<span class="linkvalredirect" style="display: none"
		aa="<%=request.getParameter("a")%>"></span>
	<div class="row titlecontainer">
		<div class="row">
			<div class="col-md-12 lefttext">
				<a href="<%=request.getContextPath()%>/app/dashboard" style="color: #384158;margin-left: 100px;">Dashboard
				</a> / Users<a class="btn btn-info inviteusermodal pull-right martop3" style="margin-right: 105px;">Invite
					User</a>
			</div>
		</div>
	</div>
	
	
	<%-- <div class="">
		<div class="row titlecontainer">
			<div class="col-md-12 lefttext">
				<a href="<%=request.getContextPath()%>/app/dashboard" style="color: #384158;margin-left: 100px;">Dashboard
				</a> / <a>Categories </a> <span class="pull-right searchbtn" style="margin-right: 130px;"><i
					class="fa fa-search" style="font-size: 20px;"></i></span>
			</div>
		</div>
	</div>
	 --%>
	
	
	
	<div class="container-fluid">
	<div class="row">
		<div class="col-md-12 lefttext alert alert-success tutoralerts" style="display: none;"><span class="username"></span>
			is successfully assign tutor</div>
		</div>
		</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12 pad0">
				<div class="col-md-3 pad0" style="box-shadow: 0px 1px 10px rgba(40,45,47,0.30);height: 775px;background: white;">
					<div class="leftsideul martop10">
						<li class="leftsideliheader">
							<h3>
								Users
								<button type="button" class="navbar-toggle mar0 pad0"
									data-toggle="collapse" data-target="#usersdiv"
									aria-expanded="false">
									<span class="sr-only">Toggle navigation</span> <i
										class="fa fa-bars"></i>
								</button>
							</h3>
						</li>
						<div id="usersdiv" class="collapse navbar-collapse pad0">
							<li class="leftsideli leftsidelink hover" al="registered"
								searchval="name">Registered Users <span class="count">0</span>
							</li>
							<li class="leftsideli leftsidelink hover" al="enrolled"
								searchval="name">Enrolled Users <span class="count">0</span>
							</li>
							<li class="leftsideli leftsidelink hover" al="paid"
								searchval="name">Paid Users <span class="count">0</span>
							</li>
							<li class="leftsideli leftsidelink hover" al="deactivated"
								searchval="name">Deactivated Users <span class="count">0</span>
							</li>
							<%
								if (orgstatus.equals("M")) {
							%>
							<li class="leftsideli leftsidelink hover" al="author"
								searchval="name">Authors <span
								class="count removeusercount">0</span>
							</li>
								<%
								}
							%>
							<li class="leftsideli leftsidelink hover" al="invited"
								searchval="email">Invited Users List <span class="count">0</span>
							</li>
						</div>
					</div>
					<div class="leftsideul martop10">
						<li class="leftsideliheader">
							<h3>
								Payment
								<button type="button" class="navbar-toggle mar0 pad0"
									data-toggle="collapse" data-target="#approvaldiv"
									aria-expanded="false">
									<span class="sr-only">Toggle navigation</span> <i
										class="fa fa-bars"></i>
								</button>
							</h3>
						</li>
						<div id="approvaldiv" class="collapse navbar-collapse pad0">
							<li class="leftsideli leftsidelink hover" al="onlinepayment"
								searchval="payment">Online <span class="count">0</span>
							</li>
							<li class="leftsideli leftsidelink hover" al="freepayment"
								searchval="payment">Free <span class="count">0</span>
							</li>
							<li class="leftsideli leftsidelink hover" al="pendingpayment"
								searchval="payment">Pending <span class="count">0</span>
							</li>
							<li class="leftsideli leftsidelink hover" al="approvedpayment"
								searchval="payment">Approved <span class="count">0</span>
							</li>
						</div>
					</div>
					<div class="leftsideul martop10">
						<li class="leftsideliheader">
							<h3>
								Certificates
								<button type="button" class="navbar-toggle mar0 pad0"
									data-toggle="collapse" data-target="#certificatediv"
									aria-expanded="false">
									<span class="sr-only">Toggle navigation</span> <i
										class="fa fa-bars"></i>
								</button>
							</h3>
						</li>
						<div id="certificatediv" class="collapse navbar-collapse pad0">
							<li class="leftsideli leftsidelink hover" al="allcertificate"
								searchval="certificate">All <span class="count">0</span>
							</li>
							<li class="leftsideli leftsidelink hover" al="pendingcertificate"
								searchval="certificate">Pending <span class="count">0</span>
							</li>
							<li class="leftsideli leftsidelink hover" al="issuedcertificate"
								searchval="certificate">Issued <span class="count">0</span>
							</li>
						</div>
					</div>
				</div>
				<div class="col-md-9 martop10 padright0">

					<div id="commonuserheaderholder"></div>
					<div id="userholder"></div>
				</div>
			</div>
		</div>
	</div>


	<div id="usercommonmodal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 id="userModalLabel" class="modal-title">User Details</h4>
				</div>
				<div class="modal-body usercommonmodalbody pad612"></div>
			</div>
		</div>
	</div>


	<div class="modal fade" id="moreinfomodal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title size20">User More Information</h4>
				</div>
				<div class="modal-body">
					<ul id="" class="nav nav-tabs firsttab pad0 mar0 marbottom10">
						<li class="borderbottoma active"><a class="learningtablinks"
							href="#profileinfotab" data-toggle="tab">Profile Information</a></li>
						<li class="borderbottoma"><a class="learningtablinks"
							href="#coursestab" data-toggle="tab">Courses</a></li>
					</ul>
					<div class="tab-content profileinfobody">
						<div class="tab-pane active" id="profileinfotab">
							<div id="profileinfotable"></div>
						</div>
						<div class="tab-pane" id="coursestab">
							<div id="coursesinfotable"></div>
						</div>
					</div>
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
					<div class="form-horizontal">
						<div class="row-fluid">
							<div id="uploadextfilediv"></div>
						</div>
						<div id="emaildiv" class="form-group">
							<label class="col-sm-4 righttext">Email Address <span
								style="color: red; margin-right: 10px;">*</span> :
							</label>
							<div class="col-sm-8">
								<textarea rows="4" cols="50"
									class="col-sm-8 emailuser form-control" required="required"
									name="emailuser" id="emailuser" style="resize: none;"
									placeholder="Enter Email address here..."></textarea>
								<span class="icon-upload uploadextfile"></span>
							</div>

						</div>

						<div class="form-group">
							<label class="control-label col-sm-4 righttext">Message :
								<span style="color: red; margin-right: 10px;">*</span>
							</label>
							<div class="col-sm-8">
								<textarea rows="4" cols="50" class="col-sm-8 form-control"
									required="required" name="messagedescription"
									id="messagedescription" style="resize: none;"
									placeholder="Enter Message here..."></textarea>
								<p class="help-block">
									<font class="mesdesccharremaining">500</font> Characters
									Remaining
								</p>
							</div>
						</div>
						<div class="form-group attchmentdiv righttext"
							style="display: none">
							<label class="col-sm-4">Attachment <span
								style="color: red; margin-right: 10px;">*</span>
							</label>
							<div class="col-sm-8">
								<font class="attchmentfilename pull-left marright7 breakword" atfilename=""></font>
								<a class="btn btn-danger pull-left attclose"><i class="fa fa-times"></i></a>
							</div>
						</div>

						<form enctype="multipart/form-data" name="upattachemntform"
							method="post" action="../rest/course/mailattach">
							<div class="form-group">
								<label class="control-label col-sm-4 righttext" for="filebutton">Attachment
									: </label>
								<div class="col-sm-8">
									<input name="mattachment" class="input-file" type="file">
									<p class="help-block" style="margin: 0px; color: red;">Note
										: only pdf format allowed</p>
									<input type="hidden" name="attachmentsizeval" value="">
								</div>
							</div>
							<div class="alert alert-warning page-alert" id="invalidalert"
								style="display: none;">
								<button type="button" class="close">
									<span aria-hidden="true">×</span>
								</button>
								<strong>Warning!</strong> In Valid File Format.
							</div>
							<div class="alert alert-danger errblcok" style="display: none;">
								<button type="button" class="close" data-dismiss="alert">&times;</button>
								<strong class="strbtn">Warning!</strong> <font class="emsg">Best
									check yo self, you're not looking too good.</font>
							</div>
							<div class="form-group prggrop" style="display: none;">
								<font class="progressper">0% </font>Uploaded <span
									class="pull-right">Please wait...</span>
								<div class="progress progress-striped active">
									<div class="bar" style="width: 0%;"></div>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4 righttext"
									for="singlebutton"></label>
								<div class="col-sm-4">
									<input type="submit" value="Attach"
										class="btn btn-flat btn-success uploadcnt">
								</div>
							</div>
						</form>
						<div class="col-md-12" style="margin-left: 0px;">
							<div id="errormessage"></div>
						</div>
						<div class="alert alert-danger alert-dismissible inviteuseralert"
							style="display: none;"></div>
						<div class="form-group">
							<label class="control-label col-sm-4 righttext"
								for="singlebutton"></label>
							<div class="col-sm-4">
								<input type="button" class="btn btn-blue sendinvitation white"
									value="Send Invitations"> <a class="btn sendingmess">
									Message is Sending .... <img alt=""
									src="<%=request.getContextPath()%>/assets/app/images/ajax-loader6.gif">
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="inviteusermodals">
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
					<div class="form-horizontal">
						<div class="row-fluid">
							<div id="uploadextfilediv"></div>
						</div>
						<div id="emaildiv" class="form-group">
							<label class="col-sm-4 righttext">Email Address <span
								style="color: red; margin-right: 10px;">*</span> :
							</label>
							<div class="col-sm-8">
								<span class="emailusertxt"></span>
								<a class="btn btn-primary emailedit">Edit</a>
								<input type="hidden" class="emailIdval">
								<input rows="4" cols="40"
									class="col-sm-8 emailusers form-control" required="required"
									name="emailuser" style="resize: none;display:none;"
									placeholder="Enter Email address here..."/>
									<a class="btn btn-primary emailupdate martop10" style="display:none;">Update</a>
								<span class="icon-upload uploadextfile"></span>
							</div>

						</div>

						<div class="form-group" style="display:none;">
							<label class="control-label col-sm-4 righttext">Message :
								<span style="color: red; margin-right: 10px;">*</span>
							</label>
							<div class="col-sm-8">
								<textarea rows="4" cols="50" class="col-sm-8 form-control msgdescription"
									required="required" name="messagedescription"
									style="resize: none;display:none;" placeholder="Enter Message here..."></textarea>
							</div>
						</div>
						<div class="col-md-12" style="margin-left: 0px;">
							<div id="errormessage"></div>
						</div>
						<div class="alert alert-danger alert-dismissible inviteuseralert"
							style="display: none;"></div>
						<div class="form-group">
							<label class="control-label col-sm-4 righttext"
								for="singlebutton"></label>
							<div class="col-sm-4">
								<input type="button" class="btn btn-blue sendinvite white"
									value="Send Invitations"> <a class="btn sendingmess">
									Message is Sending .... <img alt=""
									src="<%=request.getContextPath()%>/assets/app/images/ajax-loader6.gif">
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" value="<%=email%>" class="orgemail">
	
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/formjs/jquery.form.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/admin/adminusers.js"></script>
</body>
</html>