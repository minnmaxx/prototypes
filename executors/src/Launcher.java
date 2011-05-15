import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Launcher {

	
	public static void main( String[] args ) {
		
		ExecutorService executor = Executors.newScheduledThreadPool(1);
		
		executor.execute( new Runnable() {
			
			@Override
			public void run() {
				System.out.println( "thread" );
			}
		});
		
		System.out.println( "main" );
		
		executor.shutdown();
	}
}
