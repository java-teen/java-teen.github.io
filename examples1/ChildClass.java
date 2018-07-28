package examples1;

class ParentClass {	// <1>
	int	y = 10;		// <2>
	
	ParentClass(){	// <3>
		init();		// <4>
	}
	
	public void init(){	// <5>
		System.err.println("Y="+y);
	}
}

public class ChildClass extends ParentClass {	// <6>
	int	y = 20;			// <7>

	ChildClass(){		// <8>		
	}

	@Override
	public void init(){	// <8>
		System.err.println("Y="+y);
	}
	
	public static void main(String[] args) {
		new ChildClass();	// <10>
	}
}

