class UrlMappings {
    static mappings = {
        "/publications" (controller: "extras", action: "publications")

        "/$controller/$action?/$id?"{
            constraints {
                            // apply constraints here
                        }
	}

        "/"(view:"/index")
            "500"(view:'/error')
    }
}
