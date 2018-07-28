package examples1;

public class LifeCycle extends ParentLifeCycle {
	static int		x = 20;			// <1>
	int				y = 200;

	static {		// <2>
		System.err.println("Call LifeCycle static{}");
	}
	
	{				// <3>
		System.err.println("Call LifeCycle instance{}");
	}
	
	
	LifeCycle(){}	// <4>

	LifeCycle(int newY){	// <5>
		super(newY);
		y = newY;
	}
	
	static void staticPrint() {	// <6>
		System.err.println("LifeCycle static print "+x);
	}

	@Override
	void instancePrint() {		// <7>
		System.err.println("LifeCycle instance print "+y);
	}
	
	public static void main(String[] args) {	// <8>
		System.err.println("Main started");
		
		final ParentLifeCycle	lc1 = new ParentLifeCycle();	// <9>
		lc1.staticPrint();			// <10>
		lc1.instancePrint();		// <11>

		final ParentLifeCycle	lc2 = new LifeCycle(33);		// <12>
		lc2.staticPrint();			// <13>
		lc2.instancePrint();		// <14>
		
		System.err.println("Main ended");
	}
}

class ParentLifeCycle {			// <15>
	static int		x = 10;		// <16>
	int				y = 100;

	static {		// <16>
		System.err.println("Call ParentLifeCycle static{}");
	}
	
	{				// <17>
		System.err.println("Call ParentLifeCycle instance{}");
	}
	
	ParentLifeCycle(){}		// <18>

	ParentLifeCycle(int newY){	// <19>
		y = newY;
	}

	static void staticPrint() {	// <20>
		System.err.println("ParentLifeCycle static print "+x);
	}

	void instancePrint() {		// <21>
		System.err.println("ParentLifeCycle instance print "+y);
	}
}