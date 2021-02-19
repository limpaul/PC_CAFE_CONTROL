

import javax.swing.*;
import java.awt.*;


public class GameView extends JFrame {
	public GameView(String name) {// 740x380 1280x720
		Container c = getContentPane();
		if (name.equals("������ġ")) {
			c.add(new JLabel(new ImageIcon("game/overwatch.png")), "Center");
			setSize(740, 380);
			setVisible(true);
		}
		
		if (name.equals("LOL")) {
			c.add(new JLabel(new ImageIcon("game/lol.jpg")), "Center");
			setSize(1280, 720);
			setVisible(true);
		}
		
		if (name.equals("���ͳ�")) {
			c.add(new JLabel(new ImageIcon("game/internet.png")), "Center");
			setSize(810, 377);
			setVisible(true);
		}
		if (name.equals("��Ʋ�׶���")) {
			c.add(new JLabel(new ImageIcon("game/bettleground.png")), "Center");
			setSize(1920,1080);
			setVisible(true);
		}
	}
}
