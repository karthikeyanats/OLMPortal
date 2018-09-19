<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/plugins/daterangepicker/daterangepicker.css">
<script id="validationtempl" type="text/x-handlebars-template">
<div class='alert alert-danger alert-dismissible manageprofilecont' role='alert' >
   <button type='button' class='close' data-dismiss='alert' ><span aria-hidden='true'>×</span></button>
<strong>Warning!</strong> Please provide mandatory fields</div>
</script>
<script id="enrolllisttmpl" type="text/x-handlebars-template">
<table class="table table-bordered table-striped whitebg table-list-search">
							<thead>
								<tr>
									<th><span class="muted">Name</span></th>
									<th><span class="muted">Mail id</span></th>
									<th><span class="muted">Progress</span></th>									
								</tr>
							</thead>
							<tbody class="course-list">
							{{#if .}}
							{{#each .}}
								<tr >
									<td>{{usersname}} {{lastName}}</td><td>{{usermail}}</td><td>{{#if courseprogress}}{{courseprogress}} % {{else}}-{{/if}}</td>
								</tr>
							{{/each}}
							{{else}}
							<tr>
							<td colspan="3">No users</td>
							<tr>
							{{/if}}
							</tbody>
						</table>
</script>
<script id="coursedetailstmpl" type="text/x-handlebars-template">
<form method="post" name="coursedetailsmanage">

<h4 class="rightpanetitle">Courses</h4>
{{#if .}}
{{#each .}}
<div class="row-fluid individualcourserow">
	<div class="span2">
		<img class="courselogo" src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo">
	</div>
	<div class="span10">
		<h3 class="coursetitle" courseid="{{courseid}}">{{coursetitle}}</h3>
         <p class="detail">{{coursedescription}}</p>
		
		<a class="btn btn-flat btn-gray usercount pull-right" courseid="{{courseid}}" 
			data-toggle="modal" >{{price}}</a>
		
	</div>
</div>
{{/each}}
{{else}}
<div class="row-fluid individualcourserow" >
		<p class="center categorydescription nocoursetxt">No courses</p>
</div>
{{/if}}
<input type="hidden" name="courseid">
</form>
</script>

<script id="userdetailstmpl" type="text/x-handlebars-template">
<form method="post" name="coursedetailsmanage">
{{#each .}}
<div class="row-fluid pagestart">
		      <div class="span12 contentbackgound">
		       <section class="topboxsection ">
		       <div class="row-fluid">
		       <div class="span12">
		         <div class="span6">
		           <label >User</label>
		        </div>
		    
		       <div class="span2">
		         <label >Courses</label>
		          
		       </div>
		      <div class="span2">
		        <label >Action</label>
		      </div> 
		      </div>
		   
		     </div>
		          <hr >
		      <div class="row-fluid">
		      <div class="span12 well nice whitebg">
		        <div class="span6">
		             <div class="media">
					    <img class="img-thumbnail pull-left" src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{organizationlogo}}&filetype=logo" >
					     <div class="media-body">	
						   <strong class="media-heading">Raja</strong>					                    
						      <span class="admin-label label label-danger">admin</span>						                    
						         <br> <small> Human Resources (HR) </small>                   
						         <br> <small> raja@igrandee.com </small>      
				              </div>
		    	            </div>
		            </div>
		          
		         <div class="span2 ">
		            <span class="f_status label label-success">1 course</span>		
		         </div>  
		           
		        <div class="span2">
		          <ul class="nav " >
		<div class="btn-group">			  
					<a class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
					   	 <i class="icon-cog"></i>
					   	  <span class="caret" ></span>
					  		</a>
		 					<ul class="dropdown-menu userdropdown" >
                           	<li class="hover"><a href="#">Send a Message</a></li>
                       	 	<li><a href="#">Make  Admin</a></li>
                       	 	<li><a href="#">Remove User</a></li>
                            </ul>
	                   		 </li>
	              		  </ul>
				        </div>		          		            
		          </div>		      
		      </div>    
		   <div class="row-fluid">
		      <div class="span12 well nice whitebg">
		        <div class="span6">
		             <div class="media">
					    <img class="img-thumbnail pull-left" src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{organizationlogo}}&filetype=logo" style="padding: 0px 10px 10px 10px;height: 100px;">
					     <div class="media-body">	
						     <strong class="media-heading"> George William</strong>                    
						          <br> <small> IT/Engineering/Development </small>                    
						          <br> <small> williambenjamin_g@igrandee.com </small>   
				              </div>
		    	            </div>
		            </div>
		            
		         <div class="span2 ">
		            <span class="f_status label label-success">2 course</span>		
		         </div>  
		           
		        <div class="span2">
		          <ul class="nav " >
					 <div class="btn-group">
					  			<a class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
					   				 <i class="icon-cog"></i>
					   								 <span class="caret"></span>
					  								</a>
					 								<ul class="dropdown-menu userdropdown" >
					                            	<li class="hover"><a href="#">Send a Message</a></li>
					                           	 	<li><a href="#">Make  Admin</a></li>
					                           	 	<li><a href="#">Remove User</a></li>
					                            </ul>
					                   		 </li>
					              		  </ul>
		        </div>   
		           
		            
		          </div>
		      
		      </div>     
		      
		       </section>
		       
		      </div>  
		       
		    </div>
{{/each}}

<input type="hidden" name="courseid">
</form>
</script>
<script id="organizationwiseusertmplt" type="text/x-handlebars-template">
<h4 class="rightpanetitle">Users</h4>		             
{{#if .}}
{{#each .}}
{{#each orgnames}}
<div class="row-fluid individualcourserow">
	<div class="span2">
		<img class="courselogo" src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{organizationlogo}}&filetype=logo">
	</div>
	<div class="span6">
		<div class="media">
		     <div class="media-body">	
				   <strong class="media-heading">{{firstname}}</strong>					                    
		           <br> <small> {{users}}</small>      
			 </div>
		 </div>
	</div>
	<div class="span4">
		<ul class="nav " >
			<div class="btn-group">
				<a class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
					 <i class="icon-cog"></i>
				 	<span class="caret" ></span>
				</a>
		<ul class="dropdown-menu userdropdown" >
		   	<li class="hover"><a href="#">Send a Message</a></li>
		 	<li><a href="#">Make  Admin</a></li>
		 	<li><a href="#">Remove User</a></li>
		</ul>
					                   		 </li>
					              		  </ul>
	</div>
</div> 
{{/each}}
{{/each}}
{{else}}
<div class="row-fluid individualcourserow" >
		<p class="center categorydescription nocoursetxt">No courses</p>
</div>

{{/if}}
		               
</script>
<script id="subscriptionplantmplt" type="text/x-handlebars-template">
<form action="" name="coursepayment" method="post"> 
<h4 class="rightpanetitle" >Subscription Plan</h4>
 
<div class="widget-body tab-content row-fluid" >

<h4 >Basic Plan</h4>
<div id="basicplan"></div>

		<div class="">
{{#each .}}
		<div><b>{{@key}}</b></div>
		<br> 
		{{#each .}}
			
			<div class="well whitebg">
	<div class="row">
	<div class="col-md-3">
		<p class="package-title">{{name}}</p><p> (<i class="icon-book"> {{maxcourse}} course</i> - <i class="icon-user"> {{maxusers}} user)</i></p>
		<p class="price" style="text-align: left;">{{planamount}} / {{durationtype}}</p>
	</div>
		<div class="col-md-9">
		{{#if id}}
			{{{sid id}}}
		{{/if}} 	 	
			</div>
			</div>
		</div>
			<!--<div class="well">
				<div class="pull-left package-info">
					<div class="package-title">{{name}} (<i class="icon-book"> {{maxcourse}}course</i> - <i class="icon-user"> {{maxusers}}user)</i></div>
					<div class="price" style="text-align: left;">{{planamount}} / {{durationtype}}
				</div>
				</div>
			<div class='pull-left package-info'>
				<div class='package-title' style='margin-left: 35px;'>{{#if planid}}{{planid planid}}{{/if}}
			</div>
			</div>
				<div class="pull-right">
{{#if id}}
			{{{sid id}}}
{{/if}}
				</div>
				<div class="clear"></div>
			</div>-->
	{{/each}}
{{/each}}
	</div>
	
	<div class="tab-pane well well-small well-nice fade in" id="term2"
		style="background-color: white;">


		<div>

			<div >	
				With content subscription, you can access hundreds of courses at one
				low price of <strong>$29/m</strong> or less.
			</div>
			<div class="well" >
				<div class="pull-left package-info text-info">
					<div class="package-title">
						<i class="icon-ufo-subs sp sm"></i> Content Subscription <small>(12-month
							term)</small>
					</div>
				</div>
			</div>
			<h2 >Content
				Subscription Pricing</h2>

			<div class="price-tier-headers">
				<div class="flex-wrapper"
					style="text-align: left; margin-bottom: 10px;">
					<div >Number of Users</div>
					<div class="flex">Annual Subscription Price</div>
				</div>
			</div>
		</div>
	</div>
</div>
<input type="hidden" name="planid" class="planid" value="">
<input type="hidden" name="orgsubscriptionid" class="orgsubscriptionid" value="">
<input type="hidden" name="orgsubscriptionidval" class="orgsubscriptionidval" value="">
<input type="hidden" name="planpaymentid" class="planpayment" value="">
<input type="hidden" name="planamount" class="planname" value="">
<input type="hidden" name="durationtype" class="durationtype">
<input type="hidden" name="duration" class="duration">
</form>
</script>

<script id="basicplantmplt" type="text/x-handlebars-template">

		{{#each .}}
			<div class="well whitebg" >
	<div class="row">
	<div class="col-md-3">
		<p class="package-title">{{name}}</p><p> (<i class="icon-book"> {{maxcourse}} course</i> - <i class="icon-user"> {{maxusers}} user)</i></p>
		<p class="price" >{{planamount}} / {{durationtype}}</p>
	</div>
		<div class="col-md-9">
			{{{basicplanid id}}}
		</div>
			</div>
		</div>
	{{/each}}

</script>






<script id="courseAnalyticstmplt" type="text/x-handlebars-template">
<h4 class="marbottom15 size20 ">Course Analytics</h4>
<div class="row-fluid individualcourserow">
				 <table class="table table-striped table-borderd whitebg paginationtab">
							<thead>
								<tr>
									<th><span class="muted">Course</span></th>
									<th><span class="muted">Completed User</span></th>
									<th><span class="muted">Learning User</span></th>
									<th><span class="muted">Blocked User</span></th>
									<th><span class="muted">Wishlist User</span></th>
								</tr>
							</thead>
							<tbody class="course-list">
{{#if .}}
{{#each .}}
								<tr >
									<td>{{coursetitle}}</td>
									<td><a class="enrollstatus" style="cursor:pointer" title="Completed" status="C" courseid="{{courseid}}">
										{{#if completed}}									
											{{completed}}
										{{else}}
											0
										{{/if}}
										</a>
									</td>
									<td><a class="enrollstatus" style="cursor:pointer" title="Learning" status="A" courseid="{{courseid}}">
										{{#if learning}}									
											{{learning}}
										{{else}}
											0
										{{/if}}
										</a>
									</td>
									<td><a class="enrollstatus" style="cursor:pointer" title="Blocked" status="B" courseid="{{courseid}}">
										{{#if blocked}}									
											{{blocked}}
										{{else}}
											0
										{{/if}}
										</a>
									</td>
									<td><a class="enrollstatus" style="cursor:pointer" title="Wishlist" status="W" courseid="{{courseid}}">
										{{#if wishlist}}									
											{{wishlist}}
										{{else}}
											0
										{{/if}}
										</a>
									</td>
								</tr>
{{/each}}
{{else}}
<tr>
<td class="row-fluid individualcourserow" colspan="6">
		<p class="center categorydescription nocoursetxt">No courses</p>
</td>
<tr>
{{/if}}
							</tbody>
						</table>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
					</div>
</script>
<script id="basicprofiletempl" type="text/x-handlebars-template">
{{#each .}} 
<div id="validationdiv"></div>  
	<h3 class="marbottom15 martop10 size20">Basic Information
				<font class="error pull-right size12">* Fields are mandatory</font>
			</h3>
		<div class="form-horizontal whitebg pad10 manageprofilecont boxshadow marbottom10">
			<div class="form-group">
				<label class="col-sm-3 control-label">Organization Name <font class="error">*</font> </label>
				<div class="col-sm-4">
					<input type="text" required="required" contenteditable="true"
						class="form-control organizationname" value="{{organizationname}}" maxlength="45">				
						<div class="alert alert-danger martop10 pad10 alert-error organizationnamealert" style="display:none" >
              				<strong>Please</strong> Enter Organization name.
            			</div>
				</div>
			</div>
			<div class="form-group">
			<label class="col-sm-3 control-label">Organization Logo <font class="error">*</font></label>
				<div class="col-sm-4">
					<img src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{organizationlogo}}&filetype=logo"	class="courselogo thumbnailimage width100 height140 marbottom10"></img>  
					<input type="hidden" name="orglogo" class="orglogo" value="{{organizationlogo}}" > 
					<a class="btn white btn-blue changelogobtn">Change Image</a>
				</div>
					<div class="alert alert-danger martop10 pad10 alert-error selectimage" style="display:none" >
              		<strong>Please</strong>Select Image.
            	</div>
			</div>             
			<div class="form-group">
				<label class="col-sm-3 control-label"> Domain <font class="error" >*</font> </label>
				<div class="col-sm-4">
					<input type="text" required="required" contenteditable="true"  class="form-control domainurl" value="{{domainurl}}"  maxlength="45">
					<span class="" for="textinput"><strong>.reamedicloud.com</strong></span>
				<div class="alert alert-danger martop10 pad10 alert-error domainurlalert" style="display:none" >
              		<strong>Please</strong> Enter Domain URL
            	</div>
				</div>
				
			</div>			
			<div class="form-group">
				<label class="col-sm-3 control-label">Email<font class="error" >*</font> </label>
				<div class="col-sm-4">
					<input type="email" required="required" contenteditable="true"
						class="form-control   orgemailaddress" value="{{orgemailaddress}}" maxlength="45">
				<div class="alert alert-danger martop10 pad10 alert-error orgemailaddressalert" style="display:none">
              		<strong>Please</strong> Enter Valid Email Address.
            	</div>
				<div class="alert  alert-danger martop10 pad10 alert-error orgemailaddressalertvalid" style="display:none">
              		<strong>Please</strong> Enter valid Email Address.
            	</div>
				</div>
				
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">Phone Number <font class="error">*</font></label>
				<div class="col-sm-4">
                    <div class="input-group">
					   <span class="input-group-addon"> <i class="glyphicon glyphicon-plus"></i></span> 
					<input type="text" required="required" contenteditable="true" maxlength="12" 
						class="form-control  orgphonenumber trimvalue" value="{{orgphonenumber}}">
                     </div>
						<div class="alert alert-danger martop10 pad10 alert-error orgphonenumberalert" style="display:none">
              			<strong>Please</strong> Enter Contact Number
            		</div>
                    <div class="alert alert-danger martop10 pad10 alert-error minerror" style="display:none">
              		<strong>Please</strong> enter minimum 10 numbers
            		</div>				
				</div>			
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">Size Of Your Team <font class="error" >*</font></label>
				<div class="col-sm-4">
					<input type="text" required="required"contenteditable="true" 
						class="form-control   orgsizename" name="sizeoforg"value="{{teamsize}}" min= "1" maxlength="4">
				<div class="alert alert-danger martop10 pad10 alert-error orgsizenamealert" style="display:none">
              		<strong>Please</strong> Enter Size Of Team
            	</div>
				</div>				
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">Organization Url <font class="error">*</font></label>
				<div class="col-sm-4">
					<input type="text" required="required" contenteditable="true"
						class="form-control  orgurl" value="{{orgurl}}" maxlength="44">

				<div class="alert alert-danger martop10 pad10 alert-error orgurlalert" style="display:none">
              		<strong>Please</strong> Enter Organization Url
            	</div>
				</div>
 			</div>
 			
			<div class="form-group">
				<label class="col-sm-3 control-label"></label>
 				<div class="col-sm-4">
 					<span class="btn white btn-blue saveprofilebtn" organizationid="{{organizationid}}" 
					phoneid="{{orgphoneid}}" emailid="{{emailid}}" contactinfoid="{{contactinfoid}}" urlid="{{urlid}}"
					style="width: 100px;">Save</span>
 				</div>
			</div>
 		</div>
{{/each}}
</script>
<script id="orgsubscriptionplantmpl" type="text/x-handlebars-template">

<h4 class="rightpanetitle">Plan History</h4>
    	<table class="table table-bordered table-striped whitebg table-list-search manageprofilecont">
                    <thead>
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
								<td>{{planstartdate planstartdate}}</td>
							{{else}}
								<td>--</td>
							{{/if}}
							{{#if planenddate}}
								<td>{{planenddate planenddate}}</td>
							{{else}}
								<td>--</td>
							{{/if}}					
								<td style="text-align:center;"><span class="badge centertext white">
									<a style="cursor:pointer;" class="maxcourse white" 
									orgplansubscriptionid="{{id}}">{{publishedcoursecount}} </a> / {{maxcourse}}</span></td>
								<td ><span class="badge centertext white"><a style="cursor:pointer;" class="maxuser white"  orgplansubscriptionid="{{id}}">{{inviteduserscount}} </a> / {{maxusers}}</span></td>
								<td>{{duration}}</td>
								<td>{{durationtype}}</td>	
                       	 </tr>   
{{else}}
<tr>
<td colspan="7">Data Not Found</td>
<tr>           
                       {{/each}}         
                    </tbody>
                </table>   
</script>
<script id="courseinfotmpl" type="text/x-handlebars-template">
<div class="row-fluid" >
	<div class="span10">
		<!-- <font class=" rightpanetitle"> <b>Course Details for <font
				class="coursedetails"></font></b></font>--> <label class="coursehiddenid"
			style="display: none;"></label>
	</div>
	<div class="span2"></div>
</div>

<div class="span11"> 
    	 <table class="table table-list-search manageprofilecont">
                    <thead>
                        <tr>
                            <th>Sl. No</th>
                            <th>Course Title </th>
                            <th>Price</th>
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
<div class="row-fluid" >
	<div class="span10">
		<!-- <font class=" rightpanetitle"> <b>User Details for  <font
				class="coursedetails"></font></b></font> --><label class="coursehiddenid"
			style="display: none;"></label>
	</div>
	<div class="span2"></div>
</div>

<div class="span11"> 
    	 <table class="table table-list-search manageprofilecont">
                    <thead>
                        <tr>
                            <th>Sl. No</th>
                            <th>Email Id</th>
                          	<th> Status</th>
                       </tr>
                    </thead>
                    <tbody>
						{{#each .}}
                        	<tr>
                            	<td>{{math @index "+" 1}}</td>
                            	<td>{{emailid}}</td>
                            	<td>{{{userstatus courseinvitationstatus}}}
                       	 </tr>              
                       {{/each}}         
                    </tbody>
                </table>   
		</div>


</script>
</head>
<body>
	<%@ page
		import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
	<%
		UserDetailsAdaptor userDetailsAdaptor = null;
		userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String orgstatus = userDetailsAdaptor.getUser().getProperty(
				"orgstatus");
	%>
	<div class="container-fluid titlecontainer">
		<div class="row">
			<div class="col-md-12 lefttext">
				<a href="<%=request.getContextPath()%>/app/dashboard">Dashboard
				</a> / My Organization
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
								settings
								<button type="button" class="navbar-toggle mar0 pad0"
									data-toggle="collapse" data-target="#myorgdiv"
									aria-expanded="false">
									<span class="sr-only">Toggle navigation</span> <i
										class="fa fa-bars"></i>
								</button>
							</h3>
						</li>
						<div id="myorgdiv" class="collapse navbar-collapse pad0">
							<li class="leftsideli hover basicinfo active">Basic</li>
							<li class="leftsideli hover colorpatternlink">Color Pattern</li>
							<%
								if (orgstatus.equals("A")) {
							%>
							<li class="leftsideli hover subscrips">Subscription Plan</li>
							<li class="leftsideli hover subscriptionplandetail">Plan
								History</li>
							<%
								}
							%>
						</div>
					</ul>
					<ul class="pad0">
						<li class="leftsideliheader"><h3>
								Analytics
								<button type="button" class="navbar-toggle mar0 pad0"
									data-toggle="collapse" data-target="#organalyticsdiv"
									aria-expanded="false">
									<span class="sr-only">Toggle navigation</span> <i
										class="fa fa-bars"></i>
								</button>
							</h3></li>
						<div id="organalyticsdiv" class="collapse navbar-collapse pad0">
							<li class="leftsideli hover courseanalytics">Course
								Analytics</li>
							<li class="leftsideli hover paymentanalytics">Payment
								Analytics</li>								
						</div>
					</ul>
				</div>
				<div>
					<div class="col-md-9" id="userdetails"></div>
				</div>
			</div>
		</div>
	</div>


	<!-- New Logo Modal Start-->
	<div class="modal" id="changelogomodal">
		<form id="form1" enctype="multipart/form-data" method="post" action=""
			name="logoform">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">x</button>
						<h3>Change Logo</h3>
					</div>
					<div class="modal-body">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="control-label col-sm-4 righttext">Organization
									Logo : </label>
								<div class="col-sm-8 control-label">
									<input id="fileUpload" name="fileUpload" class="input-file "
										type="file" accept="image/jpg,image/png,image/jpeg,image/gif">
									<p class="help-block error">Recommended Formats :
										png,jpg,jpeg.
									<p class="help-block error">Recommended file Size is 1 MB.</p>
									<div class="alert alert-danger imagealert"
										style="display: none;">Invalid File Format</div>
									<div class="alert alert-danger imagesizealert"
										style="display: none;">The file Size specified is 1 MB.
										You cannot upload files greater than 1MB</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<input type="submit" style="display: none;"
							class="btn-flat btn btn-blue changelogosavebtn" value="Update" />
					</div>
					<input type="hidden" name="organizationid" class="organid" value="" />
				</div>
			</div>
		</form>
	</div>
	<!-- modal -->
	<div class="modal" id="messageinfomodal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Message</h4>
				</div>
				<div class="modal-body">
					<p class="categorydescription msginfo"></p>
				</div>
			</div>
		</div>
	</div>
	<div id="plannamedetails" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header"
					style="background-color: white; border-bottom: none;">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title">
						<span class="coursedetails">Course Details</span>
					</h4>
					<div class="alert alert-success" hidden="true" id="alertBlockBox">
						<strong>Message!</strong>
						<command id="blockmsg" style="margin-left: 0px"></command>
					</div>
				</div>
				<div class="modal-body" style="padding: 10px;">
					<div id="planinfotable"></div>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal for No of users Enrolled -->
	<div class="modal fade" id="enrolledusersmodal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">x</button>
					<h4 class="modal-title">
						<span class="resulttitle"></span> Users List
					</h4>
				</div>
				<div class="modal-body">
					<div id="enrolllistdiv"></div>
				</div>
			</div>
		</div>
	</div>
<span class="priceval" art='<s:text name="label.price"></s:text>' style="display:none"></span>
	<script type="text/javascript">
		$('#myTab a').click(function(e) {
			e.preventDefault();
			$(this).tab('show');
		});
	</script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/plugins/daterangepicker/moment.min.js"></script>
		<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/plugins/daterangepicker/daterangepicker.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/organization/paymentanalytics.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/organization/colorpattern.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/organization/organizationdatalist.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/organization/organizationuserlist.js"></script>
</body>
</html>