class User {
    String login
    String password
    String name
    String email
    String role = "user"

    static hasMany = [kmls:Kml]

    static constraints = {
        login(unique:true)
        password(password:true)
        name(nullable: false, blank: false)
        email(blank: false, nullable: false, email: true)
    }

    public boolean ownsKml(Kml kmlInstance) {
        if (id == kmlInstance.user.id || role == "admin") {
            return true
        } else {
            return false
        }
    }

    public String resetPassword(int length) {
        def availChars = []
        ('A'..'Z').each { availChars << it.toString() }
        3.times { (0..9).each { availChars << it.toString() } }
        def max = availChars.size
        def rnd = new Random()
        def sb = new StringBuilder()
        length.times { sb.append(availChars[rnd.nextInt(max)]) }
        def newPassword = sb.toString()
        password = newPassword.encodeAsHash()
        return newPassword
    }
}
