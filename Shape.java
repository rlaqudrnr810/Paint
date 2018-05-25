package Paint;

import java.awt.*;
import java.io.*;

class Shape implements Drawable, Serializable{
	static final int NONE = 0;
	static final int MOVE = 1;
	static final int UPPER_LEFT = 2;
	static final int UPPER_RIGHT = 3;
	static final int BOTTOM_LEFT = 4;
	static final int BOTTOM_RIGHT = 5;
	public int kindOfPic = 0;

	protected int polyX[] = new int[100];
	protected int polyY[] = new int[100];
	protected int xD[] = new int[100];
	protected int yD[] = new int[100];
	protected int x, y, width, height;
	protected int x2, y2, width2, height2;
	protected int x3, y3;
	protected boolean first, selected ;
	protected Rectangle u1, u2, b1, b2;
	public static int point = 5;

	
	public Shape(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		x3 = y3 = 0;
		first = true;

		x2 = x;
		y2 = y;
		width2 = width;
		height2 = height;
	}
	public Shape(int[] x, int[] y)
	{
		int Xmax, Xmin, Ymax, Ymin;			
		int count = 0;				

		Xmax = x[0];
		Xmin = x[0];
		Ymax = y[0];
		Ymin = y[0];

		for(int i=0;i<100;i++)				//0이 아닌 배열 크기
			if(x[i] != 0)
				count++;

		for(int i=0; i<count; i++)			// 0이 아닌 인자들만
		{
			if(Xmax <= x[i])				// 배열의 최댓값 Xmax에 저장
				Xmax = x[i];
			if(Xmin > x[i])					// 최솟값 Xmin에 저장
				Xmin = x[i];
			if(Ymax <= y[i])				// 최댓값 Ymax
				Ymax = y[i];
			if(Ymin > y[i])					//최솟값 Ymin
				Ymin = y[i];
		}
		for(int i=0;i<100;i++)
		{
			polyX[i] = x[i];
			polyY[i] = y[i];
		}
		this.width = Xmax - Xmin;
		this.height = Ymax - Ymin;
		this.x = Xmin;
		this.y = Ymin;
		x3 = y3 = 0;
		first = true;
	}
	public Shape() 
	{
		this(25,25,25,15);
	}

	public void paint(Graphics g)
	{
		Color fg = g.getColor();
		g.setColor(Color.white);

		if (width2 < 0)
		{
			x2 = x2 + width2;
			width2 = Math.abs(width2);
		}
		if (height2 < 0)
		{
			y2 = y2 + height2;
			height2 = Math.abs(height2);
		}
		g.drawRect(x2, y2, width2, height2);
			
		u1 = new Rectangle(x2-point,y2-point,2*point, 2*point);
		u2 = new Rectangle(x2+width2-point,y2-point,2*point, 2*point);
		b1 = new Rectangle(x2-point,y2+height2-point,2*point, 2*point);
		b2 = new Rectangle(x2+width2-point,y2+height2-point,2*point, 2*point);

		if(selected) {
			g.setColor(Color.blue);
			g.fillRect(u1.x, u1.y, u1.width, u1.height);
			g.fillRect(u2.x, u2.y, u2.width, u2.height);
			g.fillRect(b1.x, b1.y, b1.width, b1.height);
			g.fillRect(b2.x, b2.y, b2.width, b2.height);
		}
		g.setColor(fg);
		x2 = x;
		y2 = y;
		width2 = width;
		height2 = height;
	}
	public void setSelected(boolean b) {
		selected = b;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	public void move(int x0, int y0)
	{
		if(polyX[0] != 0)
		{
			int count = 0;
			for(int i=0;i<100;i++)
				if(polyX[i] != 0)
					count++;
			if(first)
			{
				for(int i=0;i<count;i++)
				{
					xD[i] = polyX[i] - x0;
					yD[i] = polyY[i] - y0;
				}
				x3 = x - x0;
				y3 = y - y0;
				first = false;
			}
			for(int i=0;i<count;i++)
			{
				polyX[i] = x0 + xD[i];
				polyY[i] = y0 + yD[i];
			}
			x = x0 + x3;
			y = y0 + y3;
		}
		else
		{
			if(first) 
			{
				x3 = x - x0;
				y3 = y - y0;
				first = false;
			}

			x = x0 + x3;
			y = y0 + y3;
		}
	}
	

	public void resize(int xMouse, int yMouse, int type) {
		if(first) {
			first = false;
		}

		switch (type) {
			case  UPPER_LEFT:
				if(width < 0 && height >= 0)
					URight(xMouse, yMouse);
				else if(width >= 0 && height < 0)
					BLeft(xMouse, yMouse);
				else if(width < 0 && height < 0)
					BRight(xMouse, yMouse);
				else
					ULeft(xMouse, yMouse);
				break;
			case  UPPER_RIGHT:
				if(width < 0 && height >= 0)
					ULeft(xMouse, yMouse);
				else if(width >= 0 && height < 0)
					BRight(xMouse, yMouse);
				else if(width < 0 && height < 0)
					BLeft(xMouse, yMouse);
				else
					URight(xMouse, yMouse);
				break;
			case  BOTTOM_LEFT:
				if(width < 0 && height >= 0)
					BRight(xMouse, yMouse);
				else if(width >= 0 && height < 0)
					ULeft(xMouse, yMouse);
				else if(width < 0 && height < 0)
					URight(xMouse, yMouse);
				else
					BLeft(xMouse, yMouse);
				break;
			case  BOTTOM_RIGHT:
				if(width < 0 && height >= 0)
					BLeft(xMouse, yMouse);
				else if(width >= 0 && height < 0)
					URight(xMouse, yMouse);
				else if(width < 0 && height < 0)
					ULeft(xMouse, yMouse);
				else
					BRight(xMouse, yMouse);
				break;
		}
	}
	public void ULeft(int xMouse, int yMouse)
	{
		width = (width2 + x - xMouse);
		height = (height2 + y - yMouse);
		x = xMouse;
		y = yMouse;	
	}
	public void URight(int xMouse, int yMouse)
	{
		width = (width2 + xMouse - (x+width2));
		height = (height2 + y - yMouse);
		y = yMouse;
	}
	public void BLeft(int xMouse, int yMouse)
	{
		width = (width2 + x - xMouse);
		height = (height2 + yMouse - (y+height2));
		x = xMouse ;
	}
	public void BRight(int xMouse, int yMouse)
	{
		if(polyX[0] != 0)
		{
			float w0, h0, w1, h1;
			int count = 0;
			w0 = width;
			h0 = height;
			
			width = (width2 + xMouse - (x+width2));
			height = (height2 + yMouse - (y+height2));	
			
			w1 =  width/w0;
			h1 =  height/h0;

			for(int i=0;i<100;i++)
				if(polyX[i] != 0)
					count++;
			for(int i=0;i<count;i++)
			{
				polyX[i] = (int)((1-w1) * x + w1 * polyX[i]);
				polyY[i] = (int)((1-h1) * y + h1 * polyY[i]);
			}

		}
		else
		{
			width = (width2 + xMouse - (x+width2));
			height = (height2 + yMouse - (y+height2));	
		}
	}

	public int focused(int xMouse, int yMouse) {
		if(u1 != null && u1.contains(xMouse, yMouse)) {
			return UPPER_LEFT;
		} else if(u2 != null && u2.contains(xMouse, yMouse)) {
			return UPPER_RIGHT;
		} else if(b1 != null && b1.contains(xMouse, yMouse)) {
			return BOTTOM_LEFT;
		} else if(b2 != null && b2.contains(xMouse, yMouse)) {
			return BOTTOM_RIGHT;
		} else if (width < 0 || height < 0)
		{
			if (width2 < 0)
			{
				x2 = x2 + width2;
				width2 = Math.abs(width2);
			}
			if (height2 < 0)
			{
				y2 = y2 + height2;
				height2 = Math.abs(height2);
			}

			if(x2<=xMouse && xMouse<=x2+width2 && 
				y2<=yMouse&&yMouse<=y2+height2) {
				x2 = x;
				y2 = y;
				width2 = width;
				height2 = height;
				return MOVE;
			}

		}
		else if(x<=xMouse && xMouse<=x+width && y<=yMouse&&yMouse<=y+height) {
			return MOVE;
		}
		return NONE;
	}
}

