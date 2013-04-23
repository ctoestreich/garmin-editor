package com.tgid.garmin.data

import groovy.transform.EqualsAndHashCode
import org.bson.types.ObjectId

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement

@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.NONE)
class Position implements Serializable {

    static belongsTo = [trackpoint: Trackpoint]
    static mapWith = "mongo"

    String id

    @XmlElement(name = "LatitudeDegrees")
    Float latitudeDegrees
    @XmlElement(name = "LongitudeDegrees")
    Float longitudeDegrees

    static constraints = {
    }


}
