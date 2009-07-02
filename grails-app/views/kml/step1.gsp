

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Step 1</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Kml List</g:link></span>
        </div>
        <div class="body">
            <h1>Step 1: sample trees</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${kmlInstance}">
            <div class="errors">
                <g:renderErrors bean="${kmlInstance}" as="list" />
            </div>
            </g:hasErrors>
                <p style="margin-left:20px;width:80%">
                    Extract tnt to an empty directory.  This will be your work directory.<br/>
                    Start TNT in the console (./tnt) and perform the following operations:<br/><br/>
                    1) Open the dna sequence data.  <a href="${createLinkTo(dir:'files',file:'nydna.tnt')}">Sample File</a><br/>
                    tnt*>proc nydna.tnt;<br/><br/>
                    2) Expand the tree buffer.<br/>
                    tnt*>hold 10000;<br/><br/>
                    3) Collect trees.  This can be done in many ways.  In this example, TNT searches for the most parsimonious trees and does 100 hits to minimum length.<br/>
                    tnt*>hits 100;<br/><br/>
                    4) Save the trees and exit.  Compact tree format does not use taxon names, so be sure that your files match taxon order exactly.<br/>
                    tnt*>tsave nytrees.ctf; save; tsave/; zzz;
                </p>
            <g:form action="step2" method="post" enctype="multipart/form-data">
                <input type="submit" value="Next" /></span>
            </g:form>
        </div>
    </body>
</html>
