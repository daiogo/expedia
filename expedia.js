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

		var start = new Date(req.query.departureYear, req.query.departureMonth - 1, req.query.departureDay, 0, 0, 0);
		var end = new Date(req.query.departureYear, req.query.departureMonth - 1, req.query.departureDay, 23, 59, 59);
		//console.log('Start: ' + start);
		//console.log('End: ' + end);

		var departFlightQuery = {
			origin: req.query.origin,
			destination: req.query.destination,
			departureDate: { $gte: start, $lte: end},
			availableSeats: { $gte: req.query.numberOfPassengers }
		}

		if (req.query.roundTrip) {
			start = new Date(req.query.returnYear, req.query.returnMonth - 1, req.query.returnDay, 0, 0, 0);
			end = new Date(req.query.returnYear, req.query.returnMonth - 1, req.query.returnDay, 23, 59, 59);
		
			var returnFlightQuery = {
				origin: req.query.destination,
				destination: req.query.origin,
				returnDate: { $gte: start, $lte: end},
				availableSeats: { $gte: req.query.numberOfPassengers }
			}
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

	});

	// Book hotel
	app.post('/book/hotel', function(req, res) {
		console.log("GOT IT!");
		Hotel.update(
			{ hotelId: req.body.hotelId },
			{ $inc: { availableRooms: -req.body.numberOfGuests } }
		);

		res.send("CONFIRMED!");
	});

	app.get('/user/:user', function(req, res) {
		res.send('Page for user ' + req.params.user + ' with option ' + req.query.option);
	});

	return app;
}