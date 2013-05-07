package pl.edu.agh.to1.dice.logic.io;

import pl.edu.agh.to1.dice.logic.commands.*;

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

                commandString = stdin.readLine().trim();

                if (commandString != null) {
                    for (Command command : availableCommands) {
                        if (commandString.equals(command.getCommandString())) {
                            return command;
                        }
                    }
                    if (commandString.startsWith("l")) {
                        int number = Integer.parseInt(commandString.substring(1).trim());
                        return new ValueGameCommand<Integer>("l", number-1);
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
            System.out.format("Otrzymales punktow: %d\n", valueCommandResponse.getValue());
        }
        catch (ClassCastException e) { }
        if (response == CommandResponses.COMMAND_UNKNOWN) {
            System.out.println("Nieznane polecenie");
        }
        if (response == CommandResponses.COMMAND_FAILED) {
            System.out.println("Polecenie nie powiodlo sie, sprobuj ponownie.");
        }
    }

    @Override
    public void send(Command command) {
    }
}
