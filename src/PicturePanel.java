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
        
        if (image != null)
        {
        	Dimension d = getSize();
    		g.drawImage(image, 0, 0, d.width, d.height, null);
        }
    }
}
