package Paint;

import java.awt.*;


public class Circle extends Shape {
	protected Color PicColor = new Color(0, 0, 0);			//�⺻ �׵θ� �� : ����

	public Circle(int x, int y, int w, int h) 				//�׵θ� �� ����
	{
		super(x, y, w, w);
		PicColor = ColorT.selectedColor();
		super.kindOfPic = 2;
	}

	public void paint(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D)g;
		super.paint(g2);
		g2.setColor(PicColor);
		g2.drawOval(x,y,width,width);
	}

}