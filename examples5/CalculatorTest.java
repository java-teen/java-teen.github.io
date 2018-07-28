package examples5;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {
	@Test	// <1>
	public void test() {
		Assert.assertEquals(Calculator.calculate(3,'+',5),Integer.valueOf(8));	// <2>
		Assert.assertEquals(Calculator.calculate(8,'-',5),Integer.valueOf(3));
		Assert.assertEquals(Calculator.calculate(8,'*',3),Integer.valueOf(24));
		Assert.assertEquals(Calculator.calculate(8,'/',4),Integer.valueOf(2));
		
		try{Calculator.calculate(null,'+',5);	// <3>
			Assert.fail("Mandatory exception was not detected (null 1-st argument)");
		} catch (NullPointerException exc) {
		}
		try{Calculator.calculate(3,'+',null);
			Assert.fail("Mandatory exception was not detected (null 3-rd argument)");
		} catch (NullPointerException exc) {
		}

		try{Calculator.calculate(3,'?',5);
			Assert.fail("Mandatory exception was not detected (unknown 2-nd argument)");
		} catch (IllegalArgumentException exc) {
		}
		try{Calculator.calculate(9,'/',0);
			Assert.fail("Mandatory exception was not detected (zero 3-rd argument for divizion)");
		} catch (IllegalArgumentException exc) {
		}
	}
}
