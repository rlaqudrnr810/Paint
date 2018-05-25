package Paint;

import java.awt.*;

public class Triangle extends Shape 
{
	protected Color PicColor = new Color(0, 0, 0);
	public Triangle(int x, int y, int w, int h) 
	{
		super(x, y, w, h);
		
		PicColor = ColorT.selectedColor();
		super.kindOfPic = 3;
	}

	public void paint(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D)g;
		super.paint(g2);
		g2.setColor(PicColor);
		int[] x1 = new int[]{x,(width+x),(x+width+x)/2};
		int[] y1 = new int[]{(height+y),(height+y),y};
		g.drawPolygon(x1, y1, 3);
		
	}
}