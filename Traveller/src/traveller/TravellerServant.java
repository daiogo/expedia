/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traveller;

import interfaces.SkyscannerInterface;
import interfaces.TravellerInterface;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
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
import messages.FlightSubscription;
import messages.Hotel;
import messages.HotelBooking;
import messages.HotelSearch;
import messages.HotelSubscription;

/**
 *
 * @author Diogo
 */
public class TravellerServant extends UnicastRemoteObject implements TravellerInterface {
    
    private SkyscannerInterface skyscannerReference;
    private ArrayList<Hotel> hotels;
    private TravellerFrame travellerFrame;
    private FlightSearchResultsFrame flightSearchResultsFrame;
    private HotelSearchResultsFrame hotelSearchResultsFrame;
    ArrayList<Customer> passengers;
    ArrayList<Customer> guests;
    String hotelCheckin;
    String hotelCheckout;
    HttpConnector httpConnector = new HttpConnector();
    
    public TravellerServant (Registry namingServiceReference) throws RemoteException {
        try {
            this.skyscannerReference = (SkyscannerInterface) namingServiceReference.lookup("skyscanner");
        } catch (NotBoundException | AccessException ex) {
            Logger.getLogger(TravellerServant.class.getName()).log(Level.SEVERE, null, ex);
        }
        travellerFrame = new TravellerFrame(this);
        travellerFrame.setLocationRelativeTo(null);
        travellerFrame.setVisible(true);
        
        passengers = new ArrayList();
        guests = new ArrayList();
        
    }
    
    public void searchFlights (FlightSearch flightSearch) {
        try {
            passengers.clear();
            for(int i = 0; i< flightSearch.getNumberOfPassengers(); i++ ){
                passengers.add(new Customer("Diogo Freitas", 21));
            }
            skyscannerReference.searchFlights(flightSearch,this);
        } catch (RemoteException ex) {
            Logger.getLogger(TravellerServant.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    @Override
    public void publish(String message) throws RemoteException {
        System.out.println("Message: " + message);
    }

    public void bookFlight(String departureFlightNumber, String returnFlightNumber, String departureDate, String returnDate){
        try {
            skyscannerReference.bookFlight(new FlightBooking( departureFlightNumber,
                    returnFlightNumber, departureDate, returnDate, true, passengers), this);
        } catch (RemoteException ex) {
            Logger.getLogger(TravellerServant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Show Flight Search results creating a FlightSearchResultsFrame
     * This method is called from Server
     * @param departingFlights
     * @param returningFlights
     * @throws RemoteException
     */
    @Override
    public void getQueriedFlights(ArrayList<Flight> departingFlights, ArrayList<Flight> returningFlights) throws RemoteException {
        
        flightSearchResultsFrame = new FlightSearchResultsFrame(this,departingFlights,returningFlights);
        flightSearchResultsFrame.setLocationRelativeTo(null);
        flightSearchResultsFrame.setVisible(true);
        
        System.out.println("------------Departing flights------------");
        for (Flight flight : departingFlights) {
            System.out.println("Flight number: " + flight.getFlightNumber());
            System.out.println("Airline: " + flight.getAirline());
            System.out.println("Origin: " + flight.getOrigin());
            System.out.println("Destination: " + flight.getDestination());
            System.out.println("Departure date: " + flight.getDepartureDate());
            System.out.println("Departure time: " + flight.getDepartureTime());
            System.out.println("Arrival time: " + flight.getArrivalTime());
            System.out.println("Airfare: " + flight.getAirfare());
            System.out.println("-----------------------------------------");
        }
        
        System.out.println("------------Returning flights------------");
        for (Flight flight : returningFlights) {
            System.out.println("Flight number: " + flight.getFlightNumber());
            System.out.println("Airline: " + flight.getAirline());
            System.out.println("Origin: " + flight.getOrigin());
            System.out.println("Destination: " + flight.getDestination());
            System.out.println("Departure date: " + flight.getDepartureDate());
            System.out.println("Departure time: " + flight.getDepartureTime());
            System.out.println("Arrival time: " + flight.getArrivalTime());
            System.out.println("Airfare: " + flight.getAirfare());
            System.out.println("-----------------------------------------");
        }
    }

    public void searchHotels(HotelSearch hotelSearch){
        try {
            guests.clear();
            for(int i = 0; i < hotelSearch.getNumberOfRooms(); i++){
                guests.add(new Customer("Diego Lee", 23));
            }
            skyscannerReference.searchHotels(hotelSearch, this);
            hotelCheckin = hotelSearch.getCheckInDate();
            hotelCheckout = hotelSearch.getCheckOutDate();
        } catch (RemoteException ex) {
            Logger.getLogger(TravellerServant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void getQueriedHotels(ArrayList<Hotel> hotels) throws RemoteException {
        this.hotels = hotels;
        
        hotelSearchResultsFrame = new HotelSearchResultsFrame(this, hotels);
        hotelSearchResultsFrame.setLocationRelativeTo(null);
        hotelSearchResultsFrame.setVisible(true);
        
        System.out.println("-----------------Hotels-----------------");
        for (Hotel hotel : hotels) {
            System.out.println("Hotel name: " + hotel.getHotelName());
            System.out.println("City: " + hotel.getCity());
            System.out.println("Price: " + hotel.getPricePerNight());
            System.out.println("Rooms: " + hotel.getAvailableRooms());
            System.out.println("-----------------------------------------");
        }
    }
    
    public void bookHotel(String hotelId){
        try {
            skyscannerReference.bookHotel(new HotelBooking(hotelId, hotelCheckin, hotelCheckout, guests.size(), guests), this);
        } catch (RemoteException ex) {
            Logger.getLogger(TravellerServant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void invokeLaterMessageDialog(String message){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JOptionPane.showMessageDialog(null, message);
            }
        });        
    }
    
    @Override
    public void displayFlightBookingConfirmation(FlightBooking flightBooking, boolean confirmation) throws RemoteException {
        if(confirmation){
            System.out.println("---Flight booking confirmed!---");
            invokeLaterMessageDialog("Flight booking confirmed!");
        } else {
            System.out.println("---No more seats available!---");
            invokeLaterMessageDialog("No more seats available!");
        }
        
    }

    @Override
    public void displayHotelBookingConfirmation(HotelBooking hotelBooking, boolean confirmation) throws RemoteException {
        if(confirmation){
            System.out.println("---Hotel booking confirmed!---");
            invokeLaterMessageDialog("Hotel booking confirmed!");
        } else {
            System.out.println("---No more rooms available!---");
            invokeLaterMessageDialog("No more rooms available!");
        }
        
    }

    @Override
    public void displayFlightNotification(FlightSubscription subscription, Flight flight) throws RemoteException {
        System.out.println("---Flight offer!---");
        invokeLaterMessageDialog("You have a new flight offer!");
    }

    @Override
    public void displayHotelNotification(HotelSubscription subscription, Hotel hotel) throws RemoteException {
        System.out.println("---Hotel offer!---");
        invokeLaterMessageDialog("You have a new hotel offer!");
    }
    
    public void registerFlightInterest(FlightSubscription subscription) throws RemoteException {
        skyscannerReference.subscribeToFlight(subscription, this);
        invokeLaterMessageDialog("Flight Interest Registered");
    }
    
    public void registerHotelInterest(HotelSubscription subscription) throws RemoteException {
        skyscannerReference.subscribeToHotel(subscription, this);
        invokeLaterMessageDialog("Hotel Interest Registered");
    }
}
