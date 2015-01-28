<!DOCTYPE html>
<html>
<head>
  <title>IHTSDO's Refset Service API Documentation</title>
  <link href='//fonts.googleapis.com/css?family=Droid+Sans:400,700' rel='stylesheet' type='text/css'/>
  <link href='webjars/swagger-ui/2.0.24/css/reset.css' media='screen' rel='stylesheet' type='text/css'/>
  <link href='webjars/swagger-ui/2.0.24/css/screen.css' media='screen' rel='stylesheet' type='text/css'/>
  <link href='webjars/swagger-ui/2.0.24/css/reset.css' media='print' rel='stylesheet' type='text/css'/>
  <link href='webjars/swagger-ui/2.0.24/css/screen.css' media='print' rel='stylesheet' type='text/css'/>
  <script type="text/javascript" src="webjars/swagger-ui/2.0.24/lib/shred.bundle.js"></script>
  <script src='webjars/swagger-ui/2.0.24/lib/jquery-1.8.0.min.js' type='text/javascript'></script>
  <script src='webjars/swagger-ui/2.0.24/lib/jquery.slideto.min.js' type='text/javascript'></script>
  <script src='webjars/swagger-ui/2.0.24/lib/jquery.wiggle.min.js' type='text/javascript'></script>
  <script src='webjars/swagger-ui/2.0.24/lib/jquery.ba-bbq.min.js' type='text/javascript'></script>
  <script src='webjars/swagger-ui/2.0.24/lib/handlebars-1.0.0.js' type='text/javascript'></script>
  <script src='webjars/swagger-ui/2.0.24/lib/underscore-min.js' type='text/javascript'></script>
  <script src='webjars/swagger-ui/2.0.24/lib/backbone-min.js' type='text/javascript'></script>
  <script src='webjars/swagger-ui/2.0.24/lib/swagger.js' type='text/javascript'></script>
  <script src='webjars/swagger-ui/2.0.24/lib/swagger-client.js' type='text/javascript'></script>
  <script src='webjars/swagger-ui/2.0.24/swagger-ui.js' type='text/javascript'></script>
  <script src='webjars/swagger-ui/2.0.24/lib/highlight.7.3.pack.js' type='text/javascript'></script>

  <!-- enabling this will enable oauth2 implicit scope support -->
  <script src='webjars/swagger-ui/2.0.24/lib/swagger-oauth.js' type='text/javascript'></script>


  <!-- enabling this will enable oauth2 implicit scope support -->
  <script src='webjars/swagger-ui/2.0.24/lib/swagger-oauth.js' type='text/javascript'></script>

  <script type="text/javascript">
    $(function () {
      window.swaggerUi = new SwaggerUi({
      url: window.location.protocol + "//" + window.location.host + "/refset/api-docs",
      dom_id: "swagger-ui-container",
      supportedSubmitMethods: ['get', 'post', 'put', 'delete'],
      onComplete: function(swaggerApi, swaggerUi){
        log("Loaded SwaggerUI");

        $('pre code').each(function(i, e) {
          hljs.highlightBlock(e)
        });
      },
      onFailure: function(data) {
        log("Unable to Load SwaggerUI");
      },
      docExpansion: "none",
      sorter : "alpha"
    });

    $('#input_apiKey').change(function() {
      var key = $('#input_apiKey')[0].value;
      log("key: " + key);
      if(key && key.trim() != "") {
        log("added key " + key);
        window.authorizations.add("key", new ApiKeyAuthorization("api_key", key, "query"));
      }
    })
    window.swaggerUi.load();
  });
  </script>

  

</head>

<body class="swagger-section" >
<div id='header' style="background-color: #FFFFFF;">
  <div class="swagger-ui-wrap">
    	<a href="." ><img alt="IHTSDO's Refset Service API docs" src="http://www.ihtsdo.org/images/small_logo.png"></a>
    <form id='api_selector'>
      <div class='input'><input id="input_baseUrl" name="baseUrl" type="text" /></div>
      <div class='input'><a id="explore" href="#" style="background-color: #00A4E4;">Explore</a></div>
    </form>
  </div>
</div>

<div id="message-bar" class="swagger-ui-wrap">&nbsp;</div>
<div id="swagger-ui-container" class="swagger-ui-wrap"></div>
</body>
</html>
