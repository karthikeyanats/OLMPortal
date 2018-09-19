<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page language="java" import="java.util.ResourceBundle"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style type="text/css">
.btn{
    border-radius: 0px;
}
.btn-primaryone{
   background-color: #ff941a;
   color: #fff; 
   line-height: 2.428571;
}
.btn-primarytwo{
   background-color: #ec407a!important;
   color: #fff;
   line-height: 2.428571; 
}
.btn-primarythree{
   background-color: #c82333;
   color: #fff;
   line-height: 2.428571; 
}
.edit{
       display: inline-block;
    min-width: 10px;
    padding: 3px 7px;
    font-size: 12px;
    font-weight: 700;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    background-color: #14bdee;
    border-radius: 10px;
}
.trash{
    display: inline-block;
    min-width: 10px;
    padding: 3px 7px;
    font-size: 12px;
    font-weight: 700;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    background-color: #d9534f;
    border-radius: 10px;
}
.modal-content{
  border-radius: 0px;
}
.form-control{
 border-radius: 0px;
}
</style>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	ResourceBundle bundle = null;
	bundle = ResourceBundle.getBundle("application");
	String board = bundle.getString("label.board");
	String category = bundle.getString("label.category");
	String medium = bundle.getString("label.medium");
	String standardlevel = bundle.getString("label.standardlevel");
%>
<%@ page
		import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
	<%
		UserDetailsAdaptor userDetailsAdaptor = null;
		userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String orgstatus = userDetailsAdaptor.getUser().getProperty(
				"orgstatus");
		String email = userDetailsAdaptor.getUser().getProperty(
				"email");
	%>
	<%
		String link = request.getParameter("a");
	%>
<%-- <style type="text/css">
 .fa{
width:14px;
height:14px;
} 
</style> --%>
<script id="coursepartlinkstmpl" type="text/x-handlebars-template">
<div class="container-fluid martopbottom10 whitebg padtopbottom10">
	<div class="category-nav col-md-12 pad0">
			<li class="col-md-3" >
			<a class="col-md-12 boardlink btn btn-primary" st="boa" style="line-height: 2.428571;"><%=board%></a>
			</li>
			<li class="col-md-3"><a class="col-md-12 boardlink btn btn-primaryone" st="cat"><%=category%></a></li>
			<li class="col-md-3"><a class="col-md-12 boardlink btn btn-primarytwo" st="med"><%=medium%></a></li>
			<li class="col-md-3"><a class="col-md-12 boardlink btn btn-primarythree" st="sta"><%=standardlevel%></a></li>		
	</div>
</div>
<div class="alert alert-success alertmessages"
		style="margin: 0px 10px 10px; display: none;">
		<button type="button" class="close" data-dismiss="alert">×</button>
		<strong class="alertstatus">Well done!</strong> <font
			class="alertmessage">You successfully read this important
			alert message.</font>
</div>
<div class="container-fluid martopbottom10 whitebg padtopbottom10" id="coursepartLinksdescription">
</div>
</script>

<script id="boardstmpl" type="text/x-handlebars-template">
<div class="col-md-12 pad0 marbottom10">
	<font class="linkstitle pull-left size20">Boards</font>
	<span class="btn-group pull-right"> <a
		class="btn btn-default newboard btn-success">New <%=board%></a> <a
		class="btn btn-default boardslistbtn">Trashed Boards</a>
	</span>
</div>
<div class="form-horozontal boarddropbox" style="display:none">
	<div class="form-group col-md-12 whitebg">
  		<label class="col-sm-3 righttext"><%=board%> Name : </label>
  		<div class="col-sm-9">
    		<input id="textinput" type="text" placeholder="Enter <%=board%> Name" class="form-control boardnameval" maxlength="45">
			<a class="btn btn-danger pull-right closeboardbtn martopbottom10" >cancel</a>
			<a class="btn btn-success pull-right boardopbtn martopbottom10 marright7" boardid="">Save</a>
  		</div>
	</div>
</div>
<table class="table table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th><%=board%></th>
			<th>Actions</th>			
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
            <td>{{math @index "+" 1}}</td>
			<td>{{boardname}}</td>
			<td ><a style="cursor:pointer;" boardname="{{boardname}}" boardid="{{boardid}}" class="edit boardmanagelinks" op="edit"><i class="fa fa-edit" style="font-size:24px"></i></a>  
				<a style="cursor:pointer;" boardname="{{boardname}}" boardid="{{boardid}}" class="trash boardmanagelinks" op="trash">Trash</a></td>
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="3"><p class="center categorydescription">No <%=board%> found</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</script>

<script id="boardforcategoriestmpl" type="text/x-handlebars-template">
<div class="form-horizontal whitebg pad10 padbottom10">
<div class="form-group marbottom0">
  <label class="col-sm-3 righttext martop7">Select <%=board%> : </label>
  <div class="col-sm-6">
    <select id="boardforcategory" class="form-control">
{{#each .}}
<option value="{{boardid}}">{{boardname}}</option>
{{/each}}
</select>  
  </div>
</div>
</div>
<div id="boardcategoriestable" class="container-fluid martopbottom10 whitebg pad0"></div>
</script>

<script id="boardcategoriestmpl" type="text/x-handlebars-template">
<div class="col-md-12 marbottom10 pad0">
<font class="linkstitle pull-left size20">category</font>
<span class="btn-group pull-right"> <a
	class="btn btn-default newcategory btn-success">New <%=category%></a> <a
	class="btn btn-default boardslistbtn">Trashed <%=category%></a>
</span>
</div>
<div class="form-horizontal boarddropbox" style="display:none;">
	<div class="form-group col-md-12">
		<label class="col-sm-3 righttext"><%=category%> Name : </label> 
		<div class="col-sm-8"><input class="categorynameval form-control" placeholder=
               "Enter <%=category%> Name" type="text" maxlength="45" ></div> 
	</div>
	<div class="form-group">
		<label class="col-sm-3 righttext"><%=category%> Description : </label>
		<div class="col-sm-8">	<textarea class="form-control categorydescription" placeholder=
			"Enter <%=category%> Description" id="message" name="message"
			rows="5" maxlength="1000"></textarea></div>
	</div>
	<div class="form-group">
		<label class="col-sm-3"></label>
		<a class="btn btn-danger btn-flat closeboardbtn pull-right" style="margin-top: 10px;">Cancel</a>             
		<a class="btn btn-success btn-flat categoryopbtn pull-right" style="margin: 10px 10px 0px 10px;">Save</a>
	</div>
</div>
<table class="table table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th><%=category%></th>
			<th>Actions</th>			
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
            <td>{{math @index "+" 1}}</td>
			<td>{{coursecategoryname}}</td>
			<td ><a style="cursor:pointer;" coursecategorydescription="{{coursecategorydescription}}" coursecategoryname="{{coursecategoryname}}" coursecategoryid="{{coursecategoryid}}" class="badge boardmanagelinks" op="edit">Edit</a>  
				<a style="cursor:pointer;" coursecategorydescription="{{coursecategorydescription}}" coursecategoryname="{{coursecategoryname}}" coursecategoryid="{{coursecategoryid}}" class="badge boardmanagelinks" op="trash">Trash</a></td>
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="3"><p class="center categorydescription">No <%=category%> found</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</script>

<script id="mediumstmpl" type="text/x-handlebars-template">
<div class="col-md-12 marbottom10 pad0">
<font class="linkstitle pull-left size20">Mediums</font>
<span class="btn-group pull-right" style="margin-bottom:10px;"> <a
	class="btn btn-default newmedium btn-success">New <%=medium%></a> <a
	class="btn btn-default boardslistbtn">Trashed Mediums</a>
</span>
</div>
<div class="form-horozontal boarddropbox" style="display:none">
	<div class="form-group col-md-12 whitebg">
  		<label class="col-sm-3 righttext"><%=medium%> Name : </label>
  		<div class="col-sm-9"> 
    		<input id="textinput" type="text" placeholder="Enter <%=medium%> Name" class="form-control mediumnameval" maxlength="45"> 
			<a class="btn btn-danger pull-right closeboardbtn martopbottom10" >cancel</a>
			<a class="btn btn-success pull-right mediumopbtn martopbottom10 marright7" mediumid="">Save</a>
  		</div>
	</div>
</div>
<table class="table table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th><%=medium%></th>
			<th>Actions</th>			
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
            <td>{{math @index "+" 1}}</td>
			<td>{{mediumname}}</td>
			<td ><a style="cursor:pointer;" mediumname="{{mediumname}}" mediumid="{{mediumid}}" class="badge boardmanagelinks" op="edit">Edit</a>  
				<a style="cursor:pointer;" mediumname="{{mediumname}}" mediumid="{{mediumid}}" class="badge boardmanagelinks" op="trash">Trash</a></td>
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="3"><p class="center categorydescription">No <%=medium%> found</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</script>
<script id="standarlevelsstmpl" type="text/x-handlebars-template">
<div class="col-md-12 marbottom10 pad0">
<font class="linkstitle pull-left size20">Standard Level</font>
<span class="btn-group pull-right" style="margin-bottom:10px;"> <a
	class="btn btn-default newstandardlevel btn-success">New <%=standardlevel%></a> <a
	class="btn btn-default boardslistbtn">Trashed <%=standardlevel%></a>
</span>
</div>
<div class="form-horozontal boarddropbox" style="display:none">
	<div class="form-group col-md-12 whitebg">
  		<label class="col-sm-3 righttext"><%=standardlevel%> Name : </label>
  		<div class="col-sm-9">
    		<input id="textinput" type="text" placeholder="Enter <%=standardlevel%> Name" class="form-control standardlevelnameval" maxlength="45">
			<a class="btn btn-danger pull-right closeboardbtn martopbottom10" >cancel</a>
			<a class="btn btn-success pull-right boardopbtn martopbottom10 marright7" standardlevelid="">Save</a>
  		</div>
	</div>
</div>
<table class="table table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th><%=standardlevel%></th>
			<th>Actions</th>			
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
            <td>{{math @index "+" 1}}</td>
			<td>{{standardlevelname}}</td>
			<td ><a style="cursor:pointer;" standardlevelname="{{standardlevelname}}" standardlevelid="{{standardlevelid}}" class="badge boardmanagelinks" op="edit">Edit</a>  
				<a style="cursor:pointer;" standardlevelname="{{standardlevelname}}" standardlevelid="{{standardlevelid}}" class="badge boardmanagelinks" op="trash">Trash</a></td>
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="3"><p class="center categorydescription">No <%=standardlevel%> found</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
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
<div class="alert alert-danger dismissible duplicate" role="alert">
<strong>Warning!</strong> {{duplicatemessage}}</div>
</script>

<script id="coursestmpl" type="text/x-handlebars-template">
<li class="leftsideli leftsideheader"><h3 class="categorytitle">Categories</h3></li>
{{#each .}}
<li class="leftsideli hover courselinktodescbtn" coursecategoryname="{{coursecategoryname}}" 
coursecategoryid="{{coursecategoryid}}">{{coursecategoryname}}<span class="pull-right count">{{coursescount}}</span></li>
{{/each}}
</script>

<script id="coursesdesctmpl" type="text/x-handlebars-template">

<form method="post" name="coursemanage">
{{#if .}}
<h3 class="cattitle"><font class="catname"></font> - Courses</h3>
{{#each .}}

{{#each courses}}
<div class="row-fluid individualcourserow">
	<div class="span2">
		<img class="courselogo" src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo">
	</div>
	<div class="span10">
		<h3  courseid="{{courseid}}" style="color:hsl(200, 100%, 37%) !important;">{{coursetitle}}</h3>
		<p class="description">{{coursedescription}}</p>   
        <a class="btn btn-flat btn-gray pull-right  managecourse" courseid="{{courseid}}" style="margin-right:10px;">
	Manage</a>	
		<a class="btn btn-flat btn-gray usercount pull-right" courseid="{{courseid}}" style="margin-right:10px;" 
			data-toggle="modal"  href="#courseUserModel" coursetitle="{{coursetitle}}" >User</a>
		<a class="btn btn-flat btn-gray assign pull-right" data-toggle="modal" href="#assignUserModel" style="margin-right:10px;" 
				courseid="{{courseid}}" coursetitle="{{coursetitle}}" >Assign</a>
	</div>
</div>
{{/each}}
{{/each}}
<input type="hidden" name="courseid">
{{else}}
<h3 class="cattitle"><font class="catname"></font>Courses</h3>
<div class="row-fluid individualcourserow" style="min-height:450px;">
<p class="center categorydescription nocoursetxt">No courses Found</p>
</div>
{{/if}}
</form>
</script>
<script type="text/x-handlebars-template" id="alerttrashtmpl">
<div class="alert alert-success">
  <button type="button" class="close" data-dismiss="alert">&times;</button>
  Course Trashed Successfully
</div>
</script>
<script type="text/x-handlebars-template" id="alertrestoretmpl">
<div class="alert alert-success">
  <button type="button" class="close" data-dismiss="alert">&times;</button>
  Course Restored Successfully
</div>
</script>
<script id="draftcoursesdesctmpl" type="text/x-handlebars-template">
<form method="post" name="coursemanage">
<h3 class="marbottom15 martop10 size20">Draft Courses</h3>
<table class="table table-striped whitebg paginationtab">
	<thead>
		<tr>
            <th>Sl.No</th>
			<th>Course Title</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
		{{#if .}}
			{{#each .}}
			<tr>
				<td>{{math @index "+" 1}}</td>
				<td>{{coursetitle}}</td>
				<td>
					<a class="btn btn-primary mcoursebtn" 
						courseid="{{courseid}}" ><i class="fa fa-crosshairs marright7"></i>Manage</a>		
					<a class="btn btn-danger deletecourse" 
						courseid="{{courseid}}"><i class="fa fa-trash-o marright7"></i>Trash
					</a>
				</td>
			</tr>		
			{{/each}}
		{{else}}
			<tr>
				<td colspan="3" class="centertext">No Courses</td>
			</tr>
		{{/if}}
	</tbody>
</table>
<input type="hidden" name="courseid">
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</form>
</script>
<script id="publishedcoursestmpl" type="text/x-handlebars-template">
<h3 class="marbottom15 martop10 size20">Published Courses</h3>
<form method="post" name="contentapproveform">
<table id="alluserstable" class="table table-striped whitebg paginationtab">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Course Title</th>
			<th class="center">Actions</th>			
		</tr>
	</thead>
	<tbody>
		{{#if .}} 
		{{#each .}}
		<tr>
            <td>{{math @index "+" 1}}</td>
			<td> {{coursetitle}}</td>			
			<td class="center"><a class="btn btn-primary btn-gray contentapprovebtn" courseid="{{courseid}}"
				data-toggle="tooltip" title="Preview">
				<i class="fa fa-eye"></i></a>
     			</td> 
		</tr>
		{{/each}} 
		{{else}}
		<tr>
			<td colspan="3"><p class="center categorydescription">No Courses</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
<input type="hidden" name="courseid">
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</form>
</script>

<script id="approvedcoursestmpl" type="text/x-handlebars-template">
<h3 class="marbottom15 martop10 size20">Approved Courses</h3>
<form method="post" name="coursemanage">
<table class="table table-striped whitebg paginationtab">
	<thead>
		<tr>
            <th>Sl.No</th>
			<th>Course Title</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
		{{#if .}}
			{{#each .}}
			<tr>
				<td>{{math @index "+" 1}}</td>
				<td>{{coursetitle}}</td>
				<td>
					<a class="btn btn-primary managecourse" courseid="{{courseid}}">
						<i class="fa fa-crosshairs marright7"></i>Manage</a>	 
     				<a data-toggle="modal" class="btn btn-primary inviteuser" courseid="{{courseid}}" style="background-color: #609 !important;border-color: #609 !important;">
						<i class="fa fa-user marright7"></i>Invite</a>
					<a class="btn btn-warning authorinfobtn" auname="{{firstName}} {{lastName}}" 
					emailid="{{authoremailid}}"><i class="fa fa-info marright7"></i>Author Info</a>
				</td>
			</tr>		
			{{/each}}
		{{else}}
			<tr>
				<td colspan="3" class="centertext">No Courses</td>
			</tr>
		{{/if}}
	</tbody>
</table>
<input type="hidden" name="courseid">
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</form>
</script>

<script id="trashedcoursesdesctmpl" type="text/x-handlebars-template">
<h3 class="marbottom15 martop10 size20">Trashed Courses</h3>
<form method="post" name="coursemanage">
<table class="table table-striped whitebg paginationtab">
	<thead>
		<tr>
            <th>Sl.No</th>
			<th>Course Title</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
		{{#if .}}
			{{#each .}}
			<tr>
				<td>{{math @index "+" 1}}</td>
				<td>{{coursetitle}}</td>
				<td>
					<a class="btn btn-primary undodeletecourse" courseid="{{courseid}}" style="margin-right:10px;">
			<i class="icon-trash marright7"></i>Restore</a>
				</td>
			</tr>		
			{{/each}}
		{{else}}
			<tr>
				<td colspan="3" class="centertext">No Courses</td>
			</tr>
		{{/if}}
	</tbody>
</table>
<input type="hidden" name="courseid">
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</form>
</script>
<script id="nocoursestmpl" type="text/x-handlebars-template">
<h3 class="rightpanetitle"></h3>
<br><h3 style="text-align:center;color: rgb(213, 156, 156);">No Courses</h3><br>
</script>
<script id="userlisttempl" type="text/x-handlebars-template">
<div class="row-fluid">
	<div class="span12" style="margin-left: 0px;">
		{{#if assignusers}}
		<div class="row-fluid" style="margin-bottom: 10px;">
			<div class="span10">
				<font class="categorydescription"> <b>Course : <font
						class="coursetitleuser"></font></b></font> <label class="coursehiddenid"
					style="display: none;"></label>
			</div>
			<div class="span2">
				<a class="btn btn-flat btn-blue coursesubmit">Assign</a>
			</div>
		</div>
		<table class="table table-bordered">
			<thead>
				<tr class="success">
					<th>Name</th>
					<th class="center"><font class="btn btn-flat selectbtn"
						style="width: 80%">Select All</font></th>
				</tr>
			</thead>
			<tbody>
				{{#each assignusers}}
				<tr>
					<td><font class="username">{{firstName}}</font></td>
					<td class="center"><font class="btn btn-flat assignbtn"
						style="width: 80%" personid="{{personid}}"
						courseenrollmentid="{{courseenrollmentid}}">Select</font></td>
				</tr>
				{{/each}}
			</tbody>
		</table>
		{{else}}
		<div class="row-fluid" style="margin-bottom: 10px;">
			<div class="span10">
				<font class="categorydescription"> <b>Course : <font
						class="coursetitleuser"></font></b></font> <label class="coursehiddenid"
					style="display: none;"></label>
			</div>
			<div class="span2"></div>
		</div>
		<p class="description center">No users to assign</p>
		{{/if}}
	</div>
</div>
</script>

<script id="usercourselisttempl" type="text/x-handlebars-template">
<div class="row-fluid" style="margin-bottom: 10px;">
	<div class="span10">
		<font class="categorydescription"> <b>Course : <font
				class="coursetitleuser"></font></b></font> <label class="coursehiddenid"
			style="display: none;"></label>
	</div>
	<div class="span2"></div>
</div>
{{#if .}}
<table class="table table-bordered">
	<thead>
		<tr class="success">
			<th>Name</th>
			<th class="center"><font>Actions</font></th>
		</tr>
	</thead>
	<tbody>
		{{#each .}}
		<tr>
			<td><font class="username">{{firstName}}</font></td>
			<td class="center"><font class="btn btn-flat blockUserbtn"
				style="width: 80%" personid="{{orgpersonid}}" message="you are blocked from a course . Contact Admin"
				courseenrollmentid="{{courseenrollmentid}}">Block User</font></td>
		</tr>
		{{/each}}
	</tbody>
</table>
{{else}}
<p class="description center">User is not available</p>
{{/if}}
</div>
</script>
<script id="myearningscoursestmpl" type="text/x-handlebars-template">
<h3 class="martop10 size20">My Earnings
<span class="pull-right"><small style="font-sixe:17px;"><b>Note : </b> For Admin No royalty will be applied</span></small>
</h3>
<table class="table table-bordered table-striped whitebg paginationtab">
	<thead>
		<tr>
			<th>Course Name</th>
			<th>Price(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
			<th>Collected Amount(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
			<th>No of Users</th>			
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
			<td>{{coursetitle}}</td>
            <td>{{price}}</td>
			<td>{{totalprice}}</td>
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
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>
<script id="authorcoursestudentsinfotmpl"
	type="text/x-handlebars-template">

<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Name</th>
			<th>Email</th>
            <!--<th>Author Royalty Amount(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>
            <th>Organization Royalty Amount(<i class="fa <s:text name='label.price'></s:text>"></i>)</th>-->
			
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
        <!-- {{#if authorroyaltyamount}}
             <td>{{authorroyaltyamount}}</td>
         {{else}}
             <td>--</td>
         {{/if}}
         {{#if orgroyaltyamount}}
             <td>{{orgroyaltyamount}}</td>
		 {{else}}		
             <td>--</td>
         {{/if}}-->
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
<script id="authorCoursesForAdminTmpl" type="text/x-handlebars-template">
<h3 class="martop10 size20 authorCoursesForAdminTmplTitle">Author Courses</h3>
<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Course</th>
			<th>Author</th>	
		<th>Email address</th>		
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr class="rr">
			<td>{{math @index "+" 1}}</td>
			<td>{{coursetitle}}</td>
			<td>{{firstName}} {{lastName}}</td>
			<td>{{authoremailid}}</td>
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="4"><p class="centertext">No Courses</p></td>
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
<script id="authorCoursesForAdminPublishedTmpl" type="text/x-handlebars-template">
<h3 class="martop10 size20 authorCoursesForAdminTmplTitle">Author Courses</h3>
<form method="post" name="contentapproveformauthor">
<table class="table table-bordered table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Course</th>
			<th>Author</th>
			<th>Email address</th>
			<th>Actions</th>			
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr  class="rr">
			<td>{{math @index "+" 1}}</td>
			<td>{{coursetitle}}</td>
			<td>{{firstName}} {{lastName}}</td>
			<td>{{authoremailid}}</td>
			<td><a class="btn btn-primary contentapprovebtn" courseid="{{courseid}}">
				<i class="fa fa-eye"></i>	
			</a></td>
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="4"><p class="centertext">No Courses</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
<input type="hidden" name="courseid">
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</form>
</script>
</head>
<body>
	<input type="hidden" class="orgstatus" value="<%=orgstatus%>">
	<div id="alertdiv"></div>
	<span class="linkattr" style="display: none" link="<%=link%>"></span>
	<div class="alert alert-danger maxcourseuser" style="display: none;">
		Your current plan does not allow creation of courses more than <font
			class="maxcoursecount"> </font>. Consider upgrading your Plan <a
			class="orgplanlink">Click hear for plan</a>
	</div>
	<div class="alert alert-danger maxuser" style="display: none;">
		Your current plan does not allow creation of users more than <font
			class="maxcoursecount"> </font>. Consider upgrading your Plan <a
			class="orgplanlink">Click hear for plan</a>
	</div>
	<div class="">
		<div class="row titlecontainer">
			<div class="col-md-12 lefttext">
				<a href="<%=request.getContextPath()%>/app/dashboard" style="color: #384158;margin-left: 100px;">Dashboard
				</a> / <a>Courses </a><span class="pull-right searchbtn" style="margin-right: 130px;"><i
					class="fa fa-search" style="font-size: 20px;"></i></span>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row dropbox titlecontainer form-horizontal pad5"
			style="display: none;">
			<div class="col-md-12">
				<div class="col-sm-1 righttext martop3">Search :</div>
				<div class="col-sm-8 martop7">
					<input class="searchtextbox form-control" type="text"
						placeholder="Enter Keywords to search">
				</div>
				<div class="col-sm-3 lefttext">
					<a class="btn btn-primary searchkwrdbtn marright7"> <i
						class="icon-search marright7"></i>Search
					</a> <a class="btn btn-danger closebtn"> <i
						class="icon-remove-sign marright7"></i>Cancel
					</a>
				</div>
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12 pad0">
				<div class="col-md-3 pad0 martop10" style="box-shadow: 0px 1px 10px rgba(40,45,47,0.30);height: 600px;background: white;">
					<ul class="pad0">
						<li class="leftsideliheader">
							<h3>
								Courses
								<button type="button" class="navbar-toggle mar0 pad0"
									data-toggle="collapse" data-target="#mycoursesdiv"
									aria-expanded="false">
									<span class="sr-only">Toggle navigation</span> <i
										class="fa fa-bars"></i>
								</button>
							</h3>

						</li>
						<div id="mycoursesdiv" class="collapse navbar-collapse pad0">
							<%
								if (orgstatus.equals("M")) {
							%>
							<li class="leftsideli coursepartsbtn hover">Course Parts</li>
							<%
								}
							%>
							<li class="leftsideli hover createcoursebtn">Create Course</li>
							<li class="leftsideli hover draftcoursebtn">Draft Courses<span
								class="draftcoursecount count"></span>
							</li>
							<li class="leftsideli hover trashedcoursebtn">Trashed
								Courses<span class="trasedcoursecount count"></span>
							</li>
							<li class="leftsideli hover publishedcoursesbtn">Published
								Courses<span class="publishedcoursecount count"></span>
							</li>
							<li class="leftsideli hover publiedcoursebtn">Approved
								Courses<span class="approvedcoursecount count"></span>
							</li>
						</div>
					</ul>
					<%
								if (orgstatus.equals("M")) {
							%>
					<ul class="pad0">
						<li class="leftsideliheader">
							<h3>
								Author Courses
								<button type="button" class="navbar-toggle mar0 pad0"
									data-toggle="collapse" data-target="#authorcoursesdiv"
									aria-expanded="false">
									<span class="sr-only">Toggle navigation</span> <i
										class="fa fa-bars"></i>
								</button>
							</h3>

						</li>
						<div id="authorcoursesdiv" class="collapse navbar-collapse pad0">
							<li class="leftsideli hover auc" cstatus="D" at="Draft">Draft Courses<span
								class="count" at="Draft"></span>
							</li>
							<li class="leftsideli hover auc" cstatus="T" at="Trashed">Trashed
								Courses<span class="count" at="Trashed"></span>
							</li>
							<li class="leftsideli hover auc"  cstatus="P" at="Published">Published
								Courses<span class="count" at="Published"></span>
							</li>
							<li class="leftsideli hover auc"  cstatus="A" at="Approved">Approved
								Courses<span class="count" at="Approved"></span>
							</li>
						</div>
					</ul>
						<%
								}
							%>
					<%-- <%
						if (orgstatus.equals("M")) {
					%> --%>
					<ul class="pad0">
						<li class="leftsideliheader">
							<h3>
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
								class="count"></span></li>
						</div>
						<%-- <%
							}
						%> --%>
					</ul>
				</div>
				<div class="col-md-9 padright0">
					<div id="coursedescriptiontable"></div>
				</div>
			</div>
		</div>
	</div>
	<form name="browseuserform" method="post">
		<input type="hidden" name="keyword" value="">
	</form>

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

	<div id="assignUserModel" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 id="myModalLabel" class="modal-title size20">
						Assign Course
						</h3>
						<div class="alert alert-success" hidden="true" id="alertBox">
							<strong>Message!</strong>
							<command id="msg" style="margin-left: 0px"></command>
						</div>
				</div>
				<div class="modal-body">
					<div id="userlist"></div>
				</div>
			</div>
		</div>
	</div>

	<div id="courseUserModel" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 id="myModalLabel" class="modal-title size20">Registered
						User</h4>
					<div class="alert alert-success" hidden="true" id="alertBlockBox">
						<strong>Message!</strong>
						<command id="blockmsg" style="margin-left: 0px"></command>
					</div>
				</div>
				<div class="modal-body" style="padding: 10px;">
					<div id="usercourselist"></div>
				</div>
				<div class="modal-footer">
					<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
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
							<div class="col-md-12" id="authorcoursestudentinfo"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="inviteusersmodal" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<form class="form-horizontal" action="" method="post"
			name="invitenewusersform">
			<div class="modal-dialog">
				<div class="modal-content padleft10">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 id="myModalLabel" class="modal-title size20">Invite New
							Users</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div id="uploadextfilediv"></div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-5">Email address <span
								class="error">*</span></label>
							<div class="col-md-6">
								<textarea rows="4" cols="50" class="emailuser form-control"
									data-role="tagsinput" required="required" name="emailuser"
									autofocus id="emailuser" style="resize: none;"
									placeholder="Enter Email address here..."></textarea>
								<a class='btn btn-primary uploadextfile martop10'><i
									class="fa fa-upload marright7"></i>Upload From File</a>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-5">Message <span
								class="error">*</span></label>
							<div class="col-md-6">
								<textarea rows="4" cols="50" class="form-control"
									required="required" name="messagedescription"
									id="messagedescription" style="resize: none;"
									placeholder="Enter Message here..."></textarea>
								<p class="help-block">
									<font class="mesdesccharremaining">500</font> Characters
									Remaining
								</p>
							</div>
						</div>
						<div class="col-md-12" style="margin-left: 0px;">
							<div id="errormessage"></div>
						</div>
						<div class="alert alert-danger alert-dismissible inviteuseralert"
							style="display: none;"></div>
						<!-- <div class='alert alert-danger' style='display: none;'
									id='errormessage'></div>  -->
						<div class="form-group centertext martop10">
							<div class="col-sm-12">
								<input type="button" class="btn white btn-blue sendinvitation"
									value="Send Invitations"> <a class="btn sendingmess">
									Message is Sending .... <img alt=""
									src="<%=request.getContextPath()%>/assets/app/images/ajax-loader6.gif">
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>

	<div id="assignUserMsgModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3 id="myModalLabel" class="size20 martopbottom0">Message</h3>
				</div>
				<div class="modal-body">
					<command id="msg"></command>
				</div>
			</div>
		</div>
	</div>

	<div id="authorInfoModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3 id="myModalLabel" class="size20 martopbottom0">Author
						Information</h3>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-4 control-label righttext">Author
								Name : </label>
							<div class="col-sm-8">
								<p class="form-control-static  autname">Author</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label righttext">Email : </label>
							<div class="col-sm-8">
								<p class="form-control-static  autemail">email@example.com</p>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<input type="hidden" id="origin"
		value="<%=request.getParameter("origin")%>" />
	<input type="hidden" value="<%=email%>" class="orgemail">
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/livevalidation/js/jquery.validate.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/assets/app/js/course/courseparts.js"
		type="text/javascript"></script>
	<script
		src="<%=request.getContextPath()%>/assets/app/js/course/admincourselist.js"
		type="text/javascript"></script>
	<script
		src="<%=request.getContextPath()%>/assets/app/js/course/emailxl.js"
		type="text/javascript"></script>
</body>
</html>