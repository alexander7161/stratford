import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Object> objectList;
    
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
    
    public void addObject(int objecti) 
    {
        switch (objecti) {
            case 1:     Object bed = new Object("Bed", false, "Where you sleep", "The Bed is too heavy for me to lift");
                        objectList.add(bed);
                        break;
            case 2:     Object costume = new Object("Costume", false, "Must be from a play", "You're already dressed");
                        objectList.add(costume);
                        break;
            case 3:     Object sandwich = new Object("Sandwich", true, "This will get you through the lecture", "");
                        objectList.add(sandwich);
                        break;          
            case 5:     Object salad = new Object("Salad", false, "Expensive food you can't afford", "You can't afford that, try lidl");
                        objectList.add(salad);
                        break;
        }
        
        
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
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString() + "\n" + getObjectString();
    }
    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
    
    private String getObjectString()
    {
        String returnString = "Objects nearby:";
        for(Object object : objectList) {
            returnString += " " + object.getName();
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
    
    public Object getObject(String objString) {
        Object obj = null;
        for (Object object : objectList) {
                if(object.getName().equalsIgnoreCase(objString)) {
                    obj = object;
                }

        }
        return obj;

    }
    
    public void removeObject(Object obj) {
        objectList.remove(obj);
    }
}

