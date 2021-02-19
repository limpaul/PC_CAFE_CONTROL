

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoneyAdd extends JFrame {
	JTabbedPane tPane = new JTabbedPane();
	JButton btn1 = new JButton();
	JLabel logo;
	int money=0;
	JButton leastMoney;
	public MoneyAdd() {
		Container contain = getContentPane();// ����,�߰�
		JPanel nPanel = new JPanel();
		nPanel.setLayout(new GridLayout(2, 1));

		JLabel lastSeatInfo = new JLabel("���� ĸ���� PC�濡 ���Ű� ȯ���մϴ�  ����¼��� 12�� ���¼��� 12 ��");
		JLabel logo = new JLabel("�ȳ��ϼ���. ����� ������� �����ϼ���");
		labelCenter(lastSeatInfo);
		labelCenter(logo);
		nPanel.add(lastSeatInfo);
		nPanel.add(logo);

		JPanel p = new JPanel();// 1�� 3��
		p.setLayout(new GridLayout(1, 3));
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();

		leftDesign(p1);
		centerDesign(p2);
		rightDesign(p3);
		p.add(p1);
		p.add(p2);
		p.add(p3);

		contain.add(nPanel, "North");
		contain.add(p, "Center");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1400, 600);
		setResizable(true);
		setVisible(true);
	}

	public void leftDesign(JPanel left) {
		MoneyAddEvent mEvent=new MoneyAddEvent();
		tPane.setTabPlacement(JTabbedPane.TOP);
		left.add(tPane);
		
		JPanel sub_panel_user = new JPanel();
		for (int i = 1; i <= 10; i++) {
			JButton btn = new JButton(userMoneyChange(i));
			sub_panel_user.add(btn);
			btn.addActionListener(mEvent);
		}
		JPanel sub_panel_sUser = new JPanel();
		for (int i = 1; i <= 10; i++) {
			JButton btn1 = new JButton(studentMoneyChange(i));
			sub_panel_sUser.add(btn1);
			btn1.addActionListener(mEvent);
		}
		JPanel sub_panel_bUser = new JPanel();
		for (int i = 1; i <= 10; i++) {
			JButton btn2 = new JButton(bUserMoneyChange(i));
			sub_panel_bUser.add(btn2);
			btn2.addActionListener(mEvent);
		}
		
		sub_panel_user.setLayout(new GridLayout(5, 2,10,10));
		sub_panel_sUser.setLayout(new GridLayout(5, 2,10,10));
		sub_panel_bUser.setLayout(new GridLayout(5, 2,10,10));
		tPane.add(" ȸ�� ", sub_panel_user);
		tPane.add(" �л� ", sub_panel_sUser);
		tPane.add(" ��ȸ�� ", sub_panel_bUser);
	}

	public void centerDesign(JPanel center) {
		MoneyAddEvent mEvent=new MoneyAddEvent();
		center.setLayout(new BorderLayout());
		JPanel sub_panel=new JPanel();
		sub_panel.setLayout(new GridLayout(5, 2,10,10));
		sub_panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		String[] arr= {"����","��ȭ��ǰ��","������ȭ��ǰ��","ƾĳ��","��ĳ��","���۱���Ʈ","���ǸӴ�","���׸Ӵ�","�۴�ī��","������īTV��ǳ��"};
		for(int i=0;i<arr.length;i++) {
			JButton btn=new JButton(arr[i]);
			btn.addActionListener(mEvent);
			sub_panel.add(btn);
			center.add(sub_panel, "Center");
		}
		 logo=new JLabel("���� ���� . ���� ��� : 0��");
		labelCenter(logo);
		center.add(logo, "North");
	
	}

	public void rightDesign(JPanel right) {
		right.setLayout(new GridLayout(3,1));
		JPanel sub_panel=new JPanel();
		sub_panel.setLayout(new GridLayout(2, 1));
		sub_panel.add(new JLabel(new ImageIcon("howto.PNG")));
		
		
		JPanel sub_panel_Center=new JPanel();
		sub_panel_Center.setLayout(new GridLayout(2, 3,5,5));
		String[] arr= {"ȸ������","��ȸ������","�¼�����","�ſ�ī��","T�Ӵ�ī��","ĳ�ú�ī��"};
		for(int i=0;i<arr.length;i++) {
			JButton btn=new JButton(arr[i]);
			sub_panel_Center.add(btn);
			sub_panel.add(sub_panel_Center);
			btn.addActionListener(new MoneyAddEvent());
		}
		
		JPanel sub_panel_South=new JPanel();
		leastMoney=new JButton("�ܵ���ȯ");
		leastMoney.addActionListener(new MoneyAddEvent());
		sub_panel_South.add(leastMoney);
		right.add(sub_panel_South);
		right.add(sub_panel);
		right.add(sub_panel_Center);
		right.add(sub_panel_South);
		
		
	}

	public String userMoneyChange(int time) {
		String returnValue = "";
		if (time == 1) {
			return "���� 1,000�� 01:00";
		}
		if (time == 2) {
			return "���� 30,000�� 30:00";
		}
		if (time == 3) {
			return "���� 2,000�� 02:00";
		}
		if (time == 4) {
			return "���� 50,000�� 50:00";
		}
		if (time == 5) {
			return "���� 3,000�� 03:00";
		}
		if (time == 7) {
			return "���� 5,000 05:00";
		}
		if(time==9) {
			return "���� 10,000 10:00";
		}
		if(time==6||time==8||time==10) {
			returnValue="";
		}
		return returnValue;
	}
	public String studentMoneyChange(int time) {
		String returnValue = "";
		if (time == 1) {
			return "���� 1,000�� 02:00";
		}
		if (time == 2) {
			return "���� 30,000�� 60:00";
		}
		if (time == 3) {
			return "���� 2,000�� 04:00��";
		}
		if (time == 4) {
			return "���� 50,000�� 100:00";
		}
		if (time == 5) {
			return "���� 3,000�� 06:00";
		}
		if (time == 7) {
			return "���� 5,000 10:00";
		}
		if(time==9) {
			return "���� 10,000 20:00";
		}
		if(time==6||time==8||time==10) {
			returnValue="";
		}
		return returnValue;
	}
	public String bUserMoneyChange(int time) {
		String returnValue = "";
		if (time == 1) {
			return "���� 1,000�� 00:30";
		}
		if (time == 2) {
			return "���� 30,000�� 15:00";
		}
		if (time == 3) {
			return "���� 2,000�� 01:00��";
		}
		if (time == 4) {
			return "���� 50,000�� 25:00";
		}
		if (time == 5) {
			return "���� 3,000�� 01:30";
		}
		if (time == 7) {
			return "���� 5,000 02:30";
		}
		if(time==9) {
			return "���� 10,000 05:00";
		}
		if(time==6||time==8||time==10) {
			returnValue="";
		}
		return returnValue;
	}
	public void labelCenter(JLabel label) {
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Serif", Font.BOLD, 30));
		label.setForeground(Color.BLACK);

	}
	
	
	class MoneyAddEvent implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			String cmd=e.getActionCommand();
			if(cmd.equals("����")) {
				String money=JOptionPane.showInputDialog(null, "���� �Է�");
				changeMoney(money);
			}
			if(cmd.equals("���� 1,000�� 01:00")) {
				if(money!=0) {
					String dbId=JOptionPane.showInputDialog(null, "���̵� �Է����ּ���");
					if(UserDAO.addTime(dbId, 60, 1000)==true) {
						JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ� ��ſ� �ð� �Ǽ���");
						money-=1000;
						logo.setText("���� ���� . ���� ��� : "+money+"��");
					}else {
						JOptionPane.showMessageDialog(null, "���̵� �ùٸ��� �ʽ��ϴ�");
					}
					//db�� ���� �ð��� �߰����ش�
				}else {
					JOptionPane.showMessageDialog(null, "���� �־��ּ���");
				}
				
			}
			if(cmd.equals("���� 2,000�� 02:00")) {
				System.out.println("2");
			}
			if(cmd.equals("�ܵ���ȯ")) {
				money=0;
				logo.setText("���� ���� . ���� ��� : "+money+"��");
			}
			if(cmd.equals("ȸ������")) {
				new UserAdd(MoneyAdd.this);
			}
		
		}
		public void changeMoney(String omoney) {
			money+=Integer.parseInt(omoney);
			logo.setText("���� ���� . ���� ��� : "+money+"��");
			
			
		}
		
	}
	
	public static void main(String[] args) {
		new MoneyAdd();
	}
}
