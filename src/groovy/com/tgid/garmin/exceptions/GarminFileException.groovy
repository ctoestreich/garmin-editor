package com.tgid.garmin.exceptions

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute

@XmlAccessorType(XmlAccessType.NONE)
class GarminFileException extends RuntimeException implements Serializable {

    @XmlAttribute
    String message
    @XmlAttribute
    String description

    def toXML(xml) {
        xml.build {
            message(message)
            description(description)
        }
    }
}
