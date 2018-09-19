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
    </style>
</head>

<body>

<div class="row">
  <div class="col-md-4"><canvas id="canvas"  width="400" height="400"></canvas></div>
  <!-- <div class="col-md-4">
 
  <p>Lesson Report</p>
  <canvas id="chart-area" width="340" height="340" style="width: 240px !important;height:240px!important;"/>
  </div> -->
  
  <div class="col-md-4">
					<div class="panel panel-primary noborder" style="height: 354px;">
						<div class="panel-heading dashheaderbg noborder">
							<h3 class="panel-title">
								<i class="fa fa-book"></i> Lessons report
							</h3>
						</div>
						
								<!-- <canvas id="topcategoriescharttable" class="chartdimension" width="350" height="175" style="width: 350px; height: 175px;"></canvas> -->
								<div class="row">
  <div class="col-md-7 col-md-offset-2">
								<canvas id="chart-area" width="340" height="340" style="width: 240px !important;height:240px!important; "/>
						</div></div>
					</div>
				</div>
  
  
  
  <div class="col-md-4">
  
  <div class="panel panel-primary noborder" style="height: 354px;">
						<div class="panel-heading dashheaderbg noborder">
							<h3 class="panel-title">
								Trainees Attendance / Attendance percentage
							</h3>
						</div>
  <div class="row">
  <div class="col-md-7 col-md-offset-2">
  
  <canvas id="chart-pie-area" width="340" height="340" style="width: 240px !important;height:240px!important; "/>
  </div>
  <div class="col-md-4">
 
  </div>
  </div>
  
  </div></div> 
</div> 

<div class="row">
  <div class="col-md-5">
  <p>Schedule Classes</p>
  <table class="table table-striped"  style="background-color: gainsboro;">
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
			<td>22-05-2016 <br>4.00-5.00pm</td>
			<td>AEH Auditorium</td>
			<td>Anatomy eye</td>
			<td>Kannan</td>
		</tr>
		<tr>
			<td>30-05-2016<br>4.00-5.00pm</td>
			<td>LAICO Conference Hall</td>
			<td>Anatomy eye</td>
			<td>Raju</td>
		</tr>
		<tr>
			<td>10-06-2016<br>4.00-5.00pm</td>
			<td>LAICO Classroom 1</td>
			<td>Anatomy eye</td>
			<td>Mano</td>
		</tr>
		<tr>
			<td>28-05-2016 <br>4.00-5.00pm</td>
			<td>LAICO Classroom 2</td>
			<td>Anatomy eye</td>
			<td>Raju</td>
		</tr>
		<tr>
			<td>26-05-2016<br>4.00-5.00pm</td>
			<td>Auditorium</td>
			<td>Anatomy eye</td>
			<td>Seeni</td>
		</tr>
		
	</tbody>
</table>
  
  </div>
  <div class="col-md-3">
  
  <p>No.of skills to be completed</p>
  <table class="table table-striped" style="background-color: gainsboro;">
	<!-- <tr>No.of skills to be completed</tr> -->
	<thead>
		<tr>
			<th>No.of cases to be completed for each skill</th>
			<th>No.of case to be seen</th>
						
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>Assistent for Gonio</td>
			<td>10 cases</td>
			
		</tr>
		<tr>
			<td>Bed Making</td>
			<td>20 cases</td>
			
		</tr>
		<tr>
			<td>Trolley set-up for cornea surgery</td>
			<td>5 cases</td>
			
		</tr>
		<tr>
			<td>Pre-operative counseling</td>
			<td>3 cases</td>
			
		</tr>
		
	</tbody>
</table>
  
  
  </div>
 <div class="col-md-4">
  
  <p>Skill Certification</p>
  <table class="table table-striped" style="background-color: #D7E7F5;">  
	<thead> 
		<tr>
			<th>Skills Required</th>
			<th>Status</th>
			
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>Patient escorting</td>
			<td>Certified</td>
			
		</tr>
		<tr>
			<td>Visual acuity measurement</td>
			<td>Certified</td>
			
		</tr>
		<tr>
			<td>Instillation of eye drops</td>
			<td>Certified</td>
 		</tr>
		<tr>
			<td>Blood pressure measurement</td>
			<td>Reassessment</td>
			
		</tr>
		<tr>
			<td>Intra-ocular pressure measurement</td>
			<td>Pending</td>
			
		</tr>
		<tr>
			<td>Segregation of patients</td>
			<td>Pending</td>
 		</tr>
	</tbody>
</table>
  
  
  </div> 
</div>
    <script>
       
        var barChartData = {
            labels: ["Lesson1", "Lesson2", "Lesson3", "Lesson4", "Lesson5", "Lesson6", "Lesson7", "Lesson8", "Lesson9", "Lesson10"],
            datasets: [{
                label: "Data",
                data: [30,70,90,20,55,60,70,45,80,30,10],
           		backgroundColor: ['#F7464A', '#5777CA', '#5777CA', '#F7464A', '#5777CA','#5777CA', '#5777CA', '#73C774', '#5777CA', '#F7464A', '#F7464A']
            }]

        };

        window.onload = function() {
            var ctx = document.getElementById("canvas").getContext("2d");
            window.myBar = new Chart(ctx, {
                type: 'bar',
                data: barChartData,
                options: {
                    
                    elements: {
                        rectangle: {
                            borderWidth: 2,
                          /*   borderColor: 'rgb(0, 255, 0)', */
                            borderSkipped: 'bottom'
                        }
                    },
                    responsive: true,
                    legend: {
                        position: 'top',
                    },
                    title: {
                        display: true,
                        text: 'Grades'
                    }
                }
            });
            
            
            var ctx = document.getElementById("chart-area").getContext("2d");
            window.myPie = new Chart(ctx, config);
            
            var ctx = document.getElementById("chart-pie-area").getContext("2d");
            window.myPie = new Chart(ctx, attence);
           
        };

			
//pie chart
	
    var config = {
        type: 'pie',
        data: {
            datasets: [{
                data: [
                    20,30
                ],
                backgroundColor: [
                    "#F7464A",
                    "#46BFBD",
                    
                ],
            }],
            labels: [
				 "Pending",
                "Completed", 
                
                
            ]
        },
        options: {
            responsive: true
        }
    };
    
    
    
    var attence = {
            type: 'pie',
            data: {
                datasets: [{
                    data: [
                        60,40
                    ],
                    backgroundColor: [
                        "#F7464A",
                        "#5777CA",
                        
                    ],
                }],
                labels: [
					 "Absent",
    				 "Present",
                     
                    
                    
                ]
            },
            options: {
                responsive: true
            }
        };
			
	</script>
</body>

</html>