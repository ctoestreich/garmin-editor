/**
 * Typeface List View
 */
define([
           "jquery",
           "underscore",
           "backbone",
           "marionette",
           "models/user_input",
           "text!templates/gpsfile/list.tmpl",
           "views/gpsfile/list_item",
           "app",
           "plugins/select2",
           "plugins/bootstrap.tooltip"
       ],

       function ($, _, Backbone, Marionette, UserInput, GpsFileListTemplate, GpsFileListItemView, app, Select2) {

           return Marionette.CompositeView.extend({

                                                                      className:'gpsfile-list',
                                                                      template:_.template(GpsFileListTemplate),

                                                                      itemView:GpsFileListItemView,
                                                                      itemViewContainer:'#file-list',

                                                                      ui:{
                                                                          nav:'#nav'
                                                                      },

                                                                      events:{
//                                                                          'click #nav a':'goToPage'
                                                                      },

                                                                      modelEvents:{
                                                                          'change':'updateSettings'
                                                                      },

                                                                      initialize:function () {
                                                                          this.collection.fetch();
                                                                      },

                                                                      updateSettings:function (model, attrs) {
                                                                          // !TODO: I shouldn't have to force a re-render of the
                                                                          // entire CompositeView
                                                                          this.render();
                                                                      },

                                                                      onRender:function () {
//                                                                          this.buildNavigation();
                                                                      }

                                                                      /**
                                                                       * Build the navigation view
                                                                       */
//                                                                      buildNavigation:function () {
//                                                                          var length = this.collection.totalPages,
//                                                                                  current = this.collection.currentPage;
//                                                                          $container = $('<ul/>');
//
//                                                                          for(i = 1; i <= length; i++) {
//                                                                              $container.append(
//                                                                                      $('<li/>', {"data-page":i})
//                                                                                              .html($('<a/>', {
//                                                                                          href:'/page/' + i,
//                                                                                          class:current == i ? 'active' : ''
//                                                                                      }).text(i))
//                                                                              );
//                                                                          }
//
//                                                                          this.ui.nav.html($container.html());
//                                                                      },

//                                                                      goToPage:function (evt) {
//                                                                          var page = $(evt.target).attr('href');
//
//                                                                          app.router.navigate(page, {trigger:true});
//                                                                          evt.preventDefault();
//                                                                      }
                                                                  });

       });
