import java.awt.Color;

/**
 * Contains the logic to rotate a Line shape in addition to
 * specifying the specific coordinates/positioning of the blocks
 * that compose said shape.
 * 
 * @author emma
 *
 */

public class Cube extends ComplexShape {

	public static final int UPPER_LEFT_BLOCK = 0;
	public static final int UPPER_RIGHT_BLOCK = 1;
	
	public static final int LOWER_LEFT_BLOCK = 2;
	public static final int LOWER_RIGHT_BLOCK = 3;


	public void rotateBlocks() {
		// no implementation necessary - cube cannot be rotated

	}
	@Override
	public void defineBlocks() {
		
		int xCoord = /*Canvas.generateRandomCoord(true)*/ Canvas.WIDTH/2 - 10;
		int yCoord = 0;
		
		Block[] blocks = getBlocks();
		
		Color colour = getColour();
		
		blocks[UPPER_LEFT_BLOCK] = new Block(colour, xCoord, yCoord, true, true);
		/* 	[X][ ]
		 *  [ ][ ]
		 * */
		blocks[UPPER_RIGHT_BLOCK] = new Block(colour, xCoord+Block.WIDTH, yCoord, true, true);
		/*	[ ][X]
		 *  [ ][ ]
		 * */
		blocks[LOWER_LEFT_BLOCK] = new Block(colour, xCoord, yCoord+Block.HEIGHT, true, true);
		/* 	[ ][ ]
		 *  [X][ ]
		 * */
		blocks[LOWER_RIGHT_BLOCK] = new Block(colour, xCoord+Block.WIDTH, yCoord+Block.HEIGHT, true, true);
		/* 	[ ][ ]
		 *  [ ][X]
		 * */
		
		setBlocks(blocks);
	}
	
	public void printCoords(){
		Block[] blocks = getBlocks();
		
		System.out.println("Upper-left Coords: \n");
		blocks[UPPER_LEFT_BLOCK].printCoords();
		
		System.out.println("Upper-right Coords: \n");
		blocks[UPPER_RIGHT_BLOCK].printCoords();
		
		System.out.println("Lower-left Coords: \n");
		blocks[LOWER_LEFT_BLOCK].printCoords();
		
		System.out.println("Lower-right Coords: \n");
		blocks[LOWER_RIGHT_BLOCK].printCoords();
	}
	@Override
	public boolean isValidRotation(Block[][] blocksInPlace) {
		//doesn't rotate, so no need to worry
		return true;
	}

}
