/**
 * Typeface List Item View
 */
define([
           "jquery",
           "underscore",
           "backbone",
           "marionette",
           "text!templates/site/home.tmpl",
           "app"
       ],

       function ($, _, Backbone, Marionette, HomeTemplate, app) {

           return Marionette.ItemView.extend({
                                                 template:_.template(HomeTemplate)
                                             });
       });