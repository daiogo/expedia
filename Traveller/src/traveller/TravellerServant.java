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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import messages.Customer;
import messages.Flight;
import messages.FlightBooking;
import messages.FlightSearch;
import messages.Hotel;
import messages.HotelBooking;
import messages.HotelSearch;

/**
 *
 * @author Diogo
 */
public class TravellerServant extends UnicastRemoteObject implements TravellerInterface {
    
    private SkyscannerInterface skyscannerReference;
    private ArrayList<Flight> departingFlights;
    private ArrayList<Flight> returningFlights;
    private ArrayList<Hotel> hotels;
    private TravellerFrame travellerFrame;
    
    public TravellerServant (Registry namingServiceReference) throws RemoteException {
        try {
            this.skyscannerReference = (SkyscannerInterface) namingServiceReference.lookup("skyscanner");
        } catch (NotBoundException | AccessException ex) {
            Logger.getLogger(TravellerServant.class.getName()).log(Level.SEVERE, null, ex);
        }
        travellerFrame = new TravellerFrame(this);
        travellerFrame.setVisible(true);
        travellerFrame.setLocationRelativeTo(null);
    }
    
    public void searchFlights (FlightSearch flightSearch) {
        try {
            skyscannerReference.searchFlights(flightSearch,this);
        } catch (RemoteException ex) {
            Logger.getLogger(TravellerServant.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 

    public void run() throws RemoteException {
        try {
            ArrayList<Customer> passengers = new ArrayList();
            passengers.add(new Customer("Diogo Freitas", 21));
            ArrayList<Customer> guests = new ArrayList();
            guests.add(new Customer("Diogo Freitas", 21));
            
            searchFlights(new FlightSearch("Curitiba", "São Paulo", true, "01/01/2016", "07/01/2016", 1));
            skyscannerReference.searchHotels(new HotelSearch("São Paulo", 1, "01/01/2016", "07/01/2016"), this);
            //skyscannerReference.bookFlight(new FlightBooking("JJ2020", "JJ2023", "01/01/2016", "07/01/2016", true, passengers), this);
            skyscannerReference.bookHotel(new HotelBooking(hotels.get(0).getHotelId(), "01/01/2016", "07/01/2016", 1, guests), this);
            searchFlights(new FlightSearch("Curitiba", "São Paulo", true, "01/01/2016", "07/01/2016", 1));
            skyscannerReference.searchHotels(new HotelSearch("São Paulo", 1, "01/01/2016", "07/01/2016"), this);
            
        } catch (AccessException ex) {
            Logger.getLogger(TravellerServant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void publish(String message) throws RemoteException {
        System.out.println("Message: " + message);
    }

    @Override
    public void getQueriedFlights(ArrayList<Flight> departingFlights, ArrayList<Flight> returningFlights) throws RemoteException {
        this.departingFlights = departingFlights;
        this.returningFlights = returningFlights;
        
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

    @Override
    public void getQueriedHotels(ArrayList<Hotel> hotels) throws RemoteException {
        this.hotels = hotels;
        
        System.out.println("-----------------Hotels-----------------");
        for (Hotel hotel : hotels) {
            System.out.println("Hotel name: " + hotel.getHotelName());
            System.out.println("City: " + hotel.getCity());
            System.out.println("-----------------------------------------");
        }
    }

    @Override
    public void displayFlightBookingConfirmation(FlightBooking flightBooking) throws RemoteException {
        System.out.println("---Flight booking confirmed!---");
        //JOptionPane.showMessageDialog(new JFrame("Booking confirmation"), "Your booking is confirmed");
    }

    @Override
    public void displayHotelBookingConfirmation(HotelBooking hotelBooking) throws RemoteException {
        System.out.println("---Hotel booking confirmed!---");
        //JOptionPane.showMessageDialog(new JFrame("Booking confirmation"), "Your booking is confirmed");
    }
}
