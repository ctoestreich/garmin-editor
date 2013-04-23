define([
           "app",
           "underscore",
           "backbone",
           "models/gpsfile"
       ],

       function (app, _, Backbone, GpsFile) {
           return Backbone.Collection.extend({

                                                 url:window.location.pathname + 'files/' + app.user.uuid,
                                                 model:GpsFile,
                                                 parse:function (response) {
                                                     return response.gpsfiles;
                                                 }
                                             });

       });
