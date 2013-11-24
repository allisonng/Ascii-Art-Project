

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;


public class CharacterProcess {

	/* This is where the image will be subsampled.
	 * Should also include method for finding brightness.
	 */
	
	BufferedImage bimg;
	Font font;
	
	public CharacterProcess(){
		//img = new BufferedImage(300, 300, BufferedImage.TYPE_CUSTOM);
	}
	

	
	/*
	 * Given a character set's bitmap, want to find brightness of every character.
	 * After, initializing this, want to have a way to match image pixel's brightness 
	 * to character's image brightness.
	 */	
	
	public void createDefaultAscii(){
		// Make sure to use only given ASCII values [32, 126]
		int start = 32;
		int end = 126;
		int arraySize = 126 - 32;
		char[] charArray = new char[arraySize];
		
		for(int i=start; i<end; i++){
			// Character.toString ((char) i);
			charArray[i] = (char)i;
		}
		
	}
	
	/* Grab image.
	 * Convert into subblocks.
	 */
	public BufferedImage getImage() throws IOException{

		// NOT in ../src/ but in ../!!! GODDAMNIT
		File img = new File("lena.jpg");
		BufferedImage newImage = ImageIO.read(img); 
		
		return newImage;
	}
	
	public float[][] convertToBlocks(BufferedImage bImg){
		/*
		 * Divide the given picture into a grid of subblocks.
		 * Each subblock will be 4x4 pixels, and each block will be converted into a character.
		 */
		int blockWidth = 4;
		int blockHeight = 4;
		int height = bImg.getHeight();
		int width = bImg.getWidth();
		int rowSize = (int)Math.floor(bImg.getWidth()/4);
		int colSize = (int)Math.floor(bImg.getHeight()/4);
		float[][] brightnessMatrix = new float[rowSize][colSize];
		
		// This will grab us our subimage.
		// Pass the subimage into method that grabs brightness
		int row = 0;
		int col;
		for(int y=0; (y+blockHeight)<=bImg.getHeight(); y+=blockHeight){
//			System.out.print("row " + y + "   col");
			
			col = 0;
			for(int x=0; (x+blockWidth)<=bImg.getWidth(); x+=blockWidth){
				if(x == 504){
					String str = "hello world";
				}
				brightnessMatrix[row][col] 
						= getBrightnessOfBlock(bImg.getSubimage(y, x,blockWidth, blockHeight));
//				System.out.print(brightnessMatrix[y][x] + " ");
				col++;
//				System.out.print(" " + x);
			}
			row++;
//			System.out.print("\n");
		}
		
		return brightnessMatrix;
		
	}
	

	public float getBrightnessOfBlock(BufferedImage bImg){
		 /*need image width, height and type
		 it seems BufferedImage is usually instantiated through .getSubImage or another class,
		 and never actually new BufferImage();
		 
		 ImageIO class as well may be useful
		 */
		
		// Filled in with random parems for now
		
		// Given image, get rgb value to determine brightness at coord(x,y).
		// getRGB() returns back an unsigned integer in form 0xAARRGGBB
		// more info here: http://stackoverflow.com/questions/2534116/how-to-convert-get-rgbx-y-integer-pixel-to-colorr-g-b-a-in-java
		
		
		// Within this block, get brightness of every pixel.
		// Then get the average of all pixels.
		int blockHeight = bImg.getHeight();
		int blockWidth = bImg.getWidth();
		float totalRgbVal = 0;
		
		for(int y=0; y<blockHeight; y++){
			for(int x=0; x<blockWidth; x++){
				int rgbVal = bImg.getRGB(x, y);
				int r = (rgbVal)&0xFF;
				int g = (rgbVal>>8)&0xFF;
				int b = (rgbVal>>16)&0xFF;
				totalRgbVal = totalRgbVal + ((r+g+b)/3);
			}
		}
		
		float blockAverage = (totalRgbVal)/(blockHeight*blockWidth);
		
		return blockAverage;
		
		
	}
	
	
	// Now to translate from given brightnesses of image to character
	public void convertImageToAscii(float[][] brightnessImage) throws IOException{
		// brightnessImage represents the brightness of the image from the divided subimages

		int rowHeight = brightnessImage.length;
		int colHeight = brightnessImage[0].length;
		System.out.println("rowheight: " + rowheight + "colheight" + colheight);
		// Create new file to write to
		File file = new File("asciiArt.txt");
		if(!file.exists()){
			file.createNewFile();
		}
		FileWriter fWriter = new FileWriter(file.getAbsoluteFile());
		BufferedWriter buffWriter = new BufferedWriter(fWriter);

		for(int row=0; row<rowHeight; row++){
			for(int col=0; col<colHeight; col++){
				buffWriter.write(matchToChar(brightnessImage[row][col]));
				
			}
			buffWriter.write("\n");
		}
		
//		buffWriter.write();
		buffWriter.close();
		
		
	}
	
	public char matchToChar(float brightness){
		// Brightness is the calculated average for a subimage.
		// That entire subimage gets converted to a char based on the brightness.
		
		if(brightness >=100){
			return '#';
		}
		else{
			return ' ';
		}
				
	}
	
	
	
}
