import java.io.*;//�ܼ��� ���� ������̶� �̷��Ը� ��
import java.net.*;//������ ���� ���� ���.
import java.util.*; //Ȥ�� *��ſ� Scanner��� �ص���.

public class MySender extends Thread {

	Socket socket;
	String myNick;
	String sendMsg;
	PrintWriter pw;

	public MySender(String myNick, Socket socket) {
		this.myNick = myNick;
		this.socket = socket;
	}
	public void run() {
		try {
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream())); // ��Ʈ������
		} catch (Exception e) {
			System.out.println("�ȴ�");
		}
	}
	public void sendMsg(String msg) {
		
		if (!msg.equals("")) { // textField ���� null�� �ƴϸ�
			pw.println("[" + myNick + "]"+msg);
			pw.flush();
		}else {
			System.out.println("��ĭ");
		}
	}

}
