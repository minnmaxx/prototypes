package innerClass;

public class Grandparent {

	public String getString() {
		throw new UnsupportedOperationException();
	}
	
	public void doGet() {
		System.out.println( getString() );
	}
}
