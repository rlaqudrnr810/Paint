package Paint;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ColorT extends Canvas {
	protected static final Dimension size = new Dimension(16, 1); // 16���� 1��
	protected static final Color[] colors = { // ����� ����
			new Color(0, 0, 0), new Color(128, 0, 0), new Color(0, 128, 0), new Color(128, 128, 0),
			new Color(0, 0, 128), new Color(128, 0, 128), new Color(0, 128, 128), new Color(192, 192, 192),
			new Color(128, 128, 128), new Color(255, 0, 0), new Color(0, 255, 0), new Color(255, 255, 0),
			new Color(0, 0, 255), new Color(255, 0, 255), new Color(0, 255, 255), new Color(255, 255, 255) };

	protected JFrame cf; // �� �޴��� ������
	protected int c = 50; // �� �޴� ũ�� ����
	public static Color selectec;

	public ColorT() {
		cf = new JFrame("Color table"); // �� ���� ��ü ����
		setSize(c * size.width, c * size.height); // ������ ����
		cf.getContentPane().add(this); //
		cf.pack();
		cf.show();
		selectec = colors[0];

		addMouseListener(new MouseAdapter() { // ���� ����
			public void mousePressed(MouseEvent e) {
				Rectangle r = new Rectangle();
				for (int y = 0; y < size.height; y++) {
					for (int x = 0; x < size.width; x++) {
						r.setBounds(x * c, y * c, c, c);
						if (r.contains(e.getPoint()))
							selectec = colors[y * size.height + x];
					}
				}
				repaint();
			}
		});
	}

	public static Color selectedColor() { // ���õ� �� ��ȯ
		return selectec;
	}

	protected void drawTable(Graphics g) {
		for (int y = 0; y < size.height; y++) {
			for (int x = 0; x < size.width; x++) {
				g.setColor(colors[y * size.height + x]);
				g.fillRect(x * c, y * c, c, c);
				if (selectec == colors[y * size.height + x]) {
					g.setXORMode(Color.white);
					g.drawRect(x * c + 1, y * c + 1, c - 2, c - 2);
					g.setPaintMode();
				}
			}
		}
	}
}