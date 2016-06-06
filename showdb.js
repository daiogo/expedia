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

Hotel.find(function(error, hotelDocs) {
	if (error) {
		console.log(error);
		process.exit(1);
	}

	console.log('Created hotels: ');
	hotelDocs.forEach(function(hotelDoc) {
		console.log(JSON.stringify(hotelDoc));
	});
	doneHotels = true;
	if (doneFlights && doneHotels) {
		process.exit(0);
	}
});

Flight.find(function(error, flightDocs) {
	if (error) {
		console.log(error);
		process.exit(1);
	}

	console.log('Created flights: ');
	flightDocs.forEach(function(flightDoc) {
		console.log(JSON.stringify(flightDoc));
	});
	doneFlights = true;
	if (doneFlights && doneHotels) {
		process.exit(0);
	}
});