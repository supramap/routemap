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
}
