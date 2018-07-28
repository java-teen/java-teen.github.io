package examples2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

public class UseFileURL {
	public static void main(String[] args) {
		try{final URL			fileURL = new URL("file:./");		// <1>
			final URLConnection	conn = fileURL.openConnection(); 	// <2>
			
			try(final InputStream		is = conn.getInputStream();	// <3>
				final Reader			rdr = new InputStreamReader(is);
				final BufferedReader	brdr = new BufferedReader(rdr)) {
				String	line;
				
				while ((line = brdr.readLine()) != null) {			// <4>
					System.err.println("readed: "+line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
