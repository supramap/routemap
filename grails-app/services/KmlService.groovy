import java.text.SimpleDateFormat

class KmlService {

    boolean transactional = true

    public static String tempFolder(String oldFolder) { //Creates a unique temp folder
        if (oldFolder != null)
            new AntBuilder().delete(dir: "${oldFolder}")
        SimpleDateFormat sdf = new SimpleDateFormat('MMddHHmmss')
        def folder = "/tmp/routemap/${sdf.format(new Date())}"
        new File(folder).mkdirs()
        return folder
    }

    public static LinkedHashMap checkFiles(String folder) {
        def problems = [:] //Map of all the problems to be returned. Key is the error number and value is the message.
        def dataFile = new File("${folder}/data")
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
            if (lineNum > 1 && fields[0] && fields[1] && fields[2]  && fields[3]) { //First line doesn't containt data
                csvTaxa.put fields[0],taxNum //Adds every taxa to the map
                taxNum++
                if (locMap.get(curLine[1]) == null) { //Adds one of each location to the locMap
                    locMap.put(curLine[1],locNum)
                    locNum++
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
        def tntTaxa = [:]
        taxNum = 0
        lineNum = 0
        dataFile.eachLine { fields ->
            lineNum++
            if (lineNum > 2) {
                tntTaxa.put fields[0],taxNum
                taxNum++
            }
        }
        if (taxNum > 30) { //Tnt restricts the number of lactions to 31
            problems.put "Error${errNum}","There must be under 31 locations"
        }
        tntTaxa.each { taxon ->
            if (csvTaxa.get(taxon.key) == null) {
                problems.put "Error${errNum}","${taxon.key} is found in the tnt file but not the csv"
                errNum++
            }
        }
        /*
         * The following makes sure location names don't contain parts of other location names
         */
        locMap.each() { Loc1 ->
            migLocations.each { Loc2
                if (loc2.key.count(Loc1.key) > 0 && Loc1.key != Loc2.key) {
                    problems.put "Error${errNum}","Location ${it} and location ${curloc} have conflicting names."
                    errNum++
                }
            }
        }
        return problems //Returns the map of warnings and errors
    }

    public static void writeScript(String folder) { //Produces a tnt script for the user
        def dataFile = new File("${folder}/data")
        def coordFile = new File("${folder}/coords")
        def output = new File("${folder}/tntscript.tnt")
        def characters //Number of characters in each sequence
        output.append("mx 1000\nxread\n'dataset'\n")
        def i = 0 //Keeps track of line number
        dataFile.splitEachLine(' ') { curLine -> //Writes the sequence data block
            i++
            if (i == 2) {
                characters = Integer.parseInt(curLine[2])+1
                output.append("${characters} ${curLine[3]}\n")
                output.append("&[dna]\n")
            } else if (i > 2 && curLine[0] != ';' && curLine[0] != 'proc') {
                output.append("${curLine[0]}\t${curLine[1]}\n")
            }
        }
        output.append("&[num]\n")
        def locMap = [:] //A map of location names to unique ids: e.g. loc1:0,loc2:1,loc3:2.  Used to make &[num] block
        def locList = []
        def locNum = 0
        i = 0
        coordFile.splitEachLine(',') { curLine -> //Builds a map of unique locations as the key and an integer as the value
            i++
            if (locNum == 10) {
                locNum = 'a' as char //Locations are labeled 1-9 and then a-z
            }
            if (locMap.get(curLine[1]) == null && i > 1) {
                locMap.put(curLine[1],locNum)
                locList += curLine[1]
                ++locNum
            }
        }
        i = 0
        coordFile.splitEachLine(',') { curLine -> //Uses the locations map to find the location id of each taxa, then writes it to output
            i++
            if (i > 1 && curLine[0] && curLine[1])
                output.append("${curLine[0]}\t${locMap.get(curLine[1])}\n")
        }
        output.append(";\ncnames\n{\n${characters-1} Geography\n")
        locList.each { curLoc -> output.append("${curLoc}\n") } //Outputs the list of locations, one location per line
        output.append(";;\nhold 10000\nccode ] ${characters-1};\nxm = hits 100;\n")
        output.append("ccode ] 0.${characters-2};\nccode [ ${characters-1};\nlog tntlog.txt\n")
        locList.eachWithIndex { locA,j ->
            locList.eachWithIndex { locB,k ->
                if (j != k) {
                    output.append("change ]./${characters-1}/ ${locA} ${locB} ;\n")
                }
            }
        }
        output.append("log /;\nzzz;\nproc/;\n")
    }
}