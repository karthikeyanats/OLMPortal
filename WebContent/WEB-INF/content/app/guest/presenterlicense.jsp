<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div class="container-fluid whitebg martop30 centertext boxshadow col-md-10 col-md-offset-1">
<div class="paymentpage">
		<form action="" name="coursepayment">
		<span id="context" style="display: none;"><%=request.getContextPath()%></span>
		<s:hidden name="price" value="%{price}"></s:hidden>
		<s:hidden name="presenterappdetailsid" value="%{presenterappdetailsid}"></s:hidden>
		<div class="offlinepayment">
		<h3 class="size20 centertext martop30">Remedi Presenter Pro</h3>
		<h3 style="font-size: 18px; color: #101011;font-weight: normal;margin-top:100px;" priceicon="<s:text name="label.price"></s:text>" class="pricebtn"></h3>
			<h3 class="martop30" style="font-size: 18px; color: rgb(185, 185, 185);">Payment Mode</h3>
			<div class="martopbottom20">
				<a type="button" href="#" data-toggle="tab" class="btn btn-flat btn-blue btn-padded btn-big paymentab">On-line</a>
				<a type="button" href="<%=request.getContextPath()%>/app/downloadpresenter" class="btn btn-flat btn-blue btn-padded btn-big paymentback">Back</a>
			</div>
		</div>
		</form>
	</div>
</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/assets/app/js/guest/presenterlicense.js"></script>
</body>
</html>