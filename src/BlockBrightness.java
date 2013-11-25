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
	
	final int blockWidth = 6;
	final int blockHeight = 6;
	
	public BlockBrightness(){
	}
	
	public BufferedImage getImage() throws IOException{

		// NOT in ../src/ but in ../!!! GODDAMNIT
		File img = new File("lena.jpg");
		BufferedImage newImage = ImageIO.read(img); 
		
		return newImage;
	}
	
	
	public int[][] convertImageToBrightnessMatrix(BufferedImage bImg){
		/*
		 * Divide the given picture into a grid of subblocks.
		 * Each subblock will be nxm pixels, and each block will be converted into a character based on intensity.
		 * 
		 * TO BE CHANGED WITH OTHER CONVERSION ALGORITHSM: blockWidth + blockHeight
		 */
		
		int rowSize = (int)Math.floor(bImg.getHeight()/blockHeight);
		int colSize = (int)Math.floor(bImg.getWidth()/blockWidth);

		// Changed float to double; float holds 4 bytes, double holds 8 bytes;
		// A little excessive but more precision is always better just in case
		int[][] brightnessMatrix = new int[rowSize][colSize];
		
		int row = 0;
		int col;
		for(int y=0; (y+blockHeight)<bImg.getHeight(); y+=blockHeight){

			col = 0;
			for(int x=0; (x+blockWidth)<bImg.getWidth(); x+=blockWidth){
				brightnessMatrix[row][col] = getBrightnessOfBlock(bImg.getSubimage(x, y, blockWidth, blockHeight));
				col++;
			}
			row++;
		}
		
		return brightnessMatrix;
	}
	
	private int getBrightnessOfBlock(BufferedImage bImg){
		// Given SUBIMAGE, get rgb value to determine brightness at coord(x,y).
		// NOTE: getRGB() returns back an unsigned integer in form 0xAARRGGBB
		// more info here: http://stackoverflow.com/questions/2534116/how-to-convert-get-rgbx-y-integer-pixel-to-colorr-g-b-a-in-java
		
		
		// Within this block, get brightness of every pixel.
		// Then get the average of all pixels.
		int blkHeight = bImg.getHeight();
		int blkWidth = bImg.getWidth();
		double totalRgbVal = 0;
		
		// luminosity y' = 0.299*r + 0.587*g +  0.114*b, y' = totalRgbVal in this case
		
		for(int y=0; y<blkHeight; y++){
			for(int x=0; x<blkWidth; x++){
				int rgbVal = bImg.getRGB(x, y);
				int r = (rgbVal)&0xFF;
				int g = (rgbVal>>8)&0xFF;
				int b = (rgbVal>>16)&0xFF;
//				totalRgbVal = totalRgbVal + ((r+g+b)/3);
				totalRgbVal = totalRgbVal + ((0.299*((float) r))+(0.587*((float) g))+(0.114*((float) b)));
			}
		}
		
		int blockAverage = (int) ((totalRgbVal)/((float) (blkHeight*blkWidth)));
		
		return blockAverage;
		
	}
	
	public int[][][] convertImageToBrightnessQuadrants(BufferedImage bImg)
	{
		int rowSize = (int)Math.floor(bImg.getHeight()/blockWidth);
		int colSize = (int)Math.floor(bImg.getWidth()/blockWidth);
		
		int quadWidth = blockWidth/2;
		int quadHeight = blockHeight/2;
		
		int[][][] brightnessMatrix = new int[rowSize][colSize][4];
		
		int row = 0;
		int col;
		for(int y=0; (y+blockHeight)<bImg.getHeight(); y+=blockHeight)
		{
			col = 0;
			for(int x=0; (x+blockWidth)<bImg.getWidth(); x+=blockWidth)
			{
				for(int quadrant = 0; quadrant < 4; quadrant++)
				{
					if (quadrant < 2)// 0 or 1
					{
						brightnessMatrix[row][col][quadrant] = getBrightnessOfBlock(bImg.getSubimage(x+(quadWidth*quadrant), y, quadWidth, quadHeight));
					}
					else// 2 or 3
						brightnessMatrix[row][col][quadrant] = getBrightnessOfBlock(bImg.getSubimage(x+(quadWidth*(quadrant-2)), y+quadHeight, quadWidth, quadHeight));
				}
				col++;
			}
			row++;
		}
		
		return brightnessMatrix;
	}
	
}
