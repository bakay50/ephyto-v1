package com.webbfontaine

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }
        "/"(controller: 'ephytoGen', action: 'list')
        "500"(view:'/error')
        "404"(view:'/notFound')
        "/access-denied"(view:'/access-denied')
    }
}
