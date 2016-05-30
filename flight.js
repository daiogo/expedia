var mongoose = require('mongoose');

var flightSchema = {
	flightNumber: {
		type: String,
		required: true
	},
	airline: {
		type: String,
		required: true
	},
	origin: {
		type: String,
		required: true
	},
	destination: {
		type: String,
		required: true
	},
	departureDate: {
		type: Date,
		required: true
	},
	arrivalDate: {
		type: Date,
		required: true
	},
	airfare: {
		type: String,
		match: /\d+\.\d{2}/,
		required: true
	},
	availableSeats: {
		type: Number,
		required: true
	}
};

module.exports = new mongoose.Schema(flightSchema);
module.exports.flightSchema = flightSchema;
