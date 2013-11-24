

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;


public class CharacterProcess {

	/* This is where the image will be subsampled.
	 * Should also include method for finding brightness.
	 */
	
	BufferedImage bimg;
	Font font;
	
	public CharacterProcess(){
	}

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
	
	public BufferedImage getImage() throws IOException{

		// NOT in ../src/ but in ../!!! GODDAMNIT
		File img = new File("lena.jpg");
		BufferedImage newImage = ImageIO.read(img); 
		
		return newImage;
	}
	
	public double[][] convertToBlocks(BufferedImage bImg){
		/*
		 * Divide the given picture into a grid of subblocks.
		 * Each subblock will be 4x4 pixels, and each block will be converted into a character.
		 * 
		 * TO BE CHANGED WITH OTHER CONVERSION ALGORITHSM: blockWidth + blockHeight
		 */
		int blockWidth = 4;
		int blockHeight = 4;
		
		int height = bImg.getHeight();
		int width = bImg.getWidth();
		
		int rowSize = (int)Math.floor(bImg.getWidth()/blockWidth);
		int colSize = (int)Math.floor(bImg.getHeight()/blockHeight);
		// Changed float to double; float holds 4 bytes, double holds 8 bytes;
		// A little excessive but more precision is always better just in case
		double[][] brightnessMatrix = new double[rowSize][colSize];
		
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
	

	public double getBrightnessOfBlock(BufferedImage bImg){
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
//				totalRgbVal = totalRgbVal + ((r+g+b)/3);
				totalRgbVal = totalRgbVal + (((0.299*r)+(0.587*g)+(0.114*b))/3);
			}
		}
		
		double blockAverage = (totalRgbVal)/(blockHeight*blockWidth);
		
		return blockAverage;
		
		
	}
	
	
	public void convertImageToAscii(float[][] brightnessImage) throws IOException{
		// brightnessImage represents the brightness of the image from the divided subimages

		int rowHeight = brightnessImage.length;
		int colHeight = brightnessImage[0].length;

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
		
		buffWriter.close();
		
		
	}
	
	public char matchToChar(float brightness){
		// Brightness is the calculated average for a subimage.
		// That entire subimage gets converted to a char below, based on the brightness.
		
		// For black and white
		if(brightness < 100){
			return '#';
		}
		else{
			return ' ';
		}
				
	}
	
	
	
}
