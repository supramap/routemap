

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Step 2</title>
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
      <div id="content">
        <h1>Step 2: download the tnt script and run it.  Upload the results.</h1>
        <p style="width:80%">
            1) Download the generated tnt script <g:link action="download" params="[file:'tntscript.tnt',content:'text/plain']">here</g:link>.
            It should resemble <a href="${createLinkTo(dir:'files',file:'nytransmissions.script')}">this</a>.<br/>
            2) Copy the script to the directory where tnt is installed.  Run the script.<br/>
            3) The script will produce a log file called tntlog.txt: upload it in the form below.
        </p>
          <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:elseif test="${flash.problems}">
              <g:each in="${flash.problems}" var="it">
                <div class="errors">${it.key}: ${it.value}</div>
              </g:each>
            </g:elseif>
            <g:else>
              <br/>
          </g:else>
        <g:form action="getLog" method="post" enctype="multipart/form-data">
          <p style="margin-left:25px;">
            Tnt Log:<input type="file" id="tntlog" name="tntlog" />
          </p><br/>
          <input type="submit" value="Next" />
        </g:form>
      </div>
    </body>
</html>
