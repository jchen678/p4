<<<<<<< HEAD
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
			actual = "";
			if (!expected.equals(actual))
				fail("expected: " + expected + " actual: " + actual);
		}
=======
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GraphProcessorTest<E> {

	private GraphProcessor testObj;
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
		testObj = new GraphProcessor();
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
		actual = ""; 
		if (!expected.equals(actual))
			fail("expected: " + expected + " actual: " + actual);
	}
	
	/**
	 * Tests the populateGraph() method. Adds several words from a test text file and checks if the
	 * number returned by populateGraph() is equal to the number of words in the file. The test
	 * passes if the method returns the number of words in the file, and fails if it does not.
	 */
	@Test
	public void test02_populateGraph() {
		expected = "7";
		actual = testObj.populateGraph("testFile1.txt") + "";
		if(!expected.equals(actual)) {
			fail("expected: " + expected + " actual: " + actual);
		}
	}
	
	/**
	 * Tests the getShortestPath() method. After populating the graph with several words from a
	 * test text file, checks that getShortestPath() returns the correct path between two words.
	 * The test passes if the method returns the correct path, and fails if it does not.
	 */
	@Test
	public void test03_getShortestPath_connected_vertices() {
		List<String> expectedList = new ArrayList<String>();
		expectedList.add("chat");
		expectedList.add("what");
		expectedList.add("wheat");
		expected = expectedList.toString();
		
		testObj.populateGraph("testFile1.txt");
		actual = testObj.getShortestPath("chat", "wheat").toString();
		if(!expected.equals(actual)) {
			fail("expected: " + expected + " actual: " + actual);
		}
	}
	
	/**
	 * Tests the getShortestDistance() method. After populating the graph with several words from a
	 * test text file, checks that getShortestDistance() returns the correct distance between two
	 * words. The test passes if the method returns the correct distance, and fails if it does not.
	 */
	@Test
	public void test04_getShortestDistance_connected_vertices() {
		expected = "3";
		testObj.populateGraph("testFile1.txt");
		actual = testObj.getShortestDistance("chat", "wheat") + "";
		if(!expected.equals(actual)) {
			fail("expected: " + expected + " actual: " + actual);
		}
	}
	
	/**
	 * Tests the getShortestPath() method. After populating the graph with several words from a
	 * test text file, call getShortestPath() on two words with no path between them. The test
	 * passes if the method returns no path, and fails otherwise.
	 */
	@Test
	public void test05_getShortestPath_unconnected_vertices() {
		expected = new ArrayList<String>().toString();
		testObj.populateGraph("testFile1.txt");
		actual = testObj.getShortestPath("what", "who") + "";
		if(!expected.equals(actual)) {
			fail("expected: " + expected + " actual: " + actual);
		}
	}
	
	/**
	 * Tests the getShortestDistance() method. After populating the graph with words from a test
	 * text file, call getShortestDistance() on two words with no path between them. The test
	 * passes if the method returns Integer.MAX_VALUE, and fails otherwise.
	 */
	@Test
	public void test06_getShortestDistance_unconnected_vertices() {
		expected = Integer.MAX_VALUE + "";
		testObj.populateGraph("testFile1.txt");
		actual = testObj.getShortestDistance("what", "who") + "";
		if(!expected.equals(actual)) {
			fail("expected: " + expected + " actual: " + actual);
		}
	}
>>>>>>> 1a5663a622b5c4d27da80e49f1b53cc98e2642bc
}