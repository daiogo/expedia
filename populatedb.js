var mongoose = require('mongoose');
var flightSchema = require('./flight');
var hotelSchema = require('./hotel');

// Connects to mongoose
mongoose.connect('mongodb://localhost:27017/test');

// Parameters are: model name, schema, collection name
var Flight = mongoose.model('Flight', flightSchema, 'flights');
var Hotel = mongoose.model('Hotel', hotelSchema, 'hotels');

var flight = new Flight({
	flightNumber: 'JJ0011',
	airline: 'LATAM',
	origin: 'Rio de Janeiro',
	destination: 'Curitiba',
	departureDate: '2016-06-01T12:00:00Z',
	arrivalDate: '2016-06-01T13:30:00Z',
	airfare: '200.30',
	availableSeats: 100
});

flight.save(function(error) {
	if (error) {
		console.log(error);
		process.exit(1);
	}


});

Flight.find(function(error, docs) {
	if (error) {
		console.log(error);
		process.exit(1);
	}

	console.log(require('util').inspect(docs));
	process.exit(0);
});