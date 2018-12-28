package etf.nim.oa130547d;

import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GraphicStack extends JPanel {

	int number_of_coins;

	public GraphicStack() {
		number_of_coins = 0;
	}

	protected void paintComponent(Graphics g) {
		int x = this.getWidth() / 2;
		int y[] = new int[10];
		y[0] = this.getHeight() / 10 - 2;
		for (int i = 1; i < 10; i++)
			y[i] = y[i - 1] + y[0];

		for (int i = 0; i < number_of_coins; i++)
			g.fillOval(x, y[i], 10, 10);
	}

	public void set_number_of_coins(int n) {
		this.number_of_coins = n;
	}

}
