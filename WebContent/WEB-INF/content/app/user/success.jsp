<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0">
<script type="text/javascript">
	window.onload = function() {
		$('.mycoursesbtn').click(function() {
			$('[name=invoiceemailform]').attr('action', 'mycourses');
			$('[name=invoiceemailform]').submit();
		});
	};
</script>
</head>
<body>
	<div class="paymentpage">
		<form name="invoiceform" method="post" action="mycourses">
			<div class="col-md-12 centertext whitebg min450 martop30 boxshadow">
				<h3 style="margin-top: 150px;">
					<p class="centertext">You have been Successfully enrolled</p>
				</h3>
				<input type="submit" value="Go to My Courses"
					class="btn btn-primary mycoursesbtn">
			</div>
		</form>
	</div>
</body>
</html>