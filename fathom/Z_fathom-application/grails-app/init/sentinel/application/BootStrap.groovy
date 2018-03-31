package sentinel.application

import grails.converters.JSON

class BootStrap {

    def init = { servletContext ->
        JSON.registerObjectMarshaller(Enum) { Enum e ->
            e.name()
        }
    }
    def destroy = {
    }
}
