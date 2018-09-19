
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Remedi-Cloud</title>
 
 <style type="text/css"> 
 
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/x-handlebars-template" id="facebooktmpl">
<div class="control-group">
    <div class="span2"></div>	
    <div class="span2">Email</div>
    <div class="span1"><span>:</span></div>				
    <div class="span4">{{email}}</div>		
</div>							
<div class="control-group">
  <div class="span2"></div>	
  <div class="span2">First Name</div>	
  <div class="span1"><span>:</span></div>					
  <div class="span4">{{firstname}}</div>		
</div>
<div class="control-group">
  <div class="span2"></div>	
  <div class="span2">Last Name</div>	
  <div class="span1"><span>:</span></div>					
  <div class="span4">{{lastname}}</div>		
</div>
</script>
<script type="text/x-handlebars-template" id="erroralerttempl">
<div class="span3"></div>
<div class="alert alert-danger alert-dismissible span6" role="alert" style="margin-left: 8px;height: 40px;width:inherit;">
<button type="button" class="close" data-dismiss="alert">
	<span aria-hidden="true">×</span>
</button>
<strong>Warning!</strong> {{errormessage}}
</div>
</script>
</head>
<body>

<%
   String name=request.getParameter("name");
   String provider=request.getParameter("provider");
   %>

<input type="hidden" id="userid" value=<%=name %>>
<input type="hidden" id="provider" value=<%=provider %>> 

 	  
	<section>		
	<div class="container">
		<div class="row-fluid pagestart">
		  <div id="erroralert"></div>
			<div class="span12 contentbackgound">			    
				<form class="well span6 offset3 forn form-horizontal" name="facebooksignup" method="post">
					<fieldset>
					<div class="tab-content">
					  <div class="tab-pane active" id="basicinfo">
					  	   <div id="socialnetworkdetails"></div>																			  
					  </div>					 				  
					</div>
				 </fieldset>				
				 <div class="control-group" style="border-top: 1px solid rgb(224, 219, 219);padding-top: 10px;margin-bottom: 5px;">
					<label class="control-label" for=""></label>
						<div class="controls" style="margin-left: 100px;">
							<a class="btn btn-primary signup" style="height: auto;margin-left: -53px;margin-right: 10px;">Signup</a>
							<a class="btn btn-danger cancel" href="<%=request.getContextPath()%>" style="height: auto;">cancel</a>
						</div>
				</div>
					
			 </form>			
			</div>
		</div>
	</div>
	</section> 
 
<script type="text/javascript" src="<%=request.getContextPath()%>/assets/app/js/guest/socialusers.js"></script> 
</body>
</html>
