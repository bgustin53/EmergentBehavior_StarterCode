import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Tree here.
 * 
 * @author Bruce Gustin
 * @version 3/24/2020
 */

public class Tree extends Actor
{
    private GreenfootImage image1, image2;
    private String color;
    private final int PROBABILITY_BLACKENED = 2; //(in percent);
    
    
    public Tree()
    {
        color = "green";
        image1 = new GreenfootImage("greenTree.png");
        image2 = new GreenfootImage("blackTree.png");
        setImage(image1);
    }
    
    /**
     * Act - do whatever the Tree wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(Button.getIndustrialRevolution() &&
           Greenfoot.getRandomNumber(100) < PROBABILITY_BLACKENED)
        {
            setImage(image2);
            color = "black";
        }
            
    }    
    
    public String getColor()
    {
        return color;
    }

}
