

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <!-- <meta name="layout" content="main" /> -->
        <title>Show Kml</title>
	<link rel="stylesheet" href="/routemap/css/main.css" />
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
    link.setHref("http://140.254.194.156${g.createLink(action:'download',id:kmlInstance.id)}");

    // attach the Link to the NetworkLink
    networkLink.setLink(link);

    // add the NetworkLink feature to Earth
    ge.getFeatures().appendChild(networkLink);
  }
</script>
    </head>
    <div id="Container">
    <body onload="init()" id='body'>
	<div class="logo"><img src="/routemap/images/grails_logo.jpg" alt="Routemap" /></div>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Kml List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Kml</g:link></span>
        </div>
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
            <div class="buttons">
                <g:form action="download" method="post">
                    <input type="hidden" name="id" value="${kmlInstance?.id}" />
                    <span class="button"><input type="submit" value="Download Kml" /></span>
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
        <br/>
        <div id="map3d_container" style="margin-left: 15px; border: 1px solid silver; height: 500px; width: 720px; clear:left;">
          <div id="map3d"></div>
        </div>
    </div>
    </body>
</html>
