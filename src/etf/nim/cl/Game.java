package etf.nim.oa130547d;

public class Game {

	public enum Mode {
		HUMAN_VS_HUMAN, HUMAN_VS_CPU, CPU_VS_CPU
	};

	public enum Last {
		PLAYER_1, PLAYER_2
	};
	
	public enum Difficulty{
		EASY, MEDIUM, HARD
	};

	Mode mode;
	Difficulty difficulty;
	int number_of_stacks;
	Window window;
	Deck deck;
	
	int number_of_coins_removed_in_last_round;
	Last who_played_last;
	int total_number_of_coins;
	
	public int coins_on_stacks[];

	public Game() {
		who_played_last = Last.PLAYER_2; // kako bi inicijalno PLAYER1 igrao
		total_number_of_coins = 0;
		number_of_stacks = 0;
		window = null;
		number_of_coins_removed_in_last_round = 10; // na pocetku je dozvoljeno proizvoljno
		coins_on_stacks = new int[10];
		for (int i = 0; i < 10; i++)
			coins_on_stacks[i] = 0;
	}

	public void set_mode(Mode mode) {
		this.mode = mode;
	}

	public Mode get_mode() {
		return mode;
	}

	public void set_number_of_stacks(int n) {
		number_of_stacks = n;
	}

	public int get_number_of_stacks() {
		return number_of_stacks;
	}

	public void set_window(Window p) {
		window = p;
		deck = p.middle;
	}

	public boolean remove_coins_from_stack(int coins, int stick) {
		if ((coins > 2 * number_of_coins_removed_in_last_round) || (coins > coins_on_stacks[stick])) {
			window.wrong_move.setVisible(true);
			return false;
		}
		
		int new_value = coins_on_stacks[stick] - coins;
		for(int i = 0; i < number_of_stacks; i++)
			if((coins_on_stacks[i] == new_value) && (new_value != 0)) { 
				window.wrong_move.setVisible(true);
				return false;
			}
		
		coins_on_stacks[stick] -= coins;
		total_number_of_coins -= coins;
		
		if (who_played_last == Last.PLAYER_1)
			who_played_last = Last.PLAYER_2;
		else
			who_played_last = Last.PLAYER_1;
		
		number_of_coins_removed_in_last_round = coins;
		deck.all_ten_stacks[stick].set_number_of_coins(coins_on_stacks[stick]);
		
		if (total_number_of_coins == 0 || (is_there_any_allowed_move() == false)) 
			window.we_have_a_winner(who_played_last);
		
		return true;
	}

	public boolean is_there_any_allowed_move() {
		int upper_limit = number_of_coins_removed_in_last_round * 2;
		if (upper_limit > 10) upper_limit = 10;
		
		int num_of_impossible_rounds = 0; // round1 = iteracija za pokusaj uklanjanja jednog novcica sa bilo kog stapa, round2 za dva itd
		int num_of_possible_moves_per_round = 0; // ekvivalentno sa num_of_non_empty_sticks
		
		for (int i = 0; i < 10; i++) 
			if(coins_on_stacks[i] > 0) 
				num_of_possible_moves_per_round++;
		
		for (int i = 1; i <= upper_limit; i++) { // za sve moguce vrednosti novcica za skidanje
			int num_of_wrong_moves = 0;
			for (int j = 0; j < number_of_stacks; j++) { // za sve sticko-ve
				boolean wrong_move = false;
				if (coins_on_stacks[j] >= i) {
					int new_value = coins_on_stacks[j] - i; //sa prvog stika skidam jedan novcic
					for(int k = 0; k < number_of_stacks; k++) { // i onda proveravam za sve sticko-ve ponovo je li okej ovako izmeniti
						if ((coins_on_stacks[k] == new_value) && (new_value != 0) && (k != j)) {
							wrong_move = true;
							break;
						} 
					}
				}
				if (wrong_move == true) num_of_wrong_moves++;
			}
			if (num_of_wrong_moves == num_of_possible_moves_per_round) 
				num_of_impossible_rounds++;
			else break; // moze se stati sa daljim ispitivanjem jer je bar jedan potez sigurno moguc
		}
		
		if(num_of_impossible_rounds == upper_limit) return false;
		else return true;
	}
	
	public void set_difficulty(Difficulty d) {
		difficulty = d;
	}
	
	public Difficulty get_difficulty() {
		return difficulty;
	}
	
	public void restart(Window w) {
		who_played_last = Last.PLAYER_2; // kako bi inicijalno PLAYER1 igrao
		total_number_of_coins = 0;
		number_of_stacks = 0;
		window = w;
		deck = w.middle;
		number_of_coins_removed_in_last_round = 10; // na pocetku je dozvoljeno proizvoljno
		coins_on_stacks = new int[10];
		for (int i = 0; i < 10; i++)
			coins_on_stacks[i] = 0;
	}
}
