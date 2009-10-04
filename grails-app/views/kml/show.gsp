

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <!-- <meta name="layout" content="main" /> -->
        <title>Show Kml</title>
	<link rel="stylesheet" href="${createLinkTo(dir:'css',file:'main.css')}" />
<script type="text/javascript" src="/js/application.js"></script>
<script type="text/javascript" src="http://www.google.com/jsapi?key=ABQIAAAA5uUDbWrxnGTprsjai05OsRTMuHb6cRl936UeYBMQY6LRFbmU_xQawJ-b3-OnT4HQOHUQjgkQ08alUA"> </script>
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
    link.setHref("http://routemap.osu.edu${g.createLink(action:'download',id:kmlInstance.id)}");

    // attach the Link to the NetworkLink
    networkLink.setLink(link);

    // add the NetworkLink feature to Earth
    ge.getFeatures().appendChild(networkLink);
  }
</script>
    </head>
    <body onload="init()" id="body">
      <div id="header">
        <div id="loginHeader"><g:loginControl/></div>
      </div>
      <div id="Container">
      <g:myNav/>
        <div id="Content">
            <h1>Show Kml</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Name:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:kmlInstance, field:'name')}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Description:</td>

                            <td valign="top" class="value">${fieldValue(bean:kmlInstance, field:'description')}</td>

                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Date Created:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:kmlInstance, field:'dateCreated')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Last Updated:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:kmlInstance, field:'lastUpdated')}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <g:form>
            <div class="buttons">
              <span class="button"><g:link style="margin-left:10px;" action="download" params="[id:params.id,file:'kml',content:'application/vnd.google-earth.kml+xml',name:'.kml']">Download Kml</g:link> | </span>
              <span class="button"><g:link action="download" params="[id:params.id,file:'seqs',content:'text/plain',name:'.fasta']">Download Sequences</g:link> | </span>
              <span class="button"><g:link action="download" params="[id:params.id,file:'coords',content:'text/plain',name:'.csv']">Download Coordinates</g:link></span>
              <input type="hidden" name="id" value="${kmlInstance?.id}" />
              <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
              <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
            </div>
            </g:form>
        <br/>
        <div id="map3d_container" style="margin-left: 20px; border: 5px solid #565656; height: 500px; width: 732px; clear:left;">
          <div id="map3d"></div>
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
