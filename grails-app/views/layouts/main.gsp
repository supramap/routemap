<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <g:layoutHead />
        <g:javascript library="application" />
    </head>
    <body>
      <div id="spinner" class="spinner" style="display:none;">
        <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
      </div>
      <div id="header">
        <div id="loginHeader"><br/><br/><g:loginControl/>
        <g:if test="${session.user != null && session.user.role == 'admin'}">
          <br/><g:link controller="user" action="list">Manage Users</g:link>
        </g:if>
        </div>
      </div>
      <div id="Container">
        
        <g:layoutBody />
      <div id="footer"></div>
      </div>
    </body>	
</html>
