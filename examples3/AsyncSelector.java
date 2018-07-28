package examples3;

import java.nio.channels.ServerSocketChannel;

import java.nio.channels.SocketChannel;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class AsyncSelector {
    public static final InetSocketAddress 	HOST_ADDRESS = new InetSocketAddress("localhost",2345);

	public static void main(String[] args) {	// <1>
		final Thread	t = new Thread(new Runnable(){
							@Override
							public void run() {
								startSelector();
							}
						});
		t.setDaemon(true);
		t.start();

		try(SocketChannel client = SocketChannel.open(HOST_ADDRESS)) {	// <2>
			for (int index = 0, maxIndex = 3; index < maxIndex; index++) {
			     final ByteBuffer 	buffer = ByteBuffer.wrap(String.format("Message %f",Math.random()).getBytes());
			     
			     client.write(buffer);
			     System.err.println("Send "+(index+1)+" of "+maxIndex);
			     buffer.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	private static void startSelector() {
        try(final Selector selector = Selector.open();	// <3>
        	final ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
        	
	        serverSocket.bind(HOST_ADDRESS);
	        serverSocket.configureBlocking(false);	// <4>
	        serverSocket.register(selector,serverSocket.validOps(), null);
	        
	        for (;;) {
	            final int noOfKeys = selector.select();	// <5>
	            
	            if (noOfKeys > 0) {
		            System.out.println("Number of items in the selector: " + noOfKeys);
		            
					for (SelectionKey ky : selector.selectedKeys()) {	// <6>
		                if (ky.isAcceptable()) {	// <7>
		                    final SocketChannel client = serverSocket.accept();
		                    
		                    client.configureBlocking(false);
		                    client.register(selector, SelectionKey.OP_READ);
		                    System.out.println("Accepted new connection from client: " + client);
		                }
		                else if (ky.isReadable()) {	// <8>
		                    final SocketChannel client = (SocketChannel) ky.channel();
		                    final ByteBuffer buffer = ByteBuffer.allocate(128);
		                    
		                    client.read(buffer);
		                    System.err.println("Got message: " + new String(buffer.array()).trim());
		                }
		            }
					selector.selectedKeys().clear();	// <9>
	            }
	        }
        } catch (IOException e) {
			e.printStackTrace();
		}
	}
}



