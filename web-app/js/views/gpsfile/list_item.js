/**
 * Typeface List Item View
 */
define([
           "jquery",
           "underscore",
           "backbone",
           "marionette",
           "text!templates/gpsfile/list_item.tmpl",
           "views/helpers/viewHelpers",
           "plugins/bootbox.min"
       ],

       function ($, _, Backbone, Marionette, GpsFileListItemTemplate, viewHelpers) {

           return Marionette.ItemView.extend({

                                                 tagName:'li',
                                                 className:'file-list-item',
                                                 template:_.template(GpsFileListItemTemplate),
                                                 templateHelpers:viewHelpers,
                                                 ui:{
                                                 },

                                                 events:{
                                                     'click .delete-file':'deleteFile',
                                                     'click .splitByLap':'downloadSplitByLap',
                                                     'click .splitByTime':'downloadSplitByTimespan'
                                                 },

                                                 modelEvents:{
                                                     'change':'render',
                                                     'add':'render',
                                                     'destroy':'render'
                                                 },

                                                 initialize:function () {
                                                 },

                                                 onRender:function () {
                                                     //app.loadFile(this);
                                                 },

                                                 deleteFile:function () {
                                                     var that = this;
                                                     bootbox.dialog("Are you sure you want to delete the file?", [{
                                                         "label" : "Do Not Delete",
                                                         "class" : "btn-info"
                                                     }, {
                                                         "label" : "Delete",
                                                         "class" : "btn-danger",
                                                         "callback": function() {
                                                             that.model.destroy();
                                                         }
                                                     }]);
                                                 },

                                                 downloadSplitByTimespan: function(){
                                                     var that = this;
                                                     bootbox.prompt("Split where data is greather than x min diff", function(result){
                                                         if(result && !isNaN(result) && result > 0){
                                                             that.download('/garmin-editor/download/timespan/' + Garmin.user.uuid + '/' + that.model.get('id') + '/' + result);
                                                         }
                                                     });
                                                 },
                                                 downloadSplitByLap:function () {
                                                     var that = this;
                                                     that.download('/garmin-editor/download/laps/' + Garmin.user.uuid + '/' + that.model.get('id'));
                                                 },
                                                 download:function (url) {
                                                     var downloadingModal = $('#downloadingFileModal');
                                                     downloadingModal.modal('show');
                                                     $.fileDownload(url, {
                                                         successCallback:function (url) {
                                                             downloadingModal.modal('hide');
                                                         },
                                                         failCallback:function (html, url) {
                                                             downloadingModal.modal('hide');
                                                         }
                                                     });
                                                 },

                                                 startLoading:function () {
                                                     this.$el.addClass('loading');
                                                 },

                                                 finishLoading:function () {
                                                     this.$el.removeClass('loading');
                                                 }
                                             });
       });
