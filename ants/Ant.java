import greenfoot.*;

/**
 * An ant that collects food.
 * 
 * @author Michael Kölling
 * @version 0.1
 */
public class Ant extends Creature
{
    /**
     * Create an ant with a given home hill. The initial speed is zero (not moving).
     */
    public Ant(AntHill home)
    {
        setHomeHill(home);
    }

    /**
     * Do what an ant's gotta do.
     */
    public void act()
    {
        testFoodPile(); // This currently does not do anything
    }
    
    private void testFoodPile()
    {
    
    /*****************************************************************
        Food food = (Food) getOneIntersectingObject(Food.class);
        if (food != null) 
        {
            food.removeCrumb();
        }
    *****************************************************************/
    
    }
}