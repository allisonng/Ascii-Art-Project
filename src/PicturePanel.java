import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.JPanel;

public class PicturePanel extends JPanel
{
private Image image;
    
    public PicturePanel(){}
    
    public void setBufferedImage(Image bufferedImage)
    {
        if (bufferedImage != null)
        {
            image = bufferedImage;
            revalidate();
            repaint();
            //setLayout(new BorderLayout());
        }
        
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
	    Dimension d = getSize();
	    g.setColor(getBackground());
	    g.fillRect(0, 0, d.width, d.height);
	    if (image != null)
            {
	    	g.drawImage(image, 0, 0, d.width, d.height, 0, 0, image.getWidth(null), image.getHeight(null), this);
            }
        
//        super.paintComponent(g);
//        
//        if (image != null)
//        {
//            Dimension d = getSize();
//            Insets insets = getInsets();
//            int width = d.width - insets.left - insets.right;
//            int height = d.height - insets.top - insets.left;
//            float x = (width - image.getWidth(null)) * 0.5f;//alignmentX
//            float y = (height - image.getHeight(null)) * 0.5f;//alignmentY
//            g.drawImage(image, (int)x + insets.left, (int)y + insets.top, this);
//        }
    }
}
