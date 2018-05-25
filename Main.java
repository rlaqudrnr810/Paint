package Paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.Scanner;

public class Main extends JFrame implements ActionListener {
	
	public static final int NONE = 0;			// ����� ���ڷ� ǥ��
	public static final int LINE = 1;
	public static final int RECTANGLE = 2;
	public static final int TRIANGLE = 3;
	public static final int CIRCLE = 4;
	public static final int RHOMBUS = 5;
	public static final int POLYGON = 6;

	public static int ob = NONE;					// ob�� 0
	Drawable co;							//drawable �������̽� ���, copy���
	public ColorT cs = new ColorT();


	JPanel jp = new JPanel();							//�г� �ʱ�ȭ
	BorderLayout bl = new BorderLayout();				//�߾��� �߽����� �������Ͽ� UI�� ��ġ�� ���ִ� 
	JPanel jptool = new JPanel();						// �޴��� �г� ����
	JPanel jpcanvas = new JPanel();						// �׸��� ��  �г� ����
	JPanel jpcolor = new JPanel();						// ���� ���� �г� ����
//	FlowLayout fl = new FlowLayout();					//��� ����

	JButton jbCir = new JButton("Circle");				// ��ư ����
	JButton jbPoly = new JButton("Polygon");
	JButton jbRect = new JButton("Rectangle");
	JButton jbTri = new JButton("Triangle");
	JButton jbRhom = new JButton("Rhombus");
	JButton jbLine = new JButton("Line");
	JButton jbDel = new JButton("Delete");
	JButton jbNew = new JButton("New");
	JButton jbCopy = new JButton("Copy");
	JButton jbPaste = new JButton("Paste");

	View canvas = new View();							//view��ü canvas ����

	public static void main(String[] args) {
		try {
			Main pic = new Main();
			pic.jbInit();							//jbInit() �޼ҵ� ����
		} catch (Exception e) {
			e.printStackTrace();					//���� ���
		}
	}

	private void jbInit() throws Exception {
		jp.setLayout(bl);
		jptool.setInputVerifier(null);							//jbtool �ʱ�ȭ
		//jptool.setLayout(fl);									// ��� ����

		jpcanvas.add(canvas);
		jpcolor.add(cs);
		this.getContentPane().add(jp, BorderLayout.CENTER);		//������ �߾ӿ� ��ġ
		jp.add(jptool, BorderLayout.NORTH);						//������ ���� ��ġ
		jp.add(jpcanvas, BorderLayout.CENTER);					//������ �߾ӿ� ��ġ
		jp.add(jpcolor, BorderLayout.SOUTH);					//������ ���ʿ� ��ġ
		jptool.add(jbLine, null);								//jbtool(�޴���)�� �׸� �߰�
		jptool.add(jbTri, null);
		jptool.add(jbRect, null);
		jptool.add(jbRhom, null);
		jptool.add(jbPoly, null);
		jptool.add(jbCir, null);
		jptool.add(jbCopy, null);
		jptool.add(jbPaste, null);
		jptool.add(jbNew, null);
		jptool.add(jbDel, null);

		jbLine.addActionListener(this);
		jbTri.addActionListener(this);
		jbRect.addActionListener(this);					//�� �׸� ���� �̺�Ʈ ������ ���
		jbRhom.addActionListener(this);
		jbPoly.addActionListener(this);
		jbCir.addActionListener(this);
		jbCopy.addActionListener(this);
		jbPaste.addActionListener(this);
		jbNew.addActionListener(this);
		jbDel.addActionListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000, 1000);
		setVisible(true);
	}

	public static int getMode() {						//������ ��� ���
		return ob;
	}

	public static void setMode(int m) {					//������ ��� ����
		ob = m;
	}

	public void actionPerformed(ActionEvent e) {		
		Object o = e.getSource();						//�޼ҵ带 ����Ͽ� �̺�Ʈ�����ʸ� �v���� ���𰡸� �Է� ��  �� �̺�Ʈ�� ����Ǵ� Ư�� �����̳��� ��� �Ӽ��� �����ü� �ִ�.
		if (o == jbRect)
			ob = RECTANGLE;
		else if (o == jbRhom)
			ob = RHOMBUS;
		else if (o == jbTri)
			ob = TRIANGLE;
		else if (o == jbLine)
			ob = LINE;
		else if (o == jbPoly)
			ob = POLYGON;
		else if (o == jbCir)
			ob = CIRCLE;
		else if (o == jbNew) {
			canvas.clear();
		} else if (o == jbCopy)
			co = canvas.sOne;
		else if (o == jbPaste) {
			Shape tpp = (Shape) co;
			switch (tpp.kindOfPic) {
			case 1:
				Line l = (Line) co;
				Line tppLine = new Line(l.x, l.y, l.width, l.height);
				tppLine.PicColor = l.PicColor;
				break;
			case 3:
				Rect r = (Rect) co;
				Rect tppRect = new Rect(r.x, r.y, r.width, r.height);
				tppRect.PicColor = r.PicColor;
				canvas.add((Drawable) tppRect);
				break;
			case 4:
				Triangle tr = (Triangle) co;
				Triangle tppTriangle = new Triangle(tr.x, tr.y, tr.width, tr.height);
				tppTriangle.PicColor = tr.PicColor;
				canvas.add((Drawable) tppTriangle);
				break;
			case 5:
				Circle oo = (Circle) co;
				Circle tppOval = new Circle(oo.x, oo.y, oo.width, oo.height);
				tppOval.PicColor = oo.PicColor;
				canvas.add((Drawable) tppOval);
				break;
			case 6:
				Rhombus Do = (Rhombus) co;
				Rhombus tppDiamond = new Rhombus(Do.x, Do.y, Do.width, Do.height);
				tppDiamond.PicColor = Do.PicColor;
				canvas.add((Drawable) tppDiamond);
			case 7:
				Polygon pg = (Polygon) co;
				Polygon tppPolyGon = new Polygon(tpp.polyX, tpp.polyY);
				tppPolyGon.PicColor = pg.PicColor;
				canvas.add((Drawable) tppPolyGon);
				break;
			}
			canvas.repaint();
		} else if (o == jbDel)
			if (canvas.el.size() > 0 && canvas.sItem > -1) {
				canvas.fT = -1;
				Drawable d = null;
				d = (Drawable) canvas.el.elementAt(canvas.sItem);
				d.setSelected(false);
				canvas.el.remove(canvas.sItem);
				canvas.sItem = -1;
				canvas.repaint();
			}
	}
}
