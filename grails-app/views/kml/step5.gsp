

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Step 5</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Kml List</g:link></span>
        </div>
        <div class="body">
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
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
