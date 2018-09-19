<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
  .center {
  			text-align: center; 
  			margin-left: auto; 
  			margin-right: auto; 
  			margin-bottom: auto; 
  			margin-top: auto;
  		}
  .hero-unit{
  			padding: 60px;
			margin:40px 25px;
			font-size: 18px;
			font-weight: 200;
			line-height: 30px;
			color: inherit;
			background-color: white;
			-webkit-border-radius: 6px;
			-moz-border-radius: 6px;
			border-radius: 6px;
  	}
</style>
</head>
<body>
	<div class="container">
		<div class="row-fluid">
		<div class="hero-unit center">
          <h1 class="cornflowerblue"><i class="fa fa-clock-o marright5"></i><br>Session Expired</h1>
          <p class="centertext martop20">Your Session has expired due to inactivity.</p> 
          <p class="centertext martop20">Please click on the below button to return to Home Page and Login Again</p>
          <a href="<%=request.getContextPath()%>" class="btn btn-large btn-info martop20"><i class="fa fa-home marright5"></i> Take Me Home</a>
        </div>
		</div>
	</div>
</body>
</html>