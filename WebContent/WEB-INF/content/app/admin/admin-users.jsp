<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page
	import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <style type="text/css">
.pagination .page{
	border-radius: 50% !important;
	margin: 0 2px !important;
}
</style> -->
<script id="datanotfoundtmpl" type="text/x-handlebars-template">
<h5 class="errormessagetext">No Users sare Available<h5>
</script>
<script id="allusertmpl" type="text/x-handlebars-template">
<div style='min-height:645px;'>
<div class="row pad0">
<div class="col-md-12 allusersearch">
	<h3 class="cattitle padleft0 marleft0 martop10 col-md-2 marbottom15 size20">All Users</h3>
	<div class="leftside searchcoursetext searchtext col-md-5" id="searchtext">
		<input type="text" class="system-search form-control" id="system" placeholder="search ...." />
	</div>
	<a data-toggle="modal" class="col-md-3 btn btn-success btn-flat pull-right inviteusermodal" >Invite New Users</a>
</div>
</div>
<div style="height:580px">
<table id="alluserstable" class="alluserstable table table-bordered table-striped whitebg list-search  manageprofilecont paginationtab" style="float:left;">
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
			<td>   <a
				class=" btn-userinfo moreinfouserbtn"style="cursor: pointer;"
				orgpersonid="{{orgpersonid}}" loginid="{{loginid}}"> {{username}} {{lastName}}</a>
			</td>
			<td>{{#if emailaddress}} <font class="">{{emailaddress}}</font> {{else}} <font
				class="lightbrown">No
				Email address</font> {{/if}}</td>
			<td class="center">
				<a class="btn btn-default btn-gray btn-userinfo messageuserbtn"
					personname="{{username}}" orgpersonid="{{orgpersonid}}" data-toggle="tooltip" title="Message">
              		<i class="fa fa-envelope"></i></a> 
     			<a class="btn btn-default btn-gray btn-userinfo removebtn"
					personname="{{username}}" orgpersonid="{{orgpersonid}}" 
					personid="{{personid}}" loginid="{{loginid}}" data-toggle="tooltip" 
					title="Click here to deactivate the user"><i class="fa fa-remove"></i>
            	</a>
			</td>		
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="4"><p class="centertext">No Users</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</div>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="paidusertmpl" type="text/x-handlebars-template">
<div style='min-height:645px;'>
<div class="row pad0">
<div class="col-md-12 allusersearch">
	<h3 class="cattitle padleft0 marleft0 martop10 col-md-2 marbottom15 size20">Paid Users</h3>		
</div>
</div>
<div style="height:580px">
<table id="alluserstable" class="alluserstable table table-bordered table-striped whitebg list-search  manageprofilecont paginationtab" style="float:left;">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Name</th>
			<th>Email</th>
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr class="paidUserRow">
            <td>{{math @index "+" 1}}</td>
			<td>   <a
				class="moreinfouserbtn" style="cursor: pointer;"
				orgpersonid="{{orgpersonid}}" > {{firstname}} {{lastname}}</a>
			</td>
			<td>{{#if emailid}} <font class="">{{emailid}}</font> {{else}} <font
				class="lightbrown">No
				Email address</font> {{/if}}</td>			
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="3"><p class="centertext">No Users</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</div>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="waitingusertmpl" type="text/x-handlebars-template">
<div style='min-height:645px;'>
<div class="row pad0">
<div class="col-md-12 inviteuserlistsearch">
	<h3 class="cattitle padleft0 marleft0 martop10 col-md-4 marbottom15 size20">Invited Users</h3>
	<div class="leftside searchcoursetext searchtext col-md-5" id="searchtext">
		<input type="text" class="system-search form-control" id="system" placeholder="search ...." />
	</div>	
</div>
</div>   
<div class="min450">
<table class="table list-search manageprofilecont paginationtab table-bordered table-striped whitebg" style="float:left;">
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
			<td colspan="3"><p class="centertext">No Users</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</div>
</div>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>
<script id="enrolleduserinfotmpl" type="text/x-handlebars-template">
<div style='min-height:645px;'>
<div class="row pad0">
<div class="col-md-12 enrolledsearch">
	<h3 class="cattitle padleft0 marleft0 martop10 col-md-4 marbottom15 size20">Enrolled Users</h3>
	<div class="leftside searchcoursetext searchtext col-md-5" id="searchtext">
		<input type="text" class="system-search form-control" id="system" placeholder="search ...." />
	</div>	
</div>
</div>
<div style="height:580px;">
	<table class="table list-search table-bordered table-striped whitebg paginationtab" style="float:left">
		<thead>
			<tr>
				<th>Sl. No</th>
				<th>Name</th>
				<th>Course Count </th>
			</tr>
		</thead>
		<tbody>
     	{{#if .}}
			{{#each .}} 
            	<tr>
                	<td >{{math @index "+" 1}}</td>
                	<td organizationid="{{organizationid}}">{{firstname}} {{lastName}}</td>
                	<td personid="{{orgpersonid}}">
						<a class="center btn btn-flat btn-gray 
						enrolcoursebtn"style="cursor: pointer;"  orgpersonid="{{orgpersonid}}" 
						courseenrollmentid="{{courseenrollmentid}}" data-toggle="tooltip" 
						title="Click here to enrolled courses">{{coursecount}}</a>
					</td>
				</tr>              
    		{{/each}}
		{{else}}
		<tr>
			<td colspan="3"><p class="centertext">No Enrolled Users</p></td>
		</tr>
		{{/if}}     
        </tbody>
	</table>   
</div>
</div>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="removeduserinfotmpl" type="text/x-handlebars-template">
<div style='min-height:645px;'>
<div class="row pad0">
<div class="col-md-12 removedsearch">
	<h3 class="cattitle padleft0 marleft0 martop10 col-md-4 marbottom15 size20">Deactivated Users</h3>
	<div class="leftside searchcoursetext searchtext col-md-5" id="searchtext">
		<input type="text" class="system-search form-control" id="system" placeholder="search ...." />
	</div>	
</div>
</div>
<div style="height:580px;">
	<table class="table table-bordered table-striped whitebg list-search manageprofilecont paginationtab" style="float:left;margin-right: 0px;">
		<thead>
			<tr> 
 				<th>Sl.No</th>
 				<th>Name</th>
 				<th>Email</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
		{{#if.}}
 			{{#each.}}
			<tr>
				<td>{{math @index "+" 1}}</td>
				<td> <a class=" btn-userinfo moreinfouserbtn" style="cursor:pointer" 
						orgpersonid="{{orgpersonid}}" loginid="{{loginid}}"> {{username}} {{lastName}}</a> 
				</td>
				<td>
					{{#if emailaddress}} 
							<font class=""><i class="icon-envelope" 
								style="padding-right: 5px;"></i>{{emailaddress}}</font> 
					{{else}}
							<font class=""><i class="icon-envelope" style="padding-right: 5px;">
							</i>No Email address</font> 
					{{/if}}
				</td>
				<td> <a class="btn btn-default btn-userinfo restoreuserbtn" 
						personname="{{username}}" orgpersonid="{{orgpersonid}}"
 						personid="{{personid}}" loginid="{{loginid}}" data-toggle="tooltip" 
						title="Click here to activate this user">Activate</a>
				</td>
			</tr>		
			{{/each}}
		{{else}}
		<tr>
			<td colspan="4"><p class="centertext">No Removed Users</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</div>
</div>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="allpaymentdetailstmpl" type="text/x-handlebars-template">
<div class="row pad0">
<div class="col-md-12 alllistsearch">
	<h3 class="cattitle padleft0 marleft0 martop10 col-md-4 marbottom15 size20">All Payment Details</h3>
	<div class="leftside searchcoursetext col-md-5" id="searchtext">
		<input type="text" class="system-search form-control" id="system" placeholder="search ...." />
	</div>	
</div>
</div>
<div style="height:580px;">
<table class="table table-bordered table-striped whitebg list-search manageprofilecont paginationtab" style="float:left;">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Name</th>
			<th>Payment Type</th>
			<th>Course Title</th>
			<th>Payment Date</th>
			<th style='text-align:center;'>Order No</th>
			<th class="center">Status</th>
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{firstname}} {{lastName}}</td>
			<td>{{paymenttype}}</td>
			<td>
				<p class='zoomin'>{{coursetitle}}</p>
			</td>
			<td>{{paymentdate}}</td>
			{{#if orderno}}<td style='text-align:center;'><span class="ordernoval">{{orderno}}</span></td>
			{{else}}<td style='text-align:center;'>-</td>{{/if}}
			<td class="center">
				<a class="btn btn-flat btn-gray btn-userinfo btn-padded paymentstatusbtn paymentapprovebtn" paymentid="{{paymentid}}" paymenttype="{{paymenttype}}" orderno="{{orderno}}"
					paymentstatus="{{paymentstatus}}" courseenrollmentstatus="{{courseenrollmentstatus}}" courseenrollmentid="{{courseenrollmentid}}" firstname="firstname">
				</a>
			</td>			
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="7"><p class="centertext">No Requests</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</div>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="pendingpaymentdetailstmpl" type="text/x-handlebars-template">
<div class="row pad0">
<div class="col-md-12 allpendingsearch">
	<h3 class="cattitle padleft0 marleft0 martop10 col-md-4 marbottom15 size20">Pending Payment Requests</h3>
	<div class="leftside searchcoursetext col-md-5" id="searchtext">
		<input type="text" class="system-search form-control" id="system" placeholder="search ...." />
	</div>	
</div>
</div>
<div style="height:580px">
<table class="table table-bordered table-striped whitebg list-search manageprofilecont paginationtab" style="float:left;">
	<thead>
		<tr>
             <th>Sl.No</th>
			<th>Name</th>
			<th>Payment Type</th>
			<th>Course Title</th>
			<th>Payment Date</th>
			<th style='text-align:center;'>Order No</th>
			<th class="center">Status</th>
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{firstname}} {{lastName}}</td>
			<td>{{paymenttype}}</td>
			<td>
                <p class='zoomin'>{{coursetitle}}</p>
            </td>
			<td>{{paymentdate}}</td>
			{{#if orderno}}<td style='text-align:center;'><span class="ordernoval">{{orderno}}</span></td>
			{{else}}<td style='text-align:center;'>-</td>{{/if}}
			<td class="center">
				<a class="btn btn-flat btn-gray btn-userinfo btn-padded paymentstatusbtn paymentapprovebtn" paymentid="{{paymentid}}" paymenttype="{{paymenttype}}" orderno="{{orderno}}"
					paymentstatus="{{paymentstatus}}" courseenrollmentstatus="{{courseenrollmentstatus}}" courseenrollmentid="{{courseenrollmentid}}" firstname="firstname">
				</a>
			</td>			
		</tr>
		{{/each}} {{else}}
		<tr class="centertext">
			<td colspan="7"><p class="centertext">No Requests</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</div>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>
<script id="approvedpaymentdetailstmpl"
	type="text/x-handlebars-template">
<div class="row pad0">
<div class="col-md-12 allapprovedsearch">
	<h3 class="cattitle padleft0 marleft0 martop10 col-md-4 marbottom15 size20">Approved Payment Details</h3>
	<div class="leftside searchcoursetext col-md-5">
		<input type="text" class="system-search form-control" id="system" placeholder="search ...." />
	</div>	
</div>
</div>  		
<div style="height:580px">
<table class="table table-bordered table-striped whitebg list-search manageprofilecont paginationtab" style="float:left;">
	<thead>
		<tr>
             <th>Sl.No</th>
			<th>Name</th>
			<th>Payment Type</th>
			<th>Course Title</th>
			<th>Payment Date</th>
			<th style='text-align:center;'>Order No</th>
			<th class="center">Status</th>
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{firstname}} {{lastName}}</td>
			<td>{{paymenttype}}</td>
			<td>
				<p class='zoomin'>{{coursetitle}}</p>
 			</td>
			<td style="width:20%;">{{paymentdate}}</td>
			{{#if orderno}}<td style='text-align:center;'><span class="ordernoval">{{orderno}}</span></td>
			{{else}}<td style='text-align:center;'>-</td>{{/if}}
			<td class="center">
				<a class="btn btn-flat btn-gray btn-userinfo btn-padded paymentstatusbtn paymentapprovebtn" paymentid="{{paymentid}}" paymenttype="{{paymenttype}}"
					paymentstatus="{{paymentstatus}}" courseenrollmentstatus="{{courseenrollmentstatus}}" courseenrollmentid="{{courseenrollmentid}}" firstname="firstname">
				</a>
			</td>			
		</tr>
		{{/each}} {{else}}
		<tr class="centertext">
			<td colspan="7"><p class="centertext">No Requests</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</div>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="allcertificatedetailstmpl" type="text/x-handlebars-template">
<div class="row pad0">
<div class="col-md-12 allcertsearch">
	<h3 class="cattitle padleft0 marleft0 martop10 col-md-4 marbottom15 size20">All Certificate Request</h3>
	<div class="leftside searchcoursetext col-md-5">
		<input type="text" class="system-search form-control" id="system" placeholder="search ...." />
	</div>	
</div>
</div>
<div style="height:580px">
<table class="table table-bordered table-striped whitebg list-search manageprofilecont paginationtab" style="float:left">
	<thead>
		<tr>
           <th>Sl.No</th>
			<th>Name</th>
			<th>Email</th>
			<th>Course</th>
			<th class="center">Status</th>			
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{username}} {{lastName}}</td>
			<td>{{#if emailaddress}}
					<font class=""><i class="icon-envelope" style="padding-right: 5px;"></i>{{emailaddress}}</font>
				{{else}}
					<font class=""><i class="icon-envelope" style="padding-right: 5px;"></i>------</font>
				{{/if}}</td>
			<td>
             	<p class='zoomin'>{{coursetitle}}</p>
			</td>
			<td style='width:10%;' class="center"><a class="btn btn-flat btn-success certificatestatusbtn btn-padded" coursecertificateid="{{coursecertificateid}}" coursecertificatestatus="{{coursecertificatestatus}}"></a></td>						
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="6"><p class="centertext">No Certificate Requests</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</div>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="pendingcertificatedetailstmpl"
	type="text/x-handlebars-template">
<div class="row pad0">
<div class="col-md-12 pendingcertsearch">
	<h3 class="cattitle padleft0 marleft0 martop10 col-md-4 marbottom15 size20">Pending Certificate Request</h3>
	<div class="leftside searchcoursetext col-md-5">
		<input type="text" class="system-search form-control" id="system" placeholder="search ...." />
	</div>	
</div>
</div>
<div style="height:580px">
<table class="table table-bordered table-striped whitebg list-search manageprofilecont paginationtab" style="float:left">
	<thead>
		<tr>
           <th>Sl.No</th>
			<th>Name</th>
			<th>Email</th>
			<th>Course</th>
			<th class="center">Status</th>			
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{username}} {{lastName}}</td>
			<td>{{#if emailaddress}}
					<font class=""><i class="icon-envelope" style="padding-right: 5px;"></i>{{emailaddress}}</font>
				{{else}}
					<font class=""><i class="icon-envelope" style="padding-right: 5px;"></i>------</font>
				{{/if}}</td>
			<td>
				<p class='zoomin'>{{coursetitle}}</p>
			</td>
			<td class="center"><a class="btn btn-success btn-gray certificatestatusbtn btn-padded" coursecertificateid="{{coursecertificateid}}" coursecertificatestatus="{{coursecertificatestatus}}"></a></td>						
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="5"><p class="centertext">No Certificate Requests</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</div>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>
<script id="issuedcertificatedetailstmpl"
	type="text/x-handlebars-template">
<div class="row pad0">
<div class="col-md-12 approvedcertsearch">
	<h3 class="cattitle padleft0 marleft0 martop10 col-md-4 marbottom15 size20">Issued Certificates</h3>
	<div class="leftside searchcoursetext col-md-5">
		<input type="text" class="system-search form-control" id="system" placeholder="search ...." />
	</div>	
</div>
</div>
<div style="height:580px">
<table class="table table-bordered table-striped whitebg  list-search manageprofilecont paginationtab" style="float:left;">
	<thead>
		<tr>
           <th>Sl.No</th>
			<th>Name</th>
			<th>Email</th>
			<th>Course</th>
			<th class="center">Status</th>			
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{username}} {{lastName}}</td>
			<td>{{#if emailaddress}}
					<font class=""><i class="icon-envelope" style="padding-right: 5px;"></i>{{emailaddress}}</font>
				{{else}}
					<font class=""><i class="icon-envelope" style="padding-right: 5px;"></i>------</font>
				{{/if}}</td>
			<td>{{coursetitle}}</td>
			<td class="center"><a class="btn btn-flat btn-success certificatestatusbtn btn-padded" coursecertificateid="{{coursecertificateid}}" coursecertificatestatus="{{coursecertificatestatus}}"></a></td>						
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="5"><p class="centertext">No Certificate Requests</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</div>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>

</script>
<script id="courseinfotmpl" type="text/x-handlebars-template">
<table class="table list-search table-bordered table-striped whitebg manageprofilecont">
	<thead>
    	<tr>
			<th>Sl. No</th>
			<th>Course Name </th>
		</tr>
	</thead>
	<tbody>
		{{#if .}}
			{{#each .}}
     			<tr>
					<td >{{math @index "+" 1}}</td>
					<td> {{coursetitle}}</td>
				</tr>              
			{{/each}}    
		{{else}}
			<tr>
				<td colspan="3"><p class="centertext">No Enrolled courses</p></td>
			</tr>
		{{/if}}     
	</tbody>
</table>
</script>

<script id="allauthorstmpl" type="text/x-handlebars-template">
<div class="row pad0">
<div class="col-md-12 allauthorsearch">
	<h3 class="cattitle padleft0 marleft0 martop10 col-md-4 marbottom15 size20">Authors</h3>
	<div class="leftside searchcoursetext col-md-5">
		<input type="text" class="system-search form-control" id="system" placeholder="search ...." />
	</div>	
</div>
</div>

<table class="table list-search table-bordered table-striped whitebg manageprofilecont paginationtab" style="float:left">
	<thead>
		<tr>
            <th>Sl.No</th>
			<th>Authors Name</th>
			<th>Email</th>
			<th>No of Courses</th>
				
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
          <td>{{math @index "+" 1}}</td>
			<td>{{username}}</td>
			<td>{{#if emailaddress}} <font class=""><i class="icon-envelope"
				style="padding-right: 5px;"></i>{{emailaddress}}</font> {{else}} <font
				class=""><i class="icon-envelope" style="padding-right: 5px;"></i>No
				Email address</font> {{/if}}</td>

			<td class="center"><a class="btn btn-flat btn-gray authorcourses"
				 orgpersonid="{{orgpersonid}}" data-toggle="tooltip" title="Click here to Course Details">{{coursecount}}</a>
			</td> 
						
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="4"><p class="centertext">No Users</p></td>
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
<script id="authorcoursestudentsinfotmpl"
	type="text/x-handlebars-template">
<div class="coursestudentstab">
<h3 class="cattitle size20"> Users List - <span id="coursetitlespan"></span>
							</h3>
<table class="table table-bordered table-striped list-search manageprofilecont">
	<thead>
		<tr>
            <th>Sl.No</th>
			<th>Users Name</th>
			<th>Email</th>
		</tr>
	</thead>
	<tbody>

		{{#if .}} {{#each .}}
		<tr> 
            <td>{{math @index "+" 1}} </td>
			<td>{{firstname}}&nbsp{{middlename}}&nbsp;{{lastname}}</td>
			<td>{{#if emailaddress}} <font class="breakword">{{emailaddress}}</font> {{else}} <font
				class="">No Email address</font> {{/if}}</td>
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="4"><p class="centertext">No Users</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</div>
</script>
<script id="errormesstmpl" type="text/x-handlebars-template">
<div class="alert alert-danger alert-dismissible" role="alert">
<button type="button" class="close" data-dismiss="alert">
	<span aria-hidden="true">×</span>
</button>
<strong>Warning!</strong> {{errormessage}}</div>
</script>

<script id="validationtmpl" type="text/x-handlebars-template">
<div class="alert alert-danger alert-dismissible" role="alert">
<strong>Warning!</strong> {{errorvalidation}}</div>
</script>

<script id="duplicatemessagetmpl" type="text/x-handlebars-template">
<div class="alert alert-danger alert-dismissible" role="alert">
<strong>Warning!</strong>{{duplicatemessage}}</div>
</script>



<script id="allpaymentdetailstmpl" type="text/x-handlebars-template">
  <div class=" alllistsearch">
	<h3 class="cattitle allusers span5">All Payment Details</h3>
	<div class="span3"></div>
     <div class="leftside searchcoursetext span4">
		<input type="text" class="system-search" id="system" placeholder="search...." />
	  </div>
</div>	
<div style="height:580px;">
<table class="table list-search manageprofilecont paginationtab" style="float:left;">
	<thead>
		<tr>
             <th>Sl.No</th>
			<th>User Name</th>
			<th>Payment Type</th>
			<th>Course Title</th>
			<th>Payment Date</th>
			<th style='text-align:center;'>Order No</th>
			<th class="center">Status</th>
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{firstname}}</td>
			<td>{{paymenttype}}</td>
			<td>{{coursetitle}}</td>
			<td>{{paymentdate}}</td>
			{{#if orderno}}<td style='text-align:center;'>{{orderno}}</td>
			{{else}}<td style='text-align:center;'>-</td>{{/if}}
			<td class="center">
				<a class="btn btn-flat btn-gray btn-userinfo btn-padded paymentstatusbtn paymentapprovebtn" paymentid="{{paymentid}}" paymenttype="{{paymenttype}}"
					paymentstatus="{{paymentstatus}}" courseenrollmentstatus="{{courseenrollmentstatus}}" courseenrollmentid="{{courseenrollmentid}}" firstname="firstname">
				</a>
			</td>			
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="6"><p class="center categorydescription">No Requests</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</div>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>


<script id="approvedpaymentdetailstmpl"
	type="text/x-handlebars-template">
  		
<div class=" allapprovedsearch" >
	<h3 class="cattitle  span5">Approved Payment Details</h3>
	<div class="span3"></div>
     <div class="leftside searchcoursetext span4">
		<input type="text" class="system-search" id="system" placeholder="search ...." />
	  </div>
</div>
<div style="height:580px">
<table class="table list-search manageprofilecont paginationtab" style="float:left;">
	<thead>
		<tr>
             <th>Sl.No</th>
			<th>User Name</th>
			<th>Payment Type</th>
			<th>Course Title</th>
			<th>Payment Date</th>
			<th style='text-align:center;'>Order No</th>
			<th class="center">Status</th>
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
			<td>{{firstname}}</td>
			<td>{{paymenttype}}</td>
			<td>{{coursetitle}}</td>
			<td>{{paymentdate}}</td>
			{{#if orderno}}<td style='text-align:center;'>{{orderno}}</td>
			{{else}}<td style='text-align:center;'>-</td>{{/if}}
			<td class="center">
				<a class="btn btn-flat btn-gray btn-userinfo btn-padded paymentstatusbtn paymentapprovebtn" paymentid="{{paymentid}}" paymenttype="{{paymenttype}}"
					paymentstatus="{{paymentstatus}}" courseenrollmentstatus="{{courseenrollmentstatus}}" courseenrollmentid="{{courseenrollmentid}}" firstname="firstname">
				</a>
			</td>			
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="6"><p class="center categorydescription">No Requests</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</div>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>





<script id="profileinfotmpl" type="text/x-handlebars-template">
{{#each .}}
					<form class="form-horizontal">
					<div class="form-group">
							<label class="control-label col-md-5">Name : </label>
						<div class="controls-label col-md-4">
								{{firstName}} {{lastName}}
						</div>
					</div>
					<div class="form-group">
							<label class="control-label col-md-5">Date of birth </label>
						<div class="controls-label col-md-4">
								{{#if dob}}
								{{dob}}
								{{else}}
								   --
								{{/if}}
						</div>
					</div>
					<div class="form-group">
							<label class="control-label col-md-5">Contact Number : </label>
						<div class="controls-label col-md-4">
								{{#if number}}
								{{number}}
								{{else}}
								  --
								{{/if}}
						</div>
					</div>
					<div class="form-group">
							<label class="control-label col-md-5">Qualification : </label>
						<div class="controls-label col-md-4">
								{{#if eduqualification}}
								{{eduqualification}}
								{{else}}
								  --
								{{/if}}
						</div>
					</div>
					<div class="form-group">
							<label class="control-label col-md-5">Email : </label>
						<div class="controls-label col-md-4">
								{{#if userid}}
								{{userid}}
								{{else}}
								  --
								{{/if}}
						</div>
					</div>
					<div class="form-group">
							<label class="control-label col-md-5">Photo : </label>
						<div class="controls-label col-md-4">
								<img src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{personphotourl}}&filetype=photo"
								class="courselogo thumbnailimage width100 height140"></img> <input type="hidden"
								name="courselogo" value="{{personphotourl}}">
						</div>
					</div>
			</form>
{{/each}}
</script>
<script id="authorcourseinfotmpl" type="text/x-handlebars-template">
<div class="aurthorcrstable">
<h3 class="rightpanetitle size20">Courses Details</h3>
<table class="table table-bordered table-striped list-search manageprofilecont" style="margin-top:0px;">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Course Name</th>
			<th>No of Users</th>					
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr class="atstudents" 	 courseid="{{courseid}}" coursetitle="{{coursetitle}}">
			<td>{{math @index "+" 1}}</td>				
			<td >
				<a href="#" class="authorcoursestudents" 
				 courseid="{{courseid}}" coursetitle="{{coursetitle}}">{{coursetitle}}</a>
			</td>
			<td>{{studentcount}}</td>

									
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="4"><p class="center categorydescription">No Courses Found</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</div>
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
<script id="nomessagestmpl" type="text/x-handlebars-template">
<h3 style="color:red;">No Messages Found</h3>
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
		String orgstatus = userDetailsAdaptor.getUser().getProperty(
				"orgstatus");
	%>
	<%
		String au = request.getParameter("a");
	%>
	<span class="linkattr" link="<%=au%>" style="display: none;"></span>
	<div class="alert alert-danger maxcourseuser" style="display: none;">
		Your current plan does not allow creation of users more than <font
			class="maxusercount"> </font>. Consider upgrading your Plan <a
			class="orgplanlink">Click hear for plan</a>
	</div>
	<div class="container-fluid titlecontainer">
		<div class="row">
			<div class="col-md-12 lefttext">
				<a href="<%=request.getContextPath()%>/app/dashboard">Dashboard
				</a> / Users
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12 pad0">
				<div class="col-md-3 pad0">
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
							<li class="leftsideli allusersbtn hover">Registered Users <span
								class="count allusercount"></span>
							</li>
							<li class="leftsideli inviteuserlist hover">Invited Users
								List <span class="count inviteusercount"></span>
							</li>
							<%
								if (orgstatus.equals("M")) {
							%>
							<li class="leftsideli allauthorsbtn hover">Authors <span
								class="count allauthorcount">0</span>
							</li>
							<%
								}
							%>
							<li class="leftsideli enrollusersbtn hover">Enrolled Users <span
								class="count alluserscount"></span>
							</li> <input type="hidden" value="organizationid"
								name="organizationid">
							<li class="leftsideli paidusersbtn hover">Paid Users
								<span class="count paidusercount">0</span>
							</li>
							<li class="leftsideli removesersbtn hover">Deactivated Users
								<span class="count removeusercount">0</span>
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
							<!-- <li class="leftsideli approvallistlinkbtn alllist hover"
								paymenttype="All" status="All">All <span
								class="count allapprovalcount">0</span>
							</li> -->
							<li class="leftsideli approvallistlinkbtn alllist hover"
								paymenttype="Online" status="All">Online <span
								class="count onlinepaiduserscount">0</span>
							</li>
							<li class="leftsideli approvallistlinkbtn alllist hover"
								paymenttype="Free" status="All">Free <span
								class="count freepaiduserscount">0</span>
							</li>
							<li class="leftsideli pendinglistlinkbtn allpedlist hover"
								paymenttype="invoice-email" status="P">Pending <span
								class="count pendingapprovalcount">0</span>
							</li>
							<li class="leftsideli approvedlistlinkbtn allaplist hover"
								paymenttype="invoice-email" status="A">Approved <span
								class="count approvedsuccesscount">0</span>
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
							<li class="leftsideli certificatebtn allcertificate hover"
								certificatestatus="All">All <span
								class="count allcertificatecount">0</span>
							</li>
							<li
								class="leftsideli pendingcertificatebtn pendingcertificate hover"
								certificatestatus="P">Pending <span
								class="count approvedcertificatecount">0</span>
							</li>
							<li
								class="leftsideli issuecertificatebtn issuedcertificate hover"
								certificatestatus="A">Issued <span
								class="count issuedcertificatecount">0</span>
							</li>
						</div>
					</div>
				</div>
				<div class="col-md-9 martop10 padright0">
					<div id="contentdisplay"></div>
				</div>
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
				<input class="col-md-9 chatmessage lightgraybg"
					id="appendedInputButton" chatterpersonid="" type="text"
					placeholder="Enter Message" maxlength="500"> <a
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



	<div class="modal fade" id="usermessage">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title size20">Message</h4>
				</div>
				<div class="modal-body">
					<p class="description succesmsg pad10"></p>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="authorcoursemodal">
		<div class="modal-dialog width70per" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title size20">Author Courses</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-12">
							<div class="col-md-6" id="authorcourseinfotable"></div>
							<div class="col-md-6" id="authorcoursestudentinfo"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="enrollcourseinfomodal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title size20">
						Enrolled Courses for <span class="enrolluser"></span>
					</h4>
				</div>
				<div class="modal-body">
					<div class="courseinfobody">
						<div>
							<div id="enrollcourseinfotable"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<div class="modal fade" id="msginfomodal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title size20">Message</h4>
				</div>
				<div class="modal-body">
					<p class="description succesmsg pad10"></p>
				</div>
			</div>
		</div>
	</div>


	<!-- modal for invite new users -->

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
								<font class="attchmentfilename breakword" atfilename=""></font>
								<a class="btn btn-flat attclose"><i class="fa fa-times"></i></a>
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

	<!-- alert message for new users -->

	<div class="modal fade" id="newusers">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title size20">Message</h4>
				</div>
				<div class="modal-body">
					<p class="description succesusers pad10"></p>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="messageinfomodal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title size20">Message</h4>
				</div>
				<div class="modal-body">
					<p style="color: #F00" class="alertmessageval">
						Your current plan does not support invite more than <span
							class="maxusers"></span> users. Please Upgrade your plan
					</p>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="inviteusermessage">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title size20">Message</h4>
				</div>
				<div class="modal-body">
					<p class="pad10">Successfully Invited.</p>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="inviteusererrormsg">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title size20">Message</h4>
				</div>
				<div class="modal-body">
					<p class="pad10">Problem in sending email, Please try after
						sometime..</p>
				</div>
			</div>
		</div>
	</div>
	<script
		src="<%=request.getContextPath()%>/assets/formjs/jquery.form.js"
		type="text/javascript"></script>
	<script
		src="<%=request.getContextPath()%>/assets/app/js/users/users.js"
		type="text/javascript"></script>
	<script
		src="<%=request.getContextPath()%>/assets/app/js/course/emailxl.js"
		type="text/javascript"></script>

	<script type="text/javascript">
		$('#inviteusersmodal').on('hidden', function() {
			$(this).find('input:text,textarea,select').val('');
			$(this).find('.alert.alert-danger.alert-dismissible').remove();
		});

		$(function() {
			$('#myTab a:last').tab('show');
		});
	</script>
</body>
</html>