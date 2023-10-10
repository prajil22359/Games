package game;
import javax.swing.*;
public class App {


	public static void main(String[] args) {
		int boardWidth= 600;
		int boardHeight= boardWidth;
		int tileSize=25;
		
		
//		JFrame.setDefaultLookAndFeelDecorated(true);
		
		JFrame frame = new JFrame("Snake");
		
//		String imgURL = "C:\\Users\\Prajil\\Pictures\\Screenshots\\arista.png";  for changing icon
//		frame.setIconImage(new ImageIcon(imgURL).getImage());
		
		frame.setVisible(true);
		frame.setSize(boardWidth, boardHeight);
		frame.setLocationRelativeTo(null);      // at center
//		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // for X to close
		
		SnakeGame snakeGame= new SnakeGame(boardWidth, boardHeight);
		frame.add(snakeGame);
		frame.pack();        // sizes the frame so that all its contents are at or above their preferred sizes.
		snakeGame.requestFocus();
	
	}
	
}