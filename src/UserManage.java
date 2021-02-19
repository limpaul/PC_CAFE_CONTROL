import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

public class UserManage extends JDialog implements ActionListener {
	JTextField idField;
	JTextField id, pwd, name, email, phone, address;
	String[] moneyArr= {"1�ð�","2�ð�","3�ð�","5�ð�","10�ð�","24�ð�"}; 
	JComboBox<String> time=new JComboBox<>(moneyArr);
	JButton btnSearch, btnSearchAll, btnDelete, btnAdd;
	JTable userTable;
	DefaultTableModel model;

	String[] menuArr = { "���̵�", "��й�ȣ", "�̸�", "�̸���", "�޴���ȭ", "�ּ�", "�����ð�", "�ѱݾ�" };
	Vector<UserDTO> userFromDB = UserDAO.userAll();
	Vector<String> userColumn = new Vector<>();
	JScrollPane scroll;

	public UserManage(JFrame frame) {
		super(frame,true);
		setTitle("������ ȸ����ȸ");
		Container c = getContentPane();
		JPanel p1 = new JPanel();
		idField = new JTextField(20);
		btnSearch = new JButton("���̵� �˻�");
		btnSearchAll = new JButton("��ü �˻�");
		btnDelete = new JButton("����");
		btnDelete.addActionListener(this);
		btnSearch.addActionListener(this);
		btnSearchAll.addActionListener(this);
		btnDelete.addActionListener(this);
		p1.add(idField);
		p1.add(btnSearch);
		p1.add(btnSearchAll);
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

		JPanel p2 = new JPanel();

		p2.setLayout(new GridLayout(2, 8));
		p2.add(new JLabel("       ���̵�"));
		p2.add(new JLabel("    ��й�ȣ"));
		p2.add(new JLabel("      �̸�"));
		p2.add(new JLabel("     �̸���"));
		p2.add(new JLabel("�ڵ���(-����)"));
		p2.add(new JLabel("       �ּ�"));
		p2.add(new JLabel("   �ð�"));
		p2.add(new JLabel(""));
		id = new JTextField();
		pwd = new JTextField();
		name = new JTextField();
		email = new JTextField();
		phone = new JTextField();
		address = new JTextField();
		//�ð� �߰���
		btnAdd = new JButton("�߰�");
		btnAdd.addActionListener(this);
		p2.add(id);
		p2.add(pwd);
		p2.add(name);
		p2.add(email);
		p2.add(phone);
		p2.add(address);
		p2.add(time);
		p2.add(btnAdd);

		c.add(p2, "South");
		//////
		setSize(700, 500);
		setResizable(false);
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
		String cmd=e.getActionCommand();
		
		
		if(cmd.equals("���̵� �˻�")) {
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
		if(cmd.equals("��ü �˻�")) {
			listAll();
		}
		
		if(cmd.equals("�߰�")) {
			//id,pwd,name,email,phone,address;
			int money=0;
			int index=time.getSelectedIndex();
			switch(moneyArr[index]) {
				case "1�ð�": money=1000;break;
				case "2�ð�": money=2000;break;
				case "3�ð�": money=3000;break;
				case "5�ð�": money=5000;break;
				case "10�ð�": money=10000;break;
				case "24�ð�": money=20000;break;
				default: money=0;
			}
			int time=Integer.parseInt(moneyArr[index].replaceAll("�ð�", ""));
			UserDTO userDTO=new UserDTO(id.getText(), pwd.getText(), name.getText(), email.getText(), phone.getText(),address.getText(),time,money);
			if(UserDAO.addUserUpdate(userDTO)==false) {
				JOptionPane.showMessageDialog(null, "ȸ�� �߰� ��� �ùٸ��� ����", "�߰� �ȵ�", JOptionPane.WARNING_MESSAGE);
			}
			id.setText("");pwd.setText("");name.setText("");email.setText("");phone.setText("");address.setText("");
			listAll();

		}
		if(cmd.equals("����")) {
			if(userTable.getSelectedRow()==-1) {
				return;
			}else {
				int row=userTable.getSelectedRow();
				model.removeRow(row);//ȭ������
				UserDAO.deleteUser(userFromDB.get(row).getId());
				idField.setText("");
			}
		}

		
	}



}
