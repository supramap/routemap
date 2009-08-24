

class UserController {

    def beforeInterceptor = [action:this.&auth, except:["login", "authenticate", "logout", "create", "save"]]

    def auth() {
        if( !(session?.user?.role == "admin") ){
            flash.message = "You must be an administrator to perform that task."
            redirect(action:"login")
            return false
        }
    }

    def login = {}

    def authenticate = {
        def user = User.findByLoginAndPassword(params.login, params.password.encodeAsHash())
        if(user){
          session.user = user
          flash.message = "Hello ${user.name}!"
          redirect(uri: "/")
        }else {
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
        }
        else { return [ userInstance : userInstance ] }
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
        }
        else {
            flash.message = "User not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def userInstance = User.get( params.id )

        if(!userInstance) {
            flash.message = "User not found with id ${params.id}"
            redirect(action:list)
        }
        else {
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
            userInstance.properties = params
            userInstance.password = params.password.encodeAsHash()
            if(!userInstance.hasErrors() && userInstance.save()) {
                flash.message = "User ${params.id} updated"
                redirect(action:show,id:userInstance.id)
            }
            else {
                render(view:'edit',model:[userInstance:userInstance])
            }
        }
        else {
            flash.message = "User not found with id ${params.id}"
            redirect(action:list)
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
}
