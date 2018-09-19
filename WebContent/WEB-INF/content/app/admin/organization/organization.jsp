<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style type="text/css">
.form-control{
 border-radius: 0px;
}
.badge{
  background-color: #14bdee;
}
</style>


<script id="organizationstmpl" type="text/x-handlebars-template">
<div> 
<div style='min-height:645px;'>
	<table class="table list-search table-bordered table-striped whitebg manageprofilecont paginationtab">
		<thead>
			<tr>
				<th>Sl. No</th>
				<th>Name</th>
				<th>Plan Name</th>
				<th>Courses / Max Courses</th>
				<th>Users / Max Users</th>
				<th style="vertical-align: middle;"> Action</th>
			</tr>
		</thead>
		<tbody>
			{{#each .}}
				<tr>
					<td>{{math @index "+" 1}}</td>
					<td class="orglist orgname" organizationid="{{organizationid}}" orgpersonid="{{orgpersonid}}"><a href="javascript:void(0);">{{orgname}}</a></td>
					<td class="planname" organizationid="{{organizationid}}" orgpersonid="{{orgpersonid}}" style="text-decoration: underline !important;">
							<a href="javascript:void(0);">{{planname}}</a></td>
					{{#if noofcourses}}
					<td class="courselist" organizationid="{{organizationid}}" orgpersonid="{{orgpersonid}}" personid="{{personid}}" orgplansubscriptionid={{id}} orgname="{{orgname}}"><a class="badge" style="text-decoration: underline !important;margin-left:20px;backgound-color:#AEE5E1;">{{noofcourses}}/
						<b>{{maxcourse}}</b> </a>  
					</td>
					{{else}}
					<td  organizationid="{{organizationid}}" orgpersonid="{{orgpersonid}}" personid="{{personid}}"><a class="badge" style="margin-left: 20px;cursor:alias;">{{noofcourses}}/
						<b>{{maxcourse}}</b> </a>      
					</td>
					{{/if}}
					{{#if noofusers}}
  					<td class="userlist" organizationid="{{organizationid}}" orgpersonid="{{orgpersonid}}"  orgplansubscriptionid={{id}} orgname="{{orgname}}"><a class="badge"style="text-decoration: underline !important;margin-left: 20px;margin-left: 20px;">{{noofusers}}/
						<b>{{maxusers}}</b></a></td>
					{{else}}
					<td class="" organizationid="{{organizationid}}" orgpersonid="{{orgpersonid}}"><a class="badge" style="margin-left: 20px;cursor:alias;">{{noofusers}}/
							 <b>{{maxusers}}</b></a></td>
					{{/if}}
					<td><a href="#" class="btn btn-flat btn-primary deactorg " orgpersonid="{{orgpersonid}}"  personid="{{personid}}" organizationid="{{organizationid}}">Deactivate</a>         
						<a class="btn btn-flat btn-primary readmore" orgpersonid="{{orgpersonid}}"  personid="{{personid}}" organizationid="{{organizationid}}" orgname="{{orgname}}">Plan history</a>
					</td>
				</tr>              
			{{/each}}         
		</tbody>
	</table>   
</div>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div> 
</div>
</script>
<script id="deorganizationstmpl" type="text/x-handlebars-template">
<div> 
<div style='min-height:645px;'>
    	 	<table class="table list-search table-bordered table-striped whitebg manageprofilecont paginationtab" >
                    <thead>
                        <tr>
                            <th>Sl. No</th>
                            <th>Organization Name</th>
                            <th>Plan Name</th>
                            <th>Courses/Max Courses</th>
                            <th> Enrolled/Users</th>
                            <th> Action</th>
                       </tr>
                    </thead>
                    <tbody>
						{{#each .}}
                        	<tr>
                            	<td>{{math @index "+" 1}}</td>
                            	<td class="orglist orgname" organizationid="{{organizationid}}" orgpersonid="{{orgpersonid}}"><a href="javascript:void(0);">{{orgname}}</a></td>
                            	<td class="planname" organizationid="{{organizationid}}" orgpersonid="{{orgpersonid}}">
                                 <a href="javascript:void(0);"> {{planname}}</a>
                                </td>
{{#if noofcourses}}
                            	<td class="courselist" organizationid="{{organizationid}}" orgpersonid="{{orgpersonid}}" personid="{{personid}}" orgplansubscriptionid={{id}}> <a class="badge">{{noofcourses}}/
                                        <b>{{maxcourse}}</b></a>       
                                </td>
{{else}}

<td  organizationid="{{organizationid}}" orgpersonid="{{orgpersonid}}" personid="{{personid}}"><a class="badge">{{noofcourses}}</b>/
                                        <b>{{maxcourse}}</b></a>       
                                </td>

{{/if}}
                          
{{#if noofusers}}
  	<td class="userlist" organizationid="{{organizationid}}" orgpersonid="{{orgpersonid}}" orgplansubscriptionid={{id}}> <a class="badge"> {{noofusers}}/
							 <b>{{maxusers}}</b> </a></td>
{{else}}

    <td class="" organizationid="{{organizationid}}" orgpersonid="{{orgpersonid}}"><a class="badge">{{noofusers}}</b>/
							 <b>{{maxusers}}</b></a></td>

{{/if}}
                          <td>
<a href="#" aria-label="Previous"  class="btn btn-flat btn-primary actorg" orgpersonid="{{orgpersonid}}"  personid="{{personid}}" organizationid="{{organizationid}}">Activate</a>         
<a href="#" class="btn btn-flat btn-danger delete" orgpersonid="{{orgpersonid}}"  personid="{{personid}}" organizationid="{{organizationid}}">Delete</a>
</td>
                       	 </tr>              
                       {{/each}}         
                    </tbody>
                </table>
</div>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div> 
		</div>
</script>


<script id="planinfotmpl" type="text/x-handlebars-template">
 {{#each .}}
<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
				<h4 id="plannamedetailsmodal" class="modal-title">Plan details for {{planname}}</h4>
	</div>
		<div class="modal-body">
<form class="form-horizontal">
					<div class="form-group">
							<label class="control-label col-md-5">Plan Name : </label>
							<div class="controls-label col-md-4">
								{{#if planname}}
								{{planname}}
								{{else}}
									--
								{{/if}}
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-5">Plan Amount (<i class="fa <s:text name='label.price'></s:text>"></i>): </label>
							<div class="controls-label col-md-4">
								{{#if planamount}}
								{{planamount}}
								{{else}}
									--
								{{/if}}
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-5">Plan Start Date : </label>
							<div class="controls-label col-md-4">
								{{#if planstartdate}}
								{{planstartdate}}
								{{else}}
									--
								{{/if}}
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-5">Plan End Date : </label>
							<div class="controls-label col-md-4">
								{{#if planenddate}}
								{{planenddate}}
								{{else}}
									--
								{{/if}}
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-5">Date of subscription : </label>
							<div class="controls-label col-md-4">
								{{#if dateofsubscription}}
								{{dateofsubscription}}
								{{else}}
									--
								{{/if}}
							</div>
						</div>
			</form>
  {{/each}}
</script>


<script id="orginfotmpl" type="text/x-handlebars-template">
    	 <table class="table table-bordered table-striped whitebg table-list-search manageprofilecont">
                    <thead>
                        <tr>
                            <th>Sl. No</th>
                            <th>Plan Name </th>
                            <th>Plan Amount(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
                            <th>Start Date</th>
							<th>End Date</th>
                       </tr>
                    </thead>
                    <tbody>
						{{#each .}}
                        	<tr> 
                            	<td>{{math @index "+" 1}}</td>
                            	<td>{{planname}}</td>
   
								<td>{{planamount}}</td>
                                <td>{{dateParser planstartdate}}</td>
                                <td>{{dateParser planenddate}}</td>
								
                            	
                       	 </tr>              
                       {{/each}}         
                    </tbody>
                </table>   
</script>


<script id="courseinfotmpl" type="text/x-handlebars-template">
<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
				<h4 id="plannamedetailsmodal" class="modal-title">Course details for <span class="coursedetails"></span></h4>
		</div>
		<div class="modal-body">
    	 <table class="table table-bordered table-striped whitebg table-list-search manageprofilecont">
                    <thead>
                        <tr>
                            <th>Sl. No</th>
                            <th>Course Title </th>
                            <th>Price(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
                          	<th>Status</th>
                       </tr>
                    </thead>
                    <tbody>
						{{#each .}}
                        	<tr>
                            	<td>{{math @index "+" 1}}</td>
						{{#if coursetitle}}
                            	<td>{{coursetitle}}</td>
							{{else}}
								<td>---</td>
						{{/if}}
						{{#if price}}
                                <td>{{price}}</td>
							{{else}}
								<td>---</td>
						{{/if}}
                  				<td>{{{status coursestatus}}}</td>          	
                       	 </tr>              
                       {{/each}}         
                    </tbody>
                </table>   
		</div>


</script>

<script id="userinfotmpl" type="text/x-handlebars-template">
<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
				<h4 id="plannamedetailsmodal" class="modal-title">User details for <span class="userdetails"></span></h4>
		</div>
		<div class="modal-body">

    	 <table class="table table-bordered table-striped whitebg  table-list-search manageprofilecont">
                    <thead>
                        <tr>
                            <th>Sl. No</th>
                            <th>Email</th>
                          	<th>Status</th>
                       </tr>
                    </thead>
                    <tbody>
						{{#each .}}
                        	<tr>
                            	<td>{{math @index "+" 1}}</td>
						{{#if emailid}}
                                <td>{{emailid}}</td>
						{{else}}
								<td>---</td>
						{{/if}}
           				{{#if courseinvitationstatus}}
                                <td>{{{userstatus courseinvitationstatus}}}</td>
						{{else}}
								<td>---</td>
						{{/if}}                 	
                       	 </tr>              
                       {{/each}}         
                    </tbody>
                </table>   
		</div>
</script>


<script id="subscriptionplantmplt" type="text/x-handlebars-template">
<form action="" name="coursepayment" method="post"> 
 <div class="well" style="background-color:white">

{{#each .}}
		<!--<div style="color: #B42E33; margin-left:10px;" class='orgnamess'><b>Organization Name : {{@key}}</b></div>
		<br> -->
		<!--<table class="table table-list-search manageprofilecont table-bordered table-striped whitebg paginationtab">
			<thead style="color: #B42E33 !important; background-color:white !important;">
				<tr>
                   	<td><b>Organization Name : {{@key}}</b></td>
				</tr>
			</thead>	
		</table>-->
		<table class="table table-list-search manageprofilecont table-bordered table-striped whitebg paginationtab">
             	<!--<thead style="color: #B42E33 !important; background-color:white !important;">
					<tr>
                   		<td colspan='7'><b>Organization Name : {{@key}}</b></td>
					</tr>
				</thead>-->
					<thead>
						<tr style="color: #B42E33 !important; background-color:white !important;">
                   			<td colspan='7'><b>Organization Name : {{@key}}</b></td>
						</tr>
                        <tr>
                            <th>Plan Name</th>
                            <th>Plan Start Date</th>
                            <th>Plan End Date</th>
                            <th>Courses / Max Courses</th>
							<th>Users / Max Users</th>
							<th>Duration</th>
							<th>Durationtype</th>
                       </tr>
                    </thead>
                    <tbody>
						{{#each .}}
                        	<tr>
                            	<td>{{planname}}</td>
								{{#if planstartdate}}
                                <td>{{planstartdates planstartdate}}</td>
								{{else}}
                                <td>--</td>
								{{/if}}
								{{#if planenddate}}
								<td>{{planenddates planenddate}}</td>
								{{else}}
								<td>--</td>
								{{/if}}
								{{#if noofcourses}}
								<td class="courselist" organizationid="{{organizationid}}" orgpersonid="{{orgpersonid}}" personid="{{personid}}" orgplansubscriptionid={{id}} orgname="{{orgname}}"><a class="badge" style="text-decoration: underline !important;margin-left:20px;backgound-color:#AEE5E1;">{{noofcourses}}/
								<b>{{maxcourse}}</b> </a>  
								</td>
								{{else}}
								<td organizationid="{{organizationid}}" orgpersonid="{{orgpersonid}}" personid="{{personid}}"><a class="badge" style="margin-left: 20px;cursor:alias;">{{noofcourses}}/
								<b>{{maxcourse}}</b> </a>      
								</td>
								{{/if}}
								{{#if noofusers}}
  								<td class="userlist" organizationid="{{organizationid}}" orgpersonid="{{orgpersonid}}"  orgplansubscriptionid={{id}} orgname="{{orgname}}"><a class="badge"style="text-decoration: underline !important;margin-left: 20px;margin-left: 20px;">{{noofusers}}/
								<b>{{maxusers}}</b></a></td>
								{{else}}
								<td class="" organizationid="{{organizationid}}" orgpersonid="{{orgpersonid}}"><a class="badge" style="margin-left: 20px;cursor:alias;">{{noofusers}}/
								<b>{{maxusers}}</b></a></td>
								{{/if}}
								<td>{{duration}}</td>
								<td>{{durationtype}}</td>
                       	 </tr>              
                       {{/each}}         
                    </tbody>
                </table>
{{/each}}
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div> 
</div>
</form>
</script>

<script id="nodatatemp" type="text/x-handlebars-template">
<h3 class="nodata" align="center">No Data Found</h3>
</script>
<script id="nodatatempsubsplandetails" type="text/x-handlebars-template">

<table class="table table-list-search manageprofilecont table-bordered table-striped whitebg ">
                    <thead>
                        <tr>
                            <th>Plan Name</th>
                            <th>Plan Start Date</th>
                            <th>Plan End Date</th>
                            <th>MaxCourse</th>
							<th>MaxUsers</th>
							<th>Duration</th>
							<th>Durationtype</th>
                       </tr>
                    </thead>
                    <tbody>
					<tr>
						<td colspan='7'>Data not found</td>
					</tr>      
                    </tbody>
                </table>
</script>
<script id="nodatatempplandetails" type="text/x-handlebars-template">

<table class="table table-list-search manageprofilecont">
                    <thead>
                        <tr>
                            <th>Sl. No</th>
                            <th>Plan Name </th>
                            <th>Plan Amount(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
                            <th>Start Date</th>
							<th>End Date</th>
                       </tr>
                    </thead>
                    <tbody>
					<tr>
						<td colspan='5' style="text-align:center;">
						<h5 class="alert alert-error">No Data Found</h5>
						</td>
					</tr>      
                    </tbody>
                </table>
</script>

<script id="orgdetail" type="text/x-handlebars-template">
{{#each .}}	
<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
				<h4 id="plannamedetailsmodal" class="modal-title">Organization details for {{orgname}}</h4>
		</div>
		<div class="modal-body">
			<form class="form-horizontal">
					<div class="form-group centertext">
							<div class="controls-label col-md-4 col-md-offset-3">
								<img src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&amp;r2={{logopath}}" class="thumbnailimage width100 height140">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 righttext">Organization Name : </label>
							<div class="controls-label col-md-8">
								{{#if orgname}}
								{{orgname}}
								{{else}}
									--
								{{/if}}
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-4 righttext">Phone Number : </label>
							<div class="controls-label col-md-8">
								{{orgphonenumber}}
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-4 righttext">Organization Email : </label>
							<div class="controls-label col-md-8">
								{{#if orgemailid}}
								{{orgemailid}}
								{{else}}
									--
								{{/if}}
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 righttext">Personal Email : </label>
							<div class="controls-label col-md-8">
								{{#if userid}}
								{{userid}}
								{{else}}
									--
								{{/if}}
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 righttext">Domain Url : </label>
							<div class="controls-label col-md-8">
								{{#if domainurl}}
								{{domainurl}}
								{{else}}
									--
								{{/if}}
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 righttext">Organization Url : </label>
							<div class="controls-label col-md-8">
								{{#if orgurl}}
								{{orgurl}}
								{{else}}
									--
								{{/if}}
							</div>
						</div>
			</form>
</div>
{{/each}}
</script>
</head>
<body>
	<div class="row titlecontainer">
		<div class="row">
			<div class="col-md-12 lefttext">
				<a href="<%=request.getContextPath()%>/app/dashboard" style="color: #384158;margin-left: 100px;">Dashboard
				</a> / Organizations
			</div>
		</div>
	</div>
	<div class="container-fluid pad0">
		<div class="row">
			<div class="col-md-12 pad0">
				<div class="col-md-3" style="box-shadow: 0px 1px 10px rgba(40,45,47,0.30);height: 220px;background: white;">
					<div class="leftsideul martop10">
						<li class="leftsideliheader">
							<h3>
								Organization
								<button type="button" class="navbar-toggle mar0 pad0"
									data-toggle="collapse" data-target="#orgdiv"
									aria-expanded="false">
									<span class="sr-only">Toggle navigation</span> <i
										class="fa fa-bars"></i>
								</button>
							</h3>
						</li>
						<div id="orgdiv" class="collapse navbar-collapse pad0">
							<li class="leftsideli hover active vieworganization">Organizations<span
								class="count orgcount"></span>
							</li>
							<li class="leftsideli hover trashed">Deactivated
								Organization<span class="count trashcount"></span>
							</li>
							<li class="leftsideli hover subscriptionplandetail">Subscription
								Plan Details</li>
						</div>
					</div>
				</div>
				<div class="col-md-9">
					<div class="row pad0">
						<div class="col-md-12 orgsearch">
							<h3
								class="cattitle padleft0 marleft0 martop10 col-md-4 marbottom15 size20">Organizations</h3>
							<div class="leftside searchcoursetext searchtext col-md-5"
								id="searchtext">
								<input type="text" class="system-search form-control"
									id="system" placeholder="search ...." />
							</div>
						</div>
						<div class="col-md-12 deorgsearch" style="display: none;">
							<h3
								class="cattitle padleft0 marleft0 martop10 col-md-4 marbottom15 size20">DeActivated
								Organizations</h3>
							<div class="leftside searchcoursetext searchtext col-md-5"
								id="searchtext">
								<input type="text" class="system-search form-control"
									id="system" placeholder="search ...." />
							</div>
						</div>
						<div class="col-md-12 orgplan" style="display: none;">
							<h3
								class="cattitle padleft0 marleft0 martop10 col-md-4 marbottom15 size20">Subscription
								Plan Details</h3>
						</div>
					</div>
					<div class="row pad0">
						<div class="col-md-12">
							<div id="contentdisplay"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="orgcreationmodal" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog width60per">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 id="myModalLabel" class="modal-title">Create Organization</h4>
				</div>
				<div class="modal-body">
					<div class="alert alert-success" hidden="true" id="alertBox">
						<strong id="Message"></strong>
						<command id="msg"></command>
					</div>
					<form class="form-horizontal" method="post"
						action="saveorganization" enctype="multipart/form-data"
						name="organizationcreationform">
						<h3 class="titleh3">Organization Details</h3>
						<hr>
						<div class="alert alert-danger" hidden="true" id="alertBoxOrg">
							<strong>warning !</strong> <br>
							<command id="alertBoxorgmsg"></command>
						</div>
						<div class="form-group">
							<label class="control-label col-md-5" for="">Organization
								Name</label>
							<div class="col-md-6">
								<input type="text" class="form-control" name="organizationname"
									id="organizationname" placeholder="Enter Organization Name"
									required="required">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-5" for="">Organization
								Logo</label>
							<div class="col-md-6">
								<input type="file" class="form-control" id="organizationlogo"
									required="required" name="fileUpload"
									placeholder="Enter Organization Logo">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-5" for="">Email</label>
							<div class="col-md-6">
								<input type="email" class="form-control" name="email" id="email"
									placeholder="Enter Email" required="required">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-5" for="">Contact no</label>
							<div class="col-md-6">
								<input type="text" class="form-control" name="contactno"
									id="contactno" placeholder="Enter Contact no"
									required="required">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-5" for="">Name of the
								portal</label>
							<div class="col-md-6">
								<input type="text" class="form-control" name="portalname"
									required="required" id="nameofportal"
									placeholder="Enter Name of the portal">
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-5" for="">Organization
								url</label>
							<div class="col-md-6">
								<input type="text" class="form-control" name="organizationurl"
									required="required" id="organizationurl"
									placeholder="Enter Organization url">
							</div>
						</div>
						<input type="hidden" id="orgid" name="organizationidhidden">
						<input type="hidden" id="contactinfoid" name="contctinformationid">
						<input type="hidden" id="emailid" name="emailid"> <input
							type="hidden" id="phoneid" name="phoneid"> <input
							type="hidden" id="contactinfoid1" name="contctinformationid1">
						<input type="hidden" id="emailid1" name="emailid1"> <input
							type="hidden" id="phoneid1" name="phoneid1"> <input
							type="hidden" id="urlid" name="urlid"> <input
							type="hidden" id="orgpersonid" name="orgpersonid"> <input
							type="hidden" id="loginid" name="loginid"> <input
							type="hidden" id="roleid" name="roleid"> <input
							type="hidden" id="departmentid" name="departmentid"> <input
							type="hidden" id="addressid" name="addressid"> <input
							type="hidden" id="personid" name="personid">

						<h3 class="titleh3">Basic Details</h3>
						<hr>
						<div class="form-group">
							<label class="control-label col-md-5" for="">FirstName</label>
							<div class="col-md-6">
								<input type="text" class="form-control" name="orgfirstname"
									id="orgfirstname" placeholder="Enter Organization Name">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-5" for="">LastName</label>
							<div class="col-md-6">
								<input type="text" class="form-control" id="orglastname"
									name="orglastname" placeholder="">
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-5" for="">Email</label>
							<div class="col-md-6">
								<input type="email" class="form-control" name="orgemail"
									id="orgemail" placeholder="">
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-5" for="">Contact No</label>
							<div class="col-md-6">
								<input type="text" class="form-control" name="orgcontactno"
									id="orgcontactno" placeholder="Enter Contact no"
									required="required">
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-5" for="">UserName</label>
							<div class="col-md-5">
								<input type="text" class="form-control" name="orgusername"
									id="orgusername" placeholder="Enter Name of the portal">
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-5" for="">Password</label>
							<div class="col-md-5">
								<input type="password" class="form-control" name="orgpassword"
									required="required" id="orgpassword"
									placeholder="Enter Organization url">
							</div>
						</div>
						<div class="form-group">
							<div class="controls">
								<input type=submit>Submit</input>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="orgmessage">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Message</h4>
				</div>
				<div class="modal-body">
					<p class="description succesmsg" style="padding: 10px;"></p>
				</div>
				<div class="modal-footer">
					<a class="btn btn-flat btn-blue" data-dismiss="modal">Close</a>
				</div>
			</div>
		</div>
	</div>
	<div id="plannamedetails" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog width60per">
			<div class="modal-content">
				<div id="planinfotable"></div>
			</div>
		</div>
	</div>

	<div id="orgdetailslist" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog width60per">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title">
						Plan details for <span class="orgdetails"></span>
					</h4>
				</div>
				<div class="modal-body" style="padding: 10px;">
					<div id="orginfotable"></div>
				</div>
			</div>
		</div>
	</div>
	<div id="orgdetailslist" class="modal hide fade in" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="false">
		<div class="modal-dialog width60per">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<font class=" rightpanetitle" style="border-bottom-style: none;">
						<b>Plan Details for <font class="orgdetails"></font></b>
					</font> <label class="coursehiddenid" style="display: none;"></label>
				</div>
				<div class="modal-body" style="padding: 10px;">
					<div id="orginfotable"></div>
				</div>
				<div class="modal-footer">
					<button class="btn btn-flat" data-dismiss="modal"
						aria-hidden="true">Close</button>
				</div>
			</div>
		</div>
	</div>
	<script
		src="<%=request.getContextPath()%>/assets/app/js/organization/organization.js"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/organization/filter.js"></script>
</body>
</html>