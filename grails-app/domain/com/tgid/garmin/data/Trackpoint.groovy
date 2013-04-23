package com.tgid.garmin.data

import groovy.transform.EqualsAndHashCode

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper

@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.NONE)
class Trackpoint implements Serializable {

    static mapWith = "mongo"

    String id

    static belongsTo = [lap: Lap]
    static hasMany = [tpxs: Tpx, positions: Position]

    @XmlElement(name = "Time")
    String time
    @XmlElement(name = "AltitudeMeters")
    Float altitudeMeters = 0f
    @XmlElement(name = "DistanceMeters")
    Float distanceMeters = 0f

    @XmlElementWrapper(name = "Extensions")
    @XmlElement(name = "TPX")
    List<Tpx> tpxs = []

    @XmlElement(name = "Position")
    List<Position> positions = []

//    static embedded = ['tpxs', 'position']

    static constraints = {
        time blank: true, nullable: true
        positions nullable: true
        altitudeMetersnullable: true
        distanceMeters nullable: true
        time nullable: true
    }

    static mapping = {
        tpxs lazy: false
        positions lazy: false
    }
}
