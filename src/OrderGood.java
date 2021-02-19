

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class OrderGood extends JDialog {

	private JButton top10Button1, top10Button2, top10Button3, top10Button4, top10Button5;
	private JButton all, eat, ramen, setMenu, gansick, cafe, gaga, pushSearch;
	private JTextField search;
	private JTable table;
	private JButton btnPlus, btnMinus;
	private JScrollPane scroll;
	private JLabel orderValue;
	private ButtonGroup bgp1 = new ButtonGroup();
	private ButtonGroup bgp2 = new ButtonGroup();
	private JRadioButton cash, card, trafficCard, daily;
	private JRadioButton frontCash, backCash;
	private String[] moneyValue = { "���ұݾ� ����", "1000��", "2000��", "3000��", "4000��", "5000��", "10000��", "50000��" };
	private JComboBox<String> cb = new JComboBox<String>(moneyValue);
	private JTextArea need;// �ֹ� ��û���� �Է�
	private JButton orderClick;// �ֹ� ��ư Ŭ��
	private Vector<String> column = new Vector<String>();
	private Vector<OrderGood> moneyManage = new Vector<OrderGood>();
	private DefaultTableModel model; // JTable �߰� ����

	private String name;// ��ǰ�̸�
	private int money;// ��ǰ����
	private int count;// ��ǰ����

	private String num;
	// Ŭ���̾�Ʈ �ʵ�
	Socket cSocket;
	BufferedReader br;
	PrintWriter pw;
	String data;

	String[] allText = { "��ġ����", "��ġ����", "������", "�ܹ���", "�ε��", "�����", "�ֽ�", "���Ƕ��̽�", "���", "�����", "ä��", "������" };
	JPanel p1;
	JButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12;
	JButton[] btnArr = { btn1, btn2, btn3, btn4, btn4, btn6, btn7, btn8, btn9, btn10, btn11, btn12 };

	String[] foodAll = { "��ü/bokum.jpg", "��ü/chamcibokum.jpg", "��ü/ddukk.jpg", "��ü/hamberger.jpg", "��ü/indea.jpg",
			"��ü/jinramen.jpg", "��ü/juice.jpg", "��ü/omelrais.jpg", "��ü/ramen.jpg", "��ü/samyangramen.jpg",
			"��ü/vegetal.png", "��ü/yukgaeramen.jpg" };

	OrderEvent orderEvent = new OrderEvent();
	JFrame frame;
	public OrderGood(String name, int money, int count) {
		this.name = name;
		this.money = money;
		this.count = count;
	}

	public OrderGood(JFrame frame,String num) {
		super(frame,num,true);
		this.num=num;
		Container contain = getContentPane();
		JPanel panelModel = new JPanel();
		JPanel panelList = new JPanel();

		setSize(1300, 1000);
		setLocation(500, 10);

		orderGoodModel(panelModel);
		OrderList(panelList);

		
		contain.add(panelModel, "North");
		contain.add(panelList, "Center");
		// setResizable(false);
		setResizable(false);
		setVisible(true);
	}

	public void orderGoodModel(JPanel panel) {
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new GridLayout(1, 5));
		JPanel pick = new JPanel();
		pick.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pick.setLayout(new GridLayout(1, 9, 10, 10));

		top10Button1 = new JButton(new ImageIcon("��ü/bokum.jpg"));
		subPanel.add(top10Button1);

		top10Button2 = new JButton(new ImageIcon("��ü/chamcibokum.jpg"));
		subPanel.add(top10Button2);

		top10Button3 = new JButton(new ImageIcon("��ü/ddukk.jpg"));
		subPanel.add(top10Button3);

		top10Button4 = new JButton(new ImageIcon("��ü/hamberger.jpg"));
		subPanel.add(top10Button4);

		top10Button5 = new JButton(new ImageIcon("��ü/jajangmen.jpg"));
		subPanel.add(top10Button5);

		all = new JButton("��ü");
		eat = new JButton("�Ļ��");
		ramen = new JButton("����");
		setMenu = new JButton("��Ʈ�޴�");
		gansick = new JButton("����");
		cafe = new JButton("CAFE");
		gaga = new JButton("���ڷ�");
		pushSearch = new JButton("�˻�");
		search = new JTextField();

		pick.add(all);
		pick.add(eat);
		pick.add(ramen);
		pick.add(setMenu);
		pick.add(gansick);
		pick.add(cafe);
		pick.add(gaga);
		pick.add(search);
		pick.add(pushSearch);

		all.addActionListener(orderEvent);
		eat.addActionListener(orderEvent);
		ramen.addActionListener(orderEvent);
		setMenu.addActionListener(orderEvent);
		gansick.addActionListener(orderEvent);
		cafe.addActionListener(orderEvent);
		gaga.addActionListener(orderEvent);
		pushSearch.addActionListener(orderEvent);

		JLabel label = new JLabel("ĸ���� ���ʼ���PC���Դϴ�!");
		panel.add(label, "North");
		panel.add(subPanel, "Center");
		panel.add(pick, "South");

	}

	public void OrderList(JPanel list) {

		list.setLayout(new BorderLayout());
		list.setBorder(BorderFactory.createEmptyBorder(10, 10, 30, 10));
		p1 = new JPanel();
		p1.setLayout(new GridLayout(3, 6, 5, 5));
		p1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		// ---------------------------------------------------------------------
		for (int i = 0; i < btnArr.length; i++) {
			btnArr[i] = new JButton(new ImageIcon(foodAll[i]));
			p1.add(btnArr[i]);
		}
		for (int i = 0; i < btnArr.length; i++) {
			btnArr[i].setIcon(new ImageIcon(foodAll[i]));
			btnArr[i].setText(allText[i]);
			btnArr[i].addActionListener(orderEvent);
		}
		/////// ---------------------------------------------------
		JPanel p2 = new JPanel();
		JPanel p2_1 = new JPanel();
		JPanel sub_panel = new JPanel();
		JPanel addMinus_panel = new JPanel();

		p2.setLayout(new BorderLayout());// ��ü layout p2 �̴�
		p2_1.setLayout(new BorderLayout());// �����г�

		JLabel mokrok = new JLabel("�ֹ����");
		sub_panel.setLayout(new BorderLayout());
		addMinus_panel.setLayout(new GridLayout(1, 2));
		//

		column.addElement("��ǰ���");
		column.addElement("����");
		column.addElement("����");
		model = new DefaultTableModel(column, 0);

		table = new JTable(model);
		table.setPreferredSize(new Dimension(200, 380));
		scroll = new JScrollPane(table);

		btnPlus = new JButton("�߰�");
		btnMinus = new JButton("����");
		sub_panel.add(scroll, "Center");
		addMinus_panel.add(btnPlus);
		addMinus_panel.add(btnMinus);
		sub_panel.add(addMinus_panel, "South");

		btnPlus.addActionListener(new OrderEvent2());
		btnMinus.addActionListener(new OrderEvent2());

		orderValue = new JLabel("��ü�ݾ�: 0��");
		orderValue.setFont(new Font("���ü", Font.BOLD, 20));
		p2_1.add(mokrok, "North");
		p2_1.add(sub_panel, "Center");
		p2_1.add(orderValue, "South");

		// p2_2 �� ����
		JPanel p2_2 = new JPanel();
		p2_2.setLayout(new GridLayout(2, 1));

		JPanel p2_2_1 = new JPanel();
		p2_2_1.setLayout(new GridLayout(1, 5));

		// ���� ��ư�߰�
		JLabel l1 = new JLabel("��������");
		cash = new JRadioButton("����");
		card = new JRadioButton("�ſ�ī��");
		trafficCard = new JRadioButton("����ī��");
		daily = new JRadioButton("���ױ�");
		bgp1.add(cash);
		bgp1.add(card);
		bgp1.add(trafficCard);
		bgp1.add(daily);
		cash.addActionListener(new OrderEvent2());
		card.addActionListener(new OrderEvent2());
		trafficCard.addActionListener(new OrderEvent2());
		daily.addActionListener(new OrderEvent2());
		p2_2_1.add(l1);
		p2_2_1.add(cash);
		p2_2_1.add(card);
		p2_2_1.add(trafficCard);
		p2_2_1.add(daily);
		JPanel p2_2_2 = new JPanel();
		p2_2_2.setLayout(new GridLayout(1, 3));

		// ���ұݾ� JList �� textArea �� ���� �ĺ� �����߰�
		frontCash = new JRadioButton("����");
		backCash = new JRadioButton("�ĺ�");
		bgp2.add(frontCash);
		bgp2.add(backCash);
		p2_2_2.add(cb);
		p2_2_2.add(frontCash);
		p2_2_2.add(backCash);
		p2_2.add(p2_2_1);
		p2_2.add(p2_2_2);

		JPanel p2_3 = new JPanel();
		p2_3.setLayout(new BorderLayout());
		need = new JTextArea("�ֹ��� ��û���� �Է�", 5, 20);
		orderClick = new JButton("��ǰ�ֹ�");
		orderClick.addActionListener(new OrderEvent2());
		p2_3.add(need, "Center");
		p2_3.add(orderClick, "East");

		p2.add(p2_1, "North");
		p2.add(p2_2, "Center");
		p2.add(p2_3, "South");
		list.add(p1, "Center");
		list.add(p2, "East");
		all.addActionListener(orderEvent);

	}

	////////////////////////////////////////////// �̺�Ʈ
	////////////////////////////////////////////// Ŭ����/////////////////////////////////
	class OrderEvent implements ActionListener {
		JButton btnA, btnB, btnC, btnD, btnE, btnG, btnH, btnI, btnJ, btnK, btnL, btnM;
		JButton[] btnArr2 = { btnA, btnB, btnC, btnD, btnE, btnG, btnH, btnI, btnJ, btnK, btnL, btnM };
		FoodSearch sDialog = null;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String cmd = e.getActionCommand();

			if (cmd.equals("��ü")) {
				btnAll();

			}
			if (cmd.equals("�Ļ��")) {
				sicksaryu();
			}
			if (cmd.equals("����")) {
				ramenryu();

			}
			if (cmd.equals("��Ʈ�޴�")) {
				set();
			}
			if (cmd.equals("����")) {
				gansick();
			}
			if (cmd.equals("CAFE")) {
				cafe();
			}
			if (cmd.equals("���ڷ�")) {
				gaga();
			}
			if (cmd.equals("�˻�")) {
				String searchData = search.getText();
				sDialog = new FoodSearch(searchData, frame);
				sDialog.setVisible(true);
			}
			if (!cmd.equals("��ü") && !cmd.equals("�Ļ��") && !cmd.equals("����") && !cmd.equals("��Ʈ�޴�")
					&& !cmd.equals("����") && !cmd.equals("CAFE") && !cmd.equals("���ڷ�") && !cmd.equals("�˻�")) {

				FoodValue returnValue = new FoodValue().returnValue(cmd);

				String name = returnValue.getName();
				int value = returnValue.getValue();

				String[] arr = { name, Integer.toString(value), Integer.toString(1) };
				model.addRow(arr);

			}

		}

		public void btnAll() {
			for (int i = 0; i < btnArr.length; i++) {
				btnArr[i].setIcon(new ImageIcon(foodAll[i]));
				btnArr[i].setText(allText[i]);

			}
		}

		public void sicksaryu() {

			String[] sicksa = { "��ü/bokum.jpg", "��ü/chamcibokum.jpg", "��ü/ddukk.jpg", "��ü/hamberger.jpg",
					"��ü/indea.jpg", "��ü/juice.jpg", "��ü/omelrais.jpg", "��ü/vegetal.png", "��ü/��ġ�Ǹ�.jpg", "��ü/�ٴ�.jpg",
					"��ü/�ٺ�ť.jpg", "��ü/�δ����.jpg" };
			String[] arr = { "��ġ����", "��ġ����", "������", "�ܹ���", "�ε��", "�����", "���Ƕ��̽�", "ä��", "��ġ", "�ٴ�", "�ٺ�ť", "�δ����" };
			for (int i = 0; i < btnArr.length; i++) {
				btnArr[i].setIcon(new ImageIcon(sicksa[i]));// ��ư ��������
				btnArr[i].setText(arr[i]);// ���� �ٲ�

			}

		}

		public void ramenryu() {
			String[] arr = { "�����", "���", "�����", "������", "�Ϻ����", "�������", "�нĶ��", "�Ұ����", "���߶��", "�ᳪ�����", "�Ŷ��",
					"�Ϻ����2" };
			String ramenryu[] = { "����/jinramen.jpg", "����/ramen.jpg", "����/samyangramen.jpg", "����/yukgaeramen.jpg",
					"����/������.jpeg", "����/�������.jpg", "����/�������.jpg", "����/��ξ�.jpeg", "����/�����.jpg", "����/�����.jpg",
					"����/�Ŷ��.jpg", "����/�Ϻ�.jpg" };

			for (int i = 0; i < arr.length; i++) {
				btnArr[i].setIcon(new ImageIcon(ramenryu[i]));
				btnArr[i].setText(arr[i]);

			}

		}

		public void set() {
			for (int i = 0; i < btnArr.length; i++) {
				btnArr[i].setIcon(new ImageIcon(""));
				btnArr[i].setText("�غ���");
			}
		}

		public void gansick() {
			String[] gansick = { "����/�κ���.png", "����/����.jpg", "����/��¡��.jpg", "����/¯��.jpg", "����/��īĨ.jpg", "����/������Ĩ.png",
					"����/�˵��.jpg", "����/���ڸ�.jpg", "����/��ũ.jpg", "�غ���", "�غ���", "�غ���" };
			String[] arr = { "�κ���", "����", "��¡��", "���¯", "��īĨ", "������Ĩ", "�˵��", "���ڸ�", "", "", "", "" };
			for (int i = 0; i < gansick.length; i++) {
				btnArr[i].setIcon(new ImageIcon(gansick[i]));
				btnArr[i].setText(arr[i]);
			}
		}

		public void cafe() {
			String[] cafe = { "Ŀ��/�����.jpg", "Ŀ��/�ƽ�.jpg", "Ŀ��/�ٴҶ�.jpg", "Ŀ��/�߶��.jpg", "Ŀ��/��Ŀ��.gif", "Ŀ��/�Ƹ޸�ī��.jpg",
					"Ŀ��/�Ϻ�.jpg", "Ŀ��/���ʷ���Ŀ��.jpg", "Ŀ��/���ھ�.jpg", "Ŀ��/��������.png", "Ŀ��/����Ŀ��.jpg", "Ŀ��/ȣ��Ŀ��.jpg" };
			String[] arr = { "�����", "�ƽ�", "�ٴҶ�", "�߶��", "��", "�Ƹ޸�ī��", "�Ϻ�", "���ʷ���", "���ھ�", "��������", "����", "ȣ��" };
			for (int i = 0; i < cafe.length; i++) {
				btnArr[i].setIcon(new ImageIcon(cafe[i]));
				btnArr[i].setText(arr[i]);
			}
		}

		public void gaga() {
			String[] gaga = { "����/mini.png", "����/����.jpg", "����/������.gif", "����/����Ĩ.png", "����/������.jpg", "����/�����佺.jpg",
					"����/�κΰ���.jpg", "����/����Ĩ.png", "����/�Ұ���.jpg", "����/������.jpg", "����/����������.jpg", "����/¯��.jpg" };
			String[] arr = { "�̴�", "����", "������", "����Ĩ", "������", "�����佺", "�κΰ���", "����Ĩ", "�Ұ���", "������", "�κΰ���", "¯��" };
			for (int i = 0; i < gaga.length; i++) {
				btnArr[i].setIcon(new ImageIcon(gaga[i]));
				btnArr[i].setText(arr[i]);
			}
		}

	}

	// -------------------------------���ο� ���� Ŭ����
	// ���-----------------------------------------
	class OrderEvent2 implements ActionListener {
		int mount = 0;
		String[] arr = { "����", "����", "����", "����", "����" };

		public void actionPerformed(ActionEvent x) {
			int m = 0;
			String cmd = x.getActionCommand();

			if (cmd.equals("��ü")) {
				for (int i = 0; i < btnArr.length; i++) {
					btnArr[i].addActionListener(this);
				}
			}

			if (cmd.equals("�Ļ��")) {
				for (int i = 0; i < btnArr.length; i++) {
					btnArr[i].addActionListener(this);
				}
			}
			if (cmd.equals("����")) {
				for (int i = 0; i < btnArr.length; i++) {
					btnArr[i].addActionListener(this);
				}
			}
			if (cmd.equals("��Ʈ�޴�")) {
				for (int i = 0; i < btnArr.length; i++) {
					btnArr[i].addActionListener(this);
				}
			}
			if (cmd.equals("����")) {
				for (int i = 0; i < btnArr.length; i++) {
					btnArr[i].addActionListener(this);
				}
			}
			if (cmd.equals("CAFE")) {
				for (int i = 0; i < btnArr.length; i++) {
					btnArr[i].addActionListener(this);
				}
			}
			if (cmd.equals("���ڷ�")) {
				for (int i = 0; i < btnArr.length; i++) {
					btnArr[i].addActionListener(this);
				}
			}
			int mountOfMoney = 0;
			if (cmd.equals("�߰�")) {/////////////// ��� ��ǰ �߰� ������
				try {

					int row = table.getSelectedRow();

					String name = (String) table.getValueAt(row, 0); // ��ǰ�̸�
					int money = Integer.parseInt((String) table.getValueAt(row, 1)); // ��
					int count = Integer.parseInt((String) table.getValueAt(row, 2)); // Ƚ��

					moneyManage.add(new OrderGood(name, money, count)); // vector �� �� �߰�
					orderValue.setFont(new Font("���", Font.BOLD, 20));// ���� ����

					for (int i = 0; i < moneyManage.size(); i++) {
						mountOfMoney += moneyManage.get(i).getMoney();
					}
					orderValue.setText("��ü�ݾ�: " + mountOfMoney + "��");
				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "��ǰ�� �����Ѵ��� �߰���ư�� �����ּ���");

				}
			}
			if (cmd.equals("����")) {

				if (table.getSelectedRow() == -1) {
					return;

				} else {
					int row = table.getSelectedRow();// JTable���� ������ ��
					String name = (String) table.getValueAt(row, 0); // �̸�
					for (int i = 0; i < moneyManage.size(); i++) {
						if (moneyManage.get(i).getName().equals(name)) {
							moneyManage.remove(i); // ��� Vector ���� ������
						}
					}

					for (int i = 0; i < moneyManage.size(); i++) {
						mountOfMoney += moneyManage.get(i).getMoney(); // �׸��� ���������� ���϶�
					}
					orderValue.setText("��ü�ݾ�: " + mountOfMoney + "��");// ������
					model.removeRow(table.getSelectedRow());

				}
			}
			try {
				if (cmd.equals("��ǰ�ֹ�")) {
					String typeOfBuy = null;

					for (int i = 0; i < moneyManage.size(); i++) {
						System.out.println("������ǰ�̸�" + moneyManage.get(i).getName());
						m += moneyManage.get(i).getMoney();
						arr[i] = moneyManage.get(i).getName();
					}

					String clientMoney = cb.getSelectedItem().toString();
					if (!cash.isSelected() && !card.isSelected() && !trafficCard.isSelected() && !daily.isSelected()) {
						JOptionPane.showMessageDialog(null, "�������� �������ּ���", "���", JOptionPane.WARNING_MESSAGE);
					} else if (clientMoney.equals("���ұݾ� ����")) {
						JOptionPane.showMessageDialog(null, "���ұݾ��� �������ּ���", "���", JOptionPane.WARNING_MESSAGE);
					} else if (Integer.parseInt(clientMoney.replace("��", "")) < m) {
						System.out.println(Integer.parseInt(clientMoney.replace("��", "")));

						JOptionPane.showMessageDialog(null, "��ǰ������ �� �����ϴ�", "���", JOptionPane.WARNING_MESSAGE);

					} else if (moneyManage.size() > 5) {
						JOptionPane.showMessageDialog(null, " 6�� �̻� ����Ŵ�ϴ�", "���", JOptionPane.WARNING_MESSAGE);
					} else {

						if (cash.isSelected()) {
							System.out.println("��������:����");
							typeOfBuy = cash.getText();
						} else if (card.isSelected()) {
							System.out.println("��������:ī��");
							typeOfBuy = card.getText();
						} else if (trafficCard.isSelected()) {
							System.out.println("��������:����ī��");
							typeOfBuy = trafficCard.getText();
						} else if (daily.isSelected()) {
							System.out.println("��������:���ױ�");
							typeOfBuy = daily.getText();
						}
						System.out.println("��������:" + typeOfBuy);
						try {
							System.out.println("�ֹ� 1" + arr[0]);
							System.out.println("�ֹ� 2" + arr[1]);
							System.out.println("�ֹ� 3" + arr[2]);
							System.out.println("�ֹ� 4" + arr[3]);
							System.out.println("�ֹ� 5" + arr[4]);
							System.out.println("��ǰ ����" + m);
							System.out.println("����� ���ұݾ�" + clientMoney);
							System.out.println("�ڸ���ȣ:" + num);
						} catch (ArrayIndexOutOfBoundsException c) {
							// TODO: handle exception
							System.out.println("��ǰ���� ������");
						}

						sendOrderData(typeOfBuy, arr[0], arr[1], arr[2], arr[3], arr[4], m,
								Integer.parseInt(clientMoney.replace("��", "")), need.getText(), num);

					}
				}
			} catch (Exception e) {
				// TODO: handle exception

				JOptionPane.showMessageDialog(null, "��ǰ�� �߰����ּ���", "���", JOptionPane.WARNING_MESSAGE);
			}

		}
	}

	// =========================Ŭ���̾�Ʈ ����================================
	public void sendOrderData(String type, String order1, String order2, String order3, String order4, String order5,
			int mountOfMoney, int clientOfMoney, String text, String num) {

		try {
			cSocket = new Socket("127.0.0.1", 9999);
			br = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));// ���Ž�Ʈ�� ����
			pw = new PrintWriter(new OutputStreamWriter(cSocket.getOutputStream()));// ��½�Ʈ�� ����

			pw.println("order/" + type + "/" + order1 + "/" + order2 + "/" + order3 + "/" + order4 + "/" + order5 + "/"
					+ mountOfMoney + "/" + clientOfMoney + "/" + need.getText() + "/" + num);// 11�� ������ ����
			pw.flush();
			while ((data = br.readLine()) != null) {
				if (data.equals("�ֹ�����")) {
					JOptionPane.showMessageDialog(null, "�ֹ��� �Ϸ�Ǿ����ϴ�");
					pw.close();
					br.close();
					cSocket.close();
				} else {
					JOptionPane.showMessageDialog(null, "ī���Ϳ��� �ֹ����ּ���");
				}

			}
			pw.close();
			br.close();
			cSocket.close();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				pw.close();
				br.close();
				cSocket.close();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("������ ������� ���Ͽ����ϴ�");
			}
		}
	}

	// ----------------------------------------------------------------------
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	class SearchWord extends JFrame {
		public SearchWord() {
			String searchText = search.getText();
			if (new FoodValue().returnValue(searchText) != null) {

			}

			setSize(500, 500);
			setVisible(true);
		}
	}

	
}
