import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Enum of colours that are valid for a Block object
 * @see Block
 * @author emma
 */
public enum ValidColour {
	BLUE(0, Color.BLUE),
	PINK(1, Color.PINK),
	ORANGE(2, Color.ORANGE),
	GREEN(3, Color.GREEN),
	MAGENTA(4, Color.MAGENTA),
	RED(5, Color.RED),
	CYAN(6, Color.CYAN);
	private final int iIndex;//the index of the colour
	private final Color iColour;//the colour it self
	private static final List<ValidColour> VALUES =
			Collections.unmodifiableList(Arrays.asList(values()));//a static list of all of the values
	private static final int SIZE = VALUES.size();//the size of the list
	private static final Random RANDOM = new Random();//seed for getting random colours
	
	/**
	 * Default constructor
	 * @param index
	 * @param colour
	 */
	ValidColour(int index, Color colour){
		iIndex=index;
		iColour = colour;
	}
	
	/**
	 * Get the index of the ValidColour
	 * @return iIndex
	 */
	public int getIndex(){
		return iIndex;
	}
	
	/**
	 * Get the colour of the ValidColour
	 * @return iColour
	 */
	public Color getColour(){
		return iColour;
	}
	
	/**
	 * Static method for getting a random ValidColour entry
	 */
	public static ValidColour randomValidColour() {
		return VALUES.get(RANDOM.nextInt(SIZE));
		//get an element from the static VALUES arrayList at a random index value
	}
}
