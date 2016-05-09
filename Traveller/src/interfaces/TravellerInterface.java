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
import messages.Hotel;

/**
 *
 * @author Diogo
 */
public interface TravellerInterface extends Remote {
    public void publish(String message) throws RemoteException;
    public void getQueriedFlights(ArrayList<Flight> departingFlights, ArrayList<Flight> returningFlights) throws RemoteException;
    public void getQueriedHotels(ArrayList<Hotel> hotels) throws RemoteException;
}
