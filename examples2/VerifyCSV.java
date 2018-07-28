package examples2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.regex.Pattern;

public class VerifyCSV {
	public static void main(String[] args) throws IOException {
		final Pattern	p = Pattern.compile("\\s*\\d+\\s*\\,\\s*\\\"[^\\\"]*\\\"\\s*\\,\\s*(\\-)*\\d+(\\.\\d+)?");	// <1>
		
		try(final InputStream		is = VerifyCSV.class.getResourceAsStream("data.csv");	// <2>
			final Reader			rdr = new InputStreamReader(is);
			final BufferedReader	brdr = new BufferedReader(rdr)) {
			final long				start = System.currentTimeMillis();
			
			String					line;
			int						lineNo = 1, errNo = 0;
			
			while ((line = brdr.readLine()) != null) {
				if (!p.matcher(line).matches()) {		// <3>
					System.err.println("Line "+lineNo+" is not verified");
					errNo++;
				}
				lineNo++;
			}
			System.err.println("Processing complete, "+(lineNo-1)+" lines were processed, "+errNo+" errors detected, duration = "+(System.currentTimeMillis()-start)+" msec");
		}
	}

}
