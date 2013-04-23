package com.tgid.garmin

import com.tgid.garmin.data.Activity
import com.tgid.garmin.data.Upload
import grails.converters.JSON
import org.apache.commons.fileupload.FileUploadException
import org.json.simple.JSONObject
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.multipart.commons.CommonsMultipartFile
import java.rmi.activation.ActivationID

class FileUploadController {

    def fileProcessorService
    def grailsApplication

    static allowedMethods = [save: "POST", update: "POST", delete: "DELETE"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        //params.max = Math.min(max ?: 10, 100)
        println Upload.findAllByUuid(params.uuid).size()
        def jsonList = []
        def gpsfiles = [gpsfiles: Upload.findAllByUuid(params?.uuid ?: '-1').collect {
            getUploadJSON(it)
        }]
        render gpsfiles as JSON
    }

    def create() {
        [uploadInstance: new Upload(params)]
    }

    def save() {
        def uploadInstance = new Upload(params)
        if(!uploadInstance.save(flush: true)) {
            render(view: "create", model: [uploadInstance: uploadInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'upload.label', default: 'Upload'), uploadInstance.id])
        redirect(action: "show", id: uploadInstance.id)
    }

    def show(String id) {
        def uploadInstance = Upload.get(id)
        if(!uploadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'upload.label', default: 'Upload'), id])
            redirect(action: "list")
            return
        }

        [uploadInstance: uploadInstance]
    }

    def edit(String id) {
        def uploadInstance = Upload.get(id)
        if(!uploadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'upload.label', default: 'Upload'), id])
            redirect(action: "list")
            return
        }

        [uploadInstance: uploadInstance]
    }

    def update(String id, Long version) {
        def uploadInstance = Upload.get(id)
        if(!uploadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'upload.label', default: 'Upload'), id])
            redirect(action: "list")
            return
        }

        if(version != null) {
            if(uploadInstance.version > version) {
                uploadInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                                                  [message(code: 'upload.label', default: 'Upload')] as Object[],
                                                  "Another user has updated this Upload while you were editing")
                render(view: "edit", model: [uploadInstance: uploadInstance])
                return
            }
        }

        uploadInstance.properties = params

        if(!uploadInstance.save(flush: true)) {
            render(view: "edit", model: [uploadInstance: uploadInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'upload.label', default: 'Upload'), uploadInstance.id])
        redirect(action: "show", id: uploadInstance.id)
    }

    def delete() {
        def result = [message: '']
        println params.id
        println "in delete"
        def uploadInstance = Upload.get(params.id)
        try {
            if(uploadInstance) {
                uploadInstance.delete(flush: true)
                result = [message: 'deleted']
            }
        }
        catch(DataIntegrityViolationException e) {
            log.error e
        }
        render result as JSON
    }

    def upload() {
        switch(request.method) {
            case 'GET':
                break
            case 'POST':
                try {
                    def jsonList = []
                    println params.toString()
                    println params.uuid
                    params.list("files[]").each {CommonsMultipartFile file ->
                        if(file) {
                            if(file.originalFilename.toLowerCase().endsWith('tcx')) {
                                File myFile = new File(grailsApplication.config.garmin.basedir.toString(), file.originalFilename)
                                myFile.mkdirs()
                                file.transferTo(myFile)
                                def upload = fileProcessorService.uploadFile(myFile, params.uuid)
                                deleteFile(myFile)
                                jsonList << getUploadJSON(upload)
                            } else {
                                println file.originalFilename
                                throw new RuntimeException("File type ${file.originalFilename.tokenize('.').last()} not supported.");
                            }
                        }
                    }
                    log.debug jsonList as JSON
                    render(contentType: "text/json", text: jsonList as JSON)
                    return
                } catch(FileUploadException e) {
                    throw new RuntimeException(e);
                } catch(Exception e) {
                    throw new RuntimeException(e);
                }
        }
    }

    void deleteFile(File file) {
        try {
            file.delete()
        } catch(Exception ex) {
            log.error(ex)
        }
    }

    def split(){
        def type = params?.type ?: 'laps'
    }

    private getUploadJSON(Upload upload) {
        JSONObject json = new JSONObject()
        Activity activity = (Activity) upload.activities?.get(0)
        json.put("id", upload.id)
        json.put("name", activity?.activityId ?: '')
        json.put("sport", activity?.sport ?: 'Unknown')
        json.put("lapCount", activity?.laps?.size() ?: 0)
        json.put("totalTimeSeconds", activity?.laps?.totalTimeSeconds?.sum() ?: 0)
        json.put("distanceMeters", activity?.laps?.distanceMeters?.sum() ?: 0)
        json.put("maximumSpeed", activity?.laps?.maximumSpeed?.max() ?: 0)
        json.put("calories", activity?.laps?.calories?.sum() ?: 0)
        json.put("intensity", activity?.laps?.get(0)?.intensity ?: '')
        json
    }
}
