package Paint;

import java.awt.*;

public class Line extends Shape 
{
	protected Color PicColor = new Color(0, 0, 0);
	
	public Line(int x, int y, int w, int h) 
	{
		super(x, y, w, h);
		PicColor = ColorT.selectedColor();
		super.kindOfPic = 1;
	}

	public void paint(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D)g;
		super.paint(g2);
		g2.setColor(PicColor);	
		g2.drawLine(x,y,x+width,y+height);
	}
}

