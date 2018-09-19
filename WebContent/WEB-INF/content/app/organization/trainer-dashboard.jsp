<!doctype html>
<html>

<head>
    <title>Bar Chart</title>
    <!-- <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script> -->
    <script src="<%=request.getContextPath()%>/assets/chart/js/Chart.bundle.js"></script>
    <style>
    canvas {
        -moz-user-select: none;
        -webkit-user-select: none;
        -ms-user-select: none;
    }
    
    table {
    background-color: rgba(109, 103, 103, 0.33)!important;
}
.fixed{
	width: 140px!important;
}
.fixed img{
height: 24px;
}
    </style>
</head>

<body>


<div class="row">
<div class="container">
  <div class="col-md-12 ">
  <span >Scheduled Classes</span>
  <div class="pull-right"><span  style="padding: 0 0.5pc; background-color: rgba(109, 103, 103, 0.33)!important;"></span><span> Knowledge</span><span  style="padding: 0 0.5pc; background-color: white;"></span><span> Skills</span></div>	
  <table class="table table-striped">
	<thead>
		<tr>
			<th>Date time</th>
			<th>Venu</th>
			<th>Topic lession</th>
			<th>Tutor</th>			
		</tr>
	</thead> 
	<tbody>
		<tr>
			<td>22-05-2016 4.00-5.00pm</td>
			<td>AEH Auditorium</td>
			<td>Anatomy eye</td>
			<td>Kannan</td>
		</tr>
		<tr>
			<td>30-05-2016 4.00-5.00pm</td>
			<td>LAICO Conference Hall</td>
			<td>Anatomy eye</td>
			<td>Raju</td>
		</tr>
		<tr>
			<td>10-06-2016 4.00-5.00pm</td>
			<td>LAICO Classroom 1</td>
			<td>Anatomy eye</td>
			<td>Mano</td>
		</tr>
		<tr>
			<td>28-05-2016 4.00-5.00pm</td>
			<td>LAICO Classroom 2</td>
			<td>Anatomy eye</td>
			<td>Raju</td>
		</tr>
		<tr>
			<td>26-05-2016 4.00-5.00pm</td>
			<td>Auditorium</td>
			<td>Anatomy eye</td>
			<td>Seeni</td>
		</tr>
		
	</tbody>
</table>
  
  
  </div>
  </div>
  
</div>


<div class="row">
<div class="container">
 <div class="col-md-7">
  
  <p></p>
  <table class="table table-striped">
	<tr>Lesson Feedback</tr>
	<thead>
		<tr>
			<th>Lesson</th>
			<th>FeedBack(Rating)</th>
						
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>Instilling of Eye Drops</td>
			<td><div class="fixed"></div></td>
			
		</tr>
		<tr>
			<td>Myopia</td>
			<td><div class="fixed"></div></td>
			
		</tr>
		<tr>
			<td>Hand Hygiene</td>
			<td><div class="fixed"></div></td>
			
		</tr>
		<tr>
			<td>Visual Fields</td>
			<td><div class="fixed"></div></td>
			
		</tr>
		<tr>
			<td>Instilling of Eye Drops</td>
			<td><div class="fixed"></div></td>
			
		</tr>
		<tr>
			<td>Myopia</td>
			<td><div class="fixed"></div></td>
			
		</tr>
		<tr>
			<td>Instilling of Eye Drops</td>
			<td><div class="fixed"></div></td>
			
		</tr>
		
	</tbody>
</table>
  
  
  </div>
  
  				<div class="col-md-5 ">
  				
					<div class="panel panel-primary noborder" style="margin-top: 30px;">
						<div class="panel-heading dashheaderbg noborder">
							<h3 class="panel-title">
								<i class="fa fa-book"></i> Classes
							</h3>
						</div>
						
								<!-- <canvas id="topcategoriescharttable" class="chartdimension" width="350" height="175" style="width: 350px; height: 175px;"></canvas> -->
								<div class="row">
  						<div class="col-md-8">
								<canvas id="chart-area" width="340" height="340" style="width: 240px !important;height:240px!important; margin-left: 78px;"/>
						</div></div>
					</div>
				</div>
  
  
  
 
  </div>
</div>

    <script>
       
        window.onload = function() {
          
            
            
            var ctx = document.getElementById("chart-area").getContext("2d");
            window.myPie = new Chart(ctx, config);
            
           /*  var ctx = document.getElementById("chart-pie-area").getContext("2d");
            window.myPie = new Chart(ctx, attence); */
            
            $('.fixed').raty({
            	  	readOnly:  true,
            	  	
            	  	starHalf      : courseData.getContextPath()+"/"+'assets/app/images/star/star-half.png',
	          		starOff       : courseData.getContextPath()+"/"+'assets/app/images/star/star-off.png', 
	          		starOn        : courseData.getContextPath()+"/"+'assets/app/images/star/star-on.png',
	          		score: 3
            	});

            	
           
        };

			
//pie chart
	
    var config = {
        type: 'pie',
        data: {
            datasets: [{
                data: [
                    20,40,10
                ],
                backgroundColor: [
                    "#F7464A",
                    "#46BFBD",
                    "#001FBD",
                    
                ],
            }],
            labels: [
				 "Pending",
                "Completed",
                "Postponded",
                
                
            ]
        },
        options: {
            responsive: true
        }
    };
    
    
    
  /*   var attence = {
            type: 'pie',
            data: {
                datasets: [{
                    data: [
                        60,40,40,55,35
                    ],
                    backgroundColor: [
                        "#9777CA",
                        "#5777CA",
                        "#6677CA",
                        "#7577CA",
                        "#4577CA",
                        
                    ],
                }],
                labels: [
					 "Batch1",
					 "Batch2",
					 "Batch3",
					 "Batch4",
					 "Batch5",
					 
                     
                    
                    
                ]
            },
            options: {
                responsive: true
            }
        }; */
			
	</script>
</body>

</html>