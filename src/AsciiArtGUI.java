import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
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
    private PicturePanel inputPicPanel;
    private JTextArea outputTextArea;
    private JFileChooser fileChooser;
    private BufferedImage inputImage;
    
    private BlockBrightness blockIntensities;
    private CharacterProcess characterProcessor;
    
    private JButton jButton1;
    private JScrollPane jScrollPane;
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
    	String convertedToAscii = "";
    	
    	if (jComboBox2.getSelectedIndex() == 0)//naive black and white
    	{

    		//DO CONVERSION HERE
    		int[][] brightnessMatrix = blockIntensities.convertImageToBrightnessMatrix(inputImage);
    		convertedToAscii = characterProcessor.convertImageToAscii(brightnessMatrix, CharacterProcess.BLACKWHITE);

    	}
    	else if (jComboBox2.getSelectedIndex() == 1)//4 quadrant black and white
    	{
    		//DO CONVERSION HERE
    		int[][][] quadrantBrightness = blockIntensities.convertImageToBrightnessQuadrants(inputImage);
    		convertedToAscii = characterProcessor.convertQuadImageToAscii(quadrantBrightness);

    	}
    	else if (jComboBox2.getSelectedIndex() == 2)//Many ASCII characters
    	{
    		//DO CONVERSION HERE
    		int[][] brightnessMatrix = blockIntensities.convertImageToBrightnessMatrix(inputImage);
    		
    	    CharacterSet cs = new CharacterSet();
    	    characterProcessor.chars = cs.getChars();
    	    characterProcessor.density = cs.scaleDensities();
    	    //System.out.println("im the best");
    	    	
    	    convertedToAscii = characterProcessor.convertImageToAscii(brightnessMatrix, CharacterProcess.GREYSCALE);
    	}
    	
    	// Outputs it to the right hand side of GUI.
    	outputTextArea.setText(convertedToAscii);
    	
    	if (jComboBox1.getSelectedIndex() == 0)//convert to .txt
		{
			characterProcessor.convertAsciiToTextFile(convertedToAscii);
		}
    	else if (jComboBox1.getSelectedIndex() == 1)//convert to .html
		{
			characterProcessor.convertAsciiToHtmlFile(convertedToAscii);
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
        jComboBox2 = new JComboBox();
        jLabel2 = new JLabel();
        jScrollPane = new JScrollPane();
        outputTextArea = new JTextArea();
        jMenuBar1 = new JMenuBar();
        jMenu1 = new JMenu();
        jMenuItem1 = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("List of Algorithms:");

        jComboBox1.setEditable(true);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { ".TXT", ".HTML" }));
        jComboBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setText("CONVERT!");
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	try {
					jButton1ActionPerformed(evt);
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });

        GroupLayout jPanel1Layout = new GroupLayout(inputPicPanel);
        inputPicPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );

        jComboBox2.setEditable(true);
        jComboBox2.setModel(new DefaultComboBoxModel(new String[] { "Naive B&W", "4 Quadrant B&W", "Naive Greyscale" }));
        jComboBox2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Convert to Format:");

        outputTextArea.setColumns(20);
        outputTextArea.setRows(5);
        outputTextArea.setEditable(false);
        outputTextArea.setFont(new Font("Courier", Font.PLAIN, 5));
        jScrollPane.setViewportView(outputTextArea);

        jMenu1.setText("File");

        jMenuItem1.setText("Open Image");
        jMenuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox2, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(57, 57, 57)
                                .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(110, 110, 110)
                        .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE))
                    .addComponent(inputPicPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane, GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane)
                    .addComponent(inputPicPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jComboBox2, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addComponent(jButton1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

}
