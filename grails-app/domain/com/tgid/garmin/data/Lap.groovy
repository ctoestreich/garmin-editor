package com.tgid.garmin.data

import groovy.transform.EqualsAndHashCode

import javax.xml.bind.annotation.*

@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.NONE)
class Lap implements Serializable {

    static mapWith = "mongo"

    String id

//    static belongsTo = [activity: Activity]
    static hasMany = [trackpoints: Trackpoint]

    @XmlAttribute(name = "StartTime")
    String startTime
    @XmlElement(name = "TotalTimeSeconds")
    Float totalTimeSeconds = 0f
    @XmlElement(name = "DistanceMeters")
    Float distanceMeters = 0f
    @XmlElement(name = "MaximumSpeed")
    Float maximumSpeed = 0f
    @XmlElement(name = "Calories")
    Integer calories = 0
    @XmlElement(name = "Intensity")
    String intensity
    @XmlElement(name = "TriggerMethod")
    String triggerMethod

    @XmlElementWrapper(name = "Track")
    @XmlElement(name = "Trackpoint")
    List<Trackpoint> trackpoints = []

//    static embedded = ['trackpoints']

    static constraints = {
        startTime blank: true, nullable: true
        totalTimeSeconds nullable: true
        distanceMetersnullable: true
        maximumSpeed nullable: true
        calories nullable: true
        intensity blank: true, nullable: true
        triggerMethod blank: true, nullable: true
    }

    static mapping = {
        trackpoints lazy:  false
    }
}
