package game;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
	int boardWidth;
	int boardHeight;
	int tileSize = 25;

	private class Tile {
		int x;
		int y;

		Tile(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	// snake
	Tile snakeHead;
	ArrayList<Tile> snakeBody;
	// food
	Tile food;
	Random random;

	int velocityX;
	int velocityY;

	boolean gameOver = false;

	// gameLogic
	Timer gameLoop;// Timer a class, with arguments-->
					// delay between timer events in millsec, and
					// ActionListener object, whose actionPerformed method gets invoked as the timer
					// ticks

	SnakeGame(int boardWidth, int boardHeight) {
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
		setBackground(Color.black);

		addKeyListener(this);
		setFocusable(true);

		snakeHead = new Tile(5, 5);
		snakeBody = new ArrayList<Tile>();

		food = new Tile(10, 10);
		random = new Random();
		placeFood();

		velocityX = 1;
		velocityY = 0;

		gameLoop = new Timer(100, this); // every 100ms, the actionPerformed method will be called .
		gameLoop.start();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {

		// Grid
//		for (int i = 0; i < boardWidth / tileSize; i++) {
//			g.drawLine(i * tileSize, 0, i * tileSize, boardHeight); // horizontal lines (0,0) to (0,600), (25,0) to
//																	// (25,600)...
//			g.drawLine(0, i * tileSize, boardWidth, i * tileSize); // vertical lines
//		}

		// snake Head
		g.setColor(Color.green);
//		g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
		g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize,true);
		
		// food
		g.setColor(Color.red);
//		g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
		g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

		// snake Body // all other segments will be drawn looping into the arrayList
		for (int i = 0; i < snakeBody.size(); i++) {
			Tile snakePart = snakeBody.get(i);
			g.setColor(Color.green);
//			g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
			g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
		}

		// score
		g.setFont(new Font("Arial", Font.PLAIN, 16));
		if (gameOver) {
			g.setColor(Color.red);
			g.drawString("Game Over "+ String.valueOf(snakeBody.size()), tileSize, tileSize);
		} 
		else {
			g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize, tileSize);
		}

	}

	public void placeFood() {
		food.x = random.nextInt(boardWidth / tileSize);
		food.y = random.nextInt(boardWidth / tileSize);
	}

	public boolean collision(Tile tile1, Tile tile2) {
		return tile1.x == tile2.x && tile1.y == tile2.y;
	}

	public void move() {

		// eat food
		if (collision(snakeHead, food)) {
			snakeBody.add(new Tile(food.x, food.y));
			placeFood();
		}

		// the snake should be moving along snakeHead

		// logic: the first part should take into snakeHead's position,
		// and rest all taking into the position of one which is just ahead,
		// only then the keypress should be affecting snakehead's position.

		for (int i = snakeBody.size() - 1; i >= 0; i--) {
			Tile snakePart = snakeBody.get(i);
			if (i == 0) {
				snakePart.x = snakeHead.x;
				snakePart.y = snakeHead.y;
			} else {
				Tile prevSnakePart = snakeBody.get(i - 1);
				snakePart.x = prevSnakePart.x;
				snakePart.y = prevSnakePart.y;

			}
		}

		// snake Head
		snakeHead.x += velocityX;
		snakeHead.y += velocityY;

		// gameOver conditions
		
		//struck into its own body
		for (int i = 0; i < snakeBody.size(); i++) {
			Tile snakePart = snakeBody.get(i);
			if (collision(snakeHead, snakePart)) {
				gameOver = true;
			}
		}
		
		//struck into the wall
		if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth || snakeHead.y * tileSize <0
				|| snakeHead.y * tileSize >boardWidth) {
			gameOver = true;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		move();

		repaint(); // every 100 ms, the actionPerformed method is invoked to trigger repaint.
		// Repainting means that the paintComponent method will be called,
		// and the game board will be redrawn.
		if (gameOver) {
			gameLoop.stop();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) { // to not let the snake run in its own path
																	// can't just turn back and move.
			velocityX = 0;
			velocityY = -1;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
			velocityX = 0;
			velocityY = 1;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
			velocityX = 1;
			velocityY = 0;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
			velocityX = -1;
			velocityY = 0;
		}
	}

	// not needed in this game, had to implement though so just defined.
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
