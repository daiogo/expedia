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

/**
 *
 * @author Diogo
 */
public class SkyscannerServant extends UnicastRemoteObject implements SkyscannerInterface {

    public SkyscannerServant() throws RemoteException {
    
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
    public void searchFlights(TravellerInterface travellerInterface) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void searchHotels(TravellerInterface travellerInterface) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void subscribe(String subscribeTo, TravellerInterface travellerInterface) throws RemoteException {
        travellerInterface.publish(subscribeTo);
    }
    
    
    
}
