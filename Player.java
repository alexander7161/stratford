import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
import java.util.Set;
/**
 * Class which represents players in the game including the player.
 *
 * @author Alexander Davis
 * @version 8.12.2017
 */
public class Player
{
    private static ArrayList<Object> objectList; //Objects currently carryed by player.
    private static ArrayList<Room> rooms =  Room.getRooms();
    private static Room currentRoom = rooms.get(0); // Current room of player.
    private static int backpackMaxWeight; // Maximum weight carryable.
    private static int backpackWeight; // current weight in backpack.
    private static Room lastRoom; // last room player visited.
    
    /**
     * Creates player with given name, starting room and ID of greetings to set.
     * @param name, current room and greetings ID.
     */
    public Player(Room currentRoom)
    {
        // initialise instance variables
        Player.currentRoom = currentRoom;
        objectList = new ArrayList<>();
        backpackMaxWeight = 4; //Default max backpack weight is 4.
        lastRoom = null;
    }
    
    /**
     * Add object to player.
     *
     * @param  Pre-existing object to add to player.
     */
    public static void addObject(Object obj)
    {
        objectList.add(obj);
        backpackWeight = backpackWeight + obj.getWeight();
    }
    
    /**
     * 
     * @param object to remove from player.
     */
    public static void removeObject(Object obj)
    {
        objectList.remove(obj);
        backpackWeight = backpackWeight - obj.getWeight();
    }
    
    /**
     * 
     * @return true if backpack is full.
     */
    public static Boolean getBackpackFull(Object newObject)
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
     * @return object, null if player does not have object.
     */
    public static Object getObject(String obj)
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
     * Get list of objects carried by player.
     * 
     * @param String of prefix to list. Allows to list for different players. Null if no objects carried.
     * @return String of objects.
     */
    public static String getObjectCarryString(String title)
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
     * @return current room of player.
     */
    public static Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    /**
     * change current room of player.
     * @param new room to move player to.
     */
    public static void setCurrentRoom(Room newRoom)
    {
        lastRoom = currentRoom;
        currentRoom = newRoom;
    }
    
    /**
     * 
     * @return last room player was in.
     */
    public static Room getLastRoom()
    {
        return lastRoom;
    }

}
