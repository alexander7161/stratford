import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;

/**
 * This class is adapted from the "World of Zuul" application. 
 * by Michael Kölling and David J. Barnes.
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 * 
 * It also provides helpful information for using the commands.
 *
 * @author  Alexander Davis
 * @version 8.12.2017
 */

public class CommandWords
{
    // a constant HashMap that holds all valid command words and their help message.
    private static final HashMap<String, String> validCommands = new HashMap<String, String>()
    {{
        put("go", "<direction> - From the available exits, choose a single direction");
        put("quit", "- Quit the game.");
        put("help", "- Display this help message.");
        put("pickup", "<Object> - From the objects in your vicinity, pickup one.");
        put("drop", "<Object> - Drop an object you are carrying.");
        put("back", "- Return to the last room.");
        put("take", "<object> <Person> - take an object from a person in your vicinity.");
        put("give", "<object> <person> - give an object to a person in your vicinity."); 
    }};


    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        // nothing to do at the moment...
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        Set<String> commandSet = validCommands.keySet();
        String[] commands = commandSet.stream().toArray(String[]::new);
        for(int i = 0; i < commands.length; i++) {
            if(commands[i].equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    /**
     * Print all valid commands to System.out.
     */
    public void showAll() 
    {
        Set<String> commandSet = validCommands.keySet();
        String[] commands = commandSet.stream().toArray(String[]::new); // from https://stackoverflow.com/questions/5982447/how-to-convert-setstring-to-string

        for(String command : commands)
        {
            System.out.println(command + " " + validCommands.get(command));
        }
        System.out.println();
    }
    
    /**
     * @return String with help information for a specific command.
     */
    public String getHelp(String helpCommand)
    {
        String returnString = null;
        if(validCommands.containsKey(helpCommand)) {
            returnString = helpCommand + " " + validCommands.get(helpCommand);
        }
        return returnString;
    }
}
