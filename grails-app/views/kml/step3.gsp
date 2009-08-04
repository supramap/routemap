

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Step 3</title>
    </head>
    <body>
      <div id="nav1">
	<ul id="menus">
          <li><a href="${resource(dir:'')}">Home</a></li>
          <li><g:link class="current" action="create">New Kml</g:link></li>
          <li><g:link action="list">List Kmls</g:link></li>
          <li><g:link controller="helper" action="contact">Contact Us</g:link></li>
	</ul>
      </div>
        <div id="Content">
            <h1>Step 3: name the kml and give a description</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${kmlInstance}">
            <div class="errors">
                <g:renderErrors bean="${kmlInstance}" as="list" />
            </div>
            </g:hasErrors>
            <br/>
            <g:form action="save" method="post"  enctype="multipart/form-data">
              <div class="dialog" style="width:50%;">
                    <table>
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Name (required):</label>
                                </td>
                                <td valign="top">
                                    <input type="text" id="data" name="data" value="${fieldValue(bean:kmlInstance,field:'name')}"/>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Description (optional):</label>
                                </td>
                                <td valign="top">
                                    <input type="text" id="coordinates" name="coordinates" value="${fieldValue(bean:kmlInstance,field:'description')}"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
              </div>
              <br/>
              <input type="submit" value="Create" />
            </g:form>
        </div>
    </body>
</html>
