/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traveller;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import messages.Customer;
import messages.Flight;
import messages.FlightBooking;
import messages.FlightSearch;
import messages.Hotel;
import messages.HotelBooking;
import messages.HotelSearch;
import org.apache.http.client.utils.URIBuilder;

/**
 *
 * @author Diogo
 */
public class TravellerServant extends UnicastRemoteObject {
    
    private static final String _URL_ = "http://localhost:3000/";
    private ArrayList<Hotel> hotels;
    private TravellerFrame travellerFrame;
    private FlightSearchResultsFrame flightSearchResultsFrame;
    private HotelSearchResultsFrame hotelSearchResultsFrame;
    ArrayList<Customer> passengers;
    ArrayList<Customer> guests;
    String hotelCheckin;
    String hotelCheckout;
    HttpConnector httpConnector = new HttpConnector();

    
    public TravellerServant () throws RemoteException {

        travellerFrame = new TravellerFrame(this);
        travellerFrame.setLocationRelativeTo(null);
        travellerFrame.setVisible(true);
        
        passengers = new ArrayList();
        guests = new ArrayList();
        
    }
    
    public void searchFlights (FlightSearch flightSearch){
        try {
            passengers.clear();
            for(int i = 0; i< flightSearch.getNumberOfPassengers(); i++ ){
                passengers.add(new Customer("Diogo Freitas", 21));
            }
            //skyscannerReference.searchFlights(flightSearch,this);
            
            URIBuilder uRIBuilder = new URIBuilder(_URL_ + "/search/flights");
            uRIBuilder.addParameter("origin", flightSearch.getOrigin());
            uRIBuilder.addParameter("destination", flightSearch.getDestination());
            uRIBuilder.addParameter("departureDate", flightSearch.getDepartureDate());
            uRIBuilder.addParameter("numberOfPassengers", ""+passengers.size());
            
            String urlString = uRIBuilder.build().toString();
            System.out.println(urlString);
            String response = httpConnector.sendGet(urlString);
            System.out.println("Response:" + response);
            
            if(flightSearch.getRoundTrip()){
                uRIBuilder = new URIBuilder(_URL_ + "/search/flights");
                uRIBuilder.addParameter("destination", flightSearch.getOrigin());
                uRIBuilder.addParameter("origin", flightSearch.getDestination());
                uRIBuilder.addParameter("departureDate", flightSearch.getReturnDate());
                uRIBuilder.addParameter("numberOfPassengers", ""+passengers.size());

                urlString = uRIBuilder.build().toString();
                System.out.println(urlString);
                response = httpConnector.sendGet(urlString);
                System.out.println("Response:" + response);
            }
            
        } catch (URISyntaxException ex) {
            Logger.getLogger(TravellerServant.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    public void bookFlight(String departureFlightNumber, String returnFlightNumber, String departureDate, String returnDate){
        /*
        try {
            skyscannerReference.bookFlight(new FlightBooking( departureFlightNumber,
                    returnFlightNumber, departureDate, returnDate, true, passengers), this);
        } catch (RemoteException ex) {
            Logger.getLogger(TravellerServant.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }

    public void searchHotels(HotelSearch hotelSearch){

        try {
            guests.clear();
            for(int i = 0; i < hotelSearch.getNumberOfRooms(); i++){
                guests.add(new Customer("Diego Lee", 23));
            }
            //skyscannerReference.searchHotels(hotelSearch, this);
            hotelCheckin = hotelSearch.getCheckInDate();
            hotelCheckout = hotelSearch.getCheckOutDate();
            
            URIBuilder uRIBuilder = new URIBuilder(_URL_ + "/search/hotels");
            uRIBuilder.addParameter("city", hotelSearch.getCity());
            uRIBuilder.addParameter("checkInDate", hotelSearch.getCheckInDate());
            uRIBuilder.addParameter("checkOutDate", ""+ hotelSearch.getCheckOutDate());
            uRIBuilder.addParameter("numberOfRooms", ""+hotelSearch.getNumberOfRooms());

            /*
            String urlString = uRIBuilder.build().toString();
            System.out.println(urlString);
            String response = httpConnector.sendGet(urlString);
            System.out.println("Response:" + response);
            */
            
        } catch (URISyntaxException ex) {
            Logger.getLogger(TravellerServant.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void bookHotel(String hotelId){
        /*
        try {
            skyscannerReference.bookHotel(new HotelBooking(hotelId, hotelCheckin, hotelCheckout, guests.size(), guests), this);
        } catch (RemoteException ex) {
            Logger.getLogger(TravellerServant.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }
    
    public void invokeLaterMessageDialog(String message){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JOptionPane.showMessageDialog(null, message);
            }
        });        
    }

    public void displayFlightBookingConfirmation(FlightBooking flightBooking, boolean confirmation) throws RemoteException {
        if(confirmation){
            System.out.println("---Flight booking confirmed!---");
            invokeLaterMessageDialog("Flight booking confirmed!");
        } else {
            System.out.println("---No more seats available!---");
            invokeLaterMessageDialog("No more seats available!");
        }
    }

    public void displayHotelBookingConfirmation(HotelBooking hotelBooking, boolean confirmation) throws RemoteException {
        if(confirmation){
            System.out.println("---Hotel booking confirmed!---");
            invokeLaterMessageDialog("Hotel booking confirmed!");
        } else {
            System.out.println("---No more rooms available!---");
            invokeLaterMessageDialog("No more rooms available!");
        }
    }
}
