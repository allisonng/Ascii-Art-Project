

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

	// For algorithm 3
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
		
	public void convertImageToAscii(int[][] brightnessImage) throws IOException{
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
		if(ul && ur && !ll && lr)
		{
			return '.';
		}
		if(ul && ur && ll && !lr)
		{
			return '.';
		}
		if(!ul && ur && ll && lr)
		{
			return '`';
		}
		if(ul && !ur && ll && lr)
		{
			return '\'';
		}
		//two adjacent square dark
		if(!ul && !ur && ll && lr)
		{
			return '"';
		}
		if(ul && ur && !ll && !lr)
		{
			return '_';
		}
		if(!ul && ur && !ll && lr)
		{
			return '[';
		}
		if(ul && !ur && ll && !lr)
		{
			return ']';
		}
		//two nonadjacent squares
		if(!ul && ur && ll && !lr)
		{
			return '\\';
		}
		if(ul && !ur && !ll && lr)
		{
			return '/';
		}
		//three square dark
		if(!ul && !ur && !ll && lr)
		{
			return 'P';
		}
		if(ul && !ur && !ll && !lr)
		{
			return 'J';
		}
		if(!ul && ur && !ll && !lr)
		{
			return 'L';
		}
		if(!ul && !ur && ll && !lr)
		{
			return '7';
		}
		//all dark
		return '#';
	}
	
	
	
}
