import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

public class TimeEdit extends JDialog implements ActionListener {
	
	JTextField idField;
	JButton btnSearch, btnAdd, btnDelete, timeAdd, timeDelete;
	String[] moneyArr = { "60��", "120��", "180��", "300��", "600��", "1440��" };
	JComboBox<String> time = new JComboBox<>(moneyArr);

	JTable userTable;
	DefaultTableModel model;

	String[] menuArr = { "���̵�", "��й�ȣ", "�̸�", "�̸���", "�޴���ȭ", "�ּ�", "�����ð�", "�ѱݾ�" };
	Vector<UserDTO> userFromDB = UserDAO.userAll();
	Vector<String> userColumn = new Vector<>();
	JScrollPane scroll;
	JTextField id;

	public TimeEdit(JFrame frame) {
		super(frame,true);
		setTitle("�ð��߰�/�ð�����");
		Container c = getContentPane();

		JPanel p1 = new JPanel();
		idField = new JTextField(20);
		btnSearch = new JButton("�˻�");
		btnSearch.addActionListener(this);
		btnAdd = new JButton("�߰�");
		btnAdd.addActionListener(this);
		btnDelete = new JButton("�ʱ�ȭ");
		btnDelete.addActionListener(this);
		p1.add(idField);
		p1.add(btnSearch);
		p1.add(btnAdd);
		p1.add(btnDelete);
		c.add(p1, "North");

		for (int i = 0; i < menuArr.length; i++) {
			userColumn.add(menuArr[i]);
		}
		model = new DefaultTableModel(userColumn, 0);

		for (int i = 0; i < userFromDB.size(); i++) {
			String[] userRow = { userFromDB.get(i).getId(), userFromDB.get(i).getPwd(), userFromDB.get(i).getName(),
					userFromDB.get(i).getEmail(), userFromDB.get(i).getPhoneNumber(), userFromDB.get(i).getAddress(),
					Integer.toString(userFromDB.get(i).getRemainTime()),
					Integer.toString(userFromDB.get(i).getMountOfMoney()) };
			model.addRow(userRow);
		}

		userTable = new JTable(model);
		scroll = new JScrollPane(userTable);
		c.add(scroll, "Center");

		JPanel p3 = new JPanel();
		id = new JTextField(20);
		timeAdd = new JButton("�ð��߰�");
		timeAdd.addActionListener(this);
		timeDelete = new JButton("�ð�����");
		timeDelete.addActionListener(this);
		p3.add(id);
		p3.add(time);
		p3.add(timeAdd);
		p3.add(timeDelete);

		c.add(p3, "South");

		setSize(600, 500);
		setVisible(true);
	}

	public void listAll() {
		model.setRowCount(0);// ���� �ʱ�ȭ�����ش�
		Vector<UserDTO> userFromDB = UserDAO.userAll();
		model.setRowCount(0);// ���� �ʱ�ȭ�����ش�
		for (int i = 0; i < userFromDB.size(); i++) {
			String[] userRow = { userFromDB.get(i).getId(), userFromDB.get(i).getPwd(), userFromDB.get(i).getName(),
					userFromDB.get(i).getEmail(), userFromDB.get(i).getPhoneNumber(), userFromDB.get(i).getAddress(),
					Integer.toString(userFromDB.get(i).getRemainTime()),
					Integer.toString(userFromDB.get(i).getMountOfMoney()) };
			model.addRow(userRow);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		if (cmd.equals("�߰�")) {
			if (userTable.getSelectedRow() == -1) {
				return;
			} else {
				int row = userTable.getSelectedRow();
				id.setText(userFromDB.get(row).getId());
				idField.setText("");
			}
		}
		if (cmd.equals("�ʱ�ȭ")) {
			id.setText("");
			idField.setText("");
			listAll();
		}
		if (cmd.equals("�ð��߰�")) {
			String userId = id.getText();
			int index = time.getSelectedIndex();
			int time = Integer.parseInt(moneyArr[index].replaceAll("��", ""));
			int money = 0;
			switch (time) {
			case 60: 
				money = 1000;
				break;
			case 120:
				money = 2000;
				break;
			case 180:
				money = 3000;
				break;
			case 300:
				money = 5000;
				break;
			case 600:
				money = 10000;
				break;
			case 1440:
				money = 20000;
				break;
			default:
				money = 0;
			}
			UserDAO.addTime(userId, time, money);
			listAll();
			TimeSynChronize synchronize=new TimeSynChronize(userId, time,"�ð��߰�"); //Ŭ�󸮾�Ʈ���� �ð� ����ȭ �����ش� 
			synchronize.start(); //������ ���� 
		}
		if (cmd.equals("�ð�����")) {
			String userId = id.getText();
			int index = time.getSelectedIndex();
			int time = Integer.parseInt(moneyArr[index].replaceAll("��", ""));
			int money = 0;
			switch (time) {
			case 60: 
				money = 1000;
				break;
			case 120:
				money = 2000;
				break;
			case 180:
				money = 3000;
				break;
			case 300:
				money = 5000;
				break;
			case 600:
				money = 10000;
				break;
			case 1440:
				money = 20000;
				break;
			default:
				money = 0;
			}
			UserDAO.deleteTime(userId, time, money);
			listAll();
			TimeSynChronize synchronize=new TimeSynChronize(userId, time,"�ð�����"); //Ŭ�󸮾�Ʈ���� �ð� ����ȭ �����ش� 
			synchronize.start(); //������ ���� 
		}
		if(cmd.equals("�˻�")) {
			String getId=idField.getText();
			ArrayList<UserDTO> userInfo= UserDAO.clientAll(getId);
			String id=null;String pwd=null;String name=null;String email=null;
			String phonNumber=null;String address=null;String remainTime=null;String mountToMoney=null;
		
			for(int i=0;i<userInfo.size();i++) {
				id=userInfo.get(i).getId();
				pwd=userInfo.get(i).getPwd();
				name=userInfo.get(i).getName();
				email=userInfo.get(i).getEmail();
				phonNumber=userInfo.get(i).getPhoneNumber();
				address=userInfo.get(i).getAddress();
				remainTime=Integer.toString(userInfo.get(i).getRemainTime());
				mountToMoney=Integer.toString(userInfo.get(i).getMountOfMoney());
			}
			model.setRowCount(0);//���� �ʱ�ȭ�����ش�
			for(int i=0;i<userInfo.size();i++) {
				String[] userRow= {id,pwd,name,email,phonNumber,address,remainTime,mountToMoney};
				model.addRow(userRow);
			}
			idField.setText("");
		}
	}

	

}
