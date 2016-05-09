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
import messages.Flight;
import messages.FlightSearch;
import messages.HotelSearch;

/**
 *
 * @author Diogo
 */
public class SkyscannerServant extends UnicastRemoteObject implements SkyscannerInterface {

    // Instantiate GUI here!
    private Database database;
    
    public SkyscannerServant() throws RemoteException {
        this.database = new Database();
        database.getFlights().add(new Flight("JJ2020", "TAM", "Curitiba", "São Paulo", "01/01/2016", "11:30am", "12:30pm", 61.50, 100));
        database.getFlights().add(new Flight("JJ2021", "TAM", "Curitiba", "Rio de Janeiro", "01/01/2016", "12:00pm", "13:20pm", 76.25, 100));
        database.getFlights().add(new Flight("JJ2022", "TAM", "Rio de Janeiro", "Curitiba", "07/01/2016", "12:00pm", "13:20pm", 76.25, 100));
        database.getFlights().add(new Flight("JJ2023", "TAM", "São Paulo", "Curitiba", "07/01/2016", "11:30am", "12:30pm", 61.50, 100));
        database.getFlights().add(new Flight("JJ2024", "TAM", "São Paulo", "Curitiba", "07/01/2016", "15:30am", "16:30pm", 61.50, 100));
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
            else if (flightSearch.isRoundTrip() &&
                     flightSearch.getDestination().equals(flight.getOrigin()) &&
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void subscribe(String subscribeTo, TravellerInterface travellerInterface) throws RemoteException {
        travellerInterface.publish(subscribeTo);
    }
    
    
    
}
