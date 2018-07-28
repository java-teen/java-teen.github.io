package examples2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class Externalization implements Externalizable {
	private int		number;
	private String	name;

	public Externalization(int number, String name) {
		this.number = number;
		this.name = name;
	}

	public Externalization(){	// <1>
		this.number = 0;
		this.name = "empty";
	}

	@Override	// <2>
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		number = in.readInt();	// <3>
		name = in.readUTF();
	}

	@Override	// <4>
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(number);	// <5>
		out.writeUTF(name);
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException { 	// <6>
		try(final ByteArrayOutputStream		baos = new ByteArrayOutputStream()) {
			try(final ObjectOutputStream	oos = new ObjectOutputStream(baos)) {			
				oos.writeInt(123);	
				oos.writeObject(new Externalization[]{
							new Externalization(123,"my caption")
							, new Externalization(456,"your caption")
							});
				oos.flush();		
			}
			
			try(final InputStream		is = new ByteArrayInputStream(baos.toByteArray());	
				final ObjectInputStream	ois = new ObjectInputStream(is)) {	
		
				System.err.println(ois.readInt());			
				System.err.println(Arrays.toString((Externalization[])ois.readObject()));
			}
		}
	}

	@Override
	public String toString() {
		return "Externalization [number=" + number + ", name=" + name + "]";
	}
}
