

class UserController {

    def beforeInterceptor = [action:this.&auth, except:["login", "authenticate", "logout", "create", "save", "reset", "resetPass"]]

    def auth() {
        if(session?.user?.role != "admin" && session?.user?.id.toInteger() != params?.id?.toInteger()){
            flash.message = "You do not have permission to perform that task."
            redirect(action:"login")
            return false
        }
    }

    def login = {}

    def authenticate = {
        def user = User.findByLoginAndPassword(params.login, params.password.encodeAsHash())
        if(user){
          session.user = user
          redirect(uri: "/")
        } else {
          flash.message = "Sorry, ${params.login}. Please try again."
          redirect(action:"login")
        }
    }

    def logout = {
        if (session.user) {
            flash.message = "Goodbye ${session.user.name}"
            session.user = null
        }
        redirect(uri: "/")
    }


    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ userInstanceList: User.list( params ), userInstanceTotal: User.count() ]
    }

    def show = {
        def userInstance = User.get( params.id )

        if(!userInstance) {
            flash.message = "User not found with id ${params.id}"
            redirect(action:list)
        } else { return [ userInstance : userInstance ] }
    }

    def delete = {
        def userInstance = User.get( params.id )
        if(userInstance) {
            try {
                userInstance.delete(flush:true)
                flash.message = "User ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "User ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        } else {
            flash.message = "User not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def userInstance = User.get( params.id )

        if(!userInstance) {
            flash.message = "User not found with id ${params.id}"
            redirect(action:list)
        } else {
            return [userInstance:userInstance]
        }
    }

    def update = {
        def userInstance = User.get( params.id )
        if(userInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(userInstance.version > version) {                   
                    userInstance.errors.rejectValue("version", "user.optimistic.locking.failure", "Another user has updated this User while you were editing.")
                    render(view:'edit',model:[userInstance:userInstance])
                    return
                }
            }
            userInstance.name = params.name
            userInstance.email = params.email
            if (params.newPassword != '' && User.findByLoginAndPassword(session.user.login, params.oldPassword.encodeAsHash()) != null && params.newPassword == params.confirm) {
                userInstance.password = params.newPassword.encodeAsHash()
                if(!userInstance.hasErrors() && userInstance.save()) {
                    flash.message = "User ${params.id} updated"
                    redirect(action:show,id:userInstance.id)
                } else {
                    render(view:'edit',model:[userInstance:userInstance])
                }
            } else if (params.newPassword != '' && User.findByLoginAndPassword(session.user.login, params.oldPassword.encodeAsHash()) == null) {
                flash.message = "The old password was incorrect.  Please try again."
                redirect(action:edit,id:userInstance.id)
            } else if (params.newPassword.toString() != '' && params.newPassword != params.confirm) {
                flash.message = "Passwords do not match.  Please try again."
                redirect(action:edit,id:userInstance.id)
            } else {
                redirect(action:edit,id:userInstance.id)
            }   
        } else {
            flash.message = "User not found with id ${params.id}"
            redirect(uri:"/")
        }
    }
    def create = {
        def userInstance = new User(params)
        return [userInstance:userInstance]
    }

    def save = {
        if (params.accept == null) {
            flash.message = "You must accept the EULA to create an account"
            redirect(action:create, params:params)
        }else if (params.password != params.confirm) {
            flash.message = "Passwords do not match"
            redirect(uri: "/user/create")
        } else {
            def userInstance = new User(params)
            userInstance.password = params.password.encodeAsHash()
            if(!userInstance.hasErrors() && userInstance.save()) {
                flash.message = "User ${userInstance.login} created"
                redirect(uri: "/user/login")
            } else {
                render(view:'create',model:[userInstance:userInstance])
            }
        }
    }

    def reset = {}

    def resetPass = {
        def userInstance = User.findByLoginAndEmail(params.login, params.email)
        if (userInstance != null) {
            def newPass = userInstance.resetPassword(8)
            if(!userInstance.hasErrors() && userInstance.save()) {
                sendMail {
                    to "${userInstance.email}"
                    subject "Routemap Password"
                    body "Your routemap password has been reset.  The new password is: ${newPass}\nLogin at routemap.osu.edu, and click 'Manage Account' in the upper right corner to change your password."
                }
                flash.message = "An email with the new password has been sent to ${userInstance.email}."
                redirect(action:login)
            }
        } else {
            flash.message = "No account was found with that login and email."
            redirect(action:login)
        }
    }
}
