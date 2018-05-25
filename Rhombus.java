package Paint;

import java.awt.*;

public class Rhombus extends Shape 
{
	protected Color PicColor = new Color(0, 0, 0);

	public Rhombus(int x, int y, int w, int h) 
	{
		super(x, y, w, w);
		PicColor = ColorT.selectedColor();
		super.kindOfPic = 5;
	}

	public void paint(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D)g;
		super.paint(g2);
		g2.setColor(PicColor);

		int[] x2 = new int[]{x,x+(width)/2,(x+width),x+(width)/2};
		int[] y2 = new int[]{y+(height)/2,y+height,y+(height)/2,y};
		g.drawPolygon(x2, y2, 4);
	}
}