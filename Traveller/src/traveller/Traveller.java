/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traveller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Diogo
 */
public class Traveller {

    public static void main(String[] args) throws RemoteException {
        Registry namingServiceReference = LocateRegistry.getRegistry(1099);
        TravellerServant travellerServantReference = new TravellerServant(namingServiceReference);
        
    }
    
}
