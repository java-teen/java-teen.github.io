package examples2;

import java.util.ArrayList;
import java.util.List;

public class ListsInJava {
	public static void main(String[] args) {
		final List<String>	pseudoStack = new ArrayList<>();	// <1>
		
		pseudoStack.add("line1");				// <2>
		pseudoStack.add(0,"line2");
		pseudoStack.add(0,"line3");
		System.err.println(pseudoStack.get(0));	// <3>
		
		if (pseudoStack.contains("line3")) {	// <4>
			System.err.println("Found!");
		}
		
		while (pseudoStack.size() > 0) {		// <5>
			System.err.println("remove "+pseudoStack.remove(0));	// <6>
		}
	}
}
