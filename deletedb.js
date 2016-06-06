var mongoose = require('mongoose');
var shortid = require('shortid');
var flightSchema = require('./flight');
var hotelSchema = require('./hotel');

// Connects to mongoose
mongoose.connect('mongodb://localhost:27017/test');

// Parameters are: model name, schema, collection name
var Flight = mongoose.model('Flight', flightSchema, 'flights');
var Hotel = mongoose.model('Hotel', hotelSchema, 'hotels');

var doneFlights = false;
var doneHotels = false;

// Delete all previous entries
Hotel.remove({}, function(){
	doneHotels = true;
	if (doneFlights && doneHotels) {
		process.exit(0);
	}
});

Flight.remove({}, function(){
	doneFlights = true;
	if (doneFlights && doneHotels) {
		process.exit(0);
	}
});
