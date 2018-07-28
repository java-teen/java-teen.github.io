package examples2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;

public class UseUDP {
	public static void main(String[] args) throws InterruptedException, IOException {
		final InetAddress	addr = Inet4Address.getLocalHost();
		final Thread	t = new Thread(new Runnable(){
				@Override
				public void run() {
					try(final DatagramSocket 	ds = new DatagramSocket(12000)) {	// <1>
						final byte[]			buf = new byte[1024];				// <2>
						final DatagramPacket	pack = new DatagramPacket(buf,buf.length);
						
						ds.receive(pack);		// <3>
						System.err.println("Receive: "+new String(pack.getData(),0,pack.getLength())); // <4>
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		);
		t.setDaemon(true);		t.start();
		
		try(final DatagramSocket 	ds = new DatagramSocket(12001)) {	// <5>
			final byte[]			buf = new byte[1024];
			final DatagramPacket	pack = new DatagramPacket(buf,buf.length);
			
			pack.setData("test string".getBytes());	// <6>
			pack.setAddress(addr);	
			pack.setPort(12000);
			ds.send(pack);		// <7>
			System.err.println("Send data...");
		}
	}
}
