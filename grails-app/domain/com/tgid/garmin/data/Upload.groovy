package com.tgid.garmin.data

import groovy.transform.EqualsAndHashCode

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlElementWrapper
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlNs
import javax.xml.bind.annotation.XmlSchema

@EqualsAndHashCode
@XmlRootElement(name="TrainingCenterDatabase")
@XmlAccessorType(XmlAccessType.NONE)
class Upload implements Serializable {

    static mapWith = "mongo"

    static hasMany = [activities: Activity, authors: Author]

    String id
    //replace with user eventually
    String uuid

    @XmlElementWrapper(name = "Activities")
    @XmlElement(name = "Activity")
    List<Activity> activities = []

    @XmlElement(name = "Author")
    List<Author> authors = []

//    static embedded = ['activities']

    Date createDate = new Date()

    static mapping = {
        autoTimestamp false
        activities lazy: false
        authors lazy: false
    }
}
