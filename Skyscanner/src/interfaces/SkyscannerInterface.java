/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import messages.FlightBooking;
import messages.FlightSearch;
import messages.FlightSubscription;
import messages.HotelBooking;
import messages.HotelSearch;
import messages.HotelSubscription;

/**
 *
 * @author Diogo
 */
public interface SkyscannerInterface extends Remote {
    public void bookFlight(FlightBooking booking, TravellerInterface travellerInterface) throws RemoteException;
    public void bookHotel(HotelBooking booking, TravellerInterface travellerInterface) throws RemoteException;
    public void searchFlights(FlightSearch flightSearch, TravellerInterface travellerInterface) throws RemoteException;
    public void searchHotels(HotelSearch hotelSearch, TravellerInterface travellerInterface) throws RemoteException;
    public void subscribeToFlight(FlightSubscription subscription, TravellerInterface travellerInterface) throws RemoteException;
    public void subscribeToHotel(HotelSubscription subscription, TravellerInterface travellerInterface) throws RemoteException;
    
}
