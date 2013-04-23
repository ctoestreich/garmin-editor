// Set the require.js configuration for your application.
require.config({

                   // Initialize the application with the main application file.
                   deps:["main"],

                   paths:{
                       // JavaScript folders.
                       libs:"libs",
                       plugins:"plugins",

                       // Libraries.
                       jquery:"libs/jquery.min",
                       backbone:"libs/backbone.min",
                       bootstrap: "libs/bootstrap.min",
                       underscore : 'libs/underscore.min',
                       marionette : 'plugins/backbone.marionette',
                       'backbone.wreqr' : 'plugins/backbone.wreqr',
                       'backbone.babysitter' : 'plugins/backbone.babysitter'
                   },

                   shim:{
                       jquery:{
                           exports:"jQuery"
                       },
                       underscore : {
                           exports : '_'
                       },

                       bootstrap:{
                           deps:["jquery"],
                           exports:"Bootstrap"
                       },

                       // Backbone library depends on lodash and jQuery.
                       backbone:{
                           deps:["underscore", "jquery", "bootstrap"],
                           exports:"Backbone"
                       },

                       "plugins/underscore.string":{
                           deps:["underscore"]
                       },

                       "plugins/jquery.filedownload":{
                           deps:["jquery"]
                       },

                       "plugins/backbone.paginator":{
                           deps:["jquery", "backbone"],
                           exports:"Backbone.Paginator"
                       },

                       "plugins/backbone.localStorage":{
                           deps:["jquery", "backbone", "underscore"],
                           exports:"Backbone.LocalStorage"
                       },

                       "plugins/select2":{
                           deps:["jquery"],
                           exports:"Select2"
                       },

                       "plugins/bootstrap.tooltip":{
                           deps:["jquery"],
                           exports:"jQuery.fn.tooltip"
                       },

                       marionette : {
                           deps : ['jquery', 'underscore', 'backbone'],
                           exports : 'Marionette'
                       }
                   }

               });
