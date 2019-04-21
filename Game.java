import java.util.*;
import java.util.Random; 
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes + Zachary Johnson
 * @version 4/2019
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private ArrayList<Item> carriedItems;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        carriedItems = new ArrayList<Item>();
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        //Room outside, theater, pub, lab, office;
        Room sidewalk, sidepath,sidepath2, entrance, inside, frontDesk, eastWing, westWing, eastWingCont, westWingCont, basement, xroom1, xroom2, xroom3, dbasement;

        sidewalk = new Room("You are standing on the sidewalk. In front of you is a large building with a sign next to it, that says ''Taylor Hebert Historical Museum''. You see a possible side path going along the east and west sides of the building.");
        sidepath = new Room("The tile here is cracked and broken. Vines and grass around it are growing freely. To the left of the path, are old rusty metal doors. They appear to go into some sort of basement.");
        sidepath2 = new Room("As you walk this side path, you notice that the grass is far to unweildy to continue walking forward.");
        entrance = new Room("");
        inside = new Room("As you open the doors and enter the Museum, you see a front desk and two hallways a bit further ahead. \n There are a few paintings and pictures of a tall, stick-thin,young woman with long, curly black hair and glasses.");
        frontDesk = new Room("In front of you is a desk with a bell on it, along with a piece of paper that says ''Ring for Service''. \n After ringing the bell and waiting a little while, a blonde haired woman walks up behin the desk. \nThe woman introduces herself as Sarah, and gives you the usual tour guide introduction to the museum. \nHer first suggestion for you, is to look around the east wing of the museum.");
        eastWing = new Room("The entrance to the East wing. There is a hallway in front of you and the front desk is behind you. Weirdly enough, there is what appears to be a key on a small table near the entrance of the hallway.");
        westWing = new Room("The entrance to the West wing. There is a hallway in front of you and the front desk is behind you.");
        eastWingCont = new Room("As you walk into the hallway, you notice the large displays immediately. One wall is titled ''Skitter''. \nThere are images of a woman in a black body suit. Next to it is a manikin with a tattered version of the body suit in the pictures. \n The other wall is titled ''Weaver'' with images of a woman in a white body suit. It is obvious that this is the same person as the other wall.");
        westWingCont = new Room("This hallway is covered in even more pictures of the woman in the body suit. Most of them are pictures of her surrounded by insects attacking villains or even heros. \nThough there are also, pictures of her attacking monstrosities or giving out food to people.");
        basement = new Room("You have entered the basement, through some large rusty doors. In this room is a empty doorframe to your left and a cabinet made of wood to your right.");
        xroom1 = new Room("Skitter's Room");
        xroom2 = new Room("Weaver's Room");
        xroom3 = new Room("Khepri's Room");
        dbasement = new Room("Here is the end game.");
        
        //All Items & Where they are & if they have a use
        Item key = new Item("key","This is a small key, it seems incomplete.", 1);
        Item key_part1 = new Item("part1","The first part of the whole key.", 1);
        Item key_part2 = new Item("part2","The second part of the whole key.", 1);
        Item key_part3 = new Item("part3","The third part of the whole key. You might be able to open multiple doors with this.", 1);
        eastWing.addItem(key);
        xroom1.addItem(key_part1);
        xroom2.addItem(key_part2);
        xroom3.addItem(key_part3);
        
        
        //All Locked Rooms

        xroom1.setLocked(true);
        xroom2.setLocked(true);
        xroom3.setLocked(true);
        
        dbasement.setLocked(true);
        
        
        //All Room Exits
        sidewalk.setExit("north", entrance);
        sidewalk.setExit("west", sidepath);
        sidewalk.setExit("east", sidepath2);
        
        sidepath.setExit("west", basement);
        sidepath.setExit("east", sidewalk);
        
        sidepath2.setExit("west", sidewalk);
        
        entrance.setExit("north", inside);
        entrance.setExit("south", sidewalk);
        
        inside.setExit("north", frontDesk);
        inside.setExit("south", entrance);
        
        frontDesk.setExit("south", inside);
        frontDesk.setExit("east", eastWing);
        frontDesk.setExit("west", westWing);
        
        eastWing.setExit("north",eastWingCont);
        eastWing.setExit("west", frontDesk);
        
        westWing.setExit("north",westWingCont);
        westWing.setExit("east",frontDesk);
        
        eastWingCont.setExit("south",eastWing);
        westWingCont.setExit("south",westWing);
        
        westWingCont.setExit("west",sidepath);
        
        basement.setExit("south", xroom1);
        basement.setExit("east", sidepath);
        basement.setExit("north", dbasement);
        
        xroom1.setExit("west", xroom2);
        xroom1.setExit("north", basement);
        
        xroom2.setExit("north", xroom3);
        xroom2.setExit("south", xroom1);
        
        xroom3.setExit("south", xroom2);
        

        //All items you start the game with
        carriedItems.add(new Item("Coins", "This is a pouch of coins. Maybe they could be traded for something?", 2));
        
        currentRoom = sidewalk;  // start game in the void
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println("~~----------------------------------------------------------------~~");
        System.out.println("~|~~~~~~~~~~~~~~~~~~~~~~~WORM - A Text Adventure~~~~~~~~~~~~~~~~~~|~");
        System.out.println("~|~~~~~~~~~~~~~~~~~~~~~~~Made by Zachary Johnson~~~~~~~~~~~~~~~~~~|~");
        System.out.println("~|~~~~~~~~~~~~~~~~~~~Based on the novel by John C. McCrea~~~~~~~~~|~");
        System.out.println("~-----------------------------------------------------------------~~");
        System.out.println("");
        System.out.println("''Say '" + CommandWord.HELP + "' if you need help.''");
        System.out.println();
        System.out.println("You feel your consciousness return to you. As you open your eyes, you realize that you are laying on the ground. \nYou manage to clamber back onto your feet, and you realize something is in your pocket. \nYou start looking around your surroundings. ");
        System.out.println("" + currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;
            
            case LOOK:
                look();
                break;
                
            case INV:
               inventory();
                break;
                
            case USE:
                use(command);
                break;
                
            case BACK:
                back(command);
                break;
                
            case TAKE:
                take(command);
                break;
                
            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }
    
    /**
     * This is what happens when the user calls the use command
     * @param command The command to be processed.
     */
    private void use(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Use what?");
            return;
        }
        
        //Random rand = new Random();
        String item = command.getSecondWord();
        String direction;
        //int rand_int1 = rand.nextInt(2); 
        
        Room nextRoom;
        switch(item){
        case "key":
        direction = "south";
        nextRoom = currentRoom.getExit(direction);
        if (nextRoom.getLocked()){
                System.out.println("You hear a sound as you unlock the 'door'. A portal appears in the empty doorframe.");
                nextRoom.setLocked(false);
        }
        break;
        
        case "part1":
        direction = "west";
        nextRoom = currentRoom.getExit(direction);
        if (nextRoom.getLocked()){
                System.out.println("You hear a sound as you unlock another door, along with another portal.");
                nextRoom.setLocked(false);
        }
        break;
        
        case "part2":
        direction = "north";
        nextRoom = currentRoom.getExit(direction);
        if (nextRoom.getLocked()){
                System.out.println("You put the key into the wooden door and turn it. The old door slowly opens by itself in front of you.");
                nextRoom.setLocked(false);
            }
        break;
        
        case "part3":
            System.out.println("You put the completed key into the cabinet. The cabinet clicks as you open it. \nOn the inside is a picture of the same young woman, you saw in the museum's pictures. \nThat's what you think they are, but on closer inspection, it is a mirror.");
            System.out.println("Thank you for playing my game.");
        break;
        
        }
    }
    
    /**
     * Code for the Look Command
     */
    private void look(){
        System.out.println(currentRoom.getLongDescription()); 
    }
    
    // implementations of user commands
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are in the Taylor Hebert Museum");
        System.out.println("This museum is a omage to the online novel, Worm.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null || nextRoom.getLocked() )
            System.out.println("There is no door or that door is locked.");
        else {
            previousRoom = currentRoom; //store previous room (in order to enable back command)
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /**
     * This method:
     * -Checks if there is anything in the inventory (If there isn't anything,it will tell the user)
     * -Outputs the list of all items (Their names, their description & their weight.)
     * -Outputs the total weight of all of the items in the inventory.
     */
    private void inventory(){
    if (carriedItems.size() == 0) {
                 System.out.println("you are not carrying anything");
            } else {
            System.out.print("You have the following:");
            
            for (int n = 0; n < carriedItems.size(); n++) {
                Item item = (Item) carriedItems.get(n);
                System.out.println(" " + item.getName());
                System.out.println("" + item.getDescription());
            }
       System.out.println();
       System.out.println("Total weight of all carried Items: " + totalWeight(carriedItems));
                    }
    
    }
    
    /**
     * Calculate the total Weight of all items in a list
     * @param L this takes the list of all carried items.
     */
    private int totalWeight(ArrayList L) {
        int n=0;
        int sum = 0;
        while (n < L.size()) {
            Item i = (Item) L.get(n);
            sum += i.getWeight();
            n++;
        }
        return sum;    // not found above
    }
     
    /**
    * Go back to the previous room.
    * @param command The command to be processed.
    */
         private void back(Command command)
     {
         if(previousRoom == null || previousRoom.getLocked()) {
            System.out.println("Sorry, cannot go back.");
            return;
        }
        if(command.hasSecondWord()) {
            // if there is a second word, we cannot go back...
            System.out.println("I don't know what you mean...");
            return;
        }
           currentRoom = previousRoom; //change location to the previous room
        //print method where you are
        System.out.println(currentRoom.getLongDescription());
    } 
    
    /**
    * Finds Items by name in a list
    * @param s this takes the name of an item from the method that calls it
    * @param L this takes the ArrayList from the method that calls it.
    */
    private Item findByName(String s, ArrayList L) {
        int n=0;
        while (n < L.size()) {
            Item i = (Item) L.get(n);
            if (s.equals(i.getName()))
                return i;
            n++;
        }
        return null;
    }
    
    /**
    * Checks if there is an item in the room when, then adds it to the player's inventory
    * @param command The command to be processed.
    */
    public void take(Command command) {
        if (! command.hasSecondWord()) {  // "TAKE",but no object named
            System.out.println("Take what?");
            return;
        }
        String s = command.getSecondWord();
        Item i = findByName(s, currentRoom.getItems());
        if (i == null) {
            System.out.println("There is no " + s + " in this room.");
            return;
        }
        currentRoom.getItems().remove(i);
        carriedItems.add(i);
               System.out.println("You have taken the " + s);
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    
    public static void main(String[] args){
        new Game();
    
    }
    
}
