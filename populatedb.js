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
		departureDate: '08/06/2016',
		arrivalDate: '08/06/2016',
		departureTime: '10:00am',
		arrivalTime: '11:00am',
		airfare: '55.50',
		availableSeats: 2
	})
	,
	new Flight({
		flightNumber: shortid.generate(),
		airline: 'GOL',
		origin: 'Curitiba',
		destination: 'São Paulo',
		departureDate: '08/06/2016',
		arrivalDate: '08/06/2016',
		departureTime: '10:00am',
		arrivalTime: '11:00am',
		airfare: '65.50',
		availableSeats: 2
	})
	,
	new Flight({
		flightNumber: shortid.generate(),
		airline: 'Azul',
		origin: 'Curitiba',
		destination: 'São Paulo',
		departureDate: '08/06/2016',
		arrivalDate: '08/06/2016',
		departureTime: '10:00am',
		arrivalTime: '11:00am',
		airfare: '45.50',
		availableSeats: 2
	})
	,
	new Flight({
		flightNumber: shortid.generate(),
		airline: 'TAM',
		origin: 'São Paulo',
		destination: 'Curitiba',
		departureDate: '18/06/2016',
		arrivalDate: '18/06/2016',
		departureTime: '10:00am',
		arrivalTime: '11:00am',
		airfare: '55.50',
		availableSeats: 2
	})
	,
	new Flight({
		flightNumber: shortid.generate(),
		airline: 'GOL',
		origin: 'São Paulo',
		destination: 'Curitiba',
		departureDate: '18/06/2016',
		arrivalDate: '18/06/2016',
		departureTime: '10:00am',
		arrivalTime: '11:00am',
		airfare: '65.50',
		availableSeats: 2
	})
	,
	new Flight({
		flightNumber: shortid.generate(),
		airline: 'Azul',
		origin: 'São Paulo',
		destination: 'Curitiba',
		departureDate: '18/06/2016',
		arrivalDate: '18/06/2016',
		departureTime: '10:00am',
		arrivalTime: '11:00am',
		airfare: '45.50',
		availableSeats: 2
	})
	,
	new Flight({
		flightNumber: shortid.generate(),
		airline: 'TAM',
		origin: 'Curitiba',
		destination: 'Rio de Janeiro',
		departureDate: '08/06/2016',
		arrivalDate: '08/06/2016',
		departureTime: '10:00am',
		arrivalTime: '11:30am',
		airfare: '75.50',
		availableSeats: 2
	})
	,
	new Flight({
		flightNumber: shortid.generate(),
		airline: 'GOL',
		origin: 'Curitiba',
		destination: 'Rio de Janeiro',
		departureDate: '08/06/2016',
		arrivalDate: '08/06/2016',
		departureTime: '10:00am',
		arrivalTime: '11:30am',
		airfare: '95.50',
		availableSeats: 2
	})
	,
	new Flight({
		flightNumber: shortid.generate(),
		airline: 'Azul',
		origin: 'Curitiba',
		destination: 'Rio de Janeiro',
		departureDate: '08/06/2016',
		arrivalDate: '08/06/2016',
		departureTime: '10:00am',
		arrivalTime: '11:30am',
		airfare: '85.50',
		availableSeats: 2
	})
	,
	new Flight({
		flightNumber: shortid.generate(),
		airline: 'TAM',
		origin: 'Rio de Janeiro',
		destination: 'Curitiba',
		departureDate: '18/06/2016',
		arrivalDate: '18/06/2016',
		departureTime: '10:00am',
		arrivalTime: '11:30am',
		airfare: '75.50',
		availableSeats: 2
	})
	,
	new Flight({
		flightNumber: shortid.generate(),
		airline: 'GOL',
		origin: 'Rio de Janeiro',
		destination: 'Curitiba',
		departureDate: '18/06/2016',
		arrivalDate: '18/06/2016',
		departureTime: '10:00am',
		arrivalTime: '11:30am',
		airfare: '95.50',
		availableSeats: 2
	})
	,
	new Flight({
		flightNumber: shortid.generate(),
		airline: 'Azul',
		origin: 'Rio de Janeiro',
		destination: 'Curitiba',
		departureDate: '18/06/2016',
		arrivalDate: '18/06/2016',
		departureTime: '10:00am',
		arrivalTime: '11:30am',
		airfare: '85.50',
		availableSeats: 2
	})
	,
	
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