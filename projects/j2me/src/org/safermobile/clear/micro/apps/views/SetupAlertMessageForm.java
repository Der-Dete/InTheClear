package org.safermobile.clear.micro.apps.views;


import javax.microedition.lcdui.Graphics;

import org.j4me.ui.*;
import org.j4me.ui.components.*;
import org.safermobile.clear.micro.L10nConstants;
import org.safermobile.clear.micro.L10nResources;
import org.safermobile.clear.micro.apps.LocaleManager;
import org.safermobile.clear.micro.apps.PanicConfigMIDlet;
import org.safermobile.clear.micro.apps.PanicConstants;

/**
 * Example of a <code>TextBox</code> component.
 */
public class SetupAlertMessageForm
        extends Dialog implements Runnable
{
        /**
         * The previous screen.
         */
        private PanicConfigMIDlet _midlet;
        
        /**
         * The number box used by this example for entering phone numbers.
         */
        private TextBox alertMsg;
        
    	private Label _label = new Label();

    	L10nResources l10n = LocaleManager.getResources();

        /**
         * Constructs a screen that shows a <code>TextBox</code> component in action.
         * 
         * @param previous is the screen to return to once this done.
         */
        public SetupAlertMessageForm (PanicConfigMIDlet midlet)
        {
               _midlet = midlet;
                
                // Set the title and menu.
                setTitle(l10n.getString(L10nConstants.keys.PANIC_MESSAGE_TITLE) );
                setMenuText(  l10n.getString(L10nConstants.keys.MENU_BACK) ,  l10n.getString(L10nConstants.keys.MENU_NEXT) );

             // Center the text.
        		_label.setHorizontalAlignment( Graphics.LEFT );

        		_label.setLabel(l10n.getString(L10nConstants.keys.PANIC_MESSAGE_INFO));
        		
        		// Add the label to this screen.
        		append( _label );
        		
                
                // Add the phone number box.
                alertMsg = new TextBox();
                alertMsg.setLabel(l10n.getString(L10nConstants.keys.PANIC_MESSAGE_LBL_MSG));                
                append( alertMsg );
               
                load();
        }

        private boolean persist ()
        {
        	if (alertMsg.getString().length() > 0)
        	{
        		_midlet.savePref(PanicConstants.PREFS_KEY_MESSAGE, alertMsg.getString());
        		return true;
        	}
        	
        	return false;
        }
        
        private void load ()
        {
        	
        	String alertMsgString = _midlet.loadPref(PanicConstants.PREFS_KEY_MESSAGE);
        	
        	if (alertMsgString != null && alertMsgString.length() > 0)
        		alertMsg.setString(alertMsgString);
        	else
        		alertMsg.setString(l10n.getString(L10nConstants.keys.DEFAULT_ALERT_MSG));
        }
        
        
        public void run ()
        {
        	if (persist())
        	{
        		_midlet.showNext();
        	}
        	else
        	{
            	_midlet.showAlert(l10n.getString(L10nConstants.keys.TITLE_ERROR), l10n.getString(L10nConstants.keys.ERROR_PREFS), 0);
        		
        	}
        }
        
        /**
         * Takes the user to the previous screen.
         */
        protected void declineNotify ()
        {
               _midlet.showPrev();
        }
        
        protected void acceptNotify() 
        {		
        	new Thread(this).start();
		}
}
