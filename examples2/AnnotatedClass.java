package examples2;

import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class AnnotatedClass {
	public static void execute(final Class<?> entity) {	// <1>
		call(entity,Init.class);	// <2>
		call(entity,Process.class);
		call(entity,Terminate.class);
	}
	
	private static void call(Class<?> entity, Class<? extends Annotation> anno) {	// <3>
		while (entity != null) {	// <4>
			for (Method m : entity.getDeclaredMethods()) {	// <5>
				if (m.isAnnotationPresent(anno) && Modifier.isStatic(m.getModifiers())) {	// <6>
					try{m.setAccessible(true);	// <7>
						m.invoke(null);			// <8>
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					}
					return;
				}
			}
			entity = entity.getSuperclass();	// <9>
		}
	}


	public static void main(String[] args) {
		execute(Parent.class);	// <10>
		execute(Child.class);	// <11>
	}
}

class Parent {	// <12>
	@Init
	public static void callInit(){System.err.println("Parent init");}
	@Process
	public static void callProcess(){System.err.println("Parent Process");}
	@Terminate
	public static void callTerm(){System.err.println("Parent terminate");}
}

class Child extends Parent {	// <13>
	@Process
	public static void cjildCall(){System.err.println("Child Process");}
}

@Target(value=ElementType.METHOD)	// <14>
@Retention(value= RetentionPolicy.RUNTIME)
@interface Init {}

@Target(value=ElementType.METHOD)
@Retention(value= RetentionPolicy.RUNTIME)
@interface Process {}

@Target(value=ElementType.METHOD)
@Retention(value= RetentionPolicy.RUNTIME)
@interface Terminate {}

