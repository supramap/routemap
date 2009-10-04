<html>
  <head>
    <meta name="layout" content="main" />
    <title>Reset</title>
  </head>
  <body>
      <g:myNav/>
    <div id="Content">
    <div id="body" style="float: none;">
      <h1>Password Reset</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <g:form action="resetPass" method="post" name="form1">
        <div class="dialog">
          <table>
            <tbody>
              <tr class="prop">
                <td class="name">
                  <label for="login">Login:</label>
                </td>
                <td>
                  <input type="text" id="login" name="login"/>
                </td>
              </tr>

              <tr class="prop">
                <td class="name">
                  <label for="password">Email:</label>
                </td>
                <td>
                  <input type="text" id="email" name="email"/>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="buttons">
          <span class="button">
            <input class="save" type="submit" value="Reset Password" />
          </span>
        </div>
      </g:form>
      <script language="javascript">document.form1.login.focus()</script>
    </div>
    </div>
  </body>
</html>
