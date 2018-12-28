package etf.nim.oa130547d;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Deck extends JPanel {

	public GraphicStack[] all_ten_stacks;
	JPanel[] panels;
	
	public Deck() {
		all_ten_stacks = new GraphicStack[10];
		panels = new JPanel[10];
		
		this.setLayout(new GridLayout(2, 5));
		
		for (int i = 0; i < 10; i++) {
			all_ten_stacks[i] = new GraphicStack();
			panels[i] = new JPanel(new BorderLayout());
			panels[i].add(all_ten_stacks[i],BorderLayout.CENTER);
			JLabel labela = new JLabel("S T A C K "+ Integer.toString(i+1));
			labela.setHorizontalAlignment(SwingConstants.CENTER);
			panels[i].add(labela, BorderLayout.SOUTH);
			this.add(panels[i]);
		}
	}

}
