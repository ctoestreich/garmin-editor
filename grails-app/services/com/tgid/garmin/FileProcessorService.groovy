package com.tgid.garmin

import groovy.util.slurpersupport.GPathResult

import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

import com.tgid.garmin.data.*

class FileProcessorService {

    def grailsApplication

    def uploadFile(File file, String uuid) {
        processXmlData(file.text, uuid)
    }

    def loadSampleData() {
        def files = new File('src/java/test')
        files.eachFile {
            def upload = processXmlData(it.text)
        }
    }

    List<File> splitByTimespan(Upload upload, Long minutes) {
        def uploads = []
        def lastTrackpointTime

        def newUpload = makeNewUpload(upload)
        if(upload.authors.size() > 0) {
            newUpload.addToAuthors(upload.authors?.get(0))
        }
        upload.activities.each { activity ->
            def newActivity = makeNewActivity(activity)
            newUpload.addToActivities(newActivity)
            activity.laps.each { lap ->
                def newLap = makeNewLap(lap)
                newActivity.addToLaps(newLap)
                lap.trackpoints.each { trackpoint ->
                    def newTrackpoint = makeNewTrackpoint(trackpoint)
                    Date trackpointTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ss'.'S'Z'", trackpoint.time)
                    def gapped = lastTrackpointTime && (((trackpointTime.time - lastTrackpointTime.time) / 1000) / 60) > minutes
                    if(lastTrackpointTime && gapped) {
                        uploads << newUpload
                        newUpload = makeNewUpload(upload)
                        if(upload.authors.size() > 0) {
                            newUpload.addToAuthors(upload.authors?.get(0))
                        }
                        newActivity = makeNewActivity(activity)
                        newUpload.addToActivities(newActivity)
                        newLap = makeNewLap(lap)
                        newActivity.addToLaps(newLap)
                    }

                    newLap.addToTrackpoints(newTrackpoint)
                    lastTrackpointTime = trackpointTime
                }
            }
        }

        if(newUpload) {
            uploads << newUpload
        }

        addUploadsToFileList(uploads)
    }

    Trackpoint makeNewTrackpoint(Trackpoint trackpoint) {
        Trackpoint newTrackpoint = new Trackpoint(altitudeMeters: trackpoint.altitudeMeters, distanceMeters: trackpoint.distanceMeters,
                                                  time: trackpoint.time)
        trackpoint.positions.each { position ->
            newTrackpoint.addToPositions(new Position(latitudeDegrees: position.latitudeDegrees, longitudeDegrees: position.longitudeDegrees))
        }
        trackpoint.tpxs.each {tpx ->
            newTrackpoint.addToTpxs(new Tpx(speed: tpx.speed))
        }
        newTrackpoint
    }

    Lap makeNewLap(Lap lap) {
        new Lap(calories: lap.calories, distanceMeters: lap.distanceMeters,
                intensity: lap.intensity, maximumSpeed: lap.maximumSpeed, startTime: lap.startTime,
                totalTimeSeconds: lap.totalTimeSeconds)
    }

    Activity makeNewActivity(Activity activity) {
        new Activity(sport: activity.sport, activityId: activity.activityId)
    }

    Upload makeNewUpload(Upload upload) {
        new Upload(uuid: upload.uuid)
    }

    List<File> splitByLaps(Upload upload) {
        List<Upload> uploads = []

        upload.activities.each { activity ->
            activity.laps.each {lap ->
                def newUpload = new Upload(id: upload.id, uuid: upload.uuid)
                def newActivity = new Activity(id: activity.id, sport: activity.sport, activityId: activity.activityId)
                newActivity.addToLaps(lap)
                newUpload.addToActivities(newActivity)
                if(upload.authors.size() > 0) {
                    newUpload.addToAuthors(upload.authors?.get(0))
                }
                fixSummaryData(newUpload)
                uploads << newUpload
            }
        }

        addUploadsToFileList(uploads)
    }

    private List<File> addUploadsToFileList(List<Upload> uploads) {
        def files = []
        uploads.eachWithIndex { newUpload, index ->
            new File(grailsApplication.config.garmin.download.toString()).mkdirs()
            def file = new File(grailsApplication.config.garmin.download.toString(), "${newUpload.uuid}_lap${index}.tcx")
            file.text = marshalToXmlString(newUpload)
            files << file
        }

        files
    }

    void fixSummaryData(Upload upload) {
        def times = []
        upload.activities.each {activity ->
            activity.laps.each { lap ->
                updateTotalTimeSeconds(lap)
                updateDistanceMeters(lap)
                updateMaximumSpeed(lap)
            }
        }
    }

    private void updateMaximumSpeed(Lap lap) {
        lap.maximumSpeed = (lap.trackpoints*.tpxs.collect { it.speed }?.flatten()?.max() ?: lap.maximumSpeed) as Float
    }

    private void updateDistanceMeters(Lap lap) {
        def distances = lap.trackpoints.collect { it.distanceMeters }.sort { it}
        lap.distanceMeters = (distances?.last() ?: 0) - (distances?.first() ?: 0)
    }

    private void updateTotalTimeSeconds(Lap lap) {
        def times = lap.trackpoints.collect { it.time }.sort { it }
        Date startDate = new Date().parse("yyyy-MM-dd'T'HH:mm:ss'.'S'Z'", times.first())
        Date endDate = new Date().parse("yyyy-MM-dd'T'HH:mm:ss'.'S'Z'", times.last())
        def diff = Math.abs(startDate.getTime() - endDate.getTime()) / 1000
        lap.totalTimeSeconds = diff
    }

    String marshalToXmlString(Upload upload) {
        StringWriter writer = new StringWriter()
        def context = JAXBContext.newInstance(Upload, Activity, Lap, Position, Tpx, Trackpoint);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal((Upload) upload, writer)
        addNamespaces(writer.toString())
    }

    String addNamespaces(String xml) {
        xml.replace("<TrainingCenterDatabase>", "<TrainingCenterDatabase xsi:schemaLocation=\"http://www.garmin.com/xmlschemas/TrainingCenterDatabase/v2 http://www.garmin.com/xmlschemas/TrainingCenterDatabasev2.xsd\"\n" +
                                                "  xmlns:ns5=\"http://www.garmin.com/xmlschemas/ActivityGoals/v1\"\n" +
                                                "  xmlns:ns3=\"http://www.garmin.com/xmlschemas/ActivityExtension/v2\"\n" +
                                                "  xmlns:ns2=\"http://www.garmin.com/xmlschemas/UserProfile/v2\"\n" +
                                                "  xmlns=\"http://www.garmin.com/xmlschemas/TrainingCenterDatabase/v2\"\n" +
                                                "  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ns4=\"http://www.garmin.com/xmlschemas/ProfileExtension/v1\">")
                .replace("<Author>", "<Author xsi:type=\"Application_t\">")
    }

    private Upload processXmlData(String xmlString, String uuid = 'ABC123XYZ789') {
        def xml = new XmlSlurper().parseText(xmlString)
        def upload = new Upload(uuid: uuid).save(flush: true, failOnError: true)

        addAuthorAndVersion(upload, xml)

        addActivities(upload, xml)

        upload.save(flush: true, failOnError: true)
    }

    private void addActivities(Upload upload, GPathResult xml) {
        xml.Activities.Activity.each {xmlActivity ->
            def activity = new Activity()
            activity.activityId = xmlActivity.Id
            activity.sport = xmlActivity.@Sport

//            upload.activities << activity
            upload.addToActivities(activity)

            addLaps(activity, xmlActivity)
        }
    }

    private void addLaps(Activity activity, GPathResult xmlActivity) {
        xmlActivity.Lap.each { xmlLap ->
            def lap = new Lap()

            lap.startTime = xmlLap.@StartTime
            lap.totalTimeSeconds = (xmlLap?.TotalTimeSeconds?.text() ?: 0) as Float
            lap.distanceMeters = (xmlLap?.DistanceMeters?.text() ?: 0) as Float
            lap.maximumSpeed = (xmlLap?.MaximumSpeed?.text() ?: 0) as Float
            lap.calories = (xmlLap?.Calories?.text() ?: 0) as Integer
            lap.intensity = xmlLap?.Intensity
            lap.triggerMethod = xmlLap?.TriggerMethod

            activity.addToLaps(lap)

            addTrackpoints(lap, xmlLap)
        }
    }

    private void addTrackpoints(Lap lap, GPathResult xmlLap) {
        xmlLap?.Track?.Trackpoint?.each { xmlTrackpoint ->
            Trackpoint trackpoint = new Trackpoint()

            trackpoint.time = xmlTrackpoint?.Time
            trackpoint.altitudeMeters = (xmlTrackpoint?.AltitudeMeters?.text() ?: 0) as Float
            trackpoint.distanceMeters = (xmlTrackpoint?.DistanceMeters?.text() ?: 0) as Float

            Position position = new Position()
            position.latitudeDegrees = (xmlTrackpoint?.Position?.LatitudeDegrees?.text() ?: 0) as Float
            position.longitudeDegrees = (xmlTrackpoint?.Position?.LongitudeDegrees?.text() ?: 0) as Float
            trackpoint.addToPositions(position)

            Tpx tpx = new Tpx()
            tpx.speed = (xmlTrackpoint?.Extensions?.TPX?.Speed?.text() ?: 0) as Float
            trackpoint.addToTpxs(tpx)

            lap.addToTrackpoints(trackpoint)
        }
    }

    private void addAuthorAndVersion(Upload upload, GPathResult xml) {
        def author = new Author()
        author.name = xml?.Author?.Name ?: ''
        author.langID = xml?.Author?.LangID ?: ''
        author.partNumber = xml?.Author?.PartNumber ?: ''

        def version = new Version()
        version.buildMajor = xml?.Author?.Build?.Version?.BuildMajor ?: 0
        version.buildMinor = xml?.Author?.Build?.Version?.BuildMinor ?: 0
        version.versionMajor = xml?.Author?.Build?.Version?.VersionMajor ?: 0
        version.versionMinor = xml?.Author?.Build?.Version?.VersionMinor ?: 0

        author.addToVersions(version)
        upload.addToAuthors(author)
    }
}
