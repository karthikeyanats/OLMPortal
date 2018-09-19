<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/flowplayer/skin/minimalist.css">
<script id="subjectpartstmpl" type="text/x-handlebars-template">
<div class="accordion lightgraybg pad5" id="accordion2" data-collapse-type="manual">
{{#each .}}
	<div class="accordion-group">
		<div class="accordion-heading pad10 lightgraybg" style="border-left:5px solid red">
			<a class="accordion-toggle" data-toggle="collapse"
				data-parent="#accordion2" href="#collapse{{coursesectionid}}">
				{{math @index "+" 1}} . {{coursesectiontitle}}
			</a>
		</div>		
		<div id="collapse{{coursesectionid}}" class="accordion-body collapse">
			<div class="accordion-inner">
				<ul style="margin-left:0px;">
				{{#each lectures}}
					<li class="hover contentlink" courselecturetitle="{{courselecturetitle}}" 
						coursesectiontitle="{{coursesectiontitle}}" courselectureid="{{courselectureid}}" 
						style="padding:10px;">{{math @index "+" 1}} . {{courselecturetitle}}</li>
				{{/each}}
				<ul>	
			</div>
		</div>	
	</div>
{{/each}}	
</div>
</script>
<script id="contentstatustmpl" type="text/x-handlebars-template"> 
{{#each .}}
<div class="col-md-12 marbottom10">
	<h4 class="mar0 martop10"><font class="cntstatusfor"></font>
		<span class="btn btn-flat btn-danger pull-right rejectbtn" contentid="{{contentid}}">Reject</span>
		<span class="btn btn-flat btn-success pull-right approvebtn marright7" contentid="{{contentid}}">Approve</span>
	</h4>
</div>
{{/each}}
</script>
<script id="pdfcontenttmpl" type="text/x-handlebars-template"> 
{{#each .}}
<div class="col-md-12 borderall1gray pad10">
	<object class="pdfcontent min500  width100" data="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{contentpath}}">
		Unable to view the content
	</object>
</div>
{{/each}}
</script>
<!-- Video Content Template Start -->
<script id="videocontenttmpl" type="text/x-handlebars-template">
{{#each .}}
<div class="col-md-12  borderall1gray pad10">
	<div class="flowplayer min500 pad10 width100" data-swf="<%=request.getContextPath()%>/assets/flowplayer/igplayer.swf" data-ratio="0.5">
		<video data-setup='{"customControlsOnMobile": true}'>
			<source type="video/flv" src="<%=request.getContextPath()%>/PlayFile?r1=serverpath&r2={{contentpath}}">
			</source>
		</video>
	</div>
</div>   
{{/each}}
</script>
<!-- Video Content Template End -->

<!-- SWF Content Template Start -->
<script id="swfcontentmpl" type="text/x-handlebars-template">
<div class="col-md-12 martop10">
{{#each .}}
 <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" 
	codebase="http://download.macromedia.com/pub/shockwave/
	cabs/flash/swflash.cab#version=6,0,40,0" id="mymoviename" class="min500 pad10 width100"> 
		<param name="movie" value="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{contentpath}}" /> 
		<param name="quality" value="high" /> 
		<param name="bgcolor" value="#fff" /> 
		<param name="wmode" value="transparent"> 
		<embed class="swfembed" style="width: 100%; height: 555px;" src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{contentpath}}" 
			wmode="transparent" quality="high" bgcolor="#ffffff" name="mymoviename" align="" type="application/x-shockwave-flash"> 
		</embed> 
</object> 
{{/each}}
</div>
</script>
</head>
<body>
	<div class="alert alert-success updatemessge" style="display: none;">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<strong>Message ! </strong> <font class="updatemsgdesc">The
			Course has been Approved</font>.
	</div>

	<div class="container-fluid titlecontainer">
		<div class="row">
			<div class="col-md-12 lefttext">
				<div class="col-md-10 lefttext">
					<a href="<%=request.getContextPath()%>/app/dashboard">Dashboard
					</a> / <a href="<%=request.getContextPath()%>/app/courses">Courses
					</a>/ Content Approve for Course <font class="crstitle"></font>
				</div>
				<div class="col-md-2 decisiondiv" id="decisiondiv"
					style="display: none; text-align: right;">
					<span class="btn btn-flat btn-success coursebtn" op="Approve"
						status="A" >Approve</span> <span
						class="btn btn-flat btn-danger coursebtn" op="Reject" status="D">Reject</span>
				</div>
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12 whitebg min450 pad0">
				<div id="subjectparts" class="col-md-3 pad5"></div>
				<div class="col-md-9 pad0">
					<div id="contentstatustable"></div>
					<div id="contentviewtable"></div>
				</div>
			</div>
		</div>
	</div>

	<input type="hidden" name="courseid"
		value="<s:property value="courseid"/>">
	<input type="hidden" name="authororgpersonid" value="">

	<div class="modal fade" id="approveModal">
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
					<div class="form-horizontal">
						<div class="alert alert-danger reasonwarning">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<font class="reasonmsg">Please Fill the Reason.</font>
						</div>
						<div class="alert alert-success reasonupdate">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<font class="reasonmsg">Reason Updated successfully.</font>
						</div>
						<div class="form-group">
							<label class="col-sm-3 righttext">Reason : </label>
							<div class="col-sm-9">
								<textarea class="form-control" id="approvedescription"
									name="approvedescription"></textarea>
									<p class="help-block"><font class="rejectreasoncount">500</font> Charcters Remaining</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3"></label>
							<div class="col-sm-5">
								<button id="singlebutton" name="singlebutton"
									class="btn btn-primary">Save</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<form name="contentapproveform" method="post"></form>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/course/contentapproval.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/flowplayer/iGplayer.min.js"></script>	
</body>
</html>