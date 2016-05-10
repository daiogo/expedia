/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyscanner;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Skyscanner {

    private SkyscannerServant skyscannerServantReference;
    private Registry namingServiceReference;
    
    public Skyscanner() {
        
    }
    
    public void initialize() throws RemoteException, AlreadyBoundException {
        namingServiceReference = LocateRegistry.createRegistry(1099);
        skyscannerServantReference = new SkyscannerServant();
        namingServiceReference.bind("skyscanner", skyscannerServantReference);
    }
    
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        try {
            Skyscanner s = new Skyscanner();
            s.initialize();
        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(Skyscanner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
