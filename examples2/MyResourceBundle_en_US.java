package examples2;

import java.util.ListResourceBundle;

public class MyResourceBundle_en_US extends ListResourceBundle {
	@Override
	protected Object[][] getContents() {
		return new Object[][]{new Object[]{"key","value"}};
	}
}
