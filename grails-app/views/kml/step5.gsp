

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Step 5</title>
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
            <h1>Step 5: name kml and give description</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${kmlInstance}">
            <div class="errors">
                <g:renderErrors bean="${kmlInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post"  enctype="multipart/form-data">
                <div class="dialog">
                    <table>
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Name:</label>
                                </td>
                                <td valign="top" class="value">
                                    <input type="text" id="name" name="name" value="${fieldValue(bean:kmlInstance,field:'name')}"/>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="description">
                                    <label for="description">Description:</label>
                                </td>
                                <td valign="top" class="value">
                                    <input type="text" id="description" name="description" value="${fieldValue(bean:kmlInstance,field:'description')}"/>
                                </td>
                            </tr>

                        </tbody>
                    </table>
                </div>
                  <input type="submit" value="Create" />
            </g:form>
        </div>
    </body>
</html>
