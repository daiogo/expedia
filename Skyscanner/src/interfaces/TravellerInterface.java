/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import messages.Flight;
import messages.FlightBooking;
import messages.FlightSubscription;
import messages.Hotel;
import messages.HotelBooking;
import messages.HotelSubscription;

/**
 *
 * @author Diogo
 */
public interface TravellerInterface extends Remote {
    public void getQueriedFlights(ArrayList<Flight> departingFlights, ArrayList<Flight> returningFlights) throws RemoteException;
    public void getQueriedHotels(ArrayList<Hotel> hotels) throws RemoteException;
    public void displayFlightBookingConfirmation(FlightBooking flightBooking, boolean confirmation) throws RemoteException;
    public void displayHotelBookingConfirmation(HotelBooking hotelBooking, boolean confirmation) throws RemoteException;
    public void displayFlightNotification(FlightSubscription subscription, Flight flight) throws RemoteException;
    public void displayHotelNotification(HotelSubscription subscription, Hotel hotel) throws RemoteException;
}
