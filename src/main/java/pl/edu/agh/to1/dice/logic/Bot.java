package pl.edu.agh.to1.dice.logic;

import java.util.Set;
import pl.edu.agh.to1.dice.logic.commands.Command;
import pl.edu.agh.to1.dice.logic.commands.CommandResponse;
import pl.edu.agh.to1.dice.logic.commands.ValueCommandResponse;
import pl.edu.agh.to1.dice.logic.io.GameOutputController;
import pl.edu.agh.to1.dice.logic.io.IOController;

public class Bot implements GameOutputController, IOController{

	private Set<Command> availableCommands;
	private Table table;
	private DiceSet diceSet;
	private Player player;
	
	public void init(Set<Command> availableCommands) {
		this.availableCommands = availableCommands;		
	}

	@SuppressWarnings("unchecked")
	public Command read(Set<Command> availableCommands) {  
		this.availableCommands = availableCommands;	
		Command command = null;
	//	CommandResponse max1,tmp;
		ValueCommandResponse<Integer> max,tmp;
		max = new ValueCommandResponse<Integer>(-1);
		for(Command com: availableCommands){
			tmp = (ValueCommandResponse<Integer>) table.testHandle(com); 
		//	tmp = table.testHandle(com); 
			if( tmp.getValue() > max.getValue()){
				max = tmp;
				command = com;
			}			
		}
		table.doHandle(command); // chyba b³edne jest to ale to zale¿y od koncepcji kto zapisuje wartoœc do tablicy
		return command;
	}
	public void callback(Command command) {
		// TODO Auto-generated method stub		
	}

	public void init(Player player, GameState initialState) {
		table = initialState.getTables().get(player);
		diceSet = initialState.getDiceSet();
		this.player = player;
	}

	public void update(GameState newState) {
		table = newState.getTables().get(player);
		diceSet = newState.getDiceSet();
	}

	public void finish(Set<Player> winners) {
		// TODO Auto-generated method stub		
	}

	public void terminate() {
		// TODO Auto-generated method stub
		
	}

	public void send(Command command) {
		// TODO Auto-generated method stub	
	}

}
