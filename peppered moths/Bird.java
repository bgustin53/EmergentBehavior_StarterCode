import greenfoot.*;
import java.util.List;

/**
 * A moth in a predator/prey simulation. moths move around and breed.
 * 
 * @author M Kölling & Bruce Gustin
 * @version 3/28/2020
 */
public class Bird extends Animal
{
    // Characteristics shared by all birdes (static fields).
    
    // The age to which a bird can live.
    private static final int MAX_AGE = 18;
    // The age at which a bird can start to breed.
    private static final int BREEDING_AGE = 5;
    // The likelihood of a bird breeding (in percent).
    private static final int BREEDING_PROBABILITY = 7;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // The food value of a single moth. In effect, this is the
    // number of steps a bird can go before it has to eat again.
    private static final int MOTH_FOOD_VALUE = 10;
    
    // Individual characteristics (instance fields).

    // The bird's food level, which is increased by eating moths.
    private int foodLevel;

    /**
     * Default constructor for testing.
     */
    public Bird()
    {
        this(true);
    }

    /**
     * Create a bird. A bird can be created as a new born (age zero
     * and not hungry) or with random age.
     * @param randomAge If true, the bird will have random age and hunger level.
     */
    public Bird(boolean randomAge)
    {
        super();
        if(randomAge) {
            setAge(Greenfoot.getRandomNumber(MAX_AGE / 2));
            foodLevel = Greenfoot.getRandomNumber(MOTH_FOOD_VALUE);
        }
        else {
            // leave age at 0
            foodLevel = MOTH_FOOD_VALUE;
        }
    }
    
    /**
     * This is what the bird does most of the time: it hunts for
     * moths. In the process, it might breed, die of hunger,
     * or die of old age.
     */
    public void act() 
    {
        incrementAge();
        incrementHunger();
        if (isAlive()) {
            // New birdes are born into adjacent locations.
            int births = breed();
            for(int b = 0; b < births; b++) {
                Location loc = getField().freeAdjacentLocation(getX(), getY());
                if (loc != null) {
                    Bird newbird = new Bird(false);
                    getField().addObject(newbird, loc.getX(), loc.getY());
                }
            }
            // Move towards the source of food if found.
            Location newLocation = findFood(getX(), getY());
            if(newLocation == null) {  // no food found - move randomly
                newLocation = getField().freeAdjacentLocation(getX(), getY());
            }
            if(newLocation != null) {
                setLocation(newLocation.getX(), newLocation.getY());
            }
            else {
                // can neither move nor stay - overcrowding - all locations taken
                setDead();
            }
        }
    }    
    
    /**
     * Increase the age. This could result in the bird's death.
     */
    private void incrementAge()
    {
        setAge(getAge() + 1);
        if(getAge() > MAX_AGE)
        {
            setDead();
        }
    }
    
    /**
     * Make this bird more hungry. This could result in the bird's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) 
        {
            setDead();
        }
    }
    
    /**
     * Tell the bird to look for moths adjacent to its current location.
     * Only the first live moths is eaten.
     * @param field The field in which it must look.
     * @param location Where in the field it is located.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood(int x, int y)
    {
        List moths = getNeighbours(1, true, Moth.class);
        Location location = null;
        // There is a moth nearby
        if (!moths.isEmpty())  
        {
            Moth moth = (Moth) moths.get(0);
            boolean peppered = moth.isPeppered();
            Tree tree = ((Tree) getOneIntersectingObject(Tree.class));
            // The moth is on a tree
            if(tree != null)
            {
                // The moth is visible to the bird
                if(tree.getColor().equals("green") && !peppered || 
                   tree.getColor().equals("black") && peppered)
                {
                    location = new Location(moth.getX(), moth.getY());
                    moth.setDead();  // eat it
                    foodLevel = MOTH_FOOD_VALUE;
                }
            }
            // The moth is not on a tree
            else
            {
                    location = new Location(moth.getX(), moth.getY());
                    moth.setDead();  // eat it
                    foodLevel = MOTH_FOOD_VALUE;
            }
        }
        return location;
    }
        
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * Return the number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && Greenfoot.getRandomNumber(100) <= BREEDING_PROBABILITY) {
            births = Greenfoot.getRandomNumber(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
    
    /**
     * A bird can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return getAge() >= BREEDING_AGE;
    }
}
