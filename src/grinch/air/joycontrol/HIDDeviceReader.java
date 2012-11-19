package grinch.air.joycontrol;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;

import javax.swing.Timer;

import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDManager;

public class HIDDeviceReader extends Observable{
	
	private static int vendorID = 0;
    private static int productID = 0;
    
    public void changeDevice(int vendorID, int productID){
    	tmrConnected.stop();
    	HIDDeviceReader.vendorID = vendorID;
    	HIDDeviceReader.productID = productID;
    	darkTornado = null;	
    	setHooking(false);
    	setChanged();
    	tmrConnected.start();
    }
    private static final int BUFSIZE = 2048;
    private static int readUpdateDelayMs = 100;
    
    public int getReadUpdateDelayMs() {
		return readUpdateDelayMs;
	}
	static HIDManager manager;
    static HIDDevice darkTornado = null;
    
    private Boolean connected = false;
    private static Timer tmrConnected;
    public void setReadUpdateDelayMs(int readUpdateDelayMs) {
		HIDDeviceReader.readUpdateDelayMs = readUpdateDelayMs;
		tmrConnected.setDelay(readUpdateDelayMs);
	}
    private boolean hooking = false;
    
    public boolean isHooking() {
		return hooking;
	}

	public void setHooking(boolean hooking) {
		this.hooking = hooking;
	}
	private byte[] data = new byte[BUFSIZE];
    
    /**
     * Packages' bytes
     * @return Package
     */
    public byte[] getData() {
		return data;
	}
    
	private int countBytes;
 
	/**
	 * Packages' bytes count
	 * @return bytes count
	 */
    public int getCountBytes() {
		return countBytes;
	}
    
	private boolean isConnected() {
		return connected;
	}
	
	private void setConnected() {
		boolean preState = connected;
		if (darkTornado == null){
			this.connected = false;
		}else{
			this.connected = true;
		}
		if (preState != connected)
			setChanged();
		notifyObservers(isConnected());
	}
    
    public HIDDeviceReader(){
    	try {
			manager = HIDManager.getInstance();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	setChanged();
    	tmrConnected = new Timer(readUpdateDelayMs, new ActionListener()
		  {
		      public void actionPerformed(ActionEvent e)
		      {
		    		HIDDevice dev = null;
					if (darkTornado == null){
						try {
							dev = manager.openById(HIDDeviceReader.vendorID, HIDDeviceReader.productID, null);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						darkTornado = dev;
					}					
					if (darkTornado != null){
						try {
							countBytes = darkTornado.read(data);
							if (isHooking()) setChanged();
						} catch (IOException e1) {
							darkTornado = null;
						}
					}
					setConnected();
		      }
		  } );
    }
    
    /**
	 * Start hooking
	 */
	public void start(){
		tmrConnected.start();
	}
	
    /**
	 * Stop hooking
	 */
	public void stop(){
		tmrConnected.stop();
	}
}
