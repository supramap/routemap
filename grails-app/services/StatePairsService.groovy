class StatePairsService {

    boolean transactional = true

    public static LinkedHashMap convert(String folder) {
        def coordinates = new File("/tmp/routemap/${folder}/coordinates")
        def output = new File("/tmp/routemap/${folder}/transmissions.script")

        //The following ensures all newline charachters are in unix format
        String text = coordinates.getText()
        text = text.replaceAll("\r\n|\n\r|\r","\n")
        coordinates.write(text)

        def problems = [:]
        def locations = []
        def lineNum = 0
        def errNum = 1

        //The following checks the coordinates line for correct formatting and in bounds locations
        coordinates.splitEachLine(',') {fields ->
            lineNum++
            if (lineNum > 1) //First line doesn't containt data
            {
                def latitude = Float.parseFloat(fields[1])
                def longitude = Float.parseFloat(fields[2])
                locations.add(fields[0])
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

        //locations.eachWithIndex{ it, i -> println "${i} : ${it}"}

        //Writes the tnt script
        output.append("quote Calculating transmission routes;\nsilent = console;\n")
        locations.eachWithIndex { change, i ->
            locations.eachWithIndex{ to, j ->
                if (i != j) {
                    output.append("log change${change}_to_${to}.txt ; change ]./0/ ${change} ${to} ; log /;\n")
                }
            }
        }
        output.append("silent - console;")
        if (problems) {
            println "Problems:"
            problems.each{println "${it.key}:${it.value}"}
        }
        return problems //returns a map of problems
    }
}

