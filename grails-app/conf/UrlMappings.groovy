class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        "/files/$uuid?/$id?" {
            controller = 'fileUpload'
            action = [GET: "list", POST: "save", DELETE: "delete", PUT: "edit"]
        }

        "/download/$type/$uuid/$id/$timespan?" {
            controller = 'fileDownload'
            action = [GET: "split"]
        }

        "/"(view: "/index")

        "500"(controller: "error", action: "handle")
        "404"(controller: "error", action: "unsupported")


    }
}
