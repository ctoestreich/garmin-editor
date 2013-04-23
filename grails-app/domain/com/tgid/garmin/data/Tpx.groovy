package com.tgid.garmin.data

import groovy.transform.EqualsAndHashCode
import org.bson.types.ObjectId

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlNs

@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.NONE)
class Tpx implements Serializable {

    static mapWith = "mongo"

//    static belongsTo = [trackpoint: Trackpoint]

    String id

    @XmlElement(name = "Speed")
    Float speed

    static constraints = {
        speed nullable: true
    }
}
