

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Kml List</title>
    </head>
    <body>
      <g:myNav current="list"/>
        <div class="body">
            <h1>Kml List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>

                          <g:sortableColumn property="user" title="Owner" />
                        
                          <g:sortableColumn property="name" title="Name" />

                          <g:sortableColumn property="description" title="Description" />
                        
                          <g:sortableColumn property="dateCreated" title="Date Created" />

                          <g:sortableColumn property="lastUpdated" title="Last Updated" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${kmlInstanceList}" status="i" var="kmlInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                            <td>${kmlInstance.user.name}</td>
                        
                            <td><g:link action="show" id="${kmlInstance.id}">${fieldValue(bean:kmlInstance, field:'name')}</g:link></td>
                        
                            <td>${fieldValue(bean:kmlInstance, field:'description')}</td>

                            <td>${fieldValue(bean:kmlInstance, field:'dateCreated')}</td>
                        
                            <td>${fieldValue(bean:kmlInstance, field:'lastUpdated')}</td>

                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${kmlInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
