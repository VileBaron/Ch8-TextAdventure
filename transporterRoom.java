import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Write a description of class transporterRoom here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class transporterRoom extends Room
{
    // instance variables - replace the example below with your own
    private int x;
    private HashMap<String, Room> exits;

    /**
     * Constructor for objects of class transporterRoom
     */
    public transporterRoom(String description)
    {
        // initialise instance variables
        super(description);
        exits = new HashMap<String, Room>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setTransport(String direction, Room neighbor) 
    {
        exits.put("north", neighbor);
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
    
    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        String s =  "" + description + ".\n" + getExitString();
       return s;
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
}
