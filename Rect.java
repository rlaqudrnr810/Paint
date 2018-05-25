package Paint;

import java.awt.*;

import javax.swing.JButton;

public class Rect extends Shape
{
	protected Color PicColor = new Color(0, 0, 0);

	public Rect(int x, int y, int w, int h) 
	{
		super(x, y, w, h);
		PicColor = ColorT.selectedColor();
		super.kindOfPic = 4;
	}
	

	public Rect() {
		// TODO Auto-generated constructor stub
	}


	public void paint(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D)g;
		super.paint(g2);
		g2.setColor(PicColor);
		g.drawRect(x, y, width, height);
		
	}
}	