/**
 * GpsFiles Module
 */
define([
	"jquery",
	"underscore",
	"backbone",
	"app",
	"models/user_input",
	"collections/gpsfiles",
	"views/gpsfile/list"
],

function( $, _, Backbone, app, UserInput, GpsFiles, GpsFilesListView ){

	return app.module("GpsFilesList", function( GpsFilesList ){

		// Currently unused. Was using a <style> tag to append new preview file declarations
		// app.addInitializer(function( options ){
		// 	var style = GpsFilesList.styles;
		// 	style.type = 'text/css';

		// 	document.getElementsByTagName('head')[0].appendChild(style);
		// });

		app.isFileLoaded = function( id ){
			return _.detect( GpsFilesList.loaded, function( obj ){
				return obj.hasOwnProperty( id );
			});
		};

		app.loadFile = function( view ){
			var id = view.model.get('id'),
			    fileURL = view.model.url(),
			    identifier = {},
			    callback = function(){
			    	this.trigger('file:finish:' + id);
			    };

			identifier[ id ] = id;

			app.trigger('file:start:' + id );
//
//			(function(d,t,u,id,cb){
//				var g = d.createElement(t),
//				    s = d.getElementsByTagName(t)[0];
//
//				g.src = u;
//				g.id = id;
//				g.onload = _.bind(cb, app);
//				s.parentNode.insertBefore(g,s);
//
//			}(document,'script', fileURL, scriptID, callback));

			GpsFilesList.loaded.push( identifier );

//			this.addFileStyle( view );

			app.tracker.push(['_trackEvent', 'Load File', id]);
		};

//		app.addFileStyle = function( view ){
//
//			$('.file-preview', view.el).css('fileFamily', view.model.get('id'));
//		};


		_.extend( GpsFilesList, {
			loaded : [],
			collection : new GpsFiles(),
			view : GpsFilesListView
		});
	});

});

