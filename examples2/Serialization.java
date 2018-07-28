package examples2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

public class Serialization implements Serializable {
	private static final long 	serialVersionUID = -6466779156450723940L;
	public final String			caption;
	
	private Serialization(){
		this.caption = "";
	}
	
	public Serialization(final String caption) {
		this.caption = caption;
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		try(final ByteArrayOutputStream		baos = new ByteArrayOutputStream()) {
			try(final ObjectOutputStream	oos = new ObjectOutputStream(baos)) {			// <1>
				oos.writeInt(123);		// <2>
				oos.writeObject(new Serialization[]{
							new Serialization("my caption")
							, new Serialization("your caption")
							});
				oos.flush();			// <3>
			}
			
			try(final InputStream		is = new ByteArrayInputStream(baos.toByteArray());	// <4>
				final ObjectInputStream	ois = new ObjectInputStream(is)) {	// <5>
		
				System.err.println(ois.readInt());			// <6>
				System.err.println(Arrays.toString((Serialization[])ois.readObject()));
			}
		}
	}

	@Override
	public String toString() {
		return "Serialization [caption=" + caption + "]";
	}
}
