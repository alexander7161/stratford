import java.util.ArrayList;
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

    /**
     * Constructor for objects of class Actor
     */
    public Actor()
    {
        // initialise instance variables
        
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
    }
        private String getObjectCarryString()
    {
        String returnString = "Objects carrying:";
        for(Object object : objectList) {
            returnString += " " + object.getName();
        }
        return returnString;
    }
}
