<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ecloud Log File</title>
<link
	href="<%=request.getContextPath()%>/assets/plugins/datepicker/css/bootstrap-datepicker.min.css"
	type="text/css" rel="stylesheet">
</head>
<body>
	<div class="container-fluid ">
		<div class="row titlecontainer">
			<div class="col-md-12">
				<div class="col-md-7 lefttext">
					<a>Ecloud Log file</a>
				</div>
				<div class="col-md-2 righttext">
					<a class="btn btn-success downloadLogFile">Download</a>
				</div>
				<div class="col-md-3">
					<div class="form-group martop3">
						<label class="col-sm-6 righttext control-label">Choose
							Date : </label>
						<div class="col-sm-6">
							<input type="text" readonly class="form-control logdateval">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid ">
		<div class="row titlecontainer">
			<div class="col-md-12 padright0">
				<object class="min500 width100 logobject"
					data="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2=/logs/logging.txt">
					Unable to view the content </object>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/plugins/datepicker/js/bootstrap-datepicker.min.js"></script>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('.logdateval').datepicker({
								format : 'yyyy-mm-dd'
							});

							var fileDownloadAttr = courseData.getContextPath()
									+ "/OpenFile?r1=serverpath&r2=/logs/logging.txt&r3=download&r4=ori";
							$('.downloadLogFile')
									.attr('href', fileDownloadAttr);

							$('.logdateval').change(
									function() {

										var todaydate = new Date()
												.toDateString();
										var changeddate = new Date($(
												'.logdateval').val())
												.toDateString();

										var logFileUrl;
										if (todaydate == changeddate) {
											logFileUrl = "/logs/logging.txt";
										} else {
											logFileUrl = "/logs/logging.txt."
													+ $(this).val();
										}

										var fileAttr = courseData
												.getContextPath()
												+ "/OpenFile?r1=serverpath&r2="
												+ logFileUrl;
										var fileDownloadAttr = courseData
												.getContextPath()
												+ "/OpenFile?r1=serverpath&r2="
												+ logFileUrl
												+ "&r3=download&r4=ori";

										$('.logobject').attr('data', fileAttr);
										$('.downloadLogFile').attr('href',
												fileDownloadAttr);

									});
						});
	</script>
</body>
</html>