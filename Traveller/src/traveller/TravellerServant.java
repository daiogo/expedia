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
import messages.FlightSearch;

/**
 *
 * @author Diogo
 */
public class TravellerServant extends UnicastRemoteObject implements TravellerInterface {
    
    private SkyscannerInterface skyscannerReference;
    
    public TravellerServant (Registry namingServiceReference) throws RemoteException {
        try {
            this.skyscannerReference = (SkyscannerInterface) namingServiceReference.lookup("skyscanner");
        } catch (NotBoundException | AccessException ex) {
            Logger.getLogger(TravellerServant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() throws RemoteException {
        skyscannerReference.searchFlights(new FlightSearch("Curitiba", "São Paulo", true, "01/01/2016", "07/01/2016"), this);
    }
    
    @Override
    public void publish(String message) throws RemoteException {
        System.out.println("Message: " + message);
    }

    @Override
    public void getQueriedFlights(ArrayList<Flight> departingFlights, ArrayList<Flight> returningFlights) throws RemoteException {
        System.out.println("----------Departing flights----------");
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
        
        System.out.println("----------Returning flights----------");
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
