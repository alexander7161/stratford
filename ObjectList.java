
/**
 * Class which contains a method to 
 * create all kinds of objects in the game.
 *
 * @author Alexander Davis
 * @version 8.12.2017
 */
public class ObjectList
{

    public ObjectList()
    {
        
    }

    /**
     * Creates object from string of name of an object.
     */
    public Object createObject(String objectString)
    {
        if (objectString.equalsIgnoreCase("bed")) { 
            Object bed = new Object("Bed");
            bed.setPickupString("The Bed is too heavy for me to lift");
            return bed;
        }
        else if (objectString.equalsIgnoreCase("costume")) {
            Object costume = new Object("Costume");
            costume.setLiftable();
            return costume;
        }
        else if (objectString.equalsIgnoreCase("sandwich")) { 
            Object sandwich = new Object("Sandwich");
            sandwich.setLiftable();
            return sandwich;
        }
        else if (objectString.equalsIgnoreCase("salad")) { 
            Object salad = new Object("Salad");
            salad.setPickupString("You can't afford that, try lidl");
            return salad;
        }
        else if (objectString.equalsIgnoreCase("bench")) { 
            Object bench = new Object("Bench");
            bench.setPickupString("You can't lift that");
            return bench;
        }
        else if (objectString.equalsIgnoreCase("litter")) { 
            Object litter = new Object("Litter");
            litter.setLiftable();
            litter.setWeight(1);
            return litter;
        }
        else if (objectString.equalsIgnoreCase("champagne")) { 
            Object champagne = new Object("Champagne");
            champagne.setPickupString("Keep dreaming.");
            return champagne;
        }
        else if (objectString.equalsIgnoreCase("oyster")) { 
            Object oyster = new Object("Oyster");
            oyster.setLiftable();
            oyster.setStorylineObject();
            return oyster;
        }
        else { return null;}
        
    }
}
