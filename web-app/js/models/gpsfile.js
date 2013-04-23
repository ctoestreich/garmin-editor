define([
           "underscore",
           "backbone"
       ],

       function( _, Backbone){

           return Backbone.Model.extend({
/*
 <Activities>
 <Activity Sport="Biking">
 <Id>2012-09-27T21:56:22.000Z</Id>
 <Lap StartTime="2012-09-27T21:56:22.000Z">
 <TotalTimeSeconds>8339.877</TotalTimeSeconds>
 <DistanceMeters>68199.67</DistanceMeters>
 <MaximumSpeed>18.665000915527344</MaximumSpeed>
 <Calories>3095</Calories>
 <Intensity>Active</Intensity>
 <TriggerMethod>Manual</TriggerMethod>

 */
                                                    defaults : {
                                                        id: '1',
                                                        name: '',
                                                        sport : 'Biking',
                                                        lapCount: 0,
                                                        totalTimeSeconds:0,
                                                        distanceMeters: 0,
                                                        maximumSpeed: 0,
                                                        calories: 0,
                                                        intensity: 'Active'
                                                    },

                                                    url : function(){
                                                        return this.collection.url + '/' + this.id;
                                                    },

                                                    initialize : function(){
//                                                        this.set({ id : '-1' }, { silent: true });
//                                                        console.log(this.url);
                                                    }

//                                                    isFavorite : function(){
//                                                        return this.get('favorite');
//                                                    }
                                                });
       });
