
import java.util.*;


public class FoodValue {
	private String name;//�����̸� 
	private int value; //���� 
	private int many;  //����
	static Vector<FoodDTO> v=UserDAO.foodAll();

	public FoodValue() {
		// TODO Auto-generated constructor stub
	}
	public FoodValue(String name) {
		this.name=name;
	}
	public FoodValue(String name,int value) {
		this.name=name;
		this.value=value;
	}
	public FoodValue(String name,String text) {
	
	}
	
	public FoodValue returnValue(String name) {
		FoodValue returnValue;
	
		for(int i=0;i<v.size();i++) {
			if(name.equals(v.get(i).getName())) {
				String name1=v.get(i).getName();
				int value=v.get(i).getPrice();
				returnValue= new FoodValue(name1,value);
			
				return returnValue;
			}
		}
		System.out.println("������ null�Ǿ��׿�");
		return new FoodValue("����",0);
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getMany() {
		return many;
	}

	public void setMany(int many) {
		this.many = many;
	}

}
