var mongoose = require('mongoose');

var hotelSchema = {
	hotelId: {
		type: String,
		required: true
	},
	hotelName: {
		type: String,
		required: true
	},
	city: {
		type: String,
		required: true
	},
	pricePerNight: {
		type: String,
		match: /\d+\.\d{2}/,
		required: true
	},
	availableRooms: {
		type: Number,
		required: true
	}
};

module.exports = new mongoose.Schema(hotelSchema);
module.exports.hotelSchema = hotelSchema;
