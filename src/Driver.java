import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 * Driver class - initialises the MoveActiveBlock thread and creates a MainWindow object
 * 
 * @author emma
 */
public class Driver {
	private static MainWindow window;//the main window of the game
	private static boolean gameOver = false;//determines whether the game is over or not
	public static void main(String args[]) throws InterruptedException{
		window = new MainWindow();//the main window
		
		Thread MoveActiveBlockThread = new Thread(new MoveActiveBlock());//the active block thread
		MoveActiveBlockThread.start();//starting the thread
	}
	
	/**
	 * Thread for moving the active block and checking if the game is over
	 * @author emma
	 *
	 */
	private static class MoveActiveBlock implements Runnable {
		public void run(){
			while(!gameOver){//logic for when the game is running
				if(!window.checkActiveBlockValid()){//if the active block has reached the top of the window
						JFrame gameOverWindow = new JFrame("Game Over");//present game over window
						gameOverWindow.setVisible(true);
						gameOverWindow.setSize(200, 100);
						gameOverWindow.add(new JLabel("Sorry! Game over!", JLabel.CENTER));
						gameOver = true;
					}
				if(!gameOver){//if game is not over
					window.moveActiveBlock();//move the active block down
					window.hasScoreChanged();//check if score has changed and if so update
					int level = window.getLevel() + 1;
					try {
						Thread.sleep(1000/level);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			}
		}
	}
}
