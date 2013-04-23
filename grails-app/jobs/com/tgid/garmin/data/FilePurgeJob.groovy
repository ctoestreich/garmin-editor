package com.tgid.garmin.data



class FilePurgeJob {

    def grailsApplication

    static triggers = {
        cron name: 'FilePurgeJobDailyTrigger', cronExpression: "0 0 1 * * ?"
    }

    def execute() {
        new File(grailsApplication.config.garmin.basedir.toString()).eachFile { File file ->
            if (file.lastModified() < (new Date() -1 ).time){
                file.delete()
            }
        }

        new File(grailsApplication.config.garmin.download.toString()).eachFile { File file ->
            if (file.lastModified() < (new Date() -1 ).time){
                file.delete()
            }
        }
    }
}
