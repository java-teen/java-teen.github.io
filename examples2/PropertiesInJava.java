package examples2;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Properties;

public class PropertiesInJava {
	private static final String	CONTENT = "key1=value1\nkey2=value2"; 

	public static void main(String[] args) throws IOException {
		final Properties	props = new Properties(); 	// <1>
		
		try(final Reader	reader = new StringReader(CONTENT)) {	// <2>
			props.load(reader);
		}
		System.err.println("Key1 = "+props.getProperty("key1"));	// <3>
		System.err.println("Key2 = "+props.getProperty("key2"));
		System.err.println("Key3 = "+props.getProperty("key3","default3"));
	}
}
