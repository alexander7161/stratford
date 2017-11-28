import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Write a description of class Actor here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Actor
{
    // instance variables - replace the example below with your own
    private ArrayList<Object> objectList;
    private Room currentRoom;
    private String name;
    private Boolean carrying;
    private String firstGreeting;
    private ArrayList<String> greetings;
    private Boolean met;

    /**
     * Constructor for objects of class Actor
     */
    public Actor(String name, Room currentRoom, int i)
    {
        // initialise instance variables
        this.name = name;
        this.currentRoom = currentRoom;
        this.carrying = false;
        objectList = new ArrayList<>();
        greetings = new ArrayList<>();
        met = false;
        
        firstGreeting = "";
        
        setGreeting(i);
    }
    
    private void setGreeting(int caseNumber) {
        switch (caseNumber) {
            case 1:     firstGreeting = "Hey! You need an Oyster to travel on the Underground!";
                        greetings.add("I thought I told you, you need an Oyster!");
                        greetings.add("I thought I told you, you need an Oyster!");
                        greetings.add("I thought I told you, you need an Oyster!");
                        greetings.add("I thought I told you, you need an Oyster!");
                        greetings.add("I thought I told you, you need an Oyster!");
                        break;
            case 2:     
                        break;
            case 3:     firstGreeting = "Hey! Have a good day today.";
                        greetings.add("You need an OysterCard? Take mine.");
                        greetings.add("You need an OysterCard? Take mine.");
                        greetings.add("You need an OysterCard? Take mine.");
                        greetings.add("You need an OysterCard? Take mine.");
                        greetings.add("You need an OysterCard? Take mine.");
                        carrying = true;
                        Object oyster = new Object("Oyster", true, "This will let you get to your lecture", "");
                        objectList.add(oyster);
                        break;
            case 4:     firstGreeting = "I just got my dream role in the new play.";
                        greetings.add("I thought I told you, you need an Oyster!");
                        greetings.add("You need an OysterCard? Take mine.");
                        greetings.add("You need an OysterCard? Take mine.");
                        greetings.add("You need an OysterCard? Take mine.");
                        greetings.add("You need an OysterCard? Take mine.");
                        break;            
        }
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void addObject(Object obj)
    {
        objectList.add(obj);
        carrying = true;
    }
    
    public String getObjectCarryString(String title)
    {
        String returnString = null;
        if(carrying) {
        returnString = title;
        for(Object object : objectList) {
            
            returnString += " " + object.getName();
        }
    }
    return returnString;
    }
    
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    public void setCurrentRoom(Room newRoom)
    {
        currentRoom = newRoom;
    }
    public void hasMet()
    {
        met = true;
    }
    public String getName() {
        return name;
    }
    public void greet() {
        if(!met) {
            System.out.println(getName() + ": " + firstGreeting);
        }
        else {
            int min = 0;
            int max = 5;
            int random = ThreadLocalRandom.current().nextInt(min, max + 1);
            System.out.println(getName() + ": " + greetings.get(random));
            if(carrying) {
                System.out.println(getObjectCarryString("Currently carrying:"));
            }
        }
    }
}
