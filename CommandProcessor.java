import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Random;

/**
 * Command processor evaluates and executes the commands that the parser returns.
 *
 * @Alexander Davis
 * @8.12.2017
 */
public class CommandProcessor
{
    private Parser parser;
    /**
     * Constructor for CommandProcessor
     */
    public CommandProcessor()
    {
        parser = new Parser();
    }
    
    /**
     * Get command from parser and process command.
     */
    public Boolean start()
    {
        Command command = parser.getCommand();
        Boolean finished = processCommand(command);
        return finished;
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
            printHelp(command);
        }
        else if (commandWord.equals("back")) {
            goBack(command);
        }
        else if (commandWord.equals("go")) {
            wantToQuit = goRoom(command);
        }
        else if(commandWord.equals("pickup")) {
            pickupObject(command);
        }
        else if(commandWord.equals("drop")) {
            dropObject(command);
        }
        else if(commandWord.equals("take")) {
            takeObject(command);
        }
        else if(commandWord.equals("give")) {
            giveObject(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        // else command not recognised.
        return wantToQuit;
    }
    
    /**
     * Print out some help information.
     * Here we print a cryptic message and a list of the 
     * command words with their usage case.
     * 
     * If there is a second command word print help
     * for that particular command.
     * 
     * After friend has moved one room, we give the Player 
     * the last room friend was seen in when they type
     * help.
     */
    private void printHelp(Command command) 
    {
        if(command.hasThirdWord()) {
            System.out.println("Please refer to only one command.");
            
        }
        if(!command.hasSecondWord())
        {
            System.out.println("You're late. You are panicking.");
            System.out.println("Where's that Oyster card?");
            System.out.println("Maybe it's with your friend");
            System.out.println();
            System.out.println("Your command words are:");
            parser.showCommands();
            System.out.println();
            if(!(Actor.getActor("friend").getLastRoom() == null)) {
                System.out.println("You recieved a text from your friend");
                System.out.println("they were last seen ");
                System.out.println(Actor.getActor("friend").getLastRoom().getShortDescription());
            }
        }
        else {
                parser.printCommandHelp(command.getSecondWord());
        }
    
    }
    
    /**
     * Sends the Player back to their previous room if they have one.
     */
    private void goBack(Command command) {
        if(command.hasSecondWord() || command.hasThirdWord())
        {
            System.out.println("Back where?");
            System.out.println("Try 'help back'");
            return;
        }
        
        if (Player.getLastRoom() == null) {
            System.out.println("No last room.");
        }
        else {
            Player.setCurrentRoom(Player.getLastRoom());
            Actor.moveActors();
            printDescription();
        
        }
    }

    /** 
     * Try to go in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * 
     * if that room is the magic room then go to random room.
     * 
     * if that room is stratford station check for oyster and sandwich (required objects to win).
     */
    private Boolean goRoom(Command command) 
    {
        Boolean hasWon = false;
        //Check format is correct with a single second word.
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            System.out.println("Try 'help go'");
            return hasWon;
        }
        if(command.hasThirdWord()) {
            System.out.println("Please use one room name");
            System.out.println("Try 'help go'");
            return hasWon;
        }
        
        String direction = command.getSecondWord();
        //Check direction is back.
        if(direction.equalsIgnoreCase("back")) {
            Command backCommand = new Command("back", null, null);
            goBack(backCommand);
            return hasWon;
        }
        

        // Try to leave current room.
        Room nextRoom = Player.getCurrentRoom().getExit(direction);

        
        if (nextRoom == null) {
            System.out.println("There is no door!");
            System.out.println("Try 'help go'");
        }
        else if(nextRoom.getMagicRoom()) {
            goRandomRoom();
            return hasWon;
        }
        else {
            Player.setCurrentRoom(nextRoom);
            Actor.moveActors();
            printDescription();
            hasWon = tryHasWon(nextRoom);
    }
    return hasWon;
    }
    
    /**
     * Send the Player to a random room. Magic room method.
     */
    private void goRandomRoom()
    {
        ArrayList<Room> rooms = Room.getRooms();
        Random rand = new Random();
        int  n = rand.nextInt(rooms.size());
        Room stratfordStation = rooms.get(5);
        Room stratfordInternational = rooms.get(10);
        if(rooms.get(n)==stratfordStation || rooms.get(n)==stratfordInternational) {
            rooms.remove(n);
            n = rand.nextInt(rooms.size()); 
        }
        Player.setCurrentRoom(rooms.get(n));
        Actor.moveActors();
        System.out.println("The magical powers of Stratford International,");
        System.out.println("which is only a domestic station,");
        System.out.println("have magically transported you.");
        System.out.println();
        printDescription();
    }
    
    /**
     * Check if the Player has won if they go to stratford station.
     */
    private Boolean tryHasWon(Room nextRoom)
    
    {
        Boolean hasWon = false;
        ArrayList<Room> rooms = Room.getRooms();
        Room stratfordStation = rooms.get(5);
        if(nextRoom == stratfordStation) {

                if(Player.getObject("oyster") == null) {
                    //No oyster card or food.
                    System.out.println("You need an oyster card to use the Underground!");
                }
                else if ((Player.getObject("oyster") != null) && (Player.getObject("sandwich") == null )) {
                    //Oyster card but no sandwich.
                    System.out.println("You've got an Oyster but");
                    System.out.println("You'll need some food to get through your lecture");
                }
                else if ((Player.getObject("oyster") != null) && (Player.getObject("sandwich") != null)) {
                    //Carrying both oyster and sandwich.
                    System.out.println();
                    System.out.println("You scan your oyster, it works!");
                    System.out.println("Your sandwich is delicous!");
                    System.out.println();
                    System.out.println("Congratulations! You made it to your lecture!");
                    System.out.println();
                    hasWon = true;
                }
        }
        return hasWon;
    }
    
    /**
     * Try to pickup object from current room.
     */
    private void pickupObject(Command command) 
    {
        //check only a single second word.
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Pickup what?");
            System.out.println("Try 'help pickup'");
            return;
        }
        if(command.hasThirdWord()) {
            System.out.println("You can only pickup one item at a time");
            System.out.println("Try 'help pickup'");
            return;
        }
        //Try to get object from current room.
        Object objectToPickup = Player.getCurrentRoom().getObject(command.getSecondWord());
        
        if (objectToPickup == null) {
            //second word is not an object in room.
            System.out.println("That object is not here!");
            System.out.println("Try 'help pickup'");
        }
        else {
        
        if (objectToPickup.getPickupable()) {
            //if object is pickupable
            if(Player.getBackpackFull(objectToPickup) == true) {
                //backpack is full
                System.out.println("You can't carry any more items.");
                System.out.println("Try dropping an item.");
            }
            else if(Player.getBackpackFull(objectToPickup) == false) {
                //backpack is not full and object is picked up.
                Player.addObject(objectToPickup);
                Player.getCurrentRoom().removeObject(objectToPickup);
                printDescription();
            }
        }
        else {
            //object is not pickupable.  
            System.out.println(objectToPickup.getPickupString());
        }
    }
    }
    
    /**
     * try to take object from actor in current room. Command in form take <object> <actor>.
     */
    private void takeObject(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take or who from.
            System.out.println("Take what, from who?");
            System.out.println("Try 'help take'");
            return;
        }
        else if(command.hasSecondWord() && !command.hasThirdWord()) {
            // if there is a second word but no third word we don't know who to take from. In case there are multiple actors.
            System.out.println("Take from who?");
            System.out.println("Try 'help take'");
            return;
        }
        if(Actor.getActorCurrentRoom(command.getThirdWord()) == null) {
            //There is no actor present.
            System.out.println("They are not there");
            return;
        }
        
        //Try to get object from actor in current room.
        Object objectToTake = Actor.getActorCurrentRoom(command.getThirdWord()).getObject(command.getSecondWord());
        //Try to get actor from current room.
        Actor actorToTake = Actor.getActorCurrentRoom(command.getThirdWord());
        
        if (objectToTake == null) {
            //Actor does not have item requested.
            System.out.println("There is no one with that item here!");
            System.out.println("Try 'help take'");
        }
        else {
            if(actorToTake == null || !actorToTake.getName().equalsIgnoreCase(command.getThirdWord())) {
                //If actor is not here or actor present does not have the name provided.
                System.out.println("That person isn't there");
                System.out.println("Try 'help take'");
            }
            else if (Player.getBackpackFull(objectToTake)) {
                System.out.println("You can't carry any more items");
                System.out.println("Try to drop some");
            }
            else { 
                //take item from actor. Also set actor to say second greeting.
                Player.addObject(objectToTake);
                actorToTake.removeObject(objectToTake);
                if(actorToTake.getStoryline() && objectToTake.getStoryline()) {actorToTake.setSecondGreeting();}
                printDescription();
            }
        }

    }
    
    /**
     * Try to give object to actor in current room. Command in form give <object> <actor>.
     */
    private void giveObject(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Give what, to who?");
            System.out.println("Try 'help give'");
            return;
        }
        else if(command.hasSecondWord() && !command.hasThirdWord()) {
            //if there is no third word we do not know who to give to.
            System.out.println("Give to who?");
            System.out.println("Try 'help give'");
            return;
        }
  
        //Try to get object from Player.
        Object objectToGive = Player.getObject(command.getSecondWord());
        //Try to get actor from room.
        Actor actorToGive = Actor.getActorCurrentRoom(command.getThirdWord());
        if(actorToGive == null) {
            //There is no actor in current room.
            System.out.println("They are not there");
            return;
        }
        
        
        if (objectToGive == null) {
            //Player does not have requested item.
            System.out.println("You don't have that item!");
            System.out.println("Try 'help give'");
        }
        else if(actorToGive.getBackpackFull(objectToGive)) {
            //Actor's backpack is full.
            System.out.println("They can't carry any more items");
            return;
        }
        else {
            //Player has item
            if(actorToGive == null || !actorToGive.getName().equalsIgnoreCase(command.getThirdWord())) {
                //Actor to give does not exist or does not equal the third word provided.
                System.out.println("That person isn't there");
                System.out.println("Try 'help give'");
            }
            else {
                //take object from Player, give to actor.
                Player.removeObject(objectToGive);
                actorToGive.addObject(objectToGive);
                printDescription();
            }
        }

    }
    
    /**
     * Drop item Player is carrying.
     */
    private void dropObject(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop
            System.out.println("Drop what?");
            System.out.println("Try 'help drop'");
            return;
        }
        
        //Try to get object from Player with command provided.
        Object objectToDrop = Player.getObject(command.getSecondWord());
        
        if (objectToDrop == null) {
            //Player is not carrying object.
            System.out.println("You don't have that object!");
            System.out.println("Try 'help drop'");
        }
        else {
            //object is removed from Player, given to room.
            Player.removeObject(objectToDrop);
            Player.getCurrentRoom().addExistingObject(objectToDrop);
            printDescription();
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
            System.out.println("Try 'help quit'");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * Print a description of current room.
     * If Player is carrying objects, print list.
     * If there are any actors nearby print a list, their items and their greetings.
     */
    public void printDescription()
    {
        //Print room description with item list.
        System.out.println(Player.getCurrentRoom().getLongDescription());
        //Print objects carried by Player.
        if(Player.getObjectCarryString("") != null) {System.out.println(Player.getObjectCarryString("Currently carrying:"));}
        //Print list of actors in current room and objects + greetings.
        Actor.printActorDescriptions();
    } 
}
