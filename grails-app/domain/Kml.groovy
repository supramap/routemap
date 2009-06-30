class Kml {
    String name
    String description
    byte[] data
    Date dateCreated
    Date lastUpdated

    static constraints = {
        name(nullable: false, blank: false)
        description(nullable: false, blank: true)
        data(nullable: false)
    }
}
