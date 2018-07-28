package examples2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ProxyUsage {
	public static final CallInterface		target = new CallImpl();		// <1>
	
	public static final InvocationHandler	ih = new InvocationHandler(){	// <2>
			@Override
			public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
				System.err.println("Send to server: "+arg1.getName()+": "+Arrays.toString(arg2));
				final Object	result = arg1.invoke(target,arg2); 
				System.err.println("Receive from server: "+result);
				return result;
			}
		}; 

	public static void main(String[] args) {
		System.err.println("Direct: ADD: "+target.add(5,3)+", SUB: "+target.subtract(6,4));	// <3>
		
		final CallInterface		proxy = (CallInterface) Proxy.newProxyInstance(				// <4>
											ProxyUsage.class.getClassLoader()
											,new Class<?>[]{CallInterface.class}
											,ih);
		
		System.err.println("Proxy: ADD: "+proxy.add(5,3)+", SUB: "+proxy.subtract(6,4));	// <5>
	}
}

interface CallInterface {						// <6>
	int add(int one, int two);
	int subtract(int one, int two);
}

class CallImpl implements CallInterface {		// <7>
	@Override public int add(int one, int two) {return one + two;}
	@Override public int subtract(int one, int two) {return one - two;}
}