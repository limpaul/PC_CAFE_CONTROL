
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class UserLogin extends JFrame {
	private JButton btnLogin, btnSearch, btnAdd, btnOff, btnRestart;
	private JPasswordField passText;
	private JTextField userText, numberText;
	private JTextArea noticeArea;
	private ButtonGroup bgp = new ButtonGroup();
	private JRadioButton r; // radio ��ư�߰�
	private JButton serverInfo;
	private int rValue; // radioButton �� �ڸ���ȣ�� ���Ѵ�
	// ���� ����
	boolean serverStatus;// ���� �������
	InetAddress serverIp;// ���� ������

	// Ŭ���̾�Ʈ �ʵ�
	Socket cSocket;
	BufferedReader br;
	PrintWriter pw;
	String data;

	public UserLogin() {
		super("�α���â");

		Container contain = getContentPane();
		JPanel p = new JPanel();
		for (int i = 0; i < 16; i++) {
			r = new JRadioButton((i + 1) + "��");
			r.addActionListener(new JRadioEvent());
			bgp.add(r);
			p.add(r);
		}
		serverInfo = new JButton("��������Ȯ��");
		p.add(serverInfo);
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		centerPanel(p1);
		SouthPanel(p2);

		contain.add(p, "North");
		contain.add(p1, "Center");
		contain.add(p2, "South");

		LoginEvent loginEvent = new LoginEvent();
		serverInfo.addActionListener(loginEvent);
		btnLogin.addActionListener(loginEvent);
		btnAdd.addActionListener(loginEvent);
		btnOff.addActionListener(loginEvent);
		btnRestart.addActionListener(loginEvent);
		btnSearch.addActionListener(loginEvent);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1200, 800);
		// setUndecorated(true);
		setResizable(true);
		setVisible(true);

	}

	public void centerPanel(JPanel panel) {
		JLabel label = new JLabel(new ImageIcon("1_1.jpg"));
		panel.add(label);
	}

	public void SouthPanel(JPanel panel) {
		panel.setLayout(new BorderLayout());

		JPanel loginAllPanel = new JPanel(new GridLayout(1, 5, 5, 5));
		loginAllPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		///
		loginAllPanel.add(new JLabel(" "));
		loginAllPanel.add(new JLabel(" "));
		loginAllPanel.add(new JLabel(" "));
		///

		JPanel login_panel = new JPanel(new GridLayout(3, 2, 5, 2));

		JLabel userLabel = new JLabel("���̵�");
		JLabel pwLabel = new JLabel("��й�ȣ");
		JLabel numberLabel = new JLabel("��ȸ��(ī���ȣ)");
		userText = new JTextField(40);
		passText = new JPasswordField(40);
		numberText = new JTextField(40);

		login_panel.add(userLabel);
		login_panel.add(userText);
		login_panel.add(pwLabel);
		login_panel.add(passText);
		login_panel.add(numberLabel);
		login_panel.add(numberText);

		labelCenter(userLabel, pwLabel, numberLabel);

		loginAllPanel.add(login_panel);

		btnLogin = new JButton("�α���");
		loginAllPanel.add(btnLogin);

		JPanel addOption = new JPanel(new GridLayout(4, 1, 5, 5));
		btnAdd = new JButton("ȸ������");
		btnSearch = new JButton("ID/PWã��");
		btnOff = new JButton("��������");
		btnRestart = new JButton("�ٽý���");
		addOption.add(btnAdd);
		addOption.add(btnSearch);
		addOption.add(btnOff);
		addOption.add(btnRestart);
		loginAllPanel.add(addOption);

		btnOff.addActionListener(new LoginEvent());
		noticeArea = new JTextArea();
		noticeArea.setText("�޽� �����е� ���Ǳ���,�����");
		loginAllPanel.add(noticeArea);

		// ��ü ����� ���ε� �����ִµ�
		JLabel text = new JLabel(" ");
		panel.add(text, "West");
		panel.add(loginAllPanel, "Center");

	}

	public void labelCenter(JLabel label1, JLabel label2, JLabel label3) {
		label1.setVerticalAlignment(SwingConstants.CENTER);
		label1.setHorizontalAlignment(SwingConstants.RIGHT);
		label2.setVerticalAlignment(SwingConstants.CENTER);
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label3.setVerticalAlignment(SwingConstants.CENTER);
		label3.setHorizontalAlignment(SwingConstants.RIGHT);
	}

	public class LoginEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			String id = userText.getText();
			char[] chpw = passText.getPassword(); // ���ڿ���ȣ��
			String pwd = new String(chpw);
			// String number = numberText.getText();
			String cmd = e.getActionCommand();
			int receive = 3;
			if (cmd.equals("��������Ȯ��")) {
				pingTest();
				if (serverStatus == true) {
					JOptionPane.showMessageDialog(null, "��������: ��ȣ\n");
				} else {
					JOptionPane.showMessageDialog(null, "���� ���� ���� ����", "���", JOptionPane.WARNING_MESSAGE);
				}
			} else {
				receive = sendData(id, pwd);

				if (receive == 1) {// ������ �����͸� ����!! �ϰ� �����͸� �޴´�
					dispose();
					new ClientLoginInfo(id, rValue);
				} else if (receive == 2) {
					JOptionPane.showMessageDialog(null, "ī���� ���� ���� �Ͻñ� �ٶ��ϴ�");
				} else {
					userText.setText("");
					passText.setText("");
					numberText.setText("");
				}

				if (cmd.equals("ȸ������")) {
					new UserAdd(UserLogin.this);

				}
				if (cmd.equals("ID/PWã��")) {
					new SearchInfo(UserLogin.this);
				}
				if (cmd.equals("��������")) {
					JOptionPane.showMessageDialog(null, "���������!");
					System.exit(0);
				}
				if (cmd.equals("�ٽý���")) {
					JOptionPane.showMessageDialog(null, "�ٽý���!");
					setVisible(false);
					setVisible(true);
				}
			}

		}

		// =========================Ŭ���̾�Ʈ ����================================
		public boolean pingTest() {
			Socket ping = null;
			PrintWriter write = null;
			BufferedReader receive = null;
			try {
				ping = new Socket("127.0.0.1", 9999);
				write = new PrintWriter(new OutputStreamWriter(ping.getOutputStream()));
				receive = new BufferedReader(new InputStreamReader(ping.getInputStream()));
				write.println("ping");
				write.flush();
				String data;
				while((data = receive.readLine())!=null) {
					//System.out.println("test");
					if(data.equals("ping")) 
						serverStatus = true;
						
					else
						serverStatus = false;
						
					break;
				}
			}catch (Exception e) {
				System.out.println("server ping test fail");
			}finally {
				try {
					write.close();
					receive.close();
					ping.close();
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
			return false;
		}
		public int sendData(String id, String pwd) {
			System.out.println("���̵� ��� ����");
			if (rValue == 0) {
				JOptionPane.showMessageDialog(null, "�ڸ��� �������ּ���", "���", JOptionPane.WARNING_MESSAGE);
			} else {
				try {
					cSocket = new Socket("127.0.0.1", 9999);
					br = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));// ���Ž�Ʈ�� ����
					pw = new PrintWriter(new OutputStreamWriter(cSocket.getOutputStream()));// ��½�Ʈ�� ����

					pw.println("login/" + id + "/" + pwd + "/" + rValue);// 4��° ���ڿ��� pc��ȣ�� �Է¹���
					pw.flush();
					while ((data = br.readLine()) != null) {
						if (data.equals("�α��μ���")) {
							return 1;
						} else if (data.equals("ī���� ���� ���� �Ͻñ�ٶ��ϴ�")) {
							return 2;
						} else if (data.equals("�α��ν���")) {
							return 3;
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "���� �������", "���", JOptionPane.WARNING_MESSAGE);
				} finally {
					try {
						System.out.println("�α��� ���� ���� ����");
						pw.close();
						br.close();
						cSocket.close();
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("������ ������� ���Ͽ����ϴ�");
					}
				}

			}
			return 3;
		}

	}

	class JRadioEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JRadioButton btn = (JRadioButton) e.getSource();
			rValue = Integer.parseInt(btn.getText().replaceAll("��", ""));

		}
	}

	// ----------------------------------------------------------------------
	public static void main(String[] args) {
		new UserLogin();
	}
}
