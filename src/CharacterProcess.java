

import java.awt.image.BufferedImage;


public class CharacterProcess {

	/* This is where the image will be subsampled.
	 * Should also include method for finding brightness.
	 */
	
	BufferedImage img;
	
	public CharacterProcess(){
		img = new BufferedImage(300, 300, BufferedImage.TYPE_CUSTOM);
	}
	
	public float getSubImageBrightness(){
		 /*need image width, height and type
		 it seems BufferedImage is usually instantiated through .getSubImage or another class,
		 and never actually new BufferImage();
		 
		 ImageIO class as well may be useful
		 */
		
		// Filled in with random parems for now
		
		// Given image, get rgb value to determine brightness at coord(x,y).
		// getRGB() returns back an unsigned integer in form 0xAARRGGBB
		// more info here: http://stackoverflow.com/questions/2534116/how-to-convert-get-rgbx-y-integer-pixel-to-colorr-g-b-a-in-java
		int rgbVal = img.getRGB(1, 1);
		int r = (rgbVal)&0xFF;
		int g = (rgbVal>>8)&0xFF;
		int b = (rgbVal>>16)&0xFF;		
		
		float totalRgbVal = (r+g+b)/3;
		
		return totalRgbVal;
	}
	
	/*
	 * Given a character set's bitmap, want to find brightness of every character.
	 * After, initializing this, want to have a way to match image pixel's brightness 
	 * to character's image brightness.
	 */	
	
	// Make a bitmap of all characters in a font
	public void createCharBitmap(){
		// Make sure to use only given ASCII values
	}
	
}
