

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create User</title>         
    </head>
    <body>
    <div id="nav1">
	<ul id="menus">
          <li><a href="${resource(dir:'')}">Home</a></li>
          <li><g:link controller="kml" action="create">New Kml</g:link></li>
          <li><g:link controller="kml" action="list">List Kmls</g:link></li>
          <li><g:link controller="helper" action="contact">Contact Us</g:link></li>
	</ul>
      </div>
    <div id="Content">
        <div id="body" style="float: none;">
            <h1>Create Account</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${userInstance}">
            <div class="errors">
                <g:renderErrors bean="${userInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" name="form1">
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
                                    <label for="login">Login:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:userInstance,field:'login','errors')}">
                                    <input type="text" id="login" name="login" value="${fieldValue(bean:userInstance,field:'login')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="password">Password:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:userInstance,field:'password','errors')}">
                                    <input type="password" id="password" name="password" value="${fieldValue(bean:userInstance,field:'password')}"/>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="confirm">Confirm Password:</label>
                                </td>
                                <td valign="top">
                                    <input type="password" id="confirm" name="confirm"/>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="eula">EULA:</label>
                                </td>
                                <td valign="top">
                                    <textarea cols="50" rows="10" readonly="yes">
COPYRIGHT © 2008 THE OHIO STATE UNIVERSITY

ALL RIGHTS RESERVED

Dr. Daniel Janies, Ph.D, Daniel.Janies@osumc.edu, 614-292-1202,

The Ohio State University College of Medicine provides this Web-Based Service for mapping geographic transmission route of Infectious diseases (collectively the “Service”) as an informational service. Use of the Service is governed by the terms and conditions provided below. Please read the statements below carefully before accessing or using the Service. By accessing or using the Service, you agree to be bound by all the terms and conditions herein.

Permission is granted to use this Service for individual, clinical, research, or noncommercial educational use only, provided that The Ohio State University and authors of the Service are acknowledged in any publications reporting its use, and the name of The Ohio State University or any of its officers, employees, students or board members is not used in any advertising or publicity pertaining to the use of the Service without specific, written prior authorization. Those desiring to incorporate the Service into commercial products or use the Service for promotional purposes should contact, Technology Licensing and Commercialization, The Ohio State University, 1216 Kinnear Road Columbus, OH 43212-1154 (614) 292-1315, http://tlc.osu.edu/.

The Service is provided AS IS, WITHOUT REPRESENTATION AS TO ITS FITNESS FOR ANY PURPOSE, AND WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, AND NONINFRINGEMENT. No guarantees are made with respect to accuracy, completeness, errors, or omissions of content. The Ohio State University has no obligation to provide support, updates, enhancements, or other modifications. In no event will the Ohio State University be liable for any decision made or action taken in reliance upon the information provided through the Service. THE OHIO STATE UNIVERSITY SHALL NOT BE LIABLE FOR ANY COMPENSATORY OR NON-COMPENSATORY DAMAGES, INCLUDING BUT NOT LIMITED TO SPECIAL, INDIRECT, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, WITH RESPECT TO ANY CLAIM ARISING OUT OF OR IN CONNECTION WITH THE USE OF THE SERVICE, EVEN IF IT HAS BEEN OR IS HEREAFTER ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.

By clicking “I Agree”, you agree to the terms and conditions stated above.
                                    </textarea>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="accept">I Agree:</label>
                                </td>
                                <td valign="top">
                                    <g:checkBox id="accept" name="accept" value="${false}" />
                                </td>
                            </tr>

                        </tbody>
                    </table>
                </div>
                <br/>
              <input type="submit" value="Create" onclick="showSpinner();"/>
              <img id="spinner" src="${createLinkTo(dir:'images', file:'ajax-loader.gif')}" alt="Please Wait..." style="visibility:hidden;"/>
            </g:form>
            <script language="javascript">document.form1.name.focus()</script>
        </div>
    </div>
    </body>
</html>
