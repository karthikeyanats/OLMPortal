<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<html>
<head>
<style type="text/css">
body {
	background-color: white !important;
}

.alert {
	padding: 6px !important;
	margin-bottom: 10px !important;
}
.container-fluid{

  padding-left: 0px;
  padding-right: 0px;
}

</style>

<link href="<%=request.getContextPath()%>/assets/app/css/contact.css"
	type="text/css" rel="stylesheet"> 
	<link href="<%=request.getContextPath()%>/assets/app/css/contact_responsive.css"
	type="text/css" rel="stylesheet"> 

</head>
<body>
	<%-- <div class="container">
		<div class="map">
			<div id="canvas-for-google-map" style="max-width: 100%;">
				<!-- <iframe style="height: 100%; width: 100%; border: 0;"
					frameborder="0"
					src="https://www.google.com/maps/embed/v1/place?q=i-Grandee,+Villapuram,+Madurai,+Tamil+Nadu,+India&key=AIzaSyAN0om9mFmy1QN6Wf54tXAowK4eT0ZUPrU"></iframe> -->
			</div>
		</div>

		<section id="contact"
			class="home-section nopadd-bot color-dark bg-white text-center padtop120">
			<div class="container marginbot-50">
				<div class="row">
					<div class="col-lg-8 col-lg-offset-2">
						<div class="animatedParent">
							<div class="section-headingcontact text-center">
								<h3 class="h-bold animated bounceInDown">CONTACT</h3>
								<div class="divider-header"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-6 contact_left">
				<div class="row marginbot-20">
					<div class="row">
						<div class="section-headingcontact marleft15">
							<h3 class="size20 floatleft">Address</h3>
							<div class="divider-header"></div>
						</div>
					</div>
					<address style="text-align: left;">
						<strong>Head Office</strong><br> No. 12, Veerapadra Pillai
						Compound,<br> Villapuram,Madurai - 625012<br> Tamil
						Nadu, India<br> <b> Email : </b> <a
							href="mailto:contact@igrandee.com" target="_blank"
							style="text-decoration: underline;" class="connectig">contact@igrandee.com</a><br>
						<b>Phone : </b> 0452 2671301<br /> <b> Mob : </b> +91- 9698487079
					</address>
				</div>
				<div class="row marginbot-20">
					<address style="text-align: left;">
						<strong>Chennai Office</strong><br>405, 3rd Floor Prestige Plaza,<br>Mother Dairy Road,<br> New Yelahanka, Bangalore,<br>
						India. PIN - 560 065<br> <b> Email : </b> <a
							href="mailto:contact@aimjoro.com" target="_blank"
							style="text-decoration: underline;" class="connectig">contact@aimjoro.com</a><br>
						<b> Mob : </b> ++080-29720783
					</address>
				</div>
				<div class="row marginbot-20">
					<address style="text-align: left;">
						<strong>Delhi Office</strong><br> #146, Himvarsha Apartment,,<br>103,Mandaveli
						main road,<br> IP - Ext - Patparganj, <br> New Delhi, -
						110092, India<br> <b> Email : </b> <a
							href="mailto:contact@igrandee.com" target="_blank"
							style="text-decoration: underline;" class="connectig">contact@igrandee.com</a><br>
						<b> Mob : </b> +91-9805047416
					</address>
				</div>
			</div>
			<div class="col-md-6 contact_right">
				<form id="contact-form" class="contact-form" name="contact-form">
					<div class="row">
						<div class="">
							<div class="">
								<div class='alert alert-success mailsuccess'
									style='display: none;' id='mailsuccess'></div>
								<div class='alert alert-danger mailfailure'
									style='display: none;' id='mailfailure'></div>
								<div class="section-headingcontact text-center marbottom20">
									<h3 class="size20">Get in touch with us</h3>
									<div class="divider-header"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="row ">
						<div class="col-md-6 ">
							<input type="text"
								class="form-control radiusnone marbottom20 trimvalue" id="name"
								name="name" placeholder="Enter name" required="required" />
							<div class='alert alert-danger' style='display: none;'
								id='nameerror'></div>
						</div>
						<div class="col-md-6">
							<input type="email"
								class="form-control radiusnone marbottom20 usermail trimvalue"
								id="email" name="email" placeholder="Enter email"
								required="required" />
							<div class='alert alert-danger' style='display: none;'
								id='emailerror'></div>
						</div>
					</div>
					<div class="alert alert-danger alreadyuser" role="alert"
						style="display: none;">
						<span class="glyphicon glyphicon-exclamation-sign "
							aria-hidden="true"></span> <span class="sr-only">Error:</span>
						User Name Already Used
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<input type="text" class="form-control radiusnone trimvalue"
									id="organizationname" name="organizationname"
									placeholder="Organization" required="required" />
								<div class='alert alert-danger' style='display: none;'
									id='organizationerror'></div>
							</div>
							<div class="form-group">
								<input type="text" class="form-control radiusnone trimvalue"
									id="subject" name="subject" placeholder="Subject"
									required="required" />
								<div class='alert alert-danger' style='display: none;'
									id='subjecterror'></div>
							</div>
							<div class="form-group">
								<textarea name="address" id="address" name="address"
									class="form-control radiusnone trimvalue" rows="4" cols="25"
									required="required" placeholder="Address"></textarea>
								<div class='alert alert-danger' style='display: none;'
									id='addresserror'></div>
							</div>
							<div class="form-group">
								<textarea name="message" id="message" name="message"
									class="form-control radiusnone trimvalue" rows="4" cols="25"
									required="required" placeholder="Message"></textarea>
								<div class='alert alert-danger' style='display: none;'
									id='messageerror'></div>
							</div>

							<input type="button"
								class="btn btn-success btn-lg btn-block radiusnone"
								value="Send Message" id="btncontactus"> <a
								class="btn sendingmess"> Message is Sending .... <img alt=""
								src="<%=request.getContextPath()%>/assets/app/images/ajax-loader6.gif"></a>
						</div>
					</div>
				</form>
				<div class="modal-backdrop  in" style="display: none;"></div>
			</div>
			<div class="clearfix"></div>
	</div>
	</div> --%>
	<!-- div class="super_container"> -->

	<!-- Header -->

	

	<!-- Menu -->

	<div class="menu d-flex flex-column align-items-end justify-content-start text-right menu_mm trans_400">
		<div class="menu_close_container"><div class="menu_close"><div></div><div></div></div></div>
		<div class="search">
			<form action="#" class="header_search_form menu_mm">
				<input type="search" class="search_input menu_mm" placeholder="Search" required="required">
				<button class="header_search_button d-flex flex-column align-items-center justify-content-center menu_mm">
					<i class="fa fa-search menu_mm" aria-hidden="true"></i>
				</button>
			</form>
		</div>
		<nav class="menu_nav">
			<ul class="menu_mm">
				<li class="menu_mm"><a href="index.html">Home</a></li>
				<li class="menu_mm"><a href="#">About</a></li>
				<li class="menu_mm"><a href="#">Courses</a></li>
				<li class="menu_mm"><a href="#">Blog</a></li>
				<li class="menu_mm"><a href="#">Page</a></li>
				<li class="menu_mm"><a href="contact.html">Contact</a></li>
			</ul>
		</nav>
	</div>
	
	
	<div class="newsletter">
		<div class="newsletter_background" style="background-image:url(./assets/app/images/newsletter_background.jpg)"></div>
		<div class="container">
			<div class="row">
				<div class="col">
					<div class="newsletter_container d-flex flex-lg-row flex-column align-items-center justify-content-start">
                        <div class="newsletter_title" style="font-size: 26px;padding-top: 43px;" align="center">COntact Us</div>
                        <!-- Newsletter Content -->
						<!-- <div class="newsletter_content text-lg-left text-center">
							<div class="newsletter_title">sign up for news and offers</div>
							<div class="newsletter_subtitle">Subcribe to lastest smartphones news & great deals we offer</div>
						</div> -->

						<!-- Newsletter Form -->
						<div class="newsletter_form_container ml-lg-auto">
							<form action="#" id="newsletter_form" class="newsletter_form d-flex flex-row align-items-center justify-content-center">
								<!-- <input type="email" class="newsletter_input" placeholder="Your Email" required="required"> -->
								<!-- <button type="submit" class="newsletter_button">subscribe</button> -->
							</form>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	
	
	
	
	
	
	<!-- Home -->

	<!-- <div class="home">
		<div class="breadcrumbs_container">
			<div class="container">
				<div class="row">
					<div class="col">
						<div class="breadcrumbs">
							<ul>
								<li><a href="index.html">Home</a></li>
								<li>Contact</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>			
	</div> -->

	<!-- Contact -->

	<div class="row" style="margin-top: 80px;">
	  
	  <div class="col-md-4">
	    <div class="contact" style="margin-left: 28px;">
		
		<!-- Contact Map -->

		<div class="contact_map">

			<!-- Google Map -->
			
			<div class="map">
				<div id="google_map" class="google_map">
					<div class="map_container">
						<div id="map"></div>
					</div>
				</div>
			</div>

		</div>
		</div>
	  
	  </div>
	  <div class="col-md-4">
	  
	        <form id="contact-form" class="contact-form" name="contact-form" style="margin-left: 18px;width: 104%;">
					<div class="row">
						<div class="">
							<div class="">
								<div class='alert alert-success mailsuccess'
									style='display: none;' id='mailsuccess'></div>
								<div class='alert alert-danger mailfailure'
									style='display: none;' id='mailfailure'></div>
								<div class="section-headingcontact text-center marbottom20">
									<div class="contact_info_title" style="font-size: 30px;">Contact Form</div><hr>
									<div class="divider-header"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="row ">
						<div class="col-md-6 ">
							<input type="text"
								class="form-control radiusnone marbottom20 trimvalue" id="name"
								name="name" placeholder="Enter name" required="required" />
							<div class='alert alert-danger' style='display: none;'
								id='nameerror'></div>
						</div>
						<div class="col-md-6">
							<input type="email"
								class="form-control radiusnone marbottom20 usermail trimvalue"
								id="email" name="email" placeholder="Enter email"
								required="required" />
							<div class='alert alert-danger' style='display: none;'
								id='emailerror'></div>
						</div>
					</div>
					<div class="alert alert-danger alreadyuser" role="alert"
						style="display: none;">
						<span class="glyphicon glyphicon-exclamation-sign "
							aria-hidden="true"></span> <span class="sr-only">Error:</span>
						User Name Already Used
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<input type="text" class="form-control radiusnone trimvalue"
									id="organizationname" name="organizationname"
									placeholder="Organization" required="required" />
								<div class='alert alert-danger' style='display: none;'
									id='organizationerror'></div>
							</div>
							<div class="form-group">
								<input type="text" class="form-control radiusnone trimvalue"
									id="subject" name="subject" placeholder="Subject"
									required="required" />
								<div class='alert alert-danger' style='display: none;'
									id='subjecterror'></div>
							</div>
							<div class="form-group">
								<textarea name="address" id="address" name="address"
									class="form-control radiusnone trimvalue" rows="4" cols="25"
									required="required" placeholder="Address"></textarea>
								<div class='alert alert-danger' style='display: none;'
									id='addresserror'></div>
							</div>
							<div class="form-group">
								<textarea name="message" id="message" name="message"
									class="form-control radiusnone trimvalue" rows="4" cols="25"
									required="required" placeholder="Message"></textarea>
								<div class='alert alert-danger' style='display: none;'
									id='messageerror'></div>
							</div>

							<input type="button"
								class="btn btn-success btn-lg btn-block radiusnone"
								value="Send Message" id="btncontactus"> <a
								class="btn sendingmess"> Message is Sending .... <img alt=""
								src="<%=request.getContextPath()%>/assets/app/images/ajax-loader6.gif"></a>
						</div>
					</div>
				</form>
	  </div>
	  
	  
	  <div class="col-md-4">
						<div class="contact_info">
							<div class="contact_info_title" style="font-size: 30px;">Contact Info</div><hr>
							<div class="contact_info_text">
								<!-- <p>It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a distribution of letters.</p> -->
							</div>
							<div class="contact_info_location">
								<div class="contact_info_location_title">Head Office</div>
								<ul class="location_list">
									<li>No 79, 2nd Floor, </li>
									<li>Tower Line Cross Street, </li>
									<li>Indhrani Nagar, Mudakku Salai, </li>
									<li>Madurai – 625016,</li>
									<li>Tamil Nadu,</li>
									<li><a href="www.aimjoro.com" target="_blank">www.Aimjoro.com</a></li>
								</ul>
							</div>
							<div class="contact_info_location">
								<div class="contact_info_location_title">Bangalore Office</div>
								<ul class="location_list">
									<li>405, 3rd Floor Prestige Plaza ,</li>
									<li>Mother Dairy Road,</li>
									<li>New Yelahanka, Bangalore,</li>
									<li>India. PIN - 560 065</li>
									<li><a href="www.aimjoro.com" target="_blank">www.Aimjoro.com</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			
	   
	
	</div>
	
	
	<%-- <div class="contact">
		
		<!-- Contact Map -->

		<div class="contact_map">

			<!-- Google Map -->
			
			<div class="map">
				<div id="google_map" class="google_map">
					<div class="map_container">
						<div id="map"></div>
					</div>
				</div>
			</div>

		</div>

		<!-- Contact Info -->

		<div class="contact_info_container">
			<div class="container">
				<div class="row">

					<!-- Contact Form -->
					<div class="col-lg-6">
						
						
						<form id="contact-form" class="contact-form" name="contact-form">
					<div class="row">
						<div class="">
							<div class="">
								<div class='alert alert-success mailsuccess'
									style='display: none;' id='mailsuccess'></div>
								<div class='alert alert-danger mailfailure'
									style='display: none;' id='mailfailure'></div>
								<div class="section-headingcontact text-center marbottom20">
									<div class="contact_info_title">Contact Form</div>
									<div class="divider-header"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="row ">
						<div class="col-md-6 ">
							<input type="text"
								class="form-control radiusnone marbottom20 trimvalue" id="name"
								name="name" placeholder="Enter name" required="required" />
							<div class='alert alert-danger' style='display: none;'
								id='nameerror'></div>
						</div>
						<div class="col-md-6">
							<input type="email"
								class="form-control radiusnone marbottom20 usermail trimvalue"
								id="email" name="email" placeholder="Enter email"
								required="required" />
							<div class='alert alert-danger' style='display: none;'
								id='emailerror'></div>
						</div>
					</div>
					<div class="alert alert-danger alreadyuser" role="alert"
						style="display: none;">
						<span class="glyphicon glyphicon-exclamation-sign "
							aria-hidden="true"></span> <span class="sr-only">Error:</span>
						User Name Already Used
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<input type="text" class="form-control radiusnone trimvalue"
									id="organizationname" name="organizationname"
									placeholder="Organization" required="required" />
								<div class='alert alert-danger' style='display: none;'
									id='organizationerror'></div>
							</div>
							<div class="form-group">
								<input type="text" class="form-control radiusnone trimvalue"
									id="subject" name="subject" placeholder="Subject"
									required="required" />
								<div class='alert alert-danger' style='display: none;'
									id='subjecterror'></div>
							</div>
							<div class="form-group">
								<textarea name="address" id="address" name="address"
									class="form-control radiusnone trimvalue" rows="4" cols="25"
									required="required" placeholder="Address"></textarea>
								<div class='alert alert-danger' style='display: none;'
									id='addresserror'></div>
							</div>
							<div class="form-group">
								<textarea name="message" id="message" name="message"
									class="form-control radiusnone trimvalue" rows="4" cols="25"
									required="required" placeholder="Message"></textarea>
								<div class='alert alert-danger' style='display: none;'
									id='messageerror'></div>
							</div>

							<input type="button"
								class="btn btn-success btn-lg btn-block radiusnone"
								value="Send Message" id="btncontactus"> <a
								class="btn sendingmess"> Message is Sending .... <img alt=""
								src="<%=request.getContextPath()%>/assets/app/images/ajax-loader6.gif"></a>
						</div>
					</div>
				</form>
						
						
						
						
						
						
						
						<!-- <div class="contact_form">
							<div class="contact_info_title">Contact Form</div>
							<form action="#" class="comment_form">
								<div>
									<div class="form_title">Name</div>
									<input type="text" class="comment_input" required="required">
								</div>
								<div>
									<div class="form_title">Email</div>
									<input type="text" class="comment_input" required="required">
								</div>
								<div>
									<div class="form_title">Message</div>
									<textarea class="comment_input comment_textarea" required="required"></textarea>
								</div>
								<div>
									<button type="submit" class="comment_button trans_200">submit now</button>
								</div>
							</form>
						</div> -->
					</div>

					<!-- Contact Info -->
					<div class="col-lg-6">
						<div class="contact_info">
							<div class="contact_info_title">Contact Info</div>
							<div class="contact_info_text">
								<!-- <p>It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a distribution of letters.</p> -->
							</div>
							<div class="contact_info_location">
								<div class="contact_info_location_title">Head Office</div>
								<ul class="location_list">
									<li>No 79, 2nd Floor, </li>
									<li>Tower Line Cross Street, </li>
									<li>Indhrani Nagar, Mudakku Salai, </li>
									<li>Madurai – 625016,</li>
									<li>Tamil Nadu,</li>
									<li><a href="www.aimjoro.com" target="_blank">www.Aimjoro.com</a></li>
								</ul>
							</div>
							<div class="contact_info_location">
								<div class="contact_info_location_title">Bangalore Office</div>
								<ul class="location_list">
									<li>405, 3rd Floor Prestige Plaza ,</li>
									<li>Mother Dairy Road,</li>
									<li>New Yelahanka, Bangalore,</li>
									<li>India. PIN - 560 065</li>
									<li><a href="www.aimjoro.com" target="_blank">www.Aimjoro.com</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div> --%>

	<!-- Newsletter -->

	
	
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/guest/contact.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/formjs/contact.js"></script>	
	<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&key=AIzaSyCIwF204lFZg1y4kPSIhKaHEXMLYxxuMhA"></script>	
</body>
</html>
