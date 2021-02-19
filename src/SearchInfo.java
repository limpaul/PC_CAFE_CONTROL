
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;

public class SearchInfo extends JDialog implements ActionListener { // ���̵�� ��й�ȣ�� ã�¼���� �ý����� ������
	private ImageIcon icon;
	private JScrollPane scrollPane;
	private JButton okButton, cancelButton;
	private JTextField nameField, emailField;
	Socket cSocket =null;
	BufferedReader br = null;
	PrintWriter pw=null;
	String data=null;
	public SearchInfo(JFrame frame) {
		super(frame, "ȸ���˻�", true);
		icon = new ImageIcon("3.png");

		JPanel background = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		scrollPane = new JScrollPane(background);
		setContentPane(scrollPane);
		searchInfoModel(background);
		setSize(400, 300);
		setVisible(true);
	}

	public void searchInfoModel(JPanel panel) {
		panel.setLayout(null);
		setTitle("ȸ�� ��ȸ");
		Font font = new Font("����", Font.BOLD, 20);
		JLabel logo = new JLabel("�̸��Ϸ� �����ϱ�");
		logo.setFont(font);
		logo.setBounds(90, 5, 300, 80);
		panel.add(logo);

		JLabel name = new JLabel("�̸�");
		name.setBounds(90, 80, 50, 80);
		panel.add(name);

		JLabel email = new JLabel("�̸���");
		email.setBounds(90, 110, 50, 80);
		panel.add(email);

		nameField = new JTextField(20);
		nameField.setBounds(140, 110, 100, 20);
		panel.add(nameField);

		emailField = new JTextField(20);
		emailField.setBounds(140, 140, 100, 20);
		panel.add(emailField);

		okButton = new JButton("Ȯ��");
		okButton.setBounds(100, 180, 100, 20);
		panel.add(okButton);

		cancelButton = new JButton("���");
		cancelButton.setBounds(200, 180, 100, 20);
		panel.add(cancelButton);

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		String name = nameField.getText();
		String email = emailField.getText();
		if (cmd.equals("Ȯ��")) {
			// JOptionPane.showMessageDialog(null, UserDAO.idPw(name, email));
			try {
				cSocket = new Socket("127.0.0.1", 9999);
				br = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));// ���Ž�Ʈ�� ����
				pw = new PrintWriter(new OutputStreamWriter(cSocket.getOutputStream()));// ��½�Ʈ�� ����
				pw.println("userSearch/" + name + "/" + email );// 4��° ���ڿ��� pc��ȣ�� �Է¹���
				pw.flush();
				
				while ((data = br.readLine()) != null) {
					String arr[]=data.split("/");
					if(arr[0].equals("��ȸ����")) {
						nameField.setText("");
						emailField.setText("");
						JOptionPane.showMessageDialog(null, arr[1]+"\n"+arr[2]);
						
					}else if(arr[0].equals("��ȸ����")){
						nameField.setText("");
						emailField.setText("");	
						JOptionPane.showMessageDialog(null, "���� ���� �ʽ��ϴ�", "�׷��� ����", JOptionPane.WARNING_MESSAGE);
											
					}
					
					break;
				}
				
				
			} catch (Exception ex) {
				// TODO: handle exception
				System.out.println(ex);
				JOptionPane.showMessageDialog(null, "���� �������", "���", JOptionPane.WARNING_MESSAGE);
			}finally {
				try {
					br.close();
					pw.close();
					cSocket.close();
				}catch (Exception e23) {
					// TODO: handle exception
				}
			}

		}
		if (cmd.equals("���"))

		{
			dispose();
		}

	}

}
