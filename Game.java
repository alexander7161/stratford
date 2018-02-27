import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Random;

/**
 * Stratford, the game.
 * 
 *  To start the game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This Game class creates and initialises all other classes: it creates all
 *  rooms, actors, objects, creates the parser and starts the game.
 * 
 * @author  Alexander Davis
 * @version 8.12.2017
 */

public class Game 
{
    private CommandProcessor commandProcessor;
    private Player player;
    private Room angelLane, theatreSquare, morrisons, stratfordCentre, lidl, stratfordStation, westfield, pool, park, eastVillage, stratfordInternational, waitrose;
    private Actor friend, salesclerk;
    
    /**
     * Create the game, create the actors and initialise its internal map.
     */
    public Game() 
    {
        
        createRooms();
        commandProcessor = new CommandProcessor();
        createActors();
        player = new Player(angelLane);
        

    }
    
    public static void main(String args[])
    {
        Game game = new Game();
        game.play();
    }
    
    /**
     * Create all the actors, including the player.
     * set the objects they carry.
     * set rooms they will not access.
     */
    private void createActors()
    {
        
        //Create friend actor, starting in westfield with greeting set 2.
        friend = new Actor("Friend", westfield, 2);
        //Give friend object oyster.
        friend.setObject("oyster");
        //Disallow entry to stations for friend.
        friend.setNoEntryExits(stratfordStation);
        friend.setNoEntryExits(stratfordInternational);
        //Add friend to HashMap actors.
        
        salesclerk = new Actor("Salesclerk", westfield, 3);
        
    }

    
    /**
     * Create all the rooms, link their exits together, and add objects to the rooms.
     */
    private void createRooms()
    {
        // create the rooms
        angelLane = new Room("in Your KCL accomodation, Angel Lane.");
        theatreSquare = new Room("in the cultural centre of Stratford, Theatre Square.");
        morrisons = new Room("in Morrisons, a large supermarket.");
        stratfordCentre = new Room("in the No. 1 shopping destination in Stratford, \n the Stratford Centre.");
        lidl = new Room("in the industry leading budget supermarket, Lidl.");
        stratfordStation = new Room("in Stratford Station.");
        westfield = new Room("in Westfield, the largest shopping centre in Western Europe.");
        pool = new Room("in the London Aquatics centre.");
        park = new Room("in the Olympic Park");
        eastVillage = new Room("in the East Village");
        stratfordInternational = new Room("in Stratford international station");
        waitrose = new Room("in the UK's leading high end supermarket, Waitrose.");
        
        // initialise room exits and item numbers. Add them to an ArrayList rooms.
        angelLane.setExit("south", theatreSquare);
        angelLane.setExit("west", stratfordStation);
        angelLane.setExit("north", eastVillage);
        angelLane.addObject("bed");
        
        eastVillage.setExit("south", angelLane);
        eastVillage.setExit("west", park);
        
        stratfordInternational.setExit("south", park);
        stratfordInternational.setMagicRoom();
        //Not added to rooms as not to be teleported to.
        
        park.setExit("east", eastVillage);
        park.setExit("south", westfield);
        park.setExit("north", stratfordInternational);
        
        westfield.setExit("east", stratfordStation);
        westfield.setExit("south", pool);
        westfield.setExit("north", park);
        westfield.setExit("in", waitrose);
        
        waitrose.setExit("out", westfield);
        waitrose.addObject("salad");
        waitrose.addObject("champagne");
        
        pool.setExit("north", westfield);
        
        theatreSquare.setExit("north", angelLane);
        theatreSquare.setExit("east", morrisons);
        theatreSquare.setExit("west", stratfordCentre);
        theatreSquare.addObject("costume");
        theatreSquare.addObject("bench");
        theatreSquare.addObject("litter");
        
        stratfordCentre.setExit("east", theatreSquare);
        stratfordCentre.setExit("south", lidl);
        stratfordCentre.setExit("north", stratfordStation);
        stratfordCentre.addObject("litter");
        
        stratfordStation.setExit("south", stratfordCentre);
        stratfordStation.setExit("east", angelLane);
        stratfordStation.setExit("west", westfield);
        stratfordStation.addObject("bench");
        
        morrisons.setExit("west", theatreSquare);
        morrisons.addObject("salad");
        
        lidl.setExit("north", stratfordCentre);
        lidl.addObject("sandwich");
    }
    
    

    /**
     *  Main play routine.  Loops until end of play.
     *  Checks whether the player has won.
     */
    public void play()
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            finished = commandProcessor.start();
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Stratford!");
        System.out.println("You're running late for your lecture.");
        System.out.println("Plus you're feeling very hungry");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        commandProcessor.printDescription();
    }
}
