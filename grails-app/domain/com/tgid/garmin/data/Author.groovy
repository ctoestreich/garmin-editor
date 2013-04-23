package com.tgid.garmin.data

import groovy.transform.EqualsAndHashCode

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper

/*
<Author xsi:type="Application_t">
    <Name>Garmin Connect API</Name>
    <Build>
      <Version>
        <VersionMajor>13</VersionMajor>
        <VersionMinor>3</VersionMinor>
        <BuildMajor>0</BuildMajor>
        <BuildMinor>0</BuildMinor>
      </Version>
    </Build>
    <LangID>en</LangID>
    <PartNumber>006-D2449-00</PartNumber>
  </Author>
 */
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.NONE)
class Author {

    static mapWith = "mongo"

    static belongsTo = [upload: Upload]
    static hasMany = [versions: Version]

    String id

    @XmlElement(name = 'Name')
    String name
    @XmlElement(name = 'LangID')
    String langID
    @XmlElement(name = 'PartNumber')
    String partNumber

    @XmlElementWrapper(name = "Build")
    @XmlElement(name = "Version")
    List<Version> versions = []

    static constraints = {
    }
}
