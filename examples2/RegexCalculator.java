package examples2;

public class RegexCalculator {
	static long calculate(final String expr) {		
		final String[]	operands = expr.split("(\\+|\\-)");	// <1>	
		final String[]	operators = expr.split("\\d+");		// <2>
		long			result = 0;
	
		for (int index = 0; index < operands.length; index++) {	// <3>
			result += ("-".equals(operators[index]) ? -1 : 1) * Integer.valueOf(operands[index]);
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.err.println("Calc="+calculate("12+3-7"));	// <4>
	}
}
