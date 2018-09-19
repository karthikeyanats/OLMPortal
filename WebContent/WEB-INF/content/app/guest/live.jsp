
<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@page language="java" import="java.util.ResourceBundle"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/converse/css/converse.min.css">
<title>Insert title here</title>

<style type="text/css">
#conversejs .chat-head #controlbox-tabs  .s {
display:none;
}
#conversejs {
margin-bottom: 470px;
margin-right:602px;
}
#conversejs .box-flyout{

position:relative;
}
#converse-register{

display:none;
}
.chat
{
    list-style: none;
    margin: 0;
    padding: 0;
}

.chat li
{
    margin-bottom: 10px;
    padding-bottom: 5px;
    border-bottom: 1px dotted #B3A9A9;
}

.chat li.left .chat-body
{
    margin-left: 60px;
}

.chat li.right .chat-body
{
    margin-right: 60px;
}


.chat li .chat-body p
{
    margin: 0;
    color: #777777;
}

.panel .slidedown .glyphicon, .chat .glyphicon
{
    margin-right: 5px;
}

.panel-body
{
    overflow-y: scroll;
    height: 250px;
}
/* 
::-webkit-scrollbar-track
{
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
    background-color: #F5F5F5;
}

::-webkit-scrollbar
{
    width: 12px;
    background-color: #F5F5F5;
}

::-webkit-scrollbar-thumb
{
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
    background-color: #555;
} */
.footer{

margin-top:337px !important;

}
.chattop{
background-color: rgb(209, 236, 233);
height: 50px;
/* line-height: 20px; */
padding-top: 11px;
padding-left: 12px;
padding-right: 7px;
margin-top: -48px;
}
.thumbsize
{
width:50px;}
#conversejs .chatbox .box-flyout {
width:267px;
}
</style>
</head>
<body>

<%@ page
		import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
	<%
		UserDetailsAdaptor userDetailsAdaptor = null;
		userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String orgpersonid = userDetailsAdaptor.getUser().getProperty(
				"orgpersonid");
		String courseenrollmentid = request
				.getParameter("courseenrollmentid");
		String livesessionid = request
				.getParameter("livesessionid");
	%>
	<span style="display: none;" class="hiddenclss"
		orgpersonid="<%=orgpersonid%>"></span>



<span style="display: none;" class="hiddenclss"
		livesessionid="<%=livesessionid%>"></span>
<s:hidden name="livesessionid" value="%{livesessionid}"></s:hidden>



 <div class="container"> 
 
  <div class="row-fluid" style="margin-top:20px;">
    <div class="span12">
     <div class="span3 thumb">
           
            <a class="thumbnail" href="#" data-image-id="" data-toggle="modal" data-title="This is my title" data-caption="Some lovely red flowers" data-image="http://onelive.us/wp-content/uploads/2014/08/flower-delivery-online.jpg" data-target="#image-gallery">
                <img class="img-responsive" src="/ecloudbaseweb/assets/app/images/ch3.jpg" alt="Short alt text">
            </a>
             <input type="text" name="courseid">
        </div> 
        </div>
        </div>    
 </div>


<s:hidden name="livesessionid" value="%{livesessionid}"></s:hidden>
 </body>
</html>