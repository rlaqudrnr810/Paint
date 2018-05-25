package Paint;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.* ;

public class View extends Canvas {
	protected Vector el;
	protected Drawable sOne;
	protected int fT, sItem = -1;
	protected int x, y, w, h, count = 0,count1=0;
	protected int polyX[] = new int[100];
	protected int polyY[] = new int[100];

	public View() {
		super();
		setSize(1000, 1000);
		setBackground(Color.white);
		el = new Vector(10, 10);							//벡터 생성
		sOne = null;
		fT = Shape.NONE;
		addMouseMotionListener(new MouseMotionHandler());
		addMouseListener(new MouseHandler());
		addKeyListener(new KeyHandler());
	}
	

	public void clear() {					//벡터의 상태를 모두 지워준다 (New기능)
		el.removeAllElements();
		repaint();
	}
	public void paint(Graphics g) 		
	{
		if(Main.getMode() == Main.POLYGON)
		{
			Graphics g2 = (Graphics)g;
			int i;
			for(i=0; i<count-1; i++)
			{	
				g2.setColor(ColorT.selectedColor());
				g2.drawLine(polyX[i], polyY[i], polyX[i+1], polyY[i+1]);
			}
		}

		int n = el.size();
		for(int i = 0; i < n; i++) {
			Drawable d = (Drawable) el.elementAt(i);
			d.paint(g);
		}
		if(sOne != null)
			sOne.paint(g);
	}

	public void add(Drawable d) {
		el.addElement(d);
	}

	public class MouseMotionHandler extends MouseMotionAdapter 
	{
		public void mouseDragged(MouseEvent e)
		{
			int mode = Main.getMode();
			if(mode != Main.NONE)
			{
				w = Math.abs(x - e.getX());
				h = Math.abs(y - e.getY());
				repaint();
			}
			else
			{
				int x2 = e.getX();
				int y2 = e.getY();
				int n = el.size();
				Drawable d = null;
				if(sOne == null)
				{
					for(int i =0; i < n; i++) {
						d = (Drawable) el.elementAt(i);
						fT = d.focused(x2, y2);
						if(fT > 0) {
							sOne = d;
							break;
						}
					}
				}
				if(sOne != null)
				{
					switch (fT) {
					  case  Shape.NONE:
						break;
					  case  Shape.MOVE:
						sOne.move(x2, y2);
						repaint();
						break;
					  case  Shape.UPPER_LEFT:
					  case  Shape.UPPER_RIGHT:
					  case  Shape.BOTTOM_LEFT:
					  case  Shape.BOTTOM_RIGHT:
						sOne.resize(x2, y2, fT);
						repaint();
						break;
					}
				}
			}
		}
	}
	public class KeyHandler extends KeyAdapter
	{
		public void keyReleased(KeyEvent e)
		{
			if(e.getKeyCode() == 27)
			{
				Drawable db = null;
				if(Main.getMode() == Main.POLYGON){
					db = new Polygon(polyX, polyY);
				}
				Main.setMode(Main.NONE);
				add(db);
				count = 0;
				for(int i=0;i<100;i++)
				{
					polyX[i] = 0;
					polyY[i] = 0;
				}
				repaint();
			}
		}
	}
	public class MouseHandler extends MouseAdapter 
	{
		public void mousePressed(MouseEvent e)
		{
			if(Main.getMode() == Main.POLYGON)
			{
				polyX[count] = e.getX();
				polyY[count] = e.getY();
				count++;
				repaint();			
			
			}
			else if(Main.getMode() != Main.NONE)
			{
				x = e.getX();
				y = e.getY();
			}
			else
			{
				int xNew = e.getX();
				int yNew = e.getY();
				int n = el.size();
				Drawable d = null;
				for(int i =0; i < n; i++) {
					d = (Drawable) el.elementAt(i);
					fT = d.focused(xNew, yNew);
					if( fT > 0) {
						if(sOne != null) {
							sOne.setSelected(false);
						}
						sOne = d;
						sOne.setSelected(true);
						sItem = i;
						repaint();
						return;
					} 
				}
				if(sOne != null) {
					sOne.setSelected(false);
					repaint();
				}
				sOne = null;
			}
		}
		public void mouseReleased(MouseEvent e)
		{
			if(Main.getMode() != Main.NONE && Main.getMode() != Main.POLYGON)
			{
				w = Math.abs(x - e.getX());
				h = Math.abs(y - e.getY());
				Drawable db = null;
				switch (Main.getMode()) {
					case  Main.NONE:
						return;
					case  Main.RECTANGLE:
						db = new Rect(x, y, w, h);
						break;
					case  Main.TRIANGLE:
						db = new Triangle(x, y, w, h);
						break;
					case  Main.LINE:
						db = new Line(x, y, e.getX()-x, e.getY()-y);
						break;
					case  Main.CIRCLE:
						db = new Circle(x, y, w, w);
						break;
					case  Main.RHOMBUS:
						db = new Rhombus(x, y, w, w);
						break;

				}
				Main.setMode(Main.NONE);
				if(db != null) {
					add(db);
					repaint();	
				}
			}		
			else
			{
				if(sOne != null) {
					sOne.setFirst(true);
				}
				fT = Shape.NONE;
			}
		}
	}
}