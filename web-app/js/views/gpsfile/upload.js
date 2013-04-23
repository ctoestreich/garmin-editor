define([
           "jquery",
           "underscore",
           "backbone",
           "marionette",
           "text!templates/gpsfile/upload.tmpl",
           "app",
           "models/gpsfile",
           "libs/jquery.ui.widget",
           "plugins/jquery.iframe-transport",
           "plugins/jquery.fileupload"

       ],

       function ($, _, Backbone, Marionette, UploadTemplate, app, GpsFile) {

           return Marionette.ItemView.extend({
                                                 template:_.template(UploadTemplate),
                                                 setStatusMessage:function (message, fadeMessage) {
                                                     $('#filestatus').html(message);
                                                     if(fadeMessage) {
                                                         $('#filestatus').fadeOut(400, function () {
                                                             $('#filestatus').html().show();
                                                         });
                                                     }
                                                 },
                                                 setModelProperties: function(model, file){
                                                     model.set('id', file.id);
                                                     model.set('name', file.name);
                                                     model.set('sport', file.sport);
                                                     model.set('lapCount', file.lapCount);
                                                     model.set('totalTimeSeconds', file.totalTimeSeconds);
                                                     model.set('distanceMeters', file.distanceMeters);
                                                     model.set('maximumSpeed', file.maximumSpeed);
                                                     model.set('calories', file.calories);
                                                     model.set('intensity', file.intensity);
                                                 },
                                                 wireFileupload:function () {
                                                     var that = this;
                                                     $('#fileupload').fileupload({
                                                                                     url:'fileUpload/upload',
                                                                                     dataType:'json',
                                                                                     add:function (e, data) {
                                                                                         data.formData = {uuid: app.user.uuid};
                                                                                         $('#progress .bar').css('width','0%');
                                                                                         $('#progress').removeClass('progress-success');
                                                                                         that.setStatusMessage('Uploading File... ');
                                                                                         data.submit();
                                                                                     },
                                                                                     error:function (e, data) {
                                                                                         if(e && e.responseText) {
                                                                                             var json = JSON.parse(e.responseText);
                                                                                             if(json && json.exception && json.exception.message) {
                                                                                                 that.setStatusMessage('Error: ' + json.exception.message);
                                                                                             }
                                                                                         }
                                                                                     },
                                                                                     done:function (e, data) {
                                                                                         var json = JSON.parse(data.jqXHR.responseText);
                                                                                         $.each(json, function (index, file) {
//                                                                                             console.log(file.id);
                                                                                             var model = new GpsFile();
                                                                                             that.setModelProperties(model, file);
                                                                                             app.files.add(model);
                                                                                             that.setStatusMessage('Successfully uploaded ' + file.name);
                                                                                         });
                                                                                         $('#progress .bar').css('width','100%');
                                                                                         $('#progress').addClass('progress-success');
                                                                                     },
                                                                                     progressall:function (e, data) {
                                                                                         var progress = parseInt(data.loaded / data.total * 100, 10);
                                                                                         $('#progress .bar').css('width',progress + '%');
                                                                                     }
                                                                                 });
                                                 }
                                             })
       });