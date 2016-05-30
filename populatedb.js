var mongoose = require('mongoose');
var shortid = require('shortid');
var flightSchema = require('./flight');
var hotelSchema = require('./hotel');

// Connects to mongoose
mongoose.connect('mongodb://localhost:27017/test');

// Parameters are: model name, schema, collection name
var Flight = mongoose.model('Flight', flightSchema, 'flights');
var Hotel = mongoose.model('Hotel', hotelSchema, 'hotels');

var hotel = new Hotel({
	hotelId: shortid.generate(),
	hotelName: 'Ibis',
	city: 'Curitiba',
	pricePerNight: '70.00',
	availableRooms: 10
});

hotel.save(function(error) {
	if (error) {
		console.log(error);
		process.exit(1);
	}


});

Hotel.find(function(error, docs) {
	if (error) {
		console.log(error);
		process.exit(1);
	}

	console.log(require('util').inspect(docs));
	process.exit(0);
});
