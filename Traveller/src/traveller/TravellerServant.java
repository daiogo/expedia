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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diogo
 */
public class TravellerServant extends UnicastRemoteObject implements TravellerInterface {
    
    public TravellerServant (Registry namingServiceReference) throws RemoteException {
        try {
            SkyscannerInterface skyscannerReference = (SkyscannerInterface) namingServiceReference.lookup("skyscanner");
            skyscannerReference.subscribe("LATAM", this);
        } catch (NotBoundException | AccessException ex) {
            Logger.getLogger(TravellerServant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void publish(String message) throws RemoteException {
        System.out.println("Message: " + message);
    }
}
