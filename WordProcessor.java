import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * This class contains some utility helper methods
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class WordProcessor {
	
	/**
	 * Gets a Stream of words from the filepath.
	 * 
	 * The Stream should only contain trimmed, non-empty and UPPERCASE words.
	 * 
	 * @see <a href="http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html">java8 stream blog</a>
	 * 
	 * @param filepath file path to the dictionary file
	 * @return Stream<String> stream of words read from the filepath
	 * @throws IOException exception resulting from accessing the filepath
	 */
	public static Stream<String> getWordStream(String filepath) throws IOException {
		/**
		 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html">java.nio.file.Files</a>
		 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Paths.html">java.nio.file.Paths</a>
		 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html">java.nio.file.Path</a>
		 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html">java.util.stream.Stream</a>
		 * 
		 * class Files has a method lines() which accepts an interface Path object and 
		 * produces a Stream<String> object via which one can read all the lines from a file as a Stream.
		 * 
		 * class Paths has a method get() which accepts one or more strings (filepath),  
		 * joins them if required and produces a interface Path object
		 * 
		 * Combining these two methods:
		 *     Files.lines(Paths.get(<string filepath>))
		 *     produces
		 *         a Stream of lines read from the filepath
		 * 
		 * Once this Stream of lines is available, you can use the powerful operations available for Stream objects to combine 
		 * multiple pre-processing operations of each line in a single statement.
		 * 
		 * Few of these features:
		 * 		1. map( )      [changes a line to the result of the applied function. Mathematically, line = operation(line)]
		 * 			-  trim all the lines
		 * 			-  convert all the lines to UpperCase
		 * 			-  example takes each of the lines one by one and apply the function toString on them as line.toString() 
		 * 			   and returns the Stream:
		 * 			        streamOfLines = streamOfLines.map(String::toString) 
		 * 
		 * 		2. filter( )   [keeps only lines which satisfy the provided condition]  
		 *      	-  can be used to only keep non-empty lines and drop empty lines
		 *      	-  example below removes all the lines from the Stream which do not equal the string "apple" 
		 *                 and returns the Stream:
		 *      			streamOfLines = streamOfLines.filter(x -> x != "apple");
		 *      			 
		 * 		3. collect( )  [collects all the lines into a java.util.List object]
		 * 			-  can be used in the function which will invoke this method to convert Stream<String> of lines to List<String> of lines
		 * 			-  example below collects all the elements of the Stream into a List and returns the List:
		 * 				List<String> listOfLines = streamOfLines.collect(Collectors::toList); 
		 * 
		 * Note: since map and filter return the updated Stream objects, they can chained together as:
		 * 		streamOfLines.map(...).filter(a -> ...).map(...) and so on
		 */
		
		// create a Stream of all of the lines in the file at filepath
		Stream<String> wordStream = Files.lines(Paths.get(filepath))
				// trim all the whitespace from each String in the Stream
				.map(String::trim)
				// remove any null or empty Strings from the Stream
				.filter(x -> x != null && !x.equals(""))
				// make all of the Strings in the Stream uppercase
				.map(String::toUpperCase);
		return wordStream;
	}
	
	/**
	 * Adjacency between word1 and word2 is defined by:
	 * if the difference between word1 and word2 is of
	 * 	1 char replacement
	 *  1 char addition
	 *  1 char deletion
	 * then 
	 *  word1 and word2 are adjacent
	 * else
	 *  word1 and word2 are not adjacent
	 *  
	 * Note: if word1 is equal to word2, they are not adjacent
	 * 
	 * @param word1 first word
	 * @param word2 second word
	 * @return true if word1 and word2 are adjacent else false
	 */
	public static boolean isAdjacent(String word1, String word2) {
		// A few easy checks to make here - if the lengths of the two words differ by more than 1,
		// they are obviously not adjacent (since adjacency allows at most 1 letter removal or
		// addition). Per the Javadoc for this method, two words are not adjacent if they are
		// equal. If either of the words is the empty string, the two will not be adjacent.
		// (I only included that check to avoid any StringIndexOutOfBounds exceptions.)
		if(Math.abs(word1.length() - word2.length()) > 1 || word1.equals(word2) ||
				word1.equals("") || word2.equals("")) {
			return false;
		}
		// If the two words have equal lengths, they are adjacent if there is exactly one
		// different letter between them. (At this point, the two words will never be equal,
		// because I already checked for that in the above conditional.) This case is easy to
		// check - just iterate over both of the strings simultaneously and keep track of any
		// adjacent characters that are not equal. If they have more than one difference, return
		// false.
		else if(word1.length() == word2.length()) {
			int numDiff = 0;
			for(int i = 0; i < word1.length(); i++) {
				if(word1.charAt(i) != word2.charAt(i)) {
					numDiff++;
				}
			}
			
			return numDiff == 1;
		}
		// If the lengths of the two words differ by 1, they are adjacent if there is exactly one
		// letter addition or removal between them. To check this, iterate over both of the strings
		// simultaneously. The first time you find unequal adjacent characters, skip that character
		// in the longer of the two strings and carry on with the iteration. The second time you
		// find unequal adjacent characters, return false.
		else {
			String longerWord = (word1.length() > word2.length()) ? word1 : word2;
			String shorterWord = (word1.length() < word2.length()) ? word1 : word2;
			
			int i = 0;		// the current character index of the longer string
			int j = 0;		// the current character index of the shorter string
			
			while(i <= shorterWord.length()) {
				if(longerWord.charAt(i) != shorterWord.charAt(j) && i == j) {
					i++;
					continue;
				}
				else if(longerWord.charAt(i) != shorterWord.charAt(j) && i != j) {
					return false;
				}
				i++;
				j++;
			}
			
			return true;	
		}	
	}
}
