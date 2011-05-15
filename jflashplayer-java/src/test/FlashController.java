package test;

import java.io.File;
import java.io.FileNotFoundException;

import com.jpackages.jflashplayer.FlashPanel;
import com.jpackages.jflashplayer.JFlashInvalidFlashException;
import com.jpackages.jflashplayer.JFlashLibraryLoadFailedException;


public class FlashController {

	private FlashPanel flashPanel;

	/**
	 * Create a FlashPanel instance and add it to the frame
	 */
	public void createFlashPanel() {
		
		// install Flash 10 if Flash 6 or greater is not present
		FlashPanel.installFlash("6");
				
		String flashVersionRequired = "9";
		try {
			// if there is Flash 9 or greater present, show Movie.swf
			// otherwise show Movie-FSCommand.swf
			String flashFilePath = "example/Main.swf";								
			FlashPanel.setRequiredFlashVersion(flashVersionRequired);

			// construct a FlashPanel displaying the SWF flash animation file
			flashPanel = new FlashPanel(new File(flashFilePath));
			
		} catch(JFlashLibraryLoadFailedException e) {			
			System.out.println("A required library (DLL) is missing or damaged.");
			e.printStackTrace();
		} catch(FileNotFoundException e) {
			System.out.println("Failed to find SWF file specified.");
			e.printStackTrace();
		} catch(JFlashInvalidFlashException e) {
			System.out.println("Required version " + flashVersionRequired + " of Flash is not installed.");
			e.printStackTrace();
		}

		// specify the object for Flash ExternalInterface.call events to search for the called method on
		flashPanel.setFlashCallObject(this);		
	}
	
	public void callFlashFunction() {

		// call the Flash function flashFunctionName as specified by ExternalInterface.addCallback
		// pass to the function arg1, arg2, arg3, and arg4
		String callResponse = (String) flashPanel.callFlashFunction(
				"connect", new Object[]{ });
		System.out.println("callResponse: " + callResponse);
	}
	
	public static void main(String[] args) {
		
		FlashController controller = new FlashController();
		controller.createFlashPanel();
		controller.callFlashFunction();
	}
}
