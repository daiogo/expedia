/*
	Travel agency system RESTful API
*/

// Imports modules
var express = require('express');					// Express.js
var mongoose = require('mongoose');					// mongoose (MongoDB driver)
var bodyparser = require('body-parser');			// body-parser (parse HTTP request body)
var flightSchema = require('./db/schemas/flight');	// Flight database schema
var hotelSchema = require('./db/schemas/hotel');	// Hotel database schema

// Connects mongoose to mongodb service
mongoose.connect('mongodb://localhost:27017/test');

// Creates mongoose models for each schema
// Parameters are: model name, schema, collection name
var Flight = mongoose.model('Flight', flightSchema, 'flights');
var Hotel = mongoose.model('Hotel', hotelSchema, 'hotels');

// Export API methods
module.exports = function() {
	// Creates and Express.js app
	var app = express();

	// Makes app able to use the body-parser module functionality
	app.use(bodyparser.json());

	// REQUEST HANDLER: Search flight
	app.get('/search/flight', function(req, res) {

		// Creates mongodb query based on request parameters (located on query string)
		var departFlightQuery = {
			origin: req.query.origin,
			destination: req.query.destination,
			departureDate: req.query.departureDate,
			availableSeats: { $gte: req.query.numberOfPassengers }
		}

		// Query the flight database and executes callback function passed
		// as parameter to send response after the query has been completed
		Flight.find(departFlightQuery, function(error, docs) {
			if (error) {
				console.log(error);
			}

			// Gets query results and send response in a JSON format
			var searchResults = require('util').inspect(docs);
			res.send(searchResults);
		});
	});

	// REQUEST HANDLER: Search hotel
	app.get('/search/hotel', function(req, res) {

		// Creates mongodb query based on request parameters (located on query string)
		var hotelQuery = {
			city: req.query.city,
			availableRooms: { $gte: req.query.numberOfGuests }
		}
		
		// Query the hotel database and executes callback function passed
		// as parameter to send response after the query has been completed
		Hotel.find(hotelQuery, function(error, docs) {
			if (error) {
				console.log(error);
			}

			// Gets query results and send response in a JSON format
			var searchResults = require('util').inspect(docs);
			res.send(searchResults);
		});
	});

	// REQUEST HANDLER: Book flight
	app.post('/book/flight', function(req, res) {
		// Creates flags used to synchronize timing for response
		var departingFlightStatus = false;
		var returnFlightStatus = false;

		// Queries database for departing flight
		Flight.findOne( { flightNumber: req.body.departingFlightNumber }, function(error, doc) {
			if (error) {
				console.log(error);
			}

			// If query successful, update database
			if (doc.availableSeats >= req.body.numberOfPassengers) {
				doc.availableSeats -= req.body.numberOfPassengers;
				doc.save();
				departingFlightStatus = true;
			} else {
				departingFlightStatus = false;
			}

			// If booking request is for round trip flights, query for returning flights
			if (req.body.roundTrip == 'true') {

				// Queries for returning flight
				Flight.findOne( { flightNumber: req.body.returningFlightNumber }, function(error, doc) {
					if (error) {
						console.log(error);
					}

					// If query successful, update database
					if (doc.availableSeats >= req.body.numberOfPassengers) {
						doc.availableSeats -= req.body.numberOfPassengers;
						doc.save();
						returnFlightStatus = true;
					} else {
						returnFlightStatus = false;
					}

					// Checks if both flights were sucessfully booked (only for return flights)
					if (departingFlightStatus && returnFlightStatus) {
						res.send("Your booking for the flights " + req.body.departingFlightNumber + " and " + req.body.returningFlightNumber + " is confirmed.");
					} else {
						res.send("Unfortunately the flights " + req.body.departingFlightNumber + " and " + req.body.returningFlightNumber + " are fully booked.");
					}

				});
			} else {
				// Checks if departing flight was sucessfully booked (only for one way flights)
				if (departingFlightStatus) {
					res.send("Your booking for flight " + req.body.departingFlightNumber + " is confirmed.");
				} else {
					res.send("Unfortunately the flight " + req.body.departingFlightNumber + " is fully booked.");
				}
			}
		});

	});

	// REQUEST HANDLER: Book hotel
	app.post('/book/hotel', function(req, res) {

		// Queries for requested hotel
		Hotel.findOne( { hotelId: req.body.hotelId }, function(error, doc) {
			if (error) {
				console.log(error);
			}

			// If query successful, update database 
			if (doc.availableRooms >= req.body.numberOfGuests) {
				doc.availableRooms -= req.body.numberOfGuests;
				doc.save();

				// Sends confirmation message response
				res.send("Your booking for hotel " + doc.hotelName + " is confirmed.");
			} else {
				// Sends error message response
				res.send("Unfortunately there are no more rooms available at " + doc.hotelName + ".");
			}
		});		
	});

	return app;
}
