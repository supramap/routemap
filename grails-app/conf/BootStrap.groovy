import grails.util.GrailsUtil

class BootStrap {
    def init = { servletContext ->
        new User(login:"admin", password:"supra.map!".encodeAsHash(), name:"Administrator", role:"admin", email:"jori@u.northwestern.edu").save()
        session.setMaxInactiveInterval(3600) //Increase session timeout to 1 hour
    }

    def destroy = {
    }
}
