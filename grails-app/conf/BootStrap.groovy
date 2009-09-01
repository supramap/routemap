import grails.util.GrailsUtil

class BootStrap {
    def init = { servletContext ->
        new User(login:"administrator", password:"supra.map!".encodeAsHash(), name:"Administrator", role:"admin", email:"jori@u.northwestern.edu").save()
    }

    def destroy = {
    }
}
