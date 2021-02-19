import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

public class TimeSynChronize  extends Thread{
	private String user;
	private int time;
	private Socket cSocket;
	private BufferedReader br;
	private PrintWriter pw;
	private String update;
	public TimeSynChronize(String user,int time,String update) {
		this.user=user;
		this.time=time;
		this.update=update;
	}
	
	public void run() {
		try {
			cSocket = new Socket("127.0.0.1", 3333);
			br = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));// ���Ž�Ʈ�� ����
			pw = new PrintWriter(new OutputStreamWriter(cSocket.getOutputStream()));// ��½�Ʈ�� ����
			System.out.println("����ȭ"+"/"+update+"/"+user+"/"+time);
			pw.println("����ȭ"+"/"+update+"/"+user+"/"+time);//4��° ���ڿ��� pc��ȣ�� �Է¹���
			
			pw.flush();
			pw.close();
			br.close();
			cSocket.close();
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "���� �������", "���", JOptionPane.WARNING_MESSAGE);
			System.out.println("��������"+e);
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
}
