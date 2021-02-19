import java.net.*;
import java.util.ArrayList;
import java.io.*;
 
import javax.swing.*;
 
public class ChatServer extends JFrame {
   
   
    JTextArea ta;
    JScrollPane pane;
    ServerSocket ss;
    Socket s;
    ArrayList<ChatThread> list;
   
    public ChatServer(){
        super("ä�� ���� v1.0.1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
       
        ta = new JTextArea();
        pane = new JScrollPane(ta);
        add(pane);
        ta.setText("ä�� ���� ���۵�!\n");
       
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
       
        //��Ʈ��ũ �ڵ�
        try{
            list = new ArrayList<>();
            ss = new ServerSocket(1995);
            while(true){
               
                s = ss.accept(); //���ӵǾ�¼����� ������������ s�� ��´�.
                ChatThread t = new ChatThread();
                list.add(t); //��Ƽ �����带 ����
                t.start();
               
            }
        }catch(Exception e){
            e.printStackTrace();
        }
       
       
       
    }//������
    class ChatThread extends Thread{
       
        BufferedReader br; //�ѱ�OK , ���پ� �Է� OK
        PrintWriter pw; //�ѱ� OK, ���پ� ��� OK
        String nickName;
        public ChatThread(){
            try{
          
                //����
                br=new BufferedReader(new InputStreamReader(s.getInputStream()));
                pw=new PrintWriter(new OutputStreamWriter(s.getOutputStream()),true);
            }catch(Exception e){
                e.printStackTrace();
            }
           
        }//���� Ŭ������ ������
        
        public void send(String str) { //ä�ó��� ����
            pw.println(str);
        }
        @Override
        public void run(){
        	String data="";
            try {
                while((data=br.readLine())!=null){                 
                    broadcast(data);
                    ta.append(data+"\n");//area �� append
                }//while
            } catch (Exception e) {
                	System.out.println("����� ����");
            }
        }
    }
    //inner class ChatThread end
    //�ܺ� Ŭ������ �޼ҵ�
    public void broadcast(String str){ //��ü �ֵ����� �����ֵ��� ���� 
        for (int i = 0; i < list.size(); i++) {
            ChatThread t = (ChatThread)list.get(i);
            t.send(str);
        }
       
    }
    public static void main(String[] args) {
		new ChatServer();
	}
   
   
}