/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyscanner;

import interfaces.SkyscannerInterface;
import interfaces.TravellerInterface;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Diogo
 */
public class Skyscanner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Registry namingServiceReference = LocateRegistry.createRegistry(1099);
        SkyscannerServant skyscannerServantReference = new SkyscannerServant();
        namingServiceReference.bind("skyscanner", skyscannerServantReference);
    }

    
}
