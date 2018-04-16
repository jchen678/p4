
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GraphProcessorTest {

    // static Stream<String> stream = null;
    static GraphProcessor testObj;
    static File path = null;
    Object expected = null;
    Object actual = null;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	// will create a temporary empty file named empty.txt at system temp location
	path = File.createTempFile("empty", "txt");
	testObj = new GraphProcessor();
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * Tests if populateGraph on empty text file will throw exception
     */
    @Test
    public void test01_populateGraph_on_empty_textfile() {
	testObj.populateGraph(path.getAbsolutePath());
    }

    /**
     * Tests the populateGraph() method. Adds several words from a test text file
     * and checks if the number returned by populateGraph() is equal to the number
     * of words in the file. The test passes if the method returns the number of
     * words in the file, and fails if it does not.
     */
    @Test
    public void test02_populateGraph() {
	expected = "7";
	actual = testObj.populateGraph("testFile1.txt") + "";
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }

    /**
     * Tests the getShortestPath() method. After populating the graph with several
     * words from a test text file, checks that getShortestPath() returns the
     * correct path between two words. The test passes if the method returns the
     * correct path, and fails if it does not.
     */
    @Test
    public void test03_getShortestPath_connected_vertices_CHAT_WHEAT() {
	List<String> expectedList = new ArrayList<String>();
	expectedList.add("CHAT");
	expectedList.add("WHAT");
	expectedList.add("WHEAT");
	expected = expectedList.toString();

	testObj.populateGraph("testFile1.txt");
	actual = testObj.getShortestPath("CHAT", "WHEAT").toString();
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }

    /**
     * Tests the getShortestDistance() method. After populating the graph with
     * several words from a test text file, checks that getShortestDistance()
     * returns the correct distance between two words. The test passes if the method
     * returns the correct distance, and fails if it does not.
     */
    @Test
    public void test04_getShortestDistance_connected_vertices_CHAT_WHEAT() {
	expected = "2";
	testObj.populateGraph("testFile1.txt");
	actual = testObj.getShortestDistance("CHAT", "WHEAT") + "";
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }

    /**
     * Tests the getShortestPath() method. After populating the graph with several
     * words from a test text file, call getShortestPath() on two words with no path
     * between them. The test passes if the method returns no path, and fails
     * otherwise.
     */
    @Test
    public void test05_getShortestPath_unconnected_vertices_CAT_BAG() {
	expected = new ArrayList<String>().toString();
	testObj.populateGraph("testFile1.txt");
	actual = testObj.getShortestPath("CAT", "BAG") + "";
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }

    /**
     * Tests the getShortestDistance() method. After populating the graph with words
     * from a test text file, call getShortestDistance() on two words with no path
     * between them. The test passes if the method returns Integer.MAX_VALUE, and
     * fails otherwise.
     */
    @Test
    public void test06_getShortestDistance_unconnected_vertices_CAT_BAG() {
	expected = Integer.MAX_VALUE + "";
	testObj.populateGraph("testFile1.txt");
	actual = testObj.getShortestDistance("CAT", "BAG") + "";
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }

    @Test
    public void test07_getShortestPath_connected_vertices_CHAT_WHAT() {
	List<String> expectedList = new ArrayList<String>();
	expectedList.add("CHAT");
	expectedList.add("WHAT");
	expected = expectedList.toString();

	testObj.populateGraph("testFile1.txt");
	actual = testObj.getShortestPath("CHAT", "WHAT").toString();
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }

    @Test
    public void test08_getShortestPath_connected_vertices_CHEAT_CHAT() {
	List<String> expectedList = new ArrayList<String>();
	expectedList.add("CHEAT");

	expectedList.add("CHAT");
	expected = expectedList.toString();

	testObj.populateGraph("testFile1.txt");
	actual = testObj.getShortestPath("CHEAT", "CHAT").toString();
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }

    @Test
    public void test09_getShortestPath_connected_vertices_CAT_WHEAT() {
	List<String> expectedList = new ArrayList<String>();
	expectedList.add("CAT");
	expectedList.add("CHAT");
	expectedList.add("WHAT");
	expectedList.add("WHEAT");
	expected = expectedList.toString();

	testObj.populateGraph("testFile1.txt");
	actual = testObj.getShortestPath("CAT", "WHEAT").toString();
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }

    @Test
    public void test10_getShortestPath_connected_vertices_WHEAT_CAT() {
	List<String> expectedList = new ArrayList<String>();
	expectedList.add("WHEAT");
	expectedList.add("WHAT");
	expectedList.add("CHAT");
	expectedList.add("CAT");
	expected = expectedList.toString();

	testObj.populateGraph("testFile1.txt");
	actual = testObj.getShortestPath("WHEAT", "CAT").toString();
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }

    @Test
    public void test11_getShortestDistance_connected_vertices_CHAT_WHAT() {
	expected = "1";
	testObj.populateGraph("testFile1.txt");
	actual = testObj.getShortestDistance("CHAT", "WHAT") + "";
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }

    @Test
    public void test12_getShortestDistance_connected_vertices_CHEAT_CHAT() {
	expected = "1";
	testObj.populateGraph("testFile1.txt");
	actual = testObj.getShortestDistance("CHEAT", "CHAT") + "";
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }

    @Test
    public void test13_getShortestDistance_connected_vertices_CAT_CHEAT() {
	expected = "3";
	testObj.populateGraph("testFile1.txt");
	actual = testObj.getShortestDistance("CAT", "WHEAT") + "";
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }

    @Test
    public void test14_getShortestDistance_connected_vertices_WHEAT_CAT() {
	expected = "3";
	testObj.populateGraph("testFile1.txt");
	actual = testObj.getShortestDistance("WHEAT", "CAT") + "";
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }

    @Test
    public void test15_getShortestPath_unconnected_vertices_CAT_WHO() {
	expected = new ArrayList<String>().toString();
	testObj.populateGraph("testFile1.txt");
	actual = testObj.getShortestPath("CAT", "WHO") + "";
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }

    @Test
    public void test16_getShortestPath_unconnected_vertices_CHEAT_WHO() {
	expected = new ArrayList<String>().toString();
	testObj.populateGraph("testFile1.txt");
	actual = testObj.getShortestPath("CHEAT", "WHO") + "";
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }

    @Test
    public void test17_getShortestPath_unconnected_vertices_BAG_WHEAT() {
	expected = new ArrayList<String>().toString();
	testObj.populateGraph("testFile1.txt");
	actual = testObj.getShortestPath("BAG", "WHEAT") + "";
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }

    @Test
    public void test18_getShortestDistance_unconnected_vertices_CAT_WHO() {
	expected = Integer.MAX_VALUE + "";
	testObj.populateGraph("testFile1.txt");
	actual = testObj.getShortestDistance("CAT", "WHO") + "";
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }

    @Test
    public void test19_getShortestDistance_unconnected_vertices_CHEAT_WHO() {
	expected = Integer.MAX_VALUE + "";
	testObj.populateGraph("testFile1.txt");
	actual = testObj.getShortestDistance("CHEAT", "WHO") + "";
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }

    @Test
    public void test20_getShortestDistance_unconnected_vertices_BAG_WHEAT() {
	expected = Integer.MAX_VALUE + "";
	testObj.populateGraph("testFile1.txt");
	actual = testObj.getShortestDistance("BAG", "WHEAT") + "";
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }

    @Test
    public void test21_getShortestPath_on_empty_textfile_and_String() {
	thrown.expect(ArrayIndexOutOfBoundsException.class);
	testObj.populateGraph(path.getAbsolutePath());
	testObj.getShortestPath("", "");
    }

    @Test
    public void test22_getShortestDistance_on_empty_textfile_and_String() {
	thrown.expect(ArrayIndexOutOfBoundsException.class);
	testObj.populateGraph(path.getAbsolutePath());
	testObj.getShortestDistance("", "");
    }

    @Test
    public void test23_shortestPathPrecomputation_on_empty_textfile() {
	testObj.populateGraph(path.getAbsolutePath());
	testObj.shortestPathPrecomputation();
    }

    @Test
    public void test24_mass_calling_getShortestPath_and_getShortestDistance() {	
	try {
	    Object[] a = WordProcessor.getWordStream("word_list.txt").toArray();
	    testObj.populateGraph("word_list.txt");
	    for (int i = 0; i < 1024; i++) {
		int randomNum = ThreadLocalRandom.current().nextInt(0, a.length);
		String s1 = a[randomNum].toString();
		randomNum = ThreadLocalRandom.current().nextInt(0, a.length);
		String s2 = a[randomNum].toString();
		testObj.getShortestDistance(s1, s2);
		testObj.getShortestPath(s1, s2);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    @Test
    public void test25_getShortestPath_on_same_word_WHAT() {
	List<String> expectedList = new ArrayList<String>();
	expectedList.add("WHAT");
	expected = expectedList.toString();

	testObj.populateGraph("testFile1.txt");
	actual = testObj.getShortestPath("WHAT", "WHAT").toString();
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }
    
    @Test
    public void test26_getShortestDistance_on_same_word_WHAT() {
	expected = "0";
	testObj.populateGraph("testFile1.txt");
	actual = testObj.getShortestDistance("WHAT", "WHAT").toString();
	if (!expected.equals(actual)) {
	    fail("expected: " + expected + " actual: " + actual);
	}
    }
    
    
}