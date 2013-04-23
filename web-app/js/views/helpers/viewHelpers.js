define(function () {
    return {
        secondsToTime:function (secs) {
            var t = new Date(1970, 0, 1);
            t.setSeconds(secs);
            var s = t.toTimeString().substr(0, 8);
            if(secs > 86399) {
                s = Math.floor((t - Date.parse("1/1/70")) / 3600000) + s.substr(2);
            }
            return s;
        },
        formatDateFromSeconds:function (formatString, seconds) {
            return this.formatDate(formatString, new Date(seconds));
        },
        formatDate:function (formatString, date) {
            var DateToFormat = (typeof date == 'undefined') ? new Date() : date;
            var DAY = DateToFormat.getDate();
            var DAYidx = DateToFormat.getDay();
            var MONTH = DateToFormat.getMonth() + 1;
            var MONTHidx = DateToFormat.getMonth();
            var YEAR = DateToFormat.getYear();
            var FULL_YEAR = DateToFormat.getFullYear();
            var HOUR = DateToFormat.getHours();
            var MINUTES = DateToFormat.getMinutes();
            var SECONDS = DateToFormat.getSeconds();

            var arrMonths = new Array("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
            var arrDay = new Array('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday');
            var strMONTH;
            var strDAY;
            var strHOUR;
            var strMINUTES;
            var strSECONDS;
            var Separator;

            if(parseInt(MONTH) < 10 && MONTH.toString().length < 2) {
                strMONTH = "0" + MONTH;
            }
            else {
                strMONTH = MONTH;
            }
            if(parseInt(DAY) < 10 && DAY.toString().length < 2) {
                strDAY = "0" + DAY;
            }
            else {
                strDAY = DAY;
            }
            if(parseInt(HOUR) < 10 && HOUR.toString().length < 2) {
                strHOUR = "0" + HOUR;
            }
            else {
                strHOUR = HOUR;
            }
            if(parseInt(MINUTES) < 10 && MINUTES.toString().length < 2) {
                strMINUTES = "0" + MINUTES;
            }
            else {
                strMINUTES = MINUTES;
            }
            if(parseInt(SECONDS) < 10 && SECONDS.toString().length < 2) {
                strSECONDS = "0" + SECONDS;
            }
            else {
                strSECONDS = SECONDS;
            }

            switch(formatString) {
                case "hh:mm:ss":
                    return strHOUR + ':' + strMINUTES + ':' + strSECONDS;
                    break;
                //More cases to meet your requirements.
            }
        }
    }
});