import java.text.SimpleDateFormat

class Kml {
    String name
    String description
    User user
    byte[] seqs
    byte[] coords
    byte[] kml
    Date dateCreated
    Date lastUpdated

    static belongsTo = [user:User]

    static constraints = {
        name(nullable: false, blank: false)
        description(nullable: false, blank: true)
    }

    public void writeTemp() {
        SimpleDateFormat sdf = new SimpleDateFormat('MMddHHmmss')
        def fname = "${sdf.format(new Date())}.kml"
        def file = new File("/public/kmls/${fname}")
        def kmlString = kml.toString()
        file.write(kmlString, "utf-")
        session.kml = "${fname}"
    }
}
