/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Diogo
 */
public interface SkyscannerInterface extends Remote {
    public void bookFlight(TravellerInterface travellerInterface) throws RemoteException;
    public void bookHotel(TravellerInterface travellerInterface) throws RemoteException;
    public void searchFlights(TravellerInterface travellerInterface) throws RemoteException;
    public void searchHotels(TravellerInterface travellerInterface) throws RemoteException;
    public void subscribe(String subscribeTo, TravellerInterface travellerInterface) throws RemoteException;
}
