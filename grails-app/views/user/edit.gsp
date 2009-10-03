

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit User</title>
    </head>
    <body>
        <g:myNav/>
	<div id="content">
        <div id="body" style="float: none;">
            <h1>Edit User</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${userInstance}">
            <div class="errors">
                <g:renderErrors bean="${userInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" name="form1">
                <input type="hidden" name="id" value="${userInstance?.id}" />
                <input type="hidden" name="version" value="${userInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:userInstance,field:'name','errors')}">
                                    <input type="text" id="name" name="name" value="${fieldValue(bean:userInstance,field:'name')}"/>
                                </td>
                            </tr> 

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="email">Email:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:userInstance,field:'email','errors')}">
                                    <input type="text" id="email" name="email" value="${fieldValue(bean:userInstance,field:'email')}"/>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="email">Old Password:</label>
                                </td>
                                <td valign="top">
                                    <input type="password" id="oldPassword" name="oldPassword"/>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="email">New Password:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:userInstance,field:'password','errors')}">
                                    <input type="password" id="newPassword" name="newPassword"/>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="email">Confirm New Password:</label>
                                </td>
                                <td valign="top">
                                    <input type="password" id="confirm" name="confirm"/>
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
            <script language="javascript">document.form1.name.focus()</script>
        </div>
	</div>
    </body>
</html>
