import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
import java.util.Set;
/**
 * Class which represents actors in the game including the player.
 *
 * @author Alexander Davis
 * @version 8.12.2017
 */
public class Actor
{
    private ArrayList<Object> objectList; //Objects currently carryed by actor.
    private Room currentRoom; // Current room of actor.
    private String name;
    private ArrayList<String> greetings; //First arraylist of greetings.
    private ArrayList<String> greetings2; // Second arraylist of greetings.
    private int backpackMaxWeight; // Maximum weight carryable.
    private int backpackWeight; // current weight in backpack.
    private Room lastRoom; // last room actor visited.
    private Boolean storylineActor;
    private Boolean firstGreeting; //Whether to give first greetings or second greetings.
    private ArrayList<Room> noEntryRooms; // Rooms an actor is not allowed to visit.
    private Boolean moves; //defines whether actor moves when moveRooms is called in Game class.
    private static ObjectList objectsList = new ObjectList();
    private static HashMap<Actor, Room> actors = new HashMap<>(); //Stores actors and rooms they are in.
    
    /**
     * Creates actor with given name, starting room and ID of greetings to set.
     * @param name, current room and greetings ID.
     */
    public Actor(String name, Room currentRoom, int greetingID)
    {
        // initialise instance variables
        this.name = name;
        this.currentRoom = currentRoom;
        objectList = new ArrayList<>();
        greetings = new ArrayList<>();
        greetings2 = new ArrayList<>();
        noEntryRooms = new ArrayList<>();
        this.backpackMaxWeight = 4; //Default max backpack weight is 4.
        this.lastRoom = null;
        this.firstGreeting = true;
        this.moves = true;
        this.storylineActor = false;
        actors.put(this, currentRoom);

        this.setGreeting(greetingID);
    }
    
    /**
     * @return set of all actors in game.
     */
    public static Set getActorsSet()
    {
        Set<Actor> actorsSet = actors.keySet();
        return actorsSet;
    } 
    
    /**
     * Moves all actors in game.
     */
    public static void moveActors()
    {
        Set<Actor> actorsSet = getActorsSet();
        for (Actor a : actorsSet) {
            //a represents an actor.
            a.moveRoom();
            //Update HashMap with current location of actor.
            
            actors.replace(a, a.getCurrentRoom());
        }
    }
    
    /**
     * Checks current room for an actor.
     * @param string of actor name.
     * @return Actor in current room. Null if actor is not present.
     */
    public static Actor getActorCurrentRoom(String inputName)
    {
        Actor result = null;
        Set<Actor> actors = Actor.getActorsSet();
        for (Actor a : actors) {
            if(a.getCurrentRoom() == Player.getCurrentRoom() && a.getName().equalsIgnoreCase(inputName)) {
                result = a;
            }
        }
        return result;
    }
    
    /**
     * Get actor in game from string of name
     * @param string with name of actor.
     * @return Actor of given name.
     */
    public static Actor getActor(String inputName)
    {
        Actor result = null;
        Set<Actor> actors = Actor.getActorsSet();
        for (Actor a : actors) {
            if(a.getName().equalsIgnoreCase(inputName)) {
                result = a;
            }
        }
        return result;
    }
    
    /**
     * Print Description of actors in room if there are actors.
     */
    public static void printActorDescriptions()
    {
        if(Actor.getAllCurrentActorsString() != null) {System.out.println("People nearby: " + getAllCurrentActorsString());}
        //If the actors have objects or greetings print them.
        if(!Actor.getAllCurrentActors().isEmpty()) { 
            //If there are actors in current room.
            Actor.printAllCurrentActorsObjectStrings();
            Actor.printAllCurrentActorsGreetings();
        }
        }
    
    /**
     * Prints object string for each actor in current room.
     */
    private static void printAllCurrentActorsObjectStrings()
    {
        ArrayList<Actor> currentActors = getAllCurrentActors();
        Boolean objects = false; // True if there are actors in current room with objects.
        for (Actor a : currentActors) {
            //For all actors in current room if they are carrying an object.
            if(a.getObjectCarryString("") != null) {
                //print object carry string.
                System.out.println(a.getObjectCarryString(a.getName() + " is carrying:"));
                objects = true;
            }
        }
        //If there are actors with objects add a line between objects and greetings.
        if(objects) { System.out.println("");}
        
    }
    
    /**
     * @return String with all current actors in current room. If no actors present return null.
     */
    private static String getAllCurrentActorsString()
    {
        String returnString = "";
        ArrayList<Actor> currentActors = getAllCurrentActors();
        //If there are no actors return null
        if(currentActors.size() == 0) { return null;}
        int length = currentActors.size() - 1; //length represents the index of actors in the arraylist.
        for(int i=0;i<length;i++) {
            //For all less than total add a comma.
            returnString = returnString + currentActors.get(i).getName() + ", ";
        }
        //Last or only actor has a full stop.
        returnString = returnString + currentActors.get(length).getName() + ".";
        return returnString;
    }
    
    /**
     * Prints greetings for all actors in current room.
     */
    private static void printAllCurrentActorsGreetings()
    {
        ArrayList<Actor> currentActors = getAllCurrentActors();
        for (Actor a : currentActors) {
            System.out.println(a.getGreeting());
        }
    }
    
    /**
     * @return all actors in current room as an arraylist.
     */
    private static ArrayList getAllCurrentActors()
    {
        ArrayList<Actor> currentActors = new ArrayList<>();
        //Get set of all actors in the game.
        Set<Actor> actors = Actor.getActorsSet();
        
        for (Actor a : actors) {
            //for each actor in the game if their current room equals the Player's current room
            //then add them to actors in current room.
            if(a.getCurrentRoom() == Player.getCurrentRoom()) {
                currentActors.add(a);
            }
        }
        return currentActors;
    }
    
    
    /**
     * Sets greetings for actor from pre-defined list.
     * @param ID of actor.
     */
    private void setGreeting(int caseNumber) {
        switch (caseNumber) {
            //player
            case 1:     
                        break;
            //Friend
            case 2:     greetings.add("Hey! I have an oyster if you need it.");
                        greetings.add("You need an oyster? Take mine.");
                        greetings2.add("Hey, good to see you.");
                        storylineActor = true;
                        break;   
            //shopclerk  
            case 3:     moves = false;
                        greetings.add("Have a good day!");
        }
    }
    
    /**
     * Set objects carried by actor.
     * 
     * @param ID of object.
     */
    public void setObject(String objectString)
    {
        objectList.add(objectsList.createObject(objectString));
        for (Object obj : objectList) {
        backpackWeight = backpackWeight + obj.getWeight();
    }
        
    }
    
    /**
     * Set no entry rooms for actors.
     * @param room that is of no entry.
     */
    public void setNoEntryExits(Room noEntryRoom)
    {
        noEntryRooms.add(noEntryRoom);
    }

    /**
     * Add object to actor.
     *
     * @param  Pre-existing object to add to actor.
     */
    public void addObject(Object obj)
    {
        objectList.add(obj);
        backpackWeight = backpackWeight + obj.getWeight();
    }
    
    /**
     * 
     * @param object to remove from actor.
     */
    public void removeObject(Object obj)
    {
        objectList.remove(obj);
        backpackWeight = backpackWeight - obj.getWeight();
    }
    
    /**
     * 
     * @return true if backpack is full.
     */
    public Boolean getBackpackFull(Object newObject)
    {
        Boolean isFull = null;
        int newWeight = newObject.getWeight() + backpackWeight;
        if(newWeight > backpackMaxWeight) {
            isFull = true;
        }
        else if (newWeight <= backpackMaxWeight)  {
            isFull = false;
        }
        return isFull;
    }
    
    /**
     * Get object player is carrying.
     * @param string of name of object.
     * @return object, null if actor does not have object.
     */
    public Object getObject(String obj)
    {
        Object result = null;
        for(Object object : objectList) {
            if(object.getName().equalsIgnoreCase(obj)) {
                result = object;
            }
        }
        return result;
    }
    
    /**
     * Get list of objects carried by actor.
     * 
     * @param String of prefix to list. Allows to list for different actors. Null if no objects carried.
     * @return String of objects.
     */
    public String getObjectCarryString(String title)
    {
        String returnString = null;
        
        if(!(objectList.size()<1)) {
            returnString = title;
            int length = objectList.size() - 1;
            for(int i=0;i<(length);i++) {
                returnString += " " + objectList.get(i).getName() + ",";
            }
            returnString += " " + objectList.get(length).getName() + ".";
        }
        return returnString;
    }
    
    /**
     * @return current room of actor.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    /**
     * change current room of actor.
     * @param new room to move actor to.
     */
    public void setCurrentRoom(Room newRoom)
    {
        lastRoom = currentRoom;
        currentRoom = newRoom;
    }
    
    /**
     * @return String with name of actor set in constructor.
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return last room actor was in.
     */
    public Room getLastRoom()
    {
        return lastRoom;
    }

    /**
     * get Greeting for actor. Determine whether first or second greeting should be given.
     * @return string with greeting.
     */
    public String getGreeting()
    {
        String greeting = getName() +": ";
        if(firstGreeting) {
            Random rand = new Random();

            int  n = rand.nextInt(greetings.size());
            greeting = greeting + greetings.get(n);
        }
        else {
            Random rand = new Random();
            
            int  n = rand.nextInt(greetings2.size());
            greeting = greeting + greetings2.get(n);
        
        }
        return greeting;
        
    }
    
    /**
     * Set actor to return second greeting.
     */
    public void setSecondGreeting() {
        firstGreeting = false;
    }
    
    /**
     * Move actor to another room given exits of current room.
     * If new room is in arraylist noEntryRooms then remove that exit from arraylist and choose another exit.
     */
    private void moveRoom()
    {
        //Check if actor should move.
        if(moves==false) { return;}
        //List of exits available.
        ArrayList<String> exits = getCurrentRoom().getExits();
        //Get random number from number of exits.
        Random rand = new Random();
        int  n = rand.nextInt(exits.size());
        //Get that room.
        Room newRoom = getCurrentRoom().getExit(exits.get(n));
        if(noEntryRooms.contains(newRoom)) {
            //If room is in noEntryRooms remove that exit
            exits.remove(n);
            //Get another random number from new size of exits list.
            n = rand.nextInt(exits.size());
            //Get another room
            newRoom = getCurrentRoom().getExit(exits.get(n));
        }
        //Set current room to new room.
        setCurrentRoom(newRoom);
    
        
    }
    
    /**
     * @return true if actor is relevant to storyline and should switch to second greeting when a storyline object is taken.
     */
    public Boolean getStoryline()
    {
        return storylineActor;
    }
}
