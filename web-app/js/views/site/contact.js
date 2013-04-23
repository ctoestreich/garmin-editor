/**
 * Typeface List Item View
 */
define([
           "jquery",
           "underscore",
           "backbone",
           "marionette",
           "text!templates/site/contact.tmpl",
           "app"
       ],

       function ($, _, Backbone, Marionette, ContactTemplate, app) {

           return Marionette.ItemView.extend({
                                                 template:_.template(ContactTemplate)
                                             });
       });