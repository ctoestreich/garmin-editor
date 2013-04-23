define([
           // Libraries.
           "jquery",
           "underscore",
           "backbone",
           "marionette",
           "models/user_input",
           "plugins/jquery.cookie",
           "libs/jquery.ui.widget"
       ],

       function ($, _, Backbone, Marionette, UserInput) {
           if(!window.Garmin) {
               var app = new Marionette.Application();
               app.user = new UserInput();
               app.addRegions({
                                  head:'#before',
                                  content:'#content',
                                  foot:'#after'
                              });

               app.createGuid = function () {
                   return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                       var r = Math.random() * 16 | 0, v = c == 'x' ? r : r & 0x3 | 0x8;
                       return v.toString(16);
                   });
               };

               var uuid = $.cookie('GarminUserUUID');
               app.user.uuid = uuid || app.createGuid();
               $.cookie('GarminUserUUID', app.user.uuid, { expires:10});
               window.Garmin = app;
           }
           return window.Garmin;

       });
