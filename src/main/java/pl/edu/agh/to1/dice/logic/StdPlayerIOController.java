package pl.edu.agh.to1.dice.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: Tomek
 * Date: 14.04.13
 * Time: 08:18
 * To change this template use File | Settings | File Templates.
 */
public class StdPlayerIOController implements PlayerIOController {
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    String std_string = null;

    @Override
    public String getCommand() {
        std_string = null;
        try {
            System.out.print("> ");
            std_string = stdin.readLine().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return std_string;
    }

    @Override
    public void sendResponse(CommandResponse response) {
        switch (response) {
            case CMD_UNKNOWN:
                System.out.format("Invalid command: \"%s\". Type 'h' for help.\n", std_string);
                break;
            case CMD_FAILED:
                System.out.format("Cannot execute: \"%s\". Type 'h' for help.\n", std_string);
                break;
        }
    }
}
