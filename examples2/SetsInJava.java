package examples2;

import java.util.HashSet;
import java.util.Set;

public class SetsInJava {
	public static void main(String[] args) {
		final Set<String>	set = new HashSet<>();	// <1>
		
		set.add("string1");		// <2>
		set.add("string2");
		set.add("string2");
		
		for (String item : set) {	// <3>
			System.err.println("Contains: "+item);
		}
		
		final Set<String> toAdd = new HashSet<>(), toIntersect = new HashSet<>(), toMinus = new HashSet<>();	// <4>
		
		toAdd.add("string3");
		toIntersect.add("string1");	toIntersect.add("string3");
		toMinus.add("string1");
		
		set.addAll(toAdd);			// <5>
		set.retainAll(toIntersect);
		set.removeAll(toMinus);
		System.err.println("Content: "+set);	// <6>
	}
}
