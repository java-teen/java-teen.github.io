package examples5.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Sqrt extends Remote {	// <1>
    double sqrt(double value) throws RemoteException;	// <2>
}
