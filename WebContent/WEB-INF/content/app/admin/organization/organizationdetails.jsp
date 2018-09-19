<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script id="orgcoursestmpl" type="text/x-handlebars-template">
{{#each .}}
<div class="row-fluid individualcourserow" >
	<div class="span2">
		<img class="courselogo" src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo">
	</div>
	<div class="span10">
		<h3 class="coursetitle" courseid="{{courseid}}">{{coursetitle}}</h3>
         <p class="detail">{{coursedescription}}</p>
		
		<a class="btn btn-flat btn-gray usercount pull-right" courseid="{{courseid}}" style="margin-right:10px;" 
			data-toggle="modal" >{{price}}</a>
		</div>
	</div>
{{else}}
  <div class="row-fluid individualcourserow" style="min-height:400px;">
	<p class="center categorydescription nocoursetxt">No Courses</p>
</div>
{{/each}}

</script>
<script id="organizationwiseusertmplt" type="text/x-handlebars-template">
{{#each .}}
{{#each orgnames}}
<div class="row-fluid individualcourserow">
	<div class="span2">
		<img class="courselogo" src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{organizationlogo}}&filetype=logo">
	</div>
	<div class="span6">
		<div class="media" style="margin-top:20px">
		     <div class="media-body">	
				   <strong class="media-heading">{{firstname}}</strong>					                    
		           <br> <small> {{users}}</small>      
			 </div>
		 </div>
	</div>
	<div class="span4">
		<ul class="nav " style="margin-top:30px;">
			<div class="btn-group">
				<a class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
					 <i class="icon-cog"></i>
				 	<span class="caret" style="border-top-color: rgb(228, 237, 241);"></span>
				</a>
		<ul class="dropdown-menu userdropdown" style="right:0px;min-width:auto;left:auto;width:auto !important;">
		   	<li class="hover"><a href="#">Send a Message</a></li>
		 	<li><a href="#">Make  Admin</a></li>
		 	<li><a href="#">Remove User</a></li>
		</ul>
					                   		 </li>
					              		  </ul>
	</div>
</div>
{{/each}}
{{else}}
	 <div class="row-fluid individualcourserow" style="min-height:400px;">
	<p class="center categorydescription nocoursetxt">No Users</p>
</div>
{{/each}}
</script>
</head>
<body>
	<form>
		 <s:hidden name="organizationid" cssClass="organid" />
		 <s:hidden name="orgname" cssClass="orgname" />	
		 <s:hidden name="orgcount" cssClass="orgcount" />
		 <s:hidden name="orgtrashcount" cssClass="orgname" />		 
	</form>
			<div class="span3 leftpane">
				<ul class="leftsideul">
		
					<li class="leftsideli">
						<h3 class="categorytitle ">Organization Info</h3> 
					</li> 
					<li class="leftsideli organizationcourses  hover active">Courses<span class="count freeusercount"></span></li>
					<li class="leftsideli organizationusers hover">Users<span class="count paidusercount"></span></li>
				</ul>
			</div>			
			<div class="span9 rightpane">
			    <div class="rightpanetitle"></div>
				<div id="orgwisecourseuserlist">
				</div>
		  </div>
	<script type="text/javascript">
	$('#myTab a').click(function (e) {
		  e.preventDefault();
		  $(this).tab('show');
		});
	
	</script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/assets/app/js/organization/particularcourses.js"></script>	
</body>
</html>