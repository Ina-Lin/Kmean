package New;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
	 public void paintComponent(Graphics g, int data [][][]){
		 for (int y=0; y<data.length; y++){
		    	for (int x=0; x< data[y].length; x++){
		    		int red = data[y][x][0];
		    		int green =  data[y][x][1];
		            int blue =  data[y][x][2];
		            g.setColor(new Color(red, green, blue));
		    	    g.drawLine(x, y, x, y);		
		    	} 
		  	}
	 }
}
