

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FoodSearch extends JDialog implements ActionListener {
	JTextField searchField;
	JLabel showImage;
	JLabel list;
	JButton searchOk, add, cancel;

	public FoodSearch(String searchName, JFrame frame) {
		super(frame,true);
		Container c = getContentPane();
		JPanel p = new JPanel();
		searchField = new JTextField(searchName, 23);
		searchOk = new JButton("�˻�");
		showImage = new JLabel(new ImageIcon("search.png"));
		searchOk.addActionListener(this);
		p.add(searchField);
		p.add(searchOk);
		p.add(showImage);
		c.add(p, "North");

		JPanel p2 = new JPanel();
		list = new JLabel("��ǰ�̸�: �˻����ּ���!      ����: �˻����ּ���!");
		p2.add(list);
		c.add(p2, "Center");

		JPanel p3 = new JPanel();
		add = new JButton("�߰�");
		cancel = new JButton("���");
		p3.add(add);
		p3.add(cancel);
		add.addActionListener(this);
		cancel.addActionListener(this);
		c.add(p3, "South");
		setSize(500, 500);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		if (cmd.equals("�˻�")) {
			String name = searchField.getText();
			ArrayList<FoodDTO> searchV = UserDAO.foodSearch(name);
			searchV.add(new FoodDTO("", 0));
			for (int i = 0; i < searchV.size(); i++) {

				if (searchV.get(i).getName().equals(name)) {// ��ǰ���̶� ��ġ�ϴ°� �ִٸ�
					list.setText("��ǰ�̸�: " + searchV.get(i).getName() + "   /   " + " ����: " + searchV.get(i).getPrice());
					String imagePath = "�����/" + searchV.get(i).getName() + ".jpg";
					showImage.setIcon(new ImageIcon(imagePath));
					break;
				}
				i++;
				if (i >= searchV.size()) {
					showImage.setIcon(new ImageIcon("sorry.gif"));
					list.setText("����");
				}
			}

		}
		if (cmd.equals("�߰�")) {
			
		}
		if (cmd.equals("���")) {
			setVisible(false);
		}

	}

}
