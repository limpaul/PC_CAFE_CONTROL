
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
public class DetailCost extends JFrame{
	private JLabel namec,userc,st,usingTimec,serviceTimec;
	public DetailCost() {
		super("PC�� �� ���");
		Container contain=getContentPane();
		JPanel p=new JPanel(new BorderLayout());
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();
		JPanel p3=new JPanel();
		JPanel p4=new JPanel();
		
		userInfo(p1);
	

		contain.add(p1, "North");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(500, 500);
		setSize(500, 500);
		setResizable(true);
		setVisible(true);
	}
	public void userInfo(JPanel panel) {
		panel.setLayout(new GridLayout(2, 2,10,5));
		panel.setBorder(new TitledBorder("���"));
		JLabel name=new JLabel(" * �̸�");namec=new JLabel("���� ");
		 JLabel userLevel=new JLabel(" * ȸ�����");userc=new JLabel("���� ");
		 labelCenter(name, namec, userLevel, userc);
		 panel.add(name);panel.add(namec);
		 panel.add(userLevel);panel.add(userc);
		 
	}
	public void userTimeInfo(JPanel panel) {
		panel.setLayout(new GridLayout(4, 4));
		JLabel enterTime=new JLabel(" * �Խǽð�");st=new JLabel("���� ");
		 JLabel startTime=new JLabel(" * ���۽ð�");
		 JLabel usingTime=new JLabel(" * ���ð�");usingTimec=new JLabel("���� ");
		 JLabel serviceTime=new JLabel(" * ���񽺽ð�");serviceTimec=new JLabel("���� ");
		 panel.add(enterTime);panel.add(st);
		 panel.add(startTime);panel.add(st);
		 panel.add(usingTime);panel.add(usingTimec);
		 panel.add(serviceTime);panel.add(serviceTimec);
		 
	}
	public void userMoneyInfo(JPanel panel) {
		
	}
	public void accountMoneyInfo(JPanel panel) {
		
	}
	public void labelCenter(JLabel label1,JLabel label2) {
		label1.setVerticalAlignment(SwingConstants.CENTER);
		label1.setHorizontalAlignment(SwingConstants.LEFT);
		label2.setVerticalAlignment(SwingConstants.CENTER);
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		
	}
	public void labelCenter(JLabel label1,JLabel label2,JLabel label3,JLabel label4) {
		label1.setVerticalAlignment(SwingConstants.CENTER);
		label1.setHorizontalAlignment(SwingConstants.LEFT);
		label2.setVerticalAlignment(SwingConstants.CENTER);
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label3.setVerticalAlignment(SwingConstants.CENTER);
		label3.setHorizontalAlignment(SwingConstants.LEFT);
		label4.setVerticalAlignment(SwingConstants.CENTER);
		label4.setHorizontalAlignment(SwingConstants.RIGHT);
	}
	
	public static void main(String[] args) {
		new DetailCost();
	}
}
