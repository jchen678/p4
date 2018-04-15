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
	
	@Test
	public void test02_populateGraph() {
		expected = "7";
		actual = testObj.populateGraph("testFile1.txt") + "";
		if(!expected.equals(actual)) {
			fail("expected: " + expected + " actual: " + actual);
		}
	}
	
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
	
	@Test
	public void test04_getShortestDistance_connected_vertices() {
		expected = "3";
		testObj.populateGraph("testFile1.txt");
		actual = testObj.getShortestDistance("chat", "wheat") + "";
		if(!expected.equals(actual)) {
			fail("expected: " + expected + " actual: " + actual);
		}
	}
	
	@Test
	public void test05_getShortestPath_unconnected_vertices() {
		expected = new ArrayList<String>().toString();
		testObj.populateGraph("testFile1.txt");
		actual = testObj.getShortestPath("what", "who") + "";
		if(!expected.equals(actual)) {
			fail("expected: " + expected + " actual: " + actual);
		}
	}
	
	@Test
	public void test06_getShortestDistance_unconnected_vertices() {
		expected = Integer.MAX_VALUE + "";
		testObj.populateGraph("testFile1.txt");
		actual = testObj.getShortestDistance("what", "who") + "";
		if(!expected.equals(actual)) {
			fail("expected: " + expected + " actual: " + actual);
		}
	}
}