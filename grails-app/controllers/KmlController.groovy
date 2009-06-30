import org.springframework.web.multipart.MultipartFile

class KmlController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ kmlInstanceList: Kml.list( params ), kmlInstanceTotal: Kml.count() ]
    }

    def show = {
        def kmlInstance = Kml.get( params.id )

        if(!kmlInstance) {
            flash.message = "Kml not found with id ${params.id}"
            redirect action:list
        }
        else { return [ kmlInstance : kmlInstance ] }
    }

    def delete = {
        def kmlInstance = Kml.get( params.id )
        if(kmlInstance) {
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
            flash.message = "Kml not found with id ${params.id}"
            redirect action:list
        }
    }

    def edit = {
        def kmlInstance = Kml.get( params.id )

        if(!kmlInstance) {
            flash.message = "Kml not found with id ${params.id}"
            redirect action:list
        }
        else {
            return [ kmlInstance : kmlInstance ]
        }
    }

    def update = {
        def kmlInstance = Kml.get( params.id )
        if(kmlInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(kmlInstance.version > version) {
                    
                    kmlInstance.errors.rejectValue("version", "kml.optimistic.locking.failure", "Another user has updated this Kml while you were editing.")
                    render(view:'edit',model:[kmlInstance:kmlInstance])
                    return
                }
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

    def step2 = {}

    def generateScript = {
        session.folder = KmlService.tempFolder("${session.folder}")

        MultipartFile coordinates = request.getFile('coordinates')

        if (!coordinates.empty) {
            coordinates.transferTo(new File("/tmp/routemap/${session.folder}/coordinates"))
            def problems = StatePairsService.convert("${session.folder}")
            if (problems) {
                flash.problems = problems
                redirect action:step2
            }
            def script = new File("/tmp/routemap/${session.folder}/transmissions.script")
            redirect action:step3
        }
        else {
            flash.message = "File cannot be empty"
            redirect action:step2
        }
    }

    def step3 = {}

    def step4 = {}
    
    def getMigrations = {
        MultipartFile migrations = request.getFile('migrations')
        
        if (!migrations.empty) {
            migrations.transferTo(new File("/tmp/routemap/${session.folder}/migrations"))
            redirect action:step5
        }
        else {
            flash.message = "File cannot be empty"
            redirect action:step4
        }

        def problems = SanityChecksService.checkCSV("/tmp/routemap/${session.folder}/migrations", "/tmp/routemap/${session.folder}/coordinates")
        if (problems) {
            flash.problems = problems
            redirect action:step4
        }
    }

    def step5 = {}

    def save = {
        def kmlInstance = new Kml();
        kmlInstance.name = params.name
        kmlInstance.description = params.description

        def error = BuildKMLService.convert("/tmp/routemap/${session.folder}/migrations", "/tmp/routemap/${session.folder}/coordinates", session.folder)
        if (error != null) {
            flash.message = "${error}"
            redirect(action:create,model:[kmlInstance:kmlInstance])
        }

        def transmissions = new File("/tmp/routemap/${session.folder}/transmissions.kml")
        kmlInstance.data = transmissions.readBytes()

        if(!kmlInstance.hasErrors() && kmlInstance.save()) {
            flash.message = "Kml ${kmlInstance.id} created"
            redirect(action:show,id:kmlInstance.id)
        }
        else {
            redirect(action:create,model:[kmlInstance:kmlInstance])
        }

        new AntBuilder().delete(dir: "/tmp/routemap/${session.folder}") //remove temp directory
    }

    def altCreate = {}

    def presave = {
        session.folder = KmlService.tempFolder("${session.folder}")

        MultipartFile coordinates = request.getFile('coordinates')
        MultipartFile migrations = request.getFile('migrations')

        if (!coordinates.empty && !migrations.empty) {
            coordinates.transferTo(new File("/tmp/routemap/${session.folder}/coordinates"))
            migrations.transferTo(new File("/tmp/routemap/${session.folder}/migrations"))
        }
        else {
            flash.message = "Coordinates and migrations cannot be empty"
            redirect action:altCreate
        }

        def problems = SanityChecksService.checkCSV("/tmp/routemap/${session.folder}/migrations", "/tmp/routemap/${session.folder}/coordinates")
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

        if (params.static ==  null) { //If the file is a kmlInstance
            download = Kml.get(params.id)
            name = download.name.replaceAll("\\s+","_")+".kml"
            content = "application/vnd.google-earth.kml+xml"
            data = download.data
        }
        else { //If the file is something else
            if (params.static == 'true') { //If the file was not produced by user input, the file is found in (grails-app)/web-app/files/
                def absPath = grailsAttributes.getApplicationContext().getResource("files/").getFile().toString() + File.separatorChar + params.file
                download = new File(absPath)
            }
            else //Otherwise the file is found in the temporary session.folder
                download = new File("/tmp/routemap/${session.folder}/${params.file}")
            name = download.getName()
            content = params.content           
            data = download.readBytes()
        }

        response.setContentType("${content}")
        response.setHeader("Content-disposition", "attachment;filename=${name}")
        response.outputStream << new ByteArrayInputStream(data)
    }
}
