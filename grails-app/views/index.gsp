

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="none" />
        <title>Routemap</title>
	<link rel="stylesheet" href="${createLinkTo(dir:'css',file:'main.css')}" />
<script type="text/javascript" src="/js/application.js"></script>
<script type="text/javascript" src="http://www.google.com/jsapi?key=ABQIAAAA5uUDbWrxnGTprsjai05OsRTMuHb6cRl936UeYBMQY6LRFbmU_xQawJ-b3-OnT4HQOHUQjgkQ08alUA"></script>
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
    link.setHref("http://routemap.osu.edu/files/example.kml");

    // attach the Link to the NetworkLink
    networkLink.setLink(link);

    // add the NetworkLink feature to Earth
    ge.getFeatures().appendChild(networkLink);
  }
</script>
    </head>
    <body onload="init()" id='body'>
      <div id="header">
        <div id="loginHeader"><g:loginControl/></div>
      </div>
      <div id="Container">
        <g:myNav current="home"/>
        <div id="Content">
        <h1 style="margin-left:20px;">Welcome to Routemap</h1>
        <p style="margin-left:20px;width:95%">
          Routemap produces a keyhole markup file (kml) that displays disease transmission events implied by genetic sequence data on pathogens.
          <br/><br/>
          Interactive routemap for geographic transmission of viral strains based on 461 full genomes of pandemic Influenza (H1N1).
          Click the dot for a location of interest.  Mouse over the dots that arise from your location of interest to see incoming and outgoing transmissions.
          On mouse over, the green lines represent outgoing routes for spread of H1N1; red lines represent incoming routes for spread of H1N1.
        </p>
        <br/>
        <div id="map3d_container" style="margin: 0 20px 0 20px; border: 5px solid #565656; height: 500px; width: 732px;">
          <div id="map3d" style="height: 100%;"></div>
        </div>
        </div>
        <div id="footer"></div>
      </div>
      <script type="text/javascript">
        var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
        document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
      </script>
      <script type="text/javascript">
        try {
          var pageTracker = _gat._getTracker("UA-10595318-1");
          pageTracker._trackPageview();
        } catch(err) {}
      </script>
    </body>
</html>
