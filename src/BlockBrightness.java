import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class BlockBrightness {

	/* GUI lets BB (BlockBrightness) know:
	 * 1. what the image is
	 * 2. what the algorithm is (b&w, high res b&w, grey scale)
	 * 3. what the format is (.txt or .html)
	 * 
	 * This class will divide 
	 */
	BufferedImage bImg;
	double[][] brightnessMatrix;
	
	public BlockBrightness(){
	}
	
	public BufferedImage getImage() throws IOException{

		// NOT in ../src/ but in ../!!! GODDAMNIT
		File img = new File("lena.jpg");
		BufferedImage newImage = ImageIO.read(img); 
		
		return newImage;
	}
	
	
	public double[][] convertImageToBrightnessMatrix(BufferedImage bImg){
		/*
		 * Divide the given picture into a grid of subblocks.
		 * Each subblock will be 4x4 pixels, and each block will be converted into a character.
		 * 
		 * TO BE CHANGED WITH OTHER CONVERSION ALGORITHSM: blockWidth + blockHeight
		 */
		int blockWidth = 4;
		int blockHeight = 4;
		
		int rowSize = (int)Math.floor(bImg.getWidth()/blockWidth);
		int colSize = (int)Math.floor(bImg.getHeight()/blockHeight);

		// Changed float to double; float holds 4 bytes, double holds 8 bytes;
		// A little excessive but more precision is always better just in case
		brightnessMatrix = new double[rowSize][colSize];
		
		int row = 0;
		int col;
		for(int y=0; (y+blockHeight)<=bImg.getHeight(); y+=blockHeight){

			col = 0;
			for(int x=0; (x+blockWidth)<=bImg.getWidth(); x+=blockWidth){
				brightnessMatrix[row][col] = getBrightnessOfBlock(bImg.getSubimage(x, y,blockWidth, blockHeight));
				col++;
			}
			row++;
		}
		
		return brightnessMatrix;
	}
	
	private double getBrightnessOfBlock(BufferedImage bImg){
		// Given SUBIMAGE, get rgb value to determine brightness at coord(x,y).
		// NOTE: getRGB() returns back an unsigned integer in form 0xAARRGGBB
		// more info here: http://stackoverflow.com/questions/2534116/how-to-convert-get-rgbx-y-integer-pixel-to-colorr-g-b-a-in-java
		
		
		// Within this block, get brightness of every pixel.
		// Then get the average of all pixels.
		int blockHeight = bImg.getHeight();
		int blockWidth = bImg.getWidth();
		double totalRgbVal = 0;
		
		// luminosity y' = 0.299*r + 0.587*g +  0.114*b, y' = totalRgbVal in this case
		
		for(int y=0; y<blockHeight; y++){
			for(int x=0; x<blockWidth; x++){
				int rgbVal = bImg.getRGB(x, y);
				int r = (rgbVal)&0xFF;
				int g = (rgbVal>>8)&0xFF;
				int b = (rgbVal>>16)&0xFF;
				totalRgbVal = totalRgbVal + ((r+g+b)/3);
//				totalRgbVal = totalRgbVal + (((0.299*r)+(0.587*g)+(0.114*b))/3);
			}
		}
		
		double blockAverage = (totalRgbVal)/(blockHeight*blockWidth);
		
		return blockAverage;
		
	}
	
}
