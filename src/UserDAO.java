
import java.sql.*;
import java.util.*;

public class UserDAO {
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("����̹� �ε� ����!");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("����̹� �ε� ����");
			e.printStackTrace();
		}
	}

	public static void close(Connection con, PreparedStatement st) {
		try {
			con.close();
			st.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("���� ���� ����");
		}
	}

	public static void close(Connection con, PreparedStatement st, ResultSet rs) {
		try {
			con.close();
			st.close();
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("���� ���� ����");
		}
	}

	public static Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/pc";
		String user = "root";
		String password = "1234";
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return con;
	}

	public static int loginCheck(String id, String pwd) {
		String sql = "select id,pwd,remaintime from userinfo where id=? and pwd=?";
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int returnValue = 3;
		try {
			System.out.println("�α�������");
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, id);
			st.setString(2, pwd);
			rs = st.executeQuery();

			while (rs.next()) {// �α��μ����� 1
				if (rs.getString("id").equals(id) && rs.getString("pwd").equals(pwd) && rs.getInt("remaintime") != 0) {
					returnValue = 1;
				} else if (rs.getInt("remaintime") == 0) {
					returnValue = 2;
				} else {
					returnValue = 3;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			System.out.println("�α����̻���� ����");
			close(con, st, rs);
		}
		return returnValue;
	}

	public static boolean insert(UserDTO userDTO) {
		String sql = "insert into userinfo values(?,?,?,?,?,?,?,?)";
		Connection con = null;
		PreparedStatement st = null;
		boolean returnValue = false;
		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, userDTO.getId());
			st.setString(2, userDTO.getPwd());
			st.setString(3, userDTO.getName());
			st.setString(4, userDTO.getEmail());
			st.setString(5, userDTO.getPhoneNumber());
			st.setString(6, userDTO.getAddress());
			st.setInt(7, userDTO.getRemainTime());
			st.setInt(8, userDTO.getMountOfMoney());
			if (st.executeUpdate() == 1) { // ���������� ���ϵǸ� true �� ��ȯ
				System.out.println("���Լ���!");
				returnValue = true;
			} else {
				System.out.println("���Խ���!");
				returnValue = false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			System.out.println("�̻��������");
			close(con, st);
		}
		return returnValue;
	}

	public static boolean verfyID(String id) {// ���̵��ߺ�üũ
		boolean returnValue = false;// ���ϰ�
		String sql = "select id from userinfo where id=?";// sql��
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			System.out.println("��ȸ����");
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, id);
			rs = st.executeQuery();
			while (rs.next()) {// ���� id ���� ���� ��ȸ�� ������ return ���� ����
				if (id.equals(rs.getString("id"))) {
					returnValue = true;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st, rs);
		}
		return returnValue;
	}

	public static String idPw(String name, String email) {
		String sql = "select id,pwd from userinfo where name=? and email=?";
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String returnValue = "�ش��ϴ� �̸� �Ǵ� ��й�ȣ�� �������� �ʽ��ϴ�";
		try {
			System.out.println("�α�������");
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, name);
			st.setString(2, email);
			rs = st.executeQuery();

			while (rs.next()) {
				returnValue = "���̵�:" + rs.getString("id") + "/" + "��й�ȣ:" + rs.getString("pwd");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			System.out.println("name �� email ��ȸ�̻���� ����");
			close(con, st, rs);
		}
		return returnValue;
	}

	public static UserDTO clientInfo(String id) {// ���̵𿡴��� ����������
		UserDTO returnValue = null;// ���ϰ�
		String sql = "select id,mountofmoney,remaintime from userinfo where id=?";// sql��
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			System.out.println("��ȸ����");
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, id);
			rs = st.executeQuery();
			while (rs.next()) {// ���� id ���� ���� ��ȸ�� ������ return ���� ����
				returnValue = new UserDTO(rs.getString("id"), rs.getInt("mountofmoney"), rs.getInt("remaintime"));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st, rs);
		}
		return returnValue;
	}

	////////////////////////// �̸��� ���� ��ü��ȸ
	public static ArrayList<UserDTO> clientAll(String id) {// ���̵𿡴��� ����������
		ArrayList<UserDTO> al = new ArrayList<UserDTO>();

		String sql = "select * from userinfo where id=?";// sql��
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, id);
			// st.setString(1, id);
			rs = st.executeQuery();
			while (rs.next()) {// ���� id ���� ���� ��ȸ�� ������ return ���� ����
				al.add(new UserDTO(rs.getString("id"), rs.getString("pwd"), rs.getString("name"), rs.getString("email"),
						rs.getString("phonenumber"), rs.getString("address"), rs.getInt("remaintime"),
						rs.getInt("mountofmoney")));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st, rs);
		}
		return al;
	}

	////////////////////////// �̸��� ���� ��ü��ȸ
	public static Vector<UserDTO> clientIdInfo(String id) {// ���̵𿡴��� ����������
		Vector<UserDTO> v = new Vector<UserDTO>();

		String sql = "select * from userinfo where id=?";// sql��
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, id);
			// st.setString(1, id);
			rs = st.executeQuery();
			while (rs.next()) {// ���� id ���� ���� ��ȸ�� ������ return ���� ����
				v.add(new UserDTO(rs.getString("id"), rs.getString("pwd"), rs.getString("name"), rs.getString("email"),
						rs.getString("phonenumber"), rs.getString("address"), rs.getInt("remaintime"),
						rs.getInt("mountofmoney")));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st, rs);
		}
		return v;
	}

	////////////////////////////// �� ��ü ��ȸ
	public static Vector<UserDTO> userAll() {// ���̵𿡴��� ����������
		Vector<UserDTO> v = new Vector<UserDTO>();

		String sql = "select * from userinfo";// sql��
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			rs = st.executeQuery();
			while (rs.next()) {// ���� id ���� ���� ��ȸ�� ������ return ���� ����
				v.add(new UserDTO(rs.getString("id"), rs.getString("pwd"), rs.getString("name"), rs.getString("email"),
						rs.getString("phonenumber"), rs.getString("address"), rs.getInt("remaintime"),
						rs.getInt("mountofmoney")));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st, rs);
		}
		return v;
	}

	//////////////////////////////////////////////////////////////
	/////////////////////////////// �������� ��ü ��ȸ//////////////////

	public static Vector<FoodDTO> foodAll() {// ���̵𿡴��� ����������
		Vector<FoodDTO> v = new Vector<FoodDTO>();

		String sql = "select * from food";// sql��
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			// st.setString(1, id);
			rs = st.executeQuery();
			while (rs.next()) {// ���� id ���� ���� ��ȸ�� ������ return ���� ����
				v.add(new FoodDTO(rs.getString("name"), rs.getInt("price")));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st, rs);
		}
		return v;
	}

	public static ArrayList<FoodDTO> foodSearch(String name) {// ���̵𿡴��� ����������
		ArrayList<FoodDTO> v = new ArrayList<FoodDTO>();

		String sql = "select name,price from food where name=?";// sql��
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, name);
			rs = st.executeQuery();

			while (rs.next()) {// ���� id ���� ���� ��ȸ�� ������ return ���� ����
				v.add(new FoodDTO(rs.getString("name"), rs.getInt("price")));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st, rs);
		}
		return v;
	}

	////////////////////////////// ������Ʈ
	public static boolean addTime(String oid, int time, int oMoney) {// ���̵��ߺ�üũ
		boolean returnValue = false;// ���ϰ�
		String sql = "update userinfo set remaintime=remaintime+?,mountofmoney=mountofmoney+? where id=? ";// sql��
		Connection con = null;
		PreparedStatement st = null;

		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setInt(1, time);
			st.setInt(2, oMoney);
			st.setString(3, oid);
			if (st.executeUpdate() == 1) {
				returnValue = true;
			} else {
				returnValue = false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st);
		}
		return returnValue;
	}

	///// �ð�����
	////////////////////////////// ������Ʈ
	public static boolean deleteTime(String oid, int time, int oMoney) {// ���̵��ߺ�üũ
		boolean returnValue = false;// ���ϰ�
		String sql = "update userinfo set remaintime=remaintime-?,mountofmoney=mountofmoney-? where id=? ";// sql��
		Connection con = null;
		PreparedStatement st = null;

		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setInt(1, time);
			st.setInt(2, oMoney);
			st.setString(3, oid);
			if (st.executeUpdate() == 1) {
				returnValue = true;
			} else {
				returnValue = false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st);
		}
		return returnValue;
	}

	////
	/////////////////////////// ����
	public static boolean deleteUser(String id) {// ���̵��ߺ�üũ
		boolean returnValue = false;// ���ϰ�
		String sql = "delete from userinfo where id=? ";// sql��
		Connection con = null;
		PreparedStatement st = null;

		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, id);
			if (st.executeUpdate() == 1) {
				returnValue = true;
			} else {
				returnValue = false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st);
		}
		return returnValue;
	}

	////
	////////////////////////////////////////// ��簪�� ������ ������Ʈ
	public static boolean addUserUpdate(UserDTO userDTO) {// ���̵��ߺ�üũ
		boolean returnValue = false;// ���ϰ�
		String sql = "insert into userinfo values(? , ? , ? , ? , ? , ? , ? , ? )";// sql��
		Connection con = null;
		PreparedStatement st = null;

		try {
			con = getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, userDTO.getId());
			st.setString(2, userDTO.getPwd());
			st.setString(3, userDTO.getName());
			st.setString(4, userDTO.getEmail());
			st.setString(5, userDTO.getPhoneNumber());
			st.setString(6, userDTO.getAddress());
			st.setInt(7, userDTO.getRemainTime());
			st.setInt(8, userDTO.getMountOfMoney());

			if (st.executeUpdate() == 1) {
				returnValue = true;
			} else {
				returnValue = false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close(con, st);
		}
		return returnValue;
	}
	///////////////////////////////

}
