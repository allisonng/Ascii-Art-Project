import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;



public class CharacterSet {

	private double[] density;
	private char[] chars;
	private double MAX_VALUE;
	public double ratioWH;
	
	private Color WHITE = new Color(255,255,255);
	private Color BLACK = new Color(0,0,0);

	
	private static char[] TESTERS = { '^', '-', '-', '.', '.' };
	
	public CharacterSet(char[] characters, Font font){
		Canvas c = new Canvas();
		FontMetrics fm = c.getFontMetrics(font);
		int h = getHeight(TESTERS, font, fm);
		int cLen = characters.length;
		int totalW = 0;
		chars = characters;
		//density = new DensityMap[cLen];
		density = new double[cLen];
		for(int i=0;i<cLen;i++)
		{
			if((int)chars[i]==127 || (int)chars[i]<32){continue;}
			String s = ""+chars[i];
			int w = fm.charWidth(chars[i]);
			totalW+=w;
			if(w<1 || h<1){continue;}
			BufferedImage tester = getImage(w,h,font,s);
			//density[i] = new DensityMap(tester, blockSize.width, blockSize.height);
			density[i] = getDensity(tester);
			if(density[i] > MAX_VALUE)
			{
				MAX_VALUE=density[i];
			}
		}
		ratioWH = (double)h/((double)totalW / (double)cLen);
	}
	
	private int getHeight(char[] chars, Font f, FontMetrics fm){
		double height = 0.0;
		for(int i=0;i<chars.length;i++){
			height += f.createGlyphVector(fm.getFontRenderContext(), ""+chars[i]).getVisualBounds().getHeight();
		}
		return (int)Math.ceil(height/(double)chars.length);
	}
	
	
	/**
	 * Get image of string.
	 * @param w The width in pixels of the image.
	 * @param h The height in pixels of the image.
	 * @param font The font to draw with.
	 * @param s The string to draw.
	 * @return An image containing the string s.
	 */
	private BufferedImage getImage(int w, int h, Font font, String s){
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(WHITE);
		g.fillRect(0, 0, w, h);
		g.setColor(BLACK);
		g.setFont(font);
		g.drawString(s, 0, h/2+h/4);
		g.dispose();
		return img;
	}
	
	
	/**
	 * Calculates the density of an RGB code.
	 * @param rgb The RGB integer representing the color in RGB.
	 * @return The density matching the RGB code.
	 */
	private static double getDensity(int rgb){
		int red = ((rgb >> 16) & 0xFF);
		int green = ((rgb >> 8) & 0xFF);
		int blue = (rgb & 0xFF);
		return 256 - (0.299 * red + 0.587 * green + 0.114 * blue);
	}
	
	/**
	 * Calculates the density of an image.
	 * @param img The image to calculate the density of.
	 * @return The density of the input image.
	 */
	public static double getDensity(BufferedImage img){
		int w = img.getWidth();
		int h = img.getHeight();
		double p = 0;
		double size = w * h;
		for(int x=0;x<w;x++)
		{
			for(int y=0;y<h;y++)
			{
				int rgb = img.getRGB(x, y);
				p+=getDensity(rgb);
			}
		}
		return p / size;
	}
}

