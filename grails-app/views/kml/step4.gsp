

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Step 4</title>
    </head>
    <body>
      <div id="nav1">
	<ul id="menus">
                <li><a href="${resource(dir:'')}">Home</a></li>
		<li class="current"><g:link class="create" action="create">New Kml</g:link></li>
		<li><g:link class="list" action="list">List Kmls</g:link></li>
	</ul>
      </div>
        <div id="Content">
            <h1>Step 4: upload the migrations csv created by makecsv.sh</h1>
            <p style="margin-left:20px; width:80%">
                <a href="${createLinkTo(dir:'files',file:'nymigrations.csv')}">Sample File</a>.
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
            <g:form action="getMigrations" method="post" enctype="multipart/form-data">
                <div class="dialog">
                    <table>
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="migrations">Migrations File:</label>
                                </td>
                                <td valign="top" class="value">
                                    <input type="file" id="migrations" name="migrations" />
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
