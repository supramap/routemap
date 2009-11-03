import org.springframework.web.multipart.MultipartFile

class KmlController {

    def beforeInterceptor = [action:this.&auth, except:["download"]]

    def auth() {
        if(!session.user) {
            redirect(controller:"user", action:"login")
            return false
        }
    }
    
    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']
    
    def index = { redirect(action:list,params:params) }

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
        } else if (session.user?.ownsKml(kmlInstance)){
            //kmlInstance.writeTemp()
            return [ kmlInstance : kmlInstance ]
        } else {
            response.sendError(403)
        }
    }

    def delete = {
        def kmlInstance = Kml.get( params.id )
        if(kmlInstance) {
            if (session.user?.ownsKml(kmlInstance)) {
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
        } else if (session.user?.ownsKml(kmlInstance)){
            return [ kmlInstance : kmlInstance ]
        } else {
            response.sendError(403)
        }
    }

    def update = {
        def kmlInstance = Kml.get( params.id )
        if(kmlInstance) {
            if (session.user?.ownsKml(kmlInstance)) {
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

    def create = {redirect action:step1}

    def step1 = {}

    def generateScript = {
        session.folder = KmlService.tempFolder("${session.folder}")

        MultipartFile sequences = request.getFile('sequences')
        MultipartFile coordinates = request.getFile('coordinates')

        if (coordinates.empty || sequences.empty) {
            flash.message = "Files cannot be empty"
            redirect action:step1
        } else if (params.treeFile == "on" && params.treeName == "") {
            flash.message = "You must specify the name of the tree file"
            redirect action:step1
        } else if (params.save == "on" && params.saveName == "") {
            flash.message = "You must specify the name of the save file"
            redirect action:step1
        } else {
            sequences.transferTo(new File("${session.folder}/seqs"))
            coordinates.transferTo(new File("${session.folder}/coords"))
            def problems = KmlService.checkFiles(session.folder, params.outgroup)
            if (problems) {
                flash.problems = problems
                redirect action:step1
            } else {
                KmlService.writeScript(session.folder, params)
                redirect action:step2
            }
        }
    }

    def step2 = {}
    
    def getLog = {
        MultipartFile migrations = request.getFile('tntlog')
        
        if (!migrations.empty) {
            migrations.transferTo(new File("${session.folder}/tntlog"))
            KmlService.writeInputs(session.folder)
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

        if (params.kmlStyle == "New")
            KmlService.writeKml("${session.folder}")
        else if (params.kmlStyle == "Old")
            AltBuildService.writeKml("${session.folder}")

        def transmissions = new File("${session.folder}/transmissions.kml")
        def sequences = new File("${session.folder}/seqs")
        def coordinates = new File("${session.folder}/coords")
        kmlInstance.kml = transmissions.readBytes()
        kmlInstance.seqs = sequences.readBytes()
        kmlInstance.coords = coordinates.readBytes()

        if(!kmlInstance.hasErrors() && kmlInstance.save()) {
            flash.message = "Kml ${kmlInstance.id} created"
            redirect(action:show,id:kmlInstance.id)
        }
        else {
            redirect(action:create,model:[kmlInstance:kmlInstance])
        }

        new AntBuilder().delete(dir: "${session.folder}") //remove temp directory
    }

    def download = {
        def download
        String name, content
        byte[] data
   
        if (params.file == 'kml' || params.file == 'seqs' || params.file == 'coords') {
            download = Kml.get(params.id)
            if (session.user?.ownsKml(download)) {
                name = download.name.replaceAll("\\s+","_")+params.name
                content = params.content
                if (params.file == 'kml') {
                    data = download.kml
                } else if (params.file == 'seqs') {
                    data = download.seqs
                } else if (params.file == 'coords') {
                    data = download.coords
                }
            } else {
                response.sendError(403)
            }
        } else { //If the tntscript
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
