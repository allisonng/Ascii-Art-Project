

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;


public class CharacterProcess {
	
	public static int BLACKWHITE = 0;
	public static int GREYSCALE = 1;

	/* This is where the image will be subsampled.
	 * Should also include method for finding brightness.
	 */
	public int[] density = null;
	public char[] chars = null;
	
	BufferedImage bimg;
	Font font;
	
	public CharacterProcess(){
	}

	// For algorithm 3
	public void createDefaultAscii(){
		// Make sure to use only given ASCII values [32, 126]
		int start = 32;
		int end = 126;
		int arraySize = end - start;
		char[] charArray = new char[arraySize];
		
		for(int i=start; i<end; i++){
			// Character.toString ((char) i);
			charArray[i] = (char)i;
		}
		
	}
		
	public void convertImageToAscii(int[][] brightnessImage, int AlgType) throws IOException{
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
				if (AlgType == 0)
					buffWriter.write(matchToChar(brightnessImage[row][col]));
				else if (AlgType == 1)
					buffWriter.write(matchGreyscaleToChar(brightnessImage[row][col], density, chars));
				
			}
			buffWriter.write("\n");
		}
		
		buffWriter.close();
		
		
	}
	
	private char matchToChar(int brightness){
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
	
	public void convertQuadImageToAscii(int[][][] brightnessImage) throws IOException{
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
				buffWriter.write(matchToQuadrantChar(brightnessImage[row][col]));
				
			}
			buffWriter.write("\n");
		}
		
		buffWriter.close();
		
		
	}
	
	private char matchToQuadrantChar(int[] quadrants)
	{
		// light = true; dark = false
		boolean ul=true, ur=true, ll=true, lr=true;
		
		for(int quadrant = 0; quadrant < quadrants.length; quadrant++)
		{
			if(quadrants[quadrant] < 100)
			{
				if(quadrant==0)
					ul = false;
				else if(quadrant==1)
					ur = false;
				else if(quadrant==2)
					ll = false;
				else
					lr = false;
			}
		}
		
		if(ul && ur && ll && lr)
		{
			return ' ';
		}
		// one square dark
		else if(ul && ur && !ll && lr)
		{
			return '.';
		}
		else if(ul && ur && ll && !lr)
		{
			return '.';
		}
		else if(!ul && ur && ll && lr)
		{
			return '`';
		}
		else if(ul && !ur && ll && lr)
		{
			return '\'';
		}
		//two adjacent square dark
		else if(!ul && !ur && ll && lr)
		{
			return '"';
		}
		else if(ul && ur && !ll && !lr)
		{
			return '_';
		}
		else if(!ul && ur && !ll && lr)
		{
			return '[';
		}
		else if(ul && !ur && ll && !lr)
		{
			return ']';
		}
		//two nonadjacent squares
		else if(!ul && ur && ll && !lr)
		{
			return '\\';
		}
		else if(ul && !ur && !ll && lr)
		{
			return '/';
		}
		//three square dark
		else if(!ul && !ur && !ll && lr)
		{
			return 'P';
		}
		else if(ul && !ur && !ll && !lr)
		{
			return 'J';
		}
		else if(!ul && ur && !ll && !lr)
		{
			return 'L';
		}
		else if(!ul && !ur && ll && !lr)
		{
			return '7';
		}
		//all dark
		return '#';
	}
	
	
	
	private char matchGreyscaleToChar(int brightness, int[] densityArr, char[] charArr)
	{
		for (int i = 1; i < densityArr.length; i++)
		{
			//value where brightness gets overtaken
			if (brightness <= densityArr[i])
			{
				// if smaller, then closer
				if (densityArr[i] - brightness <= brightness - densityArr[i-1])
				{
					return charArr[charArr.length-i-1];// i
				}
				else
				{
					return charArr[charArr.length-i];// i-1
				}
			}
		}
		return charArr[charArr.length-1];
	}
	
}
