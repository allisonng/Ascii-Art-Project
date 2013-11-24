

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
		
	public void convertImageToAscii(double[][] brightnessImage) throws IOException{
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
	
	private char matchToChar(double brightness){
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
