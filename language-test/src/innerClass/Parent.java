package innerClass;

public class Parent extends Grandparent {

	@Override
	public String getString() {
		return "Parent";
	}
	
	public void executeChild() {
		(new ChildClass()).print();
	}
	
	private class ChildClass {
		
		void print() {
			doGet();
		}
	}
	
	public static void main(String[] args) {
		(new Parent()).executeChild();
	}
}
