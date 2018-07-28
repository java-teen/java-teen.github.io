package examples5;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ExternalProcess {

	private static void output(final InputStream in, final OutputStream out) {	// <1>
		final byte[]	buffer = new byte[8192];		
		int				len;
		
		try{while ((len = in.read(buffer)) > 0) {
				out.write(buffer,0,len);
			}
		} catch (IOException e) {
			System.err.println("I/O error: "+e.getMessage());
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		final Process	p = new ProcessBuilder()	// <2>
								.command("java.exe","examples5.PseudoReplace","a","A")
								.directory(new File("target/classes"))
								.start();
		final Thread	tout = new Thread(()->output(p.getInputStream(),System.out));	// <3>
		final Thread	terr = new Thread(()->output(p.getErrorStream(),System.err));
	
		tout.setDaemon(true);	tout.start();	// <4>
		terr.setDaemon(true);	terr.start();
		
		try(final PrintWriter	wr = new PrintWriter(p.getOutputStream())) {	// <5>
			wr.println("abcde");
			wr.flush();
		}
		
		System.err.println("retCode = "+p.waitFor());	// <6>
	}
}
