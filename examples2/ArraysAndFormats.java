package examples2;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public class ArraysAndFormats {
	private static final String		CONTENT = "10\n-30\n21";

	public static void main(String[] args) throws IOException {
		final List<Integer>	content = new ArrayList<>();	// <1>
		
		try(final Reader	rdr = new StringReader(CONTENT)) {	// <2>
			final Scanner	intValues = new Scanner(rdr).useDelimiter("\n");
			
			while (intValues.hasNextInt()) {				// <3>
				content.add(intValues.nextInt());
			}
		}
		final Integer[]		result = content.toArray(new Integer[content.size()]);	// <4>
		Arrays.sort(result);
		
		final Formatter		formatter = new Formatter();	// <5>
		for (int item : result) {
			formatter.format("Value=%1$d\n",item);
		}
		
		System.err.println(formatter);		// <6>
	}
}
