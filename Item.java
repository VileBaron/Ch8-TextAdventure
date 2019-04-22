
/**
 * This class creates the items that are used in game.
 *
 * @Zachary_Johnson
 * @version 4/2019
 */
public class Item
{
    private String name;
    private String itemDescription;
    private int weight;

    /**
     * Constructor for objects of class Item
     * @param n takes the name that the method is given
     * @param d takes the description of the item that it is given
     * @param w takes the weight of the item that it is given
     */
    public Item(String n, String d, int w)
    {
        name = n;
        itemDescription = d;
        weight = w;
    }

     /**
     * @return Accessor for name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * @return Accessor for description
     */
    public String getDescription(){
        return itemDescription;
    }
    
    /**
     * @return Accessor for weight
     */
    public int getWeight()
    {
        return weight;
    }

}
