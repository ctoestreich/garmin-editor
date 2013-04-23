import grails.util.Environment
import com.mongodb.Mongo
import com.tgid.garmin.data.Upload
import com.gmongo.GMongo
import grails.converters.JSON
import grails.converters.XML
import com.tgid.garmin.exceptions.GarminFileException
import com.tgid.garmin.data.DatabasePurgeJob

class BootStrap {

    def fileProcessorService

    def init = { servletContext ->
        JSON.registerObjectMarshaller(GarminFileException) {
            def returnArray = [:]
            returnArray['message'] = it.message
            returnArray['description'] = it.description
            return returnArray
        }

//        Date.mixin = org.codehaus.groovy.runtime.TimeCategory

        XML.registerObjectMarshaller(new org.codehaus.groovy.grails.web.converters.marshaller.xml.InstanceMethodBasedMarshaller())

        environments{
            development{
                def mongo = new GMongo()
                def db = mongo.getDB('Garmin')
                db.dropDatabase()

                fileProcessorService.loadSampleData()
//                fileProcessorService.loadSampleData()

                DatabasePurgeJob.triggerNow()
            }
        }
    }
    def destroy = {
    }
}
