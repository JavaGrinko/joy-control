package grinch.air.gui;

import grinch.air.joycontrol.HIDDeviceReader;
import grinch.air.joycontrol.HIDList;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.codeminders.hidapi.HIDDeviceInfo;

public class JoyControl {
	
	static
    {
        System.loadLibrary("hidapi-jni-32");
    }

	private JFrame frmJoyControl;
	private JList<HIDDeviceInfo> list;
	
	
	//labels
	JLabel lblPath;
	JLabel lblVendorId;
	JLabel lblProductId;
	JLabel lblSerialNumber;
	JLabel lblReleaseNumber;
	JLabel lblManufacturer;
	JLabel lblProduct;
	JLabel lblUsagePage;
	JLabel lblUsage;
	JLabel lblInterfaceNumber;
	JLabel lblDevcount;
	JTabbedPane tabbedPane;
	private JPanel darkTornado;
	private JLabel lblConnect;
	private JButton btnStartHooking;
	private JButton btnStopHooking;
	private JPanel panel;
	private JLabel lblHookedPackages;
	private JLabel lblTime;
	private JLabel lblCurrentPackage;
	private JLabel lblCountBytes;
	private JLabel lblSpeed;
	private JLabel lblDelayTime;
	private JTextField textField;
	private JLabel lblMs;
	private JButton btnSubmitDelay;
	private CursorDisplay display;
	
	HIDDeviceReader deviceReader = new HIDDeviceReader();
	HIDList hidList = new HIDList();
	CurrentDevicesDisplay curDevDisp;
	HIDDeviceDisplay deviceDisplay;
	private JButton btnReset;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JoyControl window = new JoyControl();
					window.frmJoyControl.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JoyControl() {
		//ClassPathLibraryLoader.loadNativeHIDLibrary();
		initialize();
		initList();
		initDeviceReader();
	}
	
	private void initDeviceReader(){
		deviceDisplay = new HIDDeviceDisplay(deviceReader, lblConnect, 
											 lblHookedPackages, lblTime, 
											 lblCurrentPackage, lblCountBytes, 
											 lblSpeed,textField,display);
		deviceReader.start();
	}
	
	private void initList(){
		hidList.start();
		ListSelectionListener listener = new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent listSelectionEvent){
				if (list.getSelectedIndex() > -1 && list.getSelectedIndex() < curDevDisp.getDevs().length){
					lblPath.setText("Path: " + curDevDisp.getDevs()[list.getSelectedIndex()].getPath());
					lblVendorId.setText("Vendor ID: " + curDevDisp.getDevs()[list.getSelectedIndex()].getVendor_id());;
					lblProductId.setText("Product ID: " + curDevDisp.getDevs()[list.getSelectedIndex()].getProduct_id());;
					lblSerialNumber.setText("Serial number: " + curDevDisp.getDevs()[list.getSelectedIndex()].getSerial_number());;
					lblReleaseNumber.setText("Release number: " + curDevDisp.getDevs()[list.getSelectedIndex()].getRelease_number());;
					lblManufacturer.setText("Manufacturer: " + curDevDisp.getDevs()[list.getSelectedIndex()].getManufacturer_string());;
					lblProduct.setText("Product: " + curDevDisp.getDevs()[list.getSelectedIndex()].getProduct_string());;
					lblUsagePage.setText("Usage page: " + curDevDisp.getDevs()[list.getSelectedIndex()].getUsage_page());;
					lblUsage.setText("Usage: " + curDevDisp.getDevs()[list.getSelectedIndex()].getUsage());;
					lblInterfaceNumber.setText("Interface number: " + curDevDisp.getDevs()[list.getSelectedIndex()].getInterface_number());;
				}
		    }
		};
		list.addListSelectionListener(listener);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmJoyControl = new JFrame();
		frmJoyControl.setTitle("Joy Control");
		frmJoyControl.setBounds(100, 100, 666, 398);
		frmJoyControl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJoyControl.getContentPane().setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 650, 359);
		frmJoyControl.getContentPane().add(tabbedPane);
		
		JPanel hidInfo = new JPanel();
		tabbedPane.addTab("HID Info", null, hidInfo, null);
		hidInfo.setLayout(null);
		
		JLabel lblDevices = new JLabel("Devices:");
		lblDevices.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDevices.setBounds(10, 11, 115, 14);
		hidInfo.add(lblDevices);
		
		list = new JList<HIDDeviceInfo>();
		list.setBounds(20, 36, 190, 264);
		hidInfo.add(list);
		
		JLabel lblHidInfo = new JLabel("HID Device info:");
		lblHidInfo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblHidInfo.setBounds(300, 11, 115, 14);
		hidInfo.add(lblHidInfo);
		
		lblPath = new JLabel("Path: -");
		lblPath.setBounds(300, 36, 335, 14);
		hidInfo.add(lblPath);
		
		lblVendorId = new JLabel("Vendor ID: -");
		lblVendorId.setBounds(300, 59, 335, 14);
		hidInfo.add(lblVendorId);
		
		lblProductId = new JLabel("Product ID: -");
		lblProductId.setBounds(300, 84, 335, 14);
		hidInfo.add(lblProductId);
		
		lblSerialNumber = new JLabel("Serial number: -");
		lblSerialNumber.setBounds(300, 109, 335, 14);
		hidInfo.add(lblSerialNumber);
		
		lblReleaseNumber = new JLabel("Release number: -");
		lblReleaseNumber.setBounds(300, 134, 335, 14);
		hidInfo.add(lblReleaseNumber);
		
		lblManufacturer = new JLabel("Manufacturer: -");
		lblManufacturer.setBounds(300, 159, 335, 14);
		hidInfo.add(lblManufacturer);
		
		lblProduct = new JLabel("Product: -");
		lblProduct.setBounds(300, 184, 335, 14);
		hidInfo.add(lblProduct);
		
		lblUsagePage = new JLabel("Usage page: -");
		lblUsagePage.setBounds(300, 209, 335, 14);
		hidInfo.add(lblUsagePage);
		
		lblUsage = new JLabel("Usage: -");
		lblUsage.setBounds(300, 234, 335, 14);
		hidInfo.add(lblUsage);
		
		lblInterfaceNumber = new JLabel("Interface number: -");
		lblInterfaceNumber.setBounds(300, 259, 335, 14);
		hidInfo.add(lblInterfaceNumber);
		
		lblDevcount = new JLabel("devices");
		lblDevcount.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblDevcount.setBounds(24, 306, 186, 14);
		hidInfo.add(lblDevcount);
		
		darkTornado = new JPanel();
		tabbedPane.addTab("HID Device monitor", null, darkTornado, null);
		darkTornado.setLayout(null);
		curDevDisp = new CurrentDevicesDisplay(hidList,list, lblDevcount);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (list.getSelectedIndex() > -1 && list.getSelectedIndex() < curDevDisp.getDevs().length){
					deviceReader.changeDevice(curDevDisp.getDevs()[list.getSelectedIndex()].getVendor_id(), 
											  curDevDisp.getDevs()[list.getSelectedIndex()].getProduct_id());
										
				}
				tabbedPane.setTitleAt(1, curDevDisp.getDevs()[list.getSelectedIndex()].getManufacturer_string() 
										+ " " 
										+ curDevDisp.getDevs()[list.getSelectedIndex()].getProduct_string());
			}
		});
		btnConnect.setBounds(300, 284, 135, 23);
		hidInfo.add(btnConnect);
		
		lblConnect = new JLabel("Connect: ");
		lblConnect.setBounds(10, 11, 199, 14);
		darkTornado.add(lblConnect);
		
		btnStartHooking = new JButton("Start Hooking");
		btnStartHooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deviceDisplay.start();
			}
		});
		btnStartHooking.setBounds(10, 36, 119, 23);
		darkTornado.add(btnStartHooking);
		
		btnStopHooking = new JButton("Stop Hooking");
		btnStopHooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deviceDisplay.stop();
			}
		});
		btnStopHooking.setBounds(10, 70, 119, 23);
		darkTornado.add(btnStopHooking);
		
		panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 104, 263, 140);
		darkTornado.add(panel);
		panel.setLayout(null);
		
		lblHookedPackages = new JLabel("Hooked packages:");
		lblHookedPackages.setBounds(10, 11, 179, 14);
		panel.add(lblHookedPackages);
		
		lblTime = new JLabel("Time:");
		lblTime.setBounds(10, 36, 179, 14);
		panel.add(lblTime);
		
		lblCurrentPackage = new JLabel("Current package:");
		lblCurrentPackage.setBounds(10, 86, 243, 14);
		panel.add(lblCurrentPackage);
		
		lblCountBytes = new JLabel("Count bytes:");
		lblCountBytes.setBounds(10, 111, 243, 14);
		panel.add(lblCountBytes);
		
		lblSpeed = new JLabel("Speed(pack/sec):");
		lblSpeed.setBounds(10, 61, 243, 14);
		panel.add(lblSpeed);
		
		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deviceDisplay.resetStats();
			}
		});
		btnReset.setBounds(180, 111, 73, 23);
		panel.add(btnReset);
		
		lblDelayTime = new JLabel("Delay time:");
		lblDelayTime.setBounds(10, 255, 74, 14);
		darkTornado.add(lblDelayTime);
		
		textField = new JTextField();
		textField.setToolTipText("");
		textField.setBounds(81, 252, 86, 20);
		darkTornado.add(textField);
		textField.setColumns(10);
		
		lblMs = new JLabel("ms");
		lblMs.setBounds(177, 255, 46, 14);
		darkTornado.add(lblMs);
		
		btnSubmitDelay = new JButton("Submit delay");
		btnSubmitDelay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deviceReader.setReadUpdateDelayMs(Integer.parseInt(textField.getText().toString()));
			}
		});
		btnSubmitDelay.setBounds(10, 280, 169, 23);
		darkTornado.add(btnSubmitDelay);
		
		display = new CursorDisplay();
		display.setBounds(347, 33, 256, 256);
		//display.setVisible(true);
		darkTornado.add(display);
		//display.setBorder(null);
	}
}
