package grinch.air.joycontrol;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;

import javax.swing.Timer;

import com.codeminders.hidapi.HIDDeviceInfo;
import com.codeminders.hidapi.HIDManager;

/**
 * Getting HID Devices List
 * @author Grinch
 */
public class HIDList extends Observable {
	private int READ_UPDATE_DELAY_MS = 500;
	private Timer timer;
	private HIDDeviceInfo[] devs;
	private HIDManager manager;
	
	private HIDDeviceInfo[] getCurrentDevices() throws IOException{
    	return manager.listDevices();
    }
	
    public HIDList() {
		try {
			this.manager = HIDManager.getInstance();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.devs = new HIDDeviceInfo[0];
		timer = new Timer(READ_UPDATE_DELAY_MS, new ActionListener()
		  {
		      public void actionPerformed(ActionEvent e)
		      {
		    	  HIDDeviceInfo[] curDevs = null;
					try {
						curDevs = getCurrentDevices();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    		if (curDevs.length != devs.length){
		    			devs = curDevs;
		    			setChanged();
		    			notifyObservers(devs);
		    		}
		      }
		  } );
    }
    
    /**
     * Start scan    
     */
    public void start(){
    	timer.start();
    }
    /**
     * Stop scan
     */
    public void stop(){
    	timer.stop();
    }
}
