require([
            "jquery",
            "underscore",
            // Application.
            "app",
            // Main Router.
            "router",
            "backbone",
            "bootstrap"
        ],

        function ($, _, app, Router, Backbone) {

            this.app = app;

            app.addInitializer(function (options) {
                // Define your master router on the application namespace and trigger all
                // navigation from this instance.
                app.router = new Router();
                app.bootstrap = options.bootstrap;
                app.tracker = options.tracker;

                // Trigger the initial route and enable HTML5 History API support, set the
                // root folder to '/' by default.  Change in app.js.
                Backbone.history.start();

            });

            app.addInitializer(function (options) {
                $.ajaxSetup({
                                statusCode:{
                                    401:function () {
                                        // Redirec the to the login page.
                                        window.location.replace('/#login');

                                    },
                                    403:function () {
                                        // 403 -- Access denied
                                        window.location.replace('/#denied');
                                    }
                                }
                            });
            });

            $(document).ready(function () {
                app.start({
                              root:window.location.pathname,
                              bootstrap:window.bootstrap || [],
                              tracker:window._gaq || []
                          });

                $('body').popover({
                                      selector:'[rel=popover]',
                                      trigger:'hover',
                                      html:true
                                  });
            });

        });
