package hosc;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class HParsersTest4J {

	public static Test suite() {
		TestSuite suite = new TestSuite("Tasks");
		// $JUnit-BEGIN$
		suite.addTest(new JUnit4TestAdapter(hosc.HParsersTest.class));
		// $JUnit-END$
		return suite;
	}

}