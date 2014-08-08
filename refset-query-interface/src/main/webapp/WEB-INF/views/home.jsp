<!DOCTYPE html>
<!-- Copyright (c) 2012-2013 Epimorphics Ltd. Released under Apache License 2.0 http://www.apache.org/licenses/ -->


<html lang="en">
  <head>
    <meta charset="utf-8" />
    <title>SPARQL console</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="" />
    <meta name="author" content="" />

    <link href="resources/css/bootstrap.min.css" rel="stylesheet" />
    <link href="http://netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.min.css" rel="stylesheet" />

    <link href="resources/css/qonsole.css" rel="stylesheet" />
    <link href="resources/css/codemirror.css" rel="stylesheet" />
    <link href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css" rel="stylesheet" />

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="js/lib/html5shiv.js"></script>
      <script src="js/lib/respond.min.js"></script>
    <![endif]-->


    <link rel="shortcut icon" href="img/favicon.ico">

    <script src="http://code.jquery.com/jquery-1.10.1.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
    <script src="resources/js/lib/sprintf-0.7-beta1.js"></script>
    <script src="resources/js/lib/underscore.js"></script>

    <script src="resources/js/lib/codemirror.js"></script>
    <script src="resources/js/lib/foldcode.js"></script>
    <script src="resources/js/lib/foldgutter.js"></script>
    <script src="resources/js/lib/brace-fold.js"></script>
    <script src="resources/js/lib/xml-fold.js"></script>
    <script src="resources/js/lib/comment-fold.js"></script>
    <script src="resources/js/lib/javascript.js"></script>
    <script src="resources/js/lib/xml.js"></script>
    <script src="resources/js/lib/sparql.js"></script>
    <script src="resources/js/app/remote-sparql-service.js"></script>
    <script src="resources/js/app/qonsole.js"></script>
    <script src="resources/js/lib/jquery.xdomainrequest.js"></script>
    <script src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.min.js"></script>

    <!-- This link provides CORS support in IE8+ -->
    <!--[if lt IE 10]>
      <script src="js/lib/jquery.xdomainrequest.js"></script>
    <![endif]-->

    <script type="text/javascript">
      // configuration
      var config = {
        endpoints: {
    	  "default" : "sparql/jena/stream",
		  "Marmotta" : "sparql/kiwi/stream",
    	  "Fuseki-Update" : "sparql/jena/update",
		  "Marmotta-Update" : "sparql/kiwi/update",

		  "Henrik's Fuseki" : "https://sparql.sparklingideas.co.uk/snomed/query"

		  //you can add other points or change port
        },
        prefixes: {
          "rdf":      "http://www.w3.org/1999/02/22-rdf-syntax-ns#",
          "rdfs":     "http://www.w3.org/2000/01/rdf-schema#",
          "owl":      "http://www.w3.org/2002/07/owl#",
          "xsd":      "http://www.w3.org/2001/XMLSchema#",
          "sn" :      "http://snomedtools.info/snomed/term/"
        },
        queries: [
		 { "name": "EAR Concept & its Ancestor",
    		"queryURL": "resources/sparql/retrieve-ear-concept-and-its-ancester-gp-1910005.rq"
 		 }
        ]
        };

      $(function(){qonsole.init( config );});
    </script>
  </head>

  <body>
    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="row">
        <div class="navbar-header col-md-12">
          <h1 class="brand">IHTSDO - Refset</h1>
          <h2 class="brand">Sparql query console</h2>
        </div>
        </div>
      </div>
    </nav>

    <div class="container qonsole">
      <div class="col-md-3">
        <div class="col-md-12 well">
          <h2 class="">Refset queries on SNOMED snapshot - Jan 2014</h2>
          <ul class="list-inline examples">
          </ul>
        </div>

        <div class="col-md-12 well">
          <h2 class="">Prefixes</h2>
          <ul class="list-inline prefixes">
            <li class="keep">
              <a data-toggle="modal" href="#prefixEditor" class="button" title="Add a SPARQL prefix">
                <i class="icon-plus-sign"></i>
              </a>
            </li>
          </ul>
        </div>
      </div>
      <div class="col-md-9">
        <div class="col-md-12 well">
          <div class="query-edit">
            <div id="query-edit-cm" class=""></div>
          </div>
          <div class="query-chrome">
            <form class="form-inline" role="form">
              <div class="form-group">
                <label for="sparqlEndpoint"></label>
                <div class="dropdown ">
                  <a data-toggle="dropdown" class="btn btn-custom2" href="#">
                    Select endpoint <i class="icon-collapse"></i>
                  </a>
                  <ul class="dropdown-menu endpoints" role="menu" aria-labelledby="dropdownMenu1">
                  </ul>
                </div>
              </div>
              <div class="form-group">
                <label for="sparqlEndpoint">SPARQL endpoint</label>
                <input type="text" class="form-control" id="sparqlEndpoint" />
              </div>
              <div class="form-group">
                <label for="displayFormat">Results</label>
                <div class="dropdown ">
                  <a data-toggle="dropdown" class="btn btn-custom2 display-format" href="#" data-value="tsv">
                    <span>Select Query</span> <i class="icon-collapse"></i>
                  </a>
                  <ul class="dropdown-menu formats  " role="menu" aria-labelledby="dropdownMenu2">
                    <li role='presentation' class="" ><a data-value="text" role='menuitem' tabindex='-1' href='#'>Ask</a></li>
                    <li role='presentation' class="" ><a data-value="xml" role='menuitem' tabindex='-1' href='#'>XML(Construct or Describe)</a></li>
                    <li role='presentation' class="" ><a data-value="json" role='menuitem' tabindex='-1' href='#'>Any Query</a></li>
                  </ul>
                </div>

              </div>
              <div class="form-group">
                <label>&nbsp;</label>
                <a href="#" class="btn btn-success run-query form-control">perform query</a>
              </div>

            </form>
          </div>
        </div>

        <!-- results -->
        <div id="results-block" class="row sparql sparql-results">
          <div class="col-md-12">
            <div class="well">
              <div class="row">
                <div class="col-md-12">
                  <span class="loadingSpinner hidden">
                    <img src="resources/img/wait30.gif" alt="waiting for server action to complete" />
                  </span>
                  <span class="timeTaken hidden error"></span>
                </div>
              </div>
              <div class="row">
                <div class="col-md-12" id="results">
                  <h2 class="col-md-12">Query results</h2>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row clearfix"></div>

      <footer>
        <p class="text-center"> The time on the server is ${serverTime}. | &copy; <a href="http://www.epimorphics.com">Epimorphics Ltd</a> 2012&ndash;2013.
          Freely re-usable under the <a href="http://www.apache.org/licenses/LICENSE-2.0.html">Apache Open Source license</a>.</p>
      </footer>

    </div><!-- .container-->

    <!-- modal dialogue -->
    <div class="modal fade" id="prefixEditor" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">Add a SPARQL prefix</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal" role="form">
              <div class="form-group">
                <label for="inputPrefix" class="col-lg-2 control-label">Prefix</label>
                <div class="col-lg-10">
                  <input type="text" class="form-control" id="inputPrefix" placeholder="Prefix" autofocus>
                </div>
              </div>
              <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                  <button class="btn btn-sm btn-primary" id="lookupPrefix">Lookup <span></span> on prefix.cc</button>
                </div>
              </div>
              <div class="form-group">
                <label for="inputURI" class="col-lg-2 control-label">URI</label>
                <div class="col-lg-10">
                  <input type="text" class="form-control" id="inputURI" placeholder="URI">
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">cancel</button>
            <button type="button" class="btn btn-primary" data-dismiss="modal" id="addPrefix">add prefix</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

  </body>
</html>
