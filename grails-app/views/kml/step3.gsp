

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Step 3</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Kml List</g:link></span>
        </div>
        <div class="body">
            <h1>Step 3: collect information about transformation events</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${kmlInstance}">
            <div class="errors">
                <g:renderErrors bean="${kmlInstance}" as="list" />
            </div>
            </g:hasErrors>

                <p style="margin-left:20px;width:80%">
                    Download the generated tnt script <g:link action="download" params="[file:'transmissions.script',content:'text/plain',static:'false']">here</g:link>.<br/>
                    It should resemble <g:link action="download" params="[file:'nytransmissions.script',content:'text/plain',static:true]">this</g:link>.
                </p>
                <p style="margin-left:20px;width:80%">
                    Start tnt again and perform the following operations:<br/><br/>
                    1) Load the geographic datafile and expand the tree buffer.  <g:link action="download" params="[file:'nygeo.tnt',content:'plain/text',static:'true']">Sample file</g:link><br/>
                    tnt*>proc nygeo.tnt; hold 10000;<br/><br/>
                    2) Load the trees saved in step 1.<br/>
                    tnt*>shortread nytrees.ctf;<br/><br/>
                    3) Run the script that you downloaded at the beginning of this step.  This will take several minutes.<br/>
                    tnt*>proc tnt_transmissions.script<br/><br/>
                    4) Exit tnt<br/>
                    tnt*>zzz<br/><br/>
                    5) Run the <g:link action="download" params="[file:'checkpairs.sh',content:'plain/text',static:'true']">checkpairs.sh</g:link>
                    script with the coordinate csv as argument to ensure that you have all the needed components.  This will take several minutes.<br/>
                    ./checkpairs.sh nycoordinates.csv<br/>
                    If the script tells you that there were missing files, the file missingfiles.txt will tell you which, and the TNT script missing.script will produce the missing files.<br/><br/>
                    6) Run the <g:link action="download" params="[file:'makecsv.sh',content:'plain/text',static:'true']">makecsv.sh</g:link>
                    script to create the table of transmission events.<br/>
                    ./makecsv.sh coordinates.csv > migrations.csv<br/>

                </p>
             <g:form action="step4" method="post" enctype="multipart/form-data">
                <input type="submit" value="Next" /></span>
            </g:form>
        </div>
    </body>
</html>
