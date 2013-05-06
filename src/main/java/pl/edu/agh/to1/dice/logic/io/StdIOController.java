package pl.edu.agh.to1.dice.logic.io;

import pl.edu.agh.to1.dice.logic.commands.Command;
import pl.edu.agh.to1.dice.logic.commands.CommandResponse;
import pl.edu.agh.to1.dice.logic.commands.FigureCommand;
import pl.edu.agh.to1.dice.logic.commands.ValueCommandResponse;

import javax.annotation.processing.SupportedSourceVersion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

public class StdIOController implements IOController {
    private BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void init(Set<Command> availableCommands) {
        System.out.println("Hello there");
    }

    @Override
    public Command read(Set<Command> availableCommands) {
        String commandString = null;
        while (true) {
            try {
                System.out.println("Choose one:");
                for (Command command : availableCommands) {
                    System.out.print(command.toString());
                    System.out.print(", ");
                }
                System.out.println();
                System.out.print("> ");

                commandString = stdin.readLine();

                if (commandString != null) {
                    for (Command command : availableCommands) {
                        if (commandString.equals(command.toString())) {
                            return command;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void callback(CommandResponse response) {
        try {
            ValueCommandResponse<Integer> valueCommandResponse = (ValueCommandResponse<Integer>) response;
            System.out.format("%d\n", valueCommandResponse.getValue());
        }
        catch (ClassCastException e) {
            System.out.print("Some error occured, retry...");
        }
    }

    @Override
    public void send(Command command) {
    }
}
