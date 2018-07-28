package examples2;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MapsInJava {
	public static void main(String[] args) {
		final Map<String,String>	pairs = new HashMap<String,String>();	// <1>
		
		System.err.println("Put[1]: "+pairs.put("key1","value1"));			// <2>
		System.err.println("Put[2]: "+pairs.put("key1","newValue1"));
		pairs.put("key2","value2");
		
		for (Entry<String, String> item : pairs.entrySet()) {				// <3>
			System.err.println(item.getKey()+'='+item.getValue());
		}
		System.err.println("Contains:"+pairs.containsKey("key2")+", value="+pairs.get("key2"));	// <4>
	}
}
