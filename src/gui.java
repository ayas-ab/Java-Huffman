/*
 * This class was for testing purpose
 * gui.java
 * Displays the tree
 */

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;

import Huffman.BinaryTree;
import Huffman.Pair;
import Huffman.Queue;

public class gui extends Canvas implements Runnable, KeyListener {

	private ArrayList<Rectangle> nodes = new ArrayList<Rectangle>();
	private Font font = new Font(null, Font.BOLD, 15);
	private BufferStrategy strategy;
	private Thread guiThread;
	private boolean running = true;
	private Queue tree;
	private int NavX = 0, NavY = 0;
	private boolean isUp = false, isDown = false, isLeft = false,
			isRight = false;
	private boolean shown = false;

	public gui(Queue tree) throws IOException {

		this.tree = tree;
		JFrame container = new JFrame("Tree GUI");
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(1000, 520));
		panel.setLayout(null);
		container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1200, 600);
		panel.add(this);
		setIgnoreRepaint(true);

		container.pack();
		container.setResizable(false);
		container.setLocationRelativeTo(null);
		container.setVisible(true);
		createBufferStrategy(2);
		this.requestFocus();
		this.addKeyListener(this);
		this.setFocusable(true);

		strategy = getBufferStrategy();
		guiThread = new Thread(this);
		guiThread.start();

	}

	@Override
	public void run() {
		while (running) {
			try {
				if (isUp) {
					NavY -= 10;
				}

				if (isDown) {
					NavY += 10;
				}

				if (isRight) {
					NavX += 10;

				}

				if (isLeft) {
					NavX -= 10;

				}
				draw();
				if (!shown) {
					JOptionPane
							.showMessageDialog(
									null,
									"This was just to check what the tree looks like. It only works for small trees..Other wise will look really messy\nUse Arrows up,down,left,right to navigate!");

					shown = true;
				}
				Thread.sleep(100);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void draw() {
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		if (running == true) {

			g.setColor(Color.black);
			g.fillRect(0, 0, 1300, 700);
			g.setColor(Color.WHITE);
			/*
			 * for (int i = 0; i < nodes.size(); i++) { g.fillOval((int)
			 * nodes.get(i).getX(), (int) nodes.get(i).getY(), 25, 25); }
			 */

			if (!this.tree.getTree().isEmpty()) {
				g.fillOval(500 + NavX, 10 + NavY, 25, 25);
				if (this.tree.getTree().getLeft() != null) {
					addNodes(this.tree.getTree().getLeft(), 300 + this.NavX,
							70 + this.NavY, g, 0, 0);
					g.drawLine(500 + 20 + this.NavX, 14 + this.NavY,
							300 + this.NavX, 90 + this.NavY);
				}
				if (this.tree.getTree().getRight() != null) {
					addNodes(this.tree.getTree().getRight(), 700 + this.NavX,
							60 + this.NavY, g, 0, 0);
					g.drawLine(500 + 20 + this.NavX, 10 + 10 + this.NavY,
							700 + this.NavX, 70 + this.NavY);
				}

			}
			strategy.show();
			// g.fillRect(this.Player1.getX(), this.Player1.getY(), 70, 120);

		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {

		case KeyEvent.VK_UP:
			if (!isUp)
				this.isUp = true;
			break;

		case KeyEvent.VK_DOWN:
			if (!isDown)
				this.isDown = true;
			break;

		case KeyEvent.VK_LEFT:
			if (!isLeft)
				this.isLeft = true;
			break;

		case KeyEvent.VK_RIGHT:
			if (!isRight)
				this.isRight = true;
			break;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) {

		case KeyEvent.VK_UP:
			this.isUp = false;
			break;

		case KeyEvent.VK_DOWN:
			this.isDown = false;
			break;

		case KeyEvent.VK_LEFT:
			this.isLeft = false;
			break;

		case KeyEvent.VK_RIGHT:
			this.isRight = false;
			break;

		}

	}

	public void addNodes(BinaryTree<Pair> temptree, int x, int y, Graphics2D g,
			int add, int add2) {
		g.setFont(this.font);
		g.fillOval(x, y, 25, 25);
		if (!temptree.getData().getChar().equals("0")) {
			g.setColor(Color.red);

			g.drawString(temptree.getData().getChar(), x, y);
			g.setColor(Color.white);
		}
		if (temptree.getLeft() != null) {
			addNodes(temptree.getLeft(), x - 100 - add, y + 50 + add2, g,
					add + 20, add2 + 10);
			// if (temptree.getLeft().getLeft() != null)
			g.drawLine(x - 100 + 20 - add, y + 50 + 10 + add2, x + 20, y + 10);
		}
		if (temptree.getRight() != null) {
			addNodes(temptree.getRight(), x + 100 + add, y + 50 + add2, g,
					add + 20, add2 + 10);
			// if (temptree.getRight().getRight() != null)
			g.drawLine(x + 100 + 20 + add, y + 50 + 20 + add2, x + 20, y + 20);
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
