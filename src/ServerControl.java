
import java.io.*;
import java.net.*;

import javax.swing.*;
 
public class ServerControl implements Runnable{ //���� ������ Client �� �Ǿ� Client ���� ����� �Ҽ� �ֵ��� ���ش� 3333 �� ������Ʈ 
	// Ŭ���̾�Ʈ �ʵ�
		Socket cSocket;
		BufferedReader br;
		PrintWriter pw;
		String data;
	public ServerControl(JButton[] btn,String text) {
		for(int i=0;i<btn.length;i++) {
			if(btn[i].getText().equals(text)) {
				btn[i].setIcon(null);
				btn[i].setText("��밡��");
			}
		}
	}
	
	
	// =========================Ŭ���̾�Ʈ ����================================
	public void run() {
		try {
			cSocket = new Socket("127.0.0.1", 3333);
			br = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));// ���Ž�Ʈ�� ����
			pw = new PrintWriter(new OutputStreamWriter(cSocket.getOutputStream()));// ��½�Ʈ�� ����
			pw.println("shutdown");//4��° ���ڿ��� pc��ȣ�� �Է¹���
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
