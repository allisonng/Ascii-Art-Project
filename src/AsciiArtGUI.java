import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AsciiArtGUI extends JFrame //implements ActionListener
{
    private PicturePanel inputPicPanel, outputPicPanel;
    private JFileChooser fileChooser;
    private BufferedImage inputImage;
    
    private BlockBrightness blockIntensities;
    private CharacterProcess characterProcessor;
    
    private JButton jButton1;
    private JComboBox jComboBox1;
    private JComboBox jComboBox2;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JMenu jMenu1;
    private JMenuBar jMenuBar1;
    private JMenuItem jMenuItem1;
    
    public AsciiArtGUI()
    {
    	initComponents();
        
        
        fileChooser = new JFileChooser();
        inputImage = null;
        
        blockIntensities = new BlockBrightness();
        characterProcessor = new CharacterProcess();
		
		Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(scr.width/2-this.getWidth()/2,scr.height/2-this.getHeight()/2);
		this.setVisible(true);
    }
    
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt)
    {
    	
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) throws IOException
    {
    	
    	if (jComboBox2.getSelectedIndex() == 0)//naive black and white
    	{
    		//DO CONVERSION HERE
    		int[][] brightnessMatrix = blockIntensities.convertImageToBrightnessMatrix(inputImage);
    		
    		if (jComboBox1.getSelectedIndex() == 0)//convert to .txt
    		{
    			String convertedToAscii = characterProcessor.convertImageToAscii(brightnessMatrix, CharacterProcess.BLACKWHITE);
    			characterProcessor.convertAsciiToText(convertedToAscii);
    		}
    		if (jComboBox1.getSelectedIndex() == 1)//convert to .html
    		{
    			
    		}
    	}
    	else if (jComboBox2.getSelectedIndex() == 1)//4 quadrant black and white
    	{
    		//DO CONVERSION HERE
    		int[][][] quadrantBrightness = blockIntensities.convertImageToBrightnessQuadrants(inputImage);
    		
    		if (jComboBox1.getSelectedIndex() == 0)//convert to .txt
    		{
    			characterProcessor.convertQuadImageToAscii(quadrantBrightness);
    		}
    		if (jComboBox1.getSelectedIndex() == 1)//convert to .html
    		{
    			
    		}
    	}
    	else if (jComboBox2.getSelectedIndex() == 2)//Many ASCII characters
    	{
    		//DO CONVERSION HERE
    		int[][] brightnessMatrix = blockIntensities.convertImageToBrightnessMatrix(inputImage);
    		
    		if (jComboBox1.getSelectedIndex() == 0)//convert to .txt
    		{
    	    	CharacterSet cs = new CharacterSet();
    	    	cs.selectionSortArrays();
    	    	cs.removeArrayDuplicates();
    	    	characterProcessor.chars = cs.getChars();
    	    	characterProcessor.density = cs.scaleDensities();
    	    	//System.out.println("im the best");
    	    	
    	    	String convertedToAscii = characterProcessor.convertImageToAscii(brightnessMatrix, CharacterProcess.GREYSCALE);
    	    	characterProcessor.convertAsciiToText(convertedToAscii);    	    	
    		}
    		if (jComboBox1.getSelectedIndex() == 1)//convert to .html
    		{
    			
    		}
    	}
    }

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt)
    {
    	
    }

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
    	ImgFilter pictureExtensionFilter = new ImgFilter();
        fileChooser.addChoosableFileFilter(pictureExtensionFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(pictureExtensionFilter);
        
        int retVal = fileChooser.showOpenDialog(this);
        if(retVal == JFileChooser.APPROVE_OPTION){
            File pictureFile = fileChooser.getSelectedFile();
            
            try{
                inputImage = ImageIO.read(pictureFile);
                inputPicPanel.setBufferedImage(inputImage);
            }
            catch(IOException E){
                JOptionPane.showMessageDialog(this, "You did not select a valid image file");
            }
        }
    }
	
	
    private void initComponents() {

        jLabel1 = new JLabel();
        jComboBox1 = new JComboBox();
        jButton1 = new JButton();
        inputPicPanel = new PicturePanel();
        outputPicPanel = new PicturePanel();
        jComboBox2 = new JComboBox();
        jLabel2 = new JLabel();
        jMenuBar1 = new JMenuBar();
        jMenu1 = new JMenu();
        jMenuItem1 = new JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("List of Algorithms:");

        //jComboBox1.setEditable(true);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { ".TXT", ".HTML" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setText("CONVERT!");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					jButton1ActionPerformed(evt);
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });

        javax.swing.GroupLayout inputPicPanelLayout = new javax.swing.GroupLayout(inputPicPanel);
        inputPicPanel.setLayout(inputPicPanelLayout);
        inputPicPanelLayout.setHorizontalGroup(
            inputPicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );
        inputPicPanelLayout.setVerticalGroup(
            inputPicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout outputPicPanelLayout = new javax.swing.GroupLayout(outputPicPanel);
        outputPicPanel.setLayout(outputPicPanelLayout);
        outputPicPanelLayout.setHorizontalGroup(
            outputPicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );
        outputPicPanelLayout.setVerticalGroup(
            outputPicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );

        //jComboBox2.setEditable(true);
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Naive B&W", "4 Quadrant B&W", "Naive Greyscale" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Convert to Format:");

        jMenu1.setText("File");

        jMenuItem1.setText("Open Image");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(57, 57, 57)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(110, 110, 110)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(inputPicPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(outputPicPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(outputPicPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inputPicPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

}
