<!DOCTYPE html>
<html lang="en">
  <head>
  
  	<jsp:include page = "head.html" />
  	    
  </head>

  <body class="nav-md">  
    <div class="container body">    
      <div class="main_container">
      
     	<jsp:include page = "sidemenubar.html" />      
        <jsp:include page = "topmenu.html" />
        
        <!-- page content -->
        
        <div id="wrapper">
        	
        </div>    
                
		<!-- /page content -->
        
        <jsp:include page = "footer.html" />
        
      </div>
    </div>
	
	
	<!-- scripts -->
		
    <!-- jQuery -->
    <script src="vendors/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="vendors/nprogress/nprogress.js"></script>
    
    <!-- Custom Theme Scripts -->
    <script src="build/js/custom.min.js"></script>
    
    <!-- acervorama script -->
    <script src="js/main.js"></script>
    
	<!-- /scripts -->
		
  </body>
</html>