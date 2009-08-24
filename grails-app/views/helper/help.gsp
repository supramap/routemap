

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Help</title>
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
            <h1>Advanced Options Help</h1>
                <p style="width:80%">
                    Outgroup: Specify an outgroup.  By default, the first taxa in the fasta is used.<br/><br/>
                    Search level: The search level. Higher level = more thorough search, but slower.<br/><br/>
                    Treelength to hit: The length of the most parsimonious trees if you know it. This could speed up the tree collection.<br/><br/>
                    Hits to minimum length: The number of independent hits to minimum length during search.<br/><br/>
                    Load trees from file: Load trees from a treefile instead of performing a search. Trees needs to be in a format readble by tnt.<br/><br/>
                    Save trees to file: Save the trees found during search to a file.<br/><br/>
                    Custom search parameters: Replace the search parameters with custom commands.  Completely replaces the xm= line in the tnt script.
                </p><br/>
        </div>
    </body>
</html>