package test;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.jpackages.jflashplayer.FlashPanel;
import com.jpackages.jflashplayer.JFlashInvalidFlashException;
import com.jpackages.jflashplayer.JFlashLibraryLoadFailedException;

public class FlashControllerFrame extends JFrame  {

	private static final long serialVersionUID = 1L;

	//private static final String SWF = "C:/Workspace/tests/jflashplayer-flex/bin-debug/Main.swf";
	private static final String SWF = "C:/Workspace/tests/flex-spring-security/bin-debug/AutoClient.swf";
	private static final String FLASH_VERSION_REQUIRED = "9";
	
	private final Map<String,FlashPanel> flashPanels = Collections.synchronizedMap(
			new HashMap<String,FlashPanel>() );
	private final Map<String,Object> initialized = Collections.synchronizedMap(
			new HashMap<String,Object>() );
	
	static {
		
		// install Flash 10 if Flash 6 or greater is not present
		FlashPanel.installFlash("6");
		
		try {
			if( !FlashPanel.hasFlashVersion( FLASH_VERSION_REQUIRED ) ) {
				handleException( "Required version " + FLASH_VERSION_REQUIRED + " of Flash is not installed.", null );
			}
			FlashPanel.setRequiredFlashVersion( FLASH_VERSION_REQUIRED );
		} catch (JFlashLibraryLoadFailedException e) {
			handleException("A required library (DLL) is missing or damaged.", e );
		}			
	}
	
	
	/**
	 * Create a FlashPanel instance and add it to the frame
	 */
	private FlashPanel createFlashPanel( String id, String password ) {
		
		try {
			FlashPanel flashPanel = new FlashPanel(new File( SWF ));	

			// specify the object for Flash ExternalInterface.call events to search for the called method on
			flashPanel.setFlashCallObject(this);
			flashPanel.setVariables( "username=" + id + "&password=" + password );
			
			return flashPanel;
		
		} catch (JFlashLibraryLoadFailedException e) {			
			handleException("A required library (DLL) is missing or damaged.", e );
		} catch (FileNotFoundException e) {
			handleException("Failed to find SWF file specified.", e );
		} catch (JFlashInvalidFlashException e) {
			handleException( "Required version " + FLASH_VERSION_REQUIRED + " of Flash is not installed.", e );
		} 
		
		return null;
	}
	
	public void onFlashInitialized( String id ) {
		System.out.println( id + " initialized");

		final String constId = id;
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				//FlashPanel flashPanel = flashPanels.get( constId );
				initialized.put( constId, constId );
				if( initialized.size() == flashPanels.size() ) {
					
					try {
						Thread.sleep( 5000 );
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					for( FlashPanel app : flashPanels.values() ) {
						callFlashAction( app );
					}
				}
			}
		});
	}
	
	public void onFlashFinished( String id ) {
		
		System.out.println( id + " finished" );

		final String constId = id;
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				flashPanels.remove( constId );
				if( flashPanels.isEmpty() ) {
					System.exit(0);
				}
			}
		});
	}
	
	public void onFlashFault( String id, String message ) {
		System.out.println( "Flash ERROR: " + id );
		System.out.println( message );
	}
	
	public void onFlashLog( String id, String message ) {
		System.out.print( "LOG<" + id + "> " );
		System.out.println( message );		
	}

	/**
	 * Constructor of Example Frame
	 */
	public FlashControllerFrame( String id ) {
		
		JPanel content = new JPanel();
		content.setLayout( new BorderLayout() );
		
		//FlowLayout layout = new FlowLayout( FlowLayout.LEFT );
		//content.setLayout( layout );
		
		//content.add( new JButton("1") );
		//content.add( new JButton("2") );
		
		String[] positions = new String[] {
			BorderLayout.EAST,
			BorderLayout.CENTER,
			BorderLayout.WEST
		};
		
//		for( int i = 1; i <= 1; i++ ) {
//			
//			String id = Integer.toString( i );
			FlashPanel flashPanel = createFlashPanel( id, id );
			flashPanels.put( id, flashPanel );
			
//			JPanel container = new JPanel();
//			container.setLayout( new BorderLayout() );			
//			container.add( flashPanel, BorderLayout.CENTER );
			
			content.add( flashPanel, BorderLayout.CENTER  );
//		}
				
		//
		this.setSize(500, 400);
		this.setContentPane( content );
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}

	/**
	 * Call a flash function and display the return value.
	 */
//	public void callFlashAction( FlashPanel flashPanel ) {
////		// example arguments to pass to Flash
////		String arg1 = "a string as first arg";
////		Boolean arg2 = new Boolean(true); // change this to false to see a different response from Flash
////		Double arg3 = new Double(3);
////		String[] arg4 = new String[] { "this", "is", "an", "array" };
//
//		// call the Flash function flashFunctionName as specified by ExternalInterface.addCallback
//		// pass to the function arg1, arg2, arg3, and arg4
//		
//		Random random = new Random( (new Date()).getTime() );
//		int number = random.nextInt();
//		
//		String callResponse = (String) flashPanel.callFlashFunction(
//				"connect", new Object[] { Integer.toString( number ) });
//		System.out.println("connet: " + callResponse);
//	}

	public void callFlashAction( final FlashPanel flashPanel ) {
		
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				flashPanel.callFlashFunction( "start", new Object[]{} );			
			}
		});		
	}
	
	/**
	 * Called if no flash is present then exit application
	 */
	private static void handleException(String message, Exception e) {
		System.out.print( message );
		if( e != null ) {
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	/**
	 * Main method defines the example frame
	 */
	public static void main(String[] args) {
		try {
			final FlashControllerFrame frame = new FlashControllerFrame( args[0]);	
			frame.setVisible( true );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
