package examples2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class NIOCopy {
	public static void main(String[] args) throws IOException {
		final ByteBuffer	bb = ByteBuffer.allocate(8192);
		
		try(final InputStream 			is = new FileInputStream("c:/tmp/source.txt");		// <1>
			final OutputStream			os = new FileOutputStream("c:/tmp/target.txt");) {
			final ReadableByteChannel 	chIn = Channels.newChannel(is);						// <2>
			final FileChannel 			chOut = ((FileOutputStream)os).getChannel();
			
			while (chIn.read(bb) > 0) {		// <3>
				bb.flip();					// <4>
				chOut.write(bb);			// <5>
				bb.clear();					// <6>
			}
		}
	}
}
