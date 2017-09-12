<!DOCTYPE html>
<html lang="en">
  <head>
  
  	<jsp:include page = "head.html" />
  	    
  </head>

  <body class="nav-md">  
    <div class="container body">    
      <div class="main_container">
      
     	<jsp:include page = "sidemenu.html" />      
        <jsp:include page = "topmenu.html" />

        <!-- page content -->
        
        <div class="right_col" role="main">
          <div class="">
            <div class="page-title">
              <div class="title_left">
                <h3>Media Item Management</h3>
              </div>

              <div class="title_right">
                <div class="col-md-8 col-sm-8 col-xs-12 form-group pull-right top_search">
                  <div class="input-group">
                    <input type="text" class="form-control" placeholder="Search for...">
                    <span class="input-group-btn">
                      <button class="btn btn-default" type="button">Go!</button>
                    </span>
                  </div>
                </div>
              </div>
            </div>
            <div class="clearfix"></div>
         
            <div class="row">
              <div class="col-md-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>Media</h2>                    
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />
                    <form class="form-horizontal form-label-left">

                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-3 col-xs-12">Id</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" class="form-control" placeholder="" readonly="readonly">
                        </div>                        
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2 col-xs-12">Registration Date</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" class="form-control" placeholder="" readonly="readonly">
                        </div>                        
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2 col-xs-12">File name</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" class="form-control" placeholder="" readonly="readonly">
                        </div>                        
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2 col-xs-12">File Path <span class="required">*</span></label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" class="form-control" placeholder="File path">
                        </div>                        
                      </div>                      
                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2 col-xs-12">Description</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" class="form-control" placeholder="Description">
                        </div>                        
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2 col-xs-12">Classification</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" class="form-control" placeholder="Classification">
                        </div>                        
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2 col-xs-12">Subject</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" class="form-control" placeholder="Subject">
                        </div>                        
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-2 col-sm-2 col-xs-12">Additional Information
                        </label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <textarea class="form-control" rows="3" placeholder='Additional information'></textarea>
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="col-md-2 col-sm-2 col-xs-12 control-label">Media Type</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">                          
                          <div class="radio">
                            <label>
                              <input type="radio" value="AUDIO" id="mediaTypeRadio1" name="mediaTypeRadio"> Audio
                            </label>                            
                          </div>
                          <div class="radio">
                            <label>
                              <input type="radio" value="IMAGE" id="mediaTypeRadio2" name="mediaTypeRadio"> Image
                            </label>
                          </div>
                          <div class="radio">
                            <label>
                              <input type="radio" value="TEXT" id="mediaTypeRadio3" name="mediaTypeRadio"> Text
                            </label>
                          </div>
                          <div class="radio">
                            <label>
                              <input type="radio" value="VIDEO" id="mediaTypeRadio4" name="mediaTypeRadio"> Video
                            </label>
                          </div>
                        </div>
                      </div>
                      

                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
                          <button type="button" class="btn btn-primary">Cancel</button>
                          <button type="reset" class="btn btn-primary">Reset</button>
                          <button type="submit" class="btn btn-success">Submit</button>
                        </div>
                      </div>

                    </form>
                  </div>
                </div>
              </div>


            </div>
          

          </div>
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
    <!-- bootstrap-progressbar -->
    <script src="vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
    <!-- iCheck -->
    <script src="vendors/iCheck/icheck.min.js"></script>
    <!-- bootstrap-daterangepicker -->
    <script src="vendors/moment/min/moment.min.js"></script>
    <script src="vendors/bootstrap-daterangepicker/daterangepicker.js"></script>
    <!-- bootstrap-wysiwyg -->
    <script src="vendors/bootstrap-wysiwyg/js/bootstrap-wysiwyg.min.js"></script>
    <script src="vendors/jquery.hotkeys/jquery.hotkeys.js"></script>
    <script src="vendors/google-code-prettify/src/prettify.js"></script>
    <!-- jQuery Tags Input -->
    <script src="vendors/jquery.tagsinput/src/jquery.tagsinput.js"></script>
    <!-- Switchery -->
    <script src="vendors/switchery/dist/switchery.min.js"></script>
    <!-- Select2 -->
    <script src="vendors/select2/dist/js/select2.full.min.js"></script>
    <!-- Parsley -->
    <script src="vendors/parsleyjs/dist/parsley.min.js"></script>
    <!-- Autosize -->
    <script src="vendors/autosize/dist/autosize.min.js"></script>
    <!-- jQuery autocomplete -->
    <script src="vendors/devbridge-autocomplete/dist/jquery.autocomplete.min.js"></script>
    <!-- starrr -->
    <script src="vendors/starrr/dist/starrr.js"></script>
    <!-- Custom Theme Scripts -->
    <!-- <script src="build/js/custom.min.js"></script> -->
    
    <!-- /scripts -->
		
  </body>
</html>