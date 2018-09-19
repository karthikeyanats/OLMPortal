<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.dashbox h3,h5 {
	text-align: center;
}
.panel-primary>.panel-heading{
  background-color: #14bdee;
  border-color: #14bdee;
  border-top-color: #14bdee;
}

.panel{
   
  border: 0px solid transparent;
}
a{

  color: #384158;
}
 .badge {
    display: inline-block;
    min-width: 15px;
    padding: 8px 7px;
    font-size: 24px;
    font-weight: 700;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    background-color: #ffaf00;
    border-radius: 24px;
    width: 49px;
}
.badge {
    display: inline-block;
    min-width: 15px;
    padding: 8px 7px;
    font-size: 24px;
    font-weight: 700;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    background-color: #ffaf00;
    border-radius: 24px;
    width: 49px;
}
.badge {
    display: inline-block;
    min-width: 15px;
    padding: 8px 7px;
    font-size: 24px;
    font-weight: 700;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    background-color: #ffaf00;
    border-radius: 24px;
    width: 49px;
}
.badge {
    display: inline-block;
    min-width: 15px;
    padding: 8px 7px;
    font-size: 24px;
    font-weight: 700;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    background-color: #ffaf00;
    border-radius: 24px;
    width: 49px;
}
.badge {
    display: inline-block;
    min-width: 15px;
    padding: 8px 7px;
    font-size: 24px;
    font-weight: 700;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    background-color: #ffaf00;
    border-radius: 24px;
    width: 49px;
}
.badge {
    display: inline-block;
    min-width: 15px;
    padding: 8px 7px;
    font-size: 24px;
    font-weight: 700;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    background-color: #ffaf00;
    border-radius: 24px;
    width: 49px;
} 
.progress{
  
   height: 7px;
   margin-top: 8px;

}
.panel-title{

  color: #fff;
}

 .col-md-6 {
     width: 45%;
}
.svg-icon {
  width: 1em;
  height: 1em;
}

.svg-icon path,
.svg-icon polygon,
.svg-icon rect {
  fill: #30a5ff;
}

.svg-icon circle {
  stroke: #30a5ff;
  stroke-width: 1;
}
</style>
<script id="topcategoriestmpl" type="text/x-handlebars-template">
<div class="" style="padding:50px;margin-top: -27px;">
  {{#if .}}
  {{#each .}}
  <div class="container-fluid">
			<h4 class="pull-left" style="margin-left: -13px;">{{label}}</h4> 
			<p class="pull-right">{{value}}%</p>
		</div>
  <div class="progress">
    <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:{{value}}%;background-color:{{color}};"></div>
  </div>
{{/each}}{{else}}
		<tr>
			<td class="centertext" colspan="2">No Data Found</td>			
		</tr>{{/if}}
</div>
</script>
<script id="topcoursestmpl" type="text/x-handlebars-template">
<div class="" style="padding:50px;margin-top: -27px;">
  {{#if .}}
  {{#each .}}
  <div class="container-fluid">
			<h4 class="pull-left" style="margin-left: -13px;">{{label}}</h4> 
			<p class="pull-right">{{value}}%</p>
		</div>
  <div class="progress">
    <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:{{value}}%;background-color:{{color}};"></div>
  </div>
{{/each}}{{else}}
		<tr>
			<td class="centertext" colspan="2">No Data Found</td>			
		</tr>{{/if}}
</div>
</script>
<script id="nodatatmpla" type="text/x-handlebars-template">
<h3 class="nodata" align="left">No Data Found</h3>
</script>
<script id="nodatatmpl" type="text/x-handlebars-template">
<h3 class="nodata" align="left">No Data Found</h3>
</script>
<script id="newCourseCountstmpl" type="text/x-handlebars-template">
<div class="col-md-12 martop10">
	<div class="col-md-3 whitebg boxshadow dashbox" lt="draft">
		<em class="fa fa-xl fa-shopping-cart color-blue" style=" font-size: 3em; color: #512da8; margin-left: 46px;"></em>
        <h3 ><label class="badge badge-warning">{{draftCoursesCount}}</label></h3>
		<h5 ><a class="dashlink" lt="draft">Draft</a></h5>
	</div>
	<div class="col-md-3 whitebg boxshadow dashbox" lt="published">
        <em class="fa fa-xl fa-bullhorn color-blue" style=" font-size: 3em; color: #fc685f; margin-left: 52px;"></em>
		<h3 ><label class="badge badge-warning">{{publishedCoursesCount}}</label></h3>
		<h5 ><a class="dashlink" lt="published">Published</a></h5>
	</div>
    <div class="col-md-3 whitebg boxshadow dashbox" lt="approved">
        <em class="fa fa-xl fa-thumbs-up color-blue" style=" font-size: 3em; color: #29a22e; margin-left: 54px;"></em> 
		<h3 ><label class="badge badge-warning">{{approvedCoursesCount}}</label></h3>
		<h5 ><a class="dashlink" lt="approved">Approved</a></h5>
	</div>	
	<div class="col-md-3 whitebg boxshadow dashbox" lt="trashed">
        <em class="fa fa-xl fa-trash color-blue" style=" font-size: 3em; color: #f44336; margin-left: 57px;"></em>
		<h3 ><label class="badge badge-warning">{{trashedCoursesCount}}</label></h3>
		<h5 ><a class="dashlink" lt="trashed">Trashed</a></h5>
	</div>
</div>

</script>
<script id="newuserCountstmpl" type="text/x-handlebars-template">
{{#each .}}
<div class="col-md-12 martop10">
	<div class="col-md-3 whitebg boxshadow dashbox" lt="registered">
		<em class="fa fa-xl fa-male color-blue" style=" font-size: 3em; color: #30a5ff; margin-left: 60px;"></em>
        <h3 ><label class="badge badge-warning">{{registedusers}}</label></h3>
		<h5 ><a class="dashlink" lt="registered">Registered</a></h5>
	</div>
	<div class="col-md-3 whitebg boxshadow dashbox" lt="enrolled">
        <em class="fa fa-xl fa-graduation-cap color-blue" style=" font-size: 3em; color: #880e4f; margin-left: 46px;"></em>
		<h3 ><label class="badge badge-warning">{{enrolleduser}}</label></h3>
		<h5 ><a class="dashlink" lt="enrolled">Enrolled</a></h5>
	</div>
   <div class="col-md-3 whitebg boxshadow dashbox" lt="author">
        <em class="fa fa-xl fa-pencil-square color-blue" style=" font-size: 3em; color: #2bbbad; margin-left: 55px;"></em>
		<h3 ><label class="badge badge-warning">{{authors}}</label></h3>
		<h5 class="centertext "><a class="dashlink" lt="author">Authors</a></h5>
	</div>
	<div class="col-md-3 whitebg boxshadow dashbox" lt="deactivated">
        <em class="fa fa-xl fa-thumbs-down color-blue" style=" font-size: 3em; color: #ff5252; margin-left: 52px;"></em>
		<h3 ><label class="badge badge-warning">{{deactivatedusers}}</label></h3>
		<h5 class="centertext "><a class="dashlink" lt="deactivated">De-Activated</a></h5>
	</div>
</div>

{{/each}}
</script>
<script id="newuserCountsSubOrgtmpl" type="text/x-handlebars-template">
{{#each .}}
<div class="col-md-12 martopbottom10">
	<div class="col-md-6 whitebg boxshadow dashbox" lt="registered">
		<h3 >{{registedusers}}</h3>
		<h5 ><a class="dashlink" lt="registered">Registered</a></h5>
	</div>
	<div class="col-md-6 whitebg boxshadow dashbox" lt="enrolled">
		<h3 >{{enrolleduser}}</h3>
		<h5 ><a class="dashlink" lt="enrolled">Enrolled</a></h5>
	</div>
</div>
<div class="col-md-12 marbottom10">
	<div class="col-md-12 whitebg boxshadow dashbox" lt="deactivated">
		<h3 >{{deactivatedusers}}</h3>
		<h5 ><a class="dashlink" lt="deactivated">De-Activated</a></h5>
	</div>
</div>
{{/each}}
</script>
<script id="newuserRequeststmpl" type="text/x-handlebars-template">
{{#each .}}
<div class="col-md-12 marbottom10 pad0">
	<div class="col-md-6 whitebg boxshadow dashbox" lt="payment">
		<h3 >{{pendingpayment}}</h3>
		<h5 class="centertext "><a class="dashlink" lt="payment">Payment</a></h5>
	</div>
	<div class="col-md-6 whitebg boxshadow dashbox" lt="certificate">
		<h3 >{{pendingcertificates}}</h3>
		<h5 class="centertext "><a class="dashlink" lt="certificate">Certificate</a></h5>
	</div>
</div>
<div class="col-md-12 whitebg boxshadow dashbox" lt="royalty">
	<h3 >{{royaltyrequests}}</h3>
		<h5 class="centertext "><a class="dashlink" lt="royalty">Royalty</a></h5>
</div>
{{/each}}
</script>
<script id="newuserRequestsforsuborgtmpl"
	type="text/x-handlebars-template">
{{#each .}}
<div class="col-md-12 marbottom10 pad0">
	<div class="col-md-12 whitebg boxshadow dashbox" lt="payment">
		<h3 >{{pendingpayment}}</h3>
		<h5 ><a class="dashlink" lt="payment">Payment</a></h5>
	</div>	
</div>
<div class="col-md-12 pad0">
	<div class="col-md-12 whitebg boxshadow dashbox" lt="certificate">
		<h3 >{{pendingcertificates}}</h3>
		<h5 ><a class="dashlink" lt="certificate">Certificate</a></h5>
	</div>
</div>
{{/each}}
</script>
<script id="newuserAmountCollectedtmpl" type="text/x-handlebars-template">
<div class="col-md-12 whitebg boxshadow dashbox marbottom10">
	<h3 class="weeklycollected" iconprice="<s:text name='label.price'></s:text>">0</h3>
	<h5 class="centertext">Weekly</h5>
</div>
<div class="col-md-12 whitebg boxshadow dashbox marbottom10">
	<h3 class="monthlycollected" iconprice="<s:text name='label.price'></s:text>">0</h3>
	<h5 class="centertext">Monthly</h5>
</div>
<div class="col-md-12 whitebg boxshadow dashbox">
	<h3 class="yearlycollected" iconprice="<s:text name='label.price'></s:text>">0</h3>
	<h5 class="centertext">Yearly</h5>
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
		String organizationid = userDetailsAdaptor.getUser().getProperty(
				"organizationid");
		String orgstatus = userDetailsAdaptor.getUser().getProperty(
				"orgstatus");
		String orgname = userDetailsAdaptor.getUser()
				.getProperty("orgname");
		String personid = userDetailsAdaptor.getUser().getProperty(
				"orgpersonid");
	%>
	<input type="hidden" id="orgstatus" value="<%=orgstatus%>">
	<input type="hidden" id="organizationid" value="<%=organizationid%>">
	<input type="hidden" id="orgname" value="<%=orgname%>">
	<input type="hidden" id="orgpersonid" value="<%=personid%>">
	<input type="hidden" class="royaltyeffcttodate" value="">
	<input type="hidden" class="royaltyeffctfromdate" value="">
	<input type="hidden" class="royaltyeffctfromtodate" value="">
	<input type="hidden" class="emailaddress" value="">
	<span class="orgstatusspan" style="display: none" st="<%=orgstatus%>"></span>
	
	<div class="row titlecontainer">
			<div class="col-md-12 lefttext">
				<a href="/ecloudbaseweb/app/dashboard" style="color: #384158;margin-left: 100px;">
				</a>  <a> </a><span class="pull-right searchbtn" style="margin-right: 130px;"><!-- <i class="fa fa-search"></i> --></span>
			</div>
		</div>
	<div class="container-fluid marbottom10">
	    <div class="row" style="margin-top: 29px;margin-left: 150px;">
			<div class="col-md-12 pad0">
				<div class="col-md-6">
					<div class="panel panel-primary noborder">
						<div class="panel-heading dashheaderbg noborder">
							<h3 class="panel-title">
								<em class="fa fa-xl fa-graduation-cap color-blue" style="font-size: 20px;color: white;margin-left: 4px;"></em> Courses
							</h3>
						</div>
						<div class="panel-body pad0 col-md-12 lightgraybg boxshadow"
							id="newcoursescountholder" style="box-shadow: 0px 1px 10px rgba(40,45,47,0.30);height: 240px;background: white;"></div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="panel panel-primary noborder">
						<div class="panel-heading dashheaderbg noborder">
							<h3 class="panel-title">
								<em class="fa fa-xl fa-user color-blue" style="font-size: 20px;color: white;margin-left: 4px;"></em> Users
							</h3>
						</div>
						<div class="panel-body pad0 col-md-12 lightgraybg boxshadow"
							id="newuserscountholder" style="box-shadow: 0px 1px 10px rgba(40,45,47,0.30);height: 240px;background: white;"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div class="container-fluid marbottom10">
		<div class="row" style="margin-top: 31px;margin-left: 150px;">
			<div class="col-md-12 pad0">
				<div class="col-md-6">
					<div class="panel panel-primary noborder">
						<div class="panel-heading dashheaderbg noborder">
							<h3 class="panel-title">
								<em class="fa fa-xl fa-graduation-cap color-blue" style="font-size: 20px;color: white;margin-left: 4px;"></em>  Top  Categories
							</h3>
						</div>
						<div class="panel-body pad0 col-md-12 lightgraybg boxshadow"
							id="topcategoriestable" style="box-shadow: 0px 1px 10px rgba(40,45,47,0.30);height: 370px;background: white;"></div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="panel panel-primary noborder">
						<div class="panel-heading dashheaderbg noborder">
							<h3 class="panel-title">
								<em class="fa fa-xl fa-user color-blue" style="font-size: 20px;color: white;margin-left: 4px;"></em> Top  Courses
							</h3>
						</div>
						<div class="panel-body pad0 col-md-12 lightgraybg boxshadow"
							id="topcoursestable" style="box-shadow: 0px 1px 10px rgba(40,45,47,0.30);height: 370px;background: white;"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	
	
	
	

	
	<!-- <div class="container-fluid marbottom10">
		<div class="row">
			<div class="col-md-12 pad0">
				<div class="col-md-6" style="box-shadow: 0px 1px 10px rgba(40,45,47,0.30);height: 206px;background: white;">
					<div class="panel panel-primary noborder">
						<div class="panel-heading dashheaderbg noborder">
							<h3 class="panel-title">
								<i class="fa fa-book"></i> Top 5 Categories
							</h3>
						</div>
						<div class="panel-body pad0 pad10 col-md-12 lightgraybg boxshadow">
							<div class="col-md-6 pad0 padleft10 hidden-md hidden-xs" >
								<div id="topcategoriestable" ></div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="panel panel-primary noborder">
						<div class="panel-heading dashheaderbg noborder">
							<h3 class="panel-title">
								<i class="fa fa-book"></i> Top 5 Courses
							</h3>
						</div>
						<div class="panel-body pad10 col-md-12 lightgraybg boxshadow">
							<div class="col-md-6 pad0 padleft10 hidden-md hidden-xs" style="box-shadow: 0px 1px 10px rgba(40,45,47,0.30);height: 206px;background: white;">
								<div id="topcoursestable" style="box-shadow: 0px 1px 10px rgba(40,45,47,0.30);height: 206px;background: white;"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div> -->
	
	
	
	<form name="dashform" method="post" action=""></form>
	<script src="<%=request.getContextPath()%>/assets/chart/js/Chart.js"
		type="text/javascript"></script>
	<script
		src="<%=request.getContextPath()%>/assets/app/js/admin/AdminDashboard.js"
		type="text/javascript"></script>
</body>
</html>