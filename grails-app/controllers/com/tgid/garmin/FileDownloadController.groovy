package com.tgid.garmin

import com.tgid.garmin.data.Position
import com.tgid.garmin.data.Upload
import grails.converters.JSON

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.servlet.http.Cookie

class FileDownloadController {

    def fileProcessorService

    def showpositions() {
        render Position.list() as JSON
    }

    def showxml() {
        def type = params?.type ?: 'laps'
        def upload = Upload.list()?.get(params.int('id', 0))
        def files = []
        if(upload) {
            files = fileProcessorService.splitByLaps(upload)
        }

        if(files) {
            sendDownloadRequest(files)
        } else {
            render "no files created"
        }
    }

    def split() {
        def type = params?.type ?: 'laps'
        def upload = Upload.findByUuidAndId(params?.uuid ?: '-1', params?.id ?: '-1')
        List<File> files = []
        if(upload) {
            switch(type) {
                case 'laps':
                    files = fileProcessorService.splitByLaps(upload)
                    break
                case 'timespan':
                    files = fileProcessorService.splitByTimespan(upload, params.long('timespan',60))
                        break
            }
        }

        if(files) {
            sendDownloadRequest(files)
        }
    }

    private sendDownloadRequest(List<File> files) {
        String zipFileName = "joined_file.zip"
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ZipOutputStream zipFile = new ZipOutputStream(baos)

        try {
            files.each { file ->
                zipFile.putNextEntry(new ZipEntry(file.name))
                file.withInputStream { i ->
                    zipFile << i
                }
                zipFile.closeEntry()
            }
            zipFile.finish()

            def cookie = new Cookie('fileDownload', "true")
            cookie.path = '/'
            cookie.maxAge = 1
            response.setContentType("application/zip") // or or image/JPEG or text/xml or whatever type the file is
            response.setHeader("Content-disposition", "attachment;filename=${zipFileName}")
            response.addCookie(cookie)
            response.outputStream << baos.toByteArray()
            response.outputStream.flush()
        } catch(Exception e) {
            log.error e
            def cookie = new Cookie('fileDownload', "true")
            cookie.path = '/'
            cookie.maxAge = 1 // 5 years in seconds response.addCookie( cookie)
            response.addCookie(cookie)
            response.outputStream.flush()
        }
    }
}
