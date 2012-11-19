package grinch.air.gui;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JList;

import com.codeminders.hidapi.HIDDeviceInfo;

public class CurrentDevicesDisplay implements Observer{
	private HIDDeviceInfo[] devs;
	public HIDDeviceInfo[] getDevs() {
		return devs;
	}
	JList<HIDDeviceInfo> list;
	JLabel devCount;
	Observable observable;
	
	public CurrentDevicesDisplay(Observable observable, JList<HIDDeviceInfo> list, JLabel devCount){
		this.observable = observable;
		this.list = list;
		this.devCount = devCount;
		observable.addObserver(this);
	}
	
	@Override
	public void update(Observable obs, Object arg1) {
		if (arg1 instanceof HIDDeviceInfo[]){
			this.devs = (HIDDeviceInfo[]) arg1;
			display();
		}
	}
	private void display(){
		if (this.list != null){
			list.setListData(devs);
			devCount.setText(devs.length + " devices");
		}
	}
}
