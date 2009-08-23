

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
            <g:elseif test="${flash.problems}">
              <g:each in="${flash.problems}" var="it">
                <div class="errors">${it.key}: ${it.value}</div>
              </g:each>
            </g:elseif>
            <g:else>
              <br/>
            </g:else>
            <g:form action="save" method="post" name="form1" enctype="multipart/form-data">
              <div class="dialog" style="width:50%;">
                    <table>
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Name (required):</label>
                                </td>
                                <td valign="top">
                                    <input type="text" id="name" name="name" value="${fieldValue(bean:kmlInstance,field:'name')}"/>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Description (optional):</label>
                                </td>
                                <td valign="top">
                                    <input type="text" id="description" name="description" value="${fieldValue(bean:kmlInstance,field:'description')}"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
              </div>
              <br/>
              <input type="submit" value="Next" onclick="showSpinner();"/>
              <img id="spinner" src="${createLinkTo(dir:'images', file:'ajax-loader.gif')}" alt="Please Wait..." style="visibility:hidden;"/>
            </g:form>
            <script language="javascript">document.form1.name.focus()</script>
        </div>
    </body>
</html>
