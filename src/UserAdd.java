

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;


public class UserAdd extends JDialog {
	JTextField addressText, textId, nameText, emailText, phoneText1;
	JPasswordField textPw, textRpw;
	private JScrollPane scrollPane;
	private Font f1;
	private JButton btnOk, btnCancel, btnIdChk;

	// ���� ���� �ʵ�
	Socket cSocket;
	BufferedReader br;
	PrintWriter pw;
	String data;

	public UserAdd(JFrame frame) {
		super(frame, true);
		
		// ��� Panel ������ contentPane ���� ����
		Container container = getContentPane();
		JPanel background = new JPanel();
		container.add(background);

		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("ȸ������");
		setSize(480, 450);
		scrollPane = new JScrollPane(background);
		setContentPane(scrollPane);
		////////////////////////// ��׶��� �̹��� �����Ͽ���
		userAddModel(background);
		setResizable(false);
		setLocation(500, 300);
		setVisible(true);
	}

	public void userAddModel(JPanel panel) {

		panel.setLayout(new BorderLayout());

		f1 = new Font("����", Font.BOLD, 30);
		JLabel logo = new JLabel("ȸ �� �� ��");
		logo.setFont(f1);
		labelCenter(logo);
		panel.add(logo, "North");

		JPanel sub_panel = new JPanel();
		sub_panel.setLayout(new GridLayout(8, 3));
		JLabel id = new JLabel("��  ��  ��");
		labelCenter(id);
		sub_panel.add(id);

		textId = new JTextField();
		sub_panel.add(textId);

		btnIdChk = new JButton("���̵��ߺ�Ȯ��");
		sub_panel.add(btnIdChk);

		JLabel pw = new JLabel("��й�ȣ");
		labelCenter(pw);
		sub_panel.add(pw);
		textPw = new JPasswordField();
		sub_panel.add(textPw);
		JLabel blank = new JLabel("");
		sub_panel.add(blank);

		JLabel rpw = new JLabel("��й�ȣȮ��");
		labelCenter(rpw);
		sub_panel.add(rpw);
		textRpw = new JPasswordField();
		sub_panel.add(textRpw);
		JLabel blank1 = new JLabel("");
		sub_panel.add(blank1);

		JLabel name = new JLabel("�� ��");
		labelCenter(name);
		sub_panel.add(name);
		nameText = new JTextField();
		sub_panel.add(nameText);
		JLabel blank3 = new JLabel("");
		sub_panel.add(blank3);
		nameText.addKeyListener(new UserKeyEvent());

		JLabel eMail = new JLabel("�� �� ��");
		labelCenter(eMail);
		sub_panel.add(eMail);
		emailText = new JTextField();
		sub_panel.add(emailText);
		JLabel blank4 = new JLabel("");
		sub_panel.add(blank4);

		JLabel phone = new JLabel("�� �� ��");
		labelCenter(phone);
		sub_panel.add(phone);
		phoneText1 = new JTextField();
		phoneText1.addKeyListener(new UserNumber());
		JLabel alert = new JLabel("-�� �����ϰ� �Է�");
		labelCenter(alert);
		sub_panel.add(phoneText1);
		sub_panel.add(alert);

		JLabel address = new JLabel("�� ��");
		labelCenter(address);
		sub_panel.add(address);
		addressText = new JTextField();
		sub_panel.add(addressText);
		JLabel blank5 = new JLabel("");
		sub_panel.add(blank5);

		btnOk = new JButton("�����ϱ�");
		sub_panel.add(btnOk);
		btnCancel = new JButton("�������");
		sub_panel.add(btnCancel);
		JLabel blank6 = new JLabel("");
		sub_panel.add(blank6);
		panel.add(sub_panel, "Center");

		JPanel sub_panel3 = new JPanel();
		sub_panel3.setLayout(new FlowLayout());
		sub_panel3.add(btnOk);
		sub_panel3.add(btnCancel);
		panel.add(sub_panel3, "South");

		UserEvent userEvent = new UserEvent();
		btnOk.addActionListener(userEvent);
		btnCancel.addActionListener(userEvent);
		btnIdChk.addActionListener(userEvent);
	}

	public void labelCenter(JLabel label) {
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
	}

	//////////////////////////////////// �̺�Ʈ ���� �κ�

	private class UserEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String cmd = e.getActionCommand();
			String id = textId.getText();
			char[] sPw = textPw.getPassword();
			String pwd = new String(sPw);
			char[] rPw = textRpw.getPassword();
			String rPwd = new String(rPw);
			String name = nameText.getText();
			String email = emailText.getText();
			String phoneNumber = phoneText1.getText();
			String address = addressText.getText();
			if (cmd.equals("�����ϱ�")) {

				if (id.equals("")) {
					JOptionPane.showMessageDialog(null, "���̵� ��ĭ�Դϴ�");
				} else if (pwd.equals("")) {
					JOptionPane.showMessageDialog(null, "�佺������� ��ĭ�Դϴ�");
				} else if (!pwd.equals(rPwd)) {
					JOptionPane.showMessageDialog(null, "�佺���尡 ���� �ʽ��ϴ�");
				} else if (name.equals("")) {
					JOptionPane.showMessageDialog(null, "�̸����� ��ĭ�Դϴ�");
				} else if (email.equals("")) {
					JOptionPane.showMessageDialog(null, "�̸��϶��� ��ĭ�Դϴ�");
				} else if (phoneNumber.equals("")) {
					JOptionPane.showMessageDialog(null, "�޴������� ��ĭ�Դϴ�");
				} else if (address.equals("")) {
					JOptionPane.showMessageDialog(null, "�ּҶ��� ��ĭ�Դϴ�");
				} else {
					if (sendVerify(id) == false
							&& sendData(new UserDTO(id, pwd, name, email, phoneNumber, address, 0, 0)) == true) {
						JOptionPane.showMessageDialog(null, "���Լ���!");
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "���̵� �ߺ��Ǿ����ϴ�");
					}
				}
			}
			if (cmd.equals("���̵��ߺ�Ȯ��")) {
				if (UserDAO.verfyID(id) == false) {
					JOptionPane.showMessageDialog(null, "����Ҽ� �ִ� ���̵� �Դϴ�");

				} else {
					JOptionPane.showMessageDialog(null, "���Ұ����� ���̵��Դϴ�");
				}
			}
			if (cmd.equals("�������")) {
				dispose();
			}
		}
//---------------------------------------Ŭ���̾�Ʈ ���� ------------------------------------------
		public boolean sendData(UserDTO userDTO) { // ���� UserDTO �� ����

			try {
				cSocket = new Socket("127.0.0.1", 9999);
				br = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));// ���Ž�Ʈ�� ����
				pw = new PrintWriter(new OutputStreamWriter(cSocket.getOutputStream()));// ��½�Ʈ�� ����
				pw.println("userAdd/" + userDTO.getId() + "/" + userDTO.getPwd() + "/" + userDTO.getName() + "/"
						+ userDTO.getEmail() + "/" + userDTO.getPhoneNumber() + "/" + userDTO.getAddress());
				pw.flush();
				while ((data = br.readLine()) != null) {
					if (data.equals("���Լ���")) {
						return true;
					} else if (data.equals("���Խ���")) {
						return false;
					} else {
						return false;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "���� �������", "���", JOptionPane.WARNING_MESSAGE);
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

			return false;
		}

		public boolean sendVerify(String id) { // ���� UserDTO �� ����

			try {
				cSocket = new Socket("127.0.0.1", 9999);
				br = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));// ���Ž�Ʈ�� ����
				pw = new PrintWriter(new OutputStreamWriter(cSocket.getOutputStream()));// ��½�Ʈ�� ����
				pw.println("userVerify/" + id);			
				pw.flush();
				while ((data = br.readLine()) != null) {
					if (data.equals("�ߺ��ƴ�")) {
						return false;
					} else if (data.equals("�ߺ�")) {
						return true;
					} else {
						return true;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "���� �������", "���", JOptionPane.WARNING_MESSAGE);
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

			return true;
		}
	}
//----------------------------------------------------------Key �̺�Ʈ ------------------------------
	class UserKeyEvent extends KeyAdapter {
		@Override
		public void keyTyped(KeyEvent e) {
			char value = e.getKeyChar();
			System.out.println(value);
			if (!(value < '0' || value > '9')) {
				JOptionPane.showMessageDialog(null, "�ѱ� �Ǵ� �̸� �Է�", "���", JOptionPane.WARNING_MESSAGE);
				e.consume();
			}

		}

	}

	class UserNumber extends KeyAdapter {
		@Override
		public void keyTyped(KeyEvent e) {
			char value = e.getKeyChar();
			int expression = e.getKeyCode();
			System.out.println(expression);

			if ((value < '0' || value > '9')) {
				JOptionPane.showMessageDialog(null, "���ڸ� �Է�", "���", JOptionPane.WARNING_MESSAGE);
				e.consume();
			}

		}

	}

}
