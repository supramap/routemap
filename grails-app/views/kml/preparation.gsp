

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Preparation</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Kml List</g:link></span>
        </div>
        <div class="body">
            <h1>Preparation</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${kmlInstance}">
            <div class="errors">
                <g:renderErrors bean="${kmlInstance}" as="list" />
            </div>
            </g:hasErrors>
                <p style="margin-left:20px;width:80%">
                    You will need to do an analysis on your own machine(s).  There are several things you need before you begin the creation process:<br/>
                    1) TNT analysis for trees and character evolution: available <a href="http://www.zmuc.dk/public/phylogeny/TNT/">here</a>.<br/>
                    3) Several input files (sample input files will be provided throughout the walkthrough):<br/><br/>
                    Datafile - a file containing phylogenetic data, such as DNA sequences in TNT format.<br/><br/>
                    Geographic datafile - a TNT format file corresponding taxon-to-taxon in indentical order to the datafile.<br/>
                    This file should contain a single multi-state character and a list of character state names. Note that TNT limits a character to 31 states.<br/><br/>
                    Coordinates - a comma separated (csv) file containing a list of all geographic locations, their latitudes and longitudes in decimal degrees.<br/><br/>
                    NOTE: If you already have a coordinates.csv and migrations.csv, go <g:link action="altCreate">here</g:link> to generate a kml.</p>
                </p>
            <g:form action="step1" method="post" enctype="multipart/form-data">
                <input type="submit" value="Next" />
            </g:form>
        </div>
    </body>
</html>
