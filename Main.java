package Paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.Scanner;

public class Main extends JFrame implements ActionListener {
	
	public static final int NONE = 0;			// 각기능 숫자로 표현
	public static final int LINE = 1;
	public static final int RECTANGLE = 2;
	public static final int TRIANGLE = 3;
	public static final int CIRCLE = 4;
	public static final int RHOMBUS = 5;
	public static final int POLYGON = 6;

	public static int ob = NONE;					// ob에 0
	Drawable co;							//drawable 인터페이스 사용, copy사용
	public ColorT cs = new ColorT();


	JPanel jp = new JPanel();							//패널 초기화
	BorderLayout bl = new BorderLayout();				//중앙을 중심으로 동서남북에 UI를 배치할 수있는 
	JPanel jptool = new JPanel();						// 메뉴바 패널 생성
	JPanel jpcanvas = new JPanel();						// 그리는 판  패널 생성
	JPanel jpcolor = new JPanel();						// 색깔 고르는 패널 생성
//	FlowLayout fl = new FlowLayout();					//가운데 정렬

	JButton jbCir = new JButton("Circle");				// 버튼 생성
	JButton jbPoly = new JButton("Polygon");
	JButton jbRect = new JButton("Rectangle");
	JButton jbTri = new JButton("Triangle");
	JButton jbRhom = new JButton("Rhombus");
	JButton jbLine = new JButton("Line");
	JButton jbDel = new JButton("Delete");
	JButton jbNew = new JButton("New");
	JButton jbCopy = new JButton("Copy");
	JButton jbPaste = new JButton("Paste");

	View canvas = new View();							//view객체 canvas 생성

	public static void main(String[] args) {
		try {
			Main pic = new Main();
			pic.jbInit();							//jbInit() 메소드 실행
		} catch (Exception e) {
			e.printStackTrace();					//에러 출력
		}
	}

	private void jbInit() throws Exception {
		jp.setLayout(bl);
		jptool.setInputVerifier(null);							//jbtool 초기화
		//jptool.setLayout(fl);									// 가운데 정렬

		jpcanvas.add(canvas);
		jpcolor.add(cs);
		this.getContentPane().add(jp, BorderLayout.CENTER);		//보드의 중앙에 위치
		jp.add(jptool, BorderLayout.NORTH);						//보드의 남쪽 위치
		jp.add(jpcanvas, BorderLayout.CENTER);					//보드의 중앙에 위치
		jp.add(jpcolor, BorderLayout.SOUTH);					//보드의 남쪽에 위치
		jptool.add(jbLine, null);								//jbtool(메뉴바)에 항목 추가
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
		jbRect.addActionListener(this);					//각 항목에 대한 이벤트 리스너 등록
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

	public static int getMode() {						//현재의 모드 얻기
		return ob;
	}

	public static void setMode(int m) {					//현재의 모드 설정
		ob = m;
	}

	public void actionPerformed(ActionEvent e) {		
		Object o = e.getSource();						//메소드를 사용하여 이벤트리스너를 틍록한 무언가를 입력 시  그 이벤트가 실행되는 특정 컨테이너의 모든 속성을 가져올수 있다.
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
