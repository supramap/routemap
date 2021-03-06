

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>User List</title>
    </head>
    <body>
        <g:myNav/>
	<div id="content">
        <div id="body" style="float: left;">
            <h1>User List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="Id" />
                        
                            <g:sortableColumn property="login" title="Login" />
                        
                            <g:sortableColumn property="password" title="Password" />
                        
                            <g:sortableColumn property="name" title="Name" />
                        
                            <g:sortableColumn property="email" title="Email" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${userInstanceList}" status="i" var="userInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td>${fieldValue(bean:userInstance, field:'id')}</td>
                        
                            <td><g:link action="show" id="${userInstance.id}">${fieldValue(bean:userInstance, field:'login')}</g:link></td>
                        
                            <td>${fieldValue(bean:userInstance, field:'password')}</td>
                        
                            <td>${fieldValue(bean:userInstance, field:'name')}</td>
                        
                            <td>${fieldValue(bean:userInstance, field:'email')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${userInstanceTotal}" />
            </div>
	<br/>
        </div>
	</div>
    </body>
</html>
