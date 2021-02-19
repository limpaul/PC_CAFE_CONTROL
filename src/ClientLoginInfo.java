
import javax.swing.*;
import javax.swing.plaf.synth.SynthSplitPaneUI;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientLoginInfo extends JFrame {
	private JButton move, menuImage, shutdown, order, delayTime, call, message, moneyInfo, imger;
	private JLabel number, id, money, startTime, usingTime, hotMenu, remainTime;
	private JLabel dbId, dbamountOfMoney, dbStartOfTime, dbUsingTime, dbRemainTime;
	private JButton internet, lol, overWatch, bettleGround;
	private JLabel board, board2, board3;
	UserDTO getDTO = null;
	Date today = new Date();
	SimpleDateFormat time = new SimpleDateFormat("hh��mm��");
	int num;
	// �ڸ���ȣ,���̵�,�����,���۽ð�,���ð�

	// ���� ��������
	ServerSocket controlServerSocket;
	Socket controlSocket;
	BufferedReader br;
	PrintWriter pw;
	String data;

	ClientLoginInfo(String id,int num) {
		this.num=num;
		getDTO = UserDAO.clientInfo(id);
		JPanel panel = new JPanel();
		setTitle("ȸ������");
		setResizable(false);
		setLocation(800, 200);
		setSize(420, 700);
		ClientLoginInfoModel(panel,num);
		add(panel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		Runnable r = new ClientServerThread(getDTO.getId());
		Thread t = new Thread(r);
		t.start();
	}

	public void ClientLoginInfoModel(JPanel panel,int num) {

		panel.setLayout(null);

		number = new JLabel("�ڸ���ȣ"+num);
		number.setBounds(20, 20, 100, 20);
		panel.add(number);

		move = new JButton("�ڸ��̵�");
		move.setBounds(100, 20, 90, 20);
		panel.add(move);

		board = new JLabel(
				"-------------------------------------------------------------------------------------------------------");
		board.setBounds(0, 40, 1000, 10);
		panel.add(board);

		id = new JLabel("�� �� ��");
		id.setBounds(20, 60, 90, 20);
		panel.add(id);

		dbId = new JLabel(getDTO.getId());// db�κ��;��̵�
		dbId.setBounds(120, 60, 90, 20);
		panel.add(dbId);

		money = new JLabel("�����");
		money.setBounds(20, 90, 90, 20);
		panel.add(money);

		dbamountOfMoney = new JLabel(String.valueOf(getDTO.getMountOfMoney() + "��"));
		dbamountOfMoney.setBounds(120, 90, 90, 20);
		panel.add(dbamountOfMoney);

		startTime = new JLabel("���۽ð�");
		startTime.setBounds(20, 120, 90, 20);
		panel.add(startTime);

		dbStartOfTime = new JLabel(time.format(today));
		dbStartOfTime.setBounds(120, 120, 90, 20);
		panel.add(dbStartOfTime);

		usingTime = new JLabel("���ð�");
		usingTime.setBounds(20, 150, 100, 20);
		panel.add(usingTime);

		dbUsingTime = new JLabel("1�ð�");
		dbUsingTime.setBounds(120, 150, 100, 20);
		panel.add(dbUsingTime);

		remainTime = new JLabel("�����ð�");
		remainTime.setBounds(20, 180, 100, 20);
		panel.add(remainTime);

		dbRemainTime = new JLabel(String.valueOf(getDTO.getRemainTime() + "��"));
		dbRemainTime.setBounds(120, 180, 100, 20);
		panel.add(dbRemainTime);

		imger = new JButton(new ImageIcon("pic.png"));
		imger.setBounds(300, 50, 110, 70);
		panel.add(imger);

		shutdown = new JButton("�������");
		shutdown.setBounds(300, 125, 110, 70);
		panel.add(shutdown);

		order = new JButton("��ǰ�ֹ�");
		order.setBounds(0, 200, 90, 55);
		panel.add(order);

		delayTime = new JButton("���ҿ���");
		delayTime.setBounds(90, 200, 90, 55);
		panel.add(delayTime);

		call = new JButton("ȣ��");
		call.setBounds(180, 200, 60, 55);
		panel.add(call);

		message = new JButton("�޼���");
		message.setBounds(240, 200, 80, 55);
		panel.add(message);
		message.addActionListener(new ClientLoginInfoEvent());

		moneyInfo = new JButton("�������");
		moneyInfo.setBounds(320, 200, 90, 55);
		panel.add(moneyInfo);

		board2 = new JLabel(
				"-------------------------------------------------------------------------------------------------------");
		board2.setBounds(0, 220, 1000, 100);
		panel.add(board2);

		Font f = new Font("����", Font.BOLD, 20);
		hotMenu = new JLabel("�� �� �� ��");
		hotMenu.setFont(f);
		hotMenu.setBounds(150, 250, 1000, 100);
		panel.add(hotMenu);

		board3 = new JLabel(
				"-------------------------------------------------------------------------------------------------------");
		board3.setBounds(0, 280, 1000, 100);
		panel.add(board3);

		internet = new JButton("���ͳ�",new ImageIcon("chrome.jpg"));
		internet.setBounds(0, 335, 100, 100);
		panel.add(internet);
		internet.addActionListener(new ClientLoginInfoEvent());
		lol = new JButton("LOL",new ImageIcon("lol.jpg"));
		lol.setBounds(100, 335, 100, 100);
		panel.add(lol);
		lol.addActionListener(new ClientLoginInfoEvent());

		overWatch = new JButton("������ġ",new ImageIcon("overwatch.jpg"));
		overWatch.setBounds(200, 335, 100, 100);
		panel.add(overWatch);
		overWatch.addActionListener(new ClientLoginInfoEvent());

		bettleGround = new JButton("��Ʋ�׶���",new ImageIcon("bettleground.png"));
		bettleGround.setBounds(300, 335, 120, 100);
		panel.add(bettleGround);
		bettleGround.addActionListener(new ClientLoginInfoEvent());
		ClientLoginInfoEvent infoEven = new ClientLoginInfoEvent();

		shutdown.addActionListener(infoEven);
		order.addActionListener(infoEven);

	}

	class ClientServerThread implements Runnable {
		private String id;

		public ClientServerThread(String id) {
			this.id=id;
	
		}
		public void run() { // �ٸ� ������� ������ �ֳ��ϸ� GUI �� �������ȾƼ� ..
			// --------------------------------------------------------------------------------
			// ��������
			// ���� ���� ����
			
			try {
				controlServerSocket = new ServerSocket(3333);
				while (true) {
					
					System.out.println("Ŭ���̾�Ʈ ���� ������ ");
					controlSocket = controlServerSocket.accept();
					System.out.println(
							"Ŭ���̾�Ʈ ���ӿϷ�" + "ip:" + controlSocket.getInetAddress() + "port:" + controlSocket.getPort());
					br = new BufferedReader(new InputStreamReader(controlSocket.getInputStream()));// ���Ž�Ʈ�� ����
					pw = new PrintWriter(new OutputStreamWriter(controlSocket.getOutputStream()));// ��½�Ʈ�� ����
					while ((data = br.readLine()) != null) {
						int sum=0;
						String[] rData=data.split("/");
						if(rData[0].equals("����ȭ") && rData[1].equals("�ð��߰�") && rData[2].equals(id)) {
							int addTime=Integer.parseInt(rData[3]);
							String dbStringTime=dbRemainTime.getText().replaceAll("��", "");
							int dbNumTime=Integer.parseInt(dbStringTime);
							sum+=dbNumTime+addTime;
							dbRemainTime.setText(sum+"��");
							getDTO = UserDAO.clientInfo(id);
						}
						if(rData[0].equals("����ȭ") && rData[1].equals("�ð�����") && rData[2].equals(id)) {
							int minusTime=Integer.parseInt(rData[3]);
							String dbStringTime=dbRemainTime.getText().replaceAll("��", "");
							int dbNumTime=Integer.parseInt(dbStringTime);
							if(dbNumTime<=0) {
								System.out.println("�����");
								dispose();
								new UserLogin();
								controlSocket.close();
								controlServerSocket.close();
								br.close();
								pw.close();
							}
							sum+=dbNumTime-minusTime;
							dbRemainTime.setText(sum+"��");
							getDTO = UserDAO.clientInfo(id);
						
						}
								
						
						if (data.equals("shutdown")) {// �Ǵ� userAdd/���̵�/��й�ȣ/�̸� �̷������� �����͸��Է¹޴´�
							dispose();
							new UserLogin();
							controlSocket.close();
							controlServerSocket.close();
							br.close();
							pw.close();
						}

					}

				}

			} catch (Exception shutdown) {
				// TODO: handle exception
			}
		}
	}
	class ClientLoginInfoEvent implements ActionListener {

		
		
		@Override
		public  void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String cmd = e.getActionCommand();
			if (cmd.equals("�������")) {
				new UserLogin();
			}
			if (cmd.equals("��ǰ�ֹ�")) {
				new OrderGood(ClientLoginInfo.this,num+"��");
			}
			if (cmd.equals("LOL")) {
				new GameView("LOL");
			}
			if (cmd.equals("������ġ")) {
				new GameView("������ġ");
			}
			if (cmd.equals("���ͳ�")) {
				new GameView("���ͳ�");
			}
			if (cmd.equals("��Ʋ�׶���")) {
				new GameView("��Ʋ�׶���");
			}
			if(cmd.equals("�޼���")) {
				new ClientChat(ClientLoginInfo.this);
			}

		}
	}

}
