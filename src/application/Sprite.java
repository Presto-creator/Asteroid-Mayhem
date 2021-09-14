package application;

/**
 * This class was created by Steven Kronmiller and Preston Leigh
 * All images and music are open-source and copyright free
 * Version 4-26-2021
 */
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Random;

/**
 * The sprites in a game
 * @author KronmillerSteven
 */
public class Sprite { 
	
	private Image image;
	private double xPosition;
	private double yPosition;
	private double xVelocity;
	private double yVelocity;
	private double width;
	private double height;
	private Random rand = new Random();
	
	/**
	 * default constructor for sprite
	 */
	public Sprite() {
		xPosition = 400; // off screen
		yPosition = rand.nextInt(1); // fix
		xVelocity = 0; 
		yVelocity = 0;
	}
	
	/**
	 * constructor using an existing image
	 * @param image is the image the sprite uses
	 */
	public Sprite(Image image) {
		xPosition = 400; // off screen
		yPosition = rand.nextInt(1);
		xVelocity = 0; 
		yVelocity = 0;
		this.image = image;
		width = image.getWidth();
		height = image.getHeight();
	}

	/**
	 * get sprite function
	 * @return the sprite image
	 */
	public Image getSprite() {
		return this.image;
	}
	
	/**
	 * takes in an image and sets it as the sprite's image
	 * @param image is the image of the sprite
	 */
	public void setImage(Image image)
	{
		this.image = image;
		width = image.getWidth();
		height = image.getHeight();
	}
	
	/**
	 * takes in a file name and sets it as the sprites image
	 * @param filename is the file of the image
	 */
	public void setImage(String filename)
	{
		Image image = new Image(filename);
		setImage(image);
		
	}
	
	/**
	 * takes in two doubles and sets them as their corresponding coordinates
	 * @param x is the x-coordinate
	 * @param y is the y-coordinate
	 */
	public void setPosition(double x, double y)
	{
		xPosition = x;
		yPosition = y;
	}
	
	/**
	 * returns the x position
	 * @return the x position
	 */
	public double getXPosition()
	{
		return xPosition;
	}
	
	/**
	 * returns the y position
	 * @return the y position
	 */
	public double getYPosition()
	{
		return yPosition;
	}
	
	/**
	 * takes in two doubles and sets the velocity of the sprite
	 * @param x is the x velocity
	 * @param y is the y velocity
	 */
	public void setVelocity (double x, double y)
	{
		xVelocity = x;
		yVelocity = y;
	}
	
	/**
	 * takes in two doubles and adds (or subtracts) said numbers to the velocity of the sprite
	 * @param x is the x velocity to add
	 * @param y is the y velocity to add
	 */
	public void addVelocity(double x, double y)
	{
		xVelocity += x;
		yVelocity += y;
	}
	
	/**
	 * takes in a double and updates the position of the sprite
	 * @param time is the time to update the positions
	 */
	public void update(double time)
	{
		xPosition += xVelocity * time;
		yPosition += yVelocity * time;
	}
	
	/**
	 * takes in the graphicscontext and draws the sprite on screen
	 * @param gc is the graphics
	 */
	public void render (GraphicsContext gc)
	{
		gc.drawImage(image, xPosition, yPosition);
	}
	
	/**
	 * gets the boundary of the sprite's border
	 * @return the dimensions of a sprite
	 */
	public Rectangle2D getBoundary()
	{
		return new Rectangle2D(xPosition, yPosition, width, height);
	}
	
	/**
	 * takes in a sprite and checks if it intersects with the calling sprite
	 * @param s is the sprite to see if it intersects
	 * @return if the spites intersect or not
	 */
	public boolean intersects(Sprite s)
	{
		return s.getBoundary().intersects(this.getBoundary());
	}
	
	
	/**
	 * resets the position of the calling sprite
	 */
	public void resetPos()
	{
		double y = 650 * Math.random();
		setPosition(200, y);
	}
}
