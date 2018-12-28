package etf.nim.oa130547d;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Window extends JFrame {

	JMenuBar menu_bar;
	JMenu menu, menu1, menu2, rules, options;
	JMenuItem human_vs_human, cpu_vs_cpu, human_vs_cpu;
	JMenuItem stacks_num[];
	JMenuItem easy, medium, hard;
	JMenuItem read_rules;
	JMenuItem new_game;
	
	JDialog coins_placer;
	JDialog warning;
	JDialog wrong_move;
	JDialog winner_dialog;
	JDialog rules_dialog;
	JDialog welcome;
	JButton dialog_button;
	JTextArea s[];

	JPanel north;
	Deck middle;
	JPanel south;

	JButton north_button, south_button;

	JTextArea north_text_stack, south_text_stack, north_text_coin, south_text_coin;

	JLabel north_stack_label, north_coin_label, south_stack_label, south_coin_label;

	Game game;

	public Window(Game g) {

		super("Nim");

		game = g;
		
		coins_placer = new JDialog(this, "Coins Placer");

		set_up_menu();

		set_up_choose_mode();

		set_up_choose_number_of_stacks();

		create_dialog();

		// glavni Paneli
		north = new JPanel(new FlowLayout());
		middle = new Deck();
		south = new JPanel(new FlowLayout());

		game.set_window(this);

		// komponente
		north_button = new JButton("Play");
		south_button = new JButton("Play");
		north_button.setEnabled(false);
		south_button.setEnabled(false);

		north_text_stack = new JTextArea(1, 16);
		north_text_stack.setEditable(false);
		north_text_coin = new JTextArea(1, 16);
		north_text_coin.setEditable(false);
		
		south_text_stack = new JTextArea(1, 16);
		south_text_stack.setEditable(false);
		south_text_coin = new JTextArea(1, 16);
		south_text_coin.setEditable(false);
		
		north_stack_label = new JLabel("stack no: ");
		south_stack_label = new JLabel("stack no: ");

		north_coin_label = new JLabel("coins: ");
		south_coin_label = new JLabel("coins: ");

		north.add(new JLabel("   Player 1    "));
		north.add(north_stack_label);
		north.add(north_text_stack);
		north.add(north_coin_label);
		north.add(north_text_coin);
		north.add(north_button);

		south.add(new JLabel("   Player 2    "));
		south.add(south_stack_label);
		south.add(south_text_stack);
		south.add(south_coin_label);
		south.add(south_text_coin);
		south.add(south_button);

		make_sense_of_buttons();

		north.setVisible(true);
		middle.setVisible(true);
		south.setVisible(true);

		this.setLayout(new BorderLayout());
		this.add(north, BorderLayout.NORTH);
		this.add(middle, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);

		this.setVisible(true);
		this.setBounds(10, 10, 800, 800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void set_up_choose_mode() {
		human_vs_human.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				game.set_mode(Game.Mode.HUMAN_VS_HUMAN);
				menu1.setEnabled(false);
				menu.setEnabled(false);
				north_button.setEnabled(true);
				north_text_stack.setEditable(true);
				north_text_coin.setEditable(true);
			}

		});

		human_vs_cpu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				game.set_mode(Game.Mode.HUMAN_VS_CPU);
				menu1.setEnabled(false);
				menu.setEnabled(false);
				menu2.setEnabled(true);
			}

		});

		cpu_vs_cpu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				game.set_mode(Game.Mode.CPU_VS_CPU);
				menu1.setEnabled(false);
				menu.setEnabled(false);
				menu2.setEnabled(true);
			}

		});
		
		easy.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				game.set_difficulty(Game.Difficulty.EASY);
				north_button.setEnabled(true);
				if(game.get_mode() != Game.Mode.CPU_VS_CPU) {
					north_text_stack.setEditable(true);
					north_text_coin.setEditable(true);
				}
				menu2.setEnabled(false);
			}

		});
		
		medium.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				game.set_difficulty(Game.Difficulty.MEDIUM);
				north_button.setEnabled(true);
				if(game.get_mode() != Game.Mode.CPU_VS_CPU) {
					north_text_stack.setEditable(true);
					north_text_coin.setEditable(true);
				}
				menu2.setEnabled(false);
			}

		});
		
		hard.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				game.set_difficulty(Game.Difficulty.HARD);
				north_button.setEnabled(true);
				if(game.get_mode() != Game.Mode.CPU_VS_CPU) {	
					north_text_stack.setEditable(true);
					north_text_coin.setEditable(true);
				}
				menu2.setEnabled(false);
			}

		});
	}

	private void set_up_menu() {
		menu_bar = new JMenuBar();

		menu = new JMenu("GameMode");
		menu1 = new JMenu("NumberOfStacks");
		menu2 = new JMenu("Difficulty");
		rules = new JMenu("Rules");
		options = new JMenu("Options");
		
		human_vs_human = new JMenuItem("Human vs Human");
		human_vs_cpu = new JMenuItem("Human vs CPU");
		cpu_vs_cpu = new JMenuItem("CPU vs CPU");

		menu.add(human_vs_human);
		menu.add(human_vs_cpu);
		menu.add(cpu_vs_cpu);
		
		stacks_num = new JMenuItem[10];

		for (int i = 0; i < 10; i++) {
			stacks_num[i] = new JMenuItem(Integer.toString(i + 1));
			menu1.add(stacks_num[i]);
		}
		
		easy = new JMenuItem("Easy");
		medium = new JMenuItem("Medium");
		hard = new JMenuItem("Hard");
		
		menu2.add(easy);
		menu2.add(medium);
		menu2.add(hard);
		
		read_rules = new JMenuItem("ReadRules");
		rules.add(read_rules);
		
		read_rules.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				rules_dialog.setVisible(true);
			}
			
		});
		
		
		new_game = new JMenuItem("NewGame");
		options.add(new_game);
		
		
		new_game.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				restart_all();
			}
			
		});
		
		menu.setEnabled(false);
		menu2.setEnabled(false);
		menu_bar.add(menu1);
		menu_bar.add(menu);
		menu_bar.add(menu2);
		menu_bar.add(rules);
		menu_bar.add(options);
		this.setJMenuBar(menu_bar);
		
	}

	private void restart_all() {
		for(int i = 0; i < 10; i++)
			middle.all_ten_stacks[i].set_number_of_coins(0);
		game.restart(this);
		menu1.setEnabled(true);
		north_text_coin.setText(null);
		north_text_stack.setText(null);
		south_text_coin.setText(null);
		south_text_stack.setText(null);
		middle.repaint();
	}
	
	private void set_up_choose_number_of_stacks() {

		for (int i = 0; i < 10; i++) {

			int c = i + 1;

			stacks_num[i].addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					
					for(int i = 0; i< 10; i++) {
						game.coins_on_stacks[i] = 0;
						middle.all_ten_stacks[i].set_number_of_coins(0);
					}
					game.total_number_of_coins = 0;
					
					game.set_number_of_stacks(c);
				
					for (int j = 0; j < c; j++) {
						s[j].setEditable(true);
						s[j].setEnabled(true);
					}
					for(int k = c; k < 10; k++) {
						s[k].setEditable(false);
						s[k].setEnabled(false);
					}
					coins_placer.setVisible(true);
				}

			});
		}
	}

	private void create_dialog() {

		JLabel l[] = new JLabel[10];
		s = new JTextArea[10];
		JPanel r[] = new JPanel[10];

		JButton button;

		for (int i = 0; i < 10; i++) {
			if (i != 9)
				l[i] = new JLabel("  " + (i + 1) + ":  ");
			else
				l[i] = new JLabel("" + (i + 1) + ":  ");
			s[i] = new JTextArea(1, 16);
			s[i].setEditable(false);
			s[i].setEnabled(false);
			r[i] = new JPanel(new FlowLayout());
			r[i].add(l[i]);
			r[i].add(s[i]);
		}

		button = new JButton("Done");

		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				boolean error = false;
				int coins;

				for (int i = 0; i < game.get_number_of_stacks(); i++) {
					try {
						coins = Integer.parseInt(s[i].getText());
						if ((coins > 10) || (coins < 1)) throw new Exception();
						for (int j = 0; j < game.get_number_of_stacks(); j++) 
							if(game.coins_on_stacks[j] == coins) throw new Exception();
						game.coins_on_stacks[i] = coins; // logicki stapovi
						game.total_number_of_coins += coins;
						middle.all_ten_stacks[i].set_number_of_coins(coins); // graficki stapovi

					} catch (Exception e) {
						error = true;
						warning.setVisible(true);
						for(int k = 0; k < 10; k++) { //da ne bi ostajalo nekonzistentno stanje
							game.coins_on_stacks[k] = 0;
							middle.all_ten_stacks[i].set_number_of_coins(0);	
						}
						game.total_number_of_coins = 0;
						break;
					}
				}

				if (error == false) {
					coins_placer.setVisible(false);
					menu.setEnabled(true);
					middle.repaint();
				}
				
				for (int i = 0; i < game.get_number_of_stacks(); i++)
					s[i].setText(null);
			}

		});

		coins_placer.setLayout(new GridLayout(11, 1));

		for (int i = 0; i < 10; i++) {
			coins_placer.add(r[i]);
		}

		coins_placer.add(button);
		coins_placer.setBounds(100, 100, 300, 300);

		warning = new JDialog(coins_placer, "Warning");
		warning.add(new JLabel("        Please insert desired number [1,10] of coins for all stacks! The number of coins on each stack must be different! "));
		warning.setBounds(200, 200, 700, 100);

		wrong_move = new JDialog(this, "Wrong move");
		wrong_move.add(new JLabel("              The move you chose is not allowed. You can re-read game rules."));
		wrong_move.setBounds(200, 200, 500, 100);
		
		rules_dialog = new JDialog(this, "Game Rules");
		rules_dialog.setLayout(new GridLayout(25,1));
		rules_dialog.add(new JLabel("  ***** Game setup rules ***** "));
		rules_dialog.add(new JLabel("  1. Click 'NumberOfStacks' from the menu bar."));
		rules_dialog.add(new JLabel("  2. Select desired number of stacks. When pop-up window opens, select desired number of coins for each stack and then click 'Done' button."));
		rules_dialog.add(new JLabel("  3. Click 'GameMode' from the menu bar."));
		rules_dialog.add(new JLabel("  4. Choose desired game mode. If you chose HumanVsHuman, skip steps 5 and 6."));
		rules_dialog.add(new JLabel("  5. Click 'Difficulty' from the menu bar."));
		rules_dialog.add(new JLabel("  6. Choose game difficulty. "));
		rules_dialog.add(new JLabel("  ***** How to play ***** "));
		rules_dialog.add(new JLabel("  In HumanVsHuman mode both players are human players."));
		rules_dialog.add(new JLabel("  In HumanVsCPU mode, Player1 is human player and Player2 is CPU player."));
		rules_dialog.add(new JLabel("  In CPUVsCPU mode, both players are CPU players."));
		rules_dialog.add(new JLabel("  For each human player you will have to fill in 'stack_no' and 'coins' fields before clicking 'Play' button."));
		rules_dialog.add(new JLabel("  By clicking 'Play' for human player you confirm your chosen move."));
		rules_dialog.add(new JLabel("  For each CPU player you will only have to click 'Play' button."));
		rules_dialog.add(new JLabel("  By clicking 'Play' for CPU player, you allow CPU player to choose and play its next move."));
		rules_dialog.add(new JLabel("  ***** Nim rules ***** "));
		rules_dialog.add(new JLabel("  Number of coins on each stack must always be unique, unless that number is zero."));
		rules_dialog.add(new JLabel("  If your opponent removed X coins in previous move, you can only remove up to 2X coins in your move."));
		rules_dialog.add(new JLabel("  In each move, you must remove at least one coin."));
		rules_dialog.add(new JLabel("  You are not allowed to make a move that would be against previous rules."));
		rules_dialog.add(new JLabel("  At any moment you can see the number of coins removed in last move displayed in the opponent's 'coins' field ."));
		rules_dialog.add(new JLabel("  The one who removes the last coin from the deck wins."));
		rules_dialog.add(new JLabel("  ***** Starting new game ***** "));
		rules_dialog.add(new JLabel("  At any moment you can click 'Options -> NewGame', which will clear the deck and begin new game. "));
		rules_dialog.add(new JLabel("  Each time you start a new game, you must repeat game setup steps. "));
		rules_dialog.setBounds(200, 200, 900, 400);
		

		welcome = new JDialog(this, "Welcome");
		welcome.add(new JLabel("    Please read the rules from the menu bar before you start your first game!"));
		welcome.setBounds(200, 200, 460, 100);
		welcome.setVisible(true);
	}

	void we_have_a_winner(Game.Last winner) {
		winner_dialog = new JDialog(this, "Winner");
		winner_dialog.setBounds(200,200,100,100);
		if (winner == Game.Last.PLAYER_1)
			winner_dialog.add(new JLabel("            Player 1 won!"));
		else
			winner_dialog.add(new JLabel("            Player 2 won!"));
		winner_dialog.setVisible(true);
	}

	void make_sense_of_buttons() {

		north_button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				switch (game.get_mode()) {
				case HUMAN_VS_CPU:
				case HUMAN_VS_HUMAN:
					boolean error = false;
					int stick = 0, coins = 0;
					try {
						stick = Integer.parseInt(north_text_stack.getText());
						coins = Integer.parseInt(north_text_coin.getText());
						stick -= 1;
						if ((stick > 9) || (stick < 0) || (coins > 10) || (coins < 1))
							throw new Exception();
					} catch (Exception e) {
						error = true;
						wrong_move.setVisible(true);
					}
					if(error == true) break;
					boolean test = game.remove_coins_from_stack(coins, stick);
					if (test == true) {
						north_button.setEnabled(false);
						north_text_stack.setEditable(false);
						north_text_coin.setEditable(false);
						south_text_stack.setText(null);
						south_text_coin.setText(null);
						if(game.get_mode() == Game.Mode.HUMAN_VS_HUMAN) {
							south_text_stack.setEditable(true);
							south_text_coin.setEditable(true);
						}
						south_button.setEnabled(true);
						middle.repaint();
					}
					break;
				case CPU_VS_CPU:
					StrategyTree CPU_strategy = new StrategyTree(game);
					CPU_strategy.make_level_one();
					if((game.get_difficulty() == Game.Difficulty.MEDIUM) || (game.get_difficulty() == Game.Difficulty.HARD))
						CPU_strategy.make_level_two();
					if (game.get_difficulty() == Game.Difficulty.HARD) 
						CPU_strategy.make_level_three();
					Move next_move = CPU_strategy.calculate_best_move();
					boolean test1 = game.remove_coins_from_stack(next_move.num_of_coins_to_remove, next_move.stack_to_remove_from);
					if(test1 == true) {
						north_button.setEnabled(false);
						north_text_stack.setText(Integer.toString(next_move.stack_to_remove_from));
						north_text_coin.setText(Integer.toString(next_move.num_of_coins_to_remove));
						north_text_stack.setEditable(false);
						north_text_coin.setEditable(false);
						south_button.setEnabled(true);
						south_text_stack.setText(null);
						south_text_coin.setText(null);
						south_text_stack.setEditable(true);
						south_text_coin.setEditable(true);
						middle.repaint();
					}
					break;
				default:
					break;
				}
			}

		});

		south_button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				switch (game.get_mode()) {
				case HUMAN_VS_HUMAN:
					int stick = 0, coins = 0;
					boolean error = false;
					try {
						stick = Integer.parseInt(south_text_stack.getText());
						stick -= 1;
						coins = Integer.parseInt(south_text_coin.getText());
					} catch (Exception e) {
						error = true;
						wrong_move.setVisible(true);
					}
					if(error == true) break;
					boolean test = game.remove_coins_from_stack(coins, stick);
					if (test == true) {
						north_button.setEnabled(true);
						north_text_stack.setText(null);
						north_text_coin.setText(null);
						north_text_stack.setEditable(true);
						north_text_coin.setEditable(true);
						south_text_stack.setEditable(false);
						south_text_coin.setEditable(false);
						middle.repaint();
					}
					break;
				case HUMAN_VS_CPU:
				case CPU_VS_CPU:
					StrategyTree CPU_strategy = new StrategyTree(game);
					CPU_strategy.make_level_one();
					if((game.get_difficulty() == Game.Difficulty.MEDIUM) || (game.get_difficulty() == Game.Difficulty.HARD))
						CPU_strategy.make_level_two();
					if (game.get_difficulty() == Game.Difficulty.HARD) 
						CPU_strategy.make_level_three();
					Move next_move = CPU_strategy.calculate_best_move();
					boolean test1 = game.remove_coins_from_stack(next_move.num_of_coins_to_remove, next_move.stack_to_remove_from);
					if(test1 == true) {
						north_button.setEnabled(true);
						north_text_stack.setText(null);
						north_text_coin.setText(null);
						north_text_stack.setEditable(true);
						north_text_coin.setEditable(true);
						south_button.setEnabled(false);
						south_text_stack.setEditable(false);
						south_text_stack.setEditable(false);
						south_text_stack.setText(Integer.toString(next_move.stack_to_remove_from + 1));
						south_text_coin.setText(Integer.toString(next_move.num_of_coins_to_remove));
						middle.repaint();
					}
					break;
				default:
					break;

				}
			}
		});

	}
}
