import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Contains score and level meter in addition to new game and save game button.
 * Forms part of the MainWindow
 * @author emma
 * @see MainWindow
 */
public class Sidebar extends JPanel{
	
	private int iLevel = 0;
	private int iScore = 0;
	
	private JButton iBNewGame = new JButton("New Game");
	private JButton iBSaveGame = new JButton("Save Game");
	private JLabel iLLevel = new JLabel("Level: " + iLevel, JLabel.CENTER);
	private JLabel iLScore = new JLabel("Score: " + iScore, JLabel.CENTER);

	public Sidebar(){
		setPreferredSize(new Dimension(100, 100));
		setLayout(new GridLayout(4, 1));
		
		this.add(iBNewGame);
		this.add(iBSaveGame);
		this.add(iLLevel);
		this.add(iLScore);
	}
	
	/**
	 * Updates the level based on the current score
	 */
	public void setLevel(){
		if(iLevel<=12){
			iLevel = (int)iScore/50;
			iLLevel.setText("Level: " + iLevel);
		}
	}
	
	/**
	 * Updates the score
	 * @param score
	 */
	public void setScore(int score){
		iScore = score;
		iLScore.setText("Score: " + iScore);
		setLevel();
	}
	
	/**
	 * Returns the level
	 * @return iLevel
	 */
	public int getLevel(){
		return iLevel;
	}
	
}
