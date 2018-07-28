package examples5;

public class Calculator {
	/**
	 * <p>Calculate result for binary operation.</p>
	 * @param left left operand. Can't be null
	 * @param operation operation to do. Available operations are '+', '-', '*' and '/'
	 * @param right right operand. Can't be null
	 * @return result of the operation
	 * @throws NullPointerException if any operands are null
	 * @throws IllegalArgumentException when unknown operation was typed or divisor argument for '/' is 0.
	 */
	public static Integer calculate(final Integer left, final char operation, final Integer right) throws NullPointerException, IllegalArgumentException {
		if (left == null) {
			throw new NullPointerException("Left argument can't be null");
		}
		else if (right == null) {
			throw new NullPointerException("Right argument can't be null");
		}
		else {
			switch (operation) {
				case '+' :
					return left + right;
				case '-' :
					return left - right;
				case '*' :
					return left * right;
				case '/' :
					if (right == 0) {
						throw new IllegalArgumentException("Divizor for '/' operation can't be 0"); 
					}
					else {
						return left / right;
					}
				default : 
					throw new IllegalArgumentException("Operation to do ["+operation+"] is not known"); 
			}
		}
	}
}
