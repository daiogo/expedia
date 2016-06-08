/*
	Server startup file
*/

// Imports the implemented RESTful API
var expedia = require('./expedia');

// Starts server on port 3000
expedia().listen(3000);
console.log('Server listening on port 3000');
