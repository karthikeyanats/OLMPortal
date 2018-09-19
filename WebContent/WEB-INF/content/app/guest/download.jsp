<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script id="remedipresenterdownloadtmpl"
	type="text/x-handlebars-template">
<h3 class="rightpanetitle1" > 
	<s:text name="label.presenter"></s:text>
</h3>
<div class="container well" style="">
	<form name="presenterappdownload" id="presenterappdownload" method="post">
		<table class="table manageprofilecont">
			<thead>
				<tr>
					<th style="text-align:left">Features</th>
					<th><s:text name="label.presenter"></s:text></th>
					<th><s:text name="label.presenterpro"></s:text></th>
				</tr>
			</thead>
			<tbody>
					<tr>
						<td>&nbsp;Convert PowerPoint Presentations to Flash</td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></td></tr>
					<tr><td>Retain animation effects in PowerPoint files</td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></td></tr>
					<tr><td>Sync pre-recorded
						audio/video files with PowerPoint filess</td>
						<td style="text-align:center"><i class="fa fa-times" style="color:red"></i></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></td></tr>
					<tr><td>Retain linked videos in PowerPoint files</td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></i></td></tr>
					<tr><td>Sync
						Live audio/video with PowerPoint Files</td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></td></tr>
					<tr><td>Requires activation</td>
						<td style="text-align:center"><i class="fa fa-times" style="color:red"></i></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></td></tr>
					<tr><td>Save as SCORM Compliant Flash presentation</td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></td></tr>
					<tr><td>Get	an unbranded version of output player</td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></td></tr>
					<tr><td>Compile	CD/DVD of your Presentations</td>
						<td style="text-align:center"><i class="fa fa-times" style="color:red"></i></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></td></tr>
					<tr><td>Share through authorSTREAM</td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></td></tr>
					<tr><td>Requires activation</td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></td></tr>
					<tr class=""><td></td>
						<td style="text-align:center; cursor: pointer;"><a class="presenterdownloadfreelink">Free </a><i class="fa fa-check fa-2 free" style="color:green;display:none"></i></td>
						<td style="text-align:center; cursor: pointer;"><a class="presenterdownloadlicenselink">License </a><i class="fa fa-check fa-2 license" style="color:green;display:none"></td></tr>
			</tbody>
		</table>
<input type="radio" name="status" id="status" value=""> I agree all your <a class="remedipresentermodal">Terms of Services</a>
{{#each .}}
<a apppath="{{apppath}}" class="presenterapp presenter-down presenterdownload btn btn-danger pull-right" 
	presenterappdetailsid="{{presenterappdetailsid}}" typename={{typename}} price={{price}}>
	Download</a>
{{/each}}
<input type="hidden" id="presenterprice" name="price"/>
<input type="hidden" id="presenterappdetailsid" name="presenterappdetailsid"/>
</form>
</div>
</script>
<script id="newUploadformtmpl" type="text/x-handlebars-template">
 <div class="row">
	<div class="col-md-12 rightpane">
		<h3 class="marbottom15 size20" >Presenter Upload 
            <a data-toggle="modal" class="btn white btn-blue pull-right presentertype" style="display:none;">Add Presenter Type</a> 
        </h3>
		<div class="form-horizontal whitebg pad10 manageprofilecont newupload martop20">			
			<div class="form-group">
				<label class="col-sm-3 control-label righttext" >Application
					Name : </label>
				<div class="col-sm-6">
					<input type="text" mandatory="true" errorid="appname"
						class="form-control presenterinput newappname"
						placeholder="Application name" value="" maxlength="45"> <a
						class=""></a>
					<div class="alert alert-danger downerror martopbottom10 pad10" id="newappname"
						style="display: none"></div>
				</div>				
			</div>

			<div class="form-group" >
				<label class="col-sm-3 control-label righttext" >Path : </label>
				<div class="col-sm-6"> 
					<input type="text" mandatory="true" errorid="path"
						placeholder="path" class="form-control presenterinput newpath"
						value="" maxlength="250" > 
						<p class="help"><b>Note : </b>Please make sure your file name will not have space in them</p>					
						<div class="alert alert-danger downerror martopbottom10 pad10" id="newpath"
							style="display: none;"></div>
				</div>				
 			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label righttext">Help Url : </label>
				<div class="col-sm-6">
					<input type="text" mandatory="false" errorid="helpurl"
						placeholder="helpurl" class="form-control  presenterinput newhelpurl"
						value="" maxlength="250" >
					<div class="alert alert-danger downerror martopbottom10 pad10" id="newhelpurl"
						style="display: none"></div>
				</div>				
			</div>
			<div class="alert alert-danger downerror martopbottom10 pad10" id="newcategoryerror" 
					style="display: none;"></div>
			{{#each .}}
			<div class="form-group newtypenamesd martop20 marbottom10" id="{{typeid}}" typename="{{typename}}" checkststus="{{checkstatus}}">
						<div class="col-md-2 checkvalue martop20">
							<input type="checkbox" class="control-label checkid newcheckstatus" id="{{id}}"
								checkstatus="0"> <label class="checkboxs">{{typename}}</label>
							<div class="alert alert-danger" id="type" style="display: none"></div>
						</div>
						<div class="col-md-3">
							<div class="entry input-group number-spinner">
								<label class="martop10 control-label col-md-4 labellayout righttext">Price 
									</label> 
								<div class="col-md-8 control-label">
									<input
										type="text" class="form-control algin price numvalidate pricetxt1"
										value="0" typename="{{typename}}" maxlength="7">				
								</div>						
							</div>
						</div>
						<div class="col-md-3">
							<div class="entry input-group number-spinner">
								<label class="martop10 col-md-5 labellayout pad0 ">
									Duration</label> 
								<div class="input-group">
      								<span class="input-group-btn">
        								<button class="btn btn-danger chk" data-dir="dwn" type="button">
											<i class="fa fa-minus"></i>
										</button>
      								</span>
      								<input type="text" value="0" maxlength="10" 
										class="form-control answer workduration numvalidate">
									<span class="input-group-btn">
        								<button class="btn btn-danger" data-dir="up" type="button">
											<i class="fa fa-plus"></i>
										</button>
      								</span>
    							</div>				
							</div>
						</div>
						<div class="col-md-3">
							<div class="entry input-group number-spinner">
								<label class="col-md-5 labellayout">Login
									Count</label> 
								<div class="input-group">
      								<span class="input-group-btn">
        								<button class="btn btn-danger" type="button">
											<i class="fa fa-minus"></i>
										</button>
      								</span>
      								<input type="text" value="0" typename="{{typename}}" 
										class="form-control answer logincount numvalidate" maxlength="10">
									<span class="input-group-btn">
        								<button class="btn btn-danger" data-dir="up" type="button">
											<i class="fa fa-plus"></i>
										</button>
      								</span>
    							</div>				
							</div>
						</div>
					</div>
			
			{{/each}}
              <hr>
 			<div class="row centertext">
				<span class="btn white btn-blue newpresentersavebtn btn-save">Save</span>
			</div>
		</div>
	</div>
</div>
 </script>

<script id="presenterdetailstmpl" type="text/x-handlebars-template">
<h3 class="marbottom15 size20">	Upload List <span class="btn white btn-success downloadlink pull-right">Upload <s:text name="label.presenter"></s:text></span>
</h3> 
<table class="table table-striped table-borderd whitebg">
	<thead>
		<tr>
			<th>S.No</th>
			<th>Application Name</th>
			<th>Action</th> 
		</tr>
	</thead>
	<tbody >
	{{#if .}} {{#each .}}
	<tr data-toggle="collapse" data-target="#accordion{{@index}}" class="clickable">
       	<td>{{math @index "+" 1}}</td>
       	<td>{{applicationname}}</td>
       	<td>
			<a class="btn btn-primary"><i class="fa fa-eye"></i></a>
			<a class="btn btn-primary white btn-delete editbtn" presenterstatus="{{presenterstatus}}"
				presenterid="{{presenterid}}"><i class="fa fa-pencil"></i></a>				
		</td>
    </tr>
    <tr>
		<td colspan="3" class="pad0">
            <div id="accordion{{@index}}" class="collapse">
				<div class="form-horizontal pad5">
					<div class="form-group">
    					<label class="col-sm-9 centertext control-label">Details </label>
    					<div class="col-sm-2">
      					</div>
  					</div>  					
					<div class="form-group">
    					<label class="col-sm-4 control-label righttext">Application Path : </label>
    					<div class="col-sm-8 lefttext">
      						<label class="control-label breakword">{{apppath}}</label>
    					</div>
  					</div>
					<div class="form-group">
    					<label class="col-sm-4 control-label righttext">Help URL : </label>
    					<div class="col-sm-8 lefttext">
      						<label class="control-label">{{helpurlpath}}</label>
    					</div>
  					</div>
					{{#each types}}
					<div class="form-group">
    					<label class="col-sm-4 control-label righttext">{{typename}} : </label>
    					<div class="col-sm-8 lefttext pad0">
      						<label class="col-sm-12 control-label">
							<font class="col-sm-4 pad0 lefttext">Login Count : {{logincount}} </font>
							<font class="col-sm-4 lefttext">Working Duration : {{workingduration}}</font></label>
    					</div>
  					</div>	
					<div class="form-group">
    					<label class="col-sm-4 control-label righttext">Price(<i class="fa <s:text name='label.price'></s:text>"></i>) : </label>
    					<div class="col-sm-8 lefttext" id="price">
      						<label class="control-label">{{price}}</label>
    					</div>
  					</div>				
				{{/each}}
				</div>
			</div>
        </td>
    </tr>		
		{{/each}} {{else}}
		<tr>
			<td colspan="3" ><p class="centertext">No Data Found</p></td>
		</tr>
		{{/if}}
	</tbody>
</table>
</script>


<script id="presenterdownloaddetailstmpl"
	type="text/x-handlebars-template">
<div id="presendownload" >
<h3 class="marbottom15 size20">Download Details</h3>
<table class="table table-striped table-borderd whitebg  paginationtab">
	<thead>
		<tr>
            <th>S.No</th>
			<th>Name</th>
			<th>Ip Address</th>
            <th>Date Of Download</th>
 			<th>Organization Name</th>	
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
			<td>{{math @index "+" 1}}</td>
            <td>{{name}}</td>  
            <td>{{ipaddress}}</td>
            <td>{{dateofdownload}}</td>
 		<td>{{orgname}}</td>
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="4"><p class="center categorydescription">No download List</p></td>
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

<script id="editpresenterapptmpl" type="text/x-handlebars-template">
<div class="col-md-12 editpresenter pad0">
	<h2 class="marbottom15 size20">Presenter Upload</h2>
	<div class="form-horizontal whitebg pad10">
		<div class="form-group">
			<label class="control-label col-sm-3 righttext">Application
				Name : </label>
			<div class="col-sm-5">
				<input type="text" mandatory="true" errorid="appname"
					class="form-control presenterinput appname"
					presenterid="{{presenterid}}" placeholder="application name"
					value="{{applicationname}}" maxlength="45" >				
				<div class="alert alert-danger downerror martopbottom10 pad10" id="editappname"
					style="display: none;"></div>			
			</div>			
		</div>
		<div class="form-group">
			<label class="control-label col-sm-3 righttext">Path : </label>
			<div class="col-sm-5">
				<input type="text" mandatory="true" errorid="path"
					placeholder="path" class="form-control presenterinput path"
					value="{{apppath}}" maxlength="250">
				<p class="help"><b>Note : </b>Please make sure your file name will not have space in them</p>					
				<div class="alert alert-danger downerror martopbottom10 pad10" id="editpath"
					style="display: none;"></div>			
			</div>			
		</div>
		<div class="form-group">
			<label class="control-label col-sm-3 righttext">Help
					Url</label>
			<div class="col-sm-5">
				<input type="text" mandatory="false" errorid="helpurl"
					placeholder="helpurl" class="form-control  presenterinput helpurl"
					value="{{helpurlpath}}" maxlength="250">
				<div class="alert alert-danger downerror martopbottom10 pad10" id="edithelpurl"
				style="display: none;"></div>
			</div>				
		</div>
			<div class="alert alert-danger downerror martopbottom10 pad10" id="editcategoryerror" 
					style="display: none;"></div>
			{{#each types}}
			<div class="row typenamesd martop20 marbottom10" id="{{typeid}}" typename="{{typename}}" checkststus="{{checkstatus}}">
						<div class="col-md-2 chk">
							<input type="checkbox" class="checkid checkstatus" id="{{id}}"
								checkststus="{{checkstatus}}"> <label class="checkboxs">{{typename}}</label>
							<div class="alert alert-danger" id="type" style="display: none"></div>
						</div>
						<div class="col-md-3">
							<div class="entry input-group number-spinner">
								<label class="col-md-4 labellayout righttext">Price 
									</label> 
								<div class="col-md-8">
									<input
										type="text" class="form-control algin price numvalidate pricetxt1 " 
										value="{{price}}" typename="{{typename}}" maxlength="7">				
								</div>						
							</div>
						</div>
						<div class="col-md-3">
							<div class="entry input-group number-spinner">
								<label class="col-md-5 labellayout righttext">
									Duration</label> 
								<div class="input-group">
      								<span class="input-group-btn">
        								<button class="btn btn-danger chk" data-dir="dwn" type="button">
											<i class="fa fa-minus"></i>
										</button>
      								</span>
      								<input type="text" value="{{workingduration}}" typename="{{typename}}" 
										class="form-control workduration numvalidate" placeholder="" maxlength="10">
									<span class="input-group-btn">
        								<button class="btn btn-danger" data-dir="up" type="button">
											<i class="fa fa-plus"></i>
										</button>
      								</span>
    							</div>				
							</div>
						</div>
						<div class="col-md-3">
							<div class="entry input-group number-spinner">
								<label class="col-md-5 labellayout">Login
									Count</label> 
								<div class="input-group">
      								<span class="input-group-btn">
        								<button class="btn btn-danger" type="button">
											<i class="fa fa-minus"></i>
										</button>
      								</span>
      								<input type="text" value="{{logincount}}" maxlength="10" typename="{{typename}}" class="form-control logincount numvalidate" placeholder="">
									<span class="input-group-btn">
        								<button class="btn btn-danger" data-dir="up" type="button">
											<i class="fa fa-plus"></i>
										</button>
      								</span>
    							</div>				
							</div>
						</div>
					</div>
				
			{{/each}}<hr>
			<div class="row">
				<div class="col-md-12 centertext">
					<a class="btn white btn-blue updatepresenterbtn pricenew" presenterid="{{presenterid}}">Update</a>
				</div>
			</div>
		</div>	
</div>
</script>

<script id="editPresentertmpl" type="text/x-handlebars-template">
<table class="table table-striped whitebg">
	<thead>
		<tr>
            <th>Sl. No</th>
			<th>Presenter Type Name</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
       {{#if .}}
		{{#each .}}
			<tr>
                <td>{{math @index "+" 1}}</td>
				<td>{{typename}}</td>
				<td>
					<a class="btn btn-default editpresentertype" typename="{{typename}}" id="{{id}}">
						<i class="fa fa-pencil"></i>
					</a>
					<a class="btn btn-default deletepresentertype" typename="{{typename}}" id="{{id}}">
						<i class="fa fa-trash"></i>
					</a>
				</td>
			</tr>
		{{/each}}
	    {{else}}
          <tr>
            <td colspan='3' style="color:red;text-align:center;"> Record not Found</td>
          </tr>
      {{/if}}
	</tbody>
</table>
</script>

<script id="trashPresentertmpl" type="text/x-handlebars-template">
<table class="table table-striped whitebg">
	<thead>
		<tr>
            <th>Sl. No</th>
			<th>Presenter Type Name</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
      {{#if .}}
		{{#each .}}
			<tr>
                <td>{{math @index "+" 1}}</td>
				<td>{{typename}}</td>
				<td>
					<a class="btn btn-default restorepresentertype" typename="{{typename}}" id="{{id}}">
						<i class="fa fa-repeat"></i>
					</a>
                    <a class="btn btn-default deletetrashtype" typename="{{typename}}" id="{{id}}">
						<i class="fa fa-remove"></i>
					</a>
				</td>
			</tr>
		{{/each}}
        {{else}}
          <tr>
           <td colspan='3' style="color:red;text-align:center;"> Record not Found</td>
          </tr>
      {{/if}}
	</tbody>
</table>
</script>

<script id="errormessagetmpl" type="text/x-handlebars-template">
<span>{{message}}<span>
</script>
<script id="successmessagetmpl" type="text/x-handlebars-template">
<span>{{message}}<span>
</script>
<script id="downloadlisttmpl" type="text/x-handlebars-template">
<h3 class="marbottom15 size20">
	<s:text name="label.presenter"></s:text>
</h3>
<div> 
	<form id="presenterappdownload" method="post">
		<table class="table table-striped table-borderd whitebg  manageprofilecont">
			<thead>
				<tr>
					<th style="text-align:left">Features</th>
					<th><s:text name="label.presenter"></s:text></th>
					<th><s:text name="label.presenterpro"></s:text></th>
				</tr>
			</thead>
			<tbody>
					<tr>
						<td>&nbsp;Convert PowerPoint Presentations to Flash</td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></td></tr>
					<tr><td>Retain animation effects in PowerPoint files</td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></td></tr>
					<tr><td>Sync pre-recorded
						audio/video files with PowerPoint filess</td>
						<td style="text-align:center"><i class="fa fa-times" style="color:red"></i></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></td></tr>
					<tr><td>Retain linked videos in PowerPoint files</td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></i></td></tr>
					<tr><td>Sync
						Live audio/video with PowerPoint Files</td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></td></tr>
					<tr><td>Requires activation</td>
						<td style="text-align:center"><i class="fa fa-times" style="color:red"></i></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></td></tr>
					<tr><td>Save as SCORM Compliant Flash presentation</td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></td></tr>
					<tr><td>Get	an unbranded version of output player</td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></td></tr>
					<tr><td>Compile	CD/DVD of your Presentations</td>
						<td style="text-align:center"><i class="fa fa-times" style="color:red"></i></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></td></tr>
					<tr><td>Share through authorSTREAM</td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></td></tr>
					<tr><td>Requires activation</td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></i></td>
						<td style="text-align:center"><i class="fa fa-check fa-2" style="color:green"></td></tr>
					<tr class=""><td></td>
						<td style="text-align:center"><a class="presenterdownloadfreelink">Free </a><i class="fa fa-check fa-2 free" style="color:green;display:none"></i></td>
						<td style="text-align:center"><a class="presenterdownloadlicenselink">License </a><i class="fa fa-check fa-2 license" style="color:green;display:none"></i></td></tr>
			</tbody>
		</table>
<input type="radio" name="status" id="status" value=""> I agree all your <a class="remedipresentermodal">Terms of Services</a>
{{#each .}}
<a apppath="{{apppath}}" class="presenterapp presenter-down presenterdownload btn btn-danger pull-right" 
	presenterappdetailsid="{{presenterappdetailsid}}" typename="{{typename}}" price={{price}}>
	Download</a>
{{/each}}
<input type="hidden" id="presenterprice" name="price"/>
<input type="hidden" id="presenterappdetailsid" name="presenterappdetailsid"/>
</form>
</div>
</script>
<script id="facebookalerttmpl" type="text/x-handlebars-template">
<div class="alert alert-success alerter" style="display: none; margin-left: 64px; width:82%">
<a href="#" class="close alertclose" data-dismiss="alert">
   &times;
</a>
<strong id="sharestatus" style="text-align:center;">{{message}}</strong> 
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
		String roleName = userDetailsAdaptor.getUser().getProperty(
				"rolename");
		String orgstatus = userDetailsAdaptor.getUser().getProperty(
				"orgstatus");
	%>
	<div class="span9 downloadhistory">
		<div id="facebookalert"></div>
	</div>

	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12 pad0">
				<div class="col-md-3 pad0 martop10">
					<ul class="pad0">
						<li class="leftsideliheader">
							<h3>Reamedi Presenter</h3>
						</li>
						<%
							if (roleName.equals("admin") && orgstatus.equals("M")) {
						%>
						<li class="leftsideli border hover downloadhistorylink  active">Download
							History</li>
						<li class="leftsideli border hover downloadviewlink  ">Presenter
							View <span id="nothidden presenterapp" style="display: none;">null</span>
						</li>
						<%
							}
						%>
						<li class="leftsideli hover downloadlinks">Download</li>
					</ul>
				</div>
				<div>
					<div class="col-md-9" id="downloaddetails"></div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="usermessage">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<font><b>Message</b></font>
				</div>
				<div class="modal-body">
					<p class="description succesmsg" style="padding: 10px;"></p>
				</div>
				<div class="modal-footer">
					<a class="btn btn-flat btn-blue closed" data-dismiss="modal">Close</a>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="presentermodal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header pad10">

					<h3 class="martopbottom10 size20">
						New Presenter type
						<button type="button" class="close" data-dismiss="modal">x</button>
					</h3>
				</div>
				<div class="modal-body">
					<div class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-4 control-label righttext">Presenter
								Type : </label>
							<div class="col-sm-7">
								<input type="text" class="presenterdata form-control"
									placeholder="Presenter Type" maxlength="45"> <a
									class="white btn btn-blue presentersave martop10"
									id="presentersave">Save</a>
							</div>
						</div>
						<div class="alert alert-danger" hidden="true" id="alertBoxs">
							<strong>Message!</strong>
							<command id="userNamemsg"></command>
						</div>
						<div class="alert alert-success" hidden="true"
							id="alertBoxsuccess">
							<strong>Message!</strong>
							<command id="userNamemsgsuccess"></command>
						</div>
						<ul id="myTab" class="nav nav-tabs">
							<li class="borderbottoma active"><a href="#"
								class="presenterlist" data-toggle="tab"> Presenter Type List
							</a></li>
							<li class="borderbottoma"><a class="trashpresenterlist"
								href="#" data-toggle="tab">Trash Type List</a></li>
						</ul>
					</div>
					<div id="existingtypetable"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="downloadtermsModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<form name="coursesignupform" method="post" action="newSignUp">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">X</button>
						<h3>Terms of Services</h3>
					</div>
					<div class="modal-body">
						<div class="row-fluid">
							<h4>Services</h4>
							<p style="font-weight: 400;">The Company may at its sole
								discretion modify the features of the Services from time to time
								without prior notice. The Company will provide the Services in
								accordance with this Agreement. The Company may at its sole
								discretion modify the features of the Services from time to time
								without prior notice.The Company will provide the Services in
								accordance with this Agreement.</p>
						</div>

						<div class="row-fluid">
							<h4>Sensitive Data</h4>

							<p style="font-weight: 400;">The Company will not share,
								disclose, sell, lease, modify, delete or distribute any Data
								provided by You in any manner. The Company will also not view
								the Data provided by You except when given permission by You in
								writing for the purpose of support. The entire sensitive data
								clause herein shall survive termination of this agreement
								indefinitely.</p>

						</div>

						<div class="row-fluid">
							<p style="font-weight: 400;">No reselling or use outside of
								permitted terms</p>
						</div>

						<div class="row-fluid">
							<p style="font-weight: 400;">Other than using the Services as
								permitted under the terms and conditions of this Agreement or
								other written agreements between You and the Company, You may
								not resell, distribute, make any commercial use of, or use on a
								time-share or service bureau basis.</p>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<input type="hidden" name="roleval" value="<%=roleName%>">
	<input type="hidden" name="orgstatusval" value="<%=orgstatus%>">
	<s:hidden name="socialmsg" value="%{socialmsg}"></s:hidden>
	<input type="hidden" name="contextpath" class="contextpath"
		value="<%=request.getContextPath()%>">
	<script
		src="<%=request.getContextPath()%>/assets/app/js/guest/download.js"
		type="text/javascript"></script>
</body>
</html>