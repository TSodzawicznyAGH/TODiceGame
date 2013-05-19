package pl.edu.agh.to1.dice.logic;

import java.awt.*;
import java.util.*;
import pl.edu.agh.to1.dice.logic.commands.Command;
import pl.edu.agh.to1.dice.logic.commands.CommandResponse;
import pl.edu.agh.to1.dice.logic.commands.FigureCommand;
import pl.edu.agh.to1.dice.logic.commands.ValueCommandResponse;
import pl.edu.agh.to1.dice.logic.io.GameOutputController;
import pl.edu.agh.to1.dice.logic.io.IOController;

public class Bot implements GameOutputController, IOController{

	private Set<Command> availableCommands;
	private Table table;
	private DiceSet diceSet;
	private Player player;
	
	public void init(java.util.List<Command> availableCommands, Game game) {
		this.availableCommands = new HashSet<Command>(availableCommands);
	}

	@SuppressWarnings("unchecked")
	public Command read(Set<Command> availableCommands) {  
		this.availableCommands = availableCommands;	
		Command command = null;
	//	CommandResponse max1,tmp;
		ValueCommandResponse<Integer> max,tmp;
		max = new ValueCommandResponse<Integer>(-1);
		for(Command com: availableCommands){
            try {
                FigureCommand figureCommand = (FigureCommand) com;

                figureCommand.setDiceSet(diceSet);
			    tmp = (ValueCommandResponse<Integer>) table.testHandle(figureCommand);
    		//	tmp = table.testHandle(com);
	    		if( tmp.getValue() > max.getValue()){
		    		max = tmp;
			    	command = com;
    			}
            }
            catch (ClassCastException e) { /* nothing happened */ }
		}
		return command;
	}
	public void callback(CommandResponse response) {
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
