package examples2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DirManager {
	public static void copy(final File from, final File to, final boolean deleteAfterCopy) throws IOException {
		if (from.exists()) {		// <1>
			if (from.isFile()) {	// <2>
				try(final InputStream	is = new FileInputStream(from);
					final OutputStream	os = new FileOutputStream(to)) {
					final byte[]		buffer = new byte[8192];
					int					len;
					
					while ((len = is.read(buffer)) > 0) {	// <3>
						os.write(buffer,0,len);
					}
					os.flush();
				}
			}
			else {
				to.mkdirs();	// <4>
				for (File item : from.listFiles()) {	// <5>
					copy(new File(from,item.getName()),new File(to,item.getName()),deleteAfterCopy);
				}
			}
			if (deleteAfterCopy) {	// <6>
				from.delete();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		copy(new File("C:/tmp/from"),new File("C:/tmp/to"),true);	// <7>
	}
}
