

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
            <p style="margin-left:20px;">
              First time using routemap?  To see what you need, go <g:link action="preparation">here</g:link>.<br/>
              If you need help with advanced options, visit the <g:link class="current" controller="helper" action="help" target="_blank">help page</g:link>.
            <p>
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
              <div class="dialog" style="width:61%;">
                    <table>
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Data Type:</label>
                                </td>
                                <td valign="top">
                                    <g:select id="dataType" name="dataType" from="${['dna','prot']}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Sequence fasta:</label>
                                </td>
                                <td valign="top">
                                    <input type="file" id="sequences" name="sequences" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Coordinates csv:</label>
                                </td>
                                <td valign="top">
                                    <input type="file" id="coordinates" name="coordinates" />
                                </td>
                            </tr>

                            <tr class="prop">
                              <td valign="top" class="name">
                                <a id="displayText" href="javascript:toggleAdvanced();">Show Advanced</a>
                              </td>
                            </tr>

                            <tr id="advanced1" class="advanced">
                                <td valign="top" class="name">
                                    <label for="name">Outgroup:</label>
                                </td>
                                <td valign="top">
                                    <input type="text" id="outGroup" name="outGroup" />
                                </td>
                            </tr>

                            <tr id="advanced2" class="advanced">
                                <td valign="top" class="name">
                                    <label for="name">Search level:</label>
                                </td>
                                <td valign="top">
                                    <g:select id="searchLevel" name="searchLevel" from="${0..10}" noSelection="['':' ']" disabled="no"/>
                                </td>
                            </tr>

                            <tr id="advanced3" class="advanced">
                                <td valign="top" class="name">
                                    <label for="name">Treelength to hit:</label>
                                </td>
                                <td valign="top">
                                    <input type="text" id="treeLength" name="treeLength" />
                                </td>
                            </tr>

                            <tr id="advanced4" class="advanced">
                                <td valign="top" class="name">
                                    <label for="name">Hits to minimum length:</label>
                                </td>
                                <td valign="top">
                                    <input type="text" id="hits" name="hits" />
                                </td>
                            </tr>

                            <tr id="advanced5" class="advanced">
                                <td valign="top" class="name">
                                    <label for="name">Load trees from file:</label>
                                </td>
                                <td valign="top">
                                    <g:checkBox id="treeFile" name="treeFile" value="${false}" onclick="toggleOptions('tree');" />
                                </td>
                            </tr>

                            <tr id="tree1" class="advanced">
                                <td valign="top" class="name">
                                    <label for="name">File name:</label>
                                </td>
                                <td valign="top">
                                    <input type="text" id="treeName" name="treeName" />
                                </td>
                            </tr>

                            <tr id="tree2" class="advanced">
                                <td valign="top" class="name">
                                    <label for="name">Tree type:</label>
                                </td>
                                <td valign="top">
                                    <g:select id="treeType" name="treeType" from="${['parenthetical','compact']}" />
                                </td>
                            </tr>

                            <tr id="advanced6" class="advanced">
                                <td valign="top" class="name">
                                    <label for="name">Save trees to file:</label>
                                </td>
                                <td valign="top">
                                    <g:checkBox id="save" name="save" value="${false}" onclick="toggleOptions('output');" />
                                </td>
                            </tr>

                            <tr id="output1" class="advanced">
                                <td valign="top" class="name">
                                    <label for="name">File name:</label>
                                </td>
                                <td valign="top">
                                    <input type="text" id="saveName" name="saveName" />
                                </td>
                            </tr>

                            <tr id="output2" class="advanced">
                                <td valign="top" class="name">
                                    <label for="name">Tree type:</label>
                                </td>
                                <td valign="top">
                                    <g:select id="saveType" name="saveType" from="${['parenthetical','compact']}" />
                                </td>
                            </tr>

                            <tr id="advanced7" class="advanced">
                                <td valign="top" class="name">
                                    <label for="name">Custom search parameters:</label>
                                </td>
                                <td valign="top">
                                    <input type="text" id="custom" name="custom" />
                                </td>
                            </tr>
                            
                        </tbody>
                    </table>
              </div>
              <br/>
              <input type="submit" value="Next" onclick="showSpinner();"/>
              <img id="spinner" src="${createLinkTo(dir:'images', file:'ajax-loader.gif')}" alt="Please Wait..." style="visibility:hidden;"/>
            </g:form>
        </div>
    </body>
</html>
