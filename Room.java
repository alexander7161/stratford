import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class Room - an area in Stratford.
 * 
 * A room is connected to other rooms by exits
 * and can be traversed by the player
 * 
 * @author  Alexander Davis
 * @version 8.12.2017
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Object> objectList; //Stores objects in this room.
    private Boolean magicRoom; // Boolean if this is the magic room.
    private static ObjectList objectsList = new ObjectList();
    private static ArrayList<Room> rooms = new ArrayList<>();
        
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        objectList = new ArrayList<>();
        this.magicRoom = false; //By default rooms are not the magic room.
        rooms.add(this);
    }
    
    /**
     * @return arraylist of rooms in game.
     */
    public static ArrayList<Room> getRooms()
    {
       return rooms; 
    }
    
    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /**
     * Set this room as a magic room which transports player to a random room in the game.
     */
    public void setMagicRoom()
    {
        magicRoom = true;
    }
    
    /**
     * Checks if room is a magic transporter room.
     * @return true if room is a magic room.
     */
    public Boolean getMagicRoom()
    {
        return magicRoom;
    }
    
    /**
     * Add an object to the room from a pre=defined list.
     * This creates the object and adds it to an arraylist of that room.
     * @param String of object name.
     */
    public void addObject(String objectString) 
    {
        objectList.add(objectsList.createObject(objectString));
        
    }
    
    /**
     * Add an object to room that has already been created by actor for example.
     * @param Already existing object.
     */
    public void addExistingObject(Object obj)
    {
        objectList.add(obj);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north, west.
     *     Objects nearby: Knife.
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        if(getObjectString() != null) {
        return "You are " + description + ".\n" + getExitString() + "\n" + getObjectString();
    }
    else {
        return "You are " + description + ".\n" + getExitString();
    }
    }
    
    /**
     * Return a string describing the room's exits, for example
     * "Exits: north, west.".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        String[] keyArray = keys.toArray(new String[keys.size()]);
        int length = keyArray.length -1;
        for(int i=0;i<(length);i++) {
            returnString += " " + keyArray[i] + ",";
        }
        returnString += " " + keyArray[length] + ".";
        return returnString;
    }
    
    /**
     * Return array with exits of room.
     * @return arraylist with strings of exits.
     */
    public ArrayList getExits()
    {
        Set<String> keys = exits.keySet();
        ArrayList<String> exits = new ArrayList<String>();
        for (String str : keys)  {
            exits.add(str);
        }
            return exits;

    }
    
    /**
     * Return string with objects in the room.
     * "Objects nearby: costume"
     * @return details of objects in the room.
     */
    private String getObjectString()
    {
        String returnString = null;
        if(!(objectList.size() == 0)) {
        returnString = "Objects nearby:";
        int length = objectList.size() - 1;
        for(int i=0;i<(length);i++) {
            returnString += " " + objectList.get(i).getName() + ",";
        }
        returnString += " " + objectList.get(length).getName() + ".";
    }
        return returnString;
    }
    
   

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Returns an object if an object in the room matches a given string.
     * @param string of an objects name.
     * @return Object in the room that matches string.
     * 
     */
    public Object getObject(String objString) {
        Object obj = null;
        for (Object object : objectList) {
                if(object.getName().equalsIgnoreCase(objString)) {
                    obj = object;
                }
        }
        return obj;

    }
    
    /**
     * Removes Object from room.
     * @param Object to remove.
     * 
     */
    public void removeObject(Object obj) {
        objectList.remove(obj);
    }
}

