package com.tgid.garmin.data

import groovy.transform.EqualsAndHashCode

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.NONE)
class Activity implements Serializable {

    static mapWith = "mongo"

    String id

//    static belongsTo = [upload: Upload]
    static hasMany = [laps: Lap]

    @XmlAttribute(name = "Sport", required = true)
    String sport

    @XmlElement(name = "Id")
    String activityId

    @XmlElement(name="Lap")
    List<Lap> laps = []

    static constraints = {
        sport nullable: true
    }

    static mapping = {
        laps lazy:  false
    }
//    static embedded = ['laps']
}
