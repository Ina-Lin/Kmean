package New;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class ImageGui  extends JFrame {
	JPanel cotrolPanelMain = new JPanel();
	JPanel cotrolPanelShow = new JPanel();;
	
	ImagePanel imagePanel;
	ImagePanel imagePanel2;

	JButton btnShow = new JButton("顯示");
	JButton btnDither = new JButton("降色");
	
	JSlider slider;
	JLabel lbLess = new JLabel("   Iteration:  1");
	JLabel lbMore = new JLabel("10");
	
	int[][][] data;
	int height;
	int width;
	BufferedImage img = null;
	int iteration;
	
	 ImageGui (){
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
	    setTitle("K-means application to colored image's dithering");
		
		try {
			img = ImageIO.read(new File("F16.png"));
			//img = ImageIO.read(new File("F16.png"));
			
		} catch (IOException e) {
			System.out.println("IO exception");
		}
		
		height = img.getHeight();
		width = img.getWidth();
		System.out.println(height+" "+width);
		data = new int[height][width][3]; 
		
		for (int y=0; y<height; y++){
	    	for (int x=0; x<width; x++){
	    		int rgb = img.getRGB(x, y);
	    		data[y][x][0] = Util.getR(rgb);
	    		data[y][x][1] = Util.getG(rgb);
	    		data[y][x][2] = Util.getB(rgb);
	    	}
	    }

		cotrolPanelMain = new JPanel();
		cotrolPanelMain.setLayout(new GridLayout(6,1));
		cotrolPanelShow.add(btnShow);
		cotrolPanelShow.add(btnDither);
		cotrolPanelShow.add(lbLess);
		slider = new JSlider(1, 10,5);
		iteration = slider.getValue();
		cotrolPanelShow.add(slider);
		cotrolPanelShow.add(lbMore);
		cotrolPanelMain.add(cotrolPanelShow);
		cotrolPanelMain.setBounds(0, 0,1200,200);
		getContentPane().add(cotrolPanelMain);
	    imagePanel = new ImagePanel();
	    imagePanel.setBounds(20,50, 620,450);
	    getContentPane().add(imagePanel);
	    
	    imagePanel2 = new ImagePanel();
	    imagePanel2.setBounds(630,50, 1230,450);
	    getContentPane().add(imagePanel2);

	    btnShow.addActionListener(new ActionListener(){ 
			public void actionPerformed(ActionEvent arg0) {
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, data);
			}
	      });   
		    
	    slider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				iteration = slider.getValue();
				System.out.println("iteration = " + iteration);
				
			}});
	  
	    btnDither.addActionListener(new ActionListener(){ 
			public void actionPerformed(ActionEvent arg0) {
				Kmean K = new Kmean(iteration,data);
				Graphics g = imagePanel2.getGraphics();
				imagePanel2.paintComponent(g, K.data);
			}
	    });   
	 }
}



