package examples2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Calculator {
	public static void main(String[] args) {
		try(final InputStream		is = Calculator.class.getResourceAsStream("calc.txt");	// <1>
			final Reader			rdr = new InputStreamReader(is);	// <2>
			final BufferedReader	brdr = new BufferedReader(rdr)) {	// <3>
			String					line;
			
			while ((line = brdr.readLine()) != null) {	// <4>
				int	sum = 0;
				
				for (String item : (line.startsWith("-") ? "0"+line : line).replace("-", "+-").split("\\+")) {	// <5>
					sum += Integer.valueOf(item);	// <6>
				}
				System.err.print(line);				// <7>
				System.err.print('=');
				System.err.println(sum);
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
