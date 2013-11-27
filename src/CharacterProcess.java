

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
		
	public String convertImageToAscii(int[][] brightnessImage, int AlgType){
		// brightnessImage represents the brightness of the image from the divided subimages

		int rowHeight = brightnessImage.length;
		int colHeight = brightnessImage[0].length;

		String convertedAscii = "";
		
		for(int row=0; row<rowHeight; row++){
			for(int col=0; col<colHeight; col++){
				if (AlgType == BLACKWHITE)
					convertedAscii =  convertedAscii + matchToChar(brightnessImage[row][col]);
				else if (AlgType == GREYSCALE)
					convertedAscii = convertedAscii + matchGreyscaleToChar(brightnessImage[row][col], density, chars);
			}
			convertedAscii = convertedAscii + "\n";
		}		
		
		return convertedAscii;
	}
	
	public String convertQuadImageToAscii(int[][][] brightnessImage){
		// brightnessImage represents the brightness of the image from the divided subimages
		
		int rowHeight = brightnessImage.length;
		int colHeight = brightnessImage[0].length;
		
		String convertedAscii = "";
		
		for(int row=0; row<rowHeight; row++){
			for(int col=0; col<colHeight; col++){
				convertedAscii = convertedAscii + matchToQuadrantChar(brightnessImage[row][col]);				
			}
			convertedAscii = convertedAscii + "\n";
		}
		
		return convertedAscii;
	}
	
	public void convertAsciiToTextFile(String asciiText) throws IOException{
		// Create new file to write to
		File file = new File("asciiArt.txt");
		if(!file.exists()){
			file.createNewFile();
		}
		FileWriter fWriter = new FileWriter(file.getAbsoluteFile());
		BufferedWriter buffWriter = new BufferedWriter(fWriter);
		
		buffWriter.write(asciiText);		
		buffWriter.close();
		
	}
	
	public void convertAsciiToHtmlFile(String asciiText) throws IOException{
		/* 1. need monospaced font!
		 * 2. white-space pre, preserves all existing formatting 
		 * 		(so no auto word wrap or condensing white space)
		 */
		String htmlText = "\n<html>\n<head>\n\t<title>ASCII ART</title>" + 
				"\n<style type=\"text/css\">" +
				"\n\tbody{font-family: monospace; \n\twhite-space: pre;}" +
				"\n</head>" + 
				"\n</style>" +
				"\n<body>\n" + asciiText + "\n</body>\n</html>";
		
		
		File file = new File("asciiOnline.html");
		if(!file.exists()){
			file.createNewFile();
		}
		
		FileWriter fWriter = new FileWriter(file.getAbsoluteFile());
		BufferedWriter buffWriter = new BufferedWriter(fWriter);
		
		buffWriter.write(htmlText);
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
