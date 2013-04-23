/**
 * Typeface List Item View
 */
define([
           "jquery",
           "underscore",
           "backbone",
           "marionette",
           "text!templates/site/empty.tmpl"
       ],

       function ($, _, Backbone, Marionette, EmptyTemplate) {

           return Marionette.ItemView.extend({
                                                 template:_.template(EmptyTemplate)
                                             });
       });