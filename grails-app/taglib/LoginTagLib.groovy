class LoginTagLib {
    def loginControl = {
        if(session.user){
            out << "Hello ${session.user.name} "
            out << "["
            if(session.user.role == "admin") {
                out << """${link(action:"list", controller:"user"){"Manage Users"}} | """
            }
            out << """${link(action:"logout", controller:"user"){"Logout"}}]"""
        } else {
            out << """[${link(action:"login", controller:"user"){"Login"}} | """
            out << """${link(action:"create", controller:"user"){"New Account"}}]"""
        }
    }
}