

public class UserDTO {
	private String id;//���̵�
	private String pwd;//��й�ȣ
	private String name;//�̸�
	private String email;//�̸���
	private String phoneNumber;//����ȣ
	private String address;//�ּ�
	private int remainTime;//�ܿ��ð�
	private int mountOfMoney;//�ѱݾ� 
	
	public UserDTO() {
		
	}
	public UserDTO(String id,int mountOfmoney,int remainTime) {
		this.id=id;
		this.mountOfMoney=mountOfmoney;
		this.remainTime=remainTime;
	}
	public UserDTO(String id,String pwd,String name,String email,String phoneNumber,String address,int remainTime,int mountOfMoney) {
		this.id=id;
		this.pwd=pwd;
		this.name=name;
		this.email=email;
		this.phoneNumber=phoneNumber;
		this.address=address;
		this.remainTime=remainTime;
		this.mountOfMoney=mountOfMoney;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getRemainTime() {
		return remainTime;
	}
	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}
	public int getMountOfMoney() {
		return mountOfMoney;
	}
	public void setMountOfMoney(int mountOfMoney) {
		this.mountOfMoney = mountOfMoney;
	}
	
	/*   db ���̺� ���� �� sql �� 
	 create table userinfo(id varchar(20) primary key,pwd varchar(20) not null
	,name varchar(20) not null,eamil varchar(20) not null
	,phonenumber varchar(20) not null,address varchar(30) not null
	,remaintime int(25) not null,mountofmoney int(25) not null);
	 */
	
	
}
