package examples3;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.Future;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.net.InetSocketAddress;

public class AsyncIO {
    public static final InetSocketAddress 	HOST_ADDRESS = new InetSocketAddress("localhost",2345);

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {	// <1>
		final Thread	t = new Thread(new ServerExample());
		
		t.setDaemon(true);
		t.start();

		new ClientExample().run();
	}
}

class ServerExample implements Runnable {
    public void run() {
        try(final AsynchronousServerSocketChannel 	serverChannel = AsynchronousServerSocketChannel.open()) {	// <2>
        	
	        serverChannel.bind(AsyncIO.HOST_ADDRESS);
	        System.out.println("Awaiting any connection...");
	        
	        final Future<AsynchronousSocketChannel> accepted = serverChannel.accept();	// <3>
	        
	        doSomethingUseful();
	        try(final AsynchronousSocketChannel clientChannel = accepted.get()) {		// <4>
		        if ((clientChannel != null) && (clientChannel.isOpen())) {
		        	for(;;) {
		                final ByteBuffer 		buffer = ByteBuffer.allocate(128);
		                final Future<Integer> 	readed = clientChannel.read(buffer);	// <5>
		
		                while (!readed.isDone()) {
		        	        doSomethingUseful();
		                }
		                buffer.flip();
		                System.out.println("Got : "+new String(buffer.array()).trim()+", length="+readed.get());	// <6>
		                buffer.clear();
		            }
		        }
			}
        } catch (InterruptedException | IOException | ExecutionException e) {
			e.printStackTrace();
		}
    }

    void doSomethingUseful() throws InterruptedException {	// <7>
    	System.out.println("Something useful on the server...");
        Thread.sleep(1000);
    }
}

class ClientExample implements Runnable {
	public void run() {
        try(final AsynchronousSocketChannel client = AsynchronousSocketChannel.open()) {	// <8>
	        final Future<Void> connected = client.connect(AsyncIO.HOST_ADDRESS);
	        
	        doSomethingUseful();
	        connected.get();		// <9>
	        System.err.println("Client connected, starting message stream");
	        
	        for (int index = 0, maxIndex = 3; index < maxIndex; index++) {
	            final ByteBuffer 		buffer = ByteBuffer.wrap(String.format("Message content: %f", Math.random()).getBytes());
	            final Future<Integer> 	sended = client.write(buffer);	// <10>
	
	            while (! sended.isDone()) {
	    	        doSomethingUseful();
	            }
	            System.err.println("Send "+(index+1)+" of "+maxIndex+", "+sended.get()+" was sended");	// <11>
	            buffer.clear();
	        }
        } catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
    }
    
    void doSomethingUseful() throws InterruptedException {	// <12>
    	System.err.println("Something useful on the client...");
        Thread.sleep(1000);
    }
}

