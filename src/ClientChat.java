import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientChat extends JDialog implements ActionListener {
	JTextArea area;
	JScrollPane scroll;
	JTextField input;
	JButton btnOk;

	Socket mySocket;
	String myNick;
	BufferedReader br;
	PrintWriter pw;
	String data;
	MySender t; // �޼����� ������ ������
	MyReceiver r;// �޼����� �޴� ������

	public ClientChat(JFrame frame) {
		super(frame);
		setTitle("�����ä��");
		Container c = getContentPane();
		JPanel p1 = new JPanel();
		area = new JTextArea(10, 25);

		area.setEditable(false);
		input = new JTextField(20);
		input.addActionListener(this);
		btnOk = new JButton("����");
		btnOk.addActionListener(this);
		scroll = new JScrollPane(area);
		p1.add(scroll);
		p1.add(input);
		p1.add(btnOk);
		c.add(p1, "Center");

		setSize(320, 270);
		setLocation(500, 300);
		setVisible(true);

		try {
			mySocket = new Socket("127.0.0.1", 1995);
			t = new MySender("�����", mySocket);
			t.start();

			r = new MyReceiver(mySocket, area);
			r.start();

		} catch (Exception e) {
			// TODO: handle exception

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		t.sendMsg(input.getText());
		input.setText("");
	}

	

}
