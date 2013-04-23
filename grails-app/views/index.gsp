<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="garmin"/>
  <title>Garmin File Parser</title>
</head>

<body>
<header>
  <!-- Navbar
================================================== -->
  <div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
      <div class="container">
        <!-- logo -->
        <a href="#" class="brand logo">
          Garmin File Manager
        </a>
        <!-- end logo -->
        <!-- top menu -->
        <div>
          <nav>
            <ul class="nav topnav">
              <li class="dropdown">
                <a href="#"><i class="icon-home icon-white"></i> Home</a>
              </li>
              <li class="dropdown">
                <a href="#user/files"><i class="icon-upload icon-white"></i> Files</a>
              </li>
              %{--<li class="inverse">--}%
                %{--<a href="#contact"><i class="icon-envelope icon-white"></i> Contact</a>--}%
              %{--</li>--}%
            </ul>
          </nav>
        </div>
        <!-- end menu -->
      </div>
    </div>
  </div>
</header>

<div class="main-content">
  <div id="before"></div>
  <div id="content"></div>
</div>
<div class="row">
  <div id="after"></div>
</div>

<div id="downloadingFileModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <h3>Preparing File</h3>
  </div>
  <div class="modal-body">
    <h5><i class="icon-spinner icon-spin"></i>&nbsp;Preparing file for download...</h5>
    <p>Please be patient as large files may take a while to prepare.</p>
  </div>
</div>
</body>
</html>
