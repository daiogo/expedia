/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.apache.http.client.utils.URIBuilder;
import org.json.*;


/**
 *
 * @author Diogo
 */
public class Traveller {
    
    private static final String hostURL = "http://localhost:3000/";
    private TravellerFrame travellerFrame;
    private FlightSearchResultsFrame flightSearchResultsFrame;
    private HotelSearchResultsFrame hotelSearchResultsFrame;
    private FlightSearch currentFlightSearch;
    private HotelSearch currentHotelSearch;
    private HttpConnector httpConnector;

    public Traveller() {
        httpConnector = new HttpConnector();
        travellerFrame = new TravellerFrame(this);
        travellerFrame.setLocationRelativeTo(null);
        travellerFrame.setVisible(true);        
    }
    
    public void searchFlights (FlightSearch flightSearch) {
        // Keeps current search in order to store session variables
        currentFlightSearch = flightSearch;
        
        try {
            // Builds URL for HTTP request that queries for departing flights
            URIBuilder uriBuilder = new URIBuilder(hostURL + "/search/flight");
            uriBuilder.addParameter("origin", flightSearch.getOrigin());
            uriBuilder.addParameter("destination", flightSearch.getDestination());
            uriBuilder.addParameter("departureDate", flightSearch.getDepartureDate());
            uriBuilder.addParameter("numberOfPassengers", String.valueOf(flightSearch.getNumberOfPassengers()));
            
            // Converts URL to string
            String urlString = uriBuilder.build().toString();
            
            // Send GET request and waits for response
            String response = httpConnector.sendGet(urlString);
            
            // Handles response (REST API returns a JSON array)
            ArrayList<Flight> departingFlights = new ArrayList<>();
            JSONArray flights = new JSONArray(response);
            int n = flights.length();
            Flight f;
            for (int i = 0; i < n; ++i) {
                final JSONObject flight = flights.getJSONObject(i);
                String flightNumber = (flight.getString("flightNumber"));
                String airline = (flight.getString("airline"));
                String origin = (flight.getString("origin"));
                String destination = (flight.getString("destination"));
                String departureDate = (flight.getString("departureDate"));
                String arrivalDate = (flight.getString("arrivalDate"));
                departureDate = (flight.getString("departureDate"));
                String departureTime = (flight.getString("departureTime"));
                String arrivalTime = (flight.getString("arrivalTime"));
                String airfare = (flight.getString("airfare"));
                int availableSeats = (flight.getInt("availableSeats"));
                f = new Flight(flightNumber, airline, origin, destination, departureDate, departureTime, arrivalTime, Double.parseDouble(airfare), availableSeats);
                departingFlights.add(f);
            }

            // Creates array for returning flights
            ArrayList<Flight> returningFlights = new ArrayList<>();
            
            // Prepares second GET request if user searched for a round trip flights
            if (flightSearch.getRoundTrip()) {
                
                // Builds URL for HTTP request that queries for returning flights
                uriBuilder = new URIBuilder(hostURL + "/search/flight");
                uriBuilder.addParameter("destination", flightSearch.getOrigin());
                uriBuilder.addParameter("origin", flightSearch.getDestination());
                uriBuilder.addParameter("departureDate", flightSearch.getReturnDate());
                uriBuilder.addParameter("numberOfPassengers", String.valueOf(flightSearch.getNumberOfPassengers()));
                
                // Converts URL to string
                urlString = uriBuilder.build().toString();
                
                // Sends second GET request and waits for response
                response = httpConnector.sendGet(urlString);
                
                // Handles response from second GET request
                flights = new JSONArray(response);
                n = flights.length();
                for (int i = 0; i < n; ++i) {
                    final JSONObject flight = flights.getJSONObject(i);
                    String flightNumber = (flight.getString("flightNumber"));
                    String airline = (flight.getString("airline"));
                    String origin = (flight.getString("origin"));
                    String destination = (flight.getString("destination"));
                    String departureDate = (flight.getString("departureDate"));
                    departureDate = (flight.getString("departureDate"));
                    String departureTime = (flight.getString("departureTime"));
                    String arrivalTime = (flight.getString("arrivalTime"));
                    String airfare = (flight.getString("airfare"));
                    int availableSeats = (flight.getInt("availableSeats"));
                    f = new Flight(flightNumber, airline, origin, destination, departureDate, departureTime, arrivalTime, Double.parseDouble(airfare), availableSeats);
                    returningFlights.add(f);
                }
                
            }

            // Display search results in a new window
            flightSearchResultsFrame = new FlightSearchResultsFrame(this, departingFlights, returningFlights);
            flightSearchResultsFrame.setLocationRelativeTo(null);
            flightSearchResultsFrame.setVisible(true);
            
        } catch (URISyntaxException ex) {
            this.displayBookingConfirmation("ERROR | Invalid search");
        }
    } 
    
    public void bookFlight(String departureFlightNumber, String returnFlightNumber, String departureDate, String returnDate) throws UnsupportedEncodingException, IOException, Exception {
        // Builds request URL (POST)
        String url = hostURL + "/book/flight";
        
        // Builds JSON with booking information
        String postData = "{ \"departingFlightNumber\":\"" + departureFlightNumber + "\", \"returningFlightNumber\":\"" + returnFlightNumber + "\", \"roundTrip\":\"" + currentFlightSearch.getRoundTrip() + "\", \"numberOfPassengers\": " + currentFlightSearch.getNumberOfPassengers() +" }";
        
        // Sends POST request and waits for response
        String response = httpConnector.sendPost(url, postData);
        
        // Displays confirmation message
        this.displayBookingConfirmation(response);
    }

    public void searchHotels(HotelSearch hotelSearch) {
        // Keeps current search in order to store session variables
        currentHotelSearch = hotelSearch;
        
        try {
            // Builds URL for HTTP request that queries for hotels
            URIBuilder uriBuilder = new URIBuilder(hostURL + "/search/hotel");
            uriBuilder.addParameter("city", hotelSearch.getCity());
            uriBuilder.addParameter("numberOfGuests", String.valueOf(hotelSearch.getNumberOfRooms()));
            
            // Converts URL to string
            String urlString = uriBuilder.build().toString();
            
            // Sends GET request and waits for response
            String response = httpConnector.sendGet(urlString);
            
            // Handles server response
            ArrayList<Hotel> hotelsQueried = new ArrayList<>();
            JSONArray hotels = new JSONArray(response);
            int n = hotels.length();
            for (int i = 0; i < n; ++i) {
                final JSONObject hotel = hotels.getJSONObject(i);
                String hotelId = (hotel.getString("hotelId"));
                String hotelName = (hotel.getString("hotelName"));
                String city = (hotel.getString("city"));
                int availableRooms = (hotel.getInt("availableRooms"));
                String pricePerNight = (hotel.getString("pricePerNight"));
                Hotel h = new Hotel(hotelId, hotelName, city, availableRooms , Double.parseDouble(pricePerNight));
                hotelsQueried.add(h);
            }
            
            // Display search results on a new window
            hotelSearchResultsFrame = new HotelSearchResultsFrame(this, hotelsQueried);
            hotelSearchResultsFrame.setLocationRelativeTo(null);
            hotelSearchResultsFrame.setVisible(true);
            
        } catch (URISyntaxException ex) {
            this.displayBookingConfirmation("ERROR | Invalid search");
        }        
    }
    
    public void bookHotel(String hotelId) throws Exception {
        // Builds booking URL
        String url = hostURL + "/book/hotel";
        
        // Builds JSON with booking information
        String postData = "{ \"hotelId\":\"" + hotelId + "\", \"numberOfGuests\":\"" + currentHotelSearch.getNumberOfRooms() + "\" }";
        
        // Send POST request for booking and waits for response
        String response = httpConnector.sendPost(url, postData);
        
        // Display confirmation message
        this.displayBookingConfirmation(response);
    }
    
    public void invokeLaterMessageDialog(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JOptionPane.showMessageDialog(null, message);
            }
        });        
    }

    public void displayBookingConfirmation(String message) {
        invokeLaterMessageDialog(message);
    }

}
