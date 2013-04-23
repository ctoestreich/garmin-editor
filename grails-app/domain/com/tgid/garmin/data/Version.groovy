package com.tgid.garmin.data

import javax.xml.bind.annotation.XmlElement
import groovy.transform.EqualsAndHashCode
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAccessType

/*
<VersionMajor>13</VersionMajor>
        <VersionMinor>3</VersionMinor>
        <BuildMajor>0</BuildMajor>
        <BuildMinor>0</BuildMinor>
 */
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.NONE)
class Version {

    static mapWith = "mongo"
    static belongsTo = [author: Author]

    String id

    @XmlElement(name='VersionMajor')
    String versionMajor = 0
    @XmlElement(name='VersionMinor')
    String versionMinor = 0
    @XmlElement(name='BuildMajor')
    String buildMajor = 0
    @XmlElement(name='BuildMinor')
    String buildMinor = 0


    static constraints = {
    }
}
