package examples2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class UseTCP {
	public static void main(String[] args) throws InterruptedException, IOException {
		final InetAddress	addr = Inet4Address.getLocalHost();
		
		final Thread	t = new Thread(new Runnable(){
				@Override
				public void run() {
					try(final ServerSocket 		ss = new ServerSocket(12000)) {	// <1>
						try(final Socket		sock = ss.accept();				// <2>
							final InputStream	is = sock.getInputStream();		// <3>
							final OutputStream	os = sock.getOutputStream();	// <4>
							final Reader 		rdr = new InputStreamReader(is);
							final BufferedReader	brdr = new BufferedReader(rdr);
							final PrintStream	ps = new PrintStream(os)) {

							ps.println(brdr.readLine().toUpperCase());	// <5>
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		);
		t.setDaemon(true);		t.start();
		
		try(final Socket 		sock = new Socket(addr,12000);	// <6>
			final InputStream	is = sock.getInputStream();
			final OutputStream	os = sock.getOutputStream();
			final Reader 		rdr = new InputStreamReader(is);
			final BufferedReader	brdr = new BufferedReader(rdr);
			final PrintStream	ps = new PrintStream(os)) {
			
			ps.println("test string");	// <7>
			System.err.println("Read: "+brdr.readLine());	// <8>
		}
	}
}
