package grinch.air.gui;

import grinch.air.joycontrol.HIDDeviceReader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class HIDDeviceDisplay implements Observer {
	Observable observable;
	Boolean connected = false;
	byte[] data = new byte[0];
	
	/*
	 * Statistics
	 */
	private int packagesCount = 0;
	private String currentPackage = new String("-");
	private int countBytes = 0;
	private double speed = 0;
	private int time = 0;
	private Timer timer;
	private int delayTime = 0;
	
	public void resetStats(){
		packagesCount = 0;
		currentPackage = new String("-");
		countBytes = 0;
		speed = 0;
		time = 0;
	}
	
	private JLabel lblConnect;
	private JLabel lblHookedPackages;
	private JLabel lblTime;
	private JLabel lblCurrentPackage;
	private JLabel lblCountBytes;
	private JLabel lblSpeed;
	private JTextField textField;
	CursorDisplay cursorDisplay;
	
	public HIDDeviceDisplay(Observable observable, JLabel lblConnect, JLabel lblHookedPackages,
												   JLabel lblTime, JLabel lblCurrentPackage,
												   JLabel lblCountBytes, JLabel lblSpeed,JTextField textField,
												   CursorDisplay cursorDisplay){
		this.observable = observable;
		this.lblConnect = lblConnect;
		this.lblHookedPackages = lblHookedPackages;
		this.lblTime = lblTime;
		this.lblCurrentPackage = lblCurrentPackage;
		this.lblCountBytes = lblCountBytes;
		this.lblSpeed = lblSpeed;
		this.textField = textField;
		this.cursorDisplay = cursorDisplay;
		observable.addObserver(this);
		timer = new Timer(100, new ActionListener()
		  {
		      public void actionPerformed(ActionEvent e)
		      {
		    		time +=100;
		      }
		  } );
	}
	
	public void start(){
		timer.start();
		HIDDeviceReader source = (HIDDeviceReader) observable;
		source.setHooking(true);
	}
	
	public void stop(){
		timer.stop();
		HIDDeviceReader source = (HIDDeviceReader) observable;
		source.setHooking(false);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if ((arg1 instanceof Boolean)&&(!arg1.equals(null))){
			connected = (Boolean) arg1;
			if (connected){
				if (arg0 instanceof HIDDeviceReader){
					HIDDeviceReader source = (HIDDeviceReader) arg0;
					if (source.isHooking()) {
						data = source.getData();
						countBytes = source.getCountBytes();
						++packagesCount;	
						speed = packagesCount / (time/1000+1);
						if (delayTime != source.getReadUpdateDelayMs()){
							delayTime = source.getReadUpdateDelayMs();
							textField.setText(String.valueOf(delayTime));
						}
						StringBuilder str = new StringBuilder();
	                    for(int i=0; i<countBytes; i++)
	                    {
	                    	if (i != 0) str.append(":");
	                        int v = data[i];
	                        if (v<0) v = v+256;
	                        String hs = Integer.toHexString(v);
	                        if (v<16) 
	                        	str.append("0");
	                        str.append(hs);
	                    }
	                    int x = data[0];
						int y = data[1];
						if (x<0) x +=256;
						if (y<0) y +=256;
	                    cursorDisplay.setCoors(x,y);
	                    currentPackage = new String(str);
					}
					
				}
				displayON();
			}else{
				displayOFF();
			}
		}
	}
	void displayON(){
		lblConnect.setText("Connect: true");
		lblHookedPackages.setText("Hooked packages: "+packagesCount);
		lblTime.setText("Time: " + time + " ms");
		lblCurrentPackage.setText("Current package: "+currentPackage);
		lblCountBytes.setText("Count bytes: "+countBytes);
		lblSpeed.setText("Speed(pack/sec): "+speed);
	}
	void displayOFF(){
		lblConnect.setText("Connect: false");
	}

}
