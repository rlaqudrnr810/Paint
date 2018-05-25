package Paint;

import java.awt.*;

public class Polygon extends Shape
{
	protected Color PicColor = new Color(0, 0, 0);
	
	public Polygon(int x[], int y[]) 
	{
		super(x, y);
		PicColor = ColorT.selectedColor();
		super.kindOfPic = 6;
	}

	public void paint(Graphics g) 
	{
		int count = 0;
		for(int i=0;i<100;i++)
			if(polyX[i] != 0)
				count++;

		Graphics2D g2 = (Graphics2D)g;
		super.paint(g2);
		g2.setColor(PicColor);
		g2.drawPolygon(polyX, polyY, count);
	}
}