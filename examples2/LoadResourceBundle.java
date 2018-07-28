package examples2;

import java.util.ResourceBundle;
import java.util.ServiceLoader;

public class LoadResourceBundle {
	public static void main(String[] args) {
		final ResourceBundle bundle = ResourceBundle.getBundle("examples2.MyResourceBundle");	// <1>
		
		System.err.println("Key[1]: "+bundle.getString("key"));	// <2>
		
		ResourceBundle newBundle = null;	// <3>		
		for (ResourceBundle item : ServiceLoader.load(ResourceBundle.class)) {
			newBundle = item;
			break;
		}
		if (newBundle != null) {			// <4>
			System.err.println("Key[2]: "+newBundle.getString("key"));
		}
	}
}
