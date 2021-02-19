import java.io.*;//단순히 파일 입출력이라 이렇게만 함
import java.net.*;//소켓을 쓰기 위해 사용.

import javax.swing.JTextArea;

public class MyReceiver extends Thread {

	Socket socket;
	String data;
	JTextArea area;
	public MyReceiver(Socket socket,JTextArea area) {
		this.socket = socket;
		this.area=area;
	}

	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((data=br.readLine())!=null) {
				area.append(data+"\n");
			}
		} catch (Exception e) {

		}
	}

}
