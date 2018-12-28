package etf.nim.oa130547d;

import java.util.LinkedList;

public class Move {
	public Move father;
	public LinkedList<Move> sons;
	
	public int[] coins_on_stack;
	public int value;
	public int level;
	public int num_of_coins_to_remove;
	public int stack_to_remove_from;
	
	//kostruktor za pravljenje svih poteza osim korena stabla strategije
	public Move(Move father, int stick_to_remove_from, int num_of_coins_to_remove) {
		this.father = father;
		sons = new LinkedList<Move>();
		coins_on_stack = new int[10];
		for(int i = 0; i < 10; i++) coins_on_stack[i] = this.father.coins_on_stack[i];
		coins_on_stack[stick_to_remove_from] -= num_of_coins_to_remove;
		level = father.level + 1;
		this.num_of_coins_to_remove = num_of_coins_to_remove;
		this.stack_to_remove_from = stick_to_remove_from;
	}
	
	//konstruktor za pravljenje poteza koji ce biti koren stabla strategije
	public Move(Game g) {
		this.father = null;
		sons = new LinkedList<Move>();
		coins_on_stack = new int[10];
		for(int i = 0; i < 10; i++) coins_on_stack[i] = g.coins_on_stacks[i];
		level = 0;
		num_of_coins_to_remove = g.number_of_coins_removed_in_last_round; // u slucaju roota nije bas adekvatan naziv num_of_coins_to_remove
	}
}
