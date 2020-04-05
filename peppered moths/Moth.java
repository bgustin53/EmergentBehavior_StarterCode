import greenfoot.*;

/**
 * A moth in a predator/prey simulation. moths move around and breed.
 * 
 * @author M Kölling & Bruce Gustin
 * @version 3/28/2020
 */
public class Moth extends Animal
{
    // Characteristics shared by all moths (static fields).

    // The age to which a moth can live.
    private static final int MAX_AGE = 10;
    // The age at which a moth can start to breed.
    private static final int BREEDING_AGE = 4;
    // The likelihood of a moth breeding (in percent).
    private static final double BREEDING_PROBABILITY = 6;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    // The likelihood moth mutates to black (in percent).
    private static final double MUTATION_PROBABILITY = 1;
    
    private GreenfootImage mothImage;
    private boolean peppered;
    
    // Individual characteristics (instance fields).

    /**
     * Default constructor for testing.
     */
    public Moth()
    {
        this(true, true);
    }

    /**
     * Create a new moth. A moth may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the moth will have a random age.
     */
    public Moth(boolean randomAge, boolean peppered)
    {
        super();
        this.peppered = peppered;
        String imageText = (peppered) ? "pepperedMoth.png" : "blackMoth.png";
        mothImage = new GreenfootImage(imageText);
        setImage(mothImage);
        if(randomAge) {
            setAge(Greenfoot.getRandomNumber(MAX_AGE));
        }
    }
    
    /**
     * This is what the moth does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     */
    public void act() 
    {
        incrementAge();
        if (isAlive()) {
            int births = breed();
            for(int b = 0; b < births; b++) {
                Location loc = getField().freeAdjacentLocation(getX(), getY());
                if (loc != null) {
                    if(peppered) 
                    {
                       peppered = mutate();
                    }
                    Moth newMoth = new Moth(false, peppered);
                    getField().addObject(newMoth, loc.getX(), loc.getY());
                }
            }
            Location newLocation = getField().freeAdjacentLocation(getX(), getY());
            // Only move if there was a free location
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
     * Increase the age.
     * This could result in the moth's death (of old age).
     */
    private void incrementAge()
    {
        setAge(getAge() + 1);
        if(getAge() > MAX_AGE) {
            setDead();
        }
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
     * A moth can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return getAge() >= BREEDING_AGE;
    }
    
    /**
     * Randomly mutates peppered moth into black moth.
     */
    private boolean mutate()
    {
        return Greenfoot.getRandomNumber(100) > MUTATION_PROBABILITY;
    }
    
    public boolean isPeppered()
    {
        return peppered;
    }
}
