package examples5.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SqrtCient {
	public static void callRMI() {
            
        try{final Registry 	registry = LocateRegistry.getRegistry("localhost",CallRMI.PORT);	// <1>
            final Sqrt		stub = (Sqrt) registry.lookup("Sqrt");		// <2>
            
			System.err.println("SQRT(25)=" + stub.sqrt(25));			// <3>
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
