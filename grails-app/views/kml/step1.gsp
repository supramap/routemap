

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Step 1</title>
    </head>
    <body>
      <div id="nav1">
	<ul id="menus">
          <li><a href="${resource(dir:'')}">Home</a></li>
          <li><g:link class="current" action="create">New Kml</g:link></li>
          <li><g:link action="list">List Kmls</g:link></li>
	</ul>
      </div>
        <div id="Content">
            <h1>Step 1: upload the tnt sequence file and the coordinates csv</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${flash.problems}">
            <g:each in="${flash.problems}" var="it">
                <div class="errors">${it.key}: ${it.value}</div>
            </g:each>
            </g:if>
            <g:form action="generateScript" method="post" enctype="multipart/form-data">
              <p>
                TNT Data:<input type="file" id="data" name="data" /><br/>
                Coordinates CSV:<input type="file" id="coords" name="coords" />
              </p><br/>
              <input type="submit" value="Next" />
            </g:form>
        </div>
    </body>
</html>
