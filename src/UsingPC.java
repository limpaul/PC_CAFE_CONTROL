
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Vector;

public class UsingPC extends JFrame {
	private String num;
	private String id;
	//������ �ɼ� ��ư 
	private JButton[] pc = new JButton[16];
	private JButton stop = new JButton("�������");
	private JButton add = new JButton("�ð� �߰�");
	private JButton minus = new JButton("�ð� ����");
	private JButton moneyManage = new JButton("��ݰ���");
	private JButton info = new JButton("����");
	private JButton thing = new JButton("��ǰ");
	private JButton user = new JButton("ȸ��");
	private JButton card = new JButton("ī��");
	private JButton msg = new JButton("�޼���");
	
	//�����ڰ� ����� UI�� Ŭ���� ������ ���� 
	private DefaultTableModel model;
	private String[] menuArr = { "���̵�", "��й�ȣ", "�̸�", "�̸���", "�޴���ȭ", "�ּ�", "�����ð�", "�ѱݾ�" }; 
	private Vector<String> userColumn = new Vector<>();
	private JScrollPane scroll;
	private String buttonNumber;// pc�ڸ� ��ȣ
	// ����
	ServerSocket sSocket;
	Socket mySocket;
	BufferedReader br;
	PrintWriter pw;
	String data;
	String stopNum;
	Vector<UserDTO> v;
	Vector<UsingPC> userInfoList=new Vector<>();
	public UsingPC(String num,String id) {
		this.num=num;
		this.id=id;
	}
	public UsingPC() {
		UsingPCEvent event = new UsingPCEvent();
		Container contain = getContentPane();
		contain.setLayout(new BorderLayout());
		JPanel p1 = new JPanel(); 
		p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // ��ü �µθ� 10��ŭ ���� 
		p1.setLayout(new GridLayout(4, 4, 10, 30)); // �¼� UI layout ���� 
		for (int i = 0; i < 16; i++) { // �� 16�ڸ� ��� 
			String pcNum = (i + 1) + "��PC";
			pc[i] = new JButton(pcNum); // PC�ڸ��� ��ư ��ü ����. 
			p1.add(pc[i]); // ��ο� �¼� UI ���� 
			pc[i].addActionListener(event); // ����� �ڸ� �̺�Ʈ ���. 
		}
		JPanel p2 = new JPanel();  // ����� ���� ��� UI��ġ ��� 
		p2.setLayout(new BorderLayout());  // layout����  
		p2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // ��ü �µθ� 20��ŭ ���� 
		JPanel p2_1 = new JPanel(); // ������ �ɼ� ��ư UI�κ� 
		// p2_1.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
		p2_1.setLayout(new GridLayout(4, 3, 10, 10));
		p2_1.add(new JLabel(""));
		p2_1.add(new JLabel("ĸ����PC�� 02-8888-8888"));
		p2_1.add(new JLabel(""));
		p2_1.add(stop); //����� ���� ��ư ��� 
		p2_1.add(add); // �ð� �߰� ��ư ��� 
		p2_1.add(minus);//�ð� ���� ��ư ��� 
		add.addActionListener(event); //  �ð� �߰� ��ư �̺�Ʈ ��� 
		minus.addActionListener(event); // �ð� ���� ��ư �̺�Ʈ ��� 
		p2_1.add(moneyManage); // 
		p2_1.add(info); // ������ Ⱦ
		p2_1.add(msg); // ������ �޼��� UI��� 
		msg.addActionListener(event); // �޼��� �̺�Ʈ ��� 
		p2_1.add(thing); // ��ǰ ��ư�߰� 
		thing.addActionListener(event); // ��ǰ ��ư �̺�Ʈ �߰� 
		p2_1.add(user); 
		user.addActionListener(event);
		p2_1.add(card); // ī�� �̺�Ʈ �߰� 
		p2.add(p2_1, "South"); //layout���� �������� �߰� 
		
		for (int i = 0; i < menuArr.length; i++) { // ����� ���� ��� 
			userColumn.add(menuArr[i]);
		}
		scroll = new JScrollPane(new JTable(new DefaultTableModel(userColumn, 0))); // ��ũ�� ��� �߰� 

		p2.add(scroll, "Center");

		stop.addActionListener(event); //���� ��ư �߰� 
		contain.add(p1, "Center");
		contain.add(p2, "East");
		setLocation(500, 50);
		setSize(1200, 700);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		// �����κ�
		// --------------------------------------------------------------------------------------------
		try {// �α����ҋ� ������ ������
			sSocket = new ServerSocket(9999);
			while (true) {
				System.out.println("���������� ");
				mySocket = sSocket.accept();
				System.out.println("Ŭ���̾�Ʈ ���ӿϷ�" + "ip:" + mySocket.getInetAddress() + "port:" + mySocket.getPort());
				br = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));// ���Ž�Ʈ�� ����
				pw = new PrintWriter(new OutputStreamWriter(mySocket.getOutputStream()));// ��½�Ʈ�� ����
				while ((data = br.readLine()) != null) {
					System.out.println("��������Ÿ:" + data);// db���̵� ��� ���� �޼ҵ� ������ return ���ش�
					String[] checkData = data.split("/");// login/���̵�/��й�ȣ �̷������� �޴´�
					
					if (checkData[0].equals("login")) {// �Ǵ� userAdd/���̵�/��й�ȣ/�̸� �̷������� �����͸��Է¹޴´�
						// if ������ checkData[0] ���� �α��ΰ��� �������� �� �ƴϸ� ȸ������ ���������� //�Ǵ� ����ã�� ���������� �����Ѵ�
						int chkLogin = UserDAO.loginCheck(checkData[1], checkData[2]);
						if (chkLogin == 1) {// db���� 1�� �����ϸ� �α��μ����̴�
							pw.println("�α��μ���"); // Ŭ���̾�Ʈ �������� ���ڸ������ش�
							pw.flush();
							int pcNum = Integer.parseInt(checkData[3]);
							System.out.println("PC��ȣ" + pcNum);
							pc[pcNum - 1].setIcon(new ImageIcon("user.png"));// pc��ȣ �α����Ѱ� �̹����ٲ۴�
							//pc[pcNum - 1].setText(checkData[1]);//PC��ȣ�� ���� ���̵�� �������ش�
							userInfoList.add(new UsingPC(checkData[3],checkData[1]));//���̵� PC��ȣ �־���
							// �α��������� ������ ������ ��Ͻ������ �Ѵ�
							
							v = UserDAO.clientIdInfo(checkData[1]);// db�� ���� ArrayList ���� ��ȯ�޴´�
							// ** �߿��� ����Ʈ��
						} else if (chkLogin == 2) {
							pw.println("ī���� ���� ���� �Ͻñ�ٶ��ϴ�");
							pw.flush();
						} else {
							pw.println("�α��ν���");
							pw.flush();
						}
					}
					
					
					if (checkData[0].equals("userAdd")) {
						// if���� checkData[0] �� userAdd �̸� ȸ��������� �ѱ��
						String id = checkData[1];
						String pwd = checkData[2];
						String name = checkData[3];
						String email = checkData[4];
						String phoneNumber = checkData[5];
						String address = checkData[6];
						if (UserDAO.insert(new UserDTO(id, pwd, name, email, phoneNumber, address, 0, 0)) == true) {
							pw.println("���Լ���");
							pw.flush();
						} else {
							pw.println("���Խ���");
							pw.flush();
						}
					}

					if (checkData[0].equals("userVerify")) {
						// if���� checkData[0] �� userAdd �̸� ȸ��������� �ѱ��
						String id = checkData[1];
						if (UserDAO.verfyID(id) == false) {
							pw.println("�ߺ��ƴ�");
							pw.flush();
						} else {
							pw.println("�ߺ�");
							pw.flush();
						}
					}
					if (checkData[0].equals("order")) {
						pw.println("�ֹ�����");
						pw.flush();
						// if���� checkData[0] �� userAdd �̸� ȸ��������� �ѱ��
						String type = checkData[1];// ��������
						String order1 = checkData[2];// �ֹ���ǰ1
						String order2 = checkData[3];// �ֹ���ǰ2
						String order3 = checkData[4];// �ֹ���ǰ3
						String order4 = checkData[5];// �ֹ���ǰ4
						String order5 = checkData[6];// �ֹ���ǰ5
						int mountOfMoney = Integer.parseInt(checkData[7]);
						int clientOfMoeny = Integer.parseInt(checkData[8]);
						String getText = checkData[9];
						String num = checkData[10];// �ڸ���ȣ
						JOptionPane.showMessageDialog(null,
								"�ֹ� ��� \n" + "��������" + type + "\n" + "�ֹ���ǰ1: " + order1 + "\n" + "�ֹ���ǰ2: " + order2
										+ "\n" + "�ֹ���ǰ3: " + order3 + "\n" + "�ֹ���ǰ4: " + order4 + "\n" + "�ֹ���ǰ5 :"
										+ order5 + "\n" + "�� �ݾ�" + mountOfMoney + "\n" + "�մ� :" + clientOfMoeny + "\n"
										+ "���� : " + getText + "\n" + "�ڸ���ȣ" + num);

					}
					if (checkData[0].equals("userSearch")) {
						// if���� checkData[0] �� userAdd �̸� ȸ��������� �ѱ��
						String name = checkData[1];
						String email = checkData[2];
						System.out.println("���� ����Ÿ"+name+"/"+email);
						String data=null;
						if ((data=UserDAO.idPw(name, email)) != null) {
							pw.println("��ȸ����/"+data);
							pw.flush();
						} else {
							pw.println("��ȸ����/");
							pw.flush();
						}
					}
					if (checkData[0].equals("ping")) {
						pw.println("ping");
						pw.flush();
					}
				}
				//mySocket.close();
				//System.out.println("���� ���� �����");
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception

		}
		
	}
	class Receiver{
		
	}
	// ------------------------------------------------------------------------------------------------------------------------
	class UsingPCEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String cmd = e.getActionCommand();
			System.out.println(cmd);
			try {
				cmd=cmd.replaceAll("��PC", "");
		
				for (int i = 0; i <userInfoList.size(); i++) {
					String num = userInfoList.get(i).num; //��ȣ�����Ѱ�
					if (num.equals(cmd)) {// ���� ArrayList �� �ִ� id�� ���� ��ư id�� ���� ���̵��� ���̵� ������ ������ش�
						stopNum=cmd;
						String id=userInfoList.get(i).id;
						ArrayList<UserDTO> userInfo= UserDAO.clientAll(id);
						String gid=null;String pwd=null;String name=null;String email=null;
						String phonNumber=null;String address=null;String remainTime=null;String mountToMoney=null;
					
						for(int j=0;j<userInfo.size();j++) {
					
							gid=userInfo.get(j).getId();
							pwd=userInfo.get(j).getPwd();
							name=userInfo.get(j).getName();
							email=userInfo.get(j).getEmail();
							phonNumber=userInfo.get(j).getPhoneNumber();
							address=userInfo.get(j).getAddress();
							remainTime=Integer.toString(userInfo.get(j).getRemainTime());
							mountToMoney=Integer.toString(userInfo.get(j).getMountOfMoney());
							buttonNumber=cmd;
						}
						model.setRowCount(0);//���� �ʱ�ȭ�����ش�
						for(int j=0;j<userInfo.size();j++) {
							String[] userRow= {gid,pwd,name,email,phonNumber,address,remainTime,mountToMoney};
							model.addRow(userRow);
						}
					}
				}
			} catch (NullPointerException x) {
				// TODO: handle exception
				System.out.println("��ġ�ϴ°� ����");
			}
			if (cmd.equals("�������")) { // ��Ƽ�����带 ���
				
				if (buttonNumber == null) {//�ƹ��͵� Ŭ�� ���ϸ� �˴ٿ�ȵ�
					JOptionPane.showMessageDialog(null, "pc�ڸ��� Ŭ�����ּ���", "���", JOptionPane.WARNING_MESSAGE);
				} else {
					pc[Integer.parseInt(stopNum)-1].setIcon(new ImageIcon(""));
					model.removeRow(0);
					stopNum=null;
					buttonNumber=null;
					Runnable r = new ServerControl(pc,buttonNumber);
					Thread t = new Thread(r);
					t.start();
				}
			}
			if(cmd.equals("��ǰ")) {
				new Stocks(UsingPC.this);
				
			}
			if(cmd.equals("ȸ��")) {
				new UserManage(UsingPC.this);
			}
			if(cmd.equals("�ð� �߰�")) {
				new TimeEdit(UsingPC.this);
			}
			if(cmd.equals("�ð� ����")) {
				new TimeEdit(UsingPC.this);
			}
			if(cmd.equals("�޼���")) {
				new AdminChat(UsingPC.this);
			}
		}

	}

	public static void main(String[] args) {
		new UsingPC();	
	}
}
