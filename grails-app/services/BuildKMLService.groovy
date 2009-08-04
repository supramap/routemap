class BuildKMLService {

    boolean transactional = true
	
    private static String[] sources, sinks;
    private static String[][] migrations, coordinates;
    private static String origin, destination, origin2, destination2;

    public static String convert (String f1, String f2, String folder) {
        println f1
        println f2
        String error = readInputFiles(f1,f2);
        describeMigrations();
        writeKML(folder);
        return error;
    }

    private static String writeKML(String folder) {
        try {
                OutputStream fout= new FileOutputStream(folder+"/transmissions.kml");
                OutputStream bout= new BufferedOutputStream(fout);
                OutputStreamWriter out = new OutputStreamWriter(bout, "utf-8");

                out.write("<?xml version=\"1.0\" ");
                out.write("encoding=\"utf-8\"?>\n");
                out.write("<kml xmlns=\"http://earth.google.com/kml/2.1\">\n");
                out.write("\t<Document>\n");
                out.write("\t\t<Name>Transmissions</Name><open>1</open>\n");

                origin2 = ""; destination2 = "";

                //sources
                out.write("\t\t<Folder><name>Outgoing</name><open>0</open>\n");
                for (int j=0; j<sources.length; j++) {
                        origin = sources[j];
                        for (int i=0; i<sinks.length; i++) {
                                if (!migrations[i][j].equals("0")) {
                                        if (!origin2.equals(origin) && !origin2.equals("")) {
                                                out.write("\t\t</Folder>\n");
                                        }
                                        if (!origin2.equals(origin)) {
                                                out.write("\t\t<Folder><name>" + origin + "</name><open>0</open>\n");
                                        }
                                        out.write("\t\t\t<Style id=\"s" + i + "_" + j + "\">\n");
                                        out.write("\t\t\t\t<LineStyle>\n");
                                        out.write("\t\t\t\t\t<width>" + migrations[i][j] + "</width>\n");
                                        if (!migrations[j][i].equals("0")) //bidirectional
                                                out.write("\t\t\t\t\t<color>ff00ddff</color>\n");
                                        else
                                                out.write("\t\t\t\t\t<color>ff0000ff</color>\n");
                                        out.write("\t\t\t\t</LineStyle>\n");
                                        out.write("\t\t\t</Style>\n");
                                        out.write("\t\t\t<Placemark><name>" + origin + " to " + sinks[i] + "</name>\n");
                                        out.write("\t\t\t<visibility>1</visibility>");
                                        out.write("\t\t\t\t<styleUrl>#s" + i + "_" + j + "</styleUrl>\n");
                                        out.write("\t\t\t\t<MultiGeometry>\n");
                                        out.write("\t\t\t\t\t<LineString>\n");
                                        out.write("\t\t\t\t\t\t<tessellate>1</tessellate>\n");
                                        out.write("\t\t\t\t\t\t<altitudeMode>relative</altitudeMode>\n");
                                        out.write("\t\t\t\t\t\t<coordinates>" + getCoordinates(sources[j]) + ",0 " + getCoordinates(sinks[i]) + ",0 </coordinates>\n");
                                        out.write("\t\t\t\t\t</LineString>\n");
                                        out.write("\t\t\t\t</MultiGeometry>\n");
                                        out.write("\t\t\t</Placemark>\n");
                                        origin2 = origin;
                                }
                        }
                }
                out.write("\t\t</Folder>\n");
                out.write("\t\t</Folder>\n");

                //destinations
                out.write("\t\t<Folder><name>Incoming</name><open>0</open>\n");
                for (int i=0; i<sinks.length; i++) {
                        destination = sinks[i]; origin = null;
                        for (int j=0; j<sources.length; j++) {
                                if (!migrations[i][j].equals("0")) {
                                        if (!destination2.equals(destination) && !destination2.equals("")) {
                                                out.write("\t\t</Folder>\n");
                                        }
                                        if (!destination2.equals(destination)) {
                                                out.write("\t\t<Folder><name>" + destination + "</name><open>0</open>\n");
                                        }
                                        out.write("\t\t<Style id=\"s" + i + "_" + j + "\">\n");
                                        out.write("\t\t\t<LineStyle>\n");
                                        out.write("\t\t\t\t<width>2.0</width>\n");
                                        if (!migrations[j][i].equals("0")) //bidirectional
                                                out.write("\t\t\t\t\t<color>ff00ddff</color>\n");
                                        else
                                                out.write("\t\t\t\t<color>ff00ff00</color>\n");
                                        out.write("\t\t\t</LineStyle>\n");
                                        out.write("\t\t</Style>\n");
                                        out.write("\t\t<Placemark><name>" + sources[j] + " to " + destination + "</name>\n");
                                        out.write("\t\t<visibility>1</visibility>");
                                        out.write("\t\t\t<styleUrl>#s" + i + "_" + j + "</styleUrl>\n");
                                        out.write("\t\t\t<MultiGeometry>\n");
                                        out.write("\t\t\t\t<LineString>\n");
                                        out.write("\t\t\t\t\t<tessellate>1</tessellate>\n");
                                        out.write("\t\t\t\t\t<altitudeMode>relative</altitudeMode>\n");
                                        out.write("\t\t\t\t\t<coordinates>" + getCoordinates(sources[j]) + ",0 " + getCoordinates(sinks[i]) + ",0 </coordinates>\n");
                                        out.write("\t\t\t\t</LineString>\n");
                                        out.write("\t\t\t</MultiGeometry>\n");
                                        out.write("\t\t</Placemark>\n");
                                        destination2 = destination;
                                }
                        }
                }
                out.write("\t\t</Folder>\n");
                out.write("\t\t</Folder>\n");

                //add placemarks for every location in the coordinates array
                out.write("\t\t<Folder><name>locations</name><open>0</open>\n");
                for (int i=0; i<coordinates.length; i++) {
                        out.write("\t\t<Placemark>\n");
                        out.write("\t\t\t<name>" + coordinates[i][0] + "</name>\n");
                        out.write("\t\t\t<Point>\n");
                        out.write("\t\t\t\t<coordinates>" + coordinates[i][2] + "," + coordinates[i][1] + ",0</coordinates>\n");
                        out.write("\t\t\t</Point>\n");
                        out.write("\t\t\t<Style>\n");
                        out.write("\t\t\t\t<LabelStyle>\n");
                        out.write("\t\t\t\t\t<scale>1.0</scale>\n");
                        out.write("\t\t\t\t</LabelStyle>\n");
                        out.write("\t\t\t</Style>\n");
                        out.write("\t\t</Placemark>\n");
                }
                out.write("\t\t</Folder>\n");

                out.write("\t</Document>\n");
                out.write("</kml>\n");
                out.flush();
                out.close();
        }
        catch (UnsupportedEncodingException e) {
            System.out.println("This VM does not support the utf-8 character set.");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void describeMigrations () {
        for (int i=0; i<sinks.length; i++) {
                for (int j=0; j<sources.length; j++) {
                        if (!migrations[i][j].equals("0")) {
                                System.out.println("Migration from " + sources[j] + "(" + getCoordinates(sources[j]) + ")" + " to " + sinks[i] + "(" + getCoordinates(sinks[i]) + ")"  + "; " + migrations[i][j]);
                        }
                }
        }
    }

    private static double getBearing(String from, String to) {
        double lat1, long1, lat2, long2, angle;

        lat1 = getLat(from) / 180.0 * Math.PI;
        lat2 = getLat(to) / 180.0 * Math.PI;
        long1 = getLong(from) / 180.0 * Math.PI;
        long2 = getLong(to) / 180.0 * Math.PI;


        angle = - Math.atan2(Math.sin(long1 - long2) * Math.cos(lat2), Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(long1 - long2));

        if (angle < 0.0) angle += Math.PI * 2.0;
        angle = angle * 180.0 / Math.PI;
        return angle;
    }

    private static String getCoordinates (String location) {
        int index = findLocation(location);
        if (index == -1) return "0,0";
        return coordinates[index][2] + "," + coordinates[index][1];
    }

    private static double getLat (String location) {
        int index = findLocation(location);
        if (index == -1) return 0;
        return Double.parseDouble(coordinates[index][1]);
    }

    private static double getLong (String location) {
        int index = findLocation(location);
        if (index == -1) return 0;
        return Double.parseDouble(coordinates[index][2]);
    }

    private static int findLocation (String location) {
        for (int i=0; i<coordinates.length; i++) {
                if (coordinates[i][0].equals(location)){
                        return i;
                }
        }
        return -1;
    }

    private static String readInputFiles (String migrationsFile, String coordsFile) {
        Vector<String> migrationsFileContent, coordsFileContent;
        migrationsFileContent = new Vector<String>();
        coordsFileContent = new Vector<String>();
        try {
                BufferedReader migrationsReader = new BufferedReader(new FileReader(migrationsFile));
                BufferedReader coordsReader = new BufferedReader(new FileReader(coordsFile));
                String line;

                while ((line = migrationsReader.readLine()) != null)
                        migrationsFileContent.add(line);
                migrationsReader.close();

                while ((line = coordsReader.readLine()) != null)
                        coordsFileContent.add(line);
                coordsReader.close();

                int rows = migrationsFileContent.size() - 1;
                int cols = migrationsFileContent.get(0).split(",",-1).length - 1;
                int cSize = coordsFileContent.size() - 1;

                sources = new String[cols];
                sinks = new String[rows];
                coordinates = new String[cSize][3];
                migrations = new String[rows][cols];
                String[] lineValues;

                //load migrations file into arrays sources, sinks, and migrations
                for (int i=0; i<rows+1; i++ ) {
                        lineValues = migrationsFileContent.get(i).split(",",-1);
                        for (int j=0; j<cols; j++) {
                                if (i == 0){ //header line
                                        sources[j] = lineValues[j+1];
                                } else {
                                        migrations[i-1][j] = lineValues[j+1];
                                }
                        }
                        if (i != 0)
                                sinks[i-1] = lineValues[0];
                }

                //load coordinates file into array coordinates
                for (int i=0; i<cSize+1; i++ ) {
                        if (i != 0)
                                coordinates[i-1] = coordsFileContent.get(i).split(",",-1);
                }
            }
            catch (FileNotFoundException e) {
                return "Input file not found.";
            }
            catch (IOException e) {
                return "Input file could not be read.";
            }
	}
}
