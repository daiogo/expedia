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

// Create hotels
var hotelsArray = [
	new Hotel({
		hotelId: shortid.generate(),
		hotelName: 'Ibis',
		city: 'Curitiba',
		pricePerNight: '70.00',
		availableRooms: 2
	}),
	new Hotel({
		hotelId: shortid.generate(),
		hotelName: 'Ibis',
		city: 'São Paulo',
		pricePerNight: '100.00',
		availableRooms: 2
	}),
	new Hotel({
		hotelId: shortid.generate(),
		hotelName: 'Ibis',
		city: 'Rio de Janeiro',
		pricePerNight: '110.00',
		availableRooms: 2
	}),
	new Hotel({
		hotelId: shortid.generate(),
		hotelName: 'Sheraton',
		city: 'São Paulo',
		pricePerNight: '200.00',
		availableRooms: 2
	}),
	new Hotel({
		hotelId: shortid.generate(),
		hotelName: 'Sheraton',
		city: 'Rio de Janeiro',
		pricePerNight: '250.00',
		availableRooms: 2
	}),
	new Hotel({
		hotelId: shortid.generate(),
		hotelName: 'Hilton',
		city: 'São Paulo',
		pricePerNight: '500.00',
		availableRooms: 2
	}),
	new Hotel({
		hotelId: shortid.generate(),
		hotelName: 'Hilton',
		city: 'Rio de Janeiro',
		pricePerNight: '700.00',
		availableRooms: 2
	})	
];

hotelsArray.forEach(function(hotelDoc) {
	hotelDoc.save(function(error) {
		if (error) {
			console.log(error);
			process.exit(1);
		}
	});
});

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


var flightsArray = [
	new Flight({
		flightNumber: shortid.generate(),
		airline: 'TAM',
		origin: 'Curitiba',
		destination: 'São Paulo',
		departureDate: new Date(2016, 6, 8, 10, 0, 0),
		arrivalDate: new Date(2016, 6, 8, 11, 0, 0),
		airfare: '55.50',
		availableSeats: 2
	})
];

flightsArray.forEach(function(flightDoc) {
	flightDoc.save(function(error) {
		if (error) {
			console.log(error);
			process.exit(1);
		}
	});
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