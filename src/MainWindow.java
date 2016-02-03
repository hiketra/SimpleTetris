import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The MainWindow through which the user plays the game. Specifies the positioning
 * of all the JComponents that make up the user interface.
 * @author emma
 * @see Canvas
 */
public class MainWindow extends JFrame{
	
	private Canvas canvas; //the canvas for which the positioning of the 
	private int lineCount = 0; //the number of lines cleared
	private Sidebar sidebar; //the side bar
	
	/**
	 * Configures the layout, adds the appropriate JComponents, etc.
	 */
	public MainWindow(){
		JPanel contentPane = new JPanel(new FlowLayout());
		setContentPane(contentPane);
		
		canvas = new Canvas();
		contentPane.add(canvas);
		
		sidebar = new Sidebar();
		contentPane.add(sidebar);
		
		setVisible(true);
		setResizable(false);
		pack();
		setTitle("Tetris");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/**
	 * Calls the moveActiveBlock method of the canvas
	 * @see Canvas
	 */
	public void moveActiveBlock(){
		canvas.moveActiveBlock();
	}
	
	/**
	 * Calls the checkActiveBlockValid method of the canvas
	 * @see Canvas
	 * @return canvas.checkActiveBlockValid()
	 */
	public boolean checkActiveBlockValid(){
		return canvas.checkActiveBlockValid();
	}
	
	/**
	 * Calls the getActiveBlock method of the canvas
	 * @see Canvas
	 * @return canvas.getActiveBlock()
	 */
	public ComplexShape getActiveBlock(){
		return canvas.getActiveBlock();
	}
	
	/**
	 * Checks if the score has changed and returns true if so
	 */
	public boolean hasScoreChanged(){
		if(lineCount!=canvas.getLineCount()){
			lineCount = canvas.getLineCount();
			sidebar.setScore(lineCount*10);
			return true;
		}
		return false;
	}
	
	/**
	 * Get the current level
	 * @return sidebar.getLevel()
	 */
	public int getLevel(){
		return sidebar.getLevel();
	}
}
