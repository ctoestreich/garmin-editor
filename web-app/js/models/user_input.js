define([
	"underscore",
	"backbone"
],

function( _, Backbone){

	var UserInput = Backbone.Model.extend({

		defaults : {
			page: 1,
			per_page : 10,
			filter : null
		},

		initialize : function(){

		}
	});

	return UserInput;
});
