/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyscanner;

import interfaces.SkyscannerInterface;
import interfaces.TravellerInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import messages.FlightSearch;
import messages.HotelSearch;

/**
 *
 * @author Diogo
 */
public class SkyscannerServant extends UnicastRemoteObject implements SkyscannerInterface {

    private Database database;
    
    public SkyscannerServant() throws RemoteException {
        this.database = new Database();
    }

    @Override
    public void bookFlight(TravellerInterface travellerInterface) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void bookHotel(TravellerInterface travellerInterface) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void searchFlights(FlightSearch flightSearch, TravellerInterface travellerInterface) throws RemoteException {
        ArrayList<Flight> departingFlights = new ArrayList();
        ArrayList<Flight> returningFlights = new ArrayList();
        
        for (Flight flight : database.getFlights()) {
            if (flightSearch.getOrigin().equals(flight.getOrigin()) &&
                flightSearch.getDestination().equals(flight.getDestination()) &&
                flightSearch.getDepartureDate().equals(flight.getDepartureDate())
                ) {
                departingFlights.add(flight);
            }
            else if (flightSearch.getDestination().equals(flight.getOrigin()) &&
                     flightSearch.getOrigin().equals(flight.getDestination()) &&
                     flightSearch.getReturnDate().equals(flight.getDepartureDate())
                     ) {
                returningFlights.add(flight);
            }
        }
        
        travellerInterface.getQueriedFlights(departingFlights, returningFlights);
    }

    @Override
    public void searchHotels(HotelSearch hotelSearch, TravellerInterface travellerInterface) throws RemoteException {

    }

    @Override
    public void subscribe(String subscribeTo, TravellerInterface travellerInterface) throws RemoteException {
        travellerInterface.publish(subscribeTo);
    }
    
    
    
}
