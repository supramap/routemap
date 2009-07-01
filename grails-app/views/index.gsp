<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <!-- <meta name="layout" content="main" /> -->
        <title>Routemap</title>
	<link rel="stylesheet" href="/routemap/css/main.css" />
    <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
<script type="text/javascript" src="http://www.google.com/jsapi?key=ABQIAAAA5uUDbWrxnGTprsjai05OsRTcn_-zYPKvuGhF03K7CRCK0gn1chQ4BJbLKMAJh60-GTgOKuUpkyFKrQ"> </script>
<script type="text/javascript">
google.load("earth", "1");

var ge = null;

function init() {
  google.earth.createInstance("map3d", initCallback, failureCallback);
}

function initCallback(pluginInstance) {
  ge = pluginInstance;
  ge.getWindow().setVisibility(true); // required!
  ge.getNavigationControl().setVisibility(ge.VISIBILITY_AUTO);
  createNetworkLink();
  document.getElementById('installed-plugin-version').innerHTML =
    ge.getPluginVersion().toString();

}

function failureCallback(errorCode) {
  //alert("Failure loading the Google Earth Plugin: " + errorCode);
}

function createNetworkLink() {
    var networkLink = ge.createNetworkLink("");
    networkLink.setDescription("NetworkLink open to fetched content");
    networkLink.setName("Open NetworkLink");
    networkLink.setFlyToView(true);

    // create a Link object
    var link = ge.createLink("");
    link.setHref("http://140.254.194.156/routemap/files/nytransmissions.kml");

    // attach the Link to the NetworkLink
    networkLink.setLink(link);

    // add the NetworkLink feature to Earth
    ge.getFeatures().appendChild(networkLink);
  }
</script>
    </head>
    <body onload="init()" id='body'>
	<div class="logo"><img src="/routemap/images/grails_logo.jpg" alt="Routemap" /></div>
        <div class="nav">
            <span class="menuButton"><g:link class="create" controller="kml" action="create">New Kml</g:link></span>
            <span class="menuButton"><g:link class="list" controller="kml" action="list">Kml List</g:link></span>
        </div>
        <h1 style="margin-left:20px;">Welcome to Routemap</h1>
        <p style="margin-left:20px;width:80%">
          Routemap produces a keyhole markup file (kml) that displays disease transmission events implied by genetic sequence data on pathogens.<br/>
          To create a kml, click <g:link controller="kml" action="preparation">here</g:link>.<br/>
          To view kmls that have already been created, click <g:link controller="kml">here</g:link>.<br/>
          If you want to complete the entire process offline without the help of the web tutorial, the necessary files can be found <a href="http://people.mbi.ohio-state.edu/rhovmoller/routemap/walkthrough.zip">here</a>.<br/><br/>
        </p>
        <div id="map3d_container" style="border: 1px solid silver; height: 600px;clear:left;">
          <div id="map3d" style="height: 100%;"></div>
        </div>
    </body>
</html>
