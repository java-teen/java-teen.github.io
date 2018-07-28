package examples5.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class SqrtServerImpl implements Sqrt {

	@Override
	public double sqrt(double value) throws RemoteException {
		return Math.sqrt(value);
	}

	public static void createAndRegisterObject() {
        try{final SqrtServerImpl	obj = new SqrtServerImpl();		// <1>
            final Sqrt 				stub = (Sqrt) UnicastRemoteObject.exportObject(obj, 0);	// <2>
            final Registry 			registry = LocateRegistry.getRegistry(CallRMI.PORT);	// <3>
        	
			registry.bind("Sqrt", stub);		// <4>
			System.err.println("Server side was registered");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void unregisterObject() {
        try{final Registry 			registry = LocateRegistry.getRegistry(CallRMI.PORT);
	    	
			registry.unbind("Sqrt");			// <5>
			System.err.println("Server side was removed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
