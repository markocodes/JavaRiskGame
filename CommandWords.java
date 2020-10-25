/**
 * This class lists the command words for the game
 * @author Mmedara Josiah
 * @version 1.0
 */

public class CommandWords
{
    private static final String[] validCommands = {
            "state", "pass", "attack from", "attack", "quit", "help"
    };

    public CommandWords(){
        //do nothing for now
    }

    /**
     * checks if a string is a valid command
     * @param s is the string being checked
     * @return true if s is a string
     */
    public boolean isCommand(String s){
        for(String c: validCommands){
            if(c.equals(s)){
                return true;
            }
        }
        return false;
    }
}
