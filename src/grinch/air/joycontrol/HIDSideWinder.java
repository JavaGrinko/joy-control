package grinch.air.joycontrol;

import java.io.IOException;

import com.codeminders.hidapi.ClassPathLibraryLoader;
import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDManager;

public class HIDSideWinder {

	/*static
    {
        System.loadLibrary("hidapi-jni-32");
    }*/
	
	static final int VENDOR_ID = 1118;
    static final int PRODUCT_ID = 56;
    private static final int BUFSIZE = 2048;
    private static final long READ_UPDATE_DELAY_MS = 10L;

    static HIDManager manager;
    static HIDDevice sideWinder;
    
	public static void main(String[] args) throws IOException {
		ClassPathLibraryLoader.loadNativeHIDLibrary();
		initDevice();
		logDT();
	}
	private static void initDevice() throws IOException{
		manager = HIDManager.getInstance();
		sideWinder = manager.openById(VENDOR_ID, PRODUCT_ID, null);
		sideWinder.enableBlocking();
	}
	private static void logDT() throws IOException{
		byte[] buf = new byte[BUFSIZE];
		int bufSize = 0;
		int time = 0;
		while (true){
			bufSize = sideWinder.read(buf);
			System.out.print("Time "+time+" msec: ");
			for (int i=0; i < bufSize; i++){
				int b = buf[i];
				if (b<0) 
					b +=256;
				System.out.print(b+" ");
			}
			System.out.println("");
			time += READ_UPDATE_DELAY_MS;			
			if (bufSize > 0){
				try
	            {
	                Thread.sleep(READ_UPDATE_DELAY_MS);
	            } catch(InterruptedException e)
	            {
	                //Ignore
	                e.printStackTrace();
	            }
			}
		}
	}
}
