import java.io.*;//단순히 파일 입출력이라 이렇게만 함
import java.net.*;//소켓을 쓰기 위해 사용.
import java.util.*; //혹은 *대신에 Scanner라고 해도됨.

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
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream())); // 스트림생성
		} catch (Exception e) {
			System.out.println("된다");
		}
	}
	public void sendMsg(String msg) {
		
		if (!msg.equals("")) { // textField 값이 null이 아니면
			pw.println("[" + myNick + "]"+msg);
			pw.flush();
		}else {
			System.out.println("빈칸");
		}
	}

}
