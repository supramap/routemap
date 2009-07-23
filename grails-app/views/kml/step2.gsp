

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Step 2</title>
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
            <h1>Step 2: upload the coordinates csv</h1>
            <p style="margin-left:20px; width:80%">
                <a href="${createLinkTo(dir:'files',file:'nycoordinates.csv')}">Sample File</a>
            </p>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${flash.problems}">
            <g:each in="${flash.problems}" var="it">
                <div class="errors">${it.key}: ${it.value}</div>
            </g:each>
            </g:if>
            <g:hasErrors bean="${kmlInstance}">
            <div class="errors">
                <g:renderErrors bean="${kmlInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="generateScript" method="post" enctype="multipart/form-data">
                <div class="dialog">
                    <table>
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="coordinates">Coordinates File:</label>
                                </td>
                                <td valign="top" class="value">
                                    <input type="file" id="coordinates" name="coordinates" />
                                </td>
                            </tr>
                            
                        </tbody>
                    </table>
                </div>
                <input type="submit" value="Next" />
            </g:form>
        </div>
    </body>
</html>
