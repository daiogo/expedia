var express = require('express');
var mongoose = require('mongoose');
var bodyparser = require('body-parser');
var flightSchema = require('./flight');
var hotelSchema = require('./hotel');

// Connects to mongoose
mongoose.connect('mongodb://localhost:27017/test');

// Parameters are: model name, schema, collection name
var Flight = mongoose.model('Flight', flightSchema, 'flights');
var Hotel = mongoose.model('Hotel', hotelSchema, 'hotels');

module.exports = function() {
	var app = express();
	app.use(bodyparser.json());

	// Search flight
	app.get('/search/flight', function(req, res) {

		var departFlightQuery = {
			origin: req.query.origin,
			destination: req.query.destination,
			departureDate: req.query.departureDate,
			availableSeats: { $gte: req.query.numberOfPassengers }
		}

		Flight.find(departFlightQuery, function(error, docs) {
			if (error) {
				console.log(error);
			}

			var searchResults = require('util').inspect(docs);
			res.send(searchResults);
		});
	});

	// Search hotel
	app.get('/search/hotel', function(req, res) {

		var hotelQuery = {
			city: req.query.city,
			availableRooms: { $gte: req.query.numberOfGuests }
		}

		Hotel.find(hotelQuery, function(error, docs) {
			if (error) {
				console.log(error);
			}

			var searchResults = require('util').inspect(docs);
			res.send(searchResults);
		});
	});

	// Book flight
	app.post('/book/flight', function(req, res) {
		var departingFlightStatus = false;
		var returnFlightStatus = false;

		Flight.findOne( { flightNumber: req.body.departingFlightNumber }, function(error, doc) {
			if (error) {
				console.log(error);
			}

			if (doc.availableSeats >= req.body.numberOfPassengers) {
				doc.availableSeats -= req.body.numberOfPassengers;
				doc.save();
				departingFlightStatus = true;
			} else {
				departingFlightStatus = false;
			}

			if (req.body.roundTrip == 'true') {
				Flight.findOne( { flightNumber: req.body.returningFlightNumber }, function(error, doc) {
					if (error) {
						console.log(error);
					}

					if (doc.availableSeats >= req.body.numberOfPassengers) {
						doc.availableSeats -= req.body.numberOfPassengers;
						doc.save();
						returnFlightStatus = true;
					} else {
						returnFlightStatus = false;
					}

					if (departingFlightStatus && returnFlightStatus) {
						res.send("Your booking for the flights " + req.body.departingFlightNumber + " and " + req.body.returningFlightNumber + " is confirmed.");
					} else {
						res.send("Unfortunately the flights " + req.body.departingFlightNumber + " and " + req.body.returningFlightNumber + " are fully booked.");
					}

				});
			} else {
				if (departingFlightStatus) {
					res.send("Your booking for flight " + req.body.departingFlightNumber + " is confirmed.");
				} else {
					res.send("Unfortunately the flight " + req.body.departingFlightNumber + " is fully booked.");
				}
			}
		});

	});

	// Book hotel
	app.post('/book/hotel', function(req, res) {
		Hotel.findOne( { hotelId: req.body.hotelId }, function(error, doc) {
			if (error) {
				console.log(error);
			}

			if (doc.availableRooms > req.body.numberOfGuests) {
				doc.availableRooms -= req.body.numberOfGuests;
				doc.save();
				res.send("Your booking for hotel " + doc.hotelName + " is confirmed.");
			} else {
				res.send("Unfortunately there are no more rooms available at " + doc.hotelName + ".");
			}
		});		
	});

	return app;
}
