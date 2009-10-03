class MyTagLib {
    def loginControl = {
        if(session.user){
            out << "Hello ${session.user.name} "
            out << "["
            if(session.user.role == "admin") {
                out << """${link(action:"list", controller:"user"){"Manage Users"}} | """
            } else {
                out << """${link(action:"show", controller:"user", id:session.user.id){"Account Settings"}} | """
            }
            out << """${link(action:"logout", controller:"user"){"Logout"}}]"""
        } else {
            out << """[${link(action:"login", controller:"user"){"Login"}} | """
            out << """${link(action:"create", controller:"user"){"New Account"}}]"""
        }
    }

    def myNav = { attrs ->
        out << "<div id=\"nav1\">\n\t<ul id=\"menus\">"
        if (attrs['current'] == 'home')
            out << "\n\t\t<li><a href=\"/\" class=\"current\">Home</a></li>"
        else
            out << "\n\t\t<li><a href=\"/\">Home</a></li>"
        if (attrs['current'] == 'new')
            out << "\n\t\t<li><a href=\"/kml/create\" class=\"current\">New Kml</a></li>"
        else
            out << "\n\t\t<li><a href=\"/kml/create\">New Kml</a></li>"
        if (attrs['current'] == 'list')
            out << "\n\t\t<li><a href=\"/kml/list\" class=\"current\">List Kmls</a></li>"
        else
            out << "\n\t\t<li><a href=\"/kml/list\">List Kmls</a></li>"
        if (attrs['current'] == 'help')
            out << "\n\t\t<li><a href=\"/extras/help\" class=\"current\">Help</a></li>"
        else
            out << "\n\t\t<li><a href=\"/extras/help\">Help</a></li>"
        if (attrs['current'] == 'publications')
            out << "\n\t\t<li><a href=\"/extras/publications\" class=\"current\">Publications</a></li>"
        else
            out << "\n\t\t<li><a href=\"/extras/publications\">Publications</a></li>"
        if (attrs['current'] == 'contacts')
            out << "\n\t\t<li><a href=\"/extras/contacts\" class=\"current\">Contact Us</a></li>"
        else
            out << "\n\t\t<li><a href=\"/extras/contacts\">Contact Us</a></li>"
        out << "\n\t</ul>\n</div>"
    }
}