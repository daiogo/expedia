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
import messages.Hotel;
import messages.HotelBooking;

/**
 *
 * @author Diogo
 */
public interface TravellerInterface extends Remote {
    public void getQueriedFlights(ArrayList<Flight> departingFlights, ArrayList<Flight> returningFlights) throws RemoteException;
    public void getQueriedHotels(ArrayList<Hotel> hotels) throws RemoteException;
    public void displayFlightBookingConfirmation(FlightBooking flightBooking) throws RemoteException;
    public void displayHotelBookingConfirmation(HotelBooking hotelBooking) throws RemoteException;
    public void displayFlightNotification(Flight flight) throws RemoteException;
    public void displayHotelNotification(Hotel hotel) throws RemoteException;
}
