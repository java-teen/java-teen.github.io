package examples1;

public class ErrorCatch {
	static void error(int code) throws Throwable, Exception {
		switch (code) {		// <1>
			case 0 : throw new RuntimeException();
			case 1 : throw new Exception();
			case 2 : throw new Throwable();
			case 3 : return ;
		}
	}

	static void nested(int code) throws Throwable {
		try{error(code);	// <2>
		} catch (Exception exc) {
		} finally {
			System.err.println("Finally nested");
		}
	}
	

	public static void main(String[] args) {
		System.err.println("------------");
		for (int index = 0; index <= 3; index++) {		// <3>
			try{error(index);
				System.err.println("OK");
			} catch (RuntimeException exc) {
				System.err.println("Runtime error");
			} catch (Exception exc) {
				System.err.println("Exception error");
			} catch (Throwable exc) {
				System.err.println("Throwable error");
			} finally {
				System.err.println("Finally main");
			}
		}
		System.err.println("------------");
		for (int index = 0; index <= 3; index++) {		// <4>
			try{nested(index);
				System.err.println("OK");
			} catch (RuntimeException exc) {
				System.err.println("Runtime error");
			} catch (Exception exc) {
				System.err.println("Exception error");
			} catch (Throwable exc) {
				System.err.println("Throwable error");
			} finally {
				System.err.println("Finally main");
			}
		}
		System.err.println("------------");
	}

}
