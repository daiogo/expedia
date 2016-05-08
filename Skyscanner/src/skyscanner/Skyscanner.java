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

public class Skyscanner {

    private AdminGui adminGui;
    
    public Skyscanner() {
        this.adminGui = new AdminGui(this);
        adminGui.setVisible(true);
    }
    
    public void run() throws RemoteException, AlreadyBoundException {
        Registry namingServiceReference = LocateRegistry.createRegistry(1099);
        SkyscannerServant skyscannerServantReference = new SkyscannerServant();
        namingServiceReference.bind("skyscanner", skyscannerServantReference);
    }
    
    public static void main(String[] args) {
        Skyscanner s = new Skyscanner();
    }

    
}
