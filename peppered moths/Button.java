import greenfoot.*;

/**
 * A moth in a predator/prey simulation. moths move around and breed.
 * 
 * @author M Kölling & Bruce Gustin
 * @version 3/28/2020
 */
public class Button extends Actor
{
    private static final Color transparent = new Color(0,0,0,0);
    private GreenfootImage background;
    private String prefix;
    private static boolean industrialRevolution = false;
    
    public Button()
    {
        this("Begin Industrial Revolution");
    }

    /**
     * Create a button for later use to initialize the narration.
     */
    public Button(String prefix)
    {
        background = getImage();  // get image from class
        this.prefix = prefix;
        updateImage();
    }

    /**
     *
      */
     public void act()
     {
        if(Greenfoot.mouseClicked(this))
        {
            industrialRevolution = true;
            getWorld().removeObject(this);
        }
     }
    
    /**
     * Update the image on screen to show the current value.
     */
    private void updateImage()
    {
        GreenfootImage image = new GreenfootImage(background);
        GreenfootImage text = new GreenfootImage(prefix, 22, Color.BLACK, transparent);
        
        if (text.getWidth() > image.getWidth() - 20)
        {
            image.scale(text.getWidth() + 20, image.getHeight());
        }
        
        image.drawImage(text, (image.getWidth()-text.getWidth())/2, 
                        (image.getHeight()-text.getHeight())/2);
        setImage(image);
    }
       
    public static boolean getIndustrialRevolution()
    {
        return industrialRevolution;
    }
}
