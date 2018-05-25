package Paint;

import java.awt.Graphics;

interface  Drawable {
	void paint(Graphics g);
	void move(int x, int y);
	void resize(int x, int y, int type);
	int focused(int x, int y);
	void setFirst(boolean first);
	void setSelected(boolean b);
}

