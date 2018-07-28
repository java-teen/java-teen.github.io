package examples2;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class UseMethodHandle {
	public static void main(String[] args) throws Throwable {
		final MethodHandle	mh = MethodHandles.lookup().findVirtual(String.class	// <1>
																, "substring"
																, MethodType.methodType(String.class, int.class)
																);
		final String 		val = (String)mh.invoke("test",2);		// <2>
		
		System.err.println("Result="+val);
	}

}
