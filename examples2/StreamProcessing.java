package examples2;

import java.util.HashSet;
import java.util.Set;

public class StreamProcessing {
	public static final Set<FamilyAndSalary>	SOURCE = new HashSet<>();

	static {	// <1>
		SOURCE.add(new FamilyAndSalary("Ivanoff",200));
		SOURCE.add(new FamilyAndSalary("Petroff",250));
		SOURCE.add(new FamilyAndSalary("Sidoroff",400));
	}
	
	public static void main(final String[] args) {
		final float[]	sumCountAndAverage = new float[]{0,0,0};	// <2>
		
		SOURCE.forEach((item)->{sumCountAndAverage[0] += item.salary; sumCountAndAverage[1]++;});	// <3>
		sumCountAndAverage[2] = sumCountAndAverage[0]/sumCountAndAverage[1];
		
		SOURCE.stream().filter((item)->item.salary > sumCountAndAverage[2]).forEach((item)->System.err.println(item));	// <4>
	}
	
	static class FamilyAndSalary {	// <5>
		final String 	family;
		final float		salary;
		
		public FamilyAndSalary(String family, float salary) {
			this.family = family;
			this.salary = salary;
		}

		@Override
		public String toString() {
			return "FamilyAndSalary [family=" + family + ", salary=" + salary + "]";
		}
	}
}
