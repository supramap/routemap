class SanityChecksService {

    boolean transactional = true

    public static LinkedHashMap checkCSV(String migrations, String coordinates)
    {
        def migLocations = [] //List of locations from the migration file
        def coordLocations = []//List of locations from the locations file
        def problems = [:] //Map of all the problems to be returned. Key is 'Error' or 'Warning' and Value is the message.
        def migFile = new File(migrations)
        def coordFile = new File(coordinates)

        //The following replaces all the newline characters with the unix format
        String text = migFile.getText()
        text = text.replaceAll("\r\n|\n\r|\r","\n")
        migFile.write(text)

        text = coordFile.getText()
        text = text.replaceAll("\r\n|\n\r|\r","\n")
        coordFile.write(text)

        def lineNum = 0
        def errNum = 1
        def warNum = 1
        coordFile.splitEachLine(',') {fields -> //Builds coordLocations and checks lats and longs to make sure they are in bounds
            lineNum++
            if (lineNum > 1) //First line doesn't containt data
            {
                def latitude = Float.parseFloat(fields[1])
                def longitude = Float.parseFloat(fields[2])
                coordLocations.add(fields[0])
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

        lineNum = 0
        migFile.splitEachLine(',') {fields -> //Adds each location from the migrations file to migLocations
            lineNum++
            if (lineNum > 1) //First line doesn't contain data
                migLocations.add(fields[0])
        }
        migLocations -= ['sinks']

        migLocations.each() {  //Checks each location name and makes sure it is not part of another location name
            def curLoc = it
            migLocations.each {
                if (it.count(curLoc) > 0 && curLoc != it) {
                    problems.put "Error${errNum}","Location ${it} and location ${curloc} have conflicting names."
                    errNum++
                }
            }
        }

        migLocations.each { //Compares the two location lists to make sure they contain the same locations
            def place = it
            def found = coordLocations.find{it == place}
            if (found == null) {
                problems.put "Error${errNum}","${it} is found in migrations but not in coordinates."
                errNum++
            }
            else
                coordLocations -= [it] //Removes found location from list.  Any remaining locations generate a warning
        }

        if (!coordLocations.empty) { //Generates warnings for remaining locations
            coordLocations.each{problems.put "Warning${warNum}","${it} is found in coordinates but not in migrations."}
            warNum++
        }

        if (problems) {
            println "Problems:"
            problems.each{println "${it.key}:${it.value}"}
        }
        return problems //Returns the map of warnings and errors
    }
}
