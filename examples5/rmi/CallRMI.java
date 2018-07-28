package examples5.rmi;

import java.io.File;

public class CallRMI {
	public static final int		PORT = 10000;

	public static void main(String[] args) throws Exception {
		System.setProperty("java.rmi.server.codebase",new File("./target/classes").toURI().toString());	// <1>
		SqrtServerImpl.createAndRegisterObject();	// <2>
		SqrtCient.callRMI();						// <3>
		SqrtServerImpl.unregisterObject();			// <4>
	}
}
