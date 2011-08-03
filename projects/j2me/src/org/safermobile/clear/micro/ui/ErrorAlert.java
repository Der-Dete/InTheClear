package org.safermobile.clear.micro.ui;

import javax.microedition.lcdui.*;
import org.j4me.ui.*;
import org.j4me.ui.components.*;
import org.safermobile.clear.micro.L10nConstants;
import org.safermobile.clear.micro.L10nResources;
import org.safermobile.clear.micro.apps.LocaleManager;

/**
 * Objects of this class alert the user about an error.
 */
public class ErrorAlert
	extends Dialog implements OnClickListener
{
	
	L10nResources l10n = LocaleManager.getResources();

	/**
	 * The screen that will be returned to once the user dismisses this error
	 * alert.
	 */
	protected final DeviceScreen back;
	protected final DeviceScreen next;
	
	/**
	 * The UI component that displays the error message.
	 */
	private Label label = new Label();
	
	/**
	 * Constructs an error alert screen.
	 * 
	 * @param title is the title of the screen.
	 * @param error is the reason of the failure.  Often this will include
	 *  a <code>Throwable.toString</code>.
	 * @param parent is the screen to return to once the user dismisses
	 *  this error alert.
	 */
	public ErrorAlert (String title, String error, DeviceScreen back, DeviceScreen next)
	{
		// Set the alert title.
		setTitle( title );
		
		// Set the error text.
		label.setHorizontalAlignment( Graphics.LEFT );
		label.setLabel( error );
		append( label );
		
		// Add the menu buttons.
		//setMenuText( back != null?"Back":null, next!=null?"Next":null );

		Button btn = new Button();
		btn.setOnClickListener(this);
		btn.setLabel(l10n.getString(L10nConstants.keys.MENU_OK));
		append (btn);
		
		// Record the parent screen.
		this.back = back;
		this.next = next;
	}
	
   public boolean hasMenuBar ()
   {
   	return false;
   }
   
	public void setText (String msg)
	{
		label.setLabel(msg);
	}
	/**
	 * @return The error message.
	 */
	public String getText ()
	{
		return label.getLabel();
	}
	
	/**
	 * Responds to the user dismissing this screen.
	 * 
	 * @see DeviceScreen#declineNotify()
	 */
	public void declineNotify ()
	{
		if ( back != null )
		{
			// Return to the parent screen.
			back.show();
		}
	}
		
	protected void acceptNotify() {
		if ( next != null )
		{
			// Return to the parent screen.
			next.show();
		}
		
		
		
	}

	/**
	 * Notifies the user of an error.
	 * 
	 * @see DeviceScreen#showNotify()
	 */
	public void showNotify ()
	{
		// Play the error sound.
		Display display = UIManager.getDisplay();
		AlertType.ERROR.playSound( display );
		
		// Vibrate the device.
		display.vibrate( 1000 );  // 1 second
		
		// Continue processing the event.
		super.showNotify();
	}

	public void onClick(Component c) {
		
		if (next != null)
			acceptNotify();
		else
			declineNotify();
		
	}
}
