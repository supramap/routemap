

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Preparation</title>
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
            <h1>Preparation</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${kmlInstance}">
            <div class="errors">
                <g:renderErrors bean="${kmlInstance}" as="list" />
            </div>
            </g:hasErrors>
                <p style="width:80%">
                    There are several things you need before you begin the creation process:<br/>
                    1) TNT analysis for trees and character evolution: available <a href="http://www.zmuc.dk/public/phylogeny/TNT/" target="_blank">here</a>.<br/>
                    2) Two input files (<a href="${createLinkTo(dir:'files',file:'samples.zip')}">Download Samples</a>):
                </p>
                <ul style="margin-left:60px; width:80%">
                  <li>Sequence datafile - a file containing phylogenetic data, such as DNA sequences in TNT format.</li>
                  <li>
                    Geographic datafile - a comma separated (csv) file containing a list of each taxon, followed by a location name, latitude, and longitude.
                    This file should contain a single multi-state character and a list of character state names. Note that TNT limits a character to 31 states.
                  </li>
                </ul>
                <br/>
            <g:form action="step1" method="post" enctype="multipart/form-data">
                <input type="submit" value="Next" />
            </g:form>
        </div>
    </body>
</html>