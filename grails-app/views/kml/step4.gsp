

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Step 4</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Kml List</g:link></span>
        </div>
        <div id="Content">
            <h1>Step 4: upload the migrations csv created by makecsv.sh</h1>
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
            <p style="width:80%">
                It should resemble <a href="${createLinkTo(dir:'files',file:'nymigrations.csv')}">this</a>.
            </p>
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
                <div class="buttons">
                    <span class="button"><input type="submit" value="Next" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
