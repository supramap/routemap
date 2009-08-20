

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Step 1</title>
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
            <h1>Step 1: upload the tnt sequence file and the coordinates csv</h1>
            <p style="margin-left:20px;">First time using routemap?  To see what you need, go <g:link action="preparation">here</g:link>.<p>
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
            <g:form action="generateScript" method="post" enctype="multipart/form-data">
              <div class="dialog" style="width:55%;">
                    <table>
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Data Type:</label>
                                </td>
                                <td valign="top">
                                    <g:select id="datatype" name="datatype" from="${['dna','prot']}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Sequence Data:</label>
                                </td>
                                <td valign="top">
                                    <input type="file" id="sequences" name="sequences" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Coordinates CSV:</label>
                                </td>
                                <td valign="top">
                                    <input type="file" id="coordinates" name="coordinates" />
                                </td>
                            </tr>

                            <tr class="prop">
                              <td><a id="displayText" href="javascript:toggle();">Show Advanced</a></td>
                            </tr>

                            <tr id="advanced" class="advanced">
                                <td valign="top" class="name">
                                    <label for="name">Outgroup:</label>
                                </td>
                                <td valign="top">
                                    <input type="text" id="outgroup" name="outgroup" />
                                </td>
                            </tr>
                            
                        </tbody>
                    </table>
              </div>
              <br/>
              <input type="submit" value="Next" onclick="this.disabled=1; showHideSpinner();"/>
              <img id="spinner" src="${createLinkTo(dir:'images', file:'ajax-loader.gif')}" alt="Please Wait..." style="visibility:hidden;"/>
            </g:form>
        </div>
    </body>
</html>
