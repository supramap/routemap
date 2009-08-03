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
}
