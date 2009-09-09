<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'main.css')}" />
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
      <script type="text/javascript">
        var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
        document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
      </script>
      <script type="text/javascript">
        try {
          var pageTracker = _gat._getTracker("UA-10595318-1");
          pageTracker._trackPageview();
        } catch(err) {}
      </script> 
    </body>	
</html>
