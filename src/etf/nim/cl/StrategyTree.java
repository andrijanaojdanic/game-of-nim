package etf.nim.oa130547d;

import java.util.LinkedList;

public class StrategyTree {
	
	Game game;
	public Move root;
	LinkedList<Move> level1, level2, level3;
	
	public StrategyTree(Game g) {
		root = new Move(g);
		game = g;
		level1 = level2 = level3 = null;
	}
	
	public void make_level_one() {
		
		level1 = new LinkedList<Move>();
		
		int upper_limit = game.number_of_coins_removed_in_last_round * 2;
		
		for(int i = 1; i <= upper_limit; i++) { // za svaki moguci broj skinutih novcica
			for(int j = 0; j < game.get_number_of_stacks(); j++) { // za svaki stap
				
				if(root.coins_on_stack[j] >= i) {
					int new_value = game.coins_on_stacks[j] - i;
					boolean allowed = true;
					for(int k = 0; k < game.get_number_of_stacks(); k++)
						if((game.coins_on_stacks[k] == new_value) && (new_value != 0)) { 
							allowed = false;
							break;
						}
					
					//kreiranje novog moguceg poteza i dodavanje u odgovarajuce liste
					if(allowed == true) {
						Move new_move = new Move(root, j, i);
						int nim_sum = 0;
						for(int b = 0; b < game.get_number_of_stacks(); b++) 
							nim_sum ^= new_move.coins_on_stack[b];
						if(nim_sum == 0) new_move.value = 3; // za CPU je povoljno da nim_sum nakon njegovog poteza bude 0
						else new_move.value = -3; // suprotno je nepovoljno
						new_move.father.sons.add(new_move);
						level1.add(new_move);
					}
					
				}
				
			}
		}
		
	}

	public void make_level_two() {
		
		level2 = new LinkedList<Move>();
		
		if(level1.isEmpty() == false) {
			
			for (int i = 0; i < level1.size(); i++) { // za svaki cvor iz nivoa 1
				Move father_move = level1.get(i); 
				int upper_limit = father_move.num_of_coins_to_remove * 2;
				
				for(int j = 1; j < upper_limit; j++) { // za svaku vrednost skinutih novcica
					for(int k = 0; k < game.get_number_of_stacks(); k++) { // za svaki stap
						if(father_move.coins_on_stack[k] >= j) {
							boolean allowed = true;
							int new_value = father_move.coins_on_stack[k] - j;
							for(int m = 0; m < game.get_number_of_stacks(); m++) 
								if((father_move.coins_on_stack[m] == new_value) && (new_value != 0)) {
									allowed = false;
									break;
								}
							if(allowed == true) {
								Move new_move = new Move(father_move, k, j);
								int nim_sum = 0;
								for(int s = 0; s < game.get_number_of_stacks(); s++)
									nim_sum ^= new_move.coins_on_stack[s];
								if(nim_sum == 0) new_move.value = -2; //za CPU je nepovoljno ako human ostavi za sobom 0-nim-sum
								else new_move.value = 2; //povoljno je ako za sobom ostavi non-0-nim-sum
								new_move.father = father_move;
								father_move.sons.add(new_move);
								level2.add(new_move);
							}
						}
					}
						
				}
			}
		}
	}

	public void make_level_three() {
		level3 = new LinkedList<Move>();
		
		if((level2.isEmpty() == false) && (level1.isEmpty() == false)) {
			
			for (int i = 0; i < level2.size(); i++) { // za svaki cvor iz nivoa 2
				Move father_move = level2.get(i);
				int upper_limit = father_move.num_of_coins_to_remove * 2;
				
				for(int j = 1; j < upper_limit; j++) { // za svaku vrednost skinutih novcica
					for(int k = 0; k < game.get_number_of_stacks(); k++) { // za svaki stap
						if(father_move.coins_on_stack[k] >= j) {
							boolean allowed = true;
							int new_value = father_move.coins_on_stack[k] - j;
							for(int m = 0; m < game.get_number_of_stacks(); m++) 
								if((father_move.coins_on_stack[m] == new_value) && (new_value != 0)) {
									allowed = false;
									break;
								}
							if(allowed == true) {
								Move new_move = new Move(father_move, k, j);
								int nim_sum = 0;
								for(int s = 0; s < game.get_number_of_stacks(); s++)
									nim_sum ^= new_move.coins_on_stack[s];
								if(nim_sum == 0) new_move.value = 1; 
								else new_move.value = -1; 
								new_move.father = father_move;
								father_move.sons.add(new_move);
								level3.add(new_move);
							}
						}
					}
						
				}
			}
		}
	}
	
	public Move calculate_best_move() {
		
		Move best_move = null;
		Move temp = null;
		
		//easy varijanta
		if(game.get_difficulty() == Game.Difficulty.EASY) {
			
			boolean found_finish_move = false;
		
			for(int i = 0; i < level1.size(); i++) { // ukoliko ima pobednicki potez, a postoji vise mogucih zero-nim-sum poteza
				temp = level1.get(i);
				int empty_stick_counter = 0;
				for(int j = 0; j < game.get_number_of_stacks(); j++)
					if(temp.coins_on_stack[j] == 0) empty_stick_counter++;
				if(empty_stick_counter == game.get_number_of_stacks()) {
					found_finish_move = true;
					break;
				}
			}
			
			if(found_finish_move == false) {
				for(int i = 0; i < level1.size(); i++) {
					temp = level1.get(i);
					if(temp.value == 3) break;
				}
			}
			
			best_move = temp;
		}
		
		//medium varijanta
		if(game.get_difficulty() == Game.Difficulty.MEDIUM) {
			
			for(int i = 0; i < level2.size(); i++) { // za svaki cvor iz nivoa 2
				temp = level2.get(i);
				int empty_sticks_counter = 0;
				for(int j = 0; j < game.get_number_of_stacks(); j++) 
					if(temp.coins_on_stack[j] == 0) empty_sticks_counter++;
				if(empty_sticks_counter == game.get_number_of_stacks()) temp.value = -1000;  // veoma je lose za CPU ukoliko odigra potez nakon kog protivnik zavrsava partiju
				temp.father.value += temp.value;
			}
			
			
			for(int i = 0; i < level1.size(); i++) 
				if(level1.get(i).sons.size() == 0)
					level1.get(i).value += 20; //visoko se ceni potez nakon kog CPU zavrsava partiju
			
			Move max = level1.get(0);
			for(int i = 1; i < level1.size(); i++) {
				if (level1.get(i).value > max.value)
					max = level1.get(i);
			}
			best_move = max;
		}
		
		
		//hard varijanta
		if((game.get_difficulty() == Game.Difficulty.HARD) && (level1.size() != 0)) {
			
			for(int i = 0; i < level3.size(); i++) { // za svaki cvor iz nivoa 3, azuriranje vrednosti u nivou 2
				temp = level3.get(i);
				int empty_stick_counter = 0;
				for(int j = 0; j < game.get_number_of_stacks(); j++)
					if(temp.coins_on_stack[j] == 0) empty_stick_counter++;
				if(empty_stick_counter == game.get_number_of_stacks())
					temp.value += 10; //za CPU je povoljno ukoliko postoji mogucnost da zavrsi u nekom narednom potezu
				temp.father.value += temp.value;
			}
			
			for(int i = 0; i < level2.size(); i++) { // za svaki cvor iz nivoa 2, azuriranje vrednosti u nivou 1
				temp = level2.get(i);
				if(temp.sons.size() == 0) 
					temp.value = -1000; //za CPU je veoma nepovoljno ukoliko postoji mogucnost da protivnik zavrsi u narednom potezu
				temp.father.value += temp.value;
			}
			
			
			for(int i = 0; i < level1.size(); i++) // visoko se ceni potez nakon kog CPU zavrsava partiju 
				if(level1.get(i).sons.size() == 0)
					level1.get(i).value += 20;
			
			Move max = level1.get(0);
			for(int i = 1; i < level1.size(); i++) {
				if (level1.get(i).value > max.value)
					max = level1.get(i);
				
			}
			
			best_move = max;
		}
		
		return best_move;
		
	}
}
