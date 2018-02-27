
/**
 * Class for Objects in Stratford.
 *
 * @author Alexander Davis
 * @version 8.12.2017
 */
public class Object
{
    private String name;
    private Boolean liftable; //Determines whether player can pickup object.
    private String pickupString; // String displayed if player cannot pickup object.
    private int weight;
    private Boolean storylineObject;
    

    /**
     * Constructor for objects of class Objects.
     * @param name and liftable boolean.
     */
    public Object(String name)
    {
        // initialise instance variables
        this.name = name;
        this.liftable = false;
        this.pickupString = "";
        this.weight = 2;
        this.storylineObject = false;
    }
    
    public void setLiftable()
    {
        liftable = true;
    }

    /**
     * Set string if player cannot pickup object.
     *
     * @param  String displayed if item is not pickupable.
     */
    public void setPickupString(String pickup)  {
        pickupString = pickup;
    }
    
    /**
     * Gives name of object defined in constructor.
     * @return name of object.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Returns whether the player can pickup object.
     * 
     * @return true if object is pickupable.
     */
    public Boolean getPickupable()
    {
       return liftable; 
    }
    
    /**
     * Get string for when  object is not pickupable.
     * @return string when object cannot be picked up.
     */
    public String getPickupString() {
        return pickupString;
    }
    
    /**
     * Change weight of an object from default 2.
     * @param integer with new weight.
     */
    public void setWeight(int newWeight) {
        weight = newWeight;
    }
    
    /**
     * Get the weight of an object.
     * @return integer with weight of item.
     */
    public int getWeight()
    {
        return weight;
    }
    
    /**
     * Set an object to change the greeting state of an actor when taken by player.
     */
    public void setStorylineObject()
    {
        storylineObject = true;
    }
    
    /**
     * @return true if object changes greeting state of actor when taken.
     */
    public Boolean getStoryline()
    {
        return storylineObject;
    }
}
