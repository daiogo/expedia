/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traveller;

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
    
    private static final String _URL_ = "http://localhost:3000/";
    private TravellerFrame travellerFrame;
    private FlightSearchResultsFrame flightSearchResultsFrame;
    private HotelSearchResultsFrame hotelSearchResultsFrame;
    private FlightSearch currentFlightSearch;
    private HotelSearch currentHotelSearch;
    private String hotelCheckIn;
    private String hotelCheckOut;
    private HttpConnector httpConnector;

    public Traveller() {
        httpConnector = new HttpConnector();
        travellerFrame = new TravellerFrame(this);
        travellerFrame.setLocationRelativeTo(null);
        travellerFrame.setVisible(true);        
    }
    
    public void searchFlights (FlightSearch flightSearch) {
        currentFlightSearch = flightSearch;
        
        try {
            
            URIBuilder uriBuilder = new URIBuilder(_URL_ + "/search/flight");
            uriBuilder.addParameter("origin", flightSearch.getOrigin());
            uriBuilder.addParameter("destination", flightSearch.getDestination());
            uriBuilder.addParameter("departureDate", flightSearch.getDepartureDate());
            uriBuilder.addParameter("numberOfPassengers", String.valueOf(flightSearch.getNumberOfPassengers()));
            
            String urlString = uriBuilder.build().toString();
            //System.out.println(urlString);
            String response = httpConnector.sendGet(urlString);
            //System.out.println("Response:" + response);
            
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

            ArrayList<Flight> returningFlights = new ArrayList<>();;
            if (flightSearch.getRoundTrip()) {
                uriBuilder = new URIBuilder(_URL_ + "/search/flight");
                uriBuilder.addParameter("destination", flightSearch.getOrigin());
                uriBuilder.addParameter("origin", flightSearch.getDestination());
                uriBuilder.addParameter("departureDate", flightSearch.getReturnDate());
                uriBuilder.addParameter("numberOfPassengers", String.valueOf(flightSearch.getNumberOfPassengers()));
                

                urlString = uriBuilder.build().toString();
                //System.out.println(urlString);
                response = httpConnector.sendGet(urlString);
                //System.out.println("Response:" + response);
                
                flights = new JSONArray(response);
                n = flights.length();
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
                    returningFlights.add(f);
                }
                
            }

            flightSearchResultsFrame = new FlightSearchResultsFrame(this,departingFlights,returningFlights);
            flightSearchResultsFrame.setLocationRelativeTo(null);
            flightSearchResultsFrame.setVisible(true);
            
        } catch (URISyntaxException ex) {
            this.displayBookingConfirmation("ERROR | Invalid search");
        }
    } 
    
    public void bookFlight(String departureFlightNumber, String returnFlightNumber, String departureDate, String returnDate) throws UnsupportedEncodingException, IOException, Exception {
        String url = _URL_ + "/book/flight";
        String postData = "{ \"departingFlightNumber\":\"" + departureFlightNumber + "\", \"returningFlightNumber\":\"" + returnFlightNumber + "\", \"roundTrip\":\"" + currentFlightSearch.getRoundTrip() + "\", \"numberOfPassengers\": " + currentFlightSearch.getNumberOfPassengers() +" }";
        
        String response = httpConnector.sendPost(url, postData);
        this.displayBookingConfirmation(response);
    }

    public void searchHotels(HotelSearch hotelSearch) {
        currentHotelSearch = hotelSearch;
        
        try {
            hotelCheckIn = hotelSearch.getCheckInDate();
            hotelCheckOut = hotelSearch.getCheckOutDate();
            
            URIBuilder uriBuilder = new URIBuilder(_URL_ + "/search/hotel");
            uriBuilder.addParameter("city", hotelSearch.getCity());
            uriBuilder.addParameter("numberOfGuests", String.valueOf(hotelSearch.getNumberOfRooms()));
            
            String urlString = uriBuilder.build().toString();
            System.out.println(urlString);
            String response = httpConnector.sendGet(urlString);
            System.out.println("Response:" + response);
            
            ArrayList<Hotel> hotelsQueried = new ArrayList<>();
            JSONArray hotels = new JSONArray(response);
            int n = hotels.length();
            Hotel h;
            for (int i = 0; i < n; ++i) {
                final JSONObject hotel = hotels.getJSONObject(i);
                String hotelId = (hotel.getString("hotelId"));
                String hotelName = (hotel.getString("hotelName"));
                String city = (hotel.getString("city"));
                int availableRooms = (hotel.getInt("availableRooms"));
                String pricePerNight = (hotel.getString("pricePerNight"));
                h = new Hotel(hotelId, hotelName, city, availableRooms , Double.parseDouble(pricePerNight));
                hotelsQueried.add(h);
            }
            hotelSearchResultsFrame = new HotelSearchResultsFrame(this, hotelsQueried);
            hotelSearchResultsFrame.setLocationRelativeTo(null);
            hotelSearchResultsFrame.setVisible(true);
            
        } catch (URISyntaxException ex) {
            this.displayBookingConfirmation("ERROR | Invalid search");
        }        
    }
    
    public void bookHotel(String hotelId) throws Exception {
        String url = _URL_ + "/book/hotel";
        String postData = "{ \"hotelId\":\"" + hotelId + "\", \"numberOfGuests\":\"" + currentHotelSearch.getNumberOfRooms() + "\" }";
        
        String response = httpConnector.sendPost(url, postData);
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
