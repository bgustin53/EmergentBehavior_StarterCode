import greenfoot.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A moth in a predator/prey simulation. moths move around and breed.
 * 
 * @author M Kölling & Bruce Gustin
 * @version 3/28/2020
 */
public class Forest extends World
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int WIDTH = 80;
    // The default depth of the grid.
    private static final int HEIGHT = 60;
    // The probability that a bird will be created in any given grid position (in percent).
    private static final int BIRD_CREATION_PROBABILITY = 2;
    // The probability that a moth will be created in any given grid position (in percent).
    private static final int MOTH_CREATION_PROBABILITY = 4;
    // Number of trees in the forest
    private static final int NUM_OF_TREES = 7;
    // The current step of the simulation.
    private int step = 0;

    /**
     * Create the field and populate it with birds and moths.
     */
    public Forest()
    {    
        super(WIDTH, HEIGHT, 10);
        Greenfoot.setSpeed(30);
        trees();
        populate();
        addObject(new Plotter(300, 150, 500, this, Moth.class, Bird.class), 0, 0);
        setPaintOrder(Animal.class, Tree.class);
        prepare();
    }

    /**
     * Populate a field with birds and moths.
     */
    private void populate()
    {
        for(int row = 0; row < HEIGHT - 4; row++) {
            for(int col = 0; col < WIDTH; col++) {
                if(Greenfoot.getRandomNumber(100) <= BIRD_CREATION_PROBABILITY) {
                    Bird bird = new Bird(true);
                    addObject(bird, col, row);
                }
                else if(Greenfoot.getRandomNumber(100) <= MOTH_CREATION_PROBABILITY) {
                    Moth moth = new Moth();
                    addObject(moth, col, row);
                }
                // else leave the location empty.
            }
        }
    }

    /**
     * Generate random trees in the forest
     */
    private void trees()
    {
        for(int i = 0; i < NUM_OF_TREES; i++)
        {
            int x = Greenfoot.getRandomNumber(getWidth());
            int y = Greenfoot.getRandomNumber(getHeight() - 12);
            addObject(new Tree(), x, y);
        }
    }

    /**
     * Generate a random location that is adjacent to the given location, or is the 
     * same location. The returned location will be within the valid bounds of the field.
     */
    public Location randomAdjacentLocation(int x, int y)
    {
        // Generate an offset of -1, 0, or +1 for both the current x and y.
        int nextX = x + Greenfoot.getRandomNumber(3) - 1;
        int nextY = y + Greenfoot.getRandomNumber(3) - 1;
        // Check in case the new location is outside the bounds.
        if(nextX < 0 || nextX >= WIDTH || nextY < 0 || nextY >= HEIGHT) {
            return new Location(x, y);
        }
        else {
            return new Location(nextX, nextY);
        }
    }

    /**
     * Try to find a free location that is adjacent to the given location. If there is none,
     * then return the current location if it is free. If not, return null. The returned 
     * location will be within the valid bounds of the field.
     */
    public Location freeAdjacentLocation(int x, int y)
    {
        List<Location> locations = new LinkedList<Location>();

        for(int xoffset = -1; xoffset <= 1; xoffset++) {
            int nextX = x + xoffset;
            if(nextX >= 0 && nextX < WIDTH) {
                for(int yoffset = -1; yoffset <= 1; yoffset++) {
                    int nextY = y + yoffset;
                    // Exclude invalid locations and the original location.
                    if(nextY >= 0 && nextY < HEIGHT && (xoffset != 0 || yoffset != 0)) {
                        if(getObjectsAt(nextX, nextY, Animal.class).isEmpty()) {
                            locations.add(new Location(nextX, nextY));
                        }
                    }
                }
            }
        }

        if (locations.isEmpty()) {
            // no empty adjacents; check whether current location is free
            if(getObjectsAt(x, y, Animal.class).isEmpty()) {
                return new Location(x, y);
            } 
            else {
                return null;
            }
        }
        else {
            // return a random free location
            return locations.get(Greenfoot.getRandomNumber(locations.size()));
        }
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Button button = new Button();
        addObject(button,12,57);
    }
}
