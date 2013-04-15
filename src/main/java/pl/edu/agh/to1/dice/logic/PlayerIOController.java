package pl.edu.agh.to1.dice.logic;

/**
 * Created with IntelliJ IDEA.
 * User: Tomek
 * Date: 14.04.13
 * Time: 07:51
 * To change this template use File | Settings | File Templates.
 */
public interface PlayerIOController {
    public String getCommand();
    public void sendResponse(CommandResponse response);
}
