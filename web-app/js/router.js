define([
           // Application.
           "app",
           "require",

           // Plugins
           "backbone",
           "marionette",
           "jquery",

           // Views
           "views/site/home",
           "views/site/login",
           "views/site/contact",
           "views/site/empty",
           "views/gpsfile/upload",
           "modules/gpsfiles_list",

           //extras
           "plugins/jquery.filedownload"

       ],

       function (app, require, Backbone, Marionette, $, HomeView, LoginView, ContactView, EmptyView, UploadView, gpsFilesModule) {
           return Marionette.AppRouter.extend({
                                                  routes:{
                                                      "":"index",
                                                      "contact":"contact",
                                                      "user/files":"files"
                                                  },
                                                  contact:function(){
                                                      this.blankView = new EmptyView();
                                                      this.contactView = new ContactView();
                                                      app.content.show(this.contactView);
                                                      app.head.show(this.blankView);
                                                  },
                                                  index:function () {
                                                      this.blankView = new EmptyView();
                                                      this.homeView = new HomeView();
                                                      app.content.show(this.homeView);
                                                      app.head.show(this.blankView);
                                                  },
                                                  files:function () {
                                                      this.uploadView = new UploadView();
                                                      gpsFilesModule.list = new gpsFilesModule.view({
                                                                                                        model:app.user,
                                                                                                        collection:gpsFilesModule.collection
                                                                                                    });

                                                      app.head.show(this.uploadView);
                                                      app.content.show(gpsFilesModule.list);
                                                      app.files = gpsFilesModule.collection;
                                                      this.uploadView.wireFileupload();
                                                  }
                                              });

       });
