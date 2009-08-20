<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <g:layoutHead />
        <g:javascript library="application" />
    </head>
    <body>
      <div id="header">
        <div id="loginHeader"><g:loginControl/></div>
      </div>
      <div id="Container">
        
        <g:layoutBody />
      <div id="footer"></div>
      </div>
    </body>	
</html>
