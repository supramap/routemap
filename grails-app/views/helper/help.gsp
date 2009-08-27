

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Help</title>
    </head>
    <body>
      <div id="nav1">
	<ul id="menus">
          <li><a href="/">Home</a></li>
          <li><g:link controller="kml" action="create">New Kml</g:link></li>
          <li><g:link controller="kml" action="list">List Kmls</g:link></li>
          <li><g:link controller="helper" action="help" class="current">Help</g:link></li>
          <li><g:link controller="helper" action="contact">Contact Us</g:link></li>
	</ul>
      </div>
        <div id="Content">
          <h1>Preparation</h1>
            <p style="width:80%">
              There are several things you need before you begin the creation process:<br/><br/>
              1) Make sure that javascript is enabled in your browser.<br/><br/>
              2) Create a routemap account: register <g:link controller="user" action="create" >here</g:link>.<br/><br/>
              3) Download TNT analysis for trees and character evolution: available <a href="http://www.zmuc.dk/public/phylogeny/TNT/" target="_blank">here</a>.<br/><br/>
              4) Make two input files: <a href="${createLinkTo(dir:'files',file:'routemap-samples.zip')}">Download Samples</a><br/>
            </p>
            <ul style="margin-left:60px; width:80%">
              <li>
                Sequence datafile - a fasta file containing phylogenetic data, such as DNA sequences.
              </li>
              <li>
                Geographic datafile - a comma separated (csv) file containing a list of each taxon, followed by a location name, latitude, and longitude.
                The first line of the file must be a header similar to that found in the sample file.
                <br/>NOTE: TNT limits the number of unique placenames to 31, and placenames cannot contain spaces.
              </li>
            </ul>
            <br/>
            <p>
              Once these steps are completed, click 'New Kml' at the top of the page to begin the creation process.
            </p>
            <h1>Advanced Options</h1>
                <p style="width:80%">
                    Outgroup: Specify an outgroup.  By default, the first taxa in the fasta is used.<br/><br/>
                    Search level: The search level. Higher level = more thorough search, but slower.<br/><br/>
                    Treelength to hit: The length of the most parsimonious trees if you know it. This could speed up the tree collection.<br/><br/>
                    Hits to minimum length: The number of independent hits to minimum length during search.<br/><br/>
                    Load trees from file: Load trees from a treefile instead of performing a search. Trees needs to be in a format readble by tnt.
                    To use this option, specify the filename of the treefile.  When you run the downloaded tntscript,
                    make sure the specified treefile is present in the same folder as the script.<br/><br/>
                    Save trees to file: Save the trees found during search to a file.<br/><br/>
                    Custom search parameters: Replace the search parameters with custom commands.  Completely replaces the xm= line in the tnt script.
                </p><br/>
        </div>
    </body>
</html>