/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traveller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import messages.Customer;
import messages.Flight;
import messages.FlightSearch;
import messages.Hotel;
import messages.HotelSearch;
import org.apache.http.client.utils.URIBuilder;
import org.json.*;


/**
 *
 * @author Diogo
 */
public class TravellerServant extends UnicastRemoteObject {
    
    private static final String _URL_ = "http://localhost:3000/";
    private TravellerFrame travellerFrame;
    private FlightSearchResultsFrame flightSearchResultsFrame;
    private HotelSearchResultsFrame hotelSearchResultsFrame;
    private FlightSearch currentFlightSearch;
    private HotelSearch currentHotelSearch;
    ArrayList<Customer> passengers;
    ArrayList<Customer> guests;
    String hotelCheckin;
    String hotelCheckout;
    private HttpConnector httpConnector;

    
    public TravellerServant () throws RemoteException {

        travellerFrame = new TravellerFrame(this);
        travellerFrame.setLocationRelativeTo(null);
        travellerFrame.setVisible(true);
        
        passengers = new ArrayList();
        guests = new ArrayList();
        
        httpConnector = new HttpConnector();
        
    }
    
    public void searchFlights (FlightSearch flightSearch) {
        currentFlightSearch = flightSearch;
        
        try {
            passengers.clear();
            for(int i = 0; i< flightSearch.getNumberOfPassengers(); i++ ) {
                passengers.add(new Customer("Diogo Freitas", 21));
            }
            
            URIBuilder uriBuilder = new URIBuilder(_URL_ + "/search/flight");
            uriBuilder.addParameter("origin", flightSearch.getOrigin());
            uriBuilder.addParameter("destination", flightSearch.getDestination());
            uriBuilder.addParameter("departureDate", flightSearch.getDepartureDate());
            uriBuilder.addParameter("numberOfPassengers", ""+passengers.size());
            
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
                uriBuilder.addParameter("numberOfPassengers", ""+passengers.size());
                

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
            Logger.getLogger(TravellerServant.class.getName()).log(Level.SEVERE, null, ex);
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
            guests.clear();
            for(int i = 0; i < hotelSearch.getNumberOfRooms(); i++){
                guests.add(new Customer("Diego Lee", 23));
            }
            //skyscannerReference.searchHotels(hotelSearch, this);
            hotelCheckin = hotelSearch.getCheckInDate();
            hotelCheckout = hotelSearch.getCheckOutDate();
            
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
                h = new Hotel(hotelName, city, availableRooms , Double.parseDouble(pricePerNight));
                hotelsQueried.add(h);
            }
            hotelSearchResultsFrame = new HotelSearchResultsFrame(this, hotelsQueried);
            hotelSearchResultsFrame.setLocationRelativeTo(null);
            hotelSearchResultsFrame.setVisible(true);
            
        } catch (URISyntaxException ex) {
            Logger.getLogger(TravellerServant.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void bookHotel(String hotelId) throws Exception {
        String url = _URL_ + "/book/hotel";
        String postData = "{ \"hotelId\":\"" + hotelId + "\", \"numberOfGuests\":\"" + currentHotelSearch.getNumberOfRooms() + "\" }";
        
        String response = httpConnector.sendPost(url, postData);
        this.displayBookingConfirmation(response);
    }
    
    public void invokeLaterMessageDialog(String message){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JOptionPane.showMessageDialog(null, message);
            }
        });        
    }

    public void displayBookingConfirmation(String message) throws RemoteException {
        invokeLaterMessageDialog(message);
    }

}
