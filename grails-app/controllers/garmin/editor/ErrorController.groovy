package garmin.editor

import grails.converters.JSON
import javax.servlet.http.HttpServletRequest
import com.tgid.garmin.exceptions.GarminFileException

class ErrorController {

    static profiled = true
    def messageSource


    def handle = {
        log.error(request?.exception)
        def exception = request?.exception?.cause?.class
        def error = [exception: getExceptionResponse(request, exception)]
        render(contentType: "text/json", text: error as JSON)
    }

    private GarminFileException getExceptionResponse(HttpServletRequest request, Class exception) {
        log.error request?.exception

        new GarminFileException(message: request?.exception?.message)
    }

    def unsupported = {
        def error = [exception: new GarminFileException(message: "Unsupported Action")]
        render(contentType: "text/json", text: error as JSON)
    }
}
