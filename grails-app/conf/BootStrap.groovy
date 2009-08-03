import grails.util.GrailsUtil

class BootStrap {
    def init = { servletContext ->
        new User(login:"admin", password:"supra.map!", name:"Administrator", role:"admin", email:"jyro215@gmail.com").save()
        new User(login:"user", password:"password", name:"John Doe", role:"user", email:"jyro215@gmail.com").save()
    }

    def destroy = {
    }
}
