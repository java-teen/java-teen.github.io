package examples2;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SmartProperties extends Properties {
	public static final Map<Class<?>,Class<?>>	WRAPPERS = new HashMap<>();		// <1>
	
	static {
		WRAPPERS.put(byte.class,Byte.class);		WRAPPERS.put(short.class,Short.class);
		WRAPPERS.put(int.class,Integer.class);		WRAPPERS.put(long.class,Long.class);
		WRAPPERS.put(float.class,Float.class);		WRAPPERS.put(double.class,Double.class);
		WRAPPERS.put(boolean.class,Boolean.class);	WRAPPERS.put(char.class,Character.class);
	}
	
	public <T> T getProperty(final String key, final Class<T> awaited) {	// <2>
		if (awaited.isPrimitive()) {				// <3>
			return (T) convert(getProperty(key),WRAPPERS.get(awaited));		// <4>
		}
		else {
			return convert(getProperty(key),awaited);						// <5>
		}
	}

	private <T> T convert(final String value, final Class<T> awaited) {		// <6>
		try{final Method		m = awaited.getMethod("valueOf",String.class);	// <7>
			
			return (T)m.invoke(null,value);									// <8>
		} catch (NoSuchMethodException e) {									// <9>
			{
				try{final Constructor	c = awaited.getConstructor(String.class);	// <10>
					
					return (T) c.newInstance(value);							// <11>
				} catch (NoSuchMethodException e1) {							// <12>
					throw new IllegalArgumentException("Awaited class not contains both 'valueOf' method and constructor(String.class)");
				} catch (InstantiationException | IllegalAccessException | InvocationTargetException e1) {	// <13>
					throw new IllegalArgumentException("Error converting value ["+value+"] to ["+awaited.getName()+"] type: "+e1.getMessage());
				}
			}
		} catch (IllegalAccessException | InvocationTargetException | SecurityException e) {
			throw new IllegalArgumentException("Error converting value ["+value+"] to ["+awaited.getName()+"] type: "+e.getMessage());
		}
	}

	public static void main(String[] args) {
		final SmartProperties	sp = new SmartProperties();	// <14>
		
		sp.setProperty("intKey","100");						// <15>
		sp.setProperty("booleanKey","true");
		sp.setProperty("stringKey","c:/myFile.txt");
		sp.setProperty("enumKey","test2");
		
		System.err.println("int: "+sp.getProperty("intKey",int.class));		// <16>
		System.err.println("boolean: "+sp.getProperty("booleanKey",boolean.class));
		System.err.println("String: "+sp.getProperty("stringKey",String.class));
		System.err.println("File: "+sp.getProperty("stringKey",File.class));
		System.err.println("Enum: "+sp.getProperty("enumKey",TestEnum.class));
		
		try{sp.getProperty("stringKey",int.class);		// <17>
		} catch (IllegalArgumentException exc) {
			System.err.println("Error: "+exc.getMessage());
		}
		try{sp.getProperty("stringKey",List.class);			// <18>
		} catch (IllegalArgumentException exc) {
			System.err.println("Error: "+exc.getMessage());
		}
	}
}

enum TestEnum {		// <19>
	test1, test2;
}