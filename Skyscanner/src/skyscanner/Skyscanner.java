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

    private AdminGui adminGui;
    
    public Skyscanner() {
        this.adminGui = new AdminGui(this);
        adminGui.setVisible(true);
    }
    
    public void initialize() throws RemoteException, AlreadyBoundException {
        Registry namingServiceReference = LocateRegistry.createRegistry(1099);
        SkyscannerServant skyscannerServantReference = new SkyscannerServant();
        namingServiceReference.bind("skyscanner", skyscannerServantReference);
    }
    
    public static void main(String[] args) {
        try {
            Skyscanner s = new Skyscanner();
            s.initialize();
        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(Skyscanner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
