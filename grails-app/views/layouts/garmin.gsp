<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"><!--<![endif]-->
<head>
  <meta charset="utf-8"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
  <title>Garmin File Splitter</title>
  <meta name="description" content="Preview all of Adobe's Edge Fonts in a much easier and more pleasing way."/>
  <!-- <meta name="viewport" content="width=device-width"/> -->
  <script src="${resource(dir: 'js/libs', file: 'modernizr.min.js')}"></script>
  <script src="http://use.edgefonts.net/droid-sans.js"></script>
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.min.css')}"/>
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap-responsive.min.css')}"/>
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'font-awesome.min.css')}"/>
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'index.css')}"/>
  <link type="text/css" rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,400,600,700,300|Bree+Serif">
</head>

<body>

<!--[if lt IE 7]>
		<p class="chromeframe">You are using an outdated browser. <a href="http://browsehappy.com/">Upgrade your browser today</a> or <a href="http://www.google.com/chromeframe/?redirect=true">install Google Chrome Frame</a> to better experience this site.</p>
	<![endif]-->


<g:layoutBody/>

<!-- build:js scripts/amd-app.js -->
<script data-main="js/config" src="${resource(dir: 'js/libs', file: 'require.js')}"></script>
<!-- endbuild -->

<script type="text/javascript">
  window.bootstrap = []
</script>


<script>
  // GA
  var _gaq = [
    ['_setAccount', 'UA-21477675-1'],
    ['_trackPageview']
  ];
  (function (d, t) {
    var g = d.createElement(t), s = d.getElementsByTagName(t)[0];
    g.src = ('https:' == location.protocol ? '//ssl' : '//www') + '.google-analytics.com/ga.js';
    s.parentNode.insertBefore(g, s)
  }(document, 'script'));

  // G+
  (function () {
    var po = document.createElement('script');
    po.type = 'text/javascript';
    po.async = true;
    po.src = 'https://apis.google.com/js/plusone.js';
    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(po, s);
  })();

  // Twitter
  !function (d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if(!d.getElementById(id)) {
      js = d.createElement(s);
      js.id = id;
      js.src = "//platform.twitter.com/widgets.js";
      fjs.parentNode.insertBefore(js, fjs);
    }
  }(document, "script", "twitter-wjs");
</script>

</body>
</html>
