<html>
  <head>
    <meta name="layout" content="main" />
    <title>Login</title>
  </head>
  <body>
      <g:myNav/>
    <div id="Content">
    <div id="body" style="float: none;">
      <h1>Login</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <g:form action="authenticate" method="post" name="form1">
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
                  <label for="password">Password:</label>
                </td>
                <td>
                  <input type="password" id="password" name="password"/>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="buttons">
          <span class="button">
            <input class="save" type="submit" value="Login" />
          </span>
          <span class="button">
            <g:actionSubmit class="create" action="create" value="New Account" />
          </span>
        </div>
      </g:form>
      <script language="javascript">document.form1.login.focus()</script>
      <br/>
      <p style="margin-left:20px;">
        Cant remember your password?  <g:link action="reset">Reset it</g:link>.
      </p>
    </div>
    </div>
  </body>
</html>
