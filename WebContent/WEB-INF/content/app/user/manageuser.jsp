<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page
	import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script id="allusertmpl" type="text/x-handlebars-template">
<h3 class="cattitle padleft0 marleft0 martop10 marbottom15 size20">Subscribed User Details
</h3>
<label class="coursehiddenid"
			style="display: none;"></label>
<table class="table table-striped whitebg paginationtab">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Name</th>
			<th>Email</th>
			<th class="center">Actions</th>			
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td><a class="moreinfouserbtn" orgpersonid="{{personid}}">
					<font class="capitalise">{{username}} {{lastName}}</font>
				</a>
			</td>
			<td>{{#if emailaddress}}{{emailaddress}}{{else}} No Email address {{/if}}</td>
			<td class="center"><a class="btn btn-primary btn-userinfo messageuserbtn"
				personname="{{username}}" orgpersonid="{{personid}}"><i class="fa fa-envelope"></i></a></td>
						
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="4"><p class="centertext categorydescription">No Users</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="invitingusertmpl" type="text/x-handlebars-template">
<h3 class="cattitle padleft0 marleft0 martop10 marbottom15 size20">Invited User Lists</h3> 
<table class="table table-striped whitebg paginationtab">
	<thead>
		<tr>
            <th>Sl.No</th>
			<th>Email</th>
			<th>Date Of Invitation</th>
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
            <td>{{math @index "+" 1}}</td>
			<td>{{#if emailid}} <font class=""><i class="icon-envelope"
				style="padding-right: 5px;"></i>{{emailid}}</font> {{else}} <font
				class=""><i class="icon-envelope" style="padding-right: 5px;"></i>No
				Email address</font> {{/if}}</td>
		    <td>{{#if registereddate}}
                        {{registereddate}}
                {{else}}
                      -
                {{/if}}
           </td>						
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan='3'><p class="centertext categorydescription">No Users</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="paymentdetailstmpl" type="text/x-handlebars-template">
<h3 class="rightpanetitle padleft0 marleft0 martop10 marbottom15 
	size20" paymentstatus="" paymenttype="">Payment Details</h3>
<table class="table table-striped whitebg paginationtab">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Name</th>
			<th>Payment Date</th>
			<th>Order No</th>
			<th class="center">Status</th>
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td><font class="capitalise">{{firstname}}  {{lastName}}</font></td>
			<td>{{paymentdate}}</td>
			<td>{{#if orderno}}{{orderno}}{{else}}-{{/if}}</td>
			<td class="center">
				<a class="btn btn-success btn-userinfo paymentstatusbtn paymentapprovebtn" paymentid="{{paymentid}}" paymenttype="{{paymenttype}}"
					paymentstatus="{{paymentstatus}}" courseenrollmentstatus="{{courseenrollmentstatus}}" courseenrollmentid="{{courseenrollmentid}}"  courseid="{{courseid}}" firstname="firstname">
				</a>
			</td>			
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="5"><p class="centertext categorydescription">No Requests</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="onlinepaymentdetailstmpl" type="text/x-handlebars-template">
<h3 class="rightpanetitle padleft0 marleft0 martop10 marbottom15 
	size20" paymentstatus="" paymenttype="">Online Payment Details</h3>
<table class="table table-striped whitebg paginationtab">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Name</th>
			<th>Payment Date</th>
			<th>Order No</th>			
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td><font class="capitalise">{{firstname}}  {{lastName}}</font></td>
			<td>{{paymentdate}}</td>
			<td>{{#if orderno}}{{orderno}}{{else}}-{{/if}}</td>						
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="5"><p class="centertext categorydescription">No Requests</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="offlineapproveddetailstmpl" type="text/x-handlebars-template">
<h3 class="rightpanetitle padleft0 marleft0 martop10 marbottom15 
	size20" paymentstatus="" paymenttype="">Offline Approved Payment Details</h3>
<table class="table table-striped whitebg paginationtab">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Name</th>
			<th>Payment Date</th>
			<th>Order No</th>			
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td><font class="capitalise">{{firstname}}  {{lastName}}</font></td>
			<td>{{paymentdate}}</td>
			<td>{{#if orderno}}{{orderno}}{{else}}-{{/if}}</td>						
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="4"><p class="centertext categorydescription">No Requests</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="certificatedetailstmpl" type="text/x-handlebars-template">
<h3 paymentstatus="" paymenttype="" class="rightpanetitle padleft0 marleft0 martop10 marbottom15 size20" 
coursecertificatestatus="">Certificate Details</h3>
<table class="table table-striped whitebg paginationtab">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Name</th>
			<th>Email</th>
			<th class="center">Status</th>			
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td><font class="capitalise">{{username}}  {{lastName}}</font></td>
			<td>{{#if emailaddress}}
					<font class=""><i class="fa fa-envelope" style="padding-right: 5px;"></i>{{emailaddress}}</font>
				{{else}}
					<font class=""><i class="fa fa-envelope" style="padding-right: 5px;"></i>------</font>
				{{/if}}</td>
			
			<td class="center"><a class="btn btn-blue certificatestatusbtn white" coursecertificateid="{{coursecertificateid}}" coursecertificatestatus="{{coursecertificatestatus}}"></a></td>						
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="4"><p class="centertext categorydescription">No Certificate Requests</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="profileinfotmpl" type="text/x-handlebars-template">
<div class="form-horizontal">
{{#each .}}
<div class="form-group">
	<label class="control-label col-sm-4 righttext">Name :</label>
	<div class="col-sm-8">
		<label class="controls-label">{{firstName}}</label>
	</div>
</div>
<div class="form-group">
	<label class="control-label col-sm-4 righttext">Contact Number :</label>
	<div class="col-sm-8">
		<label class="controls-label">{{#if number}}
			{{number}}
		{{else}}--{{/if}}</label>
	</div>
</div>
<div class="form-group">
	<label class="control-label col-sm-4 righttext">Email :</label>
	<div class="col-sm-8">
		<label class="controls-label">{{userid}}</label>
	</div>
</div>
{{/each}}
</div>
</script>
<script id="coursesinfotmpl" type="text/x-handlebars-template">
<table class="table list-search table-striped martop7">
	<thead>
		<tr>
			<th>Course Name</th>
			<th>Progress</th>
			<th>Time Spent</th>				
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
			<td>{{coursetitle}}</td>
			<td>{{courseprogress}}</td>
			<td>{{timespent}}</td>
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="3"><p class="centertext categorydescription">No Subscribed Courses</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</script>

<script id="messagestmpl" type="text/x-handlebars-template">
{{#each .}}
<p class="btn btn-flat btn-gray messageval">{{message}}</p><br>
{{/each}}
</script>
</head>
<body>
	<%
		UserDetailsAdaptor userDetailsAdaptor = null;
		userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String orgpersonid = userDetailsAdaptor.getUser().getProperty(
				"orgpersonid");
		String coursetitle = request.getParameter("coursetitle");
		String price = request.getParameter("price");
	%>

	<div class="container-fluid titlecontainer">
		<div class="row">
			<div class="col-md-12 lefttext">
				<a href="<%=request.getContextPath()%>/app/mycourses">My Courses
				</a> / Users for the course <a><%=coursetitle%></a>
			</div>
		</div>
	</div>
	<div class="container-fluid pad0">
		<div class="row">
			<div class="col-md-12 pad0">
				<div class="col-md-3">
					<div class="leftsideul martop10">
						<li class="leftsideliheader">
							<h3>
								Users
								<button type="button" class="navbar-toggle mar0 pad0"
									data-toggle="collapse" data-target="#authusersdiv"
									aria-expanded="false">
									<span class="sr-only">Toggle navigation</span> <i
										class="fa fa-bars"></i>
								</button>
							</h3>
						</li>
						<div id="authusersdiv" class="collapse navbar-collapse pad0">
							<li class="leftsideli allusersbtn hover">Subscribed Users <span
								class="count allusercount">0</span>
							</li>
							<li class="leftsideli inviteduserbtn hover">Invited Users <span
								class="count invitedusercount">0</span>
							</li>
						</div>
					</div>
					<%if(price.equalsIgnoreCase("Free")) 
					{}else{
					%>
					<div class="leftsideul martop10">
						<li class="leftsideliheader">
							<h3>
								Approval
								<button type="button" class="navbar-toggle mar0 pad0"
									data-toggle="collapse" data-target="#authapprovaldiv"
									aria-expanded="false">
									<span class="sr-only">Toggle navigation</span> <i
										class="fa fa-bars"></i>
								</button>
							</h3>
						</li>
						<div id="authapprovaldiv" class="collapse navbar-collapse pad0">
							<li class="leftsideli approvallistlinkbtn hover"
								paymenttype="Online" status="All">Online <span
								class="count allapprovalcount">0</span>
							</li>
							<%-- <li class="leftsideli approvallistlinkbtn hover"
								paymenttype="All" status="All">All <span
								class="count allapprovalcount">0</span>
							</li> --%>
							<li class="leftsideli approvallistlinkbtn hover"
								paymenttype="invoice-email" status="P">Offline Pending <span
								class="count pendingapprovalcount">0</span>
							</li>
							<li class="leftsideli approvallistlinkbtn hover"
								paymenttype="invoice-email" status="A">Offline Approved <span
								class="count approvedsuccesscount">0</span>
							</li>
						</div>
					</div>
					<%}
					%>
					<div class="leftsideul martop10">
						<li class="leftsideliheader">
							<h3>
								Certificates
								<button type="button" class="navbar-toggle mar0 pad0"
									data-toggle="collapse" data-target="#authcertdiv"
									aria-expanded="false">
									<span class="sr-only">Toggle navigation</span> <i
										class="fa fa-bars"></i>
								</button>
							</h3>
						</li>
						<div id="authcertdiv" class="collapse navbar-collapse pad0">
							<li class="leftsideli certificatebtn hover"
								certificatestatus="All">All <span
								class="count allcertificatecount">0</span>
							</li>
							<li class="leftsideli certificatebtn hover" certificatestatus="P">Pending
								<span class="count approvedcertificatecount">0</span>
							</li>
							<li class="leftsideli certificatebtn hover" certificatestatus="A">Issued
								<span class="count issuedcertificatecount">0</span>
							</li>
						</div>
					</div>
				</div>
				<div class="col-md-9" id="contentdisplay"></div>
			</div>
		</div>
	</div>
	<div class="popwindow" style="display: none;">
		<div class="row chathead pad5">
			<div class="col-md-9 chatername"></div>
			<div class="col-md-3 closechatwindowbtn">
				<i class="fa fa-remove"></i>
			</div>
		</div>
		<div class="row" id="messagetable"></div>
		<div class="row chatfooter">
			<div class="input-append"
				style="margin-bottom: 0px; border: 0px; border-top: 1px solid gray;">
				<input class="col-md-9 chatmessage" id="appendedInputButton"
					chatterpersonid="" type="text" placeholder="Enter Message"
					maxlength="500"> <a
					class="col-md-3 btn sendmessagebtn btn-primary" chatterpersonid="">Send</a>
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
					<h4 class="modal-title size20">Information</h4>
				</div>
				<div class="modal-body" id="profileinfotable">
					<!-- <ul id="myTab" class="nav nav-tabs pad0">
						<li class="active borderbottoma"><a href="#profileinfotab"
							class="learningtablinks" data-toggle="tab">Profile
								Information</a></li>
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
					</div> -->
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="msginfomodal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<font><b>Message</b></font>
				</div>
				<div class="modal-body">
					<p class="description succesmsg pad10"></p>
				</div>
				<div class="modal-footer">
					<a class="btn btn-blue close" data-dismiss="modal">Close</a>
				</div>
			</div>
		</div>
	</div>

	<s:hidden name="courseid" value="%{courseid}"></s:hidden>
	<s:hidden name="price" value="%{price}"></s:hidden>
	<input type="hidden" name="orgpersonid" value="<%=orgpersonid%>">
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/users/manageusers.js"></script>
</body>
</html>