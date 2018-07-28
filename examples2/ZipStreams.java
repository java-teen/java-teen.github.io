package examples2;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipStreams {

	public static void main(String[] args) throws IOException {
		ZipEntry				ze;
		
		try(final ByteArrayOutputStream	baos = new ByteArrayOutputStream()) {
			try(final ZipOutputStream	zos = new ZipOutputStream(baos)) {			// <1>
				zos.putNextEntry(new ZipEntry("a/b/c/d"){{setMethod(ZipEntry.DEFLATED);}});	// <2>
				zos.write("test string 1".getBytes());	zos.flush();				// <3>
				zos.closeEntry();
				zos.putNextEntry(new ZipEntry("e"){{setMethod(ZipEntry.DEFLATED);}});	// <4>
				final PrintWriter	pw = new PrintWriter(zos);			// <5>
				pw.println("test string 2");	pw.flush();				// <6>
				zos.closeEntry();
			}
			
			try(final InputStream		is = new ByteArrayInputStream(baos.toByteArray());	// <7>
				final ZipInputStream	zis = new ZipInputStream(is)) {	// <8>
		
				while ((ze = zis.getNextEntry())!= null) {				// <9>
					final Reader			rdr = new InputStreamReader(zis);	// <10>
					final BufferedReader	brdr = new BufferedReader(rdr);
					System.err.println("Read part: "+ze.getName()+" and content: "+brdr.readLine());	// <11>
					zis.closeEntry();		// <12>
				}				
			}
		}
	}
}
