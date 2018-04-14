import static org.junit.Assert.*;

import java.util.HashMap;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
public class GraphProcessorTest<E> {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

		HashMap<E, GraphNode<E>> adjList = null;
		String expected = null;
		String actual = null;

		/**
		 * @throws java.lang.Exception
		 */
		@BeforeClass
		public static void setUpBeforeClass() throws Exception {
			// set up huge data structure
		}

		/**
		 * @throws java.lang.Exception
		 */
		@AfterClass
		public static void tearDownAfterClass() throws Exception {
		}

		/**
		 * Constructor that runs before every test
		 * 
		 * @throws java.lang.Exception
		 */
		@Before
		public void setUp() throws Exception {
			adjList = new HashMap<E, GraphNode<E>>();
		}
		
		/**
		 * @throws java.lang.Exception
		 */
		@After
		public void tearDown() throws Exception {
		}
		
		/**
		 * Tests if...
		 */
		@Test
		public void test01_() {
			expected = "true";
			actual = "" 
			if (!expected.equals(actual))
				fail("expected: " + expected + " actual: " + actual);
		}
}