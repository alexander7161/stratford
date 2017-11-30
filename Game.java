import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room angelLane, theatreSquare, morrisons, stratfordCentre, lidl, stratfordStation;
    public Actor player, stationGuard, friend, actor;
    private HashMap<Room, Actor> actors;
    private ArrayList<Actor> actorsSet;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        createActors();
        parser = new Parser();
        actorsSet = new ArrayList<>();
        Set<Room> actorRooms = actors.keySet();
        for(Room room : actorRooms) {
            actorsSet.add(actors.get(room));
        }


    }
    
    public void createActors()
    {
        actors = new HashMap<>();
        player = new Actor("player", angelLane, 2);
        stationGuard = new Actor("Station Guard", stratfordStation, 1);
        actors.put(stratfordStation, stationGuard);
        friend = new Actor("Your Friend", angelLane, 3);
        actors.put(angelLane, friend);
        actor = new Actor("Actor", theatreSquare, 4);
        actors.put(theatreSquare, actor);
    }
    
    
    /**
     * Create all the rooms and link their exits together.
     */
    public void createRooms()
    {
        // create the rooms
        angelLane = new Room("in Your KCL accomodation, Angel Lane.");
        theatreSquare = new Room("in the cultural centre of Stratford, Theatre Square.");
        morrisons = new Room("in Morrisons, a large supermarket.");
        stratfordCentre = new Room("in the No. 1 shopping destination in Stratford, the Stratford Centre.");
        lidl = new Room("in the industry leading budget supermarket, Lidl.");
        stratfordStation = new Room("in Stratford Station.");
        
        // initialise room exits
        angelLane.setExit("south", theatreSquare);
        angelLane.setExit("west", stratfordStation);
        angelLane.addObject(1);
        
        theatreSquare.setExit("north", angelLane);
        theatreSquare.setExit("east", morrisons);
        theatreSquare.setExit("west", stratfordCentre);
        theatreSquare.addObject(2);
        
        stratfordCentre.setExit("east", theatreSquare);
        stratfordCentre.setExit("south", lidl);
        stratfordCentre.setExit("north", stratfordStation);
        
        stratfordStation.setExit("south", stratfordCentre);
        stratfordStation.setExit("east", angelLane);
        
        morrisons.setExit("west", theatreSquare);
        morrisons.addObject(5);
        
        lidl.setExit("north", stratfordCentre);
        lidl.addObject(3);
    }
    
    

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            if(friend.getInteractionComplete() || player.isCarrying("sandwich")){
            stationGuard.getInteractionComplete();
        }

        
        
            for (Actor a : actorsSet) {
                   if(metPlayer(a)) {
                       a.greet();
                       a.hasMet();
                   }
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printDescription();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if(commandWord.equals("pickup")) {
            pickupObject(command);
        }
        else if(commandWord.equals("take")) {
            takeObject(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            player.setCurrentRoom(nextRoom);
            printDescription();
        }
    }
    
    private void pickupObject(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Pickup what?");
            return;
        }

        Object objectToPickup = player.getCurrentRoom().getObject(command.getSecondWord());
        
        if (objectToPickup == null) {
            System.out.println("There is no object!");
        }
        else if (objectToPickup.getPickupable()) {
            player.addObject(objectToPickup);
            player.getCurrentRoom().removeObject(objectToPickup);
            printDescription();
        }
        else {
            System.out.println(objectToPickup.getPickupString());
        }
    }


    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    private void printDescription()
    {
        System.out.println(player.getCurrentRoom().getLongDescription());
        if(player.getObjectCarryString("Currently carrying:") != null) {System.out.println(player.getObjectCarryString("Currently carrying:"));}
        if(getActorString() != null) {System.out.println(getActorString());}
    }
    
    private String getActorString()
    {
        Boolean actorPresent = false;
        String returnString = "People Nearby:";
        for (Actor a : actorsSet) {
            if(metPlayer(a)) {
                returnString += " " + a.getName();
                actorPresent = true;
            }
            
        }
        if(actorPresent == false) {returnString=null;}
        return returnString;
    }
    private Actor getActors()
    {
         Actor result = null;
         result = actors.get(player.getCurrentRoom());
         return result;
    }
    
    public Boolean metPlayer(Actor a)
    {
        Boolean met = false;
        if(player.getCurrentRoom() == a.getCurrentRoom()) {
            met = true;
        }
        return met;
    }
    
    private void takeObject(Command command)
    {
                if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Take what?");
            return;
        }
        if(getActors().getObject() == null) {
            return;
        }
        if(!command.getSecondWord().equalsIgnoreCase(getActors().getObject().getName())) {
            System.out.println("They don't have that item.");
            return;
        }

        Object objectToTake = getActors().getObject();
       
        if (getActors().getGivingItem()) {
            player.addObject(objectToTake);
            getActors().removeObject(objectToTake);
            printDescription();
        }
        else {
            System.out.println(getActors().getName() + " needs that at the moment");
        }
    }
}
