import java.text.SimpleDateFormat

class KmlService {

    boolean transactional = true

    public static LinkedHashMap checkFiles(String folder, String outGroup)
    {
        def problems = [:] //Map of all the problems to be returned. Key is the error number and value is the message.
        def dataFile = new File("${folder}/seqs")
        def coordFile = new File("${folder}/coords")
        /*
         * The following replaces all the newline characters with the unix format
         */
        String text = dataFile.getText()
        text = text.replaceAll("\r\n|\n\r|\r","\n")
        dataFile.write(text)

        text = coordFile.getText()
        text = text.replaceAll("\r\n|\n\r|\r","\n")
        coordFile.write(text)
        /*
         * The following checks to make sure all the lats/longs are in bounds.  It also builds a map of taxa and a map of locations
         */
        def csvTaxa = [:] //Taxa map
        def taxNum = 0 //Current value of csvTaxa pairs
        def locMap = [:] //Location map
        def locNum = 0 //Current value of the locmap pair
        def lineNum = 0 //The line of the input file
        def errNum = 1 //Current error number
        def latitude, longitude
        coordFile.splitEachLine(',') {fields ->
            lineNum++
            if (lineNum > 1 && fields[0] && fields[1] && fields[2]  && fields[3]) { //First line doesn't contain data
                if (csvTaxa.get(fields[0]) == null) {
                    csvTaxa.put fields[0],taxNum //Adds every taxa to the map
                    taxNum++
                } else {
                    problems.put "Error${errNum}","Taxon ${fields[0]} is in the csv multiple times."
                    errNum++
                }
                if (lineNum > 1 && fields[1] && locMap.get(fields[1]) == null) {
                    if (fields[1].find(' ') != null) {
                        problems.put "Error${errNum}","Locations ${fields[1]} contains spaces.  Placenames must be one word."
                        errNum++
                    }
                    locMap.put(fields[1],locNum)
                    ++locNum
                }
                try { //Checks to make sure lat/long are floats
                    latitude = Float.parseFloat(fields[2])
                    longitude = Float.parseFloat(fields[3])
                } catch(Exception) {
                    problems.put "Error${errNum}","The lat/long of location ${fields[0]} is not in the correct format"
                    errNum++
                }
                if (latitude && longitude) { //Checks to see if variables were set from above
                    if (latitude > 90 || latitude < -90) {
                        problems.put "Error${errNum}","The latitude of ${fields[0]} is out of bounds"
                        errNum++
                    }
                    if (longitude > 180 || longitude < -180) {
                        problems.put "Error${errNum}","The longitude of ${fields[0]} is out of bounds"
                        errNum++
                    }
                }
            }
        }
        /*
         * The following builds a map of taxa from the tnt file, and then checks to see if there is a 1:1 taxa match between files
         */
        def seqTaxa = [:]
        taxNum = 0
        dataFile.eachLine { curLine ->
            if (curLine.find(/>/)) {
                seqTaxa.put curLine-'>',taxNum
                taxNum++
            } 
        }
        if (locNum > 31) { //Tnt restricts the number of lactions to 31
            problems.put "Error${errNum}","Too many locations: ${locNum}.  The csv may contain a max of 31 unique placenames."
        }
        seqTaxa.each { taxon ->
            if (csvTaxa.get(taxon.key) == null) {
                problems.put "Error${errNum}","${taxon.key} is found in the tnt file but not the csv"
                errNum++
            }
        }
        /*
         * The following makes sure location names don't contain parts of other location names
         */
        locMap.each() { loc1 ->
            locMap.each() { loc2 ->
                if (loc2.key.count(loc1.key) > 0 && loc1.key != loc2.key) { //Checks to see if loc2 contains loc1
                    problems.put "Error${errNum}","Location ${loc1} and location ${loc2} have conflicting names."
                    errNum++
                }
            }
        }
        /*
         * The following checks to make sure the outgroup exists
         */
        if (outGroup != "" && outGroup != null) {
            if (seqTaxa.get(outGroup) == null) {
                problems.put "Error${errNum}","The outgroup, ${outGroup}, does not exist in the dataset."
                errNum++
            }
        }
        return problems //Returns the map of warnings and errors
    }

    public static void writeScript(String folder, Map params) //Produces a tnt script for the user
    {
        def dataFile = new File("${folder}/seqs")
        def coordFile = new File("${folder}/coords")
        def output = new File("${folder}/tntscript.tnt")

        def characters //Number of characters in each sequence
        def taxNum = 0 //The number of taxa in the fasta
        def lineNum = 0 //Keeps track of line number
        def temp = new File("${folder}/temp") //Temporarily holds the sequences so the they can be counted for taxNum
        temp.write("") //Make sure temp is empty
	/* Write the data block */
        dataFile.eachLine { curLine ->
            if (curLine) {
                if (curLine.find(/>/) != null) {
                    temp.append("\n${curLine-'>'}\t")
                    taxNum++
                } else {
                    temp.append("${curLine}")
                }
            }
        }
        output.write("mx 1000\nnstates 32\nxread\n'dataset'\n")
        def firstTax
        temp.eachLine { curLine ->
            lineNum++
            if (lineNum == 2) {
                def tmp = curLine.tokenize('\t')
                characters = tmp[1].trim().size()+1
                output.append("${characters} ${taxNum}\n&[${params.dataType}]\n${curLine}\n")
            } else if (lineNum > 2) {
                output.append("${curLine.trim()}\n")
            }
        }
        /* Write the location block */
        output.append("&[num]\n")
        def locMap = [:] //A map of location names to unique ids: e.g. loc1:0,loc2:1,loc3:2.  Used to make &[num] block
        def locList = []
        def locNum = 0
        lineNum = 0
        coordFile.splitEachLine(',') { curLine -> //Builds a map of unique locations as the key and an integer as the value
            lineNum++
            if (locNum == 10) {
                locNum = 'a' as char //Locations are labeled 1-9 and then a-z
            }
            if (lineNum > 1 && curLine[1] && locMap.get(curLine[1]) == null) {
                locMap.put(curLine[1],locNum)
                locList += curLine[1]
                ++locNum
            }
        }
        lineNum = 0
        coordFile.splitEachLine(',') { curLine -> //Uses the locations map to find the location id of each taxa, then writes it to output
            lineNum++
            if (lineNum > 1 && curLine[0] && curLine[1])
                output.append("${curLine[0]}\t${locMap.get(curLine[1])}\n")
        }
        output.append(";\n")
        if (params.outGroup != "") {
            output.append("outgroup ${params.outGroup};")
        }
        output.append("cnames\n{\n${characters-1} Geography\n")
        locList.each { curLoc -> output.append("${curLoc}\n") } //Outputs the list of locations, one location per line
        /* Write additional options */
        output.append(";;\nhold 10000;\nccode ] ${characters-1};\n")
        /* The following checks the params map for advanced options, and modified the xm= line accordingly */
        if (params.treeFile == "on") {
            if (params.treeType == "parenthetical") {
                output.append("proc ${params.treeName};\n")
            } else {
                output.append("shortread ${params.treeName};\n")
            }
        } else if (params.custom != "") {
            output.append("${params.custom}\n")
        } else {
            output.append("xm =")
            def hits = 100 //Default number of hits
            if (params.hits != "") {
                hits = params.hits
            }
            output.append(" hits ${hits}")
            if (params.searchLevel != "") {
                output.append(" level ${params.searchLevel}")
            }
            if (params.treeLength != "") {
                output.append(" giveupscore ${params.treeLength}")
            }
            output.append(";\n")
        }
        output.append("ccode ] 0.${characters-2};\nccode [ ${characters-1};\nlog tntlog.txt;\n")
        locList.eachWithIndex { locA,j ->
            locList.eachWithIndex { locB,k ->
                if (j != k) {
                    output.append("change ]./${characters-1}/ ${locA} ${locB} ;\n")
                }
            }
        }
        output.append("log /;\n")
        if (params.save == "on") { //Checks to see if the user wishes to save the trees found during search
            if (params.saveType == "parenthetical") {
                output.append("taxname=;\ntsave* ${params.saveName};\n")
            } else {
                output.append("tsave ${params.saveName}\n")
            }
            output.append("save;\ntsave /;\n")
        }
        output.append("zzz;\nproc/;\n")
    }

    public static void writeInputs(String folder)
    {
        def tntIn = new File("${folder}/tntlog")
        def coordIn = new File("${folder}/coords")
        def migOut = new File("${folder}/migrations")
        def coordOut = new File("${folder}/coordinates")
        /*
         * The following block of code writes the coordinates input for the BuildKmlService
         */
        def locMap = [:] //Keeps track of which locations have been added to the list
        def locList = [] //A list of all locations
        def lineNum = 0
        coordOut.write("Label,Lat,Long\n")
        coordIn.splitEachLine(',') { curLine ->
            lineNum++
            if (lineNum > 1 && curLine[1] && curLine[2] && curLine[3] && locMap.get(curLine[1]) == null) {
                    locMap.put(curLine[1],lineNum)
                    locList += curLine[1]
                    coordOut.append("${curLine[1]},${curLine[2]},${curLine[3]}\n")
            }
        }
        /*
         * The following block of code writes the migrations input for the BuildKmlService
         */
	migOut.write(",")
        locList.eachWithIndex { curLoc,i ->
            migOut.append("${curLoc}")
            if (i != (locList.size()-1)) {
                migOut.append(",")
            } else {
                migOut.append("\n")
            }
        }
        def i = 0 //Current row location number
        def j = 0 //Current column location number
        tntIn.splitEachLine(' ') { curLine ->
            if (curLine.size() == 6 && curLine[0] == "Char") {
                if (j == 0) {
                    migOut.append("${locList[i]},")
                }
                if (i == j) {
                    migOut.append("0,")
		    j++
                }
                migOut.append("${curLine[5].find(/[0-9]*\)/)-')'}") //Searches for the second number in (min-max) and adds it
                if (j == locList.size()-1) {
                    migOut.append("\n")
                    i++
                    j = 0
                } else {
                    migOut.append(",")
                    j++
                }
            }
        }
	migOut.append("0") //For the last row, the (i == j) test will never occur, so 0 must be added
    }

    public static void writeKml(String folder) //Produces the final kml
    {
        def migFile = new File("${folder}/migrations")
        def coordFile = new File("${folder}/coordinates")
        def kmlFile = new File ("${folder}/transmissions.kml")
        def locMap = [:]
        def locList = []
        def migTable = []

        /*
         * The following builds a list of locations and a map of location names to their coordinates
         */
        def lineNum = 0
        coordFile.splitEachLine(',') { curLine ->
            lineNum++
            if (lineNum > 1 && curLine[0] && curLine[1] && curLine[2]) {
                locList += curLine[0]
                locMap.put(curLine[0], "${curLine[2]},${curLine[1]}")
            }
        }

        /*
         * The following puts the migrations table into a 2d array
         */
        lineNum = 0
        migFile.splitEachLine(',') { curLine ->
            lineNum++
            if (lineNum > 1)
                migTable[lineNum-2] = curLine
        }

        /*
         * Write header and styles for kml
         */
        kmlFile.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
        kmlFile.append("<kml xmlns=\"http://earth.google.com/kml/2.1\">\n")
        kmlFile.append("\t<Document><Name>Transmissions</Name><open>1</open>\n")
        kmlFile.append("<Style id=\"sh_outgoing\"><IconStyle><scale>1.0</scale><Icon><href>http://maps.google.com/mapfiles/kml/shapes/placemark_circle_highlight.png</href></Icon></IconStyle><LabelStyle><color>ffffffff</color><scale>1.0</scale></LabelStyle><BalloonStyle><displayMode>hide</displayMode></BalloonStyle><LineStyle><width>0.8</width><color>ff00ff00</color></LineStyle></Style>\n")
        kmlFile.append("<Style id=\"sn_outgoing\"><IconStyle><scale>2.0</scale><Icon><href>http://maps.google.com/mapfiles/kml/shapes/placemark_circle_highlight.png</href></Icon></IconStyle><LabelStyle><color>ffb5b5b5</color><scale>0.7</scale></LabelStyle><BalloonStyle><displayMode>hide</displayMode> </BalloonStyle><LineStyle><color>00ffffff</color></LineStyle></Style>\n")
        kmlFile.append("<StyleMap id=\"outgoing\"><Pair><key>normal</key><styleUrl>#sn_outgoing</styleUrl></Pair><Pair><key>highlight</key><styleUrl>#sh_outgoing</styleUrl></Pair></StyleMap>\n")
        kmlFile.append("<Style id=\"sh_incoming\"><IconStyle><scale>1.0</scale><Icon><href>http://maps.google.com/mapfiles/kml/shapes/placemark_circle_highlight.png</href></Icon></IconStyle><LabelStyle><color>ffffffff</color><scale>1.0</scale></LabelStyle><BalloonStyle><displayMode>hide</displayMode></BalloonStyle><LineStyle><width>0.8</width><color>ff0000ff</color></LineStyle></Style>\n")
        kmlFile.append("<Style id=\"sn_incoming\"><IconStyle><scale>2.0</scale><Icon><href>http://maps.google.com/mapfiles/kml/shapes/placemark_circle_highlight.png</href></Icon></IconStyle><LabelStyle><color>ffb5b5b5</color><scale>0.7</scale></LabelStyle><BalloonStyle><displayMode>hide</displayMode> </BalloonStyle><LineStyle><color>00ffffff</color></LineStyle></Style>\n")
        kmlFile.append("<StyleMap id=\"incoming\"><Pair><key>normal</key><styleUrl>#sn_incoming</styleUrl></Pair><Pair><key>highlight</key><styleUrl>#sh_incoming</styleUrl></Pair></StyleMap>\n")
        
        /*
         * Write the outgoing folder
         */
        kmlFile.append("\t\t<Folder><name>Outgoing</name><open>0</open>\n")
        def rowNum = 0
        def colNum = 0
        def placeName
        migTable.each { curRow ->
            placeName = locList[rowNum]
            kmlFile.append("\t\t\t<Placemark><name>${placeName} Outgoing</name>\n")
            kmlFile.append("\t\t\t\t<styleUrl>#outgoing</styleUrl><MultiGeometry>\n")
            kmlFile.append("\t\t\t\t\t<Point><coordinates>${locMap.get(placeName)}</coordinates></Point>\n")
            curRow.each { curCol ->
                if (colNum > 0 && curCol != '0') {
                    kmlFile.append("\t\t\t\t\t<LineString><tessellate>1</tessellate><altitudeMode>relative</altitudeMode>\n")
                    kmlFile.append("\t\t\t\t\t\t<coordinates>${locMap.get(placeName)},0 ${locMap.get(locList[colNum-1])},0</coordinates>\n")
                    kmlFile.append("\t\t\t\t\t</LineString>\n")
                }
                colNum++
            }
            kmlFile.append("\t\t\t\t</MultiGeometry>\n")
            kmlFile.append("\t\t\t</Placemark>\n")
            colNum = 0
            rowNum++
        }
        kmlFile.append("\t\t</Folder>\n")

        /*
         * Write the incoming folder
         */
        kmlFile.append("\t\t<Folder><name>Incoming</name><open>0</open>\n")
        for (colNum = 1; colNum < migTable.size()+1; colNum++) {
            placeName = locList[colNum-1]
            kmlFile.append("\t\t\t<Placemark><name>${placeName} Incoming</name>\n")
            kmlFile.append("\t\t\t\t<styleUrl>#incoming</styleUrl><MultiGeometry>\n")
            kmlFile.append("\t\t\t\t\t<Point><coordinates>${locMap.get(placeName)}</coordinates></Point>\n")
            for (rowNum = 0; rowNum < migTable.size(); rowNum++) {
                if (migTable[rowNum][colNum] != '0') {
                    kmlFile.append("\t\t\t\t\t<LineString><tessellate>1</tessellate><altitudeMode>relative</altitudeMode>\n")
                    kmlFile.append("\t\t\t\t\t\t<coordinates>${locMap.get(placeName)},0 ${locMap.get(locList[rowNum])},0</coordinates>\n")
                    kmlFile.append("\t\t\t\t\t</LineString>\n")
                }
            }
            kmlFile.append("\t\t\t\t</MultiGeometry>\n")
            kmlFile.append("\t\t\t</Placemark>\n")
        }
        kmlFile.append("\t\t</Folder>\n")

        /*
         * Close the kml
         */
        kmlFile.append("\t</Document>\n")
        kmlFile.append("</kml>")
    }
}
