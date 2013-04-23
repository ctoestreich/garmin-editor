package com.tgid.garmin.data



class DatabasePurgeJob {
    static triggers = {
        cron name: 'DatabasePurgeJobDailyTrigger', cronExpression: "0 0 0 * * ?"
    }

    def execute() {
        Upload.list().each {
            if(it.createDate < (new Date() - 1)){
                it.delete()
            }
        }
    }
}
