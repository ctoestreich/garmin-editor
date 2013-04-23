package com.tgid.garmin.security

class User {

    transient springSecurityService

    String username
    String firstName
    String lastName
    String uuid
    String password
    boolean enabled = true
    boolean accountExpired = false
    boolean accountLocked = false
    boolean passwordExpired = false

    static constraints = {
        username blank: false, unique: true, email: true
        password blank: false
        uuid blank:  false
        firstName blank: false
        lastName blank: false
    }

    static mapping = {
        password column: '`password`'
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if(isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }
}
