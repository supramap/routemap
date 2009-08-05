import org.springframework.web.multipart.MultipartFile

class KmlController {

    def beforeInterceptor = [action:this.&auth]

    def auth() {
        if(!session.user) {
            redirect(controller:"user", action:"login")
            return false
        }
    }

    def ownsKml(kmlInstance) { //Checks to see if the current user owns the argument kml
        def owner = kmlInstance.user.id
        def getter = session.user.id
        def role = session.user.role
        if (owner != getter && role != "admin") {
            return false
        }
        else {
            return true
        }
    }
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        def kmls, count
        if (session.user.role == "admin") {
            kmls = Kml.list()
            count = Kml.count()
        } else {
            kmls = Kml.findAllByUser(session.user)
            count = Kml.countByUser(session.user)
        }
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ kmlInstanceList: kmls, kmlInstanceTotal: count ]
    }

    def show = {
        def kmlInstance = Kml.get( params.id )
        if(!kmlInstance) {
            flash.message = "Kml not found with id ${params.id}"
            redirect action:list
        } else if (!ownsKml(kmlInstance)){
            response.sendError(403)
        } else {
            return [ kmlInstance : kmlInstance ]
        }
    }

    def delete = {
        def kmlInstance = Kml.get( params.id )
        if(kmlInstance) {
            if (ownsKml(kmlInstance)) {
                try {
                    kmlInstance.delete(flush:true)
                    flash.message = "Kml ${params.id} deleted"
                    redirect action:list
                }
                catch(org.springframework.dao.DataIntegrityViolationException e) {
                    flash.message = "Kml ${params.id} could not be deleted"
                    redirect(action:show,id:params.id)
                }
            }
            else {
                response.sendError(403)
            }
        }
        else {
            flash.message = "Kml not found with id ${params.id}"
            redirect action:list
        }
    }

    def edit = {
        def kmlInstance = Kml.get( params.id )

        if(!kmlInstance) {
            flash.message = "Kml not found with id ${params.id}"
            redirect action:list
        } else if (!ownsKml(kmlInstance)) {
            response.sendError(403)
        }
        else {
            return [ kmlInstance : kmlInstance ]
        }
    }

    def update = {
        def kmlInstance = Kml.get( params.id )
        if(kmlInstance) {
            if (ownsKml(kmlInstance)) {
                if(params.version) {
                    def version = params.version.toLong()
                    if(kmlInstance.version > version) {

                        kmlInstance.errors.rejectValue("version", "kml.optimistic.locking.failure", "Another user has updated this Kml while you were editing.")
                        render(view:'edit',model:[kmlInstance:kmlInstance])
                        return
                    }
                }
            } else {
                response.sendError(403)
            }
            kmlInstance.properties = params
            if(!kmlInstance.hasErrors() && kmlInstance.save()) {
                flash.message = "Kml ${params.id} updated"
                redirect(action:show,id:kmlInstance.id)
            }
            else {
                render(view:'edit',model:[kmlInstance:kmlInstance])
            }
        }
        else {
            flash.message = "Kml not found with id ${params.id}"
            redirect action:list
        }
    }

    def create = {redirect action:preparation}

    def preparation = {}

    def step1 = {}

    def generateScript = {
        session.folder = KmlService.tempFolder("${session.folder}")

        MultipartFile data = request.getFile('data')
        MultipartFile coordinates = request.getFile('coordinates')

        if (!coordinates.empty || !data.empty) {
            data.transferTo(new File("${session.folder}/data"))
            coordinates.transferTo(new File("${session.folder}/coordinates"))
            def problems = KmlService.checkFiles(session.folder)
            if (problems) {
                flash.problems = problems
                redirect action:step1
            } else {
                KmlService.writeScript(session.folder)
                redirect action:step2
            }
        }
        else {
            flash.message = "Files cannot be empty"
            redirect action:step1
        }
    }

    def step2 = {}
    
    def getLog = {
        MultipartFile migrations = request.getFile('tntlog')
        
        if (!migrations.empty) {
            migrations.transferTo(new File("${session.folder}/tntlog"))
            redirect action:step3
        } else {
            flash.message = "File cannot be empty"
            redirect action:step2
        }
    }

    def step3 = {}

    def save = {
        def kmlInstance = new Kml();
        kmlInstance.name = params.name
        kmlInstance.description = params.description
        kmlInstance.user = session.user

        def error = BuildKMLService.convert("${session.folder}/migrations", "${session.folder}/coordinates", session.folder)
        if (error != null) {
            flash.message = "${error}"
            redirect(action:create,model:[kmlInstance:kmlInstance])
        }

        def transmissions = new File("${session.folder}/transmissions.kml")
        kmlInstance.data = transmissions.readBytes()

        if(!kmlInstance.hasErrors() && kmlInstance.save()) {
            flash.message = "Kml ${kmlInstance.id} created"
            redirect(action:show,id:kmlInstance.id)
        }
        else {
            redirect(action:create,model:[kmlInstance:kmlInstance])
        }

        new AntBuilder().delete(dir: "${session.folder}") //remove temp directory
    }

    def altCreate = {}

    def presave = {
        session.folder = KmlService.tempFolder("${session.folder}")

        MultipartFile coordinates = request.getFile('coordinates')
        MultipartFile migrations = request.getFile('migrations')

        if (!coordinates.empty && !migrations.empty) {
            coordinates.transferTo(new File("${session.folder}/coordinates"))
            migrations.transferTo(new File("${session.folder}/migrations"))
        }
        else {
            flash.message = "Coordinates and migrations cannot be empty"
            redirect action:altCreate
        }

        def problems = KmlService.altCheckFiles("${session.folder}/migrations", "${session.folder}/coordinates")
        if (problems) {
            flash.problems = problems
            redirect action:altCreate
        }
        else
            save()
    }

    def download = {
        String name, content
        def download
        byte[] data

        if (params.file ==  null) { //If the file is a kmlInstance
            download = Kml.get(params.id)
            if (ownsKml(download)) {
                name = download.name.replaceAll("\\s+","_")+".kml"
                content = "application/vnd.google-earth.kml+xml"
                data = download.data
            }
            else {
                response.sendError(403)
            }
        }
        else { //If the file is something else
            download = new File("${session.folder}/${params.file}")
            name = download.getName()
            content = params.content           
            data = download.readBytes()
        }
        
        response.setContentType("${content}")
        response.setHeader("Content-disposition", "attachment;filename=${name}")
        response.outputStream << new ByteArrayInputStream(data)
    }
}
