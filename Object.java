
/**
 * Write a description of class Objects here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Object
{
    // instance variables - replace the example below with your own
    private String name;
    private Boolean liftable;
    private String description;
    private String pickupString;

    /**
     * Constructor for objects of class Objects
     */
    public Object(String name, Boolean liftable, String description, String pickupString)
    {
        // initialise instance variables
        this.name = name;
        this.liftable = liftable;
        this.description = description;
        this.pickupString = pickupString;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public String getName()
    {
        // put your code here
        return name;
    }
    public Boolean getPickupable()
    {
       return liftable; 
    }
    public String getPickupString() {
        return pickupString;
    }
}
