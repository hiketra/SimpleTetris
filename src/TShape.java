import java.awt.Color;

/**
 * Contains the logic to rotate a TShape shape in addition to
 * specifying the specific coordinates/positioning of the blocks
 * that compose said shape.
 * 
 * @author emma
 *
 */

public class TShape extends ComplexShape {

	public static final int MID_TOP_BLOCK = 0;
	public static final int LEFT_BLOCK = 1;
	public static final int MID_BOTTOM_BLOCK = 2;
	public static final int RIGHT_BLOCK = 3;
	
	public static final int ANTICLOCK_0 =0;
	public static final int ANTICLOCK_90 = 1;
	public static final int ANTICLOCK_180 = 2;
	public static final int ANTICLOCK_270 = 3;
	
	private int rotationState = ANTICLOCK_0;
	
	@Override
	public void defineBlocks() {
		int xCoord = Canvas.WIDTH/2 - 10;
		int yCoord = 0;
		
		Block[] blocks = getBlocks();
		Color colour = getColour();
		
		blocks[MID_TOP_BLOCK] = new Block(colour, xCoord, yCoord, true, true);
		/*   [X]
		 *[ ][ ][ ]
		 * */
		blocks[LEFT_BLOCK] = new Block(colour, xCoord-Block.WIDTH, yCoord+Block.HEIGHT, true, true);
		/*   [ ]
		 *[X][ ][ ]
		 * */
		blocks[MID_BOTTOM_BLOCK] = new Block(colour, xCoord, yCoord+Block.HEIGHT, true, true);
		/*   [ ]
		 *[ ][X][ ]
		 * */
		blocks[RIGHT_BLOCK] = new Block(colour, xCoord+Block.WIDTH, yCoord+Block.HEIGHT, true, true);
		/*   [ ]
		 *[ ][ ][X]
		 * */
		setBlocks(blocks);
	}

	@Override
	public void rotateBlocks() {
		Block[] blocks = getBlocks();
		switch(rotationState){
		case ANTICLOCK_0:
			/*[ ][X][ ]    [ ][X][ ]
			 *[X][X][X] -->[X][X][ ]
			 *[ ][ ][ ]    [ ][X][ ]
			 * */
			blocks[MID_TOP_BLOCK].moveLeft(1);
			blocks[MID_TOP_BLOCK].moveDown(1);
			
			blocks[LEFT_BLOCK].moveRight(1);
			blocks[LEFT_BLOCK].moveDown(1);
			
			blocks[RIGHT_BLOCK].moveLeft(1);
			blocks[RIGHT_BLOCK].moveUp(1);
			break;
		case ANTICLOCK_90:
			/*[ ][X][ ]    [ ][ ][ ]
			 *[X][X][ ] -->[X][X][X]
			 *[ ][X][ ]    [ ][X][ ]
			 * */
			
			blocks[MID_TOP_BLOCK].moveRight(1);
			blocks[MID_TOP_BLOCK].moveDown(1);
			
			blocks[LEFT_BLOCK].moveRight(1);
			blocks[LEFT_BLOCK].moveUp(1);
			
			blocks[RIGHT_BLOCK].moveLeft(1);
			blocks[RIGHT_BLOCK].moveDown(1);
			
			break;
		case ANTICLOCK_180:
			/*[ ][ ][ ]    [ ][X][ ]
			 *[X][X][X] -->[ ][X][X]
			 *[ ][X][ ]    [ ][X][ ]
			 * */
			
			blocks[MID_TOP_BLOCK].moveRight(1);
			blocks[MID_TOP_BLOCK].moveUp(1);
			
			blocks[LEFT_BLOCK].moveLeft(1);
			blocks[LEFT_BLOCK].moveUp(1);
			
			blocks[RIGHT_BLOCK].moveRight(1);
			blocks[RIGHT_BLOCK].moveDown(1);
			
			break;
		case ANTICLOCK_270:
			/*[ ][X][ ]    [ ][X][ ]
			 *[ ][X][X] -->[X][X][X]
			 *[ ][X][ ]    [ ][ ][ ]
			 * */
			
			blocks[MID_TOP_BLOCK].moveLeft(1);
			blocks[MID_TOP_BLOCK].moveUp(1);
			
			blocks[LEFT_BLOCK].moveLeft(1);
			blocks[LEFT_BLOCK].moveDown(1);
			
			blocks[RIGHT_BLOCK].moveRight(1);
			blocks[RIGHT_BLOCK].moveUp(1);
			
			rotationState = -1;
			
			break;
		}
		
		rotationState++;
		
		setBlocks(blocks);

	}

	@Override
	public void printCoords() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValidRotation(Block[][] blocksInPlace) {
		switch(rotationState){
		case ANTICLOCK_0:
			return isValidDownwardMove(blocksInPlace);
		case ANTICLOCK_90:
			return isValidRightMove(blocksInPlace);
		case ANTICLOCK_180:
			return true;
		case ANTICLOCK_270:
			return isValidLeftMove(blocksInPlace);
		}
		return true;
	}
	
	public void incrementRotationState(int currentRotationState){
		if(currentRotationState<3){
			rotationState = currentRotationState++;
		}
		else{
			rotationState++;
		}
	}

}
