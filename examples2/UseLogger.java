package examples2;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class UseLogger {
	private static Logger		logger;	// = Logger.getLogger("MyLogger");	<1>  

	public static void main(String[] args) throws IOException {
		try(final InputStream	is = UseLogger.class.getResourceAsStream("logger.conf")) {
			LogManager.getLogManager().readConfiguration(is);	// <2>
		}
		logger = Logger.getLogger("MyLogger");	// <3>
		
		if (logger.isLoggable(Level.INFO)) {	// <4>
			logger.log(Level.INFO,"Test message");	// <5>
		}
		logger.log(Level.SEVERE,"Test error", new RuntimeException("Simulate error"));	// <6>
	}
}
